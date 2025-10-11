package com.example.flo.config

import com.example.flo.security.JwtAuthenticationFilter
import com.example.flo.security.JwtService
import com.example.flo.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableMethodSecurity
class SecurityConfig(
    private val securityProperties: SecurityProperties,
    private val jwtService: JwtService
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): UserDetailsService {
        val admin = User.withUsername(securityProperties.admin.username)
            .password(passwordEncoder.encode(securityProperties.admin.password))
            .roles("ADMIN")
            .build()

        return InMemoryUserDetailsManager(admin)
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf(
            "http://localhost:5173",
            "http://flo-admin-front.151.244.72.126.nip.io",
            "http://flo-frontend.151.244.72.126.nip.io"
        )
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun basicAuthFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatcher("/api/auth/token")
            .cors { }
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(HttpMethod.OPTIONS).permitAll()
                auth.anyRequest().authenticated()
            }
            .httpBasic { }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        return http.build()
    }

    @Bean
    fun apiFilterChain(http: HttpSecurity, userDetailsService: UserDetailsService): SecurityFilterChain {
        val jwtAuthenticationFilter = JwtAuthenticationFilter(jwtService, userDetailsService)

        http
            .cors { }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/",
                    "/api",
                    "/docs"
                ).permitAll()
                auth.requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                auth.requestMatchers(HttpMethod.GET, "/api/auth/**").permitAll()
                auth.requestMatchers(HttpMethod.GET, "/api/categories").permitAll()
                auth.requestMatchers(HttpMethod.GET, "/api/orders").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.GET, "/api/orders/**").permitAll()
                auth.requestMatchers(HttpMethod.POST, "/api/orders").permitAll()
                auth.requestMatchers(HttpMethod.PUT, "/api/orders/**").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.POST, "/api/categories").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
                auth.requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")
                auth.requestMatchers("/api/photos/**").permitAll()
                auth.requestMatchers("/telegram/**").permitAll()
                auth.anyRequest().permitAll()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
