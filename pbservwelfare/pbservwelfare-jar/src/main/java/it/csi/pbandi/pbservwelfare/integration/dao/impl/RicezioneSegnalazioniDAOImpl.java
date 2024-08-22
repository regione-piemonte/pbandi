/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservwelfare.dto.InfoDaNumeroDomanda;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.RicezioneSegnalazioniDAO;
import it.csi.pbandi.pbservwelfare.util.Constants;

public class RicezioneSegnalazioniDAOImpl extends BaseDAO implements RicezioneSegnalazioniDAO {
	
private Logger LOG;
	
	public RicezioneSegnalazioniDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	@Override
	public InfoDaNumeroDomanda getInfo(String numeroDomanda) {
		String prf = "[RicezioneSegnalazioniDAOImpl::getInfo]";
		LOG.info(prf + " BEGIN");
		InfoDaNumeroDomanda info = null; 
		LOG.info(prf + ", numeroDomanda=" + numeroDomanda);
		try {
			String sql = "SELECT PTD.ID_DOMANDA,																	\n" + 
					"       PTP.ID_PROGETTO,																		\n" + 
					"       PTP.CODICE_VISUALIZZATO,																\n" + 
					"       PRSP.ID_SOGGETTO,																		\n" + 
					"       PRSP.ID_PERSONA_FISICA,																	\n" + 
					"       PTS.CODICE_FISCALE_SOGGETTO,															\n" + 
					"       PTPF.NOME,																				\n" + 
					"       PTPF.COGNOME																			\n" + 
					"FROM PBANDI_T_DOMANDA PTD																		\n" + 
					"JOIN PBANDI_T_PROGETTO PTP ON PTP.ID_DOMANDA = PTD.ID_DOMANDA									\n" + 
					"JOIN PBANDI_R_SOGGETTO_PROGETTO PRSP ON PRSP.ID_PROGETTO = PTP.ID_PROGETTO						\n" + 
					"JOIN PBANDI_T_SOGGETTO PTS ON PTS.ID_SOGGETTO = PRSP.ID_SOGGETTO								\n" + 
					"JOIN PBANDI_T_PERSONA_FISICA PTPF ON PTPF.ID_PERSONA_FISICA = PRSP.ID_PERSONA_FISICA			\n" + 
					"WHERE PTD.NUMERO_DOMANDA= :numeroDomanda														\n" + 
					"  AND PRSP.ID_TIPO_BENEFICIARIO <> 4															\n" + 
					"  AND PRSP.ID_TIPO_ANAGRAFICA = 1";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

			info = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				InfoDaNumeroDomanda infoDaNumeroDomanda = null; 
				while (rs.next()) {
					infoDaNumeroDomanda = new InfoDaNumeroDomanda();
					infoDaNumeroDomanda.setIdDomanda(rs.getInt("ID_DOMANDA"));
					infoDaNumeroDomanda.setIdProgetto(rs.getInt("ID_PROGETTO"));
					infoDaNumeroDomanda.setCodiceVisualizzato(rs.getString("CODICE_VISUALIZZATO"));
					infoDaNumeroDomanda.setIdSoggetto(rs.getInt("ID_SOGGETTO"));
					infoDaNumeroDomanda.setIdPersonaFisica(rs.getInt("ID_PERSONA_FISICA"));
					infoDaNumeroDomanda.setCodiceFiscaleSoggetto(rs.getString("CODICE_FISCALE_SOGGETTO"));
					infoDaNumeroDomanda.setNome(rs.getString("NOME"));
					infoDaNumeroDomanda.setCognome(rs.getString("COGNOME"));
				}
				return infoDaNumeroDomanda;
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read InfoDaNumeroDomanda", e);
			throw new ErroreGestitoException("DaoException while trying to read InfoDaNumeroDomanda", e);
		} finally {
			LOG.info(prf + " END");
		}

		return info;
	}

	@Override
	public HashMap<String, String> getInfoTemplate(String descBreveTemplate) {
		String prf = "[RicezioneSegnalazioniDAOImpl::getInfoTemplate]";
		LOG.info(prf + " BEGIN");
		HashMap<String, String> infoTemplate = new HashMap<String, String>(); 
		LOG.info(prf + ", descBreveTemplate=" + descBreveTemplate);
		try {
			String sql = "SELECT ID_TEMPLATE_NOTIFICA,							\n" + 
					"       COMP_SUBJECT,										\n" + 
					"       COMP_MESSAGE										\n" + 
					"FROM PBANDI_D_TEMPLATE_NOTIFICA PDTN						\n" + 
					"WHERE DESCR_BREVE_TEMPLATE_NOTIFICA= :descBreveTemplate";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("descBreveTemplate", descBreveTemplate, Types.VARCHAR);

			infoTemplate = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				HashMap<String, String> map = null; 
				while (rs.next()) {
					map = new HashMap<String, String>();
					map.put("idTemplate", String.valueOf(rs.getInt("ID_TEMPLATE_NOTIFICA")));
					map.put("oggettoNotifica", rs.getString("COMP_SUBJECT"));
					map.put("messaggioNotifica", rs.getString("COMP_MESSAGE"));
				}
				return map;
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read InfoDaNumeroDomanda", e);
			throw new ErroreGestitoException("DaoException while trying to read PBANDI_D_TEMPLATE_NOTIFICA", e);
		} finally {
			LOG.info(prf + " END");
		}

		return infoTemplate;
	}

	@Override
	public int insertNotifica(Integer idProgetto, String subjectNotifica, String messageNotifica,
			Integer idTemplateNotifica) {
		String prf = "[RicezioneSegnalazioniDAOImpl::insertNotifica]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto=" + idProgetto + ", subjectNotifica=" + subjectNotifica + ", messageNotifica="
				+ messageNotifica + ", idTemplateNotifica=" + idTemplateNotifica);
		int result = -1;

		try {
			// Creo il record sulla PBANDI_T_FOLDER
	        String sql = "INSERT INTO PBANDI_T_NOTIFICA_PROCESSO PTNP(ID_NOTIFICA, ID_PROGETTO, ID_RUOLO_DI_PROCESSO_DEST, SUBJECT_NOTIFICA, MESSAGE_NOTIFICA, STATO_NOTIFICA, ID_UTENTE_MITT, DT_NOTIFICA, ID_TEMPLATE_NOTIFICA)	\n" + 
	        		"VALUES(SEQ_PBANDI_T_NOTIFICA_PROCESSO.NEXTVAL,			\n" + 
	        		"       ?,												\n" + 
	        		"       1,												\n" + 
	        		"       ?,												\n" + 
	        		"       ?,												\n" + 
	        		"       'I',											\n" + 
	        		"       -20,											\n" + 
	        		"       SYSDATE,										\n" + 
	        		"       ?)";
	        
	        result = this.namedParameterJdbcTemplate.getJdbcOperations().update(
					sql,
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setInt(1, idProgetto);
							ps.setString(2, subjectNotifica);
							getLobHandler().getLobCreator().setClobAsString(ps, 3, messageNotifica);
							ps.setInt(4, idTemplateNotifica);
						}
					});
			LOG.info("insertNotifica record modified: " + result);

		} catch (Exception e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return result;
	}
	
}
