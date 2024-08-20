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

public class InizializzaConcludiRichiestaContoEconomicoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private String codiceVisualizzatoProgetto;
	private ArrayList<CodiceDescrizione> rappresentantiLegali;
	private ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione;
	private Boolean importModificabili;
	private Double importoFinanziamentoRichiesto;
	private Long idContoEconomico;
	private Double totaleRichiestoInDomanda;

	public Double getTotaleRichiestoInDomanda() {
		return totaleRichiestoInDomanda;
	}

	public void setTotaleRichiestoInDomanda(Double totaleRichiestoInDomanda) {
		this.totaleRichiestoInDomanda = totaleRichiestoInDomanda;
	}

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public Long getIdContoEconomico() {
		return idContoEconomico;
	}

	public void setIdContoEconomico(Long idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}

	public Double getImportoFinanziamentoRichiesto() {
		return importoFinanziamentoRichiesto;
	}

	public void setImportoFinanziamentoRichiesto(Double importoFinanziamentoRichiesto) {
		this.importoFinanziamentoRichiesto = importoFinanziamentoRichiesto;
	}

	public Boolean getImportModificabili() {
		return importModificabili;
	}

	public void setImportModificabili(Boolean importModificabili) {
		this.importModificabili = importModificabili;
	}

	public ArrayList<ModalitaAgevolazione> getListaModalitaAgevolazione() {
		return listaModalitaAgevolazione;
	}

	public void setListaModalitaAgevolazione(ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione) {
		this.listaModalitaAgevolazione = listaModalitaAgevolazione;
	}

	public ArrayList<CodiceDescrizione> getRappresentantiLegali() {
		return rappresentantiLegali;
	}

	public void setRappresentantiLegali(ArrayList<CodiceDescrizione> rappresentantiLegali) {
		this.rappresentantiLegali = rappresentantiLegali;
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
