/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.business;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

import it.csi.pbandi.pbservrest.business.service.impl.CreditoApiImpl;
import it.csi.pbandi.pbservrest.business.service.impl.CupApiImpl;
import it.csi.pbandi.pbservrest.business.service.impl.DatiBeneficiarioApiImpl;
import it.csi.pbandi.pbservrest.business.service.impl.DomandeBeneficiarioApiImpl;
import it.csi.pbandi.pbservrest.business.service.impl.EsitoDurcAntimafiaApiImpl;
import it.csi.pbandi.pbservrest.security.CustomReadExceptionMapper;
import it.csi.pbandi.pbservrest.util.Constants;
import it.csi.pbandi.pbservrest.util.SpringInjectorInterceptor;
import it.csi.pbandi.pbservrest.util.SpringSupportedResource;

@ApplicationPath("/api/")
public class AppRestApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	public AppRestApplication() {

		LOG.debug("[AppRestApplication::AppRestApplication] inject");
		
		singletons.add(new SpringInjectorInterceptor());
		singletons.add(new CustomReadExceptionMapper());
		
		singletons.add(new DatiBeneficiarioApiImpl());
		singletons.add(new CreditoApiImpl());
		singletons.add(new DomandeBeneficiarioApiImpl());
		singletons.add(new EsitoDurcAntimafiaApiImpl());
		singletons.add(new CupApiImpl());
		
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
