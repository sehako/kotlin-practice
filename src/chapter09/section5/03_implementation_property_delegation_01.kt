package chapter09.section5

// 어떤 객체의 프로퍼티가 바뀔 때마다 리스너에게 변경 통지를 보내고자 한다고 가정해보자. 이런 경우를 옵저버블이라고 한다.
// 이 기능을 구현하기 위해서 첫 번째로는 위임 프로퍼티 없이 구현해보고, 추후에는 위임 프로퍼티로 리팩터링 해보도록 하자.

// 옵저버 인터페이스와 open 클래스 구현
fun interface Observer {
    fun onChange(name: String, oldValue: Any?, newValue: Any?)
}

open class Observable {
    val observers = mutableListOf<Observer>()
    fun notifyObservers(name: String, oldValue: Any?, newValue: Any?) {
        observers.forEach { it.onChange(name, oldValue, newValue) }
    }
}

class ObservePerson(
    val name: String,
    age: Int,
    salary: Int
) : Observable() {
    var age: Int = age
        set(value) {
            val oldValue = field
            field = value
            // 프로퍼티 변경을 옵저버들에게 통지한다.
            notifyObservers("age", oldValue, value)
        }
    var salary: Int = salary
        set(value) {
            val oldValue = field
            field = value
            notifyObservers("salary", oldValue, value)
        }
}

// 옵저버 활용 예시
fun observerExample() {
    val p = ObservePerson("Alice", 29, 10000)
    p.observers += Observer { propName, oldValue, newValue ->
        println("$propName changed from $oldValue to $newValue")
    }

    p.age = 30 // age changed from 29 to 30
    p.salary = 11000 // salary changed from 10000 to 11000
}
