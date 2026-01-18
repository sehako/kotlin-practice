package chapter09.section2

// 코틀린은 == 연산자를 equals 호출로 컴파일한다.
// != 역시도 !equals 호출로 컴파일된다. (이 말은 !=에 대한 별도 정의가 필요 없다는 의미이기도 하다.)
// 참고로 코틀린은 a == b 같은 비교 처리 시 양쪽이 null이 아닌 경우에만 equals를 검사하고, 양쪽이 null인 경우 true를 반환한다.
class EqualsPoint(val x: Int, val y: Int) {
    // Any에 정의된 메서드미으로 오버라이드해야 한다.
    override fun equals(other: Any?): Boolean {
        // 1. 참조 동일성 검사 (가장 빠르게 동등 여부를 검사)
        if (this === other) {
            return true
        }
        // 2. 타입 일치 여부 검사 (null 여부 포함)
        if (other !is EqualsPoint) {
            return false
        }

        // 두 객체의 값 비교
        return x == other.x && y == other.y
    }
}
