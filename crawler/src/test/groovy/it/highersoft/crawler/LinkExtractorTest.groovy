package it.highersoft.crawler

import it.highersoft.crawler.domain.Link
import it.highersoft.crawler.domain.LinkLoader
import it.highersoft.crawler.domain.Resource
import spock.lang.Specification

import java.nio.charset.Charset

class LinkExtractorTest extends Specification {
    def "should extract all valid links from html <a> elements"() {
        given:
        String body = """
            <a href="http://highersoft.it">
            <a href="https://highersoft.it">
            <a href="highersoft.it">
            <a href="www.highersoft.it">
        """
        def link = new Link(new URL("https://highersoft.it"))
        def linkLoader = Mock(LinkLoader)
        def extractor = new LinkExtractor(linkLoader)
        def resource = new Resource(link, "text/html", 100, body.getBytes(Charset.forName("UTF-8")))

        when:
        extractor.process(resource)

        then:
        1 * linkLoader.queueLinks({it ->
            it instanceof Set && it.size() == 2})
    }
}
