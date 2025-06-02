package com.example.BootBatchEx3.config;

import java.beans.Customizer;

import javax.sql.DataSource;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.job.JobStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.example.BootBatchEx3.entity.Customer;

@Configuration
public class SpringBatchConfig {
	
	int count = 0;
	
	@Bean
	public Job jobBean(
			JobRepository jobRepository, 
			JobCompletionNotificationImp listener,
			Step steps
			) {
		return new JobBuilder("job", jobRepository)
				.listener(listener)
				.start(steps)
				.build();
	}
	
	@Bean
	public Step steps(
			JobRepository jobRepository,
			DataSourceTransactionManager transactionManager,
			ItemReader<Customer> reader,
			ItemProcessor<Customer, Customer> processor,
			ItemWriter<Customer> writer
			) {
		return new StepBuilder("jobStep", jobRepository)
				.<Customer, Customer>chunk(10, transactionManager)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.listener(new ChunkListener() {
					@Override
					public void beforeChunk(ChunkContext context) {
							count++;
							System.out.println("Before processing a chunk no: " + count);
					
					}
					
					@Override
					public void afterChunk(ChunkContext context) {
						System.out.println("Finished processing a chunk no: " + count + " successfully.");
					}
					
					@Override
					public void afterChunkError(ChunkContext context) {
						System.out.println("Error occurred while processing a chunk no: " + count + "!");
					}
				})
				.build();
	}
	
	//reader
	
	@Bean
	public FlatFileItemReader<Customer> itemReader() {
		return new FlatFileItemReaderBuilder<Customer>()
				.name("itemReader")
				.resource(new ClassPathResource("customers.csv"))
				.delimited()
				.names("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob")
				.targetType(Customer.class)
				.strict(false)  // Ignores missing files or blank lines
				.build();
	}
	
	//processor
	
	@Bean
	public ItemProcessor<Customer, Customer> itemProcessor() {
		return new CustomItemProcessor();
	}
	
	//writer
	
//	@Bean
//    public ItemWriter<Customer> itemWriter(DataSource dataSource) {
//
//        return new JdbcBatchItemWriterBuilder<Customer>()
//        		.sql("INSERT INTO customer_info (id,firstName,lastName,email,gender,contactNo,country,dob) " +
//			              "VALUES (:id, :firstName, :lastName, :email, :gender, :contactNo, :country, :dob)")
//                .dataSource(dataSource)
//                .beanMapped()
//                .build();
//    }

//	For writing as well as logging purpose
	@Bean
	public ItemWriter<Customer> itemWriter(DataSource dataSource) {
	    JdbcBatchItemWriter<Customer> delegate = new JdbcBatchItemWriterBuilder<Customer>()
	        .sql("INSERT INTO customer_info (id, firstName, lastName, email, gender, contactNo, country, dob) " +
	             "VALUES (:id, :firstName, :lastName, :email, :gender, :contactNo, :country, :dob)")
	        .dataSource(dataSource)
	        .beanMapped()
	        .build();

	    // 🛠️ This line is essential
	    delegate.afterPropertiesSet();

	    return items -> {
	        System.out.println("🔹 Writing chunk of " + items.size() + " customers:");
	        for (Customer customer : items) {
	            System.out.println("   → " + customer);
	        }
	        delegate.write(items);
	    };
	}
	
}
