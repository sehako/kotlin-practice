package chapter12.section2

import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor

// ** 역직렬화 부분은 정리하기 난해하여 jkid 역직렬화 관련 로직을 gemini 내용 요약으로 대체
// --------------------------------------------------------------------------
// [1] 역직렬화의 기본 구조: Parser -> Seed -> Object
// 파서는 JSON 토큰을 읽어 Seed(중간 저장소)에 데이터를 채우고,
// Seed는 모든 데이터가 모이면 ClassInfo를 통해 실제 객체를 생성(spawn)합니다.
// --------------------------------------------------------------------------

// 기본 인터페이스: JSON 객체를 구성하는 프로퍼티 설정 동작 정의
interface JsonObject {
    fun setSimpleProperty(propertyName: String, value: Any?)
    fun createCompositeProperty(propertyName: String, isList: Boolean): JsonObject
}

// Seed: 객체 생성 전 데이터를 담아두는 '씨앗'. 재귀적으로 중첩된 객체 생성을 처리함.
interface Seed : JsonObject {
    fun spawn(): Any?
}

// --------------------------------------------------------------------------
// [2] ObjectSeed: 실제 데이터를 모으고 객체 생성을 위임하는 핵심 클래스
// --------------------------------------------------------------------------
class ObjectSeed<T : Any>(
    targetClass: KClass<T>,
    private val classInfo: ClassInfo<T> // 생성자/파라미터 정보 캐시
) : Seed {

    // 기본 타입 인자(Int, String 등) 저장소
    private val valueArguments = mutableMapOf<KParameter, Any?>()

    // 중첩 객체(또는 리스트)를 위한 Seed 저장소
    private val seedArguments = mutableMapOf<KParameter, Seed>()

    // 단순 값 프로퍼티 설정 (JSON: "name": "Jaehun")
    override fun setSimpleProperty(propertyName: String, value: Any?) {
        val param = classInfo.getConstructorParameter(propertyName)
        // 실제로는 여기서 값 타입 변환도 수행됨 (ClassInfo.deserializeConstructorArgument)
        valueArguments[param] = value
    }

    // 복합 프로퍼티(객체/리스트) 설정 (JSON: "address": { ... }) -> 재귀 구조
    override fun createCompositeProperty(propertyName: String, isList: Boolean): Seed {
        val param = classInfo.getConstructorParameter(propertyName)
        // 해당 프로퍼티 타입에 맞는 새로운 Seed 생성 (여기서는 단순화하여 자기 자신과 같은 타입 가정)
        val childSeed = ObjectSeed(param.type.classifier as KClass<Any>, classInfo as ClassInfo<Any>)
        seedArguments[param] = childSeed
        return childSeed
    }

    // [최종 단계] 모아둔 인자들을 합쳐서 실제 객체 생성
    override fun spawn(): T {
        // 1. 중첩된 Seed들을 재귀적으로 spawn하여 실제 객체로 변환
        val nestedArgs = seedArguments.mapValues { it.value.spawn() }

        // 2. 단순 값과 변환된 중첩 객체들을 합침
        val finalArguments = valueArguments + nestedArgs

        // 3. ClassInfo에게 객체 생성 위임 (callBy 사용)
        return classInfo.createInstance(finalArguments)
    }
}

// --------------------------------------------------------------------------
// [3] ClassInfo: 리플렉션 캐싱 및 callBy를 이용한 객체 생성
// --------------------------------------------------------------------------
class ClassInfo<T : Any>(kClass: KClass<T>) {
    // 주 생성자 호출
    private val constructor = kClass.primaryConstructor!!

    // JSON 키 이름("name") -> 생성자 파라미터 매핑 캐시
    private val jsonNameToParam = constructor.parameters.associateBy { it.name!! }

    fun getConstructorParameter(propertyName: String): KParameter =
        jsonNameToParam[propertyName] ?: throw IllegalArgumentException("Unknown param: $propertyName")

    // ★ 핵심: KCallable.callBy를 사용하여 객체 생성
    // callBy는 Map에 없는 파라미터가 '디폴트 값'을 가지고 있다면 자동으로 처리해줌.
    fun createInstance(arguments: Map<KParameter, Any?>): T {
        return constructor.callBy(arguments)
    }
}

// --------------------------------------------------------------------------
// [4] 실행 예시 (가상의 Parser 동작 시뮬레이션)
// --------------------------------------------------------------------------
data class User(val name: String, val age: Int = 20) // age는 디폴트 값 존재

fun main() {
    println(">>> 역직렬화 프로세스 시작")

    // 1. 초기 준비: ClassInfo 및 최상위 Seed 생성
    val classInfo = ClassInfo(User::class)
    val rootSeed = ObjectSeed(User::class, classInfo)

    // 2. 파서(Parser)가 JSON을 읽으면서 Seed 메서드를 호출한다고 가정
    // JSON: { "name": "Kotlin" } -> age는 JSON에 없음
    println("[Parser] 'name' 프로퍼티 발견 -> setSimpleProperty 호출")
    rootSeed.setSimpleProperty("name", "Kotlin")

    // 3. 파싱 종료 후 spawn 호출
    println("[System] spawn() 호출 -> 객체 생성 시도")
    val user = rootSeed.spawn()

    // 4. 결과 확인
    println(">>> 최종 생성된 객체: $user")
    // 출력: User(name=Kotlin, age=20)
    // 설명: 'age'는 seedArguments에 없었지만, callBy가 디폴트 값(20)을 사용하여 생성함.
}