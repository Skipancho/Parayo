package com.example.parayo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class PageController {
    @GetMapping("/page/v1/home")
    fun home(): String {
        return "index.html"
    }
}