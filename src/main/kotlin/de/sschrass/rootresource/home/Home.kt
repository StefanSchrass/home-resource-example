package de.sschrass.rootresource.home

import de.sschrass.rootresource.home.endpoint.EndpointMethodsMapping
import java.time.Instant

data class Home(
        val service: String,
        val description: String?,
        val version: String,
        val build: Instant,
        val author: String?,
        val endpoints: Map<String, EndpointMethodsMapping> = emptyMap()
)