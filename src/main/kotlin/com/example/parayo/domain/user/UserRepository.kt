package com.example.parayo.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @Repository 애노테이션은 이 인터페이스가 Spring이 관리하는 레포지토리 빈(Bean)으로
 * 동작한다는 것을 나타냄, 레포지토리는 의미적으로 이 클래스가 데이터의 읽기/쓰기 등을
 * 담당한다는 것을 표시한다.
 *
 *  JpaRepository를 상속받으면 레포지토리를 JPA 스펙에 맞게 확장하면서 기본적인
 *  CRUD(Create, Read, Update, Delete) 함수를 제공할 수 있게 된다.
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {
    /**
     * Spring Data JPA에서는 레포지토리 인터페이스에
     * 규칙에 맞는 간단한 함수를 정의하는 것만으로 쿼리를 대신해주는 기능이 있다.
     * 일반적인 쿼리 함수 명명 규칙은 findBy + 필드명 (+ And + 필드명 ..)의 형태이다.
     * 각 필드명은 대문자로 시작해야하며 파라미터들은 필드와 동일한 타입으로 순서에 맞게 정의해주면 된다.
     * 함수의 반환 타입은 검색했을 때 반환될 것으로 예상되는 데이터에 따라
     * 단일 엔티티나 엔티티 리스트를 기입한다.
     */
    fun findByEmail(email: String):User?
}