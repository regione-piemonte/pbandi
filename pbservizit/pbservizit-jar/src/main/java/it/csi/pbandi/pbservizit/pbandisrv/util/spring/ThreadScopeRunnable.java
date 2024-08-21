/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.util.spring;

public class ThreadScopeRunnable implements Runnable {
    
    protected Runnable target = null;

    /**
     * Constructor
     */
    public ThreadScopeRunnable(Runnable target) {
        this.target = target;
    }

    /**
     * Runs <code>Runnable</code> target and 
     * then afterword processes thread scope 
     * destruction callbacks.
     */
    public final void run() {
        try {
            target.run();
        } finally {
            ThreadScopeContextHolder.currentThreadScopeAttributes().clear();
        }
    }

}