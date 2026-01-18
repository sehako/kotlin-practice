package chapter07

// 객체만 생성해두고 나중에 전용 메서드를 통해 초기화하는 프레임워크가 존재한다. (안드로이드, JUnit 등)
// 하지만 코틀린에서 클래스 내 null이 아닌 프로퍼티를 생성자 안에서 초기화하지 않고 특별한 메서드 안에서 초기화할 수는 없다.
// 코틀린에서는 일반적으로 생성자에서 모든 프로퍼티를 초기화해야 한다.
// 게다가 프로퍼티 타입이 null이 될 수 없는 타입이라면 반드시 null이 아닌 값으로 그 프로퍼티를 초기화해야 한다.
// 또한 null이 될 수 있는 타입을 사용하면 모든 프로퍼티 접근에 null 검사를 넣거나 !!를 명시해야 한다.
// 코틀린에서는 이를 해결하기 위해서 프로퍼티를 지연 초기화할 수 있는 lateinit 변경자를 지원한다.

class MyService {
    fun performAction(): String = "actions done"
}

// JUnit 테스트 코드를 예시로 보도록 하자.
class TestCodeExample {
    // 지연 프로퍼티는 항상 var이어야 한다.
    private lateinit var service: MyService

    // @BeforeAll
    fun setUp() {
        service = MyService() // 프로퍼티 초기화
    }

    // @test
    fun testAction() {
        service.performAction() // null 검사를 수행하지 않고 프로퍼티를 사용한다.
    }
}

// 참고로 이렇게 지연 프로퍼티를 명시하면 해당 프로퍼티를 초기화하기 전에 프로퍼티에 접근했을 때
// UninitializedPropertyAccessException이 발생한다.

// lateinit 프로퍼티를 구글 주스 등의 의존관계 주입(DI) 프레임워크와 함께 사용하는 경우가 많다.
// 그런 시나리오에서는 lateinit 프로퍼티의 값을 DI 프레임워크가 외부에서 설정해준다.
// 자바 프레임워크와의 호환성 목적으로 코트린은 lateinit이 지정된 프로퍼티와 가시성이 같은 필드를 생성한다.
// 마지막으로 lateinit 프로퍼티가 반드시 클래스의 멤버일 필요는 없다. 함수 본문 안의 지역 변수나 최상위 프로퍼티도 지연 초기화할 수 있다.

