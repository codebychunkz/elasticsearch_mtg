package se.mtg.index;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author David
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {

    private final String layout;

    private final String name;

    private final String manaCost;

    private final List<String> colors;

    private final List<String> types;

    private final List<String> subtypes;

    private final String text;

    private final String power;

    private final String toughness;

    @JsonCreator
    public Card(@JsonProperty("layout") String layout,
            @JsonProperty("name") String name,
            @JsonProperty("manaCost") String manaCost,
            @JsonProperty("colors") List<String> colors,
            @JsonProperty("types") List<String> types,
            @JsonProperty("subtypes") List<String> subtypes,
            @JsonProperty("text") String text,
            @JsonProperty("power") String power,
            @JsonProperty("toughness") String toughness) {
        this.layout = layout;
        this.name = name;
        this.manaCost = manaCost;
        this.colors = colors;
        this.types = types;
        this.subtypes = subtypes;
        this.text = text;
        this.power = power;
        this.toughness = toughness;
    }

    public String getLayout() {
        return layout;
    }

    public String getName() {
        return name;
    }

    public String getManaCost() {
        return manaCost;
    }

    public List<String> getColors() {
        return colors;
    }

    public List<String> getTypes() {
        return types;
    }

    public List<String> getSubtypes() {
        return subtypes;
    }

    public String getText() {
        return text;
    }

    public String getPower() {
        return power;
    }

    public String getToughness() {
        return toughness;
    }
}
