package chapter06.section1

// associate (원소를 그룹화하지 않으면서 컬렉션으로부터 Map을 만든다)
fun associateExample() {
    val list = listOf(1, 2, 3, 4)
    list.associate { it to it * it }
    // 위 경우는 컬렉션의 원소와 그 원소의 제곱이므로 컬렉션의 원소와 연관이 있다고 볼 수 있다.
    // 이 경우에는 associateWith와 associateBy를 사용할 수 있다.
    list.associateWith { it * it } // associateWith는 컬렉션의 원래 원소를 키로 사용하고 람다는 그 원소에 대응하는 값을 만든다.
    // {1=1, 2=4, 3=9, 4=16}

    list.associateBy { it * it } // 컬렉션의 원래 원소를 맵의 값으로 하고, 람다가 만들어내는 값을 맵의 키로 사용한다.
    // {1=1, 4=2, 9=3, 16=4}
}

// 가변 컬렉션의 원소 변경
fun mutableCollectionChangeExample() {
    val names = mutableListOf("Alice", "Bob", "Charlie")
    names.replaceAll { it.uppercase() } // [ALICE, BOB, CHARLIE]
    names.fill("(redacted)") // [(redacted), (redacted), (redacted)]
}

// ifEmpty (컬렉션이 비어있을 때 기본값을 생성)
fun ifEmptyExample() {
    val empty = emptyList<String>()
    val full = listOf("apple", "orange", "banana")
    empty.ifEmpty { listOf("no", "values", "here") }
    full.ifEmpty { listOf("no", "values", "here") }
    // empty = [no, values, here]
    // full = [apple, orange, banana]

    // 참고로 문자열을 다룰 때 비어있다와 공백만 있다는 구분될 수 있기 때문에 ifBlank라는 함수를 제공한다.
    val blankName = " "
    val name = "J. Doe"

    blankName.ifEmpty { "(unnamed)" } // "  "
    blankName.ifBlank { "(unnamed)" } // (unnamed)
    name.ifBlank { "(unnamed)" } // J. Doe
}

// 컬렉션 나누기
// 컬렉션의 데이터가 어떤 계열 정보를 표현할 때 데이터를 연속적인 시간의 값들로 처리하고 싶을 경우 사용한다.
fun collectionDivideExample() {
    val temperatures = listOf(27.7, 29.8, 22.0, 35.5, 19.1)
    // 이 리스트의 값으로부터 3일간의 온도 평균을 구하고 싶을 때
    // windowed 함수를 통해 슬라이딩 윈도우를 생성하여 연산을 처리할 수 있다.
    temperatures.windowed(3) { it.average() }

    // 입력 컬렉션에 대해 슬라이딩 윈도우를 실행하는 대신
    // 컬렉션을 어떤 주어진 크기의 서로 겹치지 않는 부분으로 나누고자 할 때는 chunked 함수를 사용할 수 있다.
    temperatures.chunked(3)
    // [[27.7, 29.8, 22.0], [35.5, 19.1]]
}

// zip (두 컬렉션을 합치는 연산_
fun zipExample() {
    val names = listOf("Alice", "Bob", "Charlie")
    val ages = listOf(29, 31, 33, 37, 50)

    // zip을 통한 두 컬렉션응 합치는 방법
    val zip = names.zip(ages)
    // [(Alice, 29), (Bob, 31), (Charlie, 33)]
    // 결과 컬렉션은 컬렉션의 크기가 더 작은 쪽의 기준으로 맞춰진다.

    // 람다로 출력을 변환하여 특정 값이나 객체를 만들 수 있다.
    val zip1 = names.zip(ages) { name, age -> "$name is $age years old" }

    // to 중위 표기법처럼 zip도 중위 표기법을 활용할 수 있다.
    names zip ages // 이 경우에는 람다를 활용할 수 없다.
}

// 내포된 컬렉션의 원소 처리
class Book(val title: String, val authors: List<String>)

fun nestedCollectionExample() {
    val library = listOf(
        Book("Kotlin in Action", listOf("Isakova", "Elizarov", "Aigner", "Jemerov")),
        Book("Atomic Kotlin", listOf("Eckel", "Isakova")),
        Book("The Three-Body Problem", listOf("Liu"))
    )

    // 위 컬렉션에서 라이브러리의 모든 저자를 계산하고 싶을 때 map을 활용할 수 있을 것이다.
    val authors = library.map { it.authors } // [[Isakova, Elizarov, Aigner, Jemerov], [Eckel, Isakova], [Liu]]

    // 하지만 위 코드는 authors가 List<String>이기 때문에 결과 컬렉션은 List<List<String>>이라는 내포된 컬렉션이 된다.
    // flatMap 함수를 사용하면 authors의 집합을 계산하되 추가적인 내포 없이 계산할 수 있다.
    library.flatMap { it.authors } // [Isakova, Elizarov, Aigner, Jemerov, Eckel, Isakova, Liu]

    // 만약 변환할 것이 없고 단지 컬렉션의 컬렉션을 평평하게 만들고자 한다면 flatten을 사용할 수 있다.
    val listOfLists = listOf(listOf(1, 2), listOf(3, 4))
    listOfLists.flatten()
}
