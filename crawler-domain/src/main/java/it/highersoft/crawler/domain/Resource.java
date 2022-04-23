package it.highersoft.crawler.domain;

public record Resource(Link link, String contentType, long contentLength, byte[] body) {
}
