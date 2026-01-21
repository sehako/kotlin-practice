package chapter03.section3.util

// joinToString을 확장 함수로써 유틸리티 함수로 만든 예시
fun <T> Collection<T>.joinToStringExpandFunction(
    separator: String = ", ",
    prefix: String = "",
    postfix: String = ""
): String {
    val result = StringBuilder(prefix)

    // this.withIndex()에서 this 생략
    for ((index, element) in withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }

    result.append(postfix)
    return result.toString()
}
