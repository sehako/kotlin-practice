package chapter09.section1

// 자바에서는 기본 타입과 String 타입(+)에 대해서만 산술 연산자를 사용할 수 있다.
// 코틀린에서는 산술 연산을 오버로딩 하여 객체에서도 산술 연산자를 사용할 수 있다.
data class OperatorPoint(val x: Int, val y: Int) {
    // + 산술 연산자를 오버로딩
    // 산술 연산자를 오버로딩 할 때는 반드시 함수 앞에 operator 키워드를 명시해야 한다.
    operator fun plus(other: OperatorPoint): OperatorPoint {
        return OperatorPoint(x + other.x, y + other.y)
    } // 이 함수를 선언하면 + 기호로 두 Point 객체를 더할 때 내부적으로는 해당 함수가 두 객체의 좌표값을 합산한다.
}

// 멤버 함수 대신에 확장 함수로 정의할 수도 있다.
operator fun OperatorPoint.minus(other: OperatorPoint): OperatorPoint {
    return OperatorPoint(x - other.x, y - other.y)
}

// 프로그래머가 직접 연산자를 만들 수는 없고 다음 연산자만 오버로딩 할 수 있다.
// *(times), /(div), %(mod). +(plus), -(minus)
// 이들 연산자 간의 우선순위는 일반적인 숫자 연산과 같다.
// 참고로 코틀린에서 이렇게 정의한 산술 연산자를 자바에서 일반 함수로 호출할 수 있다.
// 한 가지 주의해야 할 점은 코틀린은 교환법칙을 지원하지 않는다.
operator fun OperatorPoint.times(scale: Int): OperatorPoint {
    return OperatorPoint(x * scale, y * scale)
}
// 위 함수는 항상 OperatorPoint * 2 이런 식으로 동작한다.
// 반대 순서의 연산을 정의하고 싶으면 추가적으로 오버로딩 해야한다.

// 또한 연산자 함수의 반환 타입이 주어진 두 피연산자 중 하나와 일치하지 않아도 된다.
// (Char 인스턴스 * 3) 형식으로 작성하면 String을 반환한다.
operator fun Char.times(count: Int): String {
    return toString().repeat(count)
}

// 오버로딩이므로 파라미터 타입만 다르면 산술 연산자 얼마든지 만들 수도 있다.
/*
코틀린은 표준 숫자 타입에 대해 비트 연산자를 정의하지 않는다. 따라서 커스텀 타입에서 비트 연산자를 정의할 수도 없다.
대신 중위 연산자 표기법을 지원하는 일반 함수를 사용해 비트 연산을 수행한다.
커스텀 타입에서도 그와 비슷한 함수 정의해 사용할 수 있다.
다음은 코틀린에서 비트 연산을 수행하는 함수의 목록이다.
shl: <<
shr: >>
ushr: >>> (부호 비트 0으로 설정)
and: &
or: |
xor: ^
inv: ~
 */


