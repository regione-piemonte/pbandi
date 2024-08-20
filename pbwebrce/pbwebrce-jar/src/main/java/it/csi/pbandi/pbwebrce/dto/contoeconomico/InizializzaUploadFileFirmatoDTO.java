/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.contoeconomico;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbwebrce.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbwebrce.util.BeanUtil;

public class InizializzaUploadFileFirmatoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private String codiceVisualizzatoProgetto;
	private Long dimMaxFileFirmato;
	
	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

	public Long getDimMaxFileFirmato() {
		return dimMaxFileFirmato;
	}

	public void setDimMaxFileFirmato(Long dimMaxFileFirmato) {
		this.dimMaxFileFirmato = dimMaxFileFirmato;
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
