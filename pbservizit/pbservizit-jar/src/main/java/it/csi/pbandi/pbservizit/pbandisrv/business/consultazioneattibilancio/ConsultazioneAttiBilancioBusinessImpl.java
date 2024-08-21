/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.consultazioneattibilancio;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.BilancioManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.AttoDiLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.DettaglioAttoDiLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.DettaglioLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.RiepilogoAttoDiLiquidazionePerProgetto;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.InputLeggiStatoElaborazioneDocDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.consultazioneattibilancio.ConsultazioneAttiBilancioException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.liquidazionebilancio.LiquidazioneBilancioException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DatiBeneficiarioBilancioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DettaglioAttoLiquidazioneListaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DettaglioAttoLiquidazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ModalitaDiAgevolazioneContoEconomicoPerBilancioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.columnfilter.DettaglioAttoLiquidazioneFilterByBeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.columnfilter.DettaglioAttoLiquidazioneFilterByCausaleErogazionePerRiepilogoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.columnfilter.DettaglioAttoLiquidazioneFilterByProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.columnfilter.DettaglioAttoLiquidazioneFilterOutImpegnoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDAliquotaRitenutaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDSettoreEnteVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoFondoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRLiquidazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAttoLiquidazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTBeneficiarioBilancioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTCapitoloVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDatiPagamentoAttoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEnteGiuridicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEstremiBancariVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTImpegnoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTMandatoQuietanzatoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPersonaFisicaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.BilancioDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.BilancioDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.OutputConsultaAttoLiquidazione;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.consultazioneattibilancio.ConsultazioneAttiBilancioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.ErrorConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ConsultazioneAttiBilancioBusinessImpl extends BusinessImpl
		implements ConsultazioneAttiBilancioSrv {

	@Autowired
	private BilancioDAOImpl bilancioDAOImpl;
	@Autowired
	private BilancioManager bilancioManager;
	@Autowired
	private ContoEconomicoManager contoEconomicoManager;
	@Autowired
	private GenericDAO genericDAO;

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public BilancioManager getBilancioManager() {
		return bilancioManager;
	}

	public void setBilancioManager(BilancioManager bilancioManager) {
		this.bilancioManager = bilancioManager;
	}

	public ContoEconomicoManager getContoEconomicoManager() {
		return contoEconomicoManager;
	}

	public void setContoEconomicoManager(
			ContoEconomicoManager contoEconomicoManager) {
		this.contoEconomicoManager = contoEconomicoManager;
	}

//	public BilancioDAO getBilancioDAO() {
//		return bilancioDAO;
//	}
//
//	public void setBilancioDAO(BilancioDAO bilancioDAO) {
//		this.bilancioDAO = bilancioDAO;
//	}

	public CodiceDescrizioneDTO[] getBeneficiariConAttiDiLiquidazione(
			Long idUtente, String identitaDigitale) throws CSIException,
			SystemException, UnrecoverableException,
			ConsultazioneAttiBilancioException {

		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale);

		List<DettaglioAttoLiquidazioneFilterByBeneficiarioVO> beneficiari;
		try {
			DettaglioAttoLiquidazioneVO al = new DettaglioAttoLiquidazioneVO();
			al.setAscendentOrder("denominazioneBeneficiarioBil");
			beneficiari = genericDAO.findListWhereDistinct(
					Condition.filterBy(al),
					DettaglioAttoLiquidazioneFilterByBeneficiarioVO.class);
		} catch (Exception e) {
			throw new UnrecoverableException(e.getMessage(), e);
		} finally {
		}

		return beanUtil.transform(beneficiari, CodiceDescrizioneDTO.class,
				new HashMap<String, String>() {
					{
						put("idSoggetto", "codice");
						put("denominazioneBeneficiarioBil", "descrizione");
					}
				});
	}

	public CodiceDescrizioneDTO[] getProgettiConAttiDiLiquidazione(
			Long idUtente, String identitaDigitale) throws CSIException,
			SystemException, UnrecoverableException,
			ConsultazioneAttiBilancioException {

		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale);
		List<DettaglioAttoLiquidazioneFilterByProgettoVO> progetti = new ArrayList<DettaglioAttoLiquidazioneFilterByProgettoVO>();

		try {

			DettaglioAttoLiquidazioneVO al = new DettaglioAttoLiquidazioneVO();
			al.setAscendentOrder("codiceVisualizzatoProgetto");
			progetti = genericDAO.findListWhereDistinct(
					new AndCondition<DettaglioAttoLiquidazioneVO>(Condition
							.filterBy(al), Condition.not(Condition.isFieldNull(
							DettaglioAttoLiquidazioneVO.class, "idProgetto"))),
					DettaglioAttoLiquidazioneFilterByProgettoVO.class);
		} catch (Exception e) {
			throw new UnrecoverableException(e.getMessage(), e);
		}  

		return beanUtil.transform(progetti, CodiceDescrizioneDTO.class,
				new HashMap<String, String>() {
					{
						put("idProgetto", "codice");
						put("codiceVisualizzatoProgetto", "descrizione");
					}
				});
	}

	public AttoDiLiquidazioneDTO[] findAttiDiLiquidazione(Long idUtente,
			String identitaDigitale, AttoDiLiquidazioneDTO filtro)
			throws CSIException, SystemException, UnrecoverableException,
			ConsultazioneAttiBilancioException {

		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale);
		List<DettaglioAttoLiquidazioneListaVO> atti = new ArrayList<DettaglioAttoLiquidazioneListaVO>();

		try {
			atti = bilancioManager.findAttiDiLiquidazioneManager(filtro);
			// Se ci sono atti in stato "IN LAVORAZIONE", riesegue la ricerca degli atti.
			if (gestioneAttiInElaborazione2(atti, idUtente)) {
				atti = bilancioManager.findAttiDiLiquidazioneManager(filtro);
			}
		} catch (Exception e) {
			throw new UnrecoverableException(e.getMessage(), e);
		}  

		return beanUtil.transform(atti, AttoDiLiquidazioneDTO.class);
	}

	public DettaglioAttoDiLiquidazioneDTO getDettaglioAttoDiLiquidazione(
			Long idUtente, String identitaDigitale, Long idAttoDiLiquidazione)
			throws CSIException, SystemException, UnrecoverableException,
			ConsultazioneAttiBilancioException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idAttoDiLiquidazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idAttoDiLiquidazione);

		DettaglioAttoDiLiquidazioneDTO dettaglio = new DettaglioAttoDiLiquidazioneDTO();
		try {
			// 1. chiama il bilancio. 
			//BigDecimal idProcedura = bilancioDAO.consultaAttoLiquidazione(new BigDecimal(idAttoDiLiquidazione),idUtente);
			OutputConsultaAttoLiquidazione out = bilancioDAOImpl.consultaAttoLiquidazione(new BigDecimal(idAttoDiLiquidazione),idUtente);
			
			BigDecimal idProcedura = null;
			if (out != null) {
				idProcedura = out.getIdAttiLiquidazioneDt();
			}
			logger.info("getDettaglioAttoDiLiquidazione(): IdAttiLiquidazioneDt = "+idProcedura);
				
			// 2. richiama il PL/SQL.
			boolean result = false;
			if(idProceduraValido(idProcedura)) {
				result = genericDAO.callProcedure().caricaAttoDiLiquidazione(idProcedura, true);
			}
			logger.info("getDettaglioAttoDiLiquidazione(): result PL/SQL = "+result);

			// 3. ottiene il dettaglio.
			dettaglio = getDettaglioAttoLiquidazionePersistito(idAttoDiLiquidazione);
			if (isNull(dettaglio.getDtAggiornamentoAtto()))
					dettaglio.setDtAggiornamentoAtto(dettaglio.getDtInserimentoAtto());
			
			dettaglio.setEsito(idProceduraValido(idProcedura) && result);
			if (out != null) {
				dettaglio.setDescStatoDocumentoContabilia(out.getDescStatoDocContabilia());	
			}

		} catch (Exception e) {
			throw new UnrecoverableException(e.getMessage(), e);
		} 

		return dettaglio;
	}
	
	private boolean idProceduraValido(BigDecimal idProcedura) {
		return (idProcedura != null && idProcedura.intValue() > 0);
	}

	public DettaglioAttoDiLiquidazioneDTO getDettaglioAttoDiLiquidazionePersistito(
			Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			ConsultazioneAttiBilancioException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto);

		DettaglioAttoDiLiquidazioneDTO dettaglio = new DettaglioAttoDiLiquidazioneDTO();
		try {
			PbandiTAttoLiquidazioneVO filtroAttoLiquidazioneVO = new PbandiTAttoLiquidazioneVO();
			filtroAttoLiquidazioneVO.setIdProgetto(beanUtil.transform(
					idProgetto, BigDecimal.class));
			filtroAttoLiquidazioneVO.setIdStatoAtto(bilancioManager
					.statoAttoBozza().getIdStatoAtto());
			List<PbandiTAttoLiquidazioneVO> attiLiquidazioneInBozza = genericDAO
					.findListWhere(filtroAttoLiquidazioneVO);
			if (attiLiquidazioneInBozza.size() > 0
					&& attiLiquidazioneInBozza.size() == 1) {
				PbandiTAttoLiquidazioneVO attoLiquidazione = attiLiquidazioneInBozza
						.get(0);
				dettaglio = getDettaglioAttoLiquidazionePersistito(beanUtil
						.transform(attoLiquidazione.getIdAttoLiquidazione(),
								Long.class));
			} else if (attiLiquidazioneInBozza.size() > 1) {
				String message = ErrorConstants.ERRORE_CONSULTAZIONE_ATTI_PIU_ATTI_IN_STATO_BOZZA;
				logger.error(message);
				throw new LiquidazioneBilancioException(message);
			}
		} catch (Exception e) {
			logger.error("Errore nel recupero della Liquidazione", e);
			throw new LiquidazioneBilancioException(e.getMessage());
		}
		return dettaglio;
	}

	private DettaglioAttoDiLiquidazioneDTO getDettaglioAttoLiquidazionePersistito(
			Long idAttoDiLiquidazione) {
		DecodificaDTO tr;
		DettaglioAttoDiLiquidazioneDTO dettaglio;
		DettaglioAttoLiquidazioneVO filter = new DettaglioAttoLiquidazioneVO();
		filter.setIdAttoLiquidazione(new BigDecimal(idAttoDiLiquidazione));
		DettaglioAttoLiquidazioneFilterOutImpegnoVO attoLiquidazioneVO = genericDAO
				.findSingleWhereDistinct(filter,
						DettaglioAttoLiquidazioneFilterOutImpegnoVO.class);
		dettaglio = beanUtil.transform(attoLiquidazioneVO,
				DettaglioAttoDiLiquidazioneDTO.class,
				new HashMap<String, String>() {
					{
						put("annoAtto", "annoAtto");
						put("descAtto", "descAtto");
						put("importoAtto", "importoTotaleAtto");
						put("descStatoAtto", "descStatoAtto");
						put("dtAggiornamento", "dtAggiornamentoAtto");
						put("dtAggiornamentoBilancio",
								"dtAggiornamentoBilancio");
						put("dtAnnulamentoAtto", "dtAnnullamentoAtto");
						put("dtCompletamentoAtto", "dtCompletamentoAtto");
						put("dtEmissioneAtto", "dtEmissioneAtto");
						put("dtInserimento", "dtInserimentoAtto");
						put("dtRicezioneAtto", "dtRicezioneAtto");
						put("dtRichiestaModifica", "dtRichiestaModifica");
						put("dtRifiutoRagioneria", "dtRifiutoRagioneria");
						put("dtScadenzaAtto", "dtScadenzaAtto");
						//put("importoAtto", "importoTotaleAtto"); 
						put("noteAtto", "noteAtto");
						put("numeroAtto", "numeroAtto");
						put("testoRichiestaModifica", "testoRichiestaModifica");
						// Dati integrativi
						put("flagAllegatiFatture", "DIFattura");
						put("flagAllegatiEstrattoProv", "DIEstrattoCopiaProvvedimento");
						put("flagAllegatiDocGiustificat", "DIDocumentazioneGiustificativa");
						put("flagAllegatiDichiarazione", "DIDichiarazione");
						put("testoAllegatiAltro", "DIAltro");
						
						put("nomeLiquidatore", "DIFunzionarioLiquidatore");
						put("numeroTelefonoLiquidatore", "DINumeroTelefono");
						put("nomeDirigenteLiquidatore", "DIDirigente");
						
						// Ritenute IRPEF e INPS
						put("descTipoSoggRitenuta", "DRTipoSoggetto");
						put("impNonSoggettoRitenuta", "DRSommeNonSoggette");
						put("dtInpsDal", "DRPeriodoAttivitaDal");
						put("dtInpsAl", "DRPeriodoAttivitaAL");
						
						put("numeroDocumentoSpesa", "numeroDocumentoSpesa");
						put("descStatoDocumento", "descStatoDocumento");						
					}
				});
	 	
		// Dati Integrativi
		if (attoLiquidazioneVO.getIdSettoreEnte() != null){
			PbandiDSettoreEnteVO settEnteVO = new PbandiDSettoreEnteVO();
			settEnteVO.setIdSettoreEnte(attoLiquidazioneVO.getIdSettoreEnte());
			settEnteVO = genericDAO.findSingleOrNoneWhere(settEnteVO);
			
			if (settEnteVO != null) {
				dettaglio.setDISettoreAppartenenza(settEnteVO.getDescSettore());
				tr = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.ENTE_DI_COMPETENZA, settEnteVO.getIdEnteCompetenza());
				if (tr != null)
					dettaglio.setDIEnteCompetenza(tr.getDescrizione());
			}
		}
		
 		tr = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.MODALITA_AGEVOLAZIONE, attoLiquidazioneVO.getIdModalitaAgevolazione());
		if (tr != null)
			dettaglio.setDIModalitaAgevolazione(tr.getDescrizione());
		
 		tr = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.CAUSALE_EROGAZIONE, attoLiquidazioneVO.getIdCausaleErogazione());
		if (tr != null)
			dettaglio.setDICausaleErogazione(tr.getDescrizione());
		
		if (attoLiquidazioneVO.getIdBeneficiarioBilancio() != null){
			PbandiTBeneficiarioBilancioVO benefVO = new PbandiTBeneficiarioBilancioVO();
			benefVO.setIdBeneficiarioBilancio(attoLiquidazioneVO.getIdBeneficiarioBilancio());
			benefVO = genericDAO.findSingleOrNoneWhere(benefVO);
			
			if (benefVO != null) {
				dettaglio.setDIQuietanzante(benefVO.getQuietanzante());
				dettaglio.setDICodiceFiscale(benefVO.getCodFiscQuietanzante());
			}
		}
		
		if (attoLiquidazioneVO.getIdDatiPagamentoAtto() != null){
			PbandiTDatiPagamentoAttoVO pagAttoVO = new PbandiTDatiPagamentoAttoVO();
			pagAttoVO.setIdDatiPagamentoAtto(attoLiquidazioneVO.getIdDatiPagamentoAtto());
			pagAttoVO = genericDAO.findSingleOrNoneWhere(pagAttoVO);
			
			if (pagAttoVO != null) {
				dettaglio.setDIDataInserimento(pagAttoVO.getDtInserimento());
				dettaglio.setDIDataAggiornamento(pagAttoVO.getDtAggiornamento());
				
				tr = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.MODALITA_EROGAZIONE, pagAttoVO.getIdModalitaErogazione());
				if (tr != null)
					dettaglio.setDIModalitaErogazione(tr.getDescrizione());
				
				if (!isNull(pagAttoVO.getIdEstremiBancari())){
					PbandiTEstremiBancariVO estrBancVO = new PbandiTEstremiBancariVO();
					estrBancVO.setIdEstremiBancari(pagAttoVO.getIdEstremiBancari());
					estrBancVO = genericDAO.findSingleOrNoneWhere(estrBancVO);
					
					if (estrBancVO != null) {
						dettaglio.setDIEstremiBancari(estrBancVO.getIban());
					}
				}
			}
		}
		
		if (attoLiquidazioneVO.getIdBeneficBilancioCedente() != null){
			PbandiTBeneficiarioBilancioVO benBilVO = new PbandiTBeneficiarioBilancioVO();
			benBilVO.setIdBeneficiarioBilancio(attoLiquidazioneVO.getIdBeneficBilancioCedente());
			benBilVO = genericDAO.findSingleOrNoneWhere(benBilVO);
			
			if (benBilVO != null) {
				if (benBilVO.getIdPersonaFisica() != null) {
					PbandiTPersonaFisicaVO persFisVO = new PbandiTPersonaFisicaVO();
					persFisVO.setIdPersonaFisica(benBilVO.getIdPersonaFisica());
					persFisVO = genericDAO.findSingleOrNoneWhere(persFisVO);
					if (persFisVO != null) {
						dettaglio.setDIBeneficiarioCedente(persFisVO.getCognome() + " " + persFisVO.getNome());
					}
				}
				if (benBilVO.getIdEnteGiuridico() != null) {
					PbandiTEnteGiuridicoVO enteGiurVO = new PbandiTEnteGiuridicoVO();
					enteGiurVO.setIdEnteGiuridico(benBilVO.getIdEnteGiuridico());
					enteGiurVO = genericDAO.findSingleOrNoneWhere(enteGiurVO);
					if (enteGiurVO != null) {
						dettaglio.setDIBeneficiarioCedente(enteGiurVO.getDenominazioneEnteGiuridico());
					}
				}
			}
		}
		
		if (attoLiquidazioneVO.getIdBeneficBilancioCeduto() != null){
			PbandiTBeneficiarioBilancioVO benBilVO = new PbandiTBeneficiarioBilancioVO();
			benBilVO.setIdBeneficiarioBilancio(attoLiquidazioneVO.getIdBeneficBilancioCeduto());
			benBilVO = genericDAO.findSingleOrNoneWhere(benBilVO);
			
			if (benBilVO != null) {
				if (benBilVO.getIdPersonaFisica() != null) {
					PbandiTPersonaFisicaVO persFisVO = new PbandiTPersonaFisicaVO();
					persFisVO.setIdPersonaFisica(benBilVO.getIdPersonaFisica());
					persFisVO = genericDAO.findSingleOrNoneWhere(persFisVO);
					if (persFisVO != null) {
						dettaglio.setDIBeneficiarioCeduto(persFisVO.getCognome() + " " + persFisVO.getNome());
					}
				}
				if (benBilVO.getIdEnteGiuridico() != null) {
					PbandiTEnteGiuridicoVO enteGiurVO = new PbandiTEnteGiuridicoVO();
					enteGiurVO.setIdEnteGiuridico(benBilVO.getIdEnteGiuridico());
					enteGiurVO = genericDAO.findSingleOrNoneWhere(enteGiurVO);
					if (enteGiurVO != null) {
						dettaglio.setDIBeneficiarioCeduto(enteGiurVO.getDenominazioneEnteGiuridico());
					}
				}
			}
		}
		
		if (attoLiquidazioneVO.getIdDatiPagamAttoBenCeduto() != null){
			PbandiTDatiPagamentoAttoVO pagAttoCedVO = new PbandiTDatiPagamentoAttoVO();
			pagAttoCedVO.setIdDatiPagamentoAtto(attoLiquidazioneVO.getIdDatiPagamAttoBenCeduto());
			pagAttoCedVO = genericDAO.findSingleOrNoneWhere(pagAttoCedVO);
			
			if (pagAttoCedVO != null) {
				dettaglio.setDIDataInserimentoBeneficiario(pagAttoCedVO.getDtInserimento());
				dettaglio.setDIDataAggiornamentoBeneficiario(pagAttoCedVO.getDtAggiornamento());
				
				tr = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.MODALITA_EROGAZIONE, pagAttoCedVO.getIdModalitaErogazione());
				if (tr != null)
					dettaglio.setDIModalitaErogazioneBeneficiario(tr.getDescrizione());
				
				PbandiTEstremiBancariVO estrBancCedVO = new PbandiTEstremiBancariVO();
				estrBancCedVO.setIdEstremiBancari(pagAttoCedVO.getIdEstremiBancari());
				estrBancCedVO = genericDAO.findSingleOrNoneWhere(estrBancCedVO);
				
				if (estrBancCedVO != null) {
					dettaglio.setDIEstremiBancariBeneficiario(estrBancCedVO.getIban());
				}
			}
		}
		
		// Dati Beneficiario
		DatiBeneficiarioBilancioVO datiBeneficiarioVO = new DatiBeneficiarioBilancioVO();
		datiBeneficiarioVO.setIdAttoLiquidazione(filter.getIdAttoLiquidazione());
		datiBeneficiarioVO = genericDAO.findSingleWhere(datiBeneficiarioVO);
		beanUtil.valueCopy(datiBeneficiarioVO, dettaglio, new HashMap<String,String>() {{
			put("codiceBeneficiarioBilancio", "DBCodiceBeneficiarioBilancio");
			put("codiceFiscaleSoggetto", "DBCodiceFiscale");
			put("partitaIva","DBPartitaIVA");
			put("denominazione", "DBDenominazione");
			put("denominazioneRagioneSociale", "DBRagioneSocialeAgg");
			put("descIndirizzo", "DBIndirizzo");
			put("descComune", "DBComune");
			put("provincia", "DBProvincia");
			put("codiceFiscaleSoggetto", "DBCodiceFiscale");
			put("partitaIva", "DBPartitaIVA");
			put("dtInserimento", "DBDataInserimento");
			put("dtAggiornamento", "DBDataAggiornamento");
			put("fax", "DBFax");
			put("email", "DBEmail");
			put("telefono", "DBTelefono");
			put("descIndirizzoSecondaria", "DBIndirizzoSecondaria");
			put("descComuneSecondaria", "DBComuneSecondaria");
			put("provinciaSecondaria", "DBProvinciaSecondaria");
		}});
		
		// Ritenute IRPEF E INPS
		if (attoLiquidazioneVO.getIdAliquotaRitenuta() != null){
			PbandiDAliquotaRitenutaVO aliquotaRitenutaVO = new PbandiDAliquotaRitenutaVO();
			aliquotaRitenutaVO.setIdAliquotaRitenuta(attoLiquidazioneVO.getIdAliquotaRitenuta());
			List<PbandiDAliquotaRitenutaVO> lstAliq = genericDAO.findListWhere(aliquotaRitenutaVO);
			if (! isEmpty(lstAliq)) {
				PbandiDAliquotaRitenutaVO aliq = lstAliq.get(0);
				Long idTipoRit = aliq.getIdTipoRitenuta().longValue();
				tr = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.TIPO_RITENUTA, idTipoRit);
				dettaglio.setDRTipoRitenuta(tr.getDescrizione());
				dettaglio.setDRAliquotaRitenuta(aliq.getDescAliquota());
			}
		}
		
 		tr = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.SITUAZIONE_INPS, attoLiquidazioneVO.getIdSituazioneInps());
		if (tr != null)
			dettaglio.setDRSituazioneINPS(tr.getDescrizione());
		
		tr = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.TIPO_ALTRA_CASSA_PREV, attoLiquidazioneVO.getIdTipoAltraCassaPrev());
		if (tr != null)
			dettaglio.setDRINPSaltraCassa(tr.getDescrizione());
		
		tr = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.ATTIVITA_INPS, attoLiquidazioneVO.getIdAttivitaInps());
		if (tr != null)
			dettaglio.setDRAttivitaINPS(tr.getDescrizione());
		
		tr = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.ALTRA_CASSA_PREVIDENZ, attoLiquidazioneVO.getIdAltraCassaPrevidenz());
		if (tr != null)
			dettaglio.setDRAltraCassaPrevid(tr.getDescrizione());
		
		tr = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.RISCHIO_INAIL, attoLiquidazioneVO.getIdRischioInail());
		if (tr != null)
			dettaglio.setDRRischioINAIL(tr.getDescrizione());
		
		// Dati Liquidazioni
		PbandiRLiquidazioneVO liquidazioneFilterVO = new PbandiRLiquidazioneVO();
		liquidazioneFilterVO.setIdAttoLiquidazione(new BigDecimal(
				idAttoDiLiquidazione));

		ArrayList<DettaglioLiquidazioneDTO> liquidazioni = new ArrayList<DettaglioLiquidazioneDTO>();
		List<PbandiRLiquidazioneVO> liquidazioniVO = genericDAO
				.findListWhere(liquidazioneFilterVO);
		for (PbandiRLiquidazioneVO liquidazioneVO : liquidazioniVO) {
			DettaglioLiquidazioneDTO tmpDettaglioLiquidazione = beanUtil
					.transform(liquidazioneVO, DettaglioLiquidazioneDTO.class,
							new HashMap<String, String>() {
								{
									put("numBilancioLiquidazione",
											"numBilancioLiquidazione");
									put("importoLiquidato", "importoLiquidato");
									put("cupLiquidazione", "cupLiquidazione");
									put("cigLiquidazione", "cigLiquidazione");
									put("dtInserimento", "dtInserimento");
									put("dtAggiornamento", "dtAggiornamento");
									put("dtAggBilancioLiquidaz",
											"dtAggBilancioLiquidazione");
									put("idLiquidazione", "idLiquidazione");
									put("annoEsercizio", "annoLiquidazione");
								}
							});
			PbandiTImpegnoVO impegnoVO = genericDAO.where(
					Condition
							.filterBy(
									new PbandiTImpegnoVO(liquidazioneVO
											.getIdImpegno()))
							.and(Condition.validOnly(PbandiTImpegnoVO.class)))
					.selectSingle();
			beanUtil.valueCopy(impegnoVO, tmpDettaglioLiquidazione,
					new HashMap<String, String>() {
						{
							put("annoImpegno", "annoImpegno");
							put("numeroImpegno", "numeroImpegno");
							put("annoEsercizio", "annoEsercizio");
							put("cupImpegno", "cupImpegno");
							put("cigImpegno", "cigImpegno");
						//	put("dtAggiornamentoBilancio",
							//		"dtAggBilancioLiquidaz");
							put("totaleLiquidatoImpegno",
									"totaleLiquidatoImpegno");
							put("totaleQuietanzatoImpegno",
									"totaleQuietanzatoImpegno");
						}
					});
			PbandiTMandatoQuietanzatoVO mandatoVO = new PbandiTMandatoQuietanzatoVO();
			mandatoVO.setProgrLiquidazione(liquidazioneVO
					.getProgrLiquidazione());
			try {
				mandatoVO = genericDAO
						.where(Condition
								.filterBy(mandatoVO)
								.and(Condition
										.validOnly(PbandiTMandatoQuietanzatoVO.class)))
						.selectSingle();
				beanUtil.valueCopy(mandatoVO, tmpDettaglioLiquidazione,
						new HashMap<String, String>() {
							{
								put("cupMandato", "cupMandato");
								put("cigMandato", "cigMandato");
								put("dtInserimento", "dtInserimentoMandato");
								put("dtQuietanza", "dtQuietanzaMandato");
								put("importoMandatoLordo",
										"importoMandatoLordo");
								put("importoMandatoNetto",
										"importoMandatoNetto");
								put("importoQuietanzato", "importoQuietanzato");
								put("importoRitenute", "importoRitenute");
								put("numeroMandato", "numeroMandato");
							}
						});
			} catch (RecordNotFoundException e) {
				logger.warn("Non esiste un mandato quietanzato per la liquidazione "
						+ liquidazioneVO.getProgrLiquidazione()
						+ " dell'atto "
						+ idAttoDiLiquidazione);
			}
			PbandiTCapitoloVO capitoloVO = genericDAO
					.findSingleOrNoneWhere(new PbandiTCapitoloVO(impegnoVO
							.getIdCapitolo()));
			if (capitoloVO != null){
				tmpDettaglioLiquidazione.setNumeroCapitolo(""
						+ capitoloVO.getNumeroCapitolo());
				tmpDettaglioLiquidazione.setDescTipoFondo(decodificheManager
						.findDescrizioneById(PbandiDTipoFondoVO.class,
								capitoloVO.getIdTipoFondo()));
			}

			tmpDettaglioLiquidazione.setIdLiquidazione(beanUtil.transform(
					liquidazioneVO.getProgrLiquidazione(), Long.class));
			
			tr = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.STATO_LIQUIDAZIONE, liquidazioneVO.getIdStatoLiquidazione());
			if (tr != null)
				tmpDettaglioLiquidazione.setStatoLiquidazione(tr.getDescrizione());
			
			liquidazioni.add(tmpDettaglioLiquidazione);
		}
		dettaglio.setLiquidazioni(liquidazioni
				.toArray(new DettaglioLiquidazioneDTO[liquidazioni.size()]));
		return dettaglio;
	}

	public RiepilogoAttoDiLiquidazionePerProgetto[] findRiepilogoAttiLiquidazione(
			Long idUtente, String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			ConsultazioneAttiBilancioException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idProgetto);

		List<RiepilogoAttoDiLiquidazionePerProgetto> riepilogo = new ArrayList<RiepilogoAttoDiLiquidazionePerProgetto>();
		try {
			ContoEconomicoDTO contoEconomicoMaster = contoEconomicoManager
					.findContoEconomicoMasterPotato(new BigDecimal(idProgetto));
			ModalitaDiAgevolazioneContoEconomicoPerBilancioVO modalitaAgevolazioneFilter = new ModalitaDiAgevolazioneContoEconomicoPerBilancioVO();
			modalitaAgevolazioneFilter.setIdContoEconomico(contoEconomicoMaster
					.getIdContoEconomico());
			List<ModalitaDiAgevolazioneContoEconomicoPerBilancioVO> modalitaAgevolazioneList = genericDAO
					.where(Condition
							.filterBy(modalitaAgevolazioneFilter)
							.and(Condition
									.validOnly(ModalitaDiAgevolazioneContoEconomicoPerBilancioVO.class)))
					.orderBy("descModalitaAgevolazione").select();
			Double ultimoImportoAgevolatoTotale = 0d;
			Double importoLiquidatoTotale = 0d;
			for (ModalitaDiAgevolazioneContoEconomicoPerBilancioVO modalitaAgevolazione : modalitaAgevolazioneList) {
				RiepilogoAttoDiLiquidazionePerProgetto tempMA = new RiepilogoAttoDiLiquidazionePerProgetto();
				tempMA.setModalitaAgevolazione(modalitaAgevolazione
						.getDescModalitaAgevolazione());
				double ultimoImportoAgevolato = (modalitaAgevolazione
						.getImportoFinanziamentoBanca() == null) ? 0d
						: NumberUtil.getDoubleValue(modalitaAgevolazione
								.getImportoFinanziamentoBanca());
				tempMA.setUltimoImportoAgevolato(ultimoImportoAgevolato);
				riepilogo.add(tempMA);
				Double importoLiquidatoParziale = 0d;
				DettaglioAttoLiquidazioneVO attoFilter = new DettaglioAttoLiquidazioneVO();
				attoFilter.setDescendentOrder("dtEmissioneAtto");
				attoFilter.setIdModalitaAgevolazione(modalitaAgevolazione
						.getIdModalitaAgevolazione());
				attoFilter.setIdProgetto(new BigDecimal(idProgetto));
				List<DettaglioAttoLiquidazioneFilterByCausaleErogazionePerRiepilogoVO> attoList = genericDAO
						.findListWhereDistinct(
								Condition.filterBy(attoFilter),
								DettaglioAttoLiquidazioneFilterByCausaleErogazionePerRiepilogoVO.class);
				// Se ci sono atti in stato "IN LAVORAZIONE", riesegue la ricerca degli atti.
				if (gestioneAttiInElaborazione(attoList,idUtente)) {
					attoList = genericDAO.findListWhereDistinct(
							Condition.filterBy(attoFilter),
							DettaglioAttoLiquidazioneFilterByCausaleErogazionePerRiepilogoVO.class);
				}
				for (DettaglioAttoLiquidazioneFilterByCausaleErogazionePerRiepilogoVO atto : attoList) {
					RiepilogoAttoDiLiquidazionePerProgetto tempCE = new RiepilogoAttoDiLiquidazionePerProgetto();
					tempCE.setCausaleErogazione(atto.getDescCausale());
					tempCE.setIdAttoDiLiquidazione(beanUtil.transform(
							atto.getIdAttoLiquidazione(), Long.class));
					double importoLiquidato = NumberUtil.getDoubleValue(atto
							.getImportoAtto());
					importoLiquidatoParziale = NumberUtil.sum(
							importoLiquidatoParziale, importoLiquidato);
					tempCE.setImportoLiquidato(importoLiquidato);
					tempCE.setEstremiAtto(atto.getEstremiAtto());										
					riepilogo.add(tempCE);
				}
				riepilogo.get(riepilogo.indexOf(tempMA)).setImportoLiquidato(
						importoLiquidatoParziale);
				importoLiquidatoTotale = NumberUtil.sum(
						importoLiquidatoParziale, importoLiquidatoTotale);
				ultimoImportoAgevolatoTotale = NumberUtil.sum(
						ultimoImportoAgevolato, ultimoImportoAgevolatoTotale);
			}
			RiepilogoAttoDiLiquidazionePerProgetto totale = new RiepilogoAttoDiLiquidazionePerProgetto();
			totale.setModalitaAgevolazione("Totale");
			totale.setUltimoImportoAgevolato(ultimoImportoAgevolatoTotale);
			totale.setImportoLiquidato(importoLiquidatoTotale);
			riepilogo.add(totale);
		} catch (Exception e) {
			logger.error(
					"Errore nel riepilogo degli atti di liquidazione per il progetto "
							+ idProgetto, e);
			throw new LiquidazioneBilancioException(e.getMessage());
		}
		return riepilogo
				.toArray(new RiepilogoAttoDiLiquidazionePerProgetto[riepilogo
						.size()]);
	}
	
	// Se uno degli atti � in stato "IN LAVORAZIONE", si chiama Contabilia per vedere se lo ha elaborato.
	private boolean gestioneAttiInElaborazione
	  (List<DettaglioAttoLiquidazioneFilterByCausaleErogazionePerRiepilogoVO> attoList,
	   Long idUtente) {
		boolean attoInElaborazione = false;
		for (DettaglioAttoLiquidazioneFilterByCausaleErogazionePerRiepilogoVO atto : attoList) {
			if (Constants.DESC_BREVE_STATO_ATTO_IN_LAVORAZIONE.equalsIgnoreCase(atto.getDescBreveStatoAtto())) {
				attoInElaborazione = true;				
				// Aggiorna lo stato dell'atto.
				InputLeggiStatoElaborazioneDocDTO input = new InputLeggiStatoElaborazioneDocDTO();
				input.setIdAttoLiquidazione(atto.getIdAttoLiquidazione().longValue());
				bilancioDAOImpl.leggiStatoElaborazioneDoc(input, idUtente);
			}
		}
		return attoInElaborazione;			
	}
	
	// Se uno degli atti � in stato "IN LAVORAZIONE", si chiama Contabilia per vedere se lo ha elaborato.
	private boolean gestioneAttiInElaborazione2
	  (List<DettaglioAttoLiquidazioneListaVO> attoList, Long idUtente) {
		boolean attoInElaborazione = false;
		for (DettaglioAttoLiquidazioneListaVO atto : attoList) {
			if (Constants.DESC_BREVE_STATO_ATTO_IN_LAVORAZIONE.equalsIgnoreCase(atto.getDescBreveStatoAtto())) {
				attoInElaborazione = true;				
				// Aggiorna lo stato dell'atto.
				InputLeggiStatoElaborazioneDocDTO input = new InputLeggiStatoElaborazioneDocDTO();
				input.setIdAttoLiquidazione(atto.getIdAttoLiquidazione().longValue());
				bilancioDAOImpl.leggiStatoElaborazioneDoc(input, idUtente);
			}
		}
		return attoInElaborazione;			
	}	

}


