package de.sschrass.rootresource.home.endpoint

import com.fasterxml.jackson.annotation.JsonFormat

@Suppress("unused")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class Endpoint(
        val id: String,
        val title  : String,
        val description : String
)  {
    FOOS(
            id = "urn:example:endpoints:foo",
            title = "The Foos endpoint",
            description = "offers operations on Foos"
    ),
    FOO_FROM_FOOID(
            id = "urn:example:endpoints:foo_from_id",
            title = "The Foos from id endpoint",
            description = "offers operations on a Foo"
    );

}
