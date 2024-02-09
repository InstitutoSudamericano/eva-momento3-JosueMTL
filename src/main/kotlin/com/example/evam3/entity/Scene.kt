package com.example.evam3.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name="scene")
class Scene {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false)
    var id: Long? = null
    var title: String?=null
    var description: String? = null
    var budget: BigDecimal? = null
    var minutes: Int? = null
    @Column(name="cost_of_the_suit")
    var costOfTheSuit: BigDecimal? = null
    @Column(name="makeup_cost")
    var makeupCost: BigDecimal? = null
    @Column(name = "film_id")
    var filmId: Long? = null
}