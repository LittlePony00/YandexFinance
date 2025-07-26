package com.ortin.ortinFyForAuthors.core.ui.components.diagram.common

fun formatNumber(number: Int): String {
    return when {
        number >= 1_000_000_000 -> {
            val value = number / 1_000_000_000.0
            if (value % 1 == 0.0) "${value.toInt()}B" else String.format("%.1fB", value)
        }
        number >= 1_000_000 -> {
            val value = number / 1_000_000.0
            if (value % 1 == 0.0) "${value.toInt()}M" else String.format("%.1fM", value)
        }
        number >= 1_000 -> {
            val value = number / 1_000.0
            if (value % 1 == 0.0) "${value.toInt()}K" else String.format("%.1fK", value)
        }
        else -> number.toString()
    }
}
