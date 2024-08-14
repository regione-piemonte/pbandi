/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto;

public class FiltroRicercaProgettiNP {
		private String idLineaIntervento;
		private String denominazioneBeneficiario;
		private String codiceProgetto;
		private String nomeBandoLinea;
		private Boolean progettiModificati;
		
		public void setIdLineaIntervento(String val) {
			idLineaIntervento = val;
		}

		public String getIdLineaIntervento() {
			return idLineaIntervento;
		}

		

		public void setDenominazioneBeneficiario(String val) {
			denominazioneBeneficiario = val;
		}

		public String getDenominazioneBeneficiario() {
			return denominazioneBeneficiario;
		}

		

		public void setCodiceProgetto(String val) {
			codiceProgetto = val;
		}

		public String getCodiceProgetto() {
			return codiceProgetto;
		}

	

		public void setNomeBandoLinea(String val) {
			nomeBandoLinea = val;
		}

		public String getNomeBandoLinea() {
			return nomeBandoLinea;
		}

		public void setProgettiModificati(Boolean val) {
			progettiModificati = val;
		}

		public Boolean getProgettiModificati() {
			return progettiModificati;
		}

		public FiltroRicercaProgettiNP() {
			super();

		}

		public String toString() {
			return super.toString();
		}
}
