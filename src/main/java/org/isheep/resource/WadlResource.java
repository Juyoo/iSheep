package org.isheep.resource;

import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by raymo on 14/12/2016.
 */
@Controller
@RequestMapping("/")
public class WadlResource {

    @RequestMapping(value = "/application.wadl", method = GET, produces = APPLICATION_XML)
    @ResponseBody
    public String displayWadl() throws IOException {
        return getResourceAsString("application.wadl");
    }

    @RequestMapping(value = "/application.wadl/xsd0.xsd", method = GET, produces = APPLICATION_XML)
    @ResponseBody
    public String displayXsd() throws IOException {
        return getResourceAsString("xsd0.xsd");
    }

    private String getResourceAsString(final String fileName) throws IOException {
        final InputStream resourceInputStream = new ClassPathResource(fileName).getInputStream();
        return IOUtils.toString(resourceInputStream, Charsets.UTF_8);
    }

}
