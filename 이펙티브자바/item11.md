## equals 를 재정의하려거든 hashCode도 재정의하라

`equals를 재정의한 클래스 모두에서 hashCode도 재정의해야 한다.`

- hashCode의 일반 규약을 어기게 되어 해당 클래스의 인스턴스를 HashMap이나 HashSet같은 컬렉션의 원소로 사용할 때 문제를 일으킬 것이다.

### Object 명세의 규약
- equals 비교에 사용되는 정보가 변경되지 않았다면, 애플리케이션이 실행되는 동안 그 객체의 hashCode 메서드는 몇 번을 호출해도 일관되게 항상 같은 값을 반환해야 한다.
- eqauals(Object)가 두 객체를 같다고 판단했다면, 두 객체의 hashCode는 똑같은 값을 반환해야 한다.
- eqauals(Object)가 두 객체를 다르다고 판단했더라도, 두 객체의 hashCode가 서로 다른 값을 반환할 필요는 없다.
  - 다른 객체에 대해서는 서로 다른 값을 반환해야 해시테이블의 성능이 좋아진다.
  

`논리적으로 같은 객체는 같은 해시코드를 반환해야 한다.`

```
Map<PhoneNumber, String> m = new HashMap<>();
m.put(new PhoneNumber(707, 867, 5309), "제니");
m.get(new PhoneNumber(707, 867, 5309), "제니");
// get의 결과가 null이 나온다.
```

- 위의 코드에서는 2개의 PhoneNumber 인스턴스가 사용되었다.
- hashCode를 재정의하지 않았기 때문에 논리적 동치인 두 객체가 서로 다른 해시코드를 반환한다.
  - 두 번째 규약인 `equals가 두 객체를 같다고 판단했다면, 두 객체의 hashCode는 똑같은 값을 반환해야 한다.`를 지키지 못한다.


- equals 비교에 사용되지 않은 필드는 반드시 제외해야 한다.

```
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donation donation = (Donation) o;
        return Objects.equals(id, donation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
```

### Object의 hash는 속도가 느리다.
- 클래스가 불변이고, 해시코드를 계산하는 비용이 크다면, 매번 새로 계산하기 보다는 캐싱하는 방식을 고려해야 한다.
- 이 타입의 객체가 주로 해시의 키로 사용될 것 같다면 인스턴스가 만들어질 때 해시코드를 계산해둬야 한다.

- 해시코드를 지연 초기화하는 hashCode 메서드 - 스레드 안정성까지 고려해야 한다.
```
private int hashCode;

@Override public int hashCode() {
    int result = hashCode;
    if (result == 0) {
        result = Short.hashCode(areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNum);
        hashCode = result;
    }
    return result;
}
```

### 정리
- equals를 재정의할 때는 hashCode도 반드시 재정의해야 한다.
- 재정의한 hashCode는 Object API 문서에 기술된 일반 규약을 따라야하며, 서로 다른 인스턴스라면 되도록 해시코드도 서로 다르게 구현해야 한다.