package com.ideazlab.releaseninja.common.utils

import jakarta.inject.Singleton
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Singleton
class BCryptPasswordEncoderService : PasswordEncoder {
    var delegate: PasswordEncoder = BCryptPasswordEncoder()
    override fun encode(rawPassword: CharSequence?): String {
        return delegate.encode(rawPassword)
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        return delegate.matches(rawPassword, encodedPassword)
    }
}