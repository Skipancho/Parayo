package com.example.parayo.domain.user

import com.example.parayo.domain.jpa.BaseEntity
import java.util.*
import javax.persistence.*

/**
 * @Entity 애노테이션은 이 클래스가 테이블에 맵핑된 정보를 가지고 있음을 의미함
 * User 클래스의 객체가 가진 데이터는 데이터베이스에 저장된 혹은 저장될 사용자 한 명의 정보를 대변한다.
 */
@Entity(name = "user")
class User(
    var email : String,
    var password : String,
    var name : String,
    var fcmToken : String?
): BaseEntity() {
}


