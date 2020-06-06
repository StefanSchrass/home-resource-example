package de.sschrass.rootresource.home.endpoint

import com.fasterxml.jackson.annotation.JsonUnwrapped

data class EndpointMethodsMapping(
        @JsonUnwrapped
        val endpoint: Endpoint,
        val methods: Map<String, HttpMethod>
) {
    companion object {
        fun fromEndpointMethodMappings(endpointMethodsMapping: Collection<EndpointMethodMapping>) = endpointMethodsMapping
                .groupBy { it.endpoint }
                .mapValues { entry -> entry.value.map { it.method } }
                .map { entry -> EndpointMethodsMapping(entry.key, entry.value.map { it.name to it }.toMap()) }
    }
}