package it.highersoft.crawler.domain;

import java.io.IOException;
import java.io.InputStream;

public class LinkResponse implements AutoCloseable {
    Link link;
    String contentType;
    int httpResponseCode;
    long contentLength;
    InputStream bodyStream;
    Runnable closeResourceCallback;

    @Override
    public void close() {
        closeResourceCallback.run();
    }

    public Resource getResource() {
        try {
            byte[] body = bodyStream.readAllBytes();
            return new Resource(link, contentType, contentLength, body);
        } catch (IOException e) {
            throw new RuntimeException("could not read body stream from: " + link.url().toString() + ", httpResponse=" + httpResponseCode);
        } finally {
            closeResourceCallback.run();
        }
    }
}
