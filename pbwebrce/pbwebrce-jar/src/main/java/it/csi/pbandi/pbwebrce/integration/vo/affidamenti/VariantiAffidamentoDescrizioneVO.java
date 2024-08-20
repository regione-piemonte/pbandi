/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.vo.affidamenti;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTVariantiAffidamentiVO;

public class VariantiAffidamentoDescrizioneVO  extends PbandiTVariantiAffidamentiVO{
	private String descrizioneTipologiaVariante;

	public String getDescrizioneTipologiaVariante() {
		return descrizioneTipologiaVariante;
	}

	public void setDescrizioneTipologiaVariante(String descrizioneTipologiaVariante) {
		this.descrizioneTipologiaVariante = descrizioneTipologiaVariante;
	}
}