package br.com.sgo.infra;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.interceptor.multipart.DefaultMultipartConfig;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@Component     
@ApplicationScoped     
public class CustomMultipartConfig extends DefaultMultipartConfig {   
    private static final Logger LOG = Logger.getLogger(CustomMultipartConfig.class);   
    private static final String SIZE_LIMIT_CTX_PARAM = "br.com.caelum.vraptor.interceptor.multipart.UPLOAD_SIZE_LIMIT";   
    private long sizeLimit;   
       
    public CustomMultipartConfig(ServletContext ctx) {   
        sizeLimit = super.getSizeLimit();   
        String value = ctx.getInitParameter(SIZE_LIMIT_CTX_PARAM);   
        if(value != null){   
            try{   
                sizeLimit = Long.parseLong(value);   
            }   
            catch(NumberFormatException e){   
                LOG.error("Error trying do convert value \"" + value + "\" to number. Using the default size " + sizeLimit, e);   
            }   
        }   
    }   
       
    public long getSizeLimit() {   
        return sizeLimit;   
    }     
}    
