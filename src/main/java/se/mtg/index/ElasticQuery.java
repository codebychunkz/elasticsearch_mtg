package se.mtg.index;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

/**
 * Ugly but works :S
 * 
 * @author David
 */
public class ElasticQuery {

    private final Map<String, Map<String, Map<String, String>>> match;

    public ElasticQuery(Map<String, Map<String, Map<String, String>>> match) {
        this.match = match;
    }

    @JsonProperty("query")
    public Map<String, Map<String, Map<String, String>>> rawQuery() {
        return match;
    }

    public static final class ElasticQueryBuilder {

        private final Map<String, Map<String, String>> query;
        
        public ElasticQueryBuilder() {
            query = new HashMap<>();
        }
        
        public ElasticQueryBuilder addFuzzyAttribute(String attribute, String value) {
            Map<String, String> fuzzyQuery = new HashMap<>();
            fuzzyQuery.put("fuzziness", "auto");
            fuzzyQuery.put("operator", "and");
            fuzzyQuery.put("query", value);

            query.put(attribute, fuzzyQuery);
            
            return this;
        }
        
        public ElasticQuery build() {
            Map<String, Map<String, Map<String, String>>> inner = new HashMap<>();
            inner.put("match", query);
            return new ElasticQuery(inner);
        }
    }
}
