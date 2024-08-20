/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbwebrce.util.BeanUtil;

public class ContoEconomico implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean hasCopiaPresente = false;
	private java.lang.String dataUltimaProposta = null;
	private java.lang.String dataUltimaRimodulazione = null;
	private java.lang.String dataPresentazioneDomanda = null;
	private java.lang.String dataFineIstruttoria = null;
	private boolean nascondiColonnaImportoRichiesto = false;
	private boolean nascondiColonnaSpesaAmmessa = false;
	
	public ContoEconomico() {
		super();
	}

	public boolean isHasCopiaPresente() {
		return hasCopiaPresente;
	}

	public void setHasCopiaPresente(boolean hasCopiaPresente) {
		this.hasCopiaPresente = hasCopiaPresente;
	}

	public java.lang.String getDataUltimaProposta() {
		return dataUltimaProposta;
	}

	public void setDataUltimaProposta(java.lang.String dataUltimaProposta) {
		this.dataUltimaProposta = dataUltimaProposta;
	}

	public java.lang.String getDataUltimaRimodulazione() {
		return dataUltimaRimodulazione;
	}

	public void setDataUltimaRimodulazione(java.lang.String dataUltimaRimodulazione) {
		this.dataUltimaRimodulazione = dataUltimaRimodulazione;
	}

	public java.lang.String getDataPresentazioneDomanda() {
		return dataPresentazioneDomanda;
	}

	public void setDataPresentazioneDomanda(java.lang.String dataPresentazioneDomanda) {
		this.dataPresentazioneDomanda = dataPresentazioneDomanda;
	}

	public java.lang.String getDataFineIstruttoria() {
		return dataFineIstruttoria;
	}

	public void setDataFineIstruttoria(java.lang.String dataFineIstruttoria) {
		this.dataFineIstruttoria = dataFineIstruttoria;
	}

	public boolean isNascondiColonnaImportoRichiesto() {
		return nascondiColonnaImportoRichiesto;
	}

	public void setNascondiColonnaImportoRichiesto(boolean nascondiColonnaImportoRichiesto) {
		this.nascondiColonnaImportoRichiesto = nascondiColonnaImportoRichiesto;
	}

	public boolean isNascondiColonnaSpesaAmmessa() {
		return nascondiColonnaSpesaAmmessa;
	}

	public void setNascondiColonnaSpesaAmmessa(boolean nascondiColonnaSpesaAmmessa) {
		this.nascondiColonnaSpesaAmmessa = nascondiColonnaSpesaAmmessa;
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
