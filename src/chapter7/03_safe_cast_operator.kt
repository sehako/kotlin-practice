package chapter7

// 코틀린에서도 as로 타입을 캐스팅 할 때, 지정된 타입으로 캐스팅할 수 없으면 ClassCastException이 발생한다.
// 이에 대해서 as?를 제공한다. 이는 지정된 타입으로 변환이 불가능하면 null을 반환한다.
// 대표적으로 equals를 구현할 때 유용하게 사용된다.

class EqualsPerson(val firstName: String, val lastName: String) {
    override fun equals(other: Any?): Boolean {
        // 변환된 타입이 null 이면 false를 반환
        val otherPerson = other as? EqualsPerson ?: return false
        return firstName == otherPerson.firstName && lastName == otherPerson.lastName
    }

    override fun hashCode(): Int {
        return firstName.hashCode() * 37 + lastName.hashCode()
    }
}
