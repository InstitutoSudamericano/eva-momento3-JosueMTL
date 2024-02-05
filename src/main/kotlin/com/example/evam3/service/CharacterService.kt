package com.example.evam3.service

import com.example.evam3.entity.Character  // Asegúrate de importar la clase Character correcta
import com.example.evam3.repository.CharacterRepository
import com.example.evam3.repository.SceneRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class CharacterService {

    @Autowired
    lateinit var characterRepository: CharacterRepository

    @Autowired
    lateinit var sceneRepository: SceneRepository

    fun list(): List<Character> {
        return characterRepository.findAll()
    }

    fun listOne(id: Long): Optional<Character> {
        return characterRepository.findById(id)
    }

    // PETICIONES POST
    fun save(character: Character): Character {
        try {
            val sceneId = character.sceneId ?: throw Exception("Id de la escena no encontrado")

            sceneRepository.findById(sceneId)
                ?: throw Exception("Id de la escena no encontrado")

            return characterRepository.save(character)
        } catch (ex: Exception) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND, ex.message)
        }
    }


    // clase service -Petición put
    fun update(character: Character): Character {
        try {
            characterRepository.findById(character.id)
                ?: throw Exception("ID no existe")

            return characterRepository.save(character)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    // clase service - Delete by id
    fun delete(id: Long?): Boolean {
        try {
            val response = characterRepository.findById(id)
                ?: throw Exception("ID no existe")
            characterRepository.deleteById(id!!)
            return true
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }
}
