package it.highersoft.crawler.domain;

import it.highersoft.crawler.domain.Link;
import it.highersoft.crawler.domain.LinkResponse;

public interface HttpClient {
    LinkResponse get(Link link);
}
