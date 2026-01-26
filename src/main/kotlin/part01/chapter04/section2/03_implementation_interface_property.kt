package part01.chapter04.section2

// 코틀린에서는 인터페이스에 추상 프로퍼티 선언을 넣을 수 있다.
interface BaseUser {
    // 인터페이스를 구현하는 클래스가 nickname의 값을 얻을 수 있는 방법을 제공해야 한다는 뜻
    val nickname: String
}

// 인터페이스에 있는 프로퍼티 선언에는 뒷받침하는 필드나 getter 등의 정보가 들어있지 않다. (아무 상태도 포함할 수 없으므로)
// 따라서 상태를 저장할 필요가 있다면 인터페이스를 구현한 하위 클래스에서 상태 저장을 위한 프로퍼티 등을 만들어야 한다.

// 위 인터페이스를 다음 3개의 방법으로 구현할 수 있다.
class PrivateUser(override val nickname: String) : BaseUser // 주 생성자에서 프로퍼티 구현

class SubscribingUser(val email: String) : BaseUser {
    override val nickname: String
        get() = email.substringBefore('@') // 커스텀 getter
}

class SocialUser(val id: Int) : BaseUser {
    override val nickname: String = getSocialName(id) // 프로퍼티 초기화 식
}

// 여기서 SubscribingUser와 SocialUser는 비슷해 보이지만
// SubscribingUser는 nickname 조회 시 커스텀 getter가 호출되고,
// SocialUser의 nickname 프로퍼티 구현은 객체 초기화 시 단 한 번 만 호출된다.

fun getSocialName(id: Int) = "socialUser_$id"

// 인터페이스에 추상 프로퍼티 뿐 아니라 getter와 setter가 있는 프로퍼티를 선언할 수도 있다.
// 추상 프로퍼티인 email과 커스텀 getter가 있는 nickname 프로퍼티 선언 예시
// 마찬가지로 인터페이스에 명시된 getter와 setter는 뒷받침하는 필드를 참조할 수 없다.
interface EmailUser {
    val email: String // 반드시 오버라이드 해야 함
    val nickname: String // 반면 nickname 필드는 상속할 수 있음
        get() = email.substringBefore('@')
}

// EmailUser 구현 클래스 예시
class EmailUserImpl(override val email: String) : EmailUser {
    // email은 추상 프로퍼티이므로 반드시 오버라이드 해서 값을 저장할 수 있도록 해야 한다.

    // 2. nickname은 이미 인터페이스에 계산 방법(get())이 적혀 있으므로
    //    따로 작성하지 않아도 자동으로 상속되어 사용할 수 있다.
}

// 책에서 위 부분이 잘 이해가 되지 않아 좀 더 찾아봤는데, 정확히는 nickname 추상 프로퍼티의 getter가
// 외부의 추상 프로퍼티로부터 값을 새롭게 만들 수 있기 때문에 EmailUserImpl이 가능하다는 것이다.
// 즉 해당 변수가 자기 자신이 저장될 필요가 있느냐에 따라서 추상 프로퍼티의 오버라이드 여부가 결정된다고 이해하면 될 것 같다.
// 따라서 만약에 nickname 추상 프로퍼티가 var 이라면 setter가 필요하고, 이 경우에는 nickname을 저장해야 할 필요가 있으므로
// 자연스럽게 nickname 추상 프로퍼티도 오버라이드 해야 한다.

/*
함수 대신 프로퍼티를 사용하는 경우

다음과 같은 특징을 만족한다면 함수 대신 프로퍼티를 사용할 수 있다.
- 예외를 던지지 않는다.
- 계산 비용이 적게 든다. (최초 실행 후 결과를 캐시해 사용할 수 있다.)
- 객체 상태가 바뀌지 않으면 여러 번 호출해도 항상 같은 결과를 돌려준다.

이 경우가 아니라면 함수를 사용하자.
 */
