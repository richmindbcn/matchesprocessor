package cat.richmind.matchprocessor.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Scope;

import cat.richmind.matchprocessor.domain.Identifier;
import cat.richmind.matchprocessor.domain.Match;

/**
 * @author ideltoro
 *
 */
@Scope("step")
public class MatchProcessor implements ItemProcessor<Identifier, Match> {
	private static final Logger LOG = LoggerFactory.getLogger(MatchProcessor.class);
	// inyectar el cliente de rito, sustituir Object por el tipo de datos concreto (MatchDTO?)
	// inyectar un segundo processor en caso de querer transformar el objeto de la API de rito
	
	@Override
	public Match process(Identifier matchId) throws Exception {
		LOG.info("Processing id " + matchId + "...");
		return new Match(matchId.getId(), "kataRichmind", new Long(Math.round(Math.random())).intValue());
	}
}