/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiWAttiLiquidazioneDtVO extends GenericVO {

  	
  	private BigDecimal idAttiLiquidazioneDt;
  	
  	private BigDecimal codbenCedente;
  	
  	private String codattivita;
  	
  	private String diratto;
  	
  	private Date dataaggatto;
  	
  	private String abi;
  	
  	private BigDecimal datoinps;
  	
  	private String cap;
  	
  	private BigDecimal codbenCeduto;
  	
  	private String quietanzante;
  	
  	private String viasede;
  	
  	private String testorichmod;
  	
  	private String iban;
  	
  	private String rischioinail;
  	
  	private String tiporecord;
  	
  	private Date dataricatto;
  	
  	private String cab;
  	
  	private BigDecimal progbenCeduto;
  	
  	private String comunesede;
  	
  	private String codaltracassa;
  	
  	private String testoaltro;
  	
  	private String fldichiaraz;
  	
  	private String flagperfis;
  	
  	private String inpsaltracassa;
  	
  	private Date dataemisatto;
  	
  	private String ragsocagg;
  	
  	private String flestrcopiaprov;
  	
  	private Date datarifatto;
  	
  	private String comune;
  	
  	private String via;
  	
  	private String desccodaccre;
  	
  	private String tiporitenuta;
  	
  	private String partiva;
  	
  	private String ragsoc;
  	
  	private String nrocc;
  	
  	private String cin;
  	
  	private BigDecimal nelenco;
  	
  	private String nroatto;
  	
  	private Date dataannulatto;
  	
  	private String annoatto;
  	
  	private String flfatture;
  	
  	private String prov;
  	
  	private String tiposoggetto;
  	
  	private BigDecimal progmodpag;
  	
  	private Date inpsdal;
  	
  	private String fldocgiust;
  	
  	private String note;
  	
  	private String descabi;
  	
  	private String settatto;
  	
  	private Date datarichmod;
  	
  	private BigDecimal importoatto;
  	
  	private BigDecimal aliqirpef;
  	
  	private String codfisc;
  	
  	private String flagOnline;
  	
  	private Date impsal;
  	
  	private String bic;
  	
  	private String provsede;
  	
  	private BigDecimal codben;
  	
  	private BigDecimal irpnonsoggette;
  	
  	private String codfiscquiet;
  	
  	private String desccab;
  	
  	private String capsede;
  	
  	private String descri;
  	
  	private String codaccre;
  	
  	private Date datacomplatto;
  	
  	private Date datascadenza;
  	
  	private BigDecimal stato;
  	
  	private String numeroDocumentoSpesa;
  	
  	private String descStatoDocumento;
  	
	public PbandiWAttiLiquidazioneDtVO() {}
  	
	public PbandiWAttiLiquidazioneDtVO (BigDecimal idAttiLiquidazioneDt) {
	
		this. idAttiLiquidazioneDt =  idAttiLiquidazioneDt;
	}
  	
	public PbandiWAttiLiquidazioneDtVO (BigDecimal idAttiLiquidazioneDt, BigDecimal codbenCedente, String codattivita, String diratto, Date dataaggatto, String abi, BigDecimal datoinps, String cap, BigDecimal codbenCeduto, String quietanzante, String viasede, String testorichmod, String iban, String rischioinail, String tiporecord, Date dataricatto, String cab, BigDecimal progbenCeduto, String comunesede, String codaltracassa, String testoaltro, String fldichiaraz, String flagperfis, String inpsaltracassa, Date dataemisatto, String ragsocagg, String flestrcopiaprov, Date datarifatto, String comune, String via, String desccodaccre, String tiporitenuta, String partiva, String ragsoc, String nrocc, String cin, BigDecimal nelenco, String nroatto, Date dataannulatto, String annoatto, String flfatture, String prov, String tiposoggetto, BigDecimal progmodpag, Date inpsdal, String fldocgiust, String note, String descabi, String settatto, Date datarichmod, BigDecimal importoatto, BigDecimal aliqirpef, String codfisc, String flagOnline, Date impsal, String bic, String provsede, BigDecimal codben, BigDecimal irpnonsoggette, String codfiscquiet, String desccab, String capsede, String descri, String codaccre, Date datacomplatto, Date datascadenza, BigDecimal stato, String descStatoDocumento,String numeroDocumentoSpesa) {
	
		this. idAttiLiquidazioneDt =  idAttiLiquidazioneDt;
		this. codbenCedente =  codbenCedente;
		this. codattivita =  codattivita;
		this. diratto =  diratto;
		this. dataaggatto =  dataaggatto;
		this. abi =  abi;
		this. datoinps =  datoinps;
		this. cap =  cap;
		this. codbenCeduto =  codbenCeduto;
		this. quietanzante =  quietanzante;
		this. viasede =  viasede;
		this. testorichmod =  testorichmod;
		this. iban =  iban;
		this. rischioinail =  rischioinail;
		this. tiporecord =  tiporecord;
		this. dataricatto =  dataricatto;
		this. cab =  cab;
		this. progbenCeduto =  progbenCeduto;
		this. comunesede =  comunesede;
		this. codaltracassa =  codaltracassa;
		this. testoaltro =  testoaltro;
		this. fldichiaraz =  fldichiaraz;
		this. flagperfis =  flagperfis;
		this. inpsaltracassa =  inpsaltracassa;
		this. dataemisatto =  dataemisatto;
		this. ragsocagg =  ragsocagg;
		this. flestrcopiaprov =  flestrcopiaprov;
		this. datarifatto =  datarifatto;
		this. comune =  comune;
		this. via =  via;
		this. desccodaccre =  desccodaccre;
		this. tiporitenuta =  tiporitenuta;
		this. partiva =  partiva;
		this. ragsoc =  ragsoc;
		this. nrocc =  nrocc;
		this. cin =  cin;
		this. nelenco =  nelenco;
		this. nroatto =  nroatto;
		this. dataannulatto =  dataannulatto;
		this. annoatto =  annoatto;
		this. flfatture =  flfatture;
		this. prov =  prov;
		this. tiposoggetto =  tiposoggetto;
		this. progmodpag =  progmodpag;
		this. inpsdal =  inpsdal;
		this. fldocgiust =  fldocgiust;
		this. note =  note;
		this. descabi =  descabi;
		this. settatto =  settatto;
		this. datarichmod =  datarichmod;
		this. importoatto =  importoatto;
		this. aliqirpef =  aliqirpef;
		this. codfisc =  codfisc;
		this. flagOnline =  flagOnline;
		this. impsal =  impsal;
		this. bic =  bic;
		this. provsede =  provsede;
		this. codben =  codben;
		this. irpnonsoggette =  irpnonsoggette;
		this. codfiscquiet =  codfiscquiet;
		this. desccab =  desccab;
		this. capsede =  capsede;
		this. descri =  descri;
		this. codaccre =  codaccre;
		this. datacomplatto =  datacomplatto;
		this. datascadenza =  datascadenza;
		this. stato =  stato;
		this. descStatoDocumento = descStatoDocumento;
		this. numeroDocumentoSpesa = numeroDocumentoSpesa;
	}
  	
  	
	public BigDecimal getIdAttiLiquidazioneDt() {
		return idAttiLiquidazioneDt;
	}
	
	public void setIdAttiLiquidazioneDt(BigDecimal idAttiLiquidazioneDt) {
		this.idAttiLiquidazioneDt = idAttiLiquidazioneDt;
	}
	
	public BigDecimal getCodbenCedente() {
		return codbenCedente;
	}
	
	public void setCodbenCedente(BigDecimal codbenCedente) {
		this.codbenCedente = codbenCedente;
	}
	
	public String getCodattivita() {
		return codattivita;
	}
	
	public void setCodattivita(String codattivita) {
		this.codattivita = codattivita;
	}
	
	public String getDiratto() {
		return diratto;
	}
	
	public void setDiratto(String diratto) {
		this.diratto = diratto;
	}
	
	public Date getDataaggatto() {
		return dataaggatto;
	}
	
	public void setDataaggatto(Date dataaggatto) {
		this.dataaggatto = dataaggatto;
	}
	
	public String getAbi() {
		return abi;
	}
	
	public void setAbi(String abi) {
		this.abi = abi;
	}
	
	public BigDecimal getDatoinps() {
		return datoinps;
	}
	
	public void setDatoinps(BigDecimal datoinps) {
		this.datoinps = datoinps;
	}
	
	public String getCap() {
		return cap;
	}
	
	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public BigDecimal getCodbenCeduto() {
		return codbenCeduto;
	}
	
	public void setCodbenCeduto(BigDecimal codbenCeduto) {
		this.codbenCeduto = codbenCeduto;
	}
	
	public String getQuietanzante() {
		return quietanzante;
	}
	
	public void setQuietanzante(String quietanzante) {
		this.quietanzante = quietanzante;
	}
	
	public String getViasede() {
		return viasede;
	}
	
	public void setViasede(String viasede) {
		this.viasede = viasede;
	}
	
	public String getTestorichmod() {
		return testorichmod;
	}
	
	public void setTestorichmod(String testorichmod) {
		this.testorichmod = testorichmod;
	}
	
	public String getIban() {
		return iban;
	}
	
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	public String getRischioinail() {
		return rischioinail;
	}
	
	public void setRischioinail(String rischioinail) {
		this.rischioinail = rischioinail;
	}
	
	public String getTiporecord() {
		return tiporecord;
	}
	
	public void setTiporecord(String tiporecord) {
		this.tiporecord = tiporecord;
	}
	
	public Date getDataricatto() {
		return dataricatto;
	}
	
	public void setDataricatto(Date dataricatto) {
		this.dataricatto = dataricatto;
	}
	
	public String getCab() {
		return cab;
	}
	
	public void setCab(String cab) {
		this.cab = cab;
	}
	
	public BigDecimal getProgbenCeduto() {
		return progbenCeduto;
	}
	
	public void setProgbenCeduto(BigDecimal progbenCeduto) {
		this.progbenCeduto = progbenCeduto;
	}
	
	public String getComunesede() {
		return comunesede;
	}
	
	public void setComunesede(String comunesede) {
		this.comunesede = comunesede;
	}
	
	public String getCodaltracassa() {
		return codaltracassa;
	}
	
	public void setCodaltracassa(String codaltracassa) {
		this.codaltracassa = codaltracassa;
	}
	
	public String getTestoaltro() {
		return testoaltro;
	}
	
	public void setTestoaltro(String testoaltro) {
		this.testoaltro = testoaltro;
	}
	
	public String getFldichiaraz() {
		return fldichiaraz;
	}
	
	public void setFldichiaraz(String fldichiaraz) {
		this.fldichiaraz = fldichiaraz;
	}
	
	public String getFlagperfis() {
		return flagperfis;
	}
	
	public void setFlagperfis(String flagperfis) {
		this.flagperfis = flagperfis;
	}
	
	public String getInpsaltracassa() {
		return inpsaltracassa;
	}
	
	public void setInpsaltracassa(String inpsaltracassa) {
		this.inpsaltracassa = inpsaltracassa;
	}
	
	public Date getDataemisatto() {
		return dataemisatto;
	}
	
	public void setDataemisatto(Date dataemisatto) {
		this.dataemisatto = dataemisatto;
	}
	
	public String getRagsocagg() {
		return ragsocagg;
	}
	
	public void setRagsocagg(String ragsocagg) {
		this.ragsocagg = ragsocagg;
	}
	
	public String getFlestrcopiaprov() {
		return flestrcopiaprov;
	}
	
	public void setFlestrcopiaprov(String flestrcopiaprov) {
		this.flestrcopiaprov = flestrcopiaprov;
	}
	
	public Date getDatarifatto() {
		return datarifatto;
	}
	
	public void setDatarifatto(Date datarifatto) {
		this.datarifatto = datarifatto;
	}
	
	public String getComune() {
		return comune;
	}
	
	public void setComune(String comune) {
		this.comune = comune;
	}
	
	public String getVia() {
		return via;
	}
	
	public void setVia(String via) {
		this.via = via;
	}
	
	public String getDesccodaccre() {
		return desccodaccre;
	}
	
	public void setDesccodaccre(String desccodaccre) {
		this.desccodaccre = desccodaccre;
	}
	
	public String getTiporitenuta() {
		return tiporitenuta;
	}
	
	public void setTiporitenuta(String tiporitenuta) {
		this.tiporitenuta = tiporitenuta;
	}
	
	public String getPartiva() {
		return partiva;
	}
	
	public void setPartiva(String partiva) {
		this.partiva = partiva;
	}
	
	public String getRagsoc() {
		return ragsoc;
	}
	
	public void setRagsoc(String ragsoc) {
		this.ragsoc = ragsoc;
	}
	
	public String getNrocc() {
		return nrocc;
	}
	
	public void setNrocc(String nrocc) {
		this.nrocc = nrocc;
	}
	
	public String getCin() {
		return cin;
	}
	
	public void setCin(String cin) {
		this.cin = cin;
	}
	
	public BigDecimal getNelenco() {
		return nelenco;
	}
	
	public void setNelenco(BigDecimal nelenco) {
		this.nelenco = nelenco;
	}
	
	public String getNroatto() {
		return nroatto;
	}
	
	public void setNroatto(String nroatto) {
		this.nroatto = nroatto;
	}
	
	public Date getDataannulatto() {
		return dataannulatto;
	}
	
	public void setDataannulatto(Date dataannulatto) {
		this.dataannulatto = dataannulatto;
	}
	
	public String getAnnoatto() {
		return annoatto;
	}
	
	public void setAnnoatto(String annoatto) {
		this.annoatto = annoatto;
	}
	
	public String getFlfatture() {
		return flfatture;
	}
	
	public void setFlfatture(String flfatture) {
		this.flfatture = flfatture;
	}
	
	public String getProv() {
		return prov;
	}
	
	public void setProv(String prov) {
		this.prov = prov;
	}
	
	public String getTiposoggetto() {
		return tiposoggetto;
	}
	
	public void setTiposoggetto(String tiposoggetto) {
		this.tiposoggetto = tiposoggetto;
	}
	
	public BigDecimal getProgmodpag() {
		return progmodpag;
	}
	
	public void setProgmodpag(BigDecimal progmodpag) {
		this.progmodpag = progmodpag;
	}
	
	public Date getInpsdal() {
		return inpsdal;
	}
	
	public void setInpsdal(Date inpsdal) {
		this.inpsdal = inpsdal;
	}
	
	public String getFldocgiust() {
		return fldocgiust;
	}
	
	public void setFldocgiust(String fldocgiust) {
		this.fldocgiust = fldocgiust;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getDescabi() {
		return descabi;
	}
	
	public void setDescabi(String descabi) {
		this.descabi = descabi;
	}
	
	public String getSettatto() {
		return settatto;
	}
	
	public void setSettatto(String settatto) {
		this.settatto = settatto;
	}
	
	public Date getDatarichmod() {
		return datarichmod;
	}
	
	public void setDatarichmod(Date datarichmod) {
		this.datarichmod = datarichmod;
	}
	
	public BigDecimal getImportoatto() {
		return importoatto;
	}
	
	public void setImportoatto(BigDecimal importoatto) {
		this.importoatto = importoatto;
	}
	
	public BigDecimal getAliqirpef() {
		return aliqirpef;
	}
	
	public void setAliqirpef(BigDecimal aliqirpef) {
		this.aliqirpef = aliqirpef;
	}
	
	public String getCodfisc() {
		return codfisc;
	}
	
	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}
	
	public String getFlagOnline() {
		return flagOnline;
	}
	
	public void setFlagOnline(String flagOnline) {
		this.flagOnline = flagOnline;
	}
	
	public Date getImpsal() {
		return impsal;
	}
	
	public void setImpsal(Date impsal) {
		this.impsal = impsal;
	}
	
	public String getBic() {
		return bic;
	}
	
	public void setBic(String bic) {
		this.bic = bic;
	}
	
	public String getProvsede() {
		return provsede;
	}
	
	public void setProvsede(String provsede) {
		this.provsede = provsede;
	}
	
	public BigDecimal getCodben() {
		return codben;
	}
	
	public void setCodben(BigDecimal codben) {
		this.codben = codben;
	}
	
	public BigDecimal getIrpnonsoggette() {
		return irpnonsoggette;
	}
	
	public void setIrpnonsoggette(BigDecimal irpnonsoggette) {
		this.irpnonsoggette = irpnonsoggette;
	}
	
	public String getCodfiscquiet() {
		return codfiscquiet;
	}
	
	public void setCodfiscquiet(String codfiscquiet) {
		this.codfiscquiet = codfiscquiet;
	}
	
	public String getDesccab() {
		return desccab;
	}
	
	public void setDesccab(String desccab) {
		this.desccab = desccab;
	}
	
	public String getCapsede() {
		return capsede;
	}
	
	public void setCapsede(String capsede) {
		this.capsede = capsede;
	}
	
	public String getDescri() {
		return descri;
	}
	
	public void setDescri(String descri) {
		this.descri = descri;
	}
	
	public String getCodaccre() {
		return codaccre;
	}
	
	public void setCodaccre(String codaccre) {
		this.codaccre = codaccre;
	}
	
	public Date getDatacomplatto() {
		return datacomplatto;
	}
	
	public void setDatacomplatto(Date datacomplatto) {
		this.datacomplatto = datacomplatto;
	}
	
	public Date getDatascadenza() {
		return datascadenza;
	}
	
	public void setDatascadenza(Date datascadenza) {
		this.datascadenza = datascadenza;
	}
	
	public BigDecimal getStato() {
		return stato;
	}
	
	public void setStato(BigDecimal stato) {
		this.stato = stato;
	}

	public String getDescStatoDocumento() {
		return descStatoDocumento;
	}

	public void setDescStatoDocumento(String descStatoDocumento) {
		this.descStatoDocumento = descStatoDocumento;
	}

	public String getNumeroDocumentoSpesa() {
		return numeroDocumentoSpesa;
	}

	public void setNumeroDocumentoSpesa(String numeroDocumentoSpesa) {
		this.numeroDocumentoSpesa = numeroDocumentoSpesa;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && flagOnline != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAttiLiquidazioneDt != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttiLiquidazioneDt);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttiLiquidazioneDt: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoatto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoatto: " + temp + "\t\n");

	    temp = DataFilter.removeNull( nroatto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nroatto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( diratto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" diratto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( settatto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" settatto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codben);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codben: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( desccodaccre);
	    if (!DataFilter.isEmpty(temp)) sb.append(" desccodaccre: " + temp + "\t\n");
	
	    temp = DataFilter.removeNull( codaccre);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codaccre: " + temp + "\t\n");	    
	    
	    temp = DataFilter.removeNull( ragsocagg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" ragsocagg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codbenCedente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codbenCedente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codattivita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codattivita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataaggatto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataaggatto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( abi);
	    if (!DataFilter.isEmpty(temp)) sb.append(" abi: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( datoinps);
	    if (!DataFilter.isEmpty(temp)) sb.append(" datoinps: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cap);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cap: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codbenCeduto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codbenCeduto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( quietanzante);
	    if (!DataFilter.isEmpty(temp)) sb.append(" quietanzante: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( viasede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" viasede: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( testorichmod);
	    if (!DataFilter.isEmpty(temp)) sb.append(" testorichmod: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( iban);
	    if (!DataFilter.isEmpty(temp)) sb.append(" iban: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( rischioinail);
	    if (!DataFilter.isEmpty(temp)) sb.append(" rischioinail: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tiporecord);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tiporecord: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataricatto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataricatto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cab);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cab: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progbenCeduto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progbenCeduto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( comunesede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" comunesede: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codaltracassa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codaltracassa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( testoaltro);
	    if (!DataFilter.isEmpty(temp)) sb.append(" testoaltro: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( fldichiaraz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" fldichiaraz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagperfis);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagperfis: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( inpsaltracassa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" inpsaltracassa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataemisatto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataemisatto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flestrcopiaprov);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flestrcopiaprov: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( datarifatto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" datarifatto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( comune);
	    if (!DataFilter.isEmpty(temp)) sb.append(" comune: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( via);
	    if (!DataFilter.isEmpty(temp)) sb.append(" via: " + temp + "\t\n");
	        
	    temp = DataFilter.removeNull( tiporitenuta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tiporitenuta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( partiva);
	    if (!DataFilter.isEmpty(temp)) sb.append(" partiva: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( ragsoc);
	    if (!DataFilter.isEmpty(temp)) sb.append(" ragsoc: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nrocc);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nrocc: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cin);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cin: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nelenco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nelenco: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataannulatto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataannulatto: " + temp + "\t\n");
	    	    
	    temp = DataFilter.removeNull( flfatture);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flfatture: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( prov);
	    if (!DataFilter.isEmpty(temp)) sb.append(" prov: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tiposoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tiposoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progmodpag);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progmodpag: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( inpsdal);
	    if (!DataFilter.isEmpty(temp)) sb.append(" inpsdal: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( fldocgiust);
	    if (!DataFilter.isEmpty(temp)) sb.append(" fldocgiust: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( note);
	    if (!DataFilter.isEmpty(temp)) sb.append(" note: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descabi);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descabi: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( datarichmod);
	    if (!DataFilter.isEmpty(temp)) sb.append(" datarichmod: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoatto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoatto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( aliqirpef);
	    if (!DataFilter.isEmpty(temp)) sb.append(" aliqirpef: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codfisc);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codfisc: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagOnline);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagOnline: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( impsal);
	    if (!DataFilter.isEmpty(temp)) sb.append(" impsal: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( bic);
	    if (!DataFilter.isEmpty(temp)) sb.append(" bic: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( provsede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" provsede: " + temp + "\t\n");
	    	    
	    temp = DataFilter.removeNull( irpnonsoggette);
	    if (!DataFilter.isEmpty(temp)) sb.append(" irpnonsoggette: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codfiscquiet);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codfiscquiet: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( desccab);
	    if (!DataFilter.isEmpty(temp)) sb.append(" desccab: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( capsede);
	    if (!DataFilter.isEmpty(temp)) sb.append(" capsede: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descri);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descri: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( datacomplatto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" datacomplatto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( datascadenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" datascadenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( stato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" stato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descStatoDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroDocumentoSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroDocumentoSpesa: " + temp + "\t\n");
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idAttiLiquidazioneDt");
		
	    return pk;
	}
	
	
}
