/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto;

public class UtenteGIDTO implements java.io.Serializable {
	
	
	static final long serialVersionUID = 1;

	private String nomeDelegato = null;
	private String cognomeDelegato = null;
	private String codFiscaleDelegato = null;
	private String nomeDelegante = null;
	private String cognomeDelegante = null;
	private String codFiscaleDelegante = null;
	private String codAzienza = null;
	private String dataScadenzaDelega = null;
	private String dataScadenzaPassword = null;
	private String identita = null;
	private Long idUtenteDelegato = null;
	private Long idSoggettoDelegante = null;
	private Long idSoggettoBeneficiario = null;
	private boolean isRappresentanteLegale = true;
	
	
	public void setNomeDelegato(String val) {
		nomeDelegato = val;
	}
	
	public String getNomeDelegato() {
		return nomeDelegato;
	}

	public void setCognomeDelegato(String val) {
		cognomeDelegato = val;
	}
	
	public String getCognomeDelegato() {
		return cognomeDelegato;
	}

	public void setCodFiscaleDelegato(String val) {
		codFiscaleDelegato = val;
	}

	public String getCodFiscaleDelegato() {
		return codFiscaleDelegato;
	}
	
	public void setNomeDelegante(String val) {
		nomeDelegante = val;
	}
	
	public String getNomeDelegante() {
		return nomeDelegante;
	}
	
	public void setCognomeDelegante(String val) {
		cognomeDelegante = val;
	}
	
	public String getCognomeDelegante() {
		return cognomeDelegante;
	}
	
	public void setCodFiscaleDelegante(String val) {
		codFiscaleDelegante = val;
	}

	
	public String getCodFiscaleDelegante() {
		return codFiscaleDelegante;
	}

	public void setCodAzienza(String val) {
		codAzienza = val;
	}

	public String getCodAzienza() {
		return codAzienza;
	}

	public void setDataScadenzaDelega(String val) {
		dataScadenzaDelega = val;
	}
	
	public String getDataScadenzaDelega() {
		return dataScadenzaDelega;
	}

	public void setDataScadenzaPassword(String val) {
		dataScadenzaPassword = val;
	}

	public String getDataScadenzaPassword() {
		return dataScadenzaPassword;
	}

	public void setIdentita(String val) {
		identita = val;
	}
	
	public String getIdentita() {
		return identita;
	}

	public void setIdUtenteDelegato(Long val) {
		idUtenteDelegato = val;
	}
	
	public Long getIdUtenteDelegato() {
		return idUtenteDelegato;
	}

	public void setIdSoggettoDelegante(Long val) {
		idSoggettoDelegante = val;
	}

	public Long getIdSoggettoDelegante() {
		return idSoggettoDelegante;
	}

	public void setIdSoggettoBeneficiario(Long val) {
		idSoggettoBeneficiario = val;
	}
	
	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	public void setIsRappresentanteLegale(boolean val) {
		isRappresentanteLegale = val;
	}
	
	public boolean getIsRappresentanteLegale() {
		return isRappresentanteLegale;
	}

}
