/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.dao.HomeDAO;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.BeneficiarioProgettoBandoLineaRuoloRowMapper;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.PermessoVORowMapper;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.RegolaAssociataBandoLineaRowMapper;
import it.csi.pbandi.pbservizit.integration.vo.BeneficiarioProgettoBandoLineaRuoloVO;
import it.csi.pbandi.pbservizit.integration.vo.PermessoVO;
import it.csi.pbandi.pbservizit.integration.vo.RegolaAssociataBandoLineaVO;
import it.csi.pbandi.pbservizit.util.Constants;
import it.csi.pbandi.pbservizit.util.FileSqlUtil;


@Component
public class HomeDAOImpl extends JdbcDaoSupport implements HomeDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public HomeDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public HomeDAOImpl() {
	}

	@Autowired
	protected FileSqlUtil fileSqlUtil;

	@Override
	public List<PermessoVO> getPermessi(String codiceRuolo, Long idSoggetto) throws ErroreGestitoException {
		String prf = "[HomeDAOImpl::getPermesso]";
		LOG.info(prf + " BEGIN");
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(
					"select DESC_MENU as descMenu, DESC_BREVE_PERMESSO as descBrevePermesso, ID_PERMESSO as idPermesso ");
			sql.append("from ( ");
			sql.append(
					"select pt.* , rta.id_soggetto, ta.desc_breve_tipo_anagrafica, ta.desc_tipo_anagrafica, p.desc_breve_permesso, p.desc_permesso, p.desc_menu ");
			sql.append(
					"from pbandi_r_permesso_tipo_anagraf pt, pbandi_d_tipo_anagrafica ta, pbandi_d_permesso p, pbandi_r_sogg_tipo_anagrafica rta ");
			sql.append(
					"where p.id_permesso = pt.id_permesso and ta.id_tipo_anagrafica = pt.id_tipo_anagrafica and rta.id_tipo_anagrafica = ta.id_tipo_anagrafica and pt.flag_menu = 'S' ");
			sql.append(
					"and (TRUNC(sysdate) >= NVL(TRUNC(p.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1) and TRUNC(sysdate) < NVL(TRUNC(p.DT_FINE_VALIDITA), TRUNC(sysdate) +1)) ");
			sql.append(
					"and (TRUNC(sysdate) >= NVL(TRUNC(ta.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1) and TRUNC(sysdate) < NVL(TRUNC(ta.DT_FINE_VALIDITA), TRUNC(sysdate) +1)) ");
			sql.append(
					"and (TRUNC(sysdate) >= NVL(TRUNC(pt.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1) and TRUNC(sysdate) < NVL(TRUNC(pt.DT_FINE_VALIDITA), TRUNC(sysdate) +1)) ");
			sql.append(
					"and (TRUNC(sysdate) >= NVL(TRUNC(rta.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1) and TRUNC(sysdate) < NVL(TRUNC(rta.DT_FINE_VALIDITA), TRUNC(sysdate) +1)) ) ");
			sql.append("where DESC_BREVE_TIPO_ANAGRAFICA = '" + codiceRuolo + "' and ID_SOGGETTO = " + idSoggetto + " order by DESC_MENU");

			LOG.debug(sql.toString());
			LOG.info(prf + "descBreveTipoAnagrafica: " + codiceRuolo + "; idSoggetto: " + idSoggetto);

			List<PermessoVO> p = getJdbcTemplate().query(sql.toString(), new PermessoVORowMapper());
			if (p != null && p.size() > 0) {
				LOG.info(prf + "record found: " + p.size());
				return p;
			} else {
				LOG.info(prf + "record found: 0");
				return null;
			}
		} catch (Exception e) {
			LOG.error(prf + "Exception while executing query to get PermessoVO", e);
			throw new ErroreGestitoException("DaoException while executing query to get PermessoVO", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Boolean isArchivioFileForBeneficiario(Long idUtente, String identitaDigitale, Long idSoggettoBeneficiario,
			String codiceRuolo) throws UtenteException, IOException {
		String prf = "[HomeDAOImpl::isArchivioFileForBeneficiario]";
		LOG.info(prf + " BEGIN");
		Boolean ret = Boolean.FALSE;
		if (idUtente == null || identitaDigitale == null || idSoggettoBeneficiario == null || codiceRuolo == null) {
			throw new UtenteException("userInfo non valorizzato correttamente");
		}
		try {
			String sql = fileSqlUtil.getQuery("BeneficiarioProgettoBandoLineaRuoloVO.sql");
			LOG.debug(sql.toString());

			List<BeneficiarioProgettoBandoLineaRuoloVO> bandolineaRuolo = getJdbcTemplate().query(sql.toString(),
					new Object[] { codiceRuolo, idSoggettoBeneficiario },
					new BeneficiarioProgettoBandoLineaRuoloRowMapper());

			if (bandolineaRuolo != null && bandolineaRuolo.size() > 0) {
				String progrBandoLinea = "(";
				for (BeneficiarioProgettoBandoLineaRuoloVO b : bandolineaRuolo) {
					progrBandoLinea += b.getProgrBandoLineaIntervento() + ", ";
				}
				progrBandoLinea = progrBandoLinea.substring(0, progrBandoLinea.length() - 2);
				progrBandoLinea += ")";

				String sqlRegola = fileSqlUtil.getQuery("RegolaAssociataBandoLineaVO.sql");
				sqlRegola += " where PROGR_BANDO_LINEA_INTERVENTO in " + progrBandoLinea + " and DESC_BREVE_REGOLA IN "
						+ "('BR42', 'BR51', 'BR52', 'BR53')";
				LOG.debug(sqlRegola);
				List<RegolaAssociataBandoLineaVO> bandolineaRegola = getJdbcTemplate().query(sqlRegola,
						new RegolaAssociataBandoLineaRowMapper());
				if (bandolineaRegola != null && bandolineaRegola.size() > 0)
					ret = Boolean.TRUE;
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			LOG.info(prf + " BEGIN");
		}
		return ret;
	}
}
