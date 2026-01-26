package part02.chapter09.section5

// 위임 프로퍼티가 어떤 방식으로 동작하는지 정리해보자.
/*
class C {
    var prop: Type by MyDelegate()
}
val c = C()
 */

// MyDelegate 클래스의 인스턴스는 감춰진 프로퍼티(<delegate>)에 저장되며,
// 컴파일러는 프로퍼티를 표현하기 위해 KProperty(<property>) 타입의 객체를 사용한다.

/*
class C {
    private val <delegate> = MyDelegate()
    var prop: Type
        get() = <delegate>.getValue(this, <property>)
        set(value: Type) = <delegate>.setValue(this, <property>, value)
}
이는 상당히 단순하지만 상당히 흥미로운 활용법이 많다.
프로퍼티 값이 저장될 장소를 바꿀 수도 있고 프로퍼티를 읽거나 쓸 때 벌어질 일을 변경할 수도 있다.
이 모두를 간결한 코드로 달성할 수 있는 것이다.
*/

// Map에 위임해서 동적으로 애트리뷰트 접근
// 자신의 프로퍼티를 동적으로 정의할 수 있는 객체를 만들 때 위임 프로퍼티를 활용하는 경우가 자주 있다.
// 연락처 관리 시스템에서 각 연락처별로 임의의 정보를 저장할 수 있게 허용한다고 가정해보자.
// 시스템에 저장된 연락처에는 특별히 처리해야 하는 일부 필수 정보가 있고, 달라질 수 있는 추가 정보가 있다.
class ContactPerson {
    private val _attributes = mutableMapOf<String, String>()

//    var name: String
//        get() = _attributes["name"]!!
//        set(value) {
//            _attributes["name"] = value
//        }

    // 여기서 추가 데이터를 객체에 읽어 들이기 위해 일반적인 API를 사용하고
    // 한 프로퍼티를 처리하기 위해 구체적인 API를 제공한다.
    // 이를 위임 프로퍼티를 활용하도록 변경하려면 by 키워드 오른쪽에 Map을 명시하면 된다.
    var name: String by _attributes
}
