/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto;



import it.csi.pbandi.pbservizit.util.BeanUtil;

import java.beans.IntrospectionException;
import java.util.Set;

public class ContoEconomico implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean hasCopiaPresente = false;
	private String dataUltimaProposta = null;
	private String dataUltimaRimodulazione = null;
	private String dataPresentazioneDomanda = null;
	private String dataFineIstruttoria = null;
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

	public String getDataUltimaProposta() {
		return dataUltimaProposta;
	}

	public void setDataUltimaProposta(String dataUltimaProposta) {
		this.dataUltimaProposta = dataUltimaProposta;
	}

	public String getDataUltimaRimodulazione() {
		return dataUltimaRimodulazione;
	}

	public void setDataUltimaRimodulazione(String dataUltimaRimodulazione) {
		this.dataUltimaRimodulazione = dataUltimaRimodulazione;
	}

	public String getDataPresentazioneDomanda() {
		return dataPresentazioneDomanda;
	}

	public void setDataPresentazioneDomanda(String dataPresentazioneDomanda) {
		this.dataPresentazioneDomanda = dataPresentazioneDomanda;
	}

	public String getDataFineIstruttoria() {
		return dataFineIstruttoria;
	}

	public void setDataFineIstruttoria(String dataFineIstruttoria) {
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
