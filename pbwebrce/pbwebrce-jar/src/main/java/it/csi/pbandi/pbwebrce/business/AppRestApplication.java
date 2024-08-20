/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

import io.swagger.jaxrs.config.BeanConfig;
import it.csi.pbandi.pbservizit.business.api.impl.ArchivioFileApiServiceImpl;
import it.csi.pbandi.pbservizit.business.api.impl.HomeApiServiceImpl;
import it.csi.pbandi.pbservizit.business.api.impl.UserApiServiceImpl;
import it.csi.pbandi.pbservizit.business.api.impl.attivita.AttivitaApiServiceImpl;
import it.csi.pbandi.pbservizit.business.api.impl.CronoProgrammaFasiApiImpl;
import it.csi.pbandi.pbservizit.business.api.impl.IndicatoriApiServiceImpl;
import it.csi.pbandi.pbservizit.security.CustomReadExceptionMapper;
//import it.csi.pbandi.pbweb.business.ArchivioFileApiServiceImpl;
import it.csi.pbandi.pbwebrce.business.service.impl.AffidamentiServiceImpl;
import it.csi.pbandi.pbwebrce.business.service.impl.ChecklistServiceImpl;
import it.csi.pbandi.pbwebrce.business.service.impl.ContoEconomicoServiceImpl;
import it.csi.pbandi.pbwebrce.business.service.impl.ContoEconomicoWaServiceImpl;
import it.csi.pbandi.pbwebrce.business.service.impl.InizializzazioneServiceImpl;
import it.csi.pbandi.pbwebrce.business.service.impl.PingServiceImpl;
import it.csi.pbandi.pbwebrce.util.CharsetInterceptorFilter;
import it.csi.pbandi.pbwebrce.util.Constants;
import it.csi.pbandi.pbwebrce.util.SpringInjectorInterceptor;
import it.csi.pbandi.pbwebrce.util.SpringSupportedResource;

@ApplicationPath("/restfacade/")
public class AppRestApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	public AppRestApplication() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost("localhost:4260");
		beanConfig.setBasePath("/finanziamenti/bandi/pbwebrce/restfacade");
		beanConfig.setResourcePackage("it.csi.pbandi.pbwebrce.business.service");
		beanConfig.setScan(true);

		LOG.debug("[AppRestApplication::AppRestApplication] inject");
		singletons.add(new SpringInjectorInterceptor());
		singletons.add(new CharsetInterceptorFilter());
		singletons.add(PingServiceImpl.class);
		singletons.add(InizializzazioneServiceImpl.class);
		singletons.add(AffidamentiServiceImpl.class);
		singletons.add(ChecklistServiceImpl.class);
		singletons.add(ContoEconomicoServiceImpl.class);
		singletons.add(ContoEconomicoWaServiceImpl.class);

		// pbservizit - servizi trasversali
		singletons.add(UserApiServiceImpl.class);
		singletons.add(HomeApiServiceImpl.class);
		singletons.add(AttivitaApiServiceImpl.class);
		singletons.add(ArchivioFileApiServiceImpl.class);
		singletons.add(CronoProgrammaFasiApiImpl.class);
		singletons.add(IndicatoriApiServiceImpl.class);
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
