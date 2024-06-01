package com.personal.weathering.search.domain.models

import androidx.annotation.StringRes
import com.personal.weathering.R

data class SearchLanguage(@StringRes val name: Int, val code: String) {
    companion object {
        val languages = listOf(
            SearchLanguage(R.string.english, "en"),
            SearchLanguage(R.string.german, "de"),
            SearchLanguage(R.string.french, "fr"),
            SearchLanguage(R.string.spanish, "es"),
            SearchLanguage(R.string.italian, "it"),
            SearchLanguage(R.string.portuguese, "pt"),
            SearchLanguage(R.string.russian, "ru"),
            SearchLanguage(R.string.turkish, "tr"),
            SearchLanguage(R.string.hindi, "hi")
        )
    }
}