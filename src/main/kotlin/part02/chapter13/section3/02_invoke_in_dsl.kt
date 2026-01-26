package part02.chapter13.section3

// 이제 DSL의 invoke 관례를 살펴보자. 이를 위해 gradle 의존관계 정의를 보도록 하자.
/*
dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.12.0")
}
 */

// 이 코드처럼 내포된 블록 구조를 허용하는 한편, 평평한 함수 호출 구조도 함께 제공하는 API를 만든다고 가정해보자.
// 즉 두 상황 모두 허용되는 것이다.
/*
dependencies.implementation("org.jetbrains.exposed:exposed-core:8.40.1")

dependencies {
    implementation("org.jetbrains.exposed:exposed-core:8.40.1")
}
 */
// 첫 번째 경우는 dependencies 변수에서 implementation 메서드를 호출한다.
// dependencies 내에 람다를 받는 invoke 메서드를 정의하면 두 번째 방식의 호출을 사용할 수 있다.
// 이때 호출 구문을 완전히 풀어쓰면 dependencies.invoke({...})
// dependencies는 DependencyHandler 클래스의 인스턴스다.
// 이는 compile과 invoke 메서드 정의가 들어있다.
// invoke는 수신 객체 지정 람다를 파라미터로 받는데, 이 수신 객체는 다시 DependencyHandler가 된다.
// DependencyHandler가 암시적 수신 객체이므로 람다 안에서 compile 같은 DependencyHandler의 메서드를 직접 호출할 수 있다.
class DependencyHandler {
    fun implementation(coordinate: String) {
        println("Added dependency on $coordinate")
    }

    // invoke를 정의해 DSL 스타일 API를 제공한다.
    operator fun invoke(body: DependencyHandler.() -> Unit) {
        body() // this.body()
    }
}

fun dependenciesExample() {
    val dependencies = DependencyHandler()
    dependencies.implementation("org.jetbrains.kotlin:kotlin-stdlib")
    dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-html:0.12.0")
    }
    // 위 코드는 다음과 같이 변환된다.
    /*
    dependencies.invoke({
        this.implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
    })
     */
    // 즉 DependencyHandler의 인스턴스를 함수처럼 호출하면서 람다를 인자로 넘긴 것이다.
}
