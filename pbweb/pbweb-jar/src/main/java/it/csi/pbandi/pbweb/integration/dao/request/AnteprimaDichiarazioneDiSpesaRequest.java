/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.request;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.TipoAllegatoDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class AnteprimaDichiarazioneDiSpesaRequest {
		
	// Da valorizzare solo se si vuole generare l'anteprima +Green.
	// Bottone "anteprima dichiarazione progetto a contributo".
	private Long idProgettoContributoPiuGreen;				
	
	private Long idBandoLinea;
	private Long idProgetto;
	private Long idSoggetto;
	private Long idSoggettoBeneficiario;
	private Date dataLimiteDocumentiRendicontabili;
	private String codiceTipoDichiarazioneDiSpesa;			// I (intermedia), F (finale), IN (integrativa).
	private Long idRappresentanteLegale;
	private Long idDelegato;								// non obbligatorio.
	
	// Campi per la DS Finale + Comunicazione Fine Progetto.	
	private Double importoRichiestaSaldo;
	private String note;									// a video sono le Osservazioni.
	
	private ArrayList<TipoAllegatoDTO> listaTipoAllegati;
	
	public ArrayList<TipoAllegatoDTO> getListaTipoAllegati() {
		return listaTipoAllegati;
	}

	public void setListaTipoAllegati(ArrayList<TipoAllegatoDTO> listaTipoAllegati) {
		this.listaTipoAllegati = listaTipoAllegati;
	}

	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}

	public Double getImportoRichiestaSaldo() {
		return importoRichiestaSaldo;
	}

	public void setImportoRichiestaSaldo(Double importoRichiestaSaldo) {
		this.importoRichiestaSaldo = importoRichiestaSaldo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getIdBandoLinea() {
		return idBandoLinea;
	}

	public void setIdBandoLinea(Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Long getIdProgettoContributoPiuGreen() {
		return idProgettoContributoPiuGreen;
	}

	public void setIdProgettoContributoPiuGreen(Long idProgettoContributoPiuGreen) {
		this.idProgettoContributoPiuGreen = idProgettoContributoPiuGreen;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public Date getDataLimiteDocumentiRendicontabili() {
		return dataLimiteDocumentiRendicontabili;
	}

	public void setDataLimiteDocumentiRendicontabili(Date dataLimiteDocumentiRendicontabili) {
		this.dataLimiteDocumentiRendicontabili = dataLimiteDocumentiRendicontabili;
	}

	public String getCodiceTipoDichiarazioneDiSpesa() {
		return codiceTipoDichiarazioneDiSpesa;
	}

	public void setCodiceTipoDichiarazioneDiSpesa(String codiceTipoDichiarazioneDiSpesa) {
		this.codiceTipoDichiarazioneDiSpesa = codiceTipoDichiarazioneDiSpesa;
	}

	public Long getIdRappresentanteLegale() {
		return idRappresentanteLegale;
	}

	public void setIdRappresentanteLegale(Long idRappresentanteLegale) {
		this.idRappresentanteLegale = idRappresentanteLegale;
	}

	public Long getIdDelegato() {
		return idDelegato;
	}

	public void setIdDelegato(Long idDelegato) {
		this.idDelegato = idDelegato;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				if (propName.equalsIgnoreCase("listaTipoAllegati")) {
					ArrayList<TipoAllegatoDTO> temp = (ArrayList<TipoAllegatoDTO>) BeanUtil.getPropertyValueByName(this, propName);
					sb.append((temp == null) ? "\nlistaTipoAllegati = null" : "\nlistaTipoAllegati.size = "+listaTipoAllegati.size());
				} else {
					sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
				}
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}

}
