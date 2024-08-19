/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.pbandisrv.business.manager.TrasferimentiManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.CausaleTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.EsitoSalvaTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.FiltroTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.SoggettoTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.TrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweberog.dto.trasferimenti.FiltroTrasferimento;
import it.csi.pbandi.pbweberog.integration.dao.TrasferimentiDAO;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.FileSqlUtil;
import it.csi.pbandi.pbweberog.util.MessageConstants;
import it.csi.pbandi.pbweberog.util.StringUtil;



@Component
public class TrasferimentiDAOImpl extends JdbcDaoSupport implements TrasferimentiDAO{
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	protected FileSqlUtil fileSqlUtil;
	
	@Autowired
	TrasferimentiManager trasferimentiManager;
	
	@Autowired
	private DataSource dataSource;
	
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	private static int RICEVENTE_TRASFERIMENTO = 1;
//	@Autowired
//	private GenericDAO genericDAO;
//
//	public void setGenericDAO(GenericDAO genericDAO) {
//		this.genericDAO = genericDAO;
//	}
//
//	public GenericDAO getGenericDAO() {
//		return genericDAO;
//	}
//////	
//////	
//////	protected FileApiServiceImpl fileApiServiceImpl;
//////	
	@Autowired
	public TrasferimentiDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//		this.genericDAO = new GenericDAO(dataSource);
	}

	public TrasferimentiDAOImpl() { }

	@Override
	@Transactional
	public SoggettoTrasferimentoDTO[] findSoggettiTrasferimento(Long idUtente, String idIride) throws Exception {
		String prf = "[TrasferimentiDAOImpl::findSoggettiTrasferimento]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride" };	
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride);
			LOG.info(prf + " END");
			return trasferimentiManager.findSoggettiTrasferimento();
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
//		try {	
//			List<SoggettoTrasferimentoVO> result = null;
//			
//			StringBuilder sqlSelect = new StringBuilder("SELECT ")
//			.append("PBANDI_T_ENTE_GIURIDICO.ID_SOGGETTO as idSoggettoBeneficiario, ")
//			.append("PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO as cfBeneficiario, ")
//			.append("PBANDI_T_ENTE_GIURIDICO.DENOMINAZIONE_ENTE_GIURIDICO as denominazioneBeneficiario ");
//					
//			StringBuilder tables = new StringBuilder(" PBANDI.PBANDI_T_SOGGETTO, PBANDI_T_ENTE_GIURIDICO, ");
//			
//			tables.append(" ( select PBANDI_T_ENTE_GIURIDICO.id_soggetto, ")
//			.append(" max(PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico) AS id_ente_giuridico ") 
//			.append(" from PBANDI_T_ENTE_GIURIDICO, PBANDI_T_SOGGETTO where ")
//			.append(" PBANDI_T_SOGGETTO.RICEVENTE_TRASF = :riceventeTrasferimento ")
//			.append(" AND PBANDI_T_SOGGETTO.id_soggetto= PBANDI_T_ENTE_GIURIDICO.id_soggetto ")
//			.append(" AND TRUNC(sysdate) < NVL(TRUNC(PBANDI_T_ENTE_GIURIDICO.DT_FINE_VALIDITA), TRUNC(sysdate)+1) ")
//			.append(" group by PBANDI_T_ENTE_GIURIDICO.id_soggetto ")
//			.append("  ) A");
//
//			
//			sqlSelect.append(" FROM ").append(tables);
//			
//			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
//			//			conditionList.add(new StringBuilder(" PBANDI_T_SOGGETTO.RICEVENTE_TRASF = :riceventeTrasferimento "));
//			
//			conditionList.add(new StringBuilder("PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico =  A.id_ente_giuridico"));
//			conditionList.add(new StringBuilder("PBANDI_T_SOGGETTO.id_soggetto =  PBANDI_T_ENTE_GIURIDICO.id_soggetto"));
//			conditionList.add(new StringBuilder(" TRUNC(sysdate)  < NVL(TRUNC(PBANDI_T_ENTE_GIURIDICO.DT_FINE_VALIDITA), TRUNC(sysdate)  +1) "));
//
//			MapSqlParameterSource params = new MapSqlParameterSource();
//						
//			params.addValue("riceventeTrasferimento", RICEVENTE_TRASFERIMENTO, Types.INTEGER);			
//				
//			setWhereClause(conditionList, sqlSelect, tables);
//			
//			sqlSelect.append(" ORDER BY PBANDI_T_ENTE_GIURIDICO.DENOMINAZIONE_ENTE_GIURIDICO ");
//			
//			//logger.debug("Query:"+sqlSelect.toString());
//			result = query(sqlSelect.toString(), params, new PbandiSoggettoTrasferimentoRowMapper());
//			
//			return beanUtil.transform(result, SoggettoTrasferimentoDTO.class);
//			
//		} catch(Exception e) {
//			throw e;
//		} finally {
//			LOG.info(prf + " END");
//		}
	}

//	protected void setWhereClause(List<StringBuilder> conditionList,
//			StringBuilder sqlSelect, StringBuilder tables) {
//		if (!conditionList.isEmpty()) {
//			sqlSelect.append(" WHERE");
//			for (int i = 0; i < conditionList.size(); i++) {
//				if (i > 0) {
//					sqlSelect.append(" AND");
//				}
//				sqlSelect.append(" ").append(conditionList.get(i));
//			}
//		}
//	}
//	protected <T> List<T> query(String sql, MapSqlParameterSource params,
//			RowMapper<T> rowMapper) {
//		List<T> elementList = null;
//		//String logQuery = logQuery(sql, params);
//		try {
//			elementList = namedJdbcTemplate.query(sql, params, rowMapper);
//			return elementList;
//		} catch (Exception e) {
//			if (e instanceof EmptyResultDataAccessException) {
//				LOG.warn("No data found!\n");
//			} else {
//				LOG.fatal(e.getMessage(), e);
//				throw new RuntimeException(e);
//			}
//		} finally {
//			LOG.info("records found: "+ (elementList != null ? elementList.size() : 0)+"\n");
//		}
//		return null;
//
//	}
	@Override
	@Transactional
	public CausaleTrasferimentoDTO[] findElencoCausaliTrasferimenti(Long idUtente, String idIride) throws Exception {
		String prf = "[TrasferimentiDAOImpl::findElencoCausaliTrasferimenti]";
		LOG.info(prf + " BEGIN");

		try {
			String[] nameParameter = { "idUtente", "idIride" };	
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride);
			LOG.info(prf + " END");
			return trasferimentiManager.findCausaliTrasferimenti();
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public TrasferimentoDTO[] ricercaTrasferimenti(Long idUtente, String idIride, FiltroTrasferimento filtro) throws Exception {
		String prf = "[TrasferimentiDAOImpl::ricercaTrasferimenti]";
		LOG.info(prf + " BEGIN");
		FiltroTrasferimentoDTO filtroSrv = new FiltroTrasferimentoDTO();
		
		if (filtro != null && (!StringUtil.isEmpty(filtro.getDaDataTrasferimento())
				|| !StringUtil.isEmpty(filtro.getADataTrasferimento())
				|| !StringUtil.isEmpty(filtro.getCodiceTrasferimento())
				|| !StringUtil.isEmpty(filtro.getFlagPubblicoPrivato()) 
				|| (filtro.getIdCausaleTrasferimento() != null)
				|| (filtro.getIdSoggettoBeneficiario() != null) || filtro.getIdLineaDiIntervento() != null)) {
			
		}
		filtroSrv.setIdCausaleTrasferimento(filtro.getIdCausaleTrasferimento());
		filtroSrv.setIdSoggettoBeneficiario(filtro.getIdSoggettoBeneficiario());
		
		if (!StringUtil.isEmpty(filtro.getDaDataTrasferimento())) {
			filtroSrv.setDaDataTrasferimento(DateUtil.getDate(filtro.getDaDataTrasferimento()));
		}
		if (!StringUtil.isEmpty(filtro.getADataTrasferimento())) {
			filtroSrv.setADataTrasferimento(DateUtil.getDate(filtro.getADataTrasferimento()));
		}
		if (!StringUtil.isEmpty(filtro.getCodiceTrasferimento())) {
			filtroSrv.setCodiceTrasferimento(filtro.getCodiceTrasferimento());
		}
		if (!StringUtil.isEmpty(filtro.getFlagPubblicoPrivato())) {
			if (!filtro.getFlagPubblicoPrivato().equals("0"));
				filtroSrv.setFlagPubblicoPrivato(filtro.getFlagPubblicoPrivato());
		}
		if ( filtro.getIdLineaDiIntervento() != null ) {
			filtroSrv.setIdLineaDiIntervento(filtro.getIdLineaDiIntervento());
		}
		
		try {
			String[] nameParameter = { "idUtente", "idIride" };	
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride);
			TrasferimentoDTO[] elencoTrasferimento = trasferimentiManager.findTrasferimenti(idUtente, idIride, filtroSrv);
			return elencoTrasferimento;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoSalvaTrasferimentoDTO modificaTrasferimento(Long idUtente, String idIride, TrasferimentoDTO trasferimento) throws Exception {
		String prf = "[TrasferimentiDAOImpl::modificaTrasferimento]";
		LOG.info(prf + " BEGIN");

		try {
			EsitoSalvaTrasferimentoDTO esito = new EsitoSalvaTrasferimentoDTO();
			// 1 - validazioni necessarie
			String[] nameParameter = { "idUtente", "idIride", "trasferimento" };
			ValidatorInput.verifyNullValue(nameParameter, trasferimento);
			
			
			esito = trasferimentiManager.modificaTrasferimento(idUtente, idIride, trasferimento);
			if(esito.getEsito()) {
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				esito.setMsgs(new MessaggioDTO[]{msg});
			}
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public TrasferimentoDTO findDettaglioTrasferimento(Long idUtente, String idIride, Long idTrasferimento) throws Exception {
		String prf = "[TrasferimentiDAOImpl::findDettaglioTrasferimento]";
		LOG.info(prf + " BEGIN");

		try {
			// 1 - validazioni necessarie
			String[] nameParameter = { "idUtente", "idIride", "idTrasferimento" };
			ValidatorInput.verifyNullValue(nameParameter, idTrasferimento);
			
			LOG.info(prf + " END");
			return  trasferimentiManager.findTrasferimento(idTrasferimento);
			
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoSalvaTrasferimentoDTO eliminaTrasferimento(Long idUtente, String idIride, Long idTrasferimento) throws Exception {
		String prf = "[TrasferimentiDAOImpl::eliminaTrasferimento]";
		LOG.info(prf + " BEGIN");

		try {
			EsitoSalvaTrasferimentoDTO esito = new EsitoSalvaTrasferimentoDTO();
			
			String[] nameParameter = { "idUtente", "idIride", "idTrasferimento" };
			ValidatorInput.verifyNullValue(nameParameter, idTrasferimento);
			
			
			esito =  trasferimentiManager.eliminaTrasferimento(idTrasferimento);
			if(esito.getEsito()) {
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(MessageConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
				esito.setMsgs(new MessaggioDTO[]{msg});
			}
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoSalvaTrasferimentoDTO creaTrasferimento(Long idUtente, String idIride, TrasferimentoDTO trasferimento) throws Exception {
		String prf = "[TrasferimentiDAOImpl::creaTrasferimento]";
		LOG.info(prf + " BEGIN");

		try {
			EsitoSalvaTrasferimentoDTO esito = new EsitoSalvaTrasferimentoDTO();
			
			String[] nameParameter = { "idUtente", "idIride", "trasferimento" };
			ValidatorInput.verifyNullValue(nameParameter, trasferimento);
			
			
			esito =  trasferimentiManager.creaTrasferimento(idUtente, idIride,trasferimento);
			if(esito.getEsito()) {
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				esito.setMsgs(new MessaggioDTO[]{msg});
			}
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}
	
	
	
	
	private class EnteProgettoDomandaVORowMapper implements RowMapper< PbandiDLineaDiInterventoVO> {

		public  PbandiDLineaDiInterventoVO mapRow(ResultSet rs, int rowNum) throws SQLException {

			PbandiDLineaDiInterventoVO pbandiDLineaDiInterventoVO = new  PbandiDLineaDiInterventoVO();
			
			pbandiDLineaDiInterventoVO.setIdLineaDiIntervento(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
			pbandiDLineaDiInterventoVO.setIdLineaDiInterventoPadre(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO_PADRE"));
			pbandiDLineaDiInterventoVO.setDescBreveLinea(rs.getString("DESC_BREVE_LINEA"));
			pbandiDLineaDiInterventoVO.setDescLinea(rs.getString("DESC_LINEA"));
			
			return pbandiDLineaDiInterventoVO;
			
		}
	}

	@Override
	public List<PbandiDLineaDiInterventoVO> findLineaDiIntervento() throws Exception {
		
		String prf = "[TrasferimentiDAOImpl::findLineaDiIntervento]";
		LOG.info(prf + " BEGIN");
		
		final String FIND_LINA_INTEVERNTO = ""
				+ "SELECT T.*, T.rowid FROM PBANDI_D_LINEA_DI_INTERVENTO T WHERE T.ID_TIPO_LINEA_INTERVENTO = 1 AND T.FLAG_TRASFERIMENTI = 'S' ";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<PbandiDLineaDiInterventoVO> pbandiDLineaDiInterventoVO = jdbcTemplate.query(FIND_LINA_INTEVERNTO, new EnteProgettoDomandaVORowMapper(), null);
		
		LOG.info(prf + " END");
		
		return pbandiDLineaDiInterventoVO;
	}
}
