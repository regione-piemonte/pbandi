/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.swagger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import io.swagger.jaxrs.config.BeanConfig;

public class Bootstrap extends HttpServlet{
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	    public void init(ServletConfig config) throws ServletException {
	        super.init(config);

	        BeanConfig beanConfig = new BeanConfig();
	        beanConfig.setVersion("1.0.2");
	        beanConfig.setSchemes(new String[]{"http"});
	        beanConfig.setHost("localhost:4260");
	        beanConfig.setBasePath("/finanziamenti/bandi/pbwebrce/restfacade");
	        beanConfig.setResourcePackage("it.csi.pbandi.pbwebrce.business.service");
	        beanConfig.setScan(true);
	    } 
}
