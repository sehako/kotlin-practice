package chapter04.section1

import java.io.Serializable

// 코틀린에서도 클래스 내에 중첩 클래스를 선언할 수 있다.
// 하지만 자바와 다르게 중첩 클래스는 명시적으로 요청하지 않으면 바깥 클래스 인스턴스에 대한 접근 권한이 없다.

// View의 상태를 직렬화 하는 예시
interface State : Serializable

interface View {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
}

// ViewButton 코드 참고

// 반면 코틀린에서는 중첩 클래스가 기본적으로 동작하는 방식은 좀 다르다.
class ButtonWithView : View {
    override fun getCurrentState(): State = ButtonState()
    override fun restoreState(state: State) {}

    // 코틀린에서 중첩 클래스에 아무런 변경자도 없으면 자바의 정적 중첩 클래스와 대응된다.
    class ButtonState : State {}

    // 이를 비정적 중첩 클래스로 변경해서 바깥쪽 클래스에 대한 참조를 포함하려면 inner를 선언하면 된다.
    inner class InnerButtonState : State {
        fun test(): ButtonWithView {
            getCurrentState()
            return this@ButtonWithView
        }
    }
}

// 비정적 중첩 클래스로 변경해서 바깥쪽 클래스에 대한 참조를 포함하려면 inner를 선언하면 된다.
class Outer {
    inner class Inner {
        // 그리고 코틀린은 바깥쪽 클래스 참조에 접근하려면 다음과 같이 명시해야 한다.
        fun test(): Outer = this@Outer
    }
}

// 코틀린 중첩 클래스를 유용하게 사용하는 방법 - 봉인된 클래스
interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

// 이렇게 when 식을 활용할 때 디폴트 분기인 else를 강제한다. (현재는 예외를 던짐)
// 이 경우 새로운 하위 클래스를 추가해도 컴파일러가 when이 모든 경우를 처리하는지 제대로 검사할 수 없다.
fun eval(e: Expr): Int = when (e) {
    is Num -> e.value
    is Sum -> eval(e.left) + eval(e.right)
    else -> throw IllegalArgumentException("Unknown expression")
}

// 코틀린은 이런 문제에 대해서 봉인된 클래스를 통해 해결한다.
// 상위 클래스에 sealed 변경자를 붙이면 그 상위 클래스를 상속한 하위 클래스의 가능성을 제한할 수 있다.
// sealed 클래스의 직접적인 하위 클래스들은 반드시 컴파일 시점에 알려져야 하며,
// 봉인된 클래스가 정의된 패키지와 같은 패키지에 속해야 하며, 모든 하위 클래스가 같은 모듈 안에 위치해야 한다.

// 따라서 인터페이스를 쓰는 대신 Expr을 sealed 클래스로 만들고 Sum을 Expr의 하위 클래스로 할 수 있다.
sealed class SealedExpr
class NumSealed(val value: Int) : SealedExpr()
class SumSealed(val left: SealedExpr, val right: SealedExpr) : SealedExpr()

// when 식에서 봉인 클래스의 모든 하위 클래스를 처리하면 else 분기가 필요 없다.
// 그리고 컴파일러가 모든 분기를 처리하는지 확인해준다.
// 또한 sealed는 클래스가 추상 클래스임을 명시한다.
// 따라서 abstract를 명시할 필요가 없으며 추상 멤버를 선언할 수 있다.
fun eval(e: SealedExpr): Int = when (e) {
    is NumSealed -> e.value
    is SumSealed -> eval(e.left) + eval(e.right)
}

// 이렇게 추가적인 하위 클래스를 정의하면 when 식에 컴파일 오류가 발생한다.
// class MulSealed(val left: SealedExpr, val right: SealedExpr) : SealedExpr()

// 클래스 뿐 아니라 인터페이스에 sealed를 명시할 수 있다.
// 마찬가지로 when 식에 모든 구현을 처리하면 else 분기를 작성하지 않아도 된다.
sealed interface Toggleable {
    // 프로퍼티와 함수 정의가 포함될 수 있다.
    fun toggle()
}

class LightSwitch : Toggleable {
    override fun toggle() = println("Light switched")
}

class Camera : Toggleable {
    override fun toggle() = println("Camera shot")
}
