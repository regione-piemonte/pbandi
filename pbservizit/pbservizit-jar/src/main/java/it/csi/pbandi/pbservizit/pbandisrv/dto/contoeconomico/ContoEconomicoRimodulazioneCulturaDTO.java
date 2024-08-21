/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico;
public class ContoEconomicoRimodulazioneCulturaDTO extends ContoEconomicoRimodulazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private long idVoceDiEntrata;
	private String completamento;
	private String flagEdit;
	private String descVoceDiEntrata;

	private ContoEconomicoRimodulazioneCulturaDTO[] figli = null;

	public ContoEconomicoRimodulazioneCulturaDTO(ContoEconomicoRimodulazioneDTO temp) {
		super();
	}

	public ContoEconomicoRimodulazioneCulturaDTO() {
		super();
	}
	public long getIdVoceDiEntrata() {
		return idVoceDiEntrata;
	}

	public void setIdVoceDiEntrata(long idVoceDiEntrata) {
		this.idVoceDiEntrata = idVoceDiEntrata;
	}

	public String getCompletamento() {
		return completamento;
	}

	public void setCompletamento(String completamento) {
		this.completamento = completamento;
	}

	public String getFlagEdit() {
		return flagEdit;
	}

	public void setFlagEdit(String flagEdit) {
		this.flagEdit = flagEdit;
	}

	public String getDescVoceDiEntrata() {
		return descVoceDiEntrata;
	}

	public void setDescVoceDiEntrata(String descVoceDiEntrata) {
		this.descVoceDiEntrata = descVoceDiEntrata;
	}

	@Override
	public ContoEconomicoRimodulazioneCulturaDTO[] getFigli() {
		return figli;
	}

	public void setFigli(ContoEconomicoRimodulazioneCulturaDTO[] figli) {
		this.figli = figli;
	}


}
