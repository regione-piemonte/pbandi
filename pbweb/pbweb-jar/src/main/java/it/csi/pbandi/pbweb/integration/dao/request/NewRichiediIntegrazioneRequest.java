/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.request;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.RigaValidazioneItemDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class NewRichiediIntegrazioneRequest {
	
	private Long idDichiarazioneDiSpesa;
	private Long numGiorniScadenza;
	private Long idStatoRichiesta;

	public Long getIdDichiarazioneDiSpesa() {
		return idDichiarazioneDiSpesa;
	}

	public void setIdDichiarazioneDiSpesa(Long idDichiarazioneDiSpesa) {
		this.idDichiarazioneDiSpesa = idDichiarazioneDiSpesa;
	}

	
	public Long getNumGiorniScadenza() {
		return numGiorniScadenza;
	}

	public void setNumGiorniScadenza(Long numGiorniScadenza) {
		this.numGiorniScadenza = numGiorniScadenza;
	}

	public Long getIdStatoRichiesta() {
		return idStatoRichiesta;
	}

	public void setIdStatoRichiesta(Long idStatoRichiesta) {
		this.idStatoRichiesta = idStatoRichiesta;
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
