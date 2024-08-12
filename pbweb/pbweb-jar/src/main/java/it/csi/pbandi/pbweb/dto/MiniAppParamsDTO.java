package it.csi.pbandi.pbweb.dto;

import java.io.Serializable;

public class MiniAppParamsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String codCausaleErogazione;
	private String codiceRuolo;
	private String idDichiarazione;
	private String idNotifica;
	private String identitaIride;
	private String idProgetto;
	private String idSoggettoBeneficiario;
	private String idSoggetto;
	private String idUtente;
	private boolean isIncaricato;
	private boolean isNeoFlux;
	private String processCommand;
	private String processIdentity;
	private String taskIdentity;
	private String taskName;
	private String userCredentials;
	private String workflowAction;
	private String webModule;	
	private String idSoggettoBeneficiarioDocumProgetto;
	private String idProgettoDocumProgetto;
	
	public MiniAppParamsDTO(int idProgetto2, int idSoggetto2, int idSoggettoBen, int idUtente2, String ruolo,
			String taskIdentity2, String taskName2, String wkfAction) {
		
		this.idProgetto = idProgetto2 + "";
		this.idSoggetto = idSoggetto2 + "";
		this.idSoggettoBeneficiario = idSoggettoBen + "";
		this.idUtente = idUtente2 + "";
		this.codiceRuolo = ruolo;
		this.taskIdentity = taskIdentity2;
		this.taskName = taskName2;
		this.workflowAction = wkfAction;
		
	}
	
	public String getIdSoggettoBeneficiarioDocumProgetto() {
		return idSoggettoBeneficiarioDocumProgetto;
	}
	public void setIdSoggettoBeneficiarioDocumProgetto(
			String idSoggettoBeneficiarioDocumProgetto) {
		this.idSoggettoBeneficiarioDocumProgetto = idSoggettoBeneficiarioDocumProgetto;
	}
	public String getIdProgettoDocumProgetto() {
		return idProgettoDocumProgetto;
	}
	public void setIdProgettoDocumProgetto(String idProgettoDocumProgetto) {
		this.idProgettoDocumProgetto = idProgettoDocumProgetto;
	}
	public String getProcessIdentity() {
		return processIdentity;
	}
	public void setProcessIdentity(String processIdentity) {
		this.processIdentity = processIdentity;
	}
	public String getProcessCommand() {
		return processCommand;
	}
	public void setProcessCommand(String processCommand) {
		this.processCommand = processCommand;
	}
	public String getWorkflowAction() {
		return workflowAction;
	}
	public void setWorkflowAction(String workflowAction) {
		this.workflowAction = workflowAction;
	}
	public String getUserCredentials() {
		return userCredentials;
	}
	public void setUserCredentials(String userCredentials) {
		this.userCredentials = userCredentials;
	}
	public String getIdDichiarazione() {
		return idDichiarazione;
	}
	public void setIdDichiarazione(String idDichiarazione) {
		this.idDichiarazione = idDichiarazione;
	}
	public String getCodiceRuolo() {
		return codiceRuolo;
	}
	public void setCodiceRuolo(String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}
	public String getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(String idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public String getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getIdentitaIride() {
		return identitaIride;
	}
	public void setIdentitaIride(String identitaIride) {
		this.identitaIride = identitaIride;
	}
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public boolean isIncaricato() {
		return isIncaricato;
	}
	public void setIncaricato(boolean isIncaricato) {
		this.isIncaricato = isIncaricato;
	}
	public void setTaskIdentity(String taskIdentity) {
		this.taskIdentity = taskIdentity;
	}
	public String getTaskIdentity() {
		return taskIdentity;
	}
	public void setIdProgetto(String idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getIdProgetto() {
		return idProgetto;
	}
	public void setCodCausaleErogazione(String codCausaleErogazione) {
		this.codCausaleErogazione = codCausaleErogazione;
	}
	public String getCodCausaleErogazione() {
		return codCausaleErogazione;
	}
	public void setWebModule(String webModule) {
		this.webModule = webModule;
	}
	public String getWebModule() {
		return webModule;
	}
	public boolean isNeoFlux() {
		return isNeoFlux;
	}
	public void setNeoFlux(boolean isNeoFlux) {
		this.isNeoFlux = isNeoFlux;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getIdNotifica() {
		return idNotifica;
	}
	public void setIdNotifica(String idNotifica) {
		this.idNotifica = idNotifica;
	}
	@Override
	public String toString() {
		return "MiniAppParamsDTO [codCausaleErogazione=" + codCausaleErogazione + ", codiceRuolo=" + codiceRuolo
				+ ", idDichiarazione=" + idDichiarazione + ", idNotifica=" + idNotifica + ", identitaIride="
				+ identitaIride + ", idProgetto=" + idProgetto + ", idSoggettoBeneficiario=" + idSoggettoBeneficiario
				+ ", idSoggetto=" + idSoggetto + ", idUtente=" + idUtente + ", isIncaricato=" + isIncaricato
				+ ", isNeoFlux=" + isNeoFlux + ", processCommand=" + processCommand + ", processIdentity="
				+ processIdentity + ", taskIdentity=" + taskIdentity + ", taskName=" + taskName + ", userCredentials="
				+ userCredentials + ", workflowAction=" + workflowAction + ", webModule=" + webModule
				+ ", idSoggettoBeneficiarioDocumProgetto=" + idSoggettoBeneficiarioDocumProgetto
				+ ", idProgettoDocumProgetto=" + idProgettoDocumProgetto + "]";
	}

}
