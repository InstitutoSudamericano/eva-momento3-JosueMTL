package com.example.evam3.service

import com.example.evam3.entity.Scene
import com.example.evam3.repository.FilmRepository
import com.example.evam3.repository.SceneRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.util.*

@Service
class SceneService {

    @Autowired
    lateinit var sceneRepository: SceneRepository

    @Autowired
    lateinit var filmRepository: FilmRepository

    fun list(): List<Scene> {
        return sceneRepository.findAll()
    }

    fun listOne(id: Long): Optional<Scene> {
        return sceneRepository.findById(id)
    }

    // PETICIONES POST
    fun save(scene: Scene): Scene {
        try {
            scene.description?.takeIf { it.trim().isNotEmpty() }
                ?: throw Exception("El nombre del actor no debe ser vacío")
            val film = filmRepository.findById(scene.filmId)
                ?: throw Exception("Id del film no encontrados")

            // Ensure cost_of_the_suit and makeup_cost do not exceed the budget
            if ((scene.minutes?.toLong() ?: 0) > (film.duration ?: 0)) {
                throw Exception("El total de minutos excede la duración de la película")
            }

            // Ensure the budget does not exceed the investment of the film
            if ((scene.budget ?: BigDecimal.ZERO).compareTo((film.investment ?: BigDecimal.ZERO) as BigDecimal?) == 1) {
                throw Exception("El presupuesto de la escena excede la inversión de la película")
            }


            val films = filmRepository.findById(scene.filmId)
            val currentTotalCost = (sceneRepository.sumMinutesByFilmId(scene.filmId!!) ?: 0).toBigDecimal()

            if (films != null) {
                if (currentTotalCost + (scene.minutes ?.toBigDecimal() ?: BigDecimal.ZERO) > ((film.duration ?: BigDecimal.ZERO) as BigDecimal?)) {
                    throw Exception("El total de minutos excede la duracion de la pelicula")
                }
            }


            return sceneRepository.save(scene)
        } catch (ex: Exception) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST, ex.message, ex
            )
        }
    }


    // Clase service - Petición put
    fun update(scene: Scene): Scene {
        scene.id?.let { sceneRepository.findById(it) } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "ID no existe")
        return sceneRepository.save(scene)
    }

    // Clase service - Delete by id
    fun delete(id: Long?): Boolean? {
        id?.let { sceneRepository.findById(it) } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "ID no existe")
        sceneRepository.deleteById(id!!)
        return true
    }
}
