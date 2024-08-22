/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.pbservwelfare.dto.FileInfo;
import it.csi.pbandi.pbservwelfare.dto.Fornitori;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.GestioneFornitoriDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;

public class GestioneFornitoriDAOImpl extends BaseDAO implements GestioneFornitoriDAO {

    private final Logger LOG;

    protected FileApiServiceImpl fileApiServiceImpl;

    public static final String ROOT_ARCHIVIO_FILE = "/pbstorage_online";


    public GestioneFornitoriDAOImpl() {
        LOG = Logger.getLogger(Constants.COMPONENT_NAME);
    }

    @Override
    public Long getIdSoggettoBeneficiario(String numeroDomanda) {
        String prf = "[GestioneFornitoriDAOImpl::getIdSoggettoBeneficiario]";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + ", numeroDomanda=" + numeroDomanda);

        Long idSoggBenef = null;

        try {
            String sql = "SELECT prsp.ID_SOGGETTO \n" +
                    "FROM PBANDI_T_DOMANDA ptd\n" +
                    "JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_DOMANDA = ptd.ID_DOMANDA \n" +
                    "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO \n" +
                    "WHERE ptd.NUMERO_DOMANDA = :numeroDomanda\n" +
                    "AND prsp.ID_TIPO_ANAGRAFICA = 1\n" +
                    "AND prsp.ID_TIPO_BENEFICIARIO <> 4";

            LOG.debug(sql);

            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

            idSoggBenef = this.queryForLong(sql, param);

        } catch (IncorrectResultSizeDataAccessException ignored) {
        } catch (DataAccessException ex) {
            throw ex;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to getIdSoggettoBeneficiario", e);
            throw new ErroreGestitoException("DaoException while trying to getIdSoggettoBeneficiario", e);
        } finally {
            LOG.info(prf + " END");
        }

        return idSoggBenef;
    }

    @Override
    public List<Long> getIdFornitore(Long idSoggBenef, String codiceFiscale) {
        String prf = "[GestioneFornitoriDAOImpl::getIdSoggettoBeneficiario]";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + ", idSoggBenef=" + idSoggBenef);
        LOG.info(prf + ", codiceFiscale=" + codiceFiscale);

        List<Long> idFornitore = null;

        try {
            String sql = "SELECT ptf.ID_FORNITORE \n" +
                    "FROM PBANDI_T_FORNITORE ptf \n" +
                    "WHERE ptf.CODICE_FISCALE_FORNITORE = :codiceFiscale\n" +
                    "AND ptf.ID_SOGGETTO_FORNITORE = :idSoggBenef";

            LOG.debug(sql);

            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("idSoggBenef", idSoggBenef, Types.INTEGER);
            param.addValue("codiceFiscale", codiceFiscale, Types.VARCHAR);

            idFornitore = this.queryForLongList(sql, param);

        } catch (IncorrectResultSizeDataAccessException ignored) {
        } catch (DataAccessException ex) {
            throw ex;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to getIdFornitore", e);
            throw new ErroreGestitoException("DaoException while trying to getIdFornitore", e);
        } finally {
            LOG.info(prf + " END");
        }

        return idFornitore;
    }

    @Override
    public void disabilitaFornitore(Long idFornitore) {
        String prf = "[GestioneFornitoriDAOImpl::disabilitaFornitore]";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + ", idFornitore=" + idFornitore);

        try {
            String sql = "UPDATE PBANDI_T_FORNITORE SET \n" +
                    "DT_FINE_VALIDITA = CURRENT_DATE,\n" +
                    "ID_UTENTE_AGG = :idUtente\n" +
                    "WHERE ID_FORNITORE = :idFornitore";

            LOG.debug(sql);

            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("idUtente", -20, Types.INTEGER);
            param.addValue("idFornitore", idFornitore, Types.INTEGER);

            if(this.update(sql, param) == 0){
                throw new Exception("Nessun fornitore trovato con id = " + idFornitore);
            }
        } catch (DataAccessException ex) {
            throw ex;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to disabilitaFornitore", e);
            throw new ErroreGestitoException("DaoException while trying to disabilitaFornitore", e);
        } finally {
            LOG.info(prf + " END");
        }
    }

    @Override
    public void salvaContratto(String numeroDomanda, Long idSoggBenef, String codiceFiscale, Long idFornitore, byte[] file, String nomeFile, Date dataInizio, Date dataFine) {
        String prf = "[GestioneFornitoriDAOImpl::salvaContratto]";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + ", idSoggBenef=" + idSoggBenef);
        LOG.info(prf + ", codiceFiscale=" + codiceFiscale);
        LOG.info(prf + ", nomeFile=" + nomeFile);

        try {
            String sql;
            MapSqlParameterSource param;

            // Cerco folder beneficiario

            sql = "SELECT ptf.ID_FOLDER\n" +
                    "FROM PBANDI_T_FOLDER ptf\n" +
                    "WHERE ptf.NOME_FOLDER = '/root'\n" +
                    "AND ptf.ID_SOGGETTO_BEN = :idSoggettoBen\n" +
                    "AND ptf.ID_PROGETTO IS NULL";
            LOG.debug(sql);

            param = new MapSqlParameterSource();
            param.addValue("idSoggettoBen", idSoggBenef, Types.INTEGER);

            String idFolderBeneficiario = this.namedParameterJdbcTemplate.query(sql, param, rs -> rs.next() ? rs.getString("ID_FOLDER") : null);

            if(idFolderBeneficiario == null) {
                // Folder beneficiario non trovato
                LOG.info("Folder beneficiario mancante ");

                // Creo folder beneficiario
                sql = "SELECT SEQ_PBANDI_T_FOLDER.nextval FROM dual";
                param = new MapSqlParameterSource();
                idFolderBeneficiario = this.queryForString(sql, param);

                LOG.info("Creo folder beneficiario con id = " + idFolderBeneficiario);

                sql = "INSERT INTO PBANDI_T_FOLDER ptf \n" +
                        "(ID_FOLDER, NOME_FOLDER, ID_SOGGETTO_BEN, ID_UTENTE_INS, DT_INSERIMENTO) \n" +
                        "VALUES (:idFolder, :nomeFolder, :idSoggBenef, :idUtente, CURRENT_DATE)";
                param = new MapSqlParameterSource();
                param.addValue("idFolder", idFolderBeneficiario, Types.INTEGER);
                param.addValue("nomeFolder", "/root", Types.VARCHAR);
                param.addValue("idSoggBenef", idSoggBenef, Types.INTEGER);
                param.addValue("idUtente", -20, Types.INTEGER);

                if(this.update(sql, param) == 0){
                    throw new Exception("Errore nella creazione del folder per il beneficiario");
                }
            } else {
                // Folder beneficiario trovato
                LOG.info("Trovato folder beneficiario con id = " + idFolderBeneficiario);
            }

            // Cerco folder progetto

            // Ricavo progetto e codice_visualizzato dal numeroDomanda
            sql = "SELECT ptp.ID_PROGETTO, ptp.CODICE_VISUALIZZATO \n" +
                    "FROM PBANDI_T_PROGETTO ptp \n" +
                    "JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA \n" +
                    "WHERE ptd.NUMERO_DOMANDA = :numeroDomanda";
            LOG.debug(sql);

            param = new MapSqlParameterSource();
            param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

            HashMap<String, String> resultSet = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
                HashMap<String, String> map = new HashMap<>();
                if(rs.next()){
                    map.put("codiceVisualizzato", rs.getString("CODICE_VISUALIZZATO"));
                    map.put("idProgetto", rs.getString("ID_PROGETTO"));
                }
                return map;
            });
            String codiceVisualizzato = resultSet.get("codiceVisualizzato");
            String idProgetto = resultSet.get("idProgetto");

            if(codiceVisualizzato == null || idProgetto == null){
                throw new Exception("Progetto non trovato per numeroDomanda = " + numeroDomanda);
            }

            // Ricavo il folder del progetto con codice_visualizzato e id_folder del beneficiario
            sql = "SELECT ptf.ID_FOLDER \n" +
                    "FROM PBANDI_T_FOLDER ptf \n" +
                    "WHERE ptf.ID_SOGGETTO_BEN = :idSoggBenef \n" +
                    "AND ptf.ID_PROGETTO = :idProgetto \n" +
                    "AND ptf.NOME_FOLDER = :nomeFolder";
            LOG.debug(sql);

            param = new MapSqlParameterSource();
            param.addValue("idSoggBenef", idSoggBenef, Types.INTEGER);
            param.addValue("idProgetto", idProgetto, Types.INTEGER);
            param.addValue("nomeFolder", "/" + codiceVisualizzato, Types.VARCHAR);

            String idFolderProgetto = this.namedParameterJdbcTemplate.query(sql, param, rs -> rs.next() ? rs.getString("ID_FOLDER") : null);

            if(idFolderProgetto == null) {
                // Folder progetto non trovato
                LOG.info("Folder progetto mancante ");

                // Creo folder progetto
                sql = "SELECT SEQ_PBANDI_T_FOLDER.nextval FROM dual";
                param = new MapSqlParameterSource();
                idFolderProgetto = this.queryForString(sql, param);

                LOG.info("Creo folder progetto con id = " + idFolderProgetto);

                sql = "INSERT INTO PBANDI_T_FOLDER ptf \n" +
                        "(ID_FOLDER, ID_PADRE, NOME_FOLDER, ID_SOGGETTO_BEN, ID_UTENTE_INS, DT_INSERIMENTO, ID_PROGETTO) \n" +
                        "VALUES (:idFolder, :idFolderPadre, :nomeFolder, :idSoggBenef, :idUtente, CURRENT_DATE, :idProgetto)";
                param = new MapSqlParameterSource();
                param.addValue("idFolder", idFolderProgetto, Types.INTEGER);
                param.addValue("idFolderPadre", idFolderBeneficiario, Types.INTEGER);
                param.addValue("nomeFolder", "/" + codiceVisualizzato, Types.VARCHAR);
                param.addValue("idSoggBenef", idSoggBenef, Types.INTEGER);
                param.addValue("idUtente", -20, Types.INTEGER);
                param.addValue("idProgetto", idProgetto, Types.INTEGER);

                if(this.update(sql, param) == 0){
                    throw new Exception("Errore nella creazione del folder per il progetto");
                }
            } else {
                // Folder progetto trovato
                LOG.info("Trovato folder progetto con id = " + idFolderProgetto);
            }

            // Cerco folder contratto

            sql = "SELECT ptf.ID_FOLDER \n" +
                    "FROM PBANDI_T_FOLDER ptf \n" +
                    "WHERE ptf.ID_PROGETTO = :idProgetto\n" +
                    "AND ptf.ID_SOGGETTO_BEN = :idSoggBenef\n" +
                    "AND ptf.NOME_FOLDER = '/Contratto_fornitore_' || :codiceFiscale";
            LOG.debug(sql);

            param = new MapSqlParameterSource();
            param.addValue("idProgetto", idProgetto, Types.INTEGER);
            param.addValue("idSoggBenef", idSoggBenef, Types.INTEGER);
            param.addValue("codiceFiscale", codiceFiscale, Types.VARCHAR);

            String idFolderContratto = this.namedParameterJdbcTemplate.query(sql, param, rs -> rs.next() ? rs.getString("ID_FOLDER") : null);

            if(idFolderContratto == null) {
                // Folder contratto non trovato
                LOG.info("Folder contratto mancante ");

                // Creo folder beneficiario
                sql = "SELECT SEQ_PBANDI_T_FOLDER.nextval FROM dual";
                param = new MapSqlParameterSource();
                idFolderContratto = this.queryForString(sql, param);

                LOG.info("Creo folder contratto con id = " + idFolderContratto);

                sql = "INSERT INTO PBANDI_T_FOLDER ptf \n" +
                        "(ID_FOLDER, ID_PADRE, NOME_FOLDER, ID_SOGGETTO_BEN, ID_UTENTE_INS, DT_INSERIMENTO, ID_PROGETTO) \n" +
                        "VALUES (:idFolder, :idFolderPadre, :nomeFolder, :idSoggBenef, :idUtente, CURRENT_DATE, :idProgetto)";
                param = new MapSqlParameterSource();
                param.addValue("idFolder", idFolderContratto, Types.INTEGER);
                param.addValue("idFolderPadre", idFolderProgetto, Types.INTEGER);
                param.addValue("nomeFolder", "/" + "Contratto_fornitore_" + codiceFiscale, Types.VARCHAR);
                param.addValue("idSoggBenef", idSoggBenef, Types.INTEGER);
                param.addValue("idUtente", -20, Types.INTEGER);
                param.addValue("idProgetto", idProgetto, Types.INTEGER);

                if(this.update(sql, param) == 0){
                    throw new Exception("Errore nella creazione del folder per il contratto");
                }
            }else {
                // Folder contratto trovato
                LOG.info("Trovato folder contratto con id = " + idFolderContratto);
            }

            // Creo record del file sulla PBANDI_T_DOCUMENTO_INDEX
            sql = "SELECT SEQ_PBANDI_T_DOCUMENTO_INDEX.nextval FROM dual";
            param = new MapSqlParameterSource();
            Long idDocumentoIndex = this.queryForLong(sql, param);

            LOG.info("Creo il record sulla PBANDI_T_DOCUMENTO_INDEX con id = " + idDocumentoIndex);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dataInizioString = "_" + sdf.format(dataInizio);
            String dataFineString = "";
            if(dataFine != null){
                dataFineString = "_" + sdf.format(dataFine);
            }
            String nomeFileSenzaEstensione = nomeFile.substring(0, nomeFile.lastIndexOf('.'));
            String estensioneFile = nomeFile.substring(nomeFile.lastIndexOf('.'));
            String nomeFileElaborato = nomeFileSenzaEstensione + dataInizioString + dataFineString + "_" + idDocumentoIndex + estensioneFile;

            sql = "INSERT INTO PBANDI_T_DOCUMENTO_INDEX ptdi\n" +
                    "(ID_DOCUMENTO_INDEX , ID_TARGET, ID_ENTITA, UUID_NODO, REPOSITORY, DT_INSERIMENTO_INDEX, ID_TIPO_DOCUMENTO_INDEX, ID_UTENTE_INS, NOME_FILE, NOME_DOCUMENTO, ID_PROGETTO, ID_STATO_DOCUMENTO, ID_SOGG_BENEFICIARIO) \n" +
                    "VALUES (:idDocumentoIndex, :idFolder, '360', 'UUID', 'AF', CURRENT_DATE, :idTipoDocIndex, :idUtente, :nomeFile, :nomeDocumento, :idProgetto, 1, :idSoggBenef)";
            param = new MapSqlParameterSource();
            param.addValue("idDocumentoIndex", idDocumentoIndex, Types.INTEGER);
            param.addValue("idFolder", idFolderContratto, Types.INTEGER);
            param.addValue("idTipoDocIndex", 23, Types.INTEGER);
            param.addValue("idUtente", -20, Types.INTEGER);
            param.addValue("nomeFile", nomeFileElaborato, Types.VARCHAR);
            param.addValue("nomeDocumento", nomeFileElaborato, Types.VARCHAR);
            param.addValue("idProgetto", idProgetto, Types.INTEGER);
            param.addValue("idSoggBenef", idSoggBenef, Types.INTEGER);

            if(this.update(sql, param) == 0){
                throw new Exception("Errore nell'inserimento del record sulla PBANDI_T_DOCUMENTO_INDEX");
            }

            // Creo record del file sulla PBANDI_T_FILE
            sql = "SELECT SEQ_PBANDI_T_FILE.nextval FROM dual";
            param = new MapSqlParameterSource();
            Long idFile = this.queryForLong(sql, param);

            LOG.info("Creo il record sulla PBANDI_T_FILE con id = " + idFile);

            sql = "INSERT INTO PBANDI_T_FILE ptf \n" +
                    "(ID_FILE , ID_FOLDER, ID_DOCUMENTO_INDEX, NOME_FILE, SIZE_FILE, ID_UTENTE_INS, DT_INSERIMENTO) \n" +
                    "VALUES (:idFile, :idFolder, :idDocumentoIndex, :nomeFile, :sizeFile, :idUtente, CURRENT_DATE)";
            param = new MapSqlParameterSource();
            param.addValue("idFile", idFile, Types.INTEGER);
            param.addValue("idFolder", idFolderContratto, Types.INTEGER);
            param.addValue("idDocumentoIndex", idDocumentoIndex, Types.INTEGER);
            param.addValue("nomeFile", nomeFileElaborato, Types.VARCHAR);
            param.addValue("sizeFile", file.length, Types.INTEGER);
            param.addValue("idUtente", -20, Types.INTEGER);

            if(this.update(sql, param) == 0){
                throw new Exception("Errore nell'inserimento del record sulla PBANDI_T_FILE");
            }

            //PBANDI_T_FILE_ENTITA
            sql = "SELECT SEQ_PBANDI_T_FILE_ENTITA.nextval FROM dual";
            param = new MapSqlParameterSource();
            Long idFileEntita = this.queryForLong(sql, param);

            LOG.info("Creo il record sulla PBANDI_T_FILE_ENTITA con id = " + idFileEntita);

            sql = "INSERT INTO PBANDI_T_FILE_ENTITA ptfe \n" +
                    "(ID_FILE_ENTITA, ID_FILE, ID_ENTITA, ID_TARGET, ID_PROGETTO) \n" +
                    "VALUES (:idFileEntita, :idFile, 11, :idFornitore, :idProgetto)";
            param = new MapSqlParameterSource();
            param.addValue("idFileEntita", idFileEntita, Types.INTEGER);
            param.addValue("idFile", idFile, Types.INTEGER);
            param.addValue("idFornitore", idFornitore, Types.INTEGER);
            param.addValue("idProgetto", idProgetto, Types.INTEGER);

            if(this.update(sql, param) == 0){
                throw new Exception("Errore nell'inserimento del record sulla PBANDI_T_FILE_ENTITA");
            }

            // Salvo il file su file system
            LOG.info("Salvo il contratto su file system.");
            if(!salvaSuFileSystem(file, nomeFileElaborato)){
                throw new Exception("Errore nel salvataggio del file sul file system");
            }
        } catch (DataAccessException ex) {
            throw ex;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to salvaContratto", e);
            throw new ErroreGestitoException("DaoException while trying to salvaContratto", e);
        } finally {
            LOG.info(prf + " END");
        }
    }

    @Override
    public Long salvaFornitore(Long idSoggBenef, String nome, String cognome, String denominazione, String codiceFiscale, String partitaIva, String codiceFormaGiuridica, Date dataInizio, Date dataFine) {
        String prf = "[GestioneFornitoriDAOImpl::salvaFornitore]";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + ", idSoggBenef=" + idSoggBenef);
        LOG.info(prf + ", nome=" + nome);
        LOG.info(prf + ", cognome=" + cognome);
        LOG.info(prf + ", denominazione=" + denominazione);
        LOG.info(prf + ", codiceFiscale=" + codiceFiscale);
        LOG.info(prf + ", partitaIva=" + partitaIva);
        LOG.info(prf + ", codiceFormaGiuridica=" + codiceFormaGiuridica);
        LOG.info(prf + ", dataInizio=" + dataInizio);
        LOG.info(prf + ", dataFine=" + dataFine);

        Long idFornitore;

        try {
            // Genero id nuovo fornitore
            String sql = "SELECT SEQ_PBANDI_T_FORNITORE.nextval FROM dual";
            MapSqlParameterSource param = new MapSqlParameterSource();
            idFornitore = this.queryForLong(sql, param);

            // Cerco l'id forma giuridica
            Long idFormaGiuridica = null;
            if(codiceFormaGiuridica != null){
                sql = "SELECT pdfg.ID_FORMA_GIURIDICA FROM PBANDI_D_FORMA_GIURIDICA pdfg WHERE pdfg.COD_FORMA_GIURIDICA = :codFormaGiuridica";
                param = new MapSqlParameterSource();
                param.addValue("codFormaGiuridica", codiceFormaGiuridica, Types.VARCHAR);
                idFormaGiuridica = this.queryForLong(sql, param);
            }

            // Inserisco il fornitore
            sql = "INSERT INTO PBANDI_T_FORNITORE ptf\n" +
                    "(ID_FORNITORE, CODICE_FISCALE_FORNITORE, PARTITA_IVA_FORNITORE, DENOMINAZIONE_FORNITORE, \n" +
                    "COGNOME_FORNITORE, NOME_FORNITORE, ID_SOGGETTO_FORNITORE, ID_UTENTE_INS, ID_TIPO_SOGGETTO,\n" +
                    "DT_INIZIO_VALIDITA, ID_FORMA_GIURIDICA, DT_INIZIO_CONTRATTO, DT_FINE_CONTRATTO) \n" +
                    "VALUES (:idFornitore, :codiceFiscale, :partitaIva, :denominazione, :cognome, \n" +
                    ":nome, :idSoggBenef, :idUtente, :idTipoSoggetto, CURRENT_DATE, :idFormaGiuridica, :dtInizio, :dtFine)";

            LOG.debug(sql);

            param = new MapSqlParameterSource();
            param.addValue("idFornitore", idFornitore, Types.INTEGER);
            param.addValue("codiceFiscale", codiceFiscale, Types.VARCHAR);
            param.addValue("partitaIva", partitaIva, Types.VARCHAR);
            param.addValue("denominazione", denominazione, Types.VARCHAR);
            param.addValue("cognome", cognome, Types.VARCHAR);
            param.addValue("nome", nome, Types.VARCHAR);
            param.addValue("idSoggBenef", idSoggBenef, Types.INTEGER);
            param.addValue("idUtente", -20, Types.INTEGER);
            param.addValue("idTipoSoggetto", (CommonUtil.isEmpty(denominazione) ? 1 : 2), Types.INTEGER);
            param.addValue("idFormaGiuridica", idFormaGiuridica, Types.INTEGER);
            param.addValue("dtInizio", dataInizio, Types.DATE);
            param.addValue("dtFine", dataFine, Types.DATE);

            this.update(sql, param);
        } catch (DataAccessException ex) {
            throw ex;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to salvaFornitore", e);
            throw new ErroreGestitoException("DaoException while trying to salvaFornitore", e);
        } finally {
            LOG.info(prf + " END");
        }

        return idFornitore;
    }

    @Override
    public List<Fornitori> getFornitori(String numeroDomanda) {
        String prf = "[GestioneFornitoriDAOImpl::getFornitori]";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + ", numeroDomanda=" + numeroDomanda);

        List<Fornitori> fornitori;

        try {
            String sql = "SELECT \n" +
                    "ptf.ID_FORNITORE,\n" +
                    "ptf.CODICE_FISCALE_FORNITORE,\n" +
                    "ptf.NOME_FORNITORE,\n" +
                    "ptf.COGNOME_FORNITORE,\n" +
                    "ptf.DENOMINAZIONE_FORNITORE,\n" +
                    "pdfg.COD_FORMA_GIURIDICA,\n" +
                    "ptf.DT_INIZIO_CONTRATTO,\n" +
                    "ptf.DT_FINE_CONTRATTO\n" +
                    "FROM PBANDI_T_FORNITORE ptf \n" +
                    "LEFT JOIN PBANDI_D_FORMA_GIURIDICA pdfg ON pdfg.ID_FORMA_GIURIDICA = ptf.ID_FORMA_GIURIDICA\n" +
                    "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_SOGGETTO = ptf.ID_SOGGETTO_FORNITORE \n" +
                    "JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO \n" +
                    "JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA \n" +
                    "WHERE ptd.NUMERO_DOMANDA = :numeroDomanda\n" +
                    "AND ptf.DT_FINE_VALIDITA IS NULL\n" +
                    "AND prsp.ID_TIPO_ANAGRAFICA = 1\n" +
                    "AND prsp.ID_TIPO_BENEFICIARIO <> 4\n" +
                    "AND prsp.DT_FINE_VALIDITA IS NULL";

            LOG.debug(sql);

            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

            fornitori = this.namedParameterJdbcTemplate.query(sql, param, ((rs, rowNum) -> {
                Fornitori dto = new Fornitori();

                dto.setIdFornitore(rs.getInt("ID_FORNITORE"));
                dto.setCodiceFiscaleFornitore(rs.getString("CODICE_FISCALE_FORNITORE"));
                dto.setDenominazione(rs.getString("DENOMINAZIONE_FORNITORE"));
                dto.setNome(rs.getString("NOME_FORNITORE"));
                dto.setCognome(rs.getString("COGNOME_FORNITORE"));
                dto.setCodiceFormaGiuridica(rs.getString("COD_FORMA_GIURIDICA"));
                dto.setDataInizio(rs.getDate("DT_INIZIO_CONTRATTO"));
                dto.setDataFine(rs.getDate("DT_FINE_CONTRATTO"));

                return dto;
            }));

            sql = "SELECT \n" +
                    "ptdi.ID_DOCUMENTO_INDEX,\n" +
                    "ptdi.NOME_FILE\n" +
                    "FROM PBANDI_T_FILE_ENTITA ptfe\n" +
                    "JOIN PBANDI_C_ENTITA pce ON pce.NOME_ENTITA = 'PBANDI_T_FORNITORE'\n" +
                    "JOIN PBANDI_T_FILE ptf ON ptf.ID_FILE = ptfe.ID_FILE \n" +
                    "JOIN PBANDI_T_DOCUMENTO_INDEX ptdi ON ptdi.ID_DOCUMENTO_INDEX = ptf.ID_DOCUMENTO_INDEX \n" +
                    "JOIN PBANDI_T_DOMANDA ptd ON ptd.NUMERO_DOMANDA = :numeroDomanda \n" +
                    "JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_DOMANDA = ptd.ID_DOMANDA \n" +
                    "WHERE ptfe.ID_TARGET = :idFornitore\n" +
                    "AND ptfe.ID_ENTITA = pce.ID_ENTITA \n" +
                    "AND ptfe.ID_PROGETTO = ptp.ID_PROGETTO ";

            for(Fornitori fornitore : fornitori) {
                param = new MapSqlParameterSource();
                param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);
                param.addValue("idFornitore", fornitore.getIdFornitore(), Types.INTEGER);

                fornitore.setFile(this.namedParameterJdbcTemplate.query(sql, param, ((rs, rowNum) -> {
                    FileInfo file = new FileInfo();

                    file.setIdDocumentoIndex(rs.getInt("ID_DOCUMENTO_INDEX"));
                    file.setNomeFile(rs.getString("NOME_FILE"));

                    return file;
                })));
            }

        } catch (DataAccessException ex) {
            throw ex;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to getFornitori", e);
            throw new ErroreGestitoException("DaoException while trying to getFornitori", e);
        } finally {
            LOG.info(prf + " END");
        }

        return fornitori;
    }

	private boolean salvaSuFileSystem(byte[] file, String fileName) {
		String prf = "[GestioneFornitoriDAOImpl::salvaFileSuFileSystem] ";
		LOG.info(prf + " BEGIN");
		boolean esito;
		try {
            fileApiServiceImpl = new FileApiServiceImpl(ROOT_ARCHIVIO_FILE);

			InputStream is = new ByteArrayInputStream(file);
			esito = fileApiServiceImpl.uploadFile(is, fileName, "AF");
			if (!esito) {
				// Se la cartella non esiste, la creo e riprovo.
				esito = fileApiServiceImpl.dirExists("AF", true);
				if (esito) {
					esito = fileApiServiceImpl.uploadFile(is, fileName, "AF");
					LOG.info(prf + " file [" + fileName + "] salvato su storage");
				}
			}
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			esito = false;
		}
		return esito;
	}
}
