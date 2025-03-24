package com.example.kata_batch_transform.config;



import javax.sql.DataSource;

import com.example.kata_batch_transform.processor.NumberProcess;
import com.example.kata_batch_transform.reader.NumberReader;
import com.example.kata_batch_transform.writer.NumberWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;



@Configuration
public class BatchConfig{


    @Value("${input.file}")
    private String inputFile;
    @Value("${output.file}")
    private String outputFile;

    @Value("${batch.chunkSize}")
    private int chunkSize;

    @Value("${batch.job.name}")
    private String jobName;

    @Value("${batch.step.name}")
    private String stepName;

    @Bean
    public NumberReader reader(){
        return  new NumberReader(inputFile);
    }

    @Bean
    public NumberProcess process() {
        return new NumberProcess();
    }

    @Bean
    public NumberWriter writer() {
        return new NumberWriter(outputFile);
    }


    // Stocker l'état des jobs dans une dataSource
    @Bean
    public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    //gérer les transactions sur la base de donnée
    @Bean
    public JdbcTransactionManager batchTransactionManager(DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }

    //créer un base de donnée embarqué en memoire
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .build();
    }
    //création du Step
    @Bean
    public Step transformStep( JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(stepName, jobRepository)
                .<Integer, String>chunk(chunkSize, transactionManager)
                .reader(reader())
                .processor(process())
                .writer(writer())
                .build();
    }
    //Création du job
    @Bean
    public Job transformJob(Step transformStep,JobRepository jobRepository) {
        return new JobBuilder(jobName, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(transformStep)
                .build();
    }
    //lancer un JOB
    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository, TaskExecutor taskExecutor) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(taskExecutor) ;
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }



}