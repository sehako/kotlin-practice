package chapter5.section4

// 수신 객체에 대한 어떤 동작을 수행한 후 수신 객체를 반환한다.
// apply와 비슷하지만 람다 안에서는 수신 객체를 인자로 참조할 수 있다는 차이점이 있다.
// 따라서 파라미터 이름을 부여하거나 기본 이름인 it을 사용해야 한다.
val x: List<Int> = listOf(1, 2, 3).also { println("Also called with $it") }
// also를 통해 코드가 어떤 효과를 추가로 수행하는 의미로 해석될 수 있다.

fun doMoreWithAlso() {
    val fruits = listOf("banana", "avocado", "apple")
    val uppercaseFruits = mutableListOf<String>()
    // 각 이름을 대문자로 바꾼 컬렉션으로 변환
    // 그리고(also) 매핑의 결과를 다른 컬렉션에도 추가
    // 그 후 컬렉션에서 이름이 5글자보다 더 긴 과일만 선택
    // 그리고(also) 그 결과를 출력한 다음 리스트의 순서를 반전
    val reversedLongFruits = fruits
        .map { it.uppercase() }
        .also { uppercaseFruits.addAll(it) }
        .filter { it.length > 5 }
        .also { println(it) }
        .reversed()
}
