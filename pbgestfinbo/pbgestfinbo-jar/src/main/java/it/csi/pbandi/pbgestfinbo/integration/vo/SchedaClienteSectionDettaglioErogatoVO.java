/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;

public class SchedaClienteSectionDettaglioErogatoVO {
	
		private BigDecimal idDomanda;
		private BigDecimal idSoggetto;
		private BigDecimal idProgetto;
		
		private Long idAgevolazione;
		private Long idAgevolazioneRif;
		private String descBreveTipoAgevolazione;
		private String descTipoAgevolazione;
		private Long numErogazioni; // Serve per logica label e Causale
		private String dispTipoAgevolazione;
		private String dispLabelCausale;
		private Long idCausale;
		private String descCausale;
		private String descBreveCausale;
		private Date dataErogazione;
		private BigDecimal totFin;
		private BigDecimal totBan;
		


		public SchedaClienteSectionDettaglioErogatoVO(BigDecimal idDomanda, BigDecimal idSoggetto,
				BigDecimal idProgetto, Long idAgevolazione, Long idAgevolazioneRif, String descBreveTipoAgevolazione,
				String descTipoAgevolazione, Long numErogazioni, String dispTipoAgevolazione, String dispLabelCausale,
				Long idCausale, String descCausale, String descBreveCausale, Date dataErogazione, BigDecimal totFin,
				BigDecimal totBan, String confidi) {
			this.idDomanda = idDomanda;
			this.idSoggetto = idSoggetto;
			this.idProgetto = idProgetto;
			this.idAgevolazione = idAgevolazione;
			this.idAgevolazioneRif = idAgevolazioneRif;
			this.descBreveTipoAgevolazione = descBreveTipoAgevolazione;
			this.descTipoAgevolazione = descTipoAgevolazione;
			this.numErogazioni = numErogazioni;
			this.dispTipoAgevolazione = dispTipoAgevolazione;
			this.dispLabelCausale = dispLabelCausale;
			this.idCausale = idCausale;
			this.descCausale = descCausale;
			this.descBreveCausale = descBreveCausale;
			this.dataErogazione = dataErogazione;
			this.totFin = totFin;
			this.totBan = totBan;
		}



		public SchedaClienteSectionDettaglioErogatoVO() {
		}


		public BigDecimal getIdDomanda() {
			return idDomanda;
		}


		public void setIdDomanda(BigDecimal idDomanda) {
			this.idDomanda = idDomanda;
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


		public Long getIdCausale() {
			return idCausale;
		}


		public void setIdCausale(Long idCausale) {
			this.idCausale = idCausale;
		}


		public String getDescCausale() {
			return descCausale;
		}


		public void setDescCausale(String descCausale) {
			this.descCausale = descCausale;
		}


		public String getDescBreveCausale() {
			return descBreveCausale;
		}


		public void setDescBreveCausale(String descBreveCausale) {
			this.descBreveCausale = descBreveCausale;
		}


		public Date getDataErogazione() {
			return dataErogazione;
		}


		public void setDataErogazione(Date dataErogazione) {
			this.dataErogazione = dataErogazione;
		}


		public BigDecimal getTotFin() {
			return totFin;
		}


		public void setTotFin(BigDecimal totFin) {
			this.totFin = totFin;
		}


		public BigDecimal getTotBan() {
			return totBan;
		}


		public void setTotBan(BigDecimal totBan) {
			this.totBan = totBan;
		}


		public Long getIdAgevolazione() {
			return idAgevolazione;
		}


		public void setIdAgevolazione(Long idAgevolazione) {
			this.idAgevolazione = idAgevolazione;
		}


		public Long getIdAgevolazioneRif() {
			return idAgevolazioneRif;
		}


		public void setIdAgevolazioneRif(Long idAgevolazioneRif) {
			this.idAgevolazioneRif = idAgevolazioneRif;
		}


		public String getDescBreveTipoAgevolazione() {
			return descBreveTipoAgevolazione;
		}


		public void setDescBreveTipoAgevolazione(String descBreveTipoAgevolazione) {
			this.descBreveTipoAgevolazione = descBreveTipoAgevolazione;
		}


		public String getDispLabelCausale() {
			return dispLabelCausale;
		}


		public void setDispLabelCausale(String dispLabelCausale) {
			this.dispLabelCausale = dispLabelCausale;
		}


		public Long getNumErogazioni() {
			return numErogazioni;
		}


		public void setNumErogazioni(Long numErogazioni) {
			this.numErogazioni = numErogazioni;
		}


		public String getDescTipoAgevolazione() {
			return descTipoAgevolazione;
		}


		public void setDescTipoAgevolazione(String descTipoAgevolazione) {
			this.descTipoAgevolazione = descTipoAgevolazione;
		}


		public String getDispTipoAgevolazione() {
			return dispTipoAgevolazione;
		}


		public void setDispTipoAgevolazione(String dispTipoAgevolazione) {
			this.dispTipoAgevolazione = dispTipoAgevolazione;
		}


		@Override
		public String toString() {
			return "SchedaClienteSectionDettaglioErogatoVO [idDomanda=" + idDomanda + ", idSoggetto=" + idSoggetto
					+ ", idProgetto=" + idProgetto + ", idAgevolazione=" + idAgevolazione + ", idAgevolazioneRif="
					+ idAgevolazioneRif + ", descBreveTipoAgevolazione=" + descBreveTipoAgevolazione
					+ ", descTipoAgevolazione=" + descTipoAgevolazione + ", numErogazioni=" + numErogazioni
					+ ", dispTipoAgevolazione=" + dispTipoAgevolazione + ", dispLabelCausale=" + dispLabelCausale
					+ ", idCausale=" + idCausale + ", descCausale=" + descCausale + ", descBreveCausale="
					+ descBreveCausale + ", dataErogazione=" + dataErogazione + ", totFin=" + totFin + ", totBan="
					+ totBan + "]";
		}



}
