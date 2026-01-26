package part02.chapter13.section2

import kotlinx.html.*
import kotlinx.html.stream.createHTML

// 어떤 것을 만들기 위한 DSL을 보통 빌더라고 부른다. HTML을 DSL로 만든다면 이는 곧 HTML 빌더라고 부른다.
// 앞서 HTML 테이블을 HTML 빌더로 만든 코틀린 코드를 살펴보자.
fun createSimpleTable() = createHTML().table {
    tr {
        td { +"cell" }
    }
}

// 이는 고차 함수로 수신 객체 지정 람다를 인자로 받아서 HTML을 빌드하는 일반적인 코틀린 코드이다.
// 여기서 각 수신 객체 지정 람다가 이름 결정 규칙을 바꾼다는 것을 보도록 하자.
// 각 람다는 자신의 본문에서 호출될 수 있는 함수들을 새로 추가한다.
// table 함수는 tr 함수를 사용해 tr 태그를 만들고, tr은 td 함수를 사용해 td 태그를 만든다.
// 이런 설계로 인해 실제 HTML 문법을 따르는 코드만 작성할 수 있게 되는 것이다.
// 각 블록의 이름 결정 규칙은 각 람다의 수신 객체에 의해 결정된다. table에 전달된 수신 객체는 TABLE 타입을,
// 마찬가지로 tr은 TR, td는 TD 객체에 대한 확장 함수 타입의 람다를 받는다.
/*
@HtmlTagMarker
open class Tag

class TABLE : Tag {
    fun tr(init : TR.() -> Unit) // TR 타입을 수신 객체로 받는 람다를 인자로 받는다.
}

class TR : Tag {
    fun td(init : TD.() -> Unit) // TD 타입을 수신 객체로 받는 람다를 인자로 받는다.
}
// 생성 코드에 명시적으로 나타나면 안되는 유틸리티 클래스이므로 이름을 모두 대문자로 만든 것도 볼 수 있다.
 */
// tr과 td 함수의 init 파라미터 타입은 모두 확장 함수 타입이다.
// 이는 각 메서드에 전달한 람다의 수신 객체 타입인 TR과 TD를 지정한다.
// 이를 this로 명시하면 다음과 같다.
fun createSimpleTableWithThis() = createHTML().table {
    this@table.tr {
        (this@tr).td { +"cell" }
    }
}

// 수신 객체 지정 람다가 다른 수신 객체 지정 람다에 전달되면 안쪽에서 바깥쪽 람다에 정의된 수신 객체를 사용할 수 있다.
// 이를 막기 위해서 코틀린은 @DslMarker 메타어노테이션을 명시해 내포된 람다에서 외부 람다의 수신 객체에 접근하지 못하게 제한할 수 있다.
// kotlin-html에서는 HtmlTagMarker에 대해 @DslMarker가 적용되어 있다.
/*
@DslMarker
annotation class HtmlTagMarker
 */
// 따라서 다음과 같이 사용하면 컴파일 에러가 발생한다.
fun createLink() = createHTML().body {
    a {
        img {
//            href = "kotlinlang.org"  <- 컴파일 에러 발생
            // 다음과 같이 수신 객체 지정을 this@a(바깥쪽의 A 태그의 인스턴스 객체)로 명시해야 한다.
            (this@a).href = "kotlinlang.org"
        }
    }
}

