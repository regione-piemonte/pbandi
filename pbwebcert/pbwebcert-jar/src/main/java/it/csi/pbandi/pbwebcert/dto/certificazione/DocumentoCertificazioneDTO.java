/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto.certificazione;

import java.util.Date;

public class DocumentoCertificazioneDTO {
		static final long serialVersionUID = 1;
		private String descTipoDocIndex;
		private Long idDocumentoIndex;
		private String nomeFile;
		private byte[] bytes;
		private String descBreveTipoDocIndex;
		private String noteDocumentoIndex;
		private Date dtInserimentoIndex;
		private String codiceProgettoVisualizzato;
		private Long idProgetto;
		private Long idPropostaCertificaz;
		private String descTipoDocIndexStato;
		private String nomeDocumento;
		
		public void setDescTipoDocIndex(String val) {
			descTipoDocIndex = val;
		}

		public java.lang.String getDescTipoDocIndex() {
			return descTipoDocIndex;
		}

		public void setIdDocumentoIndex(Long val) {
			idDocumentoIndex = val;
		}

		public java.lang.Long getIdDocumentoIndex() {
			return idDocumentoIndex;
		}
		

		public void setNomeFile(String val) {
			nomeFile = val;
		}

		public java.lang.String getNomeFile() {
			return nomeFile;
		}


		public void setBytes(byte[] val) {
			bytes = val;
		}

		public byte[] getBytes() {
			return bytes;
		}	

		public void setDescBreveTipoDocIndex(java.lang.String val) {
			descBreveTipoDocIndex = val;
		}

		public java.lang.String getDescBreveTipoDocIndex() {
			return descBreveTipoDocIndex;
		}


		public void setNoteDocumentoIndex(java.lang.String val) {
			noteDocumentoIndex = val;
		}

		public java.lang.String getNoteDocumentoIndex() {
			return noteDocumentoIndex;
		}

		public void setDtInserimentoIndex(java.util.Date val) {
			dtInserimentoIndex = val;
		}

		public java.util.Date getDtInserimentoIndex() {
			return dtInserimentoIndex;
		}	

		public void setCodiceProgettoVisualizzato(java.lang.String val) {
			codiceProgettoVisualizzato = val;
		}
		public java.lang.String getCodiceProgettoVisualizzato() {
			return codiceProgettoVisualizzato;
		}

		public void setIdPropostaCertificaz(java.lang.Long val) {
			idPropostaCertificaz = val;
		}
		
		public java.lang.Long getIdPropostaCertificaz() {
			return idPropostaCertificaz;
		}

	
		public void setIdProgetto(java.lang.Long val) {
			idProgetto = val;
		}

		public java.lang.Long getIdProgetto() {
			return idProgetto;
		}

		public void setDescTipoDocIndexStato(java.lang.String val) {
			descTipoDocIndexStato = val;
		}

		public java.lang.String getDescTipoDocIndexStato() {
			return descTipoDocIndexStato;
		}

		public String getNomeDocumento() {
			return nomeDocumento;
		}

		public void setNomeDocumento(String nomeDocumento) {
			this.nomeDocumento = nomeDocumento;
		}
}
