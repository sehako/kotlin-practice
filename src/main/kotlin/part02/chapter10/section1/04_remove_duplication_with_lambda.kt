package part02.chapter10.section1

// 함수 타입과 람다식은 재사용하기 좋은 코드를 만들 때 훌륭한 도구가 된다.
// 웹 사이트 방문 기록을 분석한다고 가정해보자.
enum class OS { WINDOWS, LINUX, MAC, IOS, ANDROID }

data class SiteVisit(
    val path: String,
    val duration: Double,
    val os: OS
)

val log = listOf(
    SiteVisit("/", 34.0, OS.WINDOWS),
    SiteVisit("/login", 12.0, OS.WINDOWS),
    SiteVisit("/settings", 10.0, OS.MAC),
    SiteVisit("/settings", 15.0, OS.ANDROID),
    SiteVisit("/settings", 24.0, OS.IOS)
)

// 여기서 윈도우 사용자의 평균 방문 시간을 출력한다고 가정하면 average를 사용할 수 있다.
val averageWindowsDuration = log
    .filter { it.os == OS.WINDOWS }
    .map(SiteVisit::duration)
    .average()

// 각 운영체제마다 이 코드를 작성하는 건 중복이다. 확장 함수로 만들어보자.
fun List<SiteVisit>.averageDurationFor(os: OS) =
    this.filter { it.os == os }
        .map(SiteVisit::duration)
        .average()

// 하지만 모바일 디바이스 사용자 (IOS, ANDROID) 대상이라면 다음과 같이 코드를 약간 수정해야 한다.
fun List<SiteVisit>.averageMobileDuration() = log
    .filter { it.os in setOf(OS.IOS, OS.ANDROID) }
    .map(SiteVisit::duration)
    .average()

// 검색 조건이 다양해질수록 이런 함수를 계속해서 만들어야 한다.
// 이럴 때 람다를 사용하면 필요한 조건을 인자로 받아 전달하는 식으로 구현할 수 있다.
fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) =
    this.filter(predicate)
        .map(SiteVisit::duration)
        .average()

// 람다를 사용해서 데이터의 반복이나 반복적인 행동을 추출할 수 있다.
