/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbwebbo.dto.IntegerValueDTO;
import it.csi.pbandi.pbwebbo.dto.StringValueDTO;
import it.csi.pbandi.pbwebbo.dto.amministrativoContabile.MonitoringAmministrativoContabileDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.AnagraficaDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.BancaSuggestVO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.EstremiBancariEstesiDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.ModAgevEstremiBancariDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.ModalitaAgevolazioneDTO;
import it.csi.pbandi.pbwebbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbwebbo.integration.dao.AmministrativoContabileDAO;
import it.csi.pbandi.pbwebbo.integration.dao.ConfigurazioneBandoLineaDAO;
import it.csi.pbandi.pbwebbo.util.BeanRowMapper;
import it.csi.pbandi.pbwebbo.util.Constants;


@Service
public class AmministrativoContabileDAOImpl extends JdbcDaoSupport implements AmministrativoContabileDAO {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	public AmministrativoContabileDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	
	// 7.1	Algoritmo Tracciamento chiamata al servizio esposto da amministrativo contabile
	@Override
	public Long trackCallToAmministrativoContabilePre(
															Long idServizio, // 7 = AnagraficaFondo.Anagrafica, 8 = AnagraficaFondo.IbanFondo
															Long idUtente,
															String modalitaChiamata, // I = insert, U = update
															String parametriInput, // Concatenzaione chiave-valore
															String parametriOutput,
															Long idEntita, // Valorizzato a seconda del servizio chiamato, es: 5 se è stato chiamato il servizio AnagraficaFondo.Anagrafica, 128 se è stato chiamato il servizio AnagraficaFondo.IbanFondo
															Long idTarget // Valorizzato con l’identificativo relative all’ID_ENTITA, es: Coincide con l’ID_BANDO se è stato chiamato il servizio AnagraficaFondo.Anagrafica, Coincide con ID_ESTREMI_BANCARI se è stato chiamato il servizio AnagraficaFondo.IbanFondo

		) throws Exception {
			

			String className = Thread.currentThread().getStackTrace()[1].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			String prf = "[" + className + "::" + methodName + "]";
			logger.info(prf + " BEGIN");	
			
			Long newId;
			
			try {
				
				
				String sqlSeq = "select SEQ_PBANDI_T_MON_SERV.nextval from dual ";
				logger.info("\n"+sqlSeq.toString());
				newId = (Long) getJdbcTemplate().queryForObject(sqlSeq.toString(), Long.class);
				logger.info("Nuovo id PBANDI_T_NEWS = "+newId);
				
				String sql;
				sql =   "INSERT INTO  PBANDI_T_MON_SERV (\r\n"
						+ "	ID_MONIT,\r\n"
						+ "	ID_SERVIZIO,\r\n"
						+ "	ID_UTENTE,\r\n"
						+ "	MODALITA_CHIAMATA,\r\n"
						+ "	DATETIME_INIZIO_CHIAMATA,\r\n"
						+ "	PARAMETRI_DI_INPUT,\r\n"
						+ "	PARAMETRI_DI_OUTPUT,\r\n"
						+ "	ID_ENTITA,\r\n"
						+ "	ID_TARGET \r\n"
						+ ")\r\n"
						+ "VALUES (\r\n"
						+ "	?, \r\n"
						+ "	?, \r\n"
						+ "	?, \r\n"
						+ "	?, \r\n"
						+ "	SYSDATE, \r\n"
						+ "	?, \r\n"
						+ "	?, \r\n"
						+ "	?,\r\n"
						+ "	?\r\n"
						+ ")";
				
				logger.info("\n" + sql + "\n");
				
				Object[] args = new Object[] { 
												newId,
												idServizio, 
												idUtente,
												modalitaChiamata, 
												parametriInput, 
												parametriOutput,
												idEntita, 
												idTarget 
				};
				
				 getJdbcTemplate().update(sql, args);	


			} catch (Exception e) {
				String msg = "Errore l'esecuzione della query";
				logger.error(prf + msg, e);
				throw new Exception(msg, e);
			} finally {
				logger.info(prf + " END");
			}
			
			
			
			return newId;
			
		}
	
	@Override
	public void trackCallToAmministrativoContabilePost(Long idMonitAmmCont, String esito, String codErrore, String msgErr, String parametriOutput) throws Exception {
		
		

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");	
		
		
		
		try {		
			
			
			String sql;
			sql =   "UPDATE PBANDI_T_MON_SERV\r\n"
					+ "SET \r\n"
					+ "	ESITO = :ESITO,\r\n"
					+ "	CODICE_ERRORE = :CODICE_ERRORE,\r\n"
					+ "	MESSAGGIO_ERRORE = :MESSAGGIO_ERRORE,\r\n"
					+ "	DATETIME_FINE_CHIAMATA = SYSDATE,\r\n"
					+ "	PARAMETRI_DI_OUTPUT = :PARAMETRI_DI_OUTPUT \r\n"
					+ "WHERE ID_MONIT = :ID_MONIT \r\n";
			
			logger.info("\n" + sql + "\n");
			
			Object[] args = new Object[] {esito, codErrore, msgErr, parametriOutput, idMonitAmmCont};
			
			 getJdbcTemplate().update(sql, args);	


		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}
		
		
	}
	
	
	public MonitoringAmministrativoContabileDTO getTrackCallToAmministartivoContabile( Long idAmmCont) throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");	
		
		MonitoringAmministrativoContabileDTO result;
		
		try {			
			
			
			String sql;
			sql =   "SELECT * \r\n"
					+ "from PBANDI_T_MON_SERV\r\n"
					+ "WHERE ID_MONIT = ?";
			
			logger.info("\n" + sql + "\n");
			
			Object[] args = new Object[] {idAmmCont};
			
			result = (MonitoringAmministrativoContabileDTO) getJdbcTemplate().queryForObject(sql, args, new BeanRowMapper( MonitoringAmministrativoContabileDTO.class));


		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}
		
		
		return result;
		
	}

	
	
	

}
