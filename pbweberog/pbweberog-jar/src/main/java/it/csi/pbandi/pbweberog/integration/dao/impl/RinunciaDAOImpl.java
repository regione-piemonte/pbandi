/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbservizit.business.manager.DocumentiFSManager;
import it.csi.pbandi.pbservizit.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.PopolaTemplateManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SedeManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SoggettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Notification;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Task;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.BeneficiarioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.Modello;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.SedeDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DelegatoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SedeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCCostantiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRinunciaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTSoggettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweberog.dto.LongValue;
import it.csi.pbandi.pbweberog.dto.IndirizzoPrivatoCittadino;
import it.csi.pbandi.pbweberog.dto.IntegerValue;
import it.csi.pbandi.pbweberog.dto.erogazione.DichiarazioneDiRinunciaDTO;
import it.csi.pbandi.pbweberog.dto.erogazione.EsitoScaricaDichiarazioneDiRinuncia;
import it.csi.pbandi.pbweberog.exception.DaoException;
import it.csi.pbandi.pbweberog.exception.ErogazioneException;
import it.csi.pbandi.pbweberog.integration.dao.RinunciaDAO;
import it.csi.pbandi.pbweberog.integration.dao.impl.rowmapper.CostantiRowMapper;
import it.csi.pbandi.pbweberog.pbandisrv.business.digitalsign.DigitalSignBusinessImpl;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.FileSqlUtil;
import it.csi.pbandi.pbweberog.util.FileUtil;
import it.csi.pbandi.pbweberog.util.NumberUtil;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Component
public class RinunciaDAOImpl extends JdbcDaoSupport implements RinunciaDAO{
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	protected DigitalSignBusinessImpl digitalSignBusinessImpl;
	
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private SoggettoManager soggettoManager;
	
	@Autowired
	private ProgettoManager progettoManager;
	
	@Autowired
	private DecodificheManager decodificheManager;
	
	@Autowired
	private ContoEconomicoManager contoEconomicoManager;
	
	@Autowired
	private PopolaTemplateManager popolaTemplateManager;
	
	@Autowired
	private SedeManager sedeManager;
	
	@Autowired
	private NeofluxBusinessImpl neofluxBusiness;
	
	@Autowired
	private DocumentoManager documentoManager;
	
	@Autowired
	private DocumentiFSManager documentiFSManager;
	
	protected FileApiServiceImpl fileApiServiceImpl;
	
	@Autowired
	protected FileSqlUtil fileSqlUtil;
	
	
	
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	@Autowired
	public RinunciaDAOImpl(DataSource dataSource) throws Exception {
		setDataSource(dataSource);
		this.genericDAO = new GenericDAO(dataSource);
		try {
			fileApiServiceImpl = new FileApiServiceImpl("/pbstorage_online");
		} catch (IncorrectUploadPathException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public Boolean getIsBeneficiarioPrivatoCittadino(Long idProgetto, Long idUtente, String idIride)
			throws UnrecoverableException, InvalidParameterException {
		String prf = "[DichiarazioneDiSpesaDAOImpl::getIsBeneficiarioPrivatoCittadino] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente );
		
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato"); 
		}				
		
		Boolean isPersinaFisica = null;
		
		try {
			
			Long idTipoAnagrafica = 1L;
			Long idTipoBeneficiario = 4L;				
			
			String sql =  "select count(id_soggetto) as value					\r\n"
						+ "from pbandi_r_soggetto_progetto 						\r\n"
						+ "where id_progetto = ?								\r\n"
						+ "and id_tipo_anagrafica = ?							\r\n"
						+ "and id_tipo_beneficiario != ?						\r\n"
						+ "and id_persona_fisica is not null 	 					";					
			
			
			Object[] par = {idProgetto, idTipoAnagrafica, idTipoBeneficiario};
			
			logger.info(prf + "\n"+sql);	
			Integer num = ((IntegerValue)getJdbcTemplate().queryForObject(sql, par, new BeanRowMapper(IntegerValue.class))).getValue();	
						
			if(num == null)
				isPersinaFisica = false; 
			else if(num > 0)
				isPersinaFisica = true;
			else
				isPersinaFisica = false;
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nella ricerca dei dati beneficirio: ", e);
			throw new UnrecoverableException(" ERRORE nella ricerca dei dati beneficirio:");
		}
		finally {
			LOG.info(prf+" END");
		}
						
		return isPersinaFisica;
	}
	
	public Boolean getIsBeneficiarioPrivatoCittadino(Long idProgetto)
			throws UnrecoverableException {
		String prf = "[DichiarazioneDiSpesaDAOImpl::getIsBeneficiarioPrivatoCittadino] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + ";");
		
		if (idProgetto == null) {
			throw new UnrecoverableException("idProgetto non valorizzato");
		}		
				
		Boolean isPersinaFisica = null;
		
		try {
			
			Long idTipoAnagrafica = 1L;
			Long idTipoBeneficiario = 4L;				
			
			String sql =  "select count(id_soggetto) as value					\r\n"
						+ "from pbandi_r_soggetto_progetto 						\r\n"
						+ "where id_progetto = ?								\r\n"
						+ "and id_tipo_anagrafica = ?							\r\n"
						+ "and id_tipo_beneficiario != ?						\r\n"
						+ "and id_persona_fisica is not null 	 					";					
		
			
			Object[] par = {idProgetto, idTipoAnagrafica, idTipoBeneficiario};
			
			
			
			logger.info(prf + "\n"+sql);	
			Integer num = ((IntegerValue)getJdbcTemplate().queryForObject(sql, par, new BeanRowMapper(IntegerValue.class))).getValue();	
						
			if(num == null)
				isPersinaFisica = false; 
			else if(num > 0)
				isPersinaFisica = true;
			else
				isPersinaFisica = false;
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nella ricerca dei dati beneficirio: ", e);
			throw new UnrecoverableException(" ERRORE nella ricerca dei dati beneficirio:");
		}
		finally {
			LOG.info(prf+" END");
		}
						
		return isPersinaFisica;
	}
	
	
	

	private String leggiCostanteNonObbligatoria(String attributo) throws Exception {
		String valore = null;
		try {
			String sql = fileSqlUtil.getQuery("Costanti.sql");
			LOG.info("query :" + sql);
			Object[] params = new Object[] {attributo};
			PbandiCCostantiVO c = getJdbcTemplate().queryForObject(sql, params, new CostantiRowMapper());
			valore = c.getValore();
			LOG.info("Valore della costante "+attributo+" = "+valore);

		} catch (Exception e) {
			LOG.info("Costante "+attributo+" non valorizzata.");
			throw e;
		}
		return valore;
	}
	
	@Override
	@Transactional
	public RappresentanteLegaleDTO[] findRappresentantiLegali(Long idUtente, String idIride, Long idProgetto,
			Long idSoggettoRappresentante) throws UnrecoverableException {
		String prf = "[RinunciaDAOImpl::findRappresentantiLegali]";
		LOG.info(prf + " BEGIN");
		
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		
		
		//boolean isPrivatoCittadino = this.getIsBeneficiarioPrivatoCittadino(idProgetto);
		/*
		if(isPrivatoCittadino) {
			RappresentanteLegaleDTO rappresentante = null;
			
			
			List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO> rappresentantiVO  = 
					new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO>();
			rappresentantiVO.add
			return beanUtil.transform(rappresentantiVO, RappresentanteLegaleDTO.class);
		}*/
		
		
		try {			
			
			List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO> rappresentantiVO = soggettoManager
					.findRappresentantiLegali(idProgetto,idSoggettoRappresentante);
			LOG.info(prf + " END");
			return beanUtil.transform(rappresentantiVO, RappresentanteLegaleDTO.class);
			
			
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile trovare Rappresentanti Legali", e);
		}  
		
	}

	@Override
	@Transactional
	public DichiarazioneDiRinunciaDTO inviaDichiarazioneDiRinuncia(Long idUtente, String idIride,
			DichiarazioneDiRinunciaDTO dichiarazione) throws ErogazioneException  {
		String prf = "[RinunciaDAOImpl::inviaDichiarazioneDiRinuncia]";
		LOG.info(prf + " BEGIN");
		try {
			
			LOG.info(prf + "!!!!!LOG!!!!!  --------->>>>>" + idUtente + " \n");
			System.out.println("!!!!!LOG!!!!!  --------->>>>>" + idUtente + " \n");
			
			String[] nameParameter = { "idUtente", "idIride",
					"dichiarazione", "dichiarazione.giorniRinuncia",
					"dichiarazione.idProgetto",
					"dichiarazione.idRappresentanteLegale",
					"dichiarazione.importoDaRestituire",
					"dichiarazione.motivoRinuncia" };
			Long idProgetto = dichiarazione.getIdProgetto();
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					idIride, dichiarazione,
					dichiarazione.getGiorniRinuncia(), idProgetto,
					dichiarazione.getIdRappresentanteLegale(),
					dichiarazione.getImportoDaRestituire(),
					dichiarazione.getMotivoRinuncia());

			if (dichiarazione.getUuidNodoIndex() != null) {
				LOG.warn(prf + " La dichiarazione di rinuncia appena inviata ha già un UUID di INDEX:" + dichiarazione.getUuidNodoIndex());
			}
			PbandiTRinunciaVO rinunciaVO = beanUtil.transform(dichiarazione, PbandiTRinunciaVO.class, new HashMap<String, String>() {
						{
							put("idProgetto", "idProgetto");
							put("dataRinuncia", "dtRinuncia");
							put("motivoRinuncia", "motivoRinuncia");
							put("giorniRinuncia", "giorniRinuncia");
							put("importoDaRestituire", "importoDaRestituire");
						}
					});
		
			LOG.info(prf + " BEGIN ############################ PbandiTSoggettoVO - INSERT ##############################\n");
			PbandiTSoggettoVO beneficiario = new PbandiTSoggettoVO();
			beneficiario.setIdSoggetto(new BigDecimal(progettoManager.getIdBeneficiario(idProgetto)));
			beneficiario = genericDAO.findSingleWhere(beneficiario);
			LOG.info(prf + " END ############################ PbandiTSoggettoVO - INSERT ##############################\n");
			
			LOG.info(prf + " BEGIN ############################ aggiornaStatoProgetto ##############################\n");
			PbandiTProgettoVO progetto = aggiornaStatoProgetto(idUtente ,idProgetto);
			LOG.info(prf + " END ############################ aggiornaStatoProgetto ##############################\n");
			
			LOG.info(prf + " BEGIN ############################ rinunciaVO - INSERT ##############################\n");
			rinunciaVO.setIdUtenteIns(new BigDecimal(idUtente));
			rinunciaVO = genericDAO.insert(rinunciaVO);
			LOG.info(prf + " END ############################ rinunciaVO - INSERT ##############################\n");
			/*
			 * VN: Cambio lo stato del conto economico master
			 * solo se il suo stato e' IPG
			 */
			
			LOG.info(prf + " BEGIN ############################ contoEconomicoManager.findContoEconomicoMaster ##############################\n");
			ContoEconomicoDTO ce = contoEconomicoManager.findContoEconomicoMaster(NumberUtil.toBigDecimal(idProgetto));
			if (Constants.STATO_CONTO_ECONOMICO_IN_PROPOSTA_PER_GESTIONE_OPERATIVA.equalsIgnoreCase(ce.getDescBreveStatoContoEconom())) {
				LOG.info(prf + " BEGIN ############################ contoEconomicoManager.aggiornaStatoContoEconomico ##############################\n");
				contoEconomicoManager.aggiornaStatoContoEconomico(NumberUtil.toLong(ce.getIdContoEconomico()), 
						Constants.STATO_CONTO_ECONOMICO_APPROVATO, NumberUtil.toBigDecimal(idUtente));
				LOG.info(prf + " END ############################ contoEconomicoManager.aggiornaStatoContoEconomico ##############################\n");
			}
			LOG.info(prf + " END ############################ contoEconomicoManager.findContoEconomicoMaster ##############################\n");
			
			String nomeFile = Constants.NOME_FILE_RINUNCIA_PREFIX + "_" + rinunciaVO.getIdRinuncia() + "_" + DateUtil.utilToSqlDate(DateUtil.getDataOdierna()) + ".pdf";

			LOG.info(prf + " BEGIN ############################  progettoManager.getProgetto ##############################\n");
			PbandiTProgettoVO progettoVO = progettoManager.getProgetto(idProgetto.longValue());
			LOG.info(prf + " progettoManager.getProgetto progettoVO="+progettoVO);
			LOG.info(prf + " END ############################  progettoManager.getProgetto ##############################\n");
			
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_PROGETTO, beanUtil.transform(progettoVO,it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ProgettoDTO.class));
			
			popolaTemplateManager.setTipoModello(PopolaTemplateManager.MODELLO_COMUNICAZIONE_DI_RINUNCIA);
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_ID_PROGETTO,  idProgetto.longValue());
			
			
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO rappresentanteLegaleDTO; 
			
			
			boolean isBeneficiarioPrivatoCittadino = this.getIsBeneficiarioPrivatoCittadino(idProgetto);
			
			//il beneficiario è un privato cittadino
			if(isBeneficiarioPrivatoCittadino) {
				
				Long idSoggettoBeneficiario =  dichiarazione.getIdRappresentanteLegale();
				
				// Recupera i dati del privato cittadino e lo mette nella variabile rappresenatnti legali
				rappresentanteLegaleDTO = getPrivatoCittadinoForRappresentanteLegale(idSoggettoBeneficiario);
				IndirizzoPrivatoCittadino indirizzoPrivatoCittadino =  getIndirizzoPrivatoCittadino(idSoggettoBeneficiario, idProgetto);
				if(indirizzoPrivatoCittadino != null) {
					rappresentanteLegaleDTO.setIndirizzoResidenza(indirizzoPrivatoCittadino.getDescIndirizzo());
					rappresentanteLegaleDTO.setCapResidenza(indirizzoPrivatoCittadino.getCap());
					rappresentanteLegaleDTO.setIdProvinciaResidenza(indirizzoPrivatoCittadino.getIdProvincia());
					rappresentanteLegaleDTO.setIdComuneResidenza(indirizzoPrivatoCittadino.getIdComune());
				}
				
			}
			//il beneficiario non è un privato cittadino
			else {
				
				List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO> rappresentantiLegali = soggettoManager.findRappresentantiLegali(idProgetto, 
						dichiarazione.getIdRappresentanteLegale());
				
				rappresentanteLegaleDTO = rappresentantiLegali.get(0);
				
			}
				
			
			
			if(dichiarazione.getIdDelegato()!=null){
				 LOG.info(prf +" il delegato non è NULL "+dichiarazione.getIdDelegato()+", lo metto al posto del rapp legale");
				 DelegatoVO delegatoVO = new DelegatoVO();
				 delegatoVO.setIdSoggetto(dichiarazione.getIdDelegato());
				 delegatoVO.setIdProgetto(idProgetto);
				 LOG.info(prf + " BEGIN ############################  DelegatoVO.findListWhere ##############################\n");
			     List<DelegatoVO> delegati = genericDAO.findListWhere(delegatoVO);
			     LOG.info(prf + " END ############################  DelegatoVO.findListWhere ##############################\n");
			     
			     if(delegati!=null && !delegati.isEmpty()){
			    	delegatoVO=delegati.get(0);
			     }
				 LOG.info(prf + " delegatoVO caricato" );
				 rappresentanteLegaleDTO = beanUtil.transform(delegatoVO, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO.class);
				 LOG.info(prf + "rappresentanteLegaleDTO=" + rappresentanteLegaleDTO);
			}
				
			
			
			
			
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_RAPPRESENTANTE_LEGALE, rappresentanteLegaleDTO);
			
			BeneficiarioProgettoVO filtroBeneficiario = new BeneficiarioProgettoVO();
			filtroBeneficiario.setIdProgetto(new BigDecimal(idProgetto));
			LOG.info(prf + " BEGIN ############################  (progettoManager.getIdBeneficiario ##############################\n");
			filtroBeneficiario.setIdSoggetto(new BigDecimal(progettoManager.getIdBeneficiario(idProgetto)));
			LOG.info(prf + " END ############################  (progettoManager.getIdBeneficiario ##############################\n");
			
			LOG.info(prf + " BEGIN ############################  (progettoManager.findBeneficiario ##############################\n");
			BeneficiarioProgettoVO beneficiarioVO = progettoManager.findBeneficiario(filtroBeneficiario);
			LOG.info(prf + " END ############################  (progettoManager.findBeneficiario ##############################\n");
			
			popolaTemplateManager.addChiave(popolaTemplateManager.CHIAVE_BENEFICIARIO, beanUtil.transform(beneficiarioVO, BeneficiarioDTO.class));
			
			SedeVO sedeIntervento = null;
			try {
				LOG.info(prf + " BEGIN ############################  (sedeManager.findSedeIntervento ##############################\n");
				 sedeIntervento = sedeManager.findSedeIntervento(idProgetto, beneficiarioVO.getIdSoggetto().longValue());
				 LOG.info(prf + " END ############################  (sedeManager.findSedeIntervento ##############################\n");
			} catch (Exception e) {
				DaoException dse = new DaoException("Errore durante la ricerca della sede di intervento");
				throw dse;
			}
			/* * Sede intervento */
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_SEDE_INTERVENTO,beanUtil.transform(sedeIntervento,SedeDTO.class));
				
			/* * Dichiarazione di rinuncia per report */
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.DichiarazioneDiRinunciaDTO rinuncia = beanUtil.transform(dichiarazione, 
					it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.DichiarazioneDiRinunciaDTO.class);
			rinuncia.setIdDichiarazione(beanUtil.transform(rinunciaVO.getIdRinuncia(), Long.class));
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_COMUNICAZIONE_DI_RINUNCIA,rinuncia);
						
			// 11/12/15 added footer 
			popolaTemplateManager.addChiave(PopolaTemplateManager.CHIAVE_NUMERO_DOC,""+rinunciaVO.getIdRinuncia());
		
			LOG.info(prf + " BEGIN ############################  popolaTemplateManager.popolaModello ##############################\n");
			Modello modello = popolaTemplateManager.popolaModello(idProgetto.longValue());
			LOG.info(prf + " END ############################  popolaTemplateManager.popolaModello ##############################\n");	
			
			LOG.info(prf + " BEGIN ############################ JasperFillManager.fillReport ##############################\n");
			long startFillReport=System.currentTimeMillis();
			JasperPrint jasperPrint = JasperFillManager.fillReport(modello.getMasterReport(), modello.getMasterParameters(),new JREmptyDataSource());
			LOG.info(prf + " END ########################\nJasperFillManager.fillReport eseguito in "+(System.currentTimeMillis()-startFillReport)+" ms\n");
			
			LOG.info(prf + " BEGIN ############################ JasperExportManager.exportReportToPdf ##############################\n");
			long startExport=System.currentTimeMillis();
			byte[] bytesPdf = JasperExportManager.exportReportToPdf(jasperPrint);
			LOG.info(prf + " END ########################\nJasperPrint esportato to pdf in "+(System.currentTimeMillis()-startExport)+" ms\n");
			
			// NEW DYNAMIC REPORT
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			String shaHex = null;
			if(bytesPdf!=null)
			  shaHex = DigestUtils.shaHex(bytesPdf);
			
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = documentiFSManager
					.salvaInfoNodoIndexSuDb(nomeFile, beanUtil.transform(rinunciaVO.getIdRinuncia(),BigDecimal.class), dichiarazione.getIdRappresentanteLegale(),
							dichiarazione.getIdDelegato(), beanUtil.transform(rinunciaVO.getIdProgetto(),BigDecimal.class), Constants.TIPO_DOCUMENTO_INDEX_COMUNICAZIONE_DI_RINUNCIA,
							PbandiTRinunciaVO.class, null, null, idUtente, shaHex);
			
			// Nome univoco con cui il file verrà salvato su File System.
			String newNameFileSystem = pbandiTDocumentoIndexVO.getNomeFile();
			newNameFileSystem = newNameFileSystem.replaceFirst("\\.", "_"+pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue()+".");
			
			// Aggiorno la t_documento_index.
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO tempVO = new it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO();
			tempVO.setIdDocumentoIndex(pbandiTDocumentoIndexVO.getIdDocumentoIndex());
			tempVO.setNomeDocumento(newNameFileSystem);
			genericDAO.update(tempVO);
			
			dichiarazione.setUuidNodoIndex(null);
			dichiarazione.setIdDocumentoIndex(pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue());
			dichiarazione.setFileName(nomeFile);
			
			//salva al nuovo filesystem		
			LOG.info(prf + " BEGIN ############################ salva al nuovo filesystem	 ##############################\n");
			Boolean isUploaded = false;
			Boolean dirEsiste = fileApiServiceImpl.dirExists(Constants.TIPO_DOCUMENTO_INDEX_COMUNICAZIONE_DI_RINUNCIA, true);
			if(dirEsiste) {
				LOG.info(prf + "dirEsiste: "+ dirEsiste);
				InputStream inputStream = new ByteArrayInputStream(bytesPdf);
				isUploaded = fileApiServiceImpl.uploadFile(inputStream, newNameFileSystem, Constants.TIPO_DOCUMENTO_INDEX_COMUNICAZIONE_DI_RINUNCIA);
			} else {
				//Se la cartella non viene trovata, deve prima inserirla sotto \PBAN.
				FileApiServiceImpl fileApi = new FileApiServiceImpl(Constants.DIR_PBAN);
				InputStream inputStream = new ByteArrayInputStream(bytesPdf);
				isUploaded = fileApi.uploadFile(inputStream, newNameFileSystem, Constants.TIPO_DOCUMENTO_INDEX_COMUNICAZIONE_DI_RINUNCIA);
			}
			LOG.info(prf + "isUploaded: "+ isUploaded);
			if(!isUploaded) {
				throw new ErogazioneException("Impossibile salvare report nel filesystem");
			}
			LOG.info(prf + " END ############################ salva al nuovo filesystem	 ##############################\n");		
			
			LOG.warn(prf + " ############################ NEOFLUX ENDATTIVITA COMUNIC_RINUNCIA ##############################\n");
			neofluxBusiness.endAttivita(idUtente, idIride, idProgetto,Task.COMUNIC_RINUNCIA);
			LOG.warn(prf + "############################ NEOFLUX UNLOCK COMUNIC_RINUNCIA ##############################\n\n\n\n");
//							${data_invio_rinuncia}	DATA_INVIO_RINUNCIA
			List<MetaDataVO>metaDatas=new ArrayList<MetaDataVO>();
			String descrBreveTemplateNotifica=Notification.NOTIFICA_COMUNICAZIONE_RINUNCIA;
			MetaDataVO metadata1=new MetaDataVO(); 
			metadata1.setNome(Notification.DATA_INVIO_RINUNCIA);
			metadata1.setValore( DateUtil.getDate(new Date()));
			metaDatas.add(metadata1);
	
			LOG.info(prf + " calling genericDAO.callProcedure().putNotificationMetadata for COMUNIC_RINUNCIA....");
			genericDAO.callProcedure().putNotificationMetadata(metaDatas);
			
			LOG.info(prf + " calling genericDAO.callProcedure().sendNotificationMessage for COMUNIC_RINUNCIA....");
			genericDAO.callProcedure().sendNotificationMessage(BigDecimal.valueOf(idProgetto),descrBreveTemplateNotifica,Notification.ISTRUTTORE,idUtente);
			LOG.info(prf + " genericDAO.callProcedure().sendNotificationMessage for COMUNIC_RINUNCIA OK");
			
		} catch (Exception e) {
			LOG.error(prf + " Errore: " +e.getMessage(), e);
			throw new ErogazioneException(Constants.ERRORE_SERVER);
		} 
		LOG.info(prf + " END");
		return dichiarazione;
	}
	
	private it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO getPrivatoCittadinoForRappresentanteLegale(Long idSoggetto) throws DaoException {

		String prf = "[DichiarazioneDiSpesaDAOImpl::getPrivatoCittadinoForRappresentanteLegale] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idSoggetto = " + idSoggetto + " ; ");
		
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato");
		}		
			
		it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO rappresentanteLegaleDTO = null;
				
		try {	
			
			/*
			String sql 	= "select 														\r\n"
						+ "ptpf.id_soggetto,											\r\n"
						+ "ptpf.id_persona_fisica,										\r\n"
						+ "ptpf.cognome,												\r\n"
						+ "ptpf.nome,													\r\n"
						+ "ptpf.dt_nascita as data_nascita,								\r\n"
						+ "ptpf.id_comune_italiano_nascita,								\r\n"
						+ "ptpf.id_comune_estero_nascita,								\r\n"
						+ "pts.codice_fiscale_soggetto 									\r\n"
						+ "from pbandi_t_persona_fisica ptpf							\r\n"
						+ "join pbandi_t_soggetto pts									\r\n"
						+ "on ptpf.id_soggetto = pts.id_soggetto						\r\n"
						+ "where ptpf.id_soggetto = ?		 							\r\n"
						+ "and ROWNUM <=1												";	
			*/
			
			String sql 	= "select 														\r\n"
						+ "ptpf.id_soggetto,											\r\n"
						+ "ptpf.id_persona_fisica,										\r\n"
						+ "ptpf.cognome,												\r\n"
						+ "ptpf.nome,													\r\n"
						+ "ptpf.dt_nascita as data_nascita,								\r\n"
						+ "ptpf.id_comune_italiano_nascita,								\r\n"
						+ "ptpf.id_comune_estero_nascita,								\r\n"
						+ "pts.codice_fiscale_soggetto,									\r\n"
						+ "case						 									\r\n"
						+ "	when ptpf.id_comune_italiano_nascita is null				\r\n"
						+ "		then pdce.desc_comune_estero							\r\n"
						+ "	else pdc.desc_comune										\r\n"
						+ "	end as luogo_nascita										\r\n"
						+ "from pbandi_t_persona_fisica ptpf							\r\n"
						+ "join pbandi_t_soggetto pts									\r\n"
						+ "	on ptpf.id_soggetto = pts.id_soggetto						\r\n"
						+ "left join pbandi_d_comune pdc								\r\n"
						+ "	on ptpf.id_comune_italiano_nascita = pdc.id_comune			\r\n"
						+ "left join pbandi_d_comune_estero pdce						\r\n"
						+ "	on ptpf.id_comune_estero_nascita = pdce.id_comune_estero	\r\n"
						+ "where ptpf.id_soggetto = ? 									\r\n"
						+ "and ROWNUM <=1													";	
									
			Object[] par = {idSoggetto.toString()};	  			 
			
			logger.info(prf + "\n"+sql);	
			rappresentanteLegaleDTO = (it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO) 
					getJdbcTemplate().queryForObject(sql, par, new BeanRowMapper(it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO.class));	
			
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nella ricerca del rappresentante legale nel caso del privato cittadino: ", e);
			throw new DaoException(" ERRORE nella ricerca del rappresentante legale nel caso del privato cittadino: ");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return rappresentanteLegaleDTO;
		
	}

	private IndirizzoPrivatoCittadino getIndirizzoPrivatoCittadino(Long idSoggetto, Long idProgetto) throws DaoException {

		String prf = "[DichiarazioneDiSpesaDAOImpl::getIndirizzoPrivatoCittadino] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idSoggetto = " + idSoggetto + ", " +  "idProgetto = " + idProgetto + " ; ");
		
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato");
		}	
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}				
		
		IndirizzoPrivatoCittadino indirizzoPrivatoCittadino = null;
				
		try {				
			
			String sql 	= "select 														\r\n"
						+ "pti.desc_indirizzo, 											\r\n"
						+ "pti.cap,														\r\n"
						+ "pti.id_provincia,											\r\n"
						+ "pti.id_comune												\r\n"
						+ "from pbandi_r_soggetto_progetto prsp							\r\n"
						+ "join pbandi_t_indirizzo pti									\r\n"
						+ "	on prsp.id_indirizzo_persona_fisica = pti.id_indirizzo		\r\n"
						+ "where prsp.id_progetto = ?									\r\n"
						+ "and prsp.id_soggetto = ?											";	
									
			Object[] par = {idProgetto.toString(), idSoggetto.toString()};	  	 	 
			
			logger.info(prf + "\n"+sql);	
			indirizzoPrivatoCittadino = (IndirizzoPrivatoCittadino) getJdbcTemplate().queryForObject(sql, par, new BeanRowMapper(IndirizzoPrivatoCittadino.class));	
			
			 
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nella ricerca dell' indirizzo privato cittadino: ", e);
			throw new DaoException(" ERRORE nella ricerca dell' indirizzo privato cittadino: ");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return indirizzoPrivatoCittadino;
		
	}

	

	
	
	
	private PbandiTProgettoVO aggiornaStatoProgetto(Long idUtente,Long idProgetto) throws Exception {
		String prf = "[RinunciaDAOImpl::aggiornaStatoProgetto]";
		LOG.info(prf + " START");
		PbandiTProgettoVO progetto = new PbandiTProgettoVO();
		progetto.setIdProgetto(new BigDecimal(idProgetto));
		progetto.setIdStatoProgetto(decodificheManager.decodeDescBreve(
				PbandiDStatoProgettoVO.class, Constants.STATO_PROGETTO_RINUNCIA_BENEFICIARIO));
		progetto.setIdUtenteAgg(new BigDecimal(idUtente));
		genericDAO.update(progetto);

		progetto = new PbandiTProgettoVO();
		progetto.setIdProgetto(new BigDecimal(idProgetto));
		progetto = genericDAO.findSingleWhere(progetto);
		LOG.info(prf + " END");
		return progetto;
	}

	@Override
	@Transactional
	public EsitoScaricaDichiarazioneDiRinuncia scaricaDichiarazioneDiRinuncia(Long idUtente, String idIride,
			Long idDocumentoIndex) throws ErogazioneException {
		String prf = "[RinunciaDAOImpl::scaricaDichiarazioneDiRinuncia]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride",
					"uuidNodoIndex" };
			LOG.info(prf + "idDocumentoIndex: "+idDocumentoIndex);
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idDocumentoIndex);
			/*
			 * Recupero le informazioni relative al documento index selezionato
			 */
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO vo = new it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO();
			vo.setIdDocumentoIndex(new BigDecimal(idDocumentoIndex));
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO documentoIndexVO = genericDAO
					.findSingleWhere(vo);

			LOG.info(prf + " Nome file da scaricare: "+ documentoIndexVO.getNoteDocumentoIndex() + " "+ documentoIndexVO.getNomeFile());
			//scarica file da file storage
			java.io.File file =   fileApiServiceImpl.downloadFile(documentoIndexVO.getNoteDocumentoIndex(), Constants.TIPO_DOCUMENTO_INDEX_COMUNICAZIONE_DI_RINUNCIA);
			byte[] fileBytes = FileUtils.readFileToByteArray(file);
			
			EsitoScaricaDichiarazioneDiRinuncia esito = new EsitoScaricaDichiarazioneDiRinuncia();
			esito.setNomeFile(documentoIndexVO.getNomeFile());
			esito.setEsito(true);
			esito.setPdfBytes(fileBytes);
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			ErogazioneException ce = new ErogazioneException(
					"Errore scarico file da Index", e);
			ce.initCause(e);
			throw ce;
		}  
	}

	@Override
	// Salva il pdf firmato della dichiarazione di spesa.
	@Transactional(rollbackFor = {Exception.class})
	public Boolean salvaFileFirmaAutografa(MultipartFormDataInput multipartFormData, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[RinunciaDAOImpl::salvaFileFirmato] ";
		LOG.info(prf + "BEGIN");
		
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (multipartFormData == null) {
			throw new InvalidParameterException("multipartFormData non valorizzato");
		}
				
		Long idDocumentoIndex = multipartFormData.getFormDataPart("idDocumentoIndex", Long.class, null);
		LOG.info(prf+"input idDocumentoIndex = "+idDocumentoIndex);
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}
		
		// Legge il file firmato dal multipart.		
		Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
		List<InputPart> listInputPart = map.get("fileFirmaAutografa");
		
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
		
		FileDTO file = FileUtil.leggiFileDaMultipart(listInputPart, null);
		if (file == null) {
			throw new InvalidParameterException("File non valorizzato");
		}
		
		LOG.info(prf+"input idDocumentoIndex = "+idDocumentoIndex);
		LOG.info(prf+"input nomeFile = "+file.getNomeFile());
		LOG.info(prf+"input bytes.length = "+file.getBytes().length);
		
		
		Boolean esito = true;
		try {
			
			esito = documentoManager.salvaFileFirmaAutografa(file.getBytes(), file.getNomeFile(), idDocumentoIndex, idUtente);
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nel salvataggio del file firmato: ", e);
			throw new DaoException(" ERRORE nel salvataggio del file firmato.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	public Boolean salvaFileFirmato(MultipartFormDataInput multipartFormData, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[RinunciaDAOImpl::salvaFileFirmato] ";
		LOG.info(prf + "BEGIN");
		
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (multipartFormData == null) {
			throw new InvalidParameterException("multipartFormData non valorizzato");
		}
				
		Long idDocumentoIndex = multipartFormData.getFormDataPart("idDocumentoIndex", Long.class, null);
		LOG.info(prf+"input idDocumentoIndex = "+idDocumentoIndex);
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}
		
		// Legge il file firmato dal multipart.		
		Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
		List<InputPart> listInputPart = map.get("fileFirmato");
		
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
		
		FileDTO file = FileUtil.leggiFileDaMultipart(listInputPart, null);
		if (file == null) {
			throw new InvalidParameterException("File non valorizzato");
		}
		
		LOG.info(prf+"input idDocumentoIndex = "+idDocumentoIndex);
		LOG.info(prf+"input nomeFile = "+file.getNomeFile());
		LOG.info(prf+"input bytes.length = "+file.getBytes().length);
		
		
		Boolean esito = true;
		try {
			
			esito = documentoManager.salvaFileFirmato(file.getBytes(), file.getNomeFile(), idDocumentoIndex, idUtente);
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nel salvataggio del file firmato: ", e);
			throw new DaoException(" ERRORE nel salvataggio del file firmato.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
		
	
	@Override
	// Verifica la firma digitale del file in input e lo marca temporalmente.
	// NOTA: questo metodo viene chiamato in modo asincrono da Angular.
	// Ex DigitalSignAction.upload()
	public Boolean verificaFirmaDigitale(Long idDocumentoIndex, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[RinunciaDAOImpl::verificaFirmaDigitale] ";
		LOG.info(prf + "BEGIN");
		
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}
				
		LOG.info(prf+"input idDocumentoIndex = "+idDocumentoIndex);
		Boolean esito = true;
		try {
			
			FileDTO fileFirmato = documentoManager.leggiFileFirmato(idDocumentoIndex); 
			if (fileFirmato == null) {
				LOG.error(prf+" Lettura del file firmato fallita.");
				throw new Exception("Lettura del file firmato fallita.");
			}
			
			it.csi.pbandi.pbweberog.pbandisrv.dto.digitalsign.SignedFileDTO signedFileDTO = new it.csi.pbandi.pbweberog.pbandisrv.dto.digitalsign.SignedFileDTO();
			signedFileDTO.setBytes(fileFirmato.getBytes());
			signedFileDTO.setFileName(fileFirmato.getNomeFile());
			signedFileDTO.setIdDocIndex(idDocumentoIndex);
			
			LOG.info(prf+"Chiamo digitalSignSrv.signFile");
			digitalSignBusinessImpl.signFile(idUtente, idIride, signedFileDTO);
			LOG.info(prf+"Terminato digitalSignSrv.signFile");
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nella verifica del file firmato: ", e);
			throw new DaoException(" ERRORE nella verifica del file firmato.");
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	@Override
	// Setta il campo PBANDI_T_DOCUMENTO_INDEX.FLAG_FIRMA_CARTACEA a "S".
	// Ex DigitalSignBusinessImpl.setFlagCartaceo()
	public Boolean salvaInvioCartaceo(Boolean invioCartaceo, Long idDocumentoIndex, Long idUtente, String idIride) 
			throws InvalidParameterException, Exception {
		String prf = "[RinunciaDAOImpl::salvaInvioCartaceo] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "invioCartaceo = "+invioCartaceo+"; idDocumentoIndex = "+idDocumentoIndex+"; idUtente = "+idUtente);
		
		if (invioCartaceo == null) {
			throw new InvalidParameterException("invioCartaceo non valorizzato.");
		}
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		Boolean esito = true;
		try {			
			
			String flagFirmaCartacea = (invioCartaceo != null && invioCartaceo) ? "S" : "N"; 
			this.valorizzaFlagFirmaCartacea(idDocumentoIndex, flagFirmaCartacea, idUtente);

		} catch (Exception e) {
			LOG.error(prf+" ERRORE durante la valorizzazione del Flag Firma Cartacea: ", e);
			esito = false;
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	// Setta il campo PBANDI_T_DOCUMENTO_INDEX.FLAG_FIRMA_CARTACEA con il valore in input.
	private Boolean valorizzaFlagFirmaCartacea(Long idDocumentoIndex, String flag, Long idUtente) 
			throws InvalidParameterException, Exception {
		String prf = "[RinunciaDAOImpl::valorizzaFlagFirmaCartacea] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "+idDocumentoIndex = "+idDocumentoIndex+"; flag = "+flag+"; idUtente = "+idUtente);
		
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		Boolean esito = true;
		try {			
			
			String sql = "UPDATE PBANDI_T_DOCUMENTO_INDEX SET FLAG_FIRMA_CARTACEA = '"+flag+"', DT_AGGIORNAMENTO_INDEX = SYSDATE, ID_UTENTE_AGG = "+idUtente+" where ID_DOCUMENTO_INDEX = "+idDocumentoIndex;
			logger.info(prf + "\n"+sql);					
			getJdbcTemplate().update(sql);

		} catch (Exception e) {
			LOG.error(prf+" ERRORE durante la valorizzazione del Flag Firma Cartacea: ", e);
			esito = false;
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}

	@Override
	public Boolean getIsRegolaApplicabileForProgetto(Long idProgetto, String codiceRegola, Long idUtente, String idIride) throws SystemException, UnrecoverableException, CSIException{
		
		String prf = "[RinunciaDAOImpl::getIsRegolaApplicabileForProgetto] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "+idProgetto = "+idProgetto+"; codiceRegola = "+codiceRegola+"; idUtente = "+idUtente);
		
		if(idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		if(codiceRegola == null) {
			throw new InvalidParameterException("codiceRegola non valorizzato.");
		}
		
		if(idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		
		Boolean result = digitalSignBusinessImpl.isRegolaApplicabileForProgetto(idUtente, idIride, idProgetto, codiceRegola);
		
		/*
		Long idBandoLinea = findIdBandoLineaByIdProgetto(idProgetto);		
		
		if(idProgetto == null) {
			throw new CSIException("idBandoLinea ricavato da idProgetto non valorizzato.");
		}		
		digitalSignBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride, idBandoLinea, codiceRegola);
		 */
		
		
		return result;
	}
	
	private Long findIdBandoLineaByIdProgetto(Long idProgetto) {

		String prf = "[DichiarazioneDiSpesaDAOImpl::findIdBandoLineaByIdProgetto] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + ";" );
		
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		
		
		Long idBando = null;
		
		try {			
			
			String sql =  "select prbli.progr_bando_linea_intervento as value							\r\n"
						+ "from pbandi_t_progetto ptp													\r\n"
						+ "join pbandi_t_domanda ptd													\r\n"
						+ "on ptp.id_domanda = ptd.id_domanda											\r\n"
						+ "join pbandi_r_bando_linea_intervent prbli									\r\n"
						+ "on ptd.progr_bando_linea_intervento = prbli.progr_bando_linea_intervento		\r\n"
						+ "where id_progetto = ?													";	
			
			
			
			Object[] par = {idProgetto.toString()};
			
			//getJdbcTemplate().queryForObject(sql, param, new BeanRowMapper(Integer.class));
			
			logger.info(prf + "\n"+sql);	
			idBando = ((LongValue)getJdbcTemplate().queryForObject(sql, par, new BeanRowMapper(LongValue.class))).getValue();							
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE nella ricerca del bando linea intervento : ", e);
			//throw new DaoException(" ERRORE nella ricerca dei dati beneficirio:");
			return null;
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return idBando;
	}
	
	
}
