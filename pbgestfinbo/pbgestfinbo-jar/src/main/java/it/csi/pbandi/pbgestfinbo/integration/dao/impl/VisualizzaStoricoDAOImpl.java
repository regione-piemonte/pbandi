/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
import it.csi.pbandi.pbgestfinbo.integration.dao.VisualizzaStoricoDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.VisualizzaStoricoPFRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.VisualizzaStoricoRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.VisualizzaStoricoPFVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.VisualizzaStoricoVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;

@Service
public class VisualizzaStoricoDAOImpl extends JdbcDaoSupport implements VisualizzaStoricoDAO{
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	public VisualizzaStoricoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	public VisualizzaStoricoDAOImpl() {}

	@Override
	public List<VisualizzaStoricoVO> getVistaStoricoDomanda(Long idSoggetto, Long idDomanda,
			HttpServletRequest req) throws DaoException {
		String prf = "[VisualizzaStoricoDAOImpl::getVistaStoricoDomanda]";
		LOG.info(prf + " BEGIN");

		List<VisualizzaStoricoVO> storico = new ArrayList<VisualizzaStoricoVO>();
		
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT\r\n" + 
					"	pdta.DESC_TIPO_ANAGRAFICA,\r\n" + 
					"	pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n" + 
					"	pteg.FLAG_PUBBLICO_PRIVATO,\r\n" + 
					"	pteg.COD_UNI_IPA,\r\n" + 
					"	pteg.DT_COSTITUZIONE,\r\n" + 
					"	pdfg.DESC_FORMA_GIURIDICA,\r\n" + 
					"	pteg.DT_INIZIO_ATTIVITA_ESITO,\r\n" + 
					"	ptr.PEC,\r\n" + 
					"	pts.PARTITA_IVA,\r\n" + 
					"	pts2.CODICE_FISCALE_SOGGETTO,\r\n" + 
					"	pti.DESC_INDIRIZZO,\r\n" + 
					"	pti.CAP,\r\n" + 
					"	pdc.DESC_COMUNE,\r\n" + 
					"	pdp.SIGLA_PROVINCIA,\r\n" + 
					"	pdr.DESC_REGIONE,\r\n" + 
					"	pdn.DESC_NAZIONE,\r\n" + 
					"	pdaa.COD_ATECO,\r\n" + 
					"	pdaa.DESC_ATECO,\r\n" + 
					"	pdsa.DESC_STATO_ATTIVITA,\r\n" + 
					"	pteg.PERIODO_SCADENZA_ESERCIZIO,\r\n" + 
					"	pteg.DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n" + 
					"	pteg.FLAG_RATING_LEGALITA,\r\n" + 
					"	pti2.NUM_ISCRIZIONE,\r\n" + 
					"	pti2.DT_ISCRIZIONE,\r\n" + 
					"	pdss.DESC_SEZIONE_SPECIALE \r\n" + 
					"	--dati anagrafici\r\n" + 
					"FROM\r\n" + 
					"	PBANDI_R_SOGGETTO_DOMANDA prsd\r\n" + 
					"LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds \r\n" + 
					"ON\r\n" + 
					"	prsds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n" + 
					"LEFT JOIN PBANDI_D_TIPO_ANAGRAFICA pdta \r\n" + 
					"ON\r\n" + 
					"	pdta.ID_TIPO_ANAGRAFICA = prsd.ID_TIPO_ANAGRAFICA\r\n" + 
					"LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg \r\n" + 
					"ON\r\n" + 
					"	pteg.ID_ENTE_GIURIDICO = prsd.ID_ENTE_GIURIDICO\r\n" + 
					"LEFT JOIN PBANDI_D_FORMA_GIURIDICA pdfg \r\n" + 
					"ON\r\n" + 
					"	pdfg.ID_FORMA_GIURIDICA = pteg.ID_FORMA_GIURIDICA\r\n" + 
					"LEFT JOIN PBANDI_T_RECAPITI ptr \r\n" + 
					"ON\r\n" + 
					"	ptr.ID_RECAPITI = prsds.ID_RECAPITI\r\n" + 
					"	--sede legale\r\n" + 
					"LEFT JOIN PBANDI_T_SEDE pts \r\n" + 
					"ON\r\n" + 
					"	pts.ID_SEDE = prsds.ID_SEDE\r\n" + 
					"LEFT JOIN PBANDI_T_INDIRIZZO pti \r\n" + 
					"ON\r\n" + 
					"	pti.ID_INDIRIZZO = prsds.ID_INDIRIZZO\r\n" + 
					"LEFT JOIN PBANDI_D_COMUNE pdc \r\n" + 
					"ON\r\n" + 
					"	pdc.ID_COMUNE = pti.ID_COMUNE\r\n" + 
					"LEFT JOIN PBANDI_D_PROVINCIA pdp \r\n" + 
					"ON\r\n" + 
					"	pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n" + 
					"LEFT JOIN PBANDI_D_REGIONE pdr \r\n" + 
					"ON\r\n" + 
					"	pdr.ID_REGIONE = pdp.ID_REGIONE\r\n" + 
					"LEFT JOIN PBANDI_D_NAZIONE pdn ON\r\n" + 
					"	pti.ID_NAZIONE = pdn.ID_NAZIONE\r\n" + 
					"	--attivita economica\r\n" + 
					"LEFT JOIN PBANDI_D_ATTIVITA_ATECO pdaa \r\n" + 
					"ON\r\n" + 
					"	pdaa.ID_ATTIVITA_ATECO = pts.ID_ATTIVITA_ATECO\r\n" + 
					"	--dati iscrizione\r\n" + 
					"LEFT JOIN PBANDI_T_ISCRIZIONE pti2 \r\n" + 
					"ON\r\n" + 
					"	pti2.ID_ISCRIZIONE = prsd.ID_ISCRIZIONE_PERSONA_GIURID\r\n" + 
					"LEFT JOIN PBANDI_T_SOGGETTO pts2 ON \r\n" + 
					"	pteg.ID_SOGGETTO = pts2.ID_SOGGETTO\r\n" + 
					"LEFT JOIN PBANDI_D_STATO_ATTIVITA pdsa ON\r\n" + 
					"	pteg.ID_STATO_ATTIVITA = pdsa.ID_STATO_ATTIVITA\r\n" + 
					"LEFT JOIN PBANDI_T_ENTE_GIUR_SEZI ptegs ON\r\n" + 
					"	ptegs.ID_SOGGETTO = pteg.ID_SOGGETTO\r\n" + 
					"LEFT JOIN PBANDI_D_SEZIONE_SPECIALE pdss ON\r\n" + 
					"	ptegs.ID_SEZIONE_SPECIALE = pdss.ID_SEZIONE_SPECIALE\r\n" + 
					"WHERE\r\n" + 
					"	prsd.ID_SOGGETTO = :idSoggetto\r\n" + 
					"	AND prsd.ID_DOMANDA = :idDomanda\r\n" + 
					"	AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n" + 
					"	AND prsds.ID_TIPO_SEDE = 1");
			
			Object[] args = new Object[]{idSoggetto, idDomanda};

			int[] types = new int[]{Types.NUMERIC, Types.NUMERIC};
					
			storico = getJdbcTemplate().query(sql.toString(), args, types, new VisualizzaStoricoRowMapper());

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read VisualizzaStoricoVO", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read VisualizzaStoricoVO", e);
			throw new DaoException("DaoException while trying to read VisualizzaStoricoVO", e);
		}
		finally {
			LOG.info(prf + " END");
		}
		return storico;
	}
	
	@Override
	public List<VisualizzaStoricoPFVO> getVistaStoricoDomandaPF(Long idSoggetto, Long idDomanda,
			HttpServletRequest req) throws DaoException {
		String prf = "[VisualizzaStoricoDAOImpl::getVistaStoricoDomanda]";
		LOG.info(prf + " BEGIN");

		List<VisualizzaStoricoPFVO> storico = new ArrayList<VisualizzaStoricoPFVO>();
		
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT \r\n" + 
					"	ptpf.ID_PERSONA_FISICA ,\r\n" + 
					"	ptpf.COGNOME ,\r\n" + 
					"	ptpf.NOME ,\r\n" + 
					"	pts.ID_TIPO_SOGGETTO ,\r\n" + 
					"	pdts.DESC_TIPO_SOGGETTO ,\r\n" + 
					"	ptpf.DT_NASCITA ,\r\n" + 
					"	ptpf.ID_COMUNE_ITALIANO_NASCITA ,\r\n" + 
					"	pdc.DESC_COMUNE ,\r\n" + 
					"	ptpf.ID_COMUNE_ESTERO_NASCITA ,\r\n" + 
					"	pdce.DESC_COMUNE_ESTERO ,\r\n" + 
					"	pts.CODICE_FISCALE_SOGGETTO ,\r\n" + 
					"	pdc.ID_PROVINCIA ,\r\n" + 
					"	pdp.SIGLA_PROVINCIA ,\r\n" + 
					"	pdp.ID_REGIONE ,\r\n" + 
					"	pdr.DESC_REGIONE ,\r\n" + 
					"	pdn.ID_NAZIONE ,\r\n" + 
					"	pdn.DESC_NAZIONE ,\r\n" + 
					"	prsd.ID_INDIRIZZO_PERSONA_FISICA \r\n" + 
					"	ID_INDIRIZZO_PERSONA_FISICA_D ,\r\n" + 
					"	prsd.ID_DOMANDA ,\r\n" + 
					"	prsp.ID_INDIRIZZO_PERSONA_FISICA ID_INDIRIZZO_PERSONA_FISICA_P ,\r\n" + 
					"	ptid.DESC_INDIRIZZO DESC_INDIRIZZO_D ,\r\n" + 
					"	ptip.DESC_INDIRIZZO DESC_INDIRIZZO_P ,\r\n" + 
					"	ptid.ID_COMUNE ID_COMUNE_D ,\r\n" + 
					"	ptid.ID_PROVINCIA ID_PROVINCIA_D ,\r\n" + 
					"	ptid.ID_NAZIONE ID_NAZIONE_D ,\r\n" + 
					"	ptip.ID_COMUNE ID_COMUNE_P ,\r\n" + 
					"	ptip.ID_PROVINCIA ID_PROVINCIA_P ,\r\n" + 
					"	ptip.ID_NAZIONE ID_NAZIONE_P , \r\n" + 
					"	pdcd.CAP CAP_D ,\r\n" + 
					"	pdcp.CAP CAP_P ,\r\n" + 
					"	pdpd.SIGLA_PROVINCIA SIGLA_PROVINCIA_D,\r\n" + 
					"	pdpp.SIGLA_PROVINCIA SIGLA_PROVINCIA_P ,\r\n" + 
					"	pdnd.DESC_NAZIONE DESC_NAZIONE_D , \r\n" + 
					"	pdnp.DESC_NAZIONE DESC_NAZIONE_P ,\r\n" + 
					"	pdpd.ID_REGIONE ID_REGIONE_D ,\r\n" + 
					"	pdpp.ID_REGIONE ID_REGIONE_P ,\r\n" + 
					"	pdrd.DESC_REGIONE DESC_REGIONE_D ,\r\n" + 
					"	pdrp.DESC_REGIONE DESC_REGIONE_P\r\n" + 
					"FROM\r\n" + 
					"	PBANDI_T_PERSONA_FISICA ptpf\r\n" + 
					"LEFT JOIN PBANDI_T_SOGGETTO pts ON\r\n" + 
					"	(ptpf.ID_SOGGETTO = pts.ID_SOGGETTO)\r\n" + 
					"LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON\r\n" + 
					"	(pts.ID_TIPO_SOGGETTO = pdts.ID_TIPO_SOGGETTO)\r\n" + 
					"LEFT JOIN PBANDI_D_COMUNE pdc ON\r\n" + 
					"	(ptpf.ID_COMUNE_ITALIANO_NASCITA = pdc.ID_COMUNE)\r\n" + 
					"LEFT JOIN PBANDI_D_COMUNE_ESTERO pdce ON\r\n" + 
					"	(ptpf.ID_COMUNE_ESTERO_NASCITA = pdce.ID_COMUNE_ESTERO)\r\n" + 
					"LEFT JOIN PBANDI_D_PROVINCIA pdp ON\r\n" + 
					"	(pdc.ID_PROVINCIA = pdp.ID_PROVINCIA)\r\n" + 
					"LEFT JOIN PBANDI_D_REGIONE pdr ON\r\n" + 
					"	(pdp.ID_REGIONE = pdr.ID_REGIONE)\r\n" + 
					"LEFT JOIN PBANDI_D_NAZIONE pdn ON\r\n" + 
					"	(ptpf.ID_NAZIONE_NASCITA = pdn.ID_NAZIONE)\r\n" + 
					"LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON\r\n" + 
					"	(ptpf.ID_SOGGETTO = prsd.ID_SOGGETTO)\r\n" + 
					"LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON\r\n" + 
					"	(ptpf.ID_SOGGETTO = prsp.ID_SOGGETTO)\r\n" + 
					"LEFT JOIN PBANDI_T_INDIRIZZO ptid ON\r\n" + 
					"	(prsd.ID_INDIRIZZO_PERSONA_FISICA = ptid.ID_INDIRIZZO)\r\n" + 
					"LEFT JOIN PBANDI_T_INDIRIZZO ptip ON\r\n" + 
					"	(prsp.ID_INDIRIZZO_PERSONA_FISICA = ptip.ID_INDIRIZZO)\r\n" + 
					"LEFT JOIN PBANDI_D_COMUNE pdcd ON\r\n" + 
					"	(ptid.ID_COMUNE = pdcd.ID_COMUNE)\r\n" + 
					"LEFT JOIN PBANDI_D_COMUNE pdcp ON\r\n" + 
					"	(ptip.ID_COMUNE = pdcp.ID_COMUNE)\r\n" + 
					"LEFT JOIN PBANDI_D_PROVINCIA pdpd ON\r\n" + 
					"	(ptid.ID_PROVINCIA = pdpd.ID_PROVINCIA)\r\n" + 
					"LEFT JOIN PBANDI_D_PROVINCIA pdpp ON\r\n" + 
					"	(ptip.ID_PROVINCIA = pdpp.ID_PROVINCIA)\r\n" + 
					"LEFT JOIN PBANDI_D_NAZIONE pdnd ON\r\n" + 
					"	(ptid.ID_NAZIONE = pdnd.ID_NAZIONE)\r\n" + 
					"LEFT JOIN PBANDI_D_NAZIONE pdnp ON\r\n" + 
					"	(ptip.ID_NAZIONE = pdnp.ID_NAZIONE)\r\n" + 
					"LEFT JOIN PBANDI_D_REGIONE pdrd ON\r\n" + 
					"	(ptid.ID_REGIONE = pdrd.ID_REGIONE)\r\n" + 
					"LEFT JOIN PBANDI_D_REGIONE pdrp ON\r\n" + 
					"	(ptip.ID_REGIONE = pdrp.ID_REGIONE)\r\n" + 
					"WHERE pts.ID_SOGGETTO = "+ idSoggetto+" AND prsd.ID_DOMANDA = "+ idDomanda);
					

			storico = getJdbcTemplate().query(sql.toString(),new VisualizzaStoricoPFRowMapper());

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}
		return storico;
	}

	@Override
	public List<VisualizzaStoricoVO> getVistaStoricoProgetto(Long idSoggetto, Long idProgetto, HttpServletRequest req)
			throws DaoException {
		String prf = "[VisualizzaStoricoDAOImpl::getVistaStoricoProgetto]";
		LOG.info(prf + " BEGIN");

		List<VisualizzaStoricoVO> storico = new ArrayList<VisualizzaStoricoVO>();
		
		try {


			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT  e.DENOMINAZIONE_ENTE_GIURIDICO, pdfg.DESC_FORMA_GIURIDICA, e.COD_UNI_IPA, s.ID_TIPO_SOGGETTO,\r\n"
					+ "s.CODICE_FISCALE_SOGGETTO, e.DT_COSTITUZIONE, pti.DESC_INDIRIZZO, pts.PARTITA_IVA, pdc.DESC_COMUNE, pdp.DESC_PROVINCIA, pti.CAP, pdn.DESC_NAZIONE,\r\n"
					+ "pdaa.COD_ATECO, pdaa.DESC_ATECO, ptis.NUM_ISCRIZIONE, ptis.DT_ISCRIZIONE, pdp.DESC_PROVINCIA\r\n"
					+ "FROM PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "INNER JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps \r\n"
					+ "ON prsp.PROGR_SOGGETTO_PROGETTO = prsps.PROGR_SOGGETTO_PROGETTO \r\n"
					+ "INNER JOIN PBANDI_T_ISCRIZIONE ptis\r\n"
					+ "ON prsp.ID_ISCRIZIONE_PERSONA_GIURID = ptis.ID_ISCRIZIONE \r\n"
					+ "INNER JOIN PBANDI_T_INDIRIZZO pti \r\n"
					+ "ON prsps.ID_INDIRIZZO = pti.ID_INDIRIZZO \r\n"
					+ "INNER JOIN PBANDI_D_COMUNE pdc \r\n"
					+ "ON pti.ID_COMUNE = pdc.ID_COMUNE \r\n"
					+ "INNER JOIN PBANDI_D_PROVINCIA pdp \r\n"
					+ "ON pti.ID_PROVINCIA = pdp.ID_PROVINCIA \r\n"
					+ "LEFT OUTER JOIN PBANDI_D_NAZIONE pdn \r\n"
					+ "ON pti.ID_NAZIONE = pdn.ID_NAZIONE \r\n"
					+ "RIGHT JOIN PBANDI_T_SEDE pts\r\n"
					+ "ON prsps.ID_SEDE = pts.ID_SEDE \r\n"
					+ "RIGHT JOIN PBANDI_D_ATTIVITA_ATECO pdaa \r\n"
					+ "ON pts.ID_ATTIVITA_ATECO = pdaa.ID_ATTIVITA_ATECO \r\n"
					+ "INNER JOIN PBANDI_T_ENTE_GIURIDICO e \r\n"
					+ "ON e.ID_SOGGETTO = prsp.ID_SOGGETTO AND e.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
					+ "INNER JOIN PBANDI_D_FORMA_GIURIDICA pdfg \r\n"
					+ "ON e.ID_FORMA_GIURIDICA = pdfg.ID_FORMA_GIURIDICA \r\n"
					+ "RIGHT JOIN PBANDI_T_SOGGETTO s \r\n"
					+ "ON s.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
					+ "RIGHT JOIN PBANDI_D_TIPO_SOGGETTO ts\r\n"
					+ "ON s.ID_TIPO_SOGGETTO = ts.ID_TIPO_SOGGETTO \r\n"
					+ "WHERE prsp.ID_SOGGETTO = "+idSoggetto+" AND prsp.ID_PROGETTO = "+idProgetto+" AND prsp.ID_TIPO_ANAGRAFICA = 1 AND prsps.ID_TIPO_SEDE = 1");

			storico = getJdbcTemplate().query(sql.toString(),new VisualizzaStoricoRowMapper());

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}
		return storico;
	}
	
	@Override
	public List<VisualizzaStoricoPFVO> getVistaStoricoProgettoPF(Long idSoggetto, Long idProgetto, HttpServletRequest req)
			throws DaoException {
		String prf = "[VisualizzaStoricoDAOImpl::getVistaStoricoProgetto]";
		LOG.info(prf + " BEGIN");

		List<VisualizzaStoricoPFVO> storico = new ArrayList<VisualizzaStoricoPFVO>();
		
		try {


			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT \r\n" + 
					"	ptpf.ID_PERSONA_FISICA ,\r\n" + 
					"	ptpf.COGNOME ,\r\n" + 
					"	ptpf.NOME ,\r\n" + 
					"	pts.ID_TIPO_SOGGETTO ,\r\n" + 
					"	pdts.DESC_TIPO_SOGGETTO ,\r\n" + 
					"	ptpf.DT_NASCITA ,\r\n" + 
					"	ptpf.ID_COMUNE_ITALIANO_NASCITA ,\r\n" + 
					"	pdc.DESC_COMUNE ,\r\n" + 
					"	ptpf.ID_COMUNE_ESTERO_NASCITA ,\r\n" + 
					"	pdce.DESC_COMUNE_ESTERO ,\r\n" + 
					"	pts.CODICE_FISCALE_SOGGETTO ,\r\n" + 
					"	pdc.ID_PROVINCIA ,\r\n" + 
					"	pdp.SIGLA_PROVINCIA ,\r\n" + 
					"	pdp.ID_REGIONE ,\r\n" + 
					"	pdr.DESC_REGIONE ,\r\n" + 
					"	pdn.ID_NAZIONE ,\r\n" + 
					"	pdn.DESC_NAZIONE ,\r\n" + 
					"	prsd.ID_INDIRIZZO_PERSONA_FISICA \r\n" + 
					"	ID_INDIRIZZO_PERSONA_FISICA_D ,\r\n" + 
					"	prsp.ID_INDIRIZZO_PERSONA_FISICA ID_INDIRIZZO_PERSONA_FISICA_P ,\r\n" + 
					"	prsp.ID_PROGETTO ,\r\n" + 
					"	ptid.DESC_INDIRIZZO DESC_INDIRIZZO_D ,\r\n" + 
					"	ptip.DESC_INDIRIZZO DESC_INDIRIZZO_P ,\r\n" + 
					"	ptid.ID_COMUNE ID_COMUNE_D ,\r\n" + 
					"	ptid.ID_PROVINCIA ID_PROVINCIA_D ,\r\n" + 
					"	ptid.ID_NAZIONE ID_NAZIONE_D ,\r\n" + 
					"	ptip.ID_COMUNE ID_COMUNE_P ,\r\n" + 
					"	ptip.ID_PROVINCIA ID_PROVINCIA_P ,\r\n" + 
					"	ptip.ID_NAZIONE ID_NAZIONE_P , \r\n" + 
					"	pdcd.CAP CAP_D ,\r\n" + 
					"	pdcp.CAP CAP_P ,\r\n" + 
					"	pdpd.SIGLA_PROVINCIA SIGLA_PROVINCIA_D,\r\n" + 
					"	pdpp.SIGLA_PROVINCIA SIGLA_PROVINCIA_P ,\r\n" + 
					"	pdnd.DESC_NAZIONE DESC_NAZIONE_D , \r\n" + 
					"	pdnp.DESC_NAZIONE DESC_NAZIONE_P ,\r\n" + 
					"	pdpd.ID_REGIONE ID_REGIONE_D ,\r\n" + 
					"	pdpp.ID_REGIONE ID_REGIONE_P ,\r\n" + 
					"	pdrd.DESC_REGIONE DESC_REGIONE_D ,\r\n" + 
					"	pdrp.DESC_REGIONE DESC_REGIONE_P\r\n" + 
					"FROM\r\n" + 
					"	PBANDI_T_PERSONA_FISICA ptpf\r\n" + 
					"LEFT JOIN PBANDI_T_SOGGETTO pts ON\r\n" + 
					"	(ptpf.ID_SOGGETTO = pts.ID_SOGGETTO)\r\n" + 
					"LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON\r\n" + 
					"	(pts.ID_TIPO_SOGGETTO = pdts.ID_TIPO_SOGGETTO)\r\n" + 
					"LEFT JOIN PBANDI_D_COMUNE pdc ON\r\n" + 
					"	(ptpf.ID_COMUNE_ITALIANO_NASCITA = pdc.ID_COMUNE)\r\n" + 
					"LEFT JOIN PBANDI_D_COMUNE_ESTERO pdce ON\r\n" + 
					"	(ptpf.ID_COMUNE_ESTERO_NASCITA = pdce.ID_COMUNE_ESTERO)\r\n" + 
					"LEFT JOIN PBANDI_D_PROVINCIA pdp ON\r\n" + 
					"	(pdc.ID_PROVINCIA = pdp.ID_PROVINCIA)\r\n" + 
					"LEFT JOIN PBANDI_D_REGIONE pdr ON\r\n" + 
					"	(pdp.ID_REGIONE = pdr.ID_REGIONE)\r\n" + 
					"LEFT JOIN PBANDI_D_NAZIONE pdn ON\r\n" + 
					"	(ptpf.ID_NAZIONE_NASCITA = pdn.ID_NAZIONE)\r\n" + 
					"LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON\r\n" + 
					"	(ptpf.ID_SOGGETTO = prsd.ID_SOGGETTO)\r\n" + 
					"LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON\r\n" + 
					"	(ptpf.ID_SOGGETTO = prsp.ID_SOGGETTO)\r\n" + 
					"LEFT JOIN PBANDI_T_INDIRIZZO ptid ON\r\n" + 
					"	(prsd.ID_INDIRIZZO_PERSONA_FISICA = ptid.ID_INDIRIZZO)\r\n" + 
					"LEFT JOIN PBANDI_T_INDIRIZZO ptip ON\r\n" + 
					"	(prsp.ID_INDIRIZZO_PERSONA_FISICA = ptip.ID_INDIRIZZO)\r\n" + 
					"LEFT JOIN PBANDI_D_COMUNE pdcd ON\r\n" + 
					"	(ptid.ID_COMUNE = pdcd.ID_COMUNE)\r\n" + 
					"LEFT JOIN PBANDI_D_COMUNE pdcp ON\r\n" + 
					"	(ptip.ID_COMUNE = pdcp.ID_COMUNE)\r\n" + 
					"LEFT JOIN PBANDI_D_PROVINCIA pdpd ON\r\n" + 
					"	(ptid.ID_PROVINCIA = pdpd.ID_PROVINCIA)\r\n" + 
					"LEFT JOIN PBANDI_D_PROVINCIA pdpp ON\r\n" + 
					"	(ptip.ID_PROVINCIA = pdpp.ID_PROVINCIA)\r\n" + 
					"LEFT JOIN PBANDI_D_NAZIONE pdnd ON\r\n" + 
					"	(ptid.ID_NAZIONE = pdnd.ID_NAZIONE)\r\n" + 
					"LEFT JOIN PBANDI_D_NAZIONE pdnp ON\r\n" + 
					"	(ptip.ID_NAZIONE = pdnp.ID_NAZIONE)\r\n" + 
					"LEFT JOIN PBANDI_D_REGIONE pdrd ON\r\n" + 
					"	(ptid.ID_REGIONE = pdrd.ID_REGIONE)\r\n" + 
					"LEFT JOIN PBANDI_D_REGIONE pdrp ON\r\n" + 
					"	(ptip.ID_REGIONE = pdrp.ID_REGIONE)\r\n" + 
					"WHERE pts.ID_SOGGETTO = "+ idSoggetto + "AND prsp.ID_PROGETTO = " + idProgetto);

			storico = getJdbcTemplate().query(sql.toString(),new VisualizzaStoricoPFRowMapper());

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}
		return storico;
	}

	

}
