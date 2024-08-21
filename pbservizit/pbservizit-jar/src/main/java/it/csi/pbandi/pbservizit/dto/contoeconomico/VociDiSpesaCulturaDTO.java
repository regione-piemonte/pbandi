/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.contoeconomico;

import it.csi.pbandi.pbservizit.dto.ContoEconomico;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO;
import it.csi.pbandi.pbservizit.util.BeanUtil;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

public class VociDiSpesaCulturaDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO;
	private ContoEconomico datiContoEconomico;
	private ArrayList<ContoEconomicoItem> righeContoEconomico;	
	private String codiceVisualizzatoProgetto;
	
	// In più rispetto alla proposta.
	
	private Boolean progettoRicevente;
	private Double sommaEconomieUtilizzate;
	private Double percSpGenFunz;

	// A db è quota_importo_agevolato
	private Double contributoConcesso;
	public Boolean getProgettoRicevente() {
		return progettoRicevente;
	}

	public void setProgettoRicevente(Boolean progettoRicevente) {
		this.progettoRicevente = progettoRicevente;
	}

	public Double getSommaEconomieUtilizzate() {
		return sommaEconomieUtilizzate;
	}

	public void setSommaEconomieUtilizzate(Double sommaEconomieUtilizzate) {
		this.sommaEconomieUtilizzate = sommaEconomieUtilizzate;
	}

	public EsitoFindContoEconomicoDTO getEsitoFindContoEconomicoDTO() {
		return esitoFindContoEconomicoDTO;
	}

	public void setEsitoFindContoEconomicoDTO(
			EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO) {
		this.esitoFindContoEconomicoDTO = esitoFindContoEconomicoDTO;
	}

	public ContoEconomico getDatiContoEconomico() {
		return datiContoEconomico;
	}

	public void setDatiContoEconomico(ContoEconomico datiContoEconomico) {
		this.datiContoEconomico = datiContoEconomico;
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


	public Double getPercSpGenFunz() {
		return percSpGenFunz;
	}

	public void setPercSpGenFunz(Double percSpGenFunz) {
		this.percSpGenFunz = percSpGenFunz;
	}

	public Double getContributoConcesso() {
		return contributoConcesso;
	}

	public void setContributoConcesso(Double contributoConcesso) {
		this.contributoConcesso = contributoConcesso;
	}
}
