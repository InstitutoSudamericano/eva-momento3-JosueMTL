package com.example.evam3.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name="character")
class Character {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false)
    var id: Long? = null
    var description: String? = null
    var cost: BigDecimal? = null
    var stock: Int? = null
    @Column(name = "scene_id")
    var sceneId: Long? = null
}