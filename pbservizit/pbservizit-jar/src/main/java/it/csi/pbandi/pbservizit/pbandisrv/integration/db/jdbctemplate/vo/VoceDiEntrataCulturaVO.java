/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;
import java.util.List;

public class VoceDiEntrataCulturaVO extends GenericVO {
	
	private BigDecimal idVoceDiEntrata;
	private String descrizione;
	private String flagEdit;
	
	private BigDecimal idRigoContoEconomico;
	private BigDecimal idContoEconomico;
	private String completamento;
	private BigDecimal importoAmmessoFinanziamento;
	
	@Override
	public List getPK() {
		return null;
	}

	@Override
	public boolean isPKValid() {
		return false;
	}
	
	public BigDecimal getIdContoEconomico() {
		return idContoEconomico;
	}
	public void setIdContoEconomico(BigDecimal idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}
	public BigDecimal getIdVoceDiEntrata() {
		return idVoceDiEntrata;
	}
	
	public void setIdVoceDiEntrata(BigDecimal idVoceDiEntrata) {
		this.idVoceDiEntrata = idVoceDiEntrata;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getFlagEdit() {
		return flagEdit;
	}
	public void setFlagEdit(String flagEdit) {
		this.flagEdit = flagEdit;
	}
	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}
	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}
	public String getCompletamento() {
		return completamento;
	}
	public void setCompletamento(String completamento) {
		this.completamento = completamento;
	}
	
	public BigDecimal getImportoAmmessoFinanziamento() {
		return importoAmmessoFinanziamento;
	}

	public void setImportoAmmessoFinanziamento(
			BigDecimal importoAmmessoFinanziamento) {
		this.importoAmmessoFinanziamento = importoAmmessoFinanziamento;
	}

	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idVoceDiEntrata);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiEntrata: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagEdit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagEdit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRigoContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRigoContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( completamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" completamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoAmmessoFinanziamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoAmmessoFinanziamento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
}
