/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbwebbo.dto.IntegerValueDTO;
import it.csi.pbandi.pbwebbo.dto.configuraBando.DataModalitaAgevolazioneDTO;
import it.csi.pbandi.pbwebbo.integration.dao.ConfigurazioneBandoDAO;
import it.csi.pbandi.pbwebbo.integration.dao.impl.rowmapper.NaturaCipeRowMapper;
import it.csi.pbandi.pbwebbo.integration.vo.NaturaCipeVO;
import it.csi.pbandi.pbwebbo.util.BeanRowMapper;
import it.csi.pbandi.pbwebbo.util.Constants;

//@Component
@Service
public class ConfigurazioneBandoDAOImpl extends JdbcDaoSupport implements ConfigurazioneBandoDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public ConfigurazioneBandoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<DataModalitaAgevolazioneDTO> findModalitaDiAgevolazioneAssociate(Long idBando) throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");

		// Controllo parametri

		List<DataModalitaAgevolazioneDTO> dataModalitaAgevolazioneDTO = new ArrayList<DataModalitaAgevolazioneDTO>();

		String sql;
		sql = "SELECT \r\n" + "	R.ID_BANDO,\r\n" + "	D.ID_MODALITA_AGEVOLAZIONE,\r\n"
				+ "  	D.DESC_MODALITA_AGEVOLAZIONE,\r\n" + "  	R.PERCENTUALE_IMPORTO_AGEVOLATO,\r\n"
				+ "  	R.MINIMO_IMPORTO_AGEVOLATO,\r\n" + "  	R.MASSIMO_IMPORTO_AGEVOLATO,\r\n"
				+ "  	R.PERIODO_STABILITA,\r\n" + "  	R.PERCENTUALE_IMPORTO_DA_EROGARE, \r\n" + "   	R.FLAG_LIQUIDAZIONE\r\n"
				+ "FROM \r\n" + "	PBANDI_D_MODALITA_AGEVOLAZIONE D,\r\n"
				+ "  	PBANDI_R_BANDO_MODALITA_AGEVOL R\r\n"
				+ "WHERE D.ID_MODALITA_AGEVOLAZIONE = R.ID_MODALITA_AGEVOLAZIONE\r\n"
				+ "AND NVL(TRUNC(D.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)\r\n" + "AND R.ID_BANDO  = ?";

		Object[] args = new Object[] { idBando };

		logger.info(prf + "\n" + sql + "\n");
		dataModalitaAgevolazioneDTO = getJdbcTemplate().query(sql, args,
				new BeanRowMapper(DataModalitaAgevolazioneDTO.class));

		logger.info(prf + " END");

		return dataModalitaAgevolazioneDTO;

	}

	public boolean modalitaAgevolazioneHasEstremiBancariAssociati(Long idBando, Long idModalitaAgevolazione)
			throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");

		boolean hasEsterimiBancariAssociati = false;

		try {

			String sql;
			sql = "SELECT COUNT(ID_BANDO_MOD_AG_ESTR_BAN) as VALUE  \r\n" + "FROM PBANDI_R_BANDO_MOD_AG_ESTR_BAN \r\n"
					+ "WHERE ID_BANDO = ? \r\n" + "AND ID_MODALITA_AGEVOLAZIONE = ?";

			logger.info("\n" + sql);

			Object[] args = new Object[] { idBando, idModalitaAgevolazione };

			List<IntegerValueDTO> resultset = getJdbcTemplate().query(sql, args,
					new BeanRowMapper(IntegerValueDTO.class));
			Integer numeroEstrimBancariAssociati = resultset.get(0).getValue();
			if (numeroEstrimBancariAssociati != null && numeroEstrimBancariAssociati > 0)
				hasEsterimiBancariAssociati = true;

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}

		return hasEsterimiBancariAssociati;

	}

	@Override
	public List<NaturaCipeVO> findNatureCipe() throws Exception {
		String prf = "[ConfigurazioneBandoDAOImpl::findNatureCipe]";
		LOG.info(prf + " BEGIN");
		List<NaturaCipeVO> nature = null;

		try {

			String sql;
			sql = "SELECT * FROM PBANDI_D_NATURA_CIPE pdnc WHERE DT_FINE_VALIDITA IS NULL OR DT_FINE_VALIDITA > SYSDATE";

			nature = getJdbcTemplate().query(sql, new NaturaCipeRowMapper());

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}

		return nature;
	}

	@Override
	public Object getModalitaAgevErogazone() {
		String prf = "[ConfigurazioneBandoDAOImpl::findNatureCipe]";
		LOG.info(prf + " BEGIN");
		List<DecodificaDTO> listModAgev = new ArrayList<DecodificaDTO>();

		try {

			String sql;
			sql = "SELECT *\r\n"
				+ "FROM PBANDI_D_MODALITA_AGEVOLAZIONE \r\n"
				+ "WHERE ID_MODALITA_AGEVOLAZIONE_RIF IN (1,5,10)\r\n"
				+ "ORDER BY DESC_MODALITA_AGEVOLAZIONE ";

			RowMapper<DecodificaDTO> rowMapp = new RowMapper<DecodificaDTO>() {
				
				@Override
				public DecodificaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					
					
					DecodificaDTO modAgev =  new DecodificaDTO(); 
					modAgev.setId(rs.getLong("ID_MODALITA_AGEVOLAZIONE"));
					modAgev.setDescrizione(rs.getString("DESC_MODALITA_AGEVOLAZIONE"));
					modAgev.setDescrizioneBreve(rs.getString("DESC_BREVE_MODALITA_AGEVOLAZ"));
					return modAgev;
				}
			};
			listModAgev = getJdbcTemplate().query(sql,rowMapp);

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
		} finally {
			logger.info(prf + " END");
		}
		return listModAgev;
	}

}
