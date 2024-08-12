package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.BigDecimal;
import java.util.List;

public class PbandiRLineaSettoreAttVO extends GenericVO {
	
	private BigDecimal idSettoreAttivita;
	private BigDecimal idLineaDiIntervento;

	@Override
	public boolean isPKValid() {
		// TODO Auto-generated method stub
		return idSettoreAttivita != null && idLineaDiIntervento != null;
	}

	@Override
	public List getPK() {
		java.util.List<String> pk = new java.util.ArrayList<String>();
		pk.add("idSettoreAttivita");
		pk.add("idLineaDiIntervento");
	    return pk;
	}

	public BigDecimal getIdSettoreAttivita() {
		return idSettoreAttivita;
	}

	public void setIdSettoreAttivita(BigDecimal idSettoreAttivita) {
		this.idSettoreAttivita = idSettoreAttivita;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
}
