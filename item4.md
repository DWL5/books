## 아이템 4. 인스턴스화를 막으려거든 private 생성자를 사용하라



### 정적 메서드와 정적 필드만들 담은 클래스를 만들고 싶을 때가 있다.

- 해당 클래스는 인스턴스로 만들어 쓰려고 설계한 것이 아니다.
- 하지만 생성자를 명시하지 않으면 컴파일러가 자동으로 기본 생성자를 만들어준다.
  - 사용자는 이 생성자가 자동 생성된 것인지 구분할 수 없다.

### 추상클래스로 선언하면 될까?

- 추상 클래스로 만드는 것으로는 인스턴스화를 막을 수 없다.
  - 하위 클래스를 만들어 인스턴스화 하면 그만이다.
  - 추상 클래스로 선언된 클래스는 상속해서 쓰라는 뜻으로 오해할 수 있으니 더 큰 문제다.



### private 생성자를 추가하자. 클래스의 인스턴스화를 막을 수 있다.

- 명시적 생성자가 private니 바깥에서 접근할 수 없다.
- 클래스 안에서 실수라도 생성자를 호출 하지 않도록 해준다.
- 이 방식은 `상속` 을 불가능하게 하는 효과도 있다.
  - 모든 생성자는 명시적이든 묵시적이든 상위 클래스의 생성자를 호출하게 되는데, private로 생성자가 선언 되어서 하위 클래스가 상위 클래스의 생성자에 접근 할 수가 없다.


