package it.csi.pbandi.pbweb.util;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;

@Provider
public class CharsetInterceptorFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		requestContext.setProperty(InputPart.DEFAULT_CHARSET_PROPERTY, "UTF-8");
	}

}