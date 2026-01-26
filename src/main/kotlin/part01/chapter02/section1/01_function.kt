package part01.chapter02.section1

// 함수 선언 식
// 코틀린은 함수를 모든 파일의 최상위 수준에 정의 가능
fun main() { // 메인 함수에 인자가 없어도 됨 (String[] args)
    // 기존 자바 라이브러리를 간결하게 사용할 수 있도록 래핑
    println("Hello, Kotlin!")
}

// 반환값 정의 예시 (본문 함수)
// 코를린에서는 변수명 : 타입 형식
fun max(a: Int, b: Int): Int {
    // 코틀린에서 if는 결과를 만드는 식(expression)
    // 자바로 비유하면 아래 코드는 다음과 같은 삼항 연산자와 유사
    // return a > b ? a : b
    return if (a > b) a else b
}

/*
statement vs expression
statement: 자신을 둘러싸고 있는 가장 안쪽 블록의 최사위 요소로 전재하며 아무런 값을 만들어내지 않음
expression: 값을 만들어내며 다른 식의 하위 요소로 계산에 활용 가능
 */

// 식 본문을 활용한 간결성 예시 (식 본문 함수)
fun simpleMax(a: Int, b: Int): Int = if (a > b) a else b

// 반환 타입 생략 함수
// 식 본문 함수의 경우 반환 타입을 적지 않아도 컴파일러가 타입 추론 수행
// 식 본문 함수에서만 가능하다는 것에 유의
// 참고로 라이브러리 개발에는 반환 타입을 모두 명시하는 것을 권장
fun omitReturnMax(a: Int, b: Int) = if (a > b) a else b
