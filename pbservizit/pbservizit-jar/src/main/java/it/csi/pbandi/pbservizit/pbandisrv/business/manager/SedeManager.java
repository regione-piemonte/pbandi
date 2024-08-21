/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SedeProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SedeVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * Classe con costanti che rappresentano le descrizoni brevi delle decodifiche
 * (assunte come univoche). PBANDI_D_TIPO_SEDE
 * </p>
 * 
 */

public class SedeManager {
	
	@Autowired
	private BeanUtil beanUtil; 
	@Autowired
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
	

	public static String getCodiceTipoSedeIntervento() {
		return GestioneDatiDiDominioSrv.COD_TIPO_SEDE_SEDE_INTERVENTO;
	}
	
	public static String getCodiceTipoSedeLegale() {
		return GestioneDatiDiDominioSrv.COD_TIPO_SEDE_SEDE_LEGALE;
	}
	
	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}

	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}
	
	private SedeVO findSede(Long idProgetto, Long idSoggettoBeneficiario, String descBreveTipoSede) throws Exception {
		
		String[] nameParameter = { "idProgetto", "idSoggettoBeneficiario", "descBreveTipoSede"};
		
		ValidatorInput.verifyNullValue(nameParameter, idProgetto,
				idSoggettoBeneficiario,descBreveTipoSede);	
		
		SedeProgettoVO filtroVO = new SedeProgettoVO();
		filtroVO.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
		filtroVO.setIdSoggettoBeneficiario(beanUtil.transform(idSoggettoBeneficiario, BigDecimal.class));
		filtroVO.setDescBreveTipoSede(descBreveTipoSede);
		
		List<SedeVO> sedi = genericDAO.findListWhereDistinct(Condition.filterBy(filtroVO), SedeVO.class);
		if (ObjectUtil.isEmpty(sedi))
			return null;
		else 
			return sedi.get(0);
		
	}
	
	public SedeProgettoVO findSedeLegale(Long idProgetto, Long idSoggettoBeneficiario) throws Exception {
		//return findSede(idProgetto, idSoggettoBeneficiario, GestioneDatiDiDominioSrv.COD_TIPO_SEDE_SEDE_LEGALE);
	    String[] nameParameter = { "idProgetto", "idSoggettoBeneficiario", "descBreveTipoSede"};
		
		ValidatorInput.verifyNullValue(nameParameter, idProgetto,
				idSoggettoBeneficiario,GestioneDatiDiDominioSrv.COD_TIPO_SEDE_SEDE_LEGALE);	
		
		SedeProgettoVO filtroVO = new SedeProgettoVO();
		filtroVO.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
		filtroVO.setIdSoggettoBeneficiario(beanUtil.transform(idSoggettoBeneficiario, BigDecimal.class));
		filtroVO.setDescBreveTipoSede(GestioneDatiDiDominioSrv.COD_TIPO_SEDE_SEDE_LEGALE);
		
		List<SedeProgettoVO> sedi = genericDAO.findListWhereDistinct(Condition.filterBy(filtroVO), SedeProgettoVO.class);
		if (ObjectUtil.isEmpty(sedi))
			return null;
		else 
			return sedi.get(0);
	
	}
	
	
	public SedeVO findSedeIntervento(Long idProgetto, Long idSoggettoBeneficiario) throws Exception {
		return findSede(idProgetto, idSoggettoBeneficiario, GestioneDatiDiDominioSrv.COD_TIPO_SEDE_SEDE_INTERVENTO);
	}



	/**
	 * Ricerca le sedi di intervento del progetto per il beneficiario
	 * @param idProgetto
	 * @param idSoggettoBeneficiario
	 * @return
	 */
	public List<SedeProgettoVO>  findSediIntervento(BigDecimal idProgetto, BigDecimal idSoggettoBeneficiario) {
		DecodificaDTO tipoSede = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.TIPO_SEDE, Constants.TIPO_SEDE_INTERVENTO);
		SedeProgettoVO filtroSedeVO = new SedeProgettoVO();
		filtroSedeVO.setIdProgetto(idProgetto);
		filtroSedeVO.setIdSoggettoBeneficiario(idSoggettoBeneficiario);
		filtroSedeVO.setIdTipoSede(NumberUtil.toBigDecimal(tipoSede.getId()));
		filtroSedeVO.setDescendentOrder("descNazione","descRegione","descProvincia","descComune");
		
		return genericDAO.findListWhere(filtroSedeVO);
	}
	
	/**
	 * Restituisce tutte le sedi di un progetto per il beneficiario
	 */
	public List<SedeProgettoVO> findSediProgetto(BigDecimal idProgetto, BigDecimal idSoggettoBeneficiario){
		SedeProgettoVO filtroSedi = new SedeProgettoVO();
		filtroSedi.setIdProgetto(idProgetto);
		filtroSedi.setIdSoggettoBeneficiario(idSoggettoBeneficiario);
		filtroSedi.setDescendentOrder("descNazione","descRegione","descProvincia","descComune");
		return genericDAO.findListWhere(filtroSedi);
	}

	/**
	 * Restituisce la sede amministrativa di un progetto per il beneficiario, in base al seguente criterio:
	 * se esiste una sede amministrativa, restituisce quella
	 * altrimenti, se esitono delle sedi intervento, restituisce la prima di esse
	 * altrimenti restituisce la sede legale.
	 */
	public SedeVO  findSedeAmministrativa(Long idProgetto, Long idSoggettoBeneficiario) throws Exception {
		
		String[] nameParameter = { "idProgetto", "idSoggettoBeneficiario"};		
		ValidatorInput.verifyNullValue(nameParameter, idProgetto, idSoggettoBeneficiario);	
		
		// Cerca la sede amministrativa.
		SedeProgettoVO filtroSedeAmmVO = new SedeProgettoVO();
		filtroSedeAmmVO.setIdProgetto(new BigDecimal(idProgetto));
		
		//
		filtroSedeAmmVO.setDescBreveTipoSede(Constants.DESC_BREVE_TIPO_SEDE_INTERVENTO);
		
		filtroSedeAmmVO.setIdSoggettoBeneficiario(new BigDecimal(idSoggettoBeneficiario));
		filtroSedeAmmVO.setFlagSedeAmministrativa("S");		
		List<SedeVO> sediAmm = genericDAO.findListWhereDistinct(Condition.filterBy(filtroSedeAmmVO), SedeVO.class);
		if (sediAmm != null && sediAmm.size() > 0)
			return sediAmm.get(0);
			
		// Cerca la sede intervento.
		SedeVO sedeInt = this.findSedeIntervento(idProgetto, idSoggettoBeneficiario);
		if (sedeInt != null)
			return sedeInt;
		
		// Cerca la sede legale.
		SedeProgettoVO sedeProgettoVO = this.findSedeLegale(idProgetto, idSoggettoBeneficiario);
		if (sedeProgettoVO != null) {
			SedeVO sedeLeg = beanUtil.transform(sedeProgettoVO, SedeVO.class);
			return sedeLeg;
		}
		
		return null;
	
	}
	

}
