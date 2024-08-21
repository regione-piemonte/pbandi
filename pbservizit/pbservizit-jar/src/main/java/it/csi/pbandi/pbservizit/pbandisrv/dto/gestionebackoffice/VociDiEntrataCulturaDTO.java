/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice;

import it.csi.pbandi.pbservizit.dto.ContoEconomico;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ContoEconomicoDTO;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VociDiEntrataCulturaDTO implements java.io.Serializable {

  static final long serialVersionUID = 2L;

	static Logger log = Logger.getLogger(VociDiEntrataCulturaDTO.class);
	List<VoceDiEntrataCulturaDTO> vociDiEntrataCultura;


	public List<VoceDiEntrataCulturaDTO> getVociDiEntrataCultura() {
		return vociDiEntrataCultura;
	}

	public void setVociDiEntrataCultura(List<VoceDiEntrataCulturaDTO> vociDiEntrataCultura) {
		this.vociDiEntrataCultura = vociDiEntrataCultura;
	}
}