package part01.chapter04.section2

// 값을 저장하는 동시에 로직을 실행하는 접근자를 만들어보도록 하자.
// 이러려면 뒷받침 하는 필드에 접근할 수 있어야 한다.
class BackingFieldUser(val name: String) {
    var address: String = "unspecified"
        set(value) {
            // 뒷받침하는 필드 값 읽기
            println(
                """
                    Address was changed for $name:
                    "$field" -> "$value". 
                """.trimIndent()
            )

            // 뒷받침하는 필드 값 변경하기
            field = value
        }
        get() {
            // getter에서는 값을 조회하는 것만 가능하다.
            println("address getter called for $name")
            return field
            // 참고로 코틀린에서는 address = value
            // 이렇게 하면 setter가 자기 자신을 다시 호출하여 무한 재귀(StackOverflow)가 발생한다!
        }
    // 위와 같이 접근자의 본문에서는 field라는 특별한 식별자를 통해 뒷받침하는 필드에 접근할 수 있다.
    // getter에서는 field를 읽을 수만 있고, setter에서는 field를 읽거나 쓸 수 있다.
    // 참고로 변경 가능 프로퍼티의 getter와 setter의 직접 정의는 선택이다.
    // 정의하지 않으면 단순히 조회하고 값을 설정하는 getter, setter가 만들어진다.
}

// 프로퍼티가 뒷받침하는 필드를 가지는 기준은 접근자 구현에서 field의 사용 유무이다.
// 즉 기본적으로 모든 프로퍼티는 뒷받침 하는 필드를 가지게 된다.
// 하지만 field를 사용하지 않는 커스텀 접근자 구현을 정의하면 컴파일러는 뒷받침하는 필드를 생성하지 않는다.
class NoBackingFieldPerson(private var birthYear: Int) {
    var ageIn2050: Int
        get() = 2050 - birthYear
        set(value) {
            birthYear = 2050 - value
        }
}

fun main() {
    val user = BackingFieldUser("Alice")
    // 코틀린 setter는 단순한 대입식으로 호출된다.
    user.address = "Sunnyvale, CA"
    /*
    출력:
    Address was changed for Alice:
    "unspecified" -> "Sunnyvale, CA".
     */

    println(user.address)
    /*
    address getter called for Alice
    unspecified
     */
}
