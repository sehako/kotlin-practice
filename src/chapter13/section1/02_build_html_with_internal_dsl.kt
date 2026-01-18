package chapter13.section1

import kotlinx.html.stream.createHTML
import kotlinx.html.table
import kotlinx.html.td
import kotlinx.html.tr

// HTML을 생성하는 DSL인 kotlinx.html 라이브러리를 살펴보자.

fun createSimpleTable() = createHTML().table {
    tr {
        td { +"cell" }
    }
}
// 위 처럼 DSL을 구성하면 다음과 같이 HTML이 만들어진다.
/*
<table>
  <tr>
    <td>cell</td>
  </tr>
</table>
 */

// 직접 HTML 텍스트를 작성하는 것 보다 코틀린 코드로 HTML을 구성하면 다음과 같은 이점이 있다.
// 첫 번째로 코틀린 버전은 타입 안정성을 보장한다. 문법이 잘못되면 컴파일 에러가 발생한다.
// 두 번째로 코틀린 코드이기 때문에 내부에서 원하는대로 코틀린 코드를 원하는 대로 사용할 수 있다는 것이다.

fun createDynamicTable() = createHTML().table {
    val numbers = mapOf(1 to "one", 2 to "two")
    for ((num, string) in numbers) {
        tr {
            td { +"$num" }
            td { +string }
        }
    }
}

