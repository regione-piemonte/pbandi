/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.ammvoservrest.dto.DebitoResiduo;
import it.csi.pbandi.pbgestfinbo.business.manager.ActaManager;
import it.csi.pbandi.pbgestfinbo.business.manager.DocumentoManager;
import it.csi.pbandi.pbgestfinbo.business.service.impl.AmministrativoContabileServiceImpl;
import it.csi.pbandi.pbgestfinbo.dto.DatiXActaDTO;
import it.csi.pbandi.pbgestfinbo.dto.DocumentoIndexVO;
import it.csi.pbandi.pbgestfinbo.dto.EsitoDTO;
import it.csi.pbandi.pbgestfinbo.dto.GestioneAllegatiVO;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileDTO;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileUtil;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.RicercaGaranzieDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.DatiXActaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.*;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

public class RicercaGaranzieDAOImpl extends JdbcDaoSupport implements RicercaGaranzieDAO {
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public RicercaGaranzieDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	} 
	
	@Autowired
	DocumentoManager documentoManager;
	
	@Autowired
	private AmministrativoContabileServiceImpl amministrativoContabileServiceImpl;
	
	@Autowired
	ActaManager actaManager;
	
	public RicercaGaranzieDAOImpl() {
	}
	
	@Autowired
	NeofluxBusinessImpl neofluxBusinessImpl;
	
	public List<GaranziaVO> ricercaGaranzie(String descrizioneBando, String codiceProgetto, String codiceFiscale, String nag, String partitaIva, String denominazioneCognomeNome, String statoEscussione, String denominazioneBanca,  HttpServletRequest req) {
		String prf = "[RicercaGaranzieDAOImpl::getGaranzie]";
		LOG.info(prf + " BEGIN");
		List<GaranziaVO> lista = null;
		UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		try {
			String newDescBan = descrizioneBando.replaceAll("'", "''");
			String newDenom= denominazioneCognomeNome.replaceAll("'", "''");
			String newBanca= denominazioneBanca.replaceAll("'", "''");


			StringBuilder query = new StringBuilder();
			query.append("WITH denom AS (\r\n"
					+ "    SELECT pteg.ID_SOGGETTO,\r\n"
					+ "    pteg.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione,\r\n"
					+ "    prsp.ID_ENTE_GIURIDICO AS id_ente_giu,\r\n"
					+ "    NULL AS id_pers_fis\r\n"
					+ "    FROM PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "        LEFT JOIN pbandi_t_ente_giuridico pteg ON prsp.ID_ENTE_GIURIDICO = pteg.ID_ENTE_GIURIDICO\r\n"
					+ "    WHERE prsp.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    UNION\r\n"
					+ "    SELECT ptpf.ID_SOGGETTO,\r\n"
					+ "        CONCAT(ptpf.COGNOME, CONCAT(' ', ptpf.NOME)) AS denominazione,\r\n"
					+ "        NULL AS id_ente_giu,\r\n"
					+ "        prsp.ID_PERSONA_FISICA AS id_pers_fis\r\n"
					+ "    FROM pbandi_t_persona_fisica ptpf\r\n"
					+ "        LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PERSONA_FISICA = ptpf.ID_PERSONA_FISICA\r\n"
					+ "    WHERE prsp.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ ")\r\n"
					+ "SELECT DISTINCT ptb.ID_BANDO,\r\n"
					+ "    ptb.TITOLO_BANDO,\r\n"
					+ "    ptp.ID_PROGETTO,\r\n"
					+ "    ptp.CODICE_VISUALIZZATO,\r\n"
					+ "    prsp.ID_SOGGETTO,\r\n"
					+ "    denom.DENOMINAZIONE,\r\n"
					+ "    pte.id_escussione,\r\n"
					+ "    pte.DT_RIC_RICH_ESCUSSIONE,\r\n"
					+ "    pte.ID_TIPO_ESCUSSIONE,\r\n"
					+ "    pdte.DESC_TIPO_ESCUSSIONE,\r\n"
					+ "    pte.ID_STATO_ESCUSSIONE,\r\n"
					+ "    pdse.DESC_STATO_ESCUSSIONE,\r\n"
					+ "    pte.DT_INIZIO_VALIDITA,\r\n"
					+ "    pte.IMP_RICHIESTO,\r\n"
					+ "    pte.IMP_APPROVATO,\r\n"
					+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
					+ "    pts.ndg,\r\n"
					+ "    pte.ID_ESCUSSIONE,\r\n"
					+ "    pte.ID_TIPO_ESCUSSIONE,\r\n"
					+ "    pts2.PARTITA_IVA,\r\n"
					+ "    PTP.DT_CONCESSIONE,\r\n"
					+ "    ptma.ID_MODALITA_AGEVOLAZIONE,\r\n"
					+ "    ptma.ID_MODALITA_AGEVOLAZIONE_RIF,\r\n"
					+ "    CASE WHEN (pteb.ID_AGENZIA IS NOT NULL) THEN pdb2.DESC_BANCA\r\n"
					+ "        ELSE pdb.DESC_BANCA END AS desc_banca,\r\n"
					+ "    CASE WHEN (pteb.ID_AGENZIA IS NOT NULL) THEN pdb2.COD_BANCA\r\n"
					+ "        ELSE pdb.COD_BANCA END AS COD_BANCA,\r\n"
					+ "    CASE WHEN (pteb.ID_AGENZIA IS NOT NULL) THEN pdb2.ID_BANCA\r\n"
					+ "        ELSE pdb.ID_BANCA END AS id_banca,\r\n"
					+ "    pte.DT_INSERIMENTO\r\n"
					+ "FROM PBANDI_T_PROGETTO ptp\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptd.ID_DOMANDA = ptce.ID_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema ON ptce.ID_CONTO_ECONOMICO = prcema.ID_CONTO_ECONOMICO\r\n"
					+ "    LEFT JOIN PBANDI_D_MODALITA_AGEVOLAZIONE ptma ON ptma.ID_MODALITA_AGEVOLAZIONE = prcema.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_CONTO_ECONOMICO pdsce ON pdsce.ID_STATO_CONTO_ECONOMICO = ptce.ID_STATO_CONTO_ECONOMICO\r\n"
					+ "    LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "    LEFT JOIN PBANDI_T_BANDO ptb ON ptb.ID_BANDO = prbli.ID_BANDO\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO --LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
					+ "    LEFT JOIN PBANDI_T_ESCUSSIONE pte ON ptp.ID_PROGETTO = pte.ID_PROGETTO\r\n"
					+ "    LEFT JOIN PBANDI_D_TIPO_ESCUSSIONE pdte ON pdte.ID_TIPO_ESCUSSIONE = pte.ID_TIPO_ESCUSSIONE\r\n"
					+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsp.PROGR_SOGGETTO_PROGETTO = prsps.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "    LEFT JOIN PBANDI_T_SEDE pts2 ON pts2.ID_SEDE = prsps.ID_SEDE\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_ESCUSSIONE pdse ON pdse.ID_STATO_ESCUSSIONE = pte.ID_STATO_ESCUSSIONE\r\n"
					+ "    LEFT JOIN PBANDI_T_ESTREMI_BANCARI pteb ON prsp.ID_ESTREMI_BANCARI = pteb.ID_ESTREMI_BANCARI\r\n"
					+ "    LEFT JOIN pbandi_t_agenzia pta ON pta.ID_AGENZIA = pteb.ID_AGENZIA\r\n"
					+ "    LEFT JOIN PBANDI_D_BANCA pdb ON pteb.ID_BANCA = pdb.ID_BANCA\r\n"
					+ "    LEFT JOIN pbandi_d_banca pdb2 ON pdb2.ID_BANCA = pta.ID_BANCA\r\n"
					+ "    LEFT JOIN denom ON denom.id_soggetto = prsp.ID_SOGGETTO\r\n"
					+ "    AND ( ( pts.ID_TIPO_SOGGETTO = 2 AND denom.id_ente_giu = prsp.ID_ENTE_GIURIDICO\r\n"
					+ "        ) OR (  pts.ID_TIPO_SOGGETTO = 1 AND denom.id_pers_fis = prsp.ID_PERSONA_FISICA\r\n"
					+ "        ) )\r\n"
					+ "WHERE prsp.ID_TIPO_ANAGRAFICA = '1' AND prsp.ID_TIPO_BENEFICIARIO <> '4'\r\n"
					+ "    AND ptma.ID_MODALITA_AGEVOLAZIONE_RIF = 10 AND pte.DT_FINE_VALIDITA IS NULL AND ptce.DT_FINE_VALIDITA IS NULL \r\n");
			
			if (!"".equals(descrizioneBando)) {query.append("AND UPPER(ptb.TITOLO_BANDO) = UPPER('" + newDescBan + "') \r\n");}
			
			if (!"".equals(codiceProgetto)) {query.append("AND UPPER(ptp.CODICE_VISUALIZZATO) = UPPER('" + codiceProgetto + "') \r\n");}
			
			if (!"".equals(codiceFiscale)) {query.append("AND UPPER(pts.CODICE_FISCALE_SOGGETTO) = UPPER('" + codiceFiscale + "') \r\n");}
			
			if (!"".equals(nag)) {query.append("AND UPPER(pts.ndg) = UPPER('" + nag + "') \r\n");}
			
			if (!"".equals(partitaIva)) {query.append("AND UPPER(pts2.PARTITA_IVA) = UPPER('" + partitaIva + "') \r\n");}
			
			if (!"".equals(denominazioneCognomeNome)) {query.append("AND UPPER(denom.DENOMINAZIONE) = UPPER('" + newDenom + "') \r\n");}
			
			if (!"".equals(statoEscussione)) {
				if("-1".equals(statoEscussione)) { // Cerco per domande che non hanno escussione
					query.append("AND pte.ID_ESCUSSIONE IS NULL \r\n");
				} else {
					query.append("AND UPPER(pdse.DESC_STATO_ESCUSSIONE) = UPPER('" + statoEscussione + "') \r\n");
				}
			}
			
			if (!"".equals(denominazioneBanca)) {query.append("AND UPPER(pdb.DESC_BANCA) = UPPER('" + newBanca + "') \r\n");}
			
			query.append("AND rownum < 200\r\n"
					+ "ORDER BY pte.DT_INSERIMENTO DESC");
			
			RowMapper<GaranziaVO> rw = new RowMapper<GaranziaVO>() {
				
				@Override
				public GaranziaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					GaranziaVO gar = new GaranziaVO();
					gar.setIdProgetto(rs.getLong("ID_PROGETTO"));
					gar.setIdBando(rs.getLong("ID_BANDO"));
					gar.setIdModalitaAgevolazione(rs.getLong("ID_MODALITA_AGEVOLAZIONE"));
					gar.setIdModalitaAgevolazioneRif(rs.getLong("ID_MODALITA_AGEVOLAZIONE_RIF"));
					gar.setDescrizioneBando(rs.getString("TITOLO_BANDO"));
					gar.setCodiceProgetto(rs.getString("CODICE_VISUALIZZATO"));
					gar.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
					gar.setNag(rs.getString("ID_SOGGETTO"));
					gar.setNdg(rs.getString("ndg"));
					gar.setPartitaIva(rs.getString("PARTITA_IVA"));
					gar.setDenominazioneCognomeNome(rs.getString("DENOMINAZIONE"));
					gar.setStatoEscussione(rs.getString("DESC_STATO_ESCUSSIONE"));
					gar.setDenominazioneBanca(rs.getString("DESC_BANCA"));
					gar.setDataRicevimentoEscussione(rs.getDate("DT_RIC_RICH_ESCUSSIONE"));
					gar.setTipoEscussione(rs.getString("DESC_TIPO_ESCUSSIONE"));
					gar.setDataStato(rs.getDate("DT_INIZIO_VALIDITA"));
					gar.setImportoRichiesto(rs.getDouble("IMP_RICHIESTO"));
					gar.setImportoApprovato(rs.getDouble("IMP_APPROVATO"));
					gar.setIdEscussione(rs.getBigDecimal("ID_ESCUSSIONE"));
					
					try {
						//DEBUG
						//LOG.info(prf + "gar.getCodiceProgetto() " + gar.getCodiceProgetto() + " - len: " + gar.getCodiceProgetto().length());
						//LOG.info(prf + "gar.getCodiceProgetto().charAt(10) " + gar.getCodiceProgetto().charAt(10));
						//LOG.info(prf + "gar.getCodiceProgetto().substring(11, 13) " + gar.getCodiceProgetto().substring(11, 13));
						//LOG.info(prf + "gar.getCodiceProgetto().substring(11, 13).equals('00') " + gar.getCodiceProgetto().substring(11, 13).equals("00"));

						// Controllo (carco di capire) se si tratta di un bando misto
						if (gar.getCodiceProgetto().length() == 16 && (gar.getCodiceProgetto().charAt(10) == 'F' || gar.getCodiceProgetto().charAt(10) == 'C' || gar.getCodiceProgetto().charAt(10) == 'G') && gar.getCodiceProgetto().substring(11, 13).equals("00")) {
							gar.setIsFondoMisto(true);
						} else {
							gar.setIsFondoMisto(false);
						}

					} catch (Exception e) {
						gar.setIsFondoMisto(false);
					}
					
					return gar;
				}
				
			};
			
			//TODO: Implementare un sistema simile a getProcedimentoRevoca su ProcedimentoRevocaDAOImpl
			//		o simile a getIterAutorizzativi su IterAutorizzativiDAOImpl
			
			lista = getJdbcTemplate().query(query.toString(), rw);
			
			//  recupero i dettagli delle garanzie; 
			for (GaranziaVO garanziaVO : lista) {
				garanziaVO.setListaDettagli(getDettagliGaranza(garanziaVO.getIdProgetto().toString(), garanziaVO.getIdEscussione()));
				if(garanziaVO.getListaDettagli()!=null && garanziaVO.getListaDettagli().size()>0) {
					for(DettaglioGaranziaVO dettaglio: garanziaVO.getListaDettagli()) {		// "10" corrisponde alla modalita di agevolazione rif delle garanzie					
						DebitoResiduo deb = amministrativoContabileServiceImpl.callToDebitoResiduo(garanziaVO.getIdProgetto().intValue(), garanziaVO.getIdBando().intValue(), garanziaVO.getIdModalitaAgevolazione().intValue(), garanziaVO.getIdModalitaAgevolazioneRif().intValue(), userInfoSec.getIdUtente());
						if(deb!=null) {
							dettaglio.setImportoDebitoResiduo(new BigDecimal(deb.getDebitoResiduo()));
						}
					}
				}
			}
			
			
		} catch (RecordNotFoundException e)
		{
			throw e;
		}
		
		catch (Exception e)
		{
			LOG.error(prf + "Exception while trying to read GaranziaVO", e);
			throw new ErroreGestitoException("DaoException while trying to read GaranziaVO", e);
		}
		finally
		{
			LOG.info(prf + " END");
		}
		
		return lista;
	}

	// questo metodo restituisce i dettagli della garanzia
	/* MA PERCHE' MAI DOVETE FARE UNA COSA DEL GENERE */
	private List<DettaglioGaranziaVO> getDettagliGaranza(String idProgetto, BigDecimal idEscussione) {
		
		List<DettaglioGaranziaVO> dettagli= new ArrayList<DettaglioGaranziaVO>();
		
		List<Integer> ras = new ArrayList<>();
		
		try {
			
			DettaglioGaranziaVO det = new DettaglioGaranziaVO();
			
			/* MAI STATO SETTATO ID PROGETTO, COME PENSATE CHE AVREBBE MAI FUNZIONATO SE SEMPRE NULLO? */
			det.setIdProgetto(new BigDecimal(idProgetto));
			
			det.setTipoAgevolazione(getTipoAgevolazione(idProgetto));
			det.setImportoAmmesso(BigDecimal.valueOf(getImportoAmmesso(idProgetto)));
			det.setTotaleFondo(BigDecimal.valueOf(getTotaleFondo(idProgetto)));
			det.setTotaleBanca(BigDecimal.valueOf(getTotaleBanca(idProgetto)));
			det.setDtConcessione(getDtConcessione(idProgetto));
			det.setDtErogazioneFinanziamento(getDtErogazione(idProgetto));
			det.setImportoEscusso(getImportoEscusso(idProgetto, idEscussione)); // TODO: entrare per idEscussione
			det.setStatoCredito(getStatoDelCredito(idProgetto));
			ras=getRevocaAzioniSaldo(idProgetto);
			
			if(ras.get(0)>0) {
				det.setRevocaBancaria("Si");
			}else {
				det.setRevocaBancaria("No");
			}
			
			if(ras.get(1)>0) {
				det.setAzioniRecuperoBanca("Si");
			}else {
				det.setAzioniRecuperoBanca("No");
			}
			
			if(ras.get(2)>0) {
				det.setSaldoEStralcio("Si");
			}else {
				det.setSaldoEStralcio("No");
			}
			dettagli.add(det); 
			
			
		} catch (Exception e) {
			LOG.error(e);
		}
		
		return dettagli;
	}

	public List<String> getSuggestions(String value, String id) throws Exception {
		String prf = "[RicercaGaranzieDAOImpl::getSuggestion]";
		LOG.info(prf + " BEGIN");
		List<String> lista = null;
		
		try {
					
			String query = "";
			
			switch(id) {
				case "1": // Descrizione Bando
					query = "SELECT DISTINCT\r\n"
							+ "	ptb.TITOLO_BANDO\r\n"
							+ "FROM\r\n"
							+ "	PBANDI_T_PROGETTO ptp\r\n"
							+ "JOIN\r\n"
							+ "	PBANDI_T_DOMANDA ptd\r\n"
							+ "ON\r\n"
							+ "	ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
							+ "JOIN\r\n"
							+ "	PBANDI_R_BANDO_LINEA_INTERVENT prbli\r\n"
							+ "ON\r\n"
							+ "	prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
							+ "JOIN\r\n"
							+ "	PBANDI_T_BANDO ptb\r\n"
							+ "ON\r\n"
							+ "	ptb.ID_BANDO = prbli.ID_BANDO\r\n"
							+ "JOIN\r\n"
							+ "	PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
							+ "ON\r\n"
							+ "	ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
							+ "WHERE\r\n"
							+ "	prsp.ID_TIPO_ANAGRAFICA = '1' AND prsp.ID_TIPO_BENEFICIARIO <> '4' AND UPPER(ptb.TITOLO_BANDO) LIKE UPPER('%' || ? || '%') AND ROWNUM <= 100\r\n"
							+ "ORDER BY\r\n"
							+ "	ptb.TITOLO_BANDO ASC";
					break;
					
				case "2": // Codice Progetto
					query = "SELECT DISTINCT\r\n"
							+ "	ptp.CODICE_VISUALIZZATO\r\n"
							+ "FROM\r\n"
							+ "	PBANDI_T_PROGETTO ptp\r\n"
							+ "JOIN\r\n"
							+ "	PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
							+ "ON\r\n"
							+ "	ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
							+ "WHERE\r\n"
							+ "	prsp.ID_TIPO_ANAGRAFICA = '1' AND prsp.ID_TIPO_BENEFICIARIO <> '4' AND UPPER(ptp.CODICE_VISUALIZZATO) LIKE UPPER('%' || ? || '%') AND ROWNUM <= 100\r\n"
							+ "ORDER BY\r\n"
							+ "	ptp.CODICE_VISUALIZZATO ASC";
					break;
					
				case "3": // Codice Fiscale
					query = "SELECT DISTINCT\r\n"
							+ "	pts.CODICE_FISCALE_SOGGETTO\r\n"
							+ "FROM\r\n"
							+ "	PBANDI_T_SOGGETTO pts\r\n"
							+ "JOIN\r\n"
							+ "	PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
							+ "ON\r\n"
							+ "	pts.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
							+ "WHERE\r\n"
							+ "	prsp.ID_TIPO_ANAGRAFICA = '1' AND prsp.ID_TIPO_BENEFICIARIO <> '4' AND UPPER(pts.CODICE_FISCALE_SOGGETTO) LIKE UPPER(? || '%') AND ROWNUM <= 100\r\n"
							+ "ORDER BY\r\n"
							+ "	pts.CODICE_FISCALE_SOGGETTO ASC";
					break;
					
				case "4": // NDG
					query = "SELECT DISTINCT\r\n"
							+ "	pts.ndg \r\n"
							+ "FROM\r\n"
							+ "	PBANDI_R_SOGGETTO_PROGETTO prsp left join pbandi_t_soggetto pts on pts.id_soggetto = prsp.id_soggetto\r\n"
							+ "WHERE\r\n"
							+ "	prsp.ID_TIPO_ANAGRAFICA = '1' AND prsp.ID_TIPO_BENEFICIARIO <> '4' AND UPPER(pts.ndg) LIKE UPPER('%' || ? || '%') AND ROWNUM <= 100\r\n"
							+ "ORDER BY\r\n"
							+ "	pts.ndg ASC";
					break;
					
				case "5": // Partita Iva
					query = "SELECT DISTINCT\r\n"
							+ "	pts2.PARTITA_IVA\r\n"
							+ "FROM\r\n"
							+ "	PBANDI_T_PROGETTO ptp\r\n"
							+ "JOIN\r\n"
							+ "	PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
							+ "ON\r\n"
							+ "	ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
							+ "JOIN\r\n"
							+ "	PBANDI_R_SOGG_PROGETTO_SEDE prsps\r\n"
							+ "ON\r\n"
							+ "	prsp.PROGR_SOGGETTO_PROGETTO = prsps.PROGR_SOGGETTO_PROGETTO\r\n"
							+ "JOIN\r\n"
							+ "	PBANDI_T_SEDE pts2\r\n"
							+ "ON\r\n"
							+ "	pts2.ID_SEDE = prsps.ID_SEDE\r\n"
							+ "WHERE\r\n"
							+ "	prsp.ID_TIPO_ANAGRAFICA = '1' AND prsp.ID_TIPO_BENEFICIARIO <> '4' AND UPPER(pts2.PARTITA_IVA) LIKE UPPER(? || '%') AND ROWNUM <= 100\r\n"
							+ "ORDER BY\r\n"
							+ "	pts2.PARTITA_IVA ASC";
					break;
					
				case "6": // Denominazione
					/* OLD - cercava solo per ente giuridico
					 * query.append("SELECT DISTINCT\r\n"
							+ "	pteg.DENOMINAZIONE_ENTE_GIURIDICO\r\n"
							+ "FROM\r\n"
							+ "	PBANDI_T_PROGETTO ptp\r\n"
							+ "JOIN\r\n"
							+ "	PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
							+ "ON\r\n"
							+ "	ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
							+ "JOIN\r\n"
							+ "	PBANDI_T_ENTE_GIURIDICO pteg\r\n"
							+ "ON\r\n"
							+ "	pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
							+ "WHERE\r\n"
							+ "	prsp.ID_TIPO_ANAGRAFICA = '1' AND prsp.ID_TIPO_BENEFICIARIO <> '4' AND UPPER(pteg.DENOMINAZIONE_ENTE_GIURIDICO) LIKE UPPER('%" + value + "%') AND ROWNUM <= 100\r\n"
							+ "ORDER BY\r\n"
							+ "	pteg.DENOMINAZIONE_ENTE_GIURIDICO ASC");*/

					query = "WITH selezione AS (\r\n"
							+ "	SELECT DISTINCT ptpf.COGNOME || ' ' || ptpf.NOME AS denominazione, prsp.ID_SOGGETTO\r\n"
							+ "	FROM pbandi_r_soggetto_progetto prsp\r\n"
							+ "		LEFT JOIN pbandi_t_persona_fisica ptpf ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\r\n"
							+ "	WHERE prsp.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_TIPO_ANAGRAFICA = 1 AND ptpf.COGNOME IS NOT NULL\r\n"
							+ "	--ORDER BY denominazione ASC \r\n"
							+ "	UNION SELECT DISTINCT pteg.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione, prsp.ID_SOGGETTO\r\n"
							+ "	FROM pbandi_r_soggetto_progetto prsp\r\n"
							+ "		LEFT JOIN pbandi_t_ente_giuridico pteg ON pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO AND pteg.DENOMINAZIONE_ENTE_GIURIDICO IS NOT NULL \r\n"
							+ "	WHERE prsp.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_TIPO_ANAGRAFICA = 1 \r\n"
							+ "	ORDER BY denominazione ASC )\r\n"
							+ "SELECT selezione.denominazione\r\n"
							+ "FROM selezione\r\n"
							+ "WHERE UPPER(denominazione) LIKE UPPER('%' || ? || '%')\r\n"
							+ "AND ROWNUM <= 100";
					break;
					
				case "7": // Denom Banca
					query = "WITH selection AS (\r\n"
							+ "SELECT DISTINCT pdb.DESC_BANCA\r\n"
							+ "FROM PBANDI_D_BANCA pdb\r\n"
							+ "WHERE UPPER(pdb.DESC_BANCA) LIKE UPPER('%' || ? || '%')\r\n"
							+ "ORDER BY pdb.DESC_BANCA ASC)\r\n"
							+ "SELECT * FROM selection\r\n"
							+ "WHERE ROWNUM <= 100";
					break;
					
				default:				
					LOG.error("There was an error with ids");
			}
			
			lista = getJdbcTemplate().queryForList(query.toString(), String.class, new Object [] { value });
			
		} catch (RecordNotFoundException e)
		{
			throw e;
		}
		
		catch (Exception e)
		{
			LOG.error(prf + "Exception while trying to read GaranziaVO - suggestion", e);
			throw new ErroreGestitoException("DaoException while trying to read GaranziaVO - suggestion", e);
		}
		finally
		{
			LOG.info(prf + " END");
		}
			
		return lista;
	}
	
	@Override
	public List<String> initListaStatiEscussione() {
		String prf = "[RicercaGaranzieDAOImpl::getListaStatiEscussione]";
		LOG.info(prf + " BEGIN");
		List<String> stati = new ArrayList<String>();
		
		try {
			String query = "SELECT DISTINCT pdse.DESC_STATO_ESCUSSIONE\r\n"
					+ "FROM PBANDI_D_STATO_ESCUSSIONE pdse\r\n"
					+ "ORDER BY pdse.DESC_STATO_ESCUSSIONE";
			
			stati = getJdbcTemplate().queryForList(query, String.class);
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_D_STATO_ESCUSSIONE", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return stati;
	}
	
	@Override
	public List<SuggestBancheVO> getBancaSuggestion(String value) throws Exception {
		String prf = "[RicercaGaranzieDAOImpl::getBancaSuggestion]";
		LOG.info(prf + " BEGIN");
			
		List<SuggestBancheVO> banche = null;
		
		try {
			StringBuilder queryBanche = new StringBuilder();
				
			queryBanche.append("SELECT DISTINCT pteb.ID_BANCA , pteb.IBAN , pdb.DESC_BANCA \r\n"
					+ "FROM  PBANDI_T_ESTREMI_BANCARI pteb\r\n"
					+ "LEFT JOIN PBANDI_D_BANCA pdb ON pdb.ID_BANCA = pteb.ID_BANCA\r\n"
					+ "WHERE pteb.ID_BANCA IS NOT NULL \r\n"
					+ "AND UPPER(pteb.IBAN )  LIKE UPPER('%" + value +"%') \r\n"
					+ "AND rownum <=100\r\n"
					+ "ORDER BY pteb.IBAN ASC");
			
			RowMapper<SuggestBancheVO> rw = new RowMapper<SuggestBancheVO>() {
				@Override
				public SuggestBancheVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					SuggestBancheVO gar = new SuggestBancheVO();
					gar.setIdBanca(rs.getLong("ID_BANCA"));
					gar.setIban(rs.getString("IBAN"));
					gar.setBanca(rs.getString("DESC_BANCA"));
					return gar;
				}};
			
			banche = getJdbcTemplate().query(queryBanche.toString(), rw);
		}
		
		catch (RecordNotFoundException e)
		{
			throw e;
		}
		
		catch (Exception e)
		{
			LOG.error(prf + "Exception while trying to read RicercaGaranzieDAOImpl - ListBanche", e);
			throw new ErroreGestitoException("DaoException while trying to read RicercaGaranzieDAOImpl - ListBanche", e);
		}
		finally
		{
			LOG.info(prf + " END");
		}
			
		return banche;
	}

	
	

	@Override
	public List<GaranziaVO> getListaTipoEscussione() {
		String prf = "[RicercaGaranzieDAOImpl::getListaTipoEscussione]";
		LOG.info(prf + " BEGIN");
		List<GaranziaVO> lista = new ArrayList<GaranziaVO>();
		
		try {
			String query = "SELECT DISTINCT\r\n"
					+ "	pdte.DESC_TIPO_ESCUSSIONE \r\n"
					+ "FROM\r\n"
					+ "	PBANDI_D_TIPO_ESCUSSIONE pdte \r\n"
					+ "ORDER BY\r\n"
					+ "	pdte.DESC_TIPO_ESCUSSIONE";
			
			RowMapper<GaranziaVO> rw = new RowMapper<GaranziaVO>() {
				
				@Override
				public GaranziaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					GaranziaVO gar = new GaranziaVO();
					gar.setTipoEscussione(rs.getString("DESC_TIPO_ESCUSSIONE"));
					return gar;
				}
				
			};
			
			lista = getJdbcTemplate().query(query, rw);
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_D_STATO_ESCUSSIONE", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return lista;
	}
	
	@Override
	public InitDialogEscussioneVO initDialogEscussione(int idStatoEscussione) {
		String prf = "[RicercaGaranzieDAOImpl::initDialogEscussione]";
		LOG.info(prf + " BEGIN");
		
		InitDialogEscussioneVO cont = new InitDialogEscussioneVO();
		//List<GaranziaVO> lista = new ArrayList<GaranziaVO>();
		
		try {
			String query = "SELECT pdte.DESC_TIPO_ESCUSSIONE \r\n"
					+ "FROM PBANDI_D_TIPO_ESCUSSIONE pdte\r\n"
					+ "WHERE pdte.ID_TIPO_ESCUSSIONE <> 4\r\n"
					+ "ORDER BY pdte.ID_TIPO_ESCUSSIONE";
			
			cont.setTipiEscussione(getJdbcTemplate().queryForList(query, String.class));
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_D_STATO_ESCUSSIONE", e);
		}
		
		
		try {
			StringBuilder query = new StringBuilder();
			
			query.append("SELECT pdse.DESC_STATO_ESCUSSIONE\r\n"
					+ "FROM PBANDI_D_STATO_ESCUSSIONE pdse\r\n");
			
			switch (idStatoEscussione) {
			case 0:
				query.append("WHERE ID_STATO_ESCUSSIONE = 1");
				break;
				
			case 1:
				query.append("WHERE ID_STATO_ESCUSSIONE = 2");
				break;
				
			case 2:
				query.append("WHERE ID_STATO_ESCUSSIONE = 3 OR ID_STATO_ESCUSSIONE = 5 OR ID_STATO_ESCUSSIONE = 6");
				break;
				
			case 3:
				query.append("WHERE ID_STATO_ESCUSSIONE = 4 OR ID_STATO_ESCUSSIONE = 5 OR ID_STATO_ESCUSSIONE = 6");
				break;	
				
			case 4:
				query.append("WHERE ID_STATO_ESCUSSIONE = 5 OR ID_STATO_ESCUSSIONE = 6");
				break;		
				
			case 5:
				// Tutti gli stati
				break;
				
			case 6:
				// Tutti gli stati
				break;	
				
			default:
				query.append("WHERE ID_STATO_ESCUSSIONE = 2");
				break;
			}
			
			query.append("ORDER BY pdse.ID_STATO_ESCUSSIONE");
			
			cont.setStatiEscussione(getJdbcTemplate().queryForList(query.toString(), String.class));
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_D_STATO_ESCUSSIONE", e);
		}
		
		
		LOG.info(prf + " END");
		return cont;
	}

	
	//INIZIO VISUALIZZA DETTAGLIO GARANZIE
	
	@Override
	public String getTipoAgevolazione(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getTipoAgevolazione]";
		LOG.info(prf + " BEGIN");
		String dati= null;
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql =" SELECT                                                              \n"
					+ "  DESC_BREVE_MODALITA_AGEVOLAZ                                         \n"
					+ " FROM                                                                  \n"
					+ "   PBANDI_T_PROGETTO ptp,                                              \n"
					+ "   PBANDI_T_CONTO_ECONOMICO ptce,                                      \n"
					+ "   PBANDI_D_STATO_CONTO_ECONOMICO  pdsce,                              \n"
					+ "   PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema,                              \n"
					+ "   PBANDI_D_MODALITA_AGEVOLAZIONE pdma                                 \n"
					+ " WHERE                                                                 \n"
					+ "  ptp.id_progetto=:idProgetto AND                                      \n"
					+ "  ptp.ID_DOMANDA= ptce.ID_DOMANDA AND                                  \n"
					+ "  ptce.DT_FINE_VALIDITA  is null AND                                   \n"
					+ "  ptce.ID_STATO_CONTO_ECONOMICO = pdsce.ID_STATO_CONTO_ECONOMICO  AND  \n"
					+ "  pdsce.ID_TIPOLOGIA_CONTO_ECONOMICO =2 AND                            \n"
					+ "  ptce.ID_CONTO_ECONOMICO  = prcema.ID_CONTO_ECONOMICO  AND            \n"
					+ "  prcema.ID_MODALITA_AGEVOLAZIONE in( 1, 5, 10) AND                    \n"
					+ "  prcema.ID_MODALITA_AGEVOLAZIONE =pdma.ID_MODALITA_AGEVOLAZIONE       \n";
			LOG.debug(sql); 
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<String>() {
				
				@Override
				public String extractData(ResultSet rs) throws SQLException {
					String elencoDati = null;
					
					while(rs.next()) {
						elencoDati=rs.getString("DESC_BREVE_MODALITA_AGEVOLAZ");
					}
					
					return elencoDati;
				}
				
			});
		
		}catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		
		return dati;
	}
	
	@Override
	public int getImportoAmmesso(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getImportoAmmesso]";
		LOG.info(prf + " BEGIN");
		int dati= 0; 
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			/* OLD
			String sql =" SELECT                                                              \n"
					+ "  (sum(Imp_quota_sogg_finanziatore) + sum(Importo_finanziamento_banca)) importo  \n"
					+ " FROM                                                                  \n"
					+ "   PBANDI_R_PROG_SOGG_FINANZIAT prpsf,                                 \n"
					+ "   PBANDI_D_SOGGETTO_FINANZIATORE pdsf,                                \n"
					+ "   PBANDI_D_STATO_CONTO_ECONOMICO pdsce,                               \n"
					+ "   PBANDI_T_CONTO_ECONOMICO ptce                                       \n"
					+ " WHERE                                                                 \n"
					+ "  prpsf.ID_SOGGETTO_FINANZIATORE = pdsf.ID_SOGGETTO_FINANZIATORE AND   \n"
					+ "  pdsf.FLAG_AGEVOLATO ='S' AND                                         \n"
					+ "  prpsf.id_progetto= :idProggetto AND                                  \n"
					+ "  pdsce.ID_STATO_CONTO_ECONOMICO = ptce.ID_STATO_CONTO_ECONOMICO  and  \n"
					+ "  pdsce.ID_TIPOLOGIA_CONTO_ECONOMICO=2 AND                             \n"
					+ "  ptce.DT_FINE_VALIDITA IS  null AND                                   \n"
					+ "  ptce.ID_DOMANDA = prpsf.id_progetto                                  \n"; // idDomanda invece che idProgetto
			LOG.debug(sql);*/
			String sql = "SELECT conecomodag.IMPORTO_AMMESSO_FINPIS\r\n"
					+ "FROM PBANDI_T_PROGETTO pro\r\n"
					+ "LEFT JOIN PBANDI_T_CONTO_ECONOMICO coneco ON pro.ID_DOMANDA = coneco.ID_DOMANDA AND coneco.DT_FINE_VALIDITA IS NULL\r\n"
					+ "LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV conecomodag ON coneco.ID_CONTO_ECONOMICO = conecomodag.ID_CONTO_ECONOMICO\r\n"
					+ "WHERE conecomodag.ID_MODALITA_AGEVOLAZIONE = 10 AND pro.ID_PROGETTO = ?";
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<Integer>() {
				
				@Override
				public Integer extractData(ResultSet rs) throws SQLException {
					int elencoDati = 0;
					
					while(rs.next()) {	
						elencoDati=rs.getInt("IMPORTO_AMMESSO_FINPIS");
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		LOG.error(prf + "Record not find exception = " + e.getMessage());
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}
	
	@Override
	public int getTotaleFondo(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getTotaleFondo]";
		LOG.info(prf + " BEGIN");
		int dati= 0;
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql =" SELECT                                                              \n"
					+ "  sum(prpsf.IMP_QUOTA_SOGG_FINANZIATORE) importo                       \n"
					+ " FROM                                                                  \n"
					+ "    PBANDI_R_PROG_SOGG_FINANZIAT prpsf,                                \n"
					+ "    PBANDI_D_SOGGETTO_FINANZIATORE pdsf                                \n"
					+ " WHERE                                                                 \n"
					+ " prpsf.ID_PROGETTO =:idProggetto                                       \n"
					+ " AND prpsf.ID_SOGGETTO_FINANZIATORE =pdsf.ID_SOGGETTO_FINANZIATORE     \n"
					+ " AND pdsf.FLAG_AGEVOLATO ='S'                                          \n";
			LOG.debug(sql);
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<Integer>() {
				
				@Override
				public Integer extractData(ResultSet rs) throws SQLException {
					int elencoDati = 0;
					
					while(rs.next()) {
						elencoDati= rs.getInt("importo");
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}
	
	
	@Override
	public int getTotaleBanca(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getTotaleBanca]";
		LOG.info(prf + " BEGIN");
		int dati=0;
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql =" SELECT                                                              \n"
					+ "  ptce.IMPORTO_FINANZIAMENTO_BANCA                                     \n"
					+ " FROM                                                                  \n"
					+ "   PBANDI_T_PROGETTO ptp,                                              \n"
					+ "  PBANDI_T_CONTO_ECONOMICO ptce,                                       \n"
					+ "  PBANDI_D_STATO_CONTO_ECONOMICO  pdsce                                \n"
					+ " WHERE                                                                 \n"
					+ " ptp.ID_PROGETTO=:idProggetto                                          \n"
					+ " AND ptp.ID_DOMANDA=ptce.ID_DOMANDA                                    \n"
					+ " AND ptce.DT_FINE_VALIDITA  is null                                    \n"
					+ " AND ptce.ID_STATO_CONTO_ECONOMICO = pdsce.ID_STATO_CONTO_ECONOMICO    \n"
					+ " AND pdsce.ID_TIPOLOGIA_CONTO_ECONOMICO =2                             \n";
			LOG.debug(sql);
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<Integer>() {
				
				@Override
				public Integer extractData(ResultSet rs) throws SQLException {
					int elencoDati = 0;
					
					while(rs.next()) {
						elencoDati= rs.getInt("IMPORTO_FINANZIAMENTO_BANCA");
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}		
	return dati;
	}
	
	@Override
	public Date getDtConcessione(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getDtConcessione]";
		LOG.info(prf + " BEGIN");
		Date dati= null;
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql =" SELECT                                                              \n"
					+ "  dt_concessione                                                       \n"
					+ " FROM                                                                  \n"
					+ "   PBANDI_T_PROGETTO                                                   \n"
					+ " WHERE                                                                 \n"
					+ " Id_progetto= :idProggetto and                                         \n"
					+ "DT_CHIUSURA_PROGETTO is null                                           \n";
			LOG.debug(sql);
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<Date>() {
				
				@Override
				public Date extractData(ResultSet rs) throws SQLException {
					Date elencoDati = null;
					
					while(rs.next()) {	
						elencoDati= rs.getDate("dt_concessione");
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}
	
	@Override
	public Date getDtErogazione(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getDtErogazione]";
		LOG.info(prf + " BEGIN");
		Date dati= null;
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql =" SELECT                                                              \n"
					+ "  max(DT_CONTABILE) data                                               \n"
					+ " FROM                                                                  \n"
					+ "   PBANDI_T_EROGAZIONE pte                                             \n"
					+ " WHERE                                                                 \n"
					+ " ID_PROGETTO =:idProgetto AND                                          \n"
					+ "ID_MODALITA_AGEVOLAZIONE IN (1, 5, 10)                                 \n"
					+ "GROUP BY ID_MODALITA_AGEVOLAZIONE                                      \n";
			LOG.debug(sql);
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<Date>() {
				
				@Override
				public Date extractData(ResultSet rs) throws SQLException {
					Date elencoDati = null;
					
					while(rs.next()) {	
						elencoDati=rs.getDate("data");	
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}
	
	@Override
	public BigDecimal getImportoEscusso(String idProgetto, BigDecimal idEscussione) {
		String prf = "[RicercaGaranzieDAOImpl::getImportoEscusso]";
		LOG.info(prf + " BEGIN");
		BigDecimal dati = null;
		LOG.info(prf + "idProgetto=" + idProgetto);
		LOG.info(prf + "idEscussione=" + idEscussione);
		try {
			
			// Non ho abbastanza esperienza per capire la genialità di questa query, spero che i miei bisnipoti un giorno la comprenderanno
			/*String sql =" SELECT                                                              \n"
					+ "  Imp_approvato                                                        \n"
					+ " FROM                                                                  \n"
					+ "  PBANDI_T_ESCUSSIONE                                                  \n"
					+ " WHERE                                                                 \n"
					+ " (Id_progetto= :idproggetto AND                                        \n"
					+ "Dt_fine_validita is NULL) OR                                           \n"
					+ "(Id_progetto = :idproggetto AND                                        \n"
					+ "Id_tipo_escussione^= 1 AND                                             \n"
					+ "Id_stato_escussione=5 AND                                              \n"
					+ "Dt_fine_validita is null)                                              \n";*/
			
			String sql ="SELECT Imp_approvato\r\n"
					+ "FROM PBANDI_T_ESCUSSIONE\r\n"
					+ "WHERE ID_ESCUSSIONE = :idEscussione";
			//LOG.debug(sql);
			//Object[] args = new Object[]{idProgetto, idProgetto};
			//int[] types = new int[]{Types.INTEGER, Types.INTEGER};
			
			Object[] args = new Object[]{idEscussione};
			int[] types = new int[]{Types.INTEGER};
			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<BigDecimal>() {
				
				@Override
				public BigDecimal extractData(ResultSet rs) throws SQLException {
					BigDecimal elencoDati = null;
					
					while(rs.next()) {		
						elencoDati = rs.getBigDecimal("Imp_approvato");
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}
	
	@Override
	public String getStatoDelCredito(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getStatoDelCredito]";
		LOG.info(prf + " BEGIN");
		String dati= null;
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql =" SELECT                                                              \n"
					+ "  c.DESC_STATO_CREDITO_FP                                              \n"
					+ " FROM                                                                  \n"
					+ " PBANDI_R_SOGGETTO_PROGETTO a,                                         \n"
					+ "PBANDI_R_SOGG_PROG_STA_CRED_FP b,                                      \n"
					+ "PBANDI_D_STATO_CREDITO_FP c                                            \n"
					+ " WHERE                                                                 \n"
					+ " a.id_progetto=:idProggetto AND                                        \n"
					+ "a.id_tipo_anagrafica=1 AND a.id_tipo_beneficiario<> 4 AND               \n"
					+ "a.PROGR_SOGGETTO_PROGETTO=b.PROGR_SOGGETTO_PROGETTO  AND                      \n"
					+ "b.dt_fine_validita is null AND                                          \n"
					+ "b.ID_STATO_CREDITO_FP =c.ID_STATO_CREDITO_FP                            \n";
			LOG.debug(sql);
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<String>() {
				
				@Override
				public String extractData(ResultSet rs) throws SQLException {
					String elencoDati = null;
					
					while(rs.next()) {		
						elencoDati= rs.getString("DESC_STATO_CREDITO_FP");
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}
	
	@Override
	public List<Integer> getRevocaAzioniSaldo(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getRevocaBancaria]";
		LOG.info(prf + " BEGIN");
		List<Integer> dati = new ArrayList<>();
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			/* MA CHE CA*** FATE! 
			 String sql =" SELECT                                                                                                  \r\n"
			 
					+ " count(id_revoca_bancaria) revoca, count(id_azione_recupero_banca) azione, count(ID_SALDO_STRALCIO) saldo  \r\n"
					+ " FROM                                                                                                       \r\n"
					+ "PBANDI_T_REVOCA_BANCARIA a\r\n"
					+ "LEFT JOIN PBANDI_T_AZIONE_RECUP_BANCA b ON b.id_progetto= a.id_progetto \r\n"
					+ "LEFT JOIN PBANDI_T_SALDO_STRALCIO c ON c.ID_PROGETTO = a.id_progetto                         \r\n"
					+ " WHERE                                                                                                            \r\n"
					+ " a.Dt_fine_validita is NULL \r\n"
					+ " AND b.Dt_fine_validita is NULL \r\n"
					+ " AND c.Dt_fine_validita is null \r\n"
					+ " AND a.ID_PROGETTO = :idProgetto \n";
			LOG.debug(sql);
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<List<Integer>>() {
				
				@Override
				public List<Integer> extractData(ResultSet rs) throws SQLException {
					List<Integer> elencoDati = new ArrayList<>();
					
					while(rs.next()) {
						List<Integer> revocaAzioneSaldo= new ArrayList<>();
						revocaAzioneSaldo.add(rs.getInt("revoca"));
						revocaAzioneSaldo.add(rs.getInt("azione"));
						revocaAzioneSaldo.add(rs.getInt("saldo"));
						elencoDati.addAll(revocaAzioneSaldo);
					}
					
					return elencoDati;
				}
				
		   });*/
			
			String sql3 ="SELECT COUNT(ptrb.ID_REVOCA_BANCARIA) AS revoca \r\n"
					+ "FROM PBANDI_T_REVOCA_BANCARIA ptrb \r\n"
					+ "WHERE DT_FINE_VALIDITA IS NULL AND ptrb.ID_PROGETTO = " + idProgetto;
			dati.add(getJdbcTemplate().queryForObject(sql3, Integer.class));
			
			String sql2 ="SELECT COUNT(ptarb.ID_AZIONE_RECUPERO_BANCA) AS azione \r\n"
					+ "FROM PBANDI_T_AZIONE_RECUP_BANCA ptarb \r\n"
					+ "WHERE DT_FINE_VALIDITA IS NULL AND ptarb.ID_PROGETTO = " + idProgetto;
			dati.add(getJdbcTemplate().queryForObject(sql2, Integer.class));

			String sql ="SELECT COUNT(ID_SALDO_STRALCIO) AS saldo \r\n"
					+ "FROM PBANDI_T_SALDO_STRALCIO ptss\r\n"
					+ "WHERE DT_FINE_VALIDITA IS NULL AND ptss.ID_PROGETTO = " + idProgetto;
			dati.add(getJdbcTemplate().queryForObject(sql, Integer.class));
			
		}catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

	return dati;
	}
	
	@Override
	public List<VisualizzaRevocaBancariaVO> getRevoca(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getRevocaBancaria]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaRevocaBancariaVO> dati = null;
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql =" SELECT                                                                                                  \n"
					+ " DT_RICEZIONE_COMUNICAZ,                                                                                   \n"
					+ "DT_REVOCA,                                                                                                 \n"
					+ "IMP_DEBITO_RESIDUO_BANCA,                                                                                  \n"
					+ "IMP_DEBITO_RESIDUO_FP,                                                                                     \n"
					+ "PERC_COFINANZ_FP,                                                                                           \n"
					+ "NUM_PROTOCOLLO,                                                                                           \n"
					+ "NOTE                                                                                                       \n"
					+ " FROM                                                                                                      \n"
					+ "PBANDI_T_REVOCA_BANCARIA                                                                                   \n"
					+ " WHERE                                                                                                     \n"
					+ " id_progetto=:idProgetto and                                                                               \n"
					+ " Dt_fine_validita is null                                                                                  \n";
			LOG.debug(sql);
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<List<VisualizzaRevocaBancariaVO>>() {
				
				@Override
				public List<VisualizzaRevocaBancariaVO> extractData(ResultSet rs) throws SQLException {
					List<VisualizzaRevocaBancariaVO> elencoDati = new ArrayList<>();
					
					while(rs.next()) {
						VisualizzaRevocaBancariaVO item= new VisualizzaRevocaBancariaVO();
						item.setDataRicezComunicazioneRevoca(rs.getDate("DT_RICEZIONE_COMUNICAZ"));
						item.setDataRevoca(rs.getDate("DT_REVOCA"));
						item.setImpDebitoResiduoBanca(rs.getBigDecimal("IMP_DEBITO_RESIDUO_BANCA"));
						item.setImpDebitoResiduoFinpiemonte(rs.getBigDecimal("IMP_DEBITO_RESIDUO_FP"));
						item.setPerCofinanziamentoFinpiemonte(rs.getLong("PERC_COFINANZ_FP"));
						item.setNumeroProtocollo(rs.getString("NUM_PROTOCOLLO"));
						item.setNote(rs.getString("NOTE"));
						elencoDati.add(item);
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}

	@Override
	public List<VisualizzaAzioniRecuperoBancaVO> getRecupero(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getRecupero]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaAzioniRecuperoBancaVO> dati = null;
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql =" SELECT                                                                                                  \n"
					+ " pdamc.Desc_att_monit_cred,                                                                                \n"
					+ " ptarb.dt_azione,                                                                                          \n"
					+ " ptpf.cognome,                                                                                             \n"
					+ " ptpf.nome,                                                                                                \n"
					+ " ptarb.num_protocollo,                                                                                     \n"
					+ " ptarb.note                                                                                                \n"
					+ " FROM                                                                                                      \n"
					+ "pbandi_t_azione_recup_banca ptarb,                                                                         \n"
					+ " pbandi_d_attivita_monit_cred pdamc,                                                                       \n"
					+ " pbandi_t_utente ptu,                                                                                      \n"
					+ " pbandi_t_persona_fisica ptpf                                                                              \n"
					+ " WHERE                                                                                                     \n"
					+ " ptarb.id_attivita_azione = pdamc.ID_TIPO_MONIT_CRED  and ptarb.dt_fine_validita is null                   \n"
					+ " and ptarb.id_utente_ins= ptu.id_utente and ptu.id_soggetto= ptpf.id_soggetto                              \n"
					+ " and ptarb.id_progetto=:idProgetto                                                                         \n";
			LOG.debug(sql);
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<List<VisualizzaAzioniRecuperoBancaVO>>() {
				
				@Override
				public List<VisualizzaAzioniRecuperoBancaVO> extractData(ResultSet rs) throws SQLException {
					List<VisualizzaAzioniRecuperoBancaVO> elencoDati = new ArrayList<>();
					
					while(rs.next()) {
						VisualizzaAzioniRecuperoBancaVO item= new VisualizzaAzioniRecuperoBancaVO();
						item.setDescrizioneAttivita(rs.getString("Desc_att_monit_cred"));
						item.setDataAzione(rs.getDate("dt_azione"));
						item.setCognome(rs.getString("cognome"));
						item.setNome(rs.getString("nome"));
						item.setNumProtocollo(rs.getString("num_protocollo"));
						item.setNote(rs.getString("note"));
						elencoDati.add(item);
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}

	@Override
	public List<VisualizzaSaldoStralcioVO> getSaldoStralcio(String idProgetto, int idAttivitaEsito) {
		String prf = "[RicercaGaranzieDAOImpl::getSaldoStralcio]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaSaldoStralcioVO> dati = null;
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql =" SELECT saldo.descSaldoStralcio,                              \n"
					+ " ptss.DT_ESITO,                                                 \n"
					+ " ptpf.cognome,                                                  \n"
					+ " ptpf.nome,                                                     \n"
					+ " ptss.DT_PROPOSTA,                                              \n"
					+ " ptss.DT_ACCETTAZIONE,                                          \n"
					+ " recupero.descRecupero,                                         \n"
					+ " ptss.IMP_DEBITORE,                                             \n"
					+ " ptss.IMP_CONFIDI,                                              \n"
					+ " ptss.DT_PAGAM_DEBITORE,                                        \n"
					+ " ptss.DT_PAGAM_CONFIDI,                                         \n"
					+ " esito.descEsito,                                               \n"
					+ " ptss.IMP_RECUPERATO,                                           \n"
					+ " ptss.IMP_PERDITA,                                              \n"
					+ " ptss.note                                                      \n"
					+ " From pbandi_t_persona_fisica ptpf,                             \n"
					+ " PBANDI_T_UTENTE ptu,                                           \n"
					+ " PBANDI_T_SALDO_STRALCIO ptss                                   \n"
					+ " LEFT JOIN (SELECT DISTINCT ID_ATTIVITA_MONIT_CRED, DESC_ATT_MONIT_CRED descSaldoStralcio   \n"
					+ "           FROM PBANDI_D_ATTIVITA_MONIT_CRED) saldo                                         \n"
					+ "					ON saldo.ID_ATTIVITA_MONIT_CRED = ptss.ID_ATTIVITA_SALDO_STRALCIO          \n"
					+ " LEFT JOIN (SELECT DISTINCT ID_ATTIVITA_MONIT_CRED, DESC_ATT_MONIT_CRED descRecupero        \n"
					+ "            FROM PBANDI_D_ATTIVITA_MONIT_CRED) recupero                                     \n"
					+ "					ON recupero.ID_ATTIVITA_MONIT_CRED = PTSS .ID_ATTIVITA_RECUPERO            \n"
					+ "LEFT JOIN (SELECT DISTINCT ID_ATTIVITA_MONIT_CRED, DESC_ATT_MONIT_CRED descEsito            \n"
					+ "          FROM PBANDI_D_ATTIVITA_MONIT_CRED) esito                                          \n"
					+ "					ON esito.ID_ATTIVITA_MONIT_CRED = :idAttivitaEsito                         \n"
					+ " Where ptpf.id_soggetto=ptu.id_soggetto                                                     \n"
					+ " and ptu.Id_utente= ptss.Id_utente_ins                                                      \n"
					+ " and ptss.id_progetto=:idProgetto                                                           \n";
			LOG.debug(sql);
			Object[] args = new Object[]{idAttivitaEsito, idProgetto};
			int[] types = new int[]{Types.INTEGER, Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<List<VisualizzaSaldoStralcioVO>>() {
				
				@Override
				public List<VisualizzaSaldoStralcioVO> extractData(ResultSet rs) throws SQLException {
					List<VisualizzaSaldoStralcioVO> elencoDati = new ArrayList<>();
					
					while(rs.next()) {
						VisualizzaSaldoStralcioVO item= new VisualizzaSaldoStralcioVO();
						item.setDescEsito(rs.getString("descEsito")); 
						item.setDataEsito(rs.getDate("DT_ESITO")); 
						item.setNome(rs.getString("nome"));
						item.setCognome(rs.getString("cognome"));
						item.setDataProposta(rs.getDate("DT_PROPOSTA"));
						item.setDataAcettazione(rs.getDate("DT_ACCETTAZIONE")); 
						item.setDescSaldoStralcio(rs.getString("descSaldoStralcio"));
						item.setImpDebitore(rs.getBigDecimal("IMP_DEBITORE"));
						item.setImpConfidi(rs.getBigDecimal("IMP_CONFIDI"));
						item.setDataPagamDebitore(rs.getDate("DT_PAGAM_DEBITORE"));
						item.setDataPagamConfidi(rs.getDate("DT_PAGAM_CONFIDI"));
						item.setDescRecupero(rs.getString("descRecupero"));
						item.setImpRecuperato(rs.getBigDecimal("IMP_RECUPERATO"));
						item.setImpPerdita(rs.getBigDecimal("IMP_PERDITA"));
						item.setNote(rs.getString("note"));
						elencoDati.add(item);
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}

	@Override
	public List<VisualizzaDatiAnagraficiVO> getDatiAnagrafici(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getDatiAnagrafici]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaDatiAnagraficiVO> dati = null;
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql ="SELECT\r\n"
					+ "	ptb.TITOLO_BANDO,\r\n"
					+ "	ptp.codice_visualizzato,\r\n"
					+ "	pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
					+ "	pts.PARTITA_IVA,\r\n"
					+ "	pdfg.DESC_FORMA_GIURIDICA,\r\n"
					+ "	pdts.desc_tipo_soggetto,\r\n"
					+ "	prsp.id_soggetto, pts2.CODICE_FISCALE_SOGGETTO,\r\n"
					+ "		CASE\r\n"
					+ "        WHEN (pteb.ID_AGENZIA IS NOT NULL) THEN pdb2.DESC_BANCA\r\n"
					+ "        ELSE pdb.DESC_BANCA\r\n"
					+ "    END AS desc_banca,\r\n"
					+ "    CASE\r\n"
					+ "        WHEN (pteb.ID_AGENZIA IS NOT NULL) THEN pdb2.COD_BANCA\r\n"
					+ "        ELSE pdb.COD_BANCA\r\n"
					+ "    END AS COD_BANCA,\r\n"
					+ "    CASE\r\n"
					+ "        WHEN (pteb.ID_AGENZIA IS NOT NULL) THEN pdb2.ID_BANCA\r\n"
					+ "        ELSE pdb.ID_BANCA\r\n"
					+ "    END AS id_banca\r\n"
					+ "FROM\r\n"
					+ "	PBANDI_R_SOGG_PROGETTO_SEDE prsps\r\n"
					+ "LEFT JOIN PBANDI_T_SEDE pts ON\r\n"
					+ "	prsps.ID_SEDE = pts.ID_SEDE\r\n"
					+ "LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON\r\n"
					+ "	prsp.PROGR_SOGGETTO_PROGETTO = prsps.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON\r\n"
					+ "	prsp.ID_ENTE_GIURIDICO = pteg.ID_ENTE_GIURIDICO\r\n"
					+ "LEFT JOIN PBANDI_D_FORMA_GIURIDICA pdfg ON\r\n"
					+ "	pteg.ID_FORMA_GIURIDICA = pdfg.ID_FORMA_GIURIDICA\r\n"
					+ "LEFT JOIN PBANDI_T_SOGGETTO pts2 ON\r\n"
					+ "	prsp.ID_SOGGETTO = pts2.ID_SOGGETTO\r\n"
					+ "LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON\r\n"
					+ "	pts2.ID_TIPO_SOGGETTO = pdts.ID_TIPO_SOGGETTO\r\n"
					+ "LEFT JOIN PBANDI_T_PROGETTO ptp ON\r\n"
					+ "	prsp.ID_PROGETTO = ptp.ID_PROGETTO\r\n"
					+ "LEFT JOIN PBANDI_T_DOMANDA ptd ON\r\n"
					+ "	ptp.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
					+ "LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON\r\n"
					+ "	ptd.progr_bando_linea_intervento = prbli.progr_bando_linea_intervento\r\n"
					+ "LEFT JOIN PBANDI_T_BANDO ptb ON\r\n"
					+ "	prbli.ID_BANDO = ptb.ID_BANDO\r\n"
					+ "		LEFT JOIN PBANDI_T_ESTREMI_BANCARI pteb ON prsp.ID_ESTREMI_BANCARI = pteb.ID_ESTREMI_BANCARI\r\n"
					+ "    LEFT JOIN pbandi_t_agenzia pta ON pta.ID_AGENZIA = pteb.ID_AGENZIA\r\n"
					+ "    LEFT JOIN PBANDI_D_BANCA pdb ON pteb.ID_BANCA = pdb.ID_BANCA\r\n"
					+ "    LEFT JOIN pbandi_d_banca pdb2 ON pdb2.ID_BANCA = pta.ID_BANCA\r\n"
					+ "WHERE\r\n"
					+ "	ptp.ID_PROGETTO = :idProgetto\r\n"
					+ "	AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "	AND prsp.ID_TIPO_BENEFICIARIO ^ = 4\r\n"
					+ "	AND prsps.ID_TIPO_SEDE = 1";
			LOG.debug(sql);
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<List<VisualizzaDatiAnagraficiVO>>() {
				
				@Override
				public List<VisualizzaDatiAnagraficiVO> extractData(ResultSet rs) throws SQLException {
					List<VisualizzaDatiAnagraficiVO> elencoDati = new ArrayList<>();
					
					while(rs.next()) {
						VisualizzaDatiAnagraficiVO item= new VisualizzaDatiAnagraficiVO();
						item.setBando(rs.getString("TITOLO_BANDO")); 
						item.setProgetto(rs.getString("CODICE_VISUALIZZATO")); 
						item.setPartitaIva(rs.getString("PARTITA_IVA"));
						item.setFormaGiuridica(rs.getString("DESC_FORMA_GIURIDICA")); 
						item.setTipoAnagrafica(rs.getString("DESC_TIPO_SOGGETTO"));
						item.setDenominazioneBanca(rs.getString("DESC_BANCA"));
						item.setDenominazione(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
						item.setCodBanca(rs.getString("COD_BANCA"));
						item.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO")); 
						item.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
						elencoDati.add(item);
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}
	
	@Override
	public List< VisualizzaDatiAnagraficiVO> getDatiAnagraficiPersona(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getDatiAnagraficiPersona]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaDatiAnagraficiVO> dati = null;
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql =" SELECT                                                          \n"
					+ " ptpf.COGNOME,                                                     \n"
					+ " ptpf.NOME,                                                        \n"
					+ " pts.CODICE_FISCALE_SOGGETTO                                       \n"
					+ " From PBANDI_R_SOGGETTO_PROGETTO prsp ,                            \n"
					+ "PBANDI_T_PERSONA_FISICA ptpf,                                      \n"
					+ "PBANDI_T_SOGGETTO pts                                              \n"
					+ " Where prsp.ID_PROGETTO = :idProgetto AND                          \n"
					+ "prsp.ID_TIPO_ANAGRAFICA =1 AND                                     \n"
					+ "prsp.ID_TIPO_BENEFICIARIO ^= 4 AND                                 \n"
					+ "prsp.ID_PERSONA_FISICA  =ptpf.ID_PERSONA_FISICA and                \n"
					+ "pts.ID_SOGGETTO  =prsp.ID_SOGGETTO                                 \n";
			LOG.debug(sql);
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<List<VisualizzaDatiAnagraficiVO>>() {
				
				@Override
				public List<VisualizzaDatiAnagraficiVO> extractData(ResultSet rs) throws SQLException {
					List<VisualizzaDatiAnagraficiVO> elencoDati = new ArrayList<>();
					
					while(rs.next()) {
						VisualizzaDatiAnagraficiVO item= new VisualizzaDatiAnagraficiVO();
						item.setNome(rs.getString("NOME")); 
						item.setCognome(rs.getString("COGNOME")); 
						item.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
											
						elencoDati.add(item);
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}
	

	@Override
	public List<VisualizzaSezioneDettagliGaranziaVO> getSezioneDettaglioGaranzia(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getSezioneDettaglioGaranzia]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaSezioneDettagliGaranziaVO> dati = null;
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql =" SELECT DISTINCT  pdma.desc_breve_modalita_agevolaz,                          \n"
					+ "       (prpsf.Imp_quota_sogg_finanziatore + IMPORTO_FINANZIAMENTO_BANCA) tot,   \n"
					+ "       (SELECT sum(prpsf.IMP_QUOTA_SOGG_FINANZIATORE)                           \n"
					+ "          from                                                                  \n"
					+ "            PBANDI_R_PROG_SOGG_FINANZIAT prpsf,                                 \n"
					+ "           PBANDI_D_SOGGETTO_FINANZIATORE pdsf                                  \n"
					+ "          WHERE                                                                 \n"
					+ "            prpsf.ID_PROGETTO =19217                                            \n"
					+ "            AND prpsf.ID_SOGGETTO_FINANZIATORE =pdsf.ID_SOGGETTO_FINANZIATORE   \n"
					+ "            AND pdsf.FLAG_AGEVOLATO ='S'                                        \n"
					+ "            and pdsf.DESC_BREVE_SOGG_FINANZIATORE = 'UE' or                     \n"
					+ "            pdsf.DESC_BREVE_SOGG_FINANZIATORE ='Stato FESR' or                  \n"
					+ "            pdsf.DESC_BREVE_SOGG_FINANZIATORE ='Regione FESR') fondo,           \n"
					+ "        ptce.IMPORTO_FINANZIAMENTO_BANCA,                                       \n"
					+ "        ptp.dt_concessione,                                                     \n"
					+ "        (SELECT max(pte.DT_CONTABILE)                                           \n"
					+ "           FROM PBANDI_T_EROGAZIONE pte                                         \n"
					+ "           WHERE pte.id_progetto= ptp.id_progetto) dtErogazione,                \n"
					+ "        pte2.Imp_approvato,                                                     \n"
					+ "        (SELECT count(id_revoca_bancaria)                                       \n"
					+ "          FROM PBANDI_T_REVOCA_BANCARIA                                         \n"
					+ "          WHERE  id_progetto=ptp.ID_PROGETTO and                                \n"
					+ "          Dt_fine_validita is null) revoca,                                     \n"
					+ "        (SELECT count(id_azione_recupero_banca)                                 \n"
					+ "           FROM PBANDI_T_AZIONE_RECUP_BANCA                                     \n"
					+ "           WHERE  id_progetto=ptp.ID_PROGETTO                                   \n"
					+ "           AND  DT_FINE_VALIDITA  IS  NULL) recupero,                           \n"
					+ "        (SELECT count (ID_SALDO_STRALCIO )                                      \n"
					+ "           FROM PBANDI_T_SALDO_STRALCIO                                         \n"
					+ "           WHERE ID_PROGETTO =ptp.ID_PROGETTO                                   \n"
					+ "           AND DT_FINE_VALIDITA IS NULL) saldoStralcio                          \n"
					+ "from PBANDI_D_MODALITA_AGEVOLAZIONE pdma,                                       \n"
					+ "     PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema,                                     \n"
					+ "     PBANDI_D_STATO_CONTO_ECONOMICO pdsce,                                      \n"
					+ "     PBANDI_T_CONTO_ECONOMICO ptce,                                             \n"
					+ "     PBANDI_T_PROGETTO ptp,                                                     \n"
					+ "     PBANDI_R_PROG_SOGG_FINANZIAT prpsf,                                        \n"
					+ "     PBANDI_D_SOGGETTO_FINANZIATORE pdsf,                                       \n"
					+ "     PBANDI_T_EROGAZIONE pte,                                                   \n"
					+ "     PBANDI_T_ESCUSSIONE pte2                                                   \n"
					+ "where prpsf.ID_SOGGETTO_FINANZIATORE= pdsf.ID_SOGGETTO_FINANZIATORE             \n"
					+ "and pdsf.FLAG_AGEVOLATO ='S'                                                    \n"
					+ "and pdma.id_modalita_agevolazione in(1, 5, 10)                                  \n"
					+ "and pdma.id_modalita_agevolazione= prcema.id_modalita_agevolazione              \n"
					+ "and prcema.id_conto_economico= ptce.id_conto_economico                          \n"
					+ "and pdsce.Id_stato_conto_economico= ptce.Id_stato_conto_economico               \n"
					+ "and pdsce.Id_tipologia_conto_economico=2                                        \n"
					+ "and ptce.id_domanda= ptp.id_domanda                                             \n"
					+ "and ptce.dt_fine_validita is null                                               \n"
					+ "and pte2.Dt_fine_validita is null                                               \n"
					+ "and pte2.id_progetto= ptp.id_progetto                                           \n"
					+ "and pte.id_progetto= ptp.id_progetto                                            \n"
					+ "and prpsf.id_progetto= ptp.id_progetto                                          \n"
					+ "and ptp.id_progetto= :idProgetto                                                \n";
			LOG.debug(sql);
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<List<VisualizzaSezioneDettagliGaranziaVO>>() {
				
				@Override
				public List<VisualizzaSezioneDettagliGaranziaVO> extractData(ResultSet rs) throws SQLException {
					List<VisualizzaSezioneDettagliGaranziaVO> elencoDati = new ArrayList<>();
					
					while(rs.next()) {
						VisualizzaSezioneDettagliGaranziaVO item= new VisualizzaSezioneDettagliGaranziaVO();
						item.setTipoAgevolazione(rs.getString("desc_breve_modalita_agevolaz")); 
						item.setImpAmmesso(rs.getBigDecimal("tot")); 
						item.setTotFondo(rs.getBigDecimal("fondo"));
						item.setTotBanca(rs.getBigDecimal("IMPORTO_FINANZIAMENTO_BANCA"));
						item.setDtConcessione(rs.getDate("dt_concessione"));
						item.setDtErogazione(rs.getDate("dtErogazione"));
						item.setImpEscusso(rs.getBigDecimal("Imp_approvato"));
						//item.setStatoCredito(rs.getString("CODICE_FISCALE_SOGGETTO"));
						int revoca = rs.getInt("revoca");
						int recupero= rs.getInt("recupero");
						int saldoStralcio = rs.getInt("saldoStralcio");
						
						if(revoca> 0) {
							item.setRevocaBancaria("Si");
						}else {
							item.setRevocaBancaria("No");
						}
						
						if(recupero> 0) {
							item.setAzioniRecBanca("Si");
						}else {
							item.setAzioniRecBanca("No");
						}
						
						if(saldoStralcio> 0) {
							item.setSaldoStralcio("Si");
						}else {
							item.setSaldoStralcio("No");
						}
											
						elencoDati.add(item);
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}
	
	@Override
	public List<VisualizzaSezioneDettagliGaranziaVO> getStatoCredito(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getStatoCredito]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaSezioneDettagliGaranziaVO> dati = null;
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql =" SELECT DISTINCT c.DESC_STATO_CREDITO_FP                                                  \n"
					+ "FROM PBANDI_R_SOGGETTO_PROGETTO a,                                                          \n"
					+ "PBANDI_R_SOGG_PROG_STA_CRED_FP b,                                                           \n"
					+ "PBANDI_D_STATO_CREDITO_FP c                                                                 \n"
					+ "WHERE                                                                                       \n"
					+ "a.id_progetto=:idProgetto AND                                                               \n"
					+ "a.id_tipo_anagrafica=1 AND a.id_tipo_beneficiario<> 4 AND                                   \n"
					+ "a.id_soggetto=b.ID_SOGG_PROG_STATO_CREDITO_FP  AND                                          \n"
					+ "b.dt_fine_validita is null AND                                                              \n"
					+ "b.ID_STATO_CREDITO_FP =c.ID_STATO_CREDITO_FP                                                \n";
			
			LOG.debug(sql);
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<List<VisualizzaSezioneDettagliGaranziaVO>>() {
				
				@Override
				public List<VisualizzaSezioneDettagliGaranziaVO> extractData(ResultSet rs) throws SQLException {
					List<VisualizzaSezioneDettagliGaranziaVO> elencoDati = new ArrayList<>();
					
					while(rs.next()) {
						VisualizzaSezioneDettagliGaranziaVO item= new VisualizzaSezioneDettagliGaranziaVO();
						
						item.setStatoCredito(rs.getString("DESC_STATO_CREDITO_FP"));
											
						elencoDati.add(item);
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}

	@Override
	public List<VisualizzaStatoCreditoVO> getStatoCreditoStorico(String idProgetto, int idUtente) {
		String prf = "[RicercaGaranzieDAOImpl::getStatoCreditoStorico]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaStatoCreditoVO> dati = new ArrayList<VisualizzaStatoCreditoVO>();
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql=null; // TODO: Controllare tutta la query
			sql ="SELECT DISTINCT pdsc.DESC_STATO_CREDITO_FP,\r\n"
					+ "	prspscf.Dt_inizio_validita,\r\n"
					+ "	ptpf.NOME,\r\n"
					+ "	ptpf.COGNOME,\r\n"
					+ "	prspscf.DT_INSERIMENTO,\r\n"
					+ "	prspscf.ID_STATO_CREDITO_FP,\r\n"
					+ "	prspscf.ID_SOGG_PROG_STATO_CREDITO_FP,\r\n"
					+ "	prspscf.DT_FINE_VALIDITA\r\n"
					+ "FROM PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "	LEFT JOIN PBANDI_R_SOGG_PROG_STA_CRED_FP prspscf ON prsp.PROGR_SOGGETTO_PROGETTO = prspscf.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "	LEFT JOIN PBANDI_D_STATO_CREDITO_FP pdsc ON prspscf.ID_STATO_CREDITO_FP = pdsc.ID_STATO_CREDITO_FP\r\n"
					+ "	LEFT JOIN PBANDI_T_UTENTE ptu ON prspscf.ID_UTENTE_INS = ptu.ID_UTENTE\r\n"
					+ "	LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_SOGGETTO = ptu.ID_SOGGETTO\r\n"
					+ "WHERE\r\n"
					+ "	prsp.id_progetto = :idProgetto\r\n"
					+ "	--AND ptpf.ID_PERSONA_FISICA IN (SELECT MAX(ptpf2.ID_PERSONA_FISICA) AS ID_PERSONA_FISICA  FROM pbandi_t_persona_fisica ptpf2 GROUP BY ID_SOGGETTO)\r\n"
					+ "	AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "  AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "	and prspscf.DT_FINE_VALIDITA IS NOT NULL\r\n"
					+ "	AND prspscf.ID_MODALITA_AGEVOLAZIONE = 10\r\n"
					+ "ORDER BY prspscf.DT_INSERIMENTO DESC";
			
			LOG.debug(sql);
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<List<VisualizzaStatoCreditoVO>>() {
				
				@Override
				public List<VisualizzaStatoCreditoVO> extractData(ResultSet rs) throws SQLException {
					List<VisualizzaStatoCreditoVO> elencoDati = new ArrayList<>();
					
					while(rs.next()) {
						VisualizzaStatoCreditoVO item= new VisualizzaStatoCreditoVO();
						
						item.setDescStato(rs.getString("DESC_STATO_CREDITO_FP"));
						item.setDtModifica(rs.getDate("Dt_inizio_validita"));
						item.setNome(rs.getString("NOME"));
						item.setCognome(rs.getString("COGNOME"));
						item.setIdStatoCredito(rs.getInt("ID_STATO_CREDITO_FP"));
						item.setProgrStatoCredito(rs.getBigDecimal("ID_SOGG_PROG_STATO_CREDITO_FP"));
											
						elencoDati.add(item);
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}											
		


	@Override
	public List<VisualizzaStoricoEscussioneVO> getStoricoEscussione(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getStoricoEscussione]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaStoricoEscussioneVO> dati = null;
		LOG.info(prf + "idProgetto=" + idProgetto);

		try {
			/*	OLD QUERY
			 String sql =" select distinct pte.Dt_ric_rich_escussione, pte.NUM_PROTOCOLLO_RICH , pdte.Desc_tipo_escussione,           \r\n"
					+ "pdse.DESC_STATO_ESCUSSIONE, pte.dt_inizio_validita, pte.dt_notifica, pte.NUM_PROTOCOLLO_NOTIF,       \r\n"
					+ "pte.Imp_approvato, pte.Imp_richiesto, pte.Causale_bonifico, pte.Iban_bonifico, pdb.Desc_banca,       \r\n"
					+ "pte.Note, ptpf.nome, ptpf.cognome, pte.dt_inserimento, pte.id_escussione                             \r\n"
					+ "from                                                                                                 \r\n"
					+ "PBANDI_T_ESCUSSIONE pte,                                                                             \r\n"
					+ "PBANDI_D_TIPO_ESCUSSIONE pdte,                                                                       \r\n"
					+ "PBANDI_D_STATO_ESCUSSIONE pdse,                                                                      \r\n"
					+ "PBANDI_D_BANCA pdb,                                                                                  \r\n"
					+ "PBANDI_T_PERSONA_FISICA ptpf,                                                                        \r\n"
					+ "PBANDI_T_UTENTE ptu                                                                                  \r\n"
					+ "WHERE                                                                                                \r\n"
					+ "pte.id_progetto= :idProgetto and                                                                     \r\n"
					+ "pdb.cod_banca= :codBanca and                                                                         \r\n"
					+ "ptu.ID_UTENTE= :codUtente  and                                                                       \r\n"
					+ "pdte.dt_fine_validita is null and                                                                    \r\n"
					+ "pdse.DT_FINE_VALIDITA IS NULL AND                                                                    \r\n"
					+ "pdte.id_tipo_escussione = pte.id_tipo_escussione AND                                                 \r\n"
					+ "pdse.id_stato_escussione = pte.id_stato_escussione and                                               \r\n"
					+ "ptpf.id_soggetto= ptu.id_soggetto                                                                    \r\n"
					+ "order by pte.dt_inserimento desc                                                                       \n";*/
			
			String sql = "select\r\n"
					+ "    distinct pte.Dt_ric_rich_escussione,\r\n"
					+ "    pte.NUM_PROTOCOLLO_RICH,\r\n"
					+ "    pdte.Desc_tipo_escussione,\r\n"
					+ "    pdse.DESC_STATO_ESCUSSIONE,\r\n"
					+ "    pte.dt_inizio_validita,\r\n"
					+ "    pte.dt_notifica,\r\n"
					+ "    pte.NUM_PROTOCOLLO_NOTIF,\r\n"
					+ "    pte.Imp_approvato,\r\n"
					+ "    pte.Imp_richiesto,\r\n"
					+ "    pte.Causale_bonifico,\r\n"
					+ "    pte.Iban_bonifico,\r\n"
					+ "    pdb.Desc_banca,\r\n"
					+ "    pte.Note,\r\n"
					+ "    ptpf.nome,\r\n"
					+ "    ptpf.cognome,\r\n"
					+ "    pte.dt_inserimento,\r\n"
					+ "    pte.id_escussione\r\n"
					+ "from\r\n"
					+ "    PBANDI_T_ESCUSSIONE pte \r\n"
					+ "    LEFT JOIN PBANDI_D_TIPO_ESCUSSIONE pdte ON  pdte.id_tipo_escussione = pte.id_tipo_escussione\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_ESCUSSIONE pdse ON pdse.id_stato_escussione = pte.id_stato_escussione\r\n"
					+ "    LEFT JOIN PBANDI_T_ESTREMI_BANCARI pteb ON pte.IBAN_BONIFICO = pteb.IBAN AND pteb.ID_BANCA IS NOT NULL \r\n"
					+ "    LEFT JOIN PBANDI_D_BANCA pdb ON pteb.ID_BANCA = pdb.ID_BANCA\r\n"
					+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = pte.ID_UTENTE_INS\r\n"
					+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptu.ID_SOGGETTO = PTPF.ID_SOGGETTO\r\n"
					+ "    AND PTPF.ID_PERSONA_FISICA IN (\r\n"
					+ "        SELECT\r\n"
					+ "            MAX(ID_PERSONA_FISICA)\r\n"
					+ "        FROM\r\n"
					+ "            PBANDI_T_PERSONA_FISICA ptpf2\r\n"
					+ "        GROUP BY\r\n"
					+ "            ptpf2.ID_SOGGETTO\r\n"
					+ "    )\r\n"
					+ "WHERE\r\n"
					+ "     pte.id_progetto = :idProgetto\r\n"
					+ "order by\r\n"
					+ "    pte.dt_inserimento desc";
			
			LOG.debug(sql);
			Object[] args = new Object[]{idProgetto};
			int[] types = new int[]{Types.INTEGER};

			dati = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<List<VisualizzaStoricoEscussioneVO>>() {
				
				@Override
				public List<VisualizzaStoricoEscussioneVO> extractData(ResultSet rs) throws SQLException {
					List<VisualizzaStoricoEscussioneVO> elencoDati = new ArrayList<>();
					
					while(rs.next()) {
						VisualizzaStoricoEscussioneVO item= new VisualizzaStoricoEscussioneVO();
						item.setDtRichEscussione(rs.getDate("Dt_ric_rich_escussione"));
						item.setNumProtocolloRich(rs.getString("NUM_PROTOCOLLO_RICH"));
						item.setDescTipoEscussione(rs.getString("Desc_tipo_escussione"));
						item.setDescStatoEscussione(rs.getString("DESC_STATO_ESCUSSIONE"));
						item.setDtInizioValidita(rs.getDate("dt_inizio_validita"));
						item.setDtNotifica(rs.getDate("dt_notifica"));
						item.setNumProtocolloNotif(rs.getString("NUM_PROTOCOLLO_NOTIF"));
						item.setImpApprovato(rs.getBigDecimal("Imp_approvato"));
						item.setImpRichiesto(rs.getBigDecimal("Imp_richiesto"));
						item.setCausaleBonifico(rs.getString("Causale_bonifico"));
						item.setIbanBonifico(rs.getString("Iban_bonifico"));
						item.setNote(rs.getString("Note"));
						item.setNome(rs.getString("nome"));
						item.setCognome(rs.getString("cognome"));
						item.setDescBanca(rs.getString("Desc_banca"));
						item.setIdEscussione(rs.getInt("id_escussione"));
						elencoDati.add(item);
					}
					
					return elencoDati;
				}
				
		   });
			
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(prf + " END");
	}
		
	return dati;
	}

	
	

	@Override
	public EsitoDTO insertStatoCredito(String statoCredito, Date dtModifica, int idSoggetto, int idProgetto, int idUtente) {
		String prf = "[RicercaGaranzieDAOImpl::insertStatoCredito]";
		LOG.info(prf + " BEGIN");
		
		EsitoDTO esito = new EsitoDTO();
		
		BigDecimal idPreviousState = null;
		
		try {
			// recupero l'id dello stato precendente
			String getPreviousID ="SELECT prspscf.ID_SOGG_PROG_STATO_CREDITO_FP \r\n"
					+ " FROM PBANDI_R_SOGG_PROG_STA_CRED_FP prspscf\r\n"
					+ " LEFT JOIN pbandi_r_soggetto_progetto prsp ON prsp.PROGR_SOGGETTO_PROGETTO = prspscf.PROGR_SOGGETTO_PROGETTO \r\n"
					+ " WHERE prsp.ID_PROGETTO =?\r\n"
					+ " AND prsp.ID_TIPO_ANAGRAFICA =1\r\n"
					+ " AND prsp.ID_TIPO_BENEFICIARIO <>4\r\n"
					+ " AND prspscf.DT_FINE_VALIDITA IS NULL";
			idPreviousState = getJdbcTemplate().queryForObject(getPreviousID, BigDecimal.class, new Object[] {idProgetto});

		} catch (Exception e) {
			idPreviousState = null; 
		}
		
		try {
			// per inserire uno stato credito bisogna recuperare sia il nextval che il progr_soggetto_progetto
			// del beneficiario dentro la tabella 
			String query = 
					"INSERT INTO PBANDI_R_SOGG_PROG_STA_CRED_FP                                               \r\n"
					+ "  ( ID_SOGG_PROG_STATO_CREDITO_FP,\r\n"
					+ "	PROGR_SOGGETTO_PROGETTO,\r\n"
					+ "	ID_STATO_CREDITO_FP,\r\n"
					+ "	ID_MODALITA_AGEVOLAZIONE,\r\n"
					+ "	ID_UTENTE_INS,\r\n"
					+ "	ID_UTENTE_AGG,\r\n"
					+ "	DT_INSERIMENTO,\r\n"
					+ "	DT_AGGIORNAMENTO,\r\n"
					+ "	DT_INIZIO_VALIDITA,\r\n"
					+ "	DT_FINE_VALIDITA )\r\n"
					+ "VALUES                                                                      \r\n"
					+ "(SEQ_PBANDI_R_SOG_PROG_STA_CRED.NEXTVAL,\r\n"
					+ "(\r\n"
					+ "	SELECT PROGR_SOGGETTO_PROGETTO\r\n"
					+ "	FROM PBANDI_R_soggetto_progetto\r\n"
					+ "	WHERE id_progetto = :idProgetto AND id_tipo_anagrafica = 1 AND id_tipo_beneficiario <> 4\r\n"
					+ "),\r\n"
					+ "( \r\n"
					+ "	SELECT ID_STATO_CREDITO_FP\r\n"
					+ "	FROM PBANDI_D_STATO_CREDITO_FP\r\n"
					+ "	WHERE DESC_BREVE_STATO_CREDITO_FP = :StatoCredito \r\n"
					+ "),\r\n"
					+ "10,\r\n"
					+ ":idUtente,\r\n"
					+ "NULL,\r\n"
					+ "SYSDATE,\r\n"
					+ "NULL,\r\n"
					+ ":dtModifica,\r\n"
					+ "NULL )    ";

			LOG.debug(query);
			
			Object[] args = new Object[]{idProgetto, statoCredito, idUtente, dtModifica};
			int[] types = new int[]{ Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.DATE};
				
			getJdbcTemplate().update(query, args, types);
			 
			} catch (Exception e) {
				esito.setEsito(false);
				esito.setMessaggio("Exception durante l'inserimento del nuovo record: " + e.toString());
				return esito;
				//val = 0;
				//throw new ErroreGestitoException("Errore nell'inserire il record", e);
			}
		
		try {
			// Se esiste un record precedente aggiorno le date
			if(idPreviousState != null) {
				String update ="UPDATE PBANDI_R_SOGG_PROG_STA_CRED_FP prspscf \r\n"
						+ " SET prspscf.DT_FINE_VALIDITA =SYSDATE, prspscf.DT_AGGIORNAMENTO = sysdate \r\n"
						+ " WHERE prspscf.ID_SOGG_PROG_STATO_CREDITO_FP=?"; 
				
				getJdbcTemplate().update(update, new Object[] {idPreviousState});
			}
		} catch (Exception e) {
			esito.setEsito(false);
			esito.setMessaggio("Exception durante l'aggiornamento del record precedente, id: " + idPreviousState + " - E: " + e.toString());
			return esito;
		}
		
		
		LOG.info(prf + " END");
		esito.setEsito(true);
		esito.setMessaggio("OK");
		return esito; 
	}

	@Override
	public void updateStatoCreditoAggiornato(Date dtModifica, int idUtente, Date vecchioDt) {
		String prf = "[RicercaGaranzieDAOImpl::updateStatoCreditoAggiornato]";
		LOG.info(prf + " BEGIN");
		int rowsUpdated = 0;
			String query = 
					"update PBANDI_R_SOGG_PROG_STA_CRED_FP                           	\n"
							+ " set id_utente_agg= :idUtente,                           \n"
							+ " dt_fine_validita= :dtModifica,                          \n"
							+ " dt_aggiornamento= CURRENT_DATE                       	\n"
							+ " where dt_inizio_validita = :vecchioDt 	                \n";

			LOG.debug(query);
			
			Object[] args = new Object[]{idUtente, dtModifica, vecchioDt};
			int[] types = new int[]{Types.INTEGER, Types.DATE, Types.DATE};
			
	
			try {
				rowsUpdated = getJdbcTemplate().update(query, args, types);
			} catch (Exception e) {
				throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
			}	
			
			LOG.info("N. record aggiornati:\n" + rowsUpdated);
			
	}


	@Override
	public int getIdSoggetto(String statoCredito, Date dtModifica) {
		String prf = "[RicercaGaranzieDAOImpl::getIdSoggetto]";
		LOG.info(prf + " BEGIN");
		int idSoggetto;
		try {
		String query =
				"SELECT ID_SOGG_PROG_STATO_CREDITO_FP                                 	\n"
						+ " FROM PBANDI_R_SOGG_PROG_STA_CRED_FP          	            \n"
						+ " where                                                       \n"
						+ "ID_STATO_CREDITO_FP= (SELECT ID_STATO_CREDITO_FP             \n"
						+ " FROM PBANDI_D_STATO_CREDITO_FP                              \n"
						+ " WHERE DESC_BREVE_STATO_CREDITO_FP=:statoCredito)  and       \n"
						+ "	dt_inizio_validita= :dtModifica                             \n";
		
		LOG.debug(query);

		Object[] args = new Object[]{statoCredito, dtModifica};
		int[] types = new int[]{Types.VARCHAR, Types.DATE};  

		idSoggetto = getJdbcTemplate().query(query, args, types, new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException {
				
				int idSog = 0;
				while(rs.next())
				{
			     idSog=  rs.getInt("ID_SOGG_PROG_STATO_CREDITO_FP");
				}
			return idSog;		
			}});
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(" END");
	}
	return idSoggetto;	
	}

	@Override
	public Date getVecchioDt(int idSoggetto) {
		String prf = "[RicercaGaranzieDAOImpl::getVecchioDt]";
		LOG.info(prf + " BEGIN");
		Date vecchiaDt= null;
		try {

			String sql =" SELECT                                                        \n"
					+ "   dt_inizio_validita                                            \n"
					+ " FROM                                                            \n"
					+ "   PBANDI_R_SOGG_PROG_STA_CRED_FP                                \n"
					+ "  WHERE ID_SOGG_PROG_STATO_CREDITO_FP = :idSoggetto              \n";
			 


			LOG.debug(sql);

			Object[] args = new Object[]{idSoggetto};
			int[] types = new int[]{Types.INTEGER};  

			vecchiaDt = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<Date>(){
				@Override
				public Date extractData(ResultSet rs) throws SQLException{
				Date dt = null;
				while(rs.next())
				{
				dt=  rs.getDate("dt_inizio_validita");
				}	
				return dt;		
				}});
		}catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return vecchiaDt;
	}


	@Override
	public int escussioneTotAccIsPresent(int idProgetto, String desc) {
		String prf = "[RicercaGaranzieDAOImpl::escussioneTotAccIsPresent]";
		LOG.info(prf + " BEGIN");
		int num;
		try {
		String query =
				"SELECT count(*) num_escussioni                                      	\n"
						+ " FROM PBANDI_T_ESCUSSIONE                    	            \n"
						+ " where                                                       \n"
						+ "ID_Progetto=:idProgetto                                      \n"
						+ "and ID_TIPO_ESCUSSIONE=(select ID_TIPO_ESCUSSIONE from PBANDI_D_TIPO_ESCUSSIONE \n"
						+ "where DESC_TIPO_ESCUSSIONE= :descTipoEscussione)             \n";
		
		LOG.debug(query);

		Object[] args = new Object[]{idProgetto, desc};
		int[] types = new int[]{Types.INTEGER, Types.VARCHAR};  

		num = getJdbcTemplate().query(query, args, types, new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException {
				
				int n = 0;
				while(rs.next())
				{
			     n= rs.getInt("num_escussioni");
				}
			return n;		
			}});
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(" END");
	}
	return num;	
	}


	@Override
	public int escussioneSaldoIsPresent(int idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::escussioneSaldoIsPresent]";
		LOG.info(prf + " BEGIN");
		int num;
		try {
		String query =
				"SELECT count(*) num_escussioni                                      	\n"
						+ " FROM PBANDI_T_ESCUSSIONE                    	            \n"
						+ " where                                                       \n"
						+ "ID_Progetto=:idProgetto                                      \n"
						+ "and ID_TIPO_ESCUSSIONE = 3                                   \n";
		
		LOG.debug(query);

		Object[] args = new Object[]{idProgetto};
		int[] types = new int[]{Types.INTEGER};  

		num = getJdbcTemplate().query(query, args, types, new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException {
				
				int n = 0;
				while(rs.next())
				{
			     n= rs.getInt("num_escussioni");
				}
			return n;		
			}});
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(" END");
	}
	return num;	
	}


    @Override
	public EsitoDTO insertEscussione(Date dtRichEscussione, String descTipoEscussione, BigDecimal impRichiesto, String numProtocolloRich,
	String note, Date dtInizioValidita, int idProgetto, String numProtocolloNotif, Long idUtente) {
		String prf = "[RicercaGaranzieDAOImpl::insertEscussione]";
		LOG.info(prf + " BEGIN");
		
		//boolean result = true;
		
		EsitoDTO esito = new EsitoDTO();
		
		
		if (idProgetto == 0) {
			//throw new InvalidParameterException("idProgetto non valorizzato.");
			esito.setEsito(false);
			esito.setMessaggio("idProgetto non valorizzato.");
			return esito;
		}
		if (idUtente == 0) {
			//throw new InvalidParameterException("idUtente non valorizzato.");
			esito.setEsito(false);
			esito.setMessaggio("idUtente non valorizzato.");
			return esito;
		}
		
		Long idEscussione = null;
		
		try {
			String getIdEscussione = "select SEQ_PBANDI_T_ESCUSSIONE.NEXTVAL from dual";
			idEscussione = getJdbcTemplate().queryForObject(getIdEscussione, Long.class);
		} catch (Exception e) {
			esito.setEsito(false);
			esito.setMessaggio("Exception in getIdEscussione: " + e.toString());
			return esito;

		}
		
		try {		
			String query = 
					"INSERT INTO PBANDI_T_ESCUSSIONE                                                          \n"
				    		  + " ( ID_ESCUSSIONE,                                                            \n"
				    		  + "  ID_PROGETTO,                                                               \n"
				    		  + "  ID_TIPO_ESCUSSIONE,                                                        \n"
				    		  + "  ID_STATO_ESCUSSIONE,                                                       \n"
				    		  + "  DT_RIC_RICH_ESCUSSIONE,                                                    \n"
				    		  + "  NUM_PROTOCOLLO_RICH,                                                       \n"
				    		  + "  DT_NOTIFICA,                                                               \n"
				    		  + "  NUM_PROTOCOLLO_NOTIF,                                                      \n"
				    		  + "  IMP_RICHIESTO,                                                             \n"
							  + "  IMP_APPROVATO,                                                             \n"
							  + "  CAUSALE_BONIFICO,                                                          \n"
							  + "  IBAN_BONIFICO,                                                             \n"
							  + "  NOTE,                                                                      \n"
							  //+ "  MOTIVO_RICH_INTEGRAZIONE,                                                \n"
							  + "  DT_INIZIO_VALIDITA,                                                        \n"
							  + "  DT_FINE_VALIDITA,                                                          \n"
							  + "  ID_UTENTE_INS,                                                             \n"
							  + "  ID_UTENTE_AGG,                                                             \n"
							  + "  DT_INSERIMENTO,                                                            \n"
							  + "  DT_AGGIORNAMENTO)                                                          \n"
				    		  + " VALUES                                                                      \n"
				    		  + " (" + idEscussione + ",                                                      \n"
                              + " :idProgetto,                                                                \n"
				    		  + " (SELECT ID_TIPO_ESCUSSIONE from PBANDI_D_TIPO_ESCUSSIONE                    \n"
				    		  + " where DESC_TIPO_ESCUSSIONE= :descTipoEscussione),                           \n"
                              + " 1,                                                                          \n"
				    		  + " :dtRichEscussione,                                                          \n"
                              + " :numProtocolloRich,                                                         \n"
				    		  + "  null,                                                                      \n"
				    		  + "  :numProtocolloNotif,                                                       \n"
				    		  + "  :impRichiesto,                                                             \n"
				    		  + "  null,                                                                      \n"
				    		  + "  null,                                                                      \n"
							  + "  null,                                                                      \n"
							  + "  :note,                                                                     \n"
							  //+ "  null,                                                                    \n"
							  + "  :dtInizioValidita,                                                         \n"
							  + "  null,                                                                      \n"
							  + "  :idUtente,                                                                 \n"
							  + "  null,                                                                      \n"
							  + "  CURRENT_DATE,                                                              \n"
							  + "  null )                                                                     \n";

			LOG.debug(query);
			
			Object[] args = new Object[]{idProgetto, descTipoEscussione, dtRichEscussione, numProtocolloRich, numProtocolloNotif
			, impRichiesto, note, dtInizioValidita, idUtente};
			
			int[] types = new int[]{Types.INTEGER, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR,
			Types.DATE, Types.INTEGER};
			
			getJdbcTemplate().update(query, args, types);
			  
			} catch (Exception e) {
				//result = false;
				//throw new ErroreGestitoException("Errore nell'inserire il record", e);
				esito.setEsito(false);
				esito.setMessaggio("Exception nell'inserimento dell'escussione: " + e.toString());
				return esito;
			}
		
		esito.setEsito(true);
		esito.setId(idEscussione);
		return esito;		
	}

    
    @Override
    @Transactional
    public EsitoDTO insertModificaEscussione(Date dtRichEscussione, String descTipoEscussione, String descStatoEscussione, BigDecimal impRichiesto, BigDecimal impApprovato, String numProtocolloRich, String numProtocolloNotif,
    		String causaleBonifico, String ibanBonifico, int idBanca, String descBanca, int idSoggProgBancaBen, int ProgrSoggettoProgetto, String note, Date dtInizioValidita, Date dataNotifica, int idProgetto, Long idUtente, int idEscussione) throws Exception {
    	String prf = "[RicercaGaranzieDAOImpl::insertModificaEscussione]";
    	LOG.info(prf + " BEGIN");

    	EsitoDTO esito = new EsitoDTO();

    	if (idEscussione == 0) {
    		esito.setEsito(false);
    		esito.setException("idEscussione non valorizzato.");
    		return esito;
    	}
    	/*if (idBanca == 0) {
    		esito.setEsito(false);
    		esito.setException("idBanca non valorizzato.");
    		return esito;
    	}
    	if (idSoggProgBancaBen == 0) {
    		esito.setEsito(false);
    		esito.setException("idBanca non valorizzato.");
    		return esito;
    		LOG.warn(prf + " idSoggProgBancaBen non valorizzato.");
    	}
    	if (ProgrSoggettoProgetto == 0) {
    		esito.setEsito(false);
    		esito.setException("idBanca non valorizzato.");
    		return esito;
    		LOG.warn(prf + " ProgrSoggettoProgetto non valorizzato.");
    	}*/
    	if (idProgetto == 0) {
    		//throw new InvalidParameterException("idProgetto non valorizzato.");
    		esito.setEsito(false);
    		esito.setException("idProgetto non valorizzato.");
    		return esito;
    	}
    	if (idUtente == 0) {
    		//throw new InvalidParameterException("idUtente non valorizzato.");
    		esito.setEsito(false);
    		esito.setException("idUtente non valorizzato.");
    		return esito;
    	}
    	if (descTipoEscussione == null || descTipoEscussione == "") {
    		esito.setEsito(false);
    		esito.setException("descTipoEscussione non valorizzato.");
    		return esito;
    	}
    	if (descStatoEscussione == null || descStatoEscussione == "") {
    		esito.setEsito(false);
    		esito.setException("descStatoEscussione non valorizzato.");
    		return esito;
    	}
    	if (descBanca == null || descBanca == "") {
    		/*esito.setEsito(false);
    		esito.setException("descBanca non valorizzato.");
    		return esito;*/
    		LOG.warn(prf + " descBanca non valorizzato.");
    	}
    	
    	//Long newIdBanca = null;
    	//if(descBanca != null && descBanca != "") {

    		/*try {
    			LOG.info(prf + " descBanca valorizzato, cerco l'id della banca per il nome '" + descBanca + "'.");
    			String queryIdBanca = "SELECT *\r\n"
    					+ "FROM (\r\n"
    					+ "	SELECT ban.ID_BANCA\r\n"
    					+ "	FROM PBANDI_D_BANCA ban\r\n"
    					+ "	WHERE ban.DESC_BANCA = '" + descBanca + "')\r\n"
    					+ "WHERE\r\n"
    					+ "	rownum = 1";
    			newIdBanca = getJdbcTemplate().queryForObject(queryIdBanca, Long.class);
    			LOG.info(prf + " newIdBanca: " + newIdBanca);

    		} catch (EmptyResultDataAccessException e) {
    			esito.setEsito(false);
    			esito.setException("Banca non trovata: " + e.toString());
    			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    			return esito;
    		} catch (Exception e) {
    			esito.setEsito(false);
    			esito.setException("Exception durante getIdBanca: " + e.toString());
    			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    			return esito;
    		}*/

    		//if(newIdBanca != idBanca) {
    			/*try {
    				LOG.info(prf + " La nuova banca non è identica a quella precedente, aggiorno prima il record precedente.");
    				String queryUpdatePrev = "UPDATE PBANDI_R_SOGG_PROG_BANCA_BEN \r\n"
    						+ "SET\r\n"
    						+ "	DT_FINE_VALIDITA = SYSDATE,\r\n"
    						+ "	ID_UTENTE_AGG = ?,\r\n"
    						+ "	DT_AGGIORNAMENTO = SYSDATE\r\n"
    						+ "WHERE\r\n"
    						+ "	ID_SOGG_PROG_BANCA_BEN = ?";

    				Object[] args3 = new Object[] { idUtente, idSoggProgBancaBen };
    				int[] types3 = new int[] { Types.NUMERIC, Types.NUMERIC };

    				getJdbcTemplate().update(queryUpdatePrev, args3, types3);
    				LOG.info(prf + " Record banca precedente aggiornato.");
    			} catch (Exception e) {
    				esito.setEsito(false);
    				esito.setException("Exception durante l'aggiornamento del record banca precedente: " + e.toString());
    				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    				return esito;
    			}*/

    			/*try {
    				LOG.info(prf + " Inserisco un uovo record con la nuova banca.");
    				String queryCopyAndInsert = "INSERT INTO PBANDI_R_SOGG_PROG_BANCA_BEN (\r\n"
    						+ "	ID_SOGG_PROG_BANCA_BEN,\r\n"
    						+ "	PROGR_SOGGETTO_PROGETTO,\r\n"
    						+ "	ID_BANCA,\r\n"
    						+ "	ID_ATTIVITA_BANCA_BEN,\r\n"
    						//+ "	DENOM_SOGGETTO_TERZO,\r\n"
    						+ "	DT_INIZIO_VALIDITA,\r\n"
    						+ "	ID_UTENTE_INS,\r\n"
    						+ "	DT_INSERIMENTO)\r\n"
    						+ "SELECT\r\n"
    						+ "	SEQ_PBANDI_R_SOGG_PROG_BAN_BEN.nextval,\r\n"
    						+ "	?,\r\n"
    						+ "	?,\r\n"
    						+ "	?,\r\n"
    						//+ "	?,\r\n"
    						+ "	SYSDATE,\r\n"
    						+ "	?,\r\n"
    						+ "	SYSDATE\r\n"
    						+ "FROM\r\n"
    						+ "	PBANDI_R_SOGG_PROG_BANCA_BEN \r\n"
    						+ "WHERE\r\n"
    						+ "	ID_SOGG_PROG_BANCA_BEN = ?";

    				Object[] args2 = new Object[] { ProgrSoggettoProgetto, newIdBanca, 10, idUtente, idSoggProgBancaBen };
    				int[] types2 = new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC , Types.NUMERIC };

    				getJdbcTemplate().update(queryCopyAndInsert, args2, types2);
    				LOG.info(prf + " Nuovo record della banca inserito correttamente.");
    			} catch (Exception e) {
    				esito.setEsito(false);
    				esito.setException("Exception durante l'inserimento del nuovo record banca: " + e.toString());
    				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    				return esito;
    			}*/
    		/*} else {
				LOG.info(prf + " La banca risulta identica a quella precedente, si ignora la modifica.");
			}
    	} else {
			LOG.info(prf + " descBanca non valorizzato, si presume che questo progetto non abbia ancora una banca associata o ci sia un errore, si ignora la modifica.");
		}*/
    	
    	
    	
    	Long newIdEscussione = null;
    	try {
    		//LOG.info(prf + " Prendo il nuovo idEscussione da restituire poi in FE.");
    		
    		String getIdEscussione = "select SEQ_PBANDI_T_ESCUSSIONE.NEXTVAL from dual";
    		newIdEscussione = getJdbcTemplate().queryForObject(getIdEscussione, Long.class);
    		
    		LOG.info(prf + " Nuovo idEscussione: " + newIdEscussione);
    	} catch (Exception e) {
    		esito.setEsito(false);
    		esito.setException("Exception in getIdEscussione: " + e.toString());
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		return esito;
    	}

    	try {
    		LOG.info(prf + " Inserisco una nuova escussione.");
    		StringBuilder query = new StringBuilder();
    		query.append("INSERT INTO PBANDI_T_ESCUSSIONE                                           \n"
    				+ " ( ID_ESCUSSIONE,                                                            \n"
    				+ "  ID_PROGETTO,                                                               \n"
    				+ "  ID_TIPO_ESCUSSIONE,                                                        \n"
    				+ "  ID_STATO_ESCUSSIONE,                                                       \n"
    				+ "  DT_RIC_RICH_ESCUSSIONE,                                                    \n"
    				+ "  NUM_PROTOCOLLO_RICH,                                                       \n"
    				+ "  DT_NOTIFICA,                                                               \n"
    				+ "  NUM_PROTOCOLLO_NOTIF,                                                      \n"
    				+ "  IMP_RICHIESTO,                                                             \n"
    				+ "  IMP_APPROVATO,                                                             \n"
    				+ "  CAUSALE_BONIFICO,                                                          \n"
    				+ "  IBAN_BONIFICO,                                                             \n"
    				+ "  NOTE,                                                                      \n"
    				//+ "  MOTIVO_RICH_INTEGRAZIONE,                                                \n"
    				+ "  DT_INIZIO_VALIDITA,                                                        \n"
    				+ "  DT_FINE_VALIDITA,                                                          \n"
    				+ "  ID_UTENTE_INS,                                                             \n"
    				+ "  ID_UTENTE_AGG,                                                             \n"
    				+ "  DT_INSERIMENTO,                                                            \n"
    				+ "  DT_AGGIORNAMENTO)                                                          \n"
    				+ " SELECT                                                                      \n"
    				+ " :newIdEscussione,                                          			        \n"
    				+ " :idProgetto,                                                                \n"
    				+ " (SELECT ID_TIPO_ESCUSSIONE from PBANDI_D_TIPO_ESCUSSIONE                    \n"
    				+ " where DESC_TIPO_ESCUSSIONE = :descTipoEscussione),                          \n"
    				+ " (SELECT ID_STATO_ESCUSSIONE from PBANDI_D_STATO_ESCUSSIONE pdse             \n"
    				+ " where DESC_STATO_ESCUSSIONE = :descStatoEscussione),                        \n"
    				+ "  :dtRichEscussione,                                                         \n"
    				+ "  :numProtocolloRich,                                                        \n"
    				+ "  :dataNotifica,                                                               \n"
    				+ "  :numProtocolloNotif,                                                       \n"
    				+ "  :impRichiesto,                                                             \n"
    				+ "  :impApprovato,                                                             \n"
    				+ "  :causaleBonifico,                                                          \n"
    				+ "  :ibanBonifico,                                                             \n"
    				+ "  :note,                                                                     \n"
    				//+ "  MOTIVO_RICH_INTEGRAZIONE,                                                \n"
    				+ "  :dtInizioValidita,                                                         \n"
    				+ "  null,                                                                      \n"
    				+ "  :idUtente,                                                                 \n"
    				+ "  null,                                                                      \n"
    				+ "  CURRENT_DATE,                                                              \n"
    				+ "  null                                                                       \n"
    				+ " from PBANDI_T_ESCUSSIONE                                                    \n"
    				+ " where ID_ESCUSSIONE = :idEscussione                                         \n");

    		//LOG.debug(prf + " Query nuova escussione: " + query);

    		Object[] args = new Object[]{newIdEscussione, idProgetto, descTipoEscussione, descStatoEscussione, dtRichEscussione, numProtocolloRich, dataNotifica, numProtocolloNotif,
    				impRichiesto, impApprovato, causaleBonifico, ibanBonifico, note, dtInizioValidita, idUtente, idEscussione};
    		int[] types = new int[]{Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.VARCHAR,
    				Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.INTEGER, Types.INTEGER};

    		getJdbcTemplate().update(query.toString(), args, types);
    		
    		LOG.info(prf + " Nuova escussione inserita.");
    	} catch (Exception e) {
    		//throw new ErroreGestitoException("Errore nell'inserire il record", e);
    		esito.setEsito(false);
			esito.setException("Exception nell'inserimento dell'escussione: " + e.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return esito;
    	}
    	
    	try {
    		LOG.info(prf + " Aggiorno il record dell'escussione precedente");
    		
			String query = 
					"update PBANDI_T_ESCUSSIONE                                     	\n"
							+ " set id_utente_agg= :idUtente,                           \n"
							+ " dt_fine_validita= :dtInizioValidita,                    \n"
							+ " dt_aggiornamento= CURRENT_DATE,							\n"
							+ "  dt_notifica = :dataNotifica	                     	\n"
							+ " where ID_ESCUSSIONE = :idEscussione    	                \n";

			//LOG.debug(query);
			Object[] args = new Object[]{idUtente, dtInizioValidita, dataNotifica, idEscussione};
			int[] types = new int[]{Types.INTEGER, Types.DATE, Types.DATE, Types.INTEGER};

			int rowsUpdated = getJdbcTemplate().update(query, args, types);
			LOG.info(prf + " Record precedente aggiornato, .update n. : " + rowsUpdated);
			
		} catch (Exception e) {
			//throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
			esito.setEsito(false);
			esito.setException("Exception durante l'aggiornamento del record escussione precedente: " + e.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return esito;
		}	
    	
    	
    	LOG.info(prf + " END");

    	esito.setEsito(true);
    	esito.setId(newIdEscussione);
    	return esito;
    }
	

	@Override
	public EsitoDTO UpdateEscussione(int idProgetto, Long idUtente, Date dtInizioValidita, Date dtNotifica, int idEscussione) {
		String prf = "[RicercaGaranzieDAOImpl::UpdateEscussione]";
		LOG.info(prf + " BEGIN");
		
		EsitoDTO esito = new EsitoDTO();
		
		try {
			String query = 
					"update PBANDI_T_ESCUSSIONE                                     	\n"
							+ " set id_utente_agg= :idUtente,                           \n"
							+ " dt_fine_validita= :dtInizioValidita,                    \n"
							+ " dt_aggiornamento= CURRENT_DATE,							\n"
							+ "  dt_notifica = :dtNotifica		                     	\n"
							+ " where ID_ESCUSSIONE = :idEscussione     	                \n";

			LOG.debug(query);

			Object[] args = new Object[]{idUtente, dtInizioValidita, dtNotifica, idEscussione};
			int[] types = new int[]{Types.INTEGER, Types.DATE, Types.DATE, Types.INTEGER};

			int rowsUpdated = getJdbcTemplate().update(query, args, types);
			LOG.info("Record aggiornati: " + rowsUpdated);
			
		} catch (Exception e) {
			//throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
			esito.setEsito(false);
			esito.setException("Exception durante l'aggiornamento del record precedente: " + e.toString());
		}	
		
		LOG.info(prf + " END");
		esito.setEsito(true);
		return esito;
			
	}

	@Override
	public EsitoDTO insertModificaStatoEscussione(String note, Date dtInizioValidita, int idEscussione, Long idUtente, Date dtNotifica, String descStatoEscussione) {
		String prf = "[RicercaGaranzieDAOImpl::insertModificaStatoEscussione]";
		LOG.info(prf + " BEGIN");
		
		EsitoDTO esito = new EsitoDTO();
		
		Long newIdEscussione = null;
		
		try {
			String getIdEscussione = "select SEQ_PBANDI_T_ESCUSSIONE.NEXTVAL from dual";
			newIdEscussione = getJdbcTemplate().queryForObject(getIdEscussione, Long.class);
		} catch (Exception e) {
			esito.setEsito(false);
			esito.setException("Exception in getNewIdEscussione: " + e.toString());
			return esito;

		}
		
		
		try {
			String query = 
					"INSERT INTO PBANDI_T_ESCUSSIONE                                                          \n"
							+ " ( ID_ESCUSSIONE,                                                            \n"
							+ "  ID_PROGETTO,                                                               \n"
							+ "  ID_TIPO_ESCUSSIONE,                                                        \n"
							+ "  ID_STATO_ESCUSSIONE,                                                       \n"
							+ "  DT_RIC_RICH_ESCUSSIONE,                                                    \n"
							+ "  NUM_PROTOCOLLO_RICH,                                                       \n"
							+ "  DT_NOTIFICA,                                                               \n"
							+ "  NUM_PROTOCOLLO_NOTIF,                                                      \n"
							+ "  IMP_RICHIESTO,                                                             \n"
							+ "  IMP_APPROVATO,                                                             \n"
							+ "  CAUSALE_BONIFICO,                                                          \n"
							+ "  IBAN_BONIFICO,                                                             \n"
							+ "  NOTE,                                                                      \n"
							//+ "  MOTIVO_RICH_INTEGRAZIONE,                                                  \n"
							+ "  DT_INIZIO_VALIDITA,                                                        \n"
							+ "  DT_FINE_VALIDITA,                                                          \n"
							+ "  ID_UTENTE_INS,                                                             \n"
							+ "  ID_UTENTE_AGG,                                                             \n"
							+ "  DT_INSERIMENTO,                                                            \n"
							+ "  DT_AGGIORNAMENTO)                                                          \n"
							+ " SELECT                                                                      \n"
							+ "  " + newIdEscussione + ",                                           \n"
							+ " id_progetto,                                                                \n"
							+ " id_tipo_escussione,                                                         \n"
							//+ " 2,                                                                          \n" // Modificato
							+ " (SELECT ID_STATO_ESCUSSIONE from PBANDI_D_STATO_ESCUSSIONE pdse  \r\n"
							+ " where DESC_STATO_ESCUSSIONE = :descStatoEscussione),                           \n"
							+ " DT_RIC_RICH_ESCUSSIONE,                                                     \n"
							+ " NUM_PROTOCOLLO_RICH,                                                        \n"
							+ "  :dtNotifica,                                                                      \n"
							+ "  NUM_PROTOCOLLO_NOTIF,                                                      \n"
							+ "  IMP_RICHIESTO,                                                             \n"
							+ "  IMP_APPROVATO,                                                             \n"
							+ "  CAUSALE_BONIFICO,                                                          \n"
							+ "  IBAN_BONIFICO,                                                             \n"
							+ "  :note,                                                                     \n"
							//+ "  MOTIVO_RICH_INTEGRAZIONE,                                                  \n"
							+ "  :dtInizioValidita,                                                         \n"
							+ "  null,                                                                      \n"
							+ "  :idUtente,                                                                 \n"
							+ "  null,                                                                      \n"
							+ "  CURRENT_DATE,                                                              \n"
							+ "  null                                                                       \n"
							+ "  from PBANDI_T_ESCUSSIONE                                                   \n"
							+ "  where id_escussione= :idEscussione                                         \n";


			LOG.debug(query);

			Object[] args = new Object[]{descStatoEscussione, dtNotifica, note, dtInizioValidita, idUtente, idEscussione};
			int[] types = new int[]{Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.DATE, Types.INTEGER, Types.INTEGER};

			int numEff = getJdbcTemplate().update(query, args, types);
			LOG.info("Record inseriti: " + numEff);
			
		} catch (Exception e) {
			//throw new ErroreGestitoException("Errore nell'inserire il record", e);
			esito.setEsito(false);
			esito.setException("Exception in insertEscussione: " + e.toString());
			return esito;
		}
		
		LOG.info(prf + " END");
		esito.setEsito(true);
		esito.setId(newIdEscussione);
		return esito;
	}

	@Override
	public Boolean salvaUploadAllegato(MultipartFormDataInput multipartFormData) {
		String prf = "[RicercaGaranzieDAOImpl::salvaUploadAllegato]";
		LOG.info(prf + " BEGIN");
		Boolean result; 
		
		try {
			Long idUtenteIns = multipartFormData.getFormDataPart("idUtenteIns", Long.class, null);
			String nomeFile = multipartFormData.getFormDataPart("nomeFile", String.class, null);
			File filePart = multipartFormData.getFormDataPart("file", File.class, null);
			BigDecimal idProgetto = multipartFormData.getFormDataPart("idProgetto", BigDecimal.class, null);
			
					
			// Legge il file firmato dal multipart.		
			Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
			List<InputPart> listInputPart = map.get("file");
			
			if (listInputPart == null) {
				LOG.info("listInputPart NULLO");
			} else {
				LOG.info("listInputPart SIZE = "+listInputPart.size());
			}
			
			for (InputPart i : listInputPart) {
				MultivaluedMap<String, String> m = i.getHeaders();
				Set<String> s = m.keySet();
				for (String x : s) {
					LOG.info("SET = "+x);
				}
			}
			
			FileDTO file = new FileDTO(); 
			file.setBytes(FileUtil.getBytesFromFile(filePart));
					//FileUtil.leggiFileDaMultipart(listInputPart, null, nomeFile);
			
			//Long idtipoindex= (long) 40;
			String descBreveTipoDocIndex =  getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_DOC_INDEX "
					+ "from PBANDI_C_TIPO_DOCUMENTO_INDEX where ID_TIPO_DOCUMENTO_INDEX=36", String.class);	
			if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
				throw new Exception("Tipo documento index non trovato.");					
			
			
			Date currentDate = new Date(System.currentTimeMillis());
			
			DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
			documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(36));
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdTarget(idProgetto);
			documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtenteIns));
			documentoIndexVO.setIdEntita(new BigDecimal(605));
			documentoIndexVO.setRepository(descBreveTipoDocIndex);
			documentoIndexVO.setUuidNodo("UUID");
			
			// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
		    result = documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, null);
		    
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	
	}
	
	@Override
    public void newAggiungiAllegato(Long idEscussione, Boolean letteraAccompagnatoria, int ambitoAllegato, byte[] allegato, String nomeAllegato, HttpServletRequest req) throws Exception {
        //  ambitoAllegato -> 0 = emissione, 1 = conferma provvedimento, 2 = ritiro in autotutela
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        String descBreveTipoDocIndex =  getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_DOC_INDEX "
				+ "from PBANDI_C_TIPO_DOCUMENTO_INDEX where ID_TIPO_DOCUMENTO_INDEX=36", String.class);	
		if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
			throw new Exception("Tipo documento index non trovato.");
        
        DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
 
        documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(36));
        documentoIndexVO.setIdEntita(new BigDecimal(605));
        documentoIndexVO.setIdTarget(BigDecimal.valueOf(idEscussione));
        documentoIndexVO.setDtInserimentoIndex(new java.sql.Date((new Date().getTime())));
        documentoIndexVO.setIdUtenteIns(BigDecimal.valueOf(userInfoSec.getIdUtente()));
        documentoIndexVO.setNomeFile(nomeAllegato);
        //documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
        documentoIndexVO.setRepository(descBreveTipoDocIndex);
        /*Dati Obbligatori*/
        documentoIndexVO.setUuidNodo("UUID");

        //salva allegato
        if(!documentoManager.salvaFileConVisibilita(allegato, documentoIndexVO, null)) {
            throw new ErroreGestitoException("Errore durante il salvataggio dell'allegato");
        }

        LOG.info(prf + "END");
    }
	
	@Override
	public Boolean aggiornaAllegati(ArrayList<Integer> allegatiPresenti, Long idTarget){
			String prf = "[PassaggioPerditaDAOImpl:: aggiornaAllegati]";
			logger.info(prf + " BEGIN");
			
			Boolean result = null; 
			try {
				for (Integer item : allegatiPresenti) {
									
					String queryModifica = "UPDATE PBANDI.PBANDI_T_DOCUMENTO_INDEX\r\n"
							+ "SET ID_TARGET = " + idTarget + "\r\n"
							+ "WHERE ID_DOCUMENTO_INDEX = " + item;

					getJdbcTemplate().update(queryModifica);
					
					logger.info(prf + " The document idIndex: " + item + " has been updated with idTarget: " + idTarget);
				}
				
				
				
			} catch (Exception e) {
				logger.error("errore durante l'update di id_target in pbandi_t_documento_index "+e);
				result= false; 
			}
			
			
			logger.info(prf + " END");
			return result;
		}

	@Override
	public List<VisualizzaAllegatiVO> getAllegati(Long idEscussione) {
		String prf = "[RicercaGaranzieDAOImpl::getAllegati]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaAllegatiVO> va;
		try {
		String query =
				"select nome_file, ID_DOCUMENTO_INDEX from PBANDI_T_DOCUMENTO_INDEX   \r\n"
				+ "where id_tipo_documento_index = 36             \r\n"
				+ "and id_entita = 605                            \r\n"
				+ "and id_target = :idEscussione        \r\n"
				+ "ORDER BY DT_INSERIMENTO_INDEX DESC";
		
		LOG.debug(query);

		Object[] args = new Object[]{idEscussione};
		int[] types = new int[]{Types.INTEGER};  

		va = getJdbcTemplate().query(query, args, types, new ResultSetExtractor<List<VisualizzaAllegatiVO>>() {
			@Override
			public List<VisualizzaAllegatiVO> extractData(ResultSet rs) throws SQLException {
				List<VisualizzaAllegatiVO> elencoDati = new ArrayList<>();
				
				
				while(rs.next())
				{
				 VisualizzaAllegatiVO item= new VisualizzaAllegatiVO();
			     item.setNomeFile(rs.getString("nome_file"));
			     item.setIdDocIndex(rs.getInt("ID_DOCUMENTO_INDEX"));
			     elencoDati.add(item);
				}
			return elencoDati;		
			}});
	}catch (RecordNotFoundException e) {
		throw e;
	} finally {
		LOG.info(" END");
	}
	return va;	

	}

	@Override
	public Boolean deleteAllegato(int idDocIndex) {
		String prf = "[RicercaGaranzieDAOImpl::deleteAllegato]";
		LOG.info(prf + " BEGIN");
		int rowsUpdated = 0;
		boolean eseguito = false;
			String query = 
					"DELETE FROM PBANDI_T_DOCUMENTO_INDEX   \n"
					+ "where id_tipo_documento_index = 36   \n"
					+ "and id_entita = 605                  \n"
					+ "and ID_DOCUMENTO_INDEX = :idDocIndex            \n";

			LOG.debug(query);
			
			Object[] args = new Object[]{idDocIndex};
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
	public void insertModificaStatoEscussioneRicIntegrazione(String note, Date dtInizioValidita, int idEscussione,
			int idUtente, String descStatoEscussione, Date dtNotifica) {
		String prf = "[RicercaGaranzieDAOImpl::insertModificaStatoEscussioneRicIntegrazione]";
		LOG.info(prf + " BEGIN");
			String query = 
					"INSERT INTO PBANDI_T_ESCUSSIONE                                                          \n"
				    		  + " ( ID_ESCUSSIONE,                                                            \n"
				    		  + "  ID_PROGETTO,                                                               \n"
				    		  + "  ID_TIPO_ESCUSSIONE,                                                        \n"
				    		  + "  ID_STATO_ESCUSSIONE,                                                       \n"
				    		  + "  DT_RIC_RICH_ESCUSSIONE,                                                    \n"
				    		  + "  NUM_PROTOCOLLO_RICH,                                                       \n"
				    		  + "  DT_NOTIFICA,                                                               \n"
				    		  + "  NUM_PROTOCOLLO_NOTIF,                                                      \n"
				    		  + "  IMP_RICHIESTO,                                                             \n"
							  + "  IMP_APPROVATO,                                                             \n"
							  + "  CAUSALE_BONIFICO,                                                          \n"
							  + "  IBAN_BONIFICO,                                                             \n"
							  + "  NOTE,                                                                      \n"
							  + "  ID_DISTINTA,                                                               \n"
							  + "  DT_INIZIO_VALIDITA,                                                        \n"
							  + "  DT_FINE_VALIDITA,                                                          \n"
							  + "  ID_UTENTE_INS,                                                             \n"
							  + "  ID_UTENTE_AGG,                                                             \n"
							  + "  DT_INSERIMENTO,                                                            \n"
							  + "  DT_AGGIORNAMENTO)                                                          \n"
				    		  + " SELECT                                                                      \n"
				    		  + "  SEQ_PBANDI_T_ESCUSSIONE.NEXTVAL,                                           \n"
                              + " id_progetto,                                                                \n"
				    		  + " id_tipo_escussione,                                                         \n"
                              + " (SELECT ID_STATO_ESCUSSIONE from PBANDI_D_STATO_ESCUSSIONE                    \n"
                              + "  where DESC_STATO_ESCUSSIONE= :descStatoEscussione),                          \n"
				    		  + " DT_RIC_RICH_ESCUSSIONE,                                                     \n"
                              + " NUM_PROTOCOLLO_RICH,                                                        \n"
				    		  + "  :dtNotifica,                                                                      \n"
				    		  + "  NUM_PROTOCOLLO_NOTIF,                                                      \n"
				    		  + "  IMP_RICHIESTO,                                                             \n"
				    		  + "  IMP_APPROVATO,                                                             \n"
				    		  + "  CAUSALE_BONIFICO,                                                          \n"
							  + "  IBAN_BONIFICO,                                                             \n"
							  + "  :note,                                                                     \n"
							  + "  ID_DISTINTA,                                                               \n"
							  + "  :dtInizioValidita,                                                         \n"
							  + "  null,                                                                      \n"
							  + "  :idUtente,                                                                 \n"
							  + "  null,                                                                      \n"
							  + "  CURRENT_DATE,                                                              \n"
							  + "  null                                                                       \n"
							  + "  from PBANDI_T_ESCUSSIONE                                                   \n"
							  + "  where id_escussione= :idEscussione                                         \n";


			LOG.debug(query);
			
			Object[] args = new Object[]{ descStatoEscussione, dtNotifica, note, dtInizioValidita, idUtente, idEscussione};
			int[] types = new int[]{Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.DATE, Types.INTEGER, Types.INTEGER};
			
	
			try {
			  getJdbcTemplate().update(query, args, types);
			} catch (Exception e) {
				throw new ErroreGestitoException("Errore nell'inserire il record", e);
			}
		
	}

	@Override
	public Boolean getAbilitazione(String ruolo) {
		String prf = "[RicercaGaranzieDAOImpl::getAbilitazione]";
		LOG.info(prf + " BEGIN");
		int trovato = 0;
		boolean eseguito = false;
			String query = 
					"SELECT count(*) ordine                                                                         \n"
					+ "FROM PBANDI_R_TIPO_STATO_ITER                                                                \n"
					+ "WHERE ID_TIPO_ITER = 14                                                                      \n"
					+ "AND id_incarico = (SELECT id_incarico FROM PBANDI_D_INCARICO WHERE desc_incarico= :ruolo)    \n"
					+ "AND ORDINE = 1                                                                               \n";

			LOG.debug(query);
			
			Object[] args = new Object[]{ruolo};
			int[] types = new int[]{Types.VARCHAR};
			
	
			try {
				trovato = getJdbcTemplate().update(query, args, types);
				
			} catch (Exception e) {
				throw new ErroreGestitoException("Errore nel trovare il record il record", e);
			}	
			if (trovato > 0) {
				eseguito= true;
			}
			
			LOG.info("N. record trovati:\n" + trovato);
			LOG.info(prf + " end");
			return eseguito;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public EsitoDTO insertRichiestaIntegrazELettera(Boolean sendLettera, Long idProgetto, Long idUtente, Long idEscussione, Long nGgScadenza, MultipartFormDataInput multipartFormData, Long idTipoDocumentoIndex) throws Exception {
		String prf = "[RicercaGaranzieDAOImpl::insertRichiestaIntegrazELettera]";
		LOG.info(prf + " BEGIN");

		EsitoDTO esito = new EsitoDTO();
		
		Long newIdRichInt = null;

		try {
			String getIdRichInt = "select SEQ_PBANDI_T_RICH_INTEGRAZ.nextval from dual";
			newIdRichInt = getJdbcTemplate().queryForObject(getIdRichInt, Long.class);
		} catch (Exception e) {
			esito.setEsito(false);
			esito.setException("Exception in getIdRichInt: " + e.toString());
			return esito;

		}
		
		// Aggiungo una nuova richiesta di integrazione
		try {
			LOG.info(prf + " nGgScadenza: " + nGgScadenza);
			if(nGgScadenza.equals(-1L)) {
				nGgScadenza = null;
			}
			
			StringBuilder query = new StringBuilder("INSERT INTO PBANDI_T_RICHIESTA_INTEGRAZ\r\n"
					+ "(ID_RICHIESTA_INTEGRAZ,\r\n"
					+ "ID_ENTITA, \r\n"
					+ "ID_TARGET,\r\n"
					+ "DT_RICHIESTA,\r\n"
					+ "ID_UTENTE_RICHIESTA,\r\n"
					+ "NUM_GIORNI_SCADENZA,\r\n"
					+ "ID_STATO_RICHIESTA,\r\n"
					+ "DT_INIZIO_VALIDITA,\r\n"
					+ "ID_UTENTE_INS,\r\n"
					+ "DT_INSERIMENTO)\r\n"
					+ "VALUES\r\n"
					+ "(:newIdRichInt,\r\n"
					+ "605,\r\n"
					+ ":idEscussione,\r\n"
					+ "SYSDATE,\r\n"
					+ ":idUtente,\r\n"
					+ ":nGgScadenza,\r\n"
					+ "4,\r\n"
					+ "SYSDATE,\r\n"
					+ ":idUtente,\r\n"
					+ "SYSDATE)");

			LOG.info(query);

			Object[] args = new Object[]{newIdRichInt, idEscussione, idUtente, nGgScadenza, idUtente};
			int[] types = new int[]{Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER};

			int rowsUpdated = getJdbcTemplate().update(query.toString(), args, types);

			LOG.info(prf + "Record inseriti in PBANDI_T_RICHIESTA_INTEGRAZ: " + rowsUpdated);

		} catch (Exception e) {
			LOG.error(e);
			esito.setEsito(false);
			esito.setException("Exception durante l'inserimento dell'integrazione: " + e.toString());
			return esito;
		}

		if(sendLettera) {
			// Salvo la lettera accompagnatoria
			try {
				Long idUtenteIns = idUtente; //multipartFormData.getFormDataPart("idUtenteIns", Long.class, null);
				String nomeFile = multipartFormData.getFormDataPart("nomeFile", String.class, null);
				File filePart = multipartFormData.getFormDataPart("file", File.class, null); 
				BigDecimal idTarget = BigDecimal.valueOf(idEscussione); //multipartFormData.getFormDataPart("idTarget", BigDecimal.class, null);

				LOG.info(prf + "idUtenteIns=" + idUtenteIns);
				LOG.info(prf + "nomeFile=" + nomeFile);
				LOG.info(prf + "idTarget=" + idTarget);

				// Legge il file firmato dal multipart.		
				Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
				List<InputPart> listInputPart = map.get("file");

				if (listInputPart == null) {
					LOG.info(prf + "listInputPart NULLO");
				} else {
					LOG.info(prf + "listInputPart SIZE = "+listInputPart.size());
				}

				for (InputPart i : listInputPart) {
					MultivaluedMap<String, String> m = i.getHeaders();
					Set<String> s = m.keySet();
					for (String x : s) {
						LOG.info(prf + "SET = "+x);
					}
				}

				FileDTO file = new FileDTO(); 
				file.setBytes(FileUtil.getBytesFromFile(filePart));
				//FileUtil.leggiFileDaMultipart(listInputPart, null, nomeFile);

				//Long idtipoindex= (long) 40;
				String descBreveTipoDocIndex =  getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_DOC_INDEX "
						+ "from PBANDI_C_TIPO_DOCUMENTO_INDEX where ID_TIPO_DOCUMENTO_INDEX=37", String.class);	
				if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
					throw new Exception("Tipo documento index non trovato.");					

				Date currentDate = new Date(System.currentTimeMillis());

				DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
				documentoIndexVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
				documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(idTipoDocumentoIndex));
				documentoIndexVO.setNomeFile(nomeFile);
				documentoIndexVO.setIdTarget(idTarget);
				documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
				documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtenteIns));
				documentoIndexVO.setIdEntita(new BigDecimal(605)); // 569
				documentoIndexVO.setRepository(descBreveTipoDocIndex);
				documentoIndexVO.setUuidNodo("UUID");

				Boolean res = documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, null);
				LOG.info("Il servizio salvaFileConVisibilita ha restituito " + res);
				if(!res) {
					//LOG.info("Lancio una bella Exception");
					//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					//throw new Exception();
					esito.setEsito(false);
					esito.setException("Exception durante l'inserimento del file: Il servizio salvaFileConVisibilita ha restituito " + res);
					return esito;
				}

			} catch (Exception e) {
				//e.printStackTrace();
				//result = false;
				esito.setEsito(false);
				esito.setException("Exception durante il salvataggio della lettera: " + e.toString());
				return esito;
			}
		}

		LOG.info(prf + " END");
		
		esito.setEsito(true);
		esito.setId(newIdRichInt);
		return esito;
		
	}


	@Override
	public Boolean salvaUploadRichiestaIntegraz(MultipartFormDataInput multipartFormData) {
		String prf = "[RicercaGaranzieDAOImpl::salvaUploadRichiestaIntegraz]";
		LOG.info(prf + " BEGIN");
		Boolean result; 
		
		try {
			Long idUtenteIns = multipartFormData.getFormDataPart("idUtenteIns", Long.class, null);
			String nomeFile = multipartFormData.getFormDataPart("nomeFile", String.class, null);
			File filePart = multipartFormData.getFormDataPart("file", File.class, null); 
			BigDecimal idTarget = multipartFormData.getFormDataPart("idTarget", BigDecimal.class, null);
			
					
			// Legge il file firmato dal multipart.		
			Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
			List<InputPart> listInputPart = map.get("file");
			
			if (listInputPart == null) {
				LOG.info("listInputPart NULLO");
			} else {
				LOG.info("listInputPart SIZE = "+listInputPart.size());
			}
			
			for (InputPart i : listInputPart) {
				MultivaluedMap<String, String> m = i.getHeaders();
				Set<String> s = m.keySet();
				for (String x : s) {
					LOG.info("SET = "+x);
				}
			}
			
			FileDTO file = new FileDTO(); 
			file.setBytes(FileUtil.getBytesFromFile(filePart));
					//FileUtil.leggiFileDaMultipart(listInputPart, null, nomeFile);
			
			//Long idtipoindex= (long) 40;
			String descBreveTipoDocIndex =  getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_DOC_INDEX "
					+ "from PBANDI_C_TIPO_DOCUMENTO_INDEX where ID_TIPO_DOCUMENTO_INDEX=37", String.class);	
			if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
				throw new Exception("Tipo documento index non trovato.");					
			
			
			Date currentDate = new Date(System.currentTimeMillis());
			
			DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
			documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(37));
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdTarget(idTarget);
			documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtenteIns));
			documentoIndexVO.setIdEntita(new BigDecimal(569));
			documentoIndexVO.setRepository(descBreveTipoDocIndex);
			documentoIndexVO.setUuidNodo("UUID");
			
			// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
		    result = documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, null);
		    
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}

	@Override
	public int getRichIntegr(String idEscussione) {
		String prf = "[RicercaGaranzieDAOImpl::getRichIntegr]";
		LOG.info(prf + " BEGIN");
		int id= 0;
		try {

			String sql =" SELECT                                                        \n"
					+ "   id_stato_richiesta                                            \n"
					+ " FROM                                                            \n"
					+ "   PBANDI_T_RICHIESTA_INTEGRAZ                                   \n"
					+ "  WHERE ID_ENTITA= 605 and id_target= :idEscussione              \n";
			 


			LOG.debug(sql);

			Object[] args = new Object[]{idEscussione};
			int[] types = new int[]{Types.INTEGER};  

			id = getJdbcTemplate().query(sql, args, types, new ResultSetExtractor<Integer>(){
				@Override
				public Integer extractData(ResultSet rs) throws SQLException{
				int idEsc = 0;
				while(rs.next())
				{
					idEsc=  rs.getInt("id_stato_richiesta");
				}	
				return idEsc;		
				}});
		}catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return id;
	}

	@Override
	public Boolean salvaUploadLettera(MultipartFormDataInput multipartFormData) {
		String prf = "[RicercaGaranzieDAOImpl::salvaUploadLettera]";
		LOG.info(prf + " BEGIN");
		Boolean result; 
		
		try {
			Long idUtenteIns = multipartFormData.getFormDataPart("idUtenteIns", Long.class, null);
			String nomeFile = multipartFormData.getFormDataPart("nomeFile", String.class, null);
			File filePart = multipartFormData.getFormDataPart("file", File.class, null); 
			BigDecimal idTarget = multipartFormData.getFormDataPart("idTarget", BigDecimal.class, null);
			
					
			// Legge il file firmato dal multipart.		
			Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
			List<InputPart> listInputPart = map.get("file");
			
			if (listInputPart == null) {
				LOG.info("listInputPart NULLO");
			} else {
				LOG.info("listInputPart SIZE = "+listInputPart.size());
			}
			
			for (InputPart i : listInputPart) {
				MultivaluedMap<String, String> m = i.getHeaders();
				Set<String> s = m.keySet();
				for (String x : s) {
					LOG.info("SET = "+x);
				}
			}
			
			FileDTO file = new FileDTO(); 
			file.setBytes(FileUtil.getBytesFromFile(filePart));
					//FileUtil.leggiFileDaMultipart(listInputPart, null, nomeFile);
			
			//Long idtipoindex= (long) 40;
			String descBreveTipoDocIndex =  getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_DOC_INDEX "
					+ "from PBANDI_C_TIPO_DOCUMENTO_INDEX where ID_TIPO_DOCUMENTO_INDEX=37", String.class);	
			if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
				throw new Exception("Tipo documento index non trovato.");					
			
			
			Date currentDate = new Date(System.currentTimeMillis());
			
			DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
			documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(36));
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdTarget(idTarget);
			documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtenteIns));
			documentoIndexVO.setIdEntita(new BigDecimal(569));
			documentoIndexVO.setRepository(descBreveTipoDocIndex);
			documentoIndexVO.setUuidNodo("UUID");
			
			// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
		    result = documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, null);
		    
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}

	@Override
	public void updateIdDistinta(Long idTarget) {
		String prf = "[RicercaGaranzieDAOImpl::updateIdDistinta]";
		LOG.info(prf + " BEGIN");
		int rowsUpdated = 0;
			String query =    
					"update PBANDI_T_ESCUSSIONE                                             	                                        \n"
							+ " set id_distinta= (select max(id_distinta) from PBANDI_T_DISTINTA                                        \n"
							+ "where id_entita= 605 and id_modalita_agevolazione= 10 and id_stato_distinta= 1)                       	\n"
							+ " where id_escussione = :idTarget     	                                                                \n";

			LOG.debug(query);
			
			Object[] args = new Object[]{idTarget};
			int[] types = new int[]{Types.INTEGER};
			
	
			try {
				rowsUpdated = getJdbcTemplate().update(query, args, types);
			} catch (Exception e) {
				throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
			}	
			
			LOG.info("N. record aggiornati:\n" + rowsUpdated);
	}

	@Override
	public void insertDistinta(Long idUtente, Long idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::insertDistinta]";
		LOG.info(prf + " BEGIN");
			String query = 
					"INSERT INTO PBANDI_T_DISTINTA                                                            \n"
				    		  + " ( ID_DISTINTA ,                                                             \n"
				    		  + "  ID_ENTITA,                                                                 \n"
				    		  + "  ID_TIPO_DISTINTA ,                                                         \n"
				    		  + "  ID_MODALITA_AGEVOLAZIONE,                                                  \n"
				    		  + "  DESCRIZIONE ,                                                              \n"
				    		  + "  ID_ESTREMI_BANCARI,                                                        \n"
				    		  + "  ID_STATO_DISTINTA,                                                         \n"
				    		  + "  DT_INIZIO_VALIDITA,                                                        \n"
				    		  + "  ID_UTENTE_INS,                                                             \n"
				    		  + "  DT_INSERIMENTO)                                                            \n"
				    		  + " VALUES                                                                      \n"
				    		  + "(seq_pbandi_t_distinta.NEXTVAL,                                              \n"
				    		  + "605,                                                                         \n"
				    		  + " (SELECT id_tipo_distinta from PBANDI_D_TIPO_DISTINTA                        \n"
                              + " where desc_breve_tipo_distinta= 'ES'),                                      \n"
				    		  + " 10,                                                                         \n"
				    		  + "  null,                                                                      \n"
				    		  + "  (SELECT ID_ESTREMI_BANCARI                                                 \n"
				    		  + "FROM PBANDI_R_SOGGETTO_PROGETTO                                              \n"
				    		  + "WHERE                                                                        \n"
				    		  + " ID_PROGETTO  = :idProgetto AND                                              \n"
				    		  + "ID_TIPO_ANAGRAFICA =1 AND                                                    \n"
				    		  + "ID_TIPO_BENEFICIARIO ^= 4                                                    \n"
				    		  + "),                                                                           \n"
				    		  + "  1,                                                                         \n"
				    		  + "  CURRENT_DATE,                                                              \n"
				    		  + "  :idUtente,                                                                   \n"
				    		  + " CURRENT_DATE)                                                               \n";

			LOG.debug(query);
			
			Object[] args = new Object[]{idProgetto, idUtente};
			int[] types = new int[]{Types.INTEGER, Types.VARCHAR};
			
	
			try {
			  getJdbcTemplate().update(query, args, types);
			} catch (Exception e) {
				throw new ErroreGestitoException("Errore nell'inserire il record", e);
			}
			LOG.info(prf + " END");
		
	}

	@Override
	public void insertDettaglio(Long idTarget, Long idUtente) {
		String prf = "[RicercaGaranzieDAOImpl::insertDettaglio]";
		LOG.info(prf + " BEGIN");

		Long idDistinta = getJdbcTemplate().queryForObject("(select max(id_distinta) from PBANDI_T_DISTINTA where id_entita = 605 and id_modalita_agevolazione = 10 and id_stato_distinta = 1)", Long.class);
		Long rigaDistinta = getJdbcTemplate().queryForObject("SELECT COUNT(1) FROM PBANDI_T_DISTINTA_DETT ptdd WHERE ptdd.ID_DISTINTA = ?", Long.class, idDistinta);

		String query =
					"INSERT INTO PBANDI_T_DISTINTA_DETT                                                       \n"
				    		  + " ( ID_DISTINTA_DETT,                                                         \n"
				    		  + "  ID_DISTINTA,                                                               \n"
				    		  + "  RIGA_DISTINTA,                                                             \n"
				    		  + "  ID_TARGET,                                                                 \n"
				    		  + "  DT_INIZIO_VALIDITA ,                                                       \n"
				    		  + "  ID_UTENTE_INS,                                                             \n"
				    		  + "  DT_INSERIMENTO)                                                            \n"
				    		  + " VALUES                                                                      \n"
				    		  + "(seq_PBANDI_T_DISTINTA_DETT.NEXTVAL,                                         \n"
				    		  + " :idDistinta,                                                                \n"
				    		  + " :rigaDistinta,                                                              \n"
				    		  + " :idTarget,                                                                  \n"
				    		  + " CURRENT_DATE,                                                               \n"
				    		  + "  :idUtente,                                                                 \n"
				    		  + "  CURRENT_DATE)                                                              \n";

			LOG.debug(query);
			
			Object[] args = new Object[]{idTarget, idDistinta, rigaDistinta, idUtente};
			int[] types = new int[]{Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR};
			
	
			try {
			  getJdbcTemplate().update(query, args, types);
			} catch (Exception e) {
				LOG.error(e);
				throw new ErroreGestitoException("Errore nell'inserire il record", e);
			}
		
	}

	@Override
	public List<GaranziaVO> getListaStatiCredito() {
		String prf = "[RicercaGaranzieDAOImpl::getListaStatiCredito]";
		LOG.info(prf + " BEGIN");
		List<GaranziaVO> lista = new ArrayList<GaranziaVO>();
		
		try {
			String query = "SELECT DISTINCT DESC_STATO_CREDITO_FP \r\n"
					+ "FROM PBANDI_D_STATO_CREDITO_FP\r\n"
					+ "ORDER BY DESC_STATO_CREDITO_FP";
			
			RowMapper<GaranziaVO> rw = new RowMapper<GaranziaVO>() {
				
				@Override
				public GaranziaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					GaranziaVO gar = new GaranziaVO();
					gar.setStatoCredito(rs.getString("DESC_STATO_CREDITO_FP"));
					return gar;
				}
				
			};
			
			lista = getJdbcTemplate().query(query, rw);
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_D_STATO_ESCUSSIONE", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return lista;
	}

	@Override
	public GaranziaVO getGaranzia(BigDecimal idEscussione, BigDecimal idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getGaranzia]";
		LOG.info(prf + " BEGIN");
		
		GaranziaVO garanzia = new GaranziaVO();
		
		try {
			List<GaranziaVO> lista = null;
			StringBuilder query = new StringBuilder();
			query.append("SELECT DISTINCT\r\n"
					+ "	ptb.ID_BANDO,\r\n"
					+ "	ptb.TITOLO_BANDO,\r\n"
					+ "	ptp.ID_PROGETTO,\r\n"
					+ "	ptp.CODICE_VISUALIZZATO,\r\n"
					+ "	prsp.ID_SOGGETTO,\r\n"
					+ "	pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
					+ "	pte.ID_ESCUSSIONE,\r\n"
					+ "	pte.DT_RIC_RICH_ESCUSSIONE,\r\n"
					+ "	pte.ID_TIPO_ESCUSSIONE,\r\n"
					+ "	pdte.DESC_TIPO_ESCUSSIONE,\r\n"
					+ "	pte.ID_STATO_ESCUSSIONE,\r\n"
					+ "	pdse.DESC_STATO_ESCUSSIONE,\r\n"
					+ "	pte.DT_INIZIO_VALIDITA,\r\n"
					+ "	pte.IMP_RICHIESTO,\r\n"
					+ "	pte.IMP_APPROVATO,\r\n"
					+ "	pts.CODICE_FISCALE_SOGGETTO,\r\n"
					+ "	pts.ndg,\r\n"
					+ "	pts2.PARTITA_IVA,\r\n"
					+ "	pte.DT_INSERIMENTO,\r\n"
					+ "	pte.DT_NOTIFICA,\r\n"
					+ "	pte.NUM_PROTOCOLLO_RICH,\r\n"
					+ "	pte.NUM_PROTOCOLLO_NOTIF,\r\n"
					+ "	pte.CAUSALE_BONIFICO,\r\n"
					+ "	pte.IBAN_BONIFICO AS iban_banca_benef,\r\n"
					+ "	pteb.ID_BANCA,\r\n"
					+ "	pdb.COD_BANCA,\r\n"
					+ "	pdb.DESC_BANCA,\r\n"
					+ "	pte.NOTE,\r\n"
					+ "	prsp.PROGR_SOGGETTO_PROGETTO,\r\n"
					+ "	pte.FLAG_ESITO\r\n"
					+ "FROM PBANDI_T_PROGETTO ptp\r\n"
					+ "	LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
					+ "	LEFT JOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptd.ID_DOMANDA = ptce.ID_DOMANDA\r\n"
					+ "	LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema ON ptce.ID_CONTO_ECONOMICO = prcema.ID_CONTO_ECONOMICO\r\n"
					+ "	LEFT JOIN PBANDI_D_STATO_CONTO_ECONOMICO pdsce ON pdsce.ID_STATO_CONTO_ECONOMICO = ptce.ID_STATO_CONTO_ECONOMICO\r\n"
					+ "	LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "	LEFT JOIN PBANDI_T_BANDO ptb ON ptb.ID_BANDO = prbli.ID_BANDO\r\n"
					+ "	LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO AND prsp.DT_FINE_VALIDITA IS NULL\r\n"
					+ "	LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
					+ "	LEFT JOIN PBANDI_T_ESCUSSIONE pte ON ptp.ID_PROGETTO = pte.ID_PROGETTO\r\n"
					+ "	LEFT JOIN PBANDI_T_ESTREMI_BANCARI pteb ON pteb.IBAN = pte.IBAN_BONIFICO AND pteb.ID_BANCA IS NOT NULL\r\n"
					+ "	LEFT JOIN PBANDI_D_BANCA pdb ON pdb.ID_BANCA = pteb.ID_BANCA\r\n"
					+ "	LEFT JOIN PBANDI_D_TIPO_ESCUSSIONE pdte ON pdte.ID_TIPO_ESCUSSIONE = pte.ID_TIPO_ESCUSSIONE\r\n"
					+ "	LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
					+ "	LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsp.PROGR_SOGGETTO_PROGETTO = prsps.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "	LEFT JOIN PBANDI_T_SEDE pts2 ON pts2.ID_SEDE = prsps.ID_SEDE\r\n"
					+ "	LEFT JOIN PBANDI_D_STATO_ESCUSSIONE pdse ON pdse.ID_STATO_ESCUSSIONE = pte.ID_STATO_ESCUSSIONE\r\n"
					+ "	--LEFT JOIN pbandi_r_sogg_prog_banca_ben prspbb ON prsp.PROGR_SOGGETTO_PROGETTO = prspbb.PROGR_SOGGETTO_PROGETTO AND prspbb.DT_FINE_VALIDITA IS null\r\n"
					+ "	--LEFT JOIN pbandi_d_banca pdba ON pdba.ID_BANCA = prspbb.ID_BANCA\r\n"
					+ "WHERE prsp.ID_TIPO_ANAGRAFICA = '1'\r\n"
					+ "	AND prsp.ID_TIPO_BENEFICIARIO <> '4'\r\n"
					+ "	AND prcema.ID_MODALITA_AGEVOLAZIONE = 10\r\n"
					+ "	AND prsp.ID_PROGETTO = " + idProgetto + "\r\n");
					if (idEscussione.compareTo(BigDecimal.ZERO) != 0) {
						query.append("	AND pte.id_escussione = " + idEscussione + "\r\n");
					}
			
			RowMapper<GaranziaVO> rw = new RowMapper<GaranziaVO>() {
				
				@Override
				public GaranziaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					GaranziaVO gar = new GaranziaVO();
					gar.setIdProgetto(rs.getLong("ID_PROGETTO"));
					gar.setDescrizioneBando(rs.getString("TITOLO_BANDO"));
					gar.setCodiceProgetto(rs.getString("CODICE_VISUALIZZATO"));
					gar.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
					gar.setNag(rs.getString("ID_SOGGETTO"));
					gar.setNdg(rs.getString("NDG"));
					gar.setPartitaIva(rs.getString("PARTITA_IVA"));
					gar.setDenominazioneCognomeNome(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
					gar.setIdStatoEscussione(rs.getLong("ID_STATO_ESCUSSIONE"));
					gar.setStatoEscussione(rs.getString("DESC_STATO_ESCUSSIONE"));
					gar.setCodBanca(rs.getString("COD_BANCA"));
					gar.setDenominazioneBanca(rs.getString("DESC_BANCA"));
					gar.setDataRicevimentoEscussione(rs.getDate("DT_RIC_RICH_ESCUSSIONE"));
					gar.setIdTipoEscussione(rs.getLong("ID_TIPO_ESCUSSIONE"));
					gar.setTipoEscussione(rs.getString("DESC_TIPO_ESCUSSIONE"));
					gar.setDataStato(rs.getDate("DT_INIZIO_VALIDITA"));
					gar.setImportoRichiesto(rs.getDouble("IMP_RICHIESTO"));
					gar.setImportoApprovato(rs.getDouble("IMP_APPROVATO"));
					gar.setIdEscussione(rs.getBigDecimal("ID_ESCUSSIONE"));
					gar.setNumProtoRichiesta(rs.getString("NUM_PROTOCOLLO_RICH"));
					gar.setDataNotifica(rs.getDate("DT_NOTIFICA"));
					gar.setNumProtoNotifica(rs.getString("NUM_PROTOCOLLO_NOTIF"));
					gar.setCausaleBonifico(rs.getString("CAUSALE_BONIFICO"));
					gar.setIbanBancaBenef(rs.getString("IBAN_BANCA_BENEF"));
					gar.setNote(rs.getString("NOTE"));
					gar.setIdBanca(rs.getLong("ID_BANCA"));
					gar.setProgrSoggettoProgetto(rs.getString("PROGR_SOGGETTO_PROGETTO"));
					
					String flag = rs.getString("FLAG_ESITO");
					LOG.info(prf + " Flag esito: " + flag);
					if("P".equals(flag)) {
						gar.setEsitoInviato(true);
					} else {
						gar.setEsitoInviato(false);
					}
					//gar.setIdSoggProgBancaBen(rs.getLong("ID_SOGG_PROG_BANCA_BEN")); // Non serve più
					
					return gar;
				}};
			
			lista = getJdbcTemplate().query(query.toString(), rw);
			
			
			//  recupero i dettagli delle garanzie; 
			garanzia = lista.get(0);
			garanzia.setListaDettagli(getDettagliGaranza(garanzia.getIdProgetto().toString(), idEscussione));
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_D_STATO_ESCUSSIONE", e);
		} finally {
			LOG.info(prf + " END");
		}
		return garanzia;
	}

	@Override
	public VisualizzaStatoCreditoVO getStatoCreditoNew(String idProgetto) {
		String prf = "[RicercaGaranzieDAOImpl::getGaranzia]";
		LOG.info(prf + " BEGIN");
		
		VisualizzaStatoCreditoVO credito = new VisualizzaStatoCreditoVO();
		List<VisualizzaStatoCreditoVO> dati = new ArrayList<VisualizzaStatoCreditoVO>();
		LOG.info(prf + "idProgetto=" + idProgetto);
		
		try {
			String sql=null;
			sql ="SELECT pdsc.DESC_STATO_CREDITO_FP,\r\n"
					+ "	prspscf.Dt_inizio_validita,\r\n"
					+ "	prspscf.DT_INSERIMENTO,\r\n"
					+ "	prspscf.ID_STATO_CREDITO_FP,\r\n"
					+ "	prspscf.ID_SOGG_PROG_STATO_CREDITO_FP,\r\n"
					+ "	prspscf.DT_FINE_VALIDITA\r\n"
					+ "FROM\r\n"
					+ "	PBANDI_R_SOGGETTO_PROGETTO prsp,\r\n"
					+ "	PBANDI_R_SOGG_PROG_STA_CRED_FP prspscf,\r\n"
					+ "	PBANDI_D_STATO_CREDITO_FP pdsc\r\n"
					+ "WHERE\r\n"
					+ "	prsp.id_progetto = ?\r\n"
					+ "	AND prsp.PROGR_SOGGETTO_PROGETTO = prspscf.PROGR_SOGGETTO_PROGETTO\r\n"
					+ "	AND prspscf.ID_STATO_CREDITO_FP = pdsc.ID_STATO_CREDITO_FP\r\n"
					+ "	AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "  AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "	and prspscf.DT_FINE_VALIDITA IS NULL\r\n"
					+ "	AND prspscf.ID_MODALITA_AGEVOLAZIONE = 10\r\n"
					+ "ORDER BY\r\n"
					+ "	prspscf.DT_INSERIMENTO DESC";



			/*dati = getJdbcTemplate().query(sql,new ResultSetExtractor<List<VisualizzaStatoCreditoVO>>() {

				@Override
				public List<VisualizzaStatoCreditoVO> extractData(ResultSet rs) throws SQLException {
					List<VisualizzaStatoCreditoVO> elencoDati = new ArrayList<>();

					while(rs.next()) {
						VisualizzaStatoCreditoVO item= new VisualizzaStatoCreditoVO();

						item.setDescStato(rs.getString("DESC_STATO_CREDITO_FP"));
						item.setDtModifica(rs.getDate("Dt_inizio_validita"));
						item.setIdStatoCredito(rs.getInt("ID_STATO_CREDITO_FP"));
						item.setProgrStatoCredito(rs.getBigDecimal("ID_SOGG_PROG_STATO_CREDITO_FP"));
						item.setDtInserimento(rs.getDate("DT_INSERIMENTO"));			
						elencoDati.add(item);
					}		

					return elencoDati;
				}

		   }, new Object[] {idProgetto});*/

			dati = getJdbcTemplate().query(sql, new RowMapper<VisualizzaStatoCreditoVO>() {

				@Override
				public VisualizzaStatoCreditoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					VisualizzaStatoCreditoVO item = new VisualizzaStatoCreditoVO();

					item.setDescStato(rs.getString("DESC_STATO_CREDITO_FP"));
					item.setDtModifica(rs.getDate("Dt_inizio_validita"));
					item.setIdStatoCredito(rs.getInt("ID_STATO_CREDITO_FP"));
					item.setProgrStatoCredito(rs.getBigDecimal("ID_SOGG_PROG_STATO_CREDITO_FP"));
					item.setDtInserimento(rs.getDate("DT_INSERIMENTO"));			

					return item;
				}}, new Object[] {idProgetto});	


			credito = dati.get(0);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read", e);
		} finally {
			LOG.info(prf + " END");
		}
		return credito;
	}

	@Override
	@Transactional
	public EsitoDTO setFlagEsito(Long idEscussione) {
		String prf = "[RicercaGaranzieDAOImpl::setFlagEsito]";
		LOG.info(prf + " BEGIN");
		
		EsitoDTO esito = new EsitoDTO();
		
		try {
			StringBuilder query = new StringBuilder(
					"UPDATE PBANDI.PBANDI_T_ESCUSSIONE\r\n"
					+ "SET FLAG_ESITO = 'P'\r\n"
					+ "WHERE ID_ESCUSSIONE = " + idEscussione);

			getJdbcTemplate().update(query.toString());

		} catch (Exception e) {
			esito.setEsito(false);
			esito.setException("Exception durante l'update dell'esito: " + e.toString());
		}
		
		LOG.info(prf + " END");
		esito.setEsito(true);
		return esito;
	}
	
	@Override
	public Boolean sendEsitoToErogazioni(Long idProgetto, Long idEscussione, int idTipoEscussione, Long idUtente) throws Exception, ErroreGestitoException {
		String prf = "[RicercaGaranzieDAOImpl::sendEsitoToErogazioni]";
		LOG.info(prf + " BEGIN");

		int updated;
		
		try {
			String query;
			Object[] args;
			int[] types;
			
			// Valorizzo l'idCausaleErogazione in base al tipo escussione
			String quickQuery = "";
			Integer idCausaleErogazione = null;
			/* 	Acconto (3) = primo acconto (1)
				Saldo (2) = saldo (4)
				Totale (1) = saldo (4)
				DEFAULT = saldo (4) */
			switch (idTipoEscussione) {
			case 1: // Totale
				quickQuery = "SELECT ID_CAUSALE_EROGAZIONE \r\n"
						+ "FROM PBANDI_D_CAUSALE_EROGAZIONE pdce\r\n"
						+ "WHERE DESC_CAUSALE = 'saldo'";
				break;
			case 2: // Saldo
				quickQuery = "SELECT ID_CAUSALE_EROGAZIONE \r\n"
						+ "FROM PBANDI_D_CAUSALE_EROGAZIONE pdce\r\n"
						+ "WHERE DESC_CAUSALE = 'saldo'";
				break;
			case 3: // Acconto
				quickQuery = "SELECT ID_CAUSALE_EROGAZIONE \r\n"
						+ "FROM PBANDI_D_CAUSALE_EROGAZIONE pdce\r\n"
						+ "WHERE DESC_CAUSALE = 'primo acconto'";
				break;
			default:
				quickQuery = "SELECT ID_CAUSALE_EROGAZIONE \r\n"
						+ "FROM PBANDI_D_CAUSALE_EROGAZIONE pdce\r\n"
						+ "WHERE DESC_CAUSALE = 'saldo'";
				break;
			}
			idCausaleErogazione = getJdbcTemplate().queryForObject(quickQuery, Integer.class);
			//LOG.info(prf + " idPropostaErogazione: " + idPropostaErogazione);			
			
			//get nuovo id proposta erogazione
			query = "SELECT SEQ_PBANDI_T_PROP_EROGAZIONE.NEXTVAL FROM DUAL";
			Integer idPropostaErogazione = getJdbcTemplate().queryForObject(query, Integer.class);
			LOG.info(prf + " idPropostaErogazione: " + idPropostaErogazione);

			//insert nuovo record su proposta erogazione
			query = "INSERT INTO PBANDI.PBANDI_T_PROPOSTA_EROGAZIONE\r\n"
					+ "(ID_PROPOSTA, ID_PROGETTO, FLAG_CTRL_PRE_EROGAZIONE, IMP_LORDO, IMP_DA_EROGARE, ID_MODALITA_AGEVOLAZIONE, DT_INIZIO_VALIDITA, ID_UTENTE_INS, DT_INSERIMENTO, ID_CAUSALE_EROGAZIONE)\r\n"
					+ "VALUES(:idPropostaErogazione, :idProgetto, 'N',\r\n"
					+ "	(SELECT NVL(IMP_APPROVATO, 0) \r\n"
					+ "	FROM PBANDI_T_ESCUSSIONE\r\n"
					+ "	WHERE ID_ESCUSSIONE = :idEscussione),\r\n"
					+ "	(SELECT NVL(IMP_APPROVATO, 0) \r\n"
					+ "	FROM PBANDI_T_ESCUSSIONE\r\n"
					+ "	WHERE ID_ESCUSSIONE = :idEscussione),\r\n"
					+ "10, SYSDATE, :idUtente, SYSDATE, :idCausaleErogazione)\r\n";
			args = new Object[]{idPropostaErogazione, idProgetto, idEscussione, idEscussione, idUtente, idCausaleErogazione};
			types = new int[]{Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER};
			updated = getJdbcTemplate().update(query, args, types);
			LOG.info(prf + " n. rows updated: " + updated);

			//update escussione con id_proposta_erogazione
			query = "UPDATE PBANDI_T_ESCUSSIONE SET \n" +
					"ID_PROPOSTA_EROGAZIONE = :idPropostaErogazione,\n" +
					"ID_UTENTE_AGG = :idUtente,\n" +
					"DT_AGGIORNAMENTO = SYSDATE\n" +
					"WHERE ID_ESCUSSIONE = :idEscussione";
			args = new Object[]{idPropostaErogazione, idUtente, idEscussione};
			types = new int[]{Types.INTEGER, Types.INTEGER, Types.INTEGER };
			updated = getJdbcTemplate().update(query, args, types);
			LOG.info(prf + " n. rows updated: " + updated);
		} catch (Exception e) {
			throw new ErroreGestitoException("Errore nell'inserire il record", e);
		}

		LOG.info(prf + " END");
		return true;
	}
	
	
	@Transactional(propagation = Propagation.MANDATORY)
	public void inviaSuDoqui(Long idWorkFlow, Integer idTipoDocLettera, Integer idTipoDocVerbale, Boolean esito, HttpServletRequest req) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		try{
			UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			if(esito) {
				LOG.info(prf + "userInfoSec= " + userInfoSec);

				//LETTERA PROTOCOLLATA IN DOQUI
				DatiXActaDTO datiActa = popolaDatiXActa(idWorkFlow, idTipoDocLettera, userInfoSec );
				LOG.info(prf + "datiActa= " + datiActa);

				String codClassificazione = actaManager.classificaDocumento(datiActa);
				LOG.info(prf + "codClassificazione= " + codClassificazione);

//				String codProtocollo;
//				if(codClassificazione!=null) {
//					datiActa.setCodiceClassificazione(codClassificazione);
//					codProtocollo = actaManager.protocollaDocumento(datiActa);
//					LOG.info(prf + "codProtocollo= " + codProtocollo);
//				}else {
//					LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//					throw new Exception ("classificazione non riuscita");
//				}
//				// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
//				updateTProtocollo(codProtocollo, userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());

				//VERBALE DI CONTROLLO IN LOCO PER DOQUI - forse non serve
				/*datiActa = popolaDatiXActa(idWorkFlow, idTipoDocVerbale, userInfoSec);
				LOG.info(prf + "datiActa= " + datiActa);*/

				codClassificazione = actaManager.classificaDocumento(datiActa);
				LOG.info(prf + "codClassificazione= " + codClassificazione);

//				if(codClassificazione!=null) {
//					datiActa.setCodiceClassificazione(codClassificazione);
//					codProtocollo = actaManager.protocollaDocumento(datiActa);
//					LOG.info(prf + "codProtocollo= " + codProtocollo);
//				}else {
//					LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//					throw new Exception ("classificazione non riuscita");
//				}
//				// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
//				updateTProtocollo(codProtocollo, userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());
			}

			Long idControlloLoco = null;
			String query = null;

			//recupero idControlloLoco
			String getIdControlloLoco =
					"SELECT ptwfe.ID_TARGET\n"
							+ "FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe\n"
							+ "JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptwfe.ID_ENTITA\n"
							+ "WHERE pce.NOME_ENTITA = ?\n"
							+ "AND ptwfe.id_work_flow = ?";
			try {
				idControlloLoco = getJdbcTemplate().queryForObject(
						getIdControlloLoco,
						new Object[]{"PBANDI_T_CONTROLLO_LOCO", idWorkFlow},
						Long.class
				);
				//update
				query =
						"UPDATE PBANDI_T_CONTROLLO_LOCO controlloLoco SET\n"
								+ "ID_ATTIV_CONTR_LOCO = ?\n"
								+ "WHERE ID_CONTROLLO_LOCO = ?";

			} catch (EmptyResultDataAccessException ignored) {}
			try {
				idControlloLoco = getJdbcTemplate().queryForObject(
						getIdControlloLoco,
						new Object[]{"PBANDI_T_CONTROLLO_LOCO_ALTRI", idWorkFlow},
						Long.class
				);
				//update
				query =
						"UPDATE PBANDI_T_CONTROLLO_LOCO_ALTRI controlloLoco SET\n"
								+ "ID_ATTIV_CONTR_LOCO = ?\n"
								+ "WHERE ID_CONTROLLO = ?";
			}catch (EmptyResultDataAccessException ignored){}

			//update
			getJdbcTemplate().update(query, esito ? 8 : 9, idControlloLoco);
		}catch (Exception e){
			LOG.error(prf + "Exception while trying to gestioneCNTEsitoPositivo", e);
			throw e;
		}finally {
			LOG.info(prf + "END");
		}
	}
	
	@Transactional(propagation = Propagation.MANDATORY)
	DatiXActaDTO popolaDatiXActa(Long idWorkFlow, Integer idTipoDocIndex, UserInfoSec userInfoSec) throws Exception {
		String prf = "[IterAutorizzativiDAOImpl::popolaDatiXActa]";
		LOG.info(prf + "BEGIN");
		DatiXActaDTO dati;

		String query = " SELECT PTWFE.ID_WORK_FLOW,  PTWFE.ID_ENTITA, PTWFE.ID_TARGET, ptwfe.ID_PROGETTO , "
				+ "    pr.ID_DOMANDA , ct.DESC_BREVE_TIPO_DOC_INDEX , ct.DESC_TIPO_DOC_INDEX , "
				+ "	   fa.DESC_FASCICOLO , di.FLAG_FIRMA_AUTOGRAFA ,  di.NOME_DOCUMENTO , di.NOME_FILE, "
				+ "	   di.ID_DOCUMENTO_INDEX ,  di.ID_TIPO_DOCUMENTO_INDEX     "
				+ "FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe,  "
				+ "	 PBANDI_T_WORK_FLOW ptwf,  "
				+ "	 PBANDI_T_DOCUMENTO_INDEX di, "
				+ "	 PBANDI_T_PROGETTO pr, "
				+ "	 PBANDI_C_TIPO_DOCUMENTO_INDEX ct, "
				+ "	 PBANDI_R_FASC_ACTA_TIP_DOC_IDX rfa, "
				+ "	 PBANDI_D_FASCICOLO_ACTA fa "
				+ "WHERE PTWFE.ID_WORK_FLOW = ? "
				+ "	AND di.ID_TIPO_DOCUMENTO_INDEX = ? "
				+ "	AND PTWF.ID_WORK_FLOW=PTWFE.ID_WORK_FLOW "
				+ "	AND di.ID_TARGET = ptwfe.ID_TARGET  "
				+ "	AND di.ID_ENTITA = ptwfe.ID_ENTITA "
				+ "	AND pr.ID_PROGETTO = ptwfe.ID_PROGETTO "
				+ "	AND di.ID_TIPO_DOCUMENTO_INDEX = ct.ID_TIPO_DOCUMENTO_INDEX  "
				+ "	AND rfa.ID_TIPO_DOCUMENTO_INDEX = di.ID_TIPO_DOCUMENTO_INDEX "
				+ "	AND fa.ID_FASCICOLO_ACTA = rfa.ID_FASCICOLO_ACTA ";

		List<DatiXActaDTO> datiList = getJdbcTemplate().query(query, new Object[]{idWorkFlow, idTipoDocIndex}, new DatiXActaRowMapper());

		if(datiList!=null && datiList.size()>1) {
			LOG.warn(prf + "trovati troppo risultati ="+datiList.size());
			throw new Exception("Trovati troppo risultati");
		}else if(datiList == null || datiList.isEmpty()) {
			dati = null;
		}else {
			dati = datiList.get(0);
		}

		LOG.info(prf + "dati="+dati);
		if(dati!=null) {

			if(dati.getIdDomanda()!=null ) {
				dati.setParolaChiave("FD"+dati.getIdDomanda().toString()); //PK potrebbe essere anche PB+id_progetto ??
				// bisogna capire se la domanda arriva da FINDOM o da PBANDI/scheda progetto
			}else {
				LOG.warn(prf + "Numero domanda non valorizzato");
				throw new Exception("Numero domanda non valorizzato");
			}
			if( dati.getIdDocIndex()!=null) {
				FileDTO fil = documentoManager.leggiFile(dati.getIdDocIndex());
				if(fil!=null) {
					LOG.info(prf + "caricato file nomefile ="+fil.getNomeFile());
					LOG.info(prf + "caricato file size ="+fil.getSizeFile());
					dati.setTsdFile(fil.getBytes());
				}
			}else {
				throw new Exception("File non trovato");
			}

			dati.setUtenteCollegatoCF(userInfoSec.getCodFisc());
			dati.setUtenteCollegatoNome(userInfoSec.getNome());
			dati.setUtenteCollegatoCognome(userInfoSec.getCognome());
			dati.setUtenteCollegatoId(userInfoSec.getIdUtente());

			try {
				String sql = "WITH denom AS (\n" +
						"    SELECT\n" +
						"    concat(persfis.NOME, CONCAT (' ', persfis.COGNOME)) as denominazione_fis,\n" +
						"    PERSFIS.ID_SOGGETTO,\n" +
						"    persfis.ID_PERSONA_FISICA\n" +
						"    FROM PBANDI_T_PERSONA_FISICA persfis\n" +
						"),\n" +
						"denom2 AS (\n" +
						"    SELECT\n" +
						"    entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione_ente,\n" +
						"    ENTEGIU.ID_SOGGETTO,\n" +
						"    entegiu.ID_ENTE_GIURIDICO\n" +
						"    FROM PBANDI_T_ENTE_GIURIDICO entegiu\n" +
						")\n" +
						"SELECT DISTINCT denom.denominazione_fis || denom2.denominazione_ente AS denominazione\n" +
						"FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe\n" +
						"JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptwfe.ID_PROGETTO\n" +
						"LEFT JOIN denom ON prsp.ID_SOGGETTO = denom.id_soggetto AND denom.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\n" +
						"LEFT JOIN denom2 ON prsp.ID_SOGGETTO = denom2.id_soggetto AND denom2.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\n" +
						"WHERE ((prsp.ID_TIPO_ANAGRAFICA = 1\n" +
						"AND prsp.ID_TIPO_BENEFICIARIO <> 4) OR prsp.ID_SOGGETTO IS NULL)\n" +
						"AND ptwfe.ID_WORK_FLOW = ?";
				dati.setDenominazioneBeneficiario(getJdbcTemplate().queryForObject(sql, String.class, idWorkFlow));
			}catch (IncorrectResultSizeDataAccessException ignored){}

		}
		LOG.info(prf + "END");
		return dati;
	}
	
}
