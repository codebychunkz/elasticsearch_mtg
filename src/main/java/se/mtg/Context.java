package se.mtg;

/**
 *
 * @author David
 */
public class Context {
    
    private final String elasticBaseUrl;
    
    private final String elasticUsername;
    
    private final String elasticPassword;
    
    private final String searchAttr;
    
    private final String searchValue;
    
    private final boolean initialized;

    public Context(String elasticBaseUrl, boolean initialized, String elasticUsername, String elasticPassword, String searchAttr, String searchValue) {
        this.elasticBaseUrl = elasticBaseUrl;
        this.initialized = initialized;
        this.elasticUsername = elasticUsername;
        this.elasticPassword = elasticPassword;
        this.searchAttr = searchAttr;
        this.searchValue = searchValue;
    }

    public String getElasticBaseUrl() {
        return elasticBaseUrl;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public String getElasticUsername() {
        return elasticUsername;
    }

    public String getElasticPassword() {
        return elasticPassword;
    }

    public String getSearchAttr() {
        return searchAttr;
    }

    public String getSearchValue() {
        return searchValue;
    }
}
