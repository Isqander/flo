package com.example.flo.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "security")
class SecurityProperties {
    val admin: Admin = Admin()
    val jwt: Jwt = Jwt()

    class Admin {
        var username: String = "admin"
        var password: String = "changeMe"
    }

    class Jwt {
        var secret: String = "0123456789abcdef0123456789abcdef"
        var expiration: Long = 3_600_000L
    }
}
