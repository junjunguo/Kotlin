package com.junjunguo.user.models.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var email: String = "",
    var name: String = "",
    var password: String = "",

    @CreationTimestamp
    var modified: Timestamp? = Timestamp(System.currentTimeMillis()),

    @UpdateTimestamp
    var created: Timestamp? = Timestamp(System.currentTimeMillis()),

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    var uuid: UUID = UUID.randomUUID()
)