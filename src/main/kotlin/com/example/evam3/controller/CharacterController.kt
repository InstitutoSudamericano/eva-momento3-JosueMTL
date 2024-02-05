package com.example.evam3.controller

import com.example.evam3.entity.Character
import com.example.evam3.service.CharacterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
@RestController
@RequestMapping("/characters") // Ruta base para este controlador
class CharacterController {

    @Autowired
    lateinit var characterService: CharacterService

    // Petición GET para obtener la lista de personajes
    @GetMapping
    fun list(): List<Character> {
        return characterService.list()
    }

    // Petición GET para obtener un personaje por ID
    @GetMapping("/{id}")
    fun listOne(@PathVariable id: Long): Optional<Character> {
        return characterService.listOne(id)
    }

    // Petición POST para agregar un nuevo personaje
    @PostMapping
    fun save(@RequestBody character: Character): ResponseEntity<Character> {
        return ResponseEntity(characterService.save(character), HttpStatus.OK)
    }

    // Petición PUT para actualizar un personaje por ID
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody character: Character): ResponseEntity<Character> {
        // Implementa la lógica de actualización según el ID proporcionado
        return ResponseEntity(characterService.update(character), HttpStatus.OK)
    }

    // Petición DELETE para eliminar un personaje por ID
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): Boolean? {
        return characterService.delete(id)
    }
}
