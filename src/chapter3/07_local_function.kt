package chapter3

// 자바 코드를 작성하다보면 DRY(Don't Repeat Yourself) 원칙(중복 코드 방지)을 지키기 힘음
// 일반적으로 메서드를 분리하지만 이게 많아질수록 각 메서드의 관계를 파악하기 어려움
// 더 발전하여 내부 클래스로 넣을 수 있지만 이에 따른 준비 코드도 요구함
// 코틀린에서는 함수에서 추출한 함수를 원래의 함수 내부에 내포시킬 수 있음
fun main() {
    // 사용자 저장 로직
    saveUser(User(1, "", ""))

    // 사용자 저장 로직 (로컬 함수 활용)
    saveUserWithLocalFunction(User(1, "", ""))

    // 확장 함수 활용
    saveUserWithExpandFunction(User(1, "", ""))

    // 이런 식으로 로컬 함수 또는 확장 함수로 중복 코드를 없애면서도 가독성을 유지 가능
    // 확장 함수를 로컬 함수로 정의할 수 있지만 내포된 함수의 깊이가 깊어지면 코드를 읽기 어려워짐
    // 따라서 일반적으로 한 단계만 함수를 내포시키라고 권장
}

class User(
    val id: Int,
    val name: String,
    val address: String
)

fun saveUser(user: User) {
    // 사용자의 필드를 검증할 때 필요한 경우를 모두 처리하다보면 프로퍼티 수 만큼의 중복이 발생
    // 이런 경우 검증 코드를 로컬 함수로 분리하면 중복을 없애는 동시에 코드 구조를 깔끔하게 유지 가능
    if (user.name.isEmpty()) {
        throw IllegalArgumentException("Error(${user.id}): Name must not be empty")
    }

    if (user.address.isEmpty()) {
        throw IllegalArgumentException("Error(${user.id}): Address must not be empty")
    }

    // 사용자 DB에 저장
}

// 로컬 함수 구현 예시
fun saveUserWithLocalFunction(user: User) {
    fun validate(
        value: String,
        fieldName: String
    ) {
        if (value.isEmpty()) {
            // 여기서 로컬 함수는 바깥 함수의 파라미터에 직접 접근할 수 있음
            throw IllegalArgumentException("Error(${user.id}): ${fieldName} must not be empty")
        }
    }

    validate(user.name, "Name")
    validate(user.address, "Address")

    // 사용자 DB에 저장
}

fun saveUserWithExpandFunction(user: User) = user.apply {
    validateBeforeSave()

    // 사용자 DB에 저장
}

// 좀 더 깔끔하게 검증 로직을 확장 함수로 추출할 수도 있다.
fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException("Error(${this.id}): ${fieldName} must not be empty")
        }
    }

    validate(name, "Name")
    validate(address, "Address")
}
