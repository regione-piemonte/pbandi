/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.datiprogetto;

import java.io.Serializable;
import java.util.Date;

public class Progetto implements Serializable{
		private Long id;
		private String codice;
		private String titolo;

		private Double importoAgevolato;

		private String cup;

		private String acronimo;

		private Date dtConcessione;

		private static final long serialVersionUID = 1L;

		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getCodice() {
			return codice;
		}

		public void setCodice(String codice) {
			this.codice = codice;
		}

		public String getTitolo() {
			return titolo;
		}

		public void setTitolo(String titolo) {
			this.titolo = titolo;
		}

		public Double getImportoAgevolato() {
			return importoAgevolato;
		}

		public void setImportoAgevolato(Double importoAgevolato) {
			this.importoAgevolato = importoAgevolato;
		}

		public String getCup() {
			return cup;
		}

		public void setCup(String cup) {
			this.cup = cup;
		}

		public String getAcronimo() {
			return acronimo;
		}

		public void setAcronimo(String acronimo) {
			this.acronimo = acronimo;
		}

		public Date getDtConcessione() {
			return dtConcessione;
		}

		public void setDtConcessione(Date dtConcessione) {
			this.dtConcessione = dtConcessione;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public Progetto() {
			super();

		}

		public String toString() {
			return super.toString();
		}
}
