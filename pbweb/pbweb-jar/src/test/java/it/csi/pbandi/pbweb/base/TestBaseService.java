package it.csi.pbandi.pbweb.base;

import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = {"classpath:/applicationContextTest.xml", "classpath:/applicationContextTest-pbservizit.xml"})
@PropertySource("classpath*:src/test/java/it/csi/pbandi/pbweb/config/enviromentTest.properties")
public class TestBaseService {

}
