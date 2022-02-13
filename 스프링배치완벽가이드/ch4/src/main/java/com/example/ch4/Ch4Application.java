package com.example.ch4;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobListenerFactoryBean;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@EnableBatchProcessing
public class Ch4Application {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("basicJob")
                .validator(validator2())
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .listener(new JobLoggerListener())
                .build();
    }

    @Bean
    public Job job2() {
        return this.jobBuilderFactory.get("basicJob2")
                .validator(validator3())
                .incrementer(new DailyJobTimeStamper())
                .start(step1())
                .listener(JobListenerFactoryBean.getListener(new JobLoggerListener()))
                .build();
    }

    @Bean
    public Job job3() {
        return this.jobBuilderFactory.get("basicJob3")
                .validator(validator2())
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    String name = (String) chunkContext.getStepContext()
                            .getJobParameters()
                            .get("fileName");
                    System.out.println(name + " Hello World!");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @StepScope
    @Bean
    public Tasklet helloWorldTasklet(@Value("#{jobParameters['name']}") String name) {
        return ((contribution, chunkContext) -> {
            System.out.println(name + "Hello World!");
            return RepeatStatus.FINISHED;
        });
    }


    @Bean
    DefaultJobParametersValidator validator2() {
        DefaultJobParametersValidator defaultJobParametersValidator = new DefaultJobParametersValidator();
        defaultJobParametersValidator.setOptionalKeys(new String[]{"run.id"});
        defaultJobParametersValidator.afterPropertiesSet();
        return defaultJobParametersValidator;
    }

    @Bean
    DefaultJobParametersValidator validator3() {
        DefaultJobParametersValidator defaultJobParametersValidator = new DefaultJobParametersValidator();
        defaultJobParametersValidator.setOptionalKeys(new String[]{"currentDate"});
        defaultJobParametersValidator.afterPropertiesSet();
        return defaultJobParametersValidator;
    }


    @Bean
    public CompositeJobParametersValidator validator() {
        CompositeJobParametersValidator validator = new CompositeJobParametersValidator();

        DefaultJobParametersValidator defaultJobParametersValidator = new DefaultJobParametersValidator();

        defaultJobParametersValidator.setRequiredKeys(new String[]{"fileName"});
        defaultJobParametersValidator.setOptionalKeys(new String[]{"name", "run.id"});

        defaultJobParametersValidator.afterPropertiesSet();

        validator.setValidators(Arrays.asList(new ParameterValidator(), defaultJobParametersValidator));

        return validator;
    }

    public static void main(String[] args) {
        SpringApplication.run(Ch4Application.class, args);
    }

}
