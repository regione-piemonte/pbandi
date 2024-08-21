/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbservizit.dto.contoeconomico.ContoEconomicoItem;
import it.csi.pbandi.pbservizit.dto.contoeconomico.InizializzaVisualizzaContoEconomicoDTO;
import it.csi.pbandi.pbservizit.dto.contoeconomico.VoceDiSpesaPreventivataDTO;
import it.csi.pbandi.pbservizit.dto.contoeconomico.VociDiSpesaCulturaDTO;
import it.csi.pbandi.pbservizit.integration.vo.EsitoOperazioneInviaDichiarazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.VoceDiEntrataCulturaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.VociDiEntrataCulturaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ContoEconomicoConStatoVO;

public interface ContoEconomicoDAO {
	InizializzaVisualizzaContoEconomicoDTO inizializzaVisualizzaContoEconomico(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;	
	ArrayList<ContoEconomicoItem> aggiornaVisualizzaContoEconomico (Long idProgetto, String tipoRicerca, Long idPartner, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	ContoEconomicoDTO trovaContoEconomico(Long idProgetto);
	ContoEconomicoConStatoVO trovaContoEconomicoProposta(Long idProgetto, Long idUtente);
	VociDiSpesaCulturaDTO vociDiSpesaCultura(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	VociDiEntrataCulturaDTO vociDiEntrataCultura(Long idProgetto, Long idUtente, String idIride) throws Exception;
	boolean salvaVociDiEntrataCultura(List<VoceDiEntrataCulturaDTO> vociDiEntrata, Long idProgettoProposta, Long idUtente, String idIride);
	Double getPercentualeImportoAgevolato(Long idBando, Long idUtente);
	boolean salvaSpesePreventivate(ArrayList<VoceDiSpesaPreventivataDTO> vociDiSpesa, Long idUtente, String idIride);
	EsitoOperazioneInviaDichiarazioneDTO inviaDichiarazioneDiSpesaCultura(MultipartFormDataInput multipartFormData,
			Long idUtente, String idIride) throws InvalidParameterException, Exception;
	VociDiEntrataCulturaDTO vociDiEntrataPerRimodulazione(Long idProgetto, Long idUtente, String idIride) throws Exception;
}

