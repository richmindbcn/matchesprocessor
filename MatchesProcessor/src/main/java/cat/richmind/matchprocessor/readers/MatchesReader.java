package cat.richmind.matchprocessor.readers;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;

public class MatchesReader extends AbstractItemCountingItemStreamItemReader<MatchReference> {
	private static final Logger LOG = Logger.getLogger(MatchesReader.class); 
	
	private List<MatchReference> matches;

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
		LOG.debug("Loading matchlist from execution context");
		//matches = new ArrayList<MatchReference>(MatchList.class.cast(context.get("matches.list")).getMatches());
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
