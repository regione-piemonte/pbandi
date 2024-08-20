/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.AnagraficaBeneficiarioDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.DatiDomandaDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.AttivitaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.DatiDomandaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.IndirizzoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SoggettiCorrelatiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioPfVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DatiDomandaEgVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DatiDomandaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.RecapitoVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.DatiIntegrativiDTO;

@Service
public class DatiDomandaDAOimpl extends JdbcDaoSupport implements DatiDomandaDAO{

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public DatiDomandaDAOimpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	@Autowired
	AnagraficaBeneficiarioDAO anagraficaDao; 

	@Override
	public List<DatiDomandaVO> getDatiDomanda(Long idSoggetto, String idDomanda, HttpServletRequest req)
			throws DaoException {
		String prf = "[DatiDomandaDAOImpl::getDatiDomanda]";
		LOG.info(prf + " BEGIN");

		List<DatiDomandaVO> datiDomanda = new ArrayList<DatiDomandaVO>();
		try {

			
			BigDecimal idProgetto = checkProgetto(idDomanda, idSoggetto); 
			
			StringBuilder sql = new StringBuilder();
			
			if(idProgetto !=null && idProgetto.intValue()>0) {
				
				 sql.append("SELECT\r\n"
				 		+ "    ptp.ID_PROGETTO,\r\n"
				 		+ "    ptd.DT_PRESENTAZIONE_DOMANDA,\r\n"
				 		+ "    pdsd.DESC_STATO_DOMANDA,\r\n"
				 		+ "    ptr.EMAIL, ptr.pec, \r\n"
				 		+ "    ptr.ID_RECAPITI,\r\n"
				 		+ "    ptr.FAX,\r\n"
				 		+ "    ptr.TELEFONO,\r\n"
				 		+ "    pteb.IBAN,\r\n"
				 		+ "    pteb.ID_ESTREMI_BANCARI,\r\n"
				 		+ "    pteb.CAB,\r\n"
				 		+ "    pteb.ABI,\r\n"
				 		+ "    --pdb.DESC_BANCA,\r\n"
				 		+ "    CASE\r\n"
				 		+ "        WHEN (pteb.ID_AGENZIA IS NOT NULL) THEN pdb2.DESC_BANCA\r\n"
				 		+ "        ELSE pdb.DESC_BANCA\r\n"
				 		+ "    END AS desc_banca,\r\n"
				 		+ "    CASE\r\n"
				 		+ "        WHEN (pteb.ID_AGENZIA IS NOT NULL) THEN pdb2.ID_BANCA\r\n"
				 		+ "        ELSE pdb.ID_BANCA\r\n"
				 		+ "    END AS id_banca,pteb.NUMERO_CONTO ,\r\n"
				 		+ "    ptr.PEC,\r\n"
				 		+ "    ptd.NUMERO_DOMANDA,\r\n"
				 		+ "    ptd.ID_DOMANDA,\r\n"
				 		+ "    prsp.ID_DOCUMENTO_PERSONA_FISICA,\r\n"
				 		+ "    prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
				 		+ "    ptdo.ID_DOCUMENTO,\r\n"
				 		+ "    ptdo.NUMERO_DOCUMENTO,\r\n"
				 		+ "    ptdo.DT_RILASCIO_DOCUMENTO,\r\n"
				 		+ "    ptdo.DT_SCADENZA_DOCUMENTO,\r\n"
				 		+ "    pdtd.DESC_TIPO_DOCUMENTO,\r\n"
				 		+ "    pdtd.ID_TIPO_DOCUMENTO, ptdo.DOCUMENTO_RILASCIATO_DA\r\n"
				 		+ "FROM\r\n"
				 		+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
				 		+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA\r\n"
				 		+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA\r\n"
				 		+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.ID_STATO_DOMANDA = ptd.ID_STATO_DOMANDA\r\n"
				 		+ "    LEFT JOIN PBANDI_T_progetto ptp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO\r\n"
				 		+ "    LEFT JOIN PBANDI_D_STATO_PROGETTO pdsp ON ptp.ID_STATO_PROGETTO = pdsp.ID_STATO_PROGETTO\r\n"
				 		+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON prsp.ID_RECAPITI_PERSONA_FISICA = ptr.ID_RECAPITI\r\n"
				 		+ "    LEFT JOIN PBANDI_T_ESTREMI_BANCARI pteb ON prsp.ID_ESTREMI_BANCARI = pteb.ID_ESTREMI_BANCARI\r\n"
				 		+ "    LEFT JOIN pbandi_t_agenzia pta ON pta.ID_AGENZIA = pteb.ID_AGENZIA\r\n"
				 		+ "    LEFT JOIN PBANDI_D_BANCA pdb ON pteb.ID_BANCA = pdb.ID_BANCA\r\n"
				 		+ "    LEFT JOIN pbandi_d_banca pdb2 ON pdb2.ID_BANCA = pta.ID_BANCA\r\n"
				 		+ "    LEFT JOIN PBANDI_T_DOCUMENTO ptdo ON ptdo.ID_DOCUMENTO = prsp.ID_DOCUMENTO_PERSONA_FISICA\r\n"
				 		+ "    LEFT JOIN PBANDI_D_TIPO_DOCUMENTO pdtd ON ptdo.ID_TIPO_DOCUMENTO = pdtd.ID_TIPO_DOCUMENTO\r\n"
				 		+ "WHERE\r\n"
				 		+ "    prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
				 		+ "    AND prsp.ID_TIPO_BENEFICIARIO <> 4  \r\n"
				 		+ "    AND prsp.DT_FINE_VALIDITA IS NULL \r\n"
				 		+ "    AND prsp.ID_SOGGETTO = ?\r\n"
				 		+ "    AND prsp.ID_PROGETTO = ?");
				 
					datiDomanda = getJdbcTemplate().query(sql.toString(),new DatiDomandaRowMapper(), new Object[] {
							idSoggetto, idProgetto
					});
					
					for (DatiDomandaVO datiDomandaVO : datiDomanda) {
						datiDomandaVO.setIdProgetto(idProgetto);
					}
			} else {
			
				sql.append("SELECT\r\n"
						+ "    ptd.ID_DOMANDA,\r\n"
						+ "    ptd.DT_PRESENTAZIONE_DOMANDA,\r\n"
						+ "    pdsd.DESC_STATO_DOMANDA,\r\n"
						+ "    ptr.EMAIL, ptr.pec,\r\n"
						+ "    ptr.ID_RECAPITI,\r\n"
						+ "    ptr.FAX,\r\n"
						+ "    ptr.TELEFONO,\r\n"
						+ "    pteb.IBAN,\r\n"
						+ "    pteb.ID_ESTREMI_BANCARI,\r\n"
						+ "    pteb.CAB,\r\n"
						+ "    pteb.ABI, pteb.NUMERO_CONTO ,\r\n"
						+ "    --pdb.DESC_BANCA,\r\n"
						+ "    CASE\r\n"
						+ "        WHEN (pteb.ID_AGENZIA IS NOT NULL) THEN pdb2.DESC_BANCA\r\n"
						+ "        ELSE pdb.DESC_BANCA\r\n"
						+ "    END AS desc_banca,\r\n"
						+ "    CASE\r\n"
						+ "        WHEN (pteb.ID_AGENZIA IS NOT NULL) THEN pdb2.ID_BANCA\r\n"
						+ "        ELSE pdb.ID_BANCA\r\n"
						+ "    END AS id_banca,\r\n"
						+ "    ptr.PEC,\r\n"
						+ "    ptd.NUMERO_DOMANDA,\r\n"
						+ "    ptd.ID_DOMANDA,\r\n"
						+ "    prsd.ID_DOCUMENTO_PERSONA_FISICA,\r\n"
						+ "    ptdo.ID_DOCUMENTO,\r\n"
						+ "    ptdo.NUMERO_DOCUMENTO,\r\n"
						+ "    ptdo.DT_RILASCIO_DOCUMENTO,\r\n"
						+ "    ptdo.DT_SCADENZA_DOCUMENTO,\r\n"
						+ "    pdtd.DESC_TIPO_DOCUMENTO,\r\n"
						+ "    pdtd.ID_TIPO_DOCUMENTO, ptdo.DOCUMENTO_RILASCIATO_DA\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.ID_STATO_DOMANDA = ptd.ID_STATO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON prsd.ID_RECAPITI_PERSONA_FISICA = ptr.ID_RECAPITI\r\n"
						+ "    LEFT JOIN PBANDI_T_ESTREMI_BANCARI pteb ON prsd.ID_ESTREMI_BANCARI = pteb.ID_ESTREMI_BANCARI\r\n"
						+ "    LEFT JOIN pbandi_t_agenzia pta ON pta.ID_AGENZIA = pteb.ID_AGENZIA\r\n"
						+ "    LEFT JOIN PBANDI_D_BANCA pdb ON pteb.ID_BANCA = pdb.ID_BANCA\r\n"
						+ "    LEFT JOIN pbandi_d_banca pdb2 ON pdb2.ID_BANCA = pta.ID_BANCA\r\n"
						+ "    LEFT JOIN PBANDI_T_DOCUMENTO ptdo ON ptdo.ID_DOCUMENTO = prsd.ID_DOCUMENTO_PERSONA_FISICA\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_DOCUMENTO pdtd ON ptdo.ID_TIPO_DOCUMENTO = pdtd.ID_TIPO_DOCUMENTO\r\n"
						+ "WHERE\r\n"
						+ "    prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsd.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    AND prsd.ID_SOGGETTO = ?\r\n"
						+ "    AND ptd.NUMERO_DOMANDA = ?");
				
				datiDomanda = getJdbcTemplate().query(sql.toString(),new DatiDomandaRowMapper(), new Object[] {
					idSoggetto,idDomanda
				});
			}


			
			

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return datiDomanda;
	}
	
	private BigDecimal checkProgetto(String numeroDomanda, Long idSoggetto) {
		BigDecimal progetto=null;
		try {
			String query = "SELECT\r\n"
					+ "    prsp.ID_PROGETTO\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
					+ "WHERE\r\n"
					+ "    ptd.NUMERO_DOMANDA = '"+numeroDomanda+"'\r\n"
					//+ " and prsd.id_soggetto="+idSoggetto +"\r\n"
					+ " and rownum <2";


			progetto = getJdbcTemplate().queryForObject(query,  BigDecimal.class);
		} catch (Exception e) {
			LOG.error(e);
			return null; 
		}
		
		
		return progetto;
	}

	@Override
	public DatiDomandaEgVO getDatiDomandaEG(Long idSoggetto, String idDomanda, HttpServletRequest req)
			throws DaoException {
		String prf = "[DatiDomandaDAOImpl::getDatiDomanda]";
		LOG.info(prf + " BEGIN");

		DatiDomandaEgVO datiDomanda = new DatiDomandaEgVO();
		
		try {

			
			BigDecimal idProgetto = checkProgetto(idDomanda, idSoggetto);
			
			if(idProgetto!=null && idProgetto.intValue()>0) {
				String sql = "SELECT\r\n"
						+ "    ptp.ID_PROGETTO ,\r\n"
						+ "    ptd.DT_PRESENTAZIONE_DOMANDA,\r\n"
						+ "    pdsd.DESC_STATO_DOMANDA,\r\n"
						+ "    pts.PARTITA_IVA,\r\n"
						+ "    pti.DESC_INDIRIZZO,\r\n"
						+ "    pti.ID_INDIRIZZO,\r\n"
						+ "    pti.CAP,\r\n"
						+ "    pdc.DESC_COMUNE,\r\n"
						+ "    pdc.ID_COMUNE,\r\n"
						+ "    pdp.DESC_PROVINCIA,\r\n"
						+ "    pdp.ID_PROVINCIA,\r\n"
						+ "    pdr.DESC_REGIONE,\r\n"
						+ "    pdr.ID_REGIONE,\r\n"
						+ "    pdn.DESC_NAZIONE,\r\n"
						+ "    pdn.ID_NAZIONE,\r\n"
						+ "    ptr.EMAIL,\r\n"
						+ "    ptr.ID_RECAPITI,\r\n"
						+ "    ptr.FAX,\r\n"
						+ "    ptr.TELEFONO,\r\n"
						+ "    pteb.IBAN,\r\n"
						+ "    pteb.ID_ESTREMI_BANCARI,\r\n"
						+ "    --pdb.DESC_BANCA,\r\n"
						+ "    ptr.PEC, \r\n"
						+ "    ptd.NUMERO_DOMANDA , ptd.ID_DOMANDA, \r\n"
						+ "    CASE  WHEN (pteb.ID_AGENZIA IS NOT NULL ) THEN  pdb2.DESC_BANCA ELSE pdb.DESC_BANCA  END AS desc_banca ,\r\n"
				 		+ "     CASE  WHEN (pteb.ID_AGENZIA IS NOT NULL ) THEN  pdb2.ID_BANCA  ELSE pdb.ID_BANCA  END AS id_banca \r\n "
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_PROGETTO  prsp\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA \r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA \r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.ID_STATO_DOMANDA = ptd.ID_STATO_DOMANDA \r\n"
						+ "    LEFT JOIN PBANDI_T_progetto ptp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO \r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_PROGETTO  pdsp ON ptp.ID_STATO_PROGETTO  = pdsp.ID_STATO_PROGETTO  \r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps  ON prsps.PROGR_SOGGETTO_PROGETTO  = prsp.PROGR_SOGGETTO_PROGETTO \r\n"
						+ "    AND prsps.ID_TIPO_SEDE = 2\r\n"
						+ "    LEFT JOIN PBANDI_T_SEDE pts ON prsps.ID_SEDE = pts.ID_SEDE\r\n"
						+ "    LEFT JOIN PBANDI_T_INDIRIZZO pti ON prsps.ID_INDIRIZZO = pti.ID_INDIRIZZO\r\n"
						+ "    LEFT JOIN PBANDI_D_COMUNE pdc ON pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
						+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp ON pti.ID_PROVINCIA = pdp.ID_PROVINCIA\r\n"
						+ "    LEFT JOIN PBANDI_D_REGIONE pdr ON pdp.ID_REGIONE = pdr.ID_REGIONE\r\n"
						+ "    LEFT JOIN PBANDI_D_NAZIONE pdn ON pti.ID_NAZIONE = pdn.ID_NAZIONE\r\n"
						+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON prsps.ID_RECAPITI = ptr.ID_RECAPITI\r\n"
						+ "    LEFT JOIN PBANDI_T_ESTREMI_BANCARI pteb ON prsp.ID_ESTREMI_BANCARI = pteb.ID_ESTREMI_BANCARI\r\n"
						+ "    LEFT JOIN PBANDI_D_BANCA pdb ON pteb.ID_BANCA = pdb.ID_BANCA\r\n"
						+ "    LEFT JOIN pbandi_t_agenzia pta ON pta.ID_AGENZIA = pteb.ID_AGENZIA \r\n"
				 		+ "    LEFT JOIN PBANDI_D_BANCA pdb ON pteb.ID_BANCA = pdb.ID_BANCA\r\n"
				 		+ "    LEFT JOIN pbandi_d_banca pdb2 ON pdb2.ID_BANCA = pta.ID_BANCA \r\n"
						+ "WHERE\r\n"
						+ "    --AND prsp.ID_SOGGETTO = ?\r\n"
						+ "     prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND prsp.ID_PROGETTO =?\r\n"
						+ " --and rownum < 2";
				
				datiDomanda = getJdbcTemplate().query(sql.toString(), new ResultSetExtractor<DatiDomandaEgVO>() {
					
					@Override
					public DatiDomandaEgVO extractData(ResultSet rs) throws SQLException, DataAccessException {
						// TODO Auto-generated method stub
						DatiDomandaEgVO datiDomanda = new DatiDomandaEgVO();
						
						while(rs.next()) {
							
							datiDomanda.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
							datiDomanda.setStatoDomanda(rs.getString("DESC_STATO_DOMANDA"));
							datiDomanda.setDataPresentazioneDomanda(rs.getString("DT_PRESENTAZIONE_DOMANDA"));
							datiDomanda.setPartitaIva(rs.getString("PARTITA_IVA"));
							datiDomanda.setDescIndirizzo(rs.getString("DESC_INDIRIZZO"));
							datiDomanda.setCap(rs.getString("CAP"));
							datiDomanda.setDescComune(rs.getString("DESC_COMUNE"));
							datiDomanda.setDescProvincia(rs.getString("DESC_PROVINCIA"));
							datiDomanda.setDescRegione(rs.getString("DESC_REGIONE"));
							datiDomanda.setDescNazione(rs.getString("DESC_NAZIONE"));
							datiDomanda.setTelefono(rs.getString("TELEFONO"));
							datiDomanda.setFax(rs.getString("FAX"));
							datiDomanda.setEmail(rs.getString("EMAIL"));
							datiDomanda.setPec(rs.getString("PEC"));
							datiDomanda.setIban(rs.getString("IBAN"));
							datiDomanda.setBanca(rs.getString("DESC_BANCA"));
							datiDomanda.setIdComune(rs.getLong("ID_COMUNE"));
							datiDomanda.setIdProvincia(rs.getLong("ID_PROVINCIA"));
							datiDomanda.setIdRegione(rs.getLong("ID_REGIONE"));
							datiDomanda.setIdNazione(rs.getLong("ID_NAZIONE"));
							datiDomanda.setIdRecapiti(rs.getLong("ID_RECAPITI"));
							datiDomanda.setIdIndirizzo(rs.getLong("ID_INDIRIZZO"));
							datiDomanda.setIdEstremiBancari(rs.getLong("ID_ESTREMI_BANCARI"));
						}
						
						return datiDomanda;
					}
					
				} , new Object[] {
					 idProgetto
				});
				
				
				// recupero dei dati della sede amministrativa: 
				
				
			} else {
				
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT\r\n"
						+ "    ptd.NUMERO_DOMANDA,\r\n"
						+ "    ptd.DT_PRESENTAZIONE_DOMANDA,\r\n"
						+ "    pdsd.DESC_STATO_DOMANDA,\r\n"
						+ "    pts.PARTITA_IVA,\r\n"
						+ "    pti.DESC_INDIRIZZO, pti.ID_INDIRIZZO ,\r\n"
						+ "    pti.CAP,\r\n"
						+ "    pdc.DESC_COMUNE, pdc.ID_COMUNE ,\r\n"
						+ "    pdp.DESC_PROVINCIA, pdp.ID_PROVINCIA ,\r\n"
						+ "    pdr.DESC_REGIONE, pdr.ID_REGIONE ,\r\n"
						+ "    pdn.DESC_NAZIONE, pdn.ID_NAZIONE ,\r\n"
						+ "    ptr.EMAIL,ptr.ID_RECAPITI ,\r\n"
						+ "    ptr.FAX,\r\n"
						+ "    ptr.TELEFONO,\r\n"
						+ "    pteb.IBAN,pteb.ID_ESTREMI_BANCARI ,\r\n"
						+ "    --pdb.DESC_BANCA,\r\n"
						+ "    ptr.PEC,\r\n"
						+ "    CASE  WHEN (pteb.ID_AGENZIA IS NOT NULL ) THEN  pdb2.DESC_BANCA ELSE pdb.DESC_BANCA  END AS desc_banca ,\r\n"
				 		+ "    CASE  WHEN (pteb.ID_AGENZIA IS NOT NULL ) THEN  pdb2.ID_BANCA  ELSE pdb.ID_BANCA  END AS id_banca \r\n "
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON ptd.ID_STATO_DOMANDA = pdsd.ID_STATO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA and  prsds.ID_TIPO_SEDE = 2\r\n"
						+ "    LEFT JOIN PBANDI_T_SEDE pts ON prsds.ID_SEDE = pts.ID_SEDE\r\n"
						+ "    LEFT JOIN PBANDI_T_INDIRIZZO pti ON prsds.ID_INDIRIZZO = pti.ID_INDIRIZZO\r\n"
						+ "    LEFT JOIN PBANDI_D_COMUNE pdc ON pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
						+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp ON pti.ID_PROVINCIA = pdp.ID_PROVINCIA\r\n"
						+ "    LEFT JOIN PBANDI_D_REGIONE pdr ON pdp.ID_REGIONE = pdr.ID_REGIONE\r\n"
						+ "    LEFT JOIN PBANDI_D_NAZIONE pdn ON pti.ID_NAZIONE = pdn.ID_NAZIONE\r\n"
						+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON prsds.ID_RECAPITI = ptr.ID_RECAPITI\r\n"
						+ "    LEFT JOIN PBANDI_T_ESTREMI_BANCARI pteb ON prsd.ID_ESTREMI_BANCARI = pteb.ID_ESTREMI_BANCARI\r\n"
						+ "    LEFT JOIN PBANDI_D_BANCA pdb ON pteb.ID_BANCA = pdb.ID_BANCA\r\n"
						+ "    LEFT JOIN pbandi_t_agenzia pta ON pta.ID_AGENZIA = pteb.ID_AGENZIA \r\n"
				 		+ "    LEFT JOIN PBANDI_D_BANCA pdb ON pteb.ID_BANCA = pdb.ID_BANCA\r\n"
				 		+ "    LEFT JOIN pbandi_d_banca pdb2 ON pdb2.ID_BANCA = pta.ID_BANCA \r\n"
						+ "WHERE\r\n"
						+ "   prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "  AND prsd.ID_TIPO_ANAGRAFICA =1\r\n"
						+"	--AND prsd.ID_SOGGETTO = ?\r\n"  
						+"	AND ptd.NUMERO_DOMANDA = ?");
				
				datiDomanda = getJdbcTemplate().query(sql.toString(), new ResultSetExtractor<DatiDomandaEgVO>() {
					
					@Override
					public DatiDomandaEgVO extractData(ResultSet rs) throws SQLException, DataAccessException {
						// TODO Auto-generated method stub
						DatiDomandaEgVO datiDomanda = new DatiDomandaEgVO();
						
						while(rs.next()) {
							
							datiDomanda.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
							datiDomanda.setStatoDomanda(rs.getString("DESC_STATO_DOMANDA"));
							datiDomanda.setDataPresentazioneDomanda(rs.getString("DT_PRESENTAZIONE_DOMANDA"));
							datiDomanda.setPartitaIva(rs.getString("PARTITA_IVA"));
							datiDomanda.setDescIndirizzo(rs.getString("DESC_INDIRIZZO"));
							datiDomanda.setCap(rs.getString("CAP"));
							datiDomanda.setDescComune(rs.getString("DESC_COMUNE"));
							datiDomanda.setDescProvincia(rs.getString("DESC_PROVINCIA"));
							datiDomanda.setDescRegione(rs.getString("DESC_REGIONE"));
							datiDomanda.setDescNazione(rs.getString("DESC_NAZIONE"));
							datiDomanda.setTelefono(rs.getString("TELEFONO"));
							datiDomanda.setFax(rs.getString("FAX"));
							datiDomanda.setEmail(rs.getString("EMAIL"));
							datiDomanda.setPec(rs.getString("PEC"));
							datiDomanda.setIban(rs.getString("IBAN"));
							datiDomanda.setBanca(rs.getString("DESC_BANCA"));
							datiDomanda.setIdComune(rs.getLong("ID_COMUNE"));
							datiDomanda.setIdProvincia(rs.getLong("ID_PROVINCIA"));
							datiDomanda.setIdRegione(rs.getLong("ID_REGIONE"));
							datiDomanda.setIdNazione(rs.getLong("ID_NAZIONE"));
							datiDomanda.setIdRecapiti(rs.getLong("ID_RECAPITI"));
							datiDomanda.setIdIndirizzo(rs.getLong("ID_INDIRIZZO"));
							datiDomanda.setIdEstremiBancari(rs.getLong("ID_ESTREMI_BANCARI"));
						}
						
						return datiDomanda;
					}
					
				}, new Object[] {
					 idDomanda
				});
			}
			
			if(datiDomanda.getIdRegione() != null && datiDomanda.getIdRegione()>0) {
				datiDomanda.setIdNazione((long)118);
				datiDomanda.setDescNazione("ITALIA");
			}
			

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read DatiDomandaEgVO", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DatiDomandaEgVO", e);
			throw new DaoException("DaoException while trying to read DatiDomandaEgVO", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return datiDomanda;
	}

	@Override
	public List<SoggettiCorrelatiVO> getElencoSoggCorrDipDaDomanda(String idDomanda, Long idSoggetto) {
		String prf = "[DatiDomandaDAOImpl::getElencoSoggCorrDipDaDomanda]";
		LOG.info(prf + " BEGIN");
		
		List<SoggettiCorrelatiVO> listaSoggetti = new ArrayList<SoggettiCorrelatiVO>(); 
		List<SoggettiCorrelatiVO> listaSoggettiPF = new ArrayList<SoggettiCorrelatiVO>(); 
		List<SoggettiCorrelatiVO> listaSoggettiEG= new ArrayList<SoggettiCorrelatiVO>(); 
		
		try {
			
			String getVeroIddomanda ="select ptd.id_domanda from pbandi_t_domanda ptd\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON ptd.id_domanda = prsd.ID_DOMANDA \r\n"
					+ "    where numero_domanda='"+idDomanda+"'\r\n"
					//+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
					+ " 	AND ROWNUM=1";
			String sql= null ,sql2 = null;
			BigDecimal veroIdDomanda = (BigDecimal) getJdbcTemplate().queryForObject(getVeroIddomanda, BigDecimal.class); 
			BigDecimal idProgetto =checkProgetto(idDomanda, idSoggetto);
			
			if(idProgetto!=null && idProgetto.intValue()>0) {
				sql = "WITH dati_soggetto AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsp.ID_SOGGETTO,\r\n"
						+ "        ptpf.COGNOME AS DESC1,\r\n"
						+ "        ptpf.NOME AS desc2,\r\n"
						+ "        prsp.ID_PERSONA_FISICA,\r\n"
						+ "        prsp.PROGR_SOGGETTO_PROGETTO \r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_PROGETTO prsp,\r\n"
						+ "        PBANDI_T_PERSONA_FISICA ptpf\r\n"
						+ "    WHERE\r\n"
						+ "        PRSP.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "        and PRSP.ID_PERSONA_FISICA = ptpf.ID_PERSONA_FISICA\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "    DISTINCT prsp.ID_SOGGETTO AS nag,\r\n"
						+ "    prsc.QUOTA_PARTECIPAZIONE,\r\n"
						+ "    pdtsc.DESC_TIPO_SOGGETTO_CORRELATO AS ruolo,\r\n"
						+ "    ds.desc1,\r\n"
						+ "    ds.desc2,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO, pts.ndg,\r\n"
						+ "    pdts.DESC_TIPO_SOGGETTO, pdtsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "    prsc.PROGR_SOGGETTI_CORRELATI,\r\n"
						+ "    prsp.PROGR_SOGGETTO_PROGETTO,  pts.ndg\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc ON prsp.PROGR_SOGGETTO_PROGETTO = prspsc.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prspsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PRSp.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "    LEFT JOIN dati_soggetto ds ON ds.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\r\n"
						+ "WHERE\r\n"
						+ "    prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "    AND pdtsc.FLAG_INDIPENDENTE ='N'\r\n"
						+ "    AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    AND prsc.ID_TIPO_SOGGETTO_CORRELATO NOT IN (2, 18, 19, 24, 25)\r\n"
						+ "    AND prsp.ID_ENTE_GIURIDICO IS NULL\r\n"
						+ "    AND prsp.ID_PROGETTO  ="+ idProgetto;
				
				sql2 = "  WITH dati_soggetto AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsp.ID_SOGGETTO,\r\n"
						+ "        pteg.DENOMINAZIONE_ENTE_GIURIDICO AS desc1,\r\n"
						+ "        '' AS desc2,\r\n"
						+ "        prsp.ID_ENTE_GIURIDICO,\r\n"
						+ "        prsp.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    FROM\r\n"
						+ "         PBANDI_R_SOGGETTO_PROGETTO prsp,\r\n"
						+ "         PBANDI_T_ENTE_GIURIDICO pteg\r\n"
						+ "    WHERE\r\n"
						+ "        prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "        AND pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "    DISTINCT prsp.ID_SOGGETTO AS nag,\r\n"
						+ "    prsc.QUOTA_PARTECIPAZIONE,\r\n"
						+ "    pdtsc.DESC_TIPO_SOGGETTO_CORRELATO AS ruolo,\r\n"
						+ "    ds.desc1,\r\n"
						+ "    ds.desc2, pdtsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    pts.ndg,\r\n"
						+ "    pdts.DESC_TIPO_SOGGETTO,\r\n"
						+ "    prsc.PROGR_SOGGETTI_CORRELATI,\r\n"
						+ "    prsp.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "    prsc.DT_FINE_VALIDITA,\r\n"
						+ "    prsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "    pts.ndg\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc ON prsp.PROGR_SOGGETTO_PROGETTO = prspsc.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prspsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PRSp.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "    LEFT JOIN dati_soggetto ds ON ds.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
						+ "WHERE\r\n"
						+ "    prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "    AND pdtsc.FLAG_INDIPENDENTE ='N'\r\n"
						+ "    AND prsc.ID_TIPO_SOGGETTO_CORRELATO NOT IN (2, 18, 19, 24, 25)\r\n"
						+ "    AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    AND prsp.ID_PERSONA_FISICA IS NULL\r\n"
						+ "    AND prsp.ID_PROGETTO  ="+ idProgetto;
				
				
				
				
			} else {
				
				sql ="WITH dati_soggetto AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsd.ID_SOGGETTO,\r\n"
						+ "        ptpf.COGNOME AS DESC1,\r\n"
						+ "        ptpf.NOME AS desc2,\r\n"
						+ "        prsd.ID_PERSONA_FISICA,\r\n"
						+ "        prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_DOMANDA prsd,\r\n"
						+ "        PBANDI_T_PERSONA_FISICA ptpf\r\n"
						+ "    WHERE\r\n"
						+ "        PRSD.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "        and PRSD.ID_PERSONA_FISICA = ptpf.ID_PERSONA_FISICA\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "    DISTINCT prsd.ID_SOGGETTO AS nag,\r\n"
						+ "    prsc.QUOTA_PARTECIPAZIONE,\r\n"
						+ "    pdtsc.DESC_TIPO_SOGGETTO_CORRELATO AS ruolo, pdtsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "    ds.desc1,\r\n"
						+ "    ds.desc2,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO, pts.ndg,\r\n"
						+ "    pdts.DESC_TIPO_SOGGETTO,\r\n"
						+ "    prsc.PROGR_SOGGETTI_CORRELATI,\r\n"
						+ "    prsd.PROGR_SOGGETTO_DOMANDA,  pts.ndg\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_DOM_SOGG_CORREL prsdsc ON PRSD.PROGR_SOGGETTO_DOMANDA = prsdsc.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prsdsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PRSD.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "    LEFT JOIN dati_soggetto ds ON ds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "WHERE\r\n"
						+ "    prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "    AND pdtsc.FLAG_INDIPENDENTE = 'N'\r\n"
						+ "    AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    AND prsc.ID_TIPO_SOGGETTO_CORRELATO NOT IN (2, 18, 19, 24, 25)\r\n"
						+ "    AND prsd.ID_ENTE_GIURIDICO IS NULL\r\n"
						+ "    AND prsd.ID_DOMANDA = "+veroIdDomanda+"\r\n";
				
				
				sql2 ="  WITH dati_soggetto AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsd2.ID_SOGGETTO,\r\n"
						+ "        pteg.DENOMINAZIONE_ENTE_GIURIDICO AS desc1,\r\n"
						+ "        '' AS desc2,\r\n"
						+ "        prsd2.ID_ENTE_GIURIDICO,\r\n"
						+ "        prsd2.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_DOMANDA prsd2,\r\n"
						+ "        PBANDI_T_ENTE_GIURIDICO pteg\r\n"
						+ "    WHERE\r\n"
						+ "        PRSD2.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "        AND pteg.ID_ENTE_GIURIDICO = PRSD2.ID_ENTE_GIURIDICO\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "    DISTINCT prsd.ID_SOGGETTO AS nag,\r\n"
						+ "    prsc.QUOTA_PARTECIPAZIONE,\r\n"
						+ "    pdtsc.DESC_TIPO_SOGGETTO_CORRELATO AS ruolo, pdtsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "    ds.desc1,\r\n"
						+ "    ds.desc2,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,pts.ndg,\r\n"
						+ "    pdts.DESC_TIPO_SOGGETTO,\r\n"
						+ "    prsc.PROGR_SOGGETTI_CORRELATI,\r\n"
						+ "    prsd.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "    prsc.DT_FINE_VALIDITA,\r\n"
						+ "    prsc.ID_TIPO_SOGGETTO_CORRELATO, pts.ndg\r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_DOM_SOGG_CORREL prsdsc ON PRSD.PROGR_SOGGETTO_DOMANDA = prsdsc.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prsdsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PRSD.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "    LEFT JOIN dati_soggetto ds ON ds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "WHERE\r\n"
						+ "    prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "    AND pdtsc.FLAG_INDIPENDENTE = 'N'\r\n"
						+ "    AND prsc.ID_TIPO_SOGGETTO_CORRELATO NOT IN (2, 18, 19, 24, 25)\r\n"
						+ "    AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    AND prsd.ID_PERSONA_FISICA IS NULL\r\n"
						+ "     AND prsd.ID_DOMANDA = "+veroIdDomanda+"\r\n";
			}
										
			RowMapper<SoggettiCorrelatiVO> lista= new RowMapper<SoggettiCorrelatiVO>() {
				@Override
				public SoggettiCorrelatiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					SoggettiCorrelatiVO soggetto =  new SoggettiCorrelatiVO(); 
					soggetto.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
					soggetto.setCognome(rs.getString("desc1"));
					soggetto.setNome(rs.getString("desc2"));
					soggetto.setNag(rs.getString("nag"));
					soggetto.setTipologia(rs.getString("DESC_TIPO_SOGGETTO"));
					soggetto.setIdSoggettoCorellato(rs.getLong("PROGR_SOGGETTI_CORRELATI"));
					soggetto.setRuolo(rs.getString("ruolo"));
					soggetto.setIdTipoSoggettoCorrelato(rs.getInt("ID_TIPO_SOGGETTO_CORRELATO"));
					soggetto.setQuotaPartecipazione(rs.getLong("QUOTA_PARTECIPAZIONE"));
					soggetto.setIdDomanda(veroIdDomanda.toString());
					soggetto.setNdg(rs.getString("ndg"));
					return soggetto;
				}		
			};
			
			listaSoggettiPF = getJdbcTemplate().query(sql, lista); 
			listaSoggettiEG=getJdbcTemplate().query(sql2, lista); 
			
			if(listaSoggettiEG != null)
			listaSoggetti.addAll(listaSoggettiEG);
			if(listaSoggettiPF!=null)
			listaSoggetti.addAll(listaSoggettiPF);
			
		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
		}

		return listaSoggetti;
	}

	@Override
	public AnagraficaBeneficiarioPfVO getAnagSoggCorrDipDaDomPF(Long idSoggetto, String idDomanda, BigDecimal idSoggCorr) {
		String prf = "[DatiDomandaDAOImpl::getAnagSoggCorrDipDaDomPF]";
		LOG.info(prf + " BEGIN");
		
		AnagraficaBeneficiarioPfVO soggetto = new AnagraficaBeneficiarioPfVO(); 
		
		try {
			//String getVeroIddomanda ="select id_domanda from pbandi_t_domanda where numero_domanda='"+idDomanda+"'\r\n"; 	
			//BigDecimal veroIdDomanda = (BigDecimal) getJdbcTemplate().queryForObject(getVeroIddomanda, BigDecimal.class); 
			String getVeroIddomanda ="select ptd.numero_domanda from pbandi_t_domanda ptd\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON ptd.id_domanda = prsd.ID_DOMANDA \r\n"
					+ "    where PRSD.id_domanda="+idDomanda+"\r\n"
//					+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
					+ " 	AND ROWNUM=1";
			String query= null;
			String veroIdDomanda = getJdbcTemplate().queryForObject(getVeroIddomanda, String.class); 
			BigDecimal idProgetto =checkProgetto(veroIdDomanda, idSoggetto);
			
			if(idProgetto!=null) {
				query ="WITH dati_soggetto AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsp.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "        PTPF.ID_SOGGETTO,\r\n"
						+ "        ptpf.DT_NASCITA,\r\n"
						+ "        ptpf.COGNOME,\r\n"
						+ "        ptpf.NOME,\r\n"
						+ "        pdc.DESC_COMUNE AS comune_nasc,\r\n"
						+ "        pdp.DESC_PROVINCIA AS prov_nasc,\r\n"
						+ "        pdr.DESC_REGIONE AS regione_nasc,\r\n"
						+ "        pdn.DESC_NAZIONE AS nazione_nasc,\r\n"
						+ "        pdc.ID_COMUNE,\r\n"
						+ "        pdc.CAP AS capPF,\r\n"
						+ "        pdp.ID_PROVINCIA AS id_prov,\r\n"
						+ "        pdr.ID_REGIONE AS id_reg,\r\n"
						+ "        pdn.ID_NAZIONE,\r\n"
						+ "        PTPF.ID_PERSONA_FISICA,\r\n"
						+ "        pdce.ID_COMUNE_ESTERO,\r\n"
						+ "        pdce.DESC_COMUNE_ESTERO,\r\n"
						+ "        pdn2.DESC_NAZIONE AS nazione_est,\r\n"
						+ "        pdn2.ID_NAZIONE AS id_nazione_est\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "        LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE pdc ON ptpf.ID_COMUNE_ITALIANO_NASCITA = pdc.ID_COMUNE\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE_ESTERO pdce ON ptpf.ID_COMUNE_ESTERO_NASCITA = pdce.ID_COMUNE_ESTERO\r\n"
						+ "        LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdc.ID_PROVINCIA = pdp.ID_PROVINCIA\r\n"
						+ "        LEFT JOIN PBANDI_D_REGIONE pdr ON pdp.ID_REGIONE = pdr.ID_REGIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn ON PTPF.ID_NAZIONE_NASCITA = pdn.ID_NAZIONE\r\n"
						+ "        AND pdce.ID_NAZIONE = pdn.ID_NAZIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn2 ON pdce.ID_NAZIONE = pdn2.ID_NAZIONE\r\n"
						+ "    WHERE\r\n"
						+ "        prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "),\r\n"
						+ "residenza AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsp.ID_SOGGETTO,\r\n"
						+ "        prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "        prsp.ID_PROGETTO,\r\n"
						+ "        pti.ID_INDIRIZZO,\r\n"
						+ "        pti.DESC_INDIRIZZO,\r\n"
						+ "        pti.cap AS CAPPF,\r\n"
						+ "        pdc.ID_COMUNE AS id_com_res,\r\n"
						+ "        pdc.DESC_COMUNE AS com_res,\r\n"
						+ "        pdp.ID_PROVINCIA AS id_prov_res,\r\n"
						+ "        pdp.SIGLA_PROVINCIA AS prov_res,\r\n"
						+ "        pdr.ID_REGIONE AS id_reg_res,\r\n"
						+ "        pdr.DESC_REGIONE AS reg_res,\r\n"
						+ "        pdn.ID_NAZIONE AS id_naz_res,\r\n"
						+ "        pdn.DESC_NAZIONE AS naz_res\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "        LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO = prsp.ID_INDIRIZZO_PERSONA_FISICA\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE pdc ON pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
						+ "        LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
						+ "        LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pdp.ID_REGIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn ON pdn.ID_NAZIONE = pti.ID_NAZIONE\r\n"
						+ "    WHERE\r\n"
						+ "        prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "    ds.id_soggetto,\r\n"
						+ "    ds.cognome,\r\n"
						+ "    ds.nome,\r\n"
						+ "    ds.DT_NASCITA,\r\n"
						+ "    ds.comune_nasc,\r\n"
						+ "    ds.prov_nasc,\r\n"
						+ "    res.capPF,\r\n"
						+ "    ds.regione_nasc,\r\n"
						+ "    ds.nazione_nasc,\r\n"
						+ "    ds.id_comune,\r\n"
						+ "    ds.id_reg,\r\n"
						+ "    ds.id_prov,\r\n"
						+ "    ds.id_nazione,\r\n"
						+ "    ds.id_nazione_est,\r\n"
						+ "    ds.nazione_est,\r\n"
						+ "    ds.ID_COMUNE_ESTERO,\r\n"
						+ "    ds.DESC_COMUNE_ESTERO,\r\n"
						+ "    ds.ID_PERSONA_FISICA,\r\n"
						+ "    res.ID_INDIRIZZO,\r\n"
						+ "    res.DESC_INDIRIZZO,\r\n"
						+ "    res.id_com_res,\r\n"
						+ "    res.id_prov_res,\r\n"
						+ "    res.id_reg_res,\r\n"
						+ "    res.id_naz_res,\r\n"
						+ "    res.com_res,\r\n"
						+ "    res.prov_res,\r\n"
						+ "    res.reg_res,\r\n"
						+ "    res.naz_res,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    pts.ndg,\r\n"
						+ "    prsp.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "    prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "    prsc.QUOTA_PARTECIPAZIONE,\r\n"
						+ "    prsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "    pdtsc.DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "    prsc.PROGR_SOGGETTI_CORRELATI,\r\n"
						+ "    prsc.ID_SOGGETTO_ENTE_GIURIDICO,    ptdo.ID_DOCUMENTO,\r\n"
						+ "    ptdo.NUMERO_DOCUMENTO,\r\n"
						+ "    ptdo.DT_RILASCIO_DOCUMENTO,\r\n"
						+ "    ptdo.DT_SCADENZA_DOCUMENTO,\r\n"
						+ "    pdtd.DESC_TIPO_DOCUMENTO,\r\n"
						+ "    pdtd.ID_TIPO_DOCUMENTO, ptdo.DOCUMENTO_RILASCIATO_DA  \r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc ON prsp.PROGR_SOGGETTO_PROGETTO = prspsc.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prspsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PRSp.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "    LEFT JOIN dati_soggetto ds ON ds.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\r\n"
						+ "    LEFT JOIN residenza res ON res.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "     LEFT JOIN PBANDI_T_DOCUMENTO ptdo ON ptdo.ID_DOCUMENTO = prsp.ID_DOCUMENTO_PERSONA_FISICA\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_DOCUMENTO pdtd ON ptdo.ID_TIPO_DOCUMENTO = pdtd.ID_TIPO_DOCUMENTO\r\n"
						+ "WHERE\r\n"
						+ "    prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "    AND pdtsc.FLAG_INDIPENDENTE = 'N'\r\n"
						+ "    AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    AND prsc.ID_TIPO_SOGGETTO_CORRELATO NOT IN (2, 18, 19, 24, 25)\r\n"
						+ "    AND prsp.ID_ENTE_GIURIDICO IS NULL\r\n"
						+ "    AND prsp.ID_PROGETTO ="+idProgetto+"\r\n"
						+ "    AND PRSP.ID_SOGGETTO ="+idSoggetto+"\r\n"
						+ "	   AND PRSC.PROGR_SOGGETTI_CORRELATI="+idSoggCorr+"\r\n"
						+ "    AND rownum <2";
				
			} else {
				query = "WITH dati_soggetto AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsd.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "        PTPF.ID_SOGGETTO,\r\n"
						+ "        ptpf.DT_NASCITA,\r\n"
						+ "        ptpf.COGNOME,\r\n"
						+ "        ptpf.NOME,\r\n"
						+ "        pdc.DESC_COMUNE AS comune_nasc,\r\n"
						+ "        pdp.DESC_PROVINCIA AS prov_nasc,\r\n"
						+ "        pdr.DESC_REGIONE AS regione_nasc,\r\n"
						+ "        pdn.DESC_NAZIONE AS nazione_nasc,\r\n"
						+ "        pdc.ID_COMUNE,\r\n"
						+ "        pdc.CAP AS capPF,\r\n"
						+ "        pdp.ID_PROVINCIA AS id_prov,\r\n"
						+ "        pdr.ID_REGIONE AS id_reg,\r\n"
						+ "        pdn.ID_NAZIONE,\r\n"
						+ "        PTPF.ID_PERSONA_FISICA,\r\n"
						+ "        pdce.ID_COMUNE_ESTERO,\r\n"
						+ "        pdce.DESC_COMUNE_ESTERO,\r\n"
						+ "        pdn2.DESC_NAZIONE AS nazione_est,\r\n"
						+ "        pdn2.ID_NAZIONE AS id_nazione_est\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "        LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_PERSONA_FISICA = prsd.ID_PERSONA_FISICA\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE pdc ON ptpf.ID_COMUNE_ITALIANO_NASCITA = pdc.ID_COMUNE\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE_ESTERO pdce ON ptpf.ID_COMUNE_ESTERO_NASCITA = pdce.ID_COMUNE_ESTERO\r\n"
						+ "        LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdc.ID_PROVINCIA = pdp.ID_PROVINCIA\r\n"
						+ "        LEFT JOIN PBANDI_D_REGIONE pdr ON pdp.ID_REGIONE = pdr.ID_REGIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn ON PTPF.ID_NAZIONE_NASCITA = pdn.ID_NAZIONE\r\n"
						+ "        AND pdce.ID_NAZIONE = pdn.ID_NAZIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn2 ON pdce.ID_NAZIONE = pdn2.ID_NAZIONE\r\n"
						+ "    WHERE\r\n"
						+ "        prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "),\r\n"
						+ "residenza AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsd.ID_SOGGETTO,\r\n"
						+ "        prsd.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "        pti.ID_INDIRIZZO,\r\n"
						+ "        pti.DESC_INDIRIZZO,\r\n"
						+ "        pti.cap AS CAPPF,\r\n"
						+ "        pdc.ID_COMUNE AS id_com_res,\r\n"
						+ "        pdc.DESC_COMUNE AS com_res,\r\n"
						+ "        pdp.ID_PROVINCIA AS id_prov_res,\r\n"
						+ "        pdp.SIGLA_PROVINCIA AS prov_res,\r\n"
						+ "        pdr.ID_REGIONE AS id_reg_res,\r\n"
						+ "        pdr.DESC_REGIONE AS reg_res,\r\n"
						+ "        pdn.ID_NAZIONE AS id_naz_res,\r\n"
						+ "        pdn.DESC_NAZIONE AS naz_res\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "        LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO = prsd.ID_INDIRIZZO_PERSONA_FISICA\r\n"
						+ "        LEFT JOIN PBANDI_D_COMUNE pdc ON pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
						+ "        LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
						+ "        LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pdp.ID_REGIONE\r\n"
						+ "        LEFT JOIN PBANDI_D_NAZIONE pdn ON pdn.ID_NAZIONE = pti.ID_NAZIONE\r\n"
						+ "    WHERE\r\n"
						+ "        prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "    ds.id_soggetto,\r\n"
						+ "    ds.cognome,\r\n"
						+ "    ds.nome,\r\n"
						+ "    ds.DT_NASCITA,\r\n"
						+ "    ds.comune_nasc,\r\n"
						+ "    ds.prov_nasc,\r\n"
						+ "    res.capPF,\r\n"
						+ "    ds.regione_nasc,\r\n"
						+ "    ds.nazione_nasc,\r\n"
						+ "    ds.id_comune,\r\n"
						+ "    ds.id_reg,\r\n"
						+ "    ds.id_prov,\r\n"
						+ "    ds.id_nazione,\r\n"
						+ "    ds.id_nazione_est,\r\n"
						+ "    ds.nazione_est,\r\n"
						+ "    ds.ID_COMUNE_ESTERO,\r\n"
						+ "    ds.DESC_COMUNE_ESTERO,\r\n"
						+ "    ds.ID_PERSONA_FISICA,\r\n"
						+ "    res.ID_INDIRIZZO,\r\n"
						+ "    res.DESC_INDIRIZZO,\r\n"
						+ "    res.id_com_res,\r\n"
						+ "    res.id_prov_res,\r\n"
						+ "    res.id_reg_res,\r\n"
						+ "    res.id_naz_res,\r\n"
						+ "    res.com_res,\r\n"
						+ "    res.prov_res,\r\n"
						+ "    res.reg_res,\r\n"
						+ "    res.naz_res,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    pts.ndg,\r\n"
						+ "    prsd.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "    prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
						+ "    prsc.QUOTA_PARTECIPAZIONE,\r\n"
						+ "    prsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "    pdtsc.DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "    prsc.PROGR_SOGGETTI_CORRELATI,\r\n"
						+ "    prsc.ID_SOGGETTO_ENTE_GIURIDICO,\r\n"
						+ "     ptdo.ID_DOCUMENTO,\r\n"
						+ "    ptdo.NUMERO_DOCUMENTO,\r\n"
						+ "    ptdo.DT_RILASCIO_DOCUMENTO,\r\n"
						+ "    ptdo.DT_SCADENZA_DOCUMENTO,\r\n"
						+ "    pdtd.DESC_TIPO_DOCUMENTO,\r\n"
						+ "    pdtd.ID_TIPO_DOCUMENTO, ptdo.DOCUMENTO_RILASCIATO_DA "
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGG_DOM_SOGG_CORREL prsdsc ON PRSD.PROGR_SOGGETTO_DOMANDA = prsdsc.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prsdsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PRSD.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "    LEFT JOIN dati_soggetto ds ON ds.ID_PERSONA_FISICA = prsd.ID_PERSONA_FISICA\r\n"
						+ "    LEFT JOIN residenza res ON res.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    left join PBANDI_R_SOGGETTO_PROGETTO prsp on prsp.PROGR_SOGGETTO_DOMANDA= prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_DOCUMENTO ptdo ON ptdo.ID_DOCUMENTO = prsd.ID_DOCUMENTO_PERSONA_FISICA\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_DOCUMENTO pdtd ON ptdo.ID_TIPO_DOCUMENTO = pdtd.ID_TIPO_DOCUMENTO\r\n"
						+ "WHERE\r\n"
						+ "    prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "    AND pdtsc.FLAG_INDIPENDENTE = 'N'\r\n"
						+ "    AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
						+ "    AND prsc.ID_TIPO_SOGGETTO_CORRELATO NOT IN (2, 18, 19, 24, 25)\r\n"
						+ "    AND prsd.ID_ENTE_GIURIDICO IS NULL\r\n"
						+ "    AND prsd.ID_DOMANDA = "+idDomanda+"\r\n"
						+ "    AND prsd.ID_SOGGETTO ="+idSoggetto+"\r\n"
						+ "	   AND PRSC.PROGR_SOGGETTI_CORRELATI="+idSoggCorr+"\r\n"
						+ "    AND rownum <2"; 
				//+ "    and res.id_domanda ="+ veroIdDomanda; 
			}
			
			
			
	   soggetto = getJdbcTemplate().query(query, new ResultSetExtractor<AnagraficaBeneficiarioPfVO>() {
				
				@Override
				public AnagraficaBeneficiarioPfVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					AnagraficaBeneficiarioPfVO anag = new AnagraficaBeneficiarioPfVO();
					
					while(rs.next()) {
						
						anag.setProgSoggDomanda(rs.getBigDecimal("PROGR_SOGGETTO_DOMANDA"));
						anag.setProgSoggProgetto(rs.getBigDecimal("PROGR_SOGGETTO_PROGETTO"));
						anag.setIdSoggettoEnteGiuridico(rs.getBigDecimal("ID_SOGGETTO_ENTE_GIURIDICO"));
						anag.setCapPF(rs.getString("capPF"));
						anag.setCognome(rs.getString("cognome"));
						anag.setNome(rs.getString("nome"));
						anag.setDataDiNascita(rs.getDate("DT_NASCITA"));
						anag.setComuneDiNascita(rs.getString("comune_nasc"));
						anag.setProvinciaDiNascita(rs.getString("prov_nasc"));
						anag.setRegioneDiNascita(rs.getString("regione_nasc"));
						anag.setNazioneDiNascita(rs.getString("nazione_nasc"));
						anag.setIdComuneDiNascita(rs.getLong("id_comune"));
						anag.setIdRegioneDiNascita(rs.getLong("id_reg"));
						anag.setIdProvinciaDiNascita(rs.getLong("id_prov"));
						anag.setIdNazioneDiNascita(rs.getLong("id_nazione"));
						anag.setIdRuoloPF(rs.getString("ID_TIPO_SOGGETTO_CORRELATO"));
						anag.setRuolo(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
						anag.setIdPersonaFisica(rs.getLong("ID_PERSONA_FISICA"));
						anag.setQuotaPartecipazione(rs.getBigDecimal("QUOTA_PARTECIPAZIONE"));
						anag.setIdIndirizzo(rs.getString("ID_INDIRIZZO"));
						anag.setIndirizzoPF(rs.getString("DESC_INDIRIZZO"));
						anag.setIdComunePF(rs.getLong("id_com_res"));
						anag.setIdProvinciaPF(rs.getLong("id_prov_res"));
						anag.setIdRegionePF(rs.getLong("id_reg_res"));
						anag.setIdNazionePF(rs.getLong("id_naz_res"));
						anag.setComunePF(rs.getString("com_res"));
						anag.setProvinciaPF(rs.getString("prov_res"));
						anag.setRegionePF(rs.getString("reg_res"));
						anag.setNazionePF(rs.getString("naz_res"));
						anag.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
						anag.setIdTipoSoggCorr(rs.getLong("ID_TIPO_SOGGETTO_CORRELATO"));// questo è il ruolo
						anag.setProgrSoggCorr(rs.getBigDecimal("PROGR_SOGGETTI_CORRELATI"));
						anag.setIdComuneEsteraNascita(rs.getLong("ID_COMUNE_ESTERO"));
						anag.setComuneNascitaEstero(rs.getString("DESC_COMUNE_ESTERO")); 
						anag.setIdNazioneEsteraNascita(rs.getLong("id_nazione_est"));
						anag.setNazioneNascitaEstera(rs.getString("nazione_est")); 
						anag.setNdg(rs.getString("ndg"));
						anag.setDocumentoIdentita(rs.getString("DESC_TIPO_DOCUMENTO"));
						anag.setIdTipoDocumentoIdentita(rs.getLong("id_tipo_documento"));
						anag.setNumeroDocumento(rs.getString("NUMERO_DOCUMENTO"));
						anag.setDataRilascio(rs.getDate("DT_RILASCIO_DOCUMENTO"));
						anag.setEnteRilascio(rs.getString("DOCUMENTO_RILASCIATO_DA"));
						anag.setScadenzaDocumento(rs.getDate("DT_SCADENZA_DOCUMENTO"));
						anag.setIdDocumento(rs.getLong("ID_DOCUMENTO"));
					}
			return anag; 
				}
	   	});
	   
	   if(soggetto.getIdNazioneEsteraNascita()!=0) {
		   soggetto.setIdNazioneDiNascita(soggetto.getIdNazioneEsteraNascita());
		   soggetto.setNazioneDiNascita(soggetto.getNazioneNascitaEstera());
		   soggetto.setComuneDiNascita(soggetto.getComuneNascitaEstero());
		   soggetto.setIdComuneDiNascita(soggetto.getIdComuneEsteraNascita());
	   }
	   if(soggetto.getRegioneDiNascita()!=null) {
		   soggetto.setNazioneDiNascita("ITALIA");
		   soggetto.setIdNazioneDiNascita((long) 118);
	   }
	   if(soggetto.getRegionePF()!=null) {
		   soggetto.setIdNazionePF((long) 118);
	   }
	   
	   
								
		} catch (Exception e) {
			LOG.error("Exception while trying to read PBANDI_T_PERSONA_FISICA", e);
		}
		
		return soggetto;
	}

	@Override
	public AnagraficaBeneficiarioVO getAnagSoggCorrDipDaDomEG(Long idSoggetto,String idDomanda, BigDecimal idSoggCorr) {
		String prf = "[DatiDomandaDAOImpl::getAnagSoggCorrDipDaDomEG]";
		LOG.info(prf + " BEGIN");
		
		AnagraficaBeneficiarioVO enteGiu = new AnagraficaBeneficiarioVO(); 
		
		try {
			String getVeroIddomanda ="select ptd.numero_domanda from pbandi_t_domanda ptd\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON ptd.id_domanda = prsd.ID_DOMANDA \r\n"
					+ "    where PRSD.id_domanda="+idDomanda+"\r\n"
//					+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
					+ " 	AND ROWNUM=1";
			String query= null;
			String veroIdDomanda = getJdbcTemplate().queryForObject(getVeroIddomanda, String.class); 
			BigDecimal idProgetto =checkProgetto(veroIdDomanda, idSoggetto);
			
			List<AnagraficaBeneficiarioVO> listaSogetti = new ArrayList<AnagraficaBeneficiarioVO>(); 
			if(idProgetto!=null) {
				query="WITH dati_anagrafici AS (\r\n"
						+ "	SELECT\r\n"
						+ "		prsc.ID_SOGGETTO,\r\n"
						+ "		pdtsc.DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "		prsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "		pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
						+ "		pteg.FLAG_PUBBLICO_PRIVATO,\r\n"
						+ "		PTEG.COD_UNI_IPA,\r\n"
						+ "		pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "		pts.ndg,\r\n"
						+ "		pdfg.DESC_FORMA_GIURIDICA,\r\n"
						+ "		pteg.ID_FORMA_GIURIDICA,\r\n"
						+ "		PTEG.DT_COSTITUZIONE,\r\n"
						+ "		pteg.DT_INIZIO_ATTIVITA_ESITO,\r\n"
						+ "		pteg.PERIODO_SCADENZA_ESERCIZIO,\r\n"
						+ "		PTEG.DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n"
						+ "		pteg.FLAG_RATING_LEGALITA,\r\n"
						+ "		pteg.ID_ENTE_GIURIDICO,\r\n"
						+ "		prsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "	FROM\r\n"
						+ "		PBANDI_T_ENTE_GIURIDICO pteg\r\n"
						+ "		LEFT JOIN PBANDI_D_FORMA_GIURIDICA pdfg ON PTEG.ID_FORMA_GIURIDICA = pdfg.ID_FORMA_GIURIDICA\r\n"
						+ "		LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PTEG.ID_SOGGETTO\r\n"
						+ "		LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.ID_SOGGETTO = pteg.ID_SOGGETTO\r\n"
						+ "		LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "),\r\n"
						+ "indirizzo AS (\r\n"
						+ "	SELECT\r\n"
						+ "		prsps.PROGR_SOGGETTO_PROGETTO ,\r\n"
						+ "		pti.DESC_INDIRIZZO,\r\n"
						+ "		pti.id_indirizzo,\r\n"
						+ "		pti.id_comune,\r\n"
						+ "		pti.id_provincia,\r\n"
						+ "		pti.id_regione,\r\n"
						+ "		pti.id_nazione,\r\n"
						+ "		pti.CAP,\r\n"
						+ "		pts.id_sede,\r\n"
						+ "		pdc.DESC_COMUNE,\r\n"
						+ "		pdp.DESC_PROVINCIA,\r\n"
						+ "		pdr.DESC_REGIONE,\r\n"
						+ "		pdn.DESC_NAZIONE,\r\n"
						+ "		pts.ID_ATTIVITA_ATECO,\r\n"
						+ "		pdaa.COD_ATECO,\r\n"
						+ "		pdaa.DESC_ATECO,\r\n"
						+ "		pts.PARTITA_IVA\r\n"
						+ "	FROM\r\n"
						+ "		PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "		LEFT JOIN  PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsp.PROGR_SOGGETTO_PROGETTO  = prsps.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "		LEFT JOIN PBANDI_T_SEDE pts ON pts.ID_SEDE = PRSpS.ID_SEDE\r\n"
						+ "		LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO = PRSpS.ID_INDIRIZZO\r\n"
						+ "		LEFT JOIN PBANDI_D_COMUNE pdc ON pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
						+ "		LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
						+ "		LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pti.ID_REGIONE\r\n"
						+ "		LEFT JOIN PBANDI_D_NAZIONE pdn ON pti.ID_NAZIONE = pdn.ID_NAZIONE\r\n"
						+ "		LEFT JOIN PBANDI_D_ATTIVITA_ATECO pdaa ON pts.ID_ATTIVITA_ATECO = pdaa.ID_ATTIVITA_ATECO\r\n"
						+ "	WHERE\r\n"
						+ "		PRSpS.ID_TIPO_SEDE = 1\r\n"
						+ "		AND prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "),\r\n"
						+ "dati_iscrizione AS (\r\n"
						+ "	SELECT\r\n"
						+ "		pti.NUM_ISCRIZIONE,\r\n"
						+ "		pti.DT_ISCRIZIONE,\r\n"
						+ "		pdp.DESC_PROVINCIA AS provincia_iscriz,\r\n"
						+ "		prsp.ID_SOGGETTO,\r\n"
						+ "		pdp.id_provincia as id_prov_iscriz,\r\n"
						+ "		prsp.PROGR_SOGGETTO_PROGETTO ,\r\n"
						+ "		pti.ID_ISCRIZIONE\r\n"
						+ "	FROM\r\n"
						+ "		PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "		LEFT JOIN PBANDI_T_ISCRIZIONE pti ON prsp.ID_ISCRIZIONE_PERSONA_GIURID = pti.ID_ISCRIZIONE\r\n"
						+ "		LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "	DISTINCT prsd.ID_SOGGETTO,\r\n"
						+ "	pdts.DESC_TIPO_SOGGETTO,\r\n"
						+ "	da.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
						+ "	da.FLAG_PUBBLICO_PRIVATO,\r\n"
						+ "	da.ID_FORMA_GIURIDICA,\r\n"
						+ "	da.COD_UNI_IPA,\r\n"
						+ "	da.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "	da.ndg,\r\n"
						+ "	da.DT_COSTITUZIONE,\r\n"
						+ "	da.DESC_FORMA_GIURIDICA,\r\n"
						+ "	pdtsc.DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "	pdtsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "	ind.DESC_INDIRIZZO,\r\n"
						+ "	ind.CAP,\r\n"
						+ "	ind.DESC_COMUNE,\r\n"
						+ "	ind.DESC_PROVINCIA,\r\n"
						+ "	ind.DESC_REGIONE,\r\n"
						+ "	ind.DESC_NAZIONE,\r\n"
						+ "	ind.COD_ATECO,\r\n"
						+ "	ind.DESC_ATECO,\r\n"
						+ "	ind.ID_ATTIVITA_ATECO,\r\n"
						+ "	ind.id_indirizzo,\r\n"
						+ "	ind.id_comune,\r\n"
						+ "	ind.id_regione,\r\n"
						+ "	ind.id_provincia,\r\n"
						+ "	ind.id_nazione,\r\n"
						+ "	ind.id_sede,\r\n"
						+ "	da.DT_INIZIO_ATTIVITA_ESITO,\r\n"
						+ "	da.PERIODO_SCADENZA_ESERCIZIO,\r\n"
						+ "	da.DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n"
						+ "	da.FLAG_RATING_LEGALITA,\r\n"
						+ "	di.NUM_ISCRIZIONE,\r\n"
						+ "	di.DT_ISCRIZIONE,\r\n"
						+ "	di.ID_ISCRIZIONE,\r\n"
						+ "	di.provincia_iscriz,\r\n"
						+ "	di.id_prov_iscriz,\r\n"
						+ "	pdss.desc_sezione_speciale,\r\n"
						+ "	ind.PARTITA_IVA,\r\n"
						+ "	prsd.ID_ENTE_GIURIDICO,\r\n"
						+ "	prsd.PROGR_SOGGETTO_DOMANDA, prsc.PROGR_SOGGETTI_CORRELATI , prsp.PROGR_SOGGETTO_PROGETTO, \r\n"
						+ " ptr.pec, ptr.id_recapiti,\r\n"
						+ " pdss.ID_SEZIONE_SPECIALE,\r\n"
						+ " ptegs.ID_ENTE_SEZIONE, prsc.QUOTA_PARTECIPAZIONE \r\n"
						+ "FROM\r\n"
						+ "	PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "	LEFT JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc ON prsp.PROGR_SOGGETTO_PROGETTO = prspsc.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "	LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prspsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "	LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "	LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PRSp.ID_SOGGETTO\r\n"
						+ "	LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "	LEFT JOIN indirizzo ind ON prsp.PROGR_SOGGETTO_PROGETTO  = ind.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "	LEFT JOIN dati_iscrizione di ON di.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "	LEFT JOIN PBANDI_T_ENTE_GIUR_SEZI ptegs ON ptegs.ID_SOGGETTO = prsp.id_soggetto\r\n"
						+ "	LEFT JOIN PBANDI_D_SEZIONE_SPECIALE pdss ON ptegs.ID_SEZIONE_SPECIALE = pdss.ID_SEZIONE_SPECIALE\r\n"
						+ "	LEFT JOIN dati_anagrafici da ON da.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
						+ "	left join PBANDI_R_SOGGETTO_DOMANDA prsD on prsp.PROGR_SOGGETTO_DOMANDA= prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ " LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsp.PROGR_SOGGETTO_PROGETTO = prsps.PROGR_SOGGETTO_PROGETTO\r\n"
						+ "	LEFT JOIN pbandi_t_recapiti ptr ON ptr.ID_RECAPITI  = prsps.ID_RECAPITI\r\n "
						+ " LEFT JOIN PBANDI_T_ENTE_GIUR_SEZI ptegs ON prsp.ID_SOGGETTO = ptegs.ID_SOGGETTO\r\n"
						+ " LEFT JOIN PBANDI_D_SEZIONE_SPECIALE pdss ON pdss.ID_SEZIONE_SPECIALE = ptegs.ID_SEZIONE_SPECIALE\r\n"
						+ "WHERE\r\n"
						+ "	prsp.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "	AND pdtsc.FLAG_INDIPENDENTE ='N'\r\n"
						+ "	AND prsc.ID_TIPO_SOGGETTO_CORRELATO NOT IN (2, 18, 19, 24, 25)\r\n"
						+ "	AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
						+ "	AND prsp.ID_PERSONA_FISICA IS NULL\r\n"
						+ "	AND prsp.ID_PROGETTO  ="+idProgetto+"\r\n"
						+ "	and prsc.PROGR_SOGGETTI_CORRELATI= "+idSoggCorr+"\r\n"
						+ "	and prsp.ID_SOGGETTO="+ idSoggetto;
			} else {		
				query = "WITH dati_anagrafici AS (\r\n"
						+ "	SELECT\r\n"
						+ "		prsc.ID_SOGGETTO,\r\n"
						+ "		pdtsc.DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "		prsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "		pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
						+ "		pteg.FLAG_PUBBLICO_PRIVATO,\r\n"
						+ "		PTEG.COD_UNI_IPA,\r\n"
						+ "		pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "		pts.ndg,\r\n"
						+ "		pdfg.DESC_FORMA_GIURIDICA,\r\n"
						+ "		pteg.ID_FORMA_GIURIDICA,\r\n"
						+ "		PTEG.DT_COSTITUZIONE,\r\n"
						+ "		pteg.DT_INIZIO_ATTIVITA_ESITO,\r\n"
						+ "		pteg.PERIODO_SCADENZA_ESERCIZIO,\r\n"
						+ "		PTEG.DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n"
						+ "		pteg.FLAG_RATING_LEGALITA,\r\n"
						+ "		pteg.ID_ENTE_GIURIDICO,\r\n"
						+ "		prsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "	FROM\r\n"
						+ "		PBANDI_T_ENTE_GIURIDICO pteg\r\n"
						+ "		LEFT JOIN PBANDI_D_FORMA_GIURIDICA pdfg ON PTEG.ID_FORMA_GIURIDICA = pdfg.ID_FORMA_GIURIDICA\r\n"
						+ "		LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PTEG.ID_SOGGETTO\r\n"
						+ "		LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.ID_SOGGETTO = pteg.ID_SOGGETTO\r\n"
						+ "		LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "),\r\n"
						+ "indirizzo AS (\r\n"
						+ "	SELECT\r\n"
						+ "		prsds.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "		pti.DESC_INDIRIZZO,\r\n"
						+ "		pti.id_indirizzo,\r\n"
						+ "		pti.id_comune,\r\n"
						+ "		pti.id_provincia,\r\n"
						+ "		pti.id_regione,\r\n"
						+ "		pti.id_nazione,\r\n"
						+ "		pti.CAP,\r\n"
						+ "		pts.id_sede,\r\n"
						+ "		pdc.DESC_COMUNE,\r\n"
						+ "		pdp.DESC_PROVINCIA,\r\n"
						+ "		pdr.DESC_REGIONE,\r\n"
						+ "		pdn.DESC_NAZIONE,\r\n"
						+ "		pts.ID_ATTIVITA_ATECO,\r\n"
						+ "		pdaa.COD_ATECO,\r\n"
						+ "		pdaa.DESC_ATECO,\r\n"
						+ "		pts.PARTITA_IVA\r\n"
						+ "	FROM\r\n"
						+ "		PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds\r\n"
						+ "		LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "		LEFT JOIN PBANDI_T_SEDE pts ON pts.ID_SEDE = PRSDS.ID_SEDE\r\n"
						+ "		LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO = PRSDS.ID_INDIRIZZO\r\n"
						+ "		LEFT JOIN PBANDI_D_COMUNE pdc ON pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
						+ "		LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
						+ "		LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pti.ID_REGIONE\r\n"
						+ "		LEFT JOIN PBANDI_D_NAZIONE pdn ON pti.ID_NAZIONE = pdn.ID_NAZIONE\r\n"
						+ "		LEFT JOIN PBANDI_D_ATTIVITA_ATECO pdaa ON pts.ID_ATTIVITA_ATECO = pdaa.ID_ATTIVITA_ATECO\r\n"
						+ "	WHERE\r\n"
						+ "		PRSDS.ID_TIPO_SEDE = 1\r\n"
						+ "		AND prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "),\r\n"
						+ "dati_iscrizione AS (\r\n"
						+ "	SELECT\r\n"
						+ "		pti.NUM_ISCRIZIONE,\r\n"
						+ "		pti.DT_ISCRIZIONE,\r\n"
						+ "		pdp.DESC_PROVINCIA AS provincia_iscriz,\r\n"
						+ "		prsd.ID_SOGGETTO,\r\n"
						+ "		pdp.id_provincia as id_prov_iscriz,\r\n"
						+ "		prsd.PROGR_SOGGETTO_DOMANDA,\r\n"
						+ "		pti.ID_ISCRIZIONE\r\n"
						+ "	FROM\r\n"
						+ "		PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "		LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON PRSD.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
						+ "		LEFT JOIN PBANDI_T_ISCRIZIONE pti ON prsd.ID_ISCRIZIONE_PERSONA_GIURID = pti.ID_ISCRIZIONE\r\n"
						+ "		LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.ID_PROVINCIA = pti.ID_PROVINCIA\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "	DISTINCT prsd.ID_SOGGETTO,\r\n"
						+ "	pdts.DESC_TIPO_SOGGETTO,\r\n"
						+ "	da.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
						+ "	da.FLAG_PUBBLICO_PRIVATO,\r\n"
						+ "	da.ID_FORMA_GIURIDICA,\r\n"
						+ "	da.COD_UNI_IPA,\r\n"
						+ "	da.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "	da.ndg,\r\n"
						+ "	da.DT_COSTITUZIONE,\r\n"
						+ "	da.DESC_FORMA_GIURIDICA,\r\n"
						+ "	pdtsc.DESC_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "	pdtsc.ID_TIPO_SOGGETTO_CORRELATO,\r\n"
						+ "	ind.DESC_INDIRIZZO,\r\n"
						+ "	ind.CAP,\r\n"
						+ "	ind.DESC_COMUNE,\r\n"
						+ "	ind.DESC_PROVINCIA,\r\n"
						+ "	ind.DESC_REGIONE,\r\n"
						+ "	ind.DESC_NAZIONE,\r\n"
						+ "	ind.COD_ATECO,\r\n"
						+ "	ind.DESC_ATECO,\r\n"
						+ "	ind.ID_ATTIVITA_ATECO,\r\n"
						+ "	ind.id_indirizzo,\r\n"
						+ "	ind.id_comune,\r\n"
						+ "	ind.id_regione,\r\n"
						+ "	ind.id_provincia,\r\n"
						+ "	ind.id_nazione,\r\n"
						+ "	ind.id_sede,\r\n"
						+ "	da.DT_INIZIO_ATTIVITA_ESITO,\r\n"
						+ "	da.PERIODO_SCADENZA_ESERCIZIO,\r\n"
						+ "	da.DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n"
						+ "	da.FLAG_RATING_LEGALITA,\r\n"
						+ "	di.NUM_ISCRIZIONE,\r\n"
						+ "	di.DT_ISCRIZIONE,\r\n"
						+ "	di.ID_ISCRIZIONE,\r\n"
						+ "	di.provincia_iscriz,\r\n"
						+ "	di.id_prov_iscriz,\r\n"
						+ "	pdss.desc_sezione_speciale,\r\n"
						+ "	ind.PARTITA_IVA,\r\n"
						+ "	prsd.ID_ENTE_GIURIDICO,\r\n"
						+ "	prsd.PROGR_SOGGETTO_DOMANDA, prsc.PROGR_SOGGETTI_CORRELATI , prsp.PROGR_SOGGETTO_PROGETTO , \r\n"
						+ " ptr.pec, ptr.id_recapiti,\r\n"
						+ " pdss.ID_SEZIONE_SPECIALE,\r\n"
						+ " ptegs.ID_ENTE_SEZIONE, prsc.QUOTA_PARTECIPAZIONE \r\n"
						+ "FROM\r\n"
						+ "	PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "	LEFT JOIN PBANDI_R_SOGG_DOM_SOGG_CORREL prsdsc ON PRSD.PROGR_SOGGETTO_DOMANDA = prsdsc.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "	LEFT JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prsdsc.PROGR_SOGGETTI_CORRELATI\r\n"
						+ "	LEFT JOIN PBANDI_D_TIPO_SOGG_CORRELATO pdtsc ON pdtsc.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO\r\n"
						+ "	LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PRSD.ID_SOGGETTO\r\n"
						+ "	LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "	LEFT JOIN indirizzo ind ON prsd.PROGR_SOGGETTO_DOMANDA = ind.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "	LEFT JOIN dati_iscrizione di ON di.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "	LEFT JOIN PBANDI_T_ENTE_GIUR_SEZI ptegs ON ptegs.ID_SOGGETTO = prsd.id_soggetto\r\n"
						+ "	LEFT JOIN PBANDI_D_SEZIONE_SPECIALE pdss ON ptegs.ID_SEZIONE_SPECIALE = pdss.ID_SEZIONE_SPECIALE\r\n"
						+ "	LEFT JOIN dati_anagrafici da ON da.ID_ENTE_GIURIDICO = prsd.ID_ENTE_GIURIDICO\r\n"
						+ "	left join PBANDI_R_SOGGETTO_PROGETTO prsp on prsp.PROGR_SOGGETTO_DOMANDA= prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ " LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "	LEFT JOIN pbandi_t_recapiti ptr ON ptr.ID_RECAPITI  = prsds.ID_RECAPITI\r\n "
						+ " LEFT JOIN PBANDI_T_ENTE_GIUR_SEZI ptegs ON prsd.ID_SOGGETTO = ptegs.ID_SOGGETTO\r\n"
						+ " LEFT JOIN PBANDI_D_SEZIONE_SPECIALE pdss ON pdss.ID_SEZIONE_SPECIALE = ptegs.ID_SEZIONE_SPECIALE\r\n"
						+ "WHERE\r\n"
						+ "	prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
						+ "	AND pdtsc.FLAG_INDIPENDENTE ='N'\r\n"
						+ "	AND prsc.ID_TIPO_SOGGETTO_CORRELATO NOT IN (2, 18, 19, 24, 25)\r\n"
						+ "	AND prsc.DT_FINE_VALIDITA IS NULL\r\n"
						+ "	AND prsd.ID_PERSONA_FISICA IS NULL\r\n"
						+ "	AND prsd.ID_DOMANDA = "+idDomanda+"\r\n"
						+ "	and prsc.PROGR_SOGGETTI_CORRELATI="+idSoggCorr+"\r\n"
						+ "	and prsd.ID_SOGGETTO="+ idSoggetto;
			}
			
			
			
			
			
			RowMapper<AnagraficaBeneficiarioVO> lista= new RowMapper<AnagraficaBeneficiarioVO>() {
				@Override
				public AnagraficaBeneficiarioVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AnagraficaBeneficiarioVO anag =  new AnagraficaBeneficiarioVO(); 
					anag.setQuotaPartecipazione(rs.getString("QUOTA_PARTECIPAZIONE"));
					anag.setRagSoc(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
					anag.setDescFormaGiur(rs.getString("DESC_FORMA_GIURIDICA"));
					anag.setIdFormaGiuridica(rs.getLong("ID_FORMA_GIURIDICA"));
					anag.setFlagPubblicoPrivato(rs.getLong("FLAG_PUBBLICO_PRIVATO"));
					anag.setCodUniIpa(rs.getString("COD_UNI_IPA"));
					anag.setRuolo(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
					anag.setCfSoggetto(rs.getString("CODICE_FISCALE_SOGGETTO"));
					anag.setDtCostituzione(rs.getDate("dt_costituzione"));
					anag.setDescIndirizzo(rs.getString("desc_indirizzo"));
					anag.setpIva(rs.getString("PARTITA_IVA"));
					anag.setDescComune(rs.getString("DESC_COMUNE"));
					anag.setDescProvincia(rs.getString("DESC_PROVINCIA"));
					anag.setCap(rs.getString("CAP"));
					anag.setDescRegione(rs.getString("DESC_REGIONE"));
					anag.setDescNazione(rs.getString("DESC_NAZIONE"));
					anag.setCodAteco(rs.getString("COD_ATECO"));
					anag.setDescAteco(rs.getString("desc_ateco"));
					anag.setFlagRatingLeg(rs.getString("FLAG_RATING_LEGALITA"));
					//anag.setDescStatoAttivita(rs.getString("desc_stato_attivita"));
					anag.setDtInizioAttEsito(rs.getDate("DT_INIZIO_ATTIVITA_ESITO"));
					anag.setDtUltimoEseChiuso(rs.getDate("dt_ultimo_esercizio_chiuso")); 
					anag.setPeriodoScadEse(rs.getString("periodo_scadenza_esercizio"));// periodo chiusura esercizio 
					anag.setNumIscrizione(rs.getString("NUM_ISCRIZIONE"));
					anag.setDtIscrizione(rs.getDate("dt_iscrizione"));
					anag.setDescProvinciaIscriz (rs.getString("provincia_iscriz")); 
					anag.setDescSezioneSpeciale(rs.getString("desc_sezione_speciale"));
					anag.setIdIndirizzo(rs.getLong("id_indirizzo"));
					anag.setIdComune(rs.getLong("id_comune"));
					anag.setIdRegione(rs.getLong("id_regione"));
					anag.setIdProvincia(rs.getLong("id_provincia"));
					anag.setIdNazione(rs.getLong("id_nazione"));
					anag.setIdSezioneSpeciale(rs.getLong("ID_SEZIONE_SPECIALE"));
					//anag.setDescStatoDomanda(rs.getString("desc_stato_domanda")); 
					anag.setProgSoggDomanda(rs.getString("PROGR_SOGGETTO_DOMANDA"));
					anag.setIdEnteGiuridico(rs.getLong("ID_ENTE_GIURIDICO"));
					anag.setDescTipoSoggCorr(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
					anag.setIdTipoSoggCorr(rs.getLong("ID_TIPO_SOGGETTO_CORRELATO"));
					anag.setIdDomanda(idDomanda);
					anag.setIdIscrizione(rs.getLong("ID_ISCRIZIONE"));
					anag.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
					anag.setIdAttAteco(rs.getLong("ID_ATTIVITA_ATECO"));
					anag.setIdSede(rs.getLong("id_sede"));
					anag.setIdProvinciaIscrizione(rs.getLong("id_prov_iscriz"));
					anag.setNdg(rs.getString("ndg"));
					anag.setDescPec(rs.getString("pec"));
					anag.setIdRecapiti(rs.getLong("id_recapiti"));
					anag.setIdSezioneSpeciale(rs.getLong("ID_SEZIONE_SPECIALE"));
					anag.setIdEnteSezione(rs.getLong("id_ente_sezione"));
					return anag;
				}		
			};
			
			listaSogetti = getJdbcTemplate().query(query, lista); 
			enteGiu = listaSogetti.get(0);
			
//			enteGiu = getJdbcTemplate().query(query, new ResultSetExtractor<AnagraficaBeneficiarioVO>() {
//			@Override
//			public AnagraficaBeneficiarioVO extractData(ResultSet rs) throws SQLException, DataAccessException {
//				// TODO Auto-generated method stub
//				AnagraficaBeneficiarioVO anag = new AnagraficaBeneficiarioVO();
//				
//				while(rs.next()) {		
//					anag.setRagSoc(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
//					anag.setDescFormaGiur(rs.getString("DESC_FORMA_GIURIDICA"));
//					anag.setFlagPubblicoPrivato(rs.getLong("FLAG_PUBBLICO_PRIVATO"));
//					anag.setCodUniIpa(rs.getString("COD_UNI_IPA"));
//					anag.setRuolo(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
//					anag.setCfSoggetto(rs.getString("CODICE_FISCALE_SOGGETTO"));
//					anag.setDtCostituzione(rs.getDate("dt_costituzione"));
//					anag.setDescIndirizzo(rs.getString("desc_indirizzo"));
//					anag.setpIva(rs.getString("PARTITA_IVA"));
//					anag.setDescComune(rs.getString("DESC_COMUNE"));
//					anag.setDescProvincia(rs.getString("DESC_PROVINCIA"));
//					anag.setCap(rs.getString("CAP"));
//					anag.setDescRegione(rs.getString("DESC_REGIONE"));
//					anag.setDescNazione(rs.getString("DESC_NAZIONE"));
//					anag.setCodAteco(rs.getString("COD_ATECO"));
//					anag.setDescAteco(rs.getString("desc_ateco"));
//					anag.setFlagRatingLeg(rs.getString("FLAG_RATING_LEGALITA"));
//					//anag.setDescStatoAttivita(rs.getString("desc_stato_attivita"));
//					anag.setDtInizioAttEsito(rs.getDate("DT_INIZIO_ATTIVITA_ESITO"));
//					anag.setDtUltimoEseChiuso(rs.getDate("dt_ultimo_esercizio_chiuso")); 
//					anag.setPeriodoScadEse(rs.getString("periodo_scadenza_esercizio"));// periodo chiusura esercizio 
//					anag.setNumIscrizione(rs.getString("NUM_ISCRIZIONE"));
//					anag.setDtIiscrizione(rs.getDate("dt_iscrizione"));
//					anag.setDescProvinciaIscriz (rs.getDate("provincia_iscriz")); 
//					anag.setDescSezioneSpeciale(rs.getString("desc_sezione_speciale"));
//				}
//				
//				return anag;
//			}
//			});			
			
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read SOGGETTO CORRELATO DIPENDENTE DA DOMANDA ENTE GIURIDICA", e);
		}
		
		
		return enteGiu;
	}

	@Override
	public List<AttivitaDTO> listRuoli() {
		String prf = "[DatiDomandaDAOImpl::getAnagSoggCorrDipDaDomEG]";
		LOG.info(prf + " BEGIN");
		
		List<AttivitaDTO> ruoli = new ArrayList<AttivitaDTO>(); 
		try {

			String query = "SELECT PDTSC.ID_TIPO_SOGGETTO_CORRELATO , "
					+ "PDTSC.DESC_TIPO_SOGGETTO_CORRELATO  FROM  PBANDI_D_TIPO_SOGG_CORRELATO pdtsc WHERE FLAG_INDIPENDENTE ='N' ";

			RowMapper<AttivitaDTO> lista = new RowMapper<AttivitaDTO>() {
				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO ruolo = new AttivitaDTO();
					ruolo.setDescAttivita(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
					ruolo.setIdAttivita(rs.getLong("ID_TIPO_SOGGETTO_CORRELATO"));
					return ruolo;
				}
			};

			ruoli = getJdbcTemplate().query(query, lista);

		} catch (Exception e) {

			LOG.error(prf + "Exception while trying to read PBANDI_D_STATO_PROP_VAR_CRE", e);
		} finally {
			LOG.info(prf + " END");
		}	
		return ruoli;
	}

	@Override
	public List<AttivitaDTO> getListaSuggest(String stringa, int id) {
		
		List<AttivitaDTO> lista = new ArrayList<AttivitaDTO>(); 
		 
		if(id==1 || id==5) {
			lista = getComuni(stringa); 
			return lista; 
		}
		if(id==2 || id==6) {
			lista =  getProvincie(stringa);
			return lista; 
		} 
		if(id==3||id==7) {
			lista = getRegioni(stringa); 
			return lista; 
		}
		if(id==4||id==8) {
			lista = getNazioni(stringa);
			return lista; 
		}
		
		return lista;
	}

	private List<AttivitaDTO> getNazioni(String stringa) {
		List<AttivitaDTO> nazioni = new ArrayList<AttivitaDTO>(); 

		try {
			String sql ="SELECT   ID_NAZIONE  as ID_ATTIVITA_MONIT_CRED, DESC_NAZIONE  as DESC_ATT_MONIT_CRED\r\n"
					+ "FROM PBANDI_D_NAZIONE pdn\r\n"
					+ "WHERE UPPER(DESC_NAZIONE) LIKE upper ('%"+stringa+"%') \r\n"
							+ " AND  ROWNUM <= 100"
					+ "ORDER BY DESC_NAZIONE "; 
			
			nazioni = getJdbcTemplate().query(sql, new AttivitaRowMapper()); 
			
		} catch (Exception e) {
		}
		
		return nazioni;
	}

	private List<AttivitaDTO> getRegioni(String stringa) {
		
		List<AttivitaDTO> regioni = new ArrayList<AttivitaDTO>(); 

		
		try {
			String sql ="select id_regione as ID_ATTIVITA_MONIT_CRED, desc_regione as DESC_ATT_MONIT_CRED"
					+ " from pbandi_d_regione "
					+ " where upper (desc_regione) like upper('%"+stringa+"%')  "
							+ " AND  ROWNUM <= 100"
					+ " order by desc_regione";
			
			regioni = getJdbcTemplate().query(sql, new AttivitaRowMapper()); 
			
		} catch (Exception e) {
		}
		return regioni;
	}

	private List<AttivitaDTO> getProvincie(String stringa) {
		List<AttivitaDTO> provincie = new ArrayList<AttivitaDTO>(); 
		
		try {
			String sql = "select pdp.id_provincia as ID_ATTIVITA_MONIT_CRED , pdp.desc_provincia as DESC_ATT_MONIT_CRED"
					+ " from pbandi_d_provincia pdp"
					+ " where upper(desc_provincia) like upper('%"+stringa+"%')"
							+ " AND  ROWNUM <= 100"
					+ " order by pdp.desc_provincia "; 
			
			provincie = getJdbcTemplate().query(sql, new AttivitaRowMapper()); 
		} catch (Exception e) {
		}
		
		return provincie;
	}

	private List<AttivitaDTO> getComuni(String stringa) {
		
		List<AttivitaDTO> comuni = new ArrayList<AttivitaDTO>(); 
		
		try {
			
			String query = "SELECT pdc.ID_COMUNE AS ID_ATTIVITA_MONIT_CRED, pdc.DESC_COMUNE AS DESC_ATT_MONIT_CRED\r\n"
					+ "FROM PBANDI_D_COMUNE pdc \r\n"
					+ "WHERE UPPER(pdc.DESC_COMUNE) LIKE upper ('%"+stringa+"%')\r\n"
					+ "UNION \r\n"
					+ "SELECT pdce.ID_COMUNE_ESTERO AS ID_ATTIVITA_MONIT_CRED, pdce.DESC_COMUNE_ESTERO AS DESC_ATT_MONIT_CRED \r\n"
					+ "FROM PBANDI_D_COMUNE_ESTERO pdce \r\n"
					+ "WHERE UPPER(pdce.DESC_COMUNE_ESTERO) LIKE upper ('%"+stringa+"%')\r\n"
					+ " AND  ROWNUM <= 100"
					+ "ORDER BY DESC_ATT_MONIT_CRED "; 
					
			comuni = getJdbcTemplate().query(query, new AttivitaRowMapper()); 			
			
		} catch (Exception e) {
		}
		return comuni;
	}

	@Override
	public Boolean modificaSoggetto(AnagraficaBeneficiarioPfVO soggetto, String idSoggetto, String idDomanda) {
		String prf = "[DatiDomandaDAOImpl::getAnagSoggCorrDipDaDomEG]";
		LOG.info(prf + " BEGIN");
		
		
		Boolean result = null ; 
		
		try {
			BigDecimal idPersFisica = null;
			boolean isInertPersFisica = false;
			AnagraficaBeneficiarioPfVO anagPFDB = new AnagraficaBeneficiarioPfVO();
			String query = " SELECT\r\n"
					+ "    cognome,\r\n"
					+ "    nome,\r\n"
					+ "    sesso,\r\n"
					+ "    dt_nascita,\r\n"
					+ "    id_cittadinanza,\r\n"
					+ "    id_titolo_studio,\r\n"
					+ "    id_occupazione,\r\n"
					+ "    id_persona_fisica,\r\n"
					+ "    ID_COMUNE_ITALIANO_NASCITA ,\r\n"
					+ "    ID_COMUNE_ESTERO_NASCITA ,\r\n"
					+ "    pdc.ID_PROVINCIA \r\n"
					+ "FROM\r\n"
					+ "    PBANDI_T_PERSONA_FISICA ptpf \r\n"
					+ "    LEFT JOIN PBANDI_D_COMUNE pdc ON pdc.ID_COMUNE = ptpf.ID_COMUNE_ITALIANO_NASCITA \r\n"
					+ "WHERE\r\n"
					+ "     ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(ID_PERSONA_FISICA) AS ID_PERSONA_FISICA\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY\r\n"
					+ "            ID_SOGGETTO\r\n"
					+ "    )\r\n"
					+ "    AND ID_SOGGETTO ="+idSoggetto;
			
			 anagPFDB = getJdbcTemplate().query(query, new ResultSetExtractor<AnagraficaBeneficiarioPfVO>(){

				@Override
				public AnagraficaBeneficiarioPfVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					
					AnagraficaBeneficiarioPfVO anag = new AnagraficaBeneficiarioPfVO(); 
					while(rs.next()) {
						anag.setCognome(rs.getString("cognome"));
						anag.setNome(rs.getString("nome"));
						anag.setSesso(rs.getString("sesso"));
						anag.setIdCittadinanza(rs.getLong("id_cittadinanza"));
						anag.setIdTitoloStudio(rs.getLong("id_titolo_studio"));
						anag.setIdOccupazione(rs.getLong("id_occupazione"));
						anag.setIdPersonaFisica(rs.getLong("id_persona_fisica"));
						anag.setDataDiNascita(rs.getDate("dt_nascita"));
						if(rs.getString("ID_COMUNE_ITALIANO_NASCITA") !=null) {
							anag.setIdComuneDiNascita(rs.getLong("ID_COMUNE_ITALIANO_NASCITA"));
							anag.setIdProvinciaDiNascita(rs.getLong("ID_PROVINCIA"));
						} else {
							anag.setIdComuneEsteraNascita(rs.getLong("ID_COMUNE_ESTERO_NASCITA"));
						}
					}
					return anag;
				}
				
			});
			
		String stato_domanda = "SELECT pdsd.DESC_STATO_DOMANDA \r\n"
				+ "FROM PBANDI_T_DOMANDA ptd \r\n"
				+ "LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.ID_STATO_DOMANDA  = ptd.ID_STATO_DOMANDA \r\n"
				+ "WHERE ptd.ID_DOMANDA  = "+ soggetto.getIdDomanda();
		soggetto.setDescStatoDomanda(getJdbcTemplate().queryForObject(stato_domanda, String.class));
			
			if((soggetto.getIdTipoSoggCorr() ==23|| soggetto.getIdTipoSoggCorr() ==3) 
					&& soggetto.getDescStatoDomanda()!="Presentata") {
							
				// modifca dati anagrafici
				if(soggetto.isDatiAnagrafici()== true) {
					// update codice_fiscale inside table pbandi_t_soggetto
					String updateCFisc = "update PBANDI_T_SOGGETTO set "
							+ " codice_fiscale_soggetto='"+soggetto.getCodiceFiscale()+"'"
							+ ", id_utente_agg="+ soggetto.getIdUtenteAgg()
							+ ",  dt_aggiornamento=sysdate"
							+ " where id_soggetto ="+idSoggetto;
					getJdbcTemplate().update(updateCFisc); 
					
					// update persona_fisica
					// check se esiste quella persona fisica
					if(checkDatiAnagPf(soggetto, anagPFDB) == true) {
						// non faccio nulla
					} else {
						 idPersFisica= insertPersonaFisica(soggetto, anagPFDB, idSoggetto);
						  isInertPersFisica = true; 
					}
				}
				
				// sede legale update 
				if(soggetto.isSedeLegale()==true) { 
					if(checkMatchCompletoIndirizzo(soggetto.getIdComunePF(), soggetto.getIdNazionePF(), soggetto.getIdProvinciaPF(),
							soggetto.getIdRegionePF(),idSoggetto, 1)==true) {
						String updateIndirizzo="updatePBANDI_T_INDIRIZZO  set"
								+ " id_utente_agg="+ soggetto.getIdUtenteAgg()
								+ " where id_indirizzo="+ soggetto.getIdIndirizzo();
						getJdbcTemplate().update(updateIndirizzo); 
			
					} else {
						BigDecimal idIndirizzo=	insertIndirizzoPF(soggetto, idSoggetto); 
						
						//String progDomadaSede= getProgDomandaSede(soggetto.getProgSoggDomanda());
						
						// update il nuovo indirizzo dentro la tabella prsdomanda
						if( isInertPersFisica) {		
						String updateIndirizzo = "update PBANDI_R_SOGGETTO_DOMANDA set "
								+ " ID_INDIRIZZO_PERSONA_FISICA ="+ idIndirizzo
								+ ", ID_UTENTE_AGG=" + soggetto.getIdUtenteAgg()
								+ " where id_persona_fisica=" + idPersFisica;
						getJdbcTemplate().update(updateIndirizzo); 
						} else {
							String updateIndirizzo = "update PBANDI_R_SOGGETTO_DOMANDA set "
									+ " ID_INDIRIZZO_PERSONA_FISICA ="+ idIndirizzo
									+ ", ID_UTENTE_AGG=" + soggetto.getIdUtenteAgg()
									+ " where id_persona_fisica=" + soggetto.getIdPersonaFisica();
							getJdbcTemplate().update(updateIndirizzo); 
						}
//						
//						if(progDomadaSede!=null) {
//							// update dentro la tabella pbandi_r_soggetto_domanda_sede con id_indirizzo
//							String updateIndirizoIntoRsoggSede = "update PBANDI_R_SOGGETTO_DOMANDA_SEDE set "
//									+ " id_indirizzo="+ idIndirizzo
//									+", id_utente_agg="+ soggetto.getIdUtenteAgg()
//									+" where PROGR_SOGGETTO_DOMANDA_SEDE="+ progDomadaSede;
//							getJdbcTemplate().update(updateIndirizoIntoRsoggSede); 
//						} else {
//							// aggiorno
//							String updateIndirizoIntoRsoggSede = "update PBANDI_R_SOGGETTO_DOMANDA_SEDE set "
//									+ " id_indirizzo="+ idIndirizzo
//									+", id_utente_agg="+ soggetto.getIdUtenteAgg()
//									+" where PROGR_SOGGETTO_DOMANDA="+ soggetto.getProgSoggDomanda();
//							getJdbcTemplate().update(updateIndirizoIntoRsoggSede); 
//						}
					}
				}
							
			result  = true; 
			} 
			else {
				result = false; 
			}
		} catch (Exception e) {
			result = false; 
			LOG.info(prf + " Errore modifica tabella PBANDI_T_INDIRIZZO");
			}
		
		return result;
	}
	private boolean checkDatiAnagPf(AnagraficaBeneficiarioPfVO soggetto, AnagraficaBeneficiarioPfVO anagDB) {
		
		if(soggetto.getIdProvinciaDiNascita() != anagDB.getIdProvinciaDiNascita())
			return false;
		if(anagDB.getIdComuneDiNascita()!= soggetto.getIdComuneDiNascita() || 
				anagDB.getIdComuneEsteraNascita()!=soggetto.getIdComuneDiNascita()) {
			return false; 
		} 
		if(soggetto.getNome()!= anagDB.getNome())
			return false; 
		if(soggetto.getCognome() != anagDB.getCognome())
			return false; 	
		if(soggetto.getDataDiNascita() != anagDB.getDataDiNascita())
			return false; 
		
			
		return true;
	}
private BigDecimal insertPersonaFisica(AnagraficaBeneficiarioPfVO soggetto, AnagraficaBeneficiarioPfVO anagDB, String idSoggetto) {
		
		String getId = "SELECT SEQ_PBANDI_T_PERSONA_FISICA.nextval FROM dual";	
		BigDecimal IdPersonaFisica =  getJdbcTemplate().queryForObject(getId, BigDecimal.class);
		
		StringBuilder insert = new StringBuilder();
		insert.append("insert into PBANDI_T_PERSONA_FISICA (\r\n"
				+ "cognome, \r\n"
				+ "nome, \r\n"
				+ "dt_nascita, \r\n"
				+ "	sesso, \r\n"
				+ "id_persona_fisica,\r\n"
				+ "dt_inizio_validita, ");
		if(soggetto.getIdNazioneDiNascita()==118) {
			insert.append(" id_comune_italiano_nascita,\r\n"
					+ "id_nazione_nascita,\r\n"
					+"id_utente_ins ,"); 
		} else {
			insert.append(" id_comune_estero_nascita,\r\n"
					+ " id_nazione_nascita,\r\n"
					+" id_utente_ins,");
		}
		insert.append(" ID_CITTADINANZA, "
				+ " id_titolo_studio, "
				+ " id_occupazione, id_soggetto)values(?,?,?,?,?,sysdate,?,?,?,?,?,?,?)");	
		getJdbcTemplate().update(insert.toString(), new Object[] {
				soggetto.getCognome(), 
				soggetto.getNome(),
				soggetto.getDataDiNascita(), 
				anagDB.getSesso(), 
				IdPersonaFisica,
				soggetto.getIdComuneDiNascita(), 
				soggetto.getIdNazioneDiNascita(), 
				soggetto.getIdUtenteAgg(),
				(anagDB.getIdCittadinanza()>0)?anagDB.getIdCittadinanza():null,
				(anagDB.getIdTitoloStudio()>0)?anagDB.getIdTitoloStudio():null,
				(anagDB.getIdOccupazione()>0)?anagDB.getIdOccupazione():null, 
				idSoggetto,
		});
		
		if(soggetto.getProgSoggDomanda()!=null) {
			String updatePRSdom="update PBANDI_R_SOGGETTO_DOMANDA set "
					+ " id_persona_fisica=" + IdPersonaFisica
					+ ", dt_aggiornamento=sysdate" 
					+ ", id_utente_agg="+soggetto.getIdUtenteAgg()
					+ " where id_soggetto="+ idSoggetto;
			getJdbcTemplate().update(updatePRSdom); 
		}
		return IdPersonaFisica;
	}

private boolean checkMatchCompletoIndirizzo(Long idComune, Long idNazione, Long idProvincia, Long idRegione,
		String idSoggetto, int pf) {
	 
	String select;
	if(pf==1) {
		
	 select ="  	SELECT pti.id_indirizzo, pti.ID_NAZIONE , pti.ID_COMUNE , PTI .ID_PROVINCIA ,  PTI .ID_REGIONE , pti .ID_COMUNE_ESTERO \r\n"
			+ "   	FROM PBANDI_T_INDIRIZZO pti LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
			+ "   	ON prsd.ID_INDIRIZZO_PERSONA_FISICA = pti.ID_INDIRIZZO \r\n"
			+ "   	WHERE prsd.ID_SOGGETTO ="+idSoggetto+"\r\n"
			+ "   	AND prsd.PROGR_SOGGETTO_DOMANDA IN (SELECT MAX(PROGR_SOGGETTO_DOMANDA) AS PROGR_SOGGETTO_DOMANDA \r\n"
			+ "   										FROM PBANDI_R_SOGGETTO_DOMANDA\r\n"
			+ "   										GROUP BY ID_SOGGETTO)"; 
	} else {
		select = "  SELECT pti.id_indirizzo, pti.ID_NAZIONE , pti.ID_COMUNE , PTI .ID_PROVINCIA ,  PTI .ID_REGIONE , pti .ID_COMUNE_ESTERO,  prsd.PROGR_SOGGETTO_DOMANDA \r\n"
				+ "    FROM PBANDI_T_INDIRIZZO pti LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsds.ID_INDIRIZZO = pti.ID_INDIRIZZO \r\n"
				+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA \r\n"
				+ "    WHERE prsd.ID_SOGGETTO ="+idSoggetto+"\r\n"
				+ "    AND prsd.PROGR_SOGGETTO_DOMANDA IN (SELECT MAX(PROGR_SOGGETTO_DOMANDA) AS PROGR_SOGGETTO_DOMANDA \r\n"
				+ "					   										FROM PBANDI_R_SOGGETTO_DOMANDA\r\n"
				+ "															GROUP BY ID_SOGGETTO)"; 
	}
	
	IndirizzoVO indirizzo = new IndirizzoVO(); 
	
	indirizzo = getJdbcTemplate().query(select, new ResultSetExtractor<IndirizzoVO>() {

		@Override
		public IndirizzoVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			IndirizzoVO ind = new IndirizzoVO(); 
			while (rs.next()) {
					ind.setIdIndirizzo(rs.getLong("id_indirizzo"));
					ind.setIdComuneEstero(rs.getLong("ID_COMUNE_ESTERO"));
					ind.setIdComune(rs.getLong("ID_COMUNE"));
					ind.setIdRegione(rs.getLong("ID_REGIONE"));
					ind.setIdProvincia(rs.getLong("ID_PROVINCIA"));
					ind.setIdNazione(rs.getLong("ID_NAZIONE"));
			}
			return ind;
		}
	}); 
			
	// verifico se è un  indirizzo estero quindi con comune estera e se è uguale alla comune passata in questa funzione
	if(indirizzo.getIdComuneEstero()!=null && indirizzo.getIdComuneEstero()==idComune) {
		if(idNazione == indirizzo.getIdNazione()) {
			return true; 
		} else {
			return false; 
		}
	} 
	
	if(indirizzo.getIdComune()==idComune  && indirizzo.getIdRegione()==idRegione && indirizzo.getIdProvincia()== idProvincia
			&& indirizzo.getIdNazione()==idNazione) {
		return true; 
	}
	return false;
}
private BigDecimal insertIndirizzoPF(AnagraficaBeneficiarioPfVO soggetto, String idSoggetto) {
	// recupero l'id_indirizzo 
			String getId = "SELECT SEQ_PBANDI_T_INDIRIZZO.nextval FROM dual";	
			BigDecimal idIndirizzo=  getJdbcTemplate().queryForObject(getId, BigDecimal.class);
			
			
			StringBuilder insert = new StringBuilder();
			
			insert.append("insert into PBANDI_T_INDIRIZZO("
					+ "id_indirizzo, "
					+ "desc_indirizzo,");
			if(soggetto.getIdProvinciaPF()!=null) {
				
				insert.append("cap, "
						+ "id_nazione, "
						+ "id_comune, "
						+ "id_regione, "
						+ "id_provincia,"
						+ "id_utente_ins, "
						+ "dt_inizio_validita) values(?,?,?,?,?,?,?,?,sysdate)"); 
			getJdbcTemplate().update(insert.toString(), new Object[] {
				idIndirizzo,
				soggetto.getIndirizzoPF(), 
				soggetto.getCapPF(), 
				soggetto.getIdNazionePF(), 
				soggetto.getIdComunePF(), 
				soggetto.getIdRegionePF(), 
				soggetto.getIdProvinciaPF(), 
				soggetto.getIdUtenteAgg()
			});

			} else {
				insert.append("id_comune_estero,"
						+ "id_nazione, "
						+ "id_utente_ins, "
						+ "ddt_inizio_validita) values(?,?,?,?,?,sysdate)");
				getJdbcTemplate().update(insert.toString(), new Object[] {
						idIndirizzo,
						soggetto.getIndirizzoPF(), 
						soggetto.getIdNazionePF(), 
						soggetto.getIdComunePF(), 
						soggetto.getIdUtenteAgg()
					});
			}
	return idIndirizzo;
}
private String getProgDomandaSede(BigDecimal progSoggDomanda) {
	
	String get="    SELECT max( prsds.PROGR_SOGGETTO_DOMANDA_SEDE ) AS PROGR_SOGGETTO_DOMANDA_SEDE \r\n"
			+ "    FROM PBANDI_R_SOGGETTO_DOMANDA prsd LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds \r\n"
			+ "   	ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA \r\n"
			+ "   	WHERE prsds.PROGR_SOGGETTO_DOMANDA="+progSoggDomanda; 
	String progDomandaSede = getJdbcTemplate().queryForObject(get, String.class);
	return progDomandaSede;
}

@Override
public Boolean modificaSoggettoEG(AnagraficaBeneficiarioVO soggetto, String idSoggetto, String idDomanda) {
	String prf = "[DatiDomandaDAOImpl::modifica soggetto correlato dipendente da domanda]";
	LOG.info(prf + " BEGIN");
	Boolean result; 
	
	try {
		
	
		// sezione sede legale PBANDI_T_INDIRIZZO 
		
		if ((soggetto.getIdTipoSoggCorr() ==23 || soggetto.getIdTipoSoggCorr()==3)
				&& soggetto.getDescStatoDomanda() != "Presentata") {
			String getVeroIddomanda ="select ptd.id_domanda from pbandi_t_domanda ptd\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON ptd.id_domanda = prsd.ID_DOMANDA \r\n"
					+ "    where numero_domanda='"+idDomanda+"'\r\n"
					+ "	    and rownum =1";
//					+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n";
			
			BigDecimal veroIdDomanda = (BigDecimal) getJdbcTemplate().queryForObject(getVeroIddomanda,
					BigDecimal.class);
			boolean insertEG = false;
			// recupero l'ultimo ente record presente sul db
			AnagraficaBeneficiarioVO anagDB = new AnagraficaBeneficiarioVO();
			String queryDB = "select * from PBANDI_T_ENTE_GIURIDICO where id_ente_giuridico="
					+ soggetto.getIdEnteGiuridico();
			anagDB=  getJdbcTemplate().query(queryDB, new ResultSetExtractor<AnagraficaBeneficiarioVO>() {

				@Override
				public AnagraficaBeneficiarioVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					AnagraficaBeneficiarioVO pg = new AnagraficaBeneficiarioVO();
					while (rs.next()) {
						pg.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
						pg.setRagSoc(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
						pg.setDtCostituzione(rs.getDate("DT_COSTITUZIONE"));
						pg.setCapSociale(rs.getLong("CAPITALE_SOCIALE"));
						pg.setIdAttIuc(rs.getLong("ID_ATTIVITA_UIC"));
						pg.setIdFormaGiuridica(rs.getLong("ID_FORMA_GIURIDICA"));
						pg.setIdClassEnte(rs.getLong("ID_CLASSIFICAZIONE_ENTE"));
						pg.setIdDimensioneImpresa(rs.getLong("ID_DIMENSIONE_IMPRESA"));
						pg.setIdEnteGiuridico(rs.getLong("ID_ENTE_GIURIDICO"));
						pg.setDtInizioVal(rs.getDate("DT_INIZIO_VALIDITA"));
						pg.setDtFineVal(rs.getDate("DT_FINE_VALIDITA"));
						pg.setDtUltimoEseChiuso(rs.getDate("DT_ULTIMO_ESERCIZIO_CHIUSO"));
						pg.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
						pg.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
						pg.setFlagPubblicoPrivato(rs.getLong("FLAG_PUBBLICO_PRIVATO"));
						pg.setCodUniIpa(rs.getString("COD_UNI_IPA"));
						pg.setIdStatoAtt(rs.getLong("ID_STATO_ATTIVITA"));
						pg.setDtValEsito(rs.getDate("DT_VALUTAZIONE_ESITO"));
						pg.setPeriodoScadEse(rs.getString("PERIODO_SCADENZA_ESERCIZIO"));
						pg.setDtInizioAttEsito(rs.getDate("DT_INIZIO_ATTIVITA_ESITO"));
						pg.setFlagRatingLeg(rs.getString("FLAG_RATING_LEGALITA"));
						
					}
					return pg;
				}}); 
			
			
			// aggiorno l'id_utente_agg presente al db
			String updateIDUtenteAgg = "update PBANDI_T_ENTE_GIURIDICO set id_utente_agg = " + soggetto.getIdUtenteAgg()
					+ ",  dt_fine_validita= sysdate " + "where id_ente_giuridico = " + soggetto.getIdEnteGiuridico();
			getJdbcTemplate().update(updateIDUtenteAgg);
			
			// recupero il prossimo id dell'ente giuridico
			String sqlSeq = "SELECT SEQ_PBANDI_T_ENTE_GIURIDICO.nextval FROM dual";
			BigDecimal identeGiuridico = getJdbcTemplate().queryForObject(sqlSeq.toString(), BigDecimal.class);
			
			
			/// sezione dati anagrafici ente giuridico  se c''è stato una modifica:aggiorno questa parte
			if (soggetto.isDatiAnagrafici() == true) {
				anagDB.setRagSoc(soggetto.getRagSoc());
				anagDB.setIdFormaGiuridica(soggetto.getIdFormaGiuridica());
				anagDB.setCodUniIpa(soggetto.getCodUniIpa());
				anagDB.setDtCostituzione(soggetto.getDtCostituzione());
				anagDB.setFlagPubblicoPrivato(soggetto.getFlagPubblicoPrivato());
				// aggiorno la tabella pbandi_t_sede per quanto riguarda la partita iva e la tabella pbandi_t_soggetto per il codiceFiscale, 
			}
			// se è stata modificata la parte economica la modifico anche
			if (soggetto.isAttivitaEconomica() == true) {
				anagDB.setFlagRatingLeg(soggetto.getFlagRatingLeg());
				anagDB.setIdStatoAtt(soggetto.getIdStatoAtt());
				anagDB.setDtInizioAttEsito(soggetto.getDtInizioAttEsito());
				anagDB.setPeriodoScadEse(soggetto.getPeriodoScadEse());
				anagDB.setDtUltimoEseChiuso(soggetto.getDtUltimoEseChiuso());

			}
			if (soggetto.isDatiAnagrafici() == true || soggetto.isAttivitaEconomica() == true) {
				anagDB.setIdUtenteIns(soggetto.getIdUtenteAgg());
				insertEG = insertDatiAnagrafici(anagDB, identeGiuridico);
				//aggiorno la tabella pbandi_t_sede con il codice ateco , la descrizione attivita ateco e la partita_iva
				
				if(soggetto.getIdSede() !=null && soggetto.getIdSede()>0 ) {
					
				String updatePTSede = "update PBANDI_T_SEDE set " 
									+ "  id_attivita_ateco=" + soggetto.getIdAttAteco()
									+ " , partita_iva=" + soggetto.getpIva() 
									+ "  where id_sede=" + soggetto.getIdSede();
				getJdbcTemplate().update(updatePTSede);
				} else {
					//insertSede(soggetto, idSoggetto); 
				}

				// update delle tabelle PBANDI_R_SOGGETTO_DOMANDA_SEDE e PBANDI_R_SOGG_PROGETTO_SEDE	
				
			}
			if (insertEG == true) {
				// se ho fatto l'inserimento allora bisigna aggiornare le tabelle  PBANDI_R_SOGGETTO_DOMANDA con il nuovo idEnteGiuridico

				String updatePRSogg = "update PBANDI_R_SOGGETTO_DOMANDA set "
						+ " DT_AGGIORNAMENTO = sysdate, "
						+ " ID_UTENTE_AGG=" + soggetto.getIdUtenteAgg()
						+ ", ID_ENTE_GIURIDICO =" + identeGiuridico
						+ " where PROGR_SOGGETTO_DOMANDA=" + soggetto.getProgSoggDomanda();

				getJdbcTemplate().update(updatePRSogg);

			} else {
				// se non ho fatto l'inserimento allora basta aggiornare le tabelle PBANDI_R_SOGGETTO_DOMANDA o/e PBANDI_R_SOGGETTO_PROGETTO
				String updatePRSogg = "update PBANDI_R_SOGGETTO_DOMANDA set "
						+ " DT_AGGIORNAMENTO = sysdate, "
						+ " ID_UTENTE_AGG=" + soggetto.getIdUtenteAgg() 
						+ " where PROGR_SOGGETTO_DOMANDA="+ soggetto.getProgSoggDomanda();
				getJdbcTemplate().update(updatePRSogg);

				if (soggetto.getProgSoggProgetto() != null) {
					String updatePrsp = "update PBANDI_R_SOGGETTO_PROGETTO set dt_aggiornamento= sysdate,"
							+ " id_utene_agg=" + soggetto.getIdUtenteAgg() 
							+ ", ID_ENTE_GIURIDICO=" + identeGiuridico
							+ " where PROGR_SOGGETTO_PROGETTO=" + soggetto.getProgSoggProgetto();

					getJdbcTemplate().update(updatePrsp);
				}
			}
			if (soggetto.isSedeLegale() == true) {
				// se presente l'id_indirizzo aggiorno i dati presenti sul db 
				if (checkMatchCompletoIndirizzo(soggetto.getIdComune(), soggetto.getIdNazione(),
						soggetto.getIdProvincia(), soggetto.getIdRegione(), idSoggetto, 2) == true) {
					updateIndirizzoSedeLegale(soggetto, idSoggetto);

				} else {
					// inserisco i dati sul db e aggiorno l'id indirizzo sulla tabella PBANDI_R_SOGGETTO_DOMANDA_SEDE e PBANDI_R_SOGGETTO_DOMANDA_SEDE
					insertSedeLegale(soggetto, idSoggetto);
				}
			}
			// sezione dati iscrizione
			if (soggetto.isDatiIscrizione() == true) {
				if (checkIscrizione(soggetto, idSoggetto) == true) {
					// soggetto gia iscritto e faccio solo l'update
					String updateIscrizione = "update PBANDI_T_ISCRIZIONE set " + " id_utente_agg="
							+ soggetto.getIdUtenteAgg() + "  where id_iscrizione=" + soggetto.getIdIscrizione();
					getJdbcTemplate().update(updateIscrizione);

				} else {
					// soggetto non iscritto e lo inserisco quando l'ho inserito aggiorno le tabelle PBANDI_R_SOGGETTO_DOMANDA

					String getId = "SELECT SEQ_PBANDI_T_ISCRIZIONE.nextval FROM dual";	
					BigDecimal idIscrizione=  getJdbcTemplate().queryForObject(getId, BigDecimal.class);
					soggetto.setFlagIscrizione("S");
					String insertIscrizione= "insert into\r\n"
							+ "    PBANDI_T_ISCRIZIONE (\r\n"
							+ "        id_iscrizione,\r\n"
							+ "        num_iscrizione,\r\n"
							+ "        dt_iscrizione,\r\n"
							+ "        FLAG_ISCRIZIONE_IN_CORSO,\r\n"
							+ "        id_utente_ins,\r\n"
							+ "        id_tipo_iscrizione,\r\n"
							+ "        id_provincia,\r\n"
							+ "        dt_inizio_validita)\r\n"
							+ "values (?,?,?,?,?,?,?, sysdate)";
					
					getJdbcTemplate().update(insertIscrizione, new Object[] {
						idIscrizione, 
						soggetto.getNumIscrizione(), 
						(soggetto.getDtIscrizione()!=null)? soggetto.getDtIscrizione():null, 
						soggetto.getFlagIscrizione().trim(),
						soggetto.getIdUtenteAgg(), 
						(soggetto.getIdTipoIscrizione()!=null && soggetto.getIdTipoIscrizione()>0 )?soggetto.getIdTipoIscrizione():2, 
						soggetto.getIdProvinciaIscrizione()
					});
					if (insertEG == true) {
						String updateRSoggDom="update PBANDI_R_SOGGETTO_DOMANDA set "
								+ " ID_ISCRIZIONE_PERSONA_GIURID = "+idIscrizione+",\r\n "
								+ "dt_aggiornamento= sysdate"
								+ ",id_utente_agg="+ soggetto.getIdUtenteAgg()
								+ "where ID_ENTE_GIURIDICO="+ identeGiuridico; 
						
						getJdbcTemplate().update(updateRSoggDom);
					} else {

						String updateRSoggDom="update PBANDI_R_SOGGETTO_DOMANDA set "
								+ " ID_ISCRIZIONE_PERSONA_GIURID = "+idIscrizione+",\r\n "
								+ "dt_aggiornamento= sysdate"
								+ ",id_utente_agg="+ soggetto.getIdUtenteAgg()
								+ "where ID_ENTE_GIURIDICO="+ soggetto.getIdEnteGiuridico(); 
						
						getJdbcTemplate().update(updateRSoggDom); 
					}
				}
			}
			if (soggetto.isSezioneAppartenenza() == true) {

				String checkSezione = "select ID_SEZIONE_SPECIALE from PBANDI_T_ENTE_GIUR_SEZI "
						+ " where id_soggetto =" + idSoggetto + "  and DT_FINE_VALIDITA is null";

				String idSezioneSpec = getJdbcTemplate().queryForObject(checkSezione, String.class);

				if (idSezioneSpec.equals(soggetto.getIdSezioneSpeciale().toString())) {
					String updateSezioneAp = "update PBANDI_T_ENTE_GIUR_SEZI set " + "id_utente_agg= "
							+ soggetto.getIdUtenteAgg() + ",DT_FINE_VALIDITA= sysdate " + " where id_ente_sezione="
							+ soggetto.getIdEnteSezione();

					getJdbcTemplate().update(updateSezioneAp);
				} else {
					// insert sezione 
					String getId = "SELECT SEQ_PBANDI_T_ENTE_GIUR_SEZI.nextval FROM dual";
					BigDecimal idSezione = getJdbcTemplate().queryForObject(getId, BigDecimal.class);

					String insertSezione = "insert into PBANDI_T_ENTE_GIUR_SEZI (" + "id_ente_sezione, "
							+ "id_soggetto, " + "id_sezione_speciale, " + "id_utente_ins, "
							+ "dt_inizio_validita) values(?,?,?,?,sysdate)";

					getJdbcTemplate().update(insertSezione, new Object[] { idSezione, idSoggetto,
							soggetto.getIdSezioneSpeciale(), soggetto.getIdUtenteAgg() });
				}

			}
			result = true;
		} else {
			result = false; 
		}
	} catch (Exception e) {
		result = false; 
		LOG.error("Exception while trying to insert soggetto ente giuridico", e);
	}
	
	return result;
}
private boolean insertSede(AnagraficaBeneficiarioVO soggetto, String idSoggetto) {

	boolean result; 
	try {
		String getId = "SELECT SEQ_PBANDI_T_SEDE.nextval FROM dual";
		String id = getJdbcTemplate().queryForObject(getId, String.class); 
		String insertSedeQuery = "insert into PBANDI_T_SEDE ("
				+ " id_sede, "
				+ " PARTITA_IVA, "
				+ " ID_UTENTE_INS, "
				+ " ID_ATTIVITA_ATECO, "
				+ " DT_INIZIO_VALIDITA) values (?,?,?,?, trunc(sysdate))"; 
		
		getJdbcTemplate().update(insertSedeQuery, new Object[] {
				id,
				soggetto.getpIva(),
				soggetto.getIdUtenteAgg(), 
				soggetto.getIdAttAteco()
		});
		
		result = true; 
	} catch (Exception e) {
		result = false; 
		LOG.error("[DatiDomandaDAOimpl::insertSede] Exception "+ e.getMessage());
	}
	
	return result; 
}

private boolean checkIscrizione(AnagraficaBeneficiarioVO soggetto, String idSoggetto) {
	
	AnagraficaBeneficiarioVO anagDB= new AnagraficaBeneficiarioVO(); 
	if(soggetto.getIdIscrizione()>0) {
		
		String getIScrizione = "Select NUM_ISCRIZIONE,  TRUNC(DT_ISCRIZIONE) AS DT_ISCRIZIONE, ID_PROVINCIA, ID_REGIONE "
				+ "from PBANDI_T_ISCRIZIONE where ID_ISCRIZIONE="+ soggetto.getIdIscrizione();
		
		anagDB = getJdbcTemplate().query(getIScrizione, new ResultSetExtractor<AnagraficaBeneficiarioVO>() {

			@Override
			public AnagraficaBeneficiarioVO extractData(ResultSet rs) throws SQLException, DataAccessException {
				AnagraficaBeneficiarioVO ana = new AnagraficaBeneficiarioVO();
				while (rs.next()) {
					ana.setIdProvinciaIscrizione(rs.getLong("ID_PROVINCIA"));
					ana.setNumIscrizione(rs.getString("NUM_ISCRIZIONE"));
					ana.setDtIscrizione(rs.getDate("DT_ISCRIZIONE"));
				}
				return ana;
			}
		});
			
	} else {
		return false;
	}
	if(anagDB.getIdProvinciaIscrizione()!=soggetto.getIdProvinciaIscrizione()) {			
		return false; 
	}
	
	if(!anagDB.getDtIscrizione().toString().trim().equals(soggetto.getDtIscrizione().toString().trim())) {			
		return false;
	}
	if(!anagDB.getNumIscrizione().trim().equals(soggetto.getNumIscrizione().trim())) {
		return false;			
	}
	
	return true;
}

private boolean insertDatiAnagrafici(AnagraficaBeneficiarioVO anagDB,  BigDecimal idEnteGiuridico) {

	boolean result; 
	try {
		anagDB.setIdEnteGiuridico(idEnteGiuridico.longValue());
		String insertEG ="insert into PBANDI_T_ENTE_GIURIDICO ("
				+ "ID_SOGGETTO,\r\n"
				+ "DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
				+ "DT_COSTITUZIONE,\r\n"
				+ "CAPITALE_SOCIALE,\r\n"
				+ "ID_ATTIVITA_UIC,\r\n"
				+ "ID_FORMA_GIURIDICA,\r\n"
				+ "ID_CLASSIFICAZIONE_ENTE,\r\n"
				+ "ID_DIMENSIONE_IMPRESA,\r\n"
				+ "ID_ENTE_GIURIDICO,\r\n"
				+ "DT_ULTIMO_ESERCIZIO_CHIUSO,\r\n"
				+ "ID_UTENTE_INS,\r\n"
				+ "FLAG_PUBBLICO_PRIVATO,\r\n"
				+ "COD_UNI_IPA,\r\n"
				+ "ID_STATO_ATTIVITA,\r\n"
				+ "DT_VALUTAZIONE_ESITO,\r\n"
				+ "PERIODO_SCADENZA_ESERCIZIO,\r\n"
				+ "DT_INIZIO_ATTIVITA_ESITO,\r\n"
				+ "DT_INIZIO_VALIDITA,\r\n"
				+ "FLAG_RATING_LEGALITA"
				+ ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)";
		
		getJdbcTemplate().update(insertEG, new Object[] {
				anagDB.getIdSoggetto(), 
				anagDB.getRagSoc(), 
				(anagDB.getDtCostituzione()!=null)? anagDB.getDtCostituzione():null, 
				(anagDB.getCapSociale()>0)?anagDB.getCapSociale():null, 
				(anagDB.getIdAttIuc()>0)?anagDB.getIdAttIuc():null, 
				(anagDB.getIdFormaGiuridica()>0)?anagDB.getIdFormaGiuridica():null, 
				(anagDB.getIdClassEnte()>0)?anagDB.getIdClassEnte():null, 
				(anagDB.getIdDimensioneImpresa()>0)?anagDB.getIdDimensioneImpresa():null, 
				anagDB.getIdEnteGiuridico(),
				(anagDB.getDtUltimoEseChiuso()!=null)? anagDB.getDtUltimoEseChiuso():null, 
				anagDB.getIdUtenteIns(), 
				anagDB.getFlagPubblicoPrivato(), 
				(anagDB.getCodUniIpa()!=null)?anagDB.getCodUniIpa():null, 
				(anagDB.getIdStatoAtt() !=null && anagDB.getIdStatoAtt()>0 )?anagDB.getIdStatoAtt():null, 
				(anagDB.getDtValEsito()!=null)? anagDB.getDtValEsito(): null, 
				anagDB.getPeriodoScadEse(), 
				(anagDB.getDtInizioAttEsito()!=null)?anagDB.getDtInizioAttEsito():null,
				anagDB.getFlagRatingLeg().trim()
		});
		
		result = true; 
		
	} catch (Exception e) {
		result  = false;
		LOG.error("[DatiDomandaDAOimpl::insertDatiAnagrafici] Exception "+ e.getMessage());
	}
	
return result; 	
}


private void updateIndirizzoSedeLegale(AnagraficaBeneficiarioVO soggetto, String idSoggetto) {

	String updateSede ="update PBANDI_T_INDIRIZZO set "
			+ "desc_indirizzo="+soggetto.getDescIndirizzo()
			+ ", id_nazione="+ soggetto.getIdNazione()
			+ ", id_regione="+soggetto.getIdRegione()
			+ ", id_comune="+soggetto.getIdComune()
			+ ", id_provincia="+soggetto.getIdProvincia()
			+ ", id_utente_agg="+ soggetto.getIdUtenteAgg()
			+ " where id_indirizzo="+soggetto.getIdIndirizzo();
	
	getJdbcTemplate().update(updateSede); 
	
}

private void insertSedeLegale(AnagraficaBeneficiarioVO soggetto, String idSoggetto) {
	
	// recupero l'id_indirizzo 
	String getId = "SELECT SEQ_PBANDI_T_INDIRIZZO.nextval FROM dual";	
	BigDecimal idIndirizzo=  getJdbcTemplate().queryForObject(getId, BigDecimal.class);
	
	
	String insertIndirizzo = "insert into PBANDI_T_INDIRIZZO ("
			+ "id_indirizzo, "
			+ "desc_indirizzo, "
			+ "cap, "
			+ "id_nazione, "
			+ "id_comune, "
			+ "id_regione, "
			+ "id_provincia,"
			+ "id_utente_ins, "
			+ "dt_inizio_validita) values(?,?,?,?,?,?,?,?,sysdate)";
	getJdbcTemplate().update(insertIndirizzo, new Object[] {
		idIndirizzo,
		soggetto.getDescIndirizzo(), 
		soggetto.getCap(), 
		soggetto.getIdNazione(), 
		soggetto.getIdComune(), 
		soggetto.getIdRegione(), 
		soggetto.getIdProvincia(), 
		soggetto.getIdUtenteAgg()
		
	});
	// inserisco il nuovo indirizzo nelle tabelle PBANDI_R_SOGGETTO_DOMANDA_SEDE e PBANDI_R_SOGGETTO_DOMANDA_SEDE
	
	String updateIndirizoIntoRsoggSede = "update PBANDI_R_SOGGETTO_DOMANDA_SEDE set "
			+ " id_indirizzo="+ idIndirizzo
			+", id_utente_agg="+ soggetto.getIdUtenteAgg()
			+" where PROGR_SOGGETTO_DOMANDA="+ soggetto.getProgSoggDomanda();
	getJdbcTemplate().update(updateIndirizoIntoRsoggSede); 
	
}

@Override
public Boolean updateDatiDomandaEG(DatiDomandaEgVO domandaEG, BigDecimal idUtente) {
	String prf = "[DatiDomandaDAOImpl::modifica dati domanda ente giuridico]";
	LOG.info(prf + " BEGIN");
	Boolean result = true;
	try {
		
		// controllo  i recapiti della domanda. 
		
		RecapitoVO recapito = new RecapitoVO(); 
		recapito = getRecapito(domandaEG.getNumeroDomanda()); 
		//
		BigDecimal idNewRecapito =insertRecapito(domandaEG, idUtente); 
		//aggiorno la tabella prsoggetto domanda sede; 
		if(idNewRecapito!=null) {
			String update="update PBANDI_R_SOGGETTO_DOMANDA_SEDE set ID_RECAPITI=? , id_utente_Agg=?\r\n"
					+ " where PROGR_SOGGETTO_DOMANDA_SEDE=?" ;
			
			getJdbcTemplate().update(update, new Object[] {
				idNewRecapito, 
				idUtente,
				recapito.getProgrSoggettoDomandaSede()
			});
		}
		
	} catch (Exception e) {
		result = false;
		LOG.error( prf + "errore modifica dati domanda dell'ente giuridico");
	} finally {
		LOG.info(prf+ "END");
	}
	
	return result;
}

private BigDecimal insertRecapito(DatiDomandaEgVO domandaEG, BigDecimal idUtente) {
	
	BigDecimal idRecapito= null; 
	try {
		idRecapito = getJdbcTemplate().queryForObject("select SEQ_PBANDI_T_RECAPITI.nextval from dual", BigDecimal.class); 
		String insertRecapito ="insert into PBANDI_T_RECAPITI (ID_RECAPITI,\r\n"
				+ "EMAIL,\r\n"
				+ "FAX,\r\n"
				+ "TELEFONO,\r\n"
				+ "ID_UTENTE_INS,\r\n"
				+ "DT_INIZIO_VALIDITA,\r\n"
				+ "PEC) values (?,?,?,?,?,trunc(sysdate), ?)"; 
		getJdbcTemplate().update(insertRecapito, new Object[] {
			idRecapito, 
			domandaEG.getEmail(),
			domandaEG.getFax(), 
			domandaEG.getTelefono(), 
			idUtente,
			(domandaEG.getPec()!=null)?domandaEG.getPec().trim():null,
		});
		
	} catch (Exception e) {
		LOG.error("errore inserimento nuovo recapito " + e.getMessage());
	}
	
	return idRecapito;
}

private RecapitoVO getRecapito(String numeroDomanda) {
	RecapitoVO recapito = new RecapitoVO();
	try {
		String getPec="SELECT\r\n"
				+ "    ptr.PEC,\r\n"
				+ "    ptr.ID_RECAPITI,\r\n"
				+ "    prsds.PROGR_SOGGETTO_DOMANDA_SEDE,\r\n"
				+ "    ptr.FAX,\r\n"
				+ "    ptr.EMAIL,\r\n"
				+ "    ptr.TELEFONO\r\n"
				+ "FROM\r\n"
				+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
				+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA\r\n"
				+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON ptr.ID_RECAPITI = prsds.ID_RECAPITI\r\n"
				+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
				+ "WHERE\r\n"
				+ "    prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
				+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
				+ "    AND prsds.ID_TIPO_SEDE = 2\r\n"
				+ "    AND ptd.NUMERO_DOMANDA = ?\r\n"
				+ "		and rownum<2"; 
		
		recapito = getJdbcTemplate().query(getPec, new Object[] {numeroDomanda}, new ResultSetExtractor<RecapitoVO>() {

			@Override
			public RecapitoVO extractData(ResultSet rs) throws SQLException, DataAccessException {
				RecapitoVO rec = new RecapitoVO();
				while (rs.next())
				{							
					rec.setProgrSoggettoDomandaSede(rs.getLong("PROGR_SOGGETTO_DOMANDA_SEDE"));
					rec.setIdRecapiti(rs.getLong("ID_RECAPITI"));
					rec.setPec(rs.getString("PEC"));
					rec.setEmail(rs.getString("EMAIL"));
					rec.setFax(rs.getString("FAX"));
					rec.setTelefono(rs.getString("TELEFONO"));
				}
				return rec;
			}
			
		});	
	} catch (Exception e) {
		LOG.error("errore lettura tabella recapiti " + e.getMessage());
	}
	return recapito;
}

@Override
public Boolean checkProgetto(String numeroDomanda) {
	
	try {
		String getIdProgetto = " SELECT prsp.ID_PROGETTO \r\n"
				+ "   FROM pbandi_r_soggetto_progetto prsp\r\n"
				+ "   LEFT JOIN pbandi_r_soggetto_domanda prsd ON prsd.progr_soggetto_domanda = prsp.PROGR_SOGGETTO_DOMANDA \r\n"
				+ "   LEFT JOIN pbandi_t_domanda ptd ON ptd.ID_DOMANDA = prsd.id_domanda\r\n"
				+ "   WHERE ptd.NUMERO_DOMANDA =?\r\n"
				+ "	  AND ROWNUM<2 ";
		BigDecimal idProgetto =null; 
		idProgetto=getJdbcTemplate().queryForObject(getIdProgetto, BigDecimal.class, new Object[] {	numeroDomanda });
		if(idProgetto!=null) {
			return true;
		} else {
			return false; 
		}
		
	} catch (Exception e) {
		LOG.error("errore lettura tabella pbandi_r_soggetto_progetto "+ e.getMessage());
	}
	
	return false;
}

@Override
public Boolean updateDatiDomandaPF(DatiDomandaVO datiDomanda, BigDecimal idUtente) {
	
	String prf = "[DatiDomandaDAOImpl::modifica dati domanda ente giuridico]";
	LOG.info(prf + " BEGIN");
	Boolean result = true;
	try {
		
		// controllo  i recapiti della domanda. 
		BigDecimal idNewRecapito= null; 
		RecapitoVO recapito = new RecapitoVO(); 
		recapito = getRecapitoPF(datiDomanda.getNumeroDomanda()); 
		boolean checkRecap=false;
		checkRecap = checkRecapitiPF(recapito, datiDomanda.getEmail(), datiDomanda.getFax(), datiDomanda.getTelefono());
		if(!checkRecap) {			
			idNewRecapito=insertRecapitoPF(datiDomanda, idUtente); 
		}
		//aggiorno la tabella prsoggetto domanda; 
		if(idNewRecapito!=null) {
			String updateTable="update PBANDI_R_SOGGETTO_DOMANDA set ID_RECAPITI_PERSONA_FISICA="+idNewRecapito+"\r\n "
					+ ",ID_UTENTE_AGG="+idUtente +"\r\n"
					+ " where PROGR_SOGGETTO_DOMANDA=" + recapito.getProgrSoggettoDomandaSede() ;
			
			getJdbcTemplate().update(updateTable);
		}
		
		// dati documento identita QUESTO DA IMPLEMENTARE DOPO SOLO SE NECESSARIO
		
		BigDecimal idDocumentoIdentità = null; 
		if(datiDomanda.getIdTipoDocumentoIdentita()!=null && datiDomanda.getNumeroDocumento() != getNumeroDocumento(datiDomanda.getNumeroDomanda())) {			
			idDocumentoIdentità = insertDocumento(datiDomanda, idUtente); 
		}
		
		if(idDocumentoIdentità!=null) {
			String updateTable="update PBANDI_R_SOGGETTO_DOMANDA set ID_DOCUMENTO_PERSONA_FISICA="+idDocumentoIdentità+"\r\n "
					+ ",ID_UTENTE_AGG="+idUtente +"\r\n"
					+ " where PROGR_SOGGETTO_DOMANDA=" + recapito.getProgrSoggettoDomandaSede() ;
			
			getJdbcTemplate().update(updateTable);
		}
		
	} catch (Exception e) {
		result = false;
		LOG.error("errore modifica dati domanda dell'ente giuridico " + e.getMessage());
	} finally {
		LOG.info(prf+ "END");
	}
	
	return result;
	}

private String getNumeroDocumento(String numeroDomanda) {
	String prf = "[DatiDomandaDAOImpl::getIdTipoDocumentoSoggetto]";
	LOG.info(prf + " BEGIN");
	String numeroDocumento = null; 
	try {
		numeroDocumento = getJdbcTemplate().queryForObject("SELECT ptd.NUMERO_DOCUMENTO \r\n"
				+ "    FROM PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
				+ "    LEFT JOIN pbandi_t_documento ptd ON prsd.ID_DOCUMENTO_PERSONA_FISICA = ptd.ID_DOCUMENTO \r\n"
				+ "    LEFT JOIN PBANDI_T_DOMANDA ptdo ON ptdo.ID_DOMANDA = prsd.ID_DOMANDA \r\n"
				+ "    WHERE prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
				+ "    AND prsd.ID_TIPO_ANAGRAFICA =1\r\n"
				+ "    AND ptdo.NUMERO_DOMANDA='"+numeroDomanda+"'", String.class);
	} catch (Exception e) {
		LOG.error("Errore lettur tabella pbandi_t_documento " + e.getMessage());
		return null;
	}
	return numeroDocumento;
}

private BigDecimal insertDocumento(DatiDomandaVO datiDomanda, BigDecimal idUtente) {
	String prf = "[DatiDomandaDAOImpl::insertDocumento]";
	LOG.info(prf + " BEGIN");
	BigDecimal idDocumento = null; 
	try {
		
		// TODO: implementation of insert into table pbandi_t_documento 
		idDocumento = getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_DOCUMENTO.nextval FROM dual", BigDecimal.class); 
		String dataScadenza = null, dataRilascioDocumento = null;
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); 
		
		if(datiDomanda.getDataRilascio()!=null) {
			 dataRilascioDocumento = dt.format(datiDomanda.getDataRilascio());
		} 
		if(datiDomanda.getScadenzaDocumento()!=null) {
			dataScadenza = dt1.format(datiDomanda.getScadenzaDocumento());
		} 
		
		
		
		String  insert ="insert into pbandi_t_documento(ID_DOCUMENTO\r\n"
				+ "NUMERO_DOCUMENTO,\r\n"
				+ "DOCUMENTO_RILASCIATO_DA,\r\n"
				+ "DT_RILASCIO_DOCUMENTO,\r\n"
				+ "DT_SCADENZA_DOCUMENTO,\r\n"
				+ "DT_INIZIO_VALIDITA,\r\n"
				+ "ID_UTENTE_INS,\r\n"
				+ "ID_TIPO_DOCUMENTO) values (?,?,?,?,?,sysdate,?,?)";
		
		getJdbcTemplate().update(insert, new Object[] {
			idDocumento, 
			datiDomanda.getNumeroDocumento(), 
			(dataRilascioDocumento!=null&&dataRilascioDocumento.trim().length()>0)? Date.valueOf(dataRilascioDocumento):null, 
			(dataScadenza!=null&&dataScadenza.trim().length()>0)? Date.valueOf(dataScadenza):null, 
			idUtente, 
			datiDomanda.getIdTipoDocumentoIdentita()
		});
		
	} catch (Exception e) {
		LOG.error("Errore insertDocumento " + e.getMessage());
	}
	return idDocumento;
}

private DatiDomandaVO getDatiDocumento(String numeroDomanda) {
	
	DatiDomandaVO doc = new DatiDomandaVO(); 
	
	try {
	//String getDati ="select"; 
			
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	
	return doc;
}
private RecapitoVO getRecapitoPF(String numeroDomanda) {
	RecapitoVO recapito = new RecapitoVO();
	try {
		String getPec="SELECT\r\n"
				+ "    ptr.PEC,\r\n"
				+ "    ptr.ID_RECAPITI,\r\n"
				+ "    prsd.PROGR_SOGGETTO_DOMANDA,\r\n"
				+ "    ptr.FAX,\r\n"
				+ "    ptr.EMAIL,\r\n"
				+ "    ptr.TELEFONO\r\n"
				+ "FROM\r\n"
				+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
				+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON ptr.ID_RECAPITI = prsd.ID_RECAPITI_PERSONA_FISICA\r\n"
				+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
				+ "WHERE\r\n"
				+ "    prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
				+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
				+ "    AND ptd.NUMERO_DOMANDA = ? \r\n"
				+ "    AND ROWNUM<2"; 
		
		recapito = getJdbcTemplate().query(getPec, new Object[] {numeroDomanda}, new ResultSetExtractor<RecapitoVO>() {

			@Override
			public RecapitoVO extractData(ResultSet rs) throws SQLException, DataAccessException {
				RecapitoVO rec = new RecapitoVO();
				while (rs.next())
				{							
					rec.setProgrSoggettoDomandaSede(rs.getLong("PROGR_SOGGETTO_DOMANDA"));
					rec.setIdRecapiti(rs.getLong("ID_RECAPITI"));
					rec.setPec(rs.getString("PEC"));
					rec.setEmail(rs.getString("EMAIL"));
					rec.setFax(rs.getString("FAX"));
					rec.setTelefono(rs.getString("TELEFONO"));
				}
				return rec;
			}
			
		});	
	} catch (Exception e) {
		LOG.error("errore lettura tabella recapiti " + e.getMessage());
	}
	return recapito;
}
private boolean checkRecapitiPF(RecapitoVO recapito, String email, String fax, String telefono) {
	
	if(!recapito.getEmail().equals(email))
		return false;
	if(!recapito.getTelefono().equals(telefono))
		return false; 
	if(!fax.equals(recapito.getFax()))
		return false; 
	return true;
}

private BigDecimal insertRecapitoPF(DatiDomandaVO datiDomandaPf, BigDecimal idUtente) {
	
	BigDecimal idRecapito= null; 
	try {
		idRecapito = getJdbcTemplate().queryForObject("select SEQ_PBANDI_T_RECAPITI.nextval from dual", BigDecimal.class); 
		String insertRecapito ="insert into PBANDI_T_RECAPITI (ID_RECAPITI,\r\n"
				+ "EMAIL,\r\n"
				+ "FAX,\r\n"
				+ "TELEFONO,\r\n"
				+ "ID_UTENTE_INS,\r\n"
				+ "pec,\r\n"
				+ "DT_INIZIO_VALIDITA)\r\n"
				+ "values (?,?,?,?,?,?, trunc(sysdate))"; 
		getJdbcTemplate().update(insertRecapito, new Object[] {
			idRecapito, 
			datiDomandaPf.getEmail(),
			datiDomandaPf.getFax(), 
			datiDomandaPf.getTelefono(), 
			idUtente,
			datiDomandaPf.getPec()
		});
		
	} catch (Exception e) {
		LOG.error("errore inserimento nuovo recapito " + e.getMessage());
	}
	
	return idRecapito;
}


@Override
public Boolean updateAnagBeneFisico(AnagraficaBeneficiarioPfVO soggetto, String idSoggetto, String numeroDomanda) throws ErroreGestitoException {
	String prf = "[AnagraficaBeneficiarioDAOImpl::updateAnagBeneFisico]";
	LOG.info(prf + " BEGIN");

	Boolean result=true; 
	try {
		BigDecimal progSoggProgetto = null;
		// questo progr soggetto progetto mi servira per poter aggiornare la tabella PROGR_SOGGETTO_PROGETTO
		try {				
			 
			progSoggProgetto = getJdbcTemplate().queryForObject("select prsp.PROGR_SOGGETTO_PROGETTO from PBANDI_R_SOGGETTO_PROGETTO prsp"
					+ " where prsp.PROGR_SOGGETTO_DOMANDA="+ soggetto.getProgSoggDomanda(), BigDecimal.class);
		} catch (Exception e) {
			LOG.error(e);
			progSoggProgetto= null;
		}
		 
		 
		AnagraficaBeneficiarioPfVO anagDB = new AnagraficaBeneficiarioPfVO();
		BigDecimal idPersFisica = null; 
		boolean isInsertPersFisica = false;
		// recupero i dati della persona fisica della personna fisica
		String query = " SELECT\r\n"
				+ "    cognome,\r\n"
				+ "    nome,\r\n"
				+ "    sesso,\r\n"
				+ "    dt_nascita,\r\n"
				+ "    id_cittadinanza,\r\n"
				+ "    id_titolo_studio,\r\n"
				+ "    id_occupazione,\r\n"
				+ "    id_persona_fisica,\r\n"
				+ "    ID_COMUNE_ITALIANO_NASCITA ,\r\n"
				+ "    ID_COMUNE_ESTERO_NASCITA ,\r\n"
				+ "    pdc.ID_PROVINCIA,  id_persona_fisica \r\n"
				+ "FROM\r\n"
				+ "    PBANDI_T_PERSONA_FISICA ptpf \r\n"
				+ "    LEFT JOIN PBANDI_D_COMUNE pdc ON pdc.ID_COMUNE = ptpf.ID_COMUNE_ITALIANO_NASCITA \r\n"
				+ "WHERE\r\n"
				+ "     ID_PERSONA_FISICA IN (\r\n"
				+ "        SELECT\r\n"
				+ "            MAX(ID_PERSONA_FISICA) AS ID_PERSONA_FISICA\r\n"
				+ "        FROM\r\n"
				+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
				+ "        GROUP BY\r\n"
				+ "            ID_SOGGETTO\r\n"
				+ "    )\r\n"
				+ "    AND ID_SOGGETTO ="+idSoggetto;
		
		anagDB = getJdbcTemplate().query(query, new ResultSetExtractor<AnagraficaBeneficiarioPfVO>(){

			@Override
			public AnagraficaBeneficiarioPfVO extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				AnagraficaBeneficiarioPfVO anag = new AnagraficaBeneficiarioPfVO(); 
				while(rs.next()) {
					anag.setCognome(rs.getString("cognome"));
					anag.setNome(rs.getString("nome"));
					anag.setSesso(rs.getString("sesso"));
					anag.setIdCittadinanza(rs.getLong("id_cittadinanza"));
					anag.setIdTitoloStudio(rs.getLong("id_titolo_studio"));
					anag.setIdOccupazione(rs.getLong("id_occupazione"));
					anag.setIdPersonaFisica(rs.getLong("id_persona_fisica"));
					anag.setDataDiNascita(rs.getDate("dt_nascita"));
					if(rs.getString("ID_COMUNE_ITALIANO_NASCITA") !=null) {
						anag.setIdComuneDiNascita(rs.getLong("ID_COMUNE_ITALIANO_NASCITA"));
						anag.setIdProvinciaDiNascita(rs.getLong("ID_PROVINCIA"));
					} else {
						anag.setIdComuneEsteraNascita(rs.getLong("ID_COMUNE_ESTERO_NASCITA"));
					}
				}
				return anag;
			}
			
		});
				
		
		if(soggetto.isDatiAnagrafici()==true) {
			// set il codice fiscale dentro la tabella PTSOGETTO; 
			String updateCFisc = "update PBANDI_T_SOGGETTO set 	codice_fiscale_soggetto='"+soggetto.getCodiceFiscale()+"'"
					+ ", id_utente_agg="+ soggetto.getIdUtenteAgg()
					+ ", dt_aggiornamento=sysdate"
					+ " where id_soggetto="+ idSoggetto;
			
			getJdbcTemplate().update(updateCFisc); 
			
			// set dati anagrafici 
			// se esiste un occorenza personna fisica allora aggiorno se no allora inserisco
			if(checkDatiAnagPf(soggetto, anagDB)==true) {
			// non faccio nulla
			
					String updatePRSdom="update PBANDI_R_SOGGETTO_DOMANDA set "
							+ " id_persona_fisica=" + soggetto.getIdPersonaFisica()
							+ ", dt_aggiornamento=sysdate" 
							+ ", id_utente_agg="+soggetto.getIdUtenteAgg()
							+ " where id_soggetto="+ idSoggetto;
					getJdbcTemplate().update(updatePRSdom); 
				
			} else {
				
				 idPersFisica= insertPersonaFisica(soggetto, anagDB, idSoggetto); 
				 isInsertPersFisica = true;
				 String updatePRSogg = "update PBANDI_R_SOGGETTO_DOMANDA set "
							+ " DT_AGGIORNAMENTO = sysdate, \r\n"
							+ " ID_UTENTE_AGG=?\r\n"
							+ ", ID_PERSONA_FISICA =?\r\n"
							+ " where PROGR_SOGGETTO_DOMANDA=?";					
					getJdbcTemplate().update(updatePRSogg, new Object[] {
							soggetto.getIdUtenteAgg(), idPersFisica, soggetto.getProgSoggDomanda()
					});
							
					if(soggetto.getProgSoggProgetto()!=null) {
						// aggiorno anche il suo idEnteGirudico
						String updatePrsp = "update PBANDI_R_SOGGETTO_PROGETTO set dt_aggiornamento= sysdate, id_utente_agg="+soggetto.getIdUtenteAgg()
						+ ", ID_PERSONA_FISICA="+ idPersFisica
						+ " where PROGR_SOGGETTO_PROGETTO="+soggetto.getProgSoggProgetto();
						getJdbcTemplate().update(updatePrsp); 
						
					}
		
			}			

		}

		
		// Residenza update che corrisponde all'indirizzo della persona fisica
		if(soggetto.isSedeLegale()==true) { 
			if(checkMatchCompletoIndirizzo(soggetto.getIdComunePF(), soggetto.getIdNazionePF(), soggetto.getIdProvinciaPF(),
					soggetto.getIdRegionePF(),idSoggetto, 1, soggetto.getIdDomanda())==true) {
				String updateIndirizzo="update PBANDI_T_INDIRIZZO  set"
						+ " id_utente_agg=?"
						+ " where PROGR_SOGGETTO_DOMANDA=?";
				getJdbcTemplate().update(updateIndirizzo, new Object[] {soggetto.getIdUtenteAgg(), soggetto.getProgSoggDomanda()}); 
	
			} else {
				BigDecimal idIndirizzo=	insertIndirizzoPF(soggetto, idSoggetto); 
							
					String updateIndirizzo = "update PBANDI_R_SOGGETTO_DOMANDA set "
							+ " ID_INDIRIZZO_PERSONA_FISICA =?"
							+ ", ID_UTENTE_AGG=?"
							+ " where PROGR_SOGGETTO_DOMANDA=?" ;
					getJdbcTemplate().update(updateIndirizzo,new Object[] {
							idIndirizzo, soggetto.getIdUtenteAgg(), soggetto.getProgSoggDomanda()
					});
					
					if(soggetto.getProgSoggProgetto()!=null) {
						String update="update PBANDI_R_SOGGETTO_PROGETTO set"
								+ " ID_INDIRIZZO_PERSONA_FISICA=?\r\n"
								+ " , ID_UTENTE_AGG=?\r\n"
								+ " WHERE PROGR_SOGGETTO_PROGETTO=?";
						getJdbcTemplate().update(update,new Object[] {
								idIndirizzo, soggetto.getIdUtenteAgg(),soggetto.getProgSoggProgetto()
						});
					} 
			}
		}

	} catch (Exception e) {
		result = false; 
		LOG.error(prf + "Exception during updateAnagBeneFisico", e);
		throw new ErroreGestitoException("Exception during updateAnagBeneFisico", e);
	} finally {
		LOG.info(prf + " END");
	}
	return result;


}

private boolean checkMatchCompletoIndirizzo(Long idComune, Long idNazione, Long idProvincia, Long idRegione,
		String idSoggetto, int pf, String idDomanda) {
	 
	String select;
	if(pf==1) {
		
	 select ="  	SELECT\r\n"
	 		+ "    pti.id_indirizzo,\r\n"
	 		+ "    pti.ID_NAZIONE,\r\n"
	 		+ "    pti.ID_COMUNE,\r\n"
	 		+ "    PTI.ID_PROVINCIA,\r\n"
	 		+ "    PTI.ID_REGIONE,\r\n"
	 		+ "    pti.ID_COMUNE_ESTERO\r\n"
	 		+ "FROM\r\n"
	 		+ "    PBANDI_T_INDIRIZZO pti\r\n"
	 		+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.ID_INDIRIZZO_PERSONA_FISICA = pti.ID_INDIRIZZO\r\n"
	 		+ "WHERE\r\n"
	 		+ "    prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
	 		+ "    AND prsd.PROGR_SOGGETTO_DOMANDA IN (\r\n"
	 		+ "        SELECT\r\n"
	 		+ "            MAX(PROGR_SOGGETTO_DOMANDA) AS PROGR_SOGGETTO_DOMANDA\r\n"
	 		+ "        FROM\r\n"
	 		+ "            PBANDI_R_SOGGETTO_DOMANDA\r\n"
	 		+ "        where\r\n"
	 		+ "            id_domanda ="+idDomanda+"\r\n"
	 		+ "            and prsd.ID_TIPO_ANAGRAFICA <> 1\r\n"
	 		+ "        GROUP BY\r\n"
	 		+ "            ID_SOGGETTO\r\n"
	 		+ "    )"; 
	} else {
		select = " SELECT\r\n"
				+ "    pti.id_indirizzo,\r\n"
				+ "    pti.ID_NAZIONE,\r\n"
				+ "    pti.ID_COMUNE,\r\n"
				+ "    PTI.ID_PROVINCIA,\r\n"
				+ "    PTI.ID_REGIONE,\r\n"
				+ "    pti.ID_COMUNE_ESTERO,\r\n"
				+ "    prsd.PROGR_SOGGETTO_DOMANDA\r\n"
				+ "FROM\r\n"
				+ "    PBANDI_T_INDIRIZZO pti\r\n"
				+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsds.ID_INDIRIZZO = pti.ID_INDIRIZZO\r\n"
				+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA\r\n"
				+ "WHERE\r\n"
				+ "    prsd.ID_SOGGETTO ="+idSoggetto+"\r\n"
				+ "    AND prsd.PROGR_SOGGETTO_DOMANDA IN (\r\n"
				+ "        SELECT\r\n"
				+ "            MAX(PROGR_SOGGETTO_DOMANDA) AS PROGR_SOGGETTO_DOMANDA\r\n"
				+ "        FROM\r\n"
				+ "            PBANDI_R_SOGGETTO_DOMANDA\r\n"
				+ "        where\r\n"
				+ "            id_domanda = "+idDomanda+"\r\n"
				+ "            and prsd.ID_TIPO_ANAGRAFICA <> 1 \r\n"
				+ "        GROUP BY\r\n"
				+ "            ID_SOGGETTO\r\n"
				+ "    )"; 
	}
	
	IndirizzoVO indirizzo = new IndirizzoVO(); 
	
	indirizzo = getJdbcTemplate().query(select, new ResultSetExtractor<IndirizzoVO>() {

		@Override
		public IndirizzoVO extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			IndirizzoVO ind = new IndirizzoVO(); 
			while (rs.next()) {
					ind.setIdIndirizzo(rs.getLong("id_indirizzo"));
					ind.setIdComuneEstero(rs.getLong("ID_COMUNE_ESTERO"));
					ind.setIdComune(rs.getLong("ID_COMUNE"));
					ind.setIdRegione(rs.getLong("ID_REGIONE"));
					ind.setIdProvincia(rs.getLong("ID_PROVINCIA"));
					ind.setIdNazione(rs.getLong("ID_NAZIONE"));
			}
			return ind;
		}
	}); 
			
	// verifico se è un  indirizzo estero quindi con comune estera e se è uguale alla comune passata in questa funzione
	if(indirizzo.getIdComuneEstero()!=null && indirizzo.getIdComuneEstero()==idComune) {
		if(idNazione == indirizzo.getIdNazione()) {
			return true; 
		} else {
			return false; 
		}
	} 
	
	if(indirizzo.getIdComune()==idComune  && indirizzo.getIdRegione()==idRegione && indirizzo.getIdProvincia()== idProvincia
			&& indirizzo.getIdNazione()==idNazione) {
		return true; 
	}
	return false;
  }

@Override
public Object getAltreSedi(Long idSoggetto, String idDomanda) throws DaoException {
	String prf = "[DatidomandaDAOImpl::getSedeIntervento]";
	LOG.info(prf + " BEGIN");
	
	
	//DatiDomandaEgVO datiDomanda = new DatiDomandaEgVO();
	List<DatiDomandaEgVO> datiDomanda = new ArrayList<DatiDomandaEgVO>();
	try {

		
		BigDecimal idProgetto = checkProgetto(idDomanda, idSoggetto);
		
		if(idProgetto!=null && idProgetto.intValue()>0) {
			String sql = "    SELECT\r\n"
					+ "    ptp.ID_PROGETTO ,\r\n"
					+ "    ptd.DT_PRESENTAZIONE_DOMANDA,\r\n"
					+ "    pdsd.DESC_STATO_DOMANDA,\r\n"
					+ "    pts.PARTITA_IVA,\r\n"
					+ "    pti.DESC_INDIRIZZO,\r\n"
					+ "    pti.ID_INDIRIZZO,\r\n"
					+ "    pti.CAP,\r\n"
					+ "    pdc.DESC_COMUNE,\r\n"
					+ "    pdc.ID_COMUNE,\r\n"
					+ "    pdp.DESC_PROVINCIA,\r\n"
					+ "    pdp.ID_PROVINCIA,\r\n"
					+ "    pdr.DESC_REGIONE,\r\n"
					+ "    pdr.ID_REGIONE,\r\n"
					+ "    pdn.DESC_NAZIONE,\r\n"
					+ "    pdn.ID_NAZIONE,\r\n"
					+ "    ptr.EMAIL,\r\n"
					+ "    ptr.ID_RECAPITI,\r\n"
					+ "    ptr.FAX,\r\n"
					+ "    ptr.TELEFONO,\r\n"
					+ "    --pdb.DESC_BANCA,\r\n"
					+ "    ptr.PEC, \r\n"
					+ "    ptd.NUMERO_DOMANDA , ptd.ID_DOMANDA ,\r\n"
					+ "    prsps.FLAG_SEDE_AMMINISTRATIVA \r\n"
					+ " FROM\r\n"
					+ "    PBANDI_R_SOGGETTO_PROGETTO  prsp\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA \r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.ID_STATO_DOMANDA = ptd.ID_STATO_DOMANDA \r\n"
					+ "    LEFT JOIN PBANDI_T_progetto ptp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO \r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_PROGETTO  pdsp ON ptp.ID_STATO_PROGETTO  = pdsp.ID_STATO_PROGETTO  \r\n"
					+ "    LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps  ON prsps.PROGR_SOGGETTO_PROGETTO  = prsp.PROGR_SOGGETTO_PROGETTO \r\n"
					+ "    LEFT JOIN PBANDI_T_SEDE pts ON prsps.ID_SEDE = pts.ID_SEDE\r\n"
					+ "    LEFT JOIN PBANDI_T_INDIRIZZO pti ON prsps.ID_INDIRIZZO = pti.ID_INDIRIZZO\r\n"
					+ "    LEFT JOIN PBANDI_D_COMUNE pdc ON pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
					+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp ON pti.ID_PROVINCIA = pdp.ID_PROVINCIA\r\n"
					+ "    LEFT JOIN PBANDI_D_REGIONE pdr ON pdp.ID_REGIONE = pdr.ID_REGIONE\r\n"
					+ "    LEFT JOIN PBANDI_D_NAZIONE pdn ON pti.ID_NAZIONE = pdn.ID_NAZIONE\r\n"
					+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON prsps.ID_RECAPITI = ptr.ID_RECAPITI\r\n"
					+ "WHERE\r\n"
					+ "     prsp.DT_FINE_VALIDITA IS NULL \r\n"
					+ "    AND prsps.ID_TIPO_SEDE = 2\r\n"
					+ "    AND prsp.ID_SOGGETTO = ?\r\n"
					+ "    AND prsp.ID_PROGETTO =?";
			
			
			RowMapper<DatiDomandaEgVO> result = new RowMapper<DatiDomandaEgVO>() {
				
				@Override
				public DatiDomandaEgVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					DatiDomandaEgVO datiDomanda = new DatiDomandaEgVO();
				
						
						datiDomanda.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
						datiDomanda.setStatoDomanda(rs.getString("DESC_STATO_DOMANDA"));
						datiDomanda.setDataPresentazioneDomanda(rs.getString("DT_PRESENTAZIONE_DOMANDA"));
						datiDomanda.setPartitaIva(rs.getString("PARTITA_IVA"));
						datiDomanda.setDescIndirizzo(rs.getString("DESC_INDIRIZZO"));
						datiDomanda.setCap(rs.getString("CAP"));
						datiDomanda.setDescComune(rs.getString("DESC_COMUNE"));
						datiDomanda.setDescProvincia(rs.getString("DESC_PROVINCIA"));
						datiDomanda.setDescRegione(rs.getString("DESC_REGIONE"));
						datiDomanda.setDescNazione(rs.getString("DESC_NAZIONE"));
						datiDomanda.setTelefono(rs.getString("TELEFONO"));
						datiDomanda.setFax(rs.getString("FAX"));
						datiDomanda.setEmail(rs.getString("EMAIL"));
						datiDomanda.setPec(rs.getString("PEC"));
						datiDomanda.setIdComune(rs.getLong("ID_COMUNE"));
						datiDomanda.setIdProvincia(rs.getLong("ID_PROVINCIA"));
						datiDomanda.setIdRegione(rs.getLong("ID_REGIONE"));
						datiDomanda.setIdNazione(rs.getLong("ID_NAZIONE"));
						datiDomanda.setIdRecapiti(rs.getLong("ID_RECAPITI"));
						datiDomanda.setIdIndirizzo(rs.getLong("ID_INDIRIZZO"));
						datiDomanda.setFlagSedeAmm(rs.getString("FLAG_SEDE_AMMINISTRATIVA"));
					
					
					if(datiDomanda.getIdRegione()>0) {
						datiDomanda.setIdNazione((long)118);
						datiDomanda.setDescNazione("ITALIA");
					}
					return datiDomanda;
				}
			};
			
			datiDomanda = getJdbcTemplate().query(sql.toString(), result , new Object[] {
					idSoggetto, idProgetto
			});
			
			
			// recupero dei dati della sede amministrativa: 
			
			
		} else {
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT\r\n"
					+ "    ptd.NUMERO_DOMANDA,\r\n"
					+ "    ptd.DT_PRESENTAZIONE_DOMANDA,\r\n"
					+ "    pdsd.DESC_STATO_DOMANDA,\r\n"
					+ "    pts.PARTITA_IVA,\r\n"
					+ "    pti.DESC_INDIRIZZO, pti.ID_INDIRIZZO ,\r\n"
					+ "    pti.CAP,\r\n"
					+ "    pdc.DESC_COMUNE, pdc.ID_COMUNE ,\r\n"
					+ "    pdp.DESC_PROVINCIA, pdp.ID_PROVINCIA ,\r\n"
					+ "    pdr.DESC_REGIONE, pdr.ID_REGIONE ,\r\n"
					+ "    pdn.DESC_NAZIONE, pdn.ID_NAZIONE ,\r\n"
					+ "    ptr.EMAIL,ptr.ID_RECAPITI ,\r\n"
					+ "    ptr.FAX,\r\n"
					+ "    ptr.TELEFONO,\r\n"
					+ "    pteb.IBAN,pteb.ID_ESTREMI_BANCARI ,\r\n"
					+ "    --pdb.DESC_BANCA,\r\n"
					+ "    ptr.PEC,\r\n"
					+ "    CASE  WHEN (pteb.ID_AGENZIA IS NOT NULL ) THEN  pdb2.DESC_BANCA ELSE pdb.DESC_BANCA  END AS desc_banca ,\r\n"
			 		+ "    CASE  WHEN (pteb.ID_AGENZIA IS NOT NULL ) THEN  pdb2.ID_BANCA  ELSE pdb.ID_BANCA  END AS id_banca \r\n "
					+ "FROM\r\n"
					+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON ptd.ID_STATO_DOMANDA = pdsd.ID_STATO_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_T_SEDE pts ON prsds.ID_SEDE = pts.ID_SEDE\r\n"
					+ "    LEFT JOIN PBANDI_T_INDIRIZZO pti ON prsds.ID_INDIRIZZO = pti.ID_INDIRIZZO\r\n"
					+ "    LEFT JOIN PBANDI_D_COMUNE pdc ON pti.ID_COMUNE = pdc.ID_COMUNE\r\n"
					+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp ON pti.ID_PROVINCIA = pdp.ID_PROVINCIA\r\n"
					+ "    LEFT JOIN PBANDI_D_REGIONE pdr ON pdp.ID_REGIONE = pdr.ID_REGIONE\r\n"
					+ "    LEFT JOIN PBANDI_D_NAZIONE pdn ON pti.ID_NAZIONE = pdn.ID_NAZIONE\r\n"
					+ "    LEFT JOIN PBANDI_T_RECAPITI ptr ON prsds.ID_RECAPITI = ptr.ID_RECAPITI\r\n"
					+ "    LEFT JOIN PBANDI_T_ESTREMI_BANCARI pteb ON prsd.ID_ESTREMI_BANCARI = pteb.ID_ESTREMI_BANCARI\r\n"
					+ "    LEFT JOIN PBANDI_D_BANCA pdb ON pteb.ID_BANCA = pdb.ID_BANCA\r\n"
					+ "    LEFT JOIN pbandi_t_agenzia pta ON pta.ID_AGENZIA = pteb.ID_AGENZIA \r\n"
			 		+ "    LEFT JOIN PBANDI_D_BANCA pdb ON pteb.ID_BANCA = pdb.ID_BANCA\r\n"
			 		+ "    LEFT JOIN pbandi_d_banca pdb2 ON pdb2.ID_BANCA = pta.ID_BANCA \r\n"
					+ "WHERE\r\n"
					+ "    prsds.ID_TIPO_SEDE = 2\r\n"
					+"	AND prsd.ID_SOGGETTO = ?\r\n"  
					+"	AND ptd.NUMERO_DOMANDA = ?");
			
			RowMapper<DatiDomandaEgVO> result = new RowMapper<DatiDomandaEgVO>() {
				
				@Override
				public DatiDomandaEgVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					// TODO Auto-generated method stub
					DatiDomandaEgVO datiDomanda = new DatiDomandaEgVO();
					
						datiDomanda.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
						datiDomanda.setStatoDomanda(rs.getString("DESC_STATO_DOMANDA"));
						datiDomanda.setDataPresentazioneDomanda(rs.getString("DT_PRESENTAZIONE_DOMANDA"));
						datiDomanda.setPartitaIva(rs.getString("PARTITA_IVA"));
						datiDomanda.setDescIndirizzo(rs.getString("DESC_INDIRIZZO"));
						datiDomanda.setCap(rs.getString("CAP"));
						datiDomanda.setDescComune(rs.getString("DESC_COMUNE"));
						datiDomanda.setDescProvincia(rs.getString("DESC_PROVINCIA"));
						datiDomanda.setDescRegione(rs.getString("DESC_REGIONE"));
						datiDomanda.setDescNazione(rs.getString("DESC_NAZIONE"));
						datiDomanda.setTelefono(rs.getString("TELEFONO"));
						datiDomanda.setFax(rs.getString("FAX"));
						datiDomanda.setEmail(rs.getString("EMAIL"));
						datiDomanda.setPec(rs.getString("PEC"));
						datiDomanda.setIban(rs.getString("IBAN"));
						datiDomanda.setBanca(rs.getString("DESC_BANCA"));
						datiDomanda.setIdComune(rs.getLong("ID_COMUNE"));
						datiDomanda.setIdProvincia(rs.getLong("ID_PROVINCIA"));
						datiDomanda.setIdRegione(rs.getLong("ID_REGIONE"));
						datiDomanda.setIdNazione(rs.getLong("ID_NAZIONE"));
						datiDomanda.setIdRecapiti(rs.getLong("ID_RECAPITI"));
						datiDomanda.setIdIndirizzo(rs.getLong("ID_INDIRIZZO"));
						datiDomanda.setIdEstremiBancari(rs.getLong("ID_ESTREMI_BANCARI"));
					
					if(datiDomanda.getIdRegione()>0) {
						datiDomanda.setIdNazione((long)118);
						datiDomanda.setDescNazione("ITALIA");
					}
					return datiDomanda;
				}
			};
			
			datiDomanda = getJdbcTemplate().query(sql.toString(),result,  new Object[] {
					idSoggetto, idDomanda
			});
		}
			

	}catch (IncorrectResultSizeDataAccessException e) {
		LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read DatiDomandaEgVO", e);

	} catch (Exception e) {
		LOG.error(prf + "Exception while trying to read DatiDomandaEgVO", e);
		throw new DaoException("DaoException while trying to read DatiDomandaEgVO", e);
	}
	finally {
		LOG.info(prf + " END");
	}

	return datiDomanda;
  }


}
