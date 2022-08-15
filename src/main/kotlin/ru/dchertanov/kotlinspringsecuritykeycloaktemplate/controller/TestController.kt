package ru.dchertanov.kotlinspringsecuritykeycloaktemplate.controller

import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


private val logger = KotlinLogging.logger() {}

@RestController
@RequestMapping("/api")
class TestController {

    @GetMapping("/anonymous")
    fun anonymousAccess(): ResponseEntity<String> {
        logger.info("Anonymous request was received!")

        return ResponseEntity.ok("ANONYMOUS")
    }

    @GetMapping("/self_employed")
    @PreAuthorize("hasRole('self_employed')")
    fun selfEmployedAccess(): ResponseEntity<String> {
        logger.info("self_employed request was received!")

        return ResponseEntity.ok("self_employed")
    }

    @GetMapping("/not_confirmed_self_employed")
    @PreAuthorize("hasRole('not_confirmed_self_employed')")
    fun notConfirmedSelfEmployedAccess(): ResponseEntity<String> {
        logger.info("not_confirmed_self_employed request was received!")

        return ResponseEntity.ok("not_confirmed_self_employed")
    }

    @GetMapping("/employee")
    @PreAuthorize("hasRole('employee')")
    fun employeeAccess(): ResponseEntity<String> {
        logger.info("employee request was received!")

        return ResponseEntity.ok("employee")
    }

    @GetMapping("/me")
    fun getMe(): Any? {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        return authentication.name
    }
}