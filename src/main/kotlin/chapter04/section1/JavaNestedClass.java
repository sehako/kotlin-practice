package chapter04.section1;

// 자바 중첩 클래스에 대한 정리
/*
Nested Class (중첩 클래스)
│
├── 1. Static Nested Class (정적 중첩 클래스)
│      └─ 특징: static 키워드 사용. 외부 클래스의 인스턴스 없이 독립적으로 생성 가능.
│
└── 2. Inner Class (내부 클래스 / 비정적 중첩 클래스)
       │
       ├── A. Member Inner Class (멤버 내부 클래스)
       │      └─ 특징: 클래스의 멤버 위치에 선언. 외부 클래스의 인스턴스가 있어야만 생성 가능.
       │
       ├── B. Local Class (지역 클래스)
       │      └─ 특징: 메서드나 초기화 블록 내부에 선언. 해당 블록 안에서만 사용 가능.
       │
       └── C. Anonymous Class (익명 클래스)
              └─ 특징: 이름이 없음. 클래스 선언과 객체 생성을 동시에 함 (일회용).
 */
public class JavaNestedClass {
    private static String staticOuterField = "외부 클래스 정적 필드";
    private String outerField = "외부 클래스 필드";

    public void methodWithClasses() {
        // 3. 지역 클래스 (Local Class)
        // 메서드 내부에서 선언되며, 해당 메서드 안에서만 유효하다.
        class LocalClass {
            void print() {
                System.out.println("지역 클래스입니다.");
            }
        }
        LocalClass lc = new LocalClass();
        lc.print();

        // 4. 익명 클래스 (Anonymous Class)
        // 클래스 선언과 동시에 객체를 생성하며, 이름이 없다.
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("익명 클래스 실행");
            }
        };

        // 참고로 함수형 인터페이스의 경우 익명 클래스를 람다식으로 생성할 수 있다.
        Runnable lambdaRunnable = () -> System.out.println("람다로 만들어진 익명 클래스");
    }

    // 1. 정적 중첩 클래스 (Static Nested Class)
    // 외부 클래스의 객체 생성 없이도 독립적으로 생성 가능하다.
    public static class StaticNested {
        void display() {
            // 외부의 인스턴스 변수에는 접근 불가 (outerField 사용 불가)
            System.out.println("접근 가능: " + staticOuterField);
        }
    }

    // 2. 내부 클래스 (Inner Class / Instance Inner Class)
    // 외부 클래스의 인스턴스가 먼저 생성되어야만 사용할 수 있다.
    public class InnerClass {
        void display() {
            // 외부 클래스의 private 자원에 자유롭게 접근 가능
            System.out.println("접근 가능: " + outerField);
            System.out.println("접근 가능: " + staticOuterField);
        }
    }
}
