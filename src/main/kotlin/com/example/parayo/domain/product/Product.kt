package com.example.parayo.domain.product

import com.example.parayo.domain.jpa.BaseEntity
import java.util.*
import javax.persistence.*

/**
 * @Column 애노테이션은 해당 프로퍼티가 데이터베이스의 컬럼에 매핑될 때의 속성들을 지정
 * 컬럼의 이름, 최대 길이, 유일 키 여부 등 여러 옵션을 지정할 수 있다.
 *
 * @Enumerated 애노테이션은 enum 클래스가 데이터베이스에 어떤 형식으로 저장되어야 할지를
 * 지정한다. 디폴트는 ORDINAL 로 정수 형태로 저장된다.
 * EnumType.STRING 시 코드 상의 문자 그대로 저장됨
 *
 * @OneToMany 애노테이션은 1:M 관계의 매핑을 의미한다.
 * 따라서 해당 프로퍼티는 MutableList 타입이어야함
 *
 * @JoinColumn 애노테이션은 해당 엔티티의 PK와 매핑되는 타겟 엔티티의 어떤 컬럼을 통해
 * 두 테이블을 조인할 것인가를 지정한다. 이 애노테이션을 통해 FK(외래키)를 추가해 조인 쿼리를 사용
 */
@Entity(name = "product")
class Product(
    @Column(length = 40)
    var name : String,
    @Column(length = 500)
    var description : String,
    var price : Int,
    var categoryId : Int,
    @Enumerated(EnumType.STRING)
    var status: ProductStatus,
    @OneToMany
    @JoinColumn(name = "productId")
    var images : MutableList<ProductImage>,
    var userId : Long
): BaseEntity() {
}