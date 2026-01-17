package chapter11.section3

// 반공변 클래스의 하위 타입 관계는 그 클래스의 타입 파라미터의 상하위 타입 관계와 반대다.
// Comparator 인터페이스를 살펴보자.
/*
interface Comparator<in T> {
    fun compare(o1: T, o2: T): Int {} <- T를 in 위치에 사용한다.
}
 */
// 이 인터페이스의 메서드는 T 타입의 값을 소비하기만 한다. 이는 T가 in 위치에만 쓰인다는 것이다.
// 여전히 어떤 타입에 대해 Comparator를 구현하면 그 타입의 하위 타입은 값을 비교할 수 있다.
sealed class Fruit {
    abstract val weight: Int
}

data class Apple(override val weight: Int) : Fruit()
data class Orange(override val weight: Int) : Fruit()

// 이런 상황에서 Comparator<Fruit>를 만들면 어떤 구체적 과일 타입이어도 비교가 가능하다.
fun compareFruitWeight() {
    val weightComparator = Comparator<Fruit> { o1, o2 -> o1.weight.compareTo(o2.weight) }
    val fruits: List<Fruit> = listOf(Apple(1), Orange(2))
    fruits.sortedWith(weightComparator)
}

// 이렇게 어떤 타입의 객체를 Comparator로 비교해야 한다면 그 타입이나 그 타입의 조상 타입을 비교할 수 있는 Comparator를 사용할 수 있다.
// 이는 Comparator<Any>가 Comparator<String>의 하위 타입이 된다는 의미이다. 그런데 Any는 String의 상위 타입이다.
// 즉, 반공변성은 어떤 클래스에 대해 타입 B가 A의 하위 타입일 때 Consumer<A>가 Consumer<B>의 하위 타입인 관계가 성립하는 것이다.

// in 이라는 키워드는 그 키워드가 붙은 타입이 이 클래스의 메서드 안으로 전달되어 메서드에 의해 소비된다는 뜻이다.
// 공변, 반공변, 무공변을 표로 요약하면 다음과 같다.
/*
| 구분 | 공변성 | 반공변성 | 무공변성 |
| 구문 예시 | Producer<out T> | Consumer<in T> | MutableList<T> |
| 정의 | 타입 인자의 하위 타입 관계가 제네릭 타입에서도 유지된다. | 타입 인자의 하위 타입 관계가 제네릭 타입에서 뒤집힌다. | 하위 타입 관계가 성립하지 않는다. |
| 관계 예시 | Producer<Cat>은 Producer<Animal>의 하위 타입이다. | Consumer<Animal>은 Consumer<Cat>의 하위 타입이다. | |
| 사용 위치 | T를 아웃 위치에서만 사용할 수 있다. | T를 인 위치에서만 사용할 수 있다. | T를 아무 위치에서나 사용할 수 있다. |
 */

// 클래스나 인터페이스가 어떤 타입 파라미터에 대해서는 공변적이면서 다른 타입 파라미터에 대해서는 반공변적일 수 있다.
/*
public interface Function1<in P1, out R> : Function<R> {
    public operator fun invoke(p1: P1): R
}
 */
// 코틀린 표기에서 (P) -> R은 Function1<P, R>을 더 알아보기 쉽게 적은 것일 뿐이다.
// 공변, 반공변을 생각하면 다음 코드는 적절한 코드라는 것을 알 수 있다.
open class Life
class LifeCat : Life()


fun enumerateCats(f: (LifeCat) -> Number) {}
fun Life.getIndex(): Int = TODO()

fun enumerateExample() {
    enumerateCats(Life::getIndex)
}

