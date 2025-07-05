package com.yandex.finance.core.network

import io.ktor.client.call.HttpClientCall
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SafeResponseTest {

    @Test
    fun `handleResponse should retry 3 times on 500 error before failing`() = runTest {
        // Given
        val mockCall = mockk<HttpClientCall>()
        val mockResponse = mockk<HttpResponse> {
            every { status } returns HttpStatusCode.InternalServerError
            every { call } returns mockCall
            coEvery { body<String>() } throws Exception("Server error")
        }

        // When/Then
        assertFailsWith<Exception> {
            mockResponse.handleResponse<String>()
        }

        // Verify that body was called 3 times (3 retries)
        coVerify(exactly = 3) { mockResponse.body<String>() }
        verify(exactly = 3) { mockResponse.call }
    }

    @Test
    fun `handleResponse should return successfully if retry succeeds`() = runTest {
        // Given
        var attempts = 0
        val expectedResponse = "Success"
        val mockCall = mockk<HttpClientCall>()
        val mockResponse = mockk<HttpResponse> {
            every { status } returns HttpStatusCode.InternalServerError
            every { call } returns mockCall
            coEvery { body<String>() } answers {
                attempts++
                if (attempts <= 2) {
                    throw Exception("Server error")
                } else {
                    expectedResponse
                }
            }
        }

        // When
        val result = mockResponse.handleResponse<String>()

        // Then
        assertEquals(expectedResponse, result)
        // Verify body was called 3 times (2 failures + 1 success)
        coVerify(exactly = 3) { mockResponse.body<String>() }
    }

    @Test
    fun `handleResponse should not retry on non-500 status`() = runTest {
        // Given
        val expectedResponse = "Success"
        val mockCall = mockk<HttpClientCall>()
        val mockResponse = mockk<HttpResponse> {
            every { status } returns HttpStatusCode.OK
            every { call } returns mockCall
            coEvery { body<String>() } returns expectedResponse
        }

        // When
        val result = mockResponse.handleResponse<String>()

        // Then
        assertEquals(expectedResponse, result)
        // Verify body was called only once
        coVerify(exactly = 1) { mockResponse.body<String>() }
    }
}
