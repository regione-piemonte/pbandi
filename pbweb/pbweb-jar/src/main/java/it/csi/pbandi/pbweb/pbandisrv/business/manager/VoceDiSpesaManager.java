package it.csi.pbandi.pbweb.pbandisrv.business.manager;

import java.util.List;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.VoceDiSpesaAssociataVO;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;

public class VoceDiSpesaManager {
	private LoggerUtil logger;
	private BeanUtil beanUtil;

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
	
	private GenericDAO genericDAO;
	
	
	public List<VoceDiSpesaAssociataVO> findVociDiSpesaAssociate(VoceDiSpesaAssociataVO filtro) {
		List<VoceDiSpesaAssociataVO> voci = getGenericDAO().findListWhere(filtro);
		return voci;
	}


	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}


	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

}
