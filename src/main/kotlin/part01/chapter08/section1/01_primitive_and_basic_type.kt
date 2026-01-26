package part01.chapter08.section1

// 자바는 원시 타입(int)을 감싼 래퍼 객체(Integer)를 사용한다.
// 코틀린은 이 두 타입을 구분하지 않는다. 따라서 다음과 같이 같은 타입을 사용한다.
val i: Int = 1
val list: List<Int> = listOf(1, 2, 3)

// 원시 타입 값에 대해 메서드를 호출할 수도 있다.
fun shorProgress(progress: Int) = progress.coerceIn(0, 100) // 값을 특정 범위로 제한

// 하지만 코틀린은 항상 객체로 표현하는 것은 아니다. 컴파일러가 가능한 가장 효율적인 방식으로 컴파일하기 때문이다.
// 따라서 대부분의 경우 원시 타입으로 컴파일된다.
/*
타입 목록과 리터럴
정수: Byte, Short, Int, Long(1L)
부동소수점: Float(1f, 1F), Double(0.1, 1.2e10, 1.2e-10)
문자: Char
불리언: Boolean
16진 리터럴: 0xCAFEBABE
2진 리터럴: 0b0000101

또한 코틀린은 JVM의 원시 타입 범위를 시프트해서 부호없는 타입을 제공한다. (일반적으로 잘 사용하지는 않는다.)
리터럴: 123U, 123UL,0x10cU
UByte (8bit): 0 ~ 2^8 - 1
UShort (16bit): 0 ~ 2^16 - 1
UInt (32bit): 0 ~ 2^32 - 1
ULong (64bit): 0 ~ 2^64 - 1

부호없는 타입도 필요할 때만 래핑된다. 참고로 JVM 명세에는 부호 없는 수에 대한 원시 타입 제공이 없다.
따라서 이는 코틀린의 인라인 클래스로 구현되었다.
 */

// 코틀린의 null이 될 수 있는 타입은 자바의 래퍼 타입으로 컴파일된다.
// 또한 제네릭 클래스를 사용하는 경우에는 JVM의 제네릭 구현 방법 때문에 래퍼 타입으로 컴파일 된다
data class PersonType(val name: String, val age: Int? = null) {
    fun isOlderThan(other: PersonType): Boolean? {
        age ?: return null
        other.age ?: return null
        return age > other.age
    }
}

fun comparePerson() {
    PersonType("A", 29).isOlderThan(PersonType("B", 30)) // false
    PersonType("A", 29).isOlderThan(PersonType("B")) // null
}

// 코틀린은 한 타입의 수를 다른 타입의 수로 자동 변환하지 않는다. (심지어 더 넓은 범위로의 변환도 제공하지 않는다)
val num = 1

//val lnum: Long = i  <- type mismatch 컴파일 오류 발생
// 직접 변환 메서드를 호출해야 한다.
val lnum: Long = num.toLong()

// 코틀린은 모든 변환 타입에 대해 toByte(), toShort(), toChar() 등의 변환 함수를 양방향으로 제공한다.
val inum: Int = lnum.toInt()

// 자바는 원시 타입 간엔 자동 변환이 되지만 래퍼 타입(Integer vs Long)은 equals 비교가 불가능해 동작이 불일치했다.
// 코틀린은 이를 통일하여, "다른 타입은 절대 같을 수 없다"는 원칙으로 타입 안정성을 보장한다.
// 이 문제는 equals 메서드에서 잘 나타난다.
fun isInList() {
    val x = 1 // int
    val lList = listOf(1L)
//    x in lList  <- 타입 관련 컴파일 오류 발생
    // 따라서 코틀린에서는 다음과 같이 타입을 명시적으로 변환해야 한다.
    x.toLong() in lList
}

// 따라서 코틀린에서 동시에 여러 숫자 타입을 사용하려면 각 변수를 명시적으로 변환해야 한다.
// 산술 연산자는 적당한 타입의 값을 받도록 오버로드 되어 있다.
fun printLong(num: Long) = println(num)
fun calculateNumber() {
    val b: Byte = 1
    val l: Long = b + 1L
    printLong(1) // 1을 Long 값으로 해석
}

// 마지막으로 코틀린은 문자열 -> 숫자, 문자열 -> 불리언에 대한 변환을 제공한다.
fun stringConvertExample() {
    "42".toInt()
    // 잘못된 값을 변환 시도하면 NumberFormatException이 발생한다.
    // 이를 위해 변환 시 문제가 생기면 null을 반환하는 확장 함수가 존재한다.
    "seven".toIntOrNull()
    // Boolean 변환은 대소를 구분하지 않는다.
    "true".toBoolean()
    "FaLse".toBoolean()
    "7".toBoolean() // false를 반환

    // 정확히 true, false와 대응시키고 싶으면 toBooleanStrict()를 사용하자.
    "true".toBooleanStrict()
    // 이 함수는 변환 실패 시 예외를 던지므로 null 반환 함수가 존재한다.
    "1".toBooleanStrictOrNull()
}
