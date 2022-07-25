package com.magnuspedro.refgraph.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.web.server.SecurityWebFilterChain


@EnableWebFluxSecurity
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
class SecurityConfig {

    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private lateinit var issuerUri: String

    @Bean
    fun jwtDecoder(): ReactiveJwtDecoder? {
        return ReactiveJwtDecoders.fromOidcIssuerLocation(issuerUri);
    }

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        http.authorizeExchange().pathMatchers(
            HttpMethod.GET,
            "/swagger-ui.html/**",
            "/configuration/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/webjars/**"
        ).permitAll().anyExchange().authenticated().and().oauth2ResourceServer().jwt()
        return http.build()
    }
}
