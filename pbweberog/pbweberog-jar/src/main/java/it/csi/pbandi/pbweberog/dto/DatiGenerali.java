/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import it.csi.pbandi.pbweberog.dto.Bando;
import it.csi.pbandi.pbweberog.dto.DatiCumuloDeMinimis;
import it.csi.pbandi.pbweberog.dto.ImportoAgevolato;
import it.csi.pbandi.pbweberog.dto.ImportoDescrizione;

public class DatiGenerali implements Serializable{
		private Progetto progetto;

		private Bando bando;

		private Beneficiario beneficiario;

		private String descrizioneBreve;

		private String codiceDichiarazione;

		private Date dataPresentazione;

		private ArrayList<ImportoAgevolato> importiAgevolati = new ArrayList<ImportoAgevolato>();

		private Double importoCertificatoNettoUltimaPropAppr;

		private Double importoValidatoNettoRevoche;

		private Double importoSoppressioni;

		private String flagImportoCertificatoNettoVisibile;

		private ArrayList<ImportoDescrizione> erogazioni = new ArrayList<ImportoDescrizione>();

		private ArrayList<ImportoDescrizione> preRecuperi = new ArrayList<ImportoDescrizione>();


		private ArrayList<ImportoDescrizione> recuperi = new ArrayList<ImportoDescrizione>();

		private ArrayList<ImportoDescrizione> revoche = new ArrayList<ImportoDescrizione>();

		private Double importoRendicontato;

		private Double importoQuietanzato;

		private Double importoCofinanziamentoPrivato;

		private Double importoCofinanziamentoPubblico;

		private Double importoAmmesso;

		private DatiCumuloDeMinimis datiCumuloDeMinimis;

		private Double importoImpegno;

		public void setImportoImpegno(Double val) {
			importoImpegno = val;
		}

		/// Field [importoResiduoAmmesso]
		private String importoResiduoAmmesso;


		/// Field [isLegatoBilancio]
		private java.lang.Boolean isLegatoBilancio;


		/// Field [economie]
		private ArrayList<EconomiaPerDatiGenerali> economie = new ArrayList<EconomiaPerDatiGenerali>();

		private static final long serialVersionUID = 1L;
		
		
		
		
		
		public Progetto getProgetto() {
			return progetto;
		}

		public void setProgetto(Progetto progetto) {
			this.progetto = progetto;
		}

		public Bando getBando() {
			return bando;
		}

		public void setBando(Bando bando) {
			this.bando = bando;
		}

		public Beneficiario getBeneficiario() {
			return beneficiario;
		}

		public void setBeneficiario(Beneficiario beneficiario) {
			this.beneficiario = beneficiario;
		}

		public String getDescrizioneBreve() {
			return descrizioneBreve;
		}

		public void setDescrizioneBreve(String descrizioneBreve) {
			this.descrizioneBreve = descrizioneBreve;
		}

		public String getCodiceDichiarazione() {
			return codiceDichiarazione;
		}

		public void setCodiceDichiarazione(String codiceDichiarazione) {
			this.codiceDichiarazione = codiceDichiarazione;
		}

		public Date getDataPresentazione() {
			return dataPresentazione;
		}

		public void setDataPresentazione(Date dataPresentazione) {
			this.dataPresentazione = dataPresentazione;
		}

		public ArrayList<ImportoAgevolato> getImportiAgevolati() {
			return importiAgevolati;
		}

		public void setImportiAgevolati(ArrayList<ImportoAgevolato> importiAgevolati) {
			this.importiAgevolati = importiAgevolati;
		}

		public Double getImportoCertificatoNettoUltimaPropAppr() {
			return importoCertificatoNettoUltimaPropAppr;
		}

		public void setImportoCertificatoNettoUltimaPropAppr(Double importoCertificatoNettoUltimaPropAppr) {
			this.importoCertificatoNettoUltimaPropAppr = importoCertificatoNettoUltimaPropAppr;
		}

		public Double getImportoValidatoNettoRevoche() {
			return importoValidatoNettoRevoche;
		}

		public void setImportoValidatoNettoRevoche(Double importoValidatoNettoRevoche) {
			this.importoValidatoNettoRevoche = importoValidatoNettoRevoche;
		}

		public Double getImportoSoppressioni() {
			return importoSoppressioni;
		}

		public void setImportoSoppressioni(Double importoSoppressioni) {
			this.importoSoppressioni = importoSoppressioni;
		}

		public String getFlagImportoCertificatoNettoVisibile() {
			return flagImportoCertificatoNettoVisibile;
		}

		public void setFlagImportoCertificatoNettoVisibile(String flagImportoCertificatoNettoVisibile) {
			this.flagImportoCertificatoNettoVisibile = flagImportoCertificatoNettoVisibile;
		}

		public ArrayList<ImportoDescrizione> getErogazioni() {
			return erogazioni;
		}

		public void setErogazioni(ArrayList<ImportoDescrizione> erogazioni) {
			this.erogazioni = erogazioni;
		}

		public ArrayList<ImportoDescrizione> getPreRecuperi() {
			return preRecuperi;
		}

		public void setPreRecuperi(ArrayList<ImportoDescrizione> preRecuperi) {
			this.preRecuperi = preRecuperi;
		}

		public ArrayList<ImportoDescrizione> getRecuperi() {
			return recuperi;
		}

		public void setRecuperi(ArrayList<ImportoDescrizione> recuperi) {
			this.recuperi = recuperi;
		}

		public ArrayList<ImportoDescrizione> getRevoche() {
			return revoche;
		}

		public void setRevoche(ArrayList<ImportoDescrizione> revoche) {
			this.revoche = revoche;
		}

		public Double getImportoRendicontato() {
			return importoRendicontato;
		}

		public void setImportoRendicontato(Double importoRendicontato) {
			this.importoRendicontato = importoRendicontato;
		}

		public Double getImportoQuietanzato() {
			return importoQuietanzato;
		}

		public void setImportoQuietanzato(Double importoQuietanzato) {
			this.importoQuietanzato = importoQuietanzato;
		}

		public Double getImportoCofinanziamentoPrivato() {
			return importoCofinanziamentoPrivato;
		}

		public void setImportoCofinanziamentoPrivato(Double importoCofinanziamentoPrivato) {
			this.importoCofinanziamentoPrivato = importoCofinanziamentoPrivato;
		}

		public Double getImportoCofinanziamentoPubblico() {
			return importoCofinanziamentoPubblico;
		}

		public void setImportoCofinanziamentoPubblico(Double importoCofinanziamentoPubblico) {
			this.importoCofinanziamentoPubblico = importoCofinanziamentoPubblico;
		}

		public Double getImportoAmmesso() {
			return importoAmmesso;
		}

		public void setImportoAmmesso(Double importoAmmesso) {
			this.importoAmmesso = importoAmmesso;
		}

		public DatiCumuloDeMinimis getDatiCumuloDeMinimis() {
			return datiCumuloDeMinimis;
		}

		public void setDatiCumuloDeMinimis(DatiCumuloDeMinimis datiCumuloDeMinimis) {
			this.datiCumuloDeMinimis = datiCumuloDeMinimis;
		}

		public String getImportoResiduoAmmesso() {
			return importoResiduoAmmesso;
		}

		public void setImportoResiduoAmmesso(String importoResiduoAmmesso) {
			this.importoResiduoAmmesso = importoResiduoAmmesso;
		}

		public java.lang.Boolean getIsLegatoBilancio() {
			return isLegatoBilancio;
		}

		public void setIsLegatoBilancio(java.lang.Boolean isLegatoBilancio) {
			this.isLegatoBilancio = isLegatoBilancio;
		}

		public ArrayList<EconomiaPerDatiGenerali> getEconomie() {
			return economie;
		}

		public void setEconomie(ArrayList<EconomiaPerDatiGenerali> economie) {
			this.economie = economie;
		}

		public Double getImportoImpegno() {
			return importoImpegno;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		
		
		public DatiGenerali() {
			super();

		}

		public String toString() {

			return super.toString();
		}
}
