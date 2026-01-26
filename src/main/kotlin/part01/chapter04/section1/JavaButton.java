package part01.chapter04.section1;

// 코틀린은 디폴트 메서드가 있는 인터페이스를 일반 인터페이스와 디폴트 메서드 구현이 정적 메서드로 들어있는 클래스를 조합해 구현
// 따라서 인터페이스에는 메서드 선언만 들어가서 코틀린 인터페이스를 자바 클래스에서 구현하고자 한다면 모든 메서드에 대한 구현이 필수적이다.
public class JavaButton implements Clickable {
    @Override
    public void click() {
        System.out.println("Java Button Clicked");
    }

    @Override
    public void showOff() {
        System.out.println("Java Button");
    }
}
