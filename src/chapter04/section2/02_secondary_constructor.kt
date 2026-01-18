package chapter04.section2

import java.net.URI

// 생성자가 여러 개 필요한 경우 (ex. 프레임워크 클래스 확장 등) 코틀린은 다음과 같이 선언한다
// 주 생성자를 사용하지 않고 constructor 키워드인 부 생성자만 두 개 선언한 예시이다.
open class Downloader {
    constructor(url: String?) {

    }

    constructor(uri: URI?) {

    }
}

// 위 클래스를 확장하면서 똑같이 부 생성자를 정의할 수 있다.
// 이때 super() 키워드를 통해 자신에 대응하는 상위 클래스 생성자를 호출한다.
// 클래스에 주 생성자가 없다면 모든 부 생성자는 반드시 상위 클래스를 초기화하거나 다른 생성자에게 생성을 위임해야 한다.
class MyDownloader : Downloader {
    constructor(url: String?) : super(url) { // 상위 클래스의 생성자 호출

    }

    constructor(uri: URI?) : super(uri) {

    }

}

// this를 활용해 같은 클래스 내 다른 생성자에게 위임할 수도 있다.
class MyDownloader2 : Downloader {
    constructor(url: String?) : this(URI(url)) {

    }

    constructor(uri: URI?) : super(uri) {
    }
}
