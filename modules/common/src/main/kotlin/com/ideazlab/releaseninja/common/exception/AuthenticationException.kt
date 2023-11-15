package com.ideazlab.releaseninja.common.exception

import io.micronaut.security.authentication.AuthenticationFailureReason
class AuthenticationException(failure: AuthenticationFailureReason):Exception() {
    init {
        when(failure){
            AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH ->{ Exception("credentials don't match.")}
            AuthenticationFailureReason.USER_DISABLED ->{ Exception("account is disabled")}
            AuthenticationFailureReason.ACCOUNT_EXPIRED ->{ Exception("account expired")}
            AuthenticationFailureReason.ACCOUNT_LOCKED ->{ Exception("account was locked")}
            AuthenticationFailureReason.PASSWORD_EXPIRED ->{ Exception("password expired")}
            else ->{ Exception("UnKnown Authentication Exception")}
        }
    }
    private val failure = failure
    fun getFailure() = failure
}