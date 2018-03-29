package com.junjunguo.user.models.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var email: String = "",
    var name: String = "",
    var password: String = "",

    @CreationTimestamp
    var modified: Date = Date(),

    @UpdateTimestamp
    var created: Date = Date()
//
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    var uuid: UUID = UUID.randomUUID()
)