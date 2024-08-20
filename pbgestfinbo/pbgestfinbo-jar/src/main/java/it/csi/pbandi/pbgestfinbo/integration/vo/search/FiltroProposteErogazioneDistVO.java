/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.sql.Date;

public class FiltroProposteErogazioneDistVO {
    private Long idBando;
    private Long idModalitaAgevolazione;
    private Date dtValidazioneSpesaFrom;
    private Date dtValidazioneSpesaTo;
    private Date dtConcessioneFrom;
    private Date dtConcessioneTo;
    private String codiceFondoFinpis;
    private String flagFinistr; 

    private String denominazione;

    private String codiceProgetto;

    
    
    public String getFlagFinistr() {
		return flagFinistr;
	}

	public void setFlagFinistr(String flagFinistr) {
		this.flagFinistr = flagFinistr;
	}

	public FiltroProposteErogazioneDistVO() {
    }

    public Long getIdBando() {
        return idBando;
    }

    public void setIdBando(Long idBando) {
        this.idBando = idBando;
    }

    public Long getIdModalitaAgevolazione() {
        return idModalitaAgevolazione;
    }

    public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
        this.idModalitaAgevolazione = idModalitaAgevolazione;
    }

    public Date getDtValidazioneSpesaFrom() {
        return dtValidazioneSpesaFrom;
    }

    public void setDtValidazioneSpesaFrom(Date dtValidazioneSpesaFrom) {
        this.dtValidazioneSpesaFrom = dtValidazioneSpesaFrom;
    }

    public Date getDtValidazioneSpesaTo() {
        return dtValidazioneSpesaTo;
    }

    public void setDtValidazioneSpesaTo(Date dtValidazioneSpesaTo) {
        this.dtValidazioneSpesaTo = dtValidazioneSpesaTo;
    }

    public Date getDtConcessioneFrom() {
        return dtConcessioneFrom;
    }

    public void setDtConcessioneFrom(Date dtConcessioneFrom) {
        this.dtConcessioneFrom = dtConcessioneFrom;
    }

    public Date getDtConcessioneTo() {
        return dtConcessioneTo;
    }

    public void setDtConcessioneTo(Date dtConcessioneTo) {
        this.dtConcessioneTo = dtConcessioneTo;
    }

    public String getCodiceFondoFinpis() {
        return codiceFondoFinpis;
    }

    public void setCodiceFondoFinpis(String codiceFondoFinpis) {
        this.codiceFondoFinpis = codiceFondoFinpis;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getCodiceProgetto() {
        return codiceProgetto;
    }

    public void setCodiceProgetto(String codiceProgetto) {
        this.codiceProgetto = codiceProgetto;
    }
}
