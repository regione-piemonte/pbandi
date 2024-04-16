/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RegolaManager;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.MessaggiConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbworkspace.util.BeanUtil;


public abstract class BusinessImpl  implements Constants, ErrorConstants, MessaggiConstants, RegoleConstants{

	@Autowired
	protected LoggerUtil logger;
	
	@Autowired
	protected BeanUtil beanUtil;
	
	public LoggerUtil getLogger() {
		return logger;
	}
	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	@Autowired
	protected DecodificheManager decodificheManager;
	
	@Autowired
	protected RegolaManager regolaManager ;
	
	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}
	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}	
	
	public void setRegolaManager(RegolaManager regolaManager) {
		this.regolaManager = regolaManager;
	}
	public RegolaManager getRegolaManager() {
		return regolaManager;
	}
	
	protected boolean isNull(Object o){
		return ObjectUtil.isNull(o);		
	}
	protected boolean isEmpty(String o){
		return ObjectUtil.isEmpty(o);		
	}
	
	protected boolean isEmpty(Object[]o){
		return ObjectUtil.isEmpty(o);		
	}
	
	protected boolean isEmpty(java.util.List<?> o){
		return ObjectUtil.isEmpty(o);
				
	}
	protected boolean isTrue(Boolean o){
		return ObjectUtil.isTrue(o);		
	}
	
	protected boolean isMaggioreDiZero(Long o){
		if (o==null){
			return false;
		}
		if(o.longValue()>0)
			return true;
		
		return false;
	}
	
	protected boolean isMaggioreDiZero(Double o){
		if (o==null){
			return false;
		}
		if(o.doubleValue()>0)
			return true;
		
		return false;
	}
	
	public void setBeanUtil(BeanUtil beanUtils) {
		this.beanUtil = beanUtils;
	}
	
	public BeanUtil getBeanUtil() {
		return beanUtil;
	}



	
}
