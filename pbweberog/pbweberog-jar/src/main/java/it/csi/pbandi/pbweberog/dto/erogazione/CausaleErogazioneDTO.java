/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

import java.util.Date;

public class CausaleErogazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private Long idCausaleErogazione;
	private String descBreveCausale;
	private String descCausale;
	private Double importoErogazione;
	private Date dtContabile;
	private String codRiferimentoErogazione;
	private Long idErogazione;
	public Long getIdCausaleErogazione() {
		return idCausaleErogazione;
	}
	public void setIdCausaleErogazione(Long idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}
	public String getDescBreveCausale() {
		return descBreveCausale;
	}
	public void setDescBreveCausale(String descBreveCausale) {
		this.descBreveCausale = descBreveCausale;
	}
	public String getDescCausale() {
		return descCausale;
	}
	public void setDescCausale(String descCausale) {
		this.descCausale = descCausale;
	}
	public Double getImportoErogazione() {
		return importoErogazione;
	}
	public void setImportoErogazione(Double importoErogazione) {
		this.importoErogazione = importoErogazione;
	}
	public Date getDtContabile() {
		return dtContabile;
	}
	public void setDtContabile(Date dtContabile) {
		this.dtContabile = dtContabile;
	}
	public String getCodRiferimentoErogazione() {
		return codRiferimentoErogazione;
	}
	public void setCodRiferimentoErogazione(String codRiferimentoErogazione) {
		this.codRiferimentoErogazione = codRiferimentoErogazione;
	}
	public Long getIdErogazione() {
		return idErogazione;
	}
	public void setIdErogazione(Long idErogazione) {
		this.idErogazione = idErogazione;
	}
	
	
	
}
