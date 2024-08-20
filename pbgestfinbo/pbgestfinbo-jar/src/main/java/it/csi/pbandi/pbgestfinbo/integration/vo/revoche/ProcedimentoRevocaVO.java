/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;

import java.math.BigDecimal;
import java.util.Date;

public class ProcedimentoRevocaVO {
    //[Numero procedimento di revoca]
    private Long idProcedimentoRevoca;
    private Long numeroProcedimentoRevoca;

    //[Beneficiario]
    private Long idSoggetto;
    private String codiceFiscaleSoggetto;
    private Long idBeneficiario; //id_ente_giuridico / id_persona_fisica
    private String denominazioneBeneficiario;

    //[Bando]
    private Long idDomanda;
    private Long progrBandoLineaIntervento;
    private String nomeBandoLinea;

    //[Progetto]
    private Long idProgetto;
    private String titoloProgetto;
    private String codiceVisualizzatoProgetto;

    //[Causa del procedimento di revoca]
    private Long idCausaleBlocco;
    private String descCausaleBlocco;

    //[Autorità controllante]
    private Long idAutoritaControllante;
    private String descAutoritaControllante;

    //[Stato del procedimento di revoca]
    private Long idStatoRevoca;
    private String descStatoRevoca;

    //[Data dello stato del procedimento di revoca]
    private Date dataStatoRevoca;

    //[Attività]
    private Long idAttivitaRevoca;
    private String descAttivitaRevoca;

    //[Data attività]
    private Date dataAttivitaRevoca;

    //[Data notifica]
    private Date dataNotifica;

    //[scadenza]
    private Long giorniScadenza;
    private Date dataScadenza;

    //[Proroga]
    private Boolean proroga;

    //[Note]
    private String note;

    //[Istruttore che ha avviato l’istruttoria]
    private Long idSoggettoIstruttore;
    private String denominazioneIstruttore;

    //[Numero di protocollo]
    private Long numeroProtocollo;

    //[Data avvio procedimento di revoca]
    private Date dataAvvioProcedimento;

    //IMPORTI PROCEDIMENTO REVOCA
    private String modalitaAgevolazioneContributo;
    private String modalitaAgevolazioneFinanziamento;
    private String modalitaAgevolazioneGaranzia;

    //[Importo ammesso]
    private BigDecimal importoAmmessoContributo;
    private BigDecimal importoAmmessoFinanziamento;
    private BigDecimal importoAmmessoGaranzia;
    //[Importo concesso] per le modalità di agevolazioni previste
    private BigDecimal importoConcessoContributo;
    private BigDecimal importoConcessoFinanziamento;
    private BigDecimal importoConcessoGaranzia;
    //[Importo già revocato] per le modalità di agevolazioni previste
    private BigDecimal importoRevocatoContributo;
    private BigDecimal importoRevocatoFinanziamento;
    private BigDecimal importoRevocatoGaranzia;
    //[Importo da revocare] per le modalità di agevolazioni previste
    private BigDecimal importoDaRevocareContributo;
    private BigDecimal importoDaRevocareFinanziamento;
    private BigDecimal importoDaRevocareGaranzia;

    //Informazioni ricavate da Amministrativo contabile
    //[Importo erogato]
    private BigDecimal importoErogatoContributo;
    private BigDecimal importoErogatoFinanziamento;
    private BigDecimal importoErogatoGaranzia;
    //[Importo recuperato]
    private BigDecimal importoRecuperatoContributo;
    private BigDecimal importoRecuperatoFinanziamento;
    private BigDecimal importoRecuperatoGaranzia;
    //[Importo rimborsato]
    private BigDecimal importoRimborsatoFinanziamento;

    //Informazioni calcolate
    //[Importo concesso al netto del revocato]
    private BigDecimal importoConcessoNeRevocatoContributo;
    private BigDecimal importoConcessoNeRevocatoFinanziamento;
    private BigDecimal importoConcessoNeRevocatoGaranzia;
    //[Importo erogato al netto del recuperato e del rimborsato]
    private BigDecimal importoErogatoNeRecuperatoRimborsatoContributo;
    private BigDecimal importoErogatoNeRecuperatoRimborsatoFinanziamento;
    private BigDecimal importoErogatoNeRecuperatoRimborsatoGaranzia;

    //valori per invia incarico ad erogazione
    private BigDecimal impIres;
    private BigDecimal impDaErogareContributo;
    private String causaleErogazioneContributo;
    private BigDecimal impDaErogareFinanziamento;
    private String causaleErogazioneFinanziamento;
    private Long idDichiarazioneSpesa;

    public ProcedimentoRevocaVO() {
    }

    public String getModalitaAgevolazioneContributo() {
        return modalitaAgevolazioneContributo;
    }

    public void setModalitaAgevolazioneContributo(String modalitaAgevolazioneContributo) {
        this.modalitaAgevolazioneContributo = modalitaAgevolazioneContributo;
    }

    public String getModalitaAgevolazioneFinanziamento() {
        return modalitaAgevolazioneFinanziamento;
    }

    public void setModalitaAgevolazioneFinanziamento(String modalitaAgevolazioneFinanziamento) {
        this.modalitaAgevolazioneFinanziamento = modalitaAgevolazioneFinanziamento;
    }

    public String getModalitaAgevolazioneGaranzia() {
        return modalitaAgevolazioneGaranzia;
    }

    public void setModalitaAgevolazioneGaranzia(String modalitaAgevolazioneGaranzia) {
        this.modalitaAgevolazioneGaranzia = modalitaAgevolazioneGaranzia;
    }

    public Long getIdProcedimentoRevoca() {
        return idProcedimentoRevoca;
    }

    public void setIdProcedimentoRevoca(Long idProcedimentoRevoca) {
        this.idProcedimentoRevoca = idProcedimentoRevoca;
    }

    public Long getNumeroProcedimentoRevoca() {
        return numeroProcedimentoRevoca;
    }

    public void setNumeroProcedimentoRevoca(Long numeroProcedimentoRevoca) {
        this.numeroProcedimentoRevoca = numeroProcedimentoRevoca;
    }

    public Long getIdSoggetto() {
        return idSoggetto;
    }

    public void setIdSoggetto(Long idSoggetto) {
        this.idSoggetto = idSoggetto;
    }

    public String getCodiceFiscaleSoggetto() {
        return codiceFiscaleSoggetto;
    }

    public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
        this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
    }

    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public String getDenominazioneBeneficiario() {
        return denominazioneBeneficiario;
    }

    public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
        this.denominazioneBeneficiario = denominazioneBeneficiario;
    }

    public Long getIdDomanda() {
        return idDomanda;
    }

    public void setIdDomanda(Long idDomanda) {
        this.idDomanda = idDomanda;
    }

    public Long getProgrBandoLineaIntervento() {
        return progrBandoLineaIntervento;
    }

    public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
        this.progrBandoLineaIntervento = progrBandoLineaIntervento;
    }

    public String getNomeBandoLinea() {
        return nomeBandoLinea;
    }

    public void setNomeBandoLinea(String nomeBandoLinea) {
        this.nomeBandoLinea = nomeBandoLinea;
    }

    public Long getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(Long idProgetto) {
        this.idProgetto = idProgetto;
    }

    public String getTitoloProgetto() {
        return titoloProgetto;
    }

    public void setTitoloProgetto(String titoloProgetto) {
        this.titoloProgetto = titoloProgetto;
    }

    public String getCodiceVisualizzatoProgetto() {
        return codiceVisualizzatoProgetto;
    }

    public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
        this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
    }

    public Long getIdCausaleBlocco() {
        return idCausaleBlocco;
    }

    public void setIdCausaleBlocco(Long idCausaleBlocco) {
        this.idCausaleBlocco = idCausaleBlocco;
    }

    public String getDescCausaleBlocco() {
        return descCausaleBlocco;
    }

    public void setDescCausaleBlocco(String descCausaleBlocco) {
        this.descCausaleBlocco = descCausaleBlocco;
    }

    public Long getIdAutoritaControllante() {
        return idAutoritaControllante;
    }

    public void setIdAutoritaControllante(Long idAutoritaControllante) {
        this.idAutoritaControllante = idAutoritaControllante;
    }

    public String getDescAutoritaControllante() {
        return descAutoritaControllante;
    }

    public void setDescAutoritaControllante(String descAutoritaControllante) {
        this.descAutoritaControllante = descAutoritaControllante;
    }

    public Long getIdStatoRevoca() {
        return idStatoRevoca;
    }

    public void setIdStatoRevoca(Long idStatoRevoca) {
        this.idStatoRevoca = idStatoRevoca;
    }

    public String getDescStatoRevoca() {
        return descStatoRevoca;
    }

    public void setDescStatoRevoca(String descStatoRevoca) {
        this.descStatoRevoca = descStatoRevoca;
    }

    public Date getDataStatoRevoca() {
        return dataStatoRevoca;
    }

    public void setDataStatoRevoca(Date dataStatoRevoca) {
        this.dataStatoRevoca = dataStatoRevoca;
    }

    public Long getIdAttivitaRevoca() {
        return idAttivitaRevoca;
    }

    public void setIdAttivitaRevoca(Long idAttivitaRevoca) {
        this.idAttivitaRevoca = idAttivitaRevoca;
    }

    public String getDescAttivitaRevoca() {
        return descAttivitaRevoca;
    }

    public void setDescAttivitaRevoca(String descAttivitaRevoca) {
        this.descAttivitaRevoca = descAttivitaRevoca;
    }

    public Date getDataAttivitaRevoca() {
        return dataAttivitaRevoca;
    }

    public void setDataAttivitaRevoca(Date dataAttivitaRevoca) {
        this.dataAttivitaRevoca = dataAttivitaRevoca;
    }

    public Date getDataNotifica() {
        return dataNotifica;
    }

    public void setDataNotifica(Date dataNotifica) {
        this.dataNotifica = dataNotifica;
    }

    public Long getGiorniScadenza() {
        return giorniScadenza;
    }

    public void setGiorniScadenza(Long giorniScadenza) {
        this.giorniScadenza = giorniScadenza;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public Boolean getProroga() {
        return proroga;
    }

    public void setProroga(Boolean proroga) {
        this.proroga = proroga;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getIdSoggettoIstruttore() {
        return idSoggettoIstruttore;
    }

    public void setIdSoggettoIstruttore(Long idSoggettoIstruttore) {
        this.idSoggettoIstruttore = idSoggettoIstruttore;
    }

    public String getDenominazioneIstruttore() {
        return denominazioneIstruttore;
    }

    public void setDenominazioneIstruttore(String denominazioneIstruttore) {
        this.denominazioneIstruttore = denominazioneIstruttore;
    }

    public Long getNumeroProtocollo() {
        return numeroProtocollo;
    }

    public void setNumeroProtocollo(Long numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataAvvioProcedimento() {
        return dataAvvioProcedimento;
    }

    public void setDataAvvioProcedimento(Date dataAvvioProcedimento) {
        this.dataAvvioProcedimento = dataAvvioProcedimento;
    }

    public BigDecimal getImportoAmmessoContributo() {
        return importoAmmessoContributo;
    }

    public void setImportoAmmessoContributo(BigDecimal importoAmmessoContributo) {
        this.importoAmmessoContributo = importoAmmessoContributo;
    }

    public BigDecimal getImportoAmmessoFinanziamento() {
        return importoAmmessoFinanziamento;
    }

    public void setImportoAmmessoFinanziamento(BigDecimal importoAmmessoFinanziamento) {
        this.importoAmmessoFinanziamento = importoAmmessoFinanziamento;
    }

    public BigDecimal getImportoAmmessoGaranzia() {
        return importoAmmessoGaranzia;
    }

    public void setImportoAmmessoGaranzia(BigDecimal importoAmmessoGaranzia) {
        this.importoAmmessoGaranzia = importoAmmessoGaranzia;
    }

    public BigDecimal getImportoConcessoContributo() {
        return importoConcessoContributo;
    }

    public void setImportoConcessoContributo(BigDecimal importoConcessoContributo) {
        this.importoConcessoContributo = importoConcessoContributo;
    }

    public BigDecimal getImportoConcessoFinanziamento() {
        return importoConcessoFinanziamento;
    }

    public void setImportoConcessoFinanziamento(BigDecimal importoConcessoFinanziamento) {
        this.importoConcessoFinanziamento = importoConcessoFinanziamento;
    }

    public BigDecimal getImportoConcessoGaranzia() {
        return importoConcessoGaranzia;
    }

    public void setImportoConcessoGaranzia(BigDecimal importoConcessoGaranzia) {
        this.importoConcessoGaranzia = importoConcessoGaranzia;
    }

    public BigDecimal getImportoRevocatoContributo() {
        return importoRevocatoContributo;
    }

    public void setImportoRevocatoContributo(BigDecimal importoRevocatoContributo) {
        this.importoRevocatoContributo = importoRevocatoContributo;
    }

    public BigDecimal getImportoRevocatoFinanziamento() {
        return importoRevocatoFinanziamento;
    }

    public void setImportoRevocatoFinanziamento(BigDecimal importoRevocatoFinanziamento) {
        this.importoRevocatoFinanziamento = importoRevocatoFinanziamento;
    }

    public BigDecimal getImportoRevocatoGaranzia() {
        return importoRevocatoGaranzia;
    }

    public void setImportoRevocatoGaranzia(BigDecimal importoRevocatoGaranzia) {
        this.importoRevocatoGaranzia = importoRevocatoGaranzia;
    }

    public BigDecimal getImportoDaRevocareContributo() {
        return importoDaRevocareContributo;
    }

    public void setImportoDaRevocareContributo(BigDecimal importoDaRevocareContributo) {
        this.importoDaRevocareContributo = importoDaRevocareContributo;
    }

    public BigDecimal getImportoDaRevocareFinanziamento() {
        return importoDaRevocareFinanziamento;
    }

    public void setImportoDaRevocareFinanziamento(BigDecimal importoDaRevocareFinanziamento) {
        this.importoDaRevocareFinanziamento = importoDaRevocareFinanziamento;
    }

    public BigDecimal getImportoDaRevocareGaranzia() {
        return importoDaRevocareGaranzia;
    }

    public void setImportoDaRevocareGaranzia(BigDecimal importoDaRevocareGaranzia) {
        this.importoDaRevocareGaranzia = importoDaRevocareGaranzia;
    }

    public BigDecimal getImportoErogatoContributo() {
        return importoErogatoContributo;
    }

    public void setImportoErogatoContributo(BigDecimal importoErogatoContributo) {
        this.importoErogatoContributo = importoErogatoContributo;
    }

    public BigDecimal getImportoErogatoFinanziamento() {
        return importoErogatoFinanziamento;
    }

    public void setImportoErogatoFinanziamento(BigDecimal importoErogatoFinanziamento) {
        this.importoErogatoFinanziamento = importoErogatoFinanziamento;
    }

    public BigDecimal getImportoErogatoGaranzia() {
        return importoErogatoGaranzia;
    }

    public void setImportoErogatoGaranzia(BigDecimal importoErogatoGaranzia) {
        this.importoErogatoGaranzia = importoErogatoGaranzia;
    }

    public BigDecimal getImportoRecuperatoContributo() {
        return importoRecuperatoContributo;
    }

    public void setImportoRecuperatoContributo(BigDecimal importoRecuperatoContributo) {
        this.importoRecuperatoContributo = importoRecuperatoContributo;
    }

    public BigDecimal getImportoRecuperatoFinanziamento() {
        return importoRecuperatoFinanziamento;
    }

    public void setImportoRecuperatoFinanziamento(BigDecimal importoRecuperatoFinanziamento) {
        this.importoRecuperatoFinanziamento = importoRecuperatoFinanziamento;
    }

    public BigDecimal getImportoRecuperatoGaranzia() {
        return importoRecuperatoGaranzia;
    }

    public void setImportoRecuperatoGaranzia(BigDecimal importoRecuperatoGaranzia) {
        this.importoRecuperatoGaranzia = importoRecuperatoGaranzia;
    }

    public BigDecimal getImportoRimborsatoFinanziamento() {
        return importoRimborsatoFinanziamento;
    }

    public void setImportoRimborsatoFinanziamento(BigDecimal importoRimborsatoFinanziamento) {
        this.importoRimborsatoFinanziamento = importoRimborsatoFinanziamento;
    }

    public BigDecimal getImportoConcessoNeRevocatoContributo() {
        return importoConcessoNeRevocatoContributo;
    }

    public void setImportoConcessoNeRevocatoContributo() {
        if(this.importoConcessoContributo != null){
            if(this.importoRevocatoContributo != null){
                this.importoConcessoNeRevocatoContributo = this.importoConcessoContributo.subtract(this.importoRevocatoContributo);
            }else{
                this.importoConcessoNeRevocatoContributo = this.importoConcessoContributo;
            }
        }
    }

    public BigDecimal getImportoConcessoNeRevocatoFinanziamento() {
        return importoConcessoNeRevocatoFinanziamento;
    }

    public void setImportoConcessoNeRevocatoFinanziamento() {
        if(this.importoConcessoFinanziamento != null){
            if(this.importoRevocatoFinanziamento != null){
                this.importoConcessoNeRevocatoFinanziamento = this.importoConcessoFinanziamento.subtract(this.importoRevocatoFinanziamento);
            }else{
                this.importoConcessoNeRevocatoFinanziamento = this.importoConcessoFinanziamento;
            }
        }
    }

    public BigDecimal getImportoConcessoNeRevocatoGaranzia() {
        return importoConcessoNeRevocatoGaranzia;
    }

    public void setImportoConcessoNeRevocatoGaranzia() {
        if(this.importoConcessoGaranzia != null){
            if(this.importoRevocatoGaranzia != null){
                this.importoConcessoNeRevocatoGaranzia = this.importoConcessoGaranzia.subtract(this.importoRevocatoGaranzia);
            }else{
                this.importoConcessoNeRevocatoGaranzia = this.importoConcessoGaranzia;
            }
        }
    }

    public BigDecimal getImportoErogatoNeRecuperatoRimborsatoContributo() {
        return importoErogatoNeRecuperatoRimborsatoContributo;
    }

    public void setImportoErogatoNeRecuperatoRimborsatoContributo() {
        if(this.importoErogatoContributo != null){
            if(this.importoRecuperatoContributo != null){
                this.importoErogatoNeRecuperatoRimborsatoContributo = this.importoErogatoContributo.subtract(this.importoRecuperatoContributo);
            }else{
                this.importoErogatoNeRecuperatoRimborsatoContributo = this.importoErogatoContributo;
            }
        }
    }

    public BigDecimal getImportoErogatoNeRecuperatoRimborsatoFinanziamento() {
        return importoErogatoNeRecuperatoRimborsatoFinanziamento;
    }

    public void setImportoErogatoNeRecuperatoRimborsatoFinanziamento() {
        if(this.importoErogatoFinanziamento != null){
            this.importoErogatoNeRecuperatoRimborsatoFinanziamento = this.importoErogatoFinanziamento;
            if(this.importoRecuperatoFinanziamento != null){
                this.importoErogatoNeRecuperatoRimborsatoFinanziamento = this.importoErogatoNeRecuperatoRimborsatoFinanziamento.subtract(this.importoRecuperatoFinanziamento);
            }
            if(this.importoRimborsatoFinanziamento != null){
                this.importoErogatoNeRecuperatoRimborsatoFinanziamento = this.importoErogatoNeRecuperatoRimborsatoFinanziamento.subtract(this.importoRimborsatoFinanziamento);
            }
        }
    }

    public BigDecimal getImportoErogatoNeRecuperatoRimborsatoGaranzia() {
        return importoErogatoNeRecuperatoRimborsatoGaranzia;
    }

    public void setImportoErogatoNeRecuperatoRimborsatoGaranzia() {
        if(this.importoErogatoGaranzia != null){
            if(this.importoRecuperatoGaranzia != null){
                this.importoErogatoNeRecuperatoRimborsatoGaranzia = this.importoErogatoGaranzia.subtract(this.importoRecuperatoGaranzia);
            }else{
                this.importoErogatoNeRecuperatoRimborsatoGaranzia = this.importoErogatoGaranzia;
            }
        }
    }

    public BigDecimal getImpIres() {
        return impIres;
    }

    public void setImpIres(BigDecimal impIres) {
        this.impIres = impIres;
    }

    public BigDecimal getImpDaErogareContributo() {
        return impDaErogareContributo;
    }

    public void setImpDaErogareContributo(BigDecimal impDaErogareContributo) {
        this.impDaErogareContributo = impDaErogareContributo;
    }

    public String getCausaleErogazioneContributo() {
        return causaleErogazioneContributo;
    }

    public void setCausaleErogazioneContributo(String causaleErogazioneContributo) {
        this.causaleErogazioneContributo = causaleErogazioneContributo;
    }

    public BigDecimal getImpDaErogareFinanziamento() {
        return impDaErogareFinanziamento;
    }

    public void setImpDaErogareFinanziamento(BigDecimal impDaErogareFinanziamento) {
        this.impDaErogareFinanziamento = impDaErogareFinanziamento;
    }

    public String getCausaleErogazioneFinanziamento() {
        return causaleErogazioneFinanziamento;
    }

    public void setCausaleErogazioneFinanziamento(String causaleErogazioneFinanziamento) {
        this.causaleErogazioneFinanziamento = causaleErogazioneFinanziamento;
    }

    public Long getIdDichiarazioneSpesa() {
        return idDichiarazioneSpesa;
    }

    public void setIdDichiarazioneSpesa(Long idDichiarazioneSpesa) {
        this.idDichiarazioneSpesa = idDichiarazioneSpesa;
    }
}
