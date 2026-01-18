package chapter06.section1

// 함수형 프로그래밍 스타일은 컬렉션을 다룰 때 여러 장점을 제공한다.
// 대부분의 작업에 대해서 표준 라이브러리가 제공하는 함수와 람다를 인자로 전달하는 방식이다.
// 컬렉션을 다룰 때 사용하게 되는 주요 함수들을 살펴보자.

// filter (Predicate를 바탕으로 컬렉션의 원소를 필터링)
fun filterExample() {
    val list = listOf(1, 2, 3, 4)
    list.filter { it % 2 == 0 } // 짝수만 남기는 filter 연산
    // 필터링을 할 때 인덱스를 기반으로 참고하게 만들 수도 있다.
    list.filterIndexed { index, i -> index % 2 == 0 && i % 2 == 0 }
}

// map (주어진 함수를 컬렉션에 각 원소에 적용하고 그 결과값을 새 컬렉션으로 반환)
fun mapExample() {
    val list = listOf(1, 2, 3, 4)
    val squareList = list.map { it * it } // 모든 원소에 대한 제곱을 수행
    // map 연산도 인덱스를 활용하는 함수가 있다.
    val mapIndexed: List<Pair<Int, Int>> = list.mapIndexed { index, i -> index to i }

    // Map 자료구조에 대해서도 다음과 같은 함수가 존재한다.
    val numbers = mapOf(1 to "one", 2 to "two")
    // Map 자료구조에 존재하는 값에 대한 변환
    numbers.mapValues { it.value.uppercase() }
    // 이처럼 Map 자료구조에서 키와 값을 처리하는 함수가 따로 존재한다.
    // filterKeys, mapKeys (키 대상 연산)
    // filterValues, mapValues (값 대상 연산)
}

// reduce (각 컬렉션에 대한 누적 연산을 수행한다)
fun reduceExample() {
    val list = listOf(1, 2, 3, 4)
    list.reduce { acc, i -> acc * i } // 모든 컬렉션 원소의 곱 연산
    // 모든 단계에 대한 누적 값을 도출하고자 한다면 runningReduce를 활용하면 된다.
    list.runningReduce { acc, i -> acc * i } // 4개 크기의 누적곱 연산 컬렉션 반환
}

// fold (reduce와 비슷하지만 임의의 시작 값을 선택할 수 있다)
fun foldExample() {
    val list = listOf(1, 2, 3, 4)
    list.fold("") { acc, i -> "$acc$i " }
    list.runningFold("") { acc, i -> "$acc$i " } // 4개 크기의 누적 문자열 연산 반환
}

// 조건을 참조해 컬렉션에 어떤 연산을 처리하는 함수
fun conditionExample() {
    val list = listOf(1, 2, 3, 4)
    list.all { it >= 0 } // 모든 원소가 0 이상인지 확인
    // 참고로 all은 빈 컬렉션에 대해서 항상 true를 반환한다 (공허한 참, vacuous truth)
    list.any { it >= 4 } // 어떤 원소가 하나라도 4 이상인지 확인
    list.none { it == 5 } // 5가 존재하지 않는지 확인
    list.count { it >= 2 } // 조건은 만족하는 원소의 개수를 확인
    list.find { it >= 3 } // 조건은 만족하는 첫 번째 원소를 탐색 (firstOrNull), 조건을 만족하는 원소가 없으면 null
    list.partition { it > 2 } // 조건을 만족하는 집단과, 만족하지 않는 집단 두 그룹의 컬렉션으로 분할
    val groupBy = list.groupBy { it } // 컬렉션의 원소를 어떤 특성에 따라 여러 그룹으로 나누는 연산
    // {1=[1], 2=[2], 3=[3], 4=[4]}
    // 컬렉션의 원소를 구분하는 특성이 키, 키에 해당하는 그룹이 값인 맵이 된다.
}
