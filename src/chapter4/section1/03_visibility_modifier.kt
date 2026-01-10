package chapter4.section1

// 코틀린은 public, protected, private 변경자를 제공하며, 이들은 자바와 비슷한 기능을 함
// 다만 자바와 다르게 protected 가시성이 같은 패키지가 아닌 하위 클래스에서만 접근이 가능하며,
// 또한 패키지 전용 가시성인 기본 가시성이 없다는 차이점도 있다.
// 추가로, 모듈(module)에만 한정된 가시성을 위해 코틀린은 internal이라는 가시성을 제공
// 코틀린은 최상위 선언에 대해 private 가시성을 허용

/*
변경자              클래스 멤버                                           최상위 선언
----------------------------------------------------------------------------------------------------
public(기본 가시성)  모든 곳에서 볼 수 있다.                              모든 곳에서 볼 수 있다.
internal            같은 모듈 안에서만 볼 수 있다.                        같은 모듈 안에서만 볼 수 있다.
protected           하위 클래스 안에서만 볼 수 있다(최상위 선언에 적용할 수 없음).   -
private             같은 클래스 안에서만 볼 수 있다.                      같은 파일 안에서만 볼 수 있다.
 */

// 코틀린 가시성 법칙: 더 넓은 가시성(public)은 더 좁은 가시성(internal/private)의 타입을 노출할 수 없다.
// 가시성 규칙 위반 예시
internal open class TalkativeButton {
    private fun yell() = println("Hey!")
    protected fun whisper() = println("Let's talk!")
}

/*
fun TalkativeButton.giveSpeech() { // public 멤버가 internal 수신 타입인 TalkativeButton을 노출
    yell() // yell은 TalkativeButton의 private 메서드
    whisper() // protected 멤버
}
 */

/*
코틀린 가시성 변경자와 자바 컴파일
코틀린읜 public, protected, private 변경자는 컴파일되어도 유지된다.
유일한 경우는 private 클래스로, 자바에서는 클래스가 private이 될 수 없기 때문이다.
따라서 내부적으로 코틀린은 private 클래스를 패키지 전용 클래스로 컴파일한다.

그리고 internal 변경자는 바이트 코드상에서 public이 된다. 때문에 자바에서 접근이 가능하지만
internal 멤버의 이름을 보기 나쁘게(mangle) 만들기 때문에 모듈 외부에서 사용하는 일을 방지한다.
 */
