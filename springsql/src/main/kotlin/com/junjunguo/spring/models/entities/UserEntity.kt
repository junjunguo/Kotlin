package com.junjunguo.spring.models.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
//@Table(name = "user") // java persistence table error.
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var email: String="",
    var name: String="",
    var password: String=""
//    @CreationTimestamp
//    var modified: Date = Date(),
//    @UpdateTimestamp
//    var created: Date = Date()
) {
//    constructor() : this(-1, "", "", "")
}