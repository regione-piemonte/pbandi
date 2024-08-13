/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl;

import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.business.manager.FatturaElettronicaManager;
import it.csi.pbandi.pbweb.dto.IntegrazioneRendicontazioneDTO;
import it.csi.pbandi.pbweb.dto.IntegrazioneRevocaDTO;
import it.csi.pbandi.pbweb.dto.rendicontazione.AllegatoDTO;
import it.csi.pbandi.pbweb.dto.rendicontazione.DocIntegrRendDTO;
import it.csi.pbandi.pbweb.dto.rendicontazione.DocumentoDiSpesaSospesoDTO;
import it.csi.pbandi.pbweb.dto.rendicontazione.QuietanzaDTO;
import it.csi.pbandi.pbweb.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbweb.exception.DaoException;
import it.csi.pbandi.pbweb.integration.dao.GestioneIntegrazioniDAO;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.AllegatoDocSpesaQuietanzaVORowMapper;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.IntegrazioneRendicontazioneRowMapper;
import it.csi.pbandi.pbweb.integration.vo.AllegatiDichiarazioneSpesaVO;
import it.csi.pbandi.pbweb.integration.vo.AllegatoDocSpesaQuietanzaVO;
import it.csi.pbandi.pbweb.integration.vo.DocumentiSpesaSospesiVO;
import it.csi.pbandi.pbweb.integration.vo.VisualizzaIntegrazioniVO;
import it.csi.pbandi.pbweb.pbandisrv.business.validazionerendicontazione.ValidazioneRendicontazioneBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.RegoleAllegatiIntegrazioneDTO;
import it.csi.pbandi.pbweb.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweb.util.BeanUtil;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.FileSqlUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import java.security.InvalidParameterException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class GestioneIntegrazioniDAOImpl extends JdbcDaoSupport implements GestioneIntegrazioniDAO {

    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    public GestioneIntegrazioniDAOImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }


    @Autowired
    DocumentoManager documentoManager;

    @Autowired
    protected ValidazioneRendicontazioneBusinessImpl getValidazioneRendicontazioneBusinessImpl;

    @Autowired
    protected FileSqlUtil fileSqlUtil;

    @Autowired
    protected BeanUtil beanUtil;

    @Autowired
    protected InizializzazioneDAOImpl inizializzazioneDAOImpl;

    @Autowired
    protected DecodificheDAOImpl decodificheDAOImpl;

    @Autowired
    // protected ArchivioFileDAOImpl archivioFileDAOImpl;
    protected it.csi.pbandi.pbservizit.integration.dao.impl.ArchivioFileDAOImpl archivioFileDAOImpl;

    @Autowired
    protected ProfilazioneDAO profilazioneDao;

    @Autowired
    protected it.csi.pbandi.pbweb.pbandisrv.business.archivio.ArchivioBusinessImpl archivioBusinessImpl;

    @Autowired
    protected it.csi.pbandi.pbweb.pbandisrv.business.profilazione.ProfilazioneBusinessImpl profilazioneBusinessImpl;

    @Autowired
    protected it.csi.pbandi.pbweb.pbandisrv.business.gestionedocumentidispesa.GestioneDocumentiDiSpesaBusinessImpl gestioneDocumentiDiSpesaBusinessImp;

    @Autowired
    protected it.csi.pbandi.pbweb.pbandisrv.business.gestionevocidispesa.GestioneVociDiSpesaBusinessImpl gestioneVociDiSpesaBusinessImpl;

    @Autowired
    protected it.csi.pbandi.pbweb.pbandisrv.business.gestionepagamenti.GestionePagamentiBusinessImpl gestionePagamentiBusinessImpl;

    @Autowired
    protected it.csi.pbandi.pbweb.pbandisrv.business.manager.ProgettoManager progettoManager;

    @Autowired
    it.csi.pbandi.pbweb.pbandisrv.business.validazionerendicontazione.ValidazioneRendicontazioneBusinessImpl validazioneRendicontazioneBusinessImpl;

    @Autowired
    FatturaElettronicaManager fatturaElettronicaManager;

	/*@Override
	public List<TipoDocumentiSpesaVO> ottieniTipologieDocumentiDiSpesaByBandoLinea(int idBandoLinea) {
		String prf = "[DocumentoDiSpesaDAOImpl::ottieniTipologieDocumentiDiSpesaByBandoLinea] ";

		LOG.info(prf + "ottieniTipologieDocumentiDiSpesaByBandoLinea: idBandoLinea = " + idBandoLinea);
		List<TipoDocumentiSpesaVO> listTipoDocumentiSpesa = new ArrayList<TipoDocumentiSpesaVO>();
		String sql;
		try {
			sql = fileSqlUtil.getQuery("OttieniTipologieDocumentiDiSpesa.sql");
			listTipoDocumentiSpesa = getJdbcTemplate().query(sql.toString(), new Object[] { idBandoLinea },
					new TipoDocumentiSpesaRowMapper());
			LOG.info(prf + "ottieniTipologieDocumentiDiSpesaByBandoLinea: listTipoDocumentiSpesa.size = "
					+ listTipoDocumentiSpesa.size());
		} catch (Exception e) {
			System.out.println("Eccezione EXc" + e.getMessage());
		}

		return listTipoDocumentiSpesa;
	}*/

    @Override
    public Boolean getAbilitaInviaIntegr(int idRichIntegrazione) {
        String prf = "[GestioneIntegrazioniDAOImpl::getAbilitaInviaIntegr]";
        LOG.info(prf + " BEGIN");
        int trovato = 0;
        boolean eseguito = false;
        String query =
                "SELECT * \n" +
                "FROM PBANDI_T_FILE_ENTITA ptfe, PBANDI_T_RICHIESTA_INTEGRAZ ptri\n" +
                "WHERE ptfe.FLAG_ENTITA = 'I' AND PTFE.ID_ENTITA = 609\n" +
                "AND ptfe.ID_TARGET = ptri.ID_richiesta_integraz\n" +
                "AND ptri.ID_STATO_richiesta = 1\n" +
                "AND ptri.ID_RICHIESTA_INTEGRAZ = :idRichIntegrazione";

        LOG.debug(query);

        Object[] args = new Object[]{idRichIntegrazione};
        int[] types = new int[]{Types.INTEGER};

        try {
            trovato = getJdbcTemplate().update(query, args, types);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (trovato > 0) {
            eseguito = true;
        }

        LOG.info("N. record trovati:\n" + trovato);
        LOG.info(prf + " end");
        return eseguito;
    }

    @Override
    public Boolean updateRichIntegrazione(int idUtente, int idRichIntegrazione) {
        String prf = "[GestioneIntegrazioniDAOImpl::updateRichIntegrazione]";
        LOG.info(prf + " BEGIN");
        int rowsUpdated = 0;
        boolean eseguito = false;
        String query =
                "UPDATE PBANDI_T_GESTIONE_REVOCA\n" +
                "SET ID_ATTIVITA_REVOCA = 7,\n" +
                "DT_ATTIVITA = CURRENT_DATE,\n" +
                "ID_UTENTE_AGG = :idUtente,\n" +
                "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                "WHERE ID_GESTIONE_REVOCA = (\n" +
                "\tSELECT ID_TARGET\n" +
                "\tFROM PBANDI_T_RICHIESTA_INTEGRAZ ptri\n" +
                "\tWHERE ID_ENTITA = 516\n" +
                "\tAND ID_RICHIESTA_INTEGRAZ = :idRichIntegrazione\n" +
                ")";

        LOG.debug(query);

        Object[] args = new Object[]{idUtente, idRichIntegrazione};
        int[] types = new int[]{Types.INTEGER, Types.INTEGER};

        try {
            rowsUpdated = getJdbcTemplate().update(query, args, types);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rowsUpdated > 0) {
            eseguito = true;
        }

        LOG.info("N. record aggiornati:\n" + rowsUpdated);
        LOG.info(prf + " end");
        return eseguito;
    }

    @Override
    public Boolean insertFileEntita(List<VisualizzaIntegrazioniVO> allegati, Long idRichIntegraz) {
        String prf = "[GestioneIntegrazioniDAOImpl::insertFileEntita]";
        LOG.info(prf + " BEGIN");
        boolean result = false;
        int rowsUpdated = 0;
        try {
            String query =
                    "INSERT INTO PBANDI_T_FILE_ENTITA(\n" +
                    "ID_FILE_ENTITA,\n" +
                    "ID_FILE,\n" +
                    "ID_ENTITA,\n" +
                    "ID_TARGET,\n" +
                    "FLAG_ENTITA\n" +
                    ") VALUES (\n" +
                    "SEQ_PBANDI_T_FILE_ENTITA.NEXTVAL,\n" +
                    "(\n" +
                    "\tSELECT ID_FILE FROM PBANDI_T_FILE\n" +
                    "\tWHERE ID_DOCUMENTO_INDEX =:idDocumentoIndex\n" +
                    "),\n" +
                    "569,\n" +
                    ":idRichIntegraz,\n" +
                    ":flagEntita\n" +
                    ")";

            LOG.debug(query);

            for (VisualizzaIntegrazioniVO visualizzaIntegrazioniVO : allegati) {
                Object[] args = new Object[]{visualizzaIntegrazioniVO.getIdDocumentoIndex(), idRichIntegraz, visualizzaIntegrazioniVO.getFlagEntita()};
                int[] types = new int[]{Types.INTEGER, Types.INTEGER, Types.VARCHAR};
                rowsUpdated = getJdbcTemplate().update(query, args, types);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rowsUpdated > 0) {
            result = true;
        }

        return result;
    }

    @Override
    public List<VisualizzaIntegrazioniVO> getAllegatiIstruttoreRevoche(int idRichIntegraz) {
        String prf = "[GestioneIntegrazioniDAOImpl::getAllegatiIstruttoreRevoche]";
        LOG.info(prf + " BEGIN");
        List<VisualizzaIntegrazioniVO> dati;
        LOG.info(prf + "idRichIntegraz=" + idRichIntegraz);

        try {
            String sql =
                "SELECT\n" +
                    "documenti.ID_DOCUMENTO_INDEX,\n" +
                    "documenti.NOME_FILE,\n" +
                    "tipoDocumento.DESC_BREVE_TIPO_DOC_INDEX\n" +
                    "FROM PBANDI_T_RICHIESTA_INTEGRAZ integraz\n" +
                    "JOIN PBANDI_C_ENTITA pce ON pce.NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'\n" +
                    "JOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_target = integraz.id_richiesta_integraz\n" +
                    "JOIN PBANDI_C_ENTITA pce2 ON pce2.NOME_ENTITA = 'PBANDI_T_RICHIESTA_INTEGRAZ'\n" +
                    "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index\n" +
                    "WHERE documenti.id_tipo_documento_index IN (45, 51)\n" +
                    "AND integraz.ID_ENTITA = pce.ID_ENTITA \n" +
                    "AND documenti.ID_ENTITA = pce2.ID_ENTITA \n" +
                    "AND integraz.ID_RICHIESTA_INTEGRAZ = :idRichIntegraz";
            LOG.debug(sql);
            Object[] args = new Object[]{idRichIntegraz};
            int[] types = new int[]{Types.INTEGER};

            dati = getJdbcTemplate().query(sql, args, types,
                rs -> {
                    List<VisualizzaIntegrazioniVO> elencoDati = new ArrayList<>();

                    while (rs.next()) {
                        VisualizzaIntegrazioniVO item = new VisualizzaIntegrazioniVO();

                        item.setNomeFile(rs.getString("NOME_FILE"));
                        item.setIdDocumentoIndex(rs.getInt("ID_DOCUMENTO_INDEX"));
                        item.setDesc(rs.getString("DESC_BREVE_TIPO_DOC_INDEX"));

                        elencoDati.add(item);
                    }

                    return elencoDati;
                }
            );

        } catch (Exception e){
            LOG.info(prf + "Error while trying to getAllegatiIstruttoreRevoche: " + e);
            dati = new ArrayList<>();
        } finally {
            LOG.info(prf + " END");
        }

        return dati;
    }

    @Override
    public List<VisualizzaIntegrazioniVO> getAllegati(int idRichIntegraz) {
        String prf = "[ContestazioniDAOImpl::getAllegati]";
        LOG.info(prf + " BEGIN");
        List<VisualizzaIntegrazioniVO> dati;
        LOG.info(prf + "idControdeduz=" + idRichIntegraz);

        try {
            String sql =
                    "SELECT \n" +
                            "a.ID_FILE, \n" +
                            "a.FLAG_ENTITA, \n" +
                            "c.ID_DOCUMENTO_INDEX, \n" +
                            "c.NOME_FILE, \n" +
                            "a.ID_FILE_ENTITA, \n" +
                            "d.DESC_BREVE_TIPO_DOC_INDEX\n" +
                            "FROM PBANDI_T_FILE_ENTITA a\n" +
                            "JOIN PBANDI_T_FILE b ON a.ID_FILE = b.ID_FILE\n" +
                            "JOIN PBANDI_T_DOCUMENTO_INDEX c ON c.ID_DOCUMENTO_INDEX = b.ID_DOCUMENTO_INDEX\n" +
                            "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX d ON d.ID_TIPO_DOCUMENTO_INDEX = c.ID_TIPO_DOCUMENTO_INDEX\n" +
                            "WHERE a.ID_ENTITA = 569 AND a.ID_TARGET = :idRichIntegraz";
            LOG.debug(sql);
            Object[] args = new Object[]{idRichIntegraz};
            int[] types = new int[]{Types.INTEGER};

            dati = getJdbcTemplate().query(sql, args, types,
                    rs -> {
                        List<VisualizzaIntegrazioniVO> elencoDati = new ArrayList<>();

                        while (rs.next()) {
                            VisualizzaIntegrazioniVO item = new VisualizzaIntegrazioniVO();

                            item.setNomeFile(rs.getString("NOME_FILE"));
                            item.setIdDocumentoIndex(rs.getInt("ID_DOCUMENTO_INDEX"));
                            item.setFlagEntita(rs.getString("FLAG_ENTITA"));
                            item.setIdFileEntita(rs.getInt("ID_FILE_ENTITA"));
                            item.setDesc(rs.getString("DESC_BREVE_TIPO_DOC_INDEX"));
                            elencoDati.add(item);
                        }

                        return elencoDati;
                    });

        } finally {
            LOG.info(prf + " END");
        }

        return dati;
    }
    @Override
    public List<VisualizzaIntegrazioniVO> getLetteraIstruttore(int idRichIntegraz) {
        String prf = "[ContestazioniDAOImpl::getAllegati]";
        LOG.info(prf + " BEGIN");
        List<VisualizzaIntegrazioniVO> dati;
        LOG.info(prf + "idControdeduz=" + idRichIntegraz);

        try {
            String sql =
                    "SELECT a.ID_DOCUMENTO_INDEX, a.NOME_FILE, c.DESC_BREVE_TIPO_DOC_INDEX\r\n"
                            + "FROM PBANDI_T_DOCUMENTO_INDEX a\r\n"
                            + "LEFT JOIN PBANDI_T_DOC_PROTOCOLLO b ON a.ID_DOCUMENTO_INDEX = b.ID_DOCUMENTO_INDEX \r\n"
                            + "LEFT JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX c ON c.ID_TIPO_DOCUMENTO_INDEX = a.ID_TIPO_DOCUMENTO_INDEX \r\n"
                            + "WHERE a.id_tipo_documento_index = 45\r\n"
                            + "AND a.id_entita = 569\r\n"
                            + "AND a.id_target = :idIntegrazione\n";
            LOG.debug(sql);
            Object[] args = new Object[]{idRichIntegraz};
            int[] types = new int[]{Types.INTEGER};

            dati = getJdbcTemplate().query(sql, args, types,
                    rs -> {
                        List<VisualizzaIntegrazioniVO> elencoDati = new ArrayList<>();

                        while (rs.next()) {
                            VisualizzaIntegrazioniVO item = new VisualizzaIntegrazioniVO();

                            item.setIdDocumentoIndex(rs.getInt("ID_DOCUMENTO_INDEX"));
                            item.setNomeFile(rs.getString("NOME_FILE"));
                            item.setDesc(rs.getString("DESC_BREVE_TIPO_DOC_INDEX"));

                            elencoDati.add(item);
                        }

                        return elencoDati;
                    });

        } finally {
            LOG.info(prf + " END");
        }

        return dati;
    }

    @Override
    public Boolean deleteAllegato(int idFileEntita) {
        String prf = "[ContestazioniDAOImpl::deleteAllegato]";
        LOG.info(prf + " BEGIN");
        int rowsUpdated;
        boolean eseguito = false;
        String query =
                "DELETE FROM PBANDI_T_FILE_ENTITA ptfe\n" +
                "WHERE ID_ENTITA = 569\n" +
                "AND ID_FILE_ENTITA = :idFileEntita";

        LOG.debug(query);

        Object[] args = new Object[]{idFileEntita};
        int[] types = new int[]{Types.INTEGER};

        rowsUpdated = getJdbcTemplate().update(query, args, types);
        if (rowsUpdated > 0) {
            eseguito = true;
        }

        LOG.info("N. record aggiornati:\n" + rowsUpdated);
        LOG.info(prf + " end");
        return eseguito;
    }

    @Override
    public List<AllegatiDichiarazioneSpesaVO> getAllegatiDichSpesa(int idDichSpesa) {
        String prf = "[GestioneIntegrazioniDAOImpl::getAllegatiDichSpesa]";
        LOG.info(prf + " BEGIN");
        List<AllegatiDichiarazioneSpesaVO> dati;

        LOG.info(prf + "idDichSpesa=" + idDichSpesa);

        try {
            String sql =
                    "SELECT *\n" +
                            "FROM PBANDI_T_FILE_ENTITA ptfe\n" +
                            "JOIN PBANDI_T_FILE ptf ON ptfe.ID_FILE = ptf.ID_FILE\n" +
                            "WHERE ptfe.ID_ENTITA = 63 AND ptfe.ID_TARGET = ?";
            LOG.debug(sql);
            dati = getJdbcTemplate().query(sql, new Object[]{idDichSpesa}, rs -> {
                List<AllegatiDichiarazioneSpesaVO> elencoDati = new ArrayList<>();

                while (rs.next()) {
                    AllegatiDichiarazioneSpesaVO item = new AllegatiDichiarazioneSpesaVO();

                    item.setIdFileEntita(rs.getLong("ID_FILE_ENTITA"));
                    item.setIdFile(rs.getLong("ID_FILE"));
                    item.setIdEntita(rs.getLong("ID_ENTITA"));
                    item.setIdTarget(rs.getLong("ID_TARGET"));
                    item.setFlagEntita(rs.getString("FLAG_ENTITA"));
                    item.setIdFolder(rs.getLong("ID_FOLDER"));
                    item.setIdDocuIndex(rs.getLong("ID_DOCUMENTO_INDEX"));
                    item.setNomeFile(rs.getString("NOME_FILE"));
                    item.setSizeFile(rs.getString("SIZE_FILE"));
                    item.setIdIntegrazioneSpesa(rs.getString("ID_INTEGRAZIONE_SPESA"));
                    elencoDati.add(item);
                }

                return elencoDati;
            });
        } finally {
            LOG.info(prf + " END");
        }

        return dati;
    }

    @Override
    public List<DocumentiSpesaSospesiVO> getDocumentiSpesaSospesi(int idProgetto) {
        String prf = "[GestioneIntegrazioniDAOImpl::getDocumentiSpesaSospesi]";
        LOG.info(prf + " BEGIN");
        List<DocumentiSpesaSospesiVO> dati;

        LOG.info(prf + "idProgetto=" + idProgetto);

        try {
            String sql =
                    "SELECT *\n" +
                    "FROM PBANDI_R_DOC_SPESA_PROGETTO prdsp \n" +
                    "WHERE prdsp.ID_PROGETTO = ?\n" +
                    "AND prdsp.ID_STATO_DOCUMENTO_SPESA = 3";
            LOG.debug(sql);
            dati = getJdbcTemplate().query(sql, new Object[]{idProgetto}, rs -> {
                List<DocumentiSpesaSospesiVO> elencoDati = new ArrayList<>();

                while (rs.next()) {
                    DocumentiSpesaSospesiVO item = new DocumentiSpesaSospesiVO();

                    item.setIdDocuSpesa(rs.getLong("ID_DOCUMENTO_DI_SPESA"));
                    item.setImportoRendicon(rs.getString("IMPORTO_RENDICONTAZIONE"));
                    item.setIdUtenteIns(rs.getString("ID_UTENTE_INS"));
                    item.setIdUtenteAgg(rs.getString("ID_UTENTE_AGG"));
                    item.setNoteValidazione(rs.getString("NOTE_VALIDAZIONE"));
                    item.setTask(rs.getString("TASK"));
                    item.setIdStatoDocumentoSpesa(rs.getString("ID_STATO_DOCUMENTO_SPESA"));
                    item.setIdStatoDocumentoSpesaValid(rs.getString("ID_STATO_DOCUMENTO_SPESA_VALID"));
                    item.setTipoInvio(rs.getString("TIPO_INVIO"));
                    item.setIdAppalto(rs.getString("ID_APPALTO"));
                    elencoDati.add(item);
                }

                return elencoDati;
            });

        } finally {
            LOG.info(prf + " END");
        }

        return dati;
    }

    @Override
    public Boolean salvaAllegatiGenerici(List<AllegatiDichiarazioneSpesaVO> allegati, Long idDichiarazioneSpesa, Long idIntegrazioneSpesa) {
        String prf = "[GestioneIntegrazioniDAOImpl::salvaAllegatiGenerici]";
        LOG.info(prf + " BEGIN");

        boolean result = false;
        int rowsUpdated = 0;

        Long ID_FILE;

        try {
            for (AllegatiDichiarazioneSpesaVO allegatiDichiarazioneSpesaVO : allegati) {
                // Trovo l'id_file da pbandi_t_file associato all'allegato da salvare

                String sql =
                        "SELECT ptf.ID_FILE \n" +
                                "FROM PBANDI_T_FILE ptf\n" +
                                "WHERE ptf.ID_DOCUMENTO_INDEX = ?\n" +
                                "AND ptf.ID_FOLDER = ? \n" +
                                "AND ptf.NOME_FILE = ?";
                LOG.debug(sql);
                ID_FILE = getJdbcTemplate().queryForObject(sql,
                        new Object[]{
                                allegatiDichiarazioneSpesaVO.getIdDocuIndex(),
                                allegatiDichiarazioneSpesaVO.getIdFolder(),
                                allegatiDichiarazioneSpesaVO.getNomeFile()
                        },
                        Long.class);

                // Salva riferimento all'allegato
                String query =
                        "INSERT INTO PBANDI.PBANDI_T_FILE_ENTITA\n" +
                                "(ID_FILE_ENTITA, ID_FILE, ID_ENTITA, ID_TARGET, ID_INTEGRAZIONE_SPESA)\n" +
                                "VALUES(SEQ_PBANDI_T_FILE_ENTITA.nextval, ?, 63, ?, ?)";
                LOG.debug(query);
                rowsUpdated = getJdbcTemplate().update(query, ID_FILE, idDichiarazioneSpesa, idIntegrazioneSpesa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rowsUpdated > 0) {
            result = true;
        }
        return result;
    }

    //INTEGRAZIONE ALLA REVOCA
    @Override
    public List<IntegrazioneRevocaDTO> getIntegrazioniRevoca(Long idProgetto, Long idBandoLinea) throws Exception {
        String prf = "[GestioneIntegrazioniDAOImpl::getIntegrazioniRevoca] ";
        LOG.info(prf + "BEGIN");

        List<IntegrazioneRevocaDTO> elencoDTO;

        LOG.info(prf + " idProgetto = " + idProgetto + ", idBandoLinea = " + idBandoLinea);

        try {
            String query =
                "SELECT DISTINCT \n" +
                "gestioneRevoca.numero_revoca AS numeroRevoca,\n" +
                "richiestaIntegrazione.id_richiesta_integraz AS idRichiestaIntegraz,\n" +
                "richiestaIntegrazione.dt_richiesta AS dtRichiesta,\n" +
                "richiestaIntegrazione.dt_notifica AS dtNotifica,\n" +
                "richiestaIntegrazione.dt_scadenza AS dtScadenza,\n" +
                "richiestaIntegrazione.dt_invio AS dtInvio,\n" +
                "statoRichiestaIntegraz.id_stato_richiesta AS idStatoRichiesta,\n" +
                "statoRichiestaIntegraz.DESC_BREVE_STATO_RICHIESTA AS descBreveStatoRichiesta, \n" +
                "statoRichiestaIntegraz.DESC_STATO_RICHIESTA AS descStatoRichiesta,\n" +
                "CASE (lettAcc.value) WHEN 1 THEN 1 ELSE 0 END AS allegatiInseriti\n" +
                "FROM PBANDI_T_RICHIESTA_INTEGRAZ richiestaIntegrazione\n" +
                "LEFT JOIN PBANDI_D_STATO_RICH_INTEGRAZ statoRichiestaIntegraz ON statoRichiestaIntegraz.ID_STATO_RICHIESTA = richiestaIntegrazione.ID_STATO_RICHIESTA \n" +
                "INNER JOIN PBANDI_T_GESTIONE_REVOCA gestioneRevoca ON richiestaIntegrazione.ID_TARGET = gestioneRevoca.ID_GESTIONE_REVOCA\n" +
                "LEFT JOIN (\n" +
                "\tSELECT COUNT(1) AS value, fileEntita.ID_TARGET AS id\n" +
                "\tFROM PBANDI_T_FILE_ENTITA fileEntita\n" +
                "\tWHERE fileEntita.ID_ENTITA = 569 \n" +
                "\tAND fileEntita.FLAG_ENTITA = 'I'\n" +
                "\tGROUP BY fileEntita.ID_TARGET\n" +
                ") lettAcc ON lettAcc.id = richiestaIntegrazione.id_richiesta_integraz\n" +
                "WHERE richiestaIntegrazione.ID_STATO_RICHIESTA IN (1, 2, 3)\n" +
                "AND richiestaIntegrazione.ID_ENTITA = 516 \n" +
                "AND richiestaIntegrazione.ID_TARGET IN ( \n" +
                "\tSELECT ID_GESTIONE_REVOCA\n" +
                "\tFROM PBANDI_T_GESTIONE_REVOCA\n" +
                "\tWHERE ID_TIPOLOGIA_REVOCA = '2'\n" +
                "\tAND ID_PROGETTO = ?\n" +
                ")";

            elencoDTO = getJdbcTemplate().query(query, new Object[]{idProgetto}, (rs, rowNum) -> {
                IntegrazioneRevocaDTO cm = new IntegrazioneRevocaDTO();

                cm.setnRevoca(rs.getLong("numeroRevoca"));
                cm.setIdRichIntegrazione(rs.getLong("idRichiestaIntegraz"));
                cm.setDataRichiesta(rs.getString("dtRichiesta"));
                cm.setDataNotifica(rs.getString("dtNotifica"));
                cm.setDataScadenza(rs.getString("dtScadenza"));
                cm.setDataInvio(rs.getString("dtInvio"));
                cm.setIdStatoRichiesta(rs.getString("idStatoRichiesta"));
                cm.setStatoRichiesta(rs.getString("descBreveStatoRichiesta"));
                cm.setLongStatoRichiesta(rs.getString("descStatoRichiesta"));
                cm.setAllegatiInseriti(rs.getLong("allegatiInseriti") == 1);

                try {
                    IntegrazioneRevocaDTO dto = getJdbcTemplate().queryForObject(
                        "SELECT * FROM (\n" +
                        "\tSELECT statoProroga.id_stato_proroga AS idStatoProroga,\n" +
                        "\tstatoProroga.desc_stato_proroga AS statoProroga\n" +
                        "\tFROM PBANDI_T_PROROGA proroga\n" +
                        "\tJOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                        "\tWHERE proroga.ID_ENTITA = 569 \n" +
                        "\tAND proroga.ID_TARGET = ?\n" +
                        "\tORDER BY proroga.DT_INSERIMENTO DESC \n" +
                        ") WHERE rownum <= 1",
                        (rs1, rowNum1) -> {
                            IntegrazioneRevocaDTO stato = new IntegrazioneRevocaDTO();

                            stato.setIdStatoProroga(rs1.getLong("idStatoProroga"));
                            stato.setStatoProroga(rs1.getString("statoProroga"));

                            return stato;
                        },
                        cm.getIdRichIntegrazione()
                    );

                    cm.setIdStatoProroga(dto.getIdStatoProroga());
                    cm.setStatoProroga(dto.getStatoProroga());
                } catch (IncorrectResultSizeDataAccessException ignored){}

                return cm;
            });
        }catch (EmptyResultDataAccessException ignored){
            elencoDTO = new ArrayList<>();
        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella ricerca delle integrazioni di revoca: ", e);
            throw new DaoException(" ERRORE nella ricerca delle integrazioni di revoca.");
        } finally {
            LOG.info(prf + " END");
        }

        return elencoDTO;
    }

    @Override
    public List<IntegrazioneRevocaDTO> getRichProroga(Long idIntegrazione) {
        String prf = "[GestioneIntegrazioniDAOImpl::getRichProroga]";
        LOG.info(prf + " BEGIN");

        List<IntegrazioneRevocaDTO> integrazioneRevocaDTO = new ArrayList<>();

        try {
            String query =
                    "SELECT \n" +
                    "proroga.dt_richiesta AS dataRichiesta,\n" +
                    "proroga.num_giorni_rich AS giorniRichiesti,\n" +
                    "proroga.motivazione AS motivazione,\n" +
                    "proroga.num_giorni_approv AS giorniApprovati,\n" +
                    "statoRichiesta.id_stato_richiesta AS idStatoRichiesta,\n" +
                    "statoRichiesta.desc_breve_stato_richiesta AS descBreveStatoRichiesta,\n" +
                    "statoRichiesta.desc_stato_richiesta AS descStatoRichiesta,\n" +
                    "statoProroga.desc_breve_stato_proroga AS statoProroga\n" +
                    "FROM PBANDI_T_PROROGA proroga\n" +
                    "JOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                    "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = proroga.id_entita\n" +
                    "JOIN PBANDI_T_RICHIESTA_INTEGRAZ richiestaIntegraz ON richiestaIntegraz.id_richiesta_integraz = proroga.id_target\n" +
                    "JOIN PBANDI_D_STATO_RICHIESTA statoRichiesta ON statoRichiesta.id_stato_richiesta = richiestaIntegraz.id_stato_richiesta\n" +
                    "WHERE entita.nome_entita = 'PBANDI_T_RICHIESTA_INTEGRAZ'\n" +
                    "AND richiestaIntegraz.id_richiesta_integraz = :idIntegrazione\n" +
                    "ORDER BY proroga.dt_richiesta";

            LOG.debug(query);

            Object[] args = new Object[] {idIntegrazione};
            int[] types = new int[] {Types.INTEGER};
            integrazioneRevocaDTO = getJdbcTemplate().query(query, args, types,
                (rs, rownum) -> {
                    IntegrazioneRevocaDTO dto = new IntegrazioneRevocaDTO();

                    dto.setDtRichiesta(rs.getDate("dataRichiesta").toString());
                    dto.setGgRichiesti(rs.getLong("giorniRichiesti"));
                    dto.setMotivazione(rs.getString("motivazione"));
                    dto.setGgApprovati(rs.getLong("giorniApprovati"));
                    dto.setIdStatoRichiesta(rs.getString("idStatoRichiesta"));
                    dto.setStatoRichiesta(rs.getString("descBreveStatoRichiesta"));
                    dto.setLongStatoRichiesta(rs.getString("descStatoRichiesta"));
                    dto.setStatoProroga(rs.getString("statoProroga"));

                    return dto;
                });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return integrazioneRevocaDTO;
    }

    @Override
    public Boolean inserisciRichProroga(Long idIntegrazione, IntegrazioneRevocaDTO integrazioneRevocaDTO, HttpServletRequest req) {
        String prf = "[GestioneIntegrazioniDAOImpl::inserisciRichProroga]";
        LOG.info(prf + " BEGIN");
        boolean result = false;
        int rowsUpdated = 0;
        try {
            String query =
                    "insert into PBANDI_T_PROROGA  \r\n"
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
                    + "CURRENT_DATE)\n";

            LOG.debug(query);

            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            Object[] args = new Object[] {idIntegrazione, integrazioneRevocaDTO.getGgRichiesti(), integrazioneRevocaDTO.getMotivazione(), userInfoSec.getIdUtente()};
            int[] types = new int[] {Types.INTEGER, Types.INTEGER, Types.VARCHAR , Types.INTEGER};
            rowsUpdated = getJdbcTemplate().update(query, args, types);

            //UPDATE GESTIONE_REVOCA
            query =
                    "UPDATE PBANDI_T_GESTIONE_REVOCA \n" +
                    "SET ID_ATTIVITA_REVOCA = 8,\n" +
                    "DT_ATTIVITA = CURRENT_DATE,\n" +
                    "ID_UTENTE_AGG = :idUtente,\n" +
                    "DT_AGGIORNAMENTO = CURRENT_DATE \n" +
                    "WHERE ID_GESTIONE_REVOCA IN (\n" +
                    "\tSELECT lastGestioneRevoca.id_gestione_revoca\n" +
                    "\tFROM PBANDI_T_RICHIESTA_INTEGRAZ richiestaIntegraz\n" +
                    "\tJOIN PBANDI_C_ENTITA entita ON entita.id_entita = richiestaIntegraz.id_entita\n" +
                    "\tJOIN PBANDI_T_GESTIONE_REVOCA gestioneRevoca ON gestioneRevoca.id_gestione_revoca = richiestaIntegraz.id_target\n" +
                    "\tJOIN PBANDI_T_GESTIONE_REVOCA lastGestioneRevoca ON lastGestioneRevoca.numero_revoca = gestioneRevoca.numero_revoca\n" +
                    "\tWHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'\n" +
                    "\tAND lastGestioneRevoca.id_tipologia_revoca = 2\n" +
                    "\tAND lastGestioneRevoca.dt_fine_validita IS NULL\n" +
                    "\tAND richiestaIntegraz.id_richiesta_integraz = :idIntegrazione\n" +
                    ")";
            LOG.debug(query);
            args = new Object[] {userInfoSec.getIdUtente(), idIntegrazione};
            types = new int[] {Types.INTEGER, Types.INTEGER};
            getJdbcTemplate().update(query, args, types);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rowsUpdated > 0) {
            result = true;
        }

        return result;
    }

    @Override
    public Boolean inviaIntegrazione(Long idIntegrazione, HttpServletRequest req) {
        String prf = "[GestioneIntegrazioniDAOImpl::inviaIntegrazione]";
        LOG.info(prf + " BEGIN");
        boolean result = false;
        int rowsUpdated = 0;
        try {
            String query =
                    "UPDATE PBANDI_T_RICHIESTA_INTEGRAZ \n" +
                    "SET DT_INVIO = CURRENT_DATE,\n" +
                    "ID_UTENTE_INVIO = ?,\n" +
                    "ID_STATO_RICHIESTA = 2,\n" +
                    "DT_AGGIORNAMENTO = CURRENT_DATE,\n" +
                    "ID_UTENTE_AGG = ?\n" +
                    "WHERE ID_RICHIESTA_INTEGRAZ = ?";

            LOG.debug(query);

            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            Object[] args = new Object[] {userInfoSec.getIdUtente(), userInfoSec.getIdUtente(), idIntegrazione};
            int[] types = new int[] {Types.INTEGER, Types.INTEGER, Types.INTEGER};
            rowsUpdated = getJdbcTemplate().update(query, args, types);

            //UPDATE GESTIONE_REVOCA
            query =
                    "UPDATE PBANDI_T_GESTIONE_REVOCA \n" +
                    "SET ID_ATTIVITA_REVOCA = 7,\n" +
                    "DT_ATTIVITA = CURRENT_DATE,\n" +
                    "ID_UTENTE_AGG = :idUtente,\n" +
                    "DT_AGGIORNAMENTO = CURRENT_DATE \n" +
                    "WHERE ID_GESTIONE_REVOCA IN (\n" +
                    "\tSELECT lastGestioneRevoca.id_gestione_revoca\n" +
                    "\tFROM PBANDI_T_RICHIESTA_INTEGRAZ richiestaIntegraz\n" +
                    "\tJOIN PBANDI_C_ENTITA entita ON entita.id_entita = richiestaIntegraz.id_entita\n" +
                    "\tJOIN PBANDI_T_GESTIONE_REVOCA gestioneRevoca ON gestioneRevoca.id_gestione_revoca = richiestaIntegraz.id_target\n" +
                    "\tJOIN PBANDI_T_GESTIONE_REVOCA lastGestioneRevoca ON lastGestioneRevoca.numero_revoca = gestioneRevoca.numero_revoca\n" +
                    "\tWHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'\n" +
                    "\tAND lastGestioneRevoca.id_tipologia_revoca = 2\n" +
                    "\tAND lastGestioneRevoca.dt_fine_validita IS NULL\n" +
                    "\tAND richiestaIntegraz.id_richiesta_integraz = :idIntegrazione\n" +
                    ")";
            LOG.debug(query);
            args = new Object[] {userInfoSec.getIdUtente(), idIntegrazione};
            types = new int[] {Types.INTEGER, Types.INTEGER};
            getJdbcTemplate().update(query, args, types);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rowsUpdated > 0) {
            result = true;
        }

        return result;
    }

    //INTEGRAZIONE ALLA RENDICONTAZIONE
    @Override
    public List<IntegrazioneRendicontazioneDTO> getIntegrazioniRendicontazione(Long idProgetto, HttpServletRequest req) {
        String prf = "[GestioneIntegrazioniDAOImpl::getIntegrazioniRendicontazione] ";
        LOG.info(prf + "BEGIN");

        List<IntegrazioneRendicontazioneDTO> elencoDTO;

        //LOG.info(prf + " idProgetto = " + idProgetto + ", idBandoLinea = " + idBandoLinea);

        try {
            //get integrazioni
            String query =
                    "SELECT \n" +
                    "dichSpesa.id_dichiarazione_spesa AS idDichiarazioneSpesa,\n" +
                    "integrazioneSpesa.id_integrazione_spesa as idIntegrazioneSpesa,\n" +
                    "integrazioneSpesa.data_richiesta as dataRichiesta,\n" +
                    "integrazioneSpesa.dt_notifica as dataNotifica,\n" +
                    "integrazioneSpesa.num_giorni_scadenza as numGiorniScadenza,\n" +
                    "integrazioneSpesa.data_invio as dataInvio,\n" +
                    "statoRichIntegr.id_stato_richiesta as idStatoRichiesta,\n" +
                    "statoRichIntegr.desc_breve_stato_richiesta as descBreveStatoRichiesta,\n" +
                    "statoRichIntegr.desc_stato_richiesta as descStatoRichiesta,\n" +
                    "CASE (\n" +
                    "\tSELECT COUNT(*)\n" +
                    "\tFROM PBANDI_T_FILE_ENTITA ptfe\n" +
                    "\tWHERE ptfe.ID_ENTITA = 63 AND ptfe.ID_INTEGRAZIONE_SPESA = integrazioneSpesa.ID_INTEGRAZIONE_SPESA AND ptfe.FLAG_ENTITA = 'I'\n" +
                    ") WHEN 0 THEN 0 ELSE 1 END AS allegatiInseriti\n" +
                    "FROM PBANDI_T_INTEGRAZIONE_SPESA integrazioneSpesa\n" +
                    "JOIN PBANDI_D_STATO_RICH_INTEGRAZ statoRichIntegr ON statoRichIntegr.ID_STATO_RICHIESTA = integrazioneSpesa.ID_STATO_RICHIESTA\n" +
                    "JOIN PBANDI_T_DICHIARAZIONE_SPESA dichSpesa ON dichSpesa.ID_DICHIARAZIONE_SPESA = integrazioneSpesa.ID_DICHIARAZIONE_SPESA\n" +
                    "WHERE integrazioneSpesa.ID_STATO_RICHIESTA IN (1, 2, 3) AND dichSpesa.id_progetto = ?\n" +
                    "ORDER BY integrazioneSpesa.DATA_RICHIESTA DESC";
            elencoDTO = getJdbcTemplate().query(query, new Object[]{idProgetto}, new IntegrazioneRendicontazioneRowMapper());

            if(elencoDTO != null) {
                for (IntegrazioneRendicontazioneDTO integrazioneRendicontazioneDTO : elencoDTO) {
                    //Aggiorno la data scadenza in base alle proroghe approvate
                    try {
                        int numGiorni = getJdbcTemplate().queryForObject(
                                "SELECT SUM(NUM_GIORNI_APPROV)\n" +
                                    "FROM PBANDI_T_PROROGA proroga\n" +
                                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_integrazione_spesa = proroga.id_target\n" +
                                    "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = proroga.id_entita\n" +
                                    "WHERE entita.nome_entita = 'PBANDI_T_INTEGRAZIONE_SPESA'\n" +
                                    "AND proroga.id_stato_proroga = 2\n" +
                                    "AND integrazione.id_integrazione_spesa = ?",
                                Integer.class,
                                integrazioneRendicontazioneDTO.getIdIntegrazioneSpesa()
                        );

                        if(integrazioneRendicontazioneDTO.getDataScadenza() != null && numGiorni > 0) {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = dateFormat.parse(integrazioneRendicontazioneDTO.getDataScadenza());

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            calendar.add(Calendar.DATE, numGiorni);
                            date = calendar.getTime();

                            integrazioneRendicontazioneDTO.setDataScadenza(dateFormat.format(date));
                        }
                    }catch (Exception ignored){}

                    //set stato proroga
                    getJdbcTemplate().query(
                        "SELECT * FROM (\n" +
                            "\tSELECT\n" +
                            "\tstatoProroga.id_stato_proroga as idStatoProroga, \n" +
                            "\tstatoProroga.desc_stato_proroga AS statoProroga\n" +
                            "\tFROM PBANDI_T_PROROGA proroga\n" +
                            "\tJOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                            "\tWHERE proroga.ID_ENTITA = 453\n" +
                            "\tAND proroga.id_target = ?\n" +
                            "\tORDER BY proroga.DT_INSERIMENTO DESC \n" +
                            ") WHERE rownum <= 1",
                            resultSet -> {
                                    integrazioneRendicontazioneDTO.setIdStatoProroga(resultSet.getLong("idStatoProroga"));
                                    integrazioneRendicontazioneDTO.setStatoProroga(resultSet.getString("statoProroga"));
                            },
                            integrazioneRendicontazioneDTO.getIdIntegrazioneSpesa()
                    );
                    //set richiedi integrazione
                    integrazioneRendicontazioneDTO.setRichiediProroga(
                            integrazioneRendicontazioneDTO.getIdStatoRichiesta() != null &&
                            integrazioneRendicontazioneDTO.getIdStatoRichiesta() == 1L &&

                            integrazioneRendicontazioneDTO.getDataNotifica() != null &&

                            (integrazioneRendicontazioneDTO.getIdStatoProroga() == null ||
                            (integrazioneRendicontazioneDTO.getIdStatoProroga() != 1 &&
                            integrazioneRendicontazioneDTO.getIdStatoProroga() != 3))
                    );
                }
            }
        } catch (Exception e) {
            elencoDTO = new ArrayList<>();
            LOG.info(prf + " Error while trying to getIntegrazioniRendicontazione: " + e);
        }

        LOG.info(prf + " END");

        return elencoDTO;
    }
    @Override
    public RegoleAllegatiIntegrazioneDTO getRegoleIntegrazione(Long idBandoLinea, HttpServletRequest req){
        String prf = "[GestioneIntegrazioniDAOImpl::getRegoleIntegrazione] ";
        LOG.info(prf + "BEGIN");

        RegoleAllegatiIntegrazioneDTO regoleAllegatiIntegrazioneDTO = new RegoleAllegatiIntegrazioneDTO();

        try {
            //Gestione regole documenti
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            Long idUtente = userInfoSec.getIdUtente();
            String idIride = userInfoSec.getIdIride();
            // Gli allegati ai pagamenti sono ammessi solo se e' abilitata la regola BR51.
            Boolean allegatoDocumentoDiSpesa = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
                    idBandoLinea, RegoleConstants.BR51_UPLOAD_ALLEGATI_SPESA);
            regoleAllegatiIntegrazioneDTO.setAllegatiAmmessiDocumentoSpesa(allegatoDocumentoDiSpesa);
            // Gli allegati alle quietanze sono ammessi solo se e' abilitata la regola BR52.
            Boolean allegatoQuietanza = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
                    idBandoLinea, RegoleConstants.BR52_UPLOAD_ALLEGATI_QUIETANZA);
            regoleAllegatiIntegrazioneDTO.setAllegatiAmmessiQuietanze(allegatoQuietanza);
            // Permette di caricare degli allegati generici (facoltativi) alla dichiarazione di spesa regola BR53
            Boolean allegatoGenerico = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
                    idBandoLinea, RegoleConstants.BR53_UPLOAD_ALLEGATI_GENERICI);
            regoleAllegatiIntegrazioneDTO.setAllegatiAmmessiGenerici(allegatoGenerico);
            // Gli allegati ai pagamenti\quietanze sono ammessi solo se e' abilitata la regola BR42.
            Boolean allegatiAmmessi = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
                    idBandoLinea, RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
            regoleAllegatiIntegrazioneDTO.setAllegatiAmmessi(allegatiAmmessi);
        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getRegoleIntegrazione: " + e);
        }

        LOG.info(prf + " END");

        return regoleAllegatiIntegrazioneDTO;
    }
    @Override
    public List<IntegrazioneRendicontazioneDTO> getRichProrogaRendicontazione(Long idIntegrazione) {
        String prf = "[GestioneIntegrazioniDAOImpl::getRichProrogaRendicontazione]";
        LOG.info(prf + " BEGIN");

        List<IntegrazioneRendicontazioneDTO> integrazioneRendicontazioneDTOList = new ArrayList<>();

        try {
            String query =
                    "SELECT \n" +
                    "proroga.dt_richiesta AS dataRichiesta,\n" +
                    "proroga.num_giorni_rich AS giorniRichiesti,\n" +
                    "proroga.motivazione AS motivazione,\n" +
                    "proroga.num_giorni_approv AS giorniApprovati,\n" +
                    "statoRichiesta.id_stato_richiesta AS idStatoRichiesta,\n" +
                    "statoRichiesta.desc_breve_stato_richiesta AS descBreveStatoRichiesta,\n" +
                    "statoRichiesta.desc_stato_richiesta AS descStatoRichiesta,\n" +
                    "statoProroga.desc_breve_stato_proroga AS statoProroga\n" +
                    "FROM PBANDI_T_PROROGA proroga\n" +
                    "JOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                    "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = proroga.id_entita\n" +
                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazioneSpesa ON integrazioneSpesa.id_integrazione_spesa = proroga.id_target\n" +
                    "JOIN PBANDI_D_STATO_RICHIESTA statoRichiesta ON statoRichiesta.id_stato_richiesta = integrazioneSpesa.id_stato_richiesta\n" +
                    "WHERE entita.nome_entita = 'PBANDI_T_INTEGRAZIONE_SPESA'\n" +
                    "AND integrazioneSpesa.id_integrazione_spesa = :idIntegrazione\n" +
                    "ORDER BY proroga.dt_richiesta";

            LOG.debug(query);

            Object[] args = new Object[] {idIntegrazione};
            int[] types = new int[] {Types.INTEGER};
            integrazioneRendicontazioneDTOList = getJdbcTemplate().query(query, args, types,
                    (rs, rownum) -> {
                        IntegrazioneRendicontazioneDTO dto = new IntegrazioneRendicontazioneDTO();

                        dto.setDtRichiesta(rs.getDate("dataRichiesta").toString());
                        dto.setGgRichiesti(rs.getLong("giorniRichiesti"));
                        dto.setMotivazione(rs.getString("motivazione"));
                        dto.setGgApprovati(rs.getLong("giorniApprovati"));
                        dto.setIdStatoRichiesta(rs.getLong("idStatoRichiesta"));
                        dto.setStatoRichiesta(rs.getString("descBreveStatoRichiesta"));
                        dto.setLongStatoRichiesta(rs.getString("descStatoRichiesta"));
                        dto.setStatoProroga(rs.getString("statoProroga"));

                        return dto;
                    });

        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getRichProrogaRendicontazione: " + e);
        }

        LOG.info(prf + " END");
        return integrazioneRendicontazioneDTOList;
    }
    @Override
    public Boolean inserisciRichProrogaRendicontazione(Long idIntegrazione, IntegrazioneRendicontazioneDTO integrazioneRendicontazioneDTO, HttpServletRequest req) {
        String prf = "[GestioneIntegrazioniDAOImpl::inserisciRichProrogaRendicontazione]";
        LOG.info(prf + " BEGIN");
        boolean result = false;
        int rowsUpdated = 0;
        try {
            String query =
                    "insert into PBANDI_T_PROROGA  \r\n"
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
                            + "453,\r\n"
                            + ":idIntegrazione,\r\n"
                            + ":ggRichiesti,\r\n"
                            + ":motivazione,\r\n"
                            + "1,\r\n"
                            + ":idUtente,\r\n"
                            + "CURRENT_DATE,\r\n"
                            + "CURRENT_DATE)\n";

            LOG.debug(query);

            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            Object[] args = new Object[] {idIntegrazione, integrazioneRendicontazioneDTO.getGgRichiesti(), integrazioneRendicontazioneDTO.getMotivazione(), userInfoSec.getIdUtente()};
            int[] types = new int[] {Types.INTEGER, Types.INTEGER, Types.VARCHAR , Types.INTEGER};
            rowsUpdated = getJdbcTemplate().update(query, args, types);

        } catch (Exception e) {
            LOG.info(prf + " Error while trying to inserisciRichProrogaRendicontazione: " + e);
        }
        if (rowsUpdated > 0) {
            result = true;
        }

        LOG.info(prf + " END");
        return result;
    }
    @Override
    public Boolean inviaIntegrazioneRendicontazione(Long idIntegrazione, HttpServletRequest req) {
        String prf = "[GestioneIntegrazioniDAOImpl::inviaIntegrazioneRendicontazione]";
        LOG.info(prf + " BEGIN");
        boolean result = false;
        int rowsUpdated = 0;
        try {
            String query =
                    "UPDATE PBANDI_T_INTEGRAZIONE_SPESA \n" +
                    "SET DATA_INVIO = CURRENT_DATE,\n" +
                    "ID_UTENTE_INVIO = ?,\n" +
                    "ID_STATO_RICHIESTA = 2 \n" +
                    "WHERE ID_INTEGRAZIONE_SPESA = ?";

            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            Object[] args = new Object[] {userInfoSec.getIdUtente(), idIntegrazione};
            
            LOG.info(prf+"<idUtenteInvio>: "+userInfoSec.getIdUtente()+", <idIntegrazioneSpesa>: "+idIntegrazione);
            LOG.info(query);
            
            int[] types = new int[] {Types.INTEGER, Types.INTEGER};
            rowsUpdated = getJdbcTemplate().update(query, args, types);

            query =
                    "UPDATE PBANDI_T_PROROGA SET\n" +
                    "ID_STATO_PROROGA = 4,\n" +
                    "ID_UTENTE_AGG = ?,\n" +
                    "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                    "WHERE ID_RICHIESTA_PROROGA IN (\n" +
                    "\tSELECT proroga.id_richiesta_proroga\n" +
                    "\tFROM PBANDI_T_PROROGA proroga \n" +
                    "\tJOIN PBANDI_C_ENTITA entita ON entita.id_entita = proroga.id_entita\n" +
                    "\tJOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_integrazione_spesa = proroga.id_target\n" +
                    "\tWHERE proroga.id_stato_proroga = 1\n" +
                    "\tAND entita.nome_entita = 'PBANDI_T_INTEGRAZIONE_SPESA'\n" +
                    "\tAND integrazione.id_integrazione_spesa = ?\n" +
                    ")";
            LOG.info(prf+"<idUtenteInvio>: "+userInfoSec.getIdUtente()+", <idIntegrazioneSpesa>: "+idIntegrazione);
            LOG.info(query);
            getJdbcTemplate().update(query, args, types);
        } catch (Exception e) {
            LOG.error(prf + " Error while trying to inviaIntegrazioneRendicontazione: " + e);
            throw e;
        }
        if (rowsUpdated > 0) {
            result = true;
        }

        LOG.info(prf + " END");
        return result;
    }

    //TAB Note alla richiesta di integrazione
    @Override
    public List<DocIntegrRendDTO> getLetteraAccIntegrazRendicont(Long idIntegrazione) {
        String prf = "[GestioneIntegrazioniDAOImpl::getLetteraAccIntegrazRendicont]";
        LOG.info(prf + " BEGIN");

        List<DocIntegrRendDTO> docIntegrRendDTO = new ArrayList<>();
        try {
            Object[] args = new Object[]{idIntegrazione};
            int[] types = new int[] {Types.INTEGER};

            String query =
                    "SELECT \n" +
                            "docIndex.id_documento_index as idDocIndex,\n" +
                            "integrazSpesa.descrizione AS note,\n" +
                            "docIndex.nome_file AS nomeFile\n" +
                            "FROM PBANDI_T_INTEGRAZIONE_SPESA integrazSpesa \n" +
                            "LEFT JOIN PBANDI_T_DOCUMENTO_INDEX docIndex ON docIndex.id_target = integrazSpesa.id_integrazione_spesa\n" +
                            "\tAND docIndex.id_entita = 453 AND docIndex.id_tipo_documento_index = 35\n" +
                            "\tAND docIndex.FLAG_VISIBILE_BEN != 'N'\n" +
                            "WHERE integrazSpesa.id_integrazione_spesa = :idIntegrazione";
            LOG.debug(query);

            docIntegrRendDTO = getJdbcTemplate().query(query, args, types,
                    (rs, rowNum) -> {
                        DocIntegrRendDTO integrRendDTO = new DocIntegrRendDTO();

                        integrRendDTO.setIdDocumentoIndex(rs.getLong("idDocIndex"));
                        integrRendDTO.setNomeFile(rs.getString("nomeFile"));
                        integrRendDTO.setNote(rs.getString("note"));

                        return integrRendDTO;
                    });
        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getLetteraAccIntegrazRendicont: " + e);
        }

        LOG.info(prf + " END");
        return docIntegrRendDTO;
    }

    @Override
    public List<DocIntegrRendDTO> getReportValidazione(Long idIntegrazione) {
        String prf = "[GestioneIntegrazioniDAOImpl::getReportValidazione]";
        LOG.info(prf + " BEGIN");

        List<DocIntegrRendDTO> docIntegrRendDTO = new ArrayList<>();
        try {
            int[] types = new int[] {Types.INTEGER};
            Object[] args = new Object[]{idIntegrazione};

            String query =
                    "SELECT \n" +
                            "docIndex.id_documento_index as idDocIndex, \n" +
                            "docIndex.nome_file AS nomeFile\n" +
                            "FROM PBANDI_T_DOCUMENTO_INDEX docIndex\n" +
                            "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazSpesa ON integrazSpesa.id_integrazione_spesa = docIndex.id_target\n" +
                            "WHERE docIndex.id_tipo_documento_index = 63\n" +
                            "AND docIndex.id_entita = 453\n" +
                            "AND integrazSpesa.id_integrazione_spesa = :idIntegrazione\n" +
                            "AND docIndex.FLAG_VISIBILE_BEN != 'N'";
            LOG.debug(query);

            docIntegrRendDTO = getJdbcTemplate().query(query, args, types,
                    (rs, rowNum) -> {
                        DocIntegrRendDTO integrRendDTO = new DocIntegrRendDTO();

                        integrRendDTO.setIdDocumentoIndex(rs.getLong("idDocIndex"));
                        integrRendDTO.setNomeFile(rs.getString("nomeFile"));

                        return integrRendDTO;
                    });
        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getReportValidazione: " + e);
        }

        LOG.info(prf + " END");
        return docIntegrRendDTO;
    }

    //TAB Allegati
    @Override
    public List<DocIntegrRendDTO> getAllegatiDichiarazioneSpesa(Long idDichiarazioneSpesa) {
        String prf = "[GestioneIntegrazioniDAOImpl::getAllegatiDichiarazioneSpesa]";
        LOG.info(prf + " BEGIN");

        List<DocIntegrRendDTO> docIntegrRendDTOS = new ArrayList<>();
        try {
            //ALLEGATI ALL'INVIO DS
            String query =
                    "SELECT " +
                    "documentoIndex.id_documento_index as idDocIndex, \n" +
                    "dichSpesa.dt_dichiarazione AS dataInvioDS,\n" +
                    "documentoIndex.nome_file AS nomeFile\n" +
                    "FROM PBANDI_T_FILE_ENTITA fileEntita\n" +
                    "JOIN PBANDI_T_DICHIARAZIONE_SPESA dichSpesa ON fileEntita.id_target = dichSpesa.id_dichiarazione_spesa\n" +
                    "JOIN PBANDI_T_FILE files ON files.id_file = fileEntita.id_file\n" +
                    "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_documento_index = files.id_documento_index\n" +
                    "WHERE dichSpesa.id_dichiarazione_spesa = :idDichiarazioneSpesa \n" +
                    "AND fileEntita.id_entita = 63 \n" +
                    "AND fileEntita.id_integrazione_spesa IS NULL";

            LOG.debug(query);

            Object[] args = new Object[] {idDichiarazioneSpesa};
            int[] types = new int[] {Types.INTEGER};
            docIntegrRendDTOS.addAll(getJdbcTemplate().query(query, args, types,
                    (rs, rowNum) -> {
                        DocIntegrRendDTO integrRendDTO = new DocIntegrRendDTO();

                        integrRendDTO.setIdDocumentoIndex(rs.getLong("idDocIndex"));
                        integrRendDTO.setNomeFile(rs.getString("nomeFile"));
                        integrRendDTO.setData(rs.getDate("dataInvioDS"));

                        return integrRendDTO;
                    }));

        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getAllegatiDichiarazioneSpesa: " + e);
            docIntegrRendDTOS = new ArrayList<>();
        }

        LOG.info(prf + " END");
        return docIntegrRendDTOS;
    }
    @Override
    public List<List<DocIntegrRendDTO>> getAllegatiIntegrazioneDS(Long idIntegrazioneSpesaCorrente, Long idDichiarazioneSpesa) {
        String prf = "[GestioneIntegrazioniDAOImpl::getAllegatiIntegrazioneDS]";
        LOG.info(prf + " BEGIN");

        List<DocIntegrRendDTO> docIntegrRendDTOS = new ArrayList<>();
        try {
            Object[] args = new Object[] {idIntegrazioneSpesaCorrente, idDichiarazioneSpesa};
            int[] types = new int[] {Types.INTEGER, Types.INTEGER};

            //PER OGNI INTEGRAZIONE ALLEGATI ALL'INTEGRAZIONE
            String getIdIntegrazioneSpesa =
                    "SELECT DISTINCT \n" +
                    "ptis.id_integrazione_spesa as idIntegrazioneSpesa\n" +
                    "FROM PBANDI_T_INTEGRAZIONE_SPESA ptis \n" +
                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA ptis2 ON ptis2.id_integrazione_spesa = :idIntegrazioneSpesa\n" +
                    "WHERE ptis.data_invio IS NOT NULL\n" +
                    "AND ptis.id_stato_richiesta = 2\n" +
                    "AND ptis.id_dichiarazione_spesa = :idDichiarazioneSpesa\n" +
                    "AND ptis.data_richiesta <= ptis2.data_richiesta";
            LOG.debug(getIdIntegrazioneSpesa);

            String query =
                    "SELECT \n" +
                    "documentoIndex.id_documento_index as idDocIndex, \n" +
                    "integrazione.id_integrazione_spesa AS idIntegrazioneSpesa,\n" +
                    "integrazione.data_invio AS dtInvioIntegrazione,\n" +
                    "documentoIndex.nome_file AS nomeFile\n" +
                    "FROM PBANDI_T_FILE_ENTITA fileEntita\n" +
                    "JOIN PBANDI_T_DICHIARAZIONE_SPESA dichSpesa ON dichSpesa.id_dichiarazione_spesa = fileEntita.id_target\n" +
                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_integrazione_spesa = fileEntita.id_integrazione_spesa\n" +
                    "JOIN PBANDI_T_FILE files ON files.id_file = fileEntita.id_file\n" +
                    "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_documento_index = files.id_documento_index\n" +
                    "WHERE fileEntita.id_entita = 63 \n" +
                    "AND integrazione.id_integrazione_spesa = :idIntegrazione";

            for(Long idIntegrazioneSpesa : getJdbcTemplate().query(getIdIntegrazioneSpesa, args, types,
                    (rs, rowNum) -> rs.getLong("idIntegrazioneSpesa"))){
                Object[] args2 = new Object[] {idIntegrazioneSpesa};
                int[] types2 = new int[] {Types.INTEGER};
                //documenti
                docIntegrRendDTOS.addAll(getJdbcTemplate().query(query, args2, types2,
                        (rs, rowNum) -> {
                            DocIntegrRendDTO integrRendDTO = new DocIntegrRendDTO();

                            integrRendDTO.setIdDocumentoIndex(rs.getLong("idDocIndex"));
                            integrRendDTO.setNomeFile(rs.getString("nomeFile"));
                            integrRendDTO.setData(rs.getDate("dtInvioIntegrazione"));
                            integrRendDTO.setIdIntegrazione(rs.getLong("idIntegrazioneSpesa"));

                            return integrRendDTO;
                        }));
            }

        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getAllegatiIntegrazioneDS: " + e);
            docIntegrRendDTOS = new ArrayList<>();
        }

        LOG.info(prf + " END");
        return separateByIntegrazione(docIntegrRendDTOS);
    }
    @Override
    public List<DocIntegrRendDTO> getAllegatiNuovaIntegrazioneDS(Long idIntegrazioneSpesaCorrente, Long idDichiarazioneSpesa) {
        String prf = "[GestioneIntegrazioniDAOImpl::getAllegatiNuovaIntegrazioneDS]";
        LOG.info(prf + " BEGIN");

        List<DocIntegrRendDTO> docIntegrRendDTOS = new ArrayList<>();
        try {
            Object[] args = new Object[] {idIntegrazioneSpesaCorrente};
            int[] types = new int[] {Types.INTEGER};

            //PER OGNI INTEGRAZIONE IN CORSO ALLEGATI ALL'INTEGRAZIONE
            String getIdIntegrazioneSpesa =
                    "SELECT DISTINCT \n" +
                    "ptis.id_integrazione_spesa as idIntegrazioneSpesa\n" +
                    "FROM PBANDI_T_INTEGRAZIONE_SPESA ptis \n" +
                    "WHERE ptis.data_invio IS NULL\n" +
                    "AND ptis.id_stato_richiesta = 1\n" +
                    "AND ptis.id_integrazione_spesa = :idIntegrazioneSpesa";
            LOG.debug(getIdIntegrazioneSpesa);
            String query =
                    "SELECT " +
                    "documentoIndex.id_documento_index as idDocIndex, \n" +
                    "fileEntita.id_file_entita as idFileEntita,\n" +
                    "fileEntita.flag_entita as flagEntita,\n" +
                    "integrazione.id_integrazione_spesa AS idIntegrazioneSpesa,\n" +
                    "documentoIndex.nome_file AS nomeFile\n" +
                    "FROM PBANDI_T_FILE_ENTITA fileEntita\n" +
                    "JOIN PBANDI_T_DICHIARAZIONE_SPESA dichSpesa ON dichSpesa.id_dichiarazione_spesa = fileEntita.id_target\n" +
                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_integrazione_spesa = fileEntita.id_integrazione_spesa\n" +
                    "JOIN PBANDI_T_FILE files ON files.id_file = fileEntita.id_file\n" +
                    "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_documento_index = files.id_documento_index\n" +
                    "WHERE fileEntita.id_entita = 63 \n" +
                    "AND integrazione.id_integrazione_spesa = :idIntegrazione";

            for(Long idIntegrazioneSpesa : getJdbcTemplate().query(getIdIntegrazioneSpesa, args, types,
                    (rs, rowNum) -> rs.getLong("idIntegrazioneSpesa"))){
                Object[] args2 = new Object[] {idIntegrazioneSpesa};
                int[] types2 = new int[] {Types.INTEGER};
                //documenti
                docIntegrRendDTOS.addAll(getJdbcTemplate().query(query, args2, types2,
                        (rs, rowNum) -> {
                            DocIntegrRendDTO integrRendDTO = new DocIntegrRendDTO();

                            integrRendDTO.setIdDocumentoIndex(rs.getLong("idDocIndex"));
                            integrRendDTO.setIdFileEntita(rs.getLong("idFileEntita"));
                            integrRendDTO.setFlagEntita(rs.getString("flagEntita"));
                            integrRendDTO.setNomeFile(rs.getString("nomeFile"));
                            integrRendDTO.setIdIntegrazione(rs.getLong("idIntegrazioneSpesa"));

                            return integrRendDTO;
                        }));
            }


        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getAllegatiNuovaIntegrazioneDS: " + e);
            docIntegrRendDTOS = new ArrayList<>();
        }

        LOG.info(prf + " END");
        return docIntegrRendDTOS;
    }

    //TAB Documenti di spesa sospesi
    @Override
    public List<DocumentoDiSpesaSospesoDTO> getDocumentiDiSpesaSospesi(Long idDichiarazioneSpesa, Long idProgetto) {
        String prf = "[GestioneIntegrazioniDAOImpl::getDocumentiDiSpesaSospesi]";
        LOG.info(prf + " BEGIN");

        List<DocumentoDiSpesaSospesoDTO> documentoDiSpesaDTOS = new ArrayList<>();
        try {
            String query =
                    "SELECT\r\n"
                    + "prdsp.id_documento_di_spesa AS idDocumentoSpesa,\r\n"
                    + "pdtds.desc_tipo_documento_spesa || ' ' || ptdds.numero_documento AS documento,\r\n"
                    + "TRIM(ptf.denominazione_fornitore || ptf.nome_fornitore || ' ' || ptf.cognome_fornitore) AS fornitore,\r\n"
                    + "prdsp.importo_rendicontazione AS importo,\r\n"
                    + "prdsp.note_validazione AS note,\r\n"
                    + "ptdds.dt_emissione_documento AS dataEmissioneDocumento\r\n"
                    + "FROM PBANDI_R_DOC_SPESA_PROGETTO prdsp\r\n"
                    + "JOIN PBANDI_T_DOCUMENTO_DI_SPESA ptdds ON ptdds.id_documento_di_spesa = prdsp.id_documento_di_spesa\r\n"
                    + "JOIN PBANDI_V_DICH_DOC_SPESA pvdds ON pvdds.id_documento_di_spesa = ptdds.id_documento_di_spesa\r\n"
                    + "JOIN PBANDI_D_TIPO_DOCUMENTO_SPESA pdtds ON pdtds.id_tipo_documento_spesa = ptdds.id_tipo_documento_spesa\r\n"
                    + "JOIN PBANDI_D_STATO_DOCUMENTO_SPESA pdsds ON pdsds.ID_STATO_DOCUMENTO_SPESA = prdsp.ID_STATO_DOCUMENTO_SPESA_VALID\r\n"
                    + "LEFT JOIN PBANDI_T_FORNITORE ptf ON ptf.id_fornitore = ptdds.id_fornitore\r\n"
                    + "WHERE pdsds.DESC_BREVE_STATO_DOC_SPESA = :descBreveStatoDocSospeso AND \r\n"
                    + "pvdds.ID_DICHIARAZIONE_SPESA = :idDichiarazioneSpesa";
            LOG.debug(query);

            Object[] args = new Object[] {Constants.CODICE_STATO_DOCUMENTO_SOSPESO, idDichiarazioneSpesa};
            int[] types = new int[] {Types.CHAR, Types.INTEGER};
            documentoDiSpesaDTOS.addAll(getJdbcTemplate().query(query, args, types,
                    (rs, rowNum) -> {
                        DocumentoDiSpesaSospesoDTO dto = new DocumentoDiSpesaSospesoDTO();

                        dto.setIdDocumentoDiSpesa(rs.getLong("idDocumentoSpesa"));
                        dto.setDocumento(rs.getString("documento"));
                        dto.setFornitore(rs.getString("fornitore"));
                        dto.setImporto(rs.getDouble("importo"));
                        dto.setNote(rs.getString("note"));
                        dto.setData(rs.getDate("dataEmissioneDocumento"));

                        return dto;
                    }));

        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getDocumentiDiSpesaSospesi: " + e);
        }

        LOG.info(prf + " END");
        return documentoDiSpesaDTOS;
    }

    @Override
    public List<DocumentoDiSpesaSospesoDTO> getDocumentiDiSpesaIntegrati(Long idIntegrazione, Long idDichiarazioneSpesa, Long idProgetto) {
        String prf = "[GestioneIntegrazioniDAOImpl::getDocumentiDiSpesaIntegrati]";
        LOG.info(prf + " BEGIN");

        List<DocumentoDiSpesaSospesoDTO> documentoDiSpesaDTOS = new ArrayList<>();
        try {
            String query =
                    "SELECT DISTINCT(doc_sp.ID_DOCUMENTO_DI_SPESA) AS idDocumentoSpesa,\r\n"
                    + "	tdoc.desc_tipo_documento_spesa || ' ' || doc_sp.numero_documento AS documento,\r\n"
                    + "	TRIM(f.denominazione_fornitore || f.nome_fornitore || ' ' || f.cognome_fornitore) AS fornitore,\r\n"
                    + "	dsp.importo_rendicontazione AS importo,\r\n"
                    + "	dsp.note_validazione AS note,\r\n"
                    + "	doc_sp.dt_emissione_documento AS dataEmissioneDocumento\r\n"
                    + "FROM PBANDI_T_INTEGRAZIONE_SPESA isp  \r\n"
                    + "JOIN PBANDI_T_FILE_ENTITA fe ON fe.ID_INTEGRAZIONE_SPESA = isp.ID_INTEGRAZIONE_SPESA\r\n"
                    + "JOIN PBANDI_V_DICH_DOC_SPESA dds ON dds.ID_DICHIARAZIONE_SPESA =isp.ID_DICHIARAZIONE_SPESA\r\n"
                    + "JOIN PBANDI_T_DOCUMENTO_DI_SPESA doc_sp ON doc_sp.ID_DOCUMENTO_DI_SPESA = dds.ID_DOCUMENTO_DI_SPESA \r\n"
                    + "JOIN PBANDI_C_ENTITA e ON e.ID_ENTITA = fe.ID_ENTITA\r\n"
                    + "JOIN PBANDI_R_DOC_SPESA_PROGETTO dsp ON dsp.ID_DOCUMENTO_DI_SPESA = doc_sp.ID_DOCUMENTO_DI_SPESA \r\n"
                    + "JOIN PBANDI_D_TIPO_DOCUMENTO_SPESA tdoc ON tdoc.ID_TIPO_DOCUMENTO_SPESA = doc_sp.ID_TIPO_DOCUMENTO_SPESA \r\n"
                    + "LEFT JOIN PBANDI_T_FORNITORE f ON f.id_fornitore = doc_sp.id_fornitore\r\n"
                    + "WHERE isp.ID_INTEGRAZIONE_SPESA = ? \r\n"
                    + "	AND isp.DATA_INVIO IS NOT NULL \r\n"
                    + "	AND doc_sp.ID_DOCUMENTO_DI_SPESA IN (\r\n"
                    + "			SELECT doc.ID_DOCUMENTO_DI_SPESA\r\n"
                    + "			FROM PBANDI_T_DOCUMENTO_DI_SPESA doc \r\n"
                    + "			WHERE doc.ID_DOCUMENTO_DI_SPESA = fe.ID_TARGET AND e.NOME_ENTITA = ?\r\n"
                    + "			UNION\r\n"
                    + "			SELECT pds.ID_DOCUMENTO_DI_SPESA\r\n"
                    + "			FROM PBANDI_R_PAGAMENTO_DOC_SPESA pds\r\n"
                    + "			WHERE fe.ID_TARGET = pds.ID_PAGAMENTO AND e.NOME_ENTITA = ?\r\n"
                    + "		)";
            
            Object[] args = new Object[] {idIntegrazione, Constants.ENTITA_T_DOCUMENTO_DI_SPESA, Constants.ENTITA_T_PAGAMENTO};
            
            LOG.info(prf +  " <idIntegrazione>: "+idIntegrazione +", <entitaDocSpesa>: "+ Constants.ENTITA_T_DOCUMENTO_DI_SPESA+", <entitaPagamento>: "+Constants.ENTITA_T_PAGAMENTO);
            LOG.info(query);
            
            int[] types = new int[] {Types.INTEGER, Types.CHAR, Types.CHAR};
            documentoDiSpesaDTOS.addAll(getJdbcTemplate().query(query, args, types,
                    (rs, rowNum) -> {
                        DocumentoDiSpesaSospesoDTO dto = new DocumentoDiSpesaSospesoDTO();

                        dto.setIdDocumentoDiSpesa(rs.getLong("idDocumentoSpesa"));
                        dto.setDocumento(rs.getString("documento"));
                        dto.setFornitore(rs.getString("fornitore"));
                        dto.setImporto(rs.getDouble("importo"));
                        dto.setNote(rs.getString("note"));
                        dto.setData(rs.getDate("dataEmissioneDocumento"));

                        return dto;
                    }));

        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getDocumentiDiSpesaSospesi: " + e);
        }

        LOG.info(prf + " END");
        return documentoDiSpesaDTOS;
    }

    @Override
    public List<DocIntegrRendDTO> getAllegatiDocumentoSpesa(Long idDichiarazioneSpesa, Long idDocumentoSpesa) {
        String prf = "[GestioneIntegrazioniDAOImpl::getAllegatiDocumentoSpesa]";
        LOG.info(prf + " BEGIN");

        List<DocIntegrRendDTO> docIntegrRendDTOS = new ArrayList<>();
        try {
            //ALLEGATI ALL'INVIO DocumentoSpesa
            String query =
                    "SELECT \n" +
                    "documentoIndex.id_documento_index as idDocIndex, \n" +
                    "documentoIndex.nome_file AS nomeFile,\n" +
                    "dichSpesa.dt_dichiarazione AS dataDichiarazione\n" +
                    "FROM PBANDI_T_FILE_ENTITA fileEntita\n" +
                    "JOIN PBANDI_T_DOCUMENTO_DI_SPESA docSpesa ON fileEntita.id_target = docSpesa.id_documento_di_spesa\n" +
                    "JOIN PBANDI_T_FILE files ON files.id_file = fileEntita.id_file\n" +
                    "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_documento_index = files.id_documento_index\n" +
                    "JOIN PBANDI_T_DICHIARAZIONE_SPESA dichSpesa ON dichSpesa.ID_DICHIARAZIONE_SPESA = :idDichiarazioneSpesa\n" +
                    "WHERE docSpesa.id_documento_di_spesa = :idDocumentoSpesa \n" +
                    "AND fileEntita.id_entita = 123 \n" +
                    "AND fileEntita.id_integrazione_spesa IS NULL";
            LOG.debug(query);

            Object[] args = new Object[] {idDichiarazioneSpesa, idDocumentoSpesa};
            int[] types = new int[] {Types.INTEGER, Types.INTEGER};
            docIntegrRendDTOS.addAll(getJdbcTemplate().query(query, args, types,
                    (rs, rowNum) -> {
                        DocIntegrRendDTO integrRendDTO = new DocIntegrRendDTO();

                        integrRendDTO.setIdDocumentoIndex(rs.getLong("idDocIndex"));
                        integrRendDTO.setNomeFile(rs.getString("nomeFile"));
                        integrRendDTO.setData(rs.getDate("dataDichiarazione"));

                        return integrRendDTO;
                    }));

        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getAllegatiIntegrazioneDocS: " + e);
            docIntegrRendDTOS = new ArrayList<>();
        }

        LOG.info(prf + " END");
        return docIntegrRendDTOS;
    }
    @Override
    public List<List<DocIntegrRendDTO>> getAllegatiIntegrazioneDocS(Long idIntegrazioneSpesaCorrente, Long idDichiarazioneSpesa, Long idDocumentoSpesa) {
        String prf = "[GestioneIntegrazioniDAOImpl::getAllegatiIntegrazioneDocS]";
        LOG.info(prf + " BEGIN");

        List<DocIntegrRendDTO> docIntegrRendDTOS = new ArrayList<>();
        try {
            //PER OGNI INTEGRAZIONE INVIATA ALLEGATI ALL'INTEGRAZIONE
            String getIdIntegrazioneSpesa =
                    "SELECT DISTINCT \n" +
                    "ptis.id_integrazione_spesa as idIntegrazioneSpesa\n" +
                    "FROM PBANDI_T_INTEGRAZIONE_SPESA ptis \n" +
                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA ptis2 ON ptis2.id_integrazione_spesa = :idIntegrazioneSpesa\n" +
                    "WHERE ptis.data_invio IS NOT NULL\n" +
                    "AND ptis.id_stato_richiesta = 2\n" +
                    "AND ptis.id_dichiarazione_spesa = :idDichiarazioneSpesa\n" +
                    "AND ptis.data_richiesta <= ptis2.data_richiesta";
            LOG.debug(getIdIntegrazioneSpesa);

            //ALLEGATI ALL'integrazione DocumentoSpesa
            String query =
                    "SELECT " +
                    "documentoIndex.id_documento_index as idDocIndex, \n" +
                    "integrazione.id_integrazione_spesa AS idIntegrazioneSpesa,\n" +
                    "integrazione.data_invio AS dtInvioIntegrazione,\n" +
                    "documentoIndex.nome_file AS nomeFile\n" +
                    "FROM PBANDI_T_FILE_ENTITA fileEntita\n" +
                    "JOIN PBANDI_T_DOCUMENTO_DI_SPESA docSpesa ON docSpesa.id_documento_di_spesa = fileEntita.id_target\n" +
                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_integrazione_spesa = fileEntita.id_integrazione_spesa\n" +
                    "JOIN PBANDI_T_FILE files ON files.id_file = fileEntita.id_file\n" +
                    "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_documento_index = files.id_documento_index\n" +
                    "WHERE fileEntita.id_entita = 123 \n" +
                    "AND integrazione.id_integrazione_spesa = :idIntegrazione\n" +
                    "AND docSpesa.id_documento_di_spesa = :idDocumentoDiSpesa";

            LOG.debug(query);

            Object[] args = new Object[] {idIntegrazioneSpesaCorrente, idDichiarazioneSpesa};
            int[] types = new int[] {Types.INTEGER, Types.INTEGER};
            for(Long idIntegrazioneSpesa : getJdbcTemplate().query(getIdIntegrazioneSpesa, args, types,
                    (rs, rowNum) -> rs.getLong("idIntegrazioneSpesa"))) {
                Object[] args2 = new Object[]{idIntegrazioneSpesa, idDocumentoSpesa};
                int[] types2 = new int[] {Types.INTEGER, Types.INTEGER};
                docIntegrRendDTOS.addAll(getJdbcTemplate().query(query, args2, types2,
                        (rs, rowNum) -> {
                            DocIntegrRendDTO integrRendDTO = new DocIntegrRendDTO();

                            integrRendDTO.setIdDocumentoIndex(rs.getLong("idDocIndex"));
                            integrRendDTO.setNomeFile(rs.getString("nomeFile"));
                            integrRendDTO.setData(rs.getDate("dtInvioIntegrazione"));
                            integrRendDTO.setIdIntegrazione(rs.getLong("idIntegrazioneSpesa"));

                            return integrRendDTO;
                        }));
            }
        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getAllegatiIntegrazioneDocS: " + e);
            docIntegrRendDTOS = new ArrayList<>();
        }

        LOG.info(prf + " END");
        return separateByIntegrazione(docIntegrRendDTOS);
    }
    @Override
    public List<DocIntegrRendDTO> getAllegatiNuovaIntegrazioneDocS(Long idIntegrazioneSpesaCorrente, Long idDocumentoSpesa) {
        String prf = "[GestioneIntegrazioniDAOImpl::getAllegatiNuovaIntegrazioneDocS]";
        LOG.info(prf + " BEGIN");

        List<DocIntegrRendDTO> docIntegrRendDTOS = new ArrayList<>();
        try {
            //PER OGNI INTEGRAZIONE IN CORSO ALLEGATI ALL'INTEGRAZIONE
            String getIdIntegrazioneSpesa =
                    "SELECT DISTINCT \n" +
                    "ptis.id_integrazione_spesa as idIntegrazioneSpesa\n" +
                    "FROM PBANDI_T_INTEGRAZIONE_SPESA ptis \n" +
                    "WHERE ptis.data_invio IS NULL\n" +
                    "AND ptis.id_stato_richiesta = 1\n" +
                    "AND ptis.id_integrazione_spesa = :idIntegrazioneSpesa";
            LOG.debug(getIdIntegrazioneSpesa);

            //ALLEGATI ALL'integrazione DocumentoSpesa
            String query =
                    "SELECT " +
                    "documentoIndex.id_documento_index as idDocIndex, \n" +
                    "fileEntita.id_file_entita as idFileEntita,\n" +
                    "integrazione.id_integrazione_spesa AS idIntegrazioneSpesa,\n" +
                    "integrazione.data_invio AS dtInvioIntegrazione,\n" +
                    "documentoIndex.nome_file AS nomeFile\n" +
                    "FROM PBANDI_T_FILE_ENTITA fileEntita\n" +
                    "JOIN PBANDI_T_DOCUMENTO_DI_SPESA docSpesa ON fileEntita.id_target = docSpesa.id_documento_di_spesa\n" +
                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_integrazione_spesa = fileEntita.id_integrazione_spesa\n" +
                    "JOIN PBANDI_T_FILE files ON files.id_file = fileEntita.id_file\n" +
                    "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_documento_index = files.id_documento_index\n" +
                    "WHERE fileEntita.id_entita = 123 \n" +
                    "AND integrazione.id_integrazione_spesa = :idIntegrazione\n" +
                    "AND docSpesa.id_documento_di_spesa = :idDocumentoDiSpesa";

            LOG.debug(query);

            Object[] args = new Object[] {idIntegrazioneSpesaCorrente};
            int[] types = new int[] {Types.INTEGER};
            for(Long idIntegrazioneSpesa : getJdbcTemplate().query(getIdIntegrazioneSpesa, args, types,
                    (rs, rowNum) -> rs.getLong("idIntegrazioneSpesa"))) {
                Object[] args2 = new Object[]{idIntegrazioneSpesa, idDocumentoSpesa};
                int[] types2 = new int[] {Types.INTEGER, Types.INTEGER};
                docIntegrRendDTOS.addAll(getJdbcTemplate().query(query, args2, types2,
                        (rs, rowNum) -> {
                            DocIntegrRendDTO integrRendDTO = new DocIntegrRendDTO();

                            integrRendDTO.setIdDocumentoIndex(rs.getLong("idDocIndex"));
                            integrRendDTO.setIdFileEntita(rs.getLong("idFileEntita"));
                            integrRendDTO.setNomeFile(rs.getString("nomeFile"));
                            integrRendDTO.setData(rs.getDate("dtInvioIntegrazione"));
                            integrRendDTO.setIdIntegrazione(rs.getLong("idIntegrazioneSpesa"));

                            return integrRendDTO;
                        }));
            }
        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getAllegatiNuovaIntegrazioneDocS: " + e);
            docIntegrRendDTOS = new ArrayList<>();
        }

        LOG.info(prf + " END");
        return docIntegrRendDTOS;
    }

    //TAB Quietanze
    @Override
    public List<QuietanzaDTO> getQuietanze(Long idDocumentoSpesa) {
        String prf = "[GestioneIntegrazioniDAOImpl::getQuietanze]";
        LOG.info(prf + " BEGIN");

        List<QuietanzaDTO> quietanzaDTOS = new ArrayList<>();
        try {
            String query =
                    "SELECT \n" +
                    "pagamento.id_pagamento AS idPagamento,\n" +
                    "modPagamento.id_modalita_pagamento AS idModalitaPagamento,\n" +
                    "modPagamento.desc_modalita_pagamento AS descModalitaPagamento,\n" +
                    "pagamento.dt_pagamento AS dataPagamento,\n" +
                    "pagamento.importo_pagamento AS importo\n" +
                    "FROM PBANDI_T_DOCUMENTO_DI_SPESA docSpesa\n" +
                    "JOIN PBANDI_R_PAGAMENTO_DOC_SPESA pagamentoDocSpesa ON pagamentoDocSpesa.id_documento_di_spesa = docSpesa.id_documento_di_spesa\n" +
                    "JOIN PBANDI_T_PAGAMENTO pagamento ON pagamento.id_pagamento = pagamentoDocSpesa.id_pagamento\n" +
                    "JOIN PBANDI_D_MODALITA_PAGAMENTO modPagamento ON modPagamento.id_modalita_pagamento = pagamento.id_modalita_pagamento\n" +
                    "WHERE docSpesa.id_documento_di_spesa = :idDocumentoSpesa";

            LOG.debug(query);

            Object[] args = new Object[] {idDocumentoSpesa};
            int[] types = new int[] {Types.INTEGER};
            quietanzaDTOS.addAll(getJdbcTemplate().query(query, args, types,
                    (rs, rowNum) -> {
                        QuietanzaDTO quietanzaDTO = new QuietanzaDTO();

                        quietanzaDTO.setIdPagamento(rs.getLong("idPagamento"));
                        quietanzaDTO.setIdModalitaPagamento(rs.getLong("idModalitaPagamento"));
                        quietanzaDTO.setDescModalitaPagamento(rs.getString("descModalitaPagamento"));
                        quietanzaDTO.setDataPagamento(rs.getDate("dataPagamento"));
                        quietanzaDTO.setImportoPagamento(rs.getDouble("importo"));

                        return quietanzaDTO;
                    }));

        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getQuietanze: " + e);
            quietanzaDTOS = new ArrayList<>();
        }

        LOG.info(prf + " END");
        return quietanzaDTOS;
    }
    @Override
    public List<DocIntegrRendDTO> getAllegatiQuietanza(Long idDichiarazioneSpesa, Long idQuietanza) {
        String prf = "[GestioneIntegrazioniDAOImpl::getAllegatiQuietanza]";
        LOG.info(prf + " BEGIN");

        List<DocIntegrRendDTO> docIntegrRendDTOS = new ArrayList<>();
        try {
            //ALLEGATI ALL'INVIO quietanza
            String query =
                    "SELECT \n" +
                    "documentoIndex.id_documento_index AS idDocIndex,\n" +
                    "documentoIndex.nome_file AS nomeFile,\n" +
                    "dichSpesa.dt_dichiarazione AS dataDichiarazione\n" +
                    "FROM PBANDI_T_FILE_ENTITA fileEntita\n" +
                    "JOIN PBANDI_T_PAGAMENTO quietanza ON fileEntita.id_target = quietanza.id_pagamento\n" +
                    "JOIN PBANDI_T_FILE files ON files.id_file = fileEntita.id_file\n" +
                    "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_documento_index = files.id_documento_index\n" +
                    "JOIN PBANDI_T_DICHIARAZIONE_SPESA dichSpesa ON dichSpesa.ID_DICHIARAZIONE_SPESA = :idDichiarazioneSpesa\n" +
                    "WHERE quietanza.id_pagamento = :idQuietanza \n" +
                    "AND fileEntita.id_entita = 70 \n" +
                    "AND fileEntita.id_integrazione_spesa IS NULL";

            LOG.debug(query);

            Object[] args = new Object[] {idDichiarazioneSpesa, idQuietanza};
            int[] types = new int[] {Types.INTEGER, Types.INTEGER};
            docIntegrRendDTOS.addAll(getJdbcTemplate().query(query, args, types,
                    (rs, rowNum) -> {
                        DocIntegrRendDTO integrRendDTO = new DocIntegrRendDTO();

                        integrRendDTO.setIdDocumentoIndex(rs.getLong("idDocIndex"));
                        integrRendDTO.setNomeFile(rs.getString("nomeFile"));
                        integrRendDTO.setData(rs.getDate("dataDichiarazione"));

                        return integrRendDTO;
                    }));

        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getAllegatiQuietanza: " + e);
            docIntegrRendDTOS = new ArrayList<>();
        }

        LOG.info(prf + " END");
        return docIntegrRendDTOS;
    }
    @Override
    public List<List<DocIntegrRendDTO>> getAllegatiIntegrazioneQuietanza(Long idIntegrazioneSpesaCorrente, Long idDichiarazioneSpesa, Long idQuietanza) {
        String prf = "[GestioneIntegrazioniDAOImpl::getAllegatiIntegrazioneQuietanza]";
        LOG.info(prf + " BEGIN");

        List<DocIntegrRendDTO> docIntegrRendDTOS = new ArrayList<>();
        try {
            //PER OGNI INTEGRAZIONE IN CORSO ALLEGATI ALL'INTEGRAZIONE
            String getIdIntegrazioneSpesa =
                    "SELECT DISTINCT \n" +
                    "ptis.id_integrazione_spesa as idIntegrazioneSpesa\n" +
                    "FROM PBANDI_T_INTEGRAZIONE_SPESA ptis \n" +
                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA ptis2 ON ptis2.id_integrazione_spesa = :idIntegrazioneSpesa\n" +
                    "WHERE ptis.data_invio IS NOT NULL\n" +
                    "AND ptis.id_stato_richiesta = 2\n" +
                    "AND ptis.id_dichiarazione_spesa = :idDichiarazioneSpesa\n" +
                    "AND ptis.data_richiesta <= ptis2.data_richiesta";
            LOG.debug(getIdIntegrazioneSpesa);

            //ALLEGATI ALL'integrazione quietanza
            String query =
                    "SELECT \n" +
                    "documentoIndex.id_documento_index AS idDocIndex,\n" +
                    "integrazione.id_integrazione_spesa AS idIntegrazioneSpesa,\n" +
                    "integrazione.data_invio AS dtInvioIntegrazione,\n" +
                    "documentoIndex.nome_file AS nomeFile\n" +
                    "FROM PBANDI_T_FILE_ENTITA fileEntita\n" +
                    "JOIN PBANDI_T_PAGAMENTO quietanza ON fileEntita.id_target = quietanza.id_pagamento\n" +
                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_integrazione_spesa = fileEntita.id_integrazione_spesa\n" +
                    "JOIN PBANDI_T_FILE files ON files.id_file = fileEntita.id_file\n" +
                    "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_documento_index = files.id_documento_index\n" +
                    "WHERE fileEntita.id_entita = 70 \n" +
                    "AND integrazione.id_integrazione_spesa = :idIntegrazione\n" +
                    "AND quietanza.id_pagamento = :idPagamento";
            LOG.debug(query);

            Object[] args = new Object[] {idIntegrazioneSpesaCorrente, idDichiarazioneSpesa};
            int[] types = new int[] {Types.INTEGER, Types.INTEGER};
            for(Long idIntegrazioneSpesa : getJdbcTemplate().query(getIdIntegrazioneSpesa, args, types,
                    (rs, rowNum) -> rs.getLong("idIntegrazioneSpesa"))) {
                Object[] args2 = new Object[]{idIntegrazioneSpesa, idQuietanza};
                int[] types2 = new int[] {Types.INTEGER, Types.INTEGER};
                docIntegrRendDTOS.addAll(getJdbcTemplate().query(query, args2, types2,
                        (rs, rowNum) -> {
                            DocIntegrRendDTO integrRendDTO = new DocIntegrRendDTO();

                            integrRendDTO.setIdDocumentoIndex(rs.getLong("idDocIndex"));
                            integrRendDTO.setNomeFile(rs.getString("nomeFile"));
                            integrRendDTO.setData(rs.getDate("dtInvioIntegrazione"));
                            integrRendDTO.setIdIntegrazione(rs.getLong("idIntegrazioneSpesa"));

                            return integrRendDTO;
                        }));
            }
        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getAllegatiIntegrazioneQuietanza: " + e);
            docIntegrRendDTOS = new ArrayList<>();
        }

        LOG.info(prf + " END");
        return separateByIntegrazione(docIntegrRendDTOS);
    }
    @Override
    public List<DocIntegrRendDTO> getAllegatiNuovaIntegrazioneQuietanza(Long idIntegrazioneSpesaCorrente, Long idQuietanza) {
        String prf = "[GestioneIntegrazioniDAOImpl::getAllegatiNuovaIntegrazioneQuietanza]";
        LOG.info(prf + " BEGIN");

        List<DocIntegrRendDTO> docIntegrRendDTOS = new ArrayList<>();
        try {
            //PER OGNI INTEGRAZIONE IN CORSO ALLEGATI ALL'INTEGRAZIONE
            String getIdIntegrazioneSpesa =
                    "SELECT DISTINCT \n" +
                    "ptis.id_integrazione_spesa as idIntegrazioneSpesa\n" +
                    "FROM PBANDI_T_INTEGRAZIONE_SPESA ptis \n" +
                    "WHERE ptis.data_invio IS NULL\n" +
                    "AND ptis.id_stato_richiesta = 1\n" +
                    "AND ptis.id_integrazione_spesa = :idIntegrazioneSpesa";
            LOG.debug(getIdIntegrazioneSpesa);

            //ALLEGATI ALL'integrazione quietanza
            String query =
                    "SELECT \n" +
                    "documentoIndex.id_documento_index as idDocIndex, \n" +
                    "fileEntita.id_file_entita as idFileEntita," +
                    "integrazione.id_integrazione_spesa AS idIntegrazioneSpesa,\n" +
                    "integrazione.data_invio AS dtInvioIntegrazione,\n" +
                    "documentoIndex.nome_file AS nomeFile\n" +
                    "FROM PBANDI_T_FILE_ENTITA fileEntita\n" +
                    "JOIN PBANDI_T_PAGAMENTO quietanza ON fileEntita.id_target = quietanza.id_pagamento\n" +
                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_integrazione_spesa = fileEntita.id_integrazione_spesa\n" +
                    "JOIN PBANDI_T_FILE files ON files.id_file = fileEntita.id_file\n" +
                    "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_documento_index = files.id_documento_index\n" +
                    "WHERE fileEntita.id_entita = 70 \n" +
                    "AND integrazione.id_integrazione_spesa = :idIntegrazione\n" +
                    "AND quietanza.id_pagamento = :idPagamento";
            LOG.debug(query);

            Object[] args = new Object[] {idIntegrazioneSpesaCorrente};
            int[] types = new int[] {Types.INTEGER};
            for(Long idIntegrazioneSpesa : getJdbcTemplate().query(getIdIntegrazioneSpesa, args, types,
                    (rs, rowNum) -> rs.getLong("idIntegrazioneSpesa"))) {
                Object[] args2 = new Object[]{idIntegrazioneSpesa, idQuietanza};
                int[] types2 = new int[] {Types.INTEGER, Types.INTEGER};
                docIntegrRendDTOS.addAll(getJdbcTemplate().query(query, args2, types2,
                        (rs, rowNum) -> {
                            DocIntegrRendDTO integrRendDTO = new DocIntegrRendDTO();

                            integrRendDTO.setIdDocumentoIndex(rs.getLong("idDocIndex"));
                            integrRendDTO.setIdFileEntita(rs.getLong("idFileEntita"));
                            integrRendDTO.setNomeFile(rs.getString("nomeFile"));
                            integrRendDTO.setData(rs.getDate("dtInvioIntegrazione"));
                            integrRendDTO.setIdIntegrazione(rs.getLong("idIntegrazioneSpesa"));

                            return integrRendDTO;
                        }));
            }
        } catch (Exception e) {
            LOG.info(prf + " Error while trying to getAllegatiNuovaIntegrazioneQuietanza: " + e);
            docIntegrRendDTOS = new ArrayList<>();
        }

        LOG.info(prf + " END");
        return docIntegrRendDTOS;
    }

    @Override
    public ResponseCodeMessage salvaAllegati(List<AllegatoDTO> allegati, HttpServletRequest req) {
        String prf = "[GestioneIntegrazioniDAOImpl::salvaAllegati]";
        LOG.info(prf + " BEGIN");

        ResponseCodeMessage esito = new ResponseCodeMessage();
        esito.setCode("KO");
        esito.setMessage("Errore durante il salvataggio degli allegati!");

        try {
            Long ID_FILE;
            Long ID_PROGETTO;
            for (AllegatoDTO allegato : allegati) {
                // Trovo l'id_file da pbandi_t_file associato all'allegato da salvare
                String sql =
                        "SELECT ptf.ID_FILE \n" +
                        "FROM PBANDI_T_FILE ptf\n" +
                        "WHERE ptf.ID_DOCUMENTO_INDEX = ?\n" +
                        "AND ptf.ID_FOLDER = ? \n" +
                        "AND ptf.NOME_FILE = ?";
                LOG.debug(sql);
                ID_FILE = getJdbcTemplate().queryForObject(sql,
                        new Object[]{
                                allegato.getIdDocumentoIndex(),
                                allegato.getIdFolder(),
                                allegato.getNome()
                        },
                        Long.class);
                sql =
                        "SELECT DISTINCT \n" +
                        "dichiarazioneSpesa.id_progetto\n" +
                        "FROM PBANDI_T_DICHIARAZIONE_SPESA dichiarazioneSpesa\n" +
                        "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_dichiarazione_spesa = dichiarazioneSpesa.id_dichiarazione_spesa\n" +
                        "WHERE integrazione.id_integrazione_spesa = ?";
                ID_PROGETTO = getJdbcTemplate().queryForObject(sql,
                        Long.class,
                        allegato.getIdIntegrazioneSpesa());

                sql =
                        "SELECT COUNT(1)\n" +
                        "FROM PBANDI_T_FILE_ENTITA\n" +
                        "WHERE ID_FILE = ?\n" +
                        "AND ID_ENTITA = ?\n" +
                        "AND ID_TARGET = ?\n" +
                        "AND ID_PROGETTO = ?\n";
                if(0 < getJdbcTemplate().queryForObject(sql, Long.class, ID_FILE, allegato.getIdEntita(), allegato.getIdTarget(), ID_PROGETTO)){
                    throw new DuplicateKeyException("Errore: allegato duplicato.");
                }else {
                    // Salva riferimento all'allegato
                    String query =
                            "INSERT INTO PBANDI.PBANDI_T_FILE_ENTITA\n" +
                                    "(ID_FILE_ENTITA, ID_FILE, ID_ENTITA, ID_TARGET, ID_INTEGRAZIONE_SPESA, ID_PROGETTO, DT_ASSOCIAZIONE, FLAG_ENTITA)\n" +
                                    "VALUES(SEQ_PBANDI_T_FILE_ENTITA.nextval, ?, ?, ?, ?, ?, CURRENT_DATE, ?)";
                    LOG.debug(query);
                    if(0!=getJdbcTemplate().update(query,
                            ID_FILE, allegato.getIdEntita(),
                            allegato.getIdTarget(), allegato.getIdIntegrazioneSpesa(),
                            ID_PROGETTO, allegato.getFlagEntita())){
                        esito.setCode("OK");
                        esito.setMessage("Allegati salvati con successo!");
                    }
                }
            }

        } catch (DuplicateKeyException e){
            esito.setCode("KO");
            esito.setMessage(e.getMessage());
        } catch (Exception e) {
            LOG.info(prf + " Error while trying to salvaAllegati: " + e);
        }
        LOG.info(prf + " END");

        return esito;
    }

    @Override
    public Boolean rimuoviAllegato(Long idFileEntita, HttpServletRequest req) {
        String prf = "[GestioneIntegrazioniDAOImpl::rimuoviAllegato]";
        LOG.info(prf + " BEGIN");
        int rowsUpdated;
        boolean eseguito = false;
        String query =
                "DELETE FROM PBANDI_T_FILE_ENTITA\n" +
                "WHERE ID_FILE_ENTITA = :idFileEntita";
        LOG.debug(query);

        Object[] args = new Object[]{idFileEntita};
        int[] types = new int[]{Types.INTEGER};

        rowsUpdated = getJdbcTemplate().update(query, args, types);
        if (rowsUpdated > 0) {
            eseguito = true;
        }

        LOG.info("N. record aggiornati:\n" + rowsUpdated);
        LOG.info(prf + " END");
        return eseguito;
    }


    private List<List<DocIntegrRendDTO>> separateByIntegrazione(List<DocIntegrRendDTO> docIntegrRendDTOList){
        String prf = "[GestioneIntegrazioniDAOImpl::separateByIntegrazione]";
        LOG.info(prf + " BEGIN");

        List<List<DocIntegrRendDTO>> output = new ArrayList<>();

        for(DocIntegrRendDTO docIntegrRendDTO : docIntegrRendDTOList){
            boolean newList = true;
            for(List<DocIntegrRendDTO> list : output){
                if(list.get(0).getIdIntegrazione().equals(docIntegrRendDTO.getIdIntegrazione())){
                    list.add(docIntegrRendDTO);
                    newList = false;
                }
            }
            if(newList){
                List<DocIntegrRendDTO> list = new ArrayList<>();
                list.add(docIntegrRendDTO);
                output.add(list);
            }
        }

        LOG.info("N. integrazioni:\n" + output.size());
        LOG.info(prf + " END");
        return output;
    }
    
    @Override
	public List<AllegatoDocSpesaQuietanzaVO> getAllegatiDocSpesaQuietanzeDaInviare(Long idIntegrazioneSpesa, Long idUtente, String idIride,
			String codiceRuolo) throws Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::getAllegatiDocSpesaQuietanzeDaInviare] ";
		LOG.info(prf + " BEGIN");

		if (idIntegrazioneSpesa == null) {
			throw new InvalidParameterException("idIntegrazioneSpesa non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		
		List<AllegatoDocSpesaQuietanzaVO> allegati = null;

		try {

			String query = "SELECT f.NOME_FILE, doc_sp.ID_DOCUMENTO_DI_SPESA,\r\n"
					+ "	tdoc.desc_tipo_documento_spesa || ' ' || doc_sp.numero_documento AS documento,\r\n"
					+ "	TRIM(f.denominazione_fornitore || f.nome_fornitore || ' ' || f.cognome_fornitore) AS fornitore,\r\n"
					+ "	dsp.importo_rendicontazione AS importo,\r\n"
					+ "	dsp.note_validazione AS note,\r\n"
					+ "	p.ID_PAGAMENTO,\r\n"
					+ "	mp.DESC_MODALITA_PAGAMENTO,\r\n"
					+ "	p.IMPORTO_PAGAMENTO,\r\n"
					+ "	p.DT_PAGAMENTO\r\n"
					+ "FROM PBANDI_T_INTEGRAZIONE_SPESA isp  \r\n"
					+ "JOIN PBANDI_T_FILE_ENTITA fe ON fe.ID_INTEGRAZIONE_SPESA = isp.ID_INTEGRAZIONE_SPESA\r\n"
					+ "JOIN PBANDI_V_DICH_DOC_SPESA dds ON dds.ID_DICHIARAZIONE_SPESA =isp.ID_DICHIARAZIONE_SPESA\r\n"
					+ "JOIN PBANDI_T_DOCUMENTO_DI_SPESA doc_sp ON doc_sp.ID_DOCUMENTO_DI_SPESA = dds.ID_DOCUMENTO_DI_SPESA \r\n"
					+ "JOIN PBANDI_C_ENTITA e ON e.ID_ENTITA = fe.ID_ENTITA\r\n"
					+ "JOIN PBANDI_T_FILE f ON f.ID_FILE =fe.ID_FILE\r\n"
					+ "JOIN PBANDI_R_DOC_SPESA_PROGETTO dsp ON dsp.ID_DOCUMENTO_DI_SPESA = doc_sp.ID_DOCUMENTO_DI_SPESA \r\n"
					+ "JOIN PBANDI_D_TIPO_DOCUMENTO_SPESA tdoc ON tdoc.ID_TIPO_DOCUMENTO_SPESA = doc_sp.ID_TIPO_DOCUMENTO_SPESA \r\n"
					+ "LEFT JOIN PBANDI_T_FORNITORE f ON f.ID_FORNITORE = doc_sp.ID_FORNITORE \r\n"
					+ "LEFT JOIN PBANDI_T_PAGAMENTO p ON p.ID_PAGAMENTO = fe.ID_TARGET AND e.NOME_ENTITA = ? \r\n"
					+ "LEFT JOIN PBANDI_D_MODALITA_PAGAMENTO mp ON mp.ID_MODALITA_PAGAMENTO = p.ID_MODALITA_PAGAMENTO \r\n"
					+ "WHERE isp.ID_INTEGRAZIONE_SPESA = ? AND \r\n"
					+ "	e.NOME_ENTITA <> ? \r\n"
					+ "	AND doc_sp.ID_DOCUMENTO_DI_SPESA IN (\r\n"
					+ "			SELECT doc.ID_DOCUMENTO_DI_SPESA\r\n"
					+ "			FROM PBANDI_T_DOCUMENTO_DI_SPESA doc \r\n"
					+ "			WHERE doc.ID_DOCUMENTO_DI_SPESA = fe.ID_TARGET AND e.NOME_ENTITA = ? \r\n"
					+ "			UNION\r\n"
					+ "			SELECT pds.ID_DOCUMENTO_DI_SPESA\r\n"
					+ "			FROM PBANDI_R_PAGAMENTO_DOC_SPESA pds\r\n"
					+ "			WHERE fe.ID_TARGET = pds.ID_PAGAMENTO AND e.NOME_ENTITA = ? \r\n"
					+ "		)";

			Object[] args = new Object[] { Constants.ENTITA_T_PAGAMENTO, idIntegrazioneSpesa, Constants.ENTITA_T_DICH_SPESA, 
					Constants.ENTITA_T_DOCUMENTO_DI_SPESA,Constants.ENTITA_T_PAGAMENTO };
			
			LOG.info(prf + "<nomeEntitaPagamento>: " + Constants.ENTITA_T_PAGAMENTO + ", <idIntegrazioneSpesa>: " + idIntegrazioneSpesa 
					+ ", <nomeEntitaDichSpesa>: " + Constants.ENTITA_T_DICH_SPESA+ ", <nomeEntitaDocSpesa>: " +
					Constants.ENTITA_T_DOCUMENTO_DI_SPESA+ ", <nomeEntitaPagamento>: " +Constants.ENTITA_T_PAGAMENTO);
			LOG.info(query);
			
			allegati = getJdbcTemplate().query(query, args, new AllegatoDocSpesaQuietanzaVORowMapper());

			if(allegati != null && allegati.size() > 0) {
				LOG.info(prf + "allegati trovati: "+ allegati.size());
			} else {
				LOG.info(prf + "allegati trovati: 0");
			}
			
		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la lettura degli allegati: ", e);
			throw new DaoException("Errore durante la lettura degli allegati.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return allegati;
	}

}
