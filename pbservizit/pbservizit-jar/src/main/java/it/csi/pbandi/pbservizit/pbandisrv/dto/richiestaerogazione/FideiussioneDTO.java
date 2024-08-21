/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione;

import java.util.Date;

public class FideiussioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private String descrizione;
	private String descrizioneTipoTrattamento;
	private Date dataDecorrenza;
	private Date dataScadenza;
	private String numero;
	private Double importo;
	private Double importoTotaleTipoTrattamento;
	private Long id;
	private Long idTipoTrattamento;
	private String descEnteEmittente;
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDescrizioneTipoTrattamento() {
		return descrizioneTipoTrattamento;
	}
	public void setDescrizioneTipoTrattamento(String descrizioneTipoTrattamento) {
		this.descrizioneTipoTrattamento = descrizioneTipoTrattamento;
	}
	public Date getDataDecorrenza() {
		return dataDecorrenza;
	}
	public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public Double getImportoTotaleTipoTrattamento() {
		return importoTotaleTipoTrattamento;
	}
	public void setImportoTotaleTipoTrattamento(Double importoTotaleTipoTrattamento) {
		this.importoTotaleTipoTrattamento = importoTotaleTipoTrattamento;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdTipoTrattamento() {
		return idTipoTrattamento;
	}
	public void setIdTipoTrattamento(Long idTipoTrattamento) {
		this.idTipoTrattamento = idTipoTrattamento;
	}
	public String getDescEnteEmittente() {
		return descEnteEmittente;
	}
	public void setDescEnteEmittente(String descEnteEmittente) {
		this.descEnteEmittente = descEnteEmittente;
	}
	
	

	
}
