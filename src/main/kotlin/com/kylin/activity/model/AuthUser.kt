package com.kylin.activity.model

import com.kylin.activity.databases.tables.pojos.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthUser constructor(user: User): UserDetails {

    var user: User = user


    override fun getUsername(): String? {
        return this.user.username
    }

    override fun getPassword(): String? {
        return this.user.password
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return this.user.enabled
    }

}



