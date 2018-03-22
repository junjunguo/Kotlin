package com.junjunguo.boot

import java.util.*

data class Person(var name: String) {
    val id: String = UUID.randomUUID().toString()
    var age: Int = 0

    constructor(name: String, age: Int) : this(name) {
        this.age = age
        this.name = name
    }

}