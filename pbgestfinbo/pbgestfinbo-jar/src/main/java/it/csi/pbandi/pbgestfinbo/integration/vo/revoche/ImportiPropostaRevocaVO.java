/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;


import java.io.Serializable;
import java.util.Date;

public class ImportiPropostaRevocaVO implements Serializable {

	private Integer idGestioneRevoca;
	private Integer idProgetto;
	private Integer idDomanda;
	private Integer idBando;

	private Integer idStatoRevoca;
	private Date dtStatoRevoca;

	private Integer idModalitaAgevolazione;
	private Integer idModalitaAgevolazioneRif;
	private String descModalitaAgevolazione;

	private Double importoConcesso;
	private Double importoRevocato;
	private Double importoConcessoAlNettoRevocato;

	private Double importoErogato;
	private Double importoRecuperato;
	private Double importoErogatoAlNettoRecuperato;

	private Double importoRimborsato;
	private Double importoErogatoAlNettoRecuperatoRimborsato;

	private Double importoAmmessoIniziale;

	public ImportiPropostaRevocaVO() {
		super();
	}

	public Integer getIdGestioneRevoca() {
		return idGestioneRevoca;
	}

	public void setIdGestioneRevoca(Integer idGestioneRevoca) {
		this.idGestioneRevoca = idGestioneRevoca;
	}

	public Integer getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Integer idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Integer getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(Integer idDomanda) {
		this.idDomanda = idDomanda;
	}

	public Integer getIdBando() {
		return idBando;
	}

	public void setIdBando(Integer idBando) {
		this.idBando = idBando;
	}

	public Integer getIdStatoRevoca() {
		return idStatoRevoca;
	}

	public void setIdStatoRevoca(Integer idStatoRevoca) {
		this.idStatoRevoca = idStatoRevoca;
	}

	public Date getDtStatoRevoca() {
		return dtStatoRevoca;
	}

	public void setDtStatoRevoca(Date dtStatoRevoca) {
		this.dtStatoRevoca = dtStatoRevoca;
	}

	public Integer getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}

	public void setIdModalitaAgevolazione(Integer idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}

	public Integer getIdModalitaAgevolazioneRif() {
		return idModalitaAgevolazioneRif;
	}

	public void setIdModalitaAgevolazioneRif(Integer idModalitaAgevolazioneRif) {
		this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
	}

	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}

	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}

	public Double getImportoConcesso() {
		return importoConcesso;
	}

	public void setImportoConcesso(Double importoConcesso) {
		this.importoConcesso = importoConcesso;
	}

	public Double getImportoRevocato() {
		return importoRevocato;
	}

	public void setImportoRevocato(Double importoRevocato) {
		this.importoRevocato = importoRevocato;
	}

	public Double getImportoConcessoAlNettoRevocato() {
		return importoConcessoAlNettoRevocato;
	}

	public void setImportoConcessoAlNettoRevocato() {
		if(importoConcesso == null){
			importoConcessoAlNettoRevocato = null;
		}else {
			if (importoRevocato != null) {
				this.importoConcessoAlNettoRevocato = importoConcesso - importoRevocato;
			} else {
				this.importoConcessoAlNettoRevocato = importoConcesso;
			}
		}
	}

	public Double getImportoErogato() {
		return importoErogato;
	}

	public void setImportoErogato(Double importoErogato) {
		this.importoErogato = importoErogato;
	}

	public Double getImportoRecuperato() {
		return importoRecuperato;
	}

	public void setImportoRecuperato(Double importoRecuperato) {
		this.importoRecuperato = importoRecuperato;
	}

	public Double getImportoErogatoAlNettoRecuperato() {
		return importoErogatoAlNettoRecuperato;
	}

	public void setImportoErogatoAlNettoRecuperato() {
		if(importoErogato == null){
			importoErogatoAlNettoRecuperato = null;
		}
		else {
			if (importoRecuperato != null) {
				this.importoErogatoAlNettoRecuperato = importoErogato - importoRecuperato;
			} else {
				this.importoErogatoAlNettoRecuperato = importoErogato;
			}
		}
	}

	public Double getImportoRimborsato() {
		return importoRimborsato;
	}

	public void setImportoRimborsato(Double importoRimborsato) {
		this.importoRimborsato = importoRimborsato;
	}

	public Double getImportoErogatoAlNettoRecuperatoRimborsato() {
		return importoErogatoAlNettoRecuperatoRimborsato;
	}

	public void setImportoErogatoAlNettoRecuperatoRimborsato() {
		if(importoErogato == null){
			importoErogatoAlNettoRecuperatoRimborsato = null;
		}else {
			this.importoErogatoAlNettoRecuperatoRimborsato = importoErogato;
			if (importoRecuperato != null) {
				this.importoErogatoAlNettoRecuperatoRimborsato -= importoRecuperato;
			}
			if (importoRimborsato != null) {
				this.importoErogatoAlNettoRecuperatoRimborsato -= importoRimborsato;
			}
		}
	}

	public Double getImportoAmmessoIniziale() {
		return importoAmmessoIniziale;
	}

	public void setImportoAmmessoIniziale(Double importoAmmessoIniziale) {
		this.importoAmmessoIniziale = importoAmmessoIniziale;
	}
}
