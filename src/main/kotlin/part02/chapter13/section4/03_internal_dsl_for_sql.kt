package part02.chapter13.section4

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

// 클래스에서 확장 함수와 확장 프로퍼티를 선언하면 그렇게 정의한 확장 함수나 확장 프로퍼티는 그들이 선언된
// 클래스의 멤버인 동시에 그들이 확장하는 다른 타입의 멤버이기도 하다. 이런 함수나 프로퍼티를 멤버 확장이라고 한다.
// 다음 코드를 보도록 하자.
object Country : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    override val primaryKey = PrimaryKey(id)
}

fun saveExample() {
    val db = Database.connect("URL")
    transaction(db) {
        SchemaUtils.create(Country)
    }
    /*
    이 코드는 다음과 같은 SQL문을 생성한다.
    CREATE TABLE IF NOT EXISTS Country (
    id INT AUTO INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_Country PRIMARY KEY (id)
    )
     */
}

// 여기서 각 컬럼의 속성을 지정하는 방법에서 멤버 확장이 쓰인다.
// 메서드를 사용해 각 컬럼의 속성을 지정한다. Column에 대해 이런 메서드를 호출할 수 있다.
/*
open class Table {
    fun <N : Any> Column<N>.autoIncrement(idSeqName: String? = null): Column<N> =
        cloneWithAutoInc(idSeqName).also { replaceColumn(this, it) }
}
 */

// 이는 Table 클래스의 멤버(Table 클래스 영역에서만 사용할 수 있음)이기는 하지만 여전히 Column의 확장 함수이기도 하다.
// 이렇게 멤버 확장으로 정의하면 메서드가 적용되는 범위를 제한할 수 있다.
// 테이블이라는 맥락이 없으면 컬럼의 프로퍼티를 정의해도 아무 의미가 없어 필요한 메서드를 찾아낼 수 없기 때문이다.
// 또한 이를 통해 수신 객체 타입을 제한할 수도 있다.

/*
멤버 확장도 여전히 해당 클래스에 속한 멤버이다. 따라서 기존 클래스의 소스코드를 손대지 않고 새로운 멤버 확장을 추가할 수는 없다.
새로운 데이터베이스가 새로운 타입을 지원한다면 Table에 작성된 코드를 수정하지 않고는 새롭게 지원하는 타입을 쓸 수 없다.
코틀린 팀은 이러한 문제를 해결하기 위해서 컨텍스트 수신 객체라는 언어 특징을 설계 중이다.
이를 사용하면 함수에 하나 이상의 수신 객체 타입을 지정할 수 있다.
 */

// 간단한 SELECT 질의를 통해 멤버 확장 함수를 좀 더 살펴보도록 하자.
object Customer : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val countryId = integer("country_id") references Country.id
    override val primaryKey = PrimaryKey(id)
}

fun selectExample() {
    val result = (Country innerJoin Customer)
        .selectAll()
        .where { Customer.countryId eq Country.id }
}

// 여기에서도 Column에 대한 확장 함수인 동시에 다른 클래스에 속한 멤버 확장이라 적절한 맥락에서만 해당 함수들을 사용할 수 있다.
/*
@LowPriorityInOverloadResolution
infix fun <T> ExpressionWithColumnType<T>.eq(t: T): Op<Boolean> = when {
    t == null -> isNull()
    (this as? Column<*>)?.isEntityIdentifier() == true -> table.mapIdComparison(t, ::EqOp)
    else -> EqOp(this, wrap(t))
}

open class Query(...) : AbstractQuery<Query>(set.source.targetTables()) {
    fun where(predicate: SqlExpressionBuilder.() -> Op<Boolean>): Query = where(SqlExpressionBuilder.predicate())
    // ...
}
 */
