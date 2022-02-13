# spring-batch-definitive-guide

스프링배치 완벽가이드 2/e

## 배치와 스프링

- 배치처리는 상호작용이나 중단 없이 유한한 양의 데이터를 처리하는 것

### 배치 처리시 고려해야 할 것

- 사용성 측면
    - 공통 컴포넌트를 쉽개 확장해 새로운 기능을 추가할 수 있는가?
    - 기존 컴포넌트를 변경할 때 시스템 전체에 미치는 영향을 알 수 있도록 단위 테스트가 잘 마련돼 있는가?
    - 잡이 실패할 때 디버깅에 오랜 시간을 소비하지 않고 언제, 어디서, 왜 실패했는지 알 수 있는가?

- 확장성 측면
    - 배치로 처리해야 하는 데이터의 규모는 매우 클 수 있다.

- 가용성 측면
    - 필요할 때 바로 배치 처리를 수행할 수 있는가?
    - 허용된 시간 내에 잡을 수행함으로써 다른 시스템에 영향을 미치지 않게 할 수 있는가?

- 보안성 측면
    - 배치 처리에서 보안의 역할은 데이터를 안전하게 저자하는 것이다.
    - 민감한 데이터베이스 필드는 암호화 돼 있는가?
    - 실수로 개인 정보를 로그로 남기지는 않는가?
    - 외부 시스템으로의 접근은 어떠한가?
        - 자격증명이 필요하며 적절한 방식으로 보안을 유지하고 있는가?

### 잡

- 잡은 중단이나 상호작용 없이 처음부터 끝까지 실행되는 처리이다.
- 잡은 여러 개의 스텝이 모여 이뤄질 수 있다.

```java
@Bean
public AccountTasklet accountTasklet() {
  return new AccountTasklet();
}

@Bean
public Job accountJob() {
  Step accountStep = 
      this.stepBuilderFactory
        .get("accountStep")
        .tasklet(accountTasklet())
        .build();
  
  return this.jobBuilderFactory
          .get("accountJob")
          .start("accountStep")
          .build();
}
```

![image](https://user-images.githubusercontent.com/18106839/151786434-4ed57da7-163d-482b-8481-572c24fed1ad.png)

- 애플리케이션 레이어
    - 개발자가 개발한 코드를 나타낸다.
- 코어 레이어
    - 배치 영역을 구성하는 실제적인 여러 컴포넌트로 이뤄져 있다.
- 인프라스트럭쳐 레이어
    - ItemReader 및 ItemWriter를 비롯해, 재시작과 관련된 문제를 해결할 수 있는 클래스와 인터페이스를 제공한다.

## 스프링 배치

- 잡은 여러 개의 스텝이 모여 이뤄질 수 있다.
- 스텝에는 tasklet기반 스텝과 chunk 기반 스텝이 있다.
    - tasklet기반 스텝은 초기화, 저장 프로시저 실행, 알림 전송 등과 같은 잡에서 일반적으로 사용
        - tasklet을 구현하여 사용하고, tasklet의 execute가 job이 끝날 때 까지 수행 된다.
        - 각 execute는 서로 독립된 트랜잭션을 얻는다.
    - chunk 기반 스텝은 구조가 약간 더 복잡하며, 아이템 기반 처리에 사용
        - 청크 기반 스텝은 ItemReader, ItemProcessor, ItemWriter라는 3개의 주요 부분으로 구성 될 수 있다.
        - ItemProcessor는 필수는 아니다. (ex. 데이터 마이그레이션)

### 스텝을 분리함으로써 이점

- 유연성
    - 스프링 배치는 개발자가 재사용이 가능하게 구성할 수 있도록 여러 빌더 클래스를 제공한다.
- 유지 보수성
    - 각 스텝의 코드는 이전 스텝이나 다음 스텝과 독립적이므로 다른 스텝에 거의 영향을 미치지 않으면서 쉽게 각 스텝의 단위 테스트, 디버그, 변경을 할 수 있다.
- 확장성
    - 잡 내에 존재하는 독립적인 스텝은 확장 가능한 다양한 방법을 제공한다.
        - ex. 스텝을 병렬로 실행할 수 있다.
        - 코드의 변경을 최소화하면서도 업무의 확장성에 대한 요구 사항을 충족할 수 있다.
- 신뢰성
    - 스프링 배치는 스텝의 여러 단계에 적용할 수 있는 강력한 오류 처리 방법을 제공하는데, 예외 발생 시 해당 아이템의 처리를 재시도 하거나 건너뛰기하는 등의 동작을 수행 할 수 있다.

### 잡 실행

![image](https://user-images.githubusercontent.com/18106839/151789571-5f58288d-2c0b-495c-90eb-9d03a96f847f.png)

- JobRepository
    - 배치 수행과 관련된 수치 데이터 (시작 시간, 종료 시간, 상태, 읽기/쓰기 횟수) 뿐만 아니라 잡의 상태를 유지 관리한다.
    - 일반적으로 관계형 데이터베이스를 사용하며 스프링 배치 내의 대부분의 주요 컴포넌트가 공유한다.
- JobLauncher
    - 잡을 실행하는 역할
    - 잡의 재실행 가능 여부 검증 (모든 잡을 재시작 할 수 있는 것은 아님)
    - 잡의 실행 방법(현재 스레드에서 수행할지 스레드 풀을 통해 실행할지 등)
    - 파라미터 유효성 검증 처리
    - 스프링 부트가 즉시 잡을 시작하는 기능을 제공하므로, 일반적으로 직접 다룰 필요가 없는 컴포넌트다.

### JobInstance, JobExecution

- JobInstance
    - 스프링 배치잡의 논리적인 실행
    - "잡의 이름"과 "잡의 논리적 실행을 위해 제공되는 고유한 식별 파라미터 모음" 으로 유일하게 존재
    - 잡이 다른 파라미터로 실행될 때마다 새로운 JobInstance가 생성된다.
- JobExecution
    - 스프링 배치 잡의 실제 실행을 의미한다.
    - 잡을 구동할 때마다 매번 새로운 JobExecution을 얻게 된다.
    - 처음 잡을 실행하면 새로운 JobInstance와 JobExecution을 얻겠지만, 실행에 실패한 이후 다시 실행하면 여전히 동일한 논리적 실행이므로 새로운 JobInstance를 얻지 못한다. 두번째
      실행을 추적하기 위한 새로운 JobExecution을 얻을 수 있다.


### 간단한 잡을 실행하기

![image](https://user-images.githubusercontent.com/18106839/151802818-ecfefd22-8d9c-40ef-88f0-95435e88ae3e.png)

- Hello world를 출력하는 잡을 실행
- 스프링 부트에는 JobLauncherCommandLineRunner라는 컴포넌트가 있다.
  - 이 컴포넌트는 스프링 배치가 클래스 경로에 있다면 실행 시에 로딩 되며, JobLauncher를 사용해 ApplicationContext에서 찾아낸 모든 잡을 실행한다.
- 잡이 스텝을 실행하고, 스텝이 수행되고 잡의 상태가 바뀌고 있다. 이 상태는 JobRepository에 기록된다.


## 잡 파라미터
- JobInstance는 잡 이름 및 잡에 전달된 식별 파라미터로 식별된다.
- 동일한 식별 파라미터를 사용해 동일한 잡을 두 번 이상 실행할 수 없다.
- 스프링 배치는 잡에 파라미터를 전달할 수 있게 해줄 뿐만 아니라 잡 실행 전에 파라미터를 자동으로 증가시키거나 검증할 수도 있게 해준다.


### 스프링 부트의 JobLauncherCommandLineRunner를 기준으로 파라미터 전달하기

```api
java -jar demo.jar name=Michael
```

- 스프링 배치의 JobParameters는 스프링 부트의 명령행 긴으을 사용해 프로퍼티를 구성하는 것과 다르다. 따라서 -- 접두사를 사용해 잡 파라미터를 전달하면 안 된다.
- 또한 스프링 배치의 JobParameters는 시스템 프로퍼티ㅣ와도 다르므로 명형행에서 -D 아규먼트를 사용해 배치 애플리케이션에 전달해서도 안된다.

<br/>

```api
java -jar demo.jar executionDate(date)=2020/12/27
```

- 스프링 배치는 파라미터의 타입을 변환하는 기능을 제공한다.
- 파라미터 이름 뒤에 괄호를 쓰고 그 안에 파라미터의 타입을 명시해 스프링 배치에게 알려주면 된다.
- 타입의 이름은 모두 소문자여야 한다.

|JOB_EXECUTION_ID|TYPE_CD|KEY_NAME|STRING_VAL|DATE_VAL|IDENTIFYING|
|----------------|-------|--------|----------|--------|-----------|
|1|DATE|executionDate||2020-12-27 00:00:00| Y


### 특정 잡 파라미터가 식별에 사용되지 않게 하려면

- 접두사 `-`를 사용한다.


```
java -jar demo.jar executionDate(date)=2020/12/27 -name=Michael
```

### 잡 파라미터에 접근하기
- ChunkContext
  - 실행 시점의 잡 상태를 제공한다. 
  - 태스크릿 내에서 처리 중인 청크와 관련된 정보도 갖고 있다.
  - 해당 청크 정보는 스텝 및 잡과 관련된 정보도 갖고 있다.

```java
    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    String name = (String) chunkContext.getStepContext()
                            .getJobParameters()
                            .get("name");
                    System.out.println(name + " Hello World!");
                    return RepeatStatus.FINISHED;
                }).build();
    }
```

- 늦은 바인딩 
  - 스텝이나 잡을 제외한 프레임워크 내 특정 부분에 파라미터를 전달하는 가장 쉬운 방법은 스프링 구성을 사용해 주입하는 것이다.



### 잡 파라미터 유효성 검증하기
- `JobParametersValidator` 인터페이스를 구현하고 해당 구현체를 잡 내에 구성하면 된다.

```java
public class ParameterValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String fileName = parameters.getString("fileName");
        
        if (!StringUtils.hasText(fileName)) {
            throw new JobParametersInvalidException("fileName parameter is missing");
        } else if (!StringUtils.endsWithIgnoreCase(fileName, "csv")) {
            throw new JobParametersInvalidException("fileName parameter does not use the csv file extension");
        }
    }
}
```


- 스프링 배치는 모든 필수 파라미터가 누락없이 전달됐는지 확인하는 유효성 검증기인 DefaultJobParametesValidator를 기본적으로 제공한다.
  - requiredKeys와 optionalKeys라는 두가지 의존성이 있다.
- DefaultJobParametesValidator는 파라미터 존재 여부를 제외한 다른 유효성 검증을 수행하지 않는다.


```java
    @Bean
    public JobParametersValidator validator() {
        DefaultJobParametersValidator validator = new DefaultJobParametersValidator();

        validator.setRequiredKeys(new String[]{"fileName"});
        validator.setOptionalKeys(new String[]{"name"});

        return validator;
    }
```

- Job에 validator를 추가하면 유효성 검증기를 사용가능하다!

```java
    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("basicJob")
                .validator(validator())
                .start(step1())
                .build();
    }
```

- 두 개의 유효성 검증기를 사용하고 싶을 때는 `CompositeJobParametersValidator`를 사용하면 된다.

```java
    @Bean
    public CompositeJobParametersValidator validator() {
        CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
        
        DefaultJobParametersValidator defaultJobParametersValidator = new DefaultJobParametersValidator();

        defaultJobParametersValidator.setRequiredKeys(new String[]{"fileName"});
        defaultJobParametersValidator.setOptionalKeys(new String[]{"name"});

        defaultJobParametersValidator.afterPropertiesSet();
        
        validator.setValidators(Arrays.asList(new ParameterValidator(), defaultJobParametersValidator));
        
        return validator;
    }
```

### 잡 파라미터 증가시키기
- `JobParametersIncrementer`는 잡에서 사용할 파라미터를 고유하게 생성할 수 있도록 스프링 배치가 제공하는 인터페이스이다.
- 새 Icrementer를 추가 할 때 해당 파라미터도 유효성 검증을 하도록 추가해야 한다.
- 잡 실행 시마다 타임스탬프를 파라미터로 사용하기

```java
public class DailyJobTimeStamper implements JobParametersIncrementer {
    @Override
    public JobParameters getNext(JobParameters parameters) {
        return new JobParametersBuilder(parameters)
                .addDate("currentDate", new Date())
                .toJobParameters();
    }
}
```

- `currentDate`를 유효성 검증(validator)에 추가한다.
- 삽질 조금 했는데.. 기존에 fileName을 requiredKey로 받던 잡을 run.id만 받도록 변경하니 배치 동작을 안함 🤔
  
```
2022-02-01 17:54:45.191  INFO 21619 --- [  restartedMain] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=basicJob]] launched with the following parameters: [{}]
2022-02-01 17:54:45.239  INFO 21619 --- [  restartedMain] o.s.batch.core.job.SimpleStepHandler     : Step already complete or not restartable, so no action to execute: StepExecution: id=1, version=3, name=step1, status=COMPLETED, exitStatus=COMPLETED, readCount=0, filterCount=0, writeCount=0 readSkipCount=0, writeSkipCount=0, processSkipCount=0, commitCount=1, rollbackCount=0, exitDescription=
2022-02-01 17:54:45.255  INFO 21619 --- [  restartedMain] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=basicJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 32ms
```

### 잡 리스너 적용하기
- 모든 잡은 생명주기를 갖는다.
- 잡 실행과 관련이 있다면 `JobExecutionListener`를 사용할 수 있다.
  - beforeJob과 afterJob 두 메서드를 제공한다.
- 잡 리스너를 작성하는 두 가지 방법이 있따.
  - `JobExectionListener` 인터페이스 구현
  - @BeforeJob, @AfterJob 애너테이션을 사용

``` java
    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("basicJob")
                .validator(validator2())
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .listener(new JobLoggerListener())
                .build();
    }
```
-  `JobExectionListener` 인터페이스 구현하면 해당 구현체를 등록하면 된다.
-  애노테이션을 이용할 경우 스프링 배치에서 이 리스너를 잡에 주입하려면 래핑해야 한다.
-  래핑은 `JobListenerFactoryBean`을 사용한다.

```java
    @Bean
    public Job job2() {
        return this.jobBuilderFactory.get("basicJob2")
                .validator(validator3())
                .incrementer(new DailyJobTimeStamper())
                .start(step1())
                .listener(JobListenerFactoryBean.getListener(new JobLoggerListener()))
                .build();
    }
```

### ExecutionContext
- 웹 애플리케이션의 HttpSession 처럼 배치에서는 ExecutionContext에 상태를 저장한다.
- 배치 처리는 특성상 상태를 가지고 있다.
- 현재 어떤 스텝이 실행되고 있는지 알아야한다.
- 해당 스텝이 처리한 레코드 개수도 알아야 한다.
- 이러한 요소들은 배치에서 진행 중인 처리뿐 아니라 이전에 실패한 처리를 다시 시작하는데 필수 적이다.


### 스텝 구성 - 테스크릿 스텝
- 테스크릿은 두 가지 방법으로 정의 될 수 있다.
- `MethodInvokingTaskletAdapter`를 사용해서 사용자 코드를 태스크릿 스텝으로 정의할 수 있다.
- Tasklet 인터페이스의 execute 메소드를 구현하여 정의 할 수 있다.
  - Tasklet 구현체 처리가 완료되면 `RepeatStatus` 객체를 반환해야 한다.
    - RepeatStatus.CONTINUABLE: 스프링 배치에서 해당 테스크릿을 다시 실행하라고 말하는 것
    - RepeatStatus.FINISHED: 처리의 성공 여부에 관계 없이 이 태스크릿의 처리를 완료하고 다음 처리를 이어서 하겠다는 의미