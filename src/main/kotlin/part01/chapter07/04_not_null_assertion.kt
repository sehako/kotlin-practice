package part01.chapter07

// null 아님 단언은 코틀린에서 null이 될 수 있는 타입의 값을 다룰 때 사용할 수 있는 도구이다.
// 느낌표를 두 개 명시(!!)하면 어떤 값이든 null이 아닌 타입으로 강제로 바꿀 수 있다.
// 실제 null에 대해 !!를 적용하면 NPE가 발생하기도 한다.

fun ignoreNulls(str: String?) {
    val strNotNull = str!! // <- null을 전달할 경우 이 지점에서 예외가 발생한다.
    println(strNotNull.length)
}

// 이때 예외가 발생하면 단언문이 위치한 곳을 가리킨다.
// 즉, 이 null 아님 단언은 이 값이 null이 아니며, 예외가 발생해도 감수하겠다고 컴파일러에게 알리는 역할이다.
// 때때로 이러한 단언문이 유용한 경우가 있다.
// 코틀린은 어떤 함수가 값의 null 여부를 이미 검사했지만 해당 함수에서 호출된 다른 함수에서 해당 값이 안전한 지 알 수 없다.
// 이런 경우 호출된 함수가 언제나 다른 함수에서 null이 아닌 값을 전달받는 것이 분명하다면 null 아님 단언문을 사용할 수 있다.
class SelectableTextList(
    val contents: List<String>,
    var selectedIndex: Int? = null
)

class CopyRowAction(val list: SelectableTextList) {
    // 이 메서드가 true인 경우에만 executeCopyRow()가 호출된다.
    fun isActionEnabled(): Boolean =
        list.selectedIndex != null

    fun executeCopyRow() {
        val index = list.selectedIndex!!
        val value = list.contents[index]
    }
}

// 이런 경우 !!를 사용하지 않으면 executeCopyRow()에서 다시 null 여부를 확인해야 한다.
// 주의점으로 !!를 null에 대해 사용해서 발생하는 예외의 스택 트레이스에는 어떤 파일의 몇 번째 줄인지에 대한 정보는 있지만,
// 어떤 식에서 예외가 발생했는지에 대한 정보는 들어있지 않다.
// 따라서 어떤 값이 null이었는지 확실히 하기 위해 여러 !! 단언문을 한 줄에 함께 쓰는 일을 피하는 것이 좋다.
// person.company!!.address!!.country <- 이런 식으로 작성하면 안된다는 의미

