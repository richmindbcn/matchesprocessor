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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cat.richmind.matchprocessor.listeners.JobCompletionNotificationListener;
import cat.richmind.matchprocessor.processors.MatchProcessor;
import cat.richmind.matchprocessor.readers.MatchesReader;
import cat.richmind.matchprocessor.writers.ElasticsearchItemWriter;
import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.Match;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

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
	public MatchesReader matchesReader() {
		return new MatchesReader();
	}
	
	@Bean
	public MatchProcessor processor() {
		LOG.debug("Creating processor bean...");
		return new MatchProcessor();
	}
	
	@Bean
	public ElasticsearchItemWriter<Match> writer() {
		LOG.debug("Creating writer bean...");
		return new ElasticsearchItemWriter<Match>();
	}
	
	/* Others */
	/* Config properties */
	@Bean("props")
	public Properties props() throws IOException {
		LOG.debug("Retrieving application properties...");
		Properties props = new Properties();
		try (InputStream is = BatchConfiguration.class.getClassLoader().getResourceAsStream("app.properties")) {
			props.load(is);			
		}
		return props;
	}
	
	/* ES Client */
	@Bean
	public RestClient elasticClient(@Qualifier("props") Properties props) {
		LOG.debug("Creating Elasticsearch REST client bean...");
		return RestClient.builder(new HttpHost(props.getProperty("elasticsearch.host"), Integer.parseInt(props.getProperty("elasticsearch.port")), null)).build();
	}
	
	/* Riot API Bean */
	@Bean
	public RiotApi riotApi(@Qualifier("props") Properties props) throws RiotApiException {
		String apiKey = props.get("riot.api.key").toString();
		ApiConfig conf = new ApiConfig();
		conf.setKey(apiKey);
		return new RiotApi(conf);
	}
	
	@Bean("summ")
	public Summoner defaultSummonerData(RiotApi riotApi, Properties props) throws RiotApiException {
		Summoner summ = null;
		try {
			summ = riotApi.getSummonerByName(Platform.getPlatformByName(props.getProperty("riot.api.default.region").toLowerCase()), props.getProperty("riot.api.default.summonerName"));
		} catch (RiotApiException e) {
			throw e;
		}
		return summ;
	}
	
	
	/* job - step */
	@Bean
	public Job retrieveMatchesJob(JobCompletionNotificationListener listener, Step processMatches) {
		return jobBuilderFactory.get("retrieveMatchesJob")
				.listener(listener)
				.flow(processMatches)
				.end()
				.build();
	}
	
	@Bean
	public Step processMatches(MatchesReader reader, MatchProcessor processor, ElasticsearchItemWriter<Match> writer) {
		return stepBuilderFactory.get("processMatches")
				.<MatchReference, Match> chunk(5)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}
}