package com.example.parayo.controller

import com.example.parayo.common.ApiResponse
import com.example.parayo.domain.auth.SignupRequest
import com.example.parayo.domain.auth.SignupService
import com.example.parayo.domain.auth.UserContextHolder
import com.example.parayo.domain.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @RestController 애노테이션이 달린 컨트롤러는 Spring에서
 * HTTP 호출의 응답으로 뷰를 렌더링하지 않고 HTTP의 본문에 직접 텍스트로
 * 이루어진 데이터를 쓴다는 것을 나타낸다.
 *
 * @GetMapping 애노테이션이 붙은 함수는 HTTP의 GET 메서드를 통해
 * 해당 함수를 실행하면 이 반환값을 응답으로 되돌려준다는 의미이다.
 *
 * @PostMapping 애노테이션은 HTTP의 POST 메서드를 이용해 매핑된 주소를
 * 호출했을 때 함수가 동작함을 나타낸다.
 *
 * @RequestBody 애노테이션은 데이터를 HTTP의 바디에서 읽는다는 것을 의미한다.
 */
@RestController
@RequestMapping("/api/v1")
class UserApiController @Autowired constructor(
    private val signupService: SignupService,
    private val userService: UserService,
    private val userContextHolder: UserContextHolder
){

    @PostMapping("/users")
    fun signup(@RequestBody signupRequest: SignupRequest)=
        ApiResponse.ok(signupService.signup(signupRequest))

    @PutMapping("/users/fcm-token")
    fun updateFcmToken(@RequestBody fcmToken : String) =
        userContextHolder.id?.let { userId ->
        ApiResponse.ok(userService.updateFcmToken(userId,fcmToken))
        } ?: ApiResponse.error("토큰 갱신 실패")
}