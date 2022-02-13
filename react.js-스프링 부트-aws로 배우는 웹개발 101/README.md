# React.js, 스프링 부트, AWS로 배우는 웹개발 101

- [SPA, REST API 기반 웹 애플리케이션 개발 - React.js, 스프링 부트, AWS로 배우는 웹개발 101](https://www.aladin.co.kr/shop/wproduct.aspx?ItemId=279824138)
- 위의 책을 읽고 정리 한다.

### 배경 지식

<br>

<details>
<summary>HTTP - HyperTet Transfer Protocol</summary>

<br>

- 애플리케이션 레벨의 네트워크 프로토콜 이다.
- 사용자는 클라이언트를 통해 서버에 HTTP 요청을 전송 가능 하다.
- HTTP 요청에는 메서드를 지정 하여 서버에 지정한 리소스에 대해 어떠한 작업을 하고 싶은지 알려준다.
  - 메소드에는 GET, POST, PUT, DELETE 등이 있다.
- HTTP 응답에는 응답코드를 함께 보내 사용자가 요청이 어떻게 처리 되었는지 알 수 있다.
  - 예를 들어 200은 성공적으로 요청을 처리, 404는 해당 리소스가 존재하지 않는다는 의미이다.

</div>
</details>

<details>
<summary>직렬화와 역직렬화</summary>

<br>

- 메모리상의 오브젝트를 다른 형태로 변환하는 작업을 직렬화, 반대 작업을 역직렬화라고 한다.
- 오브젝트를 저장하거나 네트워크를 통해 전달할 수 있도록 JSON으로 변환하는 것도 직렬화라고 할 수 있다.

</div>
</details>

<details>
<summary>서버</summary>

<br>

- 서버란 프로그램이다.
- 지정된 포트에 소켓을 열고 클라이언트가 연결할 때까지 무한 대기하며 기다린다.
- 클라이언트가 연결하면 해당 클라이언트 소켓에서 요청을 받아와 수행하고 응답을 작성하여 전달한다.

</div>

</details>

<details>
<summary>정적 웹 서버</summary>

<br>

- 정적 웹 서버란 HTTP 서버 중에서도 리소스 파일을 리턴하는 서버를 의미한다.
- 리소스에 대해 아무런 처리를 하지 않고 있는 그대로 리턴한다.
- 대표적으로 Apache나 Nginx가 있다.

</div>
</details>

</details>

<details>
<summary>동적 웹 서버</summary>

<br>

- 동적 웹 서버는 요청을 처리한 후 처리한 결과에 따라 응답 바디를 재구성하거나 HTML 템플릿 파일에 결과를 대체해 보낸다.
- 어떤 클라이언트가 요청하든 같은 응답을 리턴하는 정적 웹 서버와 달리 동적 웹서버는 클라이언트가 누군지, 어떤 매개변수를 보내는지에 따라 같은 요청이라도 다른 응답을 받을 수 있다.

</div>
</details>

<details>
<summary>자바 서블릿 컨테이너/엔진</summary>

<br>

- 개발자들은 서블릿 엔진을 설치한 후 서블릿 엔진에게 자기가 개발한 비즈니스 로직, 즉 클래스 파일과 해당 클래스 파일을 어느 요청에서 실행해야 하는지 알려줘야 한다.
- 서블릿 엔진이 이해할 수 있는 형태로 클래스 파일을 작성해야 한다.
  - javax.servelt.http.HttpServelt의 상속을 받는 서브 클래스
- 서블릿 엔진을 이용해 개발자는 서버를 처음부터 구현하지 않고도 각기 다른 비즈니스 로직을 구현하고 배포할 수 있다.

</div>
</details>

<br>

### 백엔드 개발

<details>
<summary>의존성 주입</summary>

<br>

- 의존성 주입이란 의존하는 다른 클래스들을 외부에서 주입시키는 것 이다.
- 의존성 주입을 통해 변경에 유연한 어플리케이션을 만들 수 있다.
- 스프링은 의존성 주입을 개발자 대신 해준다. (IOC)
- 스프링이 관리하는 Bean객체들은 스프링이 대신 의존성 주입을 해준다.
- 이러한 Bean들은 스프링 프레임워크의 IoC컨테이너에서 관리한다.

</div>
</details>


<details>
<summary>Dispatcher Servlet</summary>

<br>

- 앞단에서 HTTP 프로토콜로 들어오는 모든 요청을 가장 먼저 받아 적합한 컨트롤러에게 위이해주는 프론트 컨트롤러 이다.

</div>
</details>

<details>
<summary>Bean과 Component의 차이</summary>

<br>

- @Bean은 메소드 레벨에서 선언하며, 반환되는 인스턴스를 Bean으로 등록한다.
- @Component는 클래스 레벨에서 선언하며, 스프링이 런타임시에 컴포넌트 스캔을 하여 자동으로 빈을 찾고 등록한다.

</div>
</details>

<details>
<summary>Gradle</summary>

<br>

- Gradle은 빌드 자동화 툴 이다.
- 빌드 자동화 툴을 이용하면 컴파일, 라이브러리 다운, 패키징, 테스팅 등을 자동화 할 수 있다.
- 플러그인을 통해 그래들을 확장해서 사용할 수 있다.
  - 대표적인 예로 `id 'java'`가 있다.

</div>
</details>

<details>
<summary>레이어드 아키텍처 패턴</summary>

<br>

- 어떻게 코드를 적절히 분리하고 관리할 것이냐에 대한 것
- 레이어드 아키텟처 패턴은 애플리케이션을 구성하는 요소들을 수평으로 나눠 관리하는 것이다.
- 기본적으로는 레이어는 자기보다 한 단계 하위 레이어만 사용한다.

</div>
</details>

<details>
<summary>DTO - Data Transfer Object</summary>

<br>

- 비즈니스 로직을 캡슐화하기 위해 DTO를 사용한다.
  - 모델은 데이터베이스 테이블 구조와 매우 유사하다.
  - 대부분의 비즈니스는 외부인이 데이터베이스의 스키마를 아는 것을 원치 않는다.
  - DTO를 통해 외부사용자로 부터 서비스 내부의 로직, 데이터베이스의 구조 등을 숨길 수 있다.
- 클라이언트가 필요한 정보를 모델이 전부 포함하지 않는 경우가 많다.

</div>
</details>


<details>
<summary>REST API</summary>

<br>

- REST 아키텍처의 제약 조건을 준수하는 API이다.
- API가 Restful로 간주되는 기준은 아래와 같다.
  - 클라이언트-서버
  - 상태가 없는
    - 클라이언트가 서버에 요청을 보낼 때 이전 요청의 영향을 받지 않음을 의미한다.
  - 캐시되는 데이터
    - 서버에서 리소스를 리턴할 때 캐시가 가능한지 아닌지 명시할 수 있어야 한다.
    - HTTP에서는 `cache-control`라는 헤더에 리소스의 캐시 여부를 명시 할 수 있다.
  - 일관적인 인터페이스
  - 레이어 시스템
  - 코드-온-디멘드(선택사항)

</div>
</details>


<details>
<summary>JDBC - Java Database Connectivity</summary>

<br>

- 자바 프로그램이 데이터베이스와 연결되어 데이터를 주고 받을 수 있게 해주는 인터페이스
- JDBC 개발 단계
  - JDBC Driver Loading
    - 데이터베이스 벤더에 맞는 드라이버를 호출
    - 데이터베이스와 연결을 위해 드라이버를 로딩
  - Connection
    - DB와 연결을 위해 URL과 계정정보가 필요
  - Statement / PreparedStatement
    - SQL 구문을 정의하고 쿼리 전송 전에 값을 셋팅
  - executeUpdate() or execureQuery
    - executeUpate()
      - SQL 문이 INSERT, DELETE, UPDATE의 경우에 사용
      - 반환값 타입은 int
    - executeQuery()
      - SQL 문이 SELECT일 경우 사용
        - 반환값 타입은 ResultSet
  - ResultSet
    - 데이터베이스 조회 결과집합에 대한 표준
  - close
    - Connection, Statement, ResultSet에 대해 close를 수행한다.

</div>
</details>

<details>
<summary>DAO, JPA, SpringDataJPA</summary>

<br>

- DAO
  - JDBC를 통해 데이터베이스를 다루는 작업을 수행한다.
  - ResultSet을 오브젝트로 바꾸어주는 작업을 수행한다.
- JPA
  - 현재 자바 진영의 ORM 기술 표준으로, 인터페이스의 모음
  - JPA 인터페이스를 구현한 대표적인 오픈소스가 Hibernaete이다.
- Spring Data JPA
  - Spring에서 JPA를 더 사용하기 쉽게 도와주는 스프링 프로젝트
  - 추상화를 통해 Spring에서 JPA를 더 사용하기 쉽게 하였다.
    - 추상화했다는 것은 사용하기 쉬운 인터페이스를 제공한다는 것
  - 예를 들어 JpaRepository가 있다.

</div>
</details>


<details>
<summary>클래스를 엔티티로 정의 할 때 주의할 점</summary>

<br>

- 클래스에 매개변수가 없는 생성자가 필요하다.
  - NoArgsConstructor
- Getter, Setter가 필요하다.
- 기본키를 지정해줘야한다.

</div>
</details>


<details>
<summary>로깅</summary>

<br>

- Slf4j는 로깅에 대한 추상 레이어를 제공하는 것이고, 인터페이스의 모음이다.
- Slf4j를 사용하려면 구현부를 연결해줘야 한다.
- 스프링에서는 구현부로 Logback라이브러리를 사용한다.


</div>
</details>


<details>
<summary>더 공부해야 할 것</summary>

<br>

- AOP를 통해 JpaRepository가 동작하는 방식

</div>
</details>