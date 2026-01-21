package chapter04.section2

/*
OOP에서 일반적으로 생성자를 하나 이상 선언할 수 있다. 코틀린도 동일하다.
하지만 코틀린은 주 생성자와 부 생성자를 구분한다. 그리고 초기화 블록을 통해 초기화 로직을 추가할 수 있다.
 */

// 클래스 이름 뒤에 오는 괄호로 둘러싸인 코드를 주 생성자라고 한다.
class User(val nickname: String)

// 위 코드를 명시적으로 작성하면 다음과 같다고 할 수 있다.
//class Member constructor(_nickname : String) { // 파라미터 하나인 주 생성자
// 그리고 별 다르 어노테이션이나 가시성 변경자가 없다면 아래와 같이 constructor를 생략해도 된다.
class Member(_nickname: String) {
    // 이 경우에는 nickname을 초기화 하는 코드를 프로퍼티 선언에 포함시킬 수 있다.
    // 따라서 아래 초기화 블록을 활용하는 것 보다는 다음 방법을 더 많이 사용한다.
    val nickname = _nickname
//    val nickname : String
//
//    // 초기화 블록 (필요하다면 클래스 안에 여러 초기화 블록을 선언할 수 있다.)
//    init {
//        nickname = _nickname
//    }
}

// 여기에 더해 주 생성자의 파라미터를 가지고 프로퍼티를 초기화한다면
// 주 생성자 파라미터 이름 앞에 val을 추가하여 프로퍼티 정의와 초기화를 간략화 할 수 있는 것이다.
// 그리고 생성자 파라미터에도 기본값을 정의할 수 있다.
// 참고로 모든 파라미터가 기본값이 되면 컴파일러가 파라미터가 없는 생성자를 만들어준다.
class Player(val nickname: String, val level: Int = 1)

// 자바 코드가 코틀린 생성자가 제공하는 디폴트 파라미터 중에 몇 가지만 생략하는 경우에느
// 모든 파라미터에 대해 기본값을 정의한 생성자에 @JvmOverloads 생성자를 지정한다.
class Player2 @JvmOverloads constructor(val nickname: String, level: Int = 1) {}

/*
public class Player2 {

    private final String nickname;

    // 기본 생성자 (모든 파라미터)
    public Player2(String nickname, int level) {
        this.nickname = nickname;
        // level 값은 내부 로직에서 사용
    }

    // 오버로드 생성자 (기본값 적용)
    public Player2(String nickname) {
        this(nickname, 1);
    }
}
 */

// 상위 클래스의 생성자가 인자를 받아야 한다면 클래스의 주 생성자에서 기반 생성자를 호출해야 할 필요가 있다.
open class GoogleUser(val nickname: String)
class YoutubeUser(nickname: String) : GoogleUser(nickname)

// 클래스를 정의할 때 별도 생성자를 정의하지 않으면 컴파일러가 디폴트 생성자를 만들어준다.
open class Button

// 이 클래스를 상속하는 하위 클래스는 반드시 해당 클래스의 생성자를 호출해야 한다.
class RadioButton : Button() // 인터페이스는 생성자가 없기 때문에 이름만 명시한다는 차이점이 있다는 것이 유의해야 한다.

// 생성자에 private을 선언해서 클래스 외부에서 인스턴스화 하지 못하게 만들 수 있다.
class SecretClass private constructor(private val keyValue: String) // 주 생성자가 비공개 됨
// 자바에서는 유틸리티 클래스나 싱글톤 등에 private 생성자를 정의해서 인스턴스화를 막는다.
// 코틀린의 경우 최상위 함수로 유틸리티 클래스를 대체하고, 싱글톤을 사용하고 싶으면 object를 선언하면 된다.

fun main() {
    val user = User("Kotlin")
    println(user.nickname)

    // 그리고 함수 호출과 마찬가지로 생성자 인자에도 이름을 부여할 수 있다.
    val player = Player(nickname = "sehako", level = 100)
}
