package de.sschrass.rootresource.home

import de.sschrass.rootresource.home.endpoint.*
import org.springframework.boot.info.BuildProperties
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.lang.reflect.Method

@Component
class ControllerMapping(
        val requestMappingHandlerMapping: RequestMappingHandlerMapping,
        val buildProperties: BuildProperties
) {

    fun asHome() = requestMappingHandlerMapping
            .handlerMethods
            .map { endpointMethodMappings(it.key, it.value) }
            .flatten()
            .let(EndpointMethodsMapping.Companion::fromEndpointMethodMappings)
            .let { endpointMethodsMapping ->
                Home(
                        service = buildProperties.name,
                        description = buildProperties.get("description"),
                        version = buildProperties.version,
                        build = buildProperties.time,
                        author = buildProperties.get("author"),
                        self = endpointMethodsMapping
                                .filter { it.endpoint == Endpoint.HOME }
                                .map { it.methods["GET"] }
                                .map { it?.link }
                                .firstOrNull(),
                        endpoints = endpointMethodsMapping.map { it.endpoint.id to it }.toMap()
                )
            }


    private fun endpointMethodMappings(requestMapping: RequestMapping, handlerMethod: HandlerMethod) =
            endpointFromMethod(handlerMethod.method)
                    ?.let { endpoint ->
                        requestMapping
                                .methodsCondition
                                .methods
                                .mapNotNull { HttpMethod(it.name, linkFrom(requestMapping, handlerMethod)) }
                                .map { EndpointMethodMapping(endpoint, it) }
                    }
                    ?: emptyList()

    private fun linkFrom(requestMapping: RequestMapping, handlerMethod: HandlerMethod) =
            Link(
                    href = hrefFromRequestMapping(requestMapping),
                    title = endpointMappingFromMethod(handlerMethod.method)
                            ?.methodDescription
                            ?: "Oh noes",
                    type = mediaTypeFromRequestMapping(requestMapping)
            )

    private fun hrefFromRequestMapping(requestMapping: RequestMapping) =
            /*TODO Host from request*/ pathFromRequestMapping(requestMapping) + queryFromRequestMapping(requestMapping)

    private fun mediaTypeFromRequestMapping(requestMapping: RequestMapping) = requestMapping
            .producesCondition
            .toString()
            .removeSurrounding("[", "]")

    private fun pathFromRequestMapping(requestMapping: RequestMapping) = requestMapping
            .patternsCondition
            .toString()
            .removeSurrounding("[", "]")

    private fun queryFromRequestMapping(requestMapping: RequestMapping) = requestMapping
            .paramsCondition
            .toString()
            .removeSurrounding("[", "]")

    private fun endpointFromMethod(method: Method) = endpointMappingFromMethod(method)?.endpoint

    private fun endpointMappingFromMethod(method: Method) = AnnotationUtils.findAnnotation(method, EndpointMapping::class.java)

}

typealias RequestMapping = RequestMappingInfo
