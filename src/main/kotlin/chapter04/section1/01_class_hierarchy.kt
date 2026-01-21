package chapter04.section1

// 자바와 코틀린읜 클래스와 인터페이스는 비슷하지만 다른 부분이 존재
// 예를 들어 인터페이스에 프로퍼티 선언이 들어갈 수 있으며 코틀린의 선언은 기본적으로 final이며 public
// 내포 클래스는 기본적으로는 내부 클래스가 아님 (코틀린 내포 클래스에는 외부 클래스엗 대함 암시적 참조가 없음)

fun main() {
    Button().click()
    Button().showOff()

    Display().click()
    Display().showOff()

    JavaButton().click()
    JavaButton().showOff()
}

// 코틀린 인터페이스에는 추상 메서드 뿐 아니라 구현이 있는 메서드도 정의할 수 있다. (자바 default 메서드)
interface Clickable {
    // 추상 메서드
    fun click()

    // 디폴트 메서드
    // 오버라이드가 선택적
    fun showOff() = println("I'm Clickable")
}

// 코틀린은 상속이나 구현 모두 클래스 이름 뒤에 콜론(:)을 붙이고 인터페이스나 클래스 이름을 적는다
// 자바와 마찬가지로 인터페이스의 다중 구현은 허용하지만 클래스의 상속은 하나만 가능하다.
class Button : Clickable {
    // Clickable 인터페이스 구현 예시
    // 자바와 다르게 override 변경자를 꼭 사용해야 한다. (의도치 않은 오버라이딩, 오버라이드 실수 방지)
    override fun click() = println("Button Clicked")
}

// 하나의 클래스에서 같은 디폴트 메서드 시그니처를 가진 인터페이스를 구현한다면 어떨까?
interface Focusable {
    fun showOff() = println("I'm Focusable")
}

// 이 경우에는 컴파일 오류가 발생한다.
// 코틀린 컴파일러는 디폴트 메서드의 시그니처가 같은 경우에는 하위 클래스에 직접 구현하도록 강제한다.
class Display : Clickable, Focusable {
    override fun click() = println("Display Clicked")

    // 명시적으로 새로운 구현을 제공해야 한다.
    override fun showOff() {
        // 상위 타입의 이름을 제네릭으로 넣으면 어떤 상위 타입을 호출할 지 지정할 수 있다.
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}