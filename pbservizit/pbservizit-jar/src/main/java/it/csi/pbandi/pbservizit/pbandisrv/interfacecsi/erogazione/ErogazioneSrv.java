/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.erogazione;

import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.*;
import it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.*;

////{PROTECTED REGION ID(R-1513603658) ENABLED START////}
/**
 * Inserire qui la documentazione dell'interfaccia pubblica del servizio erogazione.
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
public interface ErogazioneSrv {

	////{PROTECTED REGION ID(R970314346) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findErogazione.
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
	 	
	 
	 * @param idCausaleErogazione [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.ErogazioneDTO
	 
	 * @throws ErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoErogazioneDTO findErogazione(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto,

	java.lang.Long idBandoLinea

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.ErogazioneException;

	////{PROTECTED REGION ID(R-1589156306) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findCausaliErogazione.
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
	 	
	 
	 * @param idBando [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.CodiceDescrizioneDTO[]
	 
	 * @throws ErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.CodiceDescrizioneDTO[] findCausaliErogazione(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idBando,

	java.lang.Long idFormaGiuridica,

	java.lang.Long idDimensioneImpresa

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.ErogazioneException;

	////{PROTECTED REGION ID(R-1261917136) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo inserisciErogazione.
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
	 	
	 
	 * @param erogazione [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoErogazioneDTO
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoErogazioneDTO inserisciErogazione(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto,

	it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.ErogazioneDTO erogazione,

	java.lang.String codUtente,

	it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.IstanzaAttivitaDTO istanzaAttivita

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.ErogazioneException;

	////{PROTECTED REGION ID(R-889176948) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findPagamentiQuietanzati.
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
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.PagamentoDTO[]
	 
	 * @throws ErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.PagamentoDTO[] findPagamentiQuietanzati(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.ErogazioneException;

	////{PROTECTED REGION ID(R-945532524) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo associaPagamentiQuietanzati.
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
	 	
	 
	 * @param idPagamenti [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idErogazione [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoOperazioneAssociazioneDTO
	 
	 * @throws ErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoOperazioneAssociazioneDTO associaPagamentiQuietanzati(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long[] idPagamenti,

	java.lang.Long idErogazione

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.ErogazioneException;

	////{PROTECTED REGION ID(R-1787676521) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findDettaglioErogazione.
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
	 	
	 
	 * @param idErogazione [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoErogazioneDTO
	 
	 * @throws ErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoErogazioneDTO findDettaglioErogazione(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idErogazione,

	java.lang.Long idFormaGiuridica,

	java.lang.Long idDimensioneImpresa,

	java.lang.Long idBandoLinea

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.ErogazioneException;

	////{PROTECTED REGION ID(R1281416569) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo modificaErogazione.
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
	 	
	 
	 * @param erogazione [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoErogazioneDTO
	 
	 * @throws ErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoErogazioneDTO modificaErogazione(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto,

	it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.ErogazioneDTO erogazione

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.ErogazioneException;

	////{PROTECTED REGION ID(R-1820450698) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo eliminaErogazione.
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
	 	
	 
	 * @param idErogazione [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoErogazioneDTO
	 
	 * @throws ErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoErogazioneDTO eliminaErogazione(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idErogazione

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.ErogazioneException;

	////{PROTECTED REGION ID(R-744437576) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findModalitaAgevolazionePerContoEconomico.
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
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.CodiceDescrizioneDTO[]
	 
	 * @throws ErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.CodiceDescrizioneDTO[] findModalitaAgevolazionePerContoEconomico(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.ErogazioneException;

	////{PROTECTED REGION ID(R1400449431) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findDettagliAggiuntiviErogazione.
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
	 	
	 
	 * @param erogazione [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoErogazioneDTO
	 
	 * @throws ErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.ErogazioneDTO findDettagliAggiuntiviErogazione(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.ErogazioneDTO erogazione

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.ErogazioneException;

	////{PROTECTED REGION ID(R2022555203) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo inviaDichiarazioneDiRinuncia.
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
	 	
	 
	 * @param dichiarazione [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return java.lang.Long
	 
	 * @throws ErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.DichiarazioneDiRinunciaDTO inviaDichiarazioneDiRinuncia(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.DichiarazioneDiRinunciaDTO dichiarazione,

			it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.IstanzaAttivitaDTO istanzaAttivita

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.ErogazioneException;

	////{PROTECTED REGION ID(R-2004098938) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo scaricaDichiarazioneDiRinuncia.
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
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoScaricaDichiarazioneDiRinuncia
	 
	 * @throws ErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.EsitoScaricaDichiarazioneDiRinuncia scaricaDichiarazioneDiRinuncia(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String uuidNodo

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.ErogazioneException;

	////{PROTECTED REGION ID(R-1781211531) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo isRinunciaPresente.
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
	 
	 * @param idProgetto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return java.lang.Boolean
	 
	 * @throws ErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public java.lang.Boolean isRinunciaPresente(

	java.lang.Long idProgetto

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.erogazione.ErogazioneException;

}
