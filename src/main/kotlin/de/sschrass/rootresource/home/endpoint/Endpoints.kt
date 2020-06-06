package de.sschrass.rootresource.home.endpoint

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonUnwrapped

data class HttpMethod(
        @JsonIgnore
        val name: String,
        @JsonUnwrapped
        val link: Link
)
