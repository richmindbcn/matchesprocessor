package cat.richmind.matchprocessor.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.http.HttpHost;
import org.apache.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import cat.richmind.matchprocessor.domain.Identifier;
import cat.richmind.matchprocessor.domain.Match;
import cat.richmind.matchprocessor.listeners.JobCompletionNotificationListener;
import cat.richmind.matchprocessor.processors.MatchProcessor;
import cat.richmind.matchprocessor.writers.ElasticsearchItemWriter;

@Configuration
@EnableBatchProcessing
@Import(DataSourceAutoConfiguration.class)
public class BatchConfiguration {
	/* log para tracear inicializaciones */
	private static final Logger LOG = Logger.getLogger(BatchConfiguration.class); 
	
	/* builders */
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	/* read - process - write */
	@Bean
	public FlatFileItemReader<Identifier> reader() {
		LOG.info("Creating reader bean...");
		Resource cpr = new ClassPathResource("match_ids.txt");
		return new FlatFileItemReaderBuilder<Identifier>()
			.name("reader")
			.resource(cpr)
			.delimited()
			.names(new String[]{"id"})
			.fieldSetMapper(new BeanWrapperFieldSetMapper<Identifier>(){{
				setTargetType(Identifier.class);
			}})
			.build();
	}
	
	@Bean
	public MatchProcessor processor() {
		LOG.info("Creating processor bean...");
		return new MatchProcessor();
	}
	
	@Bean
	public ElasticsearchItemWriter<Match> writer() {
		LOG.info("Creating writer bean...");
		return new ElasticsearchItemWriter<Match>();
	}
	
	/* Others */
	/* ES Client */
	@Bean
	public RestClient elasticClient(@Qualifier("props") Properties props) {
		LOG.info("Creating Elasticsearch REST client bean...");
		if (props != null) {
			LOG.info("ES HOST: " + props.getProperty("elasticsearch.host") + ":" + props.getProperty("elasticsearch.port"));
			return RestClient.builder(new HttpHost(props.getProperty("elasticsearch.host"), new Integer(props.getProperty("elasticsearch.port")).intValue(), null)).build();
		} else {
			LOG.error("\"props\" bean is null");
			return null;
		}
	}
	
	/* Config properties */
	@Bean("props")
	public Properties props() throws IOException {
		LOG.info("Retrieving application properties...");
		Properties props = new Properties();
		try (InputStream is = BatchConfiguration.class.getClassLoader().getResourceAsStream("app.properties")) {
			props.load(is);			
		}
		return props;
	}
	
	
	/* job - step */
	@Bean
	public Job retrieveMatchesJob(JobCompletionNotificationListener listener, @Qualifier("processMatches") Step processMatches) {
		return jobBuilderFactory.get("retrieveMatchesJob")
				.listener(listener)
				.flow(processMatches)
				.end()
				.build();
	}
	
	@Bean
	public Step processMatches(FlatFileItemReader<Identifier> reader, MatchProcessor processor, ElasticsearchItemWriter<Match> writer) {
		return stepBuilderFactory.get("processMatches")
				.<Identifier, Match> chunk(4)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}
}