package org.isheep.testutils;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import javax.inject.Inject;

/**
 * No database deployed, but URL available.
 * <p>
 * Created by raymo on 27/11/2016.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class WebIntegrationTest extends WebTest {

    @Inject
    protected TestRestTemplate restTemplate;

}
