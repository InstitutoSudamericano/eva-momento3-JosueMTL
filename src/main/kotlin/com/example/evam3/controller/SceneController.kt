package com.example.evam3.controller

import com.example.evam3.entity.Scene
import com.example.evam3.service.SceneService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
@RestController
@RequestMapping("/scenes")
class SceneController {

    @Autowired
    lateinit var sceneService: SceneService

    // Petición GET para obtener la lista de escenas
    @GetMapping
    fun list(): List<Scene> {
        return sceneService.list()
    }

    // Petición GET para obtener una escena por ID
    @GetMapping("/{id}")
    fun listOne(@PathVariable id: Long): Optional<Scene> {
        return sceneService.listOne(id)
    }

    // Petición POST para agregar una nueva escena
    @PostMapping
    fun save(@RequestBody scene: Scene): ResponseEntity<Scene> {
        return ResponseEntity(sceneService.save(scene), HttpStatus.OK)
    }

    // Petición PUT para actualizar una escena por ID
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody scene: Scene): ResponseEntity<Scene> {
        // Implementa la lógica de actualización según el ID proporcionado
        return ResponseEntity(sceneService.update(scene), HttpStatus.OK)
    }

    // Petición DELETE para eliminar una escena por ID
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): Boolean? {
        return sceneService.delete(id)
    }
}
