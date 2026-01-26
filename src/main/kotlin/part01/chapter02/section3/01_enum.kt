package part01.chapter02.section3

// 코틀린에서의 Enum 정의
// 자바에서는 enum Color {} 이런 식
enum class Color {
    RED, GREEN, BLUE
}

// Enum의 프로퍼티 정의 예시
enum class ColorWithProperty(
    val r: Int,
    val g: Int,
    val b: Int
) {
    // 상수 생성 시 그에 대한 프로퍼티 값 지정
    RED(255, 0, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255); // 반드시 마지막 부분에 세미콜론 명시 필요

    // 이널 클래스에서 프로퍼티 정의
    fun rgb() = (r * 256 + g) * 256 + b

    fun printColor() = println("$this is ${rgb()}")
}

fun main() {
    // Enum 사용 예시
    println(ColorWithProperty.BLUE)
    ColorWithProperty.BLUE.printColor()
}