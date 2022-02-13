# Java-TDD-Practise

- 자바와 Junit을 활용한 실용주의 단위테스트를 읽고 정리합니다.
- 해당 책은 Junit4로 되어 있어서 아쉽습니다. 책을 읽되 Junit5를 적용하여 공부합니다.

# 1부 단위테스트의 기초

### 1장 첫번째 Junit 테스트 만들기

- 코드가 정상적으로 동작하는지 확신하려고 추가적인 테스트를 작성할 필요가 있는가?
- 내가 클래스에서 결함이나 한계점을 드러낼 수 있는 테스트를 작성할 수 있을까?



### 2장 JUnit 진짜로 써 보기

- @BeforEach
  - 매 테스트 마다 테스트 인스턴스를 새로 만듦
  - 테스트 코드가 다른 테스트에 영향을 주지 말아야 한다.
    - 테스트 클래스에서는 static 변수 선언을 피해야 한다.

### 3장 JUnit 단언 깊게 파기

- 책에서는 별로 궁금한 내용이 없었다. 
- Junit뿐아니라 AssertJ에 대해서도 공부해 본다.
- 우테코에서 미션으로 주어진 테스트 코드 작성방법을 복습해 본다.

#### String 클래스에 대한 학습 테스트

##### 요구사항 1

- "1,2"을 `,`로 split 했을 때 1과 2로 잘 분리되는지 확인하는 학습 테스트를 구현한다.
- "1"을 `,`로 split 했을 때 1만을 포함하는 배열이 반환되는지에 대한 학습 테스트를 구현한다.

##### 힌트

- 배열로 반환하는 값의 경우 assertj의 contains()를 활용해 반환 값이 맞는지 검증한다.
- 배열로 반환하는 값의 경우 assertj의 containsExactly()를 활용해 반환 값이 맞는지 검증한다.

##### 요구사항 2

- "(1,2)" 값이 주어졌을 때 String의 ??? 메소드를 활용해 `()`을 제거하고 "1,2"를 반환하도록 구현한다.

##### 요구사항 3

- "abc" 값이 주어졌을 때 String의 ??? 메소드를 활용해 특정 위치의 문자를 가져오는 학습 테스트를 구현한다.
- String의 ??? 메소드를 활용해 특정 위치의 문자를 가져올 때 위치 값을 벗어나면 StringIndexOutOfBoundsException이 발생하는 부분에 대한 학습 테스트를 구현한다.
- JUnit의 @DisplayName을 활용해 테스트 메소드의 의도를 드러낸다.

#### Set Collection에 대한 학습 테스트

- 다음과 같은 Set 데이터가 주어졌을 때 요구사항을 만족해야 한다.

```java
public class SetTest {
    private Set<Integer> numbers;

    @BeforeEach
    void setUp() {
        numbers = new HashSet<>();
        numbers.add(1);
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
    }
    
    // Test Case 구현
}
```



##### 요구사항 1

- Set의 size() 메소드를 활용해 Set의 크기를 확인하는 학습테스트를 구현한다.

##### 요구사항 2

- Set의 contains() 메소드를 활용해 1, 2, 3의 값이 존재하는지를 확인하는 학습테스트를 구현하려한다.
- 구현하고 보니 다음과 같이 중복 코드가 계속해서 발생한다.
- JUnit의 ParameterizedTest를 활용해 중복 코드를 제거해 본다.

```java
  @Test
    void contains() {
        assertThat(numbers.contains(1)).isTrue();
        assertThat(numbers.contains(2)).isTrue();
        assertThat(numbers.contains(3)).isTrue();
    }
```



##### 힌트

- https://www.baeldung.com/parameterized-tests-junit-5

```java
@ParameterizedTest
@ValueSource(strings = {"", "  "})
void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input) {
    assertTrue(Strings.isBlank(input));
}
```



##### 요구사항 3

- 요구사항 2는 contains 메소드 결과 값이 true인 경우만 테스트 가능하다. 입력 값에 따라 결과 값이 다른 경우에 대한 테스트도 가능하도록 구현한다.
- 예를 들어 1, 2, 3 값은 contains 메소드 실행결과 true, 4, 5 값을 넣으면 false 가 반환되는 테스트를 하나의 Test Case로 구현한다.

##### 힌트

- https://www.baeldung.com/parameterized-tests-junit-5

```
@ParameterizedTest
@CsvSource(value = {"test:test", "tEst:test", "Java:java"}, delimiter = ':')
void toLowerCase_ShouldGenerateTheExpectedLowercaseValue(String input, String expected) {
    String actualValue = input.toLowerCase();
    assertEquals(expected, actualValue);
}
```



### 4장 테스트 조직

#### private 메소드를 테스트 해야 하는가

- private 메서드를 사용하는 public 클래스를 테스트 하는 것으로 충분하다.
- 내부 행위를 테스트 하려는 충동이 든다면 설계에 문제가 있다.
- SRP를 어기는 지 한번 확인 해 볼 것
  - SRP는 어떤 클래스가 작고 단일 목적을 가져야 함을 의미
  - 가장 좋은 해결 책은 private메서드를 추출하여 다른 클래스로 이동하는 것이다.









