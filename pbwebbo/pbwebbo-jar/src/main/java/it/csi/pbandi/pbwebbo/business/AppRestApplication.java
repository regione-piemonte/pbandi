/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

import it.csi.pbandi.pbservizit.business.api.impl.UserApiServiceImpl;
import it.csi.pbandi.pbservizit.security.CustomReadExceptionMapper;
import it.csi.pbandi.pbwebbo.business.service.impl.BoStorageServiceImpl;
import it.csi.pbandi.pbwebbo.business.service.impl.GestioneNewsImpl;
import it.csi.pbandi.pbwebbo.business.service.impl.PingServiceImpl;
import it.csi.pbandi.pbwebbo.util.Constants;
import it.csi.pbandi.pbwebbo.util.SpringInjectorInterceptor;
import it.csi.pbandi.pbwebbo.util.SpringSupportedResource;

@ApplicationPath("/restfacade/")
public class AppRestApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
    
    public AppRestApplication() {
        
    	LOG.debug("[AppRestApplication::AppRestApplication] inject");
    	singletons.add(new SpringInjectorInterceptor());
    	singletons.add(PingServiceImpl.class);
    	singletons.add(GestioneNewsImpl.class);
    	singletons.add(BoStorageServiceImpl.class);
        
        //pbservizit - servizi trasversali
        singletons.add(UserApiServiceImpl.class);
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
