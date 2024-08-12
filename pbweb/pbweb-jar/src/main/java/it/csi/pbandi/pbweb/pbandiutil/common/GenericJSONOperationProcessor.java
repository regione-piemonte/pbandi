package it.csi.pbandi.pbweb.pbandiutil.common;

import it.csi.pbandi.pbweb.pbandiutil.common.util.json.JSONArray;
import it.csi.pbandi.pbweb.pbandiutil.common.util.json.JSONException;
import it.csi.pbandi.pbweb.pbandiutil.common.util.json.JSONObject;
import it.csi.pbandi.pbweb.pbandiutil.common.util.json.JSONStringer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class GenericJSONOperationProcessor implements ApplicationContextAware {
	public static final String LOCAL_CONTEXT_NAME = "local";
	private LoggerUtil logger;
	private BeanUtil beanUtil;

	private ApplicationContext applicationContext;

	public interface ContextHandler {

		String process(JSONObject jsonRequest) throws Exception;
	}

	public String process(String request) {
		return process(request, null);
	}

	public String process(String request,
			Map<String, ContextHandler> contextHandlers) {
		logger.begin();

		String response = null;
		try {
			JSONObject jsonRequest = createRequestJSONObject(request);

			ContextHandler contextHandler = getContextHandler(contextHandlers,
					jsonRequest);

			if (contextHandler != null) {
				response = contextHandler.process(jsonRequest);
			}

			if (response == null) {
				response = jsonRequest.toString();
			}

			logger.dump(response);
		} catch (Exception e) {
			try {
				getLogger().error(
						"Exception parsing request: " + request + " (cause: "
								+ e.getMessage() + ")", e);
				response = new JSONStringer().object().key("error")
						.value(e.getMessage()).endObject().toString();
			} catch (JSONException e1) {
				getLogger().warn(
						"Exception in producing error response: "
								+ e.getMessage());
				response = "{ error : 'Error producing error message.' }";
			}
		} finally {
			logger.end();
		}

		return response;
	}

	private ContextHandler getContextHandler(
			Map<String, ContextHandler> contextHandlers, JSONObject jsonRequest) {
		String contextName = jsonRequest.optString("context");
		contextName = contextName == null ? LOCAL_CONTEXT_NAME : contextName;

		contextHandlers = contextHandlers == null ? new HashMap<String, ContextHandler>()
				: contextHandlers;
		ContextHandler localContextHandler = new ContextHandler() {

			public String process(JSONObject jsonRequest) throws Exception {
				String response = null;

				if (jsonRequest.has("type")
						&& "beanInvocation".equals(jsonRequest
								.getString("type"))) {

					Object bean = applicationContext.getBean(jsonRequest
							.getString("name"));

					Object[] params = null;
					if (jsonRequest.has("params")) {
						JSONArray jsonParams = jsonRequest
								.getJSONArray("params");
						params = new Object[jsonParams.length()];
						for (int i = 0; i < jsonParams.length(); i++) {
							params[i] = jsonParams.get(i);
						}

						logger.dump(params);
					}

					Method method = ObjectUtil.findMethod(bean,
							jsonRequest.getString("operation"), params);

					Class<?>[] parameterTypes = method.getParameterTypes();
					Object[] convertedParams = new Object[parameterTypes.length];
					for (int i = 0; i < parameterTypes.length; i++) {
						Object value = i < params.length ? params[i] : null;

						String valueTypeName = value == null ? "unknown"
								: value.getClass().toString();
						Class<?> destType = parameterTypes[i];

						logger.debug("param " + valueTypeName + " -> "
								+ destType.toString());

						convertedParams[i] = beanUtil
								.transform(value, destType);
					}

					logger.dump(convertedParams);
					Object result = method.invoke(bean, convertedParams);

					logger.dump(result);

					JSONObject jsonResponse = new JSONObject();
					jsonResponse.put("response", JSONObject.wrap(result));
					response = jsonResponse.toString();
				}

				return response;
			}

		};
		contextHandlers.put(LOCAL_CONTEXT_NAME, localContextHandler);

		logger.debug("Current contextName:" + contextName);
		ContextHandler contextHandler = contextHandlers.get(contextName);
		contextHandler = contextHandler == null ? localContextHandler : contextHandler;
		
		return contextHandler;
	}

	private JSONObject createRequestJSONObject(String request)
			throws JSONException {
		JSONObject jsonRequest = new JSONObject(request == null ? "" : request);
		logger.dump(jsonRequest);
		return jsonRequest;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

}
