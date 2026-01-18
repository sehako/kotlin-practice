package chapter04.section3

// 하위 클래스가 상위 클래스의 메서드 중 일부를 오버라이드하면 하위 클래스는 상위 클래스의 세부 구현 사항에 의존하게 된다.
// 그러다가 상위 클래스의 구현이 바뀌거나 상위 클래스에 새로운 메서드가 추가되면 하위 클래스가 정상적으로 작동하지 못하게 될 수 있다.
// 이것이 바로 코틀린이 기본적으로 클래슬르 final로 취급한 이유이기도 하다.
// 때때로 이런 final 클래스에게 새로운 동작을 추가해야 할 때 데코레이터 패턴을 사용한다.
// 하지만 이 접근의 단점은 준비 코드(보일러 플레이트)가 너무 많이 필요하다는 것이다.
// 다음은 Collection 클래스에 데코레이터 패턴을 적용한 예시다.
class DelegatingCollection<T> : Collection<T> {
    private val c: Collection<T> = arrayListOf<T>()

    override val size: Int get() = c.size
    override fun contains(element: T): Boolean = c.contains(element)
    override fun containsAll(elements: Collection<T>): Boolean = c.containsAll(elements)
    override fun isEmpty(): Boolean = c.isEmpty()
    override fun iterator(): Iterator<T> = c.iterator()
}

// 코틀린은 이런 위임은 언어 차원에서 일급 기능으로 지원한다.
// 인터페이스를 구현할 때 by 키워드를 통해 그 인터페이스에 대한 구현을 다른 객체에 위임한다는 것을 명시할 수 있는 것이다.
class DelegatingCollection2<T>(innerList: Collection<T> = mutableListOf()) : Collection<T> by innerList
// 자동 생성한 코드의 구현은 DelegatingCollection에 존재하던 구현과 비슷하다.
// 이후에 변경하고 싶은 메서드만 오버라이드 하면 된다. 즉, 기존 클래스의 메서드에 위임하는 기본 구현 메서드는 오버라이드 할 필요가 없다.

// 이를 활용해서 원소의 추가 시도 횟수를 기록하는 컬렉션을 구현하면 다음과 같다.
class CountingSet<T>(
    private val innerSet: MutableCollection<T> = hashSetOf()
) : MutableCollection<T> by innerSet { // MutableCollection의 구현을 innerSet에게 위임
    var objectsAdded = 0
        private set

    override fun add(element: T): Boolean {
        objectsAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectsAdded += elements.size
        return innerSet.addAll(elements)
    }
}
// 원소를 추가하는 메서드 이외에 나머지 메서드는 innerSet에 위임한 것을 볼 수 있다.
// 이때 CountingSet에 MutableCollection의 구현 방식에 대한 의존관계가 생기지 않는 것이 중요하다.
// 정리하자면 코틀린의 위임 기능은 인터페이스의 모든 메서드를 직접 구현할 필요 없이 기본 동작은 인터페이스를 구현한 객체에 맡기고,
// 필요한 메서드만 선택적으로 재정의(Override)할 수 있게 해주는 기능이다.
