package part01.chapter04.section3

// 자바는 equals, hashCode, toString 등의 필수 메서드가 존재한다.
// 특히 어떤 클래스가 데이터를 저장하는 역할만을 수행한다면 위 세 개의 메서드는 거의 필수적인 오버라이드 대상이다.
class Customer(val name: String, val postalCode: Int) {
    // Any 타입은 자바의 Object 객체에 대응한다
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Customer) return false
        return name == other.name && postalCode == other.postalCode
    }
    // 참고로 코틀린에서는 == 연산자가 두 객체를 비교하는 기본적인 방법이다. (내부적으로 equals 호출)
    // 코틀린에서 자바의 == 연산을 하고자 하면 === 연산자를 사용할 수 있다.

    // 자바는 equals()가 true를 반환하는 경우 두 객체는 반드시 같은 hashCode()를 반환해야 한다는 규칙이 있다.
    // 코틀린에서도 위 규칙을 준수하기 때문에 다음과 같이 hashCode()도 오버라이드 해야 한다.
    override fun hashCode(): Int = 31 * name.hashCode() + postalCode

    override fun toString(): String = "Customer(name=$name, postalCode=$postalCode)"

    // 참고 1
    fun copy(name: String = this.name, postalCode: Int = this.postalCode) = Customer(name, postalCode)
}

// 위와 같은 코드를 매번 정의하는 것은 상당히 귀찮은 일일 것이다.
// 데이터 클래스를 사용하면 코틀린 컴파일러가 이런 메서드들을 내부적으로 생성해준다.
// 아래 클래스는 자바에서 요구하는 다음 메서드들을 포함한다.
// equals, hashCode, toString
data class Customer2(val name: String, val postalCode: Int)
// equals와 hashCode는 주 생성자에 나열된 모든 프로퍼티를 고려해 만들어진다.
// 이 말은 주 생성자 밖에서 정의된 프로퍼티는 equals나 hashCode를 계산할 때 고려의 대상이 아니라는 말이기도 하다.

fun main() {
    val c1 = Customer2("A", 11521)
    val c2 = Customer2("A", 11521)
    val c3 = Customer2("B", 16943)

    println(c1)
    println(c1 == c2) // true
    println(c1 == c3) // false
    println(c1.hashCode()) // 13536
    println(c2.hashCode()) // 13536

    // 데이터 클래스에 var을 활용해도 되지만, 기본적으로 모든 프로퍼티를 읽기 전용으로 만들어 데이터클래스를 불변 클래스로 만들라고 권장한다.
    // 코틀린 컴파일러는 데이터 클래스 인스턴스를 불변 객체로 더 쉽게 활용이 가능하도록
    // 객체를 복사하면서 일부 프로퍼티를 바꿀 수 있는 copy 메서드를 지원한다.
    // 만약 일반 코틀린 클래스에서 copy를 직접 구현하면 다음과 같을 것이다 (참고 1)
    val c1Copy = c1.copy(name = "C")

    println(c1Copy)
}

/*
코틀린 데이터 클래스 VS 자바 레코드

자바 14부터 코틀린과 유사한 레코드가 도입되었다. 마찬가지로 equals, hashCode, toString을 자동으로 생성한다.
하지만 copy와 같은 편의 메서드는 없고, 추가적으로 다음과 같은 구조적 제약이 있다.
- 모든 프로퍼티가 private이며 final 이어야 함
- 레코드는 상위 클래스를 확장할 수 없음
- 클래스 본문 안에서 다른 프로퍼티를 정의할 수 없음

상호운용성 목적으로 코틀린 데이터 클래스에 @JvmRecord를 선언할 수 있으며, 그 경우 위와 같은 제약 사항을 지켜야 한다.
 */
