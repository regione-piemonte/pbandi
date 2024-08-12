package it.csi.pbandi.pbweb.pbandisrv.dto.manager.report;

public class DocumentoDiSpesa implements java.io.Serializable {
	static final long serialVersionUID = 1;

	private java.lang.Long idDocumento = null;
	public void setIdDocumento(java.lang.Long val) {
		idDocumento = val;
	}

	public java.lang.Long getIdDocumento() {
		return idDocumento;
	}

	private java.lang.String tipologia = null;

	public void setTipologia(java.lang.String val) {
		tipologia = val;
	}

	public java.lang.String getTipologia() {
		return tipologia;
	}

	private java.lang.String estremi = null;
	public void setEstremi(java.lang.String val) {
		estremi = val;
	}

	public java.lang.String getEstremi() {
		return estremi;
	}

	private java.lang.String fornitore = null;

	public void setFornitore(java.lang.String val) {
		fornitore = val;
	}

	public java.lang.String getFornitore() {
		return fornitore;
	}

	 
	private java.lang.String stato = null;

	 
	public void setStato(java.lang.String val) {
		stato = val;
	}

	public java.lang.String getStato() {
		return stato;
	}

	 
	private java.lang.String progetto = null;

	 
	public void setProgetto(java.lang.String val) {
		progetto = val;
	}

 
	public java.lang.String getProgetto() {
		return progetto;
	}

	 
	private java.lang.Double importo = null;

	 
	public void setImporto(java.lang.Double val) {
		importo = val;
	}

 
	public java.lang.Double getImporto() {
		return importo;
	}

 
	private java.lang.Double validato = null;

 
	public void setValidato(java.lang.Double val) {
		validato = val;
	}

 
	public java.lang.Double getValidato() {
		return validato;
	}

 
	private java.lang.String progetti = null;

	 
	public void setProgetti(java.lang.String val) {
		progetti = val;
	}
 
	public java.lang.String getProgetti() {
		return progetti;
	}

	 
	private java.lang.String importi = null;

	 
	public void setImporti(java.lang.String val) {
		importi = val;
	}

 
	public java.lang.String getImporti() {
		return importi;
	}

	 
	private java.lang.Boolean isModificabile = null;

	 
	public void setIsModificabile(java.lang.Boolean val) {
		isModificabile = val;
	}

 
	public java.lang.Boolean getIsModificabile() {
		return isModificabile;
	}

	 
	private java.lang.Boolean isClonabile = null;

	 
	public void setIsClonabile(java.lang.Boolean val) {
		isClonabile = val;
	}

 
	public java.lang.Boolean getIsClonabile() {
		return isClonabile;
	}

	 
	private java.lang.Boolean isEliminabile = null;

	 
	public void setIsEliminabile(java.lang.Boolean val) {
		isEliminabile = val;
	}
 
	public java.lang.Boolean getIsEliminabile() {
		return isEliminabile;
	}

	 
	private java.lang.Boolean isAssociabile = null;

	 
	public void setIsAssociabile(java.lang.Boolean val) {
		isAssociabile = val;
	}

 
	public java.lang.Boolean getIsAssociabile() {
		return isAssociabile;
	}

	 
	private java.lang.Boolean isAssociato = null;

	 
	public void setIsAssociato(java.lang.Boolean val) {
		isAssociato = val;
	}

 
	public java.lang.Boolean getIsAssociato() {
		return isAssociato;
	}

	 
	private java.lang.String tipoInvio = null;

 
	public void setTipoInvio(java.lang.String val) {
		tipoInvio = val;
	}
 
	public java.lang.String getTipoInvio() {
		return tipoInvio;
	}

	private java.lang.Boolean isAllegatiPresenti = null;

	public void setIsAllegatiPresenti(java.lang.Boolean val) {
		isAllegatiPresenti = val;
	}

 
	public java.lang.Boolean getIsAllegatiPresenti() {
		return isAllegatiPresenti;
	}

	public String getAllegatiPresenti() {
		if(isAllegatiPresenti!=null && isAllegatiPresenti.booleanValue()==true)
		return "Si";
		else return "No";
	}

	 
	public java.lang.String getDescTipoInvio() {
		if(tipoInvio!=null){
			if(tipoInvio.equals("D"))
				return "Digitale";
			else if(tipoInvio.equals("C"))
				return "Cartaceo";
			else 
				return tipoInvio;
		}
		return null;
	}

 
}
