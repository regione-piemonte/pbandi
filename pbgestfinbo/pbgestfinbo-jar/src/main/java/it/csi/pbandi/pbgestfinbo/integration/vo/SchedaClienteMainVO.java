/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SchedaClienteMainVO {

	// Tenete aggiornato l'oggetto tra BE e FE, per favore

		private BigDecimal idSoggetto;
		private BigDecimal idProgetto;
		private BigDecimal progrSoggProg;
		private BigDecimal staAz_currentId;
		private BigDecimal staAz_idStatoAzienda;
		private Date staAz_dtInizioValidita;
		private Date staAz_dtfineValidita;
		private List<String> stAz_statiAzienda;
		private BigDecimal staCre_currentId;
		private BigDecimal staCre_idStatoCredito;
		private List<String> staCre_statiCredito;
		private BigDecimal rating_currentId;
		//private List<String> rating_providers;
		private List<SuggestRatingVO> rating_ratings;
		private BigDecimal banBen_currentId;
		private BigDecimal banBen_idBanca;
		private List<String> banBen_banche;
		private List<String> banBen_motivazioni;
		
		private String bando;
		private String progetto;
		private String denominazEnteGiu;
		private String denominazPerFis;
		private String codiceFiscale;
		private String partitaIva;
		private String formaGiuridica;
		private String tipoSoggetto;
		private String statoAzienda;
		private String statoCredito;
		private String rating;
		private String provider;
		private Date dataClassRating;
		private String classeRischio;
		private Date dataClassRischio;
		private String banca;
		private String deliberatoBanca;
		private String confidi;
		private String altreGaranzie;
		
		private List<SchedaClienteSectionDettaglioErogatoVO> dettaglioErogato;
		
		private List<SchedaClienteStoricoAllVO> storicoStatoAzienda;
		private List<SchedaClienteStoricoAllVO> storicoStatoCreditoFin;
		private List<SchedaClienteStoricoAllVO> storicoRating;
		private List<SchedaClienteStoricoAllVO> storicoBancaBeneficiario;
		


		


		public SchedaClienteMainVO(BigDecimal idSoggetto, BigDecimal idProgetto, BigDecimal progrSoggProg,
				BigDecimal staAz_currentId, BigDecimal staAz_idStatoAzienda, Date staAz_dtInizioValidita,
				Date staAz_dtfineValidita, List<String> stAz_statiAzienda, BigDecimal staCre_currentId,
				BigDecimal staCre_idStatoCredito, List<String> staCre_statiCredito, BigDecimal rating_currentId,
				List<SuggestRatingVO> rating_ratings, BigDecimal banBen_currentId, BigDecimal banBen_idBanca,
				List<String> banBen_banche, List<String> banBen_motivazioni, String bando, String progetto,
				String denominazEnteGiu, String denominazPerFis, String codiceFiscale, String partitaIva,
				String formaGiuridica, String tipoSoggetto, String statoAzienda, String statoCredito, String rating,
				String provider, Date dataClassRating, String classeRischio, Date dataClassRischio, String banca,
				String deliberatoBanca, String confidi, String altreGaranzie,
				List<SchedaClienteSectionDettaglioErogatoVO> dettaglioErogato,
				List<SchedaClienteStoricoAllVO> storicoStatoAzienda,
				List<SchedaClienteStoricoAllVO> storicoStatoCreditoFin, List<SchedaClienteStoricoAllVO> storicoRating,
				List<SchedaClienteStoricoAllVO> storicoBancaBeneficiario) {
			this.idSoggetto = idSoggetto;
			this.idProgetto = idProgetto;
			this.progrSoggProg = progrSoggProg;
			this.staAz_currentId = staAz_currentId;
			this.staAz_idStatoAzienda = staAz_idStatoAzienda;
			this.staAz_dtInizioValidita = staAz_dtInizioValidita;
			this.staAz_dtfineValidita = staAz_dtfineValidita;
			this.stAz_statiAzienda = stAz_statiAzienda;
			this.staCre_currentId = staCre_currentId;
			this.staCre_idStatoCredito = staCre_idStatoCredito;
			this.staCre_statiCredito = staCre_statiCredito;
			this.rating_currentId = rating_currentId;
			this.rating_ratings = rating_ratings;
			this.banBen_currentId = banBen_currentId;
			this.banBen_idBanca = banBen_idBanca;
			this.banBen_banche = banBen_banche;
			this.banBen_motivazioni = banBen_motivazioni;
			this.bando = bando;
			this.progetto = progetto;
			this.denominazEnteGiu = denominazEnteGiu;
			this.denominazPerFis = denominazPerFis;
			this.codiceFiscale = codiceFiscale;
			this.partitaIva = partitaIva;
			this.formaGiuridica = formaGiuridica;
			this.tipoSoggetto = tipoSoggetto;
			this.statoAzienda = statoAzienda;
			this.statoCredito = statoCredito;
			this.rating = rating;
			this.provider = provider;
			this.dataClassRating = dataClassRating;
			this.classeRischio = classeRischio;
			this.dataClassRischio = dataClassRischio;
			this.banca = banca;
			this.deliberatoBanca = deliberatoBanca;
			this.confidi = confidi;
			this.altreGaranzie = altreGaranzie;
			this.dettaglioErogato = dettaglioErogato;
			this.storicoStatoAzienda = storicoStatoAzienda;
			this.storicoStatoCreditoFin = storicoStatoCreditoFin;
			this.storicoRating = storicoRating;
			this.storicoBancaBeneficiario = storicoBancaBeneficiario;
		}




		public String getConfidi() {
			return confidi;
		}




		public void setConfidi(String confidi) {
			this.confidi = confidi;
		}




		public BigDecimal getBanBen_idBanca() {
			return banBen_idBanca;
		}




		public void setBanBen_idBanca(BigDecimal banBen_idBanca) {
			this.banBen_idBanca = banBen_idBanca;
		}






		public String getDeliberatoBanca() {
			return deliberatoBanca;
		}




		public void setDeliberatoBanca(String deliberatoBanca) {
			this.deliberatoBanca = deliberatoBanca;
		}




		public BigDecimal getStaCre_idStatoCredito() {
			return staCre_idStatoCredito;
		}




		public void setStaCre_idStatoCredito(BigDecimal stacre_idStatoCredito) {
			this.staCre_idStatoCredito = stacre_idStatoCredito;
		}




		public SchedaClienteMainVO() {
		}




		public BigDecimal getIdSoggetto() {
			return idSoggetto;
		}




		public void setIdSoggetto(BigDecimal idSoggetto) {
			this.idSoggetto = idSoggetto;
		}




		public BigDecimal getIdProgetto() {
			return idProgetto;
		}




		public void setIdProgetto(BigDecimal idProgetto) {
			this.idProgetto = idProgetto;
		}




		public BigDecimal getProgrSoggProg() {
			return progrSoggProg;
		}




		public void setProgrSoggProg(BigDecimal progrSoggProg) {
			this.progrSoggProg = progrSoggProg;
		}




		public BigDecimal getStaAz_currentId() {
			return staAz_currentId;
		}




		public void setStaAz_currentId(BigDecimal staAz_currentId) {
			this.staAz_currentId = staAz_currentId;
		}




		public BigDecimal getStaAz_idStatoAzienda() {
			return staAz_idStatoAzienda;
		}




		public void setStaAz_idStatoAzienda(BigDecimal staAz_idStatoAzienda) {
			this.staAz_idStatoAzienda = staAz_idStatoAzienda;
		}




		public Date getStaAz_dtInizioValidita() {
			return staAz_dtInizioValidita;
		}




		public void setStaAz_dtInizioValidita(Date staAz_dtInizioValidita) {
			this.staAz_dtInizioValidita = staAz_dtInizioValidita;
		}




		public Date getStaAz_dtfineValidita() {
			return staAz_dtfineValidita;
		}




		public void setStaAz_dtfineValidita(Date staAz_dtfineValidita) {
			this.staAz_dtfineValidita = staAz_dtfineValidita;
		}




		public List<String> getStAz_statiAzienda() {
			return stAz_statiAzienda;
		}




		public void setStAz_statiAzienda(List<String> stAz_statiAzienda) {
			this.stAz_statiAzienda = stAz_statiAzienda;
		}




		public BigDecimal getStaCre_currentId() {
			return staCre_currentId;
		}




		public void setStaCre_currentId(BigDecimal staCre_currentId) {
			this.staCre_currentId = staCre_currentId;
		}




		public List<String> getStaCre_statiCredito() {
			return staCre_statiCredito;
		}




		public void setStaCre_statiCredito(List<String> staCre_statiCredito) {
			this.staCre_statiCredito = staCre_statiCredito;
		}




		public BigDecimal getRating_currentId() {
			return rating_currentId;
		}




		public void setRating_currentId(BigDecimal rating_currentId) {
			this.rating_currentId = rating_currentId;
		}







		public List<SuggestRatingVO> getRating_ratings() {
			return rating_ratings;
		}




		public void setRating_ratings(List<SuggestRatingVO> rating_ratings) {
			this.rating_ratings = rating_ratings;
		}




		public BigDecimal getBanBen_currentId() {
			return banBen_currentId;
		}




		public void setBanBen_currentId(BigDecimal banBen_currentId) {
			this.banBen_currentId = banBen_currentId;
		}




		public List<String> getBanBen_banche() {
			return banBen_banche;
		}




		public void setBanBen_banche(List<String> banBen_banche) {
			this.banBen_banche = banBen_banche;
		}




		public List<String> getBanBen_motivazioni() {
			return banBen_motivazioni;
		}




		public void setBanBen_motivazioni(List<String> banBen_motivazioni) {
			this.banBen_motivazioni = banBen_motivazioni;
		}




		public String getBando() {
			return bando;
		}




		public void setBando(String bando) {
			this.bando = bando;
		}




		public String getProgetto() {
			return progetto;
		}




		public void setProgetto(String progetto) {
			this.progetto = progetto;
		}




		public String getDenominazEnteGiu() {
			return denominazEnteGiu;
		}




		public void setDenominazEnteGiu(String denominazEnteGiu) {
			this.denominazEnteGiu = denominazEnteGiu;
		}




		public String getDenominazPerFis() {
			return denominazPerFis;
		}




		public void setDenominazPerFis(String denominazPerFis) {
			this.denominazPerFis = denominazPerFis;
		}




		public String getCodiceFiscale() {
			return codiceFiscale;
		}




		public void setCodiceFiscale(String codiceFiscale) {
			this.codiceFiscale = codiceFiscale;
		}




		public String getPartitaIva() {
			return partitaIva;
		}




		public void setPartitaIva(String partitaIva) {
			this.partitaIva = partitaIva;
		}




		public String getFormaGiuridica() {
			return formaGiuridica;
		}




		public void setFormaGiuridica(String formaGiuridica) {
			this.formaGiuridica = formaGiuridica;
		}




		public String getTipoSoggetto() {
			return tipoSoggetto;
		}




		public void setTipoSoggetto(String tipoSoggetto) {
			this.tipoSoggetto = tipoSoggetto;
		}




		public String getStatoAzienda() {
			return statoAzienda;
		}




		public void setStatoAzienda(String statoAzienda) {
			this.statoAzienda = statoAzienda;
		}




		public String getStatoCredito() {
			return statoCredito;
		}




		public void setStatoCredito(String statoCredito) {
			this.statoCredito = statoCredito;
		}




		public String getRating() {
			return rating;
		}




		public void setRating(String rating) {
			this.rating = rating;
		}




		public String getProvider() {
			return provider;
		}




		public void setProvider(String provider) {
			this.provider = provider;
		}




		public Date getDataClassRating() {
			return dataClassRating;
		}




		public void setDataClassRating(Date dataClassRating) {
			this.dataClassRating = dataClassRating;
		}




		public String getClasseRischio() {
			return classeRischio;
		}




		public void setClasseRischio(String classeRischio) {
			this.classeRischio = classeRischio;
		}




		public Date getDataClassRischio() {
			return dataClassRischio;
		}




		public void setDataClassRischio(Date dataClassRischio) {
			this.dataClassRischio = dataClassRischio;
		}




		public String getBanca() {
			return banca;
		}




		public void setBanca(String banca) {
			this.banca = banca;
		}




		public List<SchedaClienteSectionDettaglioErogatoVO> getDettaglioErogato() {
			return dettaglioErogato;
		}




		public void setDettaglioErogato(List<SchedaClienteSectionDettaglioErogatoVO> dettaglioErogato) {
			this.dettaglioErogato = dettaglioErogato;
		}




		public List<SchedaClienteStoricoAllVO> getStoricoStatoAzienda() {
			return storicoStatoAzienda;
		}




		public void setStoricoStatoAzienda(List<SchedaClienteStoricoAllVO> storicoStatoAzienda) {
			this.storicoStatoAzienda = storicoStatoAzienda;
		}




		public List<SchedaClienteStoricoAllVO> getStoricoStatoCreditoFin() {
			return storicoStatoCreditoFin;
		}




		public void setStoricoStatoCreditoFin(List<SchedaClienteStoricoAllVO> storicoStatoCreditoFin) {
			this.storicoStatoCreditoFin = storicoStatoCreditoFin;
		}




		public List<SchedaClienteStoricoAllVO> getStoricoRating() {
			return storicoRating;
		}




		public void setStoricoRating(List<SchedaClienteStoricoAllVO> storicoRating) {
			this.storicoRating = storicoRating;
		}




		public List<SchedaClienteStoricoAllVO> getStoricoBancaBeneficiario() {
			return storicoBancaBeneficiario;
		}




		public void setStoricoBancaBeneficiario(List<SchedaClienteStoricoAllVO> storicoBancaBeneficiario) {
			this.storicoBancaBeneficiario = storicoBancaBeneficiario;
		}




		public String getAltreGaranzie() {
			return altreGaranzie;
		}




		public void setAltreGaranzie(String altreGaranzie) {
			this.altreGaranzie = altreGaranzie;
		}




		@Override
		public String toString() {
			return "SchedaClienteMainVO [idSoggetto=" + idSoggetto + ", idProgetto=" + idProgetto + ", progrSoggProg="
					+ progrSoggProg + ", staAz_currentId=" + staAz_currentId + ", staAz_idStatoAzienda="
					+ staAz_idStatoAzienda + ", staAz_dtInizioValidita=" + staAz_dtInizioValidita
					+ ", staAz_dtfineValidita=" + staAz_dtfineValidita + ", stAz_statiAzienda=" + stAz_statiAzienda
					+ ", staCre_currentId=" + staCre_currentId + ", staCre_idStatoCredito=" + staCre_idStatoCredito
					+ ", staCre_statiCredito=" + staCre_statiCredito + ", rating_currentId=" + rating_currentId
					+ ", rating_ratings=" + rating_ratings + ", banBen_currentId=" + banBen_currentId
					+ ", banBen_idBanca=" + banBen_idBanca + ", banBen_banche=" + banBen_banche
					+ ", banBen_motivazioni=" + banBen_motivazioni + ", bando=" + bando + ", progetto=" + progetto
					+ ", denominazEnteGiu=" + denominazEnteGiu + ", denominazPerFis=" + denominazPerFis
					+ ", codiceFiscale=" + codiceFiscale + ", partitaIva=" + partitaIva + ", formaGiuridica="
					+ formaGiuridica + ", tipoSoggetto=" + tipoSoggetto + ", statoAzienda=" + statoAzienda
					+ ", statoCredito=" + statoCredito + ", rating=" + rating + ", provider=" + provider
					+ ", dataClassRating=" + dataClassRating + ", classeRischio=" + classeRischio
					+ ", dataClassRischio=" + dataClassRischio + ", banca=" + banca + ", deliberatoBanca="
					+ deliberatoBanca + ", confidi=" + confidi + ", altreGaranzie=" + altreGaranzie
					+ ", dettaglioErogato=" + dettaglioErogato + ", storicoStatoAzienda=" + storicoStatoAzienda
					+ ", storicoStatoCreditoFin=" + storicoStatoCreditoFin + ", storicoRating=" + storicoRating
					+ ", storicoBancaBeneficiario=" + storicoBancaBeneficiario + "]";
		}



		
		
		
		
		
		
		
		
		
		
		

}
