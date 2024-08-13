/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.manager;

import java.math.BigDecimal;
import java.util.Date;

public class ConfigurationDTO {
	private String archivioAllowedFileExtensions;
	private Long archivioFileSizeLimit;
	private Long archivioFileSizeLimitUpload;
	private Long asynchMapCandidateMaxInstancesNumber;
	private String  bypassCheckDigitalSign;
	private String  bypassCheckCFDigitalSign;
	private Long checklistSelectForUpdateWaitTimeout;
	private Long configurationCache = 0L;
	private boolean dbStructureCheckBlocking = false;
	private Long digitalSignFileSizeLimit;
	private Date dtBloccoAccessoUPP;
	private Long genericTimeout;
	private Long ggCalcoloDtBloccoAccessoUPP;
	private Long inviaDichiarazioneDiSpesaTimeout;
	private Long lockDocumentoScadutoTimeout;
	private Long maxNumRecordImpegni;
	private Long maxNumRecordImpegniAllineabiliBil;
	private boolean migrazioneInCorso;
	private Long maxNumFornitoriRicerca;
	private Long maxNumDocumentiRicerca;
	private Long maxNumIstanzeFluxAggiornabili;
	private Long minImportoLiquidabileCentesimiBilancio;
	private String msgAccessoBloccato;
	private String msgDataBloccoAccesso;
	private Long profileTypeDoSign;
	private Long timeoutAvvioProgetto;
	private Long userSpaceLimit;
	private boolean verificaCookie;
	private Long verificaDichiarazioneDiSpesaTimeout;
	private Long verificationTypeDoSign;
	private BigDecimal downloadSizeLimit;
	
	public Long getArchivioFileSizeLimitUpload() {
		return archivioFileSizeLimitUpload;
	}

	public void setArchivioFileSizeLimitUpload(Long archivioFileSizeLimitUpload) {
		this.archivioFileSizeLimitUpload = archivioFileSizeLimitUpload;
	}

	public Long getMaxNumRecordImpegniAllineabiliBil() {
		return maxNumRecordImpegniAllineabiliBil;
	}

	public void setMaxNumRecordImpegniAllineabiliBil(
			Long maxNumRecordImpegniAllineabiliBil) {
		this.maxNumRecordImpegniAllineabiliBil = maxNumRecordImpegniAllineabiliBil;
	}

	public Long getMinImportoLiquidabileCentesimiBilancio() {
		return minImportoLiquidabileCentesimiBilancio;
	}

	public void setMinImportoLiquidabileCentesimiBilancio(
			Long minImportoLiquidabileCentesimiBilancio) {
		this.minImportoLiquidabileCentesimiBilancio = minImportoLiquidabileCentesimiBilancio;
	}

	public Long getChecklistSelectForUpdateWaitTimeout() {
		return checklistSelectForUpdateWaitTimeout;
	}

	public void setChecklistSelectForUpdateWaitTimeout(
			Long checklistSelectForUpdateWaitTimeout) {
		this.checklistSelectForUpdateWaitTimeout = checklistSelectForUpdateWaitTimeout;
	}

	public Long getLockDocumentoScadutoTimeout() {
		return lockDocumentoScadutoTimeout;
	}

	public void setLockDocumentoScadutoTimeout(Long lockDocumentoScadutoTimeout) {
		this.lockDocumentoScadutoTimeout = lockDocumentoScadutoTimeout;
	}

	private ServerConfigurationDTO serverConfiguration = new ServerConfigurationDTO();

	private SimonConfigurationDTO simonConfiguration = new SimonConfigurationDTO();

	public void setDbStructureCheckBlocking(boolean dbStructureCheckBlocking) {
		this.dbStructureCheckBlocking = dbStructureCheckBlocking;
	}

	public boolean isDbStructureCheckBlocking() {
		return dbStructureCheckBlocking;
	}

	public void setConfigurationCache(Long configurationCache) {
		this.configurationCache = configurationCache;
	}

	public Long getConfigurationCache() {
		return configurationCache;
	}

	public void setGenericTimeout(Long genericTimeout) {
		this.genericTimeout = genericTimeout;
	}

	public Long getGenericTimeout() {
		return genericTimeout;
	}

	public void setVerificaDichiarazioneDiSpesaTimeout(
			Long verificaDichiarazioneDiSpesaTimeout) {
		this.verificaDichiarazioneDiSpesaTimeout = verificaDichiarazioneDiSpesaTimeout;
	}

	public Long getVerificaDichiarazioneDiSpesaTimeout() {
		return verificaDichiarazioneDiSpesaTimeout;
	}

	public void setInviaDichiarazioneDiSpesaTimeout(
			Long inviaDichiarazioneDiSpesaTimeout) {
		this.inviaDichiarazioneDiSpesaTimeout = inviaDichiarazioneDiSpesaTimeout;
	}

	public Long getInviaDichiarazioneDiSpesaTimeout() {
		return inviaDichiarazioneDiSpesaTimeout;
	}

	public void setAsynchMapCandidateMaxInstancesNumber(
			Long asynchMapCandidateMaxInstancesNumber) {
		this.asynchMapCandidateMaxInstancesNumber = asynchMapCandidateMaxInstancesNumber;
	}

	public Long getAsynchMapCandidateMaxInstancesNumber() {
		return asynchMapCandidateMaxInstancesNumber;
	}

	public void setServerConfiguration(
			ServerConfigurationDTO serverConfiguration) {
		this.serverConfiguration = serverConfiguration;
	}

	public ServerConfigurationDTO getServerConfiguration() {
		return serverConfiguration;
	}

	public SimonConfigurationDTO getSimonConfiguration() {
		return simonConfiguration;
	}

	public void setSimonConfiguration(SimonConfigurationDTO simonConfiguration) {
		this.simonConfiguration = simonConfiguration;
	}

	public void setDtBloccoAccessoUPP(Date dtBloccoAccessoUPP) {
		this.dtBloccoAccessoUPP = dtBloccoAccessoUPP;
	}

	public Date getDtBloccoAccessoUPP() {
		return dtBloccoAccessoUPP;
	}

	public void setGgCalcoloDtBloccoAccessoUPP(Long ggCalcoloDtBloccoAccessoUPP) {
		this.ggCalcoloDtBloccoAccessoUPP = ggCalcoloDtBloccoAccessoUPP;
	}

	public Long getGgCalcoloDtBloccoAccessoUPP() {
		return ggCalcoloDtBloccoAccessoUPP;
	}

	public void setMsgAccessoBloccato(String msgAccessoBloccato) {
		this.msgAccessoBloccato = msgAccessoBloccato;
	}

	public String getMsgAccessoBloccato() {
		return msgAccessoBloccato;
	}

	public void setMsgDataBloccoAccesso(String msgDataBloccoAccesso) {
		this.msgDataBloccoAccesso = msgDataBloccoAccesso;
	}

	public String getMsgDataBloccoAccesso() {
		return msgDataBloccoAccesso;
	}

	public void setMaxNumRecordImpegni(Long maxNumRecordImpegni) {
		this.maxNumRecordImpegni = maxNumRecordImpegni;
	}

	public Long getMaxNumRecordImpegni() {
		return maxNumRecordImpegni;
	}

	public void setTimeoutAvvioProgetto(Long timeoutAvvioProgetto) {
		this.timeoutAvvioProgetto = timeoutAvvioProgetto;
	}

	public Long getTimeoutAvvioProgetto() {
		return timeoutAvvioProgetto;
	}

	public void setVerificaCookie(boolean verificaCookie) {
		this.verificaCookie = verificaCookie;
	}

	public boolean isVerificaCookie() {
		return verificaCookie;
	}

	public boolean isMigrazioneInCorso() {
		return migrazioneInCorso;
	}

	public void setMigrazioneInCorso(boolean migrazioneInCorso) {
		this.migrazioneInCorso = migrazioneInCorso;
	}

	public Long getMaxNumFornitoriRicerca() {
		return maxNumFornitoriRicerca;
	}

	public void setMaxNumFornitoriRicerca(Long maxNumFornitoriRicerca) {
		this.maxNumFornitoriRicerca = maxNumFornitoriRicerca;
	}

	public Long getMaxNumDocumentiRicerca() {
		return maxNumDocumentiRicerca;
	}

	public void setMaxNumDocumentiRicerca(Long maxNumDocumentiRicerca) {
		this.maxNumDocumentiRicerca = maxNumDocumentiRicerca;
	}

	public Long getMaxNumIstanzeFluxAggiornabili() {
		return maxNumIstanzeFluxAggiornabili;
	}

	public void setMaxNumIstanzeFluxAggiornabili(
			Long maxNumIstanzeFluxAggiornabili) {
		this.maxNumIstanzeFluxAggiornabili = maxNumIstanzeFluxAggiornabili;
	}

	public Long getArchivioFileSizeLimit() {
		return archivioFileSizeLimit;
	}

	public void setArchivioFileSizeLimit(Long archivioFileSizeLimit) {
		this.archivioFileSizeLimit = archivioFileSizeLimit;
	}

	public Long getUserSpaceLimit() {
		return userSpaceLimit;
	}

	public void setUserSpaceLimit(Long userSpaceLimit) {
		this.userSpaceLimit = userSpaceLimit;
	}

	public String getArchivioAllowedFileExtensions() {
		return archivioAllowedFileExtensions;
	}

	public void setArchivioAllowedFileExtensions(
			String archivioAllowedFileExtensions) {
		this.archivioAllowedFileExtensions = archivioAllowedFileExtensions;
	}

	public String getBypassCheckDigitalSign() {
		return bypassCheckDigitalSign;
	}

	public void setBypassCheckDigitalSign(String bypassCheckDigitalSign) {
		this.bypassCheckDigitalSign = bypassCheckDigitalSign;
	}

	public Long getVerificationTypeDoSign() {
		return verificationTypeDoSign;
	}

	public void setVerificationTypeDoSign(Long verificationTypeDoSign) {
		this.verificationTypeDoSign = verificationTypeDoSign;
	}

	public Long getProfileTypeDoSign() {
		return profileTypeDoSign;
	}

	public void setProfileTypeDoSign(Long profileTypeDoSign) {
		this.profileTypeDoSign = profileTypeDoSign;
	}

	public String getBypassCheckCFDigitalSign() {
		return bypassCheckCFDigitalSign;
	}

	public void setBypassCheckCFDigitalSign(String bypassCheckCFDigitalSign) {
		this.bypassCheckCFDigitalSign = bypassCheckCFDigitalSign;
	}

	public Long getDigitalSignFileSizeLimit() {
		return digitalSignFileSizeLimit;
	}

	public void setDigitalSignFileSizeLimit(Long digitalSignFileSizeLimit) {
		this.digitalSignFileSizeLimit = digitalSignFileSizeLimit;
	}

	public BigDecimal getDownloadSizeLimit() {
		return downloadSizeLimit;
	}

	public void setDownloadSizeLimit(BigDecimal downloadSizeLimit) {
		this.downloadSizeLimit = downloadSizeLimit;
	}
}
