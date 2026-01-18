package chapter06.section2

// map이나 filter를 연쇄적으로 호출하면 매 단계마다 연산 결과 컬렉션을 즉시 생성한다.
// 시퀀스는 자바 8의 Stream API와 비슷하게 중간 임시 컬렉션을 사용하지 않고 컬렉션 연산을 연쇄적으로 처리할 수 있다.
fun sequenceExample() {
    val list = listOf(1, 2, 3, 4)
    list
        .asSequence()
        .map { it * it }
        .filter { it % 2 == 0 }
        .toList()
}

// 시퀀스는 Sequence라는 인터페이스에서 시작한다.
/*
public interface Sequence<out T> {
    public operator fun iterator(): Iterator<T>
}
 */
// iterator()를 통해 한 번에 단 하나 씩 열거될 수 있는 원소의 연속을 표현한다.
// 이 인터페이스를 구현한 연산이 필요할 때 게으르게 계산된다.
// 따라서 중간 처리 결과를 저장할 컬렉션 생성 없이 연산을 연쇄적으로 적용한다.
// 따라서 크기가 큰 컬렉션에 대한 연쇅적인 연산은 항상 시퀀스를 먼저 고려해야 한다.

// 시퀀스의 연산은 다른 시퀀스를 반환하는 중간 연산과 결과를 반환하는 최종 연산이 있다.
fun sequenceOperationExample() {
    val list = listOf(1, 2, 3, 4)

    // 중간 연산은 항상 시퀀스를 반환한다.
    // 참고로 현재는 kotlin.sequences.FilteringSequence 구현체를 반환한 상태이다.
    val filterSequence: Sequence<Int> = list.asSequence()
        .map { it * it }
        .filter { it % 2 == 0 }

    // 위 코드는 아무런 결과가 출력되지 않는다. 시퀀스는 항상 최종 연산이 호출될 때 위 연산이 실행된다.
    filterSequence.toList()

    // 이때 주의해야 할 점은 컬렉션 함수처럼 각 단계마다 모든 원소가 처리 된 다음에 다음 단계로 가는 것이 아닌,
    // 각 원소에 대해 순차적으로 map과 filter를 거치면서 처리되고, 두 번째 원소가 처리되는 구조가 된다.
    // 따라서 처리 도중에 결과를 얻을 수 있으면 그 연산은 끝날 수 있다.
    list.asSequence()
        .map { it * it }
        .find { it > 3 } // 원소 2에서 find가 되었으므로 시퀀스가 종료된다.

    // 따라서 부적절한 원소를 먼저 제거하고, 그 다음 적절한 연산을 처리하는 것이 성능을 개선할 수 있다는 것을 알 수 있다.
    list.asSequence()
        .filter { it > 2 } // 3 이상인 원소를 먼저 필터링
        .map { it * it } // 이후 변환
}
