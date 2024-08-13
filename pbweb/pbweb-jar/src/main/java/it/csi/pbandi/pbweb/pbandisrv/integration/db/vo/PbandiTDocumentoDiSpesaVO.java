/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;
import java.sql.Date;


public class PbandiTDocumentoDiSpesaVO extends GenericVO {


  private String destinazioneTrasferta;

  private BigDecimal idTipoOggettoAttivita;

  private BigDecimal progrFornitoreQualifica;

  private BigDecimal imponibile;

  private BigDecimal importoTotaleDocumento;

  private BigDecimal idUtenteIns;

  private BigDecimal idFornitore;

  private Date dtEmissioneDocumento;

  private BigDecimal idDocRiferimento;

  private String numeroDocumento;

  private BigDecimal idTipoDocumentoSpesa;

  private BigDecimal idSoggetto;

  private BigDecimal importoIvaCosto;

  private BigDecimal durataTrasferta;

  private BigDecimal idUtenteAgg;

  private String descDocumento;

  private BigDecimal importoIva;

  private BigDecimal idDocumentoDiSpesa;

  private String flagElettronico;

  private String flagElettXml;

  private BigDecimal idCaricaMassDocSpesa;
	private BigDecimal idParametroCompenso;
	private BigDecimal ggLavorabiliMese;
	private BigDecimal sospBrevi;
	private BigDecimal sospLungheGgTot;
	private BigDecimal sospLungheGgLav;
	private BigDecimal oreMeseLavorate;
	private BigDecimal mese;
	private BigDecimal anno;

  public PbandiTDocumentoDiSpesaVO() {
  }

  public PbandiTDocumentoDiSpesaVO(BigDecimal idDocumentoDiSpesa) {

    this.idDocumentoDiSpesa = idDocumentoDiSpesa;
  }

  public PbandiTDocumentoDiSpesaVO(String destinazioneTrasferta, BigDecimal idTipoOggettoAttivita, BigDecimal progrFornitoreQualifica, BigDecimal imponibile, BigDecimal importoTotaleDocumento, BigDecimal idUtenteIns, BigDecimal idFornitore, Date dtEmissioneDocumento, BigDecimal idDocRiferimento, String numeroDocumento, BigDecimal idTipoDocumentoSpesa, BigDecimal idSoggetto, BigDecimal importoIvaCosto, BigDecimal durataTrasferta, BigDecimal idUtenteAgg, String descDocumento, BigDecimal importoIva, BigDecimal idDocumentoDiSpesa, String flagElettronico, BigDecimal idCaricaMassDocSpesa, String flagElettXml) {

    this.destinazioneTrasferta = destinazioneTrasferta;
    this.idTipoOggettoAttivita = idTipoOggettoAttivita;
    this.progrFornitoreQualifica = progrFornitoreQualifica;
    this.imponibile = imponibile;
    this.importoTotaleDocumento = importoTotaleDocumento;
    this.idUtenteIns = idUtenteIns;
    this.idFornitore = idFornitore;
    this.dtEmissioneDocumento = dtEmissioneDocumento;
    this.idDocRiferimento = idDocRiferimento;
    this.numeroDocumento = numeroDocumento;
    this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
    this.idSoggetto = idSoggetto;
    this.importoIvaCosto = importoIvaCosto;
    this.durataTrasferta = durataTrasferta;
    this.idUtenteAgg = idUtenteAgg;
    this.descDocumento = descDocumento;
    this.importoIva = importoIva;
    this.idDocumentoDiSpesa = idDocumentoDiSpesa;
    this.flagElettronico = flagElettronico;
    this.idCaricaMassDocSpesa = idCaricaMassDocSpesa;
    this.flagElettXml = flagElettXml;
  }


  public String getDestinazioneTrasferta() {
    return destinazioneTrasferta;
  }

  public void setDestinazioneTrasferta(String destinazioneTrasferta) {
    this.destinazioneTrasferta = destinazioneTrasferta;
  }

  public BigDecimal getIdTipoOggettoAttivita() {
    return idTipoOggettoAttivita;
  }

  public void setIdTipoOggettoAttivita(BigDecimal idTipoOggettoAttivita) {
    this.idTipoOggettoAttivita = idTipoOggettoAttivita;
  }

  public BigDecimal getProgrFornitoreQualifica() {
    return progrFornitoreQualifica;
  }

  public void setProgrFornitoreQualifica(BigDecimal progrFornitoreQualifica) {
    this.progrFornitoreQualifica = progrFornitoreQualifica;
  }

  public BigDecimal getImponibile() {
    return imponibile;
  }

  public void setImponibile(BigDecimal imponibile) {
    this.imponibile = imponibile;
  }

  public BigDecimal getImportoTotaleDocumento() {
    return importoTotaleDocumento;
  }

  public void setImportoTotaleDocumento(BigDecimal importoTotaleDocumento) {
    this.importoTotaleDocumento = importoTotaleDocumento;
  }

  public BigDecimal getIdUtenteIns() {
    return idUtenteIns;
  }

  public void setIdUtenteIns(BigDecimal idUtenteIns) {
    this.idUtenteIns = idUtenteIns;
  }

  public BigDecimal getIdFornitore() {
    return idFornitore;
  }

  public void setIdFornitore(BigDecimal idFornitore) {
    this.idFornitore = idFornitore;
  }

  public Date getDtEmissioneDocumento() {
    return dtEmissioneDocumento;
  }

  public void setDtEmissioneDocumento(Date dtEmissioneDocumento) {
    this.dtEmissioneDocumento = dtEmissioneDocumento;
  }

  public BigDecimal getIdDocRiferimento() {
    return idDocRiferimento;
  }

  public void setIdDocRiferimento(BigDecimal idDocRiferimento) {
    this.idDocRiferimento = idDocRiferimento;
  }

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public BigDecimal getIdTipoDocumentoSpesa() {
    return idTipoDocumentoSpesa;
  }

  public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
    this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
  }

  public BigDecimal getIdSoggetto() {
    return idSoggetto;
  }

  public void setIdSoggetto(BigDecimal idSoggetto) {
    this.idSoggetto = idSoggetto;
  }

  public BigDecimal getImportoIvaCosto() {
    return importoIvaCosto;
  }

  public void setImportoIvaCosto(BigDecimal importoIvaCosto) {
    this.importoIvaCosto = importoIvaCosto;
  }

  public BigDecimal getDurataTrasferta() {
    return durataTrasferta;
  }

  public void setDurataTrasferta(BigDecimal durataTrasferta) {
    this.durataTrasferta = durataTrasferta;
  }

  public BigDecimal getIdUtenteAgg() {
    return idUtenteAgg;
  }

  public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
    this.idUtenteAgg = idUtenteAgg;
  }

  public String getDescDocumento() {
    return descDocumento;
  }

  public void setDescDocumento(String descDocumento) {
    this.descDocumento = descDocumento;
  }

  public BigDecimal getImportoIva() {
    return importoIva;
  }

  public void setImportoIva(BigDecimal importoIva) {
    this.importoIva = importoIva;
  }

  public BigDecimal getIdDocumentoDiSpesa() {
    return idDocumentoDiSpesa;
  }

  public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
    this.idDocumentoDiSpesa = idDocumentoDiSpesa;
  }

  public String getFlagElettronico() {
    return flagElettronico;
  }

  public void setFlagElettronico(String flagElettronico) {
    this.flagElettronico = flagElettronico;
  }

  public BigDecimal getIdCaricaMassDocSpesa() {
    return idCaricaMassDocSpesa;
  }

  public void setIdCaricaMassDocSpesa(BigDecimal idCaricaMassDocSpesa) {
    this.idCaricaMassDocSpesa = idCaricaMassDocSpesa;
  }

  public String getFlagElettXml() {
    return flagElettXml;
  }

  public void setFlagElettXml(String flagElettXml) {
    this.flagElettXml = flagElettXml;
  }

	public BigDecimal getIdParametroCompenso() {
		return idParametroCompenso;
	}

	public void setIdParametroCompenso(BigDecimal idParametroCompenso) {
		this.idParametroCompenso = idParametroCompenso;
	}

	public BigDecimal getGgLavorabiliMese() {
		return ggLavorabiliMese;
	}

	public void setGgLavorabiliMese(BigDecimal ggLavorabiliMese) {
		this.ggLavorabiliMese = ggLavorabiliMese;
	}

	public BigDecimal getSospBrevi() {
		return sospBrevi;
	}

	public void setSospBrevi(BigDecimal sospBrevi) {
		this.sospBrevi = sospBrevi;
	}

	public BigDecimal getSospLungheGgTot() {
		return sospLungheGgTot;
	}

	public void setSospLungheGgTot(BigDecimal sospLungheGgTot) {
		this.sospLungheGgTot = sospLungheGgTot;
	}

	public BigDecimal getSospLungheGgLav() {
		return sospLungheGgLav;
	}

	public void setSospLungheGgLav(BigDecimal sospLungheGgLav) {
		this.sospLungheGgLav = sospLungheGgLav;
	}

	public BigDecimal getOreMeseLavorate() {
		return oreMeseLavorate;
	}

	public void setOreMeseLavorate(BigDecimal oreMeseLavorate) {
		this.oreMeseLavorate = oreMeseLavorate;
	}

	public BigDecimal getMese() {
		return mese;
	}

	public void setMese(BigDecimal mese) {
		this.mese = mese;
	}

	public BigDecimal getAnno() {
		return anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

  public boolean isValid() {
    boolean isValid = isPKValid() && importoTotaleDocumento != null && idUtenteIns != null && dtEmissioneDocumento != null && idTipoDocumentoSpesa != null && idSoggetto != null && flagElettronico != null;
		return isValid;
  }

  public boolean isPKValid() {
    boolean isPkValid = idDocumentoDiSpesa != null;

		return isPkValid;
  }

  public String toString() {

    String temp = "";
    StringBuffer sb = new StringBuffer();
    sb.append("\t\n" + this.getClass().getName() + "\t\n");

    temp = DataFilter.removeNull(destinazioneTrasferta);
    if (!DataFilter.isEmpty(temp)) sb.append(" destinazioneTrasferta: " + temp + "\t\n");

    temp = DataFilter.removeNull(idTipoOggettoAttivita);
    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoOggettoAttivita: " + temp + "\t\n");

    temp = DataFilter.removeNull(progrFornitoreQualifica);
    if (!DataFilter.isEmpty(temp)) sb.append(" progrFornitoreQualifica: " + temp + "\t\n");

    temp = DataFilter.removeNull(imponibile);
    if (!DataFilter.isEmpty(temp)) sb.append(" imponibile: " + temp + "\t\n");

    temp = DataFilter.removeNull(importoTotaleDocumento);
    if (!DataFilter.isEmpty(temp)) sb.append(" importoTotaleDocumento: " + temp + "\t\n");

    temp = DataFilter.removeNull(idUtenteIns);
    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");

    temp = DataFilter.removeNull(idFornitore);
    if (!DataFilter.isEmpty(temp)) sb.append(" idFornitore: " + temp + "\t\n");

    temp = DataFilter.removeNull(dtEmissioneDocumento);
    if (!DataFilter.isEmpty(temp)) sb.append(" dtEmissioneDocumento: " + temp + "\t\n");

    temp = DataFilter.removeNull(idDocRiferimento);
    if (!DataFilter.isEmpty(temp)) sb.append(" idDocRiferimento: " + temp + "\t\n");

    temp = DataFilter.removeNull(numeroDocumento);
    if (!DataFilter.isEmpty(temp)) sb.append(" numeroDocumento: " + temp + "\t\n");

    temp = DataFilter.removeNull(idTipoDocumentoSpesa);
    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoSpesa: " + temp + "\t\n");

    temp = DataFilter.removeNull(idSoggetto);
    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");

    temp = DataFilter.removeNull(importoIvaCosto);
    if (!DataFilter.isEmpty(temp)) sb.append(" importoIvaCosto: " + temp + "\t\n");

    temp = DataFilter.removeNull(durataTrasferta);
    if (!DataFilter.isEmpty(temp)) sb.append(" durataTrasferta: " + temp + "\t\n");

    temp = DataFilter.removeNull(idUtenteAgg);
    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");

    temp = DataFilter.removeNull(descDocumento);
    if (!DataFilter.isEmpty(temp)) sb.append(" descDocumento: " + temp + "\t\n");

    temp = DataFilter.removeNull(importoIva);
    if (!DataFilter.isEmpty(temp)) sb.append(" importoIva: " + temp + "\t\n");

    temp = DataFilter.removeNull(idDocumentoDiSpesa);
    if (!DataFilter.isEmpty(temp)) sb.append(" idDocumentoDiSpesa: " + temp + "\t\n");

    temp = DataFilter.removeNull(flagElettronico);
    if (!DataFilter.isEmpty(temp)) sb.append(" flagElettronico: " + temp + "\t\n");

    temp = DataFilter.removeNull(idCaricaMassDocSpesa);
    if (!DataFilter.isEmpty(temp)) sb.append(" idCaricaMassDocSpesa: " + temp + "\t\n");

    temp = DataFilter.removeNull(flagElettXml);
    if (!DataFilter.isEmpty(temp)) sb.append(" flagElettXml: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idParametroCompenso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idParametroCompenso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( ggLavorabiliMese);
	    if (!DataFilter.isEmpty(temp)) sb.append(" ggLavorabiliMese: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( sospBrevi);
	    if (!DataFilter.isEmpty(temp)) sb.append(" sospBrevi: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( sospLungheGgTot);
	    if (!DataFilter.isEmpty(temp)) sb.append(" sospLungheGgTot: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( sospLungheGgLav);
	    if (!DataFilter.isEmpty(temp)) sb.append(" sospLungheGgLav: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( oreMeseLavorate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" oreMeseLavorate: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( mese);
	    if (!DataFilter.isEmpty(temp)) sb.append(" mese: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( anno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" anno: " + temp + "\t\n");

    return sb.toString();
  }

  public java.util.List getPK() {
    java.util.List pk = new java.util.ArrayList();

    pk.add("idDocumentoDiSpesa");

    return pk;
  }


}
