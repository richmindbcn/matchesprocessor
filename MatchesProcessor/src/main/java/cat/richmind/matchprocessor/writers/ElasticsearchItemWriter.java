package cat.richmind.matchprocessor.writers;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PreDestroy;

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
	
	@PreDestroy
	public void destroy() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void write(List<? extends T> items) throws Exception {
		LOG.info("Write started");
		if (client != null) {
			for (T item : items) {
				LOG.info("Sending match " + item.toString() + " to Elasticsearch server");
				// envío a elasticsearch

			}
		} else {
			LOG.error("Elasticsearch REST Client is not initialized");
		}
	}
}