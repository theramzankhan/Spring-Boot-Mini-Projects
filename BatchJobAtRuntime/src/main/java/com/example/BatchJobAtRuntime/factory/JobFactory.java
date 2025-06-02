package com.example.BatchJobAtRuntime.factory;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.BatchJobAtRuntime.entity.User;

@Component
public class JobFactory {
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;
    
    public Job createJob(String jobName, MultipartFile file) throws Exception {

        FlatFileItemReader<User> reader = new FlatFileItemReader<>();
        reader.setResource(new InputStreamResource(file.getInputStream()));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<User>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("id", "name", "age", "city");
            }});
            setFieldSetMapper(fieldSet -> {
                User user = new User();
                user.setId(fieldSet.readInt("id"));
                user.setName(fieldSet.readString("name"));
                user.setAge(fieldSet.readInt("age"));
                user.setCity(fieldSet.readString("city"));
                return user;
            });
        }});

        ItemProcessor<User, User> processor = user -> {
            user.setName(user.getName().toUpperCase());
            return user;
        };

        JdbcBatchItemWriter<User> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("INSERT INTO user (id, name, age, city) VALUES (?, ?, ?, ?)");
        writer.setItemPreparedStatementSetter((user, ps) -> {
            ps.setInt(1, user.getId());
            ps.setString(2, user.getName());
            ps.setInt(3, user.getAge());
            ps.setString(4, user.getCity());
        });
        writer.afterPropertiesSet();

        Step step = stepBuilderFactory.get("step-" + jobName)
                .<User, User>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

        return jobBuilderFactory.get("job-" + jobName)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

}
