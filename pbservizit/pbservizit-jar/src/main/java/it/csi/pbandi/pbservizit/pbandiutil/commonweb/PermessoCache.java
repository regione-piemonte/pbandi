/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandiutil.commonweb;

import java.io.ObjectStreamException;

import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.profilazione.ProfilazioneSrv;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.util.beanlocatorfactory.ServiceBeanLocator;

public class PermessoCache<Boolean> extends Cache<Boolean> {
	
	transient private ProfilazioneSrv profilazioneSrv = (ProfilazioneSrv) ServiceBeanLocator.getBeanByName("profilazioneSrv");

	@Override
	protected Boolean getOnMiss(Object... params) throws Exception {
		return (Boolean) profilazioneSrv.hasPermesso((Long) params[0],
				(String) params[1], (String) params[2],
				(String) params[3]);
	}
	
	private Object readResolve () throws ObjectStreamException {
		if (logger == null)
			logger = (LoggerUtil) ServiceBeanLocator.getBeanByName("loggerUtil");
		if (profilazioneSrv == null)
			profilazioneSrv = (ProfilazioneSrv) ServiceBeanLocator.getBeanByName("profilazioneSrv");
		return this;
	}

}
