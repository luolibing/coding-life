package cn.tim.batch.config;

import cn.tim.batch.entity.Person;
import cn.tim.batch.processor.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

/**
 * Created by LuoLiBing on 15/10/9.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


    /**
     * 读取
     * @return
     */
    @Bean
    public ItemReader<Person> reader() {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("sample-data.csv"));
        reader.setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"firstName", "lastName"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});
        }});
        return reader;
    }


    /**
     * 执行转换
     * @return
     */
    @Bean
    public ItemProcessor<Person, Person> processor() {
        return new PersonItemProcessor();
    }


    /**
     * 写
     * @param dataSource
     * @return
     */
    @Bean
    public ItemWriter<Person> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        writer.setSql("INSERT INTO people(first_name, last_name) VALUES(:firstName,:lastName)");
        writer.setDataSource(dataSource);
        return writer;
    }


    /**
     * 整个任务JOB
     * incrementer新增参数,设置监听,设置执行步骤
     * @param jobs
     * @param s1
     * @param listener
     * @return
     */
    @Bean
    public Job importUserJob(JobBuilderFactory jobs, Step s1, Step s2, JobExecutionListener listener) {
        return jobs.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                //.start(s1).next(s2)
                .flow(s1)
                .end()
                .build();
    }


    /**
     * 步骤1
     * @param stepBuilderFactory
     * @param reader
     * @param writer
     * @param processor
     * @return
     */
    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader,
                      ItemWriter<Person> writer, ItemProcessor<Person, Person> processor) {
        // 批量任务 读->处理->写入  块chunk(10) 10个一组
        return stepBuilderFactory.get("step1")
                .<Person, Person>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


}
