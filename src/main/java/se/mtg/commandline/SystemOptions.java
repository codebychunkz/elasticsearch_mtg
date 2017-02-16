package se.mtg.commandline;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 *
 * @author David
 */
public class SystemOptions {

    public static final String INITIALIZE = "init";

    public static final String ELASTICSEARCH_URL = "elasticurl";

    public static final String ELASTICSEARCH_USERNAME = "username";

    public static final String ELASTICSEARCH_PASSWORD = "password";
    
    public static final String SEARCH_ATTRIBUTE = "attr";
    
    public static final String SEARCH_VALUE = "value";

    public static final Options DEFAULT_OPTIONS = new Options()
            .addOption(Option.builder(INITIALIZE)
                    .hasArg(false)
                    .required(false)
                    .build())
            .addOption(Option.builder(ELASTICSEARCH_URL)
                    .hasArg(true)
                    .required(true)
                    .build())
            .addOption(Option.builder(ELASTICSEARCH_USERNAME)
                    .hasArg(true)
                    .required(true)
                    .build())
            .addOption(Option.builder(ELASTICSEARCH_PASSWORD)
                    .hasArg(true)
                    .required(true)
                    .build())
            .addOption(Option.builder(SEARCH_ATTRIBUTE)
                    .hasArg(true)
                    .required(true)
                    .build())
            .addOption(Option.builder(SEARCH_VALUE)
                    .hasArg(true)
                    .required(true)
                    .build());
}
