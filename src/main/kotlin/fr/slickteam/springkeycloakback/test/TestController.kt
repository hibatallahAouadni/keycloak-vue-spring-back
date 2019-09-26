package fr.slickteam.springkeycloakback.test

import fr.slickteam.springkeycloakback.ApiResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@CrossOrigin("*")
class TestController {

    @GetMapping(value = ["", "/public"])
    fun public(): ApiResponse = ApiResponse(content = "You have access to public content")

    @GetMapping("/admin")
    fun admin(): ApiResponse = ApiResponse(content = "You have access to admin content")

    @GetMapping("/user")
    fun user(): ApiResponse = ApiResponse(content = "You have access to user content")

    @GetMapping("/private")
    fun private(): ApiResponse = ApiResponse(content = "You have access to private content")

}