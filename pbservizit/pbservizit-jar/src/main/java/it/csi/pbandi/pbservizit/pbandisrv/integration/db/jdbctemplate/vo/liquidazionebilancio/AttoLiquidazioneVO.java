/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.liquidazionebilancio;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;


public class AttoLiquidazioneVO extends GenericVO {
	
 	private BigDecimal idUtenteIns;
  	
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

	public BigDecimal getImportoLiquidatoAtto() {
		return importoLiquidatoAtto;
	}

	public void setImportoLiquidatoAtto(BigDecimal importoLiquidatoAtto) {
		this.importoLiquidatoAtto = importoLiquidatoAtto;
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

	public Date getDtRifiutoRagioneria() {
		return dtRifiutoRagioneria;
	}

	public void setDtRifiutoRagioneria(Date dtRifiutoRagioneria) {
		this.dtRifiutoRagioneria = dtRifiutoRagioneria;
	}

	public void setImportoAtto(BigDecimal importoAtto) {
		this.importoAtto = importoAtto;
	}

	public BigDecimal getImportoAtto() {
		return importoAtto;
	}

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
  	
  	private BigDecimal importoLiquidatoAtto;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtEmissioneAtto;
  	
  	private Date dtInserimento;
  	
  	private String testoRichiestaModifica;
  	
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
  	
  	private Date dtRifiutoRagioneria;
  	
  	private BigDecimal importoAtto;
  	
  	// CDU-105-V03: inizio
  	private String numeroDocumentoSpesa;

	public String getNumeroDocumentoSpesa() {
		return numeroDocumentoSpesa;
	}

	public void setNumeroDocumentoSpesa(String numeroDocumentoSpesa) {
		this.numeroDocumentoSpesa = numeroDocumentoSpesa;
	}
  	// CDU-105-V03: fine
  	

}
