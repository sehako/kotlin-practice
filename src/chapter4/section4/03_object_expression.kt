package chapter4.section4

// 익명 객체를 정의할 때도 object 키워드를 사용한다. (자바의 익명 내부 클래스를 대신)

interface MouseListener {
    fun onEnter()
    fun onClick()
}

class AnonymousButton(private val listener: MouseListener)

// 여러 인터페이스를 객체 식으로 구현한 예시
interface Flyer {
    fun fly()
}

interface Swimmer {
    fun swim()
}

fun main() {
    // 객체 식을 사용하면 임의의 MouseListener 구현을 생성해서 Button 생성자에 넘길 수 있다.
    // 객체 식은 클래스를 정의하고 클래스에 속한 인스턴스를 생성하지만 이름을 붙이지는 않는다.
    AnonymousButton(object : MouseListener {
        override fun onEnter() {
            println("Mouse entered")
        }

        override fun onClick() {
            println("Mouse clicked")
        }
    })

    // 만약 이름이 필요하다면 변수에 익명 객체를 대입하면 된다.
    val listener = object : MouseListener {
        override fun onEnter() {}
        override fun onClick() {}
    }
    AnonymousButton(listener)

    // 익명 객체는 인터페이스를 하나 이상 구현할 수도 있고, 구현하지 않을 수도 있다.
    // 자바와 마찬가지로 객체 식 내에서 그 식이 포함된 함수의 변수에 접근할 수 있다.
    // 하지만 자바와 다르게 final이 아닌 변수도 객체 식 안에서 사용할 수 있다.
    var clickCount = 0
    AnonymousButton(object : MouseListener {
        override fun onEnter() {}
        override fun onClick() {
            clickCount++
        }
    })

    // 객체 식으로 여러 인터페이스 구현
    val duck = object : Flyer, Swimmer {
        override fun fly() {
            println("Duck is flying")
        }

        override fun swim() {
            println("Duck is swimming")
        }
    }
}

/*
객체 식은 익명 객체 안에서 여러 메서드를 오버라이드해야 하는 경우에 훨씬 더 유용하다.
반면에 메서드가 하나뿐인 인터페이스를 구현해야 하면 람다를 사용하는 것이 더 낫다.
 */