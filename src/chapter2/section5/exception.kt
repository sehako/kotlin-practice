package chapter2.section5

import java.io.BufferedReader
import java.io.StringReader

// 코틀린의 예외 처리는 자바나 다른 언어의 예외 처리와 비슷하게 throw 사용

fun main() {
    val percentage = 1.0
    if (percentage !in 0.0..100.0) {
        throw IllegalArgumentException("Percentage must be in range 1.0 ~ 100.0!")
    }

    // 자바와 다르게 코틀린의 throw는 식이므로 다른 식에 포함 가능
    val percentageResult =
        if (percentage in 0.0..100.0) {
            percentage
        } else {
            throw IllegalArgumentException("Percentage must be in range 1.0 ~ 100.0!")
        }

    // try, catch, finally 활용
    val input = BufferedReader(StringReader("239"))
    println(readNumber(input))
    val input2 = BufferedReader(StringReader("abc"))
    readNumberWithTryExpression(input2)
}

// 자바와는 다르게 함수가 던지는 체크 예외를 명시하지 않음
// ex) readNumber() throws IOException {}
// 코틀린은 자바와 다르게 체크와 언체크예외를 구별하지 않음
// 이를 통해 유연하게 잡고자 하는 예외만을 결정할 수 있음
fun readNumber(reader: BufferedReader): Int? { // Int?는 null 반환 때문에 명시
    try {
        val line = reader.readLine()
        return line.toInt()
    } catch (e: NumberFormatException) {
        return null
    } finally {
        reader.close()
    }
}

// 코틀린에서는 try도 식으로 활용 가능
fun readNumberWithTryExpression(reader: BufferedReader) { // Int?는 null 반환 때문에 명시
    // 이 경우 if와는 다르게 try의 본문을 반드시 중괄호로 감싸야 함
    val result = try {
        reader.readLine().toInt()
    } catch (e: NumberFormatException) { // 예외 발생 시 catch 블록에 명시된 값이 result가 됨
        "not a number"
    }
    println(result)
}
