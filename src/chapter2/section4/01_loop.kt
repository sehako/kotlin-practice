package chapter2.section4

fun main() {
    // 코틀린에서의 while과 do-while은 자바와 유사
    var count: Int = 0
    while (true) {
        if (++count == 10) break
    }

    count = 0
    do {
        count++
    } while (count < 10)

    // 코틀린의 경우 레이블을 지정하여 반복하거나 빠져나갈 루프를 빠져나갈 수 있음
    count = 0
    outer@ while (true) {
        // continue@outer로 해당 재귀를 다시 순회
        print("$count ")
        while (true) {
            if (++count == 10) break@outer
            else continue@outer
        }
        // 이곳은 절대로 도달할 수 없음
        println("this line will never be printed")
    }
    println()

    // 코틀린에서는 고전적인 for 루프가 없음
    // 두 값으로 이뤄진 구간인 '범위'를 주로 사용
    // 이 경우에는 폐구간 (1 <= num <= 10)을 가짐
    val oneToTen = 1..10

    // 어떤 범위에 값을 일정한 순서로 이터레이션 하는 경우를 progression이라고 부름
    for (i in oneToTen) print("$i ")
    println()

    // 이를 활용한 피즈버즈 예시 코드
    for (i in 1..100) {
        print("${fizzBuzz(i)} ")
    }
    println()

    // 역순회(downTo)와 증가 값 커스텀 설정(step) 예시
    //
    for (i in 100 downTo 1 step 2) {
        print("${fizzBuzz(i)} ")
    }
    println()


    // 컬렉션에 대한 이터레이션
    // 코틀린에서의 컬렉션 반복은 in을 활용 (자바의 콜론(:)과 유사)
    val alphabets = listOf("a", "b", "c")
    for (alphabet in alphabets) print("$alphabet ")
    println()

    // 맵 순회 예시 (구조 분해 구문)
    // 2진 표현 출력 예시
    val binaryReps = mutableMapOf<Char, String>() // 코틀린 가변 맵은 원소 이터레이션 순서 보장
    for (char in 'A'..'F') {
        val binary = char.code.toString(radix = 2) // 아스키 코드를 2진 표현으로 변환
        // 맵 원소 추가 예시 (자바로 치면 put())
        binaryReps[char] = binary

        // 코틀린에서의 Map.get() 예시
        // binaryReps[char]
    }

    for ((letter, binary) in binaryReps) {
        println("$letter = $binary")
    }

    // 구조 분해 구문을 컬렉션에 사용할 경우
    // 현재 인덱스를 유지하면서 컬렉션을 이터레이션할 때도 쓸 수 있음
    val list = listOf("one", "two", "three")
    for ((index, value) in list.withIndex()) {
        println("Element at $index is $value")
    }
}

fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz"
    i % 3 == 0 -> "Fizz"
    i % 5 == 0 -> "Buzz"
    else -> i.toString()
}
