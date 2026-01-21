package chapter12.section1

// 자바와 마찬가지로 코틀린 어노테이션 클래스에도 어노테이션을 붙일 수 있다.
// 어노테이션 클래스에 적용할 수 있는 어노테이션을 메타어노테이션이라고 한다.
// 표준 라이브러리에는 여러 메타어노테이션이 있으며, 이들은 컴파일러가 어노테이션을 처리하는 방법을 제어한다.
// 대표적으로 @Target이 있다. 제이키드의 JsonExclude와 JsonName 어노테이션에도 해당 어노테이션이 명시되어 있다.

/*
@Target(AnnotationTarget.PROPERTY)
annotation class JsonExclude
 */

// @Target은 어노테이션을 적용할 수 있는 요소의 유형을 지정한다.
// 어노테이션 클래스에 대해 구체적인 @Target을 지정하지 않으면 모든 선언에 적용할 수 있는 어노테이션이 된다.
// 제이키드 라이브러리는 포러포티 어노테이션만을 사용하므로 어노테이션 클래스에 @Target을 꼭 지정해야 한다.

// 어노테이션이 붙을 수 있는 타깃이 정의된 이넘은 AnnotationTarget이다.
// 그 안에는 클래스, 파일, 프로퍼티 접근자, 타입, 식 등에 대한 enum 정의가 들어있다.
// 필요하다면 @Target(AnnotationTarget.CLASS, AnnotationTarget.METHOD)처럼 둘 이상의 타깃을 한꺼번에 선언할 수도 있다.

// 메타어노테이션을 직접 만들어야 한다면 ANNOTATION_CLASS를 타깃으로 지정하자.
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class BindingAnnotation

@BindingAnnotation
annotation class MyBinding

// 대상을 PROPERTY로 지정한 어노테이션을 자바 코드에서 사용할 수는 없다.
// 자바에서 그런 어노테이션을 사용하려면 AnnotationTarget.FIELD를 두 번째 타깃으로 추가해야 한다.
// 그러면 어노테이션을 코틀린 프로퍼티와 자바 필드에 어노테이션을 적용할 수 있다.
// @Target에 정의할 수 있는 대상은 다음과 같다.
/*
PROPERTY: 코틀린 프로퍼티(변수)에 적용.
CLASS: 클래스, 인터페이스, 객체 등에 적용.
FUNCTION: 함수(메소드)에 적용.
ANNOTATION_CLASS: 또 다른 어노테이션 위에 적용 (메타 어노테이션을 만들 때 사용).
FIELD: 자바의 필드(변수)에 대응하는 곳에 적용.
 */

/*
자바에서 다른 중요한 어노테이션으로 @Retention이 있다.
이 어노테이션은 정의 중인 어노테이션 클래스를 소스 수준에만 유지할지, .class 파일에 저장할지,
실행 시점에 리플렉션을 사용해 접근할 수 있게 할지를 지정하는 메타어노테이션이다.
자바 컴파일러는 기본적으로 어노테이션을 .class 파일에는 저장하지만 런타임에는 사용할 수 없게 한다.
하지만 대부분의 어노테이션은 런타임에도 사용할 수 있어야 하므로 코틀린은 @Retention을 기본값으로 RUNTIME으로 지정한다.
*/
