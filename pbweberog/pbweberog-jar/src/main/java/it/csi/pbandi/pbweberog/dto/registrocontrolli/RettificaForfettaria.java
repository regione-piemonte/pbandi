/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.registrocontrolli;

public class RettificaForfettaria implements java.io.Serializable {

	private Long idRettificaForfett;
	private String dataInserimento;
	private Double percRett;
	private Long idCategAnagrafica;
	private String descCategAnagrafica;
	private Long idProceduraAggiudicaz;
	private String codProcAgg;
	private String cigProcAgg;
	private Long idDocumentoIndex;
	private String nomeFile;
	private Long idEsitoIntermedio;
	private String esitoIntermedio;
	private String flagRettificaIntermedio;
	private Long idEsitoDefinitivo;
	private String esitoDefinitivo;
	private String flagRettificaDefinitivo;
	private Long idPropostaCertificaz;
	private String dtOraCreazione;
	private Long idProgetto;
	private String codiceVisualizzato;
	private Long idSoggettoBeneficiario;
	private String denominazioneBeneficiario;
	private String tastoVisualizza;
	private String tastoModifica;
	private String tastoElimina;
	private String rifAffidamentoCpaCig;
	private String descEsitoRettifica;
	private String rifPropostaCertificazione;
	private Long idAppaltoChecklist;
	private Long idAppalto;

	private static final long serialVersionUID = 1L;

	
	
	
	public Long getIdRettificaForfett() {
		return idRettificaForfett;
	}

	public void setIdRettificaForfett(Long idRettificaForfett) {
		this.idRettificaForfett = idRettificaForfett;
	}

	public String getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Double getPercRett() {
		return percRett;
	}

	public void setPercRett(Double percRett) {
		this.percRett = percRett;
	}

	public Long getIdCategAnagrafica() {
		return idCategAnagrafica;
	}

	public void setIdCategAnagrafica(Long idCategAnagrafica) {
		this.idCategAnagrafica = idCategAnagrafica;
	}

	public String getDescCategAnagrafica() {
		return descCategAnagrafica;
	}

	public void setDescCategAnagrafica(String descCategAnagrafica) {
		this.descCategAnagrafica = descCategAnagrafica;
	}

	public Long getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}

	public void setIdProceduraAggiudicaz(Long idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}

	public String getCodProcAgg() {
		return codProcAgg;
	}

	public void setCodProcAgg(String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}

	public String getCigProcAgg() {
		return cigProcAgg;
	}

	public void setCigProcAgg(String cigProcAgg) {
		this.cigProcAgg = cigProcAgg;
	}

	public Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdDocumentoIndex(Long idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Long getIdEsitoIntermedio() {
		return idEsitoIntermedio;
	}

	public void setIdEsitoIntermedio(Long idEsitoIntermedio) {
		this.idEsitoIntermedio = idEsitoIntermedio;
	}

	public String getEsitoIntermedio() {
		return esitoIntermedio;
	}

	public void setEsitoIntermedio(String esitoIntermedio) {
		this.esitoIntermedio = esitoIntermedio;
	}

	public String getFlagRettificaIntermedio() {
		return flagRettificaIntermedio;
	}

	public void setFlagRettificaIntermedio(String flagRettificaIntermedio) {
		this.flagRettificaIntermedio = flagRettificaIntermedio;
	}

	public Long getIdEsitoDefinitivo() {
		return idEsitoDefinitivo;
	}

	public void setIdEsitoDefinitivo(Long idEsitoDefinitivo) {
		this.idEsitoDefinitivo = idEsitoDefinitivo;
	}

	public String getEsitoDefinitivo() {
		return esitoDefinitivo;
	}

	public void setEsitoDefinitivo(String esitoDefinitivo) {
		this.esitoDefinitivo = esitoDefinitivo;
	}

	public String getFlagRettificaDefinitivo() {
		return flagRettificaDefinitivo;
	}

	public void setFlagRettificaDefinitivo(String flagRettificaDefinitivo) {
		this.flagRettificaDefinitivo = flagRettificaDefinitivo;
	}

	public Long getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

	public void setIdPropostaCertificaz(Long idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}

	public String getDtOraCreazione() {
		return dtOraCreazione;
	}

	public void setDtOraCreazione(String dtOraCreazione) {
		this.dtOraCreazione = dtOraCreazione;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}

	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}

	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
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

	public String getRifAffidamentoCpaCig() {
		return rifAffidamentoCpaCig;
	}

	public void setRifAffidamentoCpaCig(String rifAffidamentoCpaCig) {
		this.rifAffidamentoCpaCig = rifAffidamentoCpaCig;
	}

	public String getDescEsitoRettifica() {
		return descEsitoRettifica;
	}

	public void setDescEsitoRettifica(String descEsitoRettifica) {
		this.descEsitoRettifica = descEsitoRettifica;
	}

	public String getRifPropostaCertificazione() {
		return rifPropostaCertificazione;
	}

	public void setRifPropostaCertificazione(String rifPropostaCertificazione) {
		this.rifPropostaCertificazione = rifPropostaCertificazione;
	}

	public Long getIdAppaltoChecklist() {
		return idAppaltoChecklist;
	}

	public void setIdAppaltoChecklist(Long idAppaltoChecklist) {
		this.idAppaltoChecklist = idAppaltoChecklist;
	}

	public Long getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(Long idAppalto) {
		this.idAppalto = idAppalto;
	}

	public RettificaForfettaria() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
