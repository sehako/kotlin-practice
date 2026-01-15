package chapter7

// 코틀린에서 함수나 클래스의 모든 타입 파라미터(자바의 제네릭)는 기본적으로 null이 될 수 있다.
// null될 수 있는 타입을 포함하는 어떤 타입이라도 타입 파라미터를 대신할 수 있다.
// 따라서 타입 파라미터 T를 클래스나 함수 안에서 타입 이름으로 사용하면 이름 끝에 ?가 없어도 T가 null이 될 수 있다.
fun <T> printHashCode(t: T) {
    println(t?.hashCode())
}

fun hashCodeFunctionCall() {
    printHashCode("Kotlin")
    printHashCode(null) // t는 Any?로 추론된다.
}

// 타입 파라미터가 null이 아님을 확실히 하려면 null이 될 수 없는 타입 상계(upper bound)를 지정해야 한다.
fun <T : Any> printHashCodeNotNull(t: T) {
    println(t.hashCode())
}

fun hashCodeNotNullFunctionCall() {
    printHashCodeNotNull("Kotlin")
//    printHashCodeNotNull(null) <- 컴파일 에러 발생
}
