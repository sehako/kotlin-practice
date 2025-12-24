package chapter3

import org.intellij.lang.annotations.Language

// 코틀린 문자열은 자바 문자열과 같다
// 따라서 별도의 래퍼 객체도 생기지 않는다
// 그렇지만 여기에 코틀린만의 함수(확장 함수)를 제공한다
// 따라서 더 간편하게 문자열을 다룰 수 있다
fun main() {
    // 문자열 나누기
    // 기존의 자바는 점(.)을 split()에 넣으면 정규식으로 인식
    // 따라서 다음과 같은 건 나누지 못함 1.2.44.5
    // 이건 자바에서 [1, 2, 44, 5]가 아닌 []가 됨
    // 코틀린에서는 여러 조합의 파라미터를 받는 split() 확장 함수를 제공
    // 여기서 정규식은 String이 아닌 Regex 타입의 값을 받음
    // String 사용 예시
    println("1.2.44.5".split("."))
    // Regex 사용 예시 (정규식 문법은 자바와 똑같음)
    println("1.2.44-5".split("[.\\-]".toRegex()))
    // split 확장 함수 중에서는 구분 문자열을 여러 개 받는 함수가 이미 존재
    println("1.2.44-5".split(".", "-"))

    // 정규식과 3중 따옴표로 묶은 문자열
    // 파일의 전체 경로명을 디렉터리, 파일 이름, 확장자 명으로 구분
    // 1. String을 확장한 함수
    parsePath("C:/Users/user/kotlin/kotlin-book/src/chapter2/01_variables.kt")
    // 2. 정규식 사용
    parsePathRegex("C:/Users/user/kotlin/kotlin-book/src/chapter2/01_variables.kt")

    // 3중 따옴표는 줄 바꿈을 포함해 아무 문자열이나 그대로 들어감
    val kotlinString = """
        안녕하세요
        코틀린을 배우고 있습니다.
    """.trimIndent()
    // trimIndent()는 모든 줄에서 가장 짧은 공통 들여쓰기를 찾아 각 줄의 첫 부분에서 제거
    // 그리고 공백만으로 이루어진 첫 번째 줄과 마지막 줄을 제거
    // 참고로 파일의 끝 줄 표현을 위해 OS마다 다른 문자를 사용하는데, 코틀린은 이를 모두 줄 끝으로 취급한다.
    println(kotlinString)

    // 3중 따옴표에 $나 유니코드 이스케이프를 사용하고 싶을 때는 내포 식을 사용
    val think = """Hmm ${"\uD83E\uDD14"}"""
    println(think)

    // IntelliJ의 경우 다음 어노테이션과 3중 따옴표를 사용하면
    // 문자열임에도 해당 타입의 표현인지 컴파일 단위에서 체크해줌
    @Language("JSON")
    val expectedJson = """
        {
            "name": "Kotlin",
            "type": "language"
        }
    """.trimIndent()

    println(expectedJson)
}

fun parsePath(path: String) {
    val directory = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")
    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")

    println("directory: $directory")
    println("fullName: $fileName")
    println("extension: $extension")
}

fun parsePathRegex(path: String) {
    // 3중 따옴표 문자열은 어떤 문자도 이스케이프할 필요가 없음)
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if (matchResult != null) {
        // 그룹 별로 분해한 매치 결과를 의미하는 destructured 프로퍼티를 각 변수에 대입
        val (directory, fileName, extension) = matchResult.destructured
        println("directory: $directory")
        println("fullName: $fileName")
        println("extension: $extension")
    }
}
