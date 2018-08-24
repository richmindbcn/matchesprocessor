package cat.richmind.matchprocessor.processors;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import cat.richmind.matchprocessor.domain.Identifier;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.Match;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.api.request.ratelimit.RateLimitException;
import net.rithms.riot.constant.Platform;

/**
 * @author ideltoro
 *
 */
@Scope("step")
public class MatchProcessor implements ItemProcessor<Identifier, Match> {
	private static final Logger LOG = LoggerFactory.getLogger(MatchProcessor.class);

	@Autowired
	private RiotApi riotApi;
	
	@Autowired
	private Properties props;
	
	@Autowired
	private ExecutionContext context;
	
	@Override
	public Match process(Identifier matchId) throws RiotApiException, InterruptedException {
		LOG.info("Processing id " + matchId + "...");
		Summoner summ = Summoner.class.cast(context.get("summonerData"));
		Match match = null;
		try {
			 match = riotApi.getMatch(Platform.valueOf(props.getProperty("riot.api.default.region")), Long.parseLong(matchId.getId()), summ.getAccountId());
		} catch (RiotApiException e) {
			if (e.getClass().equals(RateLimitException.class)) {
				RateLimitException rle = RateLimitException.class.cast(e);
				LOG.warn("Rate limit exceeded. Retrying after " + rle.getRetryAfter() + " seconds.");
				Thread.sleep((rle.getRetryAfter() + 1) * 1000);
				match = riotApi.getMatch(Platform.valueOf(props.getProperty("riot.api.default.region")), Long.parseLong(matchId.getId()), summ.getAccountId());
			} else {
				throw e;
			}
		}
		return match;
	}
}