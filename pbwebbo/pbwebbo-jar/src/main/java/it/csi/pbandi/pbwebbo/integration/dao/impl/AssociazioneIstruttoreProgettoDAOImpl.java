/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.csi.pbandi.pbwebbo.integration.dao.AssociazioneIstruttoreProgettoDAO;
import it.csi.pbandi.pbwebbo.integration.vo.BandoLineaAssociatiAIstruttoreVO;
import it.csi.pbandi.pbwebbo.integration.vo.BandoLineaDaAssociareAIstruttoreVO;
import it.csi.pbandi.pbwebbo.util.Constants;

@Component("associazioneIstruttoreProgettoDAO")
public class AssociazioneIstruttoreProgettoDAOImpl implements AssociazioneIstruttoreProgettoDAO {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	private DataSource dataSource;
	
	@Autowired
	public AssociazioneIstruttoreProgettoDAOImpl(DataSource dataSource) throws Exception {
		this.dataSource = dataSource;
	}
		
	
	private class PbandolineaInterventoRowMapper implements RowMapper<BandoLineaAssociatiAIstruttoreVO> {

		public BandoLineaAssociatiAIstruttoreVO mapRow(ResultSet rs, int rowNum) throws SQLException {

			BandoLineaAssociatiAIstruttoreVO obj = new BandoLineaAssociatiAIstruttoreVO();
			obj.setNomeBandolinea(rs.getString("NOME_BANDO_LINEA"));
			obj.setProgBandoLina(rs.getLong("PROGR_BANDO_LINEA_INTERVENTO"));
			
			return obj;
			
		}
	}
	
	@Override
	public List<BandoLineaAssociatiAIstruttoreVO> findBandoLineaIstruttore(Long idSoggetto, Boolean isIstruttoreAffidamenti) {
		
		String prf = "[AssociazioneIstruttoreProgettoDAOImpl::findBandoLineaIstruttore]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + "Parametri in input -> idSoggetto = "+idSoggetto + ", isIstruttoreAffidamenti = " + isIstruttoreAffidamenti);
		List<BandoLineaAssociatiAIstruttoreVO> listBandoLinea = null;
		
		try {
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT rbl.nome_bando_linea, rbl.progr_bando_linea_intervento \r\n"
					+ "FROM PBANDI_R_SOGG_BANDO_LIN_INT sbl, pbandi_r_bando_linea_intervent rbl, PBANDI_D_TIPO_ANAGRAFICA pdta \r\n"
					+ "WHERE sbl.id_soggetto = :idSoggettoIstruttore AND sbl.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento\r\n"
					+ "AND sbl.ID_TIPO_ANAGRAFICA = pdta.ID_TIPO_ANAGRAFICA ");
			if (isIstruttoreAffidamenti != null && isIstruttoreAffidamenti.equals(Boolean.TRUE)) {
				sqlSelect.append("AND pdta.DESC_BREVE_TIPO_ANAGRAFICA LIKE 'ISTR-AFFIDAMENTI'");
			} else {
				sqlSelect.append("AND pdta.DESC_BREVE_TIPO_ANAGRAFICA IN ('OI-ISTRUTTORE','ADG-ISTRUTTORE')");
			}
			
			LOG.info(sqlSelect);
			listBandoLinea = jdbcTemplate.query(sqlSelect.toString(), new PbandolineaInterventoRowMapper(), new Object[] { idSoggetto });
			
		}catch (Exception e) {
			LOG.error(e);
		}
		
		LOG.info(prf + " END");
		
		return listBandoLinea;
	}
	
	
	private class PBandiRSoggBandoLinIntRowMapper implements RowMapper<BandoLineaAssociatiAIstruttoreVO> {
		
		public BandoLineaAssociatiAIstruttoreVO mapRow(ResultSet rs, int rowNum) throws SQLException {

				BandoLineaAssociatiAIstruttoreVO obj = new BandoLineaAssociatiAIstruttoreVO();
				obj.setNumIstruttoriAssociati(rs.getLong("NUMERO_BANDO_LINEA_INTERVENTO"));
				obj.setProgBandoLina(rs.getLong("PROGR_BANDO_LINEA_INTERVENTO"));
				
				return obj;
			}
		}
		
	@Override
	public BandoLineaAssociatiAIstruttoreVO conteggioIstruttoriAssociatiBandoLinea(Long idSoggetto, Long progBandoLina, Boolean isIstruttoreAffidamenti) {
		
		String prf = "[AssociazioneIstruttoreProgettoDAOImpl::conteggioIstruttoriAssociatiBandoLinea]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idSoggetto = "+idSoggetto+", progBandoLina = "+progBandoLina);
		BandoLineaAssociatiAIstruttoreVO bandoLineaAssociatiAIstruttoreVO = null;
		
		try {
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT COUNT(*) AS NUMERO_BANDO_LINEA_INTERVENTO,sbl.progr_bando_linea_intervento \r\n"
					+ "FROM PBANDI_R_SOGG_BANDO_LIN_INT sbl, PBANDI_D_TIPO_ANAGRAFICA pdta \r\n"
					+ "WHERE sbl.id_soggetto != ? and sbl.progr_bando_linea_intervento = ? \r\n"
					+ "AND sbl.ID_TIPO_ANAGRAFICA = pdta.ID_TIPO_ANAGRAFICA ");
			if (isIstruttoreAffidamenti != null && isIstruttoreAffidamenti.equals(Boolean.TRUE)) {
				sqlSelect.append("AND pdta.DESC_BREVE_TIPO_ANAGRAFICA LIKE 'ISTR-AFFIDAMENTI' \r\n");
			} else {
				sqlSelect.append("AND pdta.DESC_BREVE_TIPO_ANAGRAFICA IN ('OI-ISTRUTTORE','ADG-ISTRUTTORE') \r\n");
			}
			sqlSelect.append("group by sbl.progr_bando_linea_intervento");
			LOG.info(sqlSelect);
			
			bandoLineaAssociatiAIstruttoreVO = jdbcTemplate.queryForObject(sqlSelect.toString(), new PBandiRSoggBandoLinIntRowMapper(), new Object[] { idSoggetto, progBandoLina });
			
		}catch (EmptyResultDataAccessException ed) {
			bandoLineaAssociatiAIstruttoreVO = new BandoLineaAssociatiAIstruttoreVO();
			bandoLineaAssociatiAIstruttoreVO.setNumIstruttoriAssociati(new Long(0));
		}
		catch (Exception e) {
			LOG.error(e);
		}
		
		LOG.debug(prf + " END");
		
		return bandoLineaAssociatiAIstruttoreVO;
	
	}
		
		
	//Al click sul numero degli istruttori associati viene, sempre visualizzato a video, il nome e il cognome dell'istruttore
	private static final String DATI_ISTRUTTORI_ASSOCIATI = ""
																+ "SELECT DISTINCT pf.cognome, pf.nome "
																+ " FROM PBANDI_R_SOGG_BANDO_LIN_INT sbl, pbandi_t_soggetto sog, pbandi_t_persona_fisica pf "
																+ " WHERE sbl.id_soggetto != ? and sbl.progr_bando_linea_intervento = ? "
																+ " AND sbl.id_soggetto = sog.id_soggetto "
																+ " AND pf.id_soggetto= sog.id_soggetto";
		
	private class PBandiTSoggettoRowMapper implements RowMapper<BandoLineaAssociatiAIstruttoreVO> {
			
			public BandoLineaAssociatiAIstruttoreVO mapRow(ResultSet rs, int rowNum) throws SQLException {

					BandoLineaAssociatiAIstruttoreVO obj = new BandoLineaAssociatiAIstruttoreVO();
					obj.setCognomeIstruttore(rs.getString("COGNOME"));
					obj.setNomneIstruttore(rs.getString("NOME"));
					
					return obj;
				}
		}

	@Override
	public List<BandoLineaAssociatiAIstruttoreVO> dettaglioIstruttore(Long idSoggetto, Long progBandoLina) {
		
		String prf = "[AssociazioneIstruttoreProgettoDAOImpl::dettaglioIstruttore]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idSoggetto = "+idSoggetto+", progBandoLina = "+progBandoLina);
		List<BandoLineaAssociatiAIstruttoreVO> listDettaglioIstruttori = null;
		
		try {
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			listDettaglioIstruttori = jdbcTemplate.query(DATI_ISTRUTTORI_ASSOCIATI, new PBandiTSoggettoRowMapper(), new Object[] { idSoggetto, progBandoLina });
			
		}catch (Exception e) {
			LOG.error(e);
		}
		
		LOG.debug(prf + " END");
		
		return listDettaglioIstruttori;
		
	}

		
	//Estraiamo i bandi linea da associare all’istruttore scelto, escludendo quelli già associati
	private static final String BANDO_LINEA_DA_ASSOCIARE_A_ISTRUTTORE = "SELECT rli.progr_bando_linea_intervento, rli.nome_bando_linea "
																		+ "FROM pbandi_r_bando_linea_intervent rli, PBANDI_R_BANDO_LINEA_ENTE_COMP rblec, "
																		+ "pbandi_t_ente_competenza ec, pbandi_d_ruolo_ente_competenza rec "
																		+ "WHERE rli.progr_bando_linea_intervento= rblec.progr_bando_linea_intervento "
																		+ "AND ec.id_ente_competenza=rblec.id_ente_competenza "
																		+ "AND rec.id_ruolo_ente_competenza= rblec.id_ruolo_ente_competenza "
																		+ "AND rec.id_ruolo_ente_competenza=1 "
																		+ "AND rblec.id_ente_competenza IN"
																		+ "("
																				+"SELECT ec.id_ente_competenza "
																				+ "FROM PBANDI_R_SOGG_TIPO_ANAGRAFICA ros, pbandi_t_soggetto sog,  "
																				+ "PBANDI_R_ENTE_COMPETENZA_SOGG ecs,pbandi_t_ente_competenza ec, pbandi_d_tipo_anagrafica ta "
																				+ "where ta.desc_breve_tipo_anagrafica = ? " // dipende dal ruolo dell'utente 2 ADG-IST-MASTER / 4 OI-IST-MASTER
																				+ "AND sog.id_soggetto=ros.id_soggetto "
																				+ "AND ros.dt_fine_validita is null "
																				+ "AND ecs.id_soggetto = sog.id_soggetto "
																				+ "AND ecs.id_ente_competenza= ec.id_ente_competenza "
																				+ "AND ec.dt_fine_validita is null "
																				+ "AND ecs.dt_fine_validita is null "
																				+ "AND ta.id_tipo_anagrafica= ros.id_tipo_anagrafica "
																				+ "AND sog.id_soggetto = ? "
																		+ ")"
																		+ "AND rli.progr_bando_linea_intervento not in (SELECT sbl.progr_bando_linea_intervento "
																		+ "FROM PBANDI_R_SOGG_BANDO_LIN_INT sbl "
																		+ "WHERE sbl.id_soggetto = ?)";
		
		
	private class PBandiRBandolineaInterventiRowMapper implements RowMapper<BandoLineaDaAssociareAIstruttoreVO> {
			
			public BandoLineaDaAssociareAIstruttoreVO mapRow(ResultSet rs, int rowNum) throws SQLException {
	
				BandoLineaDaAssociareAIstruttoreVO obj = new BandoLineaDaAssociareAIstruttoreVO();
					obj.setProgBandoLinaIntervento(rs.getLong("progr_bando_linea_intervento"));
					obj.setNomeBandolinea(rs.getString("nome_bando_linea"));
					
					return obj;
				}
		}

	@Override
	public List<BandoLineaDaAssociareAIstruttoreVO> getBandoLineaDaAssociareAIstruttoreMaster( Long idSoggetto, String descBreveTipoAnagrafica,  Long idSoggettoIstruttore) {
		
		String prf = "[AssociazioneIstruttoreProgettoDAOImpl::getBandoLineaDaAssociareAIstruttoreMaster]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idSoggetto = "+idSoggetto+", descBreveTipoAnagrafica = "+descBreveTipoAnagrafica+", idSoggettoIstruttore = "+idSoggettoIstruttore);
		List<BandoLineaDaAssociareAIstruttoreVO> listEntiDiCompetenza= null;
		
		try {
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			listEntiDiCompetenza = jdbcTemplate.query(BANDO_LINEA_DA_ASSOCIARE_A_ISTRUTTORE, new PBandiRBandolineaInterventiRowMapper(), 
					                                  new Object[] { descBreveTipoAnagrafica, idSoggetto, idSoggettoIstruttore });
			
		}catch (Exception e) {
			LOG.error(e);
		}
		
		LOG.debug(prf + " END");
		
		return listEntiDiCompetenza;
		
	}

	
	private static final String ASSOCIA_ISTRUTTORE_A_BANDO_LINEA = ""
			+ "insert into PBANDI_R_SOGG_BANDO_LIN_INT (ID_SOGGETTO, PROGR_BANDO_LINEA_INTERVENTO, DT_INIZIO_VALIDITA, DT_FINE_VALIDITA, ID_TIPO_ANAGRAFICA, "
			+ "ID_UTENTE_INS, ID_UTENTE_AGG, DT_INSERIMENTO, DT_AGGIORNAMENTO) "
			+ "values (?, ?, sysdate, null, ?, ?, null, sysdate, null)";
	
	@Override
	public int associaIstruttoreBandoLinea(Long idU, Long idSoggettoIstruttore, Long progBandoLinaIntervento, int idTipoAnagrafica) throws Exception {
		
		String prf = "[AssociazioneIstruttoreProgettoDAOImpl::associaIstruttoreBandoLinea]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idSoggettoIstruttore = "+idSoggettoIstruttore+", progBandoLinaIntervento = "+progBandoLinaIntervento+", idTipoAnagrafica = "+idTipoAnagrafica);
		int result = -1;
			
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		result = jdbcTemplate.update(ASSOCIA_ISTRUTTORE_A_BANDO_LINEA, new Object[] { idSoggettoIstruttore, progBandoLinaIntervento,  idTipoAnagrafica, idU});
		
		LOG.debug(prf + " END");
		
		return result;
		
	}
	
	
	private static final String ELIMINA_ASSOCIAZIONE_ISTRUTTORE_A_BANDO_LINEA = "DELETE FROM PBANDI_R_SOGG_BANDO_LIN_INT t "
																			  + "WHERE t.id_soggetto = ? "
																			  + "AND t.progr_bando_linea_intervento = ?";

	@Override
	public int eliminaAssocizioneIstruttoreBandoLinea(Long idU, Long idSoggettoIstruttore, Long progBandoLinaIntervento) throws Exception {
		
		String prf = "[AssociazioneIstruttoreProgettoDAOImpl::eliminaAssocizioneIstruttoreBandoLinea]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idSoggettoIstruttore = "+idSoggettoIstruttore+", progBandoLinaIntervento = "+progBandoLinaIntervento);
		int result = -1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		result = jdbcTemplate.update(ELIMINA_ASSOCIAZIONE_ISTRUTTORE_A_BANDO_LINEA, new Object[] { idSoggettoIstruttore, progBandoLinaIntervento});

		
		LOG.debug(prf + " END");
		
		return result;
		
	}


}
