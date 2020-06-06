package de.sschrass.rootresource.home.endpoint

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class EndpointMapping(
        val endpoint : Endpoint,
        val methodDescription : String
)