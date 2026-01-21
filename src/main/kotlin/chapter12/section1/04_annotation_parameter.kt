package chapter12.section1

import chapter12.jkid.CustomSerializer
import chapter12.jkid.DeserializeInterface
import chapter12.jkid.ValueSerializer
import chapter12.jkid.deserialization.deserialize
import chapter12.jkid.serialization.serialize
import java.text.SimpleDateFormat
import java.util.*

// 어떤 클래스를 선언 메타데이터로 참조할 수 있는 기능이 필요할 때도 있다.
// 클래스 참조를 파라미터로 하는 어노테이션 클래스를 선언하면 그런 기능을 사용할 수 있다.
// 제이키드 라이브러리에 있는 @DeserializeInterface는 인터페이스 타입인 프로퍼티에 대한 역직렬화를 제어할 때 쓰는 어노테이션이다.
// 이를 사용한 예시를 살펴보자.
interface Company {
    val name: String
}

data class CompanyImpl(override val name: String) : Company

data class DeserializationPerson(
    val name: String,
    // 인자로 CompanyImpl::class를 받는다.
    @DeserializeInterface(CompanyImpl::class) val company: Company
)

fun deserializePerson() {
    val json = """{"company": {"name": "JetBrains"}, "name": "Alice"}"""
    val person = DeserializationPerson(name = "Alice", company = CompanyImpl("JetBrains"))
    println(deserialize<DeserializationPerson>(json) == person) // true
}

// 해당 어노테이션의 내부 구현을 살펴보자.
/*
@Target(AnnotationTarget.PROPERTY)
annotation class DeserializeInterface(val targetClass: KClass<out Any>)
 */
// 코틀린은 클래스 참조를 인자로 받는 어노테이션을 위와 같이 정의한다. KClass 타입은 코틀린 클래스에 대한 참조를 저장한다.
// KClass의 타입 파라미터는 이 KClass의 인스턴스가 가리키는 코틀린 타입을 지정한다.
// 예를 들어 CompanyImpl::class 타입은 KClass<CompanyImpl>이며,
// 이는 방금 살펴본 DeserializeInterface의 파라미터 타입인 KClass<out Any>의 하위 타입이다.
// KClass의 타입 파라미터를 사용할 때 out 변경자 없이 KClass<Any>라고 쓰면
// CompanyImpl::class를 인자로 넘길 수 없고 Any::class만 넘길 수 있다. (공변성)

// 어노테이션 파라미터로 제네릭 클래스를 전달할 수 있다.
// 기본적으로 제이키드는 기본 타입이 아닌 프로퍼티를 내포된 객체로 직렬화한다.
// 이런 기본 동작을 변경하고 싶으면 값을 직렬화하는 로직을 직접 제공할 수 있다.

// 직렬화 클래스는 ValueSerializer<> 인터페이스를 구현해야 한다.
// 날짜 관련 직렬화 제네릭 클래스 구현
object DateSerializer : ValueSerializer<Date> {
    private val dateFormat = SimpleDateFormat("dd-mm-yyyy")

    override fun toJsonValue(value: Date): Any? =
        dateFormat.format(value)

    override fun fromJsonValue(jsonValue: Any?): Date =
        dateFormat.parse(jsonValue as String)
}

data class CustomSerialPerson(
    val name: String,
    // 커스텀 직렬화 명시
    @CustomSerializer(DateSerializer::class) val birthDate: Date
)

fun customSerializePerson() {
    val person = CustomSerialPerson(name = "Alice", birthDate = SimpleDateFormat("dd-mm-yyyy").parse("13-02-1987"))
    val json = """{"birthDate": "13-02-1987", "name": "Alice"}"""
    println(serialize(person) == json)
}

// 이는 다음 인터페이스를 구현한다.
/*
interface ValueSerializer<T> {
    fun toJsonValue(value: T): Any?
    fun fromJsonValue(jsonValue: Any?): T
}
 */

// @CustomSerializer는 커스텀 직렬화 클래스에 대한 참조를 인자로 받는다.
/*

@Target(AnnotationTarget.PROPERTY)
annotation class CustomSerializer(val serializerClass: KClass<out ValueSerializer<*>>) // 스타 프로젝션 활용
 */
// 스타 프로젝션을 통해 이 어노테이션의 인자는 ValueSerializer를 구현한 클래스만 인자로 받을 수 있게 된다.
