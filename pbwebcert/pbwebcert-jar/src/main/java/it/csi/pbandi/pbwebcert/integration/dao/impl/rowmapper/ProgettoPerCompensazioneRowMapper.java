/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.ProgettoPerCompensazioneVO;


public class ProgettoPerCompensazioneRowMapper implements RowMapper<ProgettoPerCompensazioneVO> {

	@Override
	public ProgettoPerCompensazioneVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		//? FLAG_CORREZIONE_CN, IMP_CERTIFICAZIONE_NETTO_BCK, QUOTA_REVOCA_PER_IRR, NUM_PERC_SPESA_PUBBLICA
		
		//? IMPORTO_CERT_NET_ANNO_PREC, IMPORTO_CERT_NET_ANNO_IN_CORSO, IMPORTO_PAG_VALIDATI_ORIG
		
		//? VALORE_N,VALORE_N_1,SOMMA_VALORI_N,ID_PROGETTO_SIF
				
		ProgettoPerCompensazioneVO cm = new ProgettoPerCompensazioneVO();
		
		cm.setIdPersonaFisica(rs.getBigDecimal("ID_PERSONA_FISICA"));
		cm.setIdEnteGiuridico(rs.getBigDecimal("ID_ENTE_GIURIDICO"));
		cm.setIdDimensioneImpresa(rs.getBigDecimal("ID_DIMENSIONE_IMPRESA"));
		cm.setFlagAttivo(rs.getString("FLAG_ATTIVO"));
		cm.setCodiceProgetto(rs.getString("CODICE_PROGETTO"));
		cm.setDenominazioneBeneficiario(rs.getString("DENOMINAZIONE_BENEFICIARIO"));
		cm.setNota(rs.getString("NOTA"));
		cm.setIdUtenteIns(rs.getBigDecimal("ID_UTENTE_INS"));
		cm.setIdUtenteAgg(rs.getBigDecimal("ID_UTENTE_AGG"));
		cm.setFlagComp(rs.getString("FLAG_COMP"));
		cm.setFlagCheckListInLoco(rs.getString("FLAG_CHECK_LIST_IN_LOCO"));
		cm.setFlagCheckListCertificazione(rs.getString("FLAG_CHECK_LIST_CERTIFICAZIONE"));
		cm.setIdStatoProgetto(rs.getBigDecimal("ID_STATO_PROGETTO"));
		cm.setIdTipoOperazione(rs.getBigDecimal("ID_TIPO_OPERAZIONE"));
		cm.setIdIndirizzoSedeLegale(rs.getBigDecimal("ID_INDIRIZZO_SEDE_LEGALE"));
		
		
		
		cm.setIdLineaDiIntervento(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
		cm.setTitoloProgetto(rs.getString("TITOLO_PROGETTO"));
		cm.setImportoRendicontato(rs.getBigDecimal("IMPORTO_RENDICONTATO"));
		cm.setContributoPubblicoConcesso(rs.getBigDecimal("CONTRIBUTO_PUBBLICO_CONCESSO"));
		cm.setCostoAmmesso(rs.getBigDecimal("COSTO_AMMESSO"));		
		cm.setNomeBandoLinea(rs.getString("NOME_BANDO_LINEA"));		
		cm.setDescAsse(rs.getString("DESC_ASSE"));	
		cm.setIdPropostaCertificaz(rs.getBigDecimal("ID_PROPOSTA_CERTIFICAZ"));
		cm.setIdDettPropostaCertif(rs.getBigDecimal("ID_DETT_PROPOSTA_CERTIF"));		
		cm.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
		cm.setCodiceVisualizzato(rs.getString("CODICE_VISUALIZZATO"));		
		cm.setImportoFideiussioni(rs.getBigDecimal("IMPORTO_FIDEIUSSIONI"));
		cm.setImportoPagamentiValidati(rs.getBigDecimal("IMPORTO_PAGAMENTI_VALIDATI"));
		cm.setImportoEccendenzeValidazione(rs.getBigDecimal("IMPORTO_ECCENDENZE_VALIDAZIONE"));
		cm.setImportoErogazioni(rs.getBigDecimal("IMPORTO_EROGAZIONI"));		
		cm.setSpesaCertificabileLorda(rs.getBigDecimal("SPESA_CERTIFICABILE_LORDA"));	
		cm.setImportoRevoche(rs.getBigDecimal("IMPORTO_REVOCHE"));	
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
		cm.setDtUltimaChecklistInLoco(rs.getDate("DT_ULTIMA_CHECKLIST_IN_LOCO"));
	
		return cm;
	}



}
