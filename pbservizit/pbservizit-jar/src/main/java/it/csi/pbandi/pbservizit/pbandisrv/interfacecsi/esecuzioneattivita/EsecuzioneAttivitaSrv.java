/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.esecuzioneattivita;

import it.csi.pbandi.pbservizit.pbandisrv.dto.esecuzioneattivita.*;
import it.csi.pbandi.pbservizit.pbandisrv.exception.esecuzioneattivita.*;

////{PROTECTED REGION ID(R-903221666) ENABLED START////}
/**
 * Inserire qui la documentazione dell'interfaccia pubblica del servizio esecuzione_attivita.
 * Consigli:
 * <ul>
 * <li> Descrivere qual'� lo scopo generale del servizio
 * <li> Se necessario fornire una overview delle funzioni messe a disposizione
 *      eventualmente raggruppandole logicamente. Il dettaglio dei singoli
 *      metodi va documentato in corrispondenza dei metodi stessi
 * <li> Se necessario descrivere gli scenari di utilizzo pi� frequenti, ovvero
 *      le "coreografie" (nel caso sia necessario richiamare in una sequenza
 *      particolare i metodi
 * <li> Inserire informazioni quali il livello di securizzazione A0-A3
 * <li> Inserire varie ed eventuali... 
 * </ul>
 * @generated
 */
////{PROTECTED REGION END////}
public interface EsecuzioneAttivitaSrv {

	////{PROTECTED REGION ID(R664370875) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo caricaLongProcessVariableForIstanzaAttivita.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param codUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param nomeVariabile [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param istanzaAttivita [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return java.lang.Long
	 
	 * @throws EsecuzioneAttivitaException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long caricaLongProcessVariableForIstanzaAttivita(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			java.lang.String codUtente,

			java.lang.String nomeVariabile,

			it.csi.pbandi.pbservizit.pbandisrv.dto.esecuzioneattivita.IstanzaAttivitaDTO istanzaAttivita

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.esecuzioneattivita.EsecuzioneAttivitaException;

	////{PROTECTED REGION ID(R-221729917) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo avviaAttivita.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param istanzaAttivita [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return void
	 
	 * @throws EsecuzioneAttivitaException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.esecuzioneattivita.IstanzaAttivitaDTO avviaAttivita(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			java.lang.String codUtente,

			it.csi.pbandi.pbservizit.pbandisrv.dto.esecuzioneattivita.IstanzaAttivitaDTO istanzaAttivita

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.esecuzioneattivita.EsecuzioneAttivitaException;

	////{PROTECTED REGION ID(R492212956) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo chiudiAttivita.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param codUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param istanzaAttivita [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return void
	 
	 * @throws EsecuzioneAttivitaException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public void chiudiAttivita(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			java.lang.String codUtente,

			it.csi.pbandi.pbservizit.pbandisrv.dto.esecuzioneattivita.IstanzaAttivitaDTO istanzaAttivita

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.esecuzioneattivita.EsecuzioneAttivitaException;

	////{PROTECTED REGION ID(R186313781) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo concludiAttivita.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param codUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param istanzaAttivita [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return void
	 
	 * @throws EsecuzioneAttivitaException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public void concludiAttivita(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			java.lang.String codUtente,

			it.csi.pbandi.pbservizit.pbandisrv.dto.esecuzioneattivita.IstanzaAttivitaDTO istanzaAttivita

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.esecuzioneattivita.EsecuzioneAttivitaException;

}
