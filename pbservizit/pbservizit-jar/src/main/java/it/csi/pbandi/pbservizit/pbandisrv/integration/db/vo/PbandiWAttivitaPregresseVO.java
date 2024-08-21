/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiWAttivitaPregresseVO extends GenericVO {

  	
  	private String dataIms;
  	
  	private BigDecimal idIndicatori;
  	
  	private BigDecimal importoSaldo;
  	
  	private BigDecimal idProgetto;
  	
  	private String descElemento;
  	
  	private BigDecimal idAttivitaPregresse;
  	
  	private String causale;
  	
  	private BigDecimal idDocumentoIndex;
  	
  	private BigDecimal idDomanda;
  	
  	private Date data;
  	
  	private BigDecimal importoQuietanzato;
  	
  	private Date dtInserimento;
  	
  	private String periodi;
  	
  	private String soggetto;
  	
  	private String numeroIms;
  	
  	private String note;
  	
  	private BigDecimal importoImpegno;
  	
  	private BigDecimal importoAgevolato;
  	
  	private String modalita;
  	
  	private String motivo;
  	
  	private String ente;
  	
  	private String stato;
  	
  	private BigDecimal idFaseMonit;
  	
  	private BigDecimal idContoEconomico;
  	
  	private String dataDecorrenza;
  	
  	private String codElemento;
  	
  	private BigDecimal importoRendicontato;
  	
  	private BigDecimal importoValidato;
  	
  	private String ribassoAsta;
  	
  	private BigDecimal importoAmmesso;
  	
  	private String nomeDocumento;
  	
	

	public PbandiWAttivitaPregresseVO() {}
  	
	public PbandiWAttivitaPregresseVO(BigDecimal idAttivitaPregresse) {
		this. idAttivitaPregresse =  idAttivitaPregresse;
	}
  	
	public PbandiWAttivitaPregresseVO (String dataIms, BigDecimal idIndicatori, BigDecimal importoSaldo, BigDecimal idProgetto, String descElemento, BigDecimal idAttivitaPregresse, String causale, BigDecimal idDocumentoIndex, BigDecimal idDomanda, Date data, BigDecimal importoQuietanzato, Date dtInserimento, String periodi, String soggetto, String numeroIms, String note, BigDecimal importoImpegno, BigDecimal importoAgevolato, String modalita, String motivo, String ente, String stato, BigDecimal idFaseMonit, BigDecimal idContoEconomico, String dataDecorrenza, String codElemento, BigDecimal importoRendicontato, BigDecimal importoValidato, String ribassoAsta, BigDecimal importoAmmesso) {
	
		this. dataIms =  dataIms;
		this. idIndicatori =  idIndicatori;
		this. importoSaldo =  importoSaldo;
		this. idProgetto =  idProgetto;
		this. descElemento =  descElemento;
		this. idAttivitaPregresse =  idAttivitaPregresse;
		this. causale =  causale;
		this. idDocumentoIndex =  idDocumentoIndex;
		this. idDomanda =  idDomanda;
		this. data =  data;
		this. importoQuietanzato =  importoQuietanzato;
		this. dtInserimento =  dtInserimento;
		this. periodi =  periodi;
		this. soggetto =  soggetto;
		this. numeroIms =  numeroIms;
		this. note =  note;
		this. importoImpegno =  importoImpegno;
		this. importoAgevolato =  importoAgevolato;
		this. modalita =  modalita;
		this. motivo =  motivo;
		this. ente =  ente;
		this. stato =  stato;
		this. idFaseMonit =  idFaseMonit;
		this. idContoEconomico =  idContoEconomico;
		this. dataDecorrenza =  dataDecorrenza;
		this. codElemento =  codElemento;
		this. importoRendicontato =  importoRendicontato;
		this. importoValidato =  importoValidato;
		this. ribassoAsta =  ribassoAsta;
		this. importoAmmesso =  importoAmmesso;
		this.nomeDocumento = nomeDocumento;
	}
  	
  	
	public String getDataIms() {
		return dataIms;
	}
	
	public void setDataIms(String dataIms) {
		this.dataIms = dataIms;
	}
	
	public BigDecimal getIdIndicatori() {
		return idIndicatori;
	}
	
	public void setIdIndicatori(BigDecimal idIndicatori) {
		this.idIndicatori = idIndicatori;
	}
	
	public BigDecimal getImportoSaldo() {
		return importoSaldo;
	}
	
	public void setImportoSaldo(BigDecimal importoSaldo) {
		this.importoSaldo = importoSaldo;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public String getDescElemento() {
		return descElemento;
	}
	
	public void setDescElemento(String descElemento) {
		this.descElemento = descElemento;
	}
	
	public BigDecimal getIdAttivitaPregresse() {
		return idAttivitaPregresse;
	}
	
	public void setIdAttivitaPregresse(BigDecimal idAttivitaPregresse) {
		this.idAttivitaPregresse = idAttivitaPregresse;
	}
	
	public String getCausale() {
		return causale;
	}
	
	public void setCausale(String causale) {
		this.causale = causale;
	}
	
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	
	public BigDecimal getIdDomanda() {
		return idDomanda;
	}
	
	public void setIdDomanda(BigDecimal idDomanda) {
		this.idDomanda = idDomanda;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public BigDecimal getImportoQuietanzato() {
		return importoQuietanzato;
	}
	
	public void setImportoQuietanzato(BigDecimal importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public String getPeriodi() {
		return periodi;
	}
	
	public void setPeriodi(String periodi) {
		this.periodi = periodi;
	}
	
	public String getSoggetto() {
		return soggetto;
	}
	
	public void setSoggetto(String soggetto) {
		this.soggetto = soggetto;
	}
	
	public String getNumeroIms() {
		return numeroIms;
	}
	
	public void setNumeroIms(String numeroIms) {
		this.numeroIms = numeroIms;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public BigDecimal getImportoImpegno() {
		return importoImpegno;
	}
	
	public void setImportoImpegno(BigDecimal importoImpegno) {
		this.importoImpegno = importoImpegno;
	}
	
	public BigDecimal getImportoAgevolato() {
		return importoAgevolato;
	}
	
	public void setImportoAgevolato(BigDecimal importoAgevolato) {
		this.importoAgevolato = importoAgevolato;
	}
	
	public String getModalita() {
		return modalita;
	}
	
	public void setModalita(String modalita) {
		this.modalita = modalita;
	}
	
	public String getMotivo() {
		return motivo;
	}
	
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public String getEnte() {
		return ente;
	}
	
	public void setEnte(String ente) {
		this.ente = ente;
	}
	
	public String getStato() {
		return stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public BigDecimal getIdFaseMonit() {
		return idFaseMonit;
	}
	
	public void setIdFaseMonit(BigDecimal idFaseMonit) {
		this.idFaseMonit = idFaseMonit;
	}
	
	public BigDecimal getIdContoEconomico() {
		return idContoEconomico;
	}
	
	public void setIdContoEconomico(BigDecimal idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}
	
	public String getDataDecorrenza() {
		return dataDecorrenza;
	}
	
	public void setDataDecorrenza(String dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}
	
	public String getCodElemento() {
		return codElemento;
	}
	
	public void setCodElemento(String codElemento) {
		this.codElemento = codElemento;
	}
	
	public BigDecimal getImportoRendicontato() {
		return importoRendicontato;
	}
	
	public void setImportoRendicontato(BigDecimal importoRendicontato) {
		this.importoRendicontato = importoRendicontato;
	}
	
	public BigDecimal getImportoValidato() {
		return importoValidato;
	}
	
	public void setImportoValidato(BigDecimal importoValidato) {
		this.importoValidato = importoValidato;
	}
	
	public String getRibassoAsta() {
		return ribassoAsta;
	}
	
	public void setRibassoAsta(String ribassoAsta) {
		this.ribassoAsta = ribassoAsta;
	}
	
	public BigDecimal getImportoAmmesso() {
		return importoAmmesso;
	}
	
	public void setImportoAmmesso(BigDecimal importoAmmesso) {
		this.importoAmmesso = importoAmmesso;
	}
	
	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idProgetto != null && descElemento != null && data != null && dtInserimento != null && codElemento != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAttivitaPregresse != null) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dataIms);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataIms: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndicatori);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndicatori: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoSaldo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoSaldo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descElemento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descElemento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttivitaPregresse);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttivitaPregresse: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( causale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" causale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDocumentoIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDocumentoIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( data);
	    if (!DataFilter.isEmpty(temp)) sb.append(" data: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoQuietanzato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoQuietanzato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( periodi);
	    if (!DataFilter.isEmpty(temp)) sb.append(" periodi: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( soggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" soggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroIms);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroIms: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( note);
	    if (!DataFilter.isEmpty(temp)) sb.append(" note: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoImpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoImpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoAgevolato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoAgevolato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( modalita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" modalita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( motivo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" motivo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( ente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" ente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( stato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" stato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFaseMonit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFaseMonit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idContoEconomico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idContoEconomico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataDecorrenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataDecorrenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codElemento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codElemento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoRendicontato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoRendicontato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoValidato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoValidato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( ribassoAsta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" ribassoAsta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoAmmesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoAmmesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeDocumento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		pk.add("idAttivitaPregresse");
	    return pk;
	}
	
	
}
