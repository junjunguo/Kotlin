package com.junjunguo.user.models.api

class UserRegisterModel(val name: String, val password: String, val email: String?) {
    constructor(name: String, password: String) : this(name, password, null)
}