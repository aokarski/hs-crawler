package it.highersoft.crawler.domain;

import java.util.Optional;
import java.util.Set;

public interface LinksQueue {
    Optional<Link> poll();
    void queueLinks(Set<Link> links);
}
