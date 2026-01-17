package chapter10.section1

// 함수를 반환하는 함수가 유용한 경우가 있다.
// 선택한 배송 수단에 따라서 배송비 계산 방법이 달라진다고 가정해보자.
enum class DeliveryMethod { STANDARD, EXPRESS }

class Order(val itemCount: Int)

fun getShippingCostCalculator(deliveryMethod: DeliveryMethod): (Order) -> Double { // 함수를 반환하는 함수 선언
    when (deliveryMethod) {
        // 함수에서 람다를 반환한다.
        DeliveryMethod.STANDARD -> return { order -> order.itemCount * 10.0 }
        DeliveryMethod.EXPRESS -> return { order -> order.itemCount * 20.0 }
    }
}

fun shippingCostExample() {
    val calculator = getShippingCostCalculator(DeliveryMethod.STANDARD)
    // 반환받은 함수 호출
    println(calculator(Order(3)))
}
