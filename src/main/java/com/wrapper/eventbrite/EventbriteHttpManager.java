package com.wrapper.eventbrite;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.wrapper.eventbrite.exceptions.BadRequestException;
import com.wrapper.eventbrite.exceptions.EventbriteWebApiException;
import com.wrapper.eventbrite.exceptions.RequestNotFoundException;
import com.wrapper.eventbrite.exceptions.UnauthorizedRequestException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.cache.CacheResponseStatus;
import org.apache.http.client.cache.HttpCacheContext;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.cache.CachingHttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;

public class EventbriteHttpManager implements IHttpManager {

    private final CloseableHttpClient httpClient;

    public EventbriteHttpManager() {
        RequestConfig requestConfig = RequestConfig
                .custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();

        this.httpClient = CachingHttpClients
                .custom()
                .setDefaultRequestConfig(requestConfig)
                .disableCookieManagement() // removes warning trying to set cookie from different host
                .build();
    }

    @Override
    public String get(URI uri, Header[] headers) throws IOException, EventbriteWebApiException {
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeaders(headers);

        HttpResponse httpResponse = executeRequest(httpGet);
        String responseBody = getResponseBody(httpResponse);

        httpGet.releaseConnection();
        return responseBody;
    }

    private HttpResponse executeRequest(HttpRequestBase method) throws IOException {
        HttpCacheContext context = HttpCacheContext.create();
        HttpResponse response = httpClient.execute(method, context);

        CacheResponseStatus responseStatus = context.getCacheResponseStatus();
        switch (responseStatus) {
            case CACHE_HIT:
                EventbriteApi.LOGGER.log(
                        Level.CONFIG,
                        "A response was generated from the cache with no requests sent upstream");
                break;
            case CACHE_MISS:
                EventbriteApi.LOGGER.log(
                        Level.CONFIG,
                        "A response came from the upstream server");
                break;
        }

        return response;
    }

    private static String getResponseBody(HttpResponse httpResponse) throws IOException, EventbriteWebApiException {
        final StatusLine statusLine = httpResponse.getStatusLine();
        final String responseBody = httpResponse.getEntity() != null
                ? EntityUtils.toString(httpResponse.getEntity(), "UTF-8")
                : null;

        String errorMessage = statusLine.getReasonPhrase();
        if (responseBody != null && !responseBody.equals("")) {
            try {
                final JsonElement jsonElement = new JsonParser().parse(responseBody);

                if (jsonElement.isJsonObject()) {
                    final JsonObject jsonObject = new JsonParser().parse(responseBody).getAsJsonObject();

                    if (jsonObject.has("error")) {
                        if (jsonObject.has("error_description")) {
                            errorMessage = jsonObject.get("error_description").getAsString();
                        } else if (jsonObject.get("error").isJsonObject() && jsonObject.getAsJsonObject("error").has("message")) {
                            errorMessage = jsonObject.getAsJsonObject("error").get("message").getAsString();
                        }
                    }
                }
            } catch (JsonSyntaxException e) {

            }
        }

        switch (statusLine.getStatusCode()) {
            case HttpStatus.SC_UNAUTHORIZED:
                throw new UnauthorizedRequestException(errorMessage);
            case HttpStatus.SC_BAD_REQUEST:
                throw new BadRequestException(errorMessage);
            case HttpStatus.SC_NOT_FOUND:
                throw new RequestNotFoundException(errorMessage);
        }

        return responseBody;
    }
}
