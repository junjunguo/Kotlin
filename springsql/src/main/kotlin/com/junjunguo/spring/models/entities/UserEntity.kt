package com.junjunguo.spring.models.entities

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "user")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val email: String = "",
    val name: String = "",
    val password: String = ""
//    @Temporal(TemporalType.TIMESTAMP)
//    val timestamp: Date
) : Serializable