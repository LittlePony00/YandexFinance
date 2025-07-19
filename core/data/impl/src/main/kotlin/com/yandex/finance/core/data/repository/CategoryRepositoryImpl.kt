package com.yandex.finance.core.data.repository

import com.yandex.finance.core.data.mapper.asExternalModel
import com.yandex.finance.core.data.observer.NetworkConnectivityObserver
import com.yandex.finance.core.domain.model.category.Category
import com.yandex.finance.core.localdb.dao.CategoryDao
import com.yandex.finance.core.localdb.mapper.toDomainModel
import com.yandex.finance.core.localdb.mapper.toEntity
import com.yandex.finance.core.network.category.service.CategoryService
import timber.log.Timber
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val categoryDao: CategoryDao,
    private val connectivityObserver: NetworkConnectivityObserver
) : CategoryRepository {

    override suspend fun fetchCategories(): Result<List<Category>> {
        return try {
            val localCategories = categoryDao.getAllCategoriesSync()
            
            if (connectivityObserver.isConnected()) {
                try {
                    val networkResult = categoryService.fetchCategories()
                    networkResult.fold(
                        onSuccess = { networkCategories ->
                            val entities = networkCategories.map {
                                it.asExternalModel().toEntity(
                                    lastSyncAt = System.currentTimeMillis()
                                )
                            }
                            categoryDao.insertCategories(entities)
                            
                            val domainCategories = networkCategories.map { it.asExternalModel() }
                            Result.success(domainCategories)
                        },
                        onFailure = { error ->
                            Timber.w(error, "Failed to fetch from server, using local data")
                            val localDomainCategories = localCategories.map { it.toDomainModel() }
                            Result.success(localDomainCategories)
                        }
                    )
                } catch (e: Exception) {
                    Timber.w(e, "Network error, using local data")
                    val localDomainCategories = localCategories.map { it.toDomainModel() }
                    Result.success(localDomainCategories)
                }
            } else {
                val localDomainCategories = localCategories.map { it.toDomainModel() }
                Result.success(localDomainCategories)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch categories")
            Result.failure(e)
        }
    }

    override suspend fun fetchCategoriesByType(isIncome: Boolean): Result<List<Category>> {
        return try {
            val localCategories = categoryDao.getCategoriesByTypeSync(isIncome)
            
            if (connectivityObserver.isConnected()) {
                try {
                    val networkResult = categoryService.fetchCategoriesByType(isIncome)
                    networkResult.fold(
                        onSuccess = { networkCategories ->
                            val entities = networkCategories.map {
                                it.asExternalModel().toEntity(
                                    lastSyncAt = System.currentTimeMillis()
                                )
                            }
                            categoryDao.insertCategories(entities)
                            
                            val domainCategories = networkCategories.map { it.asExternalModel() }
                            Result.success(domainCategories)
                        },
                        onFailure = { error ->
                            Timber.w(error, "Failed to fetch from server, using local data")
                            val localDomainCategories = localCategories.map { it.toDomainModel() }
                            Result.success(localDomainCategories)
                        }
                    )
                } catch (e: Exception) {
                    Timber.w(e, "Network error, using local data")
                    val localDomainCategories = localCategories.map { it.toDomainModel() }
                    Result.success(localDomainCategories)
                }
            } else {
                val localDomainCategories = localCategories.map { it.toDomainModel() }
                Result.success(localDomainCategories)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch categories by type")
            Result.failure(e)
        }
    }
}
