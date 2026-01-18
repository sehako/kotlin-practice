package chapter07

/*
데이빗 파나스는 타입을 가능한 값의 집합과 그런 값들에 대해 수행할 수 있는 연산의 집합으로 정의했다. (1976년)
이 정의를 기반으로 자바 타입을 보자. double은 64비트 부동 소수점으로 해당 타입에 속한 값이면 일반 수학 연산 함수를 ㅈ거용할 수 있다.
참조 타입은 어떨까? 모든 참조 타입은 해당 타입의 대한 값과 null을 가진다. 이 두 종류의 값은 완전히 다른다.
instanceof도 이 두 값이 다르다고 하기 때문에 실행할 수 있는 연산도 완전히 다르다.
이는 자바의 타입 시스템이 nul을 제대로 다루지 못한다는 뜻이기도 하다.
변수에 선언된 타입이 있지만 null 여부를 추가로 검사하기 전에는 그 변수에 대해 어떤 연산이 가능한 지 알 수 없다.
물론 null 관련 어노테이션이나 Optional 래퍼 타입이 있긴 하지만 완전히 NPE를 막을 수 없고, 성능 저하 문제 또한 감수해야 한다.
코틀린은 null이 될 수 있는 타입을 구분하는 것을 통해서 각 타입의 값에 대해 어떤 연산이 가능한 지 명확히 이해할 수 있고,
실행 시점에 예외를 발생시킬 수 있는 연산을 판단할 수 있다. 따라서 그런 연산을 아예 금지시킬 수 있다.
*/

// 안전한 호출 연산자 (?.)는 null 검사와 메서드 호출을 한 연산으로 수행한다.
fun stringUppercase(s: String?): String? = s?.uppercase()
// 이렇게 호출할 경우 호출하려는 값이 null이 아니라면 ?.는 일반 메서드 호출처럼 동작하지만, null이면 null이 결과값이 된다.
// 따라서 안전한 호출의 결과 타입도 null이 될 수 있다는 것에 유의해야 한다.

// 메서드 호출 뿐 아니라 프로퍼티를 읽거나 쓸 때도 안전한 호출을 사용할 수 있다.
class Employee(val name: String, val manager: Employee?)

fun managerName(employee: Employee): String? = employee.manager?.name

// 객체 그래프에서 null이 될 수 있는 중간 객체가 여럿 있다면 한 식 내에서 안전한 호출을 연쇄하여 함께 사용하면 편리하다.
class NullAddress(val country: String?)
class NullCompany(val address: NullAddress?)
class NullPerson(val name: String?, val nullCompany: NullCompany?)

fun NullPerson.countryName(): String {
    val country = this.nullCompany?.address?.country // 자바 코드보다 간결하게 null 검사를 할 수 있다.
    return if (country != null) country else "Unknown"
}

// 위 코드에서 불필요하게 if로 country의 null 여부를 검사했다.
// 코틀린은 null 대신 사용할 기본값을 지정할 때 엘비스 연산자를 사용하여 이를 간결하게 표현할 수 있다.
fun greet(name: String?) {
    // name이 null이면 "unnamed"가 된다.
    val recipient: String = name ?: "unnamed"
}

// 이를 안전한 호출 연산자와 조합하여 다음과 같이 null 반환에 따른 값을 지정하는데 사용하기도 한다.
fun strLenNullSafe(s: String?): Int = s?.length ?: 0

// 코틀린에서는 return이나 throw 등도 식이므로 엘비스 연산자의 오른쪽에 return이나 throw를 명시할 수 있다.
fun NullPerson.personInfo(): String {
    val name = this.name ?: throw IllegalStateException("No name")
    val country = this.nullCompany?.address?.country ?: "Unknown"
    return "$name from $country"
}