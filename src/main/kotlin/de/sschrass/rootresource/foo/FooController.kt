package de.sschrass.rootresource.foo

import de.sschrass.rootresource.home.ControllerMapping
import de.sschrass.rootresource.home.endpoint.Endpoint.*
import de.sschrass.rootresource.home.endpoint.EndpointMapping
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/foo", produces = ["application/vnd.example.foo+json"], consumes = ["application/vnd.example.foo+json"])
class FooController(
        private val controllerMapping: ControllerMapping
) {

    @EndpointMapping(HOME, "the home for this service")
    @GetMapping("home", produces = ["application/vnd.example.home+json"])
    fun get() = ResponseEntity.status(OK).body(controllerMapping.asHome())

    @EndpointMapping(FOOS, "for reading Foos")
    @GetMapping(produces = ["application/vnd.example.page+json"])
    fun getFoos() = ResponseEntity.status(OK).body("foos, lots of them but only a certain page.")

    @EndpointMapping(FOOS, "for adding a Foo")
    @PostMapping
    fun postFoo(@RequestBody body: String) = ResponseEntity.status(ACCEPTED)

    @EndpointMapping(FOO_FROM_FOOID, "for getting a Foo")
    @GetMapping("{fooId}")
    fun getFoo(@PathVariable("fooId") fooId: String, @RequestBody body: String) = ResponseEntity.status(OK).body("foo")

    @EndpointMapping(FOO_FROM_FOOID, "for modifying a Foo")
    @PutMapping("{fooId}")
    fun putFoo(@PathVariable("fooId") fooId: String, @RequestBody body: String) = ResponseEntity.status(NO_CONTENT)

    @EndpointMapping(FOO_FROM_FOOID, "for deleting a Foo")
    @DeleteMapping("{fooId}")
    fun deleteFoo(@PathVariable("fooId") fooId: String) = ResponseEntity.status(NO_CONTENT)

}