package com.example.parayo.domain.jpa

import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity {
    /**
     * @Id 애노테이션은 해당 필드가 이 테이블의 PK(Primary Key)라는 것을 명시해준다.
     * @GeneratedValue(strategy = GenerationType.IDENTITY) 애노테이션은 MySQL에
     * PK 생성을 위임해 테이블에 새 데이터가 저장될 때 해당 필드가 자동으로 1씩 증가하여 유일한 값을
     * 넣어주도록 한다.(AUTO_INCREMENT)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null

    var createAt: Date? = null
    var updateAt: Date? = null

    /**
     * @PrePersist가 붙은 함수는 데이터베이스에 새 데이터가 저장되기 전에 자동으로 호출
     * 데이터 저장 전에 데이터 생성일을 현재 날짜로 지정
     */
    @PrePersist
    fun prePersist(){
        createAt = Date()
        updateAt = Date()
    }

    /**
     * @PreUpdate 애노테이션은 @PrePersist와 마찬가지로 JPA의 라이프사이클 훅을 지정한다.
     * 해당 애노테이션이 붙은 함수는 데이터베이스에 데이터 업데이트 명령을 날리기 전에 실행된다.
     */
    @PreUpdate
    fun preUpdate(){
        updateAt = Date()
    }
}