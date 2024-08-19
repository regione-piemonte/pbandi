/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.contoeconomico;

public class ContoEconomicoItem implements java.io.Serializable {

	private String id;
	private String idPadre;
	private boolean haFigli = false;
	private String type;
	private int level = 0;
	private String label;
	private Double importoSpesaAmmessa;
	private Double importoSpesaRendicontata;
	private Double importoSpesaQuietanziata;
	private Double percentualeSpesaQuietanziataSuAmmessa;
	private Double importoSpesaValidataTotale;
	private Double percentualeSpesaValidataSuAmmessa;
	private String idTipologia;
	private static final long serialVersionUID = 1L;

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public boolean isHaFigli() {
		return haFigli;
	}

	public void setHaFigli(boolean haFigli) {
		this.haFigli = haFigli;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Double getImportoSpesaAmmessa() {
		return importoSpesaAmmessa;
	}

	public void setImportoSpesaAmmessa(Double importoSpesaAmmessa) {
		this.importoSpesaAmmessa = importoSpesaAmmessa;
	}

	public Double getImportoSpesaRendicontata() {
		return importoSpesaRendicontata;
	}

	public void setImportoSpesaRendicontata(Double importoSpesaRendicontata) {
		this.importoSpesaRendicontata = importoSpesaRendicontata;
	}

	public Double getImportoSpesaQuietanziata() {
		return importoSpesaQuietanziata;
	}

	public void setImportoSpesaQuietanziata(Double importoSpesaQuietanziata) {
		this.importoSpesaQuietanziata = importoSpesaQuietanziata;
	}

	public Double getPercentualeSpesaQuietanziataSuAmmessa() {
		return percentualeSpesaQuietanziataSuAmmessa;
	}

	public void setPercentualeSpesaQuietanziataSuAmmessa(Double percentualeSpesaQuietanziataSuAmmessa) {
		this.percentualeSpesaQuietanziataSuAmmessa = percentualeSpesaQuietanziataSuAmmessa;
	}

	public Double getImportoSpesaValidataTotale() {
		return importoSpesaValidataTotale;
	}

	public void setImportoSpesaValidataTotale(Double importoSpesaValidataTotale) {
		this.importoSpesaValidataTotale = importoSpesaValidataTotale;
	}

	public Double getPercentualeSpesaValidataSuAmmessa() {
		return percentualeSpesaValidataSuAmmessa;
	}

	public void setPercentualeSpesaValidataSuAmmessa(Double percentualeSpesaValidataSuAmmessa) {
		this.percentualeSpesaValidataSuAmmessa = percentualeSpesaValidataSuAmmessa;
	}

	public String getIdTipologia() {
		return idTipologia;
	}

	public void setIdTipologia(String idTipologia) {
		this.idTipologia = idTipologia;
	}

	public ContoEconomicoItem() {
		super();

	}

	public String toString() {
		return ("id = " + id + "; idPadre = " + idPadre + "; idTipologia = "
				+ idTipologia + "; level = " + level + "; haFigli = "
				+ haFigli + "; label = " + label + "; importoSpesaAmmessa = " + importoSpesaAmmessa);
	}
}
