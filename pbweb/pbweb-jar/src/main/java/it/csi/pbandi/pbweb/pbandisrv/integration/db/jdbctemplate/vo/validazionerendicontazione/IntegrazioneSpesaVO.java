package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTIntegrazioneSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;
import java.sql.Date;

public class IntegrazioneSpesaVO extends PbandiTIntegrazioneSpesaVO {

	private Date dataDichiarazione;
	private BigDecimal idProgetto;
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append(super.toString());
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataDichiarazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataDichiarazione: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public Date getDataDichiarazione() {
		return dataDichiarazione;
	}

	public void setDataDichiarazione(Date dataDichiarazione) {
		this.dataDichiarazione = dataDichiarazione;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}	
}
