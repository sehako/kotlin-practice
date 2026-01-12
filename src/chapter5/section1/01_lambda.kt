package chapter5.section1

/*
함수형 프로그래밍은 다음 특징을 가지고 있다.
함수가 일급 시민: 함수를 변수에 저장하고 파라미터로 전달하여 함수에서 다른 함수를 반환할 수 있다.
불변성: 객체가 만들어진 다음에는 내부 상태가 변하지 않음을 보장하는 방법으로 설계할 수 있다.
부수 효과 없음: 함수가 똑같은 입력에 대해 항상 같은 출력을 내놓고 다른 객체나 외부 세계의 상태를 변경하지 않는다. (순수 함수)
 */

// 람다식을 사용하면 함수를 선언하지 않고 실질적인 코드 블록을 직접 함수로 전달할 수 있다.

// 버튼 클릭에 따른 이벤트를 처리하는 자바 OnClickListener 인터페이스 구현
class View

class LambdaButton {
    fun setOnClickListener(listener: OnClickListener) {
        listener.onClick(View())
    }
}

fun main() {
    val button = LambdaButton()

    // 코틀린에서는 이러한 콜백을 object로 구현할 수 있다.
    button.setOnClickListener(object : OnClickListener {
        override fun onClick(v: View) {
            println("Button clicked!")
        }
    })

    // 더 나아가 위와 같은 코트를 람다로 고쳐쓸 수 있다.
    // 자바 익명 내부 클래스와 같지만 간결하며 읽기 쉽다
    button.setOnClickListener {
        println("Button clicked!")
    }
}
