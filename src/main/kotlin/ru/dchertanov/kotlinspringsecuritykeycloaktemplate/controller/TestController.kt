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

    @GetMapping("/user")
    @PreAuthorize("hasRole('user')")
    fun userAccess(): ResponseEntity<String> {
        logger.info("User request was received!")

        return ResponseEntity.ok("USER")
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('admin')")
    fun adminAccess(): ResponseEntity<String> {
        logger.info("Admin request was received!")

        return ResponseEntity.ok("ADMIN")
    }

    @GetMapping("/me")
    fun getMe(): Any? {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        return authentication.name
    }
}