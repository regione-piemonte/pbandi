/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

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
import it.csi.pbandi.pbgestfinbo.integration.dao.BeneficiarioDomandaDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.BeneficiarioDomandaVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;

@Service
public class BeneficiarioDomandaDAOImpl extends JdbcDaoSupport implements BeneficiarioDomandaDAO{

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public BeneficiarioDomandaDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public BeneficiarioDomandaVO getLegaleRappr(Long idSoggetto, Long idDomanda, HttpServletRequest req) throws DaoException {

		String prf = "[BeneficiarioDomandaDAOImpl::getLegaleRappr]";
		LOG.info(prf + " BEGIN");

		BeneficiarioDomandaVO beneficiario = new BeneficiarioDomandaVO();
		
		try {


			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ptpf.cognome, ptpf.nome, pdts.DESC_TIPO_SOGGETTO, pts.CODICE_FISCALE_SOGGETTO, \r\n"
					+ "ptpf.DT_NASCITA, pdc.DESC_COMUNE AS COMUNE_NASCITA, pdce.DESC_COMUNE_ESTERO AS COMUNE_ESTER_NASCITA, pdp.DESC_PROVINCIA AS PROVINCIA_NASCITA, \r\n"
					+ "pdr.DESC_REGIONE AS REGIONE_NASCITA, pdn.DESC_NAZIONE AS NAZIONE_NASCITA, pti.DESC_INDIRIZZO AS INDIRIZZO_RESIDENZA, pdc2.DESC_COMUNE AS COMUNE_RESIDENZA, pdce2.DESC_COMUNE_ESTERO AS COMUNE_ESTERO_RESIDENZA, \r\n"
					+ "pdp2.DESC_PROVINCIA AS PROVINCIA_RESIDENZA, pti.CAP, pdr2.DESC_REGIONE AS REGIONE_RESIDENZA, pdn2.DESC_NAZIONE AS NAZIONE_RESIDENZA\r\n"
					+ "FROM PBANDI_T_SOGGETTO pts \r\n"
					+ "INNER JOIN PBANDI_D_TIPO_SOGGETTO pdts \r\n"
					+ "ON pts.ID_TIPO_SOGGETTO = pdts.ID_TIPO_SOGGETTO \r\n"
					+ "INNER JOIN PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
					+ "ON pts.ID_SOGGETTO = prsd.ID_SOGGETTO\r\n"
					+ "INNER JOIN PBANDI_T_PERSONA_FISICA ptpf \r\n"
					+ "ON prsd.ID_SOGGETTO = ptpf.ID_SOGGETTO AND prsd.ID_PERSONA_FISICA = ptpf.ID_PERSONA_FISICA \r\n"
					+ "LEFT OUTER JOIN PBANDI_D_COMUNE pdc \r\n"
					+ "ON ptpf.ID_COMUNE_ITALIANO_NASCITA = pdc.ID_COMUNE -- comune di nascita\r\n"
					+ "LEFT OUTER JOIN PBANDI_D_COMUNE_ESTERO pdce \r\n"
					+ "ON ptpf.ID_COMUNE_ESTERO_NASCITA = pdce.ID_COMUNE_ESTERO \r\n"
					+ "LEFT OUTER JOIN PBANDI_D_PROVINCIA pdp \r\n"
					+ "ON pdc.ID_PROVINCIA = pdp.ID_PROVINCIA -- provincia di nascita\r\n"
					+ "LEFT OUTER JOIN PBANDI_D_REGIONE pdr \r\n"
					+ "ON pdp.ID_REGIONE = pdr.ID_REGIONE-- regione di nascita\r\n"
					+ "LEFT OUTER JOIN PBANDI_D_NAZIONE pdn \r\n"
					+ "ON ptpf.ID_NAZIONE_NASCITA = pdn.ID_NAZIONE -- nazione di nascita\r\n"
					+ "INNER JOIN PBANDI_T_INDIRIZZO pti \r\n"
					+ "ON prsd.ID_INDIRIZZO_PERSONA_FISICA = pti.ID_INDIRIZZO \r\n"
					+ "LEFT OUTER JOIN PBANDI_D_COMUNE pdc2\r\n"
					+ "ON pti.ID_COMUNE = pdc2.ID_COMUNE -- comune di residenza\r\n"
					+ "LEFT OUTER JOIN PBANDI_D_COMUNE_ESTERO pdce2 \r\n"
					+ "ON pti.ID_COMUNE_ESTERO = pdce2.ID_COMUNE_ESTERO \r\n"
					+ "LEFT OUTER JOIN PBANDI_D_PROVINCIA pdp2 \r\n"
					+ "ON pdc2.ID_PROVINCIA = pdp2.ID_PROVINCIA -- provincia di residenza\r\n"
					+ "LEFT OUTER JOIN PBANDI_D_REGIONE pdr2 \r\n"
					+ "ON pdp2.ID_REGIONE = pdr2.ID_REGIONE -- regione di residenza\r\n"
					+ "LEFT OUTER JOIN PBANDI_D_NAZIONE pdn2 \r\n"
					+ "ON pti.ID_NAZIONE = pdn2.ID_NAZIONE -- nazione di residenza\r\n"
					+ "WHERE prsd.ID_SOGGETTO = "+idSoggetto+" AND prsd.ID_DOMANDA = "+idDomanda+" AND pts.ID_TIPO_SOGGETTO = 1;");

			ResultSetExtractor<BeneficiarioDomandaVO> rse = new ResultSetExtractor<BeneficiarioDomandaVO>() {

				@Override
				public BeneficiarioDomandaVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					BeneficiarioDomandaVO vo = new BeneficiarioDomandaVO();
					vo.setCognome(rs.getString("COGNOME"));
					vo.setNome(rs.getString("NOME"));
					vo.setTipoSoggetto(rs.getString("DESC_TIPO_SOGGETTO"));
					vo.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
					vo.setDataNascita(rs.getDate("DT_NASCITA"));
					vo.setCap(rs.getLong("CAP"));
					if(rs.getString("COMUNE_ESTER_NASCITA").equals(null)) {
						vo.setComuneNascita(rs.getString("COMUNE_NASCITA"));
						vo.setProvinciaNascita(rs.getString("PROVINCIA_NASCITA"));
						vo.setRegioneNascita(rs.getString("REGIONE_NASCITA"));
						vo.setNazioneNascita(rs.getString("NAZIONE_NASCITA"));
						vo.setComuneResidenza(rs.getString("COMUNE_RESIDENZA"));
						vo.setProvinciaResidenza(rs.getString("PROVINCIA_RESIDENZA"));
						vo.setRegioneResidenza(rs.getString("REGIONE_RESIDENZA"));
						vo.setNazioneResidenza(rs.getString("NAZIONE_RESIDENZA"));
					}else {
						vo.setComuneNascita(rs.getString("COMUNE_ESTER_NASCITA"));
						vo.setComuneResidenza(rs.getString("COMUNE_ESTERO_RESIDENZA"));
					}
					
					return vo;
				}
			};
			beneficiario = getJdbcTemplate().query(sql.toString(), rse);

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + e.getMessage());

		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw new DaoException("DaoException while trying to read " + e.getMessage());
		}
		finally {
			LOG.info(prf + " END");
		}

		return beneficiario;
	}

	@Override
	public BeneficiarioDomandaVO getSedeAmm(Long idSoggetto, Long idDomanda, HttpServletRequest req)
			throws DaoException {
		String prf = "[BeneficiarioDomandaDAOImpl::getSedeAmm]";
		LOG.info(prf + " BEGIN");

		BeneficiarioDomandaVO beneficiario = new BeneficiarioDomandaVO();
		
		try {


			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT ptse.PARTITA_IVA, pdaa.COD_ATECO, pdaa.DESC_ATECO, pti.DESC_INDIRIZZO, pti.CAP, \r\n"
					+ "pdc.DESC_COMUNE, pdp.DESC_PROVINCIA, pdr.DESC_REGIONE, pdn.DESC_NAZIONE \r\n"
					+ "FROM PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
					+ "INNER JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds \r\n"
					+ "ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "INNER JOIN PBANDI_T_SEDE ptse \r\n"
					+ "ON prsds.ID_SEDE = ptse.ID_SEDE AND prsds.ID_SEDE = ptse.ID_SEDE  \r\n"
					+ "INNER JOIN PBANDI_D_ATTIVITA_ATECO pdaa \r\n"
					+ "ON ptse.ID_ATTIVITA_ATECO = pdaa.ID_ATTIVITA_ATECO \r\n"
					+ "INNER JOIN PBANDI_T_INDIRIZZO pti \r\n"
					+ "ON prsds.ID_INDIRIZZO = pti.ID_INDIRIZZO \r\n"
					+ "INNER JOIN PBANDI_D_COMUNE pdc \r\n"
					+ "ON pti.ID_COMUNE = pdc.ID_COMUNE \r\n"
					+ "INNER JOIN PBANDI_D_PROVINCIA pdp \r\n"
					+ "ON pti.ID_PROVINCIA = pdp.ID_PROVINCIA \r\n"
					+ "INNER JOIN PBANDI_D_REGIONE pdr \r\n"
					+ "ON pdp.ID_REGIONE = pdr.ID_REGIONE \r\n"
					+ "INNER JOIN PBANDI_D_NAZIONE pdn \r\n"
					+ "ON pti.ID_NAZIONE = pdn.ID_NAZIONE \r\n"
					+ "WHERE prsd.ID_SOGGETTO = "+idSoggetto+" AND prsds.ID_TIPO_SEDE = 2 AND prsd.ID_DOMANDA = "+idDomanda+";");

			ResultSetExtractor<BeneficiarioDomandaVO> rse = new ResultSetExtractor<BeneficiarioDomandaVO>() {

				@Override
				public BeneficiarioDomandaVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					BeneficiarioDomandaVO vo = new BeneficiarioDomandaVO();
					
					vo.setIndirizzoSedeAmm(rs.getString("DESC_INDIRIZZO"));
					vo.setpIvaAmm(rs.getString("PARTITA_IVA"));
					vo.setComuneSedeAmm(rs.getString("DESC_COMUNE"));
					vo.setProvinciaSedeAmm(rs.getString("DESC_PROVINCIA"));
					vo.setCapSedeAmm(rs.getLong("capSedeAmm"));
					vo.setRegioneSedeAmm(rs.getString("DESC_REGIONE"));
					vo.setNazioneSedeAmm(rs.getString("DESC_NAZIONE"));
					vo.setCodiceAtecoAmm(rs.getString("COD_ATECO"));
					vo.setDescAtecoAmm(rs.getString("DESC_ATECO"));
					
					return vo;
				}
			};
			beneficiario = getJdbcTemplate().query(sql.toString(), rse);

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + e.getMessage());

		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw new DaoException("DaoException while trying to read " + e.getMessage());
		}
		finally {
			LOG.info(prf + " END");
		}

		return beneficiario;
	}

	@Override
	public BeneficiarioDomandaVO getRecapiti(Long idSoggetto, Long idDomanda, HttpServletRequest req)
			throws DaoException {
		String prf = "[BeneficiarioDomandaDAOImpl::getRecapiti]";
		LOG.info(prf + " BEGIN");

		BeneficiarioDomandaVO beneficiario = new BeneficiarioDomandaVO();
		
		try {


			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT ptr.EMAIL, ptr.FAX, ptr.TELEFONO \r\n"
					+ "FROM PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
					+ "INNER JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds \r\n"
					+ "ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA_SEDE \r\n"
					+ "INNER JOIN PBANDI_T_RECAPITI ptr \r\n"
					+ "ON prsds.ID_RECAPITI = ptr.ID_RECAPITI \r\n"
					+ "WHERE prsd.ID_SOGGETTO = "+idSoggetto+" AND prsds.ID_TIPO_SEDE = 2 AND prsd.ID_DOMANDA = "+idDomanda+";");

			ResultSetExtractor<BeneficiarioDomandaVO> rse = new ResultSetExtractor<BeneficiarioDomandaVO>() {

				@Override
				public BeneficiarioDomandaVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					BeneficiarioDomandaVO vo = new BeneficiarioDomandaVO();
					
					vo.setTelefono(rs.getLong("TELEFONO"));
					vo.setFax(rs.getLong("FAX"));
					vo.setEmail(rs.getString("EMAIL"));
					
					return vo;
				}
			};
			beneficiario = getJdbcTemplate().query(sql.toString(), rse);

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + e.getMessage());

		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw new DaoException("DaoException while trying to read " + e.getMessage());
		}
		finally {
			LOG.info(prf + " END");
		}

		return beneficiario;
	}

	@Override
	public BeneficiarioDomandaVO getConto(Long idSoggetto, Long idDomanda, HttpServletRequest req)
			throws DaoException {
		String prf = "[BeneficiarioDomandaDAOImpl::getRecapiti]";
		LOG.info(prf + " BEGIN");

		BeneficiarioDomandaVO beneficiario = new BeneficiarioDomandaVO();
		
		try {


			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT pteb.NUMERO_CONTO, pteb.IBAN, pdb.DESC_BANCA \r\n"
					+ "FROM PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
					+ "INNER JOIN PBANDI_T_ESTREMI_BANCARI pteb \r\n"
					+ "ON prsd.ID_ESTREMI_BANCARI = pteb.ID_ESTREMI_BANCARI \r\n"
					+ "INNER JOIN PBANDI_D_BANCA pdb \r\n"
					+ "ON pteb.ID_BANCA = pdb.ID_BANCA \r\n"
					+ "WHERE prsd.ID_SOGGETTO = 13105  AND prsd.ID_DOMANDA = "+idDomanda+";");

			ResultSetExtractor<BeneficiarioDomandaVO> rse = new ResultSetExtractor<BeneficiarioDomandaVO>() {

				@Override
				public BeneficiarioDomandaVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					BeneficiarioDomandaVO vo = new BeneficiarioDomandaVO();
					
					vo.setNumeroConto(rs.getLong("NUMERO_CONTO"));
					vo.setIban(rs.getString("IBAN"));
					vo.setBanca(rs.getString("DESC_BANCA"));
					
					return vo;
				}
			};
			beneficiario = getJdbcTemplate().query(sql.toString(), rse);

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + e.getMessage());

		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw new DaoException("DaoException while trying to read " + e.getMessage());
		}
		finally {
			LOG.info(prf + " END");
		}

		return beneficiario;
	}

	@Override
	public BeneficiarioDomandaVO getDelegati(Long idDomanda, HttpServletRequest req) throws DaoException {
		String prf = "[BeneficiarioDomandaDAOImpl::getRecapiti]";
		LOG.info(prf + " BEGIN");

		BeneficiarioDomandaVO beneficiario = new BeneficiarioDomandaVO();
		
		try {


			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT pdtsc.DESC_TIPO_SOGGETTO_CORRELATO, ptpf.NOME, ptpf.COGNOME, pts.CODICE_FISCALE_SOGGETTO, pdts.DESC_TIPO_SOGGETTO,\r\n"
					+ "ptpf.DT_NASCITA, pdc.DESC_COMUNE AS COMUNE_NASCITA, pdc.CAP AS CAP_NASCITA, pdce.DESC_COMUNE_ESTERO AS COMUNE_ESTERO_NASCITA, \r\n"
					+ "pdp.DESC_PROVINCIA AS PROVINCIA_NASCITA, pdn.DESC_NAZIONE AS NAZIONE_NASCITA, pti.DESC_INDIRIZZO AS INDIRIZZO_RESIDENZA, pti.CAP AS CAP_RESIDENZA,\r\n"
					+ "pdc2.DESC_COMUNE AS COMUNE_RESIDENZA, pdce2.DESC_COMUNE_ESTERO AS COMUNE_ESTERO_RESIDENZA, \r\n"
					+ "pdp2.DESC_PROVINCIA AS PROVINCIA_RESIDENZA, pti.CAP, pdr2.DESC_REGIONE AS REGIONE_RESIDENZA, pdn2.DESC_NAZIONE AS NAZIONE_RESIDENZA\r\n"
					+ "FROM PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
					+ "INNER JOIN PBANDI_R_SOGG_DOM_SOGG_CORREL prsdsc \r\n"
					+ "ON prsd.PROGR_SOGGETTO_DOMANDA = prsdsc.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "INNER JOIN PBANDI_R_SOGGETTI_CORRELATI prsc \r\n"
					+ "ON prsdsc.PROGR_SOGGETTI_CORRELATI = prsc.PROGR_SOGGETTI_CORRELATI \r\n"
					+ "INNER JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc \r\n"
					+ "ON prsc.ID_TIPO_SOGGETTO_CORRELATO = pdtsc.ID_TIPO_SOGGETTO_CORRELATO \r\n"
					+ "INNER JOIN PBANDI_T_PERSONA_FISICA ptpf \r\n"
					+ "ON prsd.ID_SOGGETTO = ptpf.ID_SOGGETTO AND prsd.ID_PERSONA_FISICA = ptpf.ID_PERSONA_FISICA \r\n"
					+ "INNER JOIN PBANDI_T_SOGGETTO pts \r\n"
					+ "ON ptpf.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
					+ "INNER JOIN PBANDI_D_TIPO_SOGGETTO pdts \r\n"
					+ "ON pts.ID_TIPO_SOGGETTO = pdts.ID_TIPO_SOGGETTO \r\n"
					+ "LEFT OUTER JOIN PBANDI_D_COMUNE pdc \r\n"
					+ "ON ptpf.ID_COMUNE_ITALIANO_NASCITA = pdc.ID_COMUNE -- comune di nascita\r\n"
					+ "LEFT OUTER JOIN PBANDI_D_COMUNE_ESTERO pdce \r\n"
					+ "ON ptpf.ID_COMUNE_ESTERO_NASCITA = pdce.ID_COMUNE_ESTERO \r\n"
					+ "LEFT OUTER JOIN PBANDI_D_PROVINCIA pdp \r\n"
					+ "ON pdc.ID_PROVINCIA = pdp.ID_PROVINCIA -- provincia di nascita\r\n"
					+ "LEFT OUTER JOIN PBANDI_D_REGIONE pdr \r\n"
					+ "ON pdp.ID_REGIONE = pdr.ID_REGIONE-- regione di nascita\r\n"
					+ "LEFT OUTER JOIN PBANDI_D_NAZIONE pdn \r\n"
					+ "ON ptpf.ID_NAZIONE_NASCITA = pdn.ID_NAZIONE -- nazione di nascita\r\n"
					+ "--INNER JOIN PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
					+ "--ON ptpf.ID_PERSONA_FISICA = prsd.ID_PERSONA_FISICA AND ptpf.ID_SOGGETTO = prsd.ID_SOGGETTO \r\n"
					+ "LEFT OUTER JOIN PBANDI_T_INDIRIZZO pti \r\n"
					+ "ON prsd.ID_INDIRIZZO_PERSONA_FISICA = pti.ID_INDIRIZZO \r\n"
					+ "LEFT OUTER JOIN PBANDI_D_COMUNE pdc2\r\n"
					+ "ON pti.ID_COMUNE = pdc2.ID_COMUNE -- comune di residenza\r\n"
					+ "LEFT OUTER JOIN PBANDI_D_COMUNE_ESTERO pdce2 \r\n"
					+ "ON pti.ID_COMUNE_ESTERO = pdce2.ID_COMUNE_ESTERO \r\n"
					+ "LEFT OUTER JOIN PBANDI_D_PROVINCIA pdp2 \r\n"
					+ "ON pdc2.ID_PROVINCIA = pdp2.ID_PROVINCIA -- provincia di residenza\r\n"
					+ "LEFT OUTER JOIN PBANDI_D_REGIONE pdr2 \r\n"
					+ "ON pdp2.ID_REGIONE = pdr2.ID_REGIONE -- regione di residenza\r\n"
					+ "LEFT OUTER JOIN PBANDI_D_NAZIONE pdn2 \r\n"
					+ "ON pti.ID_NAZIONE = pdn2.ID_NAZIONE -- nazione di residenza\r\n"
					+ "WHERE pts.ID_TIPO_SOGGETTO = 1 AND prsd.ID_DOMANDA = "+idDomanda+" AND prsd.ID_TIPO_ANAGRAFICA = 16;");

			ResultSetExtractor<BeneficiarioDomandaVO> rse = new ResultSetExtractor<BeneficiarioDomandaVO>() {

				@Override
				public BeneficiarioDomandaVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					BeneficiarioDomandaVO vo = new BeneficiarioDomandaVO();
					
					vo.setCognomeDeleg(rs.getString("COGNOME"));
					vo.setNomeDeleg(rs.getString("NOME"));
					vo.setTipoSoggettoDeleg(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
					vo.setCfDeleg(rs.getString("CODICE_FISCALE_SOGGETTO"));
					vo.setNascitaDeleg(rs.getDate("DT_NASCITA"));
					
					if(rs.getString("COMUNE_ESTERO_NASCITA").equals(null)) {
						vo.setComuneNascitaDeleg(rs.getString("COMUNE_NASCITA"));
						vo.setProvinciaNascitaDeleg(rs.getString("PROVINCIA_NASCITA"));
						vo.setNazioneNascitaDeleg(rs.getString("NAZIONE_NASCITA"));
						vo.setComuneResidDeleg(rs.getString("COMUNE_RESIDENZA"));
						vo.setProvinciaResidenzaDeleg(rs.getString("PROVINCIA_RESIDENZA"));
						vo.setRegioneResidenzaDeleg(rs.getString("REGIONE_RESIDENZA"));
						vo.setNazioneResidenzaDeleg(rs.getString("NAZIONE_RESIDENZA"));
						vo.setCapResidDeleg(rs.getLong("CAP"));
					}else {
						vo.setComuneNascitaDeleg(rs.getString("COMUNE_ESTER_NASCITA"));
						vo.setComuneResidDeleg(rs.getString("COMUNE_ESTERO_RESIDENZA"));
					}
					
					return vo;
				}
			};
			beneficiario = getJdbcTemplate().query(sql.toString(), rse);
			

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + e.getMessage());

		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw new DaoException("DaoException while trying to read " + e.getMessage());
		}
		finally {
			LOG.info(prf + " END");
		}

		return beneficiario;
	}

	@Override
	public BeneficiarioDomandaVO getConsulenti(Long idDomanda, HttpServletRequest req) throws DaoException {
		String prf = "[BeneficiarioDomandaDAOImpl::getRecapiti]";
		LOG.info(prf + " BEGIN");

		BeneficiarioDomandaVO beneficiario = new BeneficiarioDomandaVO();
		
		try {


			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT pdtsc.DESC_TIPO_SOGGETTO_CORRELATO, pteg.DENOMINAZIONE_ENTE_GIURIDICO, pts.CODICE_FISCALE_SOGGETTO, \r\n"
					+ "pdts.DESC_TIPO_SOGGETTO, pts2.PARTITA_IVA, pdaa.COD_ATECO, pdaa.DESC_ATECO, pti.DESC_INDIRIZZO, pti.CAP, pdc.DESC_COMUNE,\r\n"
					+ "pdp.DESC_PROVINCIA, pdr.DESC_REGIONE, pdn.DESC_NAZIONE \r\n"
					+ "FROM PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
					+ "INNER JOIN PBANDI_R_SOGG_DOM_SOGG_CORREL prsdsc \r\n"
					+ "ON prsd.PROGR_SOGGETTO_DOMANDA = prsdsc.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "INNER JOIN PBANDI_R_SOGGETTI_CORRELATI prsc \r\n"
					+ "ON prsdsc.PROGR_SOGGETTI_CORRELATI = prsc.PROGR_SOGGETTI_CORRELATI \r\n"
					+ "INNER JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc \r\n"
					+ "ON prsc.ID_TIPO_SOGGETTO_CORRELATO = pdtsc.ID_TIPO_SOGGETTO_CORRELATO \r\n"
					+ "INNER JOIN PBANDI_T_ENTE_GIURIDICO pteg \r\n"
					+ "ON prsd.ID_SOGGETTO = pteg.ID_SOGGETTO AND prsd.ID_ENTE_GIURIDICO = pteg.ID_ENTE_GIURIDICO \r\n"
					+ "INNER JOIN PBANDI_T_SOGGETTO pts \r\n"
					+ "ON pteg.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
					+ "INNER JOIN PBANDI_D_TIPO_SOGGETTO pdts \r\n"
					+ "ON pts.ID_TIPO_SOGGETTO = pdts.ID_TIPO_SOGGETTO \r\n"
					+ "INNER JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds \r\n"
					+ "ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "INNER JOIN PBANDI_T_SEDE pts2 \r\n"
					+ "ON prsds.ID_SEDE = pts2.ID_SEDE \r\n"
					+ "INNER JOIN PBANDI_D_ATTIVITA_ATECO pdaa \r\n"
					+ "ON pts2.ID_ATTIVITA_ATECO = pdaa.ID_ATTIVITA_ATECO \r\n"
					+ "INNER JOIN PBANDI_T_INDIRIZZO pti \r\n"
					+ "ON prsds.ID_INDIRIZZO = pti.ID_INDIRIZZO \r\n"
					+ "INNER JOIN PBANDI_D_COMUNE pdc \r\n"
					+ "ON pti.ID_COMUNE = pdc.ID_COMUNE \r\n"
					+ "INNER JOIN PBANDI_D_PROVINCIA pdp \r\n"
					+ "ON pti.ID_PROVINCIA = pdp.ID_PROVINCIA \r\n"
					+ "INNER JOIN PBANDI_D_REGIONE pdr \r\n"
					+ "ON pdp.ID_REGIONE = pdr.ID_REGIONE \r\n"
					+ "INNER JOIN PBANDI_D_NAZIONE pdn \r\n"
					+ "ON pti.ID_NAZIONE = pdn.ID_NAZIONE \r\n"
					+ "WHERE pts.ID_TIPO_SOGGETTO = 2 AND prsd.ID_DOMANDA = "+idDomanda+" AND prsd.ID_TIPO_ANAGRAFICA = 17;");

			ResultSetExtractor<BeneficiarioDomandaVO> rse = new ResultSetExtractor<BeneficiarioDomandaVO>() {

				@Override
				public BeneficiarioDomandaVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					BeneficiarioDomandaVO vo = new BeneficiarioDomandaVO();
					
//					vo.setDenominazioneCons(rs.getString(""));
//					vo.setRuoloSoggCons(ruoloSoggCons);
//					vo.setTipoSoggettoCons();
//					vo.setCfCons(cfCons);
//					vo.setIndirizzoResidDeleg(indirizzoResidDeleg);
//					vo.setComuneSedeLeg(comuneSedeLeg);
//					vo.setpIvaCons(pIvaCons);
//					vo.setProvinciaSedeLeg(provinciaSedeLeg);
//					vo.setCapSedeLeg(capSedeLeg);
//					vo.setNazioneSedeLeg(nazioneSedeLeg);
//					vo.setCodiceAtecoLeg(codiceAtecoLeg);
//					vo.setDescAtecoLeg(descAtecoLeg);
//					
					return vo;
				}
			};
			beneficiario = getJdbcTemplate().query(sql.toString(), rse);

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + e.getMessage());

		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw new DaoException("DaoException while trying to read " + e.getMessage());
		}
		finally {
			LOG.info(prf + " END");
		}

		return beneficiario;
	}




}
