package com.junjunguo.backend.models.api

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class UserRegisterModel(@NotNull @Size(min = 3, max = 9) val name: String, val password: String, val email: String?) {
    constructor(name: String, password: String) : this(name, password, null)
    constructor() : this("", "", null)
}