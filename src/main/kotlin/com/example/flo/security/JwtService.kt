package com.example.flo.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date

@Component
class JwtService(
    private val securityProperties: SecurityProperties
) {

    private val signingKey
        get() = Keys.hmacShaKeyFor(securityProperties.jwt.secret.toByteArray(StandardCharsets.UTF_8))

    fun generateToken(username: String): String {
        val now = Date()
        val expiration = Date(now.time + securityProperties.jwt.expiration)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun extractUsername(token: String): String = parseClaims(token).subject

    fun isTokenValid(token: String): Boolean = try {
        val claims = parseClaims(token)
        claims.expiration.after(Date())
    } catch (ex: Exception) {
        false
    }

    private fun parseClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .body
    }
}
