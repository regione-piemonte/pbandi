/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.revoca;

public class DettaglioRevocaIrregolaritaDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private Long idDettRevocaIrreg;
	private Long idRevoca;
	private Long idIrregolarita;
	private Long idClassRevocaIrreg;
	private Double importo;
	private Double importoAudit;
	private String tipologia;
	private DsDettaglioRevocaIrregolaritaDTO[] dsDettagliRevocaIrregolarita;
	public Long getIdDettRevocaIrreg() {
		return idDettRevocaIrreg;
	}
	public void setIdDettRevocaIrreg(Long idDettRevocaIrreg) {
		this.idDettRevocaIrreg = idDettRevocaIrreg;
	}
	public Long getIdRevoca() {
		return idRevoca;
	}
	public void setIdRevoca(Long idRevoca) {
		this.idRevoca = idRevoca;
	}
	public Long getIdIrregolarita() {
		return idIrregolarita;
	}
	public void setIdIrregolarita(Long idIrregolarita) {
		this.idIrregolarita = idIrregolarita;
	}
	public Long getIdClassRevocaIrreg() {
		return idClassRevocaIrreg;
	}
	public void setIdClassRevocaIrreg(Long idClassRevocaIrreg) {
		this.idClassRevocaIrreg = idClassRevocaIrreg;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public Double getImportoAudit() {
		return importoAudit;
	}
	public void setImportoAudit(Double importoAudit) {
		this.importoAudit = importoAudit;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public DsDettaglioRevocaIrregolaritaDTO[] getDsDettagliRevocaIrregolarita() {
		return dsDettagliRevocaIrregolarita;
	}
	public void setDsDettagliRevocaIrregolarita(DsDettaglioRevocaIrregolaritaDTO[] dsDettagliRevocaIrregolarita) {
		this.dsDettagliRevocaIrregolarita = dsDettagliRevocaIrregolarita;
	}

	
	
}
