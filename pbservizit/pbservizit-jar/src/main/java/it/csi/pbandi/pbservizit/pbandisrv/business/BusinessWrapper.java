/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business;

import it.csi.csi.wrapper.CSIException;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SoggettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.tracciamento.TracciamentoSrv;
import it.csi.pbandi.pbservizit.pbandisrv.dto.tracciamento.TracciamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.index.IndexDAO;
import it.csi.pbandi.pbservizit.pbandisrv.util.spring.ThreadScopeRunnable;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.util.beanlocatorfactory.ServiceBeanLocator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.ejb.SessionContext;

public class BusinessWrapper {
	
	private static final String BUSINESS_WRAPPER_BEAN_NAME = "businessWrapper";

	private EjbSessionContextBean ejbSessionContext;

	private IndexDAO indexDAO;

	private LoggerUtil logger;

	private SoggettoManager soggettoManager;

	private TracciamentoSrv traceManagerBusiness;
	
	public static Object executeAnonymously(Object... params) throws Throwable {
		return getInstanceFromContext().doExecuteAnonymously(params);
	}

	public static Object execute(Object... params) throws Throwable {
		return getInstanceFromContext().doExecute(params);
	}

	public static void setEjbSessionContextInSpringContext(SessionContext ctx) {
		getInstanceFromContext().getEjbSessionContext().setEjbSession(ctx);
	}

	private static BusinessWrapper getInstanceFromContext() {
 
		BusinessWrapper bean = (BusinessWrapper) ServiceBeanLocator
				.getBeanByName(BUSINESS_WRAPPER_BEAN_NAME);
		return bean;
	}

	private static String getClassSimpleName(String clazz)
			throws ClassNotFoundException {
		Class<?> classInvocante = Class.forName(clazz);
		String classeInvocanteSimpleName = classInvocante.getSimpleName();
		return classeInvocanteSimpleName;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setSoggettoManager(SoggettoManager soggettoManager) {
		this.soggettoManager = soggettoManager;
	}

	public SoggettoManager getSoggettoManager() {
		return soggettoManager;
	}

	public void setTraceManagerBusiness(TracciamentoSrv traceManagerBusiness) {
		this.traceManagerBusiness = traceManagerBusiness;
	}

	public TracciamentoSrv getTraceManagerBusiness() {
		return traceManagerBusiness;
	}

	public void setEjbSessionContext(EjbSessionContextBean ejbSessionContext) {
		this.ejbSessionContext = ejbSessionContext;
	}

	public EjbSessionContextBean getEjbSessionContext() {
		return ejbSessionContext;
	}

	public void setIndexDAO(IndexDAO indexDAO) {
		this.indexDAO = indexDAO;
	}

	public IndexDAO getIndexDAO() {
		return indexDAO;
	}
 
	public Object doExecuteAnonymously(Object... params) throws Throwable {
		logger.warn("Chiamata anonima: utente non autenticato");
		return executeBusinessMethod(true, params);
	}

	public Object doExecute(Object... params) throws Throwable {
		return executeBusinessMethod(false, params);
	}

	private Object executeBusinessMethod(boolean anonymous, Object[] params)
			throws Exception, Throwable {
		eseguiValidazioneParametriGenerica(params);

		StackTraceElement traceElement = new Throwable().getStackTrace()[3];

		String classCompleteName = traceElement.getClassName();
	
		String classeInvocanteSimpleName = getClassSimpleName(classCompleteName);

		Object businessObjectBeanInstance = findBusinessObjectBeanInstanceByConventionFromSpringContext(classeInvocanteSimpleName);

		// nome del metodo di business da invocare
		String nomeMetodoDaInvocare = traceElement.getMethodName();
		

		// per trovare id operazione da tracciare
		String descOperazioneFisica = classeInvocanteSimpleName + "."
				+ nomeMetodoDaInvocare;
		// logger.debug("descOperazioneFisica dell'operazione da tracciare: "
		// + descOperazioneFisica);

		Method businessMethod = ObjectUtil.findMethod(
				businessObjectBeanInstance, nomeMetodoDaInvocare, params);
		Object result = executeBusinessMethod(businessObjectBeanInstance,
				businessMethod, params, descOperazioneFisica, anonymous);

		return result;
	}

	

	public Object executeBusinessMethod(Object businessObjectBeanInstance,
			Method businessMethod, Object[] params,
			String descOperazioneFisica, boolean anonymous) throws Throwable {
		if(!descOperazioneFisica.equalsIgnoreCase("GestioneDatiDiDominioImpl.getConfiguration")){
			logger.warn("@@@@@@@@@@@@@@@@@@@@@@\nservice <<<" +
			(businessObjectBeanInstance+"."+descOperazioneFisica)+">>>");
		}
		
		long start=System.currentTimeMillis();

		Object result = null;

		TracciamentoDTO tracciamentoDTO = null;
		try {
			
			 if(!descOperazioneFisica.equalsIgnoreCase("GestioneDatiDiDominioImpl.getConfiguration")){
			   tracciamentoDTO=executePreBusinessMethodInvocationHooks(params,
					descOperazioneFisica, anonymous,tracciamentoDTO);
			   }
			 
			result = businessMethod.invoke(businessObjectBeanInstance, params);
			
			if(!descOperazioneFisica.equalsIgnoreCase("GestioneDatiDiDominioImpl.getConfiguration")){
			 	executePostBusinessMethodInvocationHooks(tracciamentoDTO);
			}
		} catch (Throwable e) {
			executeBusinessMethodInvocationExceptionHooks(descOperazioneFisica,
					params, e,tracciamentoDTO);
		} finally {
			markEndForThreadScopedBeans();
			tracciamentoDTO=null;
			if(!descOperazioneFisica.equalsIgnoreCase("GestioneDatiDiDominioImpl.getConfiguration")){
				logger.warn("\nservice <<< " +
					(businessObjectBeanInstance+"."+descOperazioneFisica +" >>> executed in "+(System.currentTimeMillis()-start)+" ms\n\n\n\n\n\n"));
			}
		}
		return result;
	}

	private void executeBusinessMethodInvocationExceptionHooks(
			String descOperazioneFisica, Object[] params, Throwable e,TracciamentoDTO tracciamentoDTO)
			throws Throwable {
		logPerEccezioneBusinessMethod(descOperazioneFisica, params, e);
		getIndexDAO().rollbackNodesModifications();
		completaTracciamentoFallimentoEsecuzioneBusinessMethod(e,tracciamentoDTO);
	}

	private void logPerEccezioneBusinessMethod(String descOperazioneFisica,
			Object[] params, Throwable e) {
		logger.error("business method parameters :  " + descOperazioneFisica);
		logger.dumpError(params);
		logger.error("business method terminated with exception.",e);
	}

	private void executePostBusinessMethodInvocationHooks(TracciamentoDTO tracciamentoDTO) {
		try {
			completaTracciamentoSuccessoEsecuzioneBusinessMethod(tracciamentoDTO);
		} catch(Exception e) {
			logger.error(
					"Errore nel tracciamento. Exception gestita. (tracciamentoDTO: "
							+ tracciamentoDTO + ", traceManagerBusiness: "
							+ traceManagerBusiness + ").", e);
		}
	}

	private TracciamentoDTO executePreBusinessMethodInvocationHooks(Object[] params,
			String descOperazioneFisica, boolean anonymous,TracciamentoDTO tracciamentoDTO) throws CSIException {
	 
		Long idUtente=null;
		if(anonymous)
			idUtente=it.csi.pbandi.pbservizit.pbandiutil.common.Constants.ID_UTENTE_FITTIZIO;
		else{
			idUtente= (Long) params[0];
		}
		return iniziaTracciamentoEsecuzioneBusinessMethod(anonymous, idUtente,
				descOperazioneFisica,tracciamentoDTO);

	}

	private void markEndForThreadScopedBeans() {
		/*
		 * FIXME Paolo: ok, quello che segue dovr� essere scritto meglio per
		 * essere pi� chiaro, ma se non viene eseguito questo codice potremmo
		 * saturare la memoria dell'appserver e non eseguiamo i callback di
		 * distruzione dei bean di spring
		 * 
		 * teoricamente quando usiamo bean con scope thread dovremmo farlo da
		 * del codice eseguito in un threadscoperunnable perch� questo si
		 * preoccupa di pulire lo scope del thread corrente quando l'esecuzione
		 * termina cancellando i bean associati solo a quel thread
		 * 
		 * in ogni caso la pulizia del thread corrente avviene se questo codice
		 * viene eseguito
		 */
		new ThreadScopeRunnable(new Runnable() {
			public void run() {
			}
		}).run();
	}


	private Object findBusinessObjectBeanInstanceByConventionFromSpringContext(
			String classeInvocanteSimpleName) {
		String businessClassName = classeInvocanteSimpleName.substring(0, 1)
				.toLowerCase()
				+ classeInvocanteSimpleName.substring(1,
						classeInvocanteSimpleName.length() - 4) + "Business";

		Object objectBusinessClass = ServiceBeanLocator
				.getBeanByName(businessClassName);
		return objectBusinessClass;
	}

	private void eseguiValidazioneParametriGenerica(Object[] params) {
		try {
			ValidatorInput.verifyAtLeastOneNotNullValue(null, params);
		} catch (FormalParameterException e1) {
			RuntimeException re = new RuntimeException(
					"Parametri di input null!");
			re.initCause(e1);
			logger.fatal("input parameters null! Valorizzare idUtente !!!",
					re);
			throw re;
		}
	}

	private TracciamentoDTO iniziaTracciamentoEsecuzioneBusinessMethod(boolean anonymous,
			Long idUtente, String descOperazioneFisica,TracciamentoDTO tracciamentoDTO) {

		Long idTracciamento = null;
		
		idTracciamento = getTraceManagerBusiness().tracciaOperazione(
				descOperazioneFisica,idUtente);
		tracciamentoDTO=new TracciamentoDTO();
		tracciamentoDTO.setIdTracciamento(idTracciamento);
		tracciamentoDTO.setMethodStartTimeMillis(System.currentTimeMillis());

		return tracciamentoDTO;
	}

	private void completaTracciamentoSuccessoEsecuzioneBusinessMethod(TracciamentoDTO tracciamentoDTO) {
		
		getTraceManagerBusiness().tracciaEsitoPositivo(
				tracciamentoDTO.getIdTracciamento(),
				tracciamentoDTO.getMethodStartTimeMillis());
	}

	private void completaTracciamentoFallimentoEsecuzioneBusinessMethod(
			Throwable e,TracciamentoDTO tracciamentoDTO) throws Throwable {

		Throwable eccezioneDaTracciare = selezionaEccezioneDaTracciare(e);
		 if(traceManagerBusiness==null){
			 logger.error("\n\ntraceManagerBusiness is NULL \n\n\n\n");
		 }else{
			 
			 try{
				 traceManagerBusiness.tracciaEsitoNegativo(
							tracciamentoDTO.getIdTracciamento(),
							tracciamentoDTO.getMethodStartTimeMillis(),
							eccezioneDaTracciare.getMessage(), eccezioneDaTracciare);
			 }catch(Exception x){
				 logger.error("\nfatal error in  traceManagerBusiness.tracciaEsitoNegativo, tracciamentoDTO "+tracciamentoDTO+
						 " ,eccezioneDaTracciare "+eccezioneDaTracciare+
						 " exception message : "+x.getMessage());
			 }
		 }

		throw eccezioneDaTracciare;
	}

	private Throwable selezionaEccezioneDaTracciare(Throwable e) {
		Throwable eccezioneSelezionata = null;
		if (e instanceof InvocationTargetException) {
			Throwable cause = e.getCause();
			eccezioneSelezionata = cause;
		} else {
			eccezioneSelezionata = e;
		}
		return eccezioneSelezionata;
	}


}