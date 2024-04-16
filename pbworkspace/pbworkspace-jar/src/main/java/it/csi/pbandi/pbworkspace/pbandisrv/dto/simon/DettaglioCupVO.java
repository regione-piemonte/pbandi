/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.dto.simon;

import java.util.ArrayList;

public class DettaglioCupVO {
	private DatiGeneraliProgettoVO datiGeneraliProgetto; 
	private String tipoOperazioneDettaglioCup;

	private ArrayList<LocalizzazioneVO> localizzazioni;
	private AttivitaEconomicaBeneficiarioVO attivitaEconomicaBeneficiario;
	private DescrizioneDettaglioCupVO descrizioneDettaglioCup;
	
	private boolean esito;
	
	public String getCodiceMessaggio() {
		return codiceMessaggio;
	}
	public void setCodiceMessaggio(String codiceMessaggio) {
		this.codiceMessaggio = codiceMessaggio;
	}
	private String codiceMessaggio;
	
	public DettaglioCupVO() {
		super();
		setDatiGeneraliProgetto(new DatiGeneraliProgettoVO());
		setLocalizzazioni(new ArrayList<LocalizzazioneVO>());
		setAttivitaEconomicaBeneficiario(new AttivitaEconomicaBeneficiarioVO());
		setDescrizioneDettaglioCup(new DescrizioneDettaglioCupVO());
		this.tipoOperazioneDettaglioCup = new String();
		setEsito(false);
		setCodiceMessaggio(null);
	}
	public String getTipoOperazioneDettaglioCup() {
		return tipoOperazioneDettaglioCup;
	}
	public void setTipoOperazioneDettaglioCup(String tipoOperazioneDettaglioCup) {
		this.tipoOperazioneDettaglioCup = tipoOperazioneDettaglioCup;
	}

	public DatiGeneraliProgettoVO getDatiGeneraliProgetto() {
		return datiGeneraliProgetto;
	}
	public void setDatiGeneraliProgetto(DatiGeneraliProgettoVO datiGeneraliProgetto) {
		this.datiGeneraliProgetto = datiGeneraliProgetto;
	}
	
	public ArrayList<LocalizzazioneVO> getLocalizzazioni() {
		return localizzazioni;
	}
	public void setLocalizzazioni(ArrayList<LocalizzazioneVO> localizzazioni) {
		this.localizzazioni = localizzazioni;
	}
	public AttivitaEconomicaBeneficiarioVO getAttivitaEconomicaBeneficiario() {
		return attivitaEconomicaBeneficiario;
	}
	public void setAttivitaEconomicaBeneficiario(
			AttivitaEconomicaBeneficiarioVO attivitaEconomicaBeneficiario) {
		this.attivitaEconomicaBeneficiario = attivitaEconomicaBeneficiario;
	}
	public DescrizioneDettaglioCupVO getDescrizioneDettaglioCup() {
		return descrizioneDettaglioCup;
	}
	public void setDescrizioneDettaglioCup(
			DescrizioneDettaglioCupVO descrizioneDettaglioCup) {
		this.descrizioneDettaglioCup = descrizioneDettaglioCup;
	}

	public boolean isEsito() {
		return esito;
	}
	public void setEsito(boolean esito) {
		this.esito = esito;
	}
	
}
