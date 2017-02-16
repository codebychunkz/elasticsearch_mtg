package se.mtg;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import se.mtg.index.Card;
import se.mtg.index.ElasticCardIndexer;
import se.mtg.index.IndexResult;

/**
 *
 * @author David
 */
public class Initializer {

    public void initialize(final ElasticCardIndexer indexer) {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Card> cards;
        try (InputStream in = Initializer.class.getResourceAsStream("/mtg/AllCards.json")) {
            cards = mapper.readValue(in, new TypeReference<Map<String, Card>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Function<Card, Optional<IndexResult>> mapCardResult = (Card card) -> {

            Optional res;
            try {
                IndexResult indexResult = indexer.indexCard(card);
                res = Optional.ofNullable(indexResult);

            } catch (IOException ex) {
                res = Optional.empty();
            }

            return res;
        };

        long errors = cards.values().parallelStream()
                .map(mapCardResult)
                .filter((Optional<IndexResult> opt) -> opt.isPresent() && !opt.get().isCreated())
                .count();

        if (errors != 0) {
            throw new RuntimeException("Errors initializing: " + errors);
        }
    }
}
