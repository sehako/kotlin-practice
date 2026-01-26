package part02.chapter09.section5

// Person 클래스가 자신이 작성한 이메일 리스트를 데이터베이스로부터 조회해 제공한다고 했을 때,
// 이메일 프로퍼티의 값을 최초로 사용할 때 단 한 번만 이메일을 데이터베이스에서 가져온다고 가정해보자.

class Email

fun loadEmails(person: LazyPerson): List<Email> {
    println("Loading emails for ${person.name}")
    return listOf()
}

class LazyPerson(val name: String) {
    // 데이터 저장 및 emails의 위임 객체 역할을 하는 프로퍼티
    private var _emails: List<Email>? = null

    // _emails 프로퍼티에 대한 읽기 연산을 제공
    // 최초 접근 시 이메일을 가져오고 저장해둔 데이터가 있으면 데이터를 반환
    val emails: List<Email>
        get() {
            if (_emails == null) {
                _emails = loadEmails(this)
            }

            return _emails!!
        }
}

fun lazyInitialization() {
    val person = LazyPerson("Alice")
    person.emails // Loading emails for Alice
    person.emails // 콘솔 출력 없음
}

// 하지만 이 코드는 단점이 존재한다. 우선 지연 초기화 해야 하는 프로퍼티가 많아지면 코드가 복잡해지고,
// 구현이 스레드 안전하지 않다. 만약 여러 스레드에서 동시에 이 값에 접근했을 때 한 번만 초기화된다는 보장이 없는 것이다.

// 코틀린은 위임 프로퍼티를 활용한 더 나은 해법을 제공한다.
fun loadEmails(person: LazyInitPerson): List<Email> {
    println("Loading emails for ${person.name}")
    return listOf()
}

// 이를 사용하면 데이터를 저장할 때 쓰이는 뒷받침하는 프로퍼티와
// 값이 오직 한 번만 초기화되는 것을 보장하는 getter 로직을 함께 캡슐화한다.
class LazyInitPerson(val name: String) {
    // lazy 함수는 getValue 메서드가 들어있는 객체를 반환한다.
    // lazy 함수의 인자는 값을 초기화할 때 호출할 람다식이다. 이는 기본적으로 스레드 안전하다.
    // 심지어 필요하면 동기화에 사용할 락을 lazy 함수에 전달할 수도 있고,
    // 다중 스레드 환경에서 사용하지 않을 프로퍼티를 위해 lazy 함수가 동기화를 생략하게 할 수도 있다.
    val emails by lazy { loadEmails(this) }
}