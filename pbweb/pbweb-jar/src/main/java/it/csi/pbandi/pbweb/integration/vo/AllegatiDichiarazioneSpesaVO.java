package it.csi.pbandi.pbweb.integration.vo;


import java.util.Date;

public class AllegatiDichiarazioneSpesaVO { 
		
	private Long idFileEntita;
	private Long idFile;
	private Long idEntita;
	private Long idTarget;
	private String flagEntita;
	private Long idFolder;
	private Long idDocuIndex;
	private String nomeFile;
	private String sizeFile;
	private String idIntegrazioneSpesa;
	private Long idUtente;
	
	
	
	public String getIdIntegrazioneSpesa() {
		return idIntegrazioneSpesa;
	}

	public void setIdIntegrazioneSpesa(String idIntegrazioneSpesa) {
		this.idIntegrazioneSpesa = idIntegrazioneSpesa;
	}

	public AllegatiDichiarazioneSpesaVO() {
	}

	public AllegatiDichiarazioneSpesaVO(Long idFileEntita, Long idFile, Long idEntita, Long idTarget, String flagEntita,
			Long idFolder, Long idDocuIndex, String nomeFile, String sizeFile, String idIntegrazioneSpesa,
			Long idUtente) {
		this.idFileEntita = idFileEntita;
		this.idFile = idFile;
		this.idEntita = idEntita;
		this.idTarget = idTarget;
		this.flagEntita = flagEntita;
		this.idFolder = idFolder;
		this.idDocuIndex = idDocuIndex;
		this.nomeFile = nomeFile;
		this.sizeFile = sizeFile;
		this.idIntegrazioneSpesa = idIntegrazioneSpesa;
		this.idUtente = idUtente;
	}

	
	
	
	
	public Long getIdFileEntita() {
		return idFileEntita;
	}

	public void setIdFileEntita(Long idFileEntita) {
		this.idFileEntita = idFileEntita;
	}

	public Long getIdFile() {
		return idFile;
	}

	public void setIdFile(Long idFile) {
		this.idFile = idFile;
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

	public String getFlagEntita() {
		return flagEntita;
	}

	public void setFlagEntita(String flagEntita) {
		this.flagEntita = flagEntita;
	}

	public Long getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(Long idFolder) {
		this.idFolder = idFolder;
	}

	public Long getIdDocuIndex() {
		return idDocuIndex;
	}

	public void setIdDocuIndex(Long idDocuIndex) {
		this.idDocuIndex = idDocuIndex;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getSizeFile() {
		return sizeFile;
	}

	public void setSizeFile(String sizeFile) {
		this.sizeFile = sizeFile;
	}

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}
	
	
	
	
	
}	