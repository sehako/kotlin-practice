package chapter11.section1;

import org.jetbrains.annotations.NotNull;

public interface JBox<T> {
    void put(@NotNull T t);

    void putIfNotNull(T t); // null 값인 경우 아무것도 하지 않도록 작성
}
