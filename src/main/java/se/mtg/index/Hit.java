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
public class Hit {
    
    private final Card card;
    
    private final double score;
    
    @JsonCreator
    public Hit(@JsonProperty("_source") Card card,
               @JsonProperty("_score") double score) {
        this.card = card;
        this.score = score;
    }
    
    public Card getCard() {
        return card;
    }
    
    public double getScore() {
        return score;
    }
}