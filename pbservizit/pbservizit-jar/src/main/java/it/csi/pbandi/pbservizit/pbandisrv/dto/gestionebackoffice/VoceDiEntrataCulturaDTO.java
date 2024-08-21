/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice;

import java.math.BigDecimal;

public class VoceDiEntrataCulturaDTO implements java.io.Serializable {

  static final long serialVersionUID = 2L;

  private long idBando;
  private long idContoEconomico;
  private long idDomanda;
  private long idRigoContoEconomico;
  private long idStatoContoEconomico;
  private long idUtenteAgg;
  private long idUtenteIns;
  private long idVoceDiEntrata;
	private long idConsuntivoEntrata;

  private String descrizione;
  private String descrizioneBreve;
  private String dtFineValidita;
  private String dtInizioValidita;
  private String flagEdit;
  private BigDecimal importoAmmesso = BigDecimal.ZERO;
  private BigDecimal importoFinanzBancaRichiesto = BigDecimal.ZERO;;
  private BigDecimal importoFinanziamentoBanca = BigDecimal.ZERO;;
  private BigDecimal importoImpegnoContabile = BigDecimal.ZERO;;
  private BigDecimal importoImpegnoVincolante = BigDecimal.ZERO;;
  private BigDecimal importoQuietanzato = BigDecimal.ZERO;;
  private BigDecimal importoRendicontato = BigDecimal.ZERO;;
  private BigDecimal importoRichiesto = BigDecimal.ZERO;;
  private BigDecimal importoValidato = BigDecimal.ZERO;;
	private BigDecimal importoConsuntivoPresentato = BigDecimal.ZERO;

	//Aggiunti per rimodulazione
	private BigDecimal idContoEconomicoMaster = BigDecimal.ZERO;
	private BigDecimal idContoEconomicoCopyIst = BigDecimal.ZERO;
	private BigDecimal importoNuovaProposta = BigDecimal.ZERO;
	private BigDecimal importoUltimoAmmesso = BigDecimal.ZERO;
	private BigDecimal importoRichiestoCopyIst = BigDecimal.ZERO;
	private BigDecimal importoAmmessoRimodulazione = BigDecimal.ZERO;

	//Fine rimodulazione

  private String noteContoEconomico;
  private BigDecimal percSpGenFunz;
  private String riferimento;
  private String contrattiDaStipulare;
	private String completamento;


	public long getIdBando() {
		return idBando;
	}

	public void setIdBando(long idBando) {
		this.idBando = idBando;
	}

	public long getIdContoEconomico() {
		return idContoEconomico;
	}

	public void setIdContoEconomico(long idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}

	public long getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(long idDomanda) {
		this.idDomanda = idDomanda;
	}

	public long getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}

	public void setIdRigoContoEconomico(long idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}

	public long getIdStatoContoEconomico() {
		return idStatoContoEconomico;
	}

	public void setIdStatoContoEconomico(long idStatoContoEconomico) {
		this.idStatoContoEconomico = idStatoContoEconomico;
	}

	public long getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public long getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public long getIdVoceDiEntrata() {
		return idVoceDiEntrata;
	}

	public void setIdVoceDiEntrata(long idVoceDiEntrata) {
		this.idVoceDiEntrata = idVoceDiEntrata;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizioneBreve() {
		return descrizioneBreve;
	}

	public void setDescrizioneBreve(String descrizioneBreve) {
		this.descrizioneBreve = descrizioneBreve;
	}

	public String getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(String dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public String getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(String dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public String getFlagEdit() {
		return flagEdit;
	}

	public void setFlagEdit(String flagEdit) {
		this.flagEdit = flagEdit;
	}

	public BigDecimal getImportoAmmesso() {
		return importoAmmesso;
	}

	public void setImportoAmmesso(BigDecimal importoAmmesso) {
		this.importoAmmesso = importoAmmesso;
	}

	public BigDecimal getImportoFinanzBancaRichiesto() {
		return importoFinanzBancaRichiesto;
	}

	public void setImportoFinanzBancaRichiesto(BigDecimal importoFinanzBancaRichiesto) {
		this.importoFinanzBancaRichiesto = importoFinanzBancaRichiesto;
	}

	public BigDecimal getImportoFinanziamentoBanca() {
		return importoFinanziamentoBanca;
	}

	public void setImportoFinanziamentoBanca(BigDecimal importoFinanziamentoBanca) {
		this.importoFinanziamentoBanca = importoFinanziamentoBanca;
	}

	public BigDecimal getImportoImpegnoContabile() {
		return importoImpegnoContabile;
	}

	public void setImportoImpegnoContabile(BigDecimal importoImpegnoContabile) {
		this.importoImpegnoContabile = importoImpegnoContabile;
	}

	public BigDecimal getImportoImpegnoVincolante() {
		return importoImpegnoVincolante;
	}

	public void setImportoImpegnoVincolante(BigDecimal importoImpegnoVincolante) {
		this.importoImpegnoVincolante = importoImpegnoVincolante;
	}

	public BigDecimal getImportoQuietanzato() {
		return importoQuietanzato;
	}

	public void setImportoQuietanzato(BigDecimal importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}

	public BigDecimal getImportoRendicontato() {
		return importoRendicontato;
	}

	public void setImportoRendicontato(BigDecimal importoRendicontato) {
		this.importoRendicontato = importoRendicontato;
	}

	public BigDecimal getImportoRichiesto() {
		return importoRichiesto;
	}

	public void setImportoRichiesto(BigDecimal importoRichiesto) {
		this.importoRichiesto = importoRichiesto;
	}

	public BigDecimal getImportoValidato() {
		return importoValidato;
	}

	public void setImportoValidato(BigDecimal importoValidato) {
		this.importoValidato = importoValidato;
	}

	public String getNoteContoEconomico() {
		return noteContoEconomico;
	}

	public void setNoteContoEconomico(String noteContoEconomico) {
		this.noteContoEconomico = noteContoEconomico;
	}

	public BigDecimal getPercSpGenFunz() {
		return percSpGenFunz;
	}

	public void setPercSpGenFunz(BigDecimal percSpGenFunz) {
		this.percSpGenFunz = percSpGenFunz;
	}

	public String getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}

	public String getContrattiDaStipulare() {
		return contrattiDaStipulare;
	}

	public void setContrattiDaStipulare(String contrattiDaStipulare) {
		this.contrattiDaStipulare = contrattiDaStipulare;
	}


	public String getCompletamento() {
		return completamento;
	}

	public void setCompletamento(String completamento) {
		this.completamento = completamento;
	}


	public BigDecimal getImportoConsuntivoPresentato() {
		return importoConsuntivoPresentato;
	}

	public void setImportoConsuntivoPresentato(BigDecimal importoConsuntivoPresentato) {
		this.importoConsuntivoPresentato = importoConsuntivoPresentato;
	}


	public long getIdConsuntivoEntrata() {
		return idConsuntivoEntrata;
	}

	public void setIdConsuntivoEntrata(long idConsuntivoEntrata) {
		this.idConsuntivoEntrata = idConsuntivoEntrata;
	}

	public BigDecimal getIdContoEconomicoMaster() {
		return idContoEconomicoMaster;
	}

	public void setIdContoEconomicoMaster(BigDecimal idContoEconomicoMaster) {
		this.idContoEconomicoMaster = idContoEconomicoMaster;
	}

	public BigDecimal getIdContoEconomicoCopyIst() {
		return idContoEconomicoCopyIst;
	}

	public void setIdContoEconomicoCopyIst(BigDecimal idContoEconomicoCopyIst) {
		this.idContoEconomicoCopyIst = idContoEconomicoCopyIst;
	}

	public BigDecimal getImportoNuovaProposta() {
		return importoNuovaProposta;
	}

	public void setImportoNuovaProposta(BigDecimal importoNuovaProposta) {
		this.importoNuovaProposta = importoNuovaProposta;
	}

	public BigDecimal getImportoUltimoAmmesso() {
		return importoUltimoAmmesso;
	}

	public void setImportoUltimoAmmesso(BigDecimal importoUltimoAmmesso) {
		this.importoUltimoAmmesso = importoUltimoAmmesso;
	}

	public BigDecimal getImportoRichiestoCopyIst() {
		return importoRichiestoCopyIst;
	}

	public void setImportoRichiestoCopyIst(BigDecimal importoRichiestoCopyIst) {
		this.importoRichiestoCopyIst = importoRichiestoCopyIst;
	}

	public BigDecimal getImportoAmmessoRimodulazione() {
		return importoAmmessoRimodulazione;
	}

	public void setImportoAmmessoRimodulazione(BigDecimal importoAmmessoRimodulazione) {
		this.importoAmmessoRimodulazione = importoAmmessoRimodulazione;
	}

	@Override
	public String toString() {
		return "VoceDiEntrataCulturaDTO{" +
				"idBando=" + idBando +
				", idContoEconomico=" + idContoEconomico +
				", idDomanda=" + idDomanda +
				", idRigoContoEconomico=" + idRigoContoEconomico +
				", idStatoContoEconomico=" + idStatoContoEconomico +
				", idUtenteAgg=" + idUtenteAgg +
				", idUtenteIns=" + idUtenteIns +
				", idVoceDiEntrata=" + idVoceDiEntrata +
				", idConsuntivoEntrata=" + idConsuntivoEntrata +
				", descrizione='" + descrizione + '\'' +
				", descrizioneBreve='" + descrizioneBreve + '\'' +
				", dtFineValidita='" + dtFineValidita + '\'' +
				", dtInizioValidita='" + dtInizioValidita + '\'' +
				", flagEdit='" + flagEdit + '\'' +
				", importoAmmesso=" + importoAmmesso +
				", importoFinanzBancaRichiesto=" + importoFinanzBancaRichiesto +
				", importoFinanziamentoBanca=" + importoFinanziamentoBanca +
				", importoImpegnoContabile=" + importoImpegnoContabile +
				", importoImpegnoVincolante=" + importoImpegnoVincolante +
				", importoQuietanzato=" + importoQuietanzato +
				", importoRendicontato=" + importoRendicontato +
				", importoRichiesto=" + importoRichiesto +
				", importoValidato=" + importoValidato +
				", importoConsuntivoPresentato=" + importoConsuntivoPresentato +
				", idContoEconomicoMaster=" + idContoEconomicoMaster +
				", idContoEconomicoCopyIst=" + idContoEconomicoCopyIst +
				", importoNuovaProposta=" + importoNuovaProposta +
				", importoUltimoAmmesso=" + importoUltimoAmmesso +
				", importoRichiestoCopyIst=" + importoRichiestoCopyIst +
				", importoAmmessoRimodulazione=" + importoAmmessoRimodulazione +
				", noteContoEconomico='" + noteContoEconomico + '\'' +
				", percSpGenFunz=" + percSpGenFunz +
				", riferimento='" + riferimento + '\'' +
				", contrattiDaStipulare='" + contrattiDaStipulare + '\'' +
				", completamento='" + completamento + '\'' +
				'}';
	}
}