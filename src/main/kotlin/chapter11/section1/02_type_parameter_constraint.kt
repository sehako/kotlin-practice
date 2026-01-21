package chapter11.section1

// 타입 파라미터 제약은 클래스나 함수에 사용할 수 있는 타입 인자를 제한하는 기능이다.
// 리스트의 모든 원소의 합을 구하는 sum()은 숫자 관련 타입에만 적용할 수 있는 연산이다.
// 따라서 List<String>에는 그 함수를 적용할 수 없다.

// 어떤 타입을 제네릭의 타입 파라미터에 대한 상계(upper bound)로 지정하면
// 그 제네릭 타입을 인스턴스화할 때 타입 인자는 반드시 해당 타입(클래스) 이거나 해당 타입의 하위 타입(클래스)이어야 한다.
// 제약은 타입 파라미터 이름 뒤에 콜론(:)을 표시하고 그 뒤에 상계 타입을 적으면 된다. (이는 자바의 <T extends Number>와 같은 효과이다.)
fun <T : Number> toDouble(value: T): Double = value.toDouble()

// 이를 통해 특정 구현을 한 클래스만 타입 파라미터로 받도록 제한할 수 있다.
// T 타입의 어떤 두 값을 비교한다고 가정해보면 이는 비교가 가능하도록 Comparable을 구현해야 하므로 다음과 같이 상계를 지정할 수 있다.
fun <T : Comparable<T>> max(first: T, second: T): T =
    if (first > second) first else second

// 드물게 타입 파라미터에 대한 둘 이상의 제약을 가해야 하는 경우가 있다.
// CharSequence의 끝에 마침표를 검사하고 없는 경우 추가하는 함수가 있다면 T가 CharSequence와 Appendable을 구현해야 한다. (StringBuilder)
// 이를 타입 파라미터 제약 목록으로 다음과 같이 표현할 수 있다.
fun <T> ensureTrailingPeriod(seq: T) where T : CharSequence, T : Appendable { // 타입 파라미터 제약 목록
    if (!seq.endsWith('.')) seq.append('.')
}

// 파라미터 제약을 자주 쓰는 또 다른 경우로 null이 될 수 없는 타입으로 파라미터를 한정하고 싶을 때가 있다.
// 제네릭 클래스나 함수에서 아무런 상계를 정하지 않은 타입 파라미터는 Any?를 상계로 정한 파라미터이다.
class GenericProcessor<T> {
    fun process(value: T) {
        value?.hashCode() // value는 null이 될 수 있으므로 안전한 호출을 사용해야만 한다.
    }
}

// 따라서 항상 null이 될 수 없는 타입만 인자로 받고 싶다면 Any를 상계로 지정해야 한다.
class NonNullProcessor<T : Any> { // 항상 null이 될 수 없는 타입임을 보장
    fun process(value: T) {
        value.hashCode() // null이 될 수 없으므로 안전한 호출이 필요 없다.
    }
}

// 또한 null이 될 수 없는 타입을 상계로 정하면 파라미터가 null이 아닌 타입으로 제약된다.
class NonNullNumbers<T : Number> // Number의 하위 클래스이면서 null이 아닌 타입만 인자로 전달할 수 있다.

// 자바 인터페이스 JBox는 하나의 메서드 타입 파라미터에 대한 null 제약이 존재한다.
// 즉, 인터페이스 자체에는 T에 대한 제약이 없다.
// 코틀린은 이런 인터페이스를 구현하기 위해서 타입을 사용하는 지점에서 절대로 null이 될 수 없다고 표기하는 &를 제공한다.
class KBox<T> : JBox<T> {
    override fun put(t: T & Any) {
    }

    override fun putIfNotNull(t: T?) {
    }
}


