/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.integration.dao.IterAutorizzativiDAO;
import it.csi.pbandi.pbservizit.util.Constants;

public class IterAutorizzativiDAOImpl extends JdbcDaoSupport implements IterAutorizzativiDAO {

	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Transactional
	@Override
	public String avviaIterAutorizzativo(Long idTipoIter, Long idEntita, Long idTarget, Long idProgetto, Long idUtente) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		//check permessi
		boolean isCheckUtente = false;
		try {
			//recupero l'incarico dell'idUtente passato e confronto con l'incarico di livello 1 per l'idTipoIter passato
			String getIncaricoIter =
					"SELECT ID_INCARICO \n" +
					"FROM PBANDI_R_TIPO_STATO_ITER prtsi \n" +
					"WHERE prtsi.ID_TIPO_ITER  = ?\n" +
					"AND ORDINE = 1";
			int incaricoIter = getJdbcTemplate().queryForObject(getIncaricoIter, new Object[]{idTipoIter}, Integer.class);

			String getIncaricoUtente =
					"SELECT pris.ID_INCARICO \n" +
					"FROM PBANDI_R_INCARICO_SOGGETTO pris \n" +
					"LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = pris.ID_SOGGETTO \n" +
					"LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_SOGGETTO = pts.ID_SOGGETTO \n" +
					"WHERE ptu.ID_UTENTE = ?\n" +
					"AND id_incarico = ?\n" +
					"AND rownum = 1";
			int incaricoUtente = getJdbcTemplate().queryForObject(getIncaricoUtente, new Object[]{idUtente, incaricoIter}, Integer.class);

			if(incaricoIter == incaricoUtente) {
				isCheckUtente = true;
			}
		} catch (IncorrectResultSizeDataAccessException ignored) {} catch (Exception e) {
			logger.error(e);
		}
		if(!isCheckUtente) {
			return "Utente non abilitato all'avvio iter!";
		}
		//primo passo : creazione del workflow
		BigDecimal idWorkFlow;
		try {
			idWorkFlow = getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_WORK_FLOW.nextval FROM dual ", BigDecimal.class);

			String insert =
					"INSERT INTO PBANDI_T_WORK_FLOW (\n" +
					"ID_WORK_FLOW,\n" +
					"ID_TIPO_ITER,\n" +
					"ID_UTENTE_INS,\n" +
					"DT_INSERIMENTO\n" +
					") VALUES (?,?,?, CURRENT_DATE)";

			getJdbcTemplate().update(
					insert,
					idWorkFlow,
					idTipoIter,
					idUtente
			);
		} catch (Exception e) {
			logger.info(e);
			idWorkFlow = null;
		}
		if(idWorkFlow==null) {
			return "Errore nell'inserimento del workflow!";
		}
		//secondo passo inserimento tabella PBANDI_T_DETT_WORK_FLOW
		BigDecimal idDettWorkFlow;
		try {
			idDettWorkFlow = getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_DETT_WORK_FLOW.nextval FROM dual ", BigDecimal.class);

			// recupero lo stato iter per il primo passo,
			Long idStatoIter = getJdbcTemplate().queryForObject(
					"SELECT ID_STATO_ITER \n" +
					"FROM PBANDI_R_TIPO_STATO_ITER prtsi \n" +
					"WHERE prtsi.ID_TIPO_ITER = ? \n" +
					"AND prtsi.ORDINE = 1",
					new Object[]{idTipoIter},
					Long.class
			);

			//inserimento per il passo 1
			String insert =
					"INSERT INTO PBANDI_T_DETT_WORK_FLOW (\n" +
					"ID_DETT_WORK_FLOW,\n" +
					"ID_WORK_FLOW,\n" +
					"ID_STATO_ITER,\n" +
					"DT_APPROVAZIONE,\n" +
					"ID_UTENTE_INS,\n" +
					"ID_UTENTE_AGG,\n" +
					"DT_INSERIMENTO,\n" +
					"DT_AGGIORNAMENTO\n" +
					") VALUES (?,?,?,CURRENT_DATE,?,?,CURRENT_DATE,CURRENT_DATE)";
			getJdbcTemplate().update(insert,
					idDettWorkFlow,
					idWorkFlow,
					idStatoIter,
					idUtente,
					idUtente);

			// recupero lo stato iter per il secondo passo,
			idStatoIter = getJdbcTemplate().queryForObject(
					"SELECT ID_STATO_ITER \n" +
					"FROM PBANDI_R_TIPO_STATO_ITER prtsi \n" +
					"WHERE prtsi.ID_TIPO_ITER = ? \n" +
					"AND prtsi.ORDINE = 2",
					new Object[]{idTipoIter},
					Long.class);

			idDettWorkFlow = getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_DETT_WORK_FLOW.nextval FROM dual ", BigDecimal.class);

			String insertSecondoPasso =
					"INSERT INTO PBANDI_T_DETT_WORK_FLOW (\n" +
					"ID_DETT_WORK_FLOW,\n" +
					"ID_WORK_FLOW,\n" +
					"ID_STATO_ITER,\n" +
					"ID_UTENTE_INS,\n" +
					"DT_INSERIMENTO\n" +
					") VALUES (?,?,?,?,CURRENT_DATE)";
			getJdbcTemplate().update(insertSecondoPasso,
					idDettWorkFlow,
					idWorkFlow,
					idStatoIter,
					idUtente);

		} catch (Exception e) {
			logger.error(e);
			idDettWorkFlow = null;
		}
		if(idDettWorkFlow==null) {
			return "Errore nell'inserimento del dettaglio workflow!";
		}
		//creazone della relazione iter-entit-target
		BigDecimal idWorkFlowEntita;
		try {
			idWorkFlowEntita = getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_WORK_FLOW_ENTITA.nextval FROM dual ", BigDecimal.class);

			String insert =
					"INSERT INTO PBANDI_T_WORK_FLOW_ENTITA (\n" +
					"ID_WORK_FLOW_ENTITA,\n" +
					"ID_WORK_FLOW,\n" +
					"ID_ENTITA,\n" +
					"ID_TARGET,\n" +
					"ID_PROGETTO,\n" +
					"ID_UTENTE_INS,\n" +
					"DT_INSERIMENTO\n" +
					") VALUES(?,?,?,?,?,?, CURRENT_DATE)";
			getJdbcTemplate().update(insert,
					idWorkFlowEntita,
					idWorkFlow,
					idEntita,
					idTarget,
					idProgetto,
					idUtente);

		} catch (Exception e) {
			logger.info(e);
			idWorkFlowEntita = null;
		}
		if (idWorkFlowEntita==null) {
			return "Errore nell'inserimento dell'entita workflow!";
		}

		LOG.info(prf + "END");
		return "";
	}
}
