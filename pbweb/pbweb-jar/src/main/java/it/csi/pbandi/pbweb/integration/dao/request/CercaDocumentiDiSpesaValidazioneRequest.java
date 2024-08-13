/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.request;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class CercaDocumentiDiSpesaValidazioneRequest {
	
	private Long idDichiarazioneSpesa;
	//private Long idProgetto;
	//private Long idSoggetto;				// Commentato poichè la ricerca in validazione passa null.
	private Long idTipoDocumentoSpesa;
	private Long idTipoFornitore;
	private Date dataDocumento;
	private String numeroDocumento;
	private String cognomeFornitore;
	private String nomeFornitore;
	private String codiceFiscaleFornitore;
	private String partitaIvaFornitore;
	private String denominazioneFornitore;
	private String task;
	private ArrayList<String> statiDocumento;
	
	public Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(Long idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
/*
	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
*/
/*
	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
*/
	public Long getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}

	public void setIdTipoDocumentoSpesa(Long idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}

	public Long getIdTipoFornitore() {
		return idTipoFornitore;
	}

	public void setIdTipoFornitore(Long idTipoFornitore) {
		this.idTipoFornitore = idTipoFornitore;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getCognomeFornitore() {
		return cognomeFornitore;
	}

	public void setCognomeFornitore(String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}

	public String getNomeFornitore() {
		return nomeFornitore;
	}

	public void setNomeFornitore(String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}

	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}

	public String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}

	public void setPartitaIvaFornitore(String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}

	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}

	public void setDenominazioneFornitore(String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public ArrayList<String> getStatiDocumento() {
		return statiDocumento;
	}

	public void setStatiDocumento(ArrayList<String> statiDocumento) {
		this.statiDocumento = statiDocumento;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				if ("statiDocumento".equalsIgnoreCase(propName)) {
					ArrayList<String> lista = (ArrayList<String>) BeanUtil.getPropertyValueByName(this, propName);
					if (lista != null) {
						sb.append("\nstatiDocumento:");
						for (String id : lista) {
							sb.append("\n   stato = "+id);
						}
					} else {
						sb.append("\nstatiDocumento = null");
					}
				} else {
					sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
				}
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}
	
}
