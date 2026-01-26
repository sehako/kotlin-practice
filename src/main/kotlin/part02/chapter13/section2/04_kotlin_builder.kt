package part02.chapter13.section2

import kotlinx.html.*
import kotlinx.html.stream.createHTML

// 코틀린 내부 DSL을 사용하면 일반 코드와 마찬가지로 반복되는 내부 DSL 코드 조각을 새 함수로 묶어 재사용할 수 있다.
// 전체 목차를 HTML로 작성하는 다음 코드를 보도록 하자.
fun buildList() = createHTML().body {
    ul {
        li { a("# 1") { +"The Three-Body Problem" } }
        li { a("# 2") { +"The Dark Forest" } }
        li { a("#3") { +"Death's End" } }
    }
    h2 { id = "1"; +"The Three-Body Problem" }
    p { +"The first book tackles..." }
    h2 { id = "2"; +"The Dark Forest" }
    p { +"The second book starts with..." }
    h2 { id = "3"; +"Death's End" }
    p { +"The third book contains..." }
}

// 이를 목차를 만드는 로직과 요약을 만드는 로직을 별도의 함수로 나누어 보도록 하자.

// 우선 LISTWITHTOC를 만들어보자.
@HtmlTagMarker
class LISTWITHTOC {
    val entries = mutableListOf<Pair<String, String>>()
    fun item(headline: String, body: String) {
        entries += headline to body
    }
}

// 이를 호출할 수 있는 방법을 제공해보도록 하자. 단순화를 위해 목차를 직접 HTML body 아래에 넣어보자.
// 이 경우 listWithToc를 BODY의 확장 함수로 만들 수 있다.
fun BODY.listWithToc(block: LISTWITHTOC.() -> Unit) {
    val listWithToc = LISTWITHTOC()
    // 파라미터로 받은 수신 객체 람다 지정 함수 호출
    // 여기서는 item()을 호출하는 부분
    listWithToc.block()
    // BODY의 확장 함수이기 때문에 ul, li, h2, p 등을 사용해 항목을 외부 태그 안에 넣을 수 있다.
    ul {
        for ((index, entry) in listWithToc.entries.withIndex()) {
            li { a("#${index + 1}") { +entry.first } }
        }
    }

    for ((index, entry) in listWithToc.entries.withIndex()) {
        h2 { id = "${index + 1}"; +entry.first }
        p { +entry.second }
    }
}

// 이제 다음과 같이 호출할 수 있다.
fun buildBookList() = createHTML().body {
    listWithToc {
        item("The Three-Body Problem", "The first book tackles...")
        item("The Dark Forest", "The second book starts with...")
        item("Death's End", "The third book contains...")
    }
}
