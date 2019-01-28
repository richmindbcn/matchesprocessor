package cat.richmind.matchprocessor.readers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import cat.richmind.matchprocessor.utils.Constants;
import cat.richmind.matchprocessor.utils.ContextWrapper;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

public class MatchesReader extends AbstractItemCountingItemStreamItemReader<MatchReference> {
	private static final Logger LOG = LoggerFactory.getLogger(MatchesReader.class);
	
	private List<MatchReference> matches;
	
	@Autowired
	private RiotApi riotApi;
	
	@Autowired
	private ContextWrapper wrapper;
	
	public MatchesReader() {
		super();
		super.setName("reader");
	}

	@Override
	public MatchReference read() {
		if (super.getCurrentItemCount() >= matches.size()) {
			return null;
		}
		
		MatchReference item = doRead();
		super.setCurrentItemCount(getCurrentItemCount() + 1);

		return item;
	}
	
	@Override
	public MatchReference doRead() {
		return matches.get(super.getCurrentItemCount());
	}
	
	@Override
	protected void doOpen() throws Exception {
		LOG.debug("Loading matchlist...");
		// KATARINA
		Set<Integer> champs = new HashSet<>();
		champs.add(55);
		// PRESEASON 7 - SEASON 7 - PRESEASON 8 - SEASON 8
		Set<Integer> seasons = new HashSet<>();
		seasons.add(Constants.Seasons.PRESEASON_2017);
		seasons.add(Constants.Seasons.SEASON_2017);
		seasons.add(Constants.Seasons.PRESEASON_2018);
		seasons.add(Constants.Seasons.SEASON_2018);
		// 5vs5 DRAFT PICK - 5vs5 RANKED SOLOQ - 5vs5 FLEX RANKED
		Set<Integer> queues = new HashSet<>();
		queues.add(Constants.Queues.SR_5V5_BLIND);
		queues.add(Constants.Queues.SR_5V5_DRAFT);
		queues.add(Constants.Queues.SR_5V5_SOLOQ_RANKED);
		queues.add(Constants.Queues.SR_5V5_FLEX_RANKED);
		matches = riotApi.getMatchListByAccountId(Platform.valueOf(String.class.cast(wrapper.get("riot.api.default.region"))), 
			Summoner.class.cast(wrapper.get("summ")).getAccountId(), champs, queues, seasons).getMatches();
		super.setMaxItemCount(matches.size());
	}

	@Override
	protected void doClose() throws Exception {
		matches = null;
	}

	public List<MatchReference> getMatches() {
		return matches;
	}

	public void setMatches(List<MatchReference> matches) {
		this.matches = matches;
	}
}
