package com.junjunguo.user

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class UserDemoApplication

fun main(args: Array<String>) {
    SpringApplication.run(UserDemoApplication::class.java, *args)
}
