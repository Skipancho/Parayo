package com.example.parayo.domain.auth

import com.example.parayo.common.ParayoException
import com.example.parayo.domain.user.User
import com.example.parayo.domain.user.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import sun.security.util.Password

/**
 * @Service 애노테이션은 이 클래스가 스프링이 관리하는 빈(Bean) 클래스임을
 * 나타내며 그 중에서도 비즈니스 로직을 처리하는 클래스라는 것을 표시한다.
 *
 * @Autowired 애노테이션은 빈(Bean) 클래스를 자동으로 주입받겠다는 의미
 * @Service와 같이 스프링의 빈으로 선언된 클래스의 생성자, setter, 프로퍼티 등에
 * @Autowired 애노테이션을 붙이면 스프링이 해당하는 빈을 알아서 주입해준다.
 */
@Service
class SignupService @Autowired constructor(
    private val userRepository: UserRepository
){

    fun signup(signupRequest: SignupRequest){
        validateRequest(signupRequest)
        checkDuplicates(signupRequest.email)
        registerUser(signupRequest)
    }

    private fun validateRequest(signupRequest: SignupRequest){
        validateEmail(signupRequest.email)
        validateName(signupRequest.name)
        validatePassword(signupRequest.password)
    }

    private fun validateEmail(email : String){
        val isNotValidEmail = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"
            .toRegex(RegexOption.IGNORE_CASE)
            .matches(email)
            .not()

        if(isNotValidEmail){
            throw ParayoException("이메일 형식이 올바르지 않습니다.")
        }
    }

    private fun validateName(name : String){
        if(name.trim().length !in 2..20)
            throw ParayoException("이름은 2자 이상 20자 이하여야 합니다.")
    }

    private fun validatePassword(password: String){
        if(password.trim().length !in 8..20)
            throw ParayoException("비밀번호는 공백 제외 8자 이상 20자 이하여야 합니다.")
    }

    private fun checkDuplicates(email: String)=
        userRepository.findByEmail(email)?.let {
            throw ParayoException("이미 사용 중인 이메일입니다.")
        }

    /**
     * BCrypt.hashpw()는 문자열을 해싱해주는 기능을 한다.
     * 원본 문자열을 찾아내기 힘들도록 임의의 salt(소금?) 값을 붙여 함께 해싱하는데
     * 이 salt 값은 BCrypt.gensalt()를 통해 생성할 수 있다.
     *
     * JpaRepository에는 데이터를 저장하는  save()함수가 있다.
     * 여기에 해당하는 객체를 전달하면 데이터베이스에 사용자 정보가 저장된다.
     */
    private fun registerUser(signupRequest: SignupRequest)=
        with(signupRequest){
            val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
            val user = User(email,hashedPassword,name , fcmToken)
            userRepository.save(user)
        }
}