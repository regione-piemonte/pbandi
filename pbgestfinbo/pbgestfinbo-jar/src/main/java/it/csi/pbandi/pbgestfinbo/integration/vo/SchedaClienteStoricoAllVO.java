/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;


public class SchedaClienteStoricoAllVO {
	
		private BigDecimal whichOne;
	
		//FOR STORICO STATO AZIENDA (1)
		private String staAz_statoAzienda;
		private Date staAz_dataInizio;
		private Date staAz_dataFine;
		private String staAz_utenteModifica;
		
		//FOR STATO CREDITO FINPIEMONTE (2)
		private String creFin_statoCredito;
		private Date creFin_dataModifica;
		private String creFin_utenteModifica;
		
		//FOR STORICO RATING (3)
		private String rat_rating;
		private String rat_provider;
		private Date rat_dataClass;
		private String rat_utenteModifica;
		
		//FOR STORICO BANCA BENEFICIARIO(4)
		private String banBen_banca;
		private Date banBen_dataModifica;
		private String banBen_motivazione;
		private String banBen_soggettoTerzo;
		private String banBen_utenteModifica;
		
		
		
		public SchedaClienteStoricoAllVO(BigDecimal whichOne, String staAz_statoAzienda, Date staAz_dataInizio,
				Date staAz_dataFine, String staAz_utenteModifica, String creFin_statoCredito, Date creFin_dataModifica,
				String creFin_utenteModifica, String rat_rating, String rat_provider, Date rat_dataClass,
				String rat_utenteModifica, String banBen_banca, Date banBen_dataModifica, String banBen_motivazione,
				String banBen_soggettoTerzo, String banBen_utenteModifica) {
			this.whichOne = whichOne;
			this.staAz_statoAzienda = staAz_statoAzienda;
			this.staAz_dataInizio = staAz_dataInizio;
			this.staAz_dataFine = staAz_dataFine;
			this.staAz_utenteModifica = staAz_utenteModifica;
			this.creFin_statoCredito = creFin_statoCredito;
			this.creFin_dataModifica = creFin_dataModifica;
			this.creFin_utenteModifica = creFin_utenteModifica;
			this.rat_rating = rat_rating;
			this.rat_provider = rat_provider;
			this.rat_dataClass = rat_dataClass;
			this.rat_utenteModifica = rat_utenteModifica;
			this.banBen_banca = banBen_banca;
			this.banBen_dataModifica = banBen_dataModifica;
			this.banBen_motivazione = banBen_motivazione;
			this.banBen_soggettoTerzo = banBen_soggettoTerzo;
			this.banBen_utenteModifica = banBen_utenteModifica;
		}



		public SchedaClienteStoricoAllVO() {
		}



		public BigDecimal getWhichOne() {
			return whichOne;
		}



		public void setWhichOne(BigDecimal whichOne) {
			this.whichOne = whichOne;
		}



		public String getStaAz_statoAzienda() {
			return staAz_statoAzienda;
		}



		public void setStaAz_statoAzienda(String staAz_statoAzienda) {
			this.staAz_statoAzienda = staAz_statoAzienda;
		}



		public Date getStaAz_dataInizio() {
			return staAz_dataInizio;
		}



		public void setStaAz_dataInizio(Date staAz_dataInizio) {
			this.staAz_dataInizio = staAz_dataInizio;
		}



		public Date getStaAz_dataFine() {
			return staAz_dataFine;
		}



		public void setStaAz_dataFine(Date staAz_dataFine) {
			this.staAz_dataFine = staAz_dataFine;
		}



		public String getStaAz_utenteModifica() {
			return staAz_utenteModifica;
		}



		public void setStaAz_utenteModifica(String staAz_utenteModifica) {
			this.staAz_utenteModifica = staAz_utenteModifica;
		}



		public String getCreFin_statoCredito() {
			return creFin_statoCredito;
		}



		public void setCreFin_statoCredito(String creFin_statoCredito) {
			this.creFin_statoCredito = creFin_statoCredito;
		}



		public Date getCreFin_dataModifica() {
			return creFin_dataModifica;
		}



		public void setCreFin_dataModifica(Date creFin_dataModifica) {
			this.creFin_dataModifica = creFin_dataModifica;
		}



		public String getCreFin_utenteModifica() {
			return creFin_utenteModifica;
		}



		public void setCreFin_utenteModifica(String creFin_utenteModifica) {
			this.creFin_utenteModifica = creFin_utenteModifica;
		}



		public String getRat_rating() {
			return rat_rating;
		}



		public void setRat_rating(String rat_rating) {
			this.rat_rating = rat_rating;
		}



		public String getRat_provider() {
			return rat_provider;
		}



		public void setRat_provider(String rat_provider) {
			this.rat_provider = rat_provider;
		}



		public Date getRat_dataClass() {
			return rat_dataClass;
		}



		public void setRat_dataClass(Date rat_dataClass) {
			this.rat_dataClass = rat_dataClass;
		}



		public String getRat_utenteModifica() {
			return rat_utenteModifica;
		}



		public void setRat_utenteModifica(String rat_utenteModifica) {
			this.rat_utenteModifica = rat_utenteModifica;
		}



		public String getBanBen_banca() {
			return banBen_banca;
		}



		public void setBanBen_banca(String banBen_banca) {
			this.banBen_banca = banBen_banca;
		}



		public Date getBanBen_dataModifica() {
			return banBen_dataModifica;
		}



		public void setBanBen_dataModifica(Date banBen_dataModifica) {
			this.banBen_dataModifica = banBen_dataModifica;
		}



		public String getBanBen_motivazione() {
			return banBen_motivazione;
		}



		public void setBanBen_motivazione(String banBen_motivazione) {
			this.banBen_motivazione = banBen_motivazione;
		}



		public String getBanBen_soggettoTerzo() {
			return banBen_soggettoTerzo;
		}



		public void setBanBen_soggettoTerzo(String banBen_soggettoTerzo) {
			this.banBen_soggettoTerzo = banBen_soggettoTerzo;
		}



		public String getBanBen_utenteModifica() {
			return banBen_utenteModifica;
		}



		public void setBanBen_utenteModifica(String banBen_utenteModifica) {
			this.banBen_utenteModifica = banBen_utenteModifica;
		}



		@Override
		public String toString() {
			return "SchedaClienteStoricoAllVO [whichOne=" + whichOne + ", staAz_statoAzienda=" + staAz_statoAzienda
					+ ", staAz_dataInizio=" + staAz_dataInizio + ", staAz_dataFine=" + staAz_dataFine
					+ ", staAz_utenteModifica=" + staAz_utenteModifica + ", creFin_statoCredito=" + creFin_statoCredito
					+ ", creFin_dataModifica=" + creFin_dataModifica + ", creFin_utenteModifica="
					+ creFin_utenteModifica + ", rat_rating=" + rat_rating + ", rat_provider=" + rat_provider
					+ ", rat_dataClass=" + rat_dataClass + ", rat_utenteModifica=" + rat_utenteModifica
					+ ", banBen_banca=" + banBen_banca + ", banBen_dataModifica=" + banBen_dataModifica
					+ ", banBen_motivazione=" + banBen_motivazione + ", banBen_soggettoTerzo=" + banBen_soggettoTerzo
					+ ", banBen_utenteModifica=" + banBen_utenteModifica + "]";
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

}
