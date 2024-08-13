/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.integration.vo;

import java.beans.IntrospectionException;
import java.math.*;
import java.sql.Date;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;



public class PbandiTFileVO {

	private Date dtInserimento;

	private Date dtAggiornamento;
  	
	private BigDecimal idDocumentoIndex;

	private BigDecimal idFile;
	
	private BigDecimal idFolder;
	
  	private BigDecimal idUtenteAgg;

	private BigDecimal idUtenteIns;
  	
  	private String nomeFile;
 	
 	private BigDecimal sizeFile;
 	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}

	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
 	public PbandiTFileVO() {}
  	
	public PbandiTFileVO (BigDecimal idDocumentoIndex) {
	
		this. idDocumentoIndex =  idDocumentoIndex;
	}
  	
	public PbandiTFileVO ( Date dtInserimento, BigDecimal idDocumentoIndex , BigDecimal idFile, BigDecimal idFolder, BigDecimal idUtenteIns, String nomeFile, BigDecimal sizeFile ) {
		this. dtInserimento =  dtInserimento;
		this. idDocumentoIndex =  idDocumentoIndex;
		this. idFile=idFile;
		this. idFolder =  idFolder;
		this. idUtenteIns =  idUtenteIns;
		this. nomeFile  = nomeFile;
		this. sizeFile  = sizeFile;
	}
  	
	
  	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public BigDecimal getSizeFile() {
		return sizeFile;
	}

	public void setSizeFile(BigDecimal sizeFile) {
		this.sizeFile = sizeFile;
	}

	public Date getDtInserimento() {
		return dtInserimento;
	}

	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}

	public BigDecimal getIdFile() {
		return idFile;
	}

	public void setIdFile(BigDecimal idFile) {
		this.idFile = idFile;
	}
	
	public BigDecimal getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(BigDecimal idFolder) {
		this.idFolder = idFolder;
	}

	 
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+" : ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}		
}
