package part02.chapter11.section3

import kotlin.reflect.KClass

// 스타 프로젝션은 제네릭 타입 인자에 대한 정보가 없다는 것을 표현하고자 사용한다.
// 따라서 MutableList<*>는 MutableList<Any?>와 같지 않다.
// MutableList<*>는 어떤 정해진 구체적인 타입의 원소만을 담는 리스트지만 그 원소의 타입을 정확히 모른다는 것을 표현한다.
// 하지만 MutableList<*>에서 원소를 얻을 수는 있다. 이는 Any?의 하위 타입이라는 것은 명확하다.

fun starProjectionList() {
    val list: MutableList<*> = mutableListOf(1, "two", 3.0)

    val first = list.first() // 이는 허용한다.
//    list.add(42) <- 컴파일러는 이 메서드 호출을 금지한다.
}

// 컴파일러가 이렇게 out 프로젝션 타입으로 인식하는 이유는 내부적으로 MutableList<out Any?> 처럼 간주되기 때문이다.
// 원소 타입을 모르더라도 조회는 할 수 있지만 원소를 임의로 넣을 수는 없다. 즉, 스타 프로젝션은 자바의 <?>와 대응된다.
// 참고로 반공변 상황에서의 스타 프로젝션은 사실상 다음과 동일하다 <in Nothing>
// 이는 제네릭 클래스는 타입 파라미터가 반공변일 때 어떤 대상을 소비할지 알 수 없기 때문이다.

// 타입 인자에 대한 정보가 중요하지 않을 때 스타 프로젝션을 사용한다. (데이터를 조회하지만 타입은 의미 없을 때)
fun printFirst(list: List<*>) {
    if (list.isNotEmpty()) println(list.first())
}

// 스타 프로젝션에 대해 알아보기 위해 다음 상황을 보도록 하자.
interface FieldValidator<in T> {
    fun validate(value: T): Boolean
}

object DefaultStringValidator : FieldValidator<String> {
    override fun validate(value: String): Boolean = value.isNotBlank()
}

object DefaultIntValidator : FieldValidator<Int> {
    override fun validate(value: Int): Boolean = value >= 0
}

// 모든 검증기를 하나의 컨테이너에 넣어놓고 입력 필드의 타입에 따라서 적절한 검증기를 꺼내서 사용해본다고 가정해보자.
fun validationExample() {
    // 컨테이너에 검증기 보관
    val validators = mutableMapOf<KClass<*>, FieldValidator<*>>() // KClass 코틀린 클래스를 의미
    validators[String::class] = DefaultStringValidator
    validators[Int::class] = DefaultIntValidator

    // 이러면 검증기를 사용할 때 String 타입의 필드를 FieldValidator<*> 타입의 검증기로 검증할 수 없기 때문에 문제가 생긴다.
//    validators[String::class]!!.validate("Hello, Kotlin!") <- 컴파일 오류 발생
    // 이 오류는 알 수 없는 타입의 검증기에 구체적인 타입의 값을 넘기면 안전하지 못하다는 의미이다.
    // 이 오류를 위해서 임시적으로 타입 캐스팅으로 처리할 수도 있다.
    val stringValidator = validators[String::class] as FieldValidator<String> // 안전하지 않다고 경고한다.
    stringValidator.validate("Hello, Kotlin!")
    // 위 상황은 실수로 Int::class를 key로 조회해도 제대로 컴파일되고, 이후에 검증기 사용시 오류가 발생한다.
    // 즉, 타입 안정성도 보장할 수 없고, 실수를 방지할 수도 없다.
}

// 또 다른 해법으로 검증기를 등록하거나 가져오는 작업에 대한 유효성 검증을 캡슐화할 수도 있다.
object Validators {
    private val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()

    // 클래스와 검증기의 타입이 맞아야지만 클래스와 검증기 정보를 저장한다.
    fun <T : Any> register(kClass: KClass<T>, validator: FieldValidator<T>) {
        validators[kClass] = validator
    }

    // 안전하지 않은 캐스팅 경고가 발생하지만 잘못된 값이 들어가지 않으므로 괜찮다.
    operator fun <T : Any> get(kClass: KClass<T>): FieldValidator<T> {
        return validators[kClass] as? FieldValidator<T> ?: error("No validator for $kClass")
    }
}

// 이를 통해 외부에서 잘못된 사용을 방지할 수 있다.
