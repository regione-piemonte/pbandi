/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.business;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

import it.csi.pbandi.pbservizit.business.api.impl.HomeApiServiceImpl;
import it.csi.pbandi.pbservizit.business.api.impl.PingApiServiceImpl;
import it.csi.pbandi.pbservizit.business.api.impl.UserApiServiceImpl;
import it.csi.pbandi.pbservizit.security.CustomReadExceptionMapper;
import it.csi.pbandi.pbwebcert.business.service.impl.CertificazioneServiceImpl;
import it.csi.pbandi.pbwebcert.business.service.impl.PingServiceImpl;
import it.csi.pbandi.pbwebcert.util.Constants;
import it.csi.pbandi.pbwebcert.util.SpringInjectorInterceptor;
import it.csi.pbandi.pbwebcert.util.SpringSupportedResource;

@ApplicationPath("/restfacade/")
public class AppRestApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	public AppRestApplication() {

		LOG.debug("[AppRestApplication::AppRestApplication] inject");
		singletons.add(new SpringInjectorInterceptor());
		singletons.add(PingServiceImpl.class);
		singletons.add(CertificazioneServiceImpl.class);

		// pbservizit - servizi trasversali
		singletons.add(PingApiServiceImpl.class);
		singletons.add(UserApiServiceImpl.class);
		singletons.add(HomeApiServiceImpl.class);
		singletons.add(new CustomReadExceptionMapper());
		
		for (Object c : singletons) {
			if (c instanceof SpringSupportedResource) {
				SpringApplicationContextHelper.registerRestEasyController(c);
			}
		}

	}

	@Override
	public Set<Class<?>> getClasses() {
		return empty;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
