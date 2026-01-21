package chapter02.section2

// 커스텀 접근자: 일반적으로 어떤 프로퍼티가 같은 객체 안의 다른 프로퍼티에서 계산된 직접적인 결과
class Rectangle(
    val height: Int,
    val width: Int
) {
    val isSquare: Boolean
        // 프로퍼티의 게터 선언
        get() = height == width

    // 다음과 같이 한 줄로도 작성 가능
    // val isSquare: Boolean = height == width
}