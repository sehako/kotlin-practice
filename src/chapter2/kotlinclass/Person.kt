package chapter2.kotlinclass

// 코틀린에서의 클래스
// 맴버의 접근 제한자는 일반적으로 private
// val: getter 생성
// var: getter + setter 생성
class Person(
    val name: String,
    var isStudent: Boolean
) {
}