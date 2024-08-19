/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.registrocontrolli;

public class Irregolarita implements java.io.Serializable {

	private Long idIrregolarita;
	private String dtComunicazioneIrregolarita;
	private String codiceVisualizzatoProgetto;
	private String descrTipoIrregolarita;
	private String descrBreveTipoIrregolarita;
	private String descrQualificazioneIrregolarita;
	private String descrBreveQualificazioneIrregolarita;
	private String descrDisposizioneComunitariaTrasgredita;
	private String descrBreveDisposizioneComunitariaTrasgredita;
	private Long versioneIrregolarita;
	private String flagBloccata;
	private String riferimentoIMS;
	private String numeroVersione;
	private String notePraticaUsata;
	private String descrMetodoIndividuazioneIrregolarita;
	private String descrBreveMetodoIndividuazioneIrregolarita;
	private String descrStatoAmministrativo;
	private String descrBreveStatoAmministrativo;
	private String descrStatoFinanziario;
	private String descrBreveStatoFinanziario;
	private String descrNaturaSanzioneApplicata;
	private String descrBreveNaturaSanzioneApplicata;
	private String dtIms;
	private String soggettoResponsabile;
	private String flagProvvedimento;
	private Allegato schedaOLAF;
	private Allegato datiAggiuntivi;
	private String denominazioneBeneficiario;
	private String identificativoVersione;
	private Long idIrregolaritaCollegata;
	private Double importoIrregolarita;
	private Double quotaImpIrregCertificato;
	private String descDisimpegnoAssociato;
	private String tipoControlli;
	private Double importoAgevolazioneIrreg;
	private Long idMotivoRevoca;
	private String descMotivoRevoca;
	private String dataInizioControlli;
	private String dataFineControlli;
	private Long idIrregolaritaProvv;
	private String tastoVisualizza;
	private String tastoModifica;
	private String tastoElimina;
	private String dtComunicazioneProvv;
	private String tipoControlliProvv;
	private String dtFineProvvisoriaProvv;
	private Long idProgettoProvv;
	private Long idMotivoRevocaProvv;
	private String dtFineValiditaProvv;
	private Double importoIrregolaritaProvv;
	private Double importoAgevolazioneIrregProvv;
	private Double importoIrregolareCertificatoProvv;
	private String descMotivoRevocaProvv;
	private String dataInizioControlliProvv;
	private String dataFineControlliProvv;
	private String flagIrregolaritaAnnullataProvv;
	private String trasformaIn;
	private String descPeriodoVisualizzata;
	private String descPeriodoVisualizzataProvv;
	private String descCategAnagrafica;
	private String descCategAnagraficaProvv;
	private Long idEsitoControllo;
	private String esitoControllo;
	private Long idPeriodo;
	private Long idPeriodoProvv;
	private Long idCategAnagrafica;
	private Long idCategAnagraficaProvv;
	private String note;
	private String noteProvv;
	private String dataCampione;
	private String dataCampioneProvv;
	private static final long serialVersionUID = 1L;

	
	
	public Long getIdIrregolarita() {
		return idIrregolarita;
	}

	public void setIdIrregolarita(Long idIrregolarita) {
		this.idIrregolarita = idIrregolarita;
	}

	public String getDtComunicazioneIrregolarita() {
		return dtComunicazioneIrregolarita;
	}

	public void setDtComunicazioneIrregolarita(String dtComunicazioneIrregolarita) {
		this.dtComunicazioneIrregolarita = dtComunicazioneIrregolarita;
	}

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

	public String getDescrTipoIrregolarita() {
		return descrTipoIrregolarita;
	}

	public void setDescrTipoIrregolarita(String descrTipoIrregolarita) {
		this.descrTipoIrregolarita = descrTipoIrregolarita;
	}

	public String getDescrBreveTipoIrregolarita() {
		return descrBreveTipoIrregolarita;
	}

	public void setDescrBreveTipoIrregolarita(String descrBreveTipoIrregolarita) {
		this.descrBreveTipoIrregolarita = descrBreveTipoIrregolarita;
	}

	public String getDescrQualificazioneIrregolarita() {
		return descrQualificazioneIrregolarita;
	}

	public void setDescrQualificazioneIrregolarita(String descrQualificazioneIrregolarita) {
		this.descrQualificazioneIrregolarita = descrQualificazioneIrregolarita;
	}

	public String getDescrBreveQualificazioneIrregolarita() {
		return descrBreveQualificazioneIrregolarita;
	}

	public void setDescrBreveQualificazioneIrregolarita(String descrBreveQualificazioneIrregolarita) {
		this.descrBreveQualificazioneIrregolarita = descrBreveQualificazioneIrregolarita;
	}

	public String getDescrDisposizioneComunitariaTrasgredita() {
		return descrDisposizioneComunitariaTrasgredita;
	}

	public void setDescrDisposizioneComunitariaTrasgredita(String descrDisposizioneComunitariaTrasgredita) {
		this.descrDisposizioneComunitariaTrasgredita = descrDisposizioneComunitariaTrasgredita;
	}

	public String getDescrBreveDisposizioneComunitariaTrasgredita() {
		return descrBreveDisposizioneComunitariaTrasgredita;
	}

	public void setDescrBreveDisposizioneComunitariaTrasgredita(String descrBreveDisposizioneComunitariaTrasgredita) {
		this.descrBreveDisposizioneComunitariaTrasgredita = descrBreveDisposizioneComunitariaTrasgredita;
	}

	public Long getVersioneIrregolarita() {
		return versioneIrregolarita;
	}

	public void setVersioneIrregolarita(Long versioneIrregolarita) {
		this.versioneIrregolarita = versioneIrregolarita;
	}

	public String getFlagBloccata() {
		return flagBloccata;
	}

	public void setFlagBloccata(String flagBloccata) {
		this.flagBloccata = flagBloccata;
	}

	public String getRiferimentoIMS() {
		return riferimentoIMS;
	}

	public void setRiferimentoIMS(String riferimentoIMS) {
		this.riferimentoIMS = riferimentoIMS;
	}

	public String getNumeroVersione() {
		return numeroVersione;
	}

	public void setNumeroVersione(String numeroVersione) {
		this.numeroVersione = numeroVersione;
	}

	public String getNotePraticaUsata() {
		return notePraticaUsata;
	}

	public void setNotePraticaUsata(String notePraticaUsata) {
		this.notePraticaUsata = notePraticaUsata;
	}

	public String getDescrMetodoIndividuazioneIrregolarita() {
		return descrMetodoIndividuazioneIrregolarita;
	}

	public void setDescrMetodoIndividuazioneIrregolarita(String descrMetodoIndividuazioneIrregolarita) {
		this.descrMetodoIndividuazioneIrregolarita = descrMetodoIndividuazioneIrregolarita;
	}

	public String getDescrBreveMetodoIndividuazioneIrregolarita() {
		return descrBreveMetodoIndividuazioneIrregolarita;
	}

	public void setDescrBreveMetodoIndividuazioneIrregolarita(String descrBreveMetodoIndividuazioneIrregolarita) {
		this.descrBreveMetodoIndividuazioneIrregolarita = descrBreveMetodoIndividuazioneIrregolarita;
	}

	public String getDescrStatoAmministrativo() {
		return descrStatoAmministrativo;
	}

	public void setDescrStatoAmministrativo(String descrStatoAmministrativo) {
		this.descrStatoAmministrativo = descrStatoAmministrativo;
	}

	public String getDescrBreveStatoAmministrativo() {
		return descrBreveStatoAmministrativo;
	}

	public void setDescrBreveStatoAmministrativo(String descrBreveStatoAmministrativo) {
		this.descrBreveStatoAmministrativo = descrBreveStatoAmministrativo;
	}

	public String getDescrStatoFinanziario() {
		return descrStatoFinanziario;
	}

	public void setDescrStatoFinanziario(String descrStatoFinanziario) {
		this.descrStatoFinanziario = descrStatoFinanziario;
	}

	public String getDescrBreveStatoFinanziario() {
		return descrBreveStatoFinanziario;
	}

	public void setDescrBreveStatoFinanziario(String descrBreveStatoFinanziario) {
		this.descrBreveStatoFinanziario = descrBreveStatoFinanziario;
	}

	public String getDescrNaturaSanzioneApplicata() {
		return descrNaturaSanzioneApplicata;
	}

	public void setDescrNaturaSanzioneApplicata(String descrNaturaSanzioneApplicata) {
		this.descrNaturaSanzioneApplicata = descrNaturaSanzioneApplicata;
	}

	public String getDescrBreveNaturaSanzioneApplicata() {
		return descrBreveNaturaSanzioneApplicata;
	}

	public void setDescrBreveNaturaSanzioneApplicata(String descrBreveNaturaSanzioneApplicata) {
		this.descrBreveNaturaSanzioneApplicata = descrBreveNaturaSanzioneApplicata;
	}

	public String getDtIms() {
		return dtIms;
	}

	public void setDtIms(String dtIms) {
		this.dtIms = dtIms;
	}

	public String getSoggettoResponsabile() {
		return soggettoResponsabile;
	}

	public void setSoggettoResponsabile(String soggettoResponsabile) {
		this.soggettoResponsabile = soggettoResponsabile;
	}

	public String getFlagProvvedimento() {
		return flagProvvedimento;
	}

	public void setFlagProvvedimento(String flagProvvedimento) {
		this.flagProvvedimento = flagProvvedimento;
	}

	public Allegato getSchedaOLAF() {
		return schedaOLAF;
	}

	public void setSchedaOLAF(Allegato schedaOLAF) {
		this.schedaOLAF = schedaOLAF;
	}

	public Allegato getDatiAggiuntivi() {
		return datiAggiuntivi;
	}

	public void setDatiAggiuntivi(Allegato datiAggiuntivi) {
		this.datiAggiuntivi = datiAggiuntivi;
	}

	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}

	public String getIdentificativoVersione() {
		return identificativoVersione;
	}

	public void setIdentificativoVersione(String identificativoVersione) {
		this.identificativoVersione = identificativoVersione;
	}

	public Long getIdIrregolaritaCollegata() {
		return idIrregolaritaCollegata;
	}

	public void setIdIrregolaritaCollegata(Long idIrregolaritaCollegata) {
		this.idIrregolaritaCollegata = idIrregolaritaCollegata;
	}

	public Double getImportoIrregolarita() {
		return importoIrregolarita;
	}

	public void setImportoIrregolarita(Double importoIrregolarita) {
		this.importoIrregolarita = importoIrregolarita;
	}

	public Double getQuotaImpIrregCertificato() {
		return quotaImpIrregCertificato;
	}

	public void setQuotaImpIrregCertificato(Double quotaImpIrregCertificato) {
		this.quotaImpIrregCertificato = quotaImpIrregCertificato;
	}

	public String getDescDisimpegnoAssociato() {
		return descDisimpegnoAssociato;
	}

	public void setDescDisimpegnoAssociato(String descDisimpegnoAssociato) {
		this.descDisimpegnoAssociato = descDisimpegnoAssociato;
	}

	public String getTipoControlli() {
		return tipoControlli;
	}

	public void setTipoControlli(String tipoControlli) {
		this.tipoControlli = tipoControlli;
	}

	public Double getImportoAgevolazioneIrreg() {
		return importoAgevolazioneIrreg;
	}

	public void setImportoAgevolazioneIrreg(Double importoAgevolazioneIrreg) {
		this.importoAgevolazioneIrreg = importoAgevolazioneIrreg;
	}

	public Long getIdMotivoRevoca() {
		return idMotivoRevoca;
	}

	public void setIdMotivoRevoca(Long idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}

	public String getDescMotivoRevoca() {
		return descMotivoRevoca;
	}

	public void setDescMotivoRevoca(String descMotivoRevoca) {
		this.descMotivoRevoca = descMotivoRevoca;
	}

	public String getDataInizioControlli() {
		return dataInizioControlli;
	}

	public void setDataInizioControlli(String dataInizioControlli) {
		this.dataInizioControlli = dataInizioControlli;
	}

	public String getDataFineControlli() {
		return dataFineControlli;
	}

	public void setDataFineControlli(String dataFineControlli) {
		this.dataFineControlli = dataFineControlli;
	}

	public Long getIdIrregolaritaProvv() {
		return idIrregolaritaProvv;
	}

	public void setIdIrregolaritaProvv(Long idIrregolaritaProvv) {
		this.idIrregolaritaProvv = idIrregolaritaProvv;
	}

	public String getTastoVisualizza() {
		return tastoVisualizza;
	}

	public void setTastoVisualizza(String tastoVisualizza) {
		this.tastoVisualizza = tastoVisualizza;
	}

	public String getTastoModifica() {
		return tastoModifica;
	}

	public void setTastoModifica(String tastoModifica) {
		this.tastoModifica = tastoModifica;
	}

	public String getTastoElimina() {
		return tastoElimina;
	}

	public void setTastoElimina(String tastoElimina) {
		this.tastoElimina = tastoElimina;
	}

	public String getDtComunicazioneProvv() {
		return dtComunicazioneProvv;
	}

	public void setDtComunicazioneProvv(String dtComunicazioneProvv) {
		this.dtComunicazioneProvv = dtComunicazioneProvv;
	}

	public String getTipoControlliProvv() {
		return tipoControlliProvv;
	}

	public void setTipoControlliProvv(String tipoControlliProvv) {
		this.tipoControlliProvv = tipoControlliProvv;
	}

	public String getDtFineProvvisoriaProvv() {
		return dtFineProvvisoriaProvv;
	}

	public void setDtFineProvvisoriaProvv(String dtFineProvvisoriaProvv) {
		this.dtFineProvvisoriaProvv = dtFineProvvisoriaProvv;
	}

	public Long getIdProgettoProvv() {
		return idProgettoProvv;
	}

	public void setIdProgettoProvv(Long idProgettoProvv) {
		this.idProgettoProvv = idProgettoProvv;
	}

	public Long getIdMotivoRevocaProvv() {
		return idMotivoRevocaProvv;
	}

	public void setIdMotivoRevocaProvv(Long idMotivoRevocaProvv) {
		this.idMotivoRevocaProvv = idMotivoRevocaProvv;
	}

	public String getDtFineValiditaProvv() {
		return dtFineValiditaProvv;
	}

	public void setDtFineValiditaProvv(String dtFineValiditaProvv) {
		this.dtFineValiditaProvv = dtFineValiditaProvv;
	}

	public Double getImportoIrregolaritaProvv() {
		return importoIrregolaritaProvv;
	}

	public void setImportoIrregolaritaProvv(Double importoIrregolaritaProvv) {
		this.importoIrregolaritaProvv = importoIrregolaritaProvv;
	}

	public Double getImportoAgevolazioneIrregProvv() {
		return importoAgevolazioneIrregProvv;
	}

	public void setImportoAgevolazioneIrregProvv(Double importoAgevolazioneIrregProvv) {
		this.importoAgevolazioneIrregProvv = importoAgevolazioneIrregProvv;
	}

	public Double getImportoIrregolareCertificatoProvv() {
		return importoIrregolareCertificatoProvv;
	}

	public void setImportoIrregolareCertificatoProvv(Double importoIrregolareCertificatoProvv) {
		this.importoIrregolareCertificatoProvv = importoIrregolareCertificatoProvv;
	}

	public String getDescMotivoRevocaProvv() {
		return descMotivoRevocaProvv;
	}

	public void setDescMotivoRevocaProvv(String descMotivoRevocaProvv) {
		this.descMotivoRevocaProvv = descMotivoRevocaProvv;
	}

	public String getDataInizioControlliProvv() {
		return dataInizioControlliProvv;
	}

	public void setDataInizioControlliProvv(String dataInizioControlliProvv) {
		this.dataInizioControlliProvv = dataInizioControlliProvv;
	}

	public String getDataFineControlliProvv() {
		return dataFineControlliProvv;
	}

	public void setDataFineControlliProvv(String dataFineControlliProvv) {
		this.dataFineControlliProvv = dataFineControlliProvv;
	}

	public String getFlagIrregolaritaAnnullataProvv() {
		return flagIrregolaritaAnnullataProvv;
	}

	public void setFlagIrregolaritaAnnullataProvv(String flagIrregolaritaAnnullataProvv) {
		this.flagIrregolaritaAnnullataProvv = flagIrregolaritaAnnullataProvv;
	}

	public String getTrasformaIn() {
		return trasformaIn;
	}

	public void setTrasformaIn(String trasformaIn) {
		this.trasformaIn = trasformaIn;
	}

	public String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}

	public void setDescPeriodoVisualizzata(String descPeriodoVisualizzata) {
		this.descPeriodoVisualizzata = descPeriodoVisualizzata;
	}

	public String getDescPeriodoVisualizzataProvv() {
		return descPeriodoVisualizzataProvv;
	}

	public void setDescPeriodoVisualizzataProvv(String descPeriodoVisualizzataProvv) {
		this.descPeriodoVisualizzataProvv = descPeriodoVisualizzataProvv;
	}

	public String getDescCategAnagrafica() {
		return descCategAnagrafica;
	}

	public void setDescCategAnagrafica(String descCategAnagrafica) {
		this.descCategAnagrafica = descCategAnagrafica;
	}

	public String getDescCategAnagraficaProvv() {
		return descCategAnagraficaProvv;
	}

	public void setDescCategAnagraficaProvv(String descCategAnagraficaProvv) {
		this.descCategAnagraficaProvv = descCategAnagraficaProvv;
	}

	public Long getIdEsitoControllo() {
		return idEsitoControllo;
	}

	public void setIdEsitoControllo(Long idEsitoControllo) {
		this.idEsitoControllo = idEsitoControllo;
	}

	public String getEsitoControllo() {
		return esitoControllo;
	}

	public void setEsitoControllo(String esitoControllo) {
		this.esitoControllo = esitoControllo;
	}

	public Long getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Long idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public Long getIdPeriodoProvv() {
		return idPeriodoProvv;
	}

	public void setIdPeriodoProvv(Long idPeriodoProvv) {
		this.idPeriodoProvv = idPeriodoProvv;
	}

	public Long getIdCategAnagrafica() {
		return idCategAnagrafica;
	}

	public void setIdCategAnagrafica(Long idCategAnagrafica) {
		this.idCategAnagrafica = idCategAnagrafica;
	}

	public Long getIdCategAnagraficaProvv() {
		return idCategAnagraficaProvv;
	}

	public void setIdCategAnagraficaProvv(Long idCategAnagraficaProvv) {
		this.idCategAnagraficaProvv = idCategAnagraficaProvv;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNoteProvv() {
		return noteProvv;
	}

	public void setNoteProvv(String noteProvv) {
		this.noteProvv = noteProvv;
	}

	public String getDataCampione() {
		return dataCampione;
	}

	public void setDataCampione(String dataCampione) {
		this.dataCampione = dataCampione;
	}

	public String getDataCampioneProvv() {
		return dataCampioneProvv;
	}

	public void setDataCampioneProvv(String dataCampioneProvv) {
		this.dataCampioneProvv = dataCampioneProvv;
	}

	public Irregolarita() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
