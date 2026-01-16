package chapter8.section2;

import java.io.File;
import java.util.List;

// 컬렉션 파라미터를 받는 자바 인터페이스
public interface FileContentProcessor {
    void processContents(
            File path,
            byte[] binaryContents,
            List<String> textContents
    );
}
