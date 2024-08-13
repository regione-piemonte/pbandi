/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.business.manager;

import it.csi.pbandi.pbweb.integration.dao.impl.DichiarazioneDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.RappresentanteLegaleDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.dichiarazionedispesa.DichiarazioneDiSpesaException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDichiarazioneDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.RappresentanteLegaleDichiarazionePrivatoCittadinoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.RappresentanteLegaleDichiarazioneVO;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;

import java.security.InvalidParameterException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class RappresentanteLegaleManager {

	@Autowired
	private BeanUtil beanUtil;
	@Autowired
	private DecodificheManager decodificheManager;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO;
	@Autowired
	private DichiarazioneDiSpesaDAOImpl dichiarazioneDiSpesaDAOImpl;

	public void setPbandiDichiarazioneDiSpesaDAO(
			PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO) {
		this.pbandiDichiarazioneDiSpesaDAO = pbandiDichiarazioneDiSpesaDAO;
	}

	public PbandiDichiarazioneDiSpesaDAOImpl getPbandiDichiarazioneDiSpesaDAO() {
		return pbandiDichiarazioneDiSpesaDAO;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}


	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}


	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}

	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}


	public RappresentanteLegaleDTO findRappresentanteLegale(
			Long idProgetto,Long idSoggettoRappresentante) throws DichiarazioneDiSpesaException {		
		
		RappresentanteLegaleDichiarazioneVO rapprVO = new RappresentanteLegaleDichiarazioneVO();
		boolean isProvatoCittadino;
		try {
			isProvatoCittadino = dichiarazioneDiSpesaDAOImpl.getIsBeneficiarioPrivatoCittadino(idProgetto);
		} catch (InvalidParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DichiarazioneDiSpesaException("Errore nel verificare se il beneficiario e' un privato cittadino ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DichiarazioneDiSpesaException("Errore nel verificare se il beneficiario e' un privato cittadino ");
		}
		
		//Modifica per casistica privato cittadino
		//la query che c'era non funzionava nel caso in cui il beneficiario era un provato cittadino 
		//in questo modo in questa casistica entra in questo if e fa una query customizzata
		if (isProvatoCittadino)	{
			
			RappresentanteLegaleDichiarazionePrivatoCittadinoVO rappLegaleVO = new RappresentanteLegaleDichiarazionePrivatoCittadinoVO();			
			rappLegaleVO.setIdProgetto(idProgetto);
			rappLegaleVO.setIdSoggetto(idSoggettoRappresentante);
			
			List<RappresentanteLegaleDichiarazionePrivatoCittadinoVO> rappresentantiVO = genericDAO.findListWhere(rappLegaleVO);
			
			
			if (rappresentantiVO != null && rappresentantiVO.size() > 0) {
				logger.warn("********** Found "+rappresentantiVO.size()+" for idProgetto:"+idProgetto+" and idSoggettoRappresentante:"+idSoggettoRappresentante+" *****");
				rapprVO = rappresentantiVO.get(0);
			}
			
		}
		
		
		//Caso in cui il beneficiario non sia un privato cittadino
		else {
			
			RappresentanteLegaleDichiarazioneVO rappLegaleVO = new RappresentanteLegaleDichiarazioneVO();	
			rappLegaleVO.setIdProgetto(idProgetto);
			rappLegaleVO.setIdSoggetto(idSoggettoRappresentante);
			
			List<RappresentanteLegaleDichiarazioneVO> rappresentantiVO = genericDAO.findListWhere(rappLegaleVO);
			
			
			
			if (rappresentantiVO != null && rappresentantiVO.size() > 0) {
				logger.warn("********** Found "+rappresentantiVO.size()+" for idProgetto:"+idProgetto+" and idSoggettoRappresentante:"+idSoggettoRappresentante+" *****");
				rapprVO = rappresentantiVO.get(0);
			}
			
		}		
		

		return getBeanUtil().transform(rapprVO,RappresentanteLegaleDTO.class);
	}
	
	
	public List<RappresentanteLegaleDTO> findRappresentantiLegali(
			Long idProgetto,Long idSoggettoRappresentante) {
	 			RappresentanteLegaleDichiarazioneVO rappLegaleVO = new RappresentanteLegaleDichiarazioneVO();
			rappLegaleVO.setIdProgetto(idProgetto);
			rappLegaleVO.setIdSoggetto(idSoggettoRappresentante);
			List<RappresentanteLegaleDichiarazioneVO> rappresentantiVO = genericDAO.findListWhere(rappLegaleVO);
			return getBeanUtil().transformList(rappresentantiVO,RappresentanteLegaleDTO.class);	 
	}

	






}
