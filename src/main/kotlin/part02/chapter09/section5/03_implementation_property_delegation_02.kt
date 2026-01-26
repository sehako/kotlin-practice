package part02.chapter09.section5

// 앞선 ObservePerson에서 setter 코드의 중복이 발견된다.
// 따라서 프로퍼티의 값을 저장하고 필요에 따라 통지를 보내주는 클래스를 추출해보도록 하자.
class ObservableProperty(
    val propName: String,
    var propValue: Int,
    val observable: Observable
) {
    fun getValue(): Int = propValue
    fun setValue(newValue: Int) {
        val oldValue = propValue
        propValue = newValue
        observable.notifyObservers(propName, oldValue, newValue)
    }
}

class ObservablePerson(
    val name: String,
    age: Int,
    salary: Int
) : Observable() {
    val _age = ObservableProperty("age", age, this)
    var age: Int
        get() = _age.getValue()
        set(value) {
            _age.setValue(value)
        }
    val _salary = ObservableProperty("salary", salary, this)
    var salary: Int
        get() = _salary.getValue()
        set(value) {
            _salary.setValue(value)
        }
}

// 코드가 전체적으로 코틀린의 위임이 실제로 작동하는 방식과 비슷해졌다.
