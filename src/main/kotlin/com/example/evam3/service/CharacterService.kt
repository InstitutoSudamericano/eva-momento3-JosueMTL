package com.example.evam3.service

import com.example.evam3.entity.Character
import com.example.evam3.repository.CharacterRepository
import com.example.evam3.repository.SceneRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.util.*

@Service
class CharacterService {

    @Autowired
    lateinit var sceneRepository: SceneRepository

    @Autowired
    lateinit var characterRepository: CharacterRepository

    // Listar todos los personajes
    fun list(): List<Character> {
        return characterRepository.findAll()
    }

    // Obtener un personaje por ID
    fun listOne(id: Long): Optional<Character> {
        return characterRepository.findById(id)
    }

    // Guardar un nuevo personaje
    fun save(character: Character): Character {
        try{
            sceneRepository.findById(character.sceneId)
                ?: throw Exception("Id de la escena no encontrado")

            character.description?.takeIf { it.trim().isNotEmpty() }
                ?: throw Exception("Descripcion no debe ser vacio")

            // Calcular el costo total actual de los personajes en la escena
            val scene = sceneRepository.findById(character.sceneId)
            val currentTotalCost = characterRepository.sumCostBySceneId(character.sceneId!!) ?: BigDecimal.ZERO
            // Verificar si el nuevo personaje excede el presupuesto de la escena
            if (scene != null) {
                if (currentTotalCost.add(character.cost ?: BigDecimal.ZERO) > (scene.budget ?: BigDecimal.ZERO)) {
                    throw Exception("El costo total de los personajes excede el presupuesto de la escena")
                }
            }
            return characterRepository.save(character)
        }
        catch (ex: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,ex.message)
        }
    }

    // Actualizar un personaje
    fun update(character: Character): Character {
        try {
            characterRepository.findById(character.id)
                ?: throw Exception("ID no existe")

            return characterRepository.save(character)
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }

    // Eliminar un personaje por ID
    fun delete (id: Long?):Boolean?{
        try{
            val response = characterRepository.findById(id)
                ?: throw Exception("ID no existe")
            characterRepository.deleteById(id!!)
            return true
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }
}

// Excepción personalizada para cuando la escena no se encuentra
//class EscenaNotFoundException(message: String) : RuntimeException(message)

// Excepción personalizada para cuando la descripción es vacía
//class DescripcionVaciaException(message: String) : RuntimeException(message)

// Excepción personalizada para cuando el presupuesto es excedido
//class PresupuestoExcedidoException(message: String) : RuntimeException(message)

// Excepción personalizada para cuando el personaje no se encuentra
//class PersonajeNotFoundException(message: String) : RuntimeException(message)