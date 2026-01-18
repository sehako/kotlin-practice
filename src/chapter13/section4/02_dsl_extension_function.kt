package chapter13.section4

import kotlinx.datetime.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.DurationUnit
import kotlin.time.toDuration

// 원시 타입에 대해 확장 함수를 정의할 수 있다.

// kotlinx-datetime 라이브러리는 날짜와 시간을 다루기 위해 다음과 같은 DSL을 제공한다.
// (참고로 현재는 deprecated된 코드이다.)
val now = Clock.System.now()
val yesterday = now - 1.days
val later = now + 5.hours

// 다음 코드를 보자.
/*
import kotlin.time.DurationUnit
val Int.days: Duration
    get() = this.toDuration(DurationUnit.DAYS)
val Int.hours: Duration:
    get() = this.toDuration(DurationUnit.HOURS)
 */
// 여기서 days와 hours는 Int 타입의 확장 프로퍼티다. 코틀린에서는 아무 타입이나 확장 함수의 수신 객체 타입이 될 수 있다.
// 따라서 편하게 원시 타입에 대한 확장 함수를 정의하고 원시 타입 상수에 대해 그 확장 함수를 호출할 수 있다.
// 예를 들어 2주(fortnight)를 다음과 같이 정의할 수 있다.
val Int.fortnight: Duration
    get() = (this * 14).toDuration(DurationUnit.DAYS)
