package it.csi.pbandi.pbweb.pbandisrv.dto.manager.report;

import java.math.BigDecimal;

public class ChecklistHtmlAnagraficaDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	 

	private String ammontareTotaleSpesaRendicontata;
	private String ammontareTotaleSpesaValidata;
    private String assePrioritario;
    private String attoConcessioneContributo;
    private String bandoRiferimento;
    private String campoIrregolarita;
    private String codiceProgetto;
    private String contributoPubblicoConcesso;
    private String costoTotaleAmmesso;
    private String cup;
    private String dataControllo;
    private String denominazioneBeneficiario;
	private String descrizioneProgetto;
	private String firmaResponsabile;
    private String irregolarita;
    private String lineaIntervento;
    private String luogoControllo;
    private String referenteBeneficiario;
    private String strutturaResponsabile;
    private String titoloProgetto;
    private String totaleSpesaRendicontataOperazione;
    private String titoloBando;
    private String idDichiarazione;
    private String spesaValidataDS;
    private String spesaRendicontataDS;
    private String nomeChecklist;
    private String oggettoAffidamento;
    private String tipologiaContratto;
    private BigDecimal idProcesso;
    
    //Affidamenti
  	private String codProcAgg;
  	private String cigProcAgg;

    
    public String getAmmontareTotaleSpesaRendicontata() {
		return ammontareTotaleSpesaRendicontata;
	}
	public String getAmmontareTotaleSpesaValidata() {
		return ammontareTotaleSpesaValidata;
	}
	public String getAssePrioritario() {
		return assePrioritario;
	}
	public String getAttoConcessioneContributo() {
		return attoConcessioneContributo;
	}
	public String getBandoRiferimento() {
		return bandoRiferimento;
	}
	public String getCampoIrregolarita() {
		return campoIrregolarita;
	}
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public String getContributoPubblicoConcesso() {
		return contributoPubblicoConcesso;
	}
	public String getCostoTotaleAmmesso() {
		return costoTotaleAmmesso;
	}
	public String getCup() {
		return cup;
	}
	public String getDataControllo() {
		return dataControllo;
	}
	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}
	public String getDescrizioneProgetto() {
		return descrizioneProgetto;
	}
	public String getIrregolarita() {
		return irregolarita;
	}
	public String getLineaIntervento() {
		return lineaIntervento;
	}
	public String getReferenteBeneficiario() {
		return referenteBeneficiario;
	}
	public String getStrutturaResponsabile() {
		return strutturaResponsabile;
	}
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	public String getTotaleSpesaRendicontataOperazione() {
		return totaleSpesaRendicontataOperazione;
	}
	public String getTitoloBando() {
		return titoloBando;
	}
	public String getFirmaResponsabile() {
		return firmaResponsabile;
	}
	public String getLuogoControllo() {
		return luogoControllo;
	}
	public void setAmmontareTotaleSpesaRendicontata(
			String ammontareTotaleSpesaRendicontata) {
		this.ammontareTotaleSpesaRendicontata = ammontareTotaleSpesaRendicontata;
	}
	public void setAmmontareTotaleSpesaValidata(
			String ammontareTotaleSpesaValidata) {
		this.ammontareTotaleSpesaValidata = ammontareTotaleSpesaValidata;
	}
	public void setAssePrioritario(String assePrioritario) {
		this.assePrioritario = assePrioritario;
	}
	public void setAttoConcessioneContributo(
			String attoConcessioneContributo) {
		this.attoConcessioneContributo = attoConcessioneContributo;
	}
	public void setBandoRiferimento(String bandoRiferimento) {
		this.bandoRiferimento = bandoRiferimento;
	}
	public void setCampoIrregolarita(String campoIrregolarita) {
		this.campoIrregolarita = campoIrregolarita;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public void setContributoPubblicoConcesso(
			String contributoPubblicoConcesso) {
		this.contributoPubblicoConcesso = contributoPubblicoConcesso;
	}
	public void setCostoTotaleAmmesso(String costoTotaleAmmesso) {
		this.costoTotaleAmmesso = costoTotaleAmmesso;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	public void setDataControllo(String dataControllo) {
		this.dataControllo = dataControllo;
	}
	public void setDenominazioneBeneficiario(
			String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
	public void setDescrizioneProgetto(String descrizioneProgetto) {
		this.descrizioneProgetto = descrizioneProgetto;
	}
	public void setIrregolarita(String irregolarita) {
		this.irregolarita = irregolarita;
	}
	public void setLineaIntervento(String lineaIntervento) {
		this.lineaIntervento = lineaIntervento;
	}
	public void setReferenteBeneficiario(String referenteBeneficiario) {
		this.referenteBeneficiario = referenteBeneficiario;
	}
	public void setStrutturaResponsabile(String strutturaResponsabile) {
		this.strutturaResponsabile = strutturaResponsabile;
	}
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
	public void setTotaleSpesaRendicontataOperazione(
			String totaleSpesaRendicontataOperazione) {
		this.totaleSpesaRendicontataOperazione = totaleSpesaRendicontataOperazione;
	}
	public void setTitoloBando(String titoloBando) {
		this.titoloBando = titoloBando;
	}
	public void setFirmaResponsabile(String firmaResponsabile) {
		this.firmaResponsabile = firmaResponsabile;
	}
	public void setLuogoControllo(String luogoControllo) {
		this.luogoControllo = luogoControllo;
	}
	public String getIdDichiarazione() {
		return idDichiarazione;
	}
	public void setIdDichiarazione(String idDichiarazione) {
		this.idDichiarazione = idDichiarazione;
	}
	public String getSpesaValidataDS() {
		return spesaValidataDS;
	}
	public void setSpesaValidataDS(String spesaValidataDS) {
		this.spesaValidataDS = spesaValidataDS;
	}
	public String getSpesaRendicontataDS() {
		return spesaRendicontataDS;
	}
	public void setSpesaRendicontataDS(String spesaRendicontataDS) {
		this.spesaRendicontataDS = spesaRendicontataDS;
	}
	public String getNomeChecklist() {
		return nomeChecklist;
	}
	public void setNomeChecklist(String nomeChecklist) {
		this.nomeChecklist = nomeChecklist;
	}
	public String getOggettoAffidamento() {
		return oggettoAffidamento;
	}
	public void setOggettoAffidamento(String oggettoAffidamento) {
		this.oggettoAffidamento = oggettoAffidamento;
	}
	public String getTipologiaContratto() {
		return tipologiaContratto;
	}
	public void setTipologiaContratto(String tipologiaContratto) {
		this.tipologiaContratto = tipologiaContratto;
	}
	public BigDecimal getIdProcesso() {
		return idProcesso;
	}
	public void setIdProcesso(BigDecimal idProcesso) {
		this.idProcesso = idProcesso;
	}
	public String getCodProcAgg() {
		return codProcAgg;
	}
	public void setCodProcAgg(String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}
	public String getCigProcAgg() {
		return cigProcAgg;
	}
	public void setCigProcAgg(String cigProcAgg) {
		this.cigProcAgg = cigProcAgg;
	}
	
}
