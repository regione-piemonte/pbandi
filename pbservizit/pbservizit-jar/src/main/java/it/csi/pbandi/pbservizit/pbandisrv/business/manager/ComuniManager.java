/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDComuneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDProvinciaVO;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * </p>
 * 
 */

public class ComuniManager {
	
	private BeanUtil beanUtil; 
	private DecodificheManager decodificheManager;
	@Autowired
	private GenericDAO genericDAO;
	
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
	


	public PbandiDComuneVO[] findComune(Long idProvincia) throws Exception
{

			PbandiDComuneVO query = new PbandiDComuneVO();
			query.setIdProvincia(new BigDecimal(idProvincia));
			query.setAscendentOrder("descComune");
			PbandiDComuneVO[] comuni = genericDAO
					.findWhere(new AndCondition<PbandiDComuneVO>(
							new FilterCondition<PbandiDComuneVO>(query),
							new NullCondition<PbandiDComuneVO>(
									PbandiDComuneVO.class, "dtFineValidita")));

			return comuni;
	}

	

	public PbandiDProvinciaVO[] findProvince() throws Exception {
	
			PbandiDProvinciaVO filtro=new PbandiDProvinciaVO();
			filtro.setAscendentOrder("descProvincia");
			PbandiDProvinciaVO[] vo = genericDAO
					.findWhere(filtro);
//			IdDescBreveDescEstesaDTO[] dto = new IdDescBreveDescEstesaDTO[vo.length];
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("siglaProvincia", "descBreve");
//			map.put("descProvincia", "descEstesa");
//			map.put("idProvincia", "id");
//			beanUtil.valueCopy(vo, dto, map);
			return vo;
	}

	

}
