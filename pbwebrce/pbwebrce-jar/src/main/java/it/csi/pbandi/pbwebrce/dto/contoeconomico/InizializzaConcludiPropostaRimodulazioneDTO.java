/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.contoeconomico;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbwebrce.dto.CodiceDescrizione;
import it.csi.pbandi.pbwebrce.dto.ContoEconomico;
import it.csi.pbandi.pbwebrce.dto.ContoEconomicoItem;
import it.csi.pbandi.pbwebrce.dto.ModalitaAgevolazione;
import it.csi.pbandi.pbwebrce.dto.ProceduraAggiudicazione;
import it.csi.pbandi.pbwebrce.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbwebrce.util.BeanUtil;

public class InizializzaConcludiPropostaRimodulazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private String codiceVisualizzatoProgetto;
	private Boolean allegatiAmmessi;			// nel js messo in hInvioDigitale.
	private ArrayList<CodiceDescrizione> rappresentantiLegali;
	private ArrayList<CodiceDescrizione> delegati;
	private ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione;
	private ArrayList<ProceduraAggiudicazione> listaProcedureAggiudicazione;
	private Double totaleRichiestoNuovaProposta;

	public Double getTotaleRichiestoNuovaProposta() {
		return totaleRichiestoNuovaProposta;
	}

	public void setTotaleRichiestoNuovaProposta(Double totaleRichiestoNuovaProposta) {
		this.totaleRichiestoNuovaProposta = totaleRichiestoNuovaProposta;
	}

	public ArrayList<ProceduraAggiudicazione> getListaProcedureAggiudicazione() {
		return listaProcedureAggiudicazione;
	}

	public void setListaProcedureAggiudicazione(ArrayList<ProceduraAggiudicazione> listaProcedureAggiudicazione) {
		this.listaProcedureAggiudicazione = listaProcedureAggiudicazione;
	}

	public ArrayList<ModalitaAgevolazione> getListaModalitaAgevolazione() {
		return listaModalitaAgevolazione;
	}

	public void setListaModalitaAgevolazione(ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione) {
		this.listaModalitaAgevolazione = listaModalitaAgevolazione;
	}

	public ArrayList<CodiceDescrizione> getDelegati() {
		return delegati;
	}

	public void setDelegati(ArrayList<CodiceDescrizione> delegati) {
		this.delegati = delegati;
	}

	public ArrayList<CodiceDescrizione> getRappresentantiLegali() {
		return rappresentantiLegali;
	}

	public void setRappresentantiLegali(ArrayList<CodiceDescrizione> rappresentantiLegali) {
		this.rappresentantiLegali = rappresentantiLegali;
	}

	public Boolean getAllegatiAmmessi() {
		return allegatiAmmessi;
	}

	public void setAllegatiAmmessi(Boolean allegatiAmmessi) {
		this.allegatiAmmessi = allegatiAmmessi;
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
