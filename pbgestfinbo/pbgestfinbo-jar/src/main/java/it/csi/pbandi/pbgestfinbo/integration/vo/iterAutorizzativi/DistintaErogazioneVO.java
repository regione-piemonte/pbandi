/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi;

import java.util.Date;

public class DistintaErogazioneVO {
    private int idDistinta;
    private int idDistintaDett;

    private int rigaDistinta;

    private int idProgetto;
    private int idBando;
    private Date dataErogazione;
    private double importo;
    private int numRate;
    private int freqRate;
    private String tipoPeriodo;
    private double percentualeInteressi;
    private double importoIres;
    private int idCausaleErogazione; // Aggiunto come nuova specifica di amm.vo cont.
    private int preammortamento;
    private int idModalitaAgevolazione;

    private int idModalitaAgevolazioneRif;
    private int idTipoEscussione; 
    
    private String ibanBonifico; 
    private String causaleBonifico; 

    
    public int getIdCausaleErogazione() {
		return idCausaleErogazione;
	}

	public void setIdCausaleErogazione(int idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}

	public int getIdTipoEscussione() {
		return idTipoEscussione;
	}

	public void setIdTipoEscussione(int idTipoEscussione) {
		this.idTipoEscussione = idTipoEscussione;
	}

	public String getIbanBonifico() {
		return ibanBonifico;
	}

	public void setIbanBonifico(String ibanBonifico) {
		this.ibanBonifico = ibanBonifico;
	}

	public String getCausaleBonifico() {
		return causaleBonifico;
	}

	public void setCausaleBonifico(String causaleBonifico) {
		this.causaleBonifico = causaleBonifico;
	}

	public DistintaErogazioneVO() {
    }

    public int getIdDistinta() {
        return idDistinta;
    }

    public void setIdDistinta(int idDistinta) {
        this.idDistinta = idDistinta;
    }

    public int getIdDistintaDett() {
        return idDistintaDett;
    }

    public void setIdDistintaDett(int idDistintaDett) {
        this.idDistintaDett = idDistintaDett;
    }

    public int getRigaDistinta() {
        return rigaDistinta;
    }

    public void setRigaDistinta(int rigaDistinta) {
        this.rigaDistinta = rigaDistinta;
    }

    public int getIdBando() {
        return idBando;
    }

    public void setIdBando(int idBando) {
        this.idBando = idBando;
    }

    public Date getDataErogazione() {
        return dataErogazione;
    }

    public void setDataErogazione(Date dataErogazione) {
        this.dataErogazione = dataErogazione;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public int getNumRate() {
        return numRate;
    }

    public void setNumRate(int numRate) {
        this.numRate = numRate;
    }

    public int getFreqRate() {
        return freqRate;
    }

    public void setFreqRate(int freqRate) {
        this.freqRate = freqRate;
    }

    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    public double getPercentualeInteressi() {
        return percentualeInteressi;
    }

    public void setPercentualeInteressi(double percentualeInteressi) {
        this.percentualeInteressi = percentualeInteressi;
    }

    public double getImportoIres() {
        return importoIres;
    }

    public void setImportoIres(double importoIres) {
        this.importoIres = importoIres;
    }

    public int getPreammortamento() {
        return preammortamento;
    }

    public void setPreammortamento(int preammortamento) {
        this.preammortamento = preammortamento;
    }

    public int getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(int idProgetto) {
        this.idProgetto = idProgetto;
    }

    public int getIdModalitaAgevolazione() {
        return idModalitaAgevolazione;
    }

    public void setIdModalitaAgevolazione(int idModalitaAgevolazione) {
        this.idModalitaAgevolazione = idModalitaAgevolazione;
    }

    public int getIdModalitaAgevolazioneRif() {
        return idModalitaAgevolazioneRif;
    }

    public void setIdModalitaAgevolazioneRif(int idModalitaAgevolazioneRif) {
        this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
    }
}
