package it.csi.pbandi.pbweb.integration.dao.impl;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.dto.FornitoreComboDTO;
import it.csi.pbandi.pbweb.dto.FornitoreDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.TipologiaVoceDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.exception.DaoException;
import it.csi.pbandi.pbweb.integration.dao.DecodificheCulturaDAO;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.FornitoreQualificaRowMapper;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.PbandiDTipoSoggettoRowMapper;
import it.csi.pbandi.pbweb.integration.vo.FornitoreQualificaVO;
import it.csi.pbandi.pbweb.integration.vo.PbandiDTipoSoggettoVO;
import it.csi.pbandi.pbweb.integration.vo.PbandiRDocSpesaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.gestionedocumentidispesa.GestioneDocumentiDiSpesaBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.*;

@Service
public class DecodificheCulturaDAOImpl extends JdbcDaoSupport implements DecodificheCulturaDAO {

  @Autowired
  protected BeanUtil beanUtil;
  @Autowired
  protected GestioneDocumentiDiSpesaBusinessImpl gestioneDocumentiDiSpesaBusinessImpl;
  @Autowired
  protected GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioBusinessImpl;
  @Autowired
  protected FileSqlUtil fileSqlUtil;
  private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

  @Autowired
  public DecodificheCulturaDAOImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }

  @Override
  // ex DecodificheManager.findDecodifiche()
  public List<DecodificaDTO> ottieniTipologieFornitore() {
    String prf = "[DecodificheDAOImpl::ottieniTipologieFornitore] ";
    LOG.info(prf + " BEGIN");
    List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
    try {

      StringBuilder sql = new StringBuilder();
      sql.append("SELECT ID_TIPO_SOGGETTO, DESC_TIPO_SOGGETTO, DESC_BREVE_TIPO_SOGGETTO, DT_INIZIO_VALIDITA, DT_FINE_VALIDITA ");
      sql.append("FROM PBANDI_D_TIPO_SOGGETTO ");
      sql.append("WHERE DT_FINE_VALIDITA IS NULL ");
      sql.append("ORDER BY DESC_TIPO_SOGGETTO");

      LOG.debug("\n" + sql);

      List<PbandiDTipoSoggettoVO> lista = getJdbcTemplate().query(sql.toString(), new PbandiDTipoSoggettoRowMapper());

      if (lista != null) {
        for (PbandiDTipoSoggettoVO vo : lista) {
          DecodificaDTO dto = new DecodificaDTO();
          if (vo.getIdTipoSoggetto() != null)
            dto.setId(vo.getIdTipoSoggetto().longValue());
          dto.setDescrizione(vo.getDescTipoSoggetto());
          dto.setDescrizioneBreve(vo.getDescBreveTipoSoggetto());
          result.add(dto);
        }
      }

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca in PbandiDTipoSoggetto: ", e);
      //throw new DaoException(" ERRORE nella ricerca in PbandiDTipoSoggetto: ", e);
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  // Se il tipo di documento di spesa è cedolino, cedolinoCocopro, cedolinoCostiStandard o notaSpese,
  // si scarta persona giuridica.
  @Override
  public List<DecodificaDTO> ottieniTipologieFornitorePerIdTipoDocSpesa(int idTipoDocumentoDiSpesa) {
    String prf = "[DecodificheDAOImpl::ottieniTipologieFornitorePerIdDocSpesa] ";
    LOG.info(prf + " BEGIN");
    List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
    try {

      result = this.ottieniTipologieFornitore();

      if (idTipoDocumentoDiSpesa == Constants.ID_TIPO_DOC_SPESA_CEDOLINO ||
          idTipoDocumentoDiSpesa == Constants.ID_TIPO_DOC_SPESA_CEDOLINO_A_PROGETTO ||
          idTipoDocumentoDiSpesa == Constants.ID_TIPO_DOC_SPESA_CEDOLINO_COSTI_STANDARD ||
          idTipoDocumentoDiSpesa == Constants.ID_TIPO_DOC_SPESA_NOTA_SPESE) {
        Iterator<DecodificaDTO> iter = result.iterator();
        while (iter.hasNext()) {
          DecodificaDTO d = iter.next();
          if (d.getDescrizioneBreve().equals(
              Constants.TIPOLOGIA_PERSONA_GIURIDICA)) {
            iter.remove();
          }
        }

      }

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca in PbandiDTipoSoggetto: ", e);
      //throw new DaoException(" ERRORE nella ricerca in PbandiDTipoSoggetto: ", e);
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  @Override
  // ex gestioneDatiDiDominioSrv.findFormeGiuridiche()
  public List<DecodificaDTO> tipologieFormaGiuridica(String flagPrivato) throws Exception {
    String prf = "[DecodificheDAOImpl::tipologieFormaGiuridica] ";
    LOG.info(prf + " BEGIN");
    List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
    try {

      String sql = "select ID_FORMA_GIURIDICA as ID, DESC_FORMA_GIURIDICA as DESCRIZIONE from PBANDI_D_FORMA_GIURIDICA ";
      if (!StringUtil.isEmpty(flagPrivato))
        sql += "where FLAG_PRIVATO = '" + flagPrivato + "' ";
      sql += "ORDER BY DESC_FORMA_GIURIDICA ASC";
      LOG.info("\n" + sql);

      result = getJdbcTemplate().query(sql, new BeanRowMapper(DecodificaDTO.class));
      LOG.info(prf + "Record trovati = " + result.size());

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca in PBANDI_D_FORMA_GIURIDICA: ", e);
      throw new Exception("Errore durante la ricerca delle tipologie di forma giuridica.");
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  @Override
  public List<DecodificaDTO> nazioni() throws Exception {
    String prf = "[DecodificheDAOImpl::nazioni] ";
    LOG.info(prf + " BEGIN");
    List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
    try {

      String sql = "select ID_NAZIONE as ID, DESC_NAZIONE as DESCRIZIONE from PBANDI_D_NAZIONE ";
      sql += "where (trunc(sysdate) >= nvl(trunc(DT_INIZIO_VALIDITA), trunc(sysdate) -1) ";
      sql += " and trunc(sysdate) < nvl(trunc(DT_FINE_VALIDITA), trunc(sysdate) +1)) ORDER BY DESC_NAZIONE ASC";
      LOG.info("\n" + sql);

      result = getJdbcTemplate().query(sql, new BeanRowMapper(DecodificaDTO.class));
      LOG.info(prf + "Record trovati = " + result.size());

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca in PBANDI_D_FORMA_GIURIDICA: ", e);
      throw new Exception("Errore durante la ricerca delle nazioni.");
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  @Override
  // Restituisce l'elenco degli stati di un documento di spesa, aggiungendo DA COMPLETARE/DICHIARABILE e togliendo RESPINTO.
  // Usato per popolare la combo Stato nella pagina di ricerca dei documenti di spesa.
  public List<DecodificaDTO> statiDocumentoDiSpesa() throws Exception {
    String prf = "[DecodificheDAOImpl::statiDocumentoDiSpesa] ";
    LOG.info(prf + " BEGIN");
    List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
    try {

      String sql = "select ID_STATO_DOCUMENTO_SPESA as ID, DESC_STATO_DOCUMENTO_SPESA as DESCRIZIONE, DESC_BREVE_STATO_DOC_SPESA as DESCRIZIONE_BREVE from PBANDI_D_STATO_DOCUMENTO_SPESA ";
      sql += "where DESC_BREVE_STATO_DOC_SPESA <> 'R' and (trunc(sysdate) >= nvl(trunc(DT_INIZIO_VALIDITA), trunc(sysdate) -1) ";
      sql += " and trunc(sysdate) < nvl(trunc(DT_FINE_VALIDITA), trunc(sysdate) +1)) ORDER BY DESC_STATO_DOCUMENTO_SPESA ASC";
      LOG.info("\n" + sql);

      result = getJdbcTemplate().query(sql, new BeanRowMapper(DecodificaDTO.class));
      LOG.info(prf + "Record trovati = " + result.size());

      // Aggiungo in cima una riga con DA COMPLETARE\DICHIARABILE come fatto in CPBESGestioneDocumentiSpesa.inizializzaContentPanel()
      DecodificaDTO newDto = new DecodificaDTO();
      newDto.setId(new Long(0));
      newDto.setDescrizione("DA COMPLETARE/DICHIARABILE");
      result.add(0, newDto);

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca degli stati del documento di spesa: ", e);
      throw new Exception("Errore durante la ricerca degli stati del documento di spesa.");
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  // Restituisce l'elenco degli stati di un documento di spesa, aggiungendo TUTTI e togliendo DA COMPLETARE e DICHIARABILE.
  // Usato nella finestra di ricerca in Validazione della Rendicontazione.
  // Nota: Al momento non è esposto come servizio.
  public List<DecodificaDTO> statiDocumentoDiSpesaValidazioneRendicontazione() throws Exception {
    String prf = "[DecodificheDAOImpl::statiDocumentoDiSpesaValidazioneRendicontazione] ";
    LOG.info(prf + " BEGIN");
    List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
    try {

      String sql = "select ID_STATO_DOCUMENTO_SPESA as ID, DESC_STATO_DOCUMENTO_SPESA as DESCRIZIONE, DESC_BREVE_STATO_DOC_SPESA as DESCRIZIONE_BREVE from PBANDI_D_STATO_DOCUMENTO_SPESA ";
      sql += "where DESC_BREVE_STATO_DOC_SPESA <> 'DC' and DESC_BREVE_STATO_DOC_SPESA <> 'I' and (trunc(sysdate) >= nvl(trunc(DT_INIZIO_VALIDITA), trunc(sysdate) -1) ";
      sql += " and trunc(sysdate) < nvl(trunc(DT_FINE_VALIDITA), trunc(sysdate) +1)) ORDER BY DESC_STATO_DOCUMENTO_SPESA ASC";
      LOG.info("\n" + sql);

      result = getJdbcTemplate().query(sql, new BeanRowMapper(DecodificaDTO.class));
      LOG.info(prf + "Record trovati = " + result.size());

      // Aggiungo in cima una riga con TUTTI.
      DecodificaDTO newDto = new DecodificaDTO();
      newDto.setId(new Long(0));
      newDto.setDescrizione("TUTTI");
      result.add(0, newDto);

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca degli stati del documento di spesa: ", e);
      throw new Exception("Errore durante la ricerca degli stati del documento di spesa.");
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  @Override
  // GestioneDatiDiDominioBusinessImpl.findAllQualifiche()
  public List<DecodificaDTO> qualifiche(long idUtente) throws Exception {
    String prf = "[DecodificheDAOImpl::qualifiche] ";
    LOG.info(prf + " BEGIN");

    if (idUtente == 0) {
      throw new InvalidParameterException("idUtente non valorizzato.");
    }

    List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
    try {

      String sql = "select ID_QUALIFICA as ID, DESC_QUALIFICA as DESCRIZIONE from PBANDI_D_QUALIFICA ORDER BY DESC_QUALIFICA";
      LOG.info("\n" + sql);

      result = getJdbcTemplate().query(sql, new BeanRowMapper(DecodificaDTO.class));
      LOG.info(prf + "Record trovati = " + result.size());

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca in PBANDI_D_QUALIFICA: ", e);
      throw new Exception("Errore durante la ricerca delle qualifiche.");
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  @Override
  // Popola la combo dei fornitori nella finestra di ricerca dei Documenti Di Spesa (Rendicontazione).
  public List<DecodificaDTO> fornitoriComboRicerca(long idProgetto, HttpServletRequest req) throws Exception {
    String prf = "[DecodificheDAOImpl::fornitoriComboRicerca] ";
    LOG.info(prf + " BEGIN");
    LOG.info(prf + " input: idProgetto = " + idProgetto);

    if (idProgetto == 0) {
      throw new InvalidParameterException("idProgetto non valorizzato.");
    }

    List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
    try {

      UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

      it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.CodiceDescrizioneDTO[] elencoPbandisrv = null;
      elencoPbandisrv = gestioneDocumentiDiSpesaBusinessImpl.findFornitoriRendicontazione(userInfo.getIdUtente(), userInfo.getIdIride(), idProgetto);

      for (it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.CodiceDescrizioneDTO dtoPbandisrv : elencoPbandisrv) {
        DecodificaDTO dto = new DecodificaDTO();
        dto.setId(new Long(dtoPbandisrv.getCodice()));
        dto.setDescrizione(dtoPbandisrv.getDescrizione());
        result.add(dto);
      }

    } catch (Exception e) {
      LOG.error(prf + " ERRORE: ", e);
      throw new DaoException(" ERRORE nella ricerca dei fornitori: ", e);
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  @Override
  // ex GestioneFornitoriBusinessImpl.findFornitoriSemplificazione().
  // idTipoFornitore = PBANDI_D_TIPO_SOGGETTO.ID_TIPO_SOGGETTO (persona fisica \ persona giuridica)
  // fornitoriValidi: true = solo gli attivi, false = solo i disattivi, altrimenti tutti i fornitori.
  public List<FornitoreDTO> fornitori(long idSoggettoFornitore, long idTipoFornitore, String fornitoriValidi) {
    String prf = "[DecodificheDAOImpl::fornitori] ";
    LOG.info(prf + " BEGIN");
    LOG.info(prf + " input: idSoggettoFornitore = " + idSoggettoFornitore + "; idTipoFornitore = " + idTipoFornitore + "; fornitoriValidi = " + fornitoriValidi);
    List<FornitoreDTO> result = new ArrayList<FornitoreDTO>();
    try {
      String sql = fileSqlUtil.getQuery("FornitoreQualificaVO.sql");

      if (!StringUtils.isBlank(fornitoriValidi)) {
        if ("true".equalsIgnoreCase(fornitoriValidi)) {
          sql += " AND f.dt_fine_validita IS NULL";
        } else if ("false".equalsIgnoreCase(fornitoriValidi)) {
          sql += " AND f.dt_fine_validita IS NOT NULL";
        }
      }

      sql += " AND f.id_soggetto_fornitore = ? AND f.id_tipo_soggetto = ?";
      sql += " ORDER BY COGNOME_FORNITORE , NOME_FORNITORE , DENOMINAZIONE_FORNITORE ASC";

      LOG.info("\n" + sql);
      LOG.info("\n con parametri: idSoggettoFornitore = " + idSoggettoFornitore + "; idTipoFornitore = " + idTipoFornitore);

      List<FornitoreQualificaVO> lista = getJdbcTemplate().query(sql, new Object[]{idSoggettoFornitore, idTipoFornitore}, new FornitoreQualificaRowMapper());

      Map<String, String> map = new HashMap<String, String>();
      map.put("codiceFiscaleFornitore", "codiceFiscaleFornitore");
      map.put("cognomeFornitore", "cognomeFornitore");
      map.put("denominazioneFornitore", "denominazioneFornitore");
      map.put("descTipoSoggetto", "descTipoSoggetto");
      map.put("idFornitore", "idFornitore");
      map.put("idSoggettoFornitore", "idSoggettoFornitore");
      map.put("idTipoSoggetto", "idTipoSoggetto");
      map.put("nomeFornitore", "nomeFornitore");
      map.put("partitaIvaFornitore", "partitaIvaFornitore");
      map.put("dtFineValiditaFornitore", "dtFineValidita");
      map.put("descBreveTipoSoggetto", "descBreveTipoSoggetto");
      map.put("flagHasQualifica", "flagHasQualifica");
      map.put("idFormaGiuridica", "idFormaGiuridica");

      return beanUtil.transformList(lista, FornitoreDTO.class, map);

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca in PbandiDTipoSoggetto: ", e);
      //throw new DaoException(" ERRORE nella ricerca in PbandiDTipoSoggetto: ", e);
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  @Override
  // Popola la combo dei fornitori Documenti Di Spesa -> Nuovo Documento.
  // idTipoFornitore = PBANDI_D_TIPO_SOGGETTO.ID_TIPO_SOGGETTO (persona fisica \ persona giuridica)
  // idFornitore = id del fornitore precedentemente selezionato in caso di modifica.
  // Ex pbandiweb GestioneDocumentiDiSpesaBusinessImpl.findFornitoriByTipologiaFornitore()
  public List<FornitoreComboDTO> fornitoriCombo(long idSoggettoFornitore, long idTipoFornitore, long idFornitore) {
    String prf = "[DecodificheDAOImpl::fornitoriCombo] ";
    LOG.info(prf + " BEGIN");
    LOG.info(prf + " input: idSoggettoFornitore = " + idSoggettoFornitore + "; idTipoFornitore = " + idTipoFornitore + "; idFornitore = " + idFornitore);
    List<FornitoreComboDTO> result = new ArrayList<FornitoreComboDTO>();
    try {

      // Elenco dei fornitori.
      String fornitoriValidi = null;
      List<FornitoreDTO> fornitori = this.fornitori(idSoggettoFornitore, idTipoFornitore, fornitoriValidi);

      // Codice preso lato web e javascript per
      // - gestire la descrizione da visualizzare nella combo a video
      // - popolare un flag che dica se il fornitore è formalmente valido e quindi selezionabile.

      for (FornitoreDTO fornitore : fornitori) {

        if (fornitore.getCodiceFiscaleFornitore() != null && fornitore.getCodiceFiscaleFornitore().startsWith("PBAN")) {
          fornitore.setCodiceFiscaleFornitore("n.d. ");
        }

        if (fornitore.getDtFineValidita() == null || idFornitore == fornitore.getIdFornitore().longValue()) {
          FornitoreComboDTO fornitoreComboDTO = new FornitoreComboDTO();
          fornitoreComboDTO.setIdFornitore(fornitore.getIdFornitore());

          // 08/06/2017: su richiesta di Luigi il cf viene scritto così
          // ALLMAG S.R.L. - <span class="cf">05115410010</span>
          // Jira PBANDI-2760: aggiunto if forma giuridica.
          if (idTipoFornitore == Constants.ID_TIPOLOGIA_PERSONA_FISICA) {

            if (fornitore.getDtFineValidita() != null) {
              fornitoreComboDTO.setDescrizione(fornitore
                  .getCognomeFornitore()
                  + " "
                  + fornitore.getNomeFornitore()
                  //+ " - <span class='cf'>" + fornitore.getCodiceFiscaleFornitore() + "</span> (STORICIZZATO)");
                  + " - " + fornitore.getCodiceFiscaleFornitore() + " (STORICIZZATO)");

            } else {
              fornitoreComboDTO.setDescrizione(fornitore
                  .getCognomeFornitore()
                  + " "
                  + fornitore.getNomeFornitore()
                  //+ " - <span class='cf'>" + fornitore.getCodiceFiscaleFornitore() + "</span>");
                  + " - " + fornitore.getCodiceFiscaleFornitore());
            }

          } else {
            if (fornitore.getDtFineValidita() != null) {
              fornitoreComboDTO.setDescrizione(fornitore
                  .getDenominazioneFornitore()
                  //+ " - <span class='cf'>" + fornitore.getCodiceFiscaleFornitore() + "</span> (STORICIZZATO)");
                  + " - " + fornitore.getCodiceFiscaleFornitore());
            } else {
              fornitoreComboDTO.setDescrizione(fornitore
                  .getDenominazioneFornitore()
                  //+ " - <span class='cf'>" + fornitore.getCodiceFiscaleFornitore() + "</span>");
                  + " - " + fornitore.getCodiceFiscaleFornitore());
            }

            // Lo commento poichè da quel che capisco la forma giuridica veniva inserita da java e poi nascosta da javascript.
            // Jira PBANDI-2766: aggiunto id forma giuridica tramite un altro span in coda al precedente.
            //String id = (fornitore.getIdFormaGiuridica() == null) ? "" : fornitore.getIdFormaGiuridica().toString();
            //String s = "<span class='idFormaGiuridica' style='display: none'>"+id+"</span>";
            //fornitoreComboDTO.setDescrizione(fornitoreComboDTO.getDescrizione()+s);

          }

          /* Verifico se i fornitori sono formalmente corretti: cf valido e, per persone giuridiche, idFormaGiuridica valorizzato.*/
          String cod = fornitore.getCodiceFiscaleFornitore();
          boolean cfValido = false;
          if ("n.d.".equalsIgnoreCase(cod.trim()) || "00000000000".equalsIgnoreCase(cod)) {
            cfValido = true;
          } else {
            if (cod == "") {
              cfValido = false;
              // l'espressione regolare individua i cf autogenerati che iniziano con "PBAN_".
              // non implemento il test, poichè questo caso viene già intercettato dal test precedente su "n.d."
              //} else if (/^([A-Z]){4}_([0-9]){11}\*$/.test(cod)) {
              //	cfValido = true;
            } else if (cod == "00000000000") {
              cfValido = false;
            } else if (cod.length() == 16) {
              cfValido = ValidatorCodiceFiscale.isCodiceFiscalePersonaFisicaValido(cod);
            } else if (cod.length() == 11 && cod != "00000000000") {
              cfValido = ValidatorPartitaIva.isPartitaIvaValid(cod);
            } else {
              cfValido = false;
            }
          }

          boolean formaGiuridicaValida = true;
          if (fornitore.getIdTipoSoggetto().intValue() == Constants.ID_TIPOLOGIA_PERSONA_FISICA) {
            // persona fisica: il test su idFormaGiuridica non è applicabile.
            formaGiuridicaValida = true;
          } else {
            // persona giuridica: idFormaGiuridica deve essere valorizzato.
            formaGiuridicaValida = (fornitore.getIdFormaGiuridica() != null);
          }

          fornitoreComboDTO.setCfValido(cfValido);
          fornitoreComboDTO.setFormaGiuridicaValida(formaGiuridicaValida);

          result.add(fornitoreComboDTO);

        }
      }

    } catch (Exception e) {
      LOG.error(prf + " ERRORE: ", e);
      //throw new DaoException(" ERRORE nella ricerca in PbandiDTipoSoggetto: ", e);
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  public String descrizioneDaId(String tabella, String colonnaId, String colonnaDescrizione, Long valoreId)
      throws DaoException {
    String prf = "[DecodificheDAOImpl::descrizioneDaId()] ";
    LOG.info(prf + "BEGIN");
    String descrizione = null;
    try {
      StringBuilder sql = new StringBuilder();
      sql.append("SELECT " + colonnaDescrizione + " FROM " + tabella + " WHERE " + colonnaId + " = " + valoreId.toString());
      LOG.debug("\n" + sql);
      descrizione = getJdbcTemplate().queryForObject(sql.toString(), String.class);
    } catch (Exception e) {
      LOG.error(prf + "ERRORE nella ricerca di una descriziozne da un id: ", e);
      throw new DaoException("ERRORE nella ricerca di una descriziozne da un id: ", e);
    } finally {
      LOG.info(prf + "END");
    }

    return descrizione;
  }

  public BigDecimal idDaDescrizione(String tabella, String colonnaId, String colonnaDescrizione, String valoreDescrizione)
      throws DaoException {
    String prf = "[DecodificheDAOImpl::idDaDescrizione()] ";
    LOG.info(prf + "BEGIN");
    BigDecimal id = null;
    try {
      StringBuilder sql = new StringBuilder();
      sql.append("SELECT " + colonnaId + " FROM " + tabella + " WHERE " + colonnaDescrizione + " = '" + valoreDescrizione + "'");
      LOG.debug("\n" + sql);
      id = getJdbcTemplate().queryForObject(sql.toString(), BigDecimal.class);
    } catch (Exception e) {
      LOG.error(prf + "ERRORE nella ricerca di un id da una descriziozne: ", e);
      throw new DaoException("ERRORE nella ricerca di un id da una descriziozne: ", e);
    } finally {
      LOG.info(prf + "END");
    }

    return id;
  }

  // Restituisce il campo PBANDI_C_COSTANTI.VALORE in base al campo ATTRIBUTO.
  // Se non la trova e obbligatoria = true, innalza una eccezione.
  public String costante(String attributo, boolean obbligatoria) throws DaoException {
    String prf = "[DecodificheDAOImpl::costante()] ";
    LOG.info(prf + "BEGIN");
    String valore = null;
    try {

      String sql = "SELECT VALORE FROM PBANDI_C_COSTANTI WHERE ATTRIBUTO = '" + attributo + "'";
      LOG.debug("\n" + sql);

      valore = getJdbcTemplate().queryForObject(sql, String.class);

      if (obbligatoria && StringUtil.isEmpty(valore)) {
        throw new DaoException("Costante " + attributo + " non trovata.");
      }

    } catch (Exception e) {
      LOG.error(prf + "ERRORE nella ricerca di una COSTANTE: ", e);
      throw new DaoException("ERRORE nella ricerca della costante " + attributo + ".", e);
    } finally {
      LOG.info(prf + "END");
    }

    return valore;
  }


  @Override
  public List<DecodificaDTO> attivitaCombo(long idUtente, HttpServletRequest request) throws UtenteException, GestioneDatiDiDominioException, UnrecoverableException {
    String prf = "[DecodificheDAOImpl::attivitaCombo] ";
    LOG.info(prf + "BEGIN");

    String idIride = RequestUtil.idIrideInSessione(request);

/**
 lato pbandiweb
 GestioneDatiDiDominioBusinessImpl.findOggettoAttivita()

 lato pbandisrv
 GestioneDatiDiDominioBusinessImpl.findDecodifiche()
 */

    // Logica presa da Action di pbandiweb -> CPBESDocumentoDiSpesa::inizializzaContentPanel

    String chiave = GestioneDatiDiDominioSrv.TIPO_OGGETTO_ATTIVITA;
    LOG.debug(prf + "chiave=" + chiave);

    ArrayList<DecodificaDTO> listCD = new ArrayList<DecodificaDTO>();

    Decodifica[] arrDecodifiche = gestioneDatiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride, chiave);
    if (arrDecodifiche != null) {
      for (Decodifica decodifica : arrDecodifiche) {
        DecodificaDTO cd = new DecodificaDTO();
        cd.setId(decodifica.getId());
        if (decodifica.getDescrizione() != null)
          cd.setDescrizione(decodifica.getDescrizione());
        else
          cd.setDescrizione(decodifica.getDescrizioneBreve());
        listCD.add(cd);
      }
    } else {
      LOG.info(prf + "arrDecodifiche nullo");
    }

    return listCD;
  }


  @Override
  public List<DecodificaDTO> schemiFatturaElettronica() throws Exception {
    String prf = "[DecodificheDAOImpl::schemiFatturaElettronica] ";
    LOG.info(prf + " BEGIN");
    List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
    try {

      String sql = "SELECT DESCRIZIONE AS descrizione, DESCR_BREVE AS descrizione_breve, ID_SCHEMA_FATT_ELETT AS id ";
      sql += "FROM PBANDI_C_SCHEMA_FATT_ELETT ORDER BY DESCR_BREVE, DESCRIZIONE ASC";
      LOG.info("\n" + sql);

      result = getJdbcTemplate().query(sql, new BeanRowMapper(DecodificaDTO.class));
      LOG.info(prf + "Record trovati = " + result.size());

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca in PBANDI_C_SCHEMA_FATT_ELETT: ", e);
      throw new Exception("Errore durante la ricerca degli schemi delle fatture elettroniche.");
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  @Override
  // ex pbandiweb GestioneDocumentiDiSpesaBusinessImpl.findElencoTask()
  // Non esiste un id, quindi restituisce una lista di stringhe.
  public List<String> elencoTask(long idProgetto, long idUtente, HttpServletRequest reqo) throws Exception {
    String prf = "[DecodificheDAOImpl::elencoTask] ";
    LOG.info(prf + " BEGIN");

    if (idProgetto == 0) {
      throw new InvalidParameterException("idProgetto non valorizzato.");
    }
    if (idUtente == 0) {
      throw new InvalidParameterException("idUtente non valorizzato.");
    }

    List<String> result = new ArrayList<String>();
    try {

      String sql = "select task from PBANDI_R_DOC_SPESA_PROGETTO where ID_PROGETTO = " + idProgetto;
      LOG.info("\n" + sql);

      List<PbandiRDocSpesaProgettoVO> listaVO = getJdbcTemplate().query(sql, new BeanRowMapper(PbandiRDocSpesaProgettoVO.class));
      LOG.info(prf + "Record trovati = " + listaVO.size());

      ArrayList<String> listaDTO = new ArrayList<String>();
      for (PbandiRDocSpesaProgettoVO vo : listaVO) {
        listaDTO.add(vo.getTask());
      }

      for (int i = 0; i < listaDTO.size(); i++) {
        if (listaDTO.get(i) != null) {
          boolean found = false;
          for (int y = 0; y < result.size(); y++) {
            if (result.get(y).equals(listaDTO.get(i))) {
              found = true;
              break;
            }
          }
          if (!found) {
            result.add(listaDTO.get(i));
          }
        }
      }

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca in PBANDI_R_DOC_SPESA_PROGETTO: ", e);
      throw new Exception("Errore durante la ricerca dei task.");
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  @Override
  public List<DecodificaDTO> tipiDocumentoSpesa() throws Exception {
    String prf = "[DecodificheDAOImpl::tipiDocumentoSpesa] ";
    LOG.info(prf + " BEGIN");
    List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
    try {

      String sql = "select ID_TIPO_DOCUMENTO_SPESA as ID, DESC_TIPO_DOCUMENTO_SPESA as DESCRIZIONE from PBANDI_D_TIPO_DOCUMENTO_SPESA ";
      sql += "where (trunc(sysdate) >= nvl(trunc(DT_INIZIO_VALIDITA), trunc(sysdate) -1) ";
      sql += " and trunc(sysdate) < nvl(trunc(DT_FINE_VALIDITA), trunc(sysdate) +1)) ORDER BY DESC_TIPO_DOCUMENTO_SPESA ASC";
      LOG.info("\n" + sql);

      result = getJdbcTemplate().query(sql, new BeanRowMapper(DecodificaDTO.class));
      LOG.info(prf + "Record trovati = " + result.size());

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca in PBANDI_D_TIPO_DOCUMENTO_SPESA: ", e);
      throw new Exception("Errore durante la ricerca dei tipi documento spesa.");
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  // Restituisce true se la DS in input è di tipo finale con CFP.
  public boolean dichiarazioneSpesaFinaleConCFP(Long idDichiarazioneDiSpesa) throws DaoException {

    String sqlDS = "SELECT ID_TIPO_DICHIARAZ_SPESA FROM PBANDI_T_DICHIARAZIONE_SPESA WHERE ID_DICHIARAZIONE_SPESA = " + idDichiarazioneDiSpesa;
    LOG.info("\n" + sqlDS);
    Long idTipoDS = getJdbcTemplate().queryForObject(sqlDS, Long.class);

    String descBreveTipoDS = this.descrizioneDaId("PBANDI_D_TIPO_DICHIARAZ_SPESA", "ID_TIPO_DICHIARAZ_SPESA", "DESC_BREVE_TIPO_DICHIARA_SPESA", idTipoDS);

    return (Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE_CON_COMUNICAZIONE.equalsIgnoreCase(descBreveTipoDS));

  }

  // Restituisce l'id della CFP di un progetto.
  public Long idComunicazioneFineProgetto(Long idProgetto) {

    String sqlCFP = "SELECT ID_COMUNICAZ_FINE_PROG FROM PBANDI_T_COMUNICAZ_FINE_PROG WHERE ID_PROGETTO = " + idProgetto;
    LOG.info("\n" + sqlCFP);
    Long idCFP = getJdbcTemplate().queryForObject(sqlCFP, Long.class);

    return idCFP;

  }

  @Override
  public List<DecodificaDTO> tipiDocumentoIndexUploadable() throws Exception {
    String prf = "[DecodificheDAOImpl::tipiDocumentoIndexUploadable] ";
    LOG.info(prf + " BEGIN");
    List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
    try {

      String sql = "select ID_TIPO_DOCUMENTO_INDEX as ID, DESC_TIPO_DOC_INDEX as DESCRIZIONE from PBANDI_C_TIPO_DOCUMENTO_INDEX ";
      sql += "where FLAG_UPLOADABLE = 'S' ORDER BY DESC_TIPO_DOC_INDEX ASC";
      LOG.info("\n" + sql);

      result = getJdbcTemplate().query(sql, new BeanRowMapper(DecodificaDTO.class));
      LOG.info(prf + "Record trovati = " + result.size());

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca in PBANDI_C_TIPO_DOCUMENTO_INDEX: ", e);
      throw new Exception("Errore durante la ricerca dei tipi documento index.");
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  //select DESC_CATEG_ANAGRAFICA as descCategAnagrafica , ID_CATEG_ANAGRAFICA as idCategAnagrafica from PBANDI_D_CATEG_ANAGRAFICA ORDER BY ID_CATEG_ANAGRAFICA , DESC_CATEG_ANAGRAFICA ASC
  // Al momento non è esposto come servizio.
  public List<DecodificaDTO> categorieAnagrafica() throws Exception {
    String prf = "[DecodificheDAOImpl::categorieAnagrafica] ";
    LOG.info(prf + " BEGIN");
    List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
    try {

      String sql = "select ID_CATEG_ANAGRAFICA as ID, DESC_CATEG_ANAGRAFICA as DESCRIZIONE from PBANDI_D_CATEG_ANAGRAFICA ";
      sql += "ORDER BY ID_CATEG_ANAGRAFICA , DESC_CATEG_ANAGRAFICA ASC";
      LOG.info("\n" + sql);

      result = getJdbcTemplate().query(sql, new BeanRowMapper(DecodificaDTO.class));
      LOG.info(prf + "Record trovati = " + result.size());

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca in PBANDI_D_CATEG_ANAGRAFICA: ", e);
      throw new Exception("Errore durante la ricerca delle categorie anagrafica.");
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  public List<TipologiaVoceDiSpesaDTO> ottieniCategorie() throws Exception {
    String prf = "[DecodificheDAOImpl::categorieAnagrafica] ";
    LOG.info(prf + " BEGIN");
    List<TipologiaVoceDiSpesaDTO> result = new ArrayList<>();
  try {
    String sql = "SELECT ID_TIPOLOGIA_VOCE_DI_SPESA, DESCRIZIONE, PERC_QUOTA_CONTRIBUTO FROM PBANDI_D_TIPOL_VOCE_DI_SPESA";
    LOG.info("\n" + sql);
    result = getJdbcTemplate().query(sql, new BeanRowMapper(TipologiaVoceDiSpesaDTO.class));
    LOG.info(prf + "Record trovati = " + result.size());
  }
  catch (Exception e) {
    LOG.error(prf + " ERRORE nella ricerca in PBANDI_D_TIPOL_VOCE_DI_SPESA: ", e);
    throw new Exception("Errore durante la ricerca delle categorie anagrafica.");
  }
  finally {
    LOG.info(prf + " END");
  }
    return result;
  }

}
