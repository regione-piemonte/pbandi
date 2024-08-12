package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

public class ProgettoVO {
	
	private Long _idProgetto = null;
	private Long _idSoggettoBeneficiario = null;
	private Long _idBando = null;
	private Long _numeroIstruttoriAssociati = null;
	private Long _progrSoggettoProgetto = null;
	private String _titoloBando =  null;
	private String _codiceVisualizzato = null;
	private String _beneficiario = null;
	private String _idIstanzaProcesso = null;
	
	
	
	public String getIdIstanzaProcesso() {
		return _idIstanzaProcesso;
	}
	public void setIdIstanzaProcesso(String istanzaProcesso) {
		_idIstanzaProcesso = istanzaProcesso;
	}
	public Long getProgrSoggettoProgetto() {
		return _progrSoggettoProgetto;
	}
	public void setProgrSoggettoProgetto(Long soggettoProgetto) {
		_progrSoggettoProgetto = soggettoProgetto;
	}
	public Long getIdBando() {
		return _idBando;
	}
	public void setIdBando(Long idBando) {
		this._idBando = idBando;
	}
	
	public Long getIdSoggettoBeneficiario() {
		return _idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this._idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public Long getIdProgetto() {
		return _idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this._idProgetto = idProgetto;
	}
	public Long getNumeroIstruttoriAssociati() {
		return _numeroIstruttoriAssociati;
	}
	public void setNumeroIstruttoriAssociati(Long numeroIstruttoriAssociati) {
		this._numeroIstruttoriAssociati = numeroIstruttoriAssociati;
	}
	public String getTitoloBando() {
		return _titoloBando;
	}
	public void setTitoloBando(String titoloBando) {
		this._titoloBando = titoloBando;
	}
	public String getCodiceVisualizzato() {
		return _codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this._codiceVisualizzato = codiceVisualizzato;
	}
	public String getBeneficiario() {
		return _beneficiario;
	}
	public void setBeneficiario(String beneficiario) {
		this._beneficiario = beneficiario;
	}
	
	/**
	 * Due progetti sono uguali se hanno
	 * lo stesso idProgetto 
	 */
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj == null && this == null)
			return true;
		if (obj instanceof ProgettoVO) {
			ProgettoVO temp = (ProgettoVO) obj;
			if (temp.getIdProgetto().equals(_idProgetto))
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
	
	

}
