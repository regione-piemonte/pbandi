/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.request;

public class AmmettiESospendiProgettiRequest {
	private Long[] idsPreviewDettPropCer;

	public Long[] getIdsPreviewDettPropCer() {
		return idsPreviewDettPropCer;
	}

	public void setIdsPreviewDettPropCer(Long[] idsPreviewDettPropCer) {
		this.idsPreviewDettPropCer = idsPreviewDettPropCer;
	}
}
