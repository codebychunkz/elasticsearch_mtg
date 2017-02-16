package se.mtg;

import com.squareup.okhttp.OkHttpClient;
import java.util.Base64;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import se.mtg.commandline.SystemOptions;
import se.mtg.index.Card;
import se.mtg.index.ElasticCardIndexer;
import se.mtg.index.Hit;

/**
 *
 * @author David
 */
//gradle execute -Pappargs="-elasticurl http://192.168.99.100:9200 -username elastic -password changeme -attr name -value rage"
public class MtgMain {

    public static void main(String[] args) throws Exception {
        Context ctx = createContext(args);
        
        String userNamePassword = String.format("%s:%s", ctx.getElasticUsername(), ctx.getElasticPassword());
        String authHeader = "Basic " + Base64.getEncoder().encodeToString(userNamePassword.getBytes());
        ElasticCardIndexer indexer = new ElasticCardIndexer(new OkHttpClient(), ctx.getElasticBaseUrl(), authHeader);

        if (ctx.isInitialized()) {
            new Initializer().initialize(indexer);
        }
        
        indexer.fuzzySearch(ctx.getSearchAttr(), ctx.getSearchValue())
                .forEach(MtgMain::prettyPrintcard);
    }

    private static Context createContext(String[] args) throws ParseException {
        CommandLineParser cmdParser = new DefaultParser();
        CommandLine arguments = cmdParser.parse(SystemOptions.DEFAULT_OPTIONS, args);

        boolean doInitialize = arguments.hasOption(SystemOptions.INITIALIZE);
        String elasticUrl = arguments.getOptionValue(SystemOptions.ELASTICSEARCH_URL);
        String elasticUsername = arguments.getOptionValue(SystemOptions.ELASTICSEARCH_USERNAME);
        String elasticPassword = arguments.getOptionValue(SystemOptions.ELASTICSEARCH_PASSWORD);
        String searchAttr = arguments.getOptionValue(SystemOptions.SEARCH_ATTRIBUTE);
        String searchValue = arguments.getOptionValue(SystemOptions.SEARCH_VALUE);
        
        Context ctx = new Context(elasticUrl, doInitialize, elasticUsername, elasticPassword, searchAttr, searchValue);

        return ctx;
    }
    
    private static void prettyPrintcard(Hit hit) {
        StringBuilder sb = new StringBuilder();
        
        Card card = hit.getCard();
        sb.append("Score: ").append(hit.getScore()).append("\n")
                .append("Name: ").append(card.getName()).append("\n")
                .append("Text: ").append(card.getText()).append("\n")
                .append("Type: ").append(card.getTypes()).append("\n");
        System.out.println(sb.toString());
    }
}
