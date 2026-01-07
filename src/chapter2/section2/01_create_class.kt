package chapter2.section2

fun main() {
    // 코틀린의 클래스 인스턴스 생성 방법
    // Java와 다르게 new가 빠져있음
    val person = Person("Kotlin", true)

    // 프로퍼티의 이름을 호출하면 내부적으로 getter를 호출
    println(person.name)
    println(person.isStudent)

    // var 프로퍼티의 경우 setter가 존재
    // 일반적인 대입 방식으로 작성하면 내부적으로 setter를 호출
    person.isStudent = false

    val rectangle = Rectangle(10, 20)
    println(rectangle.isSquare)
}