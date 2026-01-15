package chapter7;

// null 가능성 어노테이션이 없는 자바 클래스
public class JavaPerson {
    // 코틀린은 이 프로퍼티가 null인지 알 수 없다.
    private final String name;

    public JavaPerson(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
