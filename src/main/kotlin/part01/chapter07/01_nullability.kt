package part01.chapter07

// null 가능성은 NPE를 피할 수 있게 돕는 코틀린 타입 시스템의 특성이다.
// 이것이 자바와의 가장 큰 차이점으로 코틀린 타입 시스템은 null이 될 수 있는 타입을 명시적으로 지원한다.
/*
자바의 다음 메서드를 보자.
int strLen(String s) {
    return s.length();
}
여기에 null을 전달하면 NPE가 발생한다.
 */

// 코틀린에서는 위와 같은 함수를 작성할 때 null 가능성을 고려할 수 있다.
// 이 함수는 null이 인자로 들어올 수 없다. 또한 null을 넘기려고 시도하면 컴파일 에러가 발생한다.
fun strLen(s: String): Int {
    // null을 넘기면 ERROR: Null can not be a value of a non-null type String 컴파일 에러 발생
    return s.length
}

// 파라미터가 null이 될 수 있으면 타입 이름 뒤에 ?를 명시하면 된다.
// 이 경우에는 해당 파라미터가 주어진 타입 또는 null이 될 수 있다는 것을 의미한다.
// 즉 모든 타입은 기본적으로 null을 허용하지 않는다.
fun strLenNullable(s: String?) {
    // 이 경우에는 s.length 같은 타입 고유의 메서드를 직접 호출할 수 없다.
    s?.length
    // 또는
    s!!.length
}

// 또한 null이 될 수 있는 값을 null이 될 수 없는 값에 대입할 수도 없다.
fun nullAssign() {
    var x: String? = null
//    var y: String = x <- 컴파일 에러 발생
    // 마찬가지로 null아닌 파라미터를 받는 함수에 전달할 수도 없다.
//    strLen(x) <- 컴파일 에러 발생
}

// null이 될 수 있는 값의 가장 중요한 것은 바로 null과의 비교를 통해 null이 아님을 확실히 하는 것이다.
fun strLenSafe(s: String?): Int = if (s != null) s.length else 0
