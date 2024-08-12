package it.csi.pbandi.pbweb.util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import it.csi.pbandi.pbweb.business.SpringApplicationContextHelper;

@Provider
public class SpringInjectorInterceptor implements PreProcessInterceptor {

    @Override
    public ServerResponse preProcess(HttpRequest request, ResourceMethodInvoker method) throws Failure, WebApplicationException {
    	
		SpringApplicationContextHelper.injectSpringBeansIntoRestEasyService(method.getResourceClass().getName());
        
        return null;
    }
}