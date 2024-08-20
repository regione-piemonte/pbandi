/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbwebrce.util.BeanUtil;

public class ContoEconomicoItem implements java.io.Serializable {

	private java.lang.String id = null;
	private java.lang.String idPadre = null;
	private long idRigoContoEconomico = 0;
	private Boolean haFigli = false;
	private java.lang.String type = null;
	private int level = 0;
	private java.lang.String label = null;
	private java.lang.String importoRichiestoInDomanda = null;
	private java.lang.String importoRichiestoUltimaProposta = null;
	private java.lang.String importoRichiestoNuovaProposta = null;
	private java.lang.String importoSpesaAmmessaInDetermina = null;
	private java.lang.String importoSpesaAmmessaUltima = null;
	private java.lang.String importoSpesaAmmessaRimodulazione = null;
	private java.lang.String importoSpesaRendicontata = null;
	private java.lang.String importoSpesaQuietanziata = null;
	private java.lang.String percSpesaQuietanziataSuAmmessa = null;
	private java.lang.String importoSpesaValidataTotale = null;
	private java.lang.String percSpesaValidataSuAmmessa = null;
	private Boolean modificabile = false;
	private java.lang.Long idVoce = null;
	private java.lang.String codiceErrore = null;
	private java.lang.String dataUltimaProposta = null;
	private java.lang.String dataFineIstruttoria = null;
	private java.lang.String dataPresentazioneDomanda = null;
	private java.lang.String dataUltimaRimodulazione = null;

	private java.lang.String delta = null;
	
	private static final long serialVersionUID = 1L;

	public ContoEconomicoItem() {
		super();
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(java.lang.String idPadre) {
		this.idPadre = idPadre;
	}

	public long getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}

	public void setIdRigoContoEconomico(long idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}

	public Boolean getHaFigli() {
		return haFigli;
	}

	public void setHaFigli(Boolean haFigli) {
		this.haFigli = haFigli;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public java.lang.String getLabel() {
		return label;
	}

	public void setLabel(java.lang.String label) {
		this.label = label;
	}

	public java.lang.String getImportoRichiestoInDomanda() {
		return importoRichiestoInDomanda;
	}

	public void setImportoRichiestoInDomanda(java.lang.String importoRichiestoInDomanda) {
		this.importoRichiestoInDomanda = importoRichiestoInDomanda;
	}

	public java.lang.String getImportoRichiestoUltimaProposta() {
		return importoRichiestoUltimaProposta;
	}

	public void setImportoRichiestoUltimaProposta(java.lang.String importoRichiestoUltimaProposta) {
		this.importoRichiestoUltimaProposta = importoRichiestoUltimaProposta;
	}

	public java.lang.String getImportoRichiestoNuovaProposta() {
		return importoRichiestoNuovaProposta;
	}

	public void setImportoRichiestoNuovaProposta(java.lang.String importoRichiestoNuovaProposta) {
		this.importoRichiestoNuovaProposta = importoRichiestoNuovaProposta;
	}

	public java.lang.String getImportoSpesaAmmessaInDetermina() {
		return importoSpesaAmmessaInDetermina;
	}

	public void setImportoSpesaAmmessaInDetermina(java.lang.String importoSpesaAmmessaInDetermina) {
		this.importoSpesaAmmessaInDetermina = importoSpesaAmmessaInDetermina;
	}

	public java.lang.String getImportoSpesaAmmessaUltima() {
		return importoSpesaAmmessaUltima;
	}

	public void setImportoSpesaAmmessaUltima(java.lang.String importoSpesaAmmessaUltima) {
		this.importoSpesaAmmessaUltima = importoSpesaAmmessaUltima;
	}

	public java.lang.String getImportoSpesaAmmessaRimodulazione() {
		return importoSpesaAmmessaRimodulazione;
	}

	public void setImportoSpesaAmmessaRimodulazione(java.lang.String importoSpesaAmmessaRimodulazione) {
		this.importoSpesaAmmessaRimodulazione = importoSpesaAmmessaRimodulazione;
	}

	public java.lang.String getImportoSpesaRendicontata() {
		return importoSpesaRendicontata;
	}

	public void setImportoSpesaRendicontata(java.lang.String importoSpesaRendicontata) {
		this.importoSpesaRendicontata = importoSpesaRendicontata;
	}

	public java.lang.String getImportoSpesaQuietanziata() {
		return importoSpesaQuietanziata;
	}

	public void setImportoSpesaQuietanziata(java.lang.String importoSpesaQuietanziata) {
		this.importoSpesaQuietanziata = importoSpesaQuietanziata;
	}

	public java.lang.String getPercSpesaQuietanziataSuAmmessa() {
		return percSpesaQuietanziataSuAmmessa;
	}

	public void setPercSpesaQuietanziataSuAmmessa(java.lang.String percSpesaQuietanziataSuAmmessa) {
		this.percSpesaQuietanziataSuAmmessa = percSpesaQuietanziataSuAmmessa;
	}

	public java.lang.String getImportoSpesaValidataTotale() {
		return importoSpesaValidataTotale;
	}

	public void setImportoSpesaValidataTotale(java.lang.String importoSpesaValidataTotale) {
		this.importoSpesaValidataTotale = importoSpesaValidataTotale;
	}

	public java.lang.String getPercSpesaValidataSuAmmessa() {
		return percSpesaValidataSuAmmessa;
	}

	public void setPercSpesaValidataSuAmmessa(java.lang.String percSpesaValidataSuAmmessa) {
		this.percSpesaValidataSuAmmessa = percSpesaValidataSuAmmessa;
	}

	public Boolean getModificabile() {
		return modificabile;
	}

	public void setModificabile(Boolean modificabile) {
		this.modificabile = modificabile;
	}

	public java.lang.Long getIdVoce() {
		return idVoce;
	}

	public void setIdVoce(java.lang.Long idVoce) {
		this.idVoce = idVoce;
	}

	public java.lang.String getCodiceErrore() {
		return codiceErrore;
	}

	public void setCodiceErrore(java.lang.String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}

	public java.lang.String getDataUltimaProposta() {
		return dataUltimaProposta;
	}

	public void setDataUltimaProposta(java.lang.String dataUltimaProposta) {
		this.dataUltimaProposta = dataUltimaProposta;
	}

	public java.lang.String getDataFineIstruttoria() {
		return dataFineIstruttoria;
	}

	public void setDataFineIstruttoria(java.lang.String dataFineIstruttoria) {
		this.dataFineIstruttoria = dataFineIstruttoria;
	}

	public java.lang.String getDataPresentazioneDomanda() {
		return dataPresentazioneDomanda;
	}

	public void setDataPresentazioneDomanda(java.lang.String dataPresentazioneDomanda) {
		this.dataPresentazioneDomanda = dataPresentazioneDomanda;
	}

	public java.lang.String getDataUltimaRimodulazione() {
		return dataUltimaRimodulazione;
	}

	public void setDataUltimaRimodulazione(java.lang.String dataUltimaRimodulazione) {
		this.dataUltimaRimodulazione = dataUltimaRimodulazione;
	}

	public java.lang.String getDelta() {
		return delta;
	}

	public void setDelta(java.lang.String delta) {
		this.delta = delta;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}
	
}
