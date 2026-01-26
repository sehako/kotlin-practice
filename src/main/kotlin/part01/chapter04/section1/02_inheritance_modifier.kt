package part01.chapter04.section1

// 코틀린에서 모든 클래스와 메서드는 기본적으로 final이다.
// 이는 자바 방식의 위험성인 취약한 기반 클래스(fragile base class) 문제를 방지하기 위함이다.
// 취약한 기반 클래스: 기반 클래스 구현을 변경함으로써 하위 클래스가 잘못된 동작을 하게 되는 경우

// 따라서 어떤 클래스의 상속 또는 오버라이드을 허용하려면 클래스 앞에 open 변경자를 붙여야 한다.
// open 클래스 예시
open class RichButton : Clickable {
    fun diable() {} // 해당 메서드는 final
    open fun animate() {} // 이 메서드는 하위 클래스에서 이 메서드를 오버라이드 할 수 있다.
    override fun click() {} // 인터페이스 구현이며, open 상태
}

// 이제 RichButton의 하위 클래스는 다음과 같은 형태를 가질 수 있다.
open class ThemedButton : RichButton() {
    override fun animate() {}
    final override fun click() {} // 이렇게 오버라이드 하는 메서드에 final을 명시하면 이 클래스의 하위 클래스는 오버라이드 불가능
    override fun showOff() {} // RichButton은 Clickable를 구현하므로 해당 인터페이스의 디폴트 메서드를 오버라이드 가능
}
// 정리하면, 기반 클래스나 인터페이스의 멤버를 오버라이드한 경우에는 기본적으로 open으로 간주

class FinalButton : ThemedButton() {
//    override fun click() {} // 컴파일 에러 발생함
}

/* 열린 클래스와 스마트 캐스트
코틀린처럼 final을 기본값으로 설정하면 스마트 캐스트가 가능하다.
클래스 프로퍼티의 경우 val 이면서 커스텀 접근자가 없는 경우 + 프로퍼티가 final 이어야만 함
*/

// 추상 클래스 (abstract)
// 추상 클래스에 정의한 추상 멤버는 항상 open이므로 별도 명시가 필요 없음(인터페이스와 동일)
abstract class Animated {
    abstract val animationSpeed: Double // 추상 멤버

    // 추상 클래스의 프로퍼티는 기본적으로 open이 아니지만 직접 지정할 수도 있다.
    val keyframes: Int = 20
    open val frames: Int = 60

    abstract fun animate() // 추상 메서드, 하위 클래스는 이를 반드시 오버라이드해야 함

    // 메서드도 마찬가지로 open이 아니지만 별도 지정이 가능함
    open fun stopAnimating() {}
    fun animateTwice() {}
}

/*
코틀린 변경자(Modifier) 요약

변경자      이 변경자가 붙은 멤버는...                    설명
-----------------------------------------------------------------------------------------------
final       오버라이드할 수 없음                         클래스 멤버의 기본 변경자.
open        오버라이드할 수 있음                         반드시 open을 명시해야 오버라이드할 수 있음.
abstract    반드시 오버라이드해야 함                      추상 클래스의 멤버에만 붙일 수 있으며, 추상 멤버에는 구현이 있으면 안됨.
override    상위 클래스나 인스턴스의 멤버를 오버라이드함       오버라이드하는 멤버는 기본적으로 열려 있으며,
                                                    오버라이드를 금지하려면 final을 명시.
 */