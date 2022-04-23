package it.highersoft.crawler;

import it.highersoft.crawler.domain.Link;
import it.highersoft.crawler.domain.LinkLoader;
import it.highersoft.crawler.domain.Resource;
import it.highersoft.crawler.domain.ResourceProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class LinkExtractor implements ResourceProcessor {
    private final static String HTML_CONTENT_TYPE = "text/html";
    private final static UrlValidator URL_VALIDATOR = new UrlValidator(new String[]{"http", "https"});
    private final LinkLoader linkLoader;

    public LinkExtractor(LinkLoader linkLoader) {
        this.linkLoader = linkLoader;
    }

    @Override
    public void process(Resource resource) {
        if (resource.contentType().startsWith(HTML_CONTENT_TYPE)) {
            String body = new String(resource.body(), StandardCharsets.UTF_8);
            Document document = Jsoup.parse(body);
            Elements aElements = document.select("a");

            final Set<Link> extractedLinks = new HashSet<>();

            aElements.stream()
                    .map(element -> element.attr("href"))
                    .filter(url -> !url.isBlank())
                    .map(url -> buildLinkUri(url, resource.link()))
                    .filter(Objects::nonNull)
                    .forEach(extractedLinks::add);

            linkLoader.queueLinks(extractedLinks);
        }
    }

    private Link buildLink(String urlStr) {
        try {
            URL url = new URL(urlStr);
            return new Link(url);
        } catch (MalformedURLException e) {
            //todo:
        }

        return null;
    }

    private Link buildLinkUri(String urlStr, Link parentLink) {
        try {
            URI uri = new URI(urlStr);
            if (uri.isAbsolute()) {
                return new Link(uri.toURL());
            }
            String missingPart = parentLink.url().getProtocol() + parentLink.url().getHost();
            URL url = new URL(missingPart + "/" + uri.getPath());
            return new Link(url);
        } catch (MalformedURLException | URISyntaxException e) {
            log.error("Invalid URL: " + urlStr);
        }

        return null;
    }


//
}
