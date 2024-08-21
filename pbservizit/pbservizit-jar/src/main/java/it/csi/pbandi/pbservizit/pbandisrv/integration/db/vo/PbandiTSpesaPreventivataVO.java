/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class PbandiTSpesaPreventivataVO extends GenericVO {
		private BigDecimal idSpesaPreventivata;
  	private BigDecimal idRigoContoEconomico;
  	private BigDecimal importoSpesaPreventivata;
  	private BigDecimal idUtenteIns;
		private BigDecimal idUtenteAgg;
		private Date dtInserimento;
		private Date dtModifica;

	public PbandiTSpesaPreventivataVO(BigDecimal idSpesaPreventivata, BigDecimal idRigoContoEconomico, BigDecimal importoSpesaPreventivata, BigDecimal idUtenteIns, BigDecimal idUtenteAgg, Date dtInserimento, Date dtModifica) {
		this.idSpesaPreventivata = idSpesaPreventivata;
		this.idRigoContoEconomico = idRigoContoEconomico;
		this.importoSpesaPreventivata = importoSpesaPreventivata;
		this.idUtenteIns = idUtenteIns;
		this.idUtenteAgg = idUtenteAgg;
		this.dtInserimento = dtInserimento;
		this.dtModifica = dtModifica;
	}

	public PbandiTSpesaPreventivataVO() {
	}

	public PbandiTSpesaPreventivataVO(BigDecimal idSpesaPreventivata) {
		this.idSpesaPreventivata = idSpesaPreventivata;
	}


	public List<String> getPK() {
		List<String> pk = new ArrayList<>();
		pk.add("idSpesaPreventivata");
		return pk;
	}

	public boolean isValid() {
		return isPKValid() && dtInserimento != null && idRigoContoEconomico != null && idUtenteIns != null && importoSpesaPreventivata != null;
	}
	public boolean isPKValid() {

		return idRigoContoEconomico != null;
	}

	public BigDecimal getIdSpesaPreventivata() {
		return idSpesaPreventivata;
	}

	public void setIdSpesaPreventivata(BigDecimal idSpesaPreventivata) {
		this.idSpesaPreventivata = idSpesaPreventivata;
	}

	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}

	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}

	public BigDecimal getImportoSpesaPreventivata() {
		return importoSpesaPreventivata;
	}

	public void setImportoSpesaPreventivata(BigDecimal importoSpesaPreventivata) {
		this.importoSpesaPreventivata = importoSpesaPreventivata;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public Date getDtInserimento() {
		return dtInserimento;
	}

	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}

	public Date getDtModifica() {
		return dtModifica;
	}

	public void setDtModifica(Date dtModifica) {
		this.dtModifica = dtModifica;
	}

	@Override
	public String toString() {
		return "PbandiTSpesaPreventivataVO{" +
				"idSpesaPreventivata=" + idSpesaPreventivata +
				", idRigoContoEconomico=" + idRigoContoEconomico +
				", importoSpesaPreventivata=" + importoSpesaPreventivata +
				", idUtenteIns=" + idUtenteIns +
				", idUtenteAgg=" + idUtenteAgg +
				", dtInserimento=" + dtInserimento +
				", dtModifica=" + dtModifica +
				"} ";
	}


}
