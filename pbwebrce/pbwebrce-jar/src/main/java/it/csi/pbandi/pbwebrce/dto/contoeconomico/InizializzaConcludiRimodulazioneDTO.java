/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.contoeconomico;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import it.csi.pbandi.pbwebrce.dto.FinanziamentoFonteFinanziaria;
import it.csi.pbandi.pbwebrce.dto.ModalitaAgevolazione;
import it.csi.pbandi.pbwebrce.util.BeanUtil;

public class InizializzaConcludiRimodulazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private String codiceVisualizzatoProgetto;
	private String tipoOperazione;
	private ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione;
	private ArrayList<FinanziamentoFonteFinanziaria> fontiFiltrate;
	private ArrayList<FinanziamentoFonteFinanziaria> listaFonti;
	private Double totaleSpesaAmmessaInRimodulazione;
	private Double totaleUltimaSpesaAmmessa;
	private Double importoUltimaPropostaCertificazionePerProgetto;
	private Double importoFinanziamentoBancario;
	private Double importoImpegnoGiuridico;
	private Date dataConcessione;
	private Double importoTotaleAgevolato; 
	
	public Double getImportoTotaleAgevolato() {
		return importoTotaleAgevolato;
	}

	public void setImportoTotaleAgevolato(Double importoTotaleAgevolato) {
		this.importoTotaleAgevolato = importoTotaleAgevolato;
	}

	public Date getDataConcessione() {
		return dataConcessione;
	}

	public void setDataConcessione(Date dataConcessione) {
		this.dataConcessione = dataConcessione;
	}

	public Double getImportoImpegnoGiuridico() {
		return importoImpegnoGiuridico;
	}

	public void setImportoImpegnoGiuridico(Double importoImpegnoGiuridico) {
		this.importoImpegnoGiuridico = importoImpegnoGiuridico;
	}

	public Double getImportoFinanziamentoBancario() {
		return importoFinanziamentoBancario;
	}

	public void setImportoFinanziamentoBancario(Double importoFinanziamentoBancario) {
		this.importoFinanziamentoBancario = importoFinanziamentoBancario;
	}

	public Double getImportoUltimaPropostaCertificazionePerProgetto() {
		return importoUltimaPropostaCertificazionePerProgetto;
	}

	public void setImportoUltimaPropostaCertificazionePerProgetto(Double importoUltimaPropostaCertificazionePerProgetto) {
		this.importoUltimaPropostaCertificazionePerProgetto = importoUltimaPropostaCertificazionePerProgetto;
	}

	public String getTipoOperazione() {
		return tipoOperazione;
	}

	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}

	public ArrayList<FinanziamentoFonteFinanziaria> getFontiFiltrate() {
		return fontiFiltrate;
	}

	public void setFontiFiltrate(ArrayList<FinanziamentoFonteFinanziaria> fontiFiltrate) {
		this.fontiFiltrate = fontiFiltrate;
	}

	public ArrayList<FinanziamentoFonteFinanziaria> getListaFonti() {
		return listaFonti;
	}

	public void setListaFonti(ArrayList<FinanziamentoFonteFinanziaria> listaFonti) {
		this.listaFonti = listaFonti;
	}

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

	public ArrayList<ModalitaAgevolazione> getListaModalitaAgevolazione() {
		return listaModalitaAgevolazione;
	}

	public void setListaModalitaAgevolazione(ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione) {
		this.listaModalitaAgevolazione = listaModalitaAgevolazione;
	}

	public Double getTotaleSpesaAmmessaInRimodulazione() {
		return totaleSpesaAmmessaInRimodulazione;
	}

	public void setTotaleSpesaAmmessaInRimodulazione(Double totaleSpesaAmmessaInRimodulazione) {
		this.totaleSpesaAmmessaInRimodulazione = totaleSpesaAmmessaInRimodulazione;
	}

	public Double getTotaleUltimaSpesaAmmessa() {
		return totaleUltimaSpesaAmmessa;
	}

	public void setTotaleUltimaSpesaAmmessa(Double totaleUltimaSpesaAmmessa) {
		this.totaleUltimaSpesaAmmessa = totaleUltimaSpesaAmmessa;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}

}
