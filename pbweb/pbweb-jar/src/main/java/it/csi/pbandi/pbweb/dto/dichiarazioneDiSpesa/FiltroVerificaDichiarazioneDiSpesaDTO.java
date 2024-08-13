/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class FiltroVerificaDichiarazioneDiSpesaDTO implements java.io.Serializable {

	private Boolean isRicercaPerCapofila = false;
	private Boolean isRicercaPerPartners = false;
	private java.lang.String dataFineRendicontazione = null;
	private java.lang.String dataInizioRendicontazione = null;
	private Boolean isStartRicerca = false;
	private java.lang.Long codPatner = null;
	private java.lang.Long idProgetto = null;
	
	public FiltroVerificaDichiarazioneDiSpesaDTO() {
	}

	public Boolean getIsRicercaPerCapofila() {
		return isRicercaPerCapofila;
	}

	public void setIsRicercaPerCapofila(Boolean isRicercaPerCapofila) {
		this.isRicercaPerCapofila = isRicercaPerCapofila;
	}

	public Boolean getIsRicercaPerPartners() {
		return isRicercaPerPartners;
	}

	public void setIsRicercaPerPartners(Boolean isRicercaPerPartners) {
		this.isRicercaPerPartners = isRicercaPerPartners;
	}

	public java.lang.String getDataFineRendicontazione() {
		return dataFineRendicontazione;
	}

	public void setDataFineRendicontazione(java.lang.String dataFineRendicontazione) {
		this.dataFineRendicontazione = dataFineRendicontazione;
	}

	public java.lang.String getDataInizioRendicontazione() {
		return dataInizioRendicontazione;
	}

	public void setDataInizioRendicontazione(java.lang.String dataInizioRendicontazione) {
		this.dataInizioRendicontazione = dataInizioRendicontazione;
	}

	public Boolean getIsStartRicerca() {
		return isStartRicerca;
	}

	public void setIsStartRicerca(Boolean isStartRicerca) {
		this.isStartRicerca = isStartRicerca;
	}

	public java.lang.Long getCodPatner() {
		return codPatner;
	}

	public void setCodPatner(java.lang.Long codPatner) {
		this.codPatner = codPatner;
	}

	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
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
