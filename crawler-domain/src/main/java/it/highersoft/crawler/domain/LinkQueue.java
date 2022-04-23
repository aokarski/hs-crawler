package it.highersoft.crawler.domain;

import it.highersoft.crawler.domain.Link;

import java.util.Optional;

public interface LinkQueue {
    Optional<Link> poll();
}
