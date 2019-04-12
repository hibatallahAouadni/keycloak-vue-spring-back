package fr.slickteam.back

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@CrossOrigin("*")
class TestController {

    @GetMapping(value = ["", "/unsecured"])
    fun unsecured(): ApiResponse = ApiResponse(content = "Not logged")

    @GetMapping("/admin")
    fun admin(): ApiResponse = ApiResponse(content = "admin")

    @GetMapping("/user")
    fun user(): ApiResponse = ApiResponse(content = "user")

    @GetMapping("/test")
    fun test(): ApiResponse = ApiResponse(content = "test")

}