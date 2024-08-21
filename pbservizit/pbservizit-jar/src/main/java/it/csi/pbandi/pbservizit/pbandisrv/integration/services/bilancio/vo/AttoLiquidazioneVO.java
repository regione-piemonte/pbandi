/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;


public class AttoLiquidazioneVO {


	private FornitoreVO fornitore;
	
	private BeneficiarioVO beneficiario;
	
	private AttoVO atto;
	
	private DettaglioConsultazioneAttoVO[] dettagliAtto;
	
	public FornitoreVO getFornitore() {
		return fornitore;
	}

	public void setFornitore(FornitoreVO fornitore) {
		this.fornitore = fornitore;
	}

	public BeneficiarioVO getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(BeneficiarioVO beneficiario) {
		this.beneficiario = beneficiario;
	}

	public AttoVO getAtto() {
		return atto;
	}

	public void setAtto(AttoVO atto) {
		this.atto = atto;
	}

	public DettaglioConsultazioneAttoVO[] getDettagliAtto() {
		return dettagliAtto;
	}

	public void setDettagliAtto(DettaglioConsultazioneAttoVO[] dettagliAtto) {
		this.dettagliAtto = dettagliAtto;
	}




	
}
