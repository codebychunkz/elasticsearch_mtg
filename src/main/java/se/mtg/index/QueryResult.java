package se.mtg.index;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author David
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryResult {
    
    private final Hits hits;
    
    @JsonCreator
    public QueryResult(@JsonProperty("hits") Hits hits) {
        this.hits = hits;
    }

    public Hits getHits() {
        return hits;
    }
}
 