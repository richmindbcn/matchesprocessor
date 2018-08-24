package cat.richmind.matchprocessor.writers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ElasticsearchItemWriter<T extends Serializable> implements ItemWriter<T> {
	private static final Logger LOG = Logger.getLogger(ElasticsearchItemWriter.class);
	
	@Autowired
	@Qualifier("elasticClient")
	RestClient client;
	
	@PostConstruct
	public void init() {
		LOG.info("Elasticsearch REST client is " + (client != null ? "not " : "") + "null");
	}
	
	@Override
	public void write(List<? extends T> items) throws Exception {
		LOG.info("Write started");
		if (client != null) {
			for (T item : items) {
				System.out.println(item.toString());
				// envío a elasticsearch
			}
		} else {
			LOG.error("Elasticsearch REST Client is not initialized");
		}
	}
}