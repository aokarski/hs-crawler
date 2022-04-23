package it.highersoft.crawler.domain;

import java.util.Set;

public interface LinkLoader {
    long queueLinks(Set<Link> linksToScan);
}
