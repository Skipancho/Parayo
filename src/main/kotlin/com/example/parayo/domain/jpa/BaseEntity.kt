package com.example.parayo.domain.jpa

import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null

    var createAt: Date? = null
    var updateAt: Date? = null

    @PrePersist
    fun prePersist(){
        createAt = Date()
        updateAt = Date()
    }

    @PreUpdate
    fun preUpdate(){
        updateAt = Date()
    }
}