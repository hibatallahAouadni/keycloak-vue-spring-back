package fr.slickteam.back.heroes

import fr.slickteam.back.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/heroes")
@CrossOrigin("*")
class HeroesController {

    @Autowired
    lateinit var service: HeroesService

    @GetMapping("")
    fun getAll(): ResponseEntity<ApiResponse> = ResponseEntity.ok(this.service.getAll())
}