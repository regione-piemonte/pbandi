
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTProgettoVO extends GenericVO {

  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal sogliaSpesaCalcErogazioni;
  	
  	private String codiceVisualizzato;
  	
  	private String acronimoProgetto;
  	
  	private Date dtChiusuraProgetto;
  	
  	private BigDecimal idGrVulnerabiliProg;
  	
  	private BigDecimal idTipoLocalizzazione;
  	
  	private BigDecimal idStatoProgetto;
  	
  	private BigDecimal percentualePremialita;
  	
  	private BigDecimal flagPrenotAvvio;
  	
  	private BigDecimal idIndRisultatoProgram;
  	
  	private String noteChiusuraProgetto;
  	
  	private BigDecimal idDomanda;
  	
  	private BigDecimal idProgettoFinanz;
  	
  	private BigDecimal idTipoOperazione;
  	
  	private String fascicoloActa;
  	
  	private BigDecimal idTemaPrioritario;
  	
  	private BigDecimal importoPremialita;
  	
  	private String titoloProgetto;
  	
  	private BigDecimal idGrandeProgetto;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idProgettoComplesso;
  	
  	private BigDecimal idClassificazioneRa;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idSpecificaStato;
  	
  	private BigDecimal idObiettivoTem;
  	
  	private String abstractProgetto;
  	
  	private Date dtComitato;
  	
  	private BigDecimal idTipoStrumentoProgr;
  	
  	private String idIstanzaProcesso;
  	
  	private BigDecimal idCategoriaCipe;
  	
  	private BigDecimal idProgetto;
  	
  	private String cup;
  	
  	private BigDecimal idObiettivoSpecifQsn;
  	
  	private BigDecimal idProgettoPadre;
  	
  	private Date dtConcessione;
  	
  	private Date dtAggiornamento;
  	
  	private String codiceProgetto;
  	
  	private BigDecimal idStrumentoAttuativo;
  	
  	private String flagArFlux;
  	
  	private String flagProgettoMaster;
  	
  	private BigDecimal idSettoreCpt;
  	
  	private BigDecimal idIndicatoreQsn;
  	
  	private BigDecimal idTipologiaCipe;
  	
  	private BigDecimal idTipoProceduraOrig;		//PBANDI-2734
  	
	public PbandiTProgettoVO() {}
  	
	public PbandiTProgettoVO (BigDecimal idProgetto) {
	
		this. idProgetto =  idProgetto;
	}
  	
	public PbandiTProgettoVO (BigDecimal idUtenteIns, BigDecimal sogliaSpesaCalcErogazioni, String codiceVisualizzato, String acronimoProgetto, Date dtChiusuraProgetto, BigDecimal idGrVulnerabiliProg, BigDecimal idTipoLocalizzazione, BigDecimal idStatoProgetto, BigDecimal percentualePremialita, BigDecimal flagPrenotAvvio, BigDecimal idIndRisultatoProgram, String noteChiusuraProgetto, BigDecimal idDomanda, BigDecimal idProgettoFinanz, BigDecimal idTipoOperazione, String fascicoloActa, BigDecimal idTemaPrioritario, BigDecimal importoPremialita, String titoloProgetto, BigDecimal idGrandeProgetto, BigDecimal idUtenteAgg, BigDecimal idProgettoComplesso, BigDecimal idClassificazioneRa, Date dtInserimento, BigDecimal idSpecificaStato, BigDecimal idObiettivoTem, String abstractProgetto, Date dtComitato, BigDecimal idTipoStrumentoProgr, String idIstanzaProcesso, BigDecimal idCategoriaCipe, BigDecimal idProgetto, String cup, BigDecimal idObiettivoSpecifQsn, BigDecimal idProgettoPadre, Date dtConcessione, Date dtAggiornamento, String codiceProgetto, BigDecimal idStrumentoAttuativo, String flagArFlux, String flagProgettoMaster, BigDecimal idSettoreCpt, BigDecimal idIndicatoreQsn, BigDecimal idTipologiaCipe, BigDecimal idTipoProceduraOrig) {
	
		this. idUtenteIns =  idUtenteIns;
		this. sogliaSpesaCalcErogazioni =  sogliaSpesaCalcErogazioni;
		this. codiceVisualizzato =  codiceVisualizzato;
		this. acronimoProgetto =  acronimoProgetto;
		this. dtChiusuraProgetto =  dtChiusuraProgetto;
		this. idGrVulnerabiliProg =  idGrVulnerabiliProg;
		this. idTipoLocalizzazione =  idTipoLocalizzazione;
		this. idStatoProgetto =  idStatoProgetto;
		this. percentualePremialita =  percentualePremialita;
		this. flagPrenotAvvio =  flagPrenotAvvio;
		this. idIndRisultatoProgram =  idIndRisultatoProgram;
		this. noteChiusuraProgetto =  noteChiusuraProgetto;
		this. idDomanda =  idDomanda;
		this. idProgettoFinanz =  idProgettoFinanz;
		this. idTipoOperazione =  idTipoOperazione;
		this. fascicoloActa =  fascicoloActa;
		this. idTemaPrioritario =  idTemaPrioritario;
		this. importoPremialita =  importoPremialita;
		this. titoloProgetto =  titoloProgetto;
		this. idGrandeProgetto =  idGrandeProgetto;
		this. idUtenteAgg =  idUtenteAgg;
		this. idProgettoComplesso =  idProgettoComplesso;
		this. idClassificazioneRa =  idClassificazioneRa;
		this. dtInserimento =  dtInserimento;
		this. idSpecificaStato =  idSpecificaStato;
		this. idObiettivoTem =  idObiettivoTem;
		this. abstractProgetto =  abstractProgetto;
		this. dtComitato =  dtComitato;
		this. idTipoStrumentoProgr =  idTipoStrumentoProgr;
		this. idIstanzaProcesso =  idIstanzaProcesso;
		this. idCategoriaCipe =  idCategoriaCipe;
		this. idProgetto =  idProgetto;
		this. cup =  cup;
		this. idObiettivoSpecifQsn =  idObiettivoSpecifQsn;
		this. idProgettoPadre =  idProgettoPadre;
		this. dtConcessione =  dtConcessione;
		this. dtAggiornamento =  dtAggiornamento;
		this. codiceProgetto =  codiceProgetto;
		this. idStrumentoAttuativo =  idStrumentoAttuativo;
		this. flagArFlux =  flagArFlux;
		this. flagProgettoMaster =  flagProgettoMaster;
		this. idSettoreCpt =  idSettoreCpt;
		this. idIndicatoreQsn =  idIndicatoreQsn;
		this. idTipologiaCipe =  idTipologiaCipe;
		this. idTipoProceduraOrig = idTipoProceduraOrig;
	}
  	
  	
	public BigDecimal getIdTipoProceduraOrig() {
		return idTipoProceduraOrig;
	}

	public void setIdTipoProceduraOrig(BigDecimal idTipoProceduraOrig) {
		this.idTipoProceduraOrig = idTipoProceduraOrig;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getSogliaSpesaCalcErogazioni() {
		return sogliaSpesaCalcErogazioni;
	}
	
	public void setSogliaSpesaCalcErogazioni(BigDecimal sogliaSpesaCalcErogazioni) {
		this.sogliaSpesaCalcErogazioni = sogliaSpesaCalcErogazioni;
	}
	
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	
	public String getAcronimoProgetto() {
		return acronimoProgetto;
	}
	
	public void setAcronimoProgetto(String acronimoProgetto) {
		this.acronimoProgetto = acronimoProgetto;
	}
	
	public Date getDtChiusuraProgetto() {
		return dtChiusuraProgetto;
	}
	
	public void setDtChiusuraProgetto(Date dtChiusuraProgetto) {
		this.dtChiusuraProgetto = dtChiusuraProgetto;
	}
	
	public BigDecimal getIdGrVulnerabiliProg() {
		return idGrVulnerabiliProg;
	}
	
	public void setIdGrVulnerabiliProg(BigDecimal idGrVulnerabiliProg) {
		this.idGrVulnerabiliProg = idGrVulnerabiliProg;
	}
	
	public BigDecimal getIdTipoLocalizzazione() {
		return idTipoLocalizzazione;
	}
	
	public void setIdTipoLocalizzazione(BigDecimal idTipoLocalizzazione) {
		this.idTipoLocalizzazione = idTipoLocalizzazione;
	}
	
	public BigDecimal getIdStatoProgetto() {
		return idStatoProgetto;
	}
	
	public void setIdStatoProgetto(BigDecimal idStatoProgetto) {
		this.idStatoProgetto = idStatoProgetto;
	}
	
	public BigDecimal getPercentualePremialita() {
		return percentualePremialita;
	}
	
	public void setPercentualePremialita(BigDecimal percentualePremialita) {
		this.percentualePremialita = percentualePremialita;
	}
	
	public BigDecimal getFlagPrenotAvvio() {
		return flagPrenotAvvio;
	}
	
	public void setFlagPrenotAvvio(BigDecimal flagPrenotAvvio) {
		this.flagPrenotAvvio = flagPrenotAvvio;
	}
	
	public BigDecimal getIdIndRisultatoProgram() {
		return idIndRisultatoProgram;
	}
	
	public void setIdIndRisultatoProgram(BigDecimal idIndRisultatoProgram) {
		this.idIndRisultatoProgram = idIndRisultatoProgram;
	}
	
	public String getNoteChiusuraProgetto() {
		return noteChiusuraProgetto;
	}
	
	public void setNoteChiusuraProgetto(String noteChiusuraProgetto) {
		this.noteChiusuraProgetto = noteChiusuraProgetto;
	}
	
	public BigDecimal getIdDomanda() {
		return idDomanda;
	}
	
	public void setIdDomanda(BigDecimal idDomanda) {
		this.idDomanda = idDomanda;
	}
	
	public BigDecimal getIdProgettoFinanz() {
		return idProgettoFinanz;
	}
	
	public void setIdProgettoFinanz(BigDecimal idProgettoFinanz) {
		this.idProgettoFinanz = idProgettoFinanz;
	}
	
	public BigDecimal getIdTipoOperazione() {
		return idTipoOperazione;
	}
	
	public void setIdTipoOperazione(BigDecimal idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}
	
	public String getFascicoloActa() {
		return fascicoloActa;
	}
	
	public void setFascicoloActa(String fascicoloActa) {
		this.fascicoloActa = fascicoloActa;
	}
	
	public BigDecimal getIdTemaPrioritario() {
		return idTemaPrioritario;
	}
	
	public void setIdTemaPrioritario(BigDecimal idTemaPrioritario) {
		this.idTemaPrioritario = idTemaPrioritario;
	}
	
	public BigDecimal getImportoPremialita() {
		return importoPremialita;
	}
	
	public void setImportoPremialita(BigDecimal importoPremialita) {
		this.importoPremialita = importoPremialita;
	}
	
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
	
	public BigDecimal getIdGrandeProgetto() {
		return idGrandeProgetto;
	}
	
	public void setIdGrandeProgetto(BigDecimal idGrandeProgetto) {
		this.idGrandeProgetto = idGrandeProgetto;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdProgettoComplesso() {
		return idProgettoComplesso;
	}
	
	public void setIdProgettoComplesso(BigDecimal idProgettoComplesso) {
		this.idProgettoComplesso = idProgettoComplesso;
	}
	
	public BigDecimal getIdClassificazioneRa() {
		return idClassificazioneRa;
	}
	
	public void setIdClassificazioneRa(BigDecimal idClassificazioneRa) {
		this.idClassificazioneRa = idClassificazioneRa;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public BigDecimal getIdSpecificaStato() {
		return idSpecificaStato;
	}
	
	public void setIdSpecificaStato(BigDecimal idSpecificaStato) {
		this.idSpecificaStato = idSpecificaStato;
	}
	
	public BigDecimal getIdObiettivoTem() {
		return idObiettivoTem;
	}
	
	public void setIdObiettivoTem(BigDecimal idObiettivoTem) {
		this.idObiettivoTem = idObiettivoTem;
	}
	
	public String getAbstractProgetto() {
		return abstractProgetto;
	}
	
	public void setAbstractProgetto(String abstractProgetto) {
		this.abstractProgetto = abstractProgetto;
	}
	
	public Date getDtComitato() {
		return dtComitato;
	}
	
	public void setDtComitato(Date dtComitato) {
		this.dtComitato = dtComitato;
	}
	
	public BigDecimal getIdTipoStrumentoProgr() {
		return idTipoStrumentoProgr;
	}
	
	public void setIdTipoStrumentoProgr(BigDecimal idTipoStrumentoProgr) {
		this.idTipoStrumentoProgr = idTipoStrumentoProgr;
	}
	
	public String getIdIstanzaProcesso() {
		return idIstanzaProcesso;
	}
	
	public void setIdIstanzaProcesso(String idIstanzaProcesso) {
		this.idIstanzaProcesso = idIstanzaProcesso;
	}
	
	public BigDecimal getIdCategoriaCipe() {
		return idCategoriaCipe;
	}
	
	public void setIdCategoriaCipe(BigDecimal idCategoriaCipe) {
		this.idCategoriaCipe = idCategoriaCipe;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public String getCup() {
		return cup;
	}
	
	public void setCup(String cup) {
		this.cup = cup;
	}
	
	public BigDecimal getIdObiettivoSpecifQsn() {
		return idObiettivoSpecifQsn;
	}
	
	public void setIdObiettivoSpecifQsn(BigDecimal idObiettivoSpecifQsn) {
		this.idObiettivoSpecifQsn = idObiettivoSpecifQsn;
	}
	
	public BigDecimal getIdProgettoPadre() {
		return idProgettoPadre;
	}
	
	public void setIdProgettoPadre(BigDecimal idProgettoPadre) {
		this.idProgettoPadre = idProgettoPadre;
	}
	
	public Date getDtConcessione() {
		return dtConcessione;
	}
	
	public void setDtConcessione(Date dtConcessione) {
		this.dtConcessione = dtConcessione;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	
	public BigDecimal getIdStrumentoAttuativo() {
		return idStrumentoAttuativo;
	}
	
	public void setIdStrumentoAttuativo(BigDecimal idStrumentoAttuativo) {
		this.idStrumentoAttuativo = idStrumentoAttuativo;
	}
	
	public String getFlagArFlux() {
		return flagArFlux;
	}
	
	public void setFlagArFlux(String flagArFlux) {
		this.flagArFlux = flagArFlux;
	}
	
	public String getFlagProgettoMaster() {
		return flagProgettoMaster;
	}
	
	public void setFlagProgettoMaster(String flagProgettoMaster) {
		this.flagProgettoMaster = flagProgettoMaster;
	}
	
	public BigDecimal getIdSettoreCpt() {
		return idSettoreCpt;
	}
	
	public void setIdSettoreCpt(BigDecimal idSettoreCpt) {
		this.idSettoreCpt = idSettoreCpt;
	}
	
	public BigDecimal getIdIndicatoreQsn() {
		return idIndicatoreQsn;
	}
	
	public void setIdIndicatoreQsn(BigDecimal idIndicatoreQsn) {
		this.idIndicatoreQsn = idIndicatoreQsn;
	}
	
	public BigDecimal getIdTipologiaCipe() {
		return idTipologiaCipe;
	}
	
	public void setIdTipologiaCipe(BigDecimal idTipologiaCipe) {
		this.idTipologiaCipe = idTipologiaCipe;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtenteIns != null && codiceVisualizzato != null && idStatoProgetto != null && idDomanda != null && idTipoOperazione != null && titoloProgetto != null && dtInserimento != null && idTipoStrumentoProgr != null && codiceProgetto != null && idStrumentoAttuativo != null && flagArFlux != null && flagProgettoMaster != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idProgetto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( sogliaSpesaCalcErogazioni);
	    if (!DataFilter.isEmpty(temp)) sb.append(" sogliaSpesaCalcErogazioni: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceVisualizzato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceVisualizzato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( acronimoProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" acronimoProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtChiusuraProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtChiusuraProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idGrVulnerabiliProg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idGrVulnerabiliProg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoLocalizzazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoLocalizzazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percentualePremialita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percentualePremialita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagPrenotAvvio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagPrenotAvvio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndRisultatoProgram);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndRisultatoProgram: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( noteChiusuraProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" noteChiusuraProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgettoFinanz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgettoFinanz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoOperazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoOperazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( fascicoloActa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" fascicoloActa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTemaPrioritario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTemaPrioritario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoPremialita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoPremialita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( titoloProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" titoloProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idGrandeProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idGrandeProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgettoComplesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgettoComplesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idClassificazioneRa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idClassificazioneRa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSpecificaStato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSpecificaStato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idObiettivoTem);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idObiettivoTem: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( abstractProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" abstractProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtComitato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtComitato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoStrumentoProgr);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoStrumentoProgr: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIstanzaProcesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIstanzaProcesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCategoriaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCategoriaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cup);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cup: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idObiettivoSpecifQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idObiettivoSpecifQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgettoPadre);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgettoPadre: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtConcessione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtConcessione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStrumentoAttuativo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStrumentoAttuativo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagArFlux);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagArFlux: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagProgettoMaster);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagProgettoMaster: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSettoreCpt);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSettoreCpt: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndicatoreQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndicatoreQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaCipe: " + temp + "\t\n");
	    	    
	    temp = DataFilter.removeNull( idTipoProceduraOrig);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoProceduraOrig: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idProgetto");
		
	    return pk;
	}
	
	
}
