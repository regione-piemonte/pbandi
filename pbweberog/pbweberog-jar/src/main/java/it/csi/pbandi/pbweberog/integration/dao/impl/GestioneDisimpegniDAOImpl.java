/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RevocaRecuperoManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.PeriodoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.EsitoSalvaRevocaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.MancatoRecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.QuotaParteVoceDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.RevocaModalitaAgevolazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.SaveDettaglioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.SaveDsDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.SaveIrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ErogazioneCausaleModalitaAgevolazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ModalitaDiAgevolazioneContoEconomicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.RevocaIrregolaritaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.recupero.RecuperoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.revoca.ModalitaAgevolazioneTotaleRevocheVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.revoca.QuotaParteVoceSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.revoca.RevocaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDCausaleDisimpegnoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDMancatoRecuperoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRDettPropCertRevocaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRDsDettRevocaIrregVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRRevocaIrregVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDettRevocaIrregVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPeriodoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRevocaVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.disimpegni.CausaleErogazioneDTO;
import it.csi.pbandi.pbweberog.dto.disimpegni.DettaglioRevocaIrregolaritaDTO;
import it.csi.pbandi.pbweberog.dto.disimpegni.DsDettaglioRevocaIrregolaritaDTO;
import it.csi.pbandi.pbweberog.dto.disimpegni.ModalitaAgevolazioneDTO;
import it.csi.pbandi.pbweberog.dto.disimpegni.RecuperoDTO;
import it.csi.pbandi.pbweberog.dto.disimpegni.RevocaDTO;
import it.csi.pbandi.pbweberog.dto.disimpegni.RevocaIrregolaritaDTO;
import it.csi.pbandi.pbweberog.dto.revoca.RigaRevocaItem;
import it.csi.pbandi.pbweberog.integration.dao.GestioneDisimpegniDAO;
import it.csi.pbandi.pbweberog.integration.request.RequestAssociaIrregolarita;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.ErrorMessages;
import it.csi.pbandi.pbweberog.util.MessageConstants;
import it.csi.pbandi.pbweberog.util.NumberUtil;

@Component
public class GestioneDisimpegniDAOImpl extends JdbcDaoSupport implements GestioneDisimpegniDAO {
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	ContoEconomicoManager contoEconomicoManager;
	
	@Autowired
	RevocaRecuperoManager revocaRecuperoManager;
	
	@Autowired
	DecodificheManager decodificheManager;
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	@Autowired
	public GestioneDisimpegniDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.genericDAO = new GenericDAO(dataSource);
	}
	

	@Override
	@Transactional
	public EsitoOperazioni checkPropostaCertificazione(Long idUtente, String idIride, Long idProgetto) throws FormalParameterException {
		String prf = "[GestioneDisimpegniDAOImpl::checkPropostaCertificazione]";
		LOG.debug(prf + " BEGIN");
		try {
			EsitoOperazioni esito = new EsitoOperazioni();
			String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idProgetto);
			/*
			 * Verifico se per il progetto esiste una proposta di certificazione
			 */
			if(revocaRecuperoManager.checkPropostaCertificazioneProgetto(idProgetto)) {
				esito.setEsito(true);
				esito.setMsg(MessageConstants.WRN_REVOCA_ESISTE_PROPOSTA_CERTIFICAZIONE);
			} else {
				//non esiste proposta certificazione per il progetto
				esito.setEsito(false);
			}
			return esito;
		}catch (Exception e) {			
			LOG.debug(prf + " " + e.getMessage());
			throw e;
		}
	}
	
	@Override
	@Transactional
	public it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO[] findRiepilogoRevoche(Long idUtente, String idIride, RevocaDTO filtro) throws Exception {
		String prf = "[GestioneDisimpegniDAOImpl::findRiepilogoRevoche]";
		LOG.debug(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale", "filtro", "filtro.idProgetto" };
		try {
			ValidatorInput.verifyNullValue(nameParameter, filtro, filtro.getIdProgetto());

			Long idProgetto = filtro.getIdProgetto();
			Long idModalitaAgevolazione = filtro.getIdModalitaAgevolazione();
			Long idRevoca = filtro.getIdRevoca();

			LOG.info(prf + " Riepilogo Revoche for idProgetto: "+idProgetto+"  ,idModalitaAgevolazione:"+idModalitaAgevolazione+"  ,idRevoca: "+idRevoca);
			
			/*  Carico le modalita' di agevolazioni del conto economico MASTER  legato al progetto */
			List<ModalitaDiAgevolazioneContoEconomicoVO> listModalitaContoEconomicoVO = null;
			try {
				listModalitaContoEconomicoVO = contoEconomicoManager.caricaModalitaAgevolazione(NumberUtil.toBigDecimal(idProgetto), Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
			} catch (ContoEconomicoNonTrovatoException e) {
				LOG.error(prf + " Nessun conto economico trovato", e);
				throw new Exception(Constants.ERRORE_SERVER);
			}
			List<it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO> result = new ArrayList<it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO>();

			if (listModalitaContoEconomicoVO != null) {
				for (ModalitaDiAgevolazioneContoEconomicoVO modContoEconomicoVO : listModalitaContoEconomicoVO) {
					it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO dto = beanUtil.transform(modContoEconomicoVO, it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO.class);
					dto.setIdProgetto(idProgetto);
					it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO dtoWithRevoche =null;
					if (idModalitaAgevolazione != null) {
						/*  Recupero i dati solo della modalita di agevolazione  che interessa */
						if (NumberUtil.compare(modContoEconomicoVO.getIdModalitaAgevolazione(), NumberUtil.toBigDecimal(idModalitaAgevolazione)) == 0) {
							dtoWithRevoche = findRiepilogoModalitaRevoca(dto, idRevoca);
						}
					} else {
						/*  Recupero i dati di tutte le modalita di agevolazioni */
						dtoWithRevoche = findRiepilogoModalitaRevoca(dto, idRevoca);
					}
					if (dtoWithRevoche != null)
						result.add(dtoWithRevoche);
					else{
						/*  Ricerco i recuperi associati alla modalita' di agevolazione del  progetto */
						Long idMod=idModalitaAgevolazione;
						if ( idMod== null) {
							idMod=modContoEconomicoVO.getIdModalitaAgevolazione().longValue();
						} 

						/*  Ricerco le causali */
						List<ErogazioneCausaleModalitaAgevolazioneVO> listCausaleModalitaAgevolazioniVO = revocaRecuperoManager.findCausaliModalitaAgevolazione(idProgetto, idMod);
						dto.setCausaliErogazioni(beanUtil.transform(listCausaleModalitaAgevolazioniVO, CausaleErogazioneDTO.class));

						/*  Ricerco i totali delle revoche e dei recuperi per la modalita' di  agevolazione */		
						result.add(dto);
					}
				}
			}
			// DETT_REVOCA_IRREG: Integro i dati estratti finora con:
			//  - gli eventuali dettagli delle irregolarita (PBANDI_T_DETT_REVOCA_IRREG)
			//  - le eventuali dichiarazioni di spesa associate ai dettagli (PBANDI_R_DS_DETT_REVOCA_IRREG)	
			result = this.integraIrregolarita(result);
			this.logIrregolarita(result);
			LOG.debug(prf + " END");
			return beanUtil.transform(result, it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO.class);
		} catch (Exception e) {			
			LOG.debug(prf + " " + e.getMessage());
			throw e;
		}
		
	}

	private void logIrregolarita(List<it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO> lista) {
		String prf = "[GestioneDisimpegniDAOImpl::logIrregolarita]";
		LOG.debug(prf + " START");
		String s = "";
		if (lista != null) {
			for (it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO m : lista) {
				if (m.getRevoche() != null) {
					for (it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO r : m.getRevoche()) {						
						s = s + "\nRevoca: idRevoca = "+r.getIdRevoca()+"; descModalitaAgevolaz = "+r.getDescModalitaAgevolazione()+"; causale = "+r.getCausaleDisimpegno()+"; importo = "+r.getImporto();
						if (r.getRevocaIrregolarita() != null) {
							for (RevocaIrregolaritaDTO i : r.getRevocaIrregolarita()) {
								s = s + "\n     Irregolarita: idIrregolarita = "+i.getIdIrregolarita()+"; tipoIrregolarita = "+i.getTipoIrregolarita()+"; quota = "+i.getQuotaIrreg()+"; DtFineValidita = "+i.getDtFineValidita();
								if (i.getDettagliRevocaIrregolarita() != null) {
									for (DettaglioRevocaIrregolaritaDTO d : i.getDettagliRevocaIrregolarita()) {
										s = s + "\n          Dettaglio: IdDettRevocaIrreg = "+d.getIdDettRevocaIrreg()+"; idClassRevocaIrreg = "+d.getIdClassRevocaIrreg()+"; importo = "+d.getImporto();
										for (DsDettaglioRevocaIrregolaritaDTO ds : d.getDsDettagliRevocaIrregolarita()) {
											s = s + "\n               DS associate: IdDichiarazioneSpesa = "+ds.getIdDichiarazioneSpesa()+"; importoIrregolareDs = "+ds.getImportoIrregolareDs();
										}
									}
								} 
							}
						} 
					}
				} 
			}
		}
		LOG.info(s);
		LOG.debug(prf + " END");
		
	}

	// Integro i dati estratti finora con:
	//  - gli eventuali dettagli delle irregolarita (PBANDI_T_DETT_REVOCA_IRREG)
	//  - le eventuali dichiarazioni di spesa associate ai dettagli (PBANDI_R_DS_DETT_REVOCA_IRREG)
	private List<it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO> integraIrregolarita( List<it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO> lista) {
		String prf = "[GestioneDisimpegniDAOImpl::integraIrregolarita]";
		LOG.debug(prf + " START");
		if (lista != null) {		
			// Scorre le modalita agevolazione per accedere alle revoche.
			for (it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO m : lista) {
				if (m.getRevoche() != null) {
					// Scorre le revoche per accedere alle irregolarità ('disimpegni' a video).
					for (it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO r : m.getRevoche()) {												
						if (r.getRevocaIrregolarita() != null) {
							// Per ogni irregolarità cerca gli eventuali dettagli.
							for (RevocaIrregolaritaDTO i : r.getRevocaIrregolarita()) {
								// Cerca gli eventuali dettagli associati all'irregolarità.
								List<PbandiTDettRevocaIrregVO> dettagliVO = findDettagliRevocaIrregolarita(r.getIdRevoca(), i.getIdIrregolarita());
								// Assegna i dettagli all'irregolarità.
								i.setDettagliRevocaIrregolarita(beanUtil.transform(dettagliVO, DettaglioRevocaIrregolaritaDTO.class));
								// Per ogni dettaglio cerca le eventuali DS associate.
								for (DettaglioRevocaIrregolaritaDTO d : i.getDettagliRevocaIrregolarita()) {
									List<PbandiRDsDettRevocaIrregVO> dsVO = findDsDettaglioRevocaIrregolarita(d.getIdDettRevocaIrreg());
									// Assegna le ds al dettaglio.
									d.setDsDettagliRevocaIrregolarita(beanUtil.transform(dsVO, DsDettaglioRevocaIrregolaritaDTO.class));
								}
							}
						} 
					}
				} 
			}
		}
		LOG.debug(prf + " END");
		return lista;
		
	}
	
	private List<PbandiTDettRevocaIrregVO> findDettagliRevocaIrregolarita(Long idRevoca, Long idIrregolarita) {
		String prf = "[GestioneDisimpegniDAOImpl::findDettagliRevocaIrregolarita]";
		LOG.debug(prf + " START");
		PbandiTDettRevocaIrregVO dettVO = new PbandiTDettRevocaIrregVO();
		dettVO.setIdRevoca(new BigDecimal(idRevoca));
		dettVO.setIdIrregolarita(new BigDecimal(idIrregolarita));
		List<PbandiTDettRevocaIrregVO> dettagliVO = genericDAO.findListWhere(dettVO);
		LOG.debug(prf + " END");
		return dettagliVO;
	}
	
	private List<PbandiRDsDettRevocaIrregVO> findDsDettaglioRevocaIrregolarita(Long idDettRevovaIrreg) {
		String prf = "[GestioneDisimpegniDAOImpl::findDsDettaglioRevocaIrregolarita]";
		LOG.debug(prf + " START");
		PbandiRDsDettRevocaIrregVO dsVO = new PbandiRDsDettRevocaIrregVO();
		dsVO.setIdDettRevocaIrreg(new BigDecimal(idDettRevovaIrreg));
		List<PbandiRDsDettRevocaIrregVO> listaDsVO = genericDAO.findListWhere(dsVO);
		LOG.debug(prf + " END");
		return listaDsVO;
	}
	
	private it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO findRiepilogoModalitaRevoca(it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO dto, Long idRevoca) throws FormalParameterException {
		String prf = "[GestioneDisimpegniDAOImpl::findRiepilogoModalitaRevoca]";
		LOG.debug(prf + " START");	
		Long idProgetto = dto.getIdProgetto();
		Long idModalitaAgevolazione = dto.getIdModalitaAgevolazione();
		List<RevocaProgettoVO> listRevocheModalitaVO = revocaRecuperoManager.findRevocheProgetto(idProgetto, idModalitaAgevolazione, idRevoca);
		 //TNT
		RevocaIrregolaritaVO filterIrregolarita=new RevocaIrregolaritaVO();
		filterIrregolarita.setIdProgetto(BigDecimal.valueOf(idProgetto));
		
		LOG.debug(prf + " cerco revocheIrregolaritaConDuplicati");
		List<RevocaIrregolaritaVO> revocheIrregolaritaConDuplicati = genericDAO.findListWhere(filterIrregolarita);

		if (!listRevocheModalitaVO.isEmpty()) {
			LOG.info(prf + " \n\ncerco irregolarità");
			List<it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO> revocheDTO=new ArrayList<it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO>();
			for (RevocaProgettoVO revocaProgettoVO : listRevocheModalitaVO) {
				if(revocaProgettoVO.getIdRevoca()!=null){
					Map<BigDecimal, RevocaIrregolaritaVO> mapIrregolaritaPerRevoca = getMapIrregolaritaPerRevoca(revocaProgettoVO.getIdRevoca(), revocheIrregolaritaConDuplicati);
					Map<BigDecimal, RevocaIrregolaritaVO> mapIrregolaritaPerRevocaNew=new HashMap<BigDecimal, RevocaIrregolaritaVO>(mapIrregolaritaPerRevoca);
					List<RevocaIrregolaritaVO> revocheIrregolaritaSenzaDuplicati =new ArrayList<RevocaIrregolaritaVO>();
					it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO revocaDTO=beanUtil.transform(revocaProgettoVO,it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO.class);
					if(revocaProgettoVO.getIdCausaleDisimpegno()!=null){
						DecodificaDTO decodifica = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.CAUSALE_DISIMPEGNO, revocaProgettoVO.getIdCausaleDisimpegno());
						revocaDTO.setCodCausaleDisimpegno(decodifica.getDescrizioneBreve());
						revocaDTO.setCausaleDisimpegno(decodifica.getDescrizione());
					}
					if (revocaProgettoVO.getIdMancatoRecupero() != null) {
						DecodificaDTO decodifica = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.MANCATO_RECUPERO, revocaProgettoVO.getIdMancatoRecupero());
						revocaDTO.setDescMancatoRecupero(decodifica.getDescrizione());
					}
					if(!revocheIrregolaritaConDuplicati.isEmpty()){
 						 for (RevocaIrregolaritaVO voz : revocheIrregolaritaConDuplicati) {
							 if(!mapIrregolaritaPerRevocaNew.containsKey(voz.getIdIrregolarita())){
								 RevocaIrregolaritaVO clone = (RevocaIrregolaritaVO)voz.clone();
								 clone.setQuotaIrreg(BigDecimal.valueOf(0d));
								 mapIrregolaritaPerRevocaNew.put(voz.getIdIrregolarita(),clone);
							 }
						 }
						 List <RevocaIrregolaritaVO>list=new ArrayList<RevocaIrregolaritaVO>(mapIrregolaritaPerRevocaNew.values());
						 revocaDTO.setRevocaIrregolarita(beanUtil.transform(list,RevocaIrregolaritaDTO.class));
					}
					revocheDTO.add(revocaDTO);
				}
			}
			
			dto.setRevoche(revocheDTO.toArray(new it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO[0]));

			/*
			 * Ricerco i recuperi associati alla modalita' di agevolazione del progetto
			 */
			LOG.debug(prf + " Ricerco i recuperi associati alla modalita' di agevolazione del progetto");
			List<RecuperoProgettoVO> listRecuperiModalitaVO = revocaRecuperoManager.findRecuperiProgetto(idProgetto, idModalitaAgevolazione, null);
			dto.setRecuperi(beanUtil.transform(listRecuperiModalitaVO, RecuperoDTO.class));

			/*  Ricerco le causali */
			LOG.debug(prf + " Ricerco le causali");
			List<ErogazioneCausaleModalitaAgevolazioneVO> listCausaleModalitaAgevolazioniVO = revocaRecuperoManager.findCausaliModalitaAgevolazione(idProgetto, idModalitaAgevolazione);
			dto.setCausaliErogazioni(beanUtil.transform(listCausaleModalitaAgevolazioniVO, CausaleErogazioneDTO.class));

			/*  Ricerco i totali delle revoche e dei recuperi per la modalita' di agevolazione */
			LOG.debug(prf + " Ricerco i totali delle revoche e dei recuperi per la modalita' di agevolazione");
			ModalitaAgevolazioneTotaleRevocheVO totaliVO = revocaRecuperoManager.findTotaleRevocheRecuperi(idProgetto, idModalitaAgevolazione);
			dto.setTotaleImportoRecuperato(NumberUtil.toDouble(totaliVO.getTotaleImportoRecuperato()));
			dto.setTotaleImportoRevocato(NumberUtil.toDouble(totaliVO.getTotaleImportoRevocato()));

			LOG.debug(prf + " END");
			return dto;
		} else {
			LOG.debug(prf + " END");
			return null;
		}
	}

	private Map<BigDecimal, RevocaIrregolaritaVO> getMapIrregolaritaPerRevoca(BigDecimal idRevoca,
			List<RevocaIrregolaritaVO> revocheIrregolaritaConDuplicati) {
		Map <BigDecimal,RevocaIrregolaritaVO> map=new HashMap<BigDecimal,RevocaIrregolaritaVO>();
		if(!revocheIrregolaritaConDuplicati.isEmpty()){
			 for (RevocaIrregolaritaVO vo  : revocheIrregolaritaConDuplicati) {
				 if(vo.getIdRevoca()!=null && vo.getIdRevoca().longValue()==idRevoca.longValue())
					map.put(vo.getIdIrregolarita(), vo);
			 }
		}
		return map;
	}

	@Override
	@Transactional
	public ArrayList<CodiceDescrizione> findPeriodi(Long idUtente, String idIride) throws FormalParameterException {
		String prf = "[GestioneDisimpegniDAOImpl::findPeriodi]";
		LOG.debug(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		logger.info("\n\n\n\nfindPeriodi");
		ValidatorInput.verifyNullValue(nameParameter, idUtente,idIride);
		PbandiTPeriodoVO pbandiTPeriodoVO=new PbandiTPeriodoVO();
		pbandiTPeriodoVO.setIdTipoPeriodo(Constants.ID_TIPO_PERIODO_ANNO_CONTABILE);
		pbandiTPeriodoVO.setDescendentOrder("dtInizioContabile");
		List<PbandiTPeriodoVO> periodi = genericDAO.findListWhere(new FilterCondition(pbandiTPeriodoVO));
		Iterator<PbandiTPeriodoVO> iter = periodi.iterator();
		int currentYear=DateUtil.getAnno();
		int mese=DateUtil.getMese();
		
		String anno=""+currentYear;
		if(mese<7)
			anno=""+(currentYear-1);
		
		while(iter.hasNext()){
			PbandiTPeriodoVO periodo = iter.next();
			String descPeriodoVisualizzata = periodo.getDescPeriodoVisualizzata();
			if(descPeriodoVisualizzata.contains(anno)){
				anno="";
			}else{
				iter.remove();
			}
		}
		Map <String,String>mapProps=new HashMap<String,String>();
		mapProps.put("idPeriodo", "codice");
		mapProps.put("descPeriodoVisualizzata", "descrizione");
		 
		PeriodoDTO[] dto = beanUtil.transform(periodi, PeriodoDTO.class);
	    ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(dto, CodiceDescrizione.class,mapProps);
	    LOG.debug(prf + " END");
		return result;
	}

	@Override
	@Transactional
	public MancatoRecuperoDTO[] findModalitaRecupero(Long idUtente, String idIride) throws FormalParameterException {
		String prf = "[GestioneDisimpegniDAOImpl::findModalitaRecupero]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "idIride" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride);
		
		PbandiDMancatoRecuperoVO pbandiDMancatoRecuperoVO = new PbandiDMancatoRecuperoVO();
		pbandiDMancatoRecuperoVO.setAscendentOrder("descMancatoRecupero");
		MancatoRecuperoDTO[] result = beanUtil.transform(genericDAO.findListWhere(new FilterCondition(pbandiDMancatoRecuperoVO)), MancatoRecuperoDTO.class);
		LOG.info(prf + " END");
		return result;
		
	}

	@Override
	@Transactional
	public QuotaParteVoceDiSpesaDTO[] findQuotePartePerRevoca(Long idSoggetto, String idIride,
			QuotaParteVoceDiSpesaDTO filtro) throws Exception {
		String prf = "[GestioneDisimpegniDAOImpl::findModalitaRecupero]";
		LOG.info(prf + " BEGIN");

		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, filtro);
			ValidatorInput.verifyAtLeastOneNotNullValue(filtro);
			ValidatorInput.verifyNullValue(new String[] { "idProgetto" },
					filtro.getIdProgetto());
			BigDecimal idContoEconomico = null;
			try {
				idContoEconomico = contoEconomicoManager
						.getIdContoMaster(NumberUtil.toBigDecimal(filtro
								.getIdProgetto()));
			} catch (ContoEconomicoNonTrovatoException e) {
				logger.error("Conto economico non trovato", e);
				throw new Exception(Constants.ERRORE_SERVER);
			}

			DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO filtroVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
			filtroVO.setIdContoEconomico(idContoEconomico);
			filtroVO.setIdProgetto(NumberUtil.toBigDecimal(filtro
					.getIdProgetto()));

			FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> filterCondition = new FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(
					filtroVO);
			
			List<QuotaParteVoceSpesaVO> quoteParte = genericDAO
					.findListWhereGroupBy(filterCondition,
							QuotaParteVoceSpesaVO.class);
			LOG.info(prf + " END");
			return beanUtil.transform(quoteParte,
					QuotaParteVoceDiSpesaDTO.class);			

		} catch (Exception e) {
			throw e;
		}
		
		
	}

	@Override
	@Transactional
	public RevocaModalitaAgevolazioneDTO[] findRevoche(Long idUtente, String idIride,
			RevocaModalitaAgevolazioneDTO filtro) throws Exception {
		String prf = "[GestioneDisimpegniDAOImpl::findRevoche]";
		LOG.info(prf + " BEGIN");

		try {
			List<ModalitaDiAgevolazioneContoEconomicoVO> listModalitaVO = null;
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, filtro);
			try {
				listModalitaVO = contoEconomicoManager.caricaModalitaAgevolazione(
								NumberUtil.toBigDecimal(filtro.getIdProgetto()),
								Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
			} catch (ContoEconomicoNonTrovatoException e) {
				LOG.error(prf + " Nessun conto economico trovato", e);
				throw new Exception(ErrorMessages.ERRORE_SERVER);
			}
			RevocaModalitaAgevolazioneDTO[] result = beanUtil.transform( listModalitaVO, RevocaModalitaAgevolazioneDTO.class);

			/*
			 * Carico gli idModalitaAgevolazione
			 */
			if (listModalitaVO != null) {
				List<ErogazioneCausaleModalitaAgevolazioneVO> listCausaleModalitaAgevolazioni = new ArrayList<ErogazioneCausaleModalitaAgevolazioneVO>();
				List<ModalitaAgevolazioneTotaleRevocheVO> listModalitaAgevolazioni = new ArrayList<ModalitaAgevolazioneTotaleRevocheVO>();
				for (ModalitaDiAgevolazioneContoEconomicoVO vo : listModalitaVO) {
					ErogazioneCausaleModalitaAgevolazioneVO causaleModalitaVO = new ErogazioneCausaleModalitaAgevolazioneVO();
					causaleModalitaVO.setIdModalitaAgevolazione(vo.getIdModalitaAgevolazione());
					listCausaleModalitaAgevolazioni.add(causaleModalitaVO);

					ModalitaAgevolazioneTotaleRevocheVO modalitaVO = new ModalitaAgevolazioneTotaleRevocheVO();
					modalitaVO.setIdModalitaAgevolazione(vo.getIdModalitaAgevolazione());
					listModalitaAgevolazioni.add(modalitaVO);
				}

				ErogazioneCausaleModalitaAgevolazioneVO filtroCausaliVO = new ErogazioneCausaleModalitaAgevolazioneVO();
				filtroCausaliVO.setIdProgetto(NumberUtil.toBigDecimal(filtro.getIdProgetto()));
				filtroCausaliVO.setAscendentOrder("idModalitaAgevolazione");

				FilterCondition<ErogazioneCausaleModalitaAgevolazioneVO> inConditionCausali = new FilterCondition<ErogazioneCausaleModalitaAgevolazioneVO>(
						listCausaleModalitaAgevolazioni);
				FilterCondition<ErogazioneCausaleModalitaAgevolazioneVO> conditionCausali = new FilterCondition<ErogazioneCausaleModalitaAgevolazioneVO>(
						filtroCausaliVO);
				AndCondition<ErogazioneCausaleModalitaAgevolazioneVO> andConditionCausali = new AndCondition<ErogazioneCausaleModalitaAgevolazioneVO>(
						inConditionCausali, conditionCausali);

				List<ErogazioneCausaleModalitaAgevolazioneVO> listCausali = genericDAO.findListWhere(andConditionCausali);

				ModalitaAgevolazioneTotaleRevocheVO filtroModalitaVO = new ModalitaAgevolazioneTotaleRevocheVO();
				filtroModalitaVO.setIdProgetto(NumberUtil.toBigDecimal(filtro.getIdProgetto()));
				filtroModalitaVO.setAscendentOrder("idModalitaAgevolazione");

				FilterCondition<ModalitaAgevolazioneTotaleRevocheVO> inConditioModalita = new FilterCondition<ModalitaAgevolazioneTotaleRevocheVO>(
						listModalitaAgevolazioni);
				FilterCondition<ModalitaAgevolazioneTotaleRevocheVO> conditionModalita = new FilterCondition<ModalitaAgevolazioneTotaleRevocheVO>(
						filtroModalitaVO);
				AndCondition<ModalitaAgevolazioneTotaleRevocheVO> andConditionModalita = new AndCondition<ModalitaAgevolazioneTotaleRevocheVO>(
						inConditioModalita, conditionModalita);

				List<ModalitaAgevolazioneTotaleRevocheVO> listModalita = genericDAO
						.findListWhere(andConditionModalita);

				for (RevocaModalitaAgevolazioneDTO dto : result) {
					BigDecimal idModalitaAgev = NumberUtil.toBigDecimal(dto.getIdModalitaAgevolazione());
					List<it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.CausaleErogazioneDTO> causaliModalita = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.CausaleErogazioneDTO>();
					for (ErogazioneCausaleModalitaAgevolazioneVO causaleVO : listCausali) {
						if (NumberUtil.compare( causaleVO.getIdModalitaAgevolazione(), idModalitaAgev) == 0) {
							it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.CausaleErogazioneDTO causale = beanUtil.transform( causaleVO, it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.CausaleErogazioneDTO.class);
							causaliModalita.add(causale);
						}
					}
					dto.setCausaliErogazioni(causaliModalita.toArray(new it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.CausaleErogazioneDTO[] {}));

					for (ModalitaAgevolazioneTotaleRevocheVO modalitaTot : listModalita) {
						if (NumberUtil.compare( modalitaTot.getIdModalitaAgevolazione(), idModalitaAgev) == 0) {
							dto.setTotaleImportoRevocato(NumberUtil.toDouble(modalitaTot.getTotaleImportoRevocato()));
							dto.setTotaleImportoRecuperato(NumberUtil.toDouble(modalitaTot.getTotaleImportoRecuperato()));
						}
					}
				}

			}
			LOG.info(prf + " END");
			return result;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoSalvaRevocaDTO checkDisimpegni(Long idUtente, String idIride,
			RevocaModalitaAgevolazioneDTO[] revoche) throws FormalParameterException {
		String prf = "[GestioneDisimpegniDAOImpl::checkDisimpegni]";
		LOG.info(prf + " BEGIN");
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
		ValidatorInput.verifyNullValue(nameParameter, revoche);
		EsitoSalvaRevocaDTO esito = new EsitoSalvaRevocaDTO();
		esito.setEsito(Boolean.FALSE);
		List<MessaggioDTO> errors = new ArrayList<MessaggioDTO>();
		if (revoche != null) {
			for (RevocaModalitaAgevolazioneDTO dto : revoche) {
				boolean hasError = false;
				/*
				 * Controllo che l'importo della revoca sia maggiore o
				 * uguale a 0
				 */
				if (NumberUtil.compare(dto.getImportoRevoca(), 0D) < 0) {
					hasError = true;
					MessaggioDTO error = new MessaggioDTO();
					error.setId(dto.getIdModalitaAgevolazione());
					error.setMsgKey(ErrorMessages.ERROR_DISIMPEGNO_IMPORTO_REVOCA_NEGATIVO);
					errors.add(error);
				}
				if (!hasError && dto.getImportoRevoca() != null) {
					Double totaleRevocato = NumberUtil.sum( NumberUtil.getDoubleValue(dto.getTotaleImportoRevocato()), dto.getImportoRevoca());			
					if (NumberUtil.compare( dto.getTotaleImportoRecuperato(), totaleRevocato) > 0) {
						hasError = true;
						MessaggioDTO error = new MessaggioDTO();
						error.setId(dto.getIdModalitaAgevolazione());
						error.setMsgKey(ErrorMessages.ERROR_DISIMPEGNO_RECUPERATO_MAGGIORE_REVOCATO);
						errors.add(error);
					}
					
					// Jira PBANDI-2911: il totale dei disimpegni non deve superare l'agevolato. 
					if (NumberUtil.compare(totaleRevocato, dto.getQuotaImportoAgevolato()) > 0) {
						hasError = true;
						MessaggioDTO error = new MessaggioDTO();
						error.setId(dto.getIdModalitaAgevolazione());
						error.setMsgKey(ErrorMessages.ERROR_REVOCA_TOTALE_REVOCATO_SUPERIORE_AGEVOLATO);
						errors.add(error);
					}

				}
			}
		}
		if (errors.isEmpty()) {
			esito.setEsito(Boolean.TRUE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(Constants.WARN_CONFERMA_SALVATAGGIO);
			esito.setMsgs(new MessaggioDTO[] { msg });
		} else {
			esito.setMsgs(beanUtil.transform(errors, MessaggioDTO.class));
		}
		LOG.info(prf + " END");
		return esito;
	}

	@Override
	@Transactional
	public EsitoSalvaRevocaDTO salvaDisimpegni(Long idUtente, String idIride, RevocaModalitaAgevolazioneDTO[] revoche) throws Exception {
		String prf = "[GestioneDisimpegniDAOImpl::salvaDisimpegni]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale", "revoche" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, revoche);
		EsitoSalvaRevocaDTO esito = new EsitoSalvaRevocaDTO();
		esito.setEsito(Boolean.FALSE);
		Long idProgetto=null;
		List<MessaggioDTO> errors = new ArrayList<MessaggioDTO>();
		if (revoche != null) {
			for (RevocaModalitaAgevolazioneDTO dto : revoche) {
				if (NumberUtil.compare(dto.getImportoRevoca(), 0D) > 0) {
					idProgetto=dto.getIdProgetto();
					PbandiTRevocaVO vo = new PbandiTRevocaVO();
					vo.setIdModalitaAgevolazione(NumberUtil.toBigDecimal(dto.getIdModalitaAgevolazione()));
					vo.setImporto(BeanUtil.transformToBigDecimal(dto.getImportoRevoca()));
					vo.setIdProgetto(NumberUtil.toBigDecimal(dto.getIdProgetto()));
					vo.setEstremi(dto.getEstremi());
					vo.setDtRevoca(DateUtil.utilToSqlDate(dto.getDtRevoca()));
					//M.R.////////////
					if (dto.getFlagOrdineRecupero() != null) vo.setFlagOrdineRecupero(dto.getFlagOrdineRecupero());
					//else vo.setFlagOrdineRecupero("N"); //tanto è un campo obbligatorio
					/////////////////
					
					if(dto.getCodCausaleDisimpegno()!=null){
						BigDecimal idCausaleDisimpegno = decodificheManager.decodeDescBreve(PbandiDCausaleDisimpegnoVO.class, dto.getCodCausaleDisimpegno());
						vo.setIdCausaleDisimpegno(idCausaleDisimpegno );
					} else {
						vo.setIdCausaleDisimpegno(null);
					}
					
					if(dto.getIdMotivoRevoca()!=null) vo.setIdMotivoRevoca(NumberUtil.toBigDecimal(dto.getIdMotivoRevoca()));
					//M.R.////////////
					if(dto.getIdMancatoRecupero()!=null) vo.setIdMancatoRecupero(NumberUtil.toBigDecimal(dto.getIdMancatoRecupero()));
					/////////////////
					
					vo.setIdPeriodo(NumberUtil.toBigDecimal(dto.getIdPeriodo()));
				 
					vo.setNote(dto.getNote());
					vo.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
					try {
						genericDAO.insert(vo);
					} catch (Exception e) {
						LOG.error(prf + "Errore insert PBANDI_T_REVOCA", e);						
						throw new Exception(Constants.ERRORE_SERVER);
					}
				}
			}
		}
		if (errors.isEmpty()) {
			esito.setEsito(Boolean.TRUE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			esito.setMsgs(new MessaggioDTO[] { msg });
		} else {
			esito.setMsgs(beanUtil.transform(errors, MessaggioDTO.class));
		}
		LOG.info(prf + " END");
		return esito;

	}

	@Override
	@Transactional
	public EsitoSalvaRevocaDTO modificaDisimpegno(Long idUtente, String idIride,
			it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO modalitaAgevolazione) throws Exception {
		String prf = "[GestioneDisimpegniDAOImpl::modificaDisimpegno]";
		LOG.info(prf + " BEGIN");
		EsitoSalvaRevocaDTO result = new EsitoSalvaRevocaDTO();
		result.setEsito(Boolean.TRUE);
		List<MessaggioDTO> msgs = new ArrayList<MessaggioDTO>();

		String[] nameParameter = { "idUtente", "identitaDigitale", "modalitaAgevolazione" };
		ValidatorInput.verifyNullValue(nameParameter, modalitaAgevolazione);
		ValidatorInput.verifyAtLeastOneNotNullValue(modalitaAgevolazione);
		ValidatorInput.verifyNullValue(new String[] { "modalitaAgevolazione,revoche[0]" }, modalitaAgevolazione.getRevoche()[0]);
		ValidatorInput.verifyNullValue(new String[] { "modalitaAgevolazione,revoche[0].idRevoca" }, modalitaAgevolazione.getRevoche()[0].getIdRevoca());

		BigDecimal totaleRevoche = BeanUtil.transformToBigDecimal(modalitaAgevolazione.getTotaleImportoRevocato());
		BigDecimal totaleRecupero = BeanUtil.transformToBigDecimal(modalitaAgevolazione.getTotaleImportoRecuperato());
		BigDecimal totaleErogato = BeanUtil.transformToBigDecimal(modalitaAgevolazione.getImportoErogazioni());
		BigDecimal importoAgevolato = BeanUtil.transformToBigDecimal(modalitaAgevolazione.getQuotaImportoAgevolato());

		it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO revoca = modalitaAgevolazione.getRevoche()[0];
		BigDecimal importoRevocato = BeanUtil.transformToBigDecimal(revoca.getImporto());

		/*
		 * Al totale delle revoche devo sottrarre il vecchio importo
		 * relativo alla revoca in oggetto e sommare il nuovo importo
		 */
		PbandiTRevocaVO revocaOldVO = new PbandiTRevocaVO();
		revocaOldVO.setIdRevoca(NumberUtil.toBigDecimal(revoca.getIdRevoca()));
		revocaOldVO = genericDAO.findSingleWhere(revocaOldVO);

		totaleRevoche = NumberUtil.subtract(totaleRevoche,revocaOldVO.getImporto());
		totaleRevoche = NumberUtil.sum(totaleRevoche, importoRevocato);

		/*
		 * Verifico che il totale delle recuperi non sia superiore al totale
		 * dei revoche
		 */
		if (NumberUtil.compare(totaleRecupero, totaleRevoche) > 0) {
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(ErrorMessages.ERROR_REVOCA_RECUPERATO_MAGGIORE_REVOCATO);
			msgs.add(msg);
			result.setEsito(Boolean.FALSE);

		}

		/*
		 * Verifico che il totale delle revoche non sia superiore alla somma
		 * erogato.
		 */
		// Jira PBANDI-2911: questo controllo va fatto solo sull'erogato e col totale dei soli disimpegni di tipo revoca.
		// lo sposto su RevocaBusinessImpl.validateInModifica()
		/*
		BigDecimal sommaErogato = totaleErogato;
		if (NumberUtil.compare(totaleErogato, importoAgevolato) > 0) {
			sommaErogato = NumberUtil.subtract(totaleErogato,
					importoAgevolato);
		}
		if (NumberUtil.compare(totaleRevoche, sommaErogato) > 0) {
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(ERROR_REVOCA_TOTALE_REVOCATO_SUPERIORE_EROGAZIONI);
			msgs.add(msg);
			result.setEsito(Boolean.FALSE);
		}
		*/

		/*
		 * Verifico se la revoca e' stata certificata e, nel caso in cui non
		 * e' stata inserita la motivazione nelle note visualizzato un msg
		 */
		if (isRevocaCertificata(revoca.getIdRevoca())
				&& (revoca.getNote() == null || revoca.getNote().trim()
						.length() == 0)) {
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(MessageConstants.WARN_REVOCA_REVOCA_CERTIFICATA);
			msgs.add(msg);
			result.setEsito(Boolean.FALSE);
		}

		/*
		 * Se non vi sono errori allora posso aggiornare la revoca
		 */
		if (result.getEsito()) {
			revocaOldVO.setImporto(BeanUtil.transformToBigDecimal(revoca.getImporto()));
			revocaOldVO.setEstremi(revoca.getEstremi());
			revocaOldVO.setDtRevoca(DateUtil.utilToSqlDate(revoca.getDtRevoca()));
			revocaOldVO.setNote(revoca.getNote());
			revocaOldVO.setIdMotivoRevoca(NumberUtil.toBigDecimal(revoca.getIdMotivoRevoca()));
			String codCausaleDisimpegno = revoca.getCodCausaleDisimpegno();
			revocaOldVO.setIdPeriodo(NumberUtil.toBigDecimal(revoca.getIdPeriodo()));
			//M.R.////////////
			revocaOldVO.setFlagOrdineRecupero(revoca.getFlagOrdineRecupero());
			revocaOldVO.setIdMancatoRecupero(NumberUtil.toBigDecimal(revoca.getIdMancatoREcupero()));
			//////////////////
			
			if(codCausaleDisimpegno!=null){
				BigDecimal idCausaleDisimpegno = decodificheManager.decodeDescBreve(PbandiDCausaleDisimpegnoVO.class, codCausaleDisimpegno);
				revocaOldVO.setIdCausaleDisimpegno(idCausaleDisimpegno );
			}else{
				revocaOldVO.setIdCausaleDisimpegno(null );
			}
			
			try {
				revocaOldVO.setIdUtenteAgg(new BigDecimal(idUtente));
				logger.info("\n\n\n\n\n\n\n\ngenericDAO.update(revocaOldVO)");
				genericDAO.updateNullables(revocaOldVO);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				msgs.add(msg);

			} catch (Exception e) {
				logger.error(
						"Errore in fase di update record in PBANDI_T_REVOCA",
						e);
				Exception re = new Exception(ErrorMessages.ERRORE_SERVER, e);
				throw re;
			}
		}

		result.setMsgs(beanUtil.transform(msgs, MessaggioDTO.class));
		return result;

	}

	private boolean isRevocaCertificata(Long idRevoca) {
		PbandiRDettPropCertRevocaVO filtroVO = new PbandiRDettPropCertRevocaVO();
		filtroVO.setIdRevoca(NumberUtil.toBigDecimal(idRevoca));
		List<PbandiRDettPropCertRevocaVO> certificazioniRevoca = genericDAO
				.findListWhere(filtroVO);

		if (certificazioniRevoca != null && !certificazioniRevoca.isEmpty())
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	@Override
	public EsitoSalvaRevocaDTO eliminaRevoca(Long idUtente, String idIride, Long idRevoca, Long idProgetto) throws Exception {
		String prf = "[GestioneDisimpegniDAOImpl::eliminaRevoca]";
		LOG.info(prf + " BEGIN");
		
		try {
			EsitoSalvaRevocaDTO result = new EsitoSalvaRevocaDTO();
			List<MessaggioDTO> msgs = new ArrayList<MessaggioDTO>();

			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idRevoca" };
			ValidatorInput.verifyNullValue(nameParameter, idRevoca);

			if (!isRevocaCertificata(idRevoca)) {
				PbandiTRevocaVO revocaVO = new PbandiTRevocaVO();
				revocaVO.setIdRevoca(NumberUtil.toBigDecimal(idRevoca));
				revocaVO = genericDAO.findSingleWhere(revocaVO);

				/*
				 * Verifico che il totale revocato (al netto dell' importo della
				 * revoca in oggetto) non sia inferiore al totale recuperato
				 */
				ModalitaAgevolazioneTotaleRevocheVO filtroTotaliVO = new ModalitaAgevolazioneTotaleRevocheVO();
				filtroTotaliVO.setIdProgetto(revocaVO.getIdProgetto());
				List<ModalitaAgevolazioneTotaleRevocheVO> totali = genericDAO
						.findListWhere(filtroTotaliVO);
				BigDecimal totaleRecuperato = new BigDecimal(0);
				BigDecimal totaleRevocato = new BigDecimal(0);
				for (ModalitaAgevolazioneTotaleRevocheVO vo : totali) {
					totaleRecuperato = NumberUtil.sum(totaleRecuperato,
							vo.getTotaleImportoRecuperato());
					totaleRevocato = NumberUtil.sum(totaleRevocato,
							vo.getTotaleImportoRevocato());
				}

				totaleRevocato = NumberUtil.subtract(totaleRevocato,
						revocaVO.getImporto());

				if (NumberUtil.compare(totaleRevocato, totaleRecuperato) >= 0) {
					try {
						// Cancello eventuali record in PBANDI_T_DETT_REVOCA_IRREG e PBANDI_R_DS_DETT_REVOCA_IRREG.
						this.cancellaDettagliEDs(idRevoca);						
						
						revocaVO = new PbandiTRevocaVO();
						revocaVO.setIdRevoca(NumberUtil.toBigDecimal(idRevoca));
						PbandiRRevocaIrregVO pbandiRRevocaIrregVO= new PbandiRRevocaIrregVO();
						pbandiRRevocaIrregVO.setIdRevoca(NumberUtil.toBigDecimal(idRevoca));
						logger.info("cancello eventuali revocheIrregolarita *** ");
						genericDAO.deleteWhere(Condition.filterBy(pbandiRRevocaIrregVO)) ;
						logger.info("cancello la revoca ");
						genericDAO.delete(revocaVO);
						MessaggioDTO msg = new MessaggioDTO();
						msg.setMsgKey(MessageConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
						result.setEsito(Boolean.TRUE);
						msgs.add(msg);
					} catch (Exception e) {
						logger.error( "Errore in fase di cancellazione record in PBANDI_T_REVOCA", e);
						Exception re = new Exception(Constants.ERRORE_SERVER, e);
						throw re;
					}
				} else {
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ErrorMessages.ERROR_REVOCA_CANCELLAZIONE_TOTALE_REVOCATO_INFERIORE_TOTALE_RECUPERATO);
					msgs.add(msg);
					result.setEsito(Boolean.FALSE);
				}
			} else {
				result.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.ERROR_REVOCA_PRESENTE_CERTIFICAZIONE_REVOCA);
				msgs.add(msg);
			}
			result.setMsgs(beanUtil.transform(msgs, MessaggioDTO.class));
			LOG.info(prf + " END");
			return result;
		} catch (Exception e) {
			throw e;
		}
		
	}

	private void cancellaDettagliEDs(Long idRevoca) throws Exception {
		String prf = "[GestioneDisimpegniDAOImpl::cancellaDettagliEDs]";
		LOG.info(prf + " BEGIN");
		try {
			LOG.info("\n\ncancellaDettagliEDs(): INIZIO");
			LOG.info("cancellaDettagliEDs(): cancella associazioni, dettagli e ds relativi alla revoca "+idRevoca);
			// Cerco i dettagli associati alla revoca.
			PbandiTDettRevocaIrregVO filtro = new PbandiTDettRevocaIrregVO();
			filtro.setIdRevoca(new BigDecimal(idRevoca));
			ArrayList<PbandiTDettRevocaIrregVO> listaDettagli = (ArrayList<PbandiTDettRevocaIrregVO>) genericDAO.findListWhere(filtro);
			LOG.info("cancellaDettagliEDs(): num dettagli da cancellare: "+listaDettagli.size());
			
			// Per ogni dettaglio:
			//  - cancello le eventuali ds associate.
			//  - cancello il dettaglio.
			for (PbandiTDettRevocaIrregVO dettaglio : listaDettagli) {
				// Cancello le eventuali ds associate al dettaglio corrente.
				PbandiRDsDettRevocaIrregVO filtroDs = new PbandiRDsDettRevocaIrregVO();
				filtroDs.setIdDettRevocaIrreg(dettaglio.getIdDettRevocaIrreg());
				ArrayList<PbandiRDsDettRevocaIrregVO> listaDs = (ArrayList<PbandiRDsDettRevocaIrregVO>) genericDAO.findListWhere(filtroDs);
				for (PbandiRDsDettRevocaIrregVO ds : listaDs) {
					genericDAO.delete(ds);
				}
				// Cancello il dettaglio corrente.
				genericDAO.delete(dettaglio);
			}
			
			// Cancello le associazioni revoca\irregolarità.
			PbandiRRevocaIrregVO filtroRevocaIrreg = new PbandiRRevocaIrregVO();
			filtroRevocaIrreg.setIdRevoca(new BigDecimal(idRevoca));
			List<PbandiRRevocaIrregVO> associazioni = genericDAO.findListWhere(filtroRevocaIrreg);
			for (PbandiRRevocaIrregVO r : associazioni) {
				genericDAO.delete(r);
			}
			
			LOG.info("\ncancellaDettagliEDs(): FINE");
		} catch (Exception e) {
			String msg = "cancellaDettagliEDs(): ERRORE durante la cancellazione di dettagli e ds: "+e;
			LOG.error(msg);
			throw new Exception(msg);
		}
		LOG.info(prf + " END");
	}

	
	@Override
	@Transactional
	public Long[] findDsIrregolarita(Long idUtente, String idIride, Long idProgetto) throws FormalParameterException {
		String prf = "[GestioneDisimpegniDAOImpl::findDsIrregolarita]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "idIride", "idProgetto" };		
		ValidatorInput.verifyNullValue(nameParameter, idUtente,idIride, idProgetto);
		
		Long[] result = null;
		try {
			PbandiTDichiarazioneSpesaVO filter = new PbandiTDichiarazioneSpesaVO();
			filter.setIdProgetto(new BigDecimal(idProgetto));
			filter.setAscendentOrder("idDichiarazioneSpesa");
			
			NotCondition<PbandiTDichiarazioneSpesaVO> dtChiusuraValidazioneValorizzata = new NotCondition<PbandiTDichiarazioneSpesaVO>(
					new NullCondition<PbandiTDichiarazioneSpesaVO>(
							PbandiTDichiarazioneSpesaVO.class,
							"dtChiusuraValidazione"));
			
			List<PbandiTDichiarazioneSpesaVO> lista = genericDAO.findListWhere(new AndCondition<PbandiTDichiarazioneSpesaVO>(
					new FilterCondition<PbandiTDichiarazioneSpesaVO>(filter), dtChiusuraValidazioneValorizzata));
			
			result = new Long[lista.size()]; 
			for (int i=0; i< lista.size(); ++i ) {	
				result[i] = lista.get(i).getIdDichiarazioneSpesa().longValue();
			}
		} catch (Exception e){
			LOG.error("ERRORE in findDsIrregolarita(): "+e);			
		}
		return result;
	}

	@Override
	@Transactional
	public EsitoSalvaRevocaDTO saveIrregolarita(Long idUtente, String idIride,
			RequestAssociaIrregolarita request) throws Exception {
		String prf = "[GestioneDisimpegniDAOImpl::saveIrregolarita]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "idIride","request" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, request);
		
		String metodo = "saveIrregolarita()";
		logger.info("\n\n\n\nsaveIrregolarita.");
		
		EsitoSalvaRevocaDTO esito = new EsitoSalvaRevocaDTO();
		esito.setEsito(Boolean.TRUE);
		
		try {

			Long idRevoca = request.getIdRevoca();
			if (idRevoca == null)
				throw new Exception("idRevoca non valorizzato.");
			
			// Cancella associazioni (PBANDI_R_REVOCA_IRREG), dettagli (PBANDI_T_DETT_REVOCA_IRREG) 
			// e ds associate (PBANDI_R_DS_DETT_REVOCA_IRREG) della revoca in input, 
			// in modo da poterle reinserire da zero.
			cancellaDettagliEDs(idRevoca);
			
			// Salva gli importi delle irregolarità in input (PBANDI_R_REVOCA_IRREG).
			salvaIrregolarita(request.getElencoSaveIrregolaritaDTO());
			
			// Salva i dettagli delle irregolarità in input (PBANDI_T_DETT_REVOCA_IRREG).
			salvaDettagli(request.getElencoSaveDettaglioDTO());
			
			// Salva le dichiarazioni di spesa associate ai dettagli delle irregolarità in input (PBANDI_R_DS_DETT_REVOCA_IRREG).
			salvaDs(request.getElencoSaveDsDTO());
			
		} catch (Exception e) {
			LOG.error(metodo+": ERRORE durante il salvataggio delle irregolarita: ", e);
			Exception re = new Exception(Constants.ERRORE_SERVER);
			throw re;
		}

		return esito;
	}

	private void salvaDs(SaveDsDTO[] elenco) throws Exception {
		String prf = "[GestioneDisimpegniDAOImpl::salvaDs]";
		LOG.info(prf + " BEGIN");
		try {
			LOG.info("\n\nsalvaDs(): INIZIO");
			if (elenco == null)
				return;
			for (SaveDsDTO dto : elenco) {
				if (dto != null) {
					if (dto.getIdRevoca() == null || dto.getIdIrregolarita() == null || dto.getIdClassRevocaIrreg() == null ||
						dto.getImportoIrregolareDs() == null)
							throw new Exception("Parametri input dettaglio irregolarita non valorizzati.");
					// Cerca il dettaglio per ricavarne l'id.
					PbandiTDettRevocaIrregVO filtro = new PbandiTDettRevocaIrregVO();
					filtro.setIdRevoca(new BigDecimal(dto.getIdRevoca()));
					filtro.setIdIrregolarita(new BigDecimal(dto.getIdIrregolarita()));			
					filtro.setIdClassRevocaIrreg(new BigDecimal(dto.getIdClassRevocaIrreg()));
					PbandiTDettRevocaIrregVO dettaglio = genericDAO.findSingleWhere(filtro);
					// Inserisce la ds.
					PbandiRDsDettRevocaIrregVO vo = new PbandiRDsDettRevocaIrregVO();
					vo.setIdDettRevocaIrreg(dettaglio.getIdDettRevocaIrreg());
					vo.setIdDichiarazioneSpesa(new BigDecimal(dto.getIdDs()));								
					vo.setImportoIrregolareDs(new BigDecimal(dto.getImportoIrregolareDs()));
					genericDAO.insert(vo);
				}
			}
			LOG.info("\n\nsalvaDs(): FINE");
		} catch (Exception e) {
			String msg = "salvaDs(): ERRORE durante il salvataggio delle DS dei dettagli delle irregolarita: "+e;
			LOG.error(msg);
			throw new Exception(msg);
		}
		
	}

	private void salvaDettagli(SaveDettaglioDTO[] elenco) throws Exception {
		try {
			LOG.info("\n\nsalvaDettagli(): INIZIO");
			if (elenco == null)
				return;
			for (SaveDettaglioDTO dto : elenco) {
				if (dto != null) {
					if (dto.getIdRevoca() == null || dto.getIdIrregolarita() == null || dto.getIdClassRevocaIrreg() == null ||
						dto.getImporto() == null || StringUtil.isBlank(dto.getTipologia()))
						throw new Exception("Parametri input dettaglio irregolarita non valorizzati.");													
					PbandiTDettRevocaIrregVO vo = new PbandiTDettRevocaIrregVO();
					vo.setIdRevoca(new BigDecimal(dto.getIdRevoca()));
					vo.setIdIrregolarita(new BigDecimal(dto.getIdIrregolarita()));			
					vo.setIdClassRevocaIrreg(new BigDecimal(dto.getIdClassRevocaIrreg()));
					vo.setTipologia(dto.getTipologia());
					vo.setImporto(new BigDecimal(dto.getImporto()));
					if (dto.getImportoAudit() != null)
						vo.setImportoAudit(new BigDecimal(dto.getImportoAudit()));
					genericDAO.insert(vo);			
				}
			}
			LOG.info("\n\nsalvaDettagli(): FINE");
		} catch (Exception e) {
			String msg = "salvaDettagli(): ERRORE durante il salvataggio dei dettagli delle irregolarita: "+e;
			LOG.error(msg);
			throw new Exception(msg);
		}
		
	}

	private void salvaIrregolarita(SaveIrregolaritaDTO[] elenco) throws java.lang.Exception {
		try {
			LOG.info("\n\nsalvaIrregolarita(): INIZIO");
			if (elenco == null)
				return;
			for (SaveIrregolaritaDTO dto : elenco) {
				if (dto != null) {
					if (dto.getIdRevoca() == null || dto.getIdIrregolarita() == null || dto.getQuotaIrregolarita() == null)
						throw new Exception("Parametri input irregolarita non valorizzati.");													
					PbandiRRevocaIrregVO vo = new PbandiRRevocaIrregVO();
					vo.setIdRevoca(new BigDecimal(dto.getIdRevoca()));
					vo.setIdIrregolarita(new BigDecimal(dto.getIdIrregolarita()));
					vo.setQuotaIrreg(new BigDecimal(dto.getQuotaIrregolarita()));
					if(dto.getQuotaIrregolarita() != 0d) {
						genericDAO.insert(vo);
					}

				}
			}
			LOG.info("\nsalvaIrregolarita(): FINE");
		} catch (Exception e) {
			String msg = "salvaIrregolarita(): ERRORE durante il salvataggio delle irregolarita: "+e;
			LOG.error(msg);
			throw new Exception(msg);
		}
	}

	@Override
	public boolean revocaConIrregolarita(Long idUtente, String idIride, Long idRevoca) throws Exception {
		
		PbandiRRevocaIrregVO filtro = new PbandiRRevocaIrregVO();
		filtro.setIdRevoca(new BigDecimal(idRevoca));
		List<PbandiRRevocaIrregVO> lista = genericDAO.findListWhere(filtro);
		if (lista != null && lista.size() > 0)
			return true;
		else
			return false;
		
	}


}
