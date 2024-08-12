
package it.csi.pbandi.pbweb.integration.vo;

import java.math.*;
import java.sql.Date;

public class PbandiTDocumentoIndexLockVO {

  	private BigDecimal idTipoDocumentoIndex;
  	
  	private BigDecimal idTarget;
  	
  	private Date dtLockDocumento;
  	
  	private BigDecimal idUtente;
  	
  	private BigDecimal idEntita;
  	
  	private BigDecimal idProgetto;
  	
	//public PbandiTDocumentoIndexLockVO() {}

	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}

	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}

	public BigDecimal getIdTarget() {
		return idTarget;
	}

	public void setIdTarget(BigDecimal idTarget) {
		this.idTarget = idTarget;
	}

	public Date getDtLockDocumento() {
		return dtLockDocumento;
	}

	public void setDtLockDocumento(Date dtLockDocumento) {
		this.dtLockDocumento = dtLockDocumento;
	}

	public BigDecimal getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(BigDecimal idUtente) {
		this.idUtente = idUtente;
	}

	public BigDecimal getIdEntita() {
		return idEntita;
	}

	public void setIdEntita(BigDecimal idEntita) {
		this.idEntita = idEntita;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
}
