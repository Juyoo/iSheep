package org.isheep.entity.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.isheep.entity.embeddable.Address;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;

/**
 * Created by raymo on 28/11/2016.
 */
public class AddressJacksonValidatorTest {

    private JacksonTester<Address> json;

    @Before
    public void setup() {
        final ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void shouldDoSomething() {
        // TODO : tests jackson serialize / deserialize to ensure validation process is correct.
    }

}
