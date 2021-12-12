package com.example.parayo.config

import com.example.parayo.interceptor.TokenValidationInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @Configuration 애노테이션은 이 클래스가 Spring에서 사용하는 설정을 담은
 * 빈(Bean) 클래스임을 나타낸다. 이 애노테이션을 사용하면 SpringBootApplication 이하의
 * 패키지에서 모든 설정 클래스들을 검사해 자동으로 빈을 생성해준다.
 */
@Configuration
class WebConfig @Autowired constructor(
    private val tokenValidationInterceptor: TokenValidationInterceptor
) : WebMvcConfigurer{

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(tokenValidationInterceptor)
            .addPathPatterns("/api/**")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/images/**")
            .addResourceLocations("file:///parayo/images/")
    }
}