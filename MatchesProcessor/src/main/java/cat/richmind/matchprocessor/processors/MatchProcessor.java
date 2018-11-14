package cat.richmind.matchprocessor.processors;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.Match;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.api.request.ratelimit.RateLimitException;
import net.rithms.riot.constant.Platform;

/**
 * @author ideltoro
 *
 */
@Scope("step")
public class MatchProcessor implements ItemProcessor<MatchReference, Match> {
	private static final Logger LOG = LoggerFactory.getLogger(MatchProcessor.class);

	@Autowired
	private RiotApi riotApi;
	
	@Autowired
	private Properties props;
	
	@Autowired
	private Summoner summ;
	
	@Override
	public Match process(MatchReference match) throws RiotApiException, InterruptedException {
		LOG.info("Processing match " + match.getGameId());
		Match result = null;
		try {
			 result = riotApi.getMatch(Platform.valueOf(props.getProperty("riot.api.default.region")), match.getGameId(), summ.getAccountId());
		} catch (RiotApiException e) {
			if (e.getClass().equals(RateLimitException.class)) {
				RateLimitException rle = RateLimitException.class.cast(e);
				LOG.warn("Rate limit exceeded. Retrying after " + rle.getRetryAfter() + " seconds.");
				Thread.sleep((rle.getRetryAfter() + 1) * 1000);
				result = riotApi.getMatch(Platform.valueOf(props.getProperty("riot.api.default.region")), match.getGameId(), summ.getAccountId());
			} else {
				throw e;
			}
		}
		return result;
	}
}