/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio;

import java.math.BigDecimal;
import java.util.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class BeneficiarioBilancioVO extends GenericVO {
	private String abi;
	private String cab;
	private String cin;
	private String bic;
	private String descBreveStatoAtto;
	
	private String cap;
	private BigDecimal codiceBeneficiarioBilancio;
	private String codiceFiscaleBen;
	private String cognome;
	private BigDecimal codModPagBilancio;
	private String denominazione;
	private String ragioneSocialeSecondaria;
	private Date dtNascita;
	private Date dtEmissioneAtto;
	private String email;
	private String iban;
	private String numeroConto;
	private BigDecimal idAttoLiquidazione;
	private BigDecimal idBeneficiarioBilancio;
	private BigDecimal idComuneSede;
	private BigDecimal idComuneSedeEstero;
	private BigDecimal idStatoSede;
	private BigDecimal idComuneNascita;
	private BigDecimal idDatiPagamentoAtto;
	private BigDecimal idEstremiBancari;
	private BigDecimal idIndirizzo;
	private BigDecimal idModalitaErogazione;	
	private BigDecimal idProgetto;
	private BigDecimal idProvinciaSede;
	private BigDecimal idProvinciaNascita;
	private BigDecimal idSede;
	private BigDecimal idSoggetto;
	private BigDecimal idStatoAtto;
	private String modalitaPagamento;
	private String nome;
	private String 	partitaIva;
	private String sede;
	private String sesso;
	private String indirizzo;
	private BigDecimal idEnteGiuridico;
	private BigDecimal idPersonaFisica;
	
	/*
	 * FIX PBandi-2137 Dati della sede secondaria
	 */
	private BigDecimal idSedeSecondaria;
	private BigDecimal idIndirizzoSedeSecondaria;
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDtNascita() {
		return dtNascita;
	}
	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public BigDecimal getIdComuneNascita() {
		return idComuneNascita;
	}
	public void setIdComuneNascita(BigDecimal idComuneNascita) {
		this.idComuneNascita = idComuneNascita;
	}
	public BigDecimal getIdProvinciaNascita() {
		return idProvinciaNascita;
	}
	public void setIdProvinciaNascita(BigDecimal idProvinciaNascita) {
		this.idProvinciaNascita = idProvinciaNascita;
	}
	public BigDecimal getIdBeneficiarioBilancio() {
		return idBeneficiarioBilancio;
	}
	public void setIdBeneficiarioBilancio(BigDecimal idBeneficiarioBilancio) {
		this.idBeneficiarioBilancio = idBeneficiarioBilancio;
	}
	public BigDecimal getCodiceBeneficiarioBilancio() {
		return codiceBeneficiarioBilancio;
	}
	public void setCodiceBeneficiarioBilancio(BigDecimal codiceBeneficiarioBilancio) {
		this.codiceBeneficiarioBilancio = codiceBeneficiarioBilancio;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public BigDecimal getIdSede() {
		return idSede;
	}
	public void setIdSede(BigDecimal idSede) {
		this.idSede = idSede;
	}
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	public String getCodiceFiscaleBen() {
		return codiceFiscaleBen;
	}
	public void setCodiceFiscaleBen(String codiceFiscaleBen) {
		this.codiceFiscaleBen = codiceFiscaleBen;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getSede() {
		return sede;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getModalitaPagamento() {
		return modalitaPagamento;
	}
	public void setModalitaPagamento(String modalitaPagamento) {
		this.modalitaPagamento = modalitaPagamento;
	}
	public void setIdAttoLiquidazione(BigDecimal idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}
	public BigDecimal getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}
	public void setCodModPagBilancio(BigDecimal codModPagBilancio) {
		this.codModPagBilancio = codModPagBilancio;
	}
	public BigDecimal getCodModPagBilancio() {
		return codModPagBilancio;
	}
	public void setIdDatiPagamentoAtto(BigDecimal idDatiPagamentoAtto) {
		this.idDatiPagamentoAtto = idDatiPagamentoAtto;
	}
	public BigDecimal getIdDatiPagamentoAtto() {
		return idDatiPagamentoAtto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setDtEmissioneAtto(Date dtEmissioneAtto) {
		this.dtEmissioneAtto = dtEmissioneAtto;
	}
	public Date getDtEmissioneAtto() {
		return dtEmissioneAtto;
	}
	public void setIdStatoAtto(BigDecimal idStatoAtto) {
		this.idStatoAtto = idStatoAtto;
	}
	public BigDecimal getIdStatoAtto() {
		return idStatoAtto;
	}
	public void setIdEstremiBancari(BigDecimal idEstremiBancari) {
		this.idEstremiBancari = idEstremiBancari;
	}
	public BigDecimal getIdEstremiBancari() {
		return idEstremiBancari;
	}
	public void setAbi(String abi) {
		this.abi = abi;
	}
	public String getAbi() {
		return abi;
	}
	public void setCab(String cab) {
		this.cab = cab;
	}
	public String getCab() {
		return cab;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getCin() {
		return cin;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	public String getBic() {
		return bic;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setIdModalitaErogazione(BigDecimal idModalitaErogazione) {
		this.idModalitaErogazione = idModalitaErogazione;
	}
	public BigDecimal getIdModalitaErogazione() {
		return idModalitaErogazione;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getCap() {
		return cap;
	}
	public void setIdComuneSede(BigDecimal idComuneSede) {
		this.idComuneSede = idComuneSede;
	}
	public BigDecimal getIdComuneSede() {
		return idComuneSede;
	}
	public void setIdProvinciaSede(BigDecimal idProvinciaSede) {
		this.idProvinciaSede = idProvinciaSede;
	}
	public BigDecimal getIdProvinciaSede() {
		return idProvinciaSede;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setRagioneSocialeSecondaria(String ragioneSocialeSecondaria) {
		this.ragioneSocialeSecondaria = ragioneSocialeSecondaria;
	}
	public String getRagioneSocialeSecondaria() {
		return ragioneSocialeSecondaria;
	}
	public void setDescBreveStatoAtto(String descBreveStatoAtto) {
		this.descBreveStatoAtto = descBreveStatoAtto;
	}
	public String getDescBreveStatoAtto() {
		return descBreveStatoAtto;
	}
	public void setIdEnteGiuridico(BigDecimal idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}
	public BigDecimal getIdEnteGiuridico() {
		return idEnteGiuridico;
	}
	public void setIdPersonaFisica(BigDecimal idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}
	public BigDecimal getIdPersonaFisica() {
		return idPersonaFisica;
	}
	public void setIdComuneSedeEstero(BigDecimal idComuneSedeEstero) {
		this.idComuneSedeEstero = idComuneSedeEstero;
	}
	public BigDecimal getIdComuneSedeEstero() {
		return idComuneSedeEstero;
	}
	public void setIdStatoSede(BigDecimal idStatoSede) {
		this.idStatoSede = idStatoSede;
	}
	public BigDecimal getIdStatoSede() {
		return idStatoSede;
	}
	public void setNumeroConto(String numeroConto) {
		this.numeroConto = numeroConto;
	}
	public String getNumeroConto() {
		return numeroConto;
	}
	public BigDecimal getIdSedeSecondaria() {
		return idSedeSecondaria;
	}
	public void setIdSedeSecondaria(BigDecimal idSedeSecondaria) {
		this.idSedeSecondaria = idSedeSecondaria;
	}
	public BigDecimal getIdIndirizzoSedeSecondaria() {
		return idIndirizzoSedeSecondaria;
	}
	public void setIdIndirizzoSedeSecondaria(BigDecimal idIndirizzoSedeSecondaria) {
		this.idIndirizzoSedeSecondaria = idIndirizzoSedeSecondaria;
	}
	

}
