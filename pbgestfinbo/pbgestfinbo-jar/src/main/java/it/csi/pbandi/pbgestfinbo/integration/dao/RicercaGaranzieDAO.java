/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbgestfinbo.dto.EsitoDTO;
import it.csi.pbandi.pbgestfinbo.dto.GestioneAllegatiVO;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.GaranziaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.InitDialogEscussioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SuggestBancheVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaAllegatiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaAzioniRecuperoBancaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaDatiAnagraficiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaRevocaBancariaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaSaldoStralcioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaSezioneDettagliGaranziaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaStatoCreditoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaStoricoEscussioneVO;

public interface RicercaGaranzieDAO {
	
	// Ricerca //
	
	List<GaranziaVO> ricercaGaranzie(String descrizioneBando, String codiceProgetto, String codiceFiscale, String nag, String partitaIva, String denominazioneCognomeNome, String statoEscussione, String denominazioneBanca, HttpServletRequest req) throws Exception;
	
	List<String> getSuggestions(String value, String id) throws Exception;
	
	List<String> initListaStatiEscussione();
	
	Boolean aggiornaAllegati(ArrayList<Integer> allegatiPresenti, Long idTarget);
	
	List<SuggestBancheVO> getBancaSuggestion(String value) throws Exception;
	
	List<GaranziaVO> getListaTipoEscussione();
	List<GaranziaVO> getListaStatiCredito();
	InitDialogEscussioneVO initDialogEscussione(int idStatoEscussione);
	String getTipoAgevolazione(String idProgetto);
	int getImportoAmmesso(String idProgetto);
	int getTotaleFondo(String idProgetto);
	int getTotaleBanca(String idProgetto);
	Date getDtConcessione(String idProgetto);
	Date getDtErogazione(String idProgetto);
	BigDecimal getImportoEscusso(String idProgetto, BigDecimal idEscussione);
	String getStatoDelCredito(String idProgetto);
	List<Integer> getRevocaAzioniSaldo(String idProgetto);
	List<VisualizzaRevocaBancariaVO> getRevoca(String idProgetto);
	List<VisualizzaAzioniRecuperoBancaVO> getRecupero(String idProgetto);
	List<VisualizzaSaldoStralcioVO> getSaldoStralcio(String idProgetto, int idAttivitaEsito);
	List<VisualizzaDatiAnagraficiVO> getDatiAnagrafici(String idProgetto);
	List<VisualizzaSezioneDettagliGaranziaVO> getSezioneDettaglioGaranzia(String idProgetto);
	List<VisualizzaStatoCreditoVO> getStatoCreditoStorico(String idProgetto, int idUtente);
	List<VisualizzaDatiAnagraficiVO> getDatiAnagraficiPersona(String idProgetto);
	List<VisualizzaSezioneDettagliGaranziaVO> getStatoCredito(String idProgetto);
	List<VisualizzaStoricoEscussioneVO> getStoricoEscussione(String idProgetto);
	EsitoDTO insertStatoCredito(String statoCredito, Date dtModifica, int idSoggetto, int idProgetto, int idUtente);
	void updateStatoCreditoAggiornato(Date dtModifica, int idUtente, Date vecchioDt);
	int getIdSoggetto(String statoCredito, Date dtModifica);
	Date getVecchioDt(int idSoggetto);
	int escussioneTotAccIsPresent(int idProgetto, String desc);
	int escussioneSaldoIsPresent(int idProgetto);
	EsitoDTO insertEscussione(Date dtRichEscussione, String descTipoEscussione, BigDecimal impRichiesto, String numProtocolloRich,
	String note, Date dtInizioValidita, int idProgetto, String numProtocolloNotif, Long idUtente);
	EsitoDTO insertModificaEscussione(Date dtRichEscussione, String descTipoEscussione, String descStatoEscussione, BigDecimal impRichiesto, BigDecimal impApprovato, String numProtocolloRich, String numProtocolloNotif,
			String causaleBonifico, String ibanBonifico, int idBanca, String descBanca, int idSoggProgBancaBen, int ProgrSoggettoProgetto, String note, Date dtInizioValidita, Date dataNotifica, int idProgetto, Long idUtente, int idEscussione) throws Exception;
	EsitoDTO UpdateEscussione(int idProgetto, Long idUtente, Date dtInizioValidita, Date dtNotifica, int idEscussione);
	EsitoDTO insertModificaStatoEscussione(String note, Date dtInizioValidita, int idEscussione, Long idUtente, Date dtNotifica, String descStatoEscussione);
	Boolean salvaUploadAllegato(MultipartFormDataInput multipartFormData);
	void newAggiungiAllegato(Long idProgetto, Boolean letteraAccompagnatoria, int ambitoAllegato, byte[] allegato, String nomeAllegato, HttpServletRequest req) throws Exception;
	List<VisualizzaAllegatiVO> getAllegati(Long idEscussione);
	Boolean deleteAllegato(int idDocIndex);
	void insertModificaStatoEscussioneRicIntegrazione(String note, Date dtInizioValidita, int idEscussione,
			int idUtente, String descStatoEscussione, Date dtNotifica);
	Boolean getAbilitazione(String ruolo);
	EsitoDTO insertRichiestaIntegrazELettera(Boolean sendLettera, Long idProgetto, Long idUtente, Long idEscussione, Long nGgScadenza, MultipartFormDataInput multipartFormData, Long idTipoDocumentoIndex) throws Exception;
	Boolean salvaUploadRichiestaIntegraz(MultipartFormDataInput multipartFormData);
	int getRichIntegr(String idEscussione);
	Boolean salvaUploadLettera(MultipartFormDataInput multipartFormData);
	void updateIdDistinta(Long idTarget);
	void insertDistinta(Long idUtente, Long idProgetto);
	void insertDettaglio(Long idTarget, Long idUtente);
	Object getGaranzia(BigDecimal idEscussione, BigDecimal idProgetto);
	VisualizzaStatoCreditoVO getStatoCreditoNew(String idProgetto);
	EsitoDTO setFlagEsito(Long idEscussione);
	Boolean sendEsitoToErogazioni(Long idProgetto, Long idEscussione, int idTipoEscussione, Long idUtente) throws Exception, ErroreGestitoException;
	
}