package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class DatiBeneficiarioBilancioVO extends GenericVO {
	private BigDecimal idAttoLiquidazione;
	private String codiceBeneficiarioBilancio;
	private String codiceFiscaleSoggetto;
	private String partitaIva;
	private Date dtInserimento;
	private Date dtAggiornamento;
	private String denominazione;
	private String denominazioneRagioneSociale;
	private String descIndirizzo;
	private String descComune;
	private String provincia;
	private String descIndirizzoSecondaria;
	private String descComuneSecondaria;
	private String provinciaSecondaria;
	private String fax;
	private String email;
	private String telefono;
	
	public BigDecimal getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}
	public void setIdAttoLiquidazione(BigDecimal idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}
	public String getCodiceBeneficiarioBilancio() {
		return codiceBeneficiarioBilancio;
	}
	public void setCodiceBeneficiarioBilancio(String codiceBeneficiarioBilancio) {
		this.codiceBeneficiarioBilancio = codiceBeneficiarioBilancio;
	}
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}
	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public Date getDtInserimento() {
		return dtInserimento;
	}
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getDenominazioneRagioneSociale() {
		return denominazioneRagioneSociale;
	}
	public void setDenominazioneRagioneSociale(String denominazioneRagioneSociale) {
		this.denominazioneRagioneSociale = denominazioneRagioneSociale;
	}
	public String getDescIndirizzo() {
		return descIndirizzo;
	}
	public void setDescIndirizzo(String descIndirizzo) {
		this.descIndirizzo = descIndirizzo;
	}
	public String getDescComune() {
		return descComune;
	}
	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getDescIndirizzoSecondaria() {
		return descIndirizzoSecondaria;
	}
	public void setDescIndirizzoSecondaria(String descIndirizzoSecondaria) {
		this.descIndirizzoSecondaria = descIndirizzoSecondaria;
	}
	public String getDescComuneSecondaria() {
		return descComuneSecondaria;
	}
	public void setDescComuneSecondaria(String descComuneSecondaria) {
		this.descComuneSecondaria = descComuneSecondaria;
	}
	public String getProvinciaSecondaria() {
		return provinciaSecondaria;
	}
	public void setProvinciaSecondaria(String provinciaSecondaria) {
		this.provinciaSecondaria = provinciaSecondaria;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


}
