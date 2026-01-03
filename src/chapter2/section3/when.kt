package chapter2.section3

// import로 Enum 상수 값을 불러올 수 있음
// 이를 통해 Color.RED -> RED 가능 (자바의 static import와 유사)
import chapter2.section3.Color.*

// when도 값을 만들어 내는 식(expression)

fun main() {
    // Color Enum으로 전달된 값으로 어떤 문자열을 얻는 함수
    println(getMnemonic(BLUE))

    println(testSituation(RED))

    println(colorToRgbValue())

    println(mixColor(RED, BLUE))
    println(mixOptimized(RED, BLUE))
}

// 함수의 반환값으로 when 식을 직접 사용
// 자바와 다르게 break를 명시하지 않아도 됨
// when은 모든 가능한 경우의 수를 작성해야 컴파일 에러가 나지 않음
fun getMnemonic(color: Color) =
    when (color) {
        RED -> "RED"
        GREEN -> "GREEN"
        BLUE -> "BLUE"
    }

// 쉼표(,)를 활용하면 다양한 값을 하나의 분기에 처리 가능
// 또한 else로 이외의 분기는 한 번에 처리 가능
fun testSituation(color: Color) =
    when (color) {
        GREEN, BLUE -> "Test is ongoing!"
        else -> "Test is dangerous!"
    }

// when 식의 대상 값을 변수로 대입하여 활용 가능
fun colorToRgbValue() =
    when (val color = measureColor()) {
        ColorWithProperty.RED -> "The color is ${color.rgb()}"
        else -> "Unknown color"
    }

fun measureColor() = ColorWithProperty.RED

// when의 분기 조건에 임의의 객체를 사용 가능
fun mixColor(c1: Color, c2: Color): String =
// when 식의 인자로 아무 객체나 사용 가능
    // 이런 경우 인자로 받은 객체가 각 분기 조건에 있는 객체와 같은지 차례대로 테스트
    when (setOf(c1, c2)) {
        setOf(RED, BLUE) -> "ORANGE"
        setOf(GREEN, BLUE) -> "CYAN"
        setOf(RED, GREEN) -> "YELLOW"
        // 일치하는 분기가 없으면 실행되는 부분
        else -> throw Exception("Unknown color combination")
    }

// 인자 없는 when 예시
// 앞선 Set 객체로 매번 객체를 만들어 비교하는 대신 Boolean을 활용
fun mixOptimized(c1: Color, c2: Color) = when {
    (c1 == RED && c2 == BLUE) || (c1 == BLUE && c2 == RED) -> "ORANGE"
    (c1 == GREEN && c2 == BLUE) || (c1 == BLUE && c2 == GREEN) -> "CYAN"
    (c1 == RED && c2 == GREEN) || (c1 == GREEN && c2 == RED) -> "YELLOW"
    else -> throw Exception("Unknown color combination")
}
