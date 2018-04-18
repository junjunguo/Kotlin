package com.junjunguo.backend.models.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Email()
    @Column(nullable = true)
    var email: String?,

    @Column(unique = true, nullable = true, length = 32)
    var mobile: String?,

    @Column(unique = true, nullable = false)
    var name: String,

    @Column(nullable = false, length = 128)
    var password: String,

    /** @UserDetails
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     * `true` if the user is enabled, `false` otherwise
     */
    @Column(name = "is_enabled")
    var isEnabled: Boolean = true,
    /** @UserDetails
     * Indicates whether the user's credentials (password) has expired. Expired credentials prevent authentication.
     * `true` if the user's credentials are valid (ie non-expired), `false` if no longer valid (ie expired)
     */
    @Column(name = "is_credentials_non_expired")
    var isCredentialsNonExpired: Boolean = true,
    /** @UserDetails
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     * `true` if the user's account is valid (ie non-expired), `false` if no longer valid (ie expired)
     */
    @Column(name = "is_account_non_expired")
    var isAccountNonExpired: Boolean = true,
    /** @UserDetails
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     * `true` if the user is not locked, `false` otherwise
     */
    @Column(name = "is_account_non_locked")
    var isAccountNonLocked: Boolean = true,

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    var modified: Date = Date(System.currentTimeMillis()),

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    var created: Date = Date(System.currentTimeMillis()),

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    var uuid: UUID = UUID.randomUUID()
) {

    constructor() : this("", "")

    constructor(name: String, password: String) : this(
        null,
        null,
        null,
        name,
        password,
        true,
        true,
        true,
        true
    )

    constructor(email: String?, name: String, password: String) : this(
        null,
        email,
        null,
        name,
        password,
        true,
        true,
        true,
        true
    )
}