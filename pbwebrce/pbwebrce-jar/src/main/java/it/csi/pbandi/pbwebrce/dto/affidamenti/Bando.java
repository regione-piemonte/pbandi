/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

import java.io.Serializable;

public class Bando implements Serializable{
		private Long idBando;

		private String descrizione;

		private String titolo;

		private static final long serialVersionUID = 1L;

		
	

		public Long getIdBando() {
			return idBando;
		}

		public void setIdBando(Long idBando) {
			this.idBando = idBando;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		public String getTitolo() {
			return titolo;
		}

		public void setTitolo(String titolo) {
			this.titolo = titolo;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public Bando() {
			super();

		}

		public String toString() {		
			return super.toString();
		}
}
