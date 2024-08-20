/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoPropostaVarazioneStatoCreditoDTO;

public class StoricoPropostaVarazioneStatoCreditoDTORowMapper implements RowMapper<StoricoPropostaVarazioneStatoCreditoDTO> {

	@Override
	public StoricoPropostaVarazioneStatoCreditoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		
	
		StoricoPropostaVarazioneStatoCreditoDTO spvc =  new StoricoPropostaVarazioneStatoCreditoDTO(); 
			spvc.setIdVariazStatoCredito(rs.getLong("ID_VARIAZ_ST_CREDITO"));
			spvc.setIdSoggProgStatoCred(rs.getLong("ID_SOGG_PROG_STATO_CREDITO_FP"));
			spvc.setProgrSoggProgetto(rs.getLong("PROGR_SOGGETTO_PROGETTO"));
			spvc.setNome(rs.getString("descr2"));
			spvc.setCognome(rs.getString("descr1"));
			String nome = rs.getString("descr2"); 
			if(nome!=null ) {
				spvc.setDenominazione(spvc.getCognome()+' '+ nome);	
			} else {
				spvc.setDenominazione(spvc.getCognome());
			}
			spvc.setTitoloBando(rs.getString("TITOLO_BANDO"));
			spvc.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
			spvc.setDescStatoCredFin(rs.getString("desc_stato_cred_fin"));
			spvc.setDataProposta(rs.getDate("data_proposta"));
			spvc.setNag(rs.getString("CODICE_VISUALIZZATO")); //  in realtà questo è il codice visualizzato del progetto
			spvc.setIdProgetto(rs.getLong("ID_PROGETTO"));
			spvc.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
			spvc.setDescNuovoStatoCred(rs.getString("desc_nuovo_stat_cred"));
			spvc.setDescStatoProposta(rs.getString("desc_stat_prop"));
			spvc.setIdStatoPropVariazCred(rs.getLong("ID_STATO_PROP_VAR_CRE"));
			spvc.setDescStatAzienda(rs.getString("desc_stato_azienda"));
			spvc.setGiorniSconf(rs.getLong("gg_sconf"));
			spvc.setPercSconf(rs.getBigDecimal("percentuale"));
			spvc.setImpSconfCapitale(rs.getBigDecimal("imp_sconf_capitale"));
			spvc.setImpSconfAgev(rs.getBigDecimal("imp_sconf_agev"));
			spvc.setImpSconfInteressi(rs.getBigDecimal("imp_sconf_interessi"));
			spvc.setPartitaIva(rs.getString("IVA"));
			spvc.setDescModalitaAgevolaz(rs.getString("DESC_MODALITA_AGEVOLAZIONE")); 
			spvc.setIdStatoCreditoAttuale(rs.getInt("ID_STATO_CREDITO_FP"));
			spvc.setIdStatoCreditoProposto(rs.getInt("ID_NUOVO_STATO_CREDITO_FP"));
			spvc.setStatoCreditoAttuale(rs.getString("desc_stato_cred_fin"));
			spvc.setStatoCreditoProposto(rs.getString("desc_nuovo_stat_cred"));
			int idModAgev = rs.getInt("ID_MODALITA_AGEVOLAZIONE_rif"); 
			spvc.setIdModalitaAgevolazioneRif(idModAgev);
			spvc.setIdModalitaAgevolazione(rs.getInt("ID_MODALITA_AGEVOLAZIONE"));
			
			switch (idModAgev) {
			case 1:
				spvc.setDescModalitaAgevolaz("Contributo");
				break;
			case 5:
				spvc.setDescModalitaAgevolaz("Finanziamento");
				break;
			case 10:
				spvc.setDescModalitaAgevolaz("Garanzia");
				break;

			default:
				break;
			}
			
			if(spvc.getIdStatoPropVariazCred()==3) {
				spvc.setConfirmable(false); 
			}else {
				spvc.setConfirmable(true);
			}
			spvc.setCodiceVisualizzatoProgetto(rs.getString("CODICE_VISUALIZZATO"));
			spvc.setNdg(rs.getString("NDG"));
			
			spvc.setCodiceFondoFinpis(rs.getString("CODICE_FONDO_FINPIS"));
			
			String flagAccettatoRifiutato = null;
			
			flagAccettatoRifiutato = rs.getString("FLAG_CONF_NUOVO_STA_CRE");
			if(flagAccettatoRifiutato!=null) {				
				switch (flagAccettatoRifiutato.trim()) {
				case "N":
					spvc.setFlagAccettatoRifiutato("Rifiutato");
					break;
				case "S":
					spvc.setFlagAccettatoRifiutato("Accettato");
					break;
					
				default:
					break;
				}
			}
			
		return spvc;
	}

}
