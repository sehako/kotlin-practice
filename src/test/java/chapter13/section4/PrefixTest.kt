package chapter13.section4

import io.kotest.matchers.should
import io.kotest.matchers.string.startWith
import kotlin.test.Test

class PrefixTest {
    @Test
    fun testKPrefix() {
        val s = "kotlin".uppercase()
        s should startWith("K")
    }
}