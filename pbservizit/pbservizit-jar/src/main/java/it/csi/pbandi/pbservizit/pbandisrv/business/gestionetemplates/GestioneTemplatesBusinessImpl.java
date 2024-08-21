/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.gestionetemplates;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.util.DatiMessaggio;
import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DynamicTemplateManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.TemplateDbManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionetemplates.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionetemplates.BandoLinea;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionetemplates.EsitoAnteprimaTemplateDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionetemplates.TemplateDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.Modello;
import it.csi.pbandi.pbservizit.pbandisrv.exception.gestionetemplates.GestioneTemplatesException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.TemplateJasperVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTTemplateVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.MailDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.vo.MailVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionetemplates.GestioneTemplatesSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.ErrorConstants;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class GestioneTemplatesBusinessImpl extends BusinessImpl implements GestioneTemplatesSrv {

	private DynamicTemplateManager dynamicTemplateManager;
	@Autowired
	private MailDAO mailDAO;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private TemplateDbManager templateDbManager;

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public MailDAO getMailDAO() {
		return mailDAO;
	}

	public void setMailDAO(MailDAO mailDAO) {
		this.mailDAO = mailDAO;
	}

	public TemplateDbManager getTemplateDbManager() {
		return templateDbManager;
	}

	public void setTemplateDbManager(TemplateDbManager templateDbManager) {
		this.templateDbManager = templateDbManager;
	}

	public void setDynamicTemplateManager(
			DynamicTemplateManager dynamicTemplateManager) {
		this.dynamicTemplateManager = dynamicTemplateManager;
	}

	public DynamicTemplateManager getDynamicTemplateManager() {
		return dynamicTemplateManager;
	}

	public String generaTemplate(Long idUtente, String identitaDigitale,
			Long idBandoLinea, Long idTipoDocumento, DatiMessaggio datiMsg)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneTemplatesException {
		String[] nameParameter = { "idUtente", "identitaDigitale"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale);
		
		nameParameter =new String []{"idBandoLinea", "idTipoDocumento"};
		ValidatorInput.verifyAtLeastOneNotNullValue(nameParameter, idBandoLinea,idTipoDocumento);

		MailVO mail = new MailVO();
		
		
		if (isNull(idTipoDocumento) || isNull(idBandoLinea) ) {
			try {
				if(isNull(idTipoDocumento)){
				
					generaAllTemplatePerBando(idUtente, idBandoLinea,
							mail);
				}else if(isNull(idBandoLinea)){
					 generaSingleTemplateForAllBandi(idUtente, idTipoDocumento,
							mail);
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}else{
			generaSingleTemplateForBando(idUtente, idBandoLinea,
					idTipoDocumento, mail);

		}
		mail.setFromAddress(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.MAIL_ADDRESS_NO_REPLY_CSI_IT);
		mail.setToAddresses(mailDAO.getTemplateEmailAddresses());
		
		mailDAO.send(mail);

		return null;
	}

	private void generaSingleTemplateForBando(Long idUtente, Long idBandoLinea,
			Long idTipoDocumento, MailVO mail) {
		String message = "";
		long startTime = DateUtil.getTimeStamp();
		try {
			templateDbManager.saveTemplate(idUtente, idTipoDocumento,
					idBandoLinea);
			message = message.concat("La generazione del template ha avuto esito POSITIVO in "
					+ ((DateUtil.getTimeStamp() - startTime) / 1000)
					+ " secondi.");
		} catch (Exception e) {
			message = message
					.concat("La generazione del template ha avuto esito NEGATIVO con eccezione " + e.getMessage());
			mail.setPriority(MailVO.Priority.HIGH);
		}
		mail.setContent(message);
		mail.setSubject("[PBANDI] Notifica termine generazione template "
			+ genericDAO.findSingleWhere(
					new PbandiCTipoDocumentoIndexVO(new BigDecimal(
							idTipoDocumento))).getDescTipoDocIndex()
			+ " per il bando-linea "
			+ genericDAO.findSingleWhere(
					new PbandiRBandoLineaInterventVO(new BigDecimal(
							idBandoLinea))).getNomeBandoLinea() + ".");
	}

	private void generaSingleTemplateForAllBandi(Long idUtente,
			Long idTipoDocumento, MailVO mail) {
		String message = "";
		
		String descTipoDocIndex = genericDAO.findSingleWhere(
				new PbandiCTipoDocumentoIndexVO(new BigDecimal(
						idTipoDocumento))).getDescTipoDocIndex();
		
		PbandiRBandoLineaInterventVO filterVO = new PbandiRBandoLineaInterventVO();

		filterVO.setAscendentOrder("nomeBandoLinea");

		List<PbandiRBandoLineaInterventVO> bandiLinea = genericDAO
				.findListWhere(filterVO);
		for (PbandiRBandoLineaInterventVO bandoLinea : bandiLinea) {
			
			try {
				long startTime = DateUtil.getTimeStamp();
				templateDbManager.saveTemplate(idUtente,idTipoDocumento,
						bandoLinea.getProgrBandoLineaIntervento().longValue());
				message = message.concat("\n\nLa generazione del template "+descTipoDocIndex+
						" per il bando "+bandoLinea.getProgrBandoLineaIntervento().longValue()
						+" ha avuto esito POSITIVO in "
						+ ((DateUtil.getTimeStamp() - startTime) / 1000)
						+ " secondi.");
			} catch (Exception e) {
				message = message
						.concat("\n\nLa generazione del template ha avuto esito NEGATIVO con eccezione " + e.getMessage());
				mail.setPriority(MailVO.Priority.HIGH);
			}
		 	
		}
		mail.setContent(message);
		mail.setSubject("[PBANDI] Notifica termine generazione dei template su tutti i bandi per il tipo documento "
			+ descTipoDocIndex +".");
	}

	private void generaAllTemplatePerBando(Long idUtente, Long idBandoLinea,
			MailVO mail) throws Exception {
		
		String message="";
		DecodificaDTO[] decodifiche =decodificheManager
			.findDecodifiche(GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_INDEX);

		for (DecodificaDTO decodificaDTO : decodifiche) {
		
			try {
				long startTime = DateUtil.getTimeStamp();
				templateDbManager.saveTemplate(idUtente, decodificaDTO.getId(),
						idBandoLinea);
				message = message.concat("\n\nLa generazione del template "+decodificaDTO.getDescrizione()+" ha avuto esito POSITIVO in "
						+ ((DateUtil.getTimeStamp() - startTime) / 1000)
						+ " secondi.");
			} catch (Exception e) {
				message = message
						.concat("\n\nLa generazione del template ha avuto esito NEGATIVO con eccezione " + e.getMessage());
				mail.setPriority(MailVO.Priority.HIGH);
				logger.info("idUtente="+ idUtente+", idBandoLinea=" + idBandoLinea + ", idTipoDocumentoIndex="+decodificaDTO.getId());
				logger.error("La generazione del template ha avuto esito NEGATIVO con eccezione " + e.getMessage());
			}
		 	
		}
		mail.setSubject("[PBANDI] Notifica termine generazione dei template per il bando-linea "
			+ genericDAO.findSingleWhere(
					new PbandiRBandoLineaInterventVO(new BigDecimal(
							idBandoLinea))).getNomeBandoLinea() + ".");
		mail.setContent(message);
	}

	public BandoLinea[] findBandoLinea(Long idUtente, String identitaDigitale)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneTemplatesException {
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale);

		PbandiRBandoLineaInterventVO filterVO = new PbandiRBandoLineaInterventVO();

//		filterVO.setAscendentOrder("nomeBandoLinea");
			filterVO.setAscendentOrder("progrBandoLineaIntervento"); // richiesta panarace 27 giu 2016

		List<PbandiRBandoLineaInterventVO> result = genericDAO
				.findListWhere(filterVO);
		return beanUtil.transform(result, BandoLinea.class);
	}

	public EsitoAnteprimaTemplateDTO anteprimaTemplate(Long idUtente,
			String identitaDigitale, Long progrBandolinea,
			Long idTipoDocumentoIndex) throws CSIException, SystemException,
			UnrecoverableException, GestioneTemplatesException {

		String[] nameParameter = { "idUtente", "identitaDigitale",
				"progrBandolinea", "idTipoDocumentoIndex" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandolinea, idTipoDocumentoIndex);

			EsitoAnteprimaTemplateDTO esito = new EsitoAnteprimaTemplateDTO();
			esito.setEsito(Boolean.FALSE);
			MessaggioDTO msg = new MessaggioDTO();

			PbandiCTipoDocumentoIndexVO docIndexVO = new PbandiCTipoDocumentoIndexVO();
			docIndexVO.setIdTipoDocumentoIndex(beanUtil.transform(
					idTipoDocumentoIndex, BigDecimal.class));
			docIndexVO = genericDAO.findSingleWhere(docIndexVO);

			try {
				esito.setBytes(dynamicTemplateManager.anteprimaDesignTemplate(
						beanUtil.transform(idTipoDocumentoIndex,
								BigDecimal.class), beanUtil.transform(
								progrBandolinea, BigDecimal.class),
						new HashMap<String, Object>()));
				String nomeFile = "anteprima_"
						+ docIndexVO.getDescTipoDocIndex() + "_"
						+ progrBandolinea + ".pdf";
				esito.setNomeFile(nomeFile);
				esito.setEsito(Boolean.TRUE);

			} catch (Exception e) {
				logger.error(
						"Errore durante la fase di creazione dell' anteprima.",
						e);
				msg.setKey(ErrorConstants.ERRORE_GESTIONE_TEMPLATE_ANTEPRIMA_IMPOSSIBILE);
				msg.setParams(new String[] { docIndexVO.getDescTipoDocIndex() });
				esito.setMsg(msg);
			}

			return esito;

	}

	public TemplateDTO[] findModelli(Long idUtente, String identitaDigitale,
			Long progrBandolinea, Long idTipoDocumentoIndex)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneTemplatesException {
		String[] nameParameter = { "idUtente", "identitaDigitale" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale);

			DecodificaDTO tipoTemplateMaster = decodificheManager
					.findDecodifica(GestioneDatiDiDominioSrv.TIPO_TEMPLATE,
							Constants.TIPO_TEMPLATE_MASTER);

			TemplateJasperVO filterVO = new TemplateJasperVO();
			filterVO.setProgrBandolineaIntervento(NumberUtil
					.toBigDecimal(progrBandolinea));
			filterVO.setIdTipoDocumentoIndex(NumberUtil
					.toBigDecimal(idTipoDocumentoIndex));
			filterVO.setIdTipoTemplate(NumberUtil
					.toBigDecimal(tipoTemplateMaster.getId()));

			List<TemplateJasperVO> result = genericDAO.findListWhere(filterVO);

			return beanUtil.transform(result, TemplateDTO.class);

	}

	public EsitoAnteprimaTemplateDTO visualizzaTemplate(Long idUtente,
			String identitaDigitale, Long idTemplate) throws CSIException,
			SystemException, UnrecoverableException, GestioneTemplatesException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idTemplate" };

		EsitoAnteprimaTemplateDTO esito = new EsitoAnteprimaTemplateDTO();
		esito.setEsito(Boolean.FALSE);
		MessaggioDTO msg = new MessaggioDTO();
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idTemplate);

			PbandiTTemplateVO filterVO = new PbandiTTemplateVO();
			filterVO.setIdTemplate(beanUtil.transform(idTemplate, BigDecimal.class));
			PbandiTTemplateVO template = null;
			Modello modello = null;
			String descTipoDocumentoIndex = null;
			try {
				template = genericDAO.findSingleWhere(Condition.filterBy(
						filterVO).and(
						Condition.validOnly(PbandiTTemplateVO.class)));
			} catch (RecordNotFoundException rnfe) {
				logger.error("Errore durante la ricerca del template.", rnfe);
				msg.setKey(ErrorConstants.ERRORE_GESTIONE_TEMPLATE_VISUALIZZA_MODELLO_IMPOSSIBILE);
				esito.setMsg(msg);
			}
			if (template != null) {
				PbandiCTipoDocumentoIndexVO docIndexVO = new PbandiCTipoDocumentoIndexVO();
				docIndexVO.setIdTipoDocumentoIndex(template.getIdTipoDocumentoIndex());
				docIndexVO = genericDAO.findSingleWhere(docIndexVO);
				descTipoDocumentoIndex = docIndexVO.getDescTipoDocIndex();
				try {
					modello = templateDbManager.recuperaModello(
							template.getIdTipoDocumentoIndex(),
							template.getProgrBandoLineaIntervento());
					
					logger.debug("[GestioneTemplatesBusinessImpl::visualizzaTemplate] modello="+modello);
					
				} catch (Exception e) {
					logger.error("Errore durante il recupero del modello.", e);
					msg.setKey(ErrorConstants.ERRORE_GESTIONE_TEMPLATE_VISUALIZZA_MODELLO_IMPOSSIBILE);
					msg.setParams(new String[] { descTipoDocumentoIndex });
					esito.setMsg(msg);
				}
			}
			if (modello != null) {
				JasperPrint jasperPrint;

				logger.debug("[GestioneTemplatesBusinessImpl::visualizzaTemplate] modello non nullo, invoco jasperreport");
				try {
					jasperPrint = JasperFillManager.fillReport(
							modello.getMasterReport(),
							modello.getMasterParameters(),
							new JREmptyDataSource());
					
				//	jasperPrint.setPageWidth(595);
				//	jasperPrint.setPageHeight(pageHeight);
					
					logger.debug("[GestioneTemplatesBusinessImpl::visualizzaTemplate] post JasperFillManager.fillReport");
					
					esito.setBytes(JasperExportManager.exportReportToPdf(jasperPrint));
					String nomeFile = "modello_" + descTipoDocumentoIndex + "_"
							+ template.getProgrBandoLineaIntervento() + ".pdf";
					esito.setNomeFile(nomeFile);

					esito.setEsito(Boolean.TRUE);
					
					logger.debug("[GestioneTemplatesBusinessImpl::visualizzaTemplate] esito popolato, nomeFile=["+nomeFile+"]");
					
				} catch (JRException e) {
					logger.error(
							"Errore durante il creazione del pdf del modello.",
							e);
					msg.setKey(ErrorConstants.ERRORE_GESTIONE_TEMPLATE_VISUALIZZA_MODELLO_IMPOSSIBILE);
					msg.setParams(new String[] { descTipoDocumentoIndex });
					esito.setMsg(msg);
				}
			}

		return esito;
	}

}
