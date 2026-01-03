package chapter3.section3.util

// 확장 함수 import 예시 확인용
fun String.lastCharOutSide(): Char = this[length - 1]

// 자바에서 이런 확장 함수는 컴파일된 정적 메서드를 호출하면서 인자로 수신 객체를 넘기면 됨
// 위 확장 함수는 최상위 함수로 명시되었기 때문에 정적 메서드로 컴파일 되어 다음과 같이 호출 가능
// char c = StringsKt.lastCharOutSide("Kotlin");
