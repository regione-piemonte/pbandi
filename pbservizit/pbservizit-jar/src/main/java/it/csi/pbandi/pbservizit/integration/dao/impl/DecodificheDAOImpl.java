/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.integration.dao.DecodificheDAO;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
//import it.csi.pbandi.pbweb.dto.AttivitaAtecoDTO;
//import it.csi.pbandi.pbweb.dto.FornitoreComboDTO;
//import it.csi.pbandi.pbweb.dto.FornitoreDTO;
//import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
//import it.csi.pbandi.pbweb.exception.DaoException;
//import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.BeanRowMapper;
//import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.FornitoreQualificaRowMapper;
//import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.PbandiDTipoSoggettoRowMapper;
//import it.csi.pbandi.pbweb.integration.vo.ArchivioFileVO;
//import it.csi.pbandi.pbweb.integration.vo.FornitoreQualificaVO;
//import it.csi.pbandi.pbweb.integration.vo.PbandiDTipoSoggettoVO;
//import it.csi.pbandi.pbweb.integration.vo.PbandiRDocSpesaProgettoVO;
//import it.csi.pbandi.pbweb.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
//import it.csi.pbandi.pbweb.pbandisrv.business.gestionedocumentidispesa.GestioneDocumentiDiSpesaBusinessImpl;
//import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica;
//import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;
//import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
//import it.csi.pbandi.pbweb.util.BeanUtil;
import it.csi.pbandi.pbservizit.util.Constants;
//import it.csi.pbandi.pbweb.util.FileSqlUtil;
//import it.csi.pbandi.pbweb.util.RequestUtil;
//import it.csi.pbandi.pbweb.util.StringUtil;
//import it.csi.pbandi.pbweb.util.ValidatorCodiceFiscale;
//import it.csi.pbandi.pbweb.util.ValidatorPartitaIva;

@Service
public class DecodificheDAOImpl extends JdbcDaoSupport implements DecodificheDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
//	@Autowired
//	protected BeanUtil beanUtil;
//	
//	@Autowired
//	protected GestioneDocumentiDiSpesaBusinessImpl gestioneDocumentiDiSpesaBusinessImpl;
//	
//	@Autowired
//	protected GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioBusinessImpl;
	
	@Autowired
	public DecodificheDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
//	@Autowired
//	protected FileSqlUtil fileSqlUtil;
	
//	@Override
//	// ex DecodificheManager.findDecodifiche()
//	public List<DecodificaDTO> ottieniTipologieFornitore() {
//		String prf = "[DecodificheDAOImpl::ottieniTipologieFornitore] ";
//		LOG.info(prf + " BEGIN");
//		List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
//		try {
//			
//			StringBuilder sql = new StringBuilder();
//			sql.append("SELECT ID_TIPO_SOGGETTO, DESC_TIPO_SOGGETTO, DESC_BREVE_TIPO_SOGGETTO, DT_INIZIO_VALIDITA, DT_FINE_VALIDITA ");
//			sql.append("FROM PBANDI_D_TIPO_SOGGETTO ");
//			sql.append("WHERE DT_FINE_VALIDITA IS NULL ");
//			sql.append("ORDER BY DESC_TIPO_SOGGETTO");
//
//			LOG.debug("\n"+sql.toString());
//			
//			List<PbandiDTipoSoggettoVO> lista =  getJdbcTemplate().query(sql.toString(), new PbandiDTipoSoggettoRowMapper());
//			
//			if(lista != null) {
//				for (PbandiDTipoSoggettoVO vo : lista) {
//					DecodificaDTO dto = new DecodificaDTO();
//					if (vo.getIdTipoSoggetto() != null)
//						dto.setId(vo.getIdTipoSoggetto().longValue());
//					dto.setDescrizione(vo.getDescTipoSoggetto());
//					dto.setDescrizioneBreve(vo.getDescBreveTipoSoggetto());
//					result.add(dto);
//				}
//			}
//
//		} catch (Exception e) {
//			LOG.error(prf+" ERRORE nella ricerca in PbandiDTipoSoggetto: ", e);
//			//throw new DaoException(" ERRORE nella ricerca in PbandiDTipoSoggetto: ", e);
//		}
//		finally {
//			LOG.info(prf+" END");
//		}
//		
//		return result;
//	}
	
	// Se il tipo di documento di spesa è cedolino, cedolinoCocopro, cedolinoCostiStandard o notaSpese,
	// si scarta persona giuridica.
//	@Override
//	public List<DecodificaDTO> ottieniTipologieFornitorePerIdTipoDocSpesa(int idTipoDocumentoDiSpesa) {
//		String prf = "[DecodificheDAOImpl::ottieniTipologieFornitorePerIdDocSpesa] ";
//		LOG.info(prf + " BEGIN");
//		List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
//		try {
//			
//			result = this.ottieniTipologieFornitore();
//			
//			if (idTipoDocumentoDiSpesa == Constants.ID_TIPO_DOC_SPESA_CEDOLINO || 
//				idTipoDocumentoDiSpesa == Constants.ID_TIPO_DOC_SPESA_CEDOLINO_A_PROGETTO || 
//				idTipoDocumentoDiSpesa == Constants.ID_TIPO_DOC_SPESA_CEDOLINO_COSTI_STANDARD || 
//				idTipoDocumentoDiSpesa == Constants.ID_TIPO_DOC_SPESA_NOTA_SPESE) {
//				Iterator<DecodificaDTO> iter = result.iterator();
//				while (iter.hasNext()) {
//					DecodificaDTO d = iter.next();
//					if (d.getDescrizioneBreve().equals(
//							Constants.TIPOLOGIA_PERSONA_GIURIDICA)) {
//						iter.remove();
//					}
//				}
//
//			}
//
//		} catch (Exception e) {
//			LOG.error(prf+" ERRORE nella ricerca in PbandiDTipoSoggetto: ", e);
//			//throw new DaoException(" ERRORE nella ricerca in PbandiDTipoSoggetto: ", e);
//		}
//		finally {
//			LOG.info(prf+" END");
//		}
//		
//		return result;
//	}
	
//	@Override
//	// ex gestioneDatiDiDominioSrv.findFormeGiuridiche()
//	public List<DecodificaDTO> tipologieFormaGiuridica(String flagPrivato) throws Exception {
//		String prf = "[DecodificheDAOImpl::tipologieFormaGiuridica] ";
//		LOG.info(prf + " BEGIN");
//		List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
//		try {
//			
//			String sql = "select ID_FORMA_GIURIDICA as ID, DESC_FORMA_GIURIDICA as DESCRIZIONE from PBANDI_D_FORMA_GIURIDICA ";
//			if (!StringUtil.isEmpty(flagPrivato))
//				sql += "where FLAG_PRIVATO = '"+flagPrivato+"' ";
//			sql += "ORDER BY DESC_FORMA_GIURIDICA ASC";
//			LOG.info("\n"+sql);
//			
//			result =  getJdbcTemplate().query(sql,  new BeanRowMapper(DecodificaDTO.class));
//			LOG.info(prf + "Record trovati = " + result.size());
//
//		} catch (Exception e) {
//			LOG.error(prf+" ERRORE nella ricerca in PBANDI_D_FORMA_GIURIDICA: ", e);
//			throw new Exception("Errore durante la ricerca delle tipologie di forma giuridica.");
//		}
//		finally {
//			LOG.info(prf+" END");
//		}
//		
//		return result;
//	}
	
//	@Override
//	public List<DecodificaDTO> nazioni() throws Exception {
//		String prf = "[DecodificheDAOImpl::nazioni] ";
//		LOG.info(prf + " BEGIN");
//		List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
//		try {
//			
//			String sql = "select ID_NAZIONE as ID, DESC_NAZIONE as DESCRIZIONE from PBANDI_D_NAZIONE ";
//			sql += "where (trunc(sysdate) >= nvl(trunc(DT_INIZIO_VALIDITA), trunc(sysdate) -1) ";
//			sql += " and trunc(sysdate) < nvl(trunc(DT_FINE_VALIDITA), trunc(sysdate) +1)) ORDER BY DESC_NAZIONE ASC";
//			LOG.info("\n"+sql);
//			
//			result =  getJdbcTemplate().query(sql,  new BeanRowMapper(DecodificaDTO.class));
//			LOG.info(prf + "Record trovati = " + result.size());
//
//		} catch (Exception e) {
//			LOG.error(prf+" ERRORE nella ricerca in PBANDI_D_FORMA_GIURIDICA: ", e);
//			throw new Exception("Errore durante la ricerca delle nazioni.");
//		}
//		finally {
//			LOG.info(prf+" END");
//		}
//		
//		return result;
//	}
	
//	@Override
//	// GestioneDatiDiDominioBusinessImpl.findAllQualifiche()
//	public List<DecodificaDTO> qualifiche(long idUtente) throws InvalidParameterException, Exception {
//		String prf = "[DecodificheDAOImpl::qualifiche] ";
//		LOG.info(prf + " BEGIN");
//		
//		if (idUtente == 0) {
//			throw new InvalidParameterException("idUtente non valorizzato.");
//		}
//		
//		List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
//		try {
//					
//			String sql = "select ID_QUALIFICA as ID, DESC_QUALIFICA as DESCRIZIONE from PBANDI_D_QUALIFICA ORDER BY DESC_QUALIFICA";
//			LOG.info("\n"+sql);
//			
//			result =  getJdbcTemplate().query(sql,  new BeanRowMapper(DecodificaDTO.class));
//			LOG.info(prf + "Record trovati = " + result.size());
//
//		} catch (Exception e) {
//			LOG.error(prf+" ERRORE nella ricerca in PBANDI_D_QUALIFICA: ", e);
//			throw new Exception("Errore durante la ricerca delle qualifiche.");
//		}
//		finally {
//			LOG.info(prf+" END");
//		}
//		
//		return result;
//	}
	
//	@Override
//	// ex GestioneFornitoriBusinessImpl.findFornitoriSemplificazione().
//	// idTipoFornitore = PBANDI_D_TIPO_SOGGETTO.ID_TIPO_SOGGETTO (persona fisica \ persona giuridica)
//	// fornitoriValidi: true = solo gli attivi, false = solo i disattivi, altrimenti tutti i fornitori.
//	public List<FornitoreDTO> fornitori(long idSoggettoFornitore, long idTipoFornitore, String fornitoriValidi) {
//		String prf = "[DecodificheDAOImpl::fornitori] ";
//		LOG.info(prf + " BEGIN");
//		LOG.info(prf + " input: idSoggettoFornitore = "+idSoggettoFornitore+"; idTipoFornitore = "+idTipoFornitore+"; fornitoriValidi = "+fornitoriValidi);
//		List<FornitoreDTO> result = new ArrayList<FornitoreDTO>();
//		try {
//			String sql = fileSqlUtil.getQuery("FornitoreQualificaVO.sql");
//			
//			if (!StringUtils.isBlank(fornitoriValidi)) {
//				if ("true".equalsIgnoreCase(fornitoriValidi)) {
//					sql += " AND f.dt_fine_validita IS NULL";
//				} else if ("false".equalsIgnoreCase(fornitoriValidi)) {
//					sql += " AND f.dt_fine_validita IS NOT NULL";
//				}
//			}
//			
//			sql += " AND f.id_soggetto_fornitore = ? AND f.id_tipo_soggetto = ?";
//			sql += " ORDER BY COGNOME_FORNITORE , NOME_FORNITORE , DENOMINAZIONE_FORNITORE ASC";
//
//			LOG.info("\n"+sql);
//			LOG.info("\n con parametri: idSoggettoFornitore = "+idSoggettoFornitore+"; idTipoFornitore = "+idTipoFornitore);
//			
//			List<FornitoreQualificaVO> lista = getJdbcTemplate().query(sql.toString(), new Object[] { idSoggettoFornitore, idTipoFornitore }, new FornitoreQualificaRowMapper());
//			
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("codiceFiscaleFornitore", "codiceFiscaleFornitore");
//			map.put("cognomeFornitore", "cognomeFornitore");
//			map.put("denominazioneFornitore", "denominazioneFornitore");
//			map.put("descTipoSoggetto", "descTipoSoggetto");
//			map.put("idFornitore", "idFornitore");
//			map.put("idSoggettoFornitore", "idSoggettoFornitore");
//			map.put("idTipoSoggetto", "idTipoSoggetto");
//			map.put("nomeFornitore", "nomeFornitore");
//			map.put("partitaIvaFornitore", "partitaIvaFornitore");
//			map.put("dtFineValiditaFornitore", "dtFineValidita");
//			map.put("descBreveTipoSoggetto", "descBreveTipoSoggetto");
//			map.put("flagHasQualifica", "flagHasQualifica");
//			map.put("idFormaGiuridica", "idFormaGiuridica");
//			
//			return beanUtil.transformList(lista, FornitoreDTO.class, map);		
//			
//		} catch (Exception e) {
//			LOG.error(prf+" ERRORE nella ricerca in PbandiDTipoSoggetto: ", e);
//			//throw new DaoException(" ERRORE nella ricerca in PbandiDTipoSoggetto: ", e);
//		}
//		finally {
//			LOG.info(prf+" END");
//		}
//		
//		return result;
//	}
	
//	@Override
//	// Popola la combo dei fornitori Documenti Di Spesa -> Nuovo Documento.
//	// idTipoFornitore = PBANDI_D_TIPO_SOGGETTO.ID_TIPO_SOGGETTO (persona fisica \ persona giuridica)
//	// idFornitore = id del fornitore precedentemente selezionato in caso di modifica.
//	// Ex pbandiweb GestioneDocumentiDiSpesaBusinessImpl.findFornitoriByTipologiaFornitore()
//	public List<FornitoreComboDTO> fornitoriCombo(long idSoggettoFornitore, long idTipoFornitore, long idFornitore) {
//		String prf = "[DecodificheDAOImpl::fornitoriCombo] ";
//		LOG.info(prf + " BEGIN");
//		LOG.info(prf + " input: idSoggettoFornitore = "+idSoggettoFornitore+"; idTipoFornitore = "+idTipoFornitore+"; idFornitore = "+idFornitore);
//		List<FornitoreComboDTO> result = new ArrayList<FornitoreComboDTO>();
//		try {
//			
//			// Elenco dei fornitori.
//			String fornitoriValidi = null;
//			List<FornitoreDTO> fornitori = this.fornitori(idSoggettoFornitore, idTipoFornitore, fornitoriValidi);
//			
//			// Codice preso lato web e javascript per 
//			// - gestire la descrizione da visualizzare nella combo a video
//			// - popolare un flag che dica se il fornitore è formalmente valido e quindi selezionabile.
//			
//			for (FornitoreDTO fornitore : fornitori) {
//				
//				if(fornitore.getCodiceFiscaleFornitore()!=null && fornitore.getCodiceFiscaleFornitore().startsWith("PBAN")){				
//					fornitore.setCodiceFiscaleFornitore("n.d. ");
//				}
//				
//				if (fornitore.getDtFineValidita() == null || idFornitore == fornitore.getIdFornitore().longValue()) {
//					FornitoreComboDTO fornitoreComboDTO = new FornitoreComboDTO();
//					fornitoreComboDTO.setIdFornitore(fornitore.getIdFornitore());
//
//					// 08/06/2017: su richiesta di Luigi il cf viene scritto così
//					// ALLMAG S.R.L. - <span class="cf">05115410010</span>
//					// Jira PBANDI-2760: aggiunto if forma giuridica.
//					if (idTipoFornitore == Constants.ID_TIPOLOGIA_PERSONA_FISICA) {
//
//						if (fornitore.getDtFineValidita() != null) {
//							fornitoreComboDTO.setDescrizione(fornitore
//									.getCognomeFornitore()
//									+ " "
//									+ fornitore.getNomeFornitore()
//									//+ " - <span class='cf'>" + fornitore.getCodiceFiscaleFornitore() + "</span> (STORICIZZATO)");
//									+ " - "	+ fornitore.getCodiceFiscaleFornitore()	+ " (STORICIZZATO)");
//									
//						} else {
//							fornitoreComboDTO.setDescrizione(fornitore
//									.getCognomeFornitore()
//									+ " "
//									+ fornitore.getNomeFornitore()
//									//+ " - <span class='cf'>" + fornitore.getCodiceFiscaleFornitore() + "</span>");
//									+ " - " + fornitore.getCodiceFiscaleFornitore());
//						}						
//
//					} else {
//						if (fornitore.getDtFineValidita() != null) {
//							fornitoreComboDTO.setDescrizione(fornitore
//									.getDenominazioneFornitore()
//									//+ " - <span class='cf'>" + fornitore.getCodiceFiscaleFornitore() + "</span> (STORICIZZATO)");
//									+ " - " + fornitore.getCodiceFiscaleFornitore());
//						} else {
//							fornitoreComboDTO.setDescrizione(fornitore
//									.getDenominazioneFornitore()
//									//+ " - <span class='cf'>" + fornitore.getCodiceFiscaleFornitore() + "</span>");
//									+ " - " + fornitore.getCodiceFiscaleFornitore());
//						}
//						
//						// Lo commento poichè da quel che capisco la forma giuridica veniva inserita da java e poi nascosta da javascript.
//						// Jira PBANDI-2766: aggiunto id forma giuridica tramite un altro span in coda al precedente.
//						//String id = (fornitore.getIdFormaGiuridica() == null) ? "" : fornitore.getIdFormaGiuridica().toString();						
//						//String s = "<span class='idFormaGiuridica' style='display: none'>"+id+"</span>";
//						//fornitoreComboDTO.setDescrizione(fornitoreComboDTO.getDescrizione()+s);						
//						
//					}
//					
//					/* Verifico se i fornitori sono formalmente corretti: cf valido e, per persone giuridiche, idFormaGiuridica valorizzato.*/
//					String cod = fornitore.getCodiceFiscaleFornitore();
//					boolean cfValido = false;
//					if("n.d.".equalsIgnoreCase(cod) || "00000000000".equalsIgnoreCase(cod)) {
//						cfValido = true;
//					} else {
//					    if( cod == "") {
//					    	cfValido = false;
//					    //} else if (/^([A-Z]){4}_([0-9]){11}\*$/.test(cod)) {
//					    //	cfValido = true;
//						} else if (cod == "00000000000"){
//							cfValido = false;
//						} else if (cod.length() == 16 ){
//							cfValido = ValidatorCodiceFiscale.isCodiceFiscalePersonaFisicaValido(cod);
//						} else if (cod.length() == 11 && cod != "00000000000"){
//							cfValido = ValidatorPartitaIva.isPartitaIvaValid(cod);	
//						} else {
//							cfValido = false;
//						}
//					}
//					
//					boolean formaGiuridicaValida = true;
//					if (fornitore.getIdTipoSoggetto().intValue() == Constants.ID_TIPOLOGIA_PERSONA_FISICA) {
//						// persona fisica: il test su idFormaGiuridica non è applicabile.
//						formaGiuridicaValida = true;
//					} else {
//						// persona giuridica: idFormaGiuridica deve essere valorizzato.
//						formaGiuridicaValida = (fornitore.getIdFormaGiuridica() != null);
//					}
//					
//					fornitoreComboDTO.setFormalmenteValido(cfValido && formaGiuridicaValida);
//					
//					result.add(fornitoreComboDTO);
//
//				}
//			}
//
//		} catch (Exception e) {
//			LOG.error(prf+" ERRORE: ", e);
//			//throw new DaoException(" ERRORE nella ricerca in PbandiDTipoSoggetto: ", e);
//		}
//		finally {
//			LOG.info(prf+" END");
//		}
//		
//		return result;
//	}
	
//	public String descrizioneDaId(String tabella, String colonnaId, String colonnaDescrizione, Long valoreId) 
//			throws DaoException {
//		String prf = "[DecodificheDAOImpl::descrizioneDaId()] ";
//		LOG.info(prf + "BEGIN");
//		String descrizione = null;
//		try {			
//			StringBuilder sql = new StringBuilder();
//			sql.append("SELECT "+colonnaDescrizione+" FROM "+tabella+" WHERE "+colonnaId+" = "+valoreId.toString());
//			LOG.debug("\n"+sql.toString());
//			descrizione = (String) getJdbcTemplate().queryForObject(sql.toString(), String.class);			
//		} catch (Exception e) {
//			LOG.error(prf+"ERRORE nella ricerca di una descriziozne da un id: ", e);
//			throw new DaoException("ERRORE nella ricerca di una descriziozne da un id: ", e);
//		}
//		finally {
//			LOG.info(prf+"END");
//		}
//		
//		return descrizione;
//	}
	
	public BigDecimal idDaDescrizione(String tabella, String colonnaId, String colonnaDescrizione, String valoreDescrizione) 
			throws DaoException {
		String prf = "[DecodificheDAOImpl::idDaDescrizione()] ";
		LOG.info(prf + "BEGIN");
		BigDecimal id = null;
		try {			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT "+colonnaId+" FROM "+tabella+" WHERE "+colonnaDescrizione+" = '"+valoreDescrizione+"'");
			LOG.debug("\n"+sql.toString());
			id = (BigDecimal) getJdbcTemplate().queryForObject(sql.toString(), BigDecimal.class);			
		} catch (Exception e) {
			LOG.error(prf+"ERRORE nella ricerca di un id da una descriziozne: ", e);
			throw new DaoException("ERRORE nella ricerca di un id da una descriziozne: ", e);
		}
		finally {
			LOG.info(prf+"END");
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
			
			String sql = "SELECT VALORE FROM PBANDI_C_COSTANTI WHERE ATTRIBUTO = '"+attributo+"'";
			LOG.debug("\n"+sql.toString());
			
			valore = (String) getJdbcTemplate().queryForObject(sql.toString(), String.class);
			
			if (obbligatoria && StringUtil.isEmpty(valore)) {
				throw new DaoException("Costante "+attributo+" non trovata.");
			}
			
		} catch (Exception e) {
			LOG.error(prf+"ERRORE nella ricerca di una COSTANTE: ", e);
			throw new DaoException("ERRORE nella ricerca della costante "+attributo+".", e);
		}
		finally {
			LOG.info(prf+"END");
		}
		
		return valore;
	}



//	@Override
//	public List<DecodificaDTO> attivitaCombo (long idUtente, HttpServletRequest request) throws UtenteException, GestioneDatiDiDominioException, UnrecoverableException {
//		String prf = "[DecodificheDAOImpl::attivitaCombo] ";
//		LOG.info(prf + "BEGIN");
//		
//		String idIride = RequestUtil.idIrideInSessione(request);
//		
///**
//lato pbandiweb
//GestioneDatiDiDominioBusinessImpl.findOggettoAttivita()
//
//lato pbandisrv
//GestioneDatiDiDominioBusinessImpl.findDecodifiche()
//*/
//		
//		// Logica presa da Action di pbandiweb -> CPBESDocumentoDiSpesa::inizializzaContentPanel
//		
//		String chiave = GestioneDatiDiDominioSrv.TIPO_OGGETTO_ATTIVITA;
//		LOG.debug(prf + "chiave="+chiave);
//		
//		ArrayList<DecodificaDTO> listCD = new ArrayList<DecodificaDTO>();
//		
//		Decodifica[] arrDecodifiche = gestioneDatiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride, chiave);
//		if(arrDecodifiche!=null) {
//			for (Decodifica decodifica : arrDecodifiche) {
//				DecodificaDTO cd = new DecodificaDTO();
//				cd.setId(decodifica.getId());
//				if (decodifica.getDescrizione() != null)
//					cd.setDescrizione(decodifica.getDescrizione());
//				else
//					cd.setDescrizione(decodifica.getDescrizioneBreve());
//				listCD.add(cd);
//			}
//		}else {
//			LOG.info(prf + "arrDecodifiche nullo");
//		}
//		
//		return listCD;
//	}
	
//	@Override
//	public List<DecodificaDTO> schemiFatturaElettronica() throws Exception {
//		String prf = "[DecodificheDAOImpl::schemiFatturaElettronica] ";
//		LOG.info(prf + " BEGIN");
//		List<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
//		try {
//			
//			String sql = "SELECT DESCRIZIONE AS descrizione, DESCR_BREVE AS descrizioneBreve, ID_SCHEMA_FATT_ELETT AS id ";
//			sql += "FROM PBANDI_C_SCHEMA_FATT_ELETT ORDER BY DESCR_BREVE, DESCRIZIONE ASC";
//			LOG.info("\n"+sql);
//			
//			result =  getJdbcTemplate().query(sql,  new BeanRowMapper(DecodificaDTO.class));
//			LOG.info(prf + "Record trovati = " + result.size());
//
//		} catch (Exception e) {
//			LOG.error(prf+" ERRORE nella ricerca in PBANDI_C_SCHEMA_FATT_ELETT: ", e);
//			throw new Exception("Errore durante la ricerca degli schemi delle fatture elettroniche.");
//		}
//		finally {
//			LOG.info(prf+" END");
//		}
//		
//		return result;
//	}
	
//	@Override
//	// ex pbandiweb GestioneDocumentiDiSpesaBusinessImpl.findElencoTask()
//	// Non esiste un id, quindi restituisce una lista di stringhe.
//	public List<String> elencoTask(long idProgetto, long idUtente, HttpServletRequest reqo) throws Exception {
//		String prf = "[DecodificheDAOImpl::elencoTask] ";
//		LOG.info(prf + " BEGIN");
//		
//		if (idProgetto == 0) {
//			throw new InvalidParameterException("idProgetto non valorizzato.");
//		}
//		if (idUtente == 0) {
//			throw new InvalidParameterException("idUtente non valorizzato.");
//		}
//		
//		List<String> result = new ArrayList<String>();
//		try {
//			
//			String sql = "select task from PBANDI_R_DOC_SPESA_PROGETTO where ID_PROGETTO = "+idProgetto;		
//			LOG.info("\n"+sql);
//			
//			List<PbandiRDocSpesaProgettoVO> listaVO =  getJdbcTemplate().query(sql,  new BeanRowMapper(PbandiRDocSpesaProgettoVO.class));
//			LOG.info(prf + "Record trovati = " + listaVO.size());
//
//			ArrayList<String> listaDTO = new ArrayList<String>();
//			for (PbandiRDocSpesaProgettoVO vo : listaVO) {
//				listaDTO.add(vo.getTask());
//			}
//			
//			for (int i = 0; i < listaDTO.size(); i++) {
//				if (listaDTO.get(i) != null) {
//					boolean found = false;
//					for (int y = 0; y < result.size(); y++) {
//						if (result.get(y).equals(listaDTO.get(i))) {
//							found = true;
//							break;
//						}
//					}
//					if (!found) {
//						result.add(listaDTO.get(i));
//					}
//				}
//			}			
//			
//		} catch (Exception e) {
//			LOG.error(prf+" ERRORE nella ricerca in PBANDI_R_DOC_SPESA_PROGETTO: ", e);
//			throw new Exception("Errore durante la ricerca dei task.");
//		}
//		finally {
//			LOG.info(prf+" END");
//		}
//		
//		return result;
//	}

}
