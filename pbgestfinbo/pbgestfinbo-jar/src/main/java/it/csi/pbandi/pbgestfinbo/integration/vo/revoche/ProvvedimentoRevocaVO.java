/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;

import java.math.BigDecimal;
import java.util.Date;

public class ProvvedimentoRevocaVO {

    //Provvedimento
    private Long idProvvedimentoRevoca;
    private Long numeroProvvedimentoRevoca;
    //Beneficiario
    private Long idSoggetto;
    private String ndg;
    private String codiceFiscaleSoggetto;
    private Long idBeneficiario;
    private String denominazioneBeneficiario;
    //Domanda
    private Long idDomanda;
    private String numeroDomanda;
    //Bando
    private Long idBando;
    private Long progrBandoLineaIntervento;
    private String nomeBandoLinea;
    //Progetto
    private Long idProgetto;
    private String titoloProgetto;
    private String codiceVisualizzatoProgetto;
    //Numero di protocollo
    private Long numeroProtocollo;
    //Data avvio del provvedimento di revoca
    private Date dataAvvioProvvedimentoRevoca;
    //Determina, Data Determina e estremi
    private Boolean flagDetermina;
    private Date dtDetermina;
    private String estremi;
    //Recupero, Modalità del recupero
    private Boolean flagOrdineRecupero;
    private Long idMancatoRecupero;
    private String descMancatoRecupero;
    //Motivo del provvedimento
    private Long idMotivoRevoca;
    private String descMotivoRevoca;
    //Causa del provvedimento di revoca
    private Long idCausaleBlocco;
    private String descCausaleBlocco;
    //Autorità controllante
    private Long idAutoritaControllante;
    private String descAutoritaControllante;
    //Stato del provvedimento di revoca
    private Long idStatoRevoca;
    private String descStatoRevoca;
    //Data dello stato provvedimento di revoca
    private Date dataStatoRevoca;
    //Attività del provvedimento di revoca
    private Long idAttivitaRevoca;
    private String descAttivitaRevoca;
    //Data attività del provvedimento di revoca
    private Date dataAttivitaRevoca;
    //Data notifica
    private Date dataNotifica;
    //Giorni di scadenza
    private Long giorniScadenza;
    //Data scadenza
    private Date dataScadenza;
    //Importo ammesso
    private BigDecimal importoAmmessoContributo;
    private BigDecimal importoAmmessoFinanziamento;
    private BigDecimal importoAmmessoGaranzia;
    //Verifica presenza disimpegni sul contributo, finanziamento e garanzia
    private Boolean flagContribRevoca;
    private Boolean flagContribMinorSpese;
    private Boolean flagContribDecurtaz;
    private Boolean flagFinanzRevoca;
    private Boolean flagFinanzMinorSpese;
    private Boolean flagFinanzDecurtaz;
    private Boolean flagGaranziaRevoca;
    private Boolean flagGaranziaMinorSpese;
    private Boolean flagGaranziaDecurtaz;
    //ModAgev
    private Long idModAgevContrib;
    private Long idModAgevContribRif;
    private String modAgevContrib;
    private Long idModAgevFinanz;
    private Long idModAgevFinanzRif;
    private String modAgevFinanz;
    private Long idModAgevGaranz;
    private Long idModAgevGaranzRif;
    private String modAgevGaranz;
    //Importo concesso
    private BigDecimal importoConcessoContributo;
    private BigDecimal importoConcessoFinanziamento;
    private BigDecimal importoConcessoGaranzia;
    //Importo già revocato (somma degli importi già revocati per il progetto)
    private BigDecimal importoRevocatoContributo;
    private BigDecimal importoRevocatoFinanziamento;
    private BigDecimal importoRevocatoGaranzia;
    //Importo erogato
    private BigDecimal importoErogatoContributo;
    private BigDecimal importoErogatoFinanziamento;
    private BigDecimal importoErogatoGaranzia;
    //Importo recuperato
    private BigDecimal importoRecuperatoContributo;
    private BigDecimal importoRecuperatoFinanziamento;
    private BigDecimal importoRecuperatoGaranzia;
    //Importo rimborsato
    private BigDecimal importoRimborsatoFinanziamento;
    //- Importo rimborsato FINANZIAMENTO
    private BigDecimal impConcAlNettoRevocatoContributo;
    private BigDecimal impConcAlNettoRevocatoFinanziamento;
    private BigDecimal impConcAlNettoRevocatoGaranzia;
    private BigDecimal impErogAlNettoRecuERimbContributo;
    private BigDecimal impErogAlNettoRecuERimbFinanziamento;
    private BigDecimal impErogAlNettoRecuERimbGaranzia;
    //Recupero importi e interessi revoca per contributo, finanziamento e garanzia
    private BigDecimal impContribRevocaNoRecu;
    private BigDecimal impContribRevocaRecu;
    private BigDecimal impContribInteressi;
    private BigDecimal impFinanzRevocaNoRecu;
    private BigDecimal impFinanzRevocaRecu;
    private BigDecimal impFinanzInteressi;
    private BigDecimal impFinanzPreRecu;
    private BigDecimal impGaranziaRevocaNoRecu;
    private BigDecimal impGaranziaRevocaRecu;
    private BigDecimal impGaranziaInteressi;
    private BigDecimal impGaranzPreRecu;
    //Note
    private String note;
    //Istruttore che ha avviato l’istruttoria
    private Long idSoggettoIstruttore;
    private String denominazioneIstruttore;
    
    private BigDecimal numeroCovar;
    

    public BigDecimal getNumeroCovar() {
		return numeroCovar;
	}

	public void setNumeroCovar(BigDecimal numeroCovar) {
		this.numeroCovar = numeroCovar;
	}

	public ProvvedimentoRevocaVO() {
    }

    public Long getIdProvvedimentoRevoca() {
        return idProvvedimentoRevoca;
    }

    public void setIdProvvedimentoRevoca(Long idProvvedimentoRevoca) {
        this.idProvvedimentoRevoca = idProvvedimentoRevoca;
    }

    public Long getNumeroProvvedimentoRevoca() {
        return numeroProvvedimentoRevoca;
    }

    public void setNumeroProvvedimentoRevoca(Long numeroProvvedimentoRevoca) {
        this.numeroProvvedimentoRevoca = numeroProvvedimentoRevoca;
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

    public String getNdg() {
        return ndg;
    }

    public void setNdg(String ndg) {
        this.ndg = ndg;
    }

    public String getNumeroDomanda() {
        return numeroDomanda;
    }

    public void setNumeroDomanda(String numeroDomanda) {
        this.numeroDomanda = numeroDomanda;
    }

    public Long getIdBando() {
        return idBando;
    }

    public void setIdBando(Long idBando) {
        this.idBando = idBando;
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

    public Long getNumeroProtocollo() {
        return numeroProtocollo;
    }

    public void setNumeroProtocollo(Long numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataAvvioProvvedimentoRevoca() {
        return dataAvvioProvvedimentoRevoca;
    }

    public void setDataAvvioProvvedimentoRevoca(Date dataAvvioProvvedimentoRevoca) {
        this.dataAvvioProvvedimentoRevoca = dataAvvioProvvedimentoRevoca;
    }

    public Boolean getFlagDetermina() {
        return flagDetermina;
    }

    public void setFlagDetermina(Boolean flagDetermina) {
        this.flagDetermina = flagDetermina;
    }

    public Date getDtDetermina() {
        return dtDetermina;
    }

    public void setDtDetermina(Date dtDetermina) {
        this.dtDetermina = dtDetermina;
    }

    public String getEstremi() {
        return estremi;
    }

    public void setEstremi(String estremi) {
        this.estremi = estremi;
    }

    public Boolean getFlagOrdineRecupero() {
        return flagOrdineRecupero;
    }

    public void setFlagOrdineRecupero(Boolean flagOrdineRecupero) {
        this.flagOrdineRecupero = flagOrdineRecupero;
    }

    public Long getIdMancatoRecupero() {
        return idMancatoRecupero;
    }

    public void setIdMancatoRecupero(Long idMancatoRecupero) {
        this.idMancatoRecupero = idMancatoRecupero;
    }

    public String getDescMancatoRecupero() {
        return descMancatoRecupero;
    }

    public void setDescMancatoRecupero(String descMancatoRecupero) {
        this.descMancatoRecupero = descMancatoRecupero;
    }

    public Long getIdMotivoRevoca() {
        return idMotivoRevoca;
    }

    public void setIdMotivoRevoca(Long idMotivoRevoca) {
        this.idMotivoRevoca = idMotivoRevoca;
    }

    public String getDescMotivoRevoca() {
        return descMotivoRevoca;
    }

    public void setDescMotivoRevoca(String descMotivoRevoca) {
        this.descMotivoRevoca = descMotivoRevoca;
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

    public Boolean getFlagContribRevoca() {
        return flagContribRevoca;
    }

    public void setFlagContribRevoca(Boolean flagContribRevoca) {
        this.flagContribRevoca = flagContribRevoca;
    }

    public Boolean getFlagContribMinorSpese() {
        return flagContribMinorSpese;
    }

    public void setFlagContribMinorSpese(Boolean flagContribMinorSpese) {
        this.flagContribMinorSpese = flagContribMinorSpese;
    }

    public Boolean getFlagContribDecurtaz() {
        return flagContribDecurtaz;
    }

    public void setFlagContribDecurtaz(Boolean flagContribDecurtaz) {
        this.flagContribDecurtaz = flagContribDecurtaz;
    }

    public Boolean getFlagFinanzRevoca() {
        return flagFinanzRevoca;
    }

    public void setFlagFinanzRevoca(Boolean flagFinanzRevoca) {
        this.flagFinanzRevoca = flagFinanzRevoca;
    }

    public Boolean getFlagFinanzMinorSpese() {
        return flagFinanzMinorSpese;
    }

    public void setFlagFinanzMinorSpese(Boolean flagFinanzMinorSpese) {
        this.flagFinanzMinorSpese = flagFinanzMinorSpese;
    }

    public Boolean getFlagFinanzDecurtaz() {
        return flagFinanzDecurtaz;
    }

    public void setFlagFinanzDecurtaz(Boolean flagFinanzDecurtaz) {
        this.flagFinanzDecurtaz = flagFinanzDecurtaz;
    }

    public Boolean getFlagGaranziaRevoca() {
        return flagGaranziaRevoca;
    }

    public void setFlagGaranziaRevoca(Boolean flagGaranziaRevoca) {
        this.flagGaranziaRevoca = flagGaranziaRevoca;
    }

    public Boolean getFlagGaranziaMinorSpese() {
        return flagGaranziaMinorSpese;
    }

    public void setFlagGaranziaMinorSpese(Boolean flagGaranziaMinorSpese) {
        this.flagGaranziaMinorSpese = flagGaranziaMinorSpese;
    }

    public Boolean getFlagGaranziaDecurtaz() {
        return flagGaranziaDecurtaz;
    }

    public void setFlagGaranziaDecurtaz(Boolean flagGaranziaDecurtaz) {
        this.flagGaranziaDecurtaz = flagGaranziaDecurtaz;
    }

    public Long getIdModAgevContrib() {
        return idModAgevContrib;
    }

    public void setIdModAgevContrib(Long idModAgevContrib) {
        this.idModAgevContrib = idModAgevContrib;
    }

    public Long getIdModAgevFinanz() {
        return idModAgevFinanz;
    }

    public void setIdModAgevFinanz(Long idModAgevFinanz) {
        this.idModAgevFinanz = idModAgevFinanz;
    }

    public Long getIdModAgevGaranz() {
        return idModAgevGaranz;
    }

    public void setIdModAgevGaranz(Long idModAgevGaranz) {
        this.idModAgevGaranz = idModAgevGaranz;
    }

    public Long getIdModAgevContribRif() {
        return idModAgevContribRif;
    }

    public void setIdModAgevContribRif(Long idModAgevContribRif) {
        this.idModAgevContribRif = idModAgevContribRif;
    }

    public Long getIdModAgevFinanzRif() {
        return idModAgevFinanzRif;
    }

    public void setIdModAgevFinanzRif(Long idModAgevFinanzRif) {
        this.idModAgevFinanzRif = idModAgevFinanzRif;
    }

    public Long getIdModAgevGaranzRif() {
        return idModAgevGaranzRif;
    }

    public void setIdModAgevGaranzRif(Long idModAgevGaranzRif) {
        this.idModAgevGaranzRif = idModAgevGaranzRif;
    }

    public String getModAgevContrib() {
        return modAgevContrib;
    }

    public void setModAgevContrib(String modAgevContrib) {
        this.modAgevContrib = modAgevContrib;
    }

    public String getModAgevFinanz() {
        return modAgevFinanz;
    }

    public void setModAgevFinanz(String modAgevFinanz) {
        this.modAgevFinanz = modAgevFinanz;
    }

    public String getModAgevGaranz() {
        return modAgevGaranz;
    }

    public void setModAgevGaranz(String modAgevGaranz) {
        this.modAgevGaranz = modAgevGaranz;
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

    public BigDecimal getImpConcAlNettoRevocatoContributo() {
        return impConcAlNettoRevocatoContributo;
    }

    public void setImpConcAlNettoRevocatoContributo() {
        if(this.importoConcessoContributo != null){
            if(this.importoRevocatoContributo != null){
                this.impConcAlNettoRevocatoContributo = this.importoConcessoContributo.subtract(this.importoRevocatoContributo);
            }else{
                this.impConcAlNettoRevocatoContributo = this.importoConcessoContributo;
            }
        }
    }

    public BigDecimal getImpConcAlNettoRevocatoFinanziamento() {
        return impConcAlNettoRevocatoFinanziamento;
    }

    public void setImpConcAlNettoRevocatoFinanziamento() {
        if(this.importoConcessoFinanziamento != null){
            if(this.importoRevocatoFinanziamento != null){
                this.impConcAlNettoRevocatoFinanziamento = this.importoConcessoFinanziamento.subtract(this.importoRevocatoFinanziamento);
            }else{
                this.impConcAlNettoRevocatoFinanziamento = this.importoConcessoFinanziamento;
            }
        }
    }

    public BigDecimal getImpConcAlNettoRevocatoGaranzia() {
        return impConcAlNettoRevocatoGaranzia;
    }

    public void setImpConcAlNettoRevocatoGaranzia() {
        if(this.importoConcessoGaranzia != null){
            if(this.importoRevocatoGaranzia != null){
                this.impConcAlNettoRevocatoGaranzia = this.importoConcessoGaranzia.subtract(this.importoRevocatoGaranzia);
            }else{
                this.impConcAlNettoRevocatoGaranzia = this.importoConcessoGaranzia;
            }
        }
    }

    public BigDecimal getImpErogAlNettoRecuERimbContributo() {
        return impErogAlNettoRecuERimbContributo;
    }

    public void setImpErogAlNettoRecuERimbContributo() {
        if(this.importoErogatoContributo != null){
            if(this.importoRecuperatoContributo != null){
                this.impErogAlNettoRecuERimbContributo = this.importoErogatoContributo.subtract(this.importoRecuperatoContributo);
            }else{
                this.impErogAlNettoRecuERimbContributo = this.importoErogatoContributo;
            }
        }
    }

    public BigDecimal getImpErogAlNettoRecuERimbFinanziamento() {
        return impErogAlNettoRecuERimbFinanziamento;
    }

    public void setImpErogAlNettoRecuERimbFinanziamento() {
        if(this.importoErogatoFinanziamento != null){
            this.impErogAlNettoRecuERimbFinanziamento = this.importoErogatoFinanziamento;
            if(this.importoRecuperatoFinanziamento != null){
                this.impErogAlNettoRecuERimbFinanziamento = this.impErogAlNettoRecuERimbFinanziamento.subtract(this.importoRecuperatoFinanziamento);
            }
            if(this.importoRimborsatoFinanziamento != null){
                this.impErogAlNettoRecuERimbFinanziamento = this.impErogAlNettoRecuERimbFinanziamento.subtract(this.importoRimborsatoFinanziamento);
            }
        }
    }

    public BigDecimal getImpErogAlNettoRecuERimbGaranzia() {
        return impErogAlNettoRecuERimbGaranzia;
    }

    public void setImpErogAlNettoRecuERimbGaranzia() {
        if(this.importoErogatoGaranzia != null){
            if(this.importoRecuperatoGaranzia != null){
                this.impErogAlNettoRecuERimbGaranzia = this.importoErogatoGaranzia.subtract(this.importoRecuperatoGaranzia);
            }else{
                this.impErogAlNettoRecuERimbGaranzia = this.importoErogatoGaranzia;
            }
        }
    }

    public BigDecimal getImpContribRevocaNoRecu() {
        return impContribRevocaNoRecu;
    }

    public void setImpContribRevocaNoRecu(BigDecimal impContribRevocaNoRecu) {
        this.impContribRevocaNoRecu = impContribRevocaNoRecu;
    }

    public BigDecimal getImpContribRevocaRecu() {
        return impContribRevocaRecu;
    }

    public void setImpContribRevocaRecu(BigDecimal impContribRevocaRecu) {
        this.impContribRevocaRecu = impContribRevocaRecu;
    }

    public BigDecimal getImpContribInteressi() {
        return impContribInteressi;
    }

    public void setImpContribInteressi(BigDecimal impContribInteressi) {
        this.impContribInteressi = impContribInteressi;
    }

    public BigDecimal getImpFinanzRevocaNoRecu() {
        return impFinanzRevocaNoRecu;
    }

    public void setImpFinanzRevocaNoRecu(BigDecimal impFinanzRevocaNoRecu) {
        this.impFinanzRevocaNoRecu = impFinanzRevocaNoRecu;
    }

    public BigDecimal getImpFinanzRevocaRecu() {
        return impFinanzRevocaRecu;
    }

    public void setImpFinanzRevocaRecu(BigDecimal impFinanzRevocaRecu) {
        this.impFinanzRevocaRecu = impFinanzRevocaRecu;
    }

    public BigDecimal getImpFinanzInteressi() {
        return impFinanzInteressi;
    }

    public void setImpFinanzInteressi(BigDecimal impFinanzInteressi) {
        this.impFinanzInteressi = impFinanzInteressi;
    }

    public BigDecimal getImpFinanzPreRecu() {
        return impFinanzPreRecu;
    }

    public void setImpFinanzPreRecu(BigDecimal impFinanzPreRecu) {
        this.impFinanzPreRecu = impFinanzPreRecu;
    }

    public BigDecimal getImpGaranziaRevocaNoRecu() {
        return impGaranziaRevocaNoRecu;
    }

    public void setImpGaranziaRevocaNoRecu(BigDecimal impGaranziaRevocaNoRecu) {
        this.impGaranziaRevocaNoRecu = impGaranziaRevocaNoRecu;
    }

    public BigDecimal getImpGaranziaRevocaRecu() {
        return impGaranziaRevocaRecu;
    }

    public void setImpGaranziaRevocaRecu(BigDecimal impGaranziaRevocaRecu) {
        this.impGaranziaRevocaRecu = impGaranziaRevocaRecu;
    }

    public BigDecimal getImpGaranziaInteressi() {
        return impGaranziaInteressi;
    }

    public void setImpGaranziaInteressi(BigDecimal impGaranziaInteressi) {
        this.impGaranziaInteressi = impGaranziaInteressi;
    }

    public BigDecimal getImpGaranzPreRecu() {
        return impGaranzPreRecu;
    }

    public void setImpGaranzPreRecu(BigDecimal impGaranzPreRecu) {
        this.impGaranzPreRecu = impGaranzPreRecu;
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
}
