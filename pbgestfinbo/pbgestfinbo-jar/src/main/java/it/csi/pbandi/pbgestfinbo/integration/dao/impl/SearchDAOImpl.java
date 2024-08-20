/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.SearchDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.SearchVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;


@Service
public class SearchDAOImpl extends JdbcDaoSupport implements SearchDAO{

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public SearchDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	//
	//	@SuppressWarnings("null")
	//	@Override
	//	public List<SearchVO> cercaBeneficiario(String cf, Long idSoggetto, String pIva, 
	//			String denominazione, String numeroDomanda, String codProgetto, String nome, String cognome, HttpServletRequest req) throws DaoException {
	//
	//		String prf = "[SearchDAOImpl::cercaBeneficiario] ";
	//		LOG.info(prf + " BEGIN");
	//		List<SearchVO> beneficiariList = new ArrayList<SearchVO> ();
	//
	//		try {
	//
	//			StringBuilder sql = new StringBuilder();
	//			StringBuilder sql2 = new StringBuilder();
	//			StringBuilder sqlIdSede = new StringBuilder();
	//
	//			if((denominazione != null && !denominazione.isEmpty()) || (pIva != null && !pIva.isEmpty())) {
	//				sqlIdSede.append("SELECT DISTINCT ID_SEDE FROM PBANDI_R_SOGGETTO_DOMANDA_SEDE s INNER JOIN PBANDI_R_SOGGETTO_DOMANDA d "
	//						+ "ON s.PROGR_SOGGETTO_DOMANDA = d.PROGR_SOGGETTO_DOMANDA "
	//						+ "WHERE d.ID_SOGGETTO = " + idSoggetto);
	//
	//				List<IdSedeVO> idSede = getJdbcTemplate().query(sqlIdSede.toString(),new IdSedeRowMapper());
	//
	//				sql.append("SELECT s.ID_TIPO_SOGGETTO, d.ID_SOGGETTO, d.ID_DOMANDA, p.ID_PROGETTO, "
	//						+ "s.CODICE_FISCALE_SOGGETTO, ts.DESC_TIPO_SOGGETTO, e.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione, "
	//						+ "e.ID_ENTE_GIURIDICO AS id_ente, ds.ID_SEDE ||'' AS id_sede, pts.PARTITA_IVA AS piva "
	//						+ "FROM PBANDI_R_SOGGETTO_DOMANDA d "
	//						+ "INNER JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE ds "
	//						+ "ON d.PROGR_SOGGETTO_DOMANDA = ds.PROGR_SOGGETTO_DOMANDA "
	//						+ "INNER JOIN PBANDI_T_DOMANDA ptd "
	//						+ "ON ptd.ID_DOMANDA = d.ID_DOMANDA "
	//						+ "INNER JOIN PBANDI_R_SOGGETTO_PROGETTO sp "
	//						+ "ON d.PROGR_SOGGETTO_DOMANDA = sp.PROGR_SOGGETTO_DOMANDA "
	//						+ "INNER JOIN PBANDI_T_PROGETTO ptp "
	//						+ "ON ptp.ID_PROGETTO = sp.ID_PROGETTO "
	//						+ "RIGHT JOIN PBANDI_T_SEDE pts "
	//						+ "ON ds.ID_SEDE = pts.ID_SEDE "
	//						+ "INNER JOIN PBANDI_R_SOGGETTO_PROGETTO p "
	//						+ "ON d.ID_SOGGETTO = p.ID_SOGGETTO "
	//						+ "RIGHT JOIN PBANDI_T_ENTE_GIURIDICO e "
	//						+ "ON e.ID_SOGGETTO = d.ID_SOGGETTO "
	//						+ "RIGHT JOIN PBANDI_T_SOGGETTO s "
	//						+ "ON s.ID_SOGGETTO = d.ID_SOGGETTO "
	//						+ "RIGHT JOIN PBANDI_D_TIPO_SOGGETTO ts "
	//						+ "ON s.ID_TIPO_SOGGETTO = ts.ID_TIPO_SOGGETTO "
	//						+ "WHERE d.ID_TIPO_ANAGRAFICA = 1 AND d.ID_TIPO_BENEFICIARIO <> 4 "
	//						+ "AND d.ID_SOGGETTO = " + idSoggetto + " AND s.ID_TIPO_SOGGETTO = 2 "
	//						+ "AND ds.ID_SEDE = " + idSede.get(0).getIdSede());
	//			}else {
	//				sql.append("SELECT s.ID_TIPO_SOGGETTO, d.ID_SOGGETTO, d.ID_DOMANDA, p.ID_PROGETTO, "
	//						+ "s.CODICE_FISCALE_SOGGETTO, ts.DESC_TIPO_SOGGETTO, e.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione, "
	//						+ "e.ID_ENTE_GIURIDICO AS id_ente, ds.ID_SEDE ||'' AS id_sede, pts.PARTITA_IVA AS piva "
	//						+ "FROM PBANDI_R_SOGGETTO_DOMANDA d "
	//						+ "INNER JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE ds "
	//						+ "ON d.PROGR_SOGGETTO_DOMANDA = ds.PROGR_SOGGETTO_DOMANDA "
	//						+ "INNER JOIN PBANDI_T_DOMANDA ptd "
	//						+ "ON ptd.ID_DOMANDA = d.ID_DOMANDA "
	//						+ "INNER JOIN PBANDI_R_SOGGETTO_PROGETTO sp "
	//						+ "ON d.PROGR_SOGGETTO_DOMANDA = sp.PROGR_SOGGETTO_DOMANDA "
	//						+ "INNER JOIN PBANDI_T_PROGETTO ptp "
	//						+ "ON ptp.ID_PROGETTO = sp.ID_PROGETTO "
	//						+ "RIGHT JOIN PBANDI_T_SEDE pts "
	//						+ "ON ds.ID_SEDE = pts.ID_SEDE "
	//						+ "INNER JOIN PBANDI_R_SOGGETTO_PROGETTO p "
	//						+ "ON d.ID_SOGGETTO = p.ID_SOGGETTO "
	//						+ "RIGHT JOIN PBANDI_T_ENTE_GIURIDICO e "
	//						+ "ON e.ID_SOGGETTO = d.ID_SOGGETTO "
	//						+ "RIGHT JOIN PBANDI_T_SOGGETTO s "
	//						+ "ON s.ID_SOGGETTO = d.ID_SOGGETTO "
	//						+ "RIGHT JOIN PBANDI_D_TIPO_SOGGETTO ts "
	//						+ "ON s.ID_TIPO_SOGGETTO = ts.ID_TIPO_SOGGETTO "
	//						+ "WHERE d.ID_TIPO_ANAGRAFICA = 1 AND d.ID_TIPO_BENEFICIARIO <> 4 "
	//						+ "AND d.ID_SOGGETTO = " + idSoggetto + " AND s.ID_TIPO_SOGGETTO = 2 ");
	//			}
	//
	//
	//			sql2.append("SELECT s.ID_TIPO_SOGGETTO, d.ID_SOGGETTO, d.ID_DOMANDA, p.ID_PROGETTO, \r\n"
	//					+ "s.CODICE_FISCALE_SOGGETTO, ts.DESC_TIPO_SOGGETTO, ptpf.NOME ||' '|| ptpf.COGNOME AS denominazione, \r\n"
	//					+ "ptpf.ID_PERSONA_FISICA AS id_ente, '' AS id_sede, '' AS piva\r\n"
	//					+ "FROM PBANDI_R_SOGGETTO_DOMANDA d  \r\n"
	//					+ "INNER JOIN PBANDI_R_SOGGETTO_PROGETTO p \r\n"
	//					+ "ON d.ID_SOGGETTO = p.ID_SOGGETTO \r\n"
	//					+ "RIGHT JOIN PBANDI_T_PERSONA_FISICA ptpf \r\n"
	//					+ "ON ptpf.ID_SOGGETTO = d.ID_SOGGETTO \r\n"
	//					+ "RIGHT JOIN PBANDI_T_SOGGETTO s \r\n"
	//					+ "ON s.ID_SOGGETTO = d.ID_SOGGETTO\r\n"
	//					+ "RIGHT JOIN PBANDI_D_TIPO_SOGGETTO ts\r\n"
	//					+ "ON s.ID_TIPO_SOGGETTO = ts.ID_TIPO_SOGGETTO \r\n"
	//					+ "INNER JOIN PBANDI_T_DOMANDA ptd \r\n"
	//					+ "ON d.ID_DOMANDA = ptd.ID_DOMANDA \r\n"
	//					+ "INNER JOIN PBANDI_T_PROGETTO ptp "
	//					+ "ON p.ID_PROGETTO = ptp.ID_PROGETTO "
	//					+ "WHERE d.ID_SOGGETTO = "+idSoggetto+" "
	//					+ "AND d.ID_TIPO_ANAGRAFICA = 1 "
	//					+ "AND s.ID_TIPO_SOGGETTO = 1 "
	//					+ "AND d.ID_TIPO_BENEFICIARIO <> 4 ");
	//
	//			if(cf != null && !cf.trim().isEmpty() ) {
	//				sql.append(" AND s.CODICE_FISCALE_SOGGETTO = '" + cf.trim() +"'");
	//				sql2.append(" AND s.CODICE_FISCALE_SOGGETTO = '" + cf.trim() +"'");
	//			}if(pIva != null && !pIva.trim().isEmpty()) {
	//				sql.append(" AND TO_CHAR(pts.PARTITA_IVA) = '" + pIva.trim() + "'");
	//			}if(denominazione != null && !denominazione.trim().isEmpty()) {
	//				sql.append(" AND e.DENOMINAZIONE_ENTE_GIURIDICO = '" + denominazione.trim() + "'");
	//			}if(numeroDomanda != null && !numeroDomanda.isEmpty()) {
	//				sql.append(" AND ptd.NUMERO_DOMANDA = '" + numeroDomanda + "'");
	//				sql2.append(" AND ptd.NUMERO_DOMANDA = '" + numeroDomanda + "'");
	//			}if(codProgetto != null && !codProgetto.isEmpty()) {
	//				sql.append(" AND ptp.CODICE_PROGETTO = '" + codProgetto + "'");
	//				sql2.append(" AND ptp.CODICE_PROGETTO = '" + codProgetto + "'");
	//			}if(nome != null && !nome.trim().isEmpty()) {
	//				sql2.append(" AND ptpf.NOME = '" + nome + "'");
	//			}if(cognome != null && !cognome.trim().isEmpty()) {
	//				sql2.append(" AND ptpf.COGNOME = '" + cognome + "'");
	//			}
	//
	//
	//			String query = "with selezione as (" + sql.toString() + " UNION " + sql2.toString() + ") select * from selezione ORDER BY id_ente DESC";
	//
	//			LOG.debug(prf + query);
	//
	//			System.out.println("query: " + query);
	//
	//			beneficiariList  = getJdbcTemplate().query(query,new SearchRowMapper());
	//
	//
	//		}catch (IncorrectResultSizeDataAccessException e) {
	//			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
	//
	//		} catch (Exception e) {
	//			LOG.error(prf + "Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
	//			throw new DaoException("DaoException while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
	//		}
	//		finally {
	//			LOG.info(prf + " END");
	//		}
	//
	//		return beneficiariList;
	//	}

	@SuppressWarnings("null")
	@Override
	public List<SearchVO> cercaBeneficiarioEg(String cf, Long idSoggetto, String pIva, String denominazione,
			String numeroDomanda, BigDecimal codProgetto, HttpServletRequest req) throws DaoException {

		String prf = "[SearchDAOImpl::cercaBeneficiario] ";
		LOG.info(prf + " BEGIN");
		List<SearchVO> beneficiariList = new ArrayList<SearchVO> ();
		

		try {
			
			if(codProgetto != null && codProgetto.intValue() >0) {
				
				StringBuilder getDati = new StringBuilder();
				 getDati.append("WITH selezione AS (\r\n"
				 		+ "    SELECT\r\n"
				 		+ "        entegiu.DENOMINAZIONE_ENTE_GIURIDICO ,\r\n"
				 		+ "        '' AS desc_2,\r\n"
				 		+ "        ENTEGIU.ID_SOGGETTO, ENTEGIU.ID_ENTE_GIURIDICO\r\n"
				 		+ "    FROM\r\n"
				 		+ "        PBANDI_T_ENTE_GIURIDICO entegiu\r\n"
				 		+ ")\r\n"
				 		+ "SELECT\r\n"
				 		+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
				 		+ "    pts.ndg,\r\n"
				 		+ "    pts.ID_SOGGETTO,\r\n"
				 		+ "    se.DENOMINAZIONE_ENTE_GIURIDICO AS DENOMINAZIONE,\r\n"
				 		+ "    se.desc_2, PRSD.ID_ENTE_GIURIDICO,\r\n"
				 		+ "    ptp.CODICE_VISUALIZZATO,\r\n"
				 		+ "    ptd.NUMERO_DOMANDA,\r\n"
				 		+ "    prsd.ID_DOMANDA,\r\n"
				 		+ "    pdsp.DESC_STATO_PROGETTO,\r\n"
				 		+ "    ptse.PARTITA_IVA, prsp.ID_PROGETTO ,\r\n"
				 		+ "    ptp.CODICE_VISUALIZZATO, ptp.CODICE_PROGETTO , pdts.DESC_TIPO_SOGGETTO \r\n"
				 		+ "FROM\r\n"
				 		+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
				 		+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
				 		+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsp.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
				 		+ "    LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
				 		+ "    LEFT JOIN selezione se ON prsp.ID_ENTE_GIURIDICO = se.ID_ENTE_GIURIDICO\r\n"
				 		+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.id_domanda\r\n"
				 		+ "    LEFT JOIN PBANDI_D_STATO_PROGETTO pdsp ON pdsp.ID_STATO_PROGETTO = ptp.ID_STATO_PROGETTO\r\n"
				 		+ "    LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsps.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
				 		+ "    LEFT JOIN PBANDI_T_SEDE ptse ON prsps.ID_SEDE = ptse.ID_SEDE\r\n"
				 		+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
				 		+ "WHERE\r\n"
				 		+ "    prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
				 		+ "    AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
				 		+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
				 		+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1    \r\n"
						+ "    AND prsp.ID_PROGETTO = "+codProgetto+"\r\n"
						+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"); 
						
						if(numeroDomanda!=null) {
							getDati.append("AND prsd.ID_DOMANDA="+ numeroDomanda + "\r\n");
						}
						getDati.append("AND rownum = 1"); 
						
						
						ResultSetExtractor<List<SearchVO>> rse = new ResultSetExtractor<List<SearchVO>>() {

							@Override
							public List<SearchVO> extractData(ResultSet rs) throws SQLException, DataAccessException {

								List<SearchVO> list = new ArrayList<SearchVO> ();

								while (rs.next())
								{

									SearchVO vo = new SearchVO();

									vo.setCf(rs.getString("CODICE_FISCALE_SOGGETTO"));
									vo.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
									vo.setDescTipoSogg(rs.getString("DESC_TIPO_SOGGETTO"));
									vo.setDenominazione(rs.getString("DENOMINAZIONE"));
									vo.setpIva(rs.getString("PARTITA_IVA"));
									vo.setIdEnteGiuridico(rs.getLong("ID_ENTE_GIURIDICO"));
									vo.setNdg(rs.getString("ndg")); 
									vo.setIdDomanda(rs.getLong("ID_DOMANDA"));
									vo.setIdProgetto(rs.getLong("ID_PROGETTO"));
									list.add(vo);

								}

								return list;
							}
						};
						beneficiariList  = getJdbcTemplate().query(getDati.toString(), rse);

			}else {
				StringBuilder getDati = new StringBuilder(); 
				getDati.append("WITH selezione AS (\r\n"
						+ "    SELECT\r\n"
						+ "        entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione,\r\n"
						+ "        '' AS desc_2,\r\n"
						+ "        ENTEGIU.ID_SOGGETTO, ENTEGIU.ID_ENTE_GIURIDICO\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_T_ENTE_GIURIDICO entegiu\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    pts.ndg,\r\n"
						+ "    pts.ID_SOGGETTO,\r\n"
						+ "    se.denominazione,\r\n"
						+ "    se.desc_2,\r\n"
						+ "    --ptp.CODICE_VISUALIZZATO,\r\n"
						+ "    ptd.NUMERO_DOMANDA,\r\n"
						+ "    prsd.ID_DOMANDA,\r\n"
						+ "    pdsd.DESC_STATO_DOMANDA,pdts.DESC_TIPO_SOGGETTO,\r\n"
						+ "    ptse.PARTITA_IVA, prsd.ID_ENTE_GIURIDICO \r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsd.ID_SOGGETTO\r\n"
						+ "    --LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    --LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
						+ "    LEFT JOIN selezione se ON prsd.ID_ENTE_GIURIDICO = se.ID_ENTE_GIURIDICO\r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.id_domanda\r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.ID_STATO_DOMANDA = ptd.ID_STATO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_T_SEDE ptse ON ptse.ID_SEDE = prsds.ID_SEDE\r\n"
						+ "WHERE\r\n"
						+ "    --prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    -- prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"); 
				
				if(numeroDomanda!=null) {
					getDati.append("and prsd.ID_DOMANDA="+ numeroDomanda + "\r\n");
				}
				getDati.append("AND rownum = 1"); 
				
				ResultSetExtractor<List<SearchVO>> rse = new ResultSetExtractor<List<SearchVO>>() {

					@Override
					public List<SearchVO> extractData(ResultSet rs) throws SQLException, DataAccessException {

						List<SearchVO> list = new ArrayList<SearchVO> ();

						while (rs.next())
						{

							SearchVO vo = new SearchVO();

							vo.setCf(rs.getString("CODICE_FISCALE_SOGGETTO"));
							vo.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
							vo.setDescTipoSogg(rs.getString("DESC_TIPO_SOGGETTO"));
							vo.setDenominazione(rs.getString("DENOMINAZIONE"));
							vo.setpIva(rs.getString("PARTITA_IVA"));
							vo.setIdEnteGiuridico(rs.getLong("ID_ENTE_GIURIDICO"));
							vo.setNdg(rs.getString("ndg")); 
							vo.setIdDomanda(rs.getLong("ID_DOMANDA"));
							//vo.setIdProgetto(rs.getLong("ID_PROGETTO"));
							
							list.add(vo);

						}

						return list;
					}
				};
				
				beneficiariList  = getJdbcTemplate().query(getDati.toString(), rse);
				
			}

			
			
		  String sqlBlocchiAtttivi = "SELECT\r\n"
		  		+ "    COUNT(*)\r\n"
		  		+ "FROM\r\n"
		  		+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
		  		+ "    LEFT JOIN PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb ON prsd.PROGR_SOGGETTO_DOMANDA = ptsdb.PROGR_SOGGETTO_DOMANDA\r\n"
		  		+ "    LEFT JOIN PBANDI_D_CAUSALE_BLOCCO pdcb ON pdcb.ID_CAUSALE_BLOCCO = ptsdb.ID_CAUSALE_BLOCCO\r\n"
		  		+ "WHERE\r\n"
		  		+ "    prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
		  		+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
		  		+ "    AND ptsdb.DT_INSERIMENTO_SBLOCCO IS NULL\r\n"
		  		+ "    AND pdcb.FLAG_BLOCCO_ANAGRAFICO = 'S'\r\n"
		  		+ "    AND ID_SOGGETTO ="+ idSoggetto;
		  
		  int numeroBlocchi = getJdbcTemplate().queryForObject(sqlBlocchiAtttivi, Integer.class); 
		  		
			if(numeroBlocchi>0) {
				for (SearchVO searchVO : beneficiariList) {
					searchVO.setFlagBloccoAnag("SI");
				}
			} else {
				for (SearchVO searchVO : beneficiariList) {
					searchVO.setFlagBloccoAnag("NO");
				}
			}


		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
			throw new DaoException("DaoException while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return beneficiariList;
	}

	@Override
	public List<SearchVO> cercaBeneficiarioPf(String cf, Long idSoggetto, String numeroDomanda, BigDecimal codProgetto,
			String nome, String cognome, HttpServletRequest req) throws DaoException {

		String prf = "[SearchDAOImpl::cercaBeneficiario] ";
		LOG.info(prf + " BEGIN");
		List<SearchVO> beneficiariList = new ArrayList<SearchVO> ();

		try {

			
			if(codProgetto !=null && codProgetto.intValue() >0) {
				StringBuilder sql = new StringBuilder();
				
				sql.append("WITH selezione AS (\r\n"
						+ "    SELECT\r\n"
						+ "        persfis.COGNOME,\r\n"
						+ "        persfis.NOME,\r\n"
						+ "        PERSFIS.ID_SOGGETTO, persfis.ID_PERSONA_FISICA\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_T_PERSONA_FISICA persfis\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    pts.ndg,\r\n"
						+ "    pts.ID_SOGGETTO,\r\n"
						+ "    se.NOME || ' ' || se.COGNOME AS denominazione,\r\n"
						+ "    ptp.CODICE_VISUALIZZATO,\r\n"
						+ "    ptd.NUMERO_DOMANDA,\r\n"
						+ "    prsd.ID_DOMANDA,\r\n"
						+ "    pdsp.DESC_STATO_PROGETTO,\r\n"
						+ "    ptse.PARTITA_IVA,\r\n"
						+ "    ptp.CODICE_VISUALIZZATO,\r\n"
						+ "    ptp.CODICE_PROGETTO,\r\n"
						+ "    prsp.id_ente_giuridico,\r\n"
						+ "    pdts.DESC_TIPO_SOGGETTO\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsp.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
						+ "    JOIN selezione se ON prsp.ID_PERSONA_FISICA = se.ID_PERSONA_FISICA\r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.id_domanda\r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_PROGETTO pdsp ON pdsp.ID_STATO_PROGETTO = ptp.ID_STATO_PROGETTO\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsps.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "    LEFT JOIN PBANDI_T_SEDE ptse ON prsps.ID_SEDE = ptse.ID_SEDE\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "WHERE\r\n"
						+ "    prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND prsp.ID_PROGETTO = "+codProgetto+"\r\n"
						+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
						);

				if(numeroDomanda != null && !numeroDomanda.isEmpty()) {
					sql.append(" AND prsd.ID_DOMANDA =" + numeroDomanda + "\r\n");
				} 
				sql.append("    AND rownum = 1");


				LOG.debug(prf + sql.toString());

				ResultSetExtractor<List<SearchVO>> rse = new ResultSetExtractor<List<SearchVO>>() {

					@Override
					public List<SearchVO> extractData(ResultSet rs) throws SQLException, DataAccessException {

						List<SearchVO> list = new ArrayList<SearchVO> ();

						while (rs.next())
						{

							SearchVO vo = new SearchVO();
							
							vo.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
							vo.setCf(rs.getString("CODICE_FISCALE_SOGGETTO"));
							vo.setDescTipoSogg(rs.getString("DESC_TIPO_SOGGETTO"));
							vo.setDenominazione(rs.getString("DENOMINAZIONE"));
							vo.setNdg(rs.getString("NDG"));
							list.add(vo);

						}

						return list;
					}
				};

				beneficiariList  = getJdbcTemplate().query(sql.toString(), rse);

			}else {
				
				StringBuilder sql = new StringBuilder();
				
				sql.append("WITH selezione AS (\r\n"
						+ "      SELECT\r\n"
						+ "        persfis.COGNOME ,\r\n"
						+ "        persfis.NOME ,\r\n"
						+ "        PERSFIS.ID_SOGGETTO, persfis.ID_PERSONA_FISICA\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_T_PERSONA_FISICA persfis\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    pts.ndg,\r\n"
						+ "    pts.ID_SOGGETTO,\r\n"
						+ "    se.NOME || ' ' || se.COGNOME AS denominazione,\r\n"
						+ "    --ptp.CODICE_VISUALIZZATO,\r\n"
						+ "    ptd.NUMERO_DOMANDA,\r\n"
						+ "    prsd.ID_DOMANDA,\r\n"
						+ "    pdsd.DESC_STATO_DOMANDA,pdts.DESC_TIPO_SOGGETTO,\r\n"
						+ "    ptse.PARTITA_IVA, prsd.ID_ENTE_GIURIDICO \r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsd.ID_SOGGETTO\r\n"
						+ "    --LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    --LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
						+ "    LEFT JOIN selezione se ON prsd.ID_PERSONA_FISICA = se.ID_PERSONA_FISICA\r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.id_domanda\r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.ID_STATO_DOMANDA = ptd.ID_STATO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_T_SEDE ptse ON ptse.ID_SEDE = prsds.ID_SEDE\r\n"
						+ "WHERE\r\n"
						+ "    --prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    --AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "     prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
						+ "    AND prsd.ID_DOMANDA ="+numeroDomanda+"\r\n"
						+ "    AND rownum = 1");

				LOG.debug(prf + sql.toString());

				ResultSetExtractor<List<SearchVO>> rse = new ResultSetExtractor<List<SearchVO>>() {

					@Override
					public List<SearchVO> extractData(ResultSet rs) throws SQLException, DataAccessException {

						List<SearchVO> list = new ArrayList<SearchVO> ();

						while (rs.next())
						{

							SearchVO vo = new SearchVO();
							

							vo.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
							vo.setCf(rs.getString("CODICE_FISCALE_SOGGETTO"));
							vo.setDescTipoSogg(rs.getString("DESC_TIPO_SOGGETTO"));
							vo.setDenominazione(rs.getString("DENOMINAZIONE"));
							vo.setNdg(rs.getString("NDG"));
							list.add(vo);

						}

						return list;
					}
				};

				beneficiariList  = getJdbcTemplate().query(sql.toString(), rse);
				
			}
			
			
			
			 String sqlBlocchiAtttivi = "SELECT\r\n"
				  		+ "    COUNT(*)\r\n"
				  		+ "FROM\r\n"
				  		+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
				  		+ "    LEFT JOIN PBANDI_T_SOGG_DOMANDA_BLOCCO ptsdb ON prsd.PROGR_SOGGETTO_DOMANDA = ptsdb.PROGR_SOGGETTO_DOMANDA\r\n"
				  		+ "    LEFT JOIN PBANDI_D_CAUSALE_BLOCCO pdcb ON pdcb.ID_CAUSALE_BLOCCO = ptsdb.ID_CAUSALE_BLOCCO\r\n"
				  		+ "WHERE\r\n"
				  		+ "    prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
				  		+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
				  		+ "    AND ptsdb.DT_INSERIMENTO_SBLOCCO IS NULL\r\n"
				  		+ "    AND pdcb.FLAG_BLOCCO_ANAGRAFICO = 'S'\r\n"
				  		+ "    AND ID_SOGGETTO ="+ idSoggetto;
				  
				  int numeroBlocchi = getJdbcTemplate().queryForObject(sqlBlocchiAtttivi, Integer.class); 
					if(numeroBlocchi>0) {
						for (SearchVO searchVO : beneficiariList) {
							searchVO.setFlagBloccoAnag("SI");
						}
					} else {
						for (SearchVO searchVO : beneficiariList) {
							searchVO.setFlagBloccoAnag("NO");
						}
					}
				  


		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
			throw new DaoException("DaoException while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return beneficiariList;
	}

}
