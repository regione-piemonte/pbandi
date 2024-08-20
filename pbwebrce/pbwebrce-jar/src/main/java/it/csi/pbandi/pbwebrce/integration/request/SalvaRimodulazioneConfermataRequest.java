/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import it.csi.pbandi.pbwebrce.dto.FinanziamentoFonteFinanziaria;
import it.csi.pbandi.pbwebrce.dto.ModalitaAgevolazione;
import it.csi.pbandi.pbwebrce.util.BeanUtil;


public class SalvaRimodulazioneConfermataRequest {
	
	private Long idProgetto;
	private Long idContoEconomico;
	private Long idSoggettoBeneficiario;
	
	private ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione;
	private ArrayList<FinanziamentoFonteFinanziaria> listaFonti;
	
	private Double importoImpegnoGiuridico;
	private Double importoFinanziamentoBancario;
	private Date dataConcessione;
	private String riferimento;
	private String note;
	
	public Double getImportoFinanziamentoBancario() {
		return importoFinanziamentoBancario;
	}
	public void setImportoFinanziamentoBancario(Double importoFinanziamentoBancario) {
		this.importoFinanziamentoBancario = importoFinanziamentoBancario;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdContoEconomico() {
		return idContoEconomico;
	}
	public void setIdContoEconomico(Long idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}
	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public ArrayList<ModalitaAgevolazione> getListaModalitaAgevolazione() {
		return listaModalitaAgevolazione;
	}
	public void setListaModalitaAgevolazione(ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione) {
		this.listaModalitaAgevolazione = listaModalitaAgevolazione;
	}
	public ArrayList<FinanziamentoFonteFinanziaria> getListaFonti() {
		return listaFonti;
	}
	public void setListaFonti(ArrayList<FinanziamentoFonteFinanziaria> listaFonti) {
		this.listaFonti = listaFonti;
	}
	public Double getImportoImpegnoGiuridico() {
		return importoImpegnoGiuridico;
	}
	public void setImportoImpegnoGiuridico(Double importoImpegnoGiuridico) {
		this.importoImpegnoGiuridico = importoImpegnoGiuridico;
	}
	public Date getDataConcessione() {
		return dataConcessione;
	}
	public void setDataConcessione(Date dataConcessione) {
		this.dataConcessione = dataConcessione;
	}
	public String getRiferimento() {
		return riferimento;
	}
	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				if ("listaModalitaAgevolazione".equalsIgnoreCase(propName)) {
					ArrayList<ModalitaAgevolazione> lista = (ArrayList<ModalitaAgevolazione>) BeanUtil.getPropertyValueByName(this, propName);
					if (lista != null) {
						sb.append("\nlistaModalitaAgevolazione.size :"+lista.size());
					} else {
						sb.append("\nlistaModalitaAgevolazione = null");
					}
				} else if ("fontiFiltrate".equalsIgnoreCase(propName)) {
					ArrayList<FinanziamentoFonteFinanziaria> lista = (ArrayList<FinanziamentoFonteFinanziaria>) BeanUtil.getPropertyValueByName(this, propName);
					if (lista != null) {
						sb.append("\nfontiFiltrate.size :"+lista.size());
					} else {
						sb.append("\nfontiFiltrate = null");
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
