/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.checklisthtml;

import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.EsitoSalvaModuloCheckListDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.*;
import it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.*;

////{PROTECTED REGION ID(R2128359999) ENABLED START////}
/**
 * Inserire qui la documentazione dell'interfaccia pubblica del servizio checklist_html.
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
public interface ChecklistHtmlSrv {

	////{PROTECTED REGION ID(R1782561952) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo saveCheckListDocumentaleHtml.
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
	 	
	 
	 * @param codStatoTipoDocIndex [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param checklistHtmlDTO [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return java.lang.Boolean
	 
	 * @throws ChecklistHtmlException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.EsitoSalvaModuloCheckListHtmlDTO saveCheckListDocumentaleHtml(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			java.lang.Long idProgetto,

			java.lang.String codStatoTipoDocIndex,

			it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO checklistHtmlDTO,

			java.lang.Long idDichiarazione

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException;

	////{PROTECTED REGION ID(R626126040) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getModuloCheckListInLocoHtml.
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
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.CheckListHtmlDTO
	 
	 * @throws CheckListHtmlException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO getModuloCheckListInLocoHtml(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto,

	java.lang.String soggettoControllore

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException;

	////{PROTECTED REGION ID(R-1615727122) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getCheckListInLocoHtml.
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
	 	
	 
	 * @param idChecklist [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.CheckListHtmlDTO
	 
	 * @throws CheckListHtmlException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO getCheckListInLocoHtml(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idDocumentoIndex

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException;

	////{PROTECTED REGION ID(R1215461961) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo saveCheckListInLocoHtml.
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
	 	
	 
	 * @param codStatoTipoDocIndex [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param  [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.EsitoSalvaModuloCheckListDTO
	 
	 * @throws CheckListException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public EsitoSalvaModuloCheckListDTO saveCheckListInLocoHtml(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto,

	java.lang.String codStatoTipoDocIndex,

	it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO checklistHtmlDTO

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException;

	////{PROTECTED REGION ID(R-1475703532) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo eliminaCheckList.
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
	 	
	 
	 * @param idChecklist [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.EsitoEliminaCheckListDTO
	 
	 * @throws ChecklistHtmlException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.EsitoEliminaCheckListHtmlDTO eliminaCheckList(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idDocumentoIndex

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException;

	////{PROTECTED REGION ID(R-1842765980) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getModuloCheckListValidazioneHtml.
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
	 	
	 
	 * @param soggettoControllore [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO
	 
	 * @throws ChecklistHtmlException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO getModuloCheckListValidazioneHtml(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto,

	java.lang.String soggettoControllore,

	java.lang.Long idDichiarazione

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException;

	////{PROTECTED REGION ID(R351392782) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getCheckListValidazioneHtml.
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
	 	
	 
	 * @param idDocumentoIndex [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idDichiarazione [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO
	 
	 * @throws ChecklistHtmlException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO getCheckListValidazioneHtml(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idDocumentoIndex,

	java.lang.Long idDichiarazione,

	java.lang.Long idProgetto

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException;

	////{PROTECTED REGION ID(R-1269686754) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getModuloCheckListAffidamentoHtml.
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
	 	
	 
	 * @param idAffidamento [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idProgetto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param soggettoControllore [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param codTipoChecklist [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO
	 
	 * @throws ChecklistHtmlException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO getModuloCheckListAffidamentoHtml(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idAffidamento,

	java.lang.Long idProgetto,

	java.lang.String soggettoControllore,

	java.lang.String codTipoChecklist

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException;

	////{PROTECTED REGION ID(R73622024) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo saveCheckListAffidamentoDocumentaleHtml.
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
	 	
	 
	 * @param codStatoTipoDocIndex [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param checklistHtmlDTO [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idAffidamento [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.EsitoSalvaModuloCheckListHtmlDTO
	 
	 * @throws ChecklistHtmlException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.EsitoSalvaModuloCheckListHtmlDTO saveCheckListAffidamentoDocumentaleHtml(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			java.lang.Long idProgetto,

			java.lang.String codStatoTipoDocIndex,

			it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistAffidamentoHtmlDTO checklistHtmlDTO,

			java.lang.Long idAffidamento,

			java.lang.Boolean isRichiestaIntegrazione,

			java.lang.String noteRichiestaIntegrazione

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException;

	////{PROTECTED REGION ID(R665261025) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo saveCheckListAffidamentoInLocoHtml.
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
	 	
	 
	 * @param codStatoTipoDocIndex [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param checklistHtmlDTO [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idAffidamento [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.EsitoSalvaModuloCheckListHtmlDTO
	 
	 * @throws ChecklistHtmlException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.EsitoSalvaModuloCheckListHtmlDTO saveCheckListAffidamentoInLocoHtml(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			java.lang.Long idProgetto,

			java.lang.String codStatoTipoDocIndex,

			it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistAffidamentoHtmlDTO checklistHtmlDTO,

			java.lang.Long idAffidamento

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.checklisthtml.ChecklistHtmlException;

}
