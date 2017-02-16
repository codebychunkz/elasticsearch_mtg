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
public class IndexResult {

    private final boolean created;

    @JsonCreator
    public IndexResult(@JsonProperty("created") boolean created) {
        this.created = created;
    }

    public boolean isCreated() {
        return created;
    }
}
