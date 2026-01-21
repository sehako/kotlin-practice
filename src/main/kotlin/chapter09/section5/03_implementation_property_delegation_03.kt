package chapter09.section5

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

// 이제 ObservableProperty를 코틀린 관례에 맞게 수정해보도록 하자.
class ObservablePropertyKt(var propValue: Int, val observable: Observable) {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): Int = propValue
    operator fun setValue(thisRef: Any?, prop: KProperty<*>, newValue: Int) {
        val oldValue = propValue
        propValue = newValue
        observable.notifyObservers(prop.name, oldValue, newValue)
    }
}

class PersonWithDelegation(val name: String, age: Int, salary: Int) : Observable() {
    var age: Int by ObservablePropertyKt(age, this)
    var salary: Int by ObservablePropertyKt(salary, this)
}
// by 키워드를 사용해 위임 객체를 지정하면 이전 예제에서 작성했던 코드들을 코틀린 컴파일러가 자동으로 처리해준다.
// 즉 위 코드는 이전 코드와 비슷하다.
// 위임 객체를 감춰진 프로퍼티에 저장하고, 주 객체의 프로퍼티를 읽거나 쓸 때마다 위임 객체의 getValue와 setValue를 호출한다.

// 관찰 가능한 프로퍼티 로직을 직접 작성하는 대신 코틀린 표준 라이브러리를 사용해도 된다.
// 그리고 해당 표준 라이브러리 클래스에 상태 변화 시 호출될 람다를 인자로 넘길 수 있다.
class DelegatedPerson(val name: String, age: Int, salary: Int) : Observable() {
    // 값이 변경되면 옵저버에게 알림
    private val onChange = { property: KProperty<*>, oldValue: Int, newValue: Int ->
        notifyObservers(property.name, oldValue, newValue)
    }

    var age: Int by Delegates.observable(age, onChange)
    var salary: Int by Delegates.observable(salary, onChange)
}

