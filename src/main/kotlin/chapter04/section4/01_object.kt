package chapter04.section4

import java.io.File

// 코틀린은 클래스를 정의하는 동시에 인스턴스를 생성하는 object 키워드를 지원한다.
// 주로 다음과 같은 상황에서 사용된다.
// - 싱글톤: 코틀린은 object를 통해 싱글톤을 언어에서 기본 지원한다.
// - 동반 객체: 어떤 객체와 관련이 있지만 호출하기 위해 그 클래스의 객체가 필요하지 않은 메서드와 팩토리 메서드를 담을 때 쓰인다.
// - 객체 식: 자바의 익명 내부 클래스 대신 쓰인다.

// 싱글톤
// object는 생성자를 쓸 수 없고, 초기 상태에 대한 요구가 있다면 본문에서 직접 정의해야 한다.
object Payroll {
    val allEmployees = arrayListOf<String>()

    fun calculateSalary() {
        for (employee in allEmployees) {

        }
    }
}

// object도 클래스나 인터페이스를 상속할 수 있다.
object CaseInsensitiveFileComparator : Comparator<File> { // Comparator 인터페이스 구현 예시
    override fun compare(o1: File, o2: File): Int {
        return o1.path.compareTo(o2.path, ignoreCase = true)
    }
}

// 클래스내에서도 object를 활용할 수 있다.
data class ObjectPerson(val name: String) {
    // 정적 중첩 클래스를 활용한 Comparator 상속 클래스를 object로 선언
    object NameComparator : Comparator<ObjectPerson> {
        override fun compare(o1: ObjectPerson, o2: ObjectPerson): Int {
            return o1.name.compareTo(o2.name)
        }
    }
}

fun main() {
    // 싱글톤 객체의 메서드 사용은 자바의 static 메서드 사용과 유사하다.
    Payroll.calculateSalary()

    // object의 상속 예시
    println(CaseInsensitiveFileComparator.compare(File("a"), File("A")))

    // object의 정적 중첩 클래스 활용 예시
    println(ObjectPerson.NameComparator.compare(ObjectPerson("a"), ObjectPerson("A")))
    /*
    코틀린 object는 유일한 인스턴스에 대한 정적 필드가 있는 자바 클래스로 컴파일된다.
    이때 해당 인스턴스 필드의 이름은 항상 INSTANCE이다.
    따라서 위 코드를 자바에서 사용한다면 다음과 같다.
    ObjectPerson.NameComparator.INSTANCE.compare(ObjectPerson("a"), ObjectPerson("A"))
     */
}

