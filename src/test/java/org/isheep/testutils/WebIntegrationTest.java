package org.isheep.testutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.isheep.config.CustomSpringProfiles;
import org.isheep.testconfig.security.MockedSecurityConfig;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.inject.Inject;

/**
 * No database deployed, but URL available.
 * <p>
 * Created by raymo on 27/11/2016.
 */
@ActiveProfiles(CustomSpringProfiles.TEST_PROFILE)
@RunWith(SpringRunner.class)
@Import(MockedSecurityConfig.class)
public abstract class WebIntegrationTest {

    @Inject
    protected ObjectMapper mapper;

    @Inject
    protected MockMvc mockMvc;


    /**
     * By default, all request are authenticated as a user.
     *
     * @param requestBuilder
     * @return
     * @throws Exception
     */
    public ResultActions perform(final RequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(requestBuilder);
    }

    public ResultActions performWithAdminRole(final MockHttpServletRequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(
                requestBuilder.header(MockedSecurityConfig.MOCKED_ROLE_HEADER, MockedSecurityConfig.ADMIN_ROLE)
        );
    }

    public ResultActions performWithNoAuthentication(final MockHttpServletRequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(
                requestBuilder.header(MockedSecurityConfig.MOCKED_ROLE_HEADER, MockedSecurityConfig.NO_AUTH)
        );
    }

    public MockHttpServletRequestBuilder get(final String url) {
        return MockMvcRequestBuilders.get(url);
    }

    public MockHttpServletRequestBuilder post(final String url) {
        return MockMvcRequestBuilders.post(url);
    }

    public MockHttpServletRequestBuilder put(final String url) {
        return MockMvcRequestBuilders.put(url);
    }

    public MockHttpServletRequestBuilder delete(final String url) {
        return MockMvcRequestBuilders.delete(url);
    }

}
