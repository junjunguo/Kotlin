package com.junjunguo.user.models.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.validator.constraints.Length
import java.sql.Timestamp
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    @Column(unique = true)
    var email: String = "",

    @Column(unique = true)
    var name: String = "",

    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    var password: String = "",

    var active: Int = 0,

    @OneToMany(mappedBy = "user")
    var roles: MutableList<Role> = mutableListOf(),

    @CreationTimestamp
    var modified: Timestamp? = Timestamp(System.currentTimeMillis()),

    @UpdateTimestamp
    var created: Timestamp? = Timestamp(System.currentTimeMillis()),

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    var uuid: UUID = UUID.randomUUID()
) {
    constructor(name: String, password: String) : this(null, "", name, password, 1)

    fun addRole(role: Role) = roles.add(role)
}