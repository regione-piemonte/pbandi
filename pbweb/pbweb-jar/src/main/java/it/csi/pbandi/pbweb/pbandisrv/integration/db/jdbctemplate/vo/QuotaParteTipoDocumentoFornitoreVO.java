/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTQuotaParteDocSpesaVO;

public class QuotaParteTipoDocumentoFornitoreVO extends
		PbandiTQuotaParteDocSpesaVO {
	
	private String descBreveTipoDocSpesa;
	private Long idTipoDocumentoSpesa;
	private Long idFornitore;
	
	public void setDescBreveTipoDocSpesa(String descBreveTipoDocSpesa) {
		this.descBreveTipoDocSpesa = descBreveTipoDocSpesa;
	}
	public String getDescBreveTipoDocSpesa() {
		return descBreveTipoDocSpesa;
	}
	public void setIdTipoDocumentoSpesa(Long idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}
	public Long getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}
	public void setIdFornitore(Long idFornitore) {
		this.idFornitore = idFornitore;
	}
	public Long getIdFornitore() {
		return idFornitore;
	}
	
	
	

}
