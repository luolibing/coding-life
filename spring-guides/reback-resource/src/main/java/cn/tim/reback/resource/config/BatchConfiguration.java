//package cn.tim.reback.resource.config;
//
//import cn.tim.reback.resource.entity.Resource;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecutionListener;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
//import org.springframework.batch.item.database.JpaPagingItemReader;
//import org.springframework.batch.item.file.FlatFileItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
///**
// * Created by LuoLiBing on 16/3/14.
// */
//@Configuration
//@EnableBatchProcessing
//public class BatchConfiguration {
//
//    @Autowired
//    EntityManagerFactory entityManagerFactory;
//
//    /**
//     * 读取
//     * @return
//     */
//    @Bean
//    public ItemReader<Resource> reader() {
//        JpaPagingItemReader<Resource> reader = new JpaPagingItemReader<>();
//        reader.setEntityManagerFactory(entityManagerFactory);
//        reader.setQueryString("select r from Resource r");
//        reader.setPageSize(100);
//        return reader;
//    }
//
//
//    /**
//     * 执行转换
//     * @return
//     */
//    @Bean
//    public ItemProcessor<Person, Person> processor() {
//        return new PersonItemProcessor();
//    }
//
//
//    /**
//     * 写
//     * @param dataSource
//     * @return
//     */
//    @Bean
//    public ItemWriter<Person> writer(DataSource dataSource) {
//        FlatFileItemWriter<Resource> writer = new FlatFileItemWriter<>();
//        writer.write();
//        return writer;
//    }
//
//
//    /**
//     * 整个任务JOB
//     * incrementer新增参数,设置监听,设置执行步骤
//     * @param jobs
//     * @param s1
//     * @param listener
//     * @return
//     */
//    @Bean
//    public Job importUserJob(JobBuilderFactory jobs, Step s1, Step s2, JobExecutionListener listener) {
//        return jobs.get("importUserJob")
//                .incrementer(new RunIdIncrementer())
//                .listener(listener)
//                //.start(s1).next(s2)
//                .flow(s1)
//                .end()
//                .build();
//    }
//
//
//    /**
//     * 步骤1
//     * @param stepBuilderFactory
//     * @param reader
//     * @param writer
//     * @param processor
//     * @return
//     */
//    @Bean
//    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader,
//                      ItemWriter<Person> writer, ItemProcessor<Person, Person> processor) {
//        // 批量任务 读->处理->写入  块chunk(10) 10个一组
//        return stepBuilderFactory.get("step1")
//                .<Person, Person>chunk(10)
//                .reader(reader)
//                .processor(processor)
//                .writer(writer)
//                .build();
//    }
//
//
//}
