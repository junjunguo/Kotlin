package com.junjunguo.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.ZoneOffset
import java.util.*
import javax.annotation.PostConstruct


@SpringBootApplication
class BackendApplication

@PostConstruct
fun started() {
    TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC))
}

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}
