package com.example.parayo.controller

import com.example.parayo.common.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

@RestController
class HelloController {
    @GetMapping("/api/v1/hello")
    fun hello() = ApiResponse.ok("world")
}