/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.business.service.impl;

import it.csi.pbandi.pbservwelfare.dto.AbilitaRendicontazioneResponse;
import it.csi.pbandi.pbservwelfare.dto.DichiarazioneSpesaResponse;
import it.csi.pbandi.pbservwelfare.dto.DocContestazioneResponse;
import it.csi.pbandi.pbservwelfare.dto.DocControdeduzioniResponse;
import it.csi.pbandi.pbservwelfare.dto.DocIntRevocaResponse;
import it.csi.pbandi.pbservwelfare.dto.DocIntegrativaSpesaResponse;
import it.csi.pbandi.pbservwelfare.dto.DocumentiSpesaListResponse;
import it.csi.pbandi.pbservwelfare.dto.DomandeConcesseResponse;
import it.csi.pbandi.pbservwelfare.dto.ElencoMensilitaResponse;
import it.csi.pbandi.pbservwelfare.dto.EsitoFornitoriResponse;
import it.csi.pbandi.pbservwelfare.dto.EsitoResponse;
import it.csi.pbandi.pbservwelfare.dto.EsitoVociDiSpesaResponse;
import it.csi.pbandi.pbservwelfare.dto.RicezioneSegnalazioniResponse;
import it.csi.pbandi.pbservwelfare.dto.SoggettiCorrelatiListResponse;
import it.csi.pbandi.pbservwelfare.dto.SoggettoDelegatoResponse;
import it.csi.pbandi.pbservwelfare.util.Constants;

public class BaseApiServiceImpl {

	/**
	 *
	 */
	public EsitoResponse getErroreServizioNonAttivo() {
		EsitoResponse resp = new EsitoResponse();
		resp.setCodiceErrore("004");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.SERVIZIO_NON_ATTIVO);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 *
	 */
	public EsitoResponse getErroreParametriInvalidi() {
		EsitoResponse resp = new EsitoResponse();
		resp.setCodiceErrore("003");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 *
	 */
	public EsitoResponse getErroreGenerico() {
		EsitoResponse resp = new EsitoResponse();
		resp.setCodiceErrore("007");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.GENERICO);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 *
	 */
	public EsitoResponse getErroreConnessioneDB() {
		EsitoResponse resp = new EsitoResponse();
		resp.setCodiceErrore("002");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.CONNESSIONE_DATABASE);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 *
	 */
	public EsitoResponse getErroreNessunDatoSelezionato() {
		EsitoResponse resp = new EsitoResponse();
		resp.setCodiceErrore("001");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.NESSUN_DATO_SELEZIONATO);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 *
	 */
	public EsitoResponse getErroreFileNonTrovato() {
		EsitoResponse resp = new EsitoResponse();
		resp.setCodiceErrore("008");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.NESSUN_FILE_TROVATO_FORNITORE);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 * 
	 */
	public AbilitaRendicontazioneResponse getErroreParametriInvalidiAbilitazioneRendicontazione() {
		AbilitaRendicontazioneResponse resp = new AbilitaRendicontazioneResponse();
		resp.setCodiceMessaggio("003");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI);
		resp.setEsito(Constants.ESITO.KO);
		return resp;
	}

	/**
	 * 
	 */
	public AbilitaRendicontazioneResponse getErroreCodiceFiscaleErrato() {
		AbilitaRendicontazioneResponse resp = new AbilitaRendicontazioneResponse();
		resp.setCodiceMessaggio("005");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.CODICE_FISCALE_ERRATO);
		resp.setEsito(Constants.ESITO.KO);
		return resp;
	}

	/**
	 * 
	 */
	public AbilitaRendicontazioneResponse getErroreProgettoNonPresente() {
		AbilitaRendicontazioneResponse resp = new AbilitaRendicontazioneResponse();
		resp.setAbilitato(false);
		resp.setCodiceMessaggio("006");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.PROGETTO_NON_PRESENTE);
		resp.setEsito(Constants.ESITO.KO);
		return resp;
	}

	/**
	 * 
	 */
	public AbilitaRendicontazioneResponse getErroreSoggettoNonAssociato() {
		AbilitaRendicontazioneResponse resp = new AbilitaRendicontazioneResponse();
		resp.setAbilitato(false);
		resp.setCodiceMessaggio("007");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.SOGGETTO_NON_ASSOCIATO_AL_PROGETTO);
		resp.setEsito(Constants.ESITO.KO);
		return resp;
	}

	/**
	 * 
	 */
	public AbilitaRendicontazioneResponse getErroreSoggettoNonCensito() {
		AbilitaRendicontazioneResponse resp = new AbilitaRendicontazioneResponse();
		resp.setAbilitato(false);
		resp.setCodiceMessaggio("008");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.SOGGETTO_NON_CENSITO_COME_FORNITORE);
		resp.setEsito(Constants.ESITO.KO);
		return resp;
	}

	/**
	 * 
	 */
	public AbilitaRendicontazioneResponse getErroreProgettoNonAbiliato() {
		AbilitaRendicontazioneResponse resp = new AbilitaRendicontazioneResponse();
		resp.setAbilitato(false);
		resp.setCodiceMessaggio("009");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.PROGETTO_NON_ABILITATO);
		resp.setEsito(Constants.ESITO.KO);
		return resp;
	}

	/**
	 * 
	 */
	public SoggettoDelegatoResponse getErroreParametriInvalidiSoggettiCorrelati() {
		SoggettoDelegatoResponse resp = new SoggettoDelegatoResponse();
		resp.setCodiceErrore("003");
		resp.setEsito("KO");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI);
		return resp;
	}

	/**
	 * 
	 */
	public DocControdeduzioniResponse getErroreParametriInvalidiDocControdeduzione() {
		DocControdeduzioniResponse resp = new DocControdeduzioniResponse();
		resp.setEsito("KO");
		resp.setCodiceErrore("003");
		resp.setMessaggioErrore(Constants.MESSAGGI.ERRORE.PARAMETRI_INSUFFICIENTI);
		return resp;
	}

	/**
	 * 
	 */
	public DocControdeduzioniResponse getErroreControdeduzioneInElaborazione() {
		DocControdeduzioniResponse resp = new DocControdeduzioniResponse();
		resp.setEsito("KO");
		resp.setCodiceErrore("005");
		resp.setMessaggioErrore(Constants.MESSAGGI.ERRORE.CONTRODEDUZIONE_IN_ELABORAZIONE);
		return resp;
	}

	/**
	 * 
	 */
	public DocControdeduzioniResponse getErroreNessunDatoSelezionatoDocContrRevoca() {
		DocControdeduzioniResponse resp = new DocControdeduzioniResponse();
		resp.setEsito("KO");
		resp.setCodiceErrore("001");
		resp.setMessaggioErrore(Constants.MESSAGGI.ERRORE.NESSUN_DATO_SELEZIONATO);
		return resp;
	}

	/**
	 * 
	 */
	public DocIntRevocaResponse getErroreParametriInvalidiDocIntRevoca() {
		DocIntRevocaResponse resp = new DocIntRevocaResponse();
		resp.setEsito("KO");
		resp.setCodiceErrore("003");
		resp.setMessaggioErrore(Constants.MESSAGGI.ERRORE.PARAMETRI_INSUFFICIENTI);
		return resp;
	}

	/**
	 * 
	 */
	public DocIntRevocaResponse getErroreIntRevocaInElaborazione() {
		DocIntRevocaResponse resp = new DocIntRevocaResponse();
		resp.setEsito("KO");
		resp.setCodiceErrore("005");
		resp.setMessaggioErrore(Constants.MESSAGGI.ERRORE.CONTRODEDUZIONE_IN_ELABORAZIONE);
		return resp;
	}

	/**
	 * 
	 */
	public DocIntRevocaResponse getErroreNessunDatoSelezionatoIntRevoca() {
		DocIntRevocaResponse resp = new DocIntRevocaResponse();
		resp.setEsito("KO");
		resp.setCodiceErrore("001");
		resp.setMessaggioErrore(Constants.MESSAGGI.ERRORE.NESSUN_DATO_SELEZIONATO);
		return resp;
	}

	/**
	 * 
	 */
	public DocIntegrativaSpesaResponse getErroreParametriInvalidiDocIntegrativaSpesa() {
		DocIntegrativaSpesaResponse resp = new DocIntegrativaSpesaResponse();
		resp.setEsito("KO");
		resp.setCodiceErrore("003");
		resp.setMessaggioErrore(Constants.MESSAGGI.ERRORE.PARAMETRI_INSUFFICIENTI);
		return resp;
	}

	/**
	 * 
	 */
	public DocIntegrativaSpesaResponse getErroreNessunDatoSelezionatoDocIntegrativaSpesa() {
		DocIntegrativaSpesaResponse resp = new DocIntegrativaSpesaResponse();
		resp.setEsito("KO");
		resp.setCodiceErrore("001");
		resp.setMessaggioErrore(Constants.MESSAGGI.ERRORE.NESSUN_DATO_SELEZIONATO);
		return resp;
	}

	/**
	 * 
	 */
	public DocIntegrativaSpesaResponse getErroreRichiestaNonInElaborazione() {
		DocIntegrativaSpesaResponse resp = new DocIntegrativaSpesaResponse();
		resp.setEsito("KO");
		resp.setCodiceErrore("007");
		resp.setMessaggioErrore(Constants.MESSAGGI.ERRORE.RICHIESTA_NON_IN_ELABORAZIONE);
		return resp;
	}

	/**
	 * 
	 */
	public DocIntegrativaSpesaResponse getErroreRichiestaChiusa() {
		DocIntegrativaSpesaResponse resp = new DocIntegrativaSpesaResponse();
		resp.setEsito("KO");
		resp.setCodiceErrore("007");
		resp.setMessaggioErrore(Constants.MESSAGGI.ERRORE.RICHIESTA_INTEGRAZIONE_CHIUSA);
		return resp;
	}

	/**
	 * 
	 */
	public DocIntegrativaSpesaResponse getErroreRichiestaScaduta() {
		DocIntegrativaSpesaResponse resp = new DocIntegrativaSpesaResponse();
		resp.setEsito("KO");
		resp.setCodiceErrore("006");
		resp.setMessaggioErrore(Constants.MESSAGGI.ERRORE.RICHIESTA_INTEGRAZIONE_SCADUTA);
		return resp;
	}

	/**
	 * 
	 */
	public DocContestazioneResponse getErroreParametriInvalidiDocContestazione() {
		DocContestazioneResponse resp = new DocContestazioneResponse();
		resp.setEsito("KO");
		resp.setCodiceErrore("003");
		resp.setMessaggioErrore(Constants.MESSAGGI.ERRORE.PARAMETRI_INSUFFICIENTI);
		return resp;
	}

	/**
	 * 
	 */
	public DocContestazioneResponse getErroreNessunDatoSelezionatoDocContestazione() {
		DocContestazioneResponse resp = new DocContestazioneResponse();
		resp.setEsito("KO");
		resp.setCodiceErrore("001");
		resp.setMessaggioErrore(Constants.MESSAGGI.ERRORE.NESSUN_DATO_SELEZIONATO);
		return resp;
	}

	/**
	 * 
	 */
	public DocContestazioneResponse getErroreDocContestazioneInElaborazione() {
		DocContestazioneResponse resp = new DocContestazioneResponse();
		resp.setEsito("KO");
		resp.setCodiceErrore("005");
		resp.setMessaggioErrore(Constants.MESSAGGI.ERRORE.CONTRODEDUZIONE_IN_ELABORAZIONE);
		return resp;
	}

	/**
	 * 
	 */
	public SoggettiCorrelatiListResponse getErroreParametriInvalidiListaSoggettiCorrelati() {
		SoggettiCorrelatiListResponse resp = new SoggettiCorrelatiListResponse();
		resp.setEsitoServizio("KO");
		resp.setCodiceErrore("003");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI);
		return resp;
	}

	/**
	 * 
	 */
	public SoggettiCorrelatiListResponse getErroreBeneficiarioNonTrovato() {
		SoggettiCorrelatiListResponse resp = new SoggettiCorrelatiListResponse();
		resp.setEsitoServizio("KO");
		resp.setCodiceErrore("005");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.CODICE_FISCALE_ERRATO);
		return resp;
	}

	/**
	 *
	 */
	public DocumentiSpesaListResponse getErroreParametriInvalidiElencoDocumenti() {
		DocumentiSpesaListResponse resp = new DocumentiSpesaListResponse();
		resp.setCodiceErrore("003");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI);
		return resp;
	}

	/**
	 *
	 */
	public DocumentiSpesaListResponse getErroreProgettoNonPresenteElencoDocumenti() {
		DocumentiSpesaListResponse resp = new DocumentiSpesaListResponse();
		resp.setCodiceErrore("006");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.PROGETTO_NON_PRESENTE);
		return resp;
	}

	/**
	 *
	 */
	public EsitoFornitoriResponse getErroreParametriInvalidiEsitoFornitori() {
		EsitoFornitoriResponse resp = new EsitoFornitoriResponse();
		resp.setCodiceErrore("003");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI);
		return resp;
	}

	/**
	 *
	 */
	public EsitoFornitoriResponse getErroreConnessioneDbEsitoFornitori() {
		EsitoFornitoriResponse resp = new EsitoFornitoriResponse();
		resp.setCodiceErrore("002");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.CONNESSIONE_DATABASE);
		return resp;
	}

	/**
	 *
	 */
	public EsitoFornitoriResponse getErroreNessunDatoSelezionatoEsitoFornitori() {
		EsitoFornitoriResponse resp = new EsitoFornitoriResponse();
		resp.setCodiceErrore("001");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.NESSUN_DATO_SELEZIONATO);
		return resp;
	}

	/**
	 *
	 */
	public EsitoFornitoriResponse getErroreGenericoEsitoFornitori() {
		EsitoFornitoriResponse resp = new EsitoFornitoriResponse();
		resp.setCodiceErrore("005");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.GENERICO);
		return resp;
	}

	/**
	 *
	 */
	public EsitoVociDiSpesaResponse getErroreParametriInvalidiEsitoVociDiSpesa() {
		EsitoVociDiSpesaResponse resp = new EsitoVociDiSpesaResponse();
		resp.setEsitoServizio("KO");
		resp.setCodiceErrore("003");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI);
		return resp;
	}

	/**
	 *
	 */
	public EsitoVociDiSpesaResponse getErroreConnessioneDbEsitoVociDiSpesa() {
		EsitoVociDiSpesaResponse resp = new EsitoVociDiSpesaResponse();
		resp.setEsitoServizio("KO");
		resp.setCodiceErrore("002");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.CONNESSIONE_DATABASE);
		return resp;
	}

	/**
	 *
	 */
	public EsitoVociDiSpesaResponse getErroreNessunDatoSelezionatoEsitoVociDiSpesa() {
		EsitoVociDiSpesaResponse resp = new EsitoVociDiSpesaResponse();
		resp.setEsitoServizio("KO");
		resp.setCodiceErrore("001");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.NESSUN_DATO_SELEZIONATO);
		return resp;
	}

	/**
	 *
	 */
	public EsitoVociDiSpesaResponse getErroreGenericoEsitoVociDiSpesa() {
		EsitoVociDiSpesaResponse resp = new EsitoVociDiSpesaResponse();
		resp.setEsitoServizio("KO");
		resp.setCodiceErrore("005");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.GENERICO);
		return resp;
	}

	/**
	 *
	 */
	public DomandeConcesseResponse getErroreParametriInvalidiDomandeConcesse() {
		DomandeConcesseResponse resp = new DomandeConcesseResponse();
		resp.setCodiceErrore("003");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 *
	 */
	public DomandeConcesseResponse getErroreNomeFileNonCorretto() {
		DomandeConcesseResponse resp = new DomandeConcesseResponse();
		resp.setCodiceErrore("004");
		resp.setMessaggio("Nome file non corretto");
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 *
	 */
	public DichiarazioneSpesaResponse getErroreParametriInvalidiDichiarazioneSpese() {
		DichiarazioneSpesaResponse resp = new DichiarazioneSpesaResponse();
		resp.setCodiceErrore("003");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 *
	 */
	public DichiarazioneSpesaResponse getErroreDocumentazioneNonAcquisita() {
		DichiarazioneSpesaResponse resp = new DichiarazioneSpesaResponse();
		resp.setCodiceErrore("005");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.DOCUMENTAZIONE_NON_ACQUISITA);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 *
	 */
	public ElencoMensilitaResponse getErroreParametriInvalidiElencoMensilita() {
		ElencoMensilitaResponse resp = new ElencoMensilitaResponse();
		resp.setCodiceErrore("003");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 *
	 */
	public ElencoMensilitaResponse getErroreNessunDato() {
		ElencoMensilitaResponse resp = new ElencoMensilitaResponse();
		resp.setCodiceErrore("001");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.NESSUN_DATO_SELEZIONATO);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 *
	 */
	public ElencoMensilitaResponse getErroreGenerico(String message) {
		ElencoMensilitaResponse resp = new ElencoMensilitaResponse();
		resp.setCodiceErrore("007");
		resp.setMessaggio(message);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 *
	 */
	public RicezioneSegnalazioniResponse getErroreGenericoRicezioneSegnalazioni(String message) {
		RicezioneSegnalazioniResponse resp = new RicezioneSegnalazioniResponse();
		resp.setCodiceErrore("007");
		resp.setMessaggio(message);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 *
	 */
	public RicezioneSegnalazioniResponse getErroreParametriInvalidiRicezioneSegnalazioni() {
		RicezioneSegnalazioniResponse resp = new RicezioneSegnalazioniResponse();
		resp.setCodiceErrore("003");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.PARAMETRI_INVALIDI);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

	/**
	 * 
	 */
	public RicezioneSegnalazioniResponse getErroreNessunRisultato() {
		RicezioneSegnalazioniResponse resp = new RicezioneSegnalazioniResponse();
		resp.setCodiceErrore("005");
		resp.setMessaggio(Constants.MESSAGGI.ERRORE.CODICE_FISCALE_ERRATO);
		resp.setEsitoServizio(Constants.ESITO.KO);
		return resp;
	}

}
