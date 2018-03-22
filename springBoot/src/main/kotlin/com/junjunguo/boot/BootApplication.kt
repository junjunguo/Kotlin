package com.junjunguo.boot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BootApplication

fun main(args: Array<String>) {
    runApplication<BootApplication>(*args)
}
