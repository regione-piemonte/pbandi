/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import it.csi.pbandi.ammvoservrest.dto.DebitoResiduo;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.BeneficiarioCreditiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.BoxListVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.CessioneQuotaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.EscussioneConfidiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.IscrizioneRuoloVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.LiberazioneBancaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.LiberazioneGaranteVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.PianoRientroVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.RevocaAmministrativaCreditiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SaveSchedaClienteAllVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SchedaClienteMainVO;


public interface RicercaBeneficiariCreditiDAO {

		// RICERCA BENEFICIARI //
	
	List<BeneficiarioCreditiVO> getElencoBeneficiari(String codiceFiscale, String ndg, String partitaIva, String denominazione, String descBando, String codiceProgetto, HttpServletRequest req) throws Exception;
	
	List<String> getSuggestion(String value, String id) throws Exception;

	List<RevocaAmministrativaCreditiVO> getRevocaAmministrativa(Long idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, String listRevoche, Long idUtente) throws Exception;
	
	DebitoResiduo getCreditoResiduoEAgevolazione(int idProgetto, int idBando, int idModalitaAgevolazioneOrig, int idModalitaAgevolazioneRif, Long idUtente) throws Exception;
	
		// MODIFICA BENEFICIARIO //
	
	List<SchedaClienteMainVO> getSchedaCliente(Long idProgetto, Long idModalitaAgevolazione) throws Exception;
	
	List<String> getListBanche(String value) throws Exception;
	
	void setStatoAzienda(SaveSchedaClienteAllVO statoAzienda, Long idModalitaAgevolazione, Boolean flagStatoAzienda, Long idProgetto) throws ErroreGestitoException; 

	void setStatoCredito(SaveSchedaClienteAllVO statoCredito, Long idModalitaAgevolazione, Long idProgetto) throws ErroreGestitoException; 
	
	void setRating(SaveSchedaClienteAllVO rating, Long idModalitaAgevolazione) throws ErroreGestitoException; 
	
	void setBanca(SaveSchedaClienteAllVO banca, Long idModalitaAgevolazione) throws ErroreGestitoException; 
	
	
		// Attività Istruttore Area Crediti //
		
	List<LiberazioneGaranteVO> getLiberazioneGarante(String idProgetto, Long idModalitaAgevolazione) throws Exception;
	
	void setLiberazioneGarante(LiberazioneGaranteVO liberazioneGarante, Long idModalitaAgevolazione) throws ErroreGestitoException; 
		
	List<EscussioneConfidiVO> getEscussioneConfidi(String idProgetto, Long idModalitaAgevolazione) throws Exception;
	
	void setEscussioneConfidi(EscussioneConfidiVO escussioneConfidi, Long idModalitaAgevolazione) throws ErroreGestitoException; 
	
	List<PianoRientroVO> getPianoRientro(String idProgetto, Long idModalitaAgevolazione) throws Exception;
	
	void setPianoRientro(PianoRientroVO pianoRientro, Long idModalitaAgevolazione) throws ErroreGestitoException; 
	
	List<LiberazioneBancaVO> getLiberazioneBanca(String idProgetto, Long idModalitaAgevolazione) throws Exception;
	
	void setLiberazioneBanca(LiberazioneBancaVO liberazioneBanca, Long idModalitaAgevolazione) throws ErroreGestitoException; 
	
	List<IscrizioneRuoloVO> getIscrizioneRuolo(String idProgetto, Long idModalitaAgevolazione) throws Exception;
	
	void setIscrizioneRuolo(IscrizioneRuoloVO iscrizioneRuolo, Long idModalitaAgevolazione) throws ErroreGestitoException;
	
	List<CessioneQuotaVO> getCessioneQuota(String idProgetto, Long idModalitaAgevolazione) throws Exception;
	
	void setCessioneQuota(CessioneQuotaVO cessioneQuota, Long idModalitaAgevolazione) throws ErroreGestitoException;

}
