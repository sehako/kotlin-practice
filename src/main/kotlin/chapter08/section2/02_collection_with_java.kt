package chapter08.section2

import java.io.File

// 자바 메서드를 호출하되 컬렉션을 인자로 넘겨야 한다면 따로 변환하거나 복사하는 등의 추가 작업 없이 직접 컬렉션을 넘기면 된다.
// java.util.Collection을 파라미터로 받는 자바 메서드가 있다면 Collection이나 MutableCollection 값을 인자로 넘길 수 있다.
// 이런 성질이 컬렉션의 변경 가능성에 대한 문제를 야기시킨다. 자바는 컬렉션의 읽기 전용 여부를 구분하지 않기 때문이다.
// 따라서 코틀린에서 Collection으로 선언했어도 자바 코드에서는 내용을 변경할 수 있다.

fun printInUppercase() {
    val list = listOf("a", "b", "c")
    println(CollectionUtils.uppercaseAll(list)) // [A, B, C]
    println(list) // [A, B, C]
}
// 마찬가지로 null이 될 수 없는 원소만 담을 수 있는 컬렉션을 넘겼는데 자바 코드에서 null을 삽입할 수도 있다.
// 따라서 컬렉션을 자바 코드에 넘길 때에는 해당 코드가 컬렉션을 변경하는지에 대한 여부에 따라서 올바른 파라미터 타입을 사용해야 한다.

// 자바에서 정의한 타입은 코틀린에서는 플랫폼 타입으로 본다. 이는 자바에서 선언한 컬렉션 역시 마찬가지이다.
// 플랫폼 타입 컬렉션은 기본적으로 변경 가능성에 대해 알 수 없다. 따라서 코틀린 코드는 읽기 전용과 변경 가능 컬렉션 두 타입으로 다룰 수 있다.
// 일반적으로는 잘 작동하지만, 컬렉션 타입이 시그니처에 존재하는 메서드 구현을 오버라이드 하는 경우 문제가 발생한다.
// 이런 경우 오버라이드 하려는 자바 컬렉션 타입을 어떤 코틀린 타입으로 표현할 지 결정해야 한다.
/*
다음과 같은 기준에 따라 결정할 수 있다.
- 컬렉션이 null이 될 수 있는가?
- 컬렉션의 원소가 null이 될 수 있는가?
- 메서드가 컬렉션을 변경할 수 있는가?
 */
// FileContentProcessor를 구현하는 예시를 보자.
/*
- 일부 파일이 이진 파일이고, 내용은 텍스트로 표현할 수 없는 경우가 있기에 리스트는 null이 될 수 있다.
- 파일의 각 줄은 null일 수 없으므로 리스트의 원소는 null이 될 수 없다.
- 리스트는 파일의 내용을 표현하며 그 내용을 바꿀 이유가 없으므로 읽기 전용이다.
위 고려사항에 따라 코틀린으로 구현한 코드는 다음과 같다.
 */
class FileIndexer : FileContentProcessor {
    override fun processContents(
        path: File?,
        binaryContents: ByteArray?,
        textContents: List<String>?
    ) {

    }
}

// 마찬가지로 DataParser를 구현한다고 가정해보자.
/*
- 호출하는 쪽에서 항상 오류 메시지를 받아야 하므로 List<String>은 null이 될 수 없다.
- 출력 리스트의 모든 원소마다 오류가 발생하는 것은 아니기 때문에 errors의 원소는 null이 될 수 있다.
- 구현 코드에서 원소를 추가할 수 있어야 하므로 List<String>은 변경 가능해야 한다.
위 고려사항에 따른 코틀린 구현은 다음과 같다.
 */

class PersonParser : DataParser<String> {
    override fun parseData(
        input: String,
        output: MutableList<String>,
        errors: List<String?>
    ) {
    }
}

// 이런 선택을 올바르게 하기 위해서는 자바 인터페이스나 클래스가 어떤 맥락에서 사용되는지 알아야 한다.
