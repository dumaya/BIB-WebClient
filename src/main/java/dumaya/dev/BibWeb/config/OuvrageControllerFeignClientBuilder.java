package dumaya.dev.BibWeb.config;

import dumaya.dev.BibWeb.service.OuvrageClient;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

public class OuvrageControllerFeignClientBuilder {

    private OuvrageClient ouvrageClient = createClient(OuvrageClient.class, "http://localhost:8001/ouvrages");

    private static <T> T createClient(Class<T> type, String uri) {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(type))
                .logLevel(Logger.Level.FULL)
                .target(type, uri);
    }

    public OuvrageClient getOuvrageClient() {
        return ouvrageClient;
    }
}
