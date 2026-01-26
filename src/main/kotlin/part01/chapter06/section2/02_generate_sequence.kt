package part01.chapter06.section2

import java.io.File

// 시퀀스를 generateSequence 함수로 사용할 수 있다.
fun generateSequenceExample() {
    val naturalNumbers = generateSequence(1) { it + 1 } // 무한 시퀀스
    val numbersTo100 = naturalNumbers.takeWhile { it <= 100 } // 유한 시퀀스

    // 위 두 시퀀스 모두 최종 연산이 호출되기 전 까지 연산을 지연한다.
    numbersTo100.sum() // 최종 연산 호출


}

// 시퀀스를 사용하는 일반적인 용례 중 하나는 객체의 조상들로 이루어진 시퀀스를 만들어내는 것이다.
// 어떤 객체의 조상이 자신과 같은 타입이고 모든 조상이 시퀀스에서 어떤 특성을 알고 싶을 때 (사람 또는 파일 디렉터리 계층 구조)
fun File.isInsideHiddenDirectory() = generateSequence(this) { it.parentFile }.any { it.isHidden }
fun fileHierarchySequenceExample() {
    val file = File("/Users/sehako/.HiddenDir/a.txt")
    file.isInsideHiddenDirectory()
}
