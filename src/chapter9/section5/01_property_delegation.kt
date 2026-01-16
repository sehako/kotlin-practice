package chapter9.section5

import kotlin.reflect.KProperty

/*
위임 프로퍼티의 일반적인 문법은 다음과 같다.
var p: Type by Delegate()
by 오른쪽의 식을 계산하여 위임에 쓰일 객체를 얻는다.

위임 프로퍼티를 정의한 클래스 내부에서는 다음과 같은 일이 일어난다.
class Example {
    var p: Type by Delegate()
}

여기서 컴파일러는 숨겨진 도우미 프로퍼티를 만들고 그 프로퍼티를 위임 객체의 인스턴스로 초기화한다.
p 프로퍼티는 바로 그 위임 객체에게 자신의 작업을 위임한다.
class Example {
    private val delegate = Delegate() 컴파일러가 생성한 도우미 프로퍼티
    var p: Type
        set(value: Type) = delegate.setValue(/*...*/, value)
        get() = delegate.getValue(/*...*/)
}

프로퍼티 위임 관례에 따라서 Delegate 클래스는 getValue와 setValue 메서드를 제공해야 한다.
그리고 변경 가능한 프로퍼티만 setValue를 사용한다. 추가로 위임 객체는 선택적으로 provideDelegate 함수 구현을 제공할 수도 있다.
이는 검증 로직 또는 위임의 인스턴스화 정책 변경에 사용될 수 있다.
 */

// 간단한 위임 프로퍼티 구현체를 만들어보자.
class Delegate(initialValue: Int = 0) {
    private var value = initialValue

    // thisRef: 위임 프로퍼티를 사용하는 객체 (Foo)
    // property: 프로퍼티에 대한 메타데이터 (KProperty)
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return value
    }

    // 1. thisRef: 위임 프로퍼티를 사용하는 객체
    // 2. property: 프로퍼티에 대한 메타데이터
    // 3. value: 새로 설정하려는 값
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        this.value = value
    }

    operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): Delegate {
        return this
    }
}

class Foo {
    var p: Int by Delegate(0)
}

// Foo.p는 일반 프로퍼티처럼 쓸 수 있고, 일반 프로퍼티처럼 보인다.
// 하지만 내부적으로 Delegate 타입의 위임 프로퍼티 객체에 있는 메서드를 호출한다.
