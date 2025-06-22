package com.yandex.finance.core.ui.util

fun String.formatWithSeparator(
    separator: String = " ",
    groupSize: Int = 3
): String {
    if (length <= groupSize) return this

    var str: String = this.substringAfter('.', "")
    if (str != "") {
        str = ".$str"
    }

    return substringBefore('.')
        .reversed()
        .chunked(groupSize)
        .joinToString(separator)
        .reversed() + str
}
