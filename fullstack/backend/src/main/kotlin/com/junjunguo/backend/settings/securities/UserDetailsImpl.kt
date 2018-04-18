package com.junjunguo.backend.settings.securities

import com.junjunguo.backend.models.entities.UserEntity
import com.junjunguo.backend.models.enums.RoleEnum
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserDetailsImpl() : UserDetails {

    private var name: String = ""
    private var password: String = ""

    private var isEnabled: Boolean = true
    private var isCredentialsNonExpired: Boolean = true
    private var isAccountNonExpired: Boolean = true
    private var isAccountNonLocked: Boolean = true

    constructor(ue: UserEntity) : this() {
        name = ue.name
        password = ue.password
        isEnabled = ue.isEnabled
        isCredentialsNonExpired = ue.isCredentialsNonExpired
        isAccountNonExpired = ue.isAccountNonExpired
        isAccountNonLocked = ue.isAccountNonLocked
    }

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
    override fun isEnabled() = isEnabled


    /**
     * Returns the username used to authenticate the user. Cannot return `null`.
     *
     * @return the username (never `null`)
     */
    override fun getUsername() = name


    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return `true` if the user's credentials are valid (ie non-expired),
     * `false` if no longer valid (ie expired)
     */
    override fun isCredentialsNonExpired() = isCredentialsNonExpired


    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    override fun getPassword() = this.password


    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return `true` if the user's account is valid (ie non-expired),
     * `false` if no longer valid (ie expired)
     */
    override fun isAccountNonExpired() = isAccountNonExpired


    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return `true` if the user is not locked, `false` otherwise
     */
    override fun isAccountNonLocked() = isAccountNonLocked

}
