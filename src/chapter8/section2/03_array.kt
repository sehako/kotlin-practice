package chapter8.section2

// 자바 main 함수의 표준 시그니처는 배열 파라미터가 존재한다. (String[] args)
// 코틀린 역시 자바와 같은 main 함수 표준 시그니처를 사용한다.
fun main(args: Array<String>) {
    // 배열의 인덱스 값의 범위에 대해 이터레이션 하기 위한 Array.indices 확장 함수
    for (i in args.indices) {
        println(args[i]) // 각괄호로 인덱스를 사용해 배열 원소에 접근
    }
}

// 코틀린 배열은 타입 파라미터를 받는 클래스이므로 배열의 원소 타입은 그 파라미터에 의해 정해진다.
// 코틀린에서 배열은 만드는 방법은 다음과 같다.
fun arrayGenerateExample() {
    // arrayOf
    // 리터럴로 받은 원소들을 포함하는 배열을 생성한다.
    val intArray: Array<Int> = arrayOf(1, 2, 3)

    // arrayOfNulls
    // 모든 원소가 null인 정해진 크기의 배열을 만든다. 당연히 원소 타입은 null이 될 수 있는 타입이다.
    val nullArray: Array<Int?> = arrayOfNulls(3)

    // Array 생성자
    // 배열 크기와 람다를 인자로 받아 람다를 호출하여 각 배열 원소를 초기화한다.
    // 원소를 하나하나 전달하지 않으면서 원소가 null이 아닌 배열을 만들어야 할 때 이 생성자를 사용한다.
    val letters = Array<String>(26) { i -> ('a' + i).toString() }
    // 람다는 배열 원소의 인덱스를 인자로 받아 해당 위치에 삽입될 원소를 반환한다.
    // 즉 위 람다식은 0부터 시작하는 인덱스 값에 'a'를 더하고, 이를 문자열로 변환한 것이다.

    // 참고로 arrayOf()와 Array 생성자는 타입 추론을 지원한다.
    val intArray2 = arrayOf(1, 2, 3)
    val letters2 = Array(26) { i -> ('a' + i).toString() }
}

// 컬렉션을 배열로 변환해야 할 때에는 toTypedArray 메서드를 사용하면 된다.
fun toTypedArrayExample() {
    val list = listOf(1, 2, 3)
    val intArray: Array<Int> = list.toTypedArray()
}

// 코틀린은 원시 타입의 배열을 표현하는 별도 클래스를 각 원시 타입마다 하나씩 제공한다.
// IntArray, LongArray, BooleanArray 등의 원시 타입 배열을 제공한다.
fun primitiveArrayExample() {
    // 원시 타입 배열도 3가지 방법으로 만들 수 있다.
    // arrayOf()
    val intNumbers = intArrayOf(1, 2, 3)
    val longNumbers = longArrayOf(1L, 2L, 3L)

    // 원시 배열 타입의 생성자 호출
    // 전달된 size 만큼 초기화된 배열을 반환한다.
    val intArray = IntArray(3) // int[]
    val longArray = LongArray(3) // long[]

    // 크기와 람다를 인자로 받는 생성자 사용
    val squares = IntArray(3) { (it + 1) * (it + 1) }

    // 또한 래퍼 객체로 이루어진 컬렉션이나 배열이 있다면 이를 원시 배열 타입으로 변환 할 수 있다.
    val intList = listOf(1, 2, 3)
    val intArrayFromList = intList.toIntArray()
}

// 코틀린은 배열 기본 연산과 함께 컬렉션에 사용할 수 있는 모든 확장 함수를 배열에도 제공한다.
// 따라서 filter나 map도 활용할 수 있다. 다만 리스트가 반환되는 것은 유의해야 한다.
// 이를 활용하여 맨 윗 부분의 fun main()을 다음과 같이 개선할 수도 있다.
fun arrayForEach(args: Array<String>) {
    args.forEachIndexed { index, string -> println("$index: $string") }
}
