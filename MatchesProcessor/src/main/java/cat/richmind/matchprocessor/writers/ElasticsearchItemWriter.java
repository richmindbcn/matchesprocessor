package cat.richmind.matchprocessor.writers;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.gson.Gson;

import cat.richmind.matchprocessor.utils.ContextWrapper;
import cat.richmind.matchprocessor.utils.DLQUtils;
import net.rithms.riot.api.Dto;

public class ElasticsearchItemWriter<T extends Serializable> extends AbstractItemStreamItemWriter<T> {
	private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchItemWriter.class);
	
	@Autowired
	@Qualifier("elasticClient")
	RestClient client;
	
	@Autowired
	ContextWrapper wrapper;
	
	@PostConstruct
	public void init() {
		super.setName("writer");
		try {
			client.performRequest(new HttpGet().getMethod(), String.class.cast(wrapper.get("elasticsearch.index")), new BasicHeader[] {});
		} catch (Exception e) {
			if (e.getClass().equals(ResponseException.class)) {
				if (ResponseException.class.cast(e).getResponse().getStatusLine().getStatusCode() == 404) {
					try {
						client.performRequest(new HttpPut().getMethod(), String.class.cast(wrapper.get("elasticsearch.index")), new HashMap<String,String>(), new StringEntity(String.class.cast(wrapper.get("elasticsearch.index.config")), ContentType.APPLICATION_JSON), new BasicHeader[] {});
					} catch (Exception e1) {
						throw new RuntimeException("Index check failed.", e1);
					}
				}
			} else {
				throw new RuntimeException("Index check failed.", e);
			}
		}
	}
	
	@PreDestroy
	public void destroy() {
		try {
			client.close();
			client = null;
		} catch (IOException e) {
			LOG.error("Elasticsearch client cannot be closed.", e);
		}
	}
	
	@Override
	public void write(List<? extends T> items) throws Exception {
		LOG.info("Write started");
		Gson gson = new Gson();
		for (T item : items) {
			try {
				Response res = client.performRequest(new HttpPost().getMethod(), String.class.cast(wrapper.get("elasticsearch.index")) + "/_doc", new HashMap<String,String>(), new StringEntity(gson.toJson(((Dto) item)), "UTF-8"), new BasicHeader[] {new BasicHeader("Content-Type", "application/json")});
				if (res.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
					LOG.error("Error while sending item " + item.toString() + ": " + res.toString() + ". It will be skipped."); // dlq
				}
			} catch (Exception e) {
				LOG.error("Error while sending item " + item.toString() + ". It will be sent to dlq."); // dlq
				DLQUtils.saveInDLQ(gson.toJson(((Dto) item)));
			}
		}
	}
}