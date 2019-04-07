package fr.slickteam.back.heroes

import fr.slickteam.back.ApiResponse
import org.springframework.stereotype.Service

@Service
class HeroesService {

    fun getAll(): ApiResponse {
        val list = listOf(
                HeroesEntity("Iron Man", "Tony Stark", "https://img.icons8.com/dusk/64/000000/iron-man.png", 48),
                HeroesEntity("Spider-Man", "Peter Parker", "https://img.icons8.com/color/48/000000/spiderman-head.png", 16),
                HeroesEntity("Captain America", "Steve Rogers", "https://img.icons8.com/color/48/000000/captain-america.png", 100),
                HeroesEntity("Thor", "Thor", "https://img.icons8.com/color/48/000000/thor.png", 1500),
                HeroesEntity("Hulk", "Bruce Banner", "https://img.icons8.com/color/48/000000/hulk.png", 49)
        )
        return ApiResponse(content = list)
    }
}