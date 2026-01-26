package part01.chapter02.section2

// 코틀린에서의 클래스
// 맴버의 접근 제한자는 일반적으로 private
// val: getter 생성
// var: getter + setter 생성
// 대부분의 프로퍼티는 그 프로퍼티의 값을 저장하기 위한 필드인 뒷받침하는 필드(backing field)가 있다.
class Person(
    val name: String,
    var isStudent: Boolean
)
