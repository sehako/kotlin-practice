package chapter4.section1;

import org.jetbrains.annotations.NotNull;

// 자바의 경우 ViewButton 클래스의 상태를 저장하는 클래스를 Button 클래스 내부에 선언
// 하지만 이 코드는 NotSerializableException: ViewButton 예외가 발생한다.
// 자바에서 클래스 안에 정의한 클래스는 자동으로 내부 클래스가 된다.
// 따라서 ButtonState는 바깥쪽 ViewButton 클래스에 대한 참조를 암시적으로 포함하므로 직렬화할 수 없다.
// 이를 해결하려면 ButtonState를 정적 중첩 클래스로 선언해야 한다.
public class ViewButton implements View {

    @Override
    @NotNull
    public State getCurrentState() {
        return new ButtonState();
    }

    @Override
    public void restoreState(@NotNull State state) {
    }

    public class ButtonState implements State {
        public ViewButton test() {
            return ViewButton.this;
        }
    }
}
