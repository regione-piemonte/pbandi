/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.registrocontrolli;

import java.io.File;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.IrregolaritaDTO;

public class ModificaIrregolaritaDefinitiva implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idU;
	private boolean modificaDatiAggiuntivi;
	private String strOldFileName;
	private IrregolaritaDTO irregolarita;
	private File olaf;
	private File zipFile;
	
	public Long getIdU() {
		return idU;
	}
	public void setIdU(Long idU) {
		this.idU = idU;
	}
	public boolean isModificaDatiAggiuntivi() {
		return modificaDatiAggiuntivi;
	}
	public void setModificaDatiAggiuntivi(boolean modificaDatiAggiuntivi) {
		this.modificaDatiAggiuntivi = modificaDatiAggiuntivi;
	}
	public String getStrOldFileName() {
		return strOldFileName;
	}
	public void setStrOldFileName(String strOldFileName) {
		this.strOldFileName = strOldFileName;
	}
	public IrregolaritaDTO getIrregolarita() {
		return irregolarita;
	}
	public void setIrregolarita(IrregolaritaDTO irregolarita) {
		this.irregolarita = irregolarita;
	}
	
	@Override
	public String toString() {
		ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this);
		builder.append("idU", idU);
		builder.append("modificaDatiAggiuntivi", modificaDatiAggiuntivi);
		builder.append("strOldFileName", strOldFileName);
		builder.append("irregolarita", irregolarita);
		return builder.toString();
	}
	public File getOlaf() {
		return olaf;
	}
	public void setOlaf(File olaf) {
		this.olaf = olaf;
	}
	public File getZipFile() {
		return zipFile;
	}
	public void setZipFile(File zipFile) {
		this.zipFile = zipFile;
	}
	


	

}
