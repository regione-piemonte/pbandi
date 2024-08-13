/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.configurazionebandolinea;

import java.io.Serializable;
import java.util.Arrays;

public class ModelloDTO implements Serializable {
	
	private static final long serialVersionUID = -685638013893648738L;
	
	private Long idU;
	private Long progrBandoLineaInterventoOld;      //id modello di documento selezionato
	private Long progrBandoLineaInterventoNew;      //idBandoLinea del bando selezionato dopo la ricerca
	private Long[] elencoIdTipoDocumentoIndex;				//Array contenente gli id dei doucmenti da copiare
	private Long[] elencoIdTipoDocumentoIndexAssociato;
	
	public Long[] getElencoIdTipoDocumentoIndex() {
		return elencoIdTipoDocumentoIndex;
	}
	public void setElencoIdTipoDocumentoIndex(Long[] elencoIdTipoDocumentoIndex) {
		this.elencoIdTipoDocumentoIndex = elencoIdTipoDocumentoIndex;
	}
	public Long[] getElencoIdTipoDocumentoIndexAssociato() {
		return elencoIdTipoDocumentoIndexAssociato;
	}
	public void setElencoIdTipoDocumentoIndexAssociato(Long[] elencoIdTipoDocumentoIndexAssociato) {
		this.elencoIdTipoDocumentoIndexAssociato = elencoIdTipoDocumentoIndexAssociato;
	}
	public Long getIdU() {
		return idU;
	}
	public void setIdU(Long idU) {
		this.idU = idU;
	}
	public Long getProgrBandoLineaInterventoOld() {
		return progrBandoLineaInterventoOld;
	}
	public void setProgrBandoLineaInterventoOld(Long progrBandoLineaInterventoOld) {
		this.progrBandoLineaInterventoOld = progrBandoLineaInterventoOld;
	}
	public Long getProgrBandoLineaInterventoNew() {
		return progrBandoLineaInterventoNew;
	}
	public void setProgrBandoLineaInterventoNew(Long progrBandoLineaInterventoNew) {
		this.progrBandoLineaInterventoNew = progrBandoLineaInterventoNew;
	}
	
	@Override
	public String toString() {
		return "ModelloDTO [idU=" + idU + ", progrBandoLineaInterventoOld=" + progrBandoLineaInterventoOld
				+ ", progrBandoLineaInterventoNew=" + progrBandoLineaInterventoNew + ", elencoIdTipoDocumentoIndex="
				+ Arrays.toString(elencoIdTipoDocumentoIndex) + ", elencoIdTipoDocumentoIndexAssociato="
				+ Arrays.toString(elencoIdTipoDocumentoIndexAssociato) + "]";
	}
	
}
