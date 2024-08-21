/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.integration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.pbandi.pbservrest.dto.RecuperoNote;
import it.csi.pbandi.pbservrest.exception.ErroreGestitoException;
import it.csi.pbandi.pbservrest.exception.RecordNotFoundException;
import it.csi.pbandi.pbservrest.integration.dao.BaseDAO;
import it.csi.pbandi.pbservrest.integration.dao.GestioneNoteDAO;
import it.csi.pbandi.pbservrest.model.SoggettoDomandaTmp;
import it.csi.pbandi.pbservrest.util.Constants;
import org.apache.commons.lang3.StringUtils;
@Repository
public class GestioneNoteDAOImpl extends BaseDAO implements GestioneNoteDAO {

	
	private Logger LOG;

	public GestioneNoteDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}
	@Override
	public List<RecuperoNote> getRecuperoNote(String codiceDomanda, String codiceBeneficiario, String agevolazione,
			String codiceFondo, String codiceFiscaleBeneficiario) {
		String prf = "[GestioneNoteDAOImpl::getRecuperoNote]";
		LOG.info(prf + " BEGIN");
		List<RecuperoNote> noteList;
		String elencoNoteSql = "";
		MapSqlParameterSource param1 = new MapSqlParameterSource(); 
		try {
			if(codiceFiscaleBeneficiario != null && StringUtils.isNotBlank(codiceFiscaleBeneficiario) ) {
				 elencoNoteSql = "SELECT\r\n"
						+ "   PTS.CODICE_FISCALE_SOGGETTO, ptn.DT_INIZIO_VALIDITA AS data_nota,\r\n"
						+ " DBMS_LOB.SUBSTR(REPLACE(\r\n"
						+ "    REPLACE(TESTO_NOTA, '</TESTO_NOTA>', ''),\r\n"
						+ "    '<TESTO_NOTA>',\r\n"
						+ "     ''\r\n"
						+ "  ), 4000, 1) AS testo_nota,\r\n"
						+ "    ptn.ID_UTENTE_INS,\r\n"
						+ "    PTPF.NOME,\r\n"
						+ "    PTPF.COGNOME, \r\n"
						+ "    'FINPIS' AS PROVENIENZA\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_T_NOTE ptn\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptn.ID_PROGETTO\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts on pts.ID_SOGGETTO = prsp.ID_SOGGETTO \r\n"
						+ "    AND prsp.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = ptn.ID_PROGETTO \r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA td ON td.ID_DOMANDA  = ptp.ID_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptn.ID_UTENTE_INS\r\n"
						+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptu.ID_SOGGETTO = PTPF.ID_SOGGETTO\r\n"
						+ "    AND PTPF.ID_PERSONA_FISICA IN (\r\n"
						+ "        SELECT\r\n"
						+ "            MAX(ID_PERSONA_FISICA)\r\n"
						+ "        FROM\r\n"
						+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
						+ "        GROUP BY\r\n"
						+ "            ptpf2.ID_SOGGETTO\r\n"
						+ "    )\r\n"
						+ "WHERE\r\n"
						+ "    prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND PTS.CODICE_FISCALE_SOGGETTO = :codFiscaleSoggetto \r\n"
						+ "    AND ptn.DT_FINE_VALIDITA IS NULL\r\n"
						+ " UNION \r\n"
						+ " SELECT PTS.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    PVG.DT_INSERIMENTO  AS data_nota,\r\n"
						+ "   PVG.NOTE  AS TESTO_NOTA ,\r\n"
						+ "    PVG.ID_UTENTE_INS ,\r\n"
						+ "    PTPF.NOME,\r\n"
						+ "    PTPF.COGNOME, \r\n"
						+ "    PVG.NOME_ENTITA_PROVENIENZA AS PROVENIENZA\r\n"
						+ " FROM PBANDI_V_NOTE_GENERALI PVG\r\n"
						+ " LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = PVG.ID_PROGETTO \r\n"
						+ " LEFT JOIN PBANDI_T_SOGGETTO pts on pts.ID_SOGGETTO = prsp.ID_SOGGETTO \r\n"
						+ "LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = PVG.ID_PROGETTO \r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA td ON td.ID_DOMANDA  = ptp.ID_DOMANDA \r\n"
						+ " LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = PVG.ID_UTENTE_INS\r\n"
						+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptu.ID_SOGGETTO = PTPF.ID_SOGGETTO\r\n"
						+ "    AND PTPF.ID_PERSONA_FISICA IN (\r\n"
						+ "        SELECT\r\n"
						+ "            MAX(ID_PERSONA_FISICA)\r\n"
						+ "        FROM\r\n"
						+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
						+ "        GROUP BY\r\n"
						+ "            ptpf2.ID_SOGGETTO\r\n"
						+ "    )\r\n"
						+ "WHERE pts.CODICE_FISCALE_SOGGETTO= :codFiscaleSoggetto \r\n"
						+ "ORDER BY data_nota DESC";
				
				LOG.info(elencoNoteSql);
				param1.addValue("codFiscaleSoggetto", codiceFiscaleBeneficiario, Types.VARCHAR);
			}else {
				elencoNoteSql ="SELECT\r\n"
						+ "   td.NUMERO_DOMANDA, ptn.DT_INIZIO_VALIDITA AS data_nota,\r\n"
						+ "     DBMS_LOB.SUBSTR(REPLACE(\r\n"
						+ "    REPLACE(TESTO_NOTA, '</TESTO_NOTA>', ''),\r\n"
						+ "    '<TESTO_NOTA>',\r\n"
						+ "    ''\r\n"
						+ "  ), 4000, 1) AS testo_nota,\r\n"
						+ "    ptn.ID_UTENTE_INS,\r\n"
						+ "    PTPF.NOME,\r\n"
						+ "    PTPF.COGNOME, \r\n"
						+ "    'FINPIS' AS PROVENIENZA\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_T_NOTE ptn\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptn.ID_PROGETTO\r\n"
						+ "    AND prsp.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = ptn.ID_PROGETTO \r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA td ON td.ID_DOMANDA  = ptp.ID_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = ptn.ID_UTENTE_INS\r\n"
						+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptu.ID_SOGGETTO = PTPF.ID_SOGGETTO\r\n"
						+ "    AND PTPF.ID_PERSONA_FISICA IN (\r\n"
						+ "        SELECT\r\n"
						+ "            MAX(ID_PERSONA_FISICA)\r\n"
						+ "        FROM\r\n"
						+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
						+ "        GROUP BY\r\n"
						+ "            ptpf2.ID_SOGGETTO\r\n"
						+ "    )\r\n"
						+ "WHERE\r\n"
						+ "    prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND td.NUMERO_DOMANDA=  :numeroDomanda \r\n"
						+ "    AND ptn.DT_FINE_VALIDITA IS NULL\r\n"
						+ " UNION \r\n"
						+ " SELECT td.NUMERO_DOMANDA,\r\n"
						+ "    PVG.DT_INSERIMENTO  AS data_nota,\r\n"
						+ "   PVG.NOTE  AS TESTO_NOTA ,\r\n"
						+ "    PVG.ID_UTENTE_INS ,\r\n"
						+ "    PTPF.NOME,\r\n"
						+ "    PTPF.COGNOME, \r\n"
						+ "    PVG.NOME_ENTITA_PROVENIENZA AS PROVENIENZA\r\n"
						+ " FROM PBANDI_V_NOTE_GENERALI PVG\r\n"
						+ " LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = PVG.ID_PROGETTO \r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA td ON td.ID_DOMANDA  = ptp.ID_DOMANDA \r\n"
						+ " LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = PVG.ID_UTENTE_INS\r\n"
						+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptu.ID_SOGGETTO = PTPF.ID_SOGGETTO\r\n"
						+ "    AND PTPF.ID_PERSONA_FISICA IN (\r\n"
						+ "        SELECT\r\n"
						+ "            MAX(ID_PERSONA_FISICA)\r\n"
						+ "        FROM\r\n"
						+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
						+ "        GROUP BY\r\n"
						+ "            ptpf2.ID_SOGGETTO\r\n"
						+ "    )\r\n"
						+ "WHERE td.NUMERO_DOMANDA= :numeroDomanda \r\n"
						+ "ORDER BY data_nota DESC" ;
				param1.addValue("numeroDomanda", codiceDomanda, Types.VARCHAR);
				LOG.info(elencoNoteSql);
			}
			
			
			noteList = this.namedParameterJdbcTemplate.query(elencoNoteSql, param1, new ResultSetExtractor<List<RecuperoNote>>() {
				
				public List<RecuperoNote> extractData(ResultSet rs2) throws SQLException, DataAccessException {
					
					List<RecuperoNote> elencoNote = new ArrayList<>();
					
					
					while(rs2.next()) {
						
						RecuperoNote ris = new RecuperoNote();
						ris.setUtenteInserimento(rs2.getString("NOME") +" "+ rs2.getString("COGNOME"));
						if(codiceFiscaleBeneficiario != null && StringUtils.isNotBlank(codiceFiscaleBeneficiario) ) {
							ris.setCodiceFiscaleBeneficiario(rs2.getString("CODICE_FISCALE_SOGGETTO"));
						}
						ris.setAreaNota(rs2.getString("PROVENIENZA"));
						ris.setNota(rs2.getString("TESTO_NOTA"));
						ris.setDataInserimento(rs2.getDate("DATA_NOTA"));
						elencoNote.add(ris);
					}

					
					return elencoNote;
				}
			});
			
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read getRecuperoNote", e);
			throw new ErroreGestitoException("DaoException while trying to read getRecuperoNote", e);
		} finally {
			LOG.info(prf + " END");
		}
		return noteList;
	
	}

}
