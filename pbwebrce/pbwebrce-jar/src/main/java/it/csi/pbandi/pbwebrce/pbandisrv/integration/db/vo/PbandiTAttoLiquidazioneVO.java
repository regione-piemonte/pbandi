/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTAttoLiquidazioneVO extends GenericVO {

  	
  	private BigDecimal idUtenteIns;
  	
  	private String numeroTelefonoLiquidatore;
  	
  	private BigDecimal idTipoAltraCassaPrev;
  	
  	private Date dtAnnulamentoAtto;
  	
  	private BigDecimal idSettoreEnte;
  	
  	private Date dtCompletamentoAtto;
  	
  	private BigDecimal idStatoAtto;
  	
  	private BigDecimal idBeneficBilancioCeduto;
  	
  	private String testoAllegatiAltro;
  	
  	private Date dtRicezioneAtto;
  	
  	private Date dtInpsDal;
  	
  	private Date dtAggiornamentoBilancio;
  	
  	private BigDecimal importoAtto;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtEmissioneAtto;
  	
  	private Date dtInserimento;
  	
  	private String testoRichiestaModifica;
  	
  	private Date dtRifiutoRagioneria;
  	
  	private BigDecimal idDatiPagamAttoBenCeduto;
  	
  	private BigDecimal annoAtto;
  	
  	private BigDecimal idDatiPagamentoAtto;
  	
  	private String flagAllegatiDocGiustificat;
  	
  	private String flagAllegatiEstrattoProv;
  	
  	private BigDecimal idRischioInail;
  	
  	private BigDecimal idAltraCassaPrevidenz;
  	
  	private BigDecimal idAttoLiquidazione;
  	
  	private BigDecimal idBeneficiarioBilancio;
  	
  	private String numeroAtto;
  	
  	private BigDecimal idProgetto;
  	
  	private BigDecimal impNonSoggettoRitenuta;
  	
  	private BigDecimal idBeneficBilancioCedente;
  	
  	private BigDecimal idSituazioneInps;
  	
  	private BigDecimal idAliquotaRitenuta;
  	
  	private BigDecimal idAttivitaInps;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtScadenzaAtto;
  	
  	private String nomeLiquidatore;
  	
  	private String flagAllegatiFatture;
  	
  	private Date dtRichiestaModifica;
  	
  	private String nomeDirigenteLiquidatore;
  	
  	private String descAtto;
  	
  	private Date dtInpsAl;
  	
  	private BigDecimal idModalitaAgevolazione;
  	
  	private String noteAtto;
  	
  	private String flagAllegatiDichiarazione;
  	
  	private BigDecimal idCausaleErogazione;
  	
  	private String numeroDocumentoSpesa;
  	
  	private String descStatoDocumento;
  	
  	private BigDecimal importoImponibile;
  	
	public PbandiTAttoLiquidazioneVO() {}
  	
	public PbandiTAttoLiquidazioneVO (BigDecimal idAttoLiquidazione) {
	
		this. idAttoLiquidazione =  idAttoLiquidazione;
	}
  	
	public PbandiTAttoLiquidazioneVO (BigDecimal idUtenteIns, String numeroTelefonoLiquidatore, BigDecimal idTipoAltraCassaPrev, Date dtAnnulamentoAtto, BigDecimal idSettoreEnte, Date dtCompletamentoAtto, BigDecimal idStatoAtto, BigDecimal idBeneficBilancioCeduto, String testoAllegatiAltro, Date dtRicezioneAtto, Date dtInpsDal, Date dtAggiornamentoBilancio, BigDecimal importoAtto, BigDecimal idUtenteAgg, Date dtEmissioneAtto, Date dtInserimento, String testoRichiestaModifica, Date dtRifiutoRagioneria, BigDecimal idDatiPagamAttoBenCeduto, BigDecimal annoAtto, BigDecimal idDatiPagamentoAtto, String flagAllegatiDocGiustificat, String flagAllegatiEstrattoProv, BigDecimal idRischioInail, BigDecimal idAltraCassaPrevidenz, BigDecimal idAttoLiquidazione, BigDecimal idBeneficiarioBilancio, String numeroAtto, BigDecimal idProgetto, BigDecimal impNonSoggettoRitenuta, BigDecimal idBeneficBilancioCedente, BigDecimal idSituazioneInps, BigDecimal idAliquotaRitenuta, BigDecimal idAttivitaInps, Date dtAggiornamento, Date dtScadenzaAtto, String nomeLiquidatore, String flagAllegatiFatture, Date dtRichiestaModifica, String nomeDirigenteLiquidatore, String descAtto, Date dtInpsAl, BigDecimal idModalitaAgevolazione, String noteAtto, String flagAllegatiDichiarazione, BigDecimal idCausaleErogazione, String numeroDocumentoSpesa, String descStatoDocumento, BigDecimal importoImponibile) {
	
		this.idUtenteIns =  idUtenteIns;
		this.numeroTelefonoLiquidatore =  numeroTelefonoLiquidatore;
		this.idTipoAltraCassaPrev =  idTipoAltraCassaPrev;
		this.dtAnnulamentoAtto =  dtAnnulamentoAtto;
		this.idSettoreEnte =  idSettoreEnte;
		this.dtCompletamentoAtto =  dtCompletamentoAtto;
		this.idStatoAtto =  idStatoAtto;
		this.idBeneficBilancioCeduto =  idBeneficBilancioCeduto;
		this.testoAllegatiAltro =  testoAllegatiAltro;
		this.dtRicezioneAtto =  dtRicezioneAtto;
		this.dtInpsDal =  dtInpsDal;
		this.dtAggiornamentoBilancio =  dtAggiornamentoBilancio;
		this.importoAtto =  importoAtto;
		this.idUtenteAgg =  idUtenteAgg;
		this.dtEmissioneAtto =  dtEmissioneAtto;
		this.dtInserimento =  dtInserimento;
		this.testoRichiestaModifica =  testoRichiestaModifica;
		this.dtRifiutoRagioneria =  dtRifiutoRagioneria;
		this.idDatiPagamAttoBenCeduto =  idDatiPagamAttoBenCeduto;
		this.annoAtto =  annoAtto;
		this.idDatiPagamentoAtto =  idDatiPagamentoAtto;
		this.flagAllegatiDocGiustificat =  flagAllegatiDocGiustificat;
		this.flagAllegatiEstrattoProv =  flagAllegatiEstrattoProv;
		this.idRischioInail =  idRischioInail;
		this.idAltraCassaPrevidenz =  idAltraCassaPrevidenz;
		this.idAttoLiquidazione =  idAttoLiquidazione;
		this.idBeneficiarioBilancio =  idBeneficiarioBilancio;
		this.numeroAtto =  numeroAtto;
		this.idProgetto =  idProgetto;
		this.impNonSoggettoRitenuta =  impNonSoggettoRitenuta;
		this.idBeneficBilancioCedente =  idBeneficBilancioCedente;
		this.idSituazioneInps =  idSituazioneInps;
		this.idAliquotaRitenuta =  idAliquotaRitenuta;
		this.idAttivitaInps =  idAttivitaInps;
		this.dtAggiornamento =  dtAggiornamento;
		this.dtScadenzaAtto =  dtScadenzaAtto;
		this.nomeLiquidatore =  nomeLiquidatore;
		this.flagAllegatiFatture =  flagAllegatiFatture;
		this.dtRichiestaModifica =  dtRichiestaModifica;
		this.nomeDirigenteLiquidatore =  nomeDirigenteLiquidatore;
		this.descAtto =  descAtto;
		this.dtInpsAl =  dtInpsAl;
		this.idModalitaAgevolazione =  idModalitaAgevolazione;
		this.noteAtto =  noteAtto;
		this.flagAllegatiDichiarazione =  flagAllegatiDichiarazione;
		this.idCausaleErogazione =  idCausaleErogazione;
		this.numeroDocumentoSpesa = numeroDocumentoSpesa;
		this.descStatoDocumento = descStatoDocumento;
		this.importoImponibile = importoImponibile;
	}
  	
	public BigDecimal getImportoImponibile() {
		return importoImponibile;
	}

	public void setImportoImponibile(BigDecimal importoImponibile) {
		this.importoImponibile = importoImponibile;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getNumeroTelefonoLiquidatore() {
		return numeroTelefonoLiquidatore;
	}
	
	public void setNumeroTelefonoLiquidatore(String numeroTelefonoLiquidatore) {
		this.numeroTelefonoLiquidatore = numeroTelefonoLiquidatore;
	}
	
	public BigDecimal getIdTipoAltraCassaPrev() {
		return idTipoAltraCassaPrev;
	}
	
	public void setIdTipoAltraCassaPrev(BigDecimal idTipoAltraCassaPrev) {
		this.idTipoAltraCassaPrev = idTipoAltraCassaPrev;
	}
	
	public Date getDtAnnulamentoAtto() {
		return dtAnnulamentoAtto;
	}
	
	public void setDtAnnulamentoAtto(Date dtAnnulamentoAtto) {
		this.dtAnnulamentoAtto = dtAnnulamentoAtto;
	}
	
	public BigDecimal getIdSettoreEnte() {
		return idSettoreEnte;
	}
	
	public void setIdSettoreEnte(BigDecimal idSettoreEnte) {
		this.idSettoreEnte = idSettoreEnte;
	}
	
	public Date getDtCompletamentoAtto() {
		return dtCompletamentoAtto;
	}
	
	public void setDtCompletamentoAtto(Date dtCompletamentoAtto) {
		this.dtCompletamentoAtto = dtCompletamentoAtto;
	}
	
	public BigDecimal getIdStatoAtto() {
		return idStatoAtto;
	}
	
	public void setIdStatoAtto(BigDecimal idStatoAtto) {
		this.idStatoAtto = idStatoAtto;
	}
	
	public BigDecimal getIdBeneficBilancioCeduto() {
		return idBeneficBilancioCeduto;
	}
	
	public void setIdBeneficBilancioCeduto(BigDecimal idBeneficBilancioCeduto) {
		this.idBeneficBilancioCeduto = idBeneficBilancioCeduto;
	}
	
	public String getTestoAllegatiAltro() {
		return testoAllegatiAltro;
	}
	
	public void setTestoAllegatiAltro(String testoAllegatiAltro) {
		this.testoAllegatiAltro = testoAllegatiAltro;
	}
	
	public Date getDtRicezioneAtto() {
		return dtRicezioneAtto;
	}
	
	public void setDtRicezioneAtto(Date dtRicezioneAtto) {
		this.dtRicezioneAtto = dtRicezioneAtto;
	}
	
	public Date getDtInpsDal() {
		return dtInpsDal;
	}
	
	public void setDtInpsDal(Date dtInpsDal) {
		this.dtInpsDal = dtInpsDal;
	}
	
	public Date getDtAggiornamentoBilancio() {
		return dtAggiornamentoBilancio;
	}
	
	public void setDtAggiornamentoBilancio(Date dtAggiornamentoBilancio) {
		this.dtAggiornamentoBilancio = dtAggiornamentoBilancio;
	}
	
	public BigDecimal getImportoAtto() {
		return importoAtto;
	}
	
	public void setImportoAtto(BigDecimal importoAtto) {
		this.importoAtto = importoAtto;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtEmissioneAtto() {
		return dtEmissioneAtto;
	}
	
	public void setDtEmissioneAtto(Date dtEmissioneAtto) {
		this.dtEmissioneAtto = dtEmissioneAtto;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public String getTestoRichiestaModifica() {
		return testoRichiestaModifica;
	}
	
	public void setTestoRichiestaModifica(String testoRichiestaModifica) {
		this.testoRichiestaModifica = testoRichiestaModifica;
	}
	
	public Date getDtRifiutoRagioneria() {
		return dtRifiutoRagioneria;
	}
	
	public void setDtRifiutoRagioneria(Date dtRifiutoRagioneria) {
		this.dtRifiutoRagioneria = dtRifiutoRagioneria;
	}
	
	public BigDecimal getIdDatiPagamAttoBenCeduto() {
		return idDatiPagamAttoBenCeduto;
	}
	
	public void setIdDatiPagamAttoBenCeduto(BigDecimal idDatiPagamAttoBenCeduto) {
		this.idDatiPagamAttoBenCeduto = idDatiPagamAttoBenCeduto;
	}
	
	public BigDecimal getAnnoAtto() {
		return annoAtto;
	}
	
	public void setAnnoAtto(BigDecimal annoAtto) {
		this.annoAtto = annoAtto;
	}
	
	public BigDecimal getIdDatiPagamentoAtto() {
		return idDatiPagamentoAtto;
	}
	
	public void setIdDatiPagamentoAtto(BigDecimal idDatiPagamentoAtto) {
		this.idDatiPagamentoAtto = idDatiPagamentoAtto;
	}
	
	public String getFlagAllegatiDocGiustificat() {
		return flagAllegatiDocGiustificat;
	}
	
	public void setFlagAllegatiDocGiustificat(String flagAllegatiDocGiustificat) {
		this.flagAllegatiDocGiustificat = flagAllegatiDocGiustificat;
	}
	
	public String getFlagAllegatiEstrattoProv() {
		return flagAllegatiEstrattoProv;
	}
	
	public void setFlagAllegatiEstrattoProv(String flagAllegatiEstrattoProv) {
		this.flagAllegatiEstrattoProv = flagAllegatiEstrattoProv;
	}
	
	public BigDecimal getIdRischioInail() {
		return idRischioInail;
	}
	
	public void setIdRischioInail(BigDecimal idRischioInail) {
		this.idRischioInail = idRischioInail;
	}
	
	public BigDecimal getIdAltraCassaPrevidenz() {
		return idAltraCassaPrevidenz;
	}
	
	public void setIdAltraCassaPrevidenz(BigDecimal idAltraCassaPrevidenz) {
		this.idAltraCassaPrevidenz = idAltraCassaPrevidenz;
	}
	
	public BigDecimal getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}
	
	public void setIdAttoLiquidazione(BigDecimal idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}
	
	public BigDecimal getIdBeneficiarioBilancio() {
		return idBeneficiarioBilancio;
	}
	
	public void setIdBeneficiarioBilancio(BigDecimal idBeneficiarioBilancio) {
		this.idBeneficiarioBilancio = idBeneficiarioBilancio;
	}
	
	public String getNumeroAtto() {
		return numeroAtto;
	}
	
	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public BigDecimal getImpNonSoggettoRitenuta() {
		return impNonSoggettoRitenuta;
	}
	
	public void setImpNonSoggettoRitenuta(BigDecimal impNonSoggettoRitenuta) {
		this.impNonSoggettoRitenuta = impNonSoggettoRitenuta;
	}
	
	public BigDecimal getIdBeneficBilancioCedente() {
		return idBeneficBilancioCedente;
	}
	
	public void setIdBeneficBilancioCedente(BigDecimal idBeneficBilancioCedente) {
		this.idBeneficBilancioCedente = idBeneficBilancioCedente;
	}
	
	public BigDecimal getIdSituazioneInps() {
		return idSituazioneInps;
	}
	
	public void setIdSituazioneInps(BigDecimal idSituazioneInps) {
		this.idSituazioneInps = idSituazioneInps;
	}
	
	public BigDecimal getIdAliquotaRitenuta() {
		return idAliquotaRitenuta;
	}
	
	public void setIdAliquotaRitenuta(BigDecimal idAliquotaRitenuta) {
		this.idAliquotaRitenuta = idAliquotaRitenuta;
	}
	
	public BigDecimal getIdAttivitaInps() {
		return idAttivitaInps;
	}
	
	public void setIdAttivitaInps(BigDecimal idAttivitaInps) {
		this.idAttivitaInps = idAttivitaInps;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public Date getDtScadenzaAtto() {
		return dtScadenzaAtto;
	}
	
	public void setDtScadenzaAtto(Date dtScadenzaAtto) {
		this.dtScadenzaAtto = dtScadenzaAtto;
	}
	
	public String getNomeLiquidatore() {
		return nomeLiquidatore;
	}
	
	public void setNomeLiquidatore(String nomeLiquidatore) {
		this.nomeLiquidatore = nomeLiquidatore;
	}
	
	public String getFlagAllegatiFatture() {
		return flagAllegatiFatture;
	}
	
	public void setFlagAllegatiFatture(String flagAllegatiFatture) {
		this.flagAllegatiFatture = flagAllegatiFatture;
	}
	
	public Date getDtRichiestaModifica() {
		return dtRichiestaModifica;
	}
	
	public void setDtRichiestaModifica(Date dtRichiestaModifica) {
		this.dtRichiestaModifica = dtRichiestaModifica;
	}
	
	public String getNomeDirigenteLiquidatore() {
		return nomeDirigenteLiquidatore;
	}
	
	public void setNomeDirigenteLiquidatore(String nomeDirigenteLiquidatore) {
		this.nomeDirigenteLiquidatore = nomeDirigenteLiquidatore;
	}
	
	public String getDescAtto() {
		return descAtto;
	}
	
	public void setDescAtto(String descAtto) {
		this.descAtto = descAtto;
	}
	
	public Date getDtInpsAl() {
		return dtInpsAl;
	}
	
	public void setDtInpsAl(Date dtInpsAl) {
		this.dtInpsAl = dtInpsAl;
	}
	
	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	
	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	
	public String getNoteAtto() {
		return noteAtto;
	}
	
	public void setNoteAtto(String noteAtto) {
		this.noteAtto = noteAtto;
	}
	
	public String getFlagAllegatiDichiarazione() {
		return flagAllegatiDichiarazione;
	}
	
	public void setFlagAllegatiDichiarazione(String flagAllegatiDichiarazione) {
		this.flagAllegatiDichiarazione = flagAllegatiDichiarazione;
	}
	
	public BigDecimal getIdCausaleErogazione() {
		return idCausaleErogazione;
	}
	
	public void setIdCausaleErogazione(BigDecimal idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}
	
	public String getNumeroDocumentoSpesa() {
		return numeroDocumentoSpesa;
	}

	public void setNumeroDocumentoSpesa(String numeroDocumentoSpesa) {
		this.numeroDocumentoSpesa = numeroDocumentoSpesa;
	}

	public String getDescStatoDocumento() {
		return descStatoDocumento;
	}

	public void setDescStatoDocumento(String descStatoDocumento) {
		this.descStatoDocumento = descStatoDocumento;
	}

	public boolean isValid() {
		boolean isValid = false;
        if (isPKValid() && idUtenteIns != null && idSettoreEnte != null && idStatoAtto != null && dtInserimento != null && annoAtto != null && flagAllegatiDocGiustificat != null && flagAllegatiEstrattoProv != null && idBeneficiarioBilancio != null && flagAllegatiFatture != null && idModalitaAgevolazione != null && flagAllegatiDichiarazione != null && idCausaleErogazione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAttoLiquidazione != null ) {
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
	    
	    temp = DataFilter.removeNull( numeroTelefonoLiquidatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroTelefonoLiquidatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAltraCassaPrev);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAltraCassaPrev: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAnnulamentoAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAnnulamentoAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSettoreEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSettoreEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtCompletamentoAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtCompletamentoAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBeneficBilancioCeduto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBeneficBilancioCeduto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( testoAllegatiAltro);
	    if (!DataFilter.isEmpty(temp)) sb.append(" testoAllegatiAltro: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtRicezioneAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtRicezioneAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInpsDal);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInpsDal: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamentoBilancio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamentoBilancio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtEmissioneAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtEmissioneAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( testoRichiestaModifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" testoRichiestaModifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtRifiutoRagioneria);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtRifiutoRagioneria: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDatiPagamAttoBenCeduto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDatiPagamAttoBenCeduto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDatiPagamentoAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDatiPagamentoAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAllegatiDocGiustificat);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAllegatiDocGiustificat: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAllegatiEstrattoProv);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAllegatiEstrattoProv: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRischioInail);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRischioInail: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAltraCassaPrevidenz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAltraCassaPrevidenz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttoLiquidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttoLiquidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBeneficiarioBilancio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBeneficiarioBilancio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( impNonSoggettoRitenuta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" impNonSoggettoRitenuta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBeneficBilancioCedente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBeneficBilancioCedente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSituazioneInps);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSituazioneInps: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAliquotaRitenuta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAliquotaRitenuta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttivitaInps);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttivitaInps: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtScadenzaAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtScadenzaAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeLiquidatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeLiquidatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAllegatiFatture);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAllegatiFatture: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtRichiestaModifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtRichiestaModifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeDirigenteLiquidatore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeDirigenteLiquidatore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInpsAl);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInpsAl: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idModalitaAgevolazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idModalitaAgevolazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( noteAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" noteAtto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAllegatiDichiarazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAllegatiDichiarazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCausaleErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCausaleErogazione: " + temp + "\t\n");
	    	    
	    temp = DataFilter.removeNull( numeroDocumentoSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroDocumentoSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descStatoDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoImponibile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoImponibile: " + temp + "\t\n");
	    
	    return sb.toString();	    
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idAttoLiquidazione");
		
	    return pk;
	}
	
	
}
