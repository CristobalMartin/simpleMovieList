package com.cristobal.simplemovielist.model

// Full response of the discover films webservice
data class DiscoverFilmsWSResult(
        val page: Int,
        val results: List<Film>
        )


