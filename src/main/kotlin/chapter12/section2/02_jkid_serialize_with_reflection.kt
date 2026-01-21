package chapter12.section2

// 제이키드가 리플렉션을 사용하는 방법을 살펴보자.
/*
fun serialize(obj: Any): String = buildString { serializeObject(obj) }

private fun StringBuilder.serializeObject(obj: Any) {
    (obj::class as KClass<Any>)
        .memberProperties
        // 여기서 어노테이션 유무를 검사하는 것을 볼 수 있다. // (코드 1)
        .filter { it.findAnnotation<JsonExclude>() == null }
        .joinToStringBuilder(this, prefix = "{", postfix = "}") {
            serializeProperty(it, obj)
        }
}

private fun StringBuilder.serializeProperty(
    prop: KProperty1<Any, *>, obj: Any
) {
    // (코드 2)
    val jsonNameAnn = prop.findAnnotation<JsonName>()
    val propName = jsonNameAnn?.name ?: prop.name
    serializeString(propName)
    append(": ")

    val value = prop.get(obj)
    val jsonValue = prop.getSerializer()?.toJsonValue(value) ?: value
    serializePropertyValue(jsonValue)
}

fun KProperty<*>.getSerializer(): ValueSerializer<Any?>? {
    val customSerializerAnn = findAnnotation<CustomSerializer>() ?: return null
    val serializerClass = customSerializerAnn.serializerClass

    // (코드 4)
    val valueSerializer = serializerClass.objectInstance
        ?: serializerClass.createInstance()
    @Suppress("UNCHECKED_CAST")
    return valueSerializer as ValueSerializer<Any?>
}
 */

// 이는 제이키드에서 직렬화를 수행하는 함수의 코드다. 임의의 객체를 받아서 그 객체에 대한 JSON 문자열로 반환한다.
// StringBuilder의 확장 함수로 구현하여 편리하게 append 메서드를 사용할 수 있도록 한 것이 눈에 띈다.
// 여기서 리플렉션이 사용된 것을 볼 수 있다. 직렬화 함수는 기본적으로 객체의 모든 프로퍼티를 적절한 타입으로 직렬화한다.

// 여기에 어노테이션이 어떻게 사용되었는지 알아보도록 하자.

// 코드 1
// 여기서 KAnnotatedElement 인터페이스에서 annotations라는 프로퍼티가 존재한다.
// 이는 @Retention을 RUNTIME으로 지정한 모든 어노테이션 인스턴스의 컬렉션이 있다.
// KProperty는 이를 확장하므로 프로퍼티의 모든 어노테이션을 얻을 수 있기 때문에 findAnnotation으로 어노테이션 검사를 할 수 있다.

// 코드 2
// @JsonName은 어노테이션의 name 인자를 알아야 한다. findAnnotation은 전달된 조건의 어노테이션을 찾으면 해당 인스턴스를 반환한다.
// 제이키드에서는 이를 통해 어노테이션을 찾은 다음에 엘비스 연산자로 @JsonName으로 지정한 이름을 우선시하도록 만들었다.

// 코드 3
// @CustomSerializer에 ValueSerializer 인터페이스를 구현한 클래스의 KClass를 전달했었다.
// 이는 getSerializer에서 검사한다. findAnnotation 함수를 통해 해당 어노테이션을 찾는다.
// 여기서 어노테이션의 값으로 클래스와 object를 처리하는 방식을 주의깊게 봐야 한다.
// 클래스와 object는 모두 KClass 클래스로 표현된다. 다만 object 선언에 의해 생성된 싱글톤은 objectInstance가 null이 아니다.
// 즉 코드 4 부분은 objectInstance를 먼저 얻고, 만약이것이 null이면 createInstance()를 통해 직접 인스턴스를 생성한다.

