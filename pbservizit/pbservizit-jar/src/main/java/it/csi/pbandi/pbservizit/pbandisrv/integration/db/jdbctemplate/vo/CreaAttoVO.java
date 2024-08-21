/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class CreaAttoVO extends GenericVO {
	private BigDecimal idAttoLiquidazione;
	private BigDecimal idSettoreEnte;
	private String annoAtto;
	private String dirAtto;
	private String settoreAtto;
	private String tipoSoggetto;
	private String tipoRitenuta;
	private BigDecimal impNonSoggette;
	private BigDecimal aliqIrpef;
	private String datoInps;
	private String inpsAltraCassa;
	private String codAttivita;
	private String codAltraCassa;
	private Date inpsDal;
	private Date inpsAl;
	private String rischioInail;
	private String divisa;
	private String note;
	private String flFatture;
	private String flEstrCopiaProv;
	private String flDocGiustif;
	private String flDichiaraz;
	private String allAltro;	
	private Date dataScadenza;
	private String nroTelLiq;
	private String nomeLiq;
	private String nomeDir;
	private BigDecimal idBeneficiarioBilancio;
	private BigDecimal idDatiPagamentoAtto;
	private String descAtto;
	
	private BigDecimal importoImponibile;
	private BigDecimal impNonSoggettoRitenuta;
	private BigDecimal idAliquotaRitenuta;
	
	public BigDecimal getImportoImponibile() {
		return importoImponibile;
	}
	public void setImportoImponibile(BigDecimal importoImponibile) {
		this.importoImponibile = importoImponibile;
	}
	public BigDecimal getImpNonSoggettoRitenuta() {
		return impNonSoggettoRitenuta;
	}
	public void setImpNonSoggettoRitenuta(BigDecimal impNonSoggettoRitenuta) {
		this.impNonSoggettoRitenuta = impNonSoggettoRitenuta;
	}
	
	public BigDecimal getIdAliquotaRitenuta() {
		return idAliquotaRitenuta;
	}
	public void setIdAliquotaRitenuta(BigDecimal idAliquotaRitenuta) {
		this.idAliquotaRitenuta = idAliquotaRitenuta;
	}

	// CDU-110-V03 inizio
	private String descCausale;
	//CDU-110-V03 fine
		
	public String getDescCausale() {
		return descCausale;
	}
	public void setDescCausale(String descCausale) {
		this.descCausale = descCausale;
	}
	public BigDecimal getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}
	public void setIdAttoLiquidazione(BigDecimal idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}	
	public String getAnnoAtto() {
		return annoAtto;
	}
	public void setAnnoAtto(String annoAtto) {
		this.annoAtto = annoAtto;
	}
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	public String getTipoRitenuta() {
		return tipoRitenuta;
	}
	public void setTipoRitenuta(String tipoRitenuta) {
		this.tipoRitenuta = tipoRitenuta;
	}
	public BigDecimal getImpNonSoggette() {
		return impNonSoggette;
	}
	public void setImpNonSoggette(BigDecimal impNonSoggette) {
		this.impNonSoggette = impNonSoggette;
	}
	public BigDecimal getAliqIrpef() {
		return aliqIrpef;
	}
	public void setAliqIrpef(BigDecimal aliqIrpef) {
		this.aliqIrpef = aliqIrpef;
	}
	public String getInpsAltraCassa() {
		return inpsAltraCassa;
	}
	public Date getInpsDal() {
		return inpsDal;
	}
	public void setInpsDal(Date inpsDal) {
		this.inpsDal = inpsDal;
	}
	public Date getInpsAl() {
		return inpsAl;
	}
	public void setInpsAl(Date inpsAl) {
		this.inpsAl = inpsAl;
	}
	public void setInpsAltraCassa(String inpsAltraCassa) {
		this.inpsAltraCassa = inpsAltraCassa;
	}
	public String getDatoInps() {
		return datoInps;
	}
	public void setDatoInps(String datoInps) {
		this.datoInps = datoInps;
	}
	public String getCodAttivita() {
		return codAttivita;
	}
	public void setCodAttivita(String codAttivita) {
		this.codAttivita = codAttivita;
	}
	public String getCodAltraCassa() {
		return codAltraCassa;
	}
	public void setCodAltraCassa(String codAltraCassa) {
		this.codAltraCassa = codAltraCassa;
	}

	public String getRischioInail() {
		return rischioInail;
	}
	public void setRischioInail(String rischioInail) {
		this.rischioInail = rischioInail;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getFlFatture() {
		return flFatture;
	}
	public void setFlFatture(String flFatture) {
		this.flFatture = flFatture;
	}
	public String getFlEstrCopiaProv() {
		return flEstrCopiaProv;
	}
	public void setFlEstrCopiaProv(String flEstrCopiaProv) {
		this.flEstrCopiaProv = flEstrCopiaProv;
	}
	public String getFlDocGiustif() {
		return flDocGiustif;
	}
	public void setFlDocGiustif(String flDocGiustif) {
		this.flDocGiustif = flDocGiustif;
	}
	public String getFlDichiaraz() {
		return flDichiaraz;
	}
	public void setFlDichiaraz(String flDichiaraz) {
		this.flDichiaraz = flDichiaraz;
	}
	public String getAllAltro() {
		return allAltro;
	}
	public void setAllAltro(String allAltro) {
		this.allAltro = allAltro;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public String getNroTelLiq() {
		return nroTelLiq;
	}
	public void setNroTelLiq(String nroTelLiq) {
		this.nroTelLiq = nroTelLiq;
	}
	public String getNomeLiq() {
		return nomeLiq;
	}
	public void setNomeLiq(String nomeLiq) {
		this.nomeLiq = nomeLiq;
	}
	public String getNomeDir() {
		return nomeDir;
	}
	public void setNomeDir(String nomeDir) {
		this.nomeDir = nomeDir;
	}
	public void setIdSettoreEnte(BigDecimal idSettoreEnte) {
		this.idSettoreEnte = idSettoreEnte;
	}
	public BigDecimal getIdSettoreEnte() {
		return idSettoreEnte;
	}
	public String getDirAtto() {
		return dirAtto;
	}
	public void setDirAtto(String dirAtto) {
		this.dirAtto = dirAtto;
	}
	public String getSettoreAtto() {
		return settoreAtto;
	}
	public void setSettoreAtto(String settoreAtto) {
		this.settoreAtto = settoreAtto;
	}
	public BigDecimal getIdBeneficiarioBilancio() {
		return idBeneficiarioBilancio;
	}
	public void setIdBeneficiarioBilancio(BigDecimal idBeneficiarioBilancio) {
		this.idBeneficiarioBilancio = idBeneficiarioBilancio;
	}
	public BigDecimal getIdDatiPagamentoAtto() {
		return idDatiPagamentoAtto;
	}
	public void setIdDatiPagamentoAtto(BigDecimal idDatiPagamentoAtto) {
		this.idDatiPagamentoAtto = idDatiPagamentoAtto;
	}
	public String getDescAtto() {
		return descAtto;
	}
	public void setDescAtto(String descAtto) {
		this.descAtto = descAtto;
	}
	
}
