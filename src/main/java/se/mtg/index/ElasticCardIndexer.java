package se.mtg.index;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author David
 */
public class ElasticCardIndexer {

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private final String baseUrl;

    private final String authHeader;

    private final OkHttpClient client;

    public ElasticCardIndexer(OkHttpClient client, String baseUrl, String authHeader) {
        this.authHeader = authHeader;
        this.client = client;
        this.baseUrl = baseUrl;
    }

    public IndexResult indexCard(Card card) throws IOException {

        RequestBody requestBody = newCardHttpBody(card);

        Request request = newBaseRequest()
                .url(elasticUrl().build())
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        IndexResult result;
        try (ResponseBody responseBody = response.body()) {
            result = JSON_MAPPER.readValue(responseBody.bytes(), IndexResult.class);
        }

        return result;
    }

    public Stream<Hit> fuzzySearch(String attribute, String value) throws IOException {
        RequestBody requestBody = newSearchHttpBody(attribute, value);

        Request request = newBaseRequest()
                .url(elasticUrl().addPathSegment("_search").build())
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        Stream<Hit> cards;
        try (ResponseBody responseBody = response.body()) {
            ObjectMapper mapper = new ObjectMapper();
            QueryResult queryResult = mapper.readValue(responseBody.byteStream(), QueryResult.class);

            Collection<Hit> hits = Optional
                    .ofNullable(queryResult.getHits().getHits())
                    .orElse(Collections.emptyList());

            cards = hits.stream();
        }

        return cards;
    }
    
    private HttpUrl.Builder elasticUrl() {
        return HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("mtg")
                .addPathSegment("cards");
    }

    private RequestBody newCardHttpBody(Card card) throws JsonProcessingException {
        byte[] rawData = JSON_MAPPER.writeValueAsBytes(card);
        return RequestBody.create(JSON_MEDIA_TYPE, rawData);
    }

    private Request.Builder newBaseRequest() {
        return new Request.Builder()
                .addHeader("Authorization", authHeader);
    }

    private RequestBody newSearchHttpBody(String attribute, String value) throws JsonProcessingException {
        ElasticQuery query = new ElasticQuery.ElasticQueryBuilder().addFuzzyAttribute(attribute, value).build();
        byte[] rawBody = JSON_MAPPER.writeValueAsBytes(query);
        return RequestBody.create(JSON_MEDIA_TYPE, rawBody);
    }
}
