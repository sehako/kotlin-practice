package chapter13.section2

// 수신 객체 지정 람다에 대해 자세히 알아보기 위해서 HTML 빌더 라이브러리를 구현해서 테이블을 HTML로 생성하는 기능을 만들어보자.
// 이미 존재하는 객체인 TABLE, TR, TD를 활용할 것이다.

// 1. DSL 범위 제어를 위한 어노테이션 (필수는 아니지만 중첩 구조에서 중요함)
@DslMarker
annotation class HtmlTagMarker2

// 2. 모든 태그의 부모 클래스
@HtmlTagMarker2
open class Tag2(val name: String) {
    // 자식 태그들을 저장할 리스트
    private val children = mutableListOf<Tag2>()

    // 자식을 초기화하고 리스트에 추가하는 공통 로직
    // <T : Tag> : 자식은 반드시 Tag를 상속받아야 함
    protected fun <T : Tag2> doInit(child: T, init: T.() -> Unit) {
        child.init()      // 1. 자식 태그의 람다(내용 구성)를 실행
        children.add(child) // 2. 구성된 자식 태그를 내 리스트에 추가
    }

    // 재귀적으로 자신과 자식들을 문자열로 변환
    override fun toString(): String {
        return "<$name>${children.joinToString("")}</$name>"
    }
}

// 3. 실제 태그 클래스 정의 (Tag 상속)
class TABLE2 : Tag2("table") {
    // TABLE 안에는 TR만 들어갈 수 있다고 규칙을 정함
    fun tr(init: TR2.() -> Unit) = doInit(TR2(), init)
}

class TR2 : Tag2("tr") {
    // TR 안에는 TD만 들어갈 수 있다고 규칙을 정함
    fun td(init: TD2.() -> Unit) = doInit(TD2(), init)
}

class TD2 : Tag2("td") // TD는 가장 하위 태그라 추가 메서드 없음

// 4. DSL 진입점 함수
// TABLE 인스턴스를 만들고, apply로 초기화한 뒤 반환
fun table(init: TABLE2.() -> Unit) = TABLE2().apply(init)

// 5. 사용 예시
fun createImplTable() {
    // 이제 이 코드가 동작합니다.
    val html = table {     // TABLE 생성 -> this는 TABLE
        tr {               // TABLE.tr() 호출 -> TR 생성 -> this는 TR
            td {           // TR.td() 호출 -> TD 생성
                // 내용 없음
            }
            td {
                // 두 번째 셀
            }
        }
    }

    println(html)
}