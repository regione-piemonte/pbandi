/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.util.spring;

import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class ThreadScope implements Scope {

    /**
     * Gets bean from scope.
     */
    public Object get(String name, ObjectFactory factory) {
        Object result = null;
        
        Map<String, Object> hBeans = ThreadScopeContextHolder.currentThreadScopeAttributes().getBeanMap();
        
        if (!hBeans.containsKey(name)) {
            result = factory.getObject();
            
            hBeans.put(name, result);
        } else {
            result = hBeans.get(name);
        }
        
        
        return result;
    }

    /**
     * Removes bean from scope.
     */
    public Object remove(String name) {
        Object result = null;
        
        Map<String, Object> hBeans = ThreadScopeContextHolder.currentThreadScopeAttributes().getBeanMap();

        if (hBeans.containsKey(name)) {
            result = hBeans.get(name);
            
            hBeans.remove(name);
        }

        return result;
    }

    public void registerDestructionCallback(String name, Runnable callback) {
        ThreadScopeContextHolder.currentThreadScopeAttributes().registerRequestDestructionCallback(name, callback);
    }

    /**
     * Gets conversation id.  Not implemented.
     */
    public String getConversationId() {
        return null;
    }

	@Override
	public Object resolveContextualObject(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}