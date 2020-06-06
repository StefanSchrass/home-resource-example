package de.sschrass.rootresource

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RootResourceApplication

fun main(args: Array<String>) {
    runApplication<RootResourceApplication>(*args)
}
