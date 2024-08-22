/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

import it.csi.pbandi.pbservwelfare.business.service.impl.AbilitazioneRendicontoApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.AcquisizioneDomandeApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.DichiarazioneSpesaApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.ElencoDocumentiSpesaApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.ElencoSoggettiCorrelatiApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.EsposizioneMensilitaApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.GestioneBeneficiarioApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.GestioneFornitoriApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.GestioneProrogheApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.GestioneStruttureApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.RicezioneSegnalazioniApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.SoggettoDelegatoApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.TrasmissioneDocumentazioneContestazioniApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.TrasmissioneDocumentazioneIntegrativaDocSpesaApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.TrasmissioneDocumentazioneIntegrativaRevocaApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.TrasmissioneDocumentazionePerControdeduzioniApiImpl;
import it.csi.pbandi.pbservwelfare.business.service.impl.TrasmissioneVociDiSpesaApiImpl;
import it.csi.pbandi.pbservwelfare.security.CustomReadExceptionMapper;
import it.csi.pbandi.pbservwelfare.util.Constants;
import it.csi.pbandi.pbservwelfare.util.SpringInjectorInterceptor;
import it.csi.pbandi.pbservwelfare.util.SpringSupportedResource;

@ApplicationPath("/api/")
public class AppRestApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	public AppRestApplication() {

		LOG.debug("[AppRestApplication::AppRestApplication] inject");
		
		singletons.add(new SpringInjectorInterceptor());
		singletons.add(new CustomReadExceptionMapper());
		
		singletons.add(new AbilitazioneRendicontoApiImpl());
		singletons.add(new SoggettoDelegatoApiImpl());
		singletons.add(new TrasmissioneDocumentazionePerControdeduzioniApiImpl());
		singletons.add(new TrasmissioneDocumentazioneIntegrativaRevocaApiImpl());
		singletons.add(new TrasmissioneDocumentazioneIntegrativaDocSpesaApiImpl());
		singletons.add(new TrasmissioneDocumentazioneContestazioniApiImpl());
		singletons.add(new ElencoSoggettiCorrelatiApiImpl());
		singletons.add(new ElencoDocumentiSpesaApiImpl());
		singletons.add(new GestioneFornitoriApiImpl());
		singletons.add(new TrasmissioneVociDiSpesaApiImpl());
		singletons.add(new AcquisizioneDomandeApiImpl());
		singletons.add(new GestioneProrogheApiImpl());
		singletons.add(new DichiarazioneSpesaApiImpl());
		singletons.add(new EsposizioneMensilitaApiImpl());
		singletons.add(new RicezioneSegnalazioniApiImpl());
		singletons.add(new GestioneBeneficiarioApiImpl());
		singletons.add(new GestioneStruttureApiImpl());

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
