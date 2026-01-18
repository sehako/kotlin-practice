// @file:JvmName(name = "Joiner") 자바 컴파일 시 만들 클래스 이름 지정 (참고 1)

package chapter03.section2.util

// 자바에서는 모든 코드를 클래스의 메서드로 작성해야 함
// 코틀린에서는 함수를 소스 파일의 최상위 수준에 명시함으로써 외부에서 import가 가능하도록 만들 수 있음
// 이렇게 최상위 함수로 명시한 경우에는 자바로 다음과 같이 컴파일 됨

/*
// 만약 이 클래스 이름을 변경하고자 한다면 파일 수준의 @file:JvmName()활용 (참고 1)
public final class JoinerKt {
    public static String joinToString(...) { ... }
}
 */

// 함수에 인자가 전달되지 않는 경우에 사용할 기본값을 명시할 수 있음 (default parameters)
// 자바는 디폴트 파라미터 개념이 없기 때문에 자바에서 편리하게 코틀린 함수를 호출하고 싶다면
// @JvmOverloads 어노테이션을 함수에 추가할 수 있음
// 모든 파라미터부터 마지막 파라미터를 제외해가면서 함수 오버로딩을 설계
fun <T> joinToString(
    collection: Collection<T>,
    separator: String = ", ",
    prefix: String = "",
    postfix: String = ""
): String {
    val result = StringBuilder(prefix)

    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }

    result.append(postfix)
    return result.toString()
}