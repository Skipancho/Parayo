package com.example.parayo.interceptor

import com.example.parayo.domain.auth.JWTUtil
import com.example.parayo.domain.auth.UserContextHolder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Component 애노테이션은 이 클래스가 Spring이 관리하는 빈(Bean) 클래스임을 나타낸다.
 * @Service 애노테이션과 기술적으로는 동일하나 의미상으로 비즈니스 로직을 처리하는
 * 클래스가 아니라는 점이 다르다.
 */
@Component
class TokenValidationInterceptor @Autowired constructor(
    private val userContextHolder: UserContextHolder
) : HandlerInterceptor {
    /**
     * 서버사이드 로깅을 위해 로거 객체를 프로퍼티로 선언
     */
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any): Boolean {
        val authHeader = request.getHeader(AUTHORIZATION)

        if (authHeader.isNullOrBlank()){
            val pair = request.method to request.servletPath
            if(!DEFAULT_ALLOWED_API_URLS.contains(pair)){
                response.sendError(401)
                return false
            }
            return true
        }else{
            val grantType = request.getParameter(GRANT_TYPE)
            val token = extractToken(authHeader)
            return handleToken(grantType, token, response)
        }
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        userContextHolder.clear()
    }



    private fun handleToken(
        grantType : String?,
        token : String,
        response: HttpServletResponse
    ) = try {
        val jwt = when(grantType){
            GRANT_TYPE_REFRESH -> JWTUtil.verifyRefresh(token)
            else -> JWTUtil.verify(token)
        }
        val email = JWTUtil.extractEmail(jwt)
        userContextHolder.set(email)
        true
    }catch (e : Exception){
        logger.error("토큰 검증 실패. token = $token",e)
        response.sendError(401)
        false
    }

    private fun extractToken(token : String) =
        token.replace(BEARER, "").trim()


    /**
     * AUTHORIZATION : Authorization 토큰이 포함된 헤더 값을 가져오기 위한 상수
     * BEARER : Authorization 헤더에 JWT 토큰을 전달할 때 사용되는 인증 방법
     *          실제 토큰을 사용하려면 헤더 값에서 이 문자열을 제거한 후 사용
     * DEFAULT_ALLOWED_API_URLS : 토큰 인증 없이 사용할 수 있는 URL 을 정의
     */
    companion object{
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
        private const val GRANT_TYPE = "grant_type"
        const val GRANT_TYPE_REFRESH = "refresh_token"

        private val DEFAULT_ALLOWED_API_URLS = listOf(
            "POST" to "/api/v1/signin",
            "POST" to "/api/v1/users"
        )
    }
}