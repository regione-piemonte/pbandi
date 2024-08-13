/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTBandoVO extends GenericVO {

  	
  	private String determinaApprovazione;
  	
  	private Date dtInizioPresentazDomande;
  	
  	private String titoloBando;
  	
  	private String flagQuietanza;
  	
  	private BigDecimal idMateria;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idIntesa;
  	
  	private BigDecimal idSettoreCpt;
  	
  	private BigDecimal importoPremialita;
  	
  	private BigDecimal idTipoOperazione;
  	
  	private Date dtScadenzaBando;
  	
  	private BigDecimal idBando;
  	
  	private BigDecimal percentualePremialita;
  	
  	private String flagGraduatoria;
  	
  	private String statoBando;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idTipologiaAttivazione;
  	
  	private BigDecimal idSottoSettoreCipe;
  	
  	private BigDecimal dotazioneFinanziaria;
  	
  	private Date dtPubblicazioneBando;
  	
  	private BigDecimal punteggioPremialita;
  	
  	private BigDecimal idNaturaCipe;
  	
  	private String flagAllegato;
  	
  	private Date dtAggiornamento;
  	
  	private BigDecimal costoTotaleMinAmmissibile;
  	
    // CDU-13 V07: inizio
	/* commentato causa sospensione collegamento con findom.
  	private String flagFindom;				  	
  	private BigDecimal numMaxDomande;
  	
  	public String getFlagFindom() {
		return flagFindom;
	}

	public void setFlagFindom(String flagFindom) {
		this.flagFindom = flagFindom;
	}

	public BigDecimal getNumMaxDomande() {
		return numMaxDomande;
	}

	public void setNumMaxDomande(BigDecimal numMaxDomande) {
		this.numMaxDomande = numMaxDomande;
	}
  	*/
  	
  	// CDU-13 V08 inizio
  	private BigDecimal idLineaDiIntervento;
  	
  	private Date dataDeterminaApprovazione;
  	
	public Date getDataDeterminaApprovazione() {
		return dataDeterminaApprovazione;
	}

	public void setDataDeterminaApprovazione(Date dataDeterminaApprovazione) {
		this.dataDeterminaApprovazione = dataDeterminaApprovazione;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}  	
  	// CDU-13 V08 fine
  	
	public PbandiTBandoVO() {}
  	
	public PbandiTBandoVO (BigDecimal idBando) {
	
		this. idBando =  idBando;
	}
  	
	public PbandiTBandoVO (String determinaApprovazione, Date dtInizioPresentazDomande, String titoloBando, String flagQuietanza, BigDecimal idMateria, Date dtInserimento, BigDecimal idUtenteIns, BigDecimal idIntesa, BigDecimal idSettoreCpt, BigDecimal importoPremialita, BigDecimal idTipoOperazione, Date dtScadenzaBando, BigDecimal idBando, BigDecimal percentualePremialita, String flagGraduatoria, String statoBando, BigDecimal idUtenteAgg, BigDecimal idTipologiaAttivazione, BigDecimal idSottoSettoreCipe, BigDecimal dotazioneFinanziaria, Date dtPubblicazioneBando, BigDecimal punteggioPremialita, BigDecimal idNaturaCipe, String flagAllegato, Date dtAggiornamento, BigDecimal costoTotaleMinAmmissibile, BigDecimal idLineaDiIntervento) {
	
		this. determinaApprovazione =  determinaApprovazione;
		this. dtInizioPresentazDomande =  dtInizioPresentazDomande;
		this. titoloBando =  titoloBando;
		this. flagQuietanza =  flagQuietanza;
		this. idMateria =  idMateria;
		this. dtInserimento =  dtInserimento;
		this. idUtenteIns =  idUtenteIns;
		this. idIntesa =  idIntesa;
		this. idSettoreCpt =  idSettoreCpt;
		this. importoPremialita =  importoPremialita;
		this. idTipoOperazione =  idTipoOperazione;
		this. dtScadenzaBando =  dtScadenzaBando;
		this. idBando =  idBando;
		this. percentualePremialita =  percentualePremialita;
		this. flagGraduatoria =  flagGraduatoria;
		this. statoBando =  statoBando;
		this. idUtenteAgg =  idUtenteAgg;
		this. idTipologiaAttivazione =  idTipologiaAttivazione;
		this. idSottoSettoreCipe =  idSottoSettoreCipe;
		this. dotazioneFinanziaria =  dotazioneFinanziaria;
		this. dtPubblicazioneBando =  dtPubblicazioneBando;
		this. punteggioPremialita =  punteggioPremialita;
		this. idNaturaCipe =  idNaturaCipe;
		this. flagAllegato =  flagAllegato;
		this. dtAggiornamento =  dtAggiornamento;
		this. costoTotaleMinAmmissibile =  costoTotaleMinAmmissibile;		
		//this.numMaxDomande = numMaxDomande;
		//this.flagFindom = flagFindom;
		this.idLineaDiIntervento = idLineaDiIntervento;
	}

	public String getDeterminaApprovazione() {
		return determinaApprovazione;
	}
	
	public void setDeterminaApprovazione(String determinaApprovazione) {
		this.determinaApprovazione = determinaApprovazione;
	}
	
	public Date getDtInizioPresentazDomande() {
		return dtInizioPresentazDomande;
	}
	
	public void setDtInizioPresentazDomande(Date dtInizioPresentazDomande) {
		this.dtInizioPresentazDomande = dtInizioPresentazDomande;
	}
	
	public String getTitoloBando() {
		return titoloBando;
	}
	
	public void setTitoloBando(String titoloBando) {
		this.titoloBando = titoloBando;
	}
	
	public String getFlagQuietanza() {
		return flagQuietanza;
	}
	
	public void setFlagQuietanza(String flagQuietanza) {
		this.flagQuietanza = flagQuietanza;
	}
	
	public BigDecimal getIdMateria() {
		return idMateria;
	}
	
	public void setIdMateria(BigDecimal idMateria) {
		this.idMateria = idMateria;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdIntesa() {
		return idIntesa;
	}
	
	public void setIdIntesa(BigDecimal idIntesa) {
		this.idIntesa = idIntesa;
	}
	
	public BigDecimal getIdSettoreCpt() {
		return idSettoreCpt;
	}
	
	public void setIdSettoreCpt(BigDecimal idSettoreCpt) {
		this.idSettoreCpt = idSettoreCpt;
	}
	
	public BigDecimal getImportoPremialita() {
		return importoPremialita;
	}
	
	public void setImportoPremialita(BigDecimal importoPremialita) {
		this.importoPremialita = importoPremialita;
	}
	
	public BigDecimal getIdTipoOperazione() {
		return idTipoOperazione;
	}
	
	public void setIdTipoOperazione(BigDecimal idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}
	
	public Date getDtScadenzaBando() {
		return dtScadenzaBando;
	}
	
	public void setDtScadenzaBando(Date dtScadenzaBando) {
		this.dtScadenzaBando = dtScadenzaBando;
	}
	
	public BigDecimal getIdBando() {
		return idBando;
	}
	
	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	
	public BigDecimal getPercentualePremialita() {
		return percentualePremialita;
	}
	
	public void setPercentualePremialita(BigDecimal percentualePremialita) {
		this.percentualePremialita = percentualePremialita;
	}
	
	public String getFlagGraduatoria() {
		return flagGraduatoria;
	}
	
	public void setFlagGraduatoria(String flagGraduatoria) {
		this.flagGraduatoria = flagGraduatoria;
	}
	
	public String getStatoBando() {
		return statoBando;
	}
	
	public void setStatoBando(String statoBando) {
		this.statoBando = statoBando;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdTipologiaAttivazione() {
		return idTipologiaAttivazione;
	}
	
	public void setIdTipologiaAttivazione(BigDecimal idTipologiaAttivazione) {
		this.idTipologiaAttivazione = idTipologiaAttivazione;
	}
	
	public BigDecimal getIdSottoSettoreCipe() {
		return idSottoSettoreCipe;
	}
	
	public void setIdSottoSettoreCipe(BigDecimal idSottoSettoreCipe) {
		this.idSottoSettoreCipe = idSottoSettoreCipe;
	}
	
	public BigDecimal getDotazioneFinanziaria() {
		return dotazioneFinanziaria;
	}
	
	public void setDotazioneFinanziaria(BigDecimal dotazioneFinanziaria) {
		this.dotazioneFinanziaria = dotazioneFinanziaria;
	}
	
	public Date getDtPubblicazioneBando() {
		return dtPubblicazioneBando;
	}
	
	public void setDtPubblicazioneBando(Date dtPubblicazioneBando) {
		this.dtPubblicazioneBando = dtPubblicazioneBando;
	}
	
	public BigDecimal getPunteggioPremialita() {
		return punteggioPremialita;
	}
	
	public void setPunteggioPremialita(BigDecimal punteggioPremialita) {
		this.punteggioPremialita = punteggioPremialita;
	}
	
	public BigDecimal getIdNaturaCipe() {
		return idNaturaCipe;
	}
	
	public void setIdNaturaCipe(BigDecimal idNaturaCipe) {
		this.idNaturaCipe = idNaturaCipe;
	}
	
	public String getFlagAllegato() {
		return flagAllegato;
	}
	
	public void setFlagAllegato(String flagAllegato) {
		this.flagAllegato = flagAllegato;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public BigDecimal getCostoTotaleMinAmmissibile() {
		return costoTotaleMinAmmissibile;
	}
	
	public void setCostoTotaleMinAmmissibile(BigDecimal costoTotaleMinAmmissibile) {
		this.costoTotaleMinAmmissibile = costoTotaleMinAmmissibile;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && flagQuietanza != null && idMateria != null && dtInserimento != null && idUtenteIns != null && idIntesa != null && idTipoOperazione != null && flagGraduatoria != null && flagAllegato != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idBando != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( determinaApprovazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" determinaApprovazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioPresentazDomande);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioPresentazDomande: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( titoloBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" titoloBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagQuietanza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagQuietanza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMateria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMateria: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIntesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIntesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSettoreCpt);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSettoreCpt: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoPremialita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoPremialita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoOperazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoOperazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtScadenzaBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtScadenzaBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percentualePremialita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percentualePremialita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagGraduatoria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagGraduatoria: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( statoBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" statoBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaAttivazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaAttivazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSottoSettoreCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSottoSettoreCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dotazioneFinanziaria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dotazioneFinanziaria: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtPubblicazioneBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtPubblicazioneBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( punteggioPremialita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" punteggioPremialita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNaturaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNaturaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAllegato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAllegato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( costoTotaleMinAmmissibile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" costoTotaleMinAmmissibile: " + temp + "\t\n");
	    	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");	 
	    
	    /* campi non pi� usati; CDU-13 V08
	    temp = DataFilter.removeNull( numMaxDomande);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numMaxDomande: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagFindom);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagFindom: " + temp + "\t\n");	     
	    */
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();		
			pk.add("idBando");		
	    return pk;
	}
	
	
}
