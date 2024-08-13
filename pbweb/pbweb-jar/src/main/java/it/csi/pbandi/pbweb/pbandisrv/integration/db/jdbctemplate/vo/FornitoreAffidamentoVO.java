/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRFornitoreAffidamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;
import java.math.BigDecimal;

public class FornitoreAffidamentoVO extends PbandiRFornitoreAffidamentoVO{
	
	private String descTipoPercettore;
	private BigDecimal idTipoSoggetto;	
	private String codiceFiscaleFornitore;
	private String nomeFornitore;
	private String cognomeFornitore;
	private String partitaIvaFornitore;
	private String denominazioneFornitore;

	public String getDescTipoPercettore() {
		return descTipoPercettore;
	}

	public void setDescTipoPercettore(String descTipoPercettore) {
		this.descTipoPercettore = descTipoPercettore;
	}

	public BigDecimal getIdTipoSoggetto() {
		return idTipoSoggetto;
	}

	public void setIdTipoSoggetto(BigDecimal idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}

	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}

	public String getNomeFornitore() {
		return nomeFornitore;
	}

	public void setNomeFornitore(String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}

	public String getCognomeFornitore() {
		return cognomeFornitore;
	}

	public void setCognomeFornitore(String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}

	public String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}

	public void setPartitaIvaFornitore(String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}

	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}

	public void setDenominazioneFornitore(String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}

	public String toString() {		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( this.getIdFornitore());
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( this.getIdAppalto());
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAppalto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( this.getIdTipoPercettore());
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoPercettore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoPercettore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoPercettore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( this.getDtInvioVerificaAffidamento());
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInvioVerificaAffidamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( this.getFlgInvioVerificaAffidamento());
	    if (!DataFilter.isEmpty(temp)) sb.append(" flgInvioVerificaAffidamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceFiscaleFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceFiscaleFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cognomeFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cognomeFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( partitaIvaFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" partitaIvaFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( denominazioneFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" denominazioneFornitore: " + temp + "\t\n");
	    
	    return sb.toString();
	}

}
