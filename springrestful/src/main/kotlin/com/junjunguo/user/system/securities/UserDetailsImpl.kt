package com.junjunguo.user.system.securities

import com.junjunguo.user.models.entities.UserEntity
import com.junjunguo.user.models.enums.RoleEnum
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserDetailsImpl() : UserDetails {

    private var email: String? = null
    private var name: String = ""
    private var id: Long = -1
    private var password: String = ""

    constructor(email: String?, name: String, id: Long = -1, password: String) : this() {
        this.email = email
        this.name = name
        this.id = id
        this.password = password
    }

    constructor(ue: UserEntity) : this(ue.email, ue.name, ue.id ?: -1, ue.password)

    /**
     * Returns the authorities granted to the user. Cannot return `null`.
     *
     * @return the authorities, sorted by natural key (never `null`)
     */
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = ArrayList<SimpleGrantedAuthority>()
        authorities.add(SimpleGrantedAuthority(RoleEnum.ROLE_USER.name))
        return authorities
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return `true` if the user is enabled, `false` otherwise
     */
    override fun isEnabled(): Boolean {
        return true
    }

    /**
     * Returns the username used to authenticate the user. Cannot return `null`.
     *
     * @return the username (never `null`)
     */
    override fun getUsername(): String {
        return name
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return `true` if the user's credentials are valid (ie non-expired),
     * `false` if no longer valid (ie expired)
     */
    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    override fun getPassword(): String {
        return this.password
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return `true` if the user's account is valid (ie non-expired),
     * `false` if no longer valid (ie expired)
     */
    override fun isAccountNonExpired(): Boolean {
        return false
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return `true` if the user is not locked, `false` otherwise
     */
    override fun isAccountNonLocked(): Boolean {
        return false
    }
}
