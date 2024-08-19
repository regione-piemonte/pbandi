/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.disimpegni;

import java.util.ArrayList;

public class DettaglioRevocaIrregolarita implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idDettRevocaIrreg;
	private Long idRevoca;
	private Long idIrregolarita;
	private Long idClassRevocaIrreg;
	private Double importo;
	private Double importoAudit;
	private String tipologia;
	private ArrayList<DsDettaglioRevocaIrregolarita> dsDettagliRevocaIrregolarita = new ArrayList<DsDettaglioRevocaIrregolarita>();

	
	
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

	public ArrayList<DsDettaglioRevocaIrregolarita> getDsDettagliRevocaIrregolarita() {
		return dsDettagliRevocaIrregolarita;
	}

	public void setDsDettagliRevocaIrregolarita(ArrayList<DsDettaglioRevocaIrregolarita> dsDettagliRevocaIrregolarita) {
		this.dsDettagliRevocaIrregolarita = dsDettagliRevocaIrregolarita;
	}

	public DettaglioRevocaIrregolarita() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
