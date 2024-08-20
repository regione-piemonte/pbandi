/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.TracciamentoDAO;
import it.csi.pbandi.pbgestfinbo.util.Constants;

@Component
public class TracciamentoDAOImpl extends JdbcDaoSupport implements TracciamentoDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	public TracciamentoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	public TracciamentoDAOImpl() {}
	
	@Override
	public Long insertTraccia(String operazione, Long idUtente, String esito) throws DaoException {
		String prf = "[TracciamentoDAOImpl::insertTraccia]";
		LOG.info(prf + " BEGIN");
		
		Long idTracciamento = null;
		Long idOperazione = null;
		
		try {
			
			idTracciamento = getIdTracciamento();
			LOG.info(prf + " idTracciamento="+idTracciamento);
			
			idOperazione = getIdOperazione(operazione);
			LOG.info(prf + " idOperazione="+idOperazione);
			
			if(idOperazione!=null) {
				String query = " INSERT INTO PBANDI_T_TRACCIAMENTO "
						+ "(ID_OPERAZIONE , DT_TRACCIAMENTO , ID_TRACCIAMENTO , ID_UTENTE , FLAG_ESITO) "
						+ "VALUES(?, sysdate, ?, ?, ? )";
				
	//			insert into PBANDI_T_TRACCIAMENTO (ID_OPERAZIONE , DT_TRACCIAMENTO , ID_TRACCIAMENTO , ID_UTENTE , FLAG_ESITO) values (478 , (to_date('19/11/2020 17:26:00','dd/MM/yyyy hh24:mi:ss')) , 148313410 , -1 , 'S')
				
				LOG.debug(prf + "  param [idTracciamento] = " + idTracciamento);
				LOG.debug(prf + "  param [idOperazione] = " + idOperazione);
				LOG.debug(prf + "  param [idUtente] = " + idUtente);
				LOG.debug(prf + "  param [esito] = " + esito);
				LOG.debug(prf + "  query = " + query);
				
				getJdbcTemplate().update(query, new Object[] { idOperazione, idTracciamento, idUtente, esito});
				
				LOG.debug(prf + "  Inserimento effettuato. Stato = SUCCESS ");
				
			}else {
				LOG.debug(prf + "  Operazione ["+operazione+"] non tracciabile....");
			}
		}
		catch (DataIntegrityViolationException ex) {
			LOG.debug(prf + "  Integrity Keys Violation ");
			if (ex instanceof DuplicateKeyException) {
				throw new DaoException("Chiave Duplicata", ex.getMostSpecificCause());
			}
			throw new DaoException("Nessun dato in base alla richiesta", ex);
		} catch (Throwable ex) {
			LOG.error(prf + " esecuzione query Failed ", ex);
			throw new DaoException("Errore di sistema", ex);
		} finally {
			LOG.debug(prf + " END ");
		}
		
		return idTracciamento;
	}

	private Long getIdOperazione(String operazione) throws DaoException {
		String prf = "[TracciamentoDAOImpl::getIdOperazione] ";
		LOG.info(prf + " BEGIN");
		Long id = null;
		
		try {
			
			String sql = "select  ID_OPERAZIONE from  PBANDI_C_OPERAZIONE "
					+ "where DESC_FISICA = ? "
					+ "and FLAG_DA_TRACCIARE = 'S' "
					+ "and DT_INIZIO_VALIDITA < sysdate "
					+ "and (DT_FINE_VALIDITA is null OR DT_FINE_VALIDITA > sysdate) " ;
			LOG.debug(prf + sql);
			
			id = (Long)getJdbcTemplate().queryForObject(sql.toString(),new Object[] {operazione}, Long.class);
			
		} catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_C_OPERAZIONE", e);
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_C_OPERAZIONE", e);
			throw new DaoException("DaoException while trying to read PBANDI_C_OPERAZIONE", e);
		}
		finally {
			LOG.info(prf + " END");
		}
		
		return id;
	}

	private Long getIdTracciamento() throws DaoException {
		String prf = "[TracciamentoDAOImpl::getIdTracciamento] ";
		LOG.info(prf + " BEGIN");
		Long id = null;
		
		try {
			
			String sql = "select SEQ_PBANDI_T_TRACCIAMENTO.nextval from dual " ;
			LOG.debug(prf + sql);
			
			id = (Long)getJdbcTemplate().queryForObject(sql.toString(), Long.class);
			
		} catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read SEQ_PBANDI_T_TRACCIAMENTO", e);
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read SEQ_PBANDI_T_TRACCIAMENTO", e);
			throw new DaoException("DaoException while trying to read SEQ_PBANDI_T_TRACCIAMENTO", e);
		}
		finally {
			LOG.info(prf + " END");
		}
		
		return id;
	}


	@Override
	public void updateTraccia(Long idTracciamento, long time) throws DaoException {
		String prf = "[TracciamentoDAOImpl::updateTraccia] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idTracciamento="+idTracciamento);
		
		// time e' in ms
		double t = time/1000;

		try {
			
			String query = " UPDATE  PBANDI_T_TRACCIAMENTO SET durata = ? WHERE id_tracciamento = ?";
			
			LOG.debug(prf + "  param [idTracciamento] = " + idTracciamento);
			LOG.debug(prf + "  query = " + query);
			
			getJdbcTemplate().update(query, new Object[] { t, idTracciamento});
			
			LOG.debug(prf + "  Inserimento effettuato. Stato = SUCCESS ");
				
		} catch (DataIntegrityViolationException ex) {
			LOG.debug(prf + "  Integrity Keys Violation ");
			if (ex instanceof DuplicateKeyException) {
				throw new DaoException("Chiave Duplicata", ex.getMostSpecificCause());
			}
			throw new DaoException("Nessun dato in base alla richiesta", ex);
		} catch (Throwable ex) {
			LOG.error(prf + " esecuzione query Failed ", ex);
			throw new DaoException("Errore di sistema", ex);
		} finally {
			LOG.debug(prf + " END ");
		}
	}

	
	
}
