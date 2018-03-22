package com.junjunguo.kotlin.classes

class Man {
    val children: MutableList<Man> = ArrayList()

    constructor()

    constructor(father: Man) {
        father.children.add(this)
    }
}