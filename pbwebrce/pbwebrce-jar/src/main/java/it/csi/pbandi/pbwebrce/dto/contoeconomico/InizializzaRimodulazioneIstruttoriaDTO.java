/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.contoeconomico;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbwebrce.dto.ContoEconomico;
import it.csi.pbandi.pbwebrce.dto.ContoEconomicoItem;
import it.csi.pbandi.pbwebrce.util.BeanUtil;

public class InizializzaRimodulazioneIstruttoriaDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO;
	private ArrayList<ContoEconomicoItem> righeContoEconomico;	
	private String codiceVisualizzatoProgetto;
	
	public it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO getEsitoFindContoEconomicoDTO() {
		return esitoFindContoEconomicoDTO;
	}

	public void setEsitoFindContoEconomicoDTO(
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO) {
		this.esitoFindContoEconomicoDTO = esitoFindContoEconomicoDTO;
	}

	public ArrayList<ContoEconomicoItem> getRigheContoEconomico() {
		return righeContoEconomico;
	}

	public void setRigheContoEconomico(ArrayList<ContoEconomicoItem> righeContoEconomico) {
		this.righeContoEconomico = righeContoEconomico;
	}

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
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
