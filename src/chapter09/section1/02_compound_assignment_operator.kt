package chapter09.section1

// plus와 같은 연산자를 오버로딩 하면 코틀린은 자동으로 += 같은 복합 대입 연산자도 함께 지원한다.
data class AssignmentPoint(val x: Int, val y: Int) {
    operator fun plus(other: AssignmentPoint) = AssignmentPoint(x + other.x, y + other.y)
}

fun plusAssignmentExample() {
    var point = AssignmentPoint(1, 2)
    point += AssignmentPoint(3, 4)
}

// 경우에 따라 += 연산이 객체의 참조를 다른 참조로 바꾸기보다 원래 객체의 내부 상태를 변경하게 만들고 싶을 때가 있다.
// 이런 경우 별도로 복합 대입 연산자를 따로 정의할 수 있다. 다음 상황을 보도록 하자.
fun addToCollection() {
    val numbers = mutableListOf(1, 2, 3)
    // 변경 가능한 컬렉션에 원소를 추가
    numbers += 4
    // 이것이 가능한 이유는 코틀린 표준 라이브러리가 변경 가능한 컬렉션에 대해 plusAssign을 정의하기 때문이다.
    /*
    @kotlin.internal.InlineOnly
    public inline operator fun <T> MutableCollection<in T>.plusAssign(element: T) {
        this.add(element)
    }
     */
}

// 이론적으로는 코드에 있는 +=를 plus와 plusAssign 양쪽으로 컴파일할 수 있다.
// 하지만 어떤 클래스가 이 두 함수를 모두 정의하고 둘 다 +=에 사용할 수 있으면 오류를 보고한다.
// 이를 해결하려면 직접 일반 함수처럼 사용하거나, var를 val로 바꿔 plus 적용이 불가능하게 만들 수 있다.
// 따라서 둘 모두 정의하지 말고, 불변 클래스라면 plus를 가변 클랙스라면 plusAssign만 정의하도록 하자.

// 코틀린 표준 라이브러리는 컬렉션에 대해 두 가지 접근 방법을 함께 제공한다
// 일반 연산자는 항상 새로운 컬렉션을,
// 복합 할당 연산자는 읽기 전용일 때는 변경을 적용한 복사본을, 변경 가능할 때는 메모리에 있는 객체 상태를 변화시킨다.
fun collectionOperation() {
    val list = mutableListOf(1, 2)
    list += 3 // [1, 2, 3]
    val newList = list + listOf(4, 5) // [1, 2, 3, 4, 5]
}
