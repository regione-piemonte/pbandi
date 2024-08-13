/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbweb.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.exception.RecordNotFoundException;
import it.csi.pbandi.pbweb.integration.dao.IntegrazioniControlliInLocoDAO;
import it.csi.pbandi.pbweb.integration.vo.IntegrazioniControlliInLocoVO;
import it.csi.pbandi.pbweb.util.BeanUtil;
import it.csi.pbandi.pbweb.util.Constants;

@Service
public class IntegrazioniControlliInLocoDAOImpl extends JdbcDaoSupport implements  IntegrazioniControlliInLocoDAO{
	
private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	public IntegrazioniControlliInLocoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);

	 
	}
	@Autowired
	DocumentoManager documentoManager;


	@Override
	public List<IntegrazioniControlliInLocoVO> getAllegati(int idIntegrazione) {
		String prf = "[IntegrazioniControlliInLocoDAOImpl::getAllegati]";
		LOG.info(prf + " BEGIN");
		List<IntegrazioniControlliInLocoVO> dati = new ArrayList<IntegrazioniControlliInLocoVO>();
		LOG.info(prf + "idIntegrazione=" + idIntegrazione);

		try {
			String sql = "SELECT a.id_file, a.FLAG_ENTITA, c.ID_DOCUMENTO_INDEX, c.NOME_FILE, a.ID_FILE_ENTITA, d.DESC_BREVE_TIPO_DOC_INDEX, c.NUM_PROTOCOLLO\r\n"
					+ "FROM PBANDI_T_FILE_ENTITA a	\r\n"
					+ "JOIN PBANDI_T_FILE b ON a.ID_FILE = b.ID_FILE 																\r\n"
					+ "JOIN PBANDI_T_DOCUMENTO_INDEX c ON c.ID_DOCUMENTO_INDEX = b.ID_DOCUMENTO_INDEX	\r\n"
					+ "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX d ON d.ID_TIPO_DOCUMENTO_INDEX = c.ID_TIPO_DOCUMENTO_INDEX\r\n"
					+ "WHERE  a.ID_ENTITA = 569 AND 																				\r\n"
					+ " a.ID_TARGET = :idIntegrazione                                                                          	  \n";
			LOG.debug(sql);
			Object[] args = new Object[] { idIntegrazione };
			int[] types = new int[] { Types.INTEGER };

			dati = getJdbcTemplate().query(sql, args, types,
					new ResultSetExtractor<List<IntegrazioniControlliInLocoVO>>() {

						@Override
						public List<IntegrazioniControlliInLocoVO> extractData(ResultSet rs) throws SQLException {
							List<IntegrazioniControlliInLocoVO>elencoDati = new ArrayList<>();

							while (rs.next()) {
								IntegrazioniControlliInLocoVO item = new IntegrazioniControlliInLocoVO();
								 
								item.setNomeFile(rs.getString("NOME_FILE"));
								item.setIdDocumentoIndex(rs.getInt("ID_DOCUMENTO_INDEX"));
								item.setFlagEntita(rs.getString("FLAG_ENTITA"));
								item.setIdFileEntita(rs.getInt("ID_FILE_ENTITA"));
								item.setDescAlleg(rs.getString("DESC_BREVE_TIPO_DOC_INDEX"));
								item.setNumProtocollo(rs.getString("NUM_PROTOCOLLO"));
								elencoDati.add(item);
							}

							return elencoDati;
						}

					});

		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return dati;
	}



	@Override
	public Boolean deleteAllegato(int idFileEntita) {
		String prf = "[IntegrazioniControlliInLocoDAOImpl::deleteAllegato]";
		LOG.info(prf + " BEGIN");
		int rowsUpdated = 0;
		boolean eseguito = false;
			String query = 
					"DELETE FROM PBANDI_T_FILE_ENTITA ptfe 																	\n"
					+ "WHERE ID_ENTITA = 569																				\n"
					+ "AND ID_FILE_ENTITA = :idFileEntita  																    \n";

			LOG.debug(query);
			
			Object[] args = new Object[]{idFileEntita};
			int[] types = new int[]{Types.INTEGER};
			
	
			try {
				rowsUpdated = getJdbcTemplate().update(query, args, types);
				
			} catch (Exception e) {
				throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
			}	
			if (rowsUpdated > 0) {
				eseguito= true;
			}
			
			LOG.info("N. record aggiornati:\n" + rowsUpdated);
			LOG.info(prf + " end");
			return eseguito;
	}




	@Override
	public Boolean insertFileEntita(List<IntegrazioniControlliInLocoVO> allegati, Long idIntegrazione) {
		String prf = "[IntegrazioniControlliInLocoDAOImpl::insertFileEntita]";
		LOG.info(prf + " BEGIN");
		Boolean result = false;
		int rowsUpdated = 0;
		try {
		String query = "insert into PBANDI_T_FILE_ENTITA										\n"
				+ "(ID_FILE_ENTITA,																\n"
				+ "ID_FILE,																		\n"
				+ "ID_ENTITA,																	\n"
				+ "ID_TARGET, 																	\n"
				+ "FLAG_ENTITA)																	\n"
				+ "VALUES																		\n"
				+ "(SEQ_PBANDI_T_FILE_ENTITA.NEXTVAL,											\n"
				+ "(SELECT ID_FILE FROM PBANDI_T_FILE 											\n"
				+ " WHERE ID_DOCUMENTO_INDEX =:idDocumentoIndex),								\n"
				+ "569,																			\n"
				+ ":idIntegrazione,															 \n"
				+ ":flagEntita)																	\n";

		LOG.debug(query);
		
		
			for(int i=0; i<allegati.size(); i++ ) {
				Object[] args = new Object[] {allegati.get(i).getIdDocumentoIndex(), idIntegrazione, allegati.get(i).getFlagEntita()};
				int[] types = new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR };
			rowsUpdated = getJdbcTemplate().update(query, args, types);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		if (rowsUpdated > 0) {
			result = true;
		}

		return result;
	}




	@Override
	public Boolean aggiornaIntegrazione(Long idUtente, int idIntegrazione) {
		String prf = "[IntegrazioniControlliInLocoDAOImpl::aggiornaContestazione]";
		LOG.info(prf + " BEGIN");
		int rowsUpdated = 0;
		Boolean eseguito = false;
		String query = "update PBANDI_T_RICHIESTA_INTEGRAZ	 				\n"
				+ "set ID_STATO_RICHIESTA = 2,								\n"
				+ "DT_INVIO = CURRENT_DATE,										\n"
				+ "ID_UTENTE_INVIO = :idUtente								\n"
				+ "where ID_RICHIESTA_INTEGRAZ = :idIntegrazione	        \n";

		LOG.debug(query);

		Object[] args = new Object[] {idUtente, idIntegrazione };
		int[] types = new int[] { Types.INTEGER, Types.INTEGER };

		try {
			rowsUpdated = getJdbcTemplate().update(query, args, types);

		} catch (Exception e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		}
		if (rowsUpdated > 0) {
			eseguito = true;
		}

		LOG.info("N. record aggiornati:\n" + rowsUpdated);
		LOG.info(prf + " end");
		return eseguito;
	
	}




	@Override
	public List<IntegrazioniControlliInLocoVO> getIntegrazioni(int idControllo, int idEntita) {
		String prf = "[IntegrazioniControlliInLocoDAOImpl::getIntegrazioni]";
		LOG.info(prf + " BEGIN");
		List<IntegrazioniControlliInLocoVO>  dati = new ArrayList<IntegrazioniControlliInLocoVO>();
		LOG.info(prf + "idControllo=" + idControllo);

		try {
			String sql = "  SELECT ID_RICHIESTA_INTEGRAZ, DT_RICHIESTA, DT_NOTIFICA, DT_SCADENZA, DT_INVIO,\r\n"
					+ "  a.ID_STATO_RICHIESTA, DESC_STATO_RICHIESTA, ID_TARGET \r\n"
					+ " FROM PBANDI_T_RICHIESTA_INTEGRAZ a\r\n"
					+ " LEFT JOIN PBANDI_D_STATO_RICH_INTEGRAZ b ON a.ID_STATO_RICHIESTA = b.ID_STATO_RICHIESTA\r\n"
					+ " WHERE ID_ENTITA = :idEntita\r\n"
					+ " AND ID_TARGET = :idControllo\r\n"
					+ "ORDER BY a.ID_STATO_RICHIESTA asc";
			
			LOG.debug(sql);
			Object[] args = new Object[] { idEntita, idControllo };
			int[] types = new int[] { Types.INTEGER, Types.INTEGER };

			dati = getJdbcTemplate().query(sql, args, types,
					new ResultSetExtractor<List<IntegrazioniControlliInLocoVO>>() {

						@Override
						public List<IntegrazioniControlliInLocoVO>  extractData(ResultSet rs) throws SQLException {
							List<IntegrazioniControlliInLocoVO>  elencoDati = new ArrayList<>();

							while (rs.next()) {
								IntegrazioniControlliInLocoVO item = new IntegrazioniControlliInLocoVO();
								item.setIdIntegrazione(rs.getInt("ID_RICHIESTA_INTEGRAZ"));
								item.setDtInvio(rs.getDate("DT_INVIO"));
								item.setIdStatoRich(rs.getInt("ID_STATO_RICHIESTA"));
								item.setDtNotifica(rs.getDate("DT_NOTIFICA"));
								item.setDtRich(rs.getDate("DT_RICHIESTA"));
								item.setDtScadenza(rs.getDate("DT_SCADENZA"));
								item.setDescStatoRich(rs.getString("DESC_STATO_RICHIESTA"));
								item.setIdControllo(rs.getInt("ID_TARGET"));

								elencoDati.add(item);
							}

							return elencoDati;
						}

					});

		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return dati;
	}



	@Override
	public List<Integer> getIdControlloLoco(int idProgetto) {
		String prf = "[IntegrazioniControlliInLocoDAOImpl::getIdControlloLoco]";
		LOG.info(prf + " BEGIN");
		List<Integer> dati = new ArrayList<Integer>();
		LOG.info(prf + "idProgetto=" + idProgetto);

		try {
			String sql = "  SELECT id_controllo_loco\r\n"
					+ " FROM PBANDI_R_CAMP_CONTR_LOCO\r\n"
					+ " WHERE ID_PROGETTO = :idProgetto \r\n"
					+ " ORDER BY ID_CONTROLLO_LOCO desc ";
			
			LOG.debug(sql);
			Object[] args = new Object[] { idProgetto };
			int[] types = new int[] { Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types,
					new ResultSetExtractor<List<Integer>>() {

						@Override
						public List<Integer>  extractData(ResultSet rs) throws SQLException {
							List<Integer>  elencoDati =new ArrayList<Integer>();

							while (rs.next()) {
								int item = 0;
								item = (rs.getInt("id_controllo_loco"));
								
								elencoDati.add(item);
							}

							return elencoDati;
						}

					});

		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return dati;
	}



	@Override
	public List<Integer> getIdControllo(int idProgetto) {
		String prf = "[IntegrazioniControlliInLocoDAOImpl::getIdControlloLoco]";
		LOG.info(prf + " BEGIN");
		List<Integer> dati = new ArrayList<Integer>();
		LOG.info(prf + "idProgetto=" + idProgetto);

		try {
			String sql = "  SELECT ID_CONTROLLO \r\n"
					+ " FROM PBANDI_T_CONTROLLO_LOCO_ALTRI\r\n"
					+ " WHERE ID_PROGETTO = :idProgetto \r\n"
					+ " ORDER BY ID_CONTROLLO desc ";
			
			LOG.debug(sql);
			Object[] args = new Object[] { idProgetto };
			int[] types = new int[] { Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types,
					new ResultSetExtractor<List<Integer>>() {

						@Override
						public List<Integer>  extractData(ResultSet rs) throws SQLException {
							List<Integer>  elencoDati = new ArrayList<Integer>();

							while (rs.next()) {
								int item = 0;
								item = (rs.getInt("ID_CONTROLLO"));
								
								elencoDati.add(item);
							}

							return elencoDati;
						}

					});

		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return dati;
	}



	@Override
	public IntegrazioniControlliInLocoVO getAbilitaRichProroga(int idControllo, int idEntita) {
		String prf = "[IntegrazioniControlliInLocoDAOImpl::getAbilitaRichProroga]";
		LOG.info(prf + " BEGIN");
		IntegrazioniControlliInLocoVO  dati = new IntegrazioniControlliInLocoVO();
		LOG.info(prf + "idControllo=" + idControllo);

		try {
			String sql = "SELECT id_stato_richiesta, DT_NOTIFICA \r\n"
					+ "FROM PBANDI_T_RICHIESTA_INTEGRAZ  \r\n"
					+ "WHERE ID_ENTITA = :idEntita\r\n"
					+ "AND ID_ENTITA = :idControllo";
			
			LOG.debug(sql);
			Object[] args = new Object[] { idEntita, idControllo };
			int[] types = new int[] { Types.INTEGER, Types.INTEGER };

			dati = getJdbcTemplate().query(sql, args, types,
					new ResultSetExtractor<IntegrazioniControlliInLocoVO>() {

						@Override
						public IntegrazioniControlliInLocoVO  extractData(ResultSet rs) throws SQLException {
							IntegrazioniControlliInLocoVO elencoDati = new IntegrazioniControlliInLocoVO();

							while (rs.next()) {
								IntegrazioniControlliInLocoVO item = new IntegrazioniControlliInLocoVO();
								if(rs.getInt("id_stato_richiesta")==1) {
								item.setStatoRich("AUTORIZZATA");
								}
								if (rs.getDate("DT_NOTIFICA")!=null) {
								item.setRichProroga(true);	
								}
								elencoDati= item;
							}

							return elencoDati;
						}

					});

		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return dati;
	}



	@Override
	public List<IntegrazioniControlliInLocoVO> getStatoProroga(int idIntegrazione) {
		String prf = "[IntegrazioniControlliInLocoDAOImpl::getStatoProroga]";
		LOG.info(prf + " BEGIN");
		List<IntegrazioniControlliInLocoVO>  dati = new ArrayList<IntegrazioniControlliInLocoVO>();
		LOG.info(prf + "idIntegrazione=" + idIntegrazione);

		try {
			String sql = "SELECT ID_STATO_PROROGA, NUM_GIORNI_RICH, NUM_GIORNI_APPROV, MOTIVAZIONE, DT_RICHIESTA \r\n"
					+ "FROM PBANDI_T_PROROGA \r\n"
					+ "WHERE ID_ENTITA=569 \r\n"
					+ "AND ID_TARGET = :idIntegrazione \r\n"
					+ "ORDER BY DT_INSERIMENTO DESC ";
			
			LOG.debug(sql);
			Object[] args = new Object[] {idIntegrazione };
			int[] types = new int[] { Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types,
					new ResultSetExtractor<List<IntegrazioniControlliInLocoVO>>() {

						@Override
						public List<IntegrazioniControlliInLocoVO>  extractData(ResultSet rs) throws SQLException {
							List<IntegrazioniControlliInLocoVO> elencoDati = new ArrayList<IntegrazioniControlliInLocoVO>();

							while (rs.next()) {
								IntegrazioniControlliInLocoVO item = new IntegrazioniControlliInLocoVO();
								if(rs.getInt("ID_STATO_PROROGA")==1) {
								item.setStatoProroga("INVIATA");
								item.setRichProroga(false);
								}else if(rs.getInt("ID_STATO_PROROGA")==2) {
								item.setStatoProroga("APPROVATA");	
								}else if(rs.getInt("ID_STATO_PROROGA")==3) {
								item.setStatoProroga("RESPINTA");
								item.setRichProroga(false);
								}
								
								item.setDtRichiesta(rs.getDate("DT_RICHIESTA"));
								item.setGgApprovati(rs.getInt("NUM_GIORNI_APPROV"));
								item.setGgRichiesti(rs.getInt("NUM_GIORNI_RICH"));
								item.setMotivazione(rs.getString("MOTIVAZIONE"));
								item.setAllegatiInseriti(false);
								elencoDati.add(item);
							}

							return elencoDati;
						}

					});

		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return dati;
	}



	@Override
	public Boolean inserisciRichProroga(int ggRichiesti, String motivazione, Long idIntegrazione, Long idUtente) {
		String prf = "[IntegrazioniControlliInLocoDAOImpl::inserisciRichProroga]";
		LOG.info(prf + " BEGIN");
		Boolean result = false;
		int rowsUpdated = 0;
		try {
		String query = "insert into PBANDI_T_PROROGA  \r\n"
				+ "(ID_RICHIESTA_PROROGA, \r\n"
				+ "ID_ENTITA,\r\n"
				+ "ID_TARGET,\r\n"
				+ "NUM_GIORNI_RICH,\r\n"
				+ "MOTIVAZIONE,\r\n"
				+ "ID_STATO_PROROGA,\r\n"
				+ "ID_UTENTE_INS,\r\n"
				+ "DT_RICHIESTA,\r\n"
				+ "DT_INSERIMENTO)\r\n"
				+ "VALUES\r\n"
				+ "(seq_pbandi_t_proroga.nextval,\r\n"
				+ "569,\r\n"
				+ ":idIntegrazione,\r\n"
				+ ":ggRichiesti,\r\n"
				+ ":motivazione,\r\n"
				+ "1,\r\n"
				+ ":idUtente,\r\n"
				+ "CURRENT_DATE,\r\n"
				+ "CURRENT_DATE)																\n";

		LOG.debug(query);
		
		
				Object[] args = new Object[] { idIntegrazione, ggRichiesti, motivazione, idUtente};
				int[] types = new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR , Types.INTEGER};
			rowsUpdated = getJdbcTemplate().update(query, args, types);
			
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		if (rowsUpdated > 0) {
			result = true;
		}

		return result;
	}



	@Override
	public IntegrazioniControlliInLocoVO getLettera(int idIntegrazione) {
		String prf = "[IntegrazioniControlliInLocoDAOImpl::getLettera]";
		LOG.info(prf + " BEGIN");
		IntegrazioniControlliInLocoVO dati = new IntegrazioniControlliInLocoVO();
		LOG.info(prf + "idIntegrazione=" + idIntegrazione);

		try {
			String sql = "SELECT a.ID_DOCUMENTO_INDEX, b.NUM_PROTOCOLLO, a.NOME_FILE, c.DESC_BREVE_TIPO_DOC_INDEX\r\n"
					+ "FROM PBANDI_T_DOCUMENTO_INDEX a\r\n"
					+ "LEFT JOIN PBANDI_T_DOC_PROTOCOLLO b ON a.ID_DOCUMENTO_INDEX = b.ID_DOCUMENTO_INDEX \r\n"
					+ "LEFT JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX c ON c.ID_TIPO_DOCUMENTO_INDEX = a.ID_TIPO_DOCUMENTO_INDEX \r\n"
					+ "WHERE a.id_tipo_documento_index = 62\r\n"
					+ "AND a.id_entita = 569\r\n"
					+ "AND a.id_target = :idIntegrazione\n";
			LOG.debug(sql);
			Object[] args = new Object[] { idIntegrazione };
			int[] types = new int[] { Types.INTEGER };

			dati = getJdbcTemplate().query(sql, args, types,
					new ResultSetExtractor<IntegrazioniControlliInLocoVO>() {

						@Override
						public IntegrazioniControlliInLocoVO extractData(ResultSet rs) throws SQLException {
							IntegrazioniControlliInLocoVO elencoDati = new IntegrazioniControlliInLocoVO();

							while (rs.next()) {
								IntegrazioniControlliInLocoVO item = new IntegrazioniControlliInLocoVO();
								 
								item.setNomeFile(rs.getString("NOME_FILE"));
								item.setIdDocumentoIndex(rs.getInt("ID_DOCUMENTO_INDEX"));
								item.setNumProtocollo(rs.getString("NUM_PROTOCOLLO"));
								item.setDescAlleg(rs.getString("DESC_BREVE_TIPO_DOC_INDEX"));

								elencoDati = item;
							}

							return elencoDati;
						}

					});

		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return dati;
	}






	




}
