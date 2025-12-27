package chapter2

fun main() {
    println(eval(Sum(Sum(Number(1), Number(2)), Number(4))))
    println(evalWhenWithBlock(Sum(Sum(Number(1), Number(2)), Number(4))))
}

// 마커 인터페이스
interface Expression

// 클래스가 구현하는 인터페이스 (implements)를 콜론(:)으로 선언
class Number(val value: Int) : Expression
class Sum(val left: Expression, val right: Expression) : Expression
// 간단한 덧셈 구현
// Number: 그에 해당하는 값 반환
// Sum: 좌항 값을 재귀적으로 계산하고 우항 값을 재귀적으로 계산한 다음에 두 값을 합한 값을 반환

// if 와 is를 사용한 식 계산
fun eval(e: Expression): Int {
    // 스마트 캐스트
    if (e is Number) {
        // as로 명시적 형변환이 가능
        // val n = e as Number <- as는 안전하지 않은 캐스팅, 심지어 이미 is로 검사해서 필요 없음
        // 하지만 is로 이미 검사한 이후에는 e를 해당 타입으로 사용 가능
        return e.value // 변환 이후 변경될 수 없는 변수만 접근 가능 (val 또는 커스텀 getter가 없는 프로퍼티)
    }

    if (e is Sum) {
        return eval(e.left) + eval(e.right)
    }

    throw IllegalArgumentException("Unknown expression")
}

// if 식을 함수 본문으로 만들 수 있음
fun evalWithIfExpression(e: Expression): Int =
    if (e is Number) e.value
    else if (e is Sum) eval(e.left) + eval(e.right)
    else throw IllegalArgumentException("Unknown expression")

// 위 if 식 활용 예시를 when을 사용한 리팩터링 예시
// 대상 값의 타입을 검사하는 when 분기
fun evalWhen(e: Expression) = when (e) {
    is Number -> e.value
    is Sum -> eval(e.left) + eval(e.right)
    else -> throw IllegalArgumentException("Unknown expression")
}

// when 역시 if 처럼 분기에 블록을 선언 가능
fun evalWhenWithBlock(e: Expression): Int = when (e) {
    is Number -> {
        println("${e.value} is a number")
        e.value
    }

    is Sum -> {
        val left = evalWhenWithBlock(e.left)
        val right = evalWhenWithBlock(e.right)

        println("Calculating $left + $right...")
        left + right
    }

    else -> throw IllegalArgumentException("Unknown expression")
}
