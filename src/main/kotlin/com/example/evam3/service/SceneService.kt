package com.example.evam3.service

import com.example.evam3.entity.Scene
import com.example.evam3.repository.FilmRepository
import com.example.evam3.repository.SceneRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
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
            filmRepository.findById(scene.filmId)
                ?: throw Exception("ID de la película no encontrado")
            return sceneRepository.save(scene)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    // clase service -Petición put
    fun update(scene: Scene): Scene {
        scene.id?.let { sceneRepository.findById(it) } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "ID no existe")
        return sceneRepository.save(scene)
    }

    // clase service - Delete by id
    fun delete(id: Long?): Boolean? {
        id?.let { sceneRepository.findById(it) } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "ID no existe")
        sceneRepository.deleteById(id!!)
        return true
    }
}
