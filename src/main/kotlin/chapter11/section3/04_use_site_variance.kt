package chapter11.section3

// 클래스를 선언하면서 변성을 지정하는 것은 선언 지점 변성(declaration site variance)이라고 한다.
// 자바에서는 타입 파라미터가 있는 타입을 사용할 때마다 그 타입 파라미터를 하위 타입이나 상위 타입 중 어떤 타입으로 대치할 수 있는지 명시해야 한다.
// 이를 와일드카드 타입 (? extends / ? super)으로 처리하는데, 이를 사용 지점 변성이라고 한다.
// 코틀린은 이런 사용 지점 변성 또한 지원한다.
// 참고로 선언 지점 변성이 변성 변경자를 단 한 번만 표시하고 클래스를 쓰는 쪽에서는 변성을 신경 쓸 필요가 없으므로 코드가 더 간결하다.

// MutableList 같은 상당수의 인터페이스는 타입 파라미터로 지정된 타입을 소비하는 동시에 생선하기 때문에 일반적으로 무공변하다.
// 하지만 그런 인터페이스 타입의 변수가 한 함수 내에서 하나의 역할만 담당하는 경우가 자주 있다.
fun <T> copyData(source: MutableList<T>, destination: MutableList<T>) {
    for (item in source) destination.add(item)
}

// 이 함수는 컬렉션의 원소를 다른 컬렉션으로 복사한다.
// 두 컬렉션 모두 무공변 타입이지만 원본에서는 읽기만 하고 대상에는 쓰기만 한다. 이 경우에는 두 컬렉션의 원소 타입이 일치할 필요가 없다.
// 위 함수가 여러 리스트 타입에 대해 작동하게 만들려면 두 번째 제네릭 파라미터를 도입핧 수도 있다.
// 이때 원본의 원소 타입은 대상 원소의 하위 타입이어야 함
fun <T : R, R> copyDataWithTwoTypeParameter(source: MutableList<T>, destination: MutableList<R>) {
    for (item in source) destination.add(item)
}

// 하지만 코틀린은 위와 같은 방법 대신에 더 간결한 표현법을 제공한다.
// 함수 구현이 사용하는 타입 파라미터의 위치에 따라서 함수 정의 시 타입 파라미터에 변성 변경자를 추가할 수 있다.
fun <T> copyDataWithOut(source: MutableList<out T>, destination: MutableList<T>) { // MutableList<? extends T>
    for (item in source) destination.add(item)
}
// 타입 선언에서 타입 파라미터를 사용하는 위치라면 어디에나 변성 변경자를 명시할 수 있다.
// 이때 타입 프로젝션이 일어난다. 따라서 source를 일반적인 MutableList가 아닌 MutableList를 프로젝션한 타입으로 만든다.
// 이 경우 copyDataWithVariance는 MutableList에서 T를 아웃 위치로 한 메서드만 사용할 수 있게 된다.
// 컴파일러는 타입 파라미터를 in 위치로 사용하지 못하게 만든다.

// 마찬가지로 타입 파라미터가 in 위치에 쓰이는 값이라고 표시할 수 있다.
fun <T> copyDataWithIn(source: List<T>, destination: MutableList<in T>) { // MutableList<? super T>
    for (item in source) destination.add(item)
}
