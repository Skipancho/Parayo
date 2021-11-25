package com.example.parayo.domain.product

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {

    //카테고리별, id 값이 큰 순으로 내림차순
    fun findByCategoryIdAndIdGreaterThanOrderByIdDesc(
        categoryId : Int?, id:Long, pageable: Pageable
    ): List<Product>

    //카테고리별, id 값이 작은 순으로 내림차순
    fun findByCategoryIdAndIdLessThanOrderByIdDesc(
        categoryId : Int?, id:Long, pageable: Pageable
    ): List<Product>
}