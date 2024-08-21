/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.neoflux;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

////{PROTECTED REGION ID(R279772230) ENABLED START////}
/**
 * Inserire qui la documentazione dell'interfaccia pubblica del servizio neoflux.
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
public interface NeofluxSrv {

	////{PROTECTED REGION ID(R749109820) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo cancelNotificationMessage.
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
	 	
	 
	 * @param idNotifica [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return void
	 
	 * @throws NeoFluxException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public void cancelNotificationMessage(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idNotifica

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

	////{PROTECTED REGION ID(R-903512003) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo endAttivita.
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
	 	
	 
	 * @param idProgetto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param descBreveTask [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return java.lang.String
	 
	 * @throws NeoFluxException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public void endAttivita(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto,

	java.lang.String descBreveTask

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

	////{PROTECTED REGION ID(R-1583293512) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getAttivita.
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
	 	
	 
	 * @param idProgetto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idRuolo [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.neoflux.TaskDTO[]
	 
	 * @throws NeoFluxException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.neoflux.TaskDTO[] getAttivita(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto,

	java.lang.String descBreveTipoAnagrafica

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

	////{PROTECTED REGION ID(R-566105677) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getAttivitaBEN.
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
	 	
	 
	 * @param progrBandoLineaIntervento [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.neoflux.TaskDTO[]
	 
	 * @throws NeoFluxException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.neoflux.TaskDTO[] getAttivitaBEN(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String descBreveTipoAnagrafica,

	java.lang.Long idSoggettoBen,

	java.lang.Long progrBandoLineaIntervento,

	java.lang.Long idProgetto,

	java.lang.String descrTask

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

	////{PROTECTED REGION ID(R-1126640126) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getAttivitaBL.
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
	 	
	 
	 * @param progrBandoLineaIntervento [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.neoflux.TaskDTO[]
	 
	 * @throws NeoFluxException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.neoflux.TaskDTO[] getAttivitaBL(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long progrBandoLineaIntervento,

	java.lang.String descBreveTipoAnagrafica

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

	////{PROTECTED REGION ID(R14138724) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getBandi.
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
	 	
	 
	 * @param idSoggetto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param ruoloUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idSoggettoBen [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.neoflux.BandoDTO[]
	 
	 * @throws NeoFluxException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.neoflux.BandoDTO[] getBandi(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idSoggetto,

	java.lang.String descBreveTipoAnagrafica,

	java.lang.Long idSoggettoBen

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

	////{PROTECTED REGION ID(R-214171348) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getNotificationMessage.
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
	 	
	 
	 * @param idNotifica [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.neoflux.NotificaDTO
	 
	 * @throws NeoFluxException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.neoflux.NotificaDTO getNotificationMessage(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idNotifica

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

	////{PROTECTED REGION ID(R1159833936) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getProcesso.
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
	 	
	 
	 * @param idProgetto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return java.lang.String
	 
	 * @throws NeoFluxException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getProcesso(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

	////{PROTECTED REGION ID(R-2091082342) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getProcessoBL.
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
	 	
	 
	 * @param progrBandoLineaIntervento [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return java.lang.String
	 
	 * @throws NeoFluxException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getProcessoBL(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long progrBandoLineaIntervento

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

	////{PROTECTED REGION ID(R1163529006) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getProgetti.
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
	 	
	 
	 * @param idSoggettoBen [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param progrBandoLineaIntervento [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.neoflux.BandoDTO[]
	 
	 * @throws NeoFluxException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.neoflux.ProgettoDTO[] getProgetti(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idSoggettoBen,

	java.lang.Long progrBandoLineaIntervento

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

	////{PROTECTED REGION ID(R1781752706) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo isAttivitaLocked.
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
	 	
	 
	 * @param idProgetto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param descBreveTask [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return java.lang.Boolean
	 
	 * @throws NeoFluxException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public java.lang.String isAttivitaLocked(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto,

	java.lang.String descBreveTask

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

	////{PROTECTED REGION ID(R-1541722556) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo startAttivita.
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
	 	
	 
	 * @param idProgetto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param descBreveTask [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return java.lang.String
	 
	 * @throws NeoFluxException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public java.lang.String startAttivita(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto,

	java.lang.String descBreveTask

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

	////{PROTECTED REGION ID(R477735794) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo unlockAttivita.
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
	 	
	 
	 * @param idProgetto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param descBreveTask [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return java.lang.String
	 
	 * @throws NeoFluxException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public void unlockAttivita(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto,

	java.lang.String descBreveTask

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;
	
	public void endAttivitaDsFinale(java.lang.Long idUtente, java.lang.String identitaDigitale,
			java.lang.Long idProgetto) throws it.csi.csi.wrapper.CSIException, it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

}
