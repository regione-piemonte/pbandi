/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao;

import java.util.List;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;
import it.csi.pbandi.pbworkspace.dto.RicercaAttivitaPrecedenteDTO;
import it.csi.pbandi.pbworkspace.dto.profilazione.ConsensoInformatoDTO;
import it.csi.pbandi.pbworkspace.exception.ErroreGestitoException;
import it.csi.pbandi.pbworkspace.exception.RecordNotFoundException;
import it.csi.pbandi.pbworkspace.exception.UtenteException;
import it.csi.pbandi.pbworkspace.integration.vo.AttivitaVO;
import it.csi.pbandi.pbworkspace.integration.vo.BandoVO;
import it.csi.pbandi.pbworkspace.integration.vo.PbandiTNotificaProcessoVO;
import it.csi.pbandi.pbworkspace.integration.vo.ProgettoVO;

public interface AttivitaDAO {

	List<ProgettoVO> getProgettiWithProcedures(Long progrBandoLineaIntervento, Long idSoggettoBen, Long idUtente)
			throws Exception;

	List<BandoVO> getBandi(String codiceRuolo, Long idSoggetto, Long idBeneficiario)
			throws ErroreGestitoException, UtenteException, RecordNotFoundException;

	List<BandoVO> ordinaBandi(List<BandoVO> bandi) throws RecordNotFoundException, Exception;

	List<AttivitaVO> getAttivitaBENWithProcedures(Long progrBandoLineaIntervento, Long idBeneficiario, Long idUtente,
			String descBreveTipoAnag, Long start, Long idProgetto, String descrAttivita) throws Exception;

	List<PbandiTNotificaProcessoVO> caricaListaNotifiche(Long idBandoLineaIntervento, Long idSoggettoBeneficiario,
			long[] idProgetti, String codiceRuolo) throws Exception;

	List<BandoVO> getBandoByProgr(String codiceRuolo, Long idSoggetto, Long idBeneficiario,
			Long progrBandoLineaIntervento) throws ErroreGestitoException, UtenteException, RecordNotFoundException;

	Integer aggiornaStatoNotifiche(long[] elencoIdNotifiche, String stato) throws Exception;

	String startAttivita(Long idUtente, String identitaDigitale, String descBreveTask, Long idProgetto) throws NeoFluxException, SystemException, UnrecoverableException, CSIException;
	
	ConsensoInformatoDTO trovaConsensoInvioComunicazione(Long idUtente) throws Exception;
	
	Integer salvaConsensoInvioComunicazione(Long idUtente, String emailConsenso, String flagConsensoMail) throws Exception;
	
	void salvaFiltriRicercaAttivita(Long idBeneficiario, Long progrBandoLineaIntervento, Long idProgetto,
			String descrAttivita, Long idUtente) throws Exception;

	RicercaAttivitaPrecedenteDTO ricercaAttivitaPrecedente(Long idUtente, String descBreveTipoAnag, Long idSoggettoBeneficiario) throws Exception;

}
