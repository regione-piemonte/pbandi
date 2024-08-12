package it.csi.pbandi.pbweb.business;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import it.csi.pbandi.pbservizit.business.api.impl.ArchivioFileApiServiceImpl;
import it.csi.pbandi.pbservizit.business.api.impl.CronoProgrammaApiServiceImpl;
import it.csi.pbandi.pbservizit.business.api.impl.CronoProgrammaFasiApiImpl;
import it.csi.pbandi.pbservizit.business.api.impl.HomeApiServiceImpl;
import it.csi.pbandi.pbservizit.business.api.impl.UserApiServiceImpl;
import it.csi.pbandi.pbservizit.business.api.impl.attivita.AttivitaApiServiceImpl;
import it.csi.pbandi.pbservizit.security.CustomReadExceptionMapper;
import it.csi.pbandi.pbweb.business.service.impl.*;
import it.csi.pbandi.pbweb.util.CharsetInterceptorFilter;
import it.csi.pbandi.pbweb.util.SpringInjectorInterceptor;
import it.csi.pbandi.pbweb.util.SpringSupportedResource;

@ApplicationPath("/restfacade/")
public class AppRestApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public AppRestApplication() {

		singletons.add(new SpringInjectorInterceptor());
		singletons.add(new CharsetInterceptorFilter());
		singletons.add(DecodificheServiceImpl.class);
		singletons.add(RendicontazioneServiceImpl.class);
		//singletons.add(ArchivioFileServiceImpl.class);

		singletons.add(GestioneProrogheServiceImpl.class);
		singletons.add(GestioneIntegrazioniServiceImpl.class);
		singletons.add(DichiarazioneDiSpesaServiceImpl.class);
		singletons.add(DocumentiDiProgettoServiceImpl.class);
		singletons.add(DocumentoDiSpesaServiceImpl.class);
		singletons.add(FornitoreServiceImpl.class);
		singletons.add(InizializzazioneServiceImpl.class);
		singletons.add(SpesaValidataServiceImpl.class);
		singletons.add(ValidazioneRendicontazioneServiceImpl.class);
		singletons.add(CheckListServiceImpl.class);

		// pbservizit - servizi trasversali
		singletons.add(UserApiServiceImpl.class);
		singletons.add(HomeApiServiceImpl.class);
		singletons.add(AttivitaApiServiceImpl.class);
		singletons.add(ArchivioFileApiServiceImpl.class);
		singletons.add(CronoProgrammaApiServiceImpl.class);
		singletons.add(CronoProgrammaFasiApiImpl.class);
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
