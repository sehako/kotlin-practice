package chapter4.section5

// 함수 시그니처가 단순한 타입인 경우에는 함수가 원치 않은 값이 전달될 수 있다.
fun addExpense(won: Int) {} // 원화를 기대했지만 달러가 들어와도 함수는 잘 작동된다.

// 이를 방지하기 위해서 VO를 활용하기도 한다.
class Won(val amount: Int)

fun addExpense(amount: Won) {}
// 하지만 위와 같은 방법은 가비지 컬렉터에 의해 제거되어야 하는 객체를 수 없이 만든다는 단점이 있다.

// 이럴 때 인라인 클래스가 도움이 된다. class 앞에 value라는 키워드를 붙이고 @JvmInline을 선언한다.
// 이는 코드상으로는 객체처럼 보이지만, 컴파일 후에는 원시 타입으로 변환되기 때문에 성능상의 이점을 가져다준다.
@JvmInline
value class Cost(val amount: Int)

// 이때 클래스가 프로퍼티를 하나만 가져야 하며, 그 프로퍼티는 주 생성자에서 초기화되어야 한다는 조건이 있다.
// 클래스 계층에 참여하지 않으므로 다른 클래스를 상속할 수 없으며, 다른 클래스가 상속할 수 없다.
// 하지만 인터페이스를 상속하거나, 메서드를 정의하거나, 계산된 프로퍼티를 제공할 수는 있다.
interface PrettyPrintable {
    fun prettyPrint(): String
}

@JvmInline
value class UsdCent(val amount: Int) : PrettyPrintable {
    val salesTax
        get() = amount * 0.06

    override fun prettyPrint(): String = "$amount"
}

/*
인라인 클래스는 코틀린 컴파일러의 특징이지만 프로젝트 발할라를 통해 JVM 자체에서 인라인 클래스를 지원하는 것이 목표이다.
이것이 바로 @JvmInline을 명시해야 하는 이유기도 하다. (코틀린 특화 기능이기 때문)
나중에는 어노테이션이 없어도 사용할 수 있을 것이다.
 */
