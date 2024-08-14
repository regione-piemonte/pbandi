/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.ReportPropostaCertificazionePorFesrVO;


public class ReportPropostaPorFesrRowMapper implements RowMapper<ReportPropostaCertificazionePorFesrVO> {

	@Override
	public ReportPropostaCertificazionePorFesrVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReportPropostaCertificazionePorFesrVO cm = new ReportPropostaCertificazionePorFesrVO();
		cm.setDescProgramma(rs.getString("DESC_PROGRAMMA"));
		cm.setIdLineaDiIntervento(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
		cm.setTitoloProgetto(rs.getString("TITOLO_PROGETTO"));
		cm.setImportoRendicontato(rs.getBigDecimal("IMPORTO_RENDICONTATO"));
		cm.setContributoPubblicoConcesso(rs.getBigDecimal("CONTRIBUTO_PUBBLICO_CONCESSO"));
		cm.setCostoAmmesso(rs.getBigDecimal("COSTO_AMMESSO"));
		cm.setComuneSedeIntervento(rs.getString("COMUNE_SEDE_INTERVENTO"));
		cm.setProvinciaSedeIntervento(rs.getString("PROVINCIA_SEDE_INTERVENTO"));
		cm.setComuneSedeIntervento(rs.getString("COMUNE_SEDE_LEGALE"));
		cm.setProvinciaSedeLegale(rs.getString("PROVINCIA_SEDE_LEGALE"));
		
		cm.setBeneficiario(rs.getString("BENEFICIARIO"));
		cm.setTipoOperazione(rs.getString("TIPO_OPERAZIONE"));
		cm.setStatoProgetto(rs.getString("STATO_PROGETTO"));
		
		cm.setDimensioneImpresa(rs.getString("DIMENSIONE_IMPRESA"));
		cm.setNomeBandoLinea(rs.getString("NOME_BANDO_LINEA"));
		cm.setResponsabilitaGestionale(rs.getString("RESPONSABILITA_GESTIONALE"));
		
		cm.setDescAsse(rs.getString("DESC_ASSE"));
		cm.setDescLinea(rs.getString("DESC_LINEA"));
		cm.setDescMisura(rs.getString("DESC_MISURA"));
		
		cm.setProgrBandoLineaIntervento(rs.getBigDecimal("PROGR_BANDO_LINEA_INTERVENTO"));
		cm.setIdPropostaCertificaz(rs.getBigDecimal("ID_PROPOSTA_CERTIFICAZ"));
		cm.setIdDettPropostaCertif(rs.getBigDecimal("ID_DETT_PROPOSTA_CERTIF"));
		
		cm.setDtOraCreazione(rs.getDate("DT_ORA_CREAZIONE"));
		cm.setDtCutOffPagamenti(rs.getDate("DT_CUT_OFF_PAGAMENTI"));
		cm.setDtCutOffValidazioni(rs.getDate("DT_CUT_OFF_VALIDAZIONI"));
		cm.setDtCutOffErogazioni(rs.getDate("DT_CUT_OFF_EROGAZIONI"));
		cm.setDtCutOffFideiussioni(rs.getDate("DT_CUT_OFF_FIDEIUSSIONI"));
		
		cm.setDescProposta(rs.getString("DESC_PROPOSTA"));
		cm.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
		cm.setCodiceVisualizzato(rs.getString("CODICE_VISUALIZZATO"));
		cm.setCup(rs.getString("CUP"));
		cm.setFlagRa(rs.getString("FLAG_RA"));
		
		cm.setCpupor1(rs.getBigDecimal("CPUPOR1"));
		cm.setCpupor2(rs.getBigDecimal("CPUPOR2"));
		cm.setCpupor3(rs.getBigDecimal("CPUPOR3"));
		cm.setCofpor(rs.getBigDecimal("COFPOR"));
		
		cm.setOthfin(rs.getBigDecimal("OTHFIN"));
		cm.setCpupor1_fesr(rs.getBigDecimal("CPUPOR1_FESR"));
		cm.setCpupor2_fesr(rs.getBigDecimal("CPUPOR2_FESR"));
		cm.setCpupor3_fesr(rs.getBigDecimal("CPUPOR3_FESR"));
		
		cm.setCpufasSta(rs.getBigDecimal("CPUFAS_STA"));
		cm.setCpufasReg(rs.getBigDecimal("CPUFAS_REG"));
		
		cm.setImportoFideiussioni(rs.getBigDecimal("IMPORTO_FIDEIUSSIONI"));
		cm.setImportoFidUtilizzate(rs.getBigDecimal("IMPORTO_FID_UTILIZZATE"));
		cm.setImportoPagValidatiOrig(rs.getBigDecimal("IMPORTO_PAG_VALIDATI_ORIG"));
		cm.setImportoPagamentiValidati(rs.getBigDecimal("IMPORTO_PAGAMENTI_VALIDATI"));
		cm.setImportoEccendenzeValidazione(rs.getBigDecimal("IMPORTO_ECCENDENZE_VALIDAZIONE"));
		cm.setImportoErogazioni(rs.getBigDecimal("IMPORTO_EROGAZIONI"));
		
		cm.setSpesaCertificabileLorda(rs.getBigDecimal("SPESA_CERTIFICABILE_LORDA"));
		
		cm.setImportoRevoche(rs.getBigDecimal("IMPORTO_REVOCHE"));
		cm.setQuotaRevocaPerIrr(rs.getBigDecimal("QUOTA_REVOCA_PER_IRR"));
		
		cm.setDtUltimaRevoca(rs.getDate("DT_ULTIMA_REVOCA"));
		
		cm.setImportoDaRecuperare(rs.getBigDecimal("IMPORTO_DA_RECUPERARE"));
		cm.setImportoPrerecuperi(rs.getBigDecimal("IMPORTO_PRERECUPERI"));
		cm.setDtUltimoPrerecupero(rs.getDate("DT_ULTIMO_PRERECUPERO"));
		
		cm.setImportoRecuperi(rs.getBigDecimal("IMPORTO_RECUPERI"));
		cm.setDtUltimoRecupero(rs.getDate("DT_ULTIMO_RECUPERO"));
		cm.setImportoSoppressioni(rs.getBigDecimal("IMPORTO_SOPPRESSIONI"));
		cm.setDtUltimaSoppressione(rs.getDate("DT_ULTIMA_SOPPRESSIONE"));
		
		
		cm.setImportoNonRilevanteCertif(rs.getBigDecimal("IMPORTO_NON_RILEVANTE_CERTIF"));
		cm.setImportoCertificazioneNetto(rs.getBigDecimal("IMPORTO_CERTIFICAZIONE_NETTO"));
		cm.setImpCertifNettoPremodifica(rs.getBigDecimal("IMP_CERTIF_NETTO_PREMODIFICA"));
		cm.setIdentificativiIrregolarita(rs.getString("IDENTIFICATIVI_IRREGOLARITA"));
		
		
		cm.setErogazioniInMenoPerErrore(rs.getBigDecimal("EROGAZIONI_IN_MENO_PER_ERRORE"));
		cm.setFideiussioniInMenoPerError(rs.getBigDecimal("FIDEIUSSIONI_IN_MENO_PER_ERROR"));
		cm.setRecuperiInMenoPerErrore(rs.getBigDecimal("RECUPERI_IN_MENO_PER_ERRORE"));
		cm.setSoppressioniInMenoPerError(rs.getBigDecimal("SOPPRESSIONI_IN_MENO_PER_ERROR"));
		cm.setPrerecuperiInMenoPerError(rs.getBigDecimal("PRERECUPERI_IN_MENO_PER_ERROR"));
		cm.setRevocheInMenoPerError(rs.getBigDecimal("REVOCHE_IN_MENO_PER_ERROR"));
		cm.setImpInteressiRecuperatiNetti(rs.getBigDecimal("IMP_INTERESSI_RECUPERATI_NETTI"));
		cm.setImpCertificabileNettoSoppr(rs.getBigDecimal("IMP_CERTIFICABILE_NETTO_SOPPR"));
		cm.setImpRevocheNettoSoppressioni(rs.getBigDecimal("IMP_REVOCHE_NETTO_SOPPRESSIONI"));
		
		cm.setImpCertificabileNettoRevoc(rs.getBigDecimal("IMP_CERTIFICABILE_NETTO_REVOC"));
		cm.setImportoRevocheIntermedio(rs.getBigDecimal("IMPORTO_REVOCHE_INTERMEDIO"));
		cm.setImpCertificazioneNettoPrec(rs.getBigDecimal("IMP_CERTIFICAZIONE_NETTO_PREC"));
		cm.setAvanzamento(rs.getBigDecimal("AVANZAMENTO"));
		cm.setImportoSoppressioniNetto(rs.getBigDecimal("IMPORTO_SOPPRESSIONI_NETTO"));
		cm.setImportoRecuperiPrerecuperi(rs.getBigDecimal("IMPORTO_RECUPERI_PRERECUPERI"));
		cm.setImportoInteressiRecuperati(rs.getBigDecimal("IMPORTO_INTERESSI_RECUPERATI"));
		cm.setImportoCertNetAnnoPrec(rs.getBigDecimal("IMPORTO_CERT_NET_ANNO_PREC"));
		cm.setImportoCertNetAnnoInCorso(rs.getBigDecimal("IMPORTO_CERT_NET_ANNO_IN_CORSO"));
		
		cm.setCodLineaAzione(rs.getString("COD_LINEA_AZIONE"));
		cm.setCodiceFiscaleSoggetto(rs.getString("CODICE_FISCALE_SOGGETTO"));
		cm.setFlagLc(rs.getString("FLAG_LC"));
		
		cm.setDtUltimaChecklistInLoco(rs.getDate("DT_ULTIMA_CHECKLIST_IN_LOCO"));
		
		cm.setImportoCertNetAnniPrec(rs.getBigDecimal("IMPORTO_CERT_NET_ANNI_PREC"));
		cm.setValoreN(rs.getBigDecimal("VALORE_N"));
		cm.setValoreN_1(rs.getBigDecimal("VALORE_N_1"));
		cm.setSommaValoriN(rs.getBigDecimal("SOMMA_VALORI_N"));
		cm.setIdProgettoSif(rs.getBigDecimal("ID_PROGETTO_SIF"));
		
		return cm;
	}



}
