package chapter5.section3;

import kotlin.Unit;

public class KotlinFunctionCall {
    public void useKotlinFunction() {
        ConsumerKt.consumeHello(s -> System.out.println(s.toUpperCase()));
        // 자바에서 코틀린 함수 타입을 사용하려면 명시적으로 Unit.INSTANCE를 반환해야 한다.
        ConsumerKt.consumeHelloFunctional(s -> {
            System.out.println(s.toUpperCase());
            return Unit.INSTANCE;
        });
    }
}
