package chapter04.section2

// 코틀린에서 접근자의 가시성은 기본적으로 프로퍼티의 가시성과 같다.
// 하지만 getter나 setter에 가시성 변경자를 추가해서 접근자의 가시성을 변경할 수 있다.
class LengthCounter {
    var counter: Int = 0
        private set // 클래스 바깥에서 프로퍼티를 바꿀 수 없음

    fun addWord(word: String) {
        counter += word.length
    }
}