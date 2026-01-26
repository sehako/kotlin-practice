package part02.chapter09.section3

// 객체가 컬렉션에 존재하는지 검사하는 연산자로는 in이 있다. 이는 contains로 구현할 수 있다.
data class InPoint(val x: Int, val y: Int)
data class InRectangle(val upperLeft: InPoint, val lowerRight: InPoint) {
}

// in 왼쪽에 있는 객체는 contains의 인자로 전달되고, 오른쪽에 있는 객체는 contains의 수신 객체가 된다.
operator fun InRectangle.contains(p: InPoint): Boolean {
    return p.x in upperLeft.x..<lowerRight.x
            && p.y in upperLeft.y..<lowerRight.y
}

fun inExample() {
    val rect = InRectangle(InPoint(10, 20), InPoint(50, 50))
    println(InPoint(25, 35) in rect)
}
