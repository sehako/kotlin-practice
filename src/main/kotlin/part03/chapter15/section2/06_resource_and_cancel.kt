package part03.chapter15.section2

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.AutoCloseable
import kotlin.time.Duration.Companion.milliseconds

// 실제 코드는 데이터베이스 연결, IO 등과 같은 리소스를 사용해 작업해야 한다.
// 그리고 이러한 자원을 명시적으로 해제해야 하기도 한다.
// 코루틴에서 취소는 코드의 조기 종료를 유발할 수 있다. 따라서 취소 이후에도 자원이 계속 사용되는 상태일 수 있다.
class DBConnection : AutoCloseable {
    fun write(s: String) = println("writing '$s' to DB")
    override fun close() = println("closing DB connection")
}

fun resourceLeakExample() {
    runBlocking {
        val dbTask = launch {
            val db = DBConnection()
            db.write("Hello")
            delay(500.milliseconds)
            db.close() // 호출되지 않음
        }

        delay(200.milliseconds)
        dbTask.cancel()
    }
}

// 이를 위해서 try-finally 블록으로 자원을 닫도록 명시하거나
// AutoClosable 인터페이스를 구현하는 경우에는 .use를 사용해야 한다.
fun coroutineResourceCloseExample() {
    runBlocking {
        val dbTask = launch {
            DBConnection().use {
                delay(500.milliseconds)
                it.write("Hello")
            }
        }
    }
}

// 마지막으로 실제 애플리케이션에서는 프레임워크가 코루틴 스코프를 제공하고, 취소를 자동으로 처리한다.
// 예를 들어 안드로이드 애플리케이션에서 ViewModel 클래스가 viewModelScope를 제공하고,
// 사용자가 이 화면을 벗어나면 해당 스코프내에서 실행된 모든 코루틴도 함께 취소된다.
