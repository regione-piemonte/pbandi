/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto;

public class DatiXActaDTO {
	
	private Long idWorkflow;
	private Long idEntita;
	private Long idTarget;
	private Long idProgetto;
	private Long idDomanda;
	private String numeroDomanda;
	private String descrBreveTipoDoc;
	private String descrTipoDoc;
	private String descrFascicolo;
	private boolean firmaDigitale;
	private String nomeFile;
	private String nomeDocumento;
	private Long idDocIndex;
  	private Long idTipoDocIndex;
  	private String denominazioneBeneficiario; //<denominazione/ragione sociale beneficiario>
  	//"<cognome nome beneficiario> oppure <legale rappresentante del beneficiario>";
  	private String beneficiarioNome;
  	private String beneficiarioCognome;
  	private String LegaleRappresentanteCognome;
  	private String LegaleRappresentanteNome;
  	
  	
  	//valori non letti da DB
  	private byte[] tsdFile;
  	private String parolaChiave;
  	private Long utenteCollegatoId;
  	private String utenteCollegatoCognome;
  	private String utenteCollegatoNome;
  	private String utenteCollegatoCF;
  	private String codiceClassificazione;


	public Long getIdWorkflow() {
		return idWorkflow;
	}

	public void setIdWorkflow(Long idWorkflow) {
		this.idWorkflow = idWorkflow;
	}

	public Long getIdEntita() {
		return idEntita;
	}

	public void setIdEntita(Long idEntita) {
		this.idEntita = idEntita;
	}

	public Long getIdTarget() {
		return idTarget;
	}

	public void setIdTarget(Long idTarget) {
		this.idTarget = idTarget;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Long getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(Long idDomanda) {
		this.idDomanda = idDomanda;
	}

	public String getNumeroDomanda() {
		return numeroDomanda;
	}

	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}

	public String getDescrBreveTipoDoc() {
		return descrBreveTipoDoc;
	}

	public void setDescrBreveTipoDoc(String descrBreveTipoDoc) {
		this.descrBreveTipoDoc = descrBreveTipoDoc;
	}

	public String getDescrTipoDoc() {
		return descrTipoDoc;
	}

	public void setDescrTipoDoc(String descrTipoDoc) {
		this.descrTipoDoc = descrTipoDoc;
	}

	public String getDescrFascicolo() {
		return descrFascicolo;
	}

	public void setDescrFascicolo(String descrFascicolo) {
		this.descrFascicolo = descrFascicolo;
	}

	public boolean isFirmaDigitale() {
		return firmaDigitale;
	}

	public void setFirmaDigitale(boolean firmaDigitale) {
		this.firmaDigitale = firmaDigitale;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

	public Long getIdDocIndex() {
		return idDocIndex;
	}

	public void setIdDocIndex(Long idDocIndex) {
		this.idDocIndex = idDocIndex;
	}

	public Long getIdTipoDocIndex() {
		return idTipoDocIndex;
	}

	public void setIdTipoDocIndex(Long idTipoDocIndex) {
		this.idTipoDocIndex = idTipoDocIndex;
	}

	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}

	public String getBeneficiarioNome() {
		return beneficiarioNome;
	}

	public void setBeneficiarioNome(String beneficiarioNome) {
		this.beneficiarioNome = beneficiarioNome;
	}

	public String getBeneficiarioCognome() {
		return beneficiarioCognome;
	}

	public void setBeneficiarioCognome(String beneficiarioCognome) {
		this.beneficiarioCognome = beneficiarioCognome;
	}

	public String getLegaleRappresentanteCognome() {
		return LegaleRappresentanteCognome;
	}

	public void setLegaleRappresentanteCognome(String legaleRappresentanteCognome) {
		LegaleRappresentanteCognome = legaleRappresentanteCognome;
	}

	public String getLegaleRappresentanteNome() {
		return LegaleRappresentanteNome;
	}

	public void setLegaleRappresentanteNome(String legaleRappresentanteNome) {
		LegaleRappresentanteNome = legaleRappresentanteNome;
	}

	public byte[] getTsdFile() {
		return tsdFile;
	}

	public void setTsdFile(byte[] tsdFile) {
		this.tsdFile = tsdFile;
	}

	public String getParolaChiave() {
		return parolaChiave;
	}

	public void setParolaChiave(String parolaChiave) {
		this.parolaChiave = parolaChiave;
	}

	public String getUtenteCollegatoCognome() {
		return utenteCollegatoCognome;
	}

	public void setUtenteCollegatoCognome(String utenteCollegatoCognome) {
		this.utenteCollegatoCognome = utenteCollegatoCognome;
	}

	public String getUtenteCollegatoNome() {
		return utenteCollegatoNome;
	}

	public void setUtenteCollegatoNome(String utenteCollegatoNome) {
		this.utenteCollegatoNome = utenteCollegatoNome;
	}

	public String getUtenteCollegatoCF() {
		return utenteCollegatoCF;
	}

	public void setUtenteCollegatoCF(String utenteCollegatoCF) {
		this.utenteCollegatoCF = utenteCollegatoCF;
	}
	
	public String getCodiceClassificazione() {
		return codiceClassificazione;
	}

	public void setCodiceClassificazione(String codiceClassificazione) {
		this.codiceClassificazione = codiceClassificazione;
	}
	
	public Long getUtenteCollegatoId() {
		return utenteCollegatoId;
	}

	public void setUtenteCollegatoId(Long utenteCollegatoId) {
		this.utenteCollegatoId = utenteCollegatoId;
	}
	
	@Override
	public String toString() {
		String r =  "DatiXActaDTO [idWorkflow=" + idWorkflow + ", idEntita=" + idEntita + ", idTarget=" + idTarget
				+ ", idProgetto=" + idProgetto + ", idDomanda=" + idDomanda + ", numeroDomanda=" + numeroDomanda + ", descrBreveTipoDoc=" + descrBreveTipoDoc
				+ ", descrTipoDoc=" + descrTipoDoc + ", descrFascicolo=" + descrFascicolo + ", firmaDigitale="
				+ firmaDigitale + ", nomeFile=" + nomeFile + ", nomeDocumento=" + nomeDocumento + ", idDocIndex="
				+ idDocIndex + ", idTipoDocIndex=" + idTipoDocIndex + ", denominazioneBeneficiario="
				+ denominazioneBeneficiario + ", beneficiarioNome=" + beneficiarioNome + ", beneficiarioCognome="
				+ beneficiarioCognome + ", LegaleRappresentanteCognome=" + LegaleRappresentanteCognome
				+ ", LegaleRappresentanteNome=" + LegaleRappresentanteNome 
				+ ", parolaChiave=" + parolaChiave + ", utenteCollegatoCognome=" + utenteCollegatoCognome
				+ ", utenteCollegatoNome=" + utenteCollegatoNome + ", utenteCollegatoCF=" + utenteCollegatoCF
				+ ", utenteCollegatoId=" + utenteCollegatoId +", codiceClassificazione=" + codiceClassificazione ;
				
				
		if(tsdFile!=null)
			r = r + ", tsdFile.length = " + tsdFile.length;
		else
			r = r + ", tsdFile=null ";
	
		r = r + "]";
		return r;
	}
	
}
