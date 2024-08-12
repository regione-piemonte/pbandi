package it.csi.pbandi.pbweb.integration.vo;

import java.util.Date;

public class IntegrazioniControlliInLocoVO {
	
	private int idIntegrazione;
	private Date dtRich;
	private Date dtNotifica;
	private Date dtScadenza;
	private Date dtInvio;
	private int idStatoRich;
	private String descStatoRich;
	private int idControllo;
	private Boolean allegatiInseriti = false;
	
	
	//Per gli allegati:
	
	private String nomeFile;
	private String flagEntita;
	private int idDocumentoIndex;
	private int idFileEntita;
	private String descAlleg;
	private String numProtocollo; 
	
	//per le proroghe 
	
	private String statoRich;
	private String statoProroga;
	private Boolean richProroga;
	private Date dtRichiesta; 
	private int ggRichiesti; 
	private String motivazione; 
	private int ggApprovati;
	
	
	public Boolean getAllegatiInseriti() {
		return allegatiInseriti;
	}
	public void setAllegatiInseriti(Boolean allegatiInseriti) {
		this.allegatiInseriti = allegatiInseriti;
	}
	public String getStatoRich() {
		return statoRich;
	}
	public void setStatoRich(String statoRich) {
		this.statoRich = statoRich;
	}
	public Boolean getRichProroga() {
		return richProroga;
	}
	public void setRichProroga(Boolean richProroga) {
		this.richProroga = richProroga;
	}
	public Date getDtRichiesta() {
		return dtRichiesta;
	}
	public void setDtRichiesta(Date dtRichiesta) {
		this.dtRichiesta = dtRichiesta;
	}
	public int getGgRichiesti() {
		return ggRichiesti;
	}
	public void setGgRichiesti(int ggRichiesti) {
		this.ggRichiesti = ggRichiesti;
	}
	public String getMotivazione() {
		return motivazione;
	}
	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}
	public int getGgApprovati() {
		return ggApprovati;
	}
	public void setGgApprovati(int ggApprovati) {
		this.ggApprovati = ggApprovati;
	}
	public String getStatoProroga() {
		return statoProroga;
	}
	public void setStatoProroga(String statoProroga) {
		this.statoProroga = statoProroga;
	} 

	
	public int getIdControllo() {
		return idControllo;
	}
	public void setIdControllo(int idControllo) {
		this.idControllo = idControllo;
	}
	public String getNumProtocollo() {
		return numProtocollo;
	}
	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}
	public int getIdIntegrazione() {
		return idIntegrazione;
	}
	public void setIdIntegrazione(int idIntegrazione) {
		this.idIntegrazione = idIntegrazione;
	}
	
	public Date getDtRich() {
		return dtRich;
	}
	public void setDtRich(Date dtRich) {
		this.dtRich = dtRich;
	}
	public Date getDtNotifica() {
		return dtNotifica;
	}
	public void setDtNotifica(Date dtNotifica) {
		this.dtNotifica = dtNotifica;
	}
	public Date getDtScadenza() {
		return dtScadenza;
	}
	public void setDtScadenza(Date dtScadenza) {
		this.dtScadenza = dtScadenza;
	}
	public Date getDtInvio() {
		return dtInvio;
	}
	public void setDtInvio(Date dtInvio) {
		this.dtInvio = dtInvio;
	}
	public int getIdStatoRich() {
		return idStatoRich;
	}
	public void setIdStatoRich(int idStatoRich) {
		this.idStatoRich = idStatoRich;
	}
	public String getDescStatoRich() {
		return descStatoRich;
	}
	public void setDescStatoRich(String descStatoRich) {
		this.descStatoRich = descStatoRich;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getFlagEntita() {
		return flagEntita;
	}
	public void setFlagEntita(String flagEntita) {
		this.flagEntita = flagEntita;
	}
	public int getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(int idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public int getIdFileEntita() {
		return idFileEntita;
	}
	public void setIdFileEntita(int idFileEntita) {
		this.idFileEntita = idFileEntita;
	}
	public String getDescAlleg() {
		return descAlleg;
	}
	public void setDescAlleg(String descAlleg) {
		this.descAlleg = descAlleg;
	}
	
	
	

}