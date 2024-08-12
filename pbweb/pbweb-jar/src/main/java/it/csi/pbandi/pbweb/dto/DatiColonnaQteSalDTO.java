package it.csi.pbandi.pbweb.dto;

public class DatiColonnaQteSalDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idColonnaQtes;
	private String descColonnaQtes;
	private String estremiAttoApprovazione;

	public Long getIdColonnaQtes() {
		return idColonnaQtes;
	}

	public void setIdColonnaQtes(Long idColonnaQtes) {
		this.idColonnaQtes = idColonnaQtes;
	}

	public String getDescColonnaQtes() {
		return descColonnaQtes;
	}

	public void setDescColonnaQtes(String descColonnaQtes) {
		this.descColonnaQtes = descColonnaQtes;
	}

	public String getEstremiAttoApprovazione() {
		return estremiAttoApprovazione;
	}

	public void setEstremiAttoApprovazione(String estremiAttoApprovazione) {
		this.estremiAttoApprovazione = estremiAttoApprovazione;
	}

}
