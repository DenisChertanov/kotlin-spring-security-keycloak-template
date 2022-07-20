package ru.dchertanov.kotlinspringsecuritykeycloaktemplate.security

import org.keycloak.adapters.springsecurity.KeycloakConfiguration
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy


@KeycloakConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : KeycloakWebSecurityConfigurerAdapter() {

    override fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy? {
        return RegisterSessionAuthenticationStrategy(SessionRegistryImpl())
    }

    @Autowired
    fun configureGlobal(authManagerBuilder: AuthenticationManagerBuilder) {
        val keycloakAuthenticationProvider = keycloakAuthenticationProvider()
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(SimpleAuthorityMapper())
        authManagerBuilder.authenticationProvider(keycloakAuthenticationProvider)
    }

    override fun configure(http: HttpSecurity) {
        super.configure(http)

        http
            .authorizeRequests()
            .antMatchers("/api/anonymous/**").permitAll()
            .anyRequest().fullyAuthenticated();
    }
}