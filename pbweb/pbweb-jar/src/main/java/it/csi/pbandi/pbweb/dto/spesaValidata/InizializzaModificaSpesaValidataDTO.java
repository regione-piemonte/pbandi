/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.spesaValidata;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoDTO;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoPagamentoDTO;
import it.csi.pbandi.pbweb.dto.RigaNotaDiCreditoItemDTO;
import it.csi.pbandi.pbweb.dto.RigaValidazioneItemDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.DocumentoDiSpesa;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class InizializzaModificaSpesaValidataDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private String codiceVisualizzatoProgetto;
	
	private DocumentoDiSpesa documentoDiSpesa;
	
	private ArrayList<RigaValidazioneItemDTO> pagamentiAssociati;
	 
	private ArrayList<RigaNotaDiCreditoItemDTO> noteDiCredito;
	
	// Se true, si visualizza il seguente msg:
	// Per il progetto è stata creata almeno una proposta di certificazione. Eventuali rettifiche sulla spesa validata potranno avere effetto sugli importi certificati.
	private Boolean esistePropostaCertificazione;
	
	private Boolean bottoneSalvaVisibile;
	
	
	ArrayList<DocumentoAllegatoDTO> allegatiDocumento;
	ArrayList<DocumentoAllegatoPagamentoDTO> allegatiPagamento;
	ArrayList<DocumentoAllegatoDTO> allegatiFornitore;
	ArrayList<DocumentoAllegatoDTO> allegatiNoteDiCredito;
	

	public ArrayList<DocumentoAllegatoDTO> getAllegatiNoteDiCredito() {
		return allegatiNoteDiCredito;
	}

	public void setAllegatiNoteDiCredito(ArrayList<DocumentoAllegatoDTO> allegatiNoteDiCredito) {
		this.allegatiNoteDiCredito = allegatiNoteDiCredito;
	}

	public Boolean getBottoneSalvaVisibile() {
		return bottoneSalvaVisibile;
	}

	public ArrayList<DocumentoAllegatoDTO> getAllegatiDocumento() {
		return allegatiDocumento;
	}

	public void setAllegatiDocumento(ArrayList<DocumentoAllegatoDTO> allegatiDocumento) {
		this.allegatiDocumento = allegatiDocumento;
	}

	public ArrayList<DocumentoAllegatoPagamentoDTO> getAllegatiPagamento() {
		return allegatiPagamento;
	}

	public void setAllegatiPagamento(ArrayList<DocumentoAllegatoPagamentoDTO> allegatiPagamento) {
		this.allegatiPagamento = allegatiPagamento;
	}

	public ArrayList<DocumentoAllegatoDTO> getAllegatiFornitore() {
		return allegatiFornitore;
	}

	public void setAllegatiFornitore(ArrayList<DocumentoAllegatoDTO> allegatiFornitore) {
		this.allegatiFornitore = allegatiFornitore;
	}

	public void setBottoneSalvaVisibile(Boolean bottoneSalvaVisibile) {
		this.bottoneSalvaVisibile = bottoneSalvaVisibile;
	}

	public Boolean getEsistePropostaCertificazione() {
		return esistePropostaCertificazione;
	}

	public void setEsistePropostaCertificazione(Boolean esistePropostaCertificazione) {
		this.esistePropostaCertificazione = esistePropostaCertificazione;
	}

	public ArrayList<RigaNotaDiCreditoItemDTO> getNoteDiCredito() {
		return noteDiCredito;
	}

	public void setNoteDiCredito(ArrayList<RigaNotaDiCreditoItemDTO> noteDiCredito) {
		this.noteDiCredito = noteDiCredito;
	}

	public ArrayList<RigaValidazioneItemDTO> getPagamentiAssociati() {
		return pagamentiAssociati;
	}

	public void setPagamentiAssociati(ArrayList<RigaValidazioneItemDTO> pagamentiAssociati) {
		this.pagamentiAssociati = pagamentiAssociati;
	}

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

	public DocumentoDiSpesa getDocumentoDiSpesa() {
		return documentoDiSpesa;
	}

	public void setDocumentoDiSpesa(DocumentoDiSpesa documentoDiSpesa) {
		this.documentoDiSpesa = documentoDiSpesa;
	}

	
}
