/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.quadroprevisionale;

public class QuadroPrevisionaleItem implements java.io.Serializable {

	/// Field [id]
	private String id = null;

	public void setId(String val) {
		id = val;
	}

	public String getId() {
		return id;
	}

	/// Field [idPadre]
	private String idPadre = null;

	public void setIdPadre(String val) {
		idPadre = val;
	}

	public String getIdPadre() {
		return idPadre;
	}

	/// Field [idRigoQuadro]
	private long idRigoQuadro = 0;

	public void setIdRigoQuadro(long val) {
		idRigoQuadro = val;
	}

	public long getIdRigoQuadro() {
		return idRigoQuadro;
	}

	/// Field [haFigli]
	private boolean haFigli = false;

	public void setHaFigli(boolean val) {
		haFigli = val;
	}

	public boolean getHaFigli() {
		return haFigli;
	}

	/// Field [level]
	private int level = 0;

	public void setLevel(int val) {
		level = val;
	}

	public int getLevel() {
		return level;
	}

	/// Field [label]
	private String label = null;

	public void setLabel(String val) {
		label = val;
	}

	public String getLabel() {
		return label;
	}

	/// Field [ultimoPreventivo]
	private String ultimoPreventivo = null;

	public void setUltimoPreventivo(String val) {
		ultimoPreventivo = val;
	}

	public String getUltimoPreventivo() {
		return ultimoPreventivo;
	}

	/// Field [nuovoPreventivo]
	private String nuovoPreventivo = null;

	public void setNuovoPreventivo(String val) {
		nuovoPreventivo = val;
	}

	public String getNuovoPreventivo() {
		return nuovoPreventivo;
	}

	/// Field [realizzato]
	private String realizzato = null;

	public void setRealizzato(String val) {
		realizzato = val;
	}

	public String getRealizzato() {
		return realizzato;
	}

	/// Field [daRealizzare]
	private String daRealizzare = null;

	public void setDaRealizzare(String val) {
		daRealizzare = val;
	}

	public String getDaRealizzare() {
		return daRealizzare;
	}

	/// Field [modificabile]
	private boolean modificabile = false;

	public void setModificabile(boolean val) {
		modificabile = val;
	}

	public boolean getModificabile() {
		return modificabile;
	}

	/// Field [idVoce]
	private Long idVoce = null;

	public void setIdVoce(Long val) {
		idVoce = val;
	}

	public Long getIdVoce() {
		return idVoce;
	}

	/// Field [codiceErrore]
	private String codiceErrore = null;

	public void setCodiceErrore(String val) {
		codiceErrore = val;
	}

	public String getCodiceErrore() {
		return codiceErrore;
	}

	/// Field [periodo]
	private String periodo = null;

	public void setPeriodo(String val) {
		periodo = val;
	}

	public String getPeriodo() {
		return periodo;
	}

	/// Field [isPeriodo]
	private boolean isPeriodo = false;

	public void setIsPeriodo(boolean val) {
		isPeriodo = val;
	}

	public boolean getIsPeriodo() {
		return isPeriodo;
	}

	/// Field [isMacroVoce]
	private boolean isMacroVoce = false;

	public void setIsMacroVoce(boolean val) {
		isMacroVoce = val;
	}

	public boolean getIsMacroVoce() {
		return isMacroVoce;
	}

	/// Field [isRigaDate]
	private boolean isRigaDate = false;

	public void setIsRigaDate(boolean val) {
		isRigaDate = val;
	}

	public boolean getIsRigaDate() {
		return isRigaDate;
	}

	/// Field [isRigaRibaltamento]
	private boolean isRigaRibaltamento = false;

	public void setIsRigaRibaltamento(boolean val) {
		isRigaRibaltamento = val;
	}

	public boolean getIsRigaRibaltamento() {
		return isRigaRibaltamento;
	}

	/// Field [dataUltimaPreventivo]
	private String dataUltimaPreventivo = null;

	public void setDataUltimaPreventivo(String val) {
		dataUltimaPreventivo = val;
	}

	public String getDataUltimaPreventivo() {
		return dataUltimaPreventivo;
	}

	/// Field [dataNuovoPreventivo]
	private String dataNuovoPreventivo = null;

	public void setDataNuovoPreventivo(String val) {
		dataNuovoPreventivo = val;
	}

	public String getDataNuovoPreventivo() {
		return dataNuovoPreventivo;
	}

	/// Field [ultimaSpesaAmmessa]
	private String ultimaSpesaAmmessa = null;

	public void setUltimaSpesaAmmessa(String val) {
		ultimaSpesaAmmessa = val;
	}

	public String getUltimaSpesaAmmessa() {
		return ultimaSpesaAmmessa;
	}

	/// Field [isVociVisibili]
	private boolean isVociVisibili = false;

	public void setIsVociVisibili(boolean val) {
		isVociVisibili = val;
	}

	public boolean getIsVociVisibili() {
		return isVociVisibili;
	}

	/// Field [isRigaTotale]
	private boolean isRigaTotale = false;

	public void setIsRigaTotale(boolean val) {
		isRigaTotale = val;
	}

	public boolean getIsRigaTotale() {
		return isRigaTotale;
	}

	/// Field [isError]
	private boolean isError = false;

	public void setIsError(boolean val) {
		isError = val;
	}

	public boolean getIsError() {
		return isError;
	}

	// il serial version uid e' fisso in quanto la classe in oggetto e' serializzabile
	// solo per la clusterizzazione della sessione web e non viene scambiata con altre
	// componenti.
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore vuoto del DTO.
	 */
	public QuadroPrevisionaleItem() {
		super();

	}

	public String toString() {
		/*PROTECTED REGION ID(R-1500866670) ENABLED START*/
		/// inserire qui la logica desiderata per la rappresenatazione a stringa
		return super.toString();
		/*PROTECTED REGION END*/
	}
}
