/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

import io.swagger.jaxrs.config.BeanConfig;
import it.csi.pbandi.pbservizit.business.api.impl.HomeApiServiceImpl;
import it.csi.pbandi.pbservizit.business.api.impl.UserApiServiceImpl;
import it.csi.pbandi.pbservizit.business.api.impl.attivita.AttivitaApiServiceImpl;
import it.csi.pbandi.pbservizit.security.CustomReadExceptionMapper;
import it.csi.pbandi.pbweberog.business.service.impl.ChiusuraProgettoServiceImpl;
import it.csi.pbandi.pbweberog.business.service.impl.ErogazioneServiceImpl;
import it.csi.pbandi.pbweberog.business.service.impl.FideiussioniServiceImpl;
import it.csi.pbandi.pbweberog.business.service.impl.GestioneDatiProgettoServiceImpl;
import it.csi.pbandi.pbweberog.business.service.impl.GestioneDisimpegniServiceImpl;
import it.csi.pbandi.pbweberog.business.service.impl.InizializzazioneServiceImpl;
import it.csi.pbandi.pbweberog.business.service.impl.MonitoraggioControlliServiceImpl;
import it.csi.pbandi.pbweberog.business.service.impl.PingServiceImpl;
import it.csi.pbandi.pbweberog.business.service.impl.RecuperiServiceImpl;
import it.csi.pbandi.pbweberog.business.service.impl.RegistroControlliServiceImpl;
import it.csi.pbandi.pbweberog.business.service.impl.RevocheServiceImpl;
import it.csi.pbandi.pbweberog.business.service.impl.RinunciaServiceImpl;
import it.csi.pbandi.pbweberog.business.service.impl.TrasferimentiServiceImpl;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.SpringInjectorInterceptor;
import it.csi.pbandi.pbweberog.util.SpringSupportedResource;

@ApplicationPath("/restfacade/")
public class AppRestApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	public AppRestApplication() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost("localhost:4280");
		beanConfig.setBasePath("/finanziamenti/bandi/pbweberog/restfacade");
		beanConfig.setResourcePackage("it.csi.pbandi.pbweberog.business.service");
		beanConfig.setScan(true);

		LOG.debug("[AppRestApplication::AppRestApplication] inject");
		singletons.add(new SpringInjectorInterceptor());
		singletons.add(PingServiceImpl.class);
		singletons.add(InizializzazioneServiceImpl.class);
		singletons.add(TrasferimentiServiceImpl.class);
		singletons.add(ErogazioneServiceImpl.class);
		singletons.add(FideiussioniServiceImpl.class);
		singletons.add(RinunciaServiceImpl.class);
		
		singletons.add(GestioneDatiProgettoServiceImpl.class);
		singletons.add(GestioneDisimpegniServiceImpl.class);
		singletons.add(RegistroControlliServiceImpl.class);
		singletons.add(MonitoraggioControlliServiceImpl.class);
		singletons.add(ChiusuraProgettoServiceImpl.class);
		singletons.add(RevocheServiceImpl.class);
		singletons.add(RecuperiServiceImpl.class);
		
		// pbservizit - servizi trasversali
		singletons.add(UserApiServiceImpl.class);
		singletons.add(HomeApiServiceImpl.class);
		singletons.add(AttivitaApiServiceImpl.class);
		singletons.add(new CustomReadExceptionMapper());

		for (Object c : singletons) {
			if (c instanceof SpringSupportedResource) {
				SpringApplicationContextHelper.registerRestEasyController(c);
			}
		}
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet();
		resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
		resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
		return resources;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
