package it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestioneaffidamenti;

import it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.*;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.*;

////{PROTECTED REGION ID(R-933253890) ENABLED START////}
/**
 * Inserire qui la documentazione dell'interfaccia pubblica del servizio gestione_affidamenti.
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
public interface GestioneAffidamentiSrv {

	////{PROTECTED REGION ID(R471019315) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findElencoAffidamenti.
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
	 	
	 
	 * @param identitaIride [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param filtro [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return void
	 
	 * @throws GestioneAffidamentiException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO[] findElencoAffidamenti(

			java.lang.Long idUtente,

			java.lang.String identitaIride,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.FiltroRicercaAffidamentiDTO filtro

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;

	////{PROTECTED REGION ID(R-1635995301) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findVarianti.
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
	 	
	 
	 * @param identitaIride [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param filtroVarianteDTO [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAppalti
	 
	 * @throws GestioneAppaltiException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.VarianteAffidamentoDTO[] findVarianti(

			java.lang.Long idUtente,

			java.lang.String identitaIride,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.VarianteAffidamentoDTO filtroVarianteDTO

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;

	////{PROTECTED REGION ID(R1880081129) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo creaVariante.
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
	 	
	 
	 * @param identitaIride [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param varianteDTO [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAppalti
	 
	 * @throws GestioneAppaltiException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti creaVariante(

			java.lang.Long idUtente,

			java.lang.String identitaIride,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.VarianteAffidamentoDTO varianteDTO

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;

	////{PROTECTED REGION ID(R632907475) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo eliminaVariante.
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
	 	
	 
	 * @param identitaIride [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idVariante [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAppalti
	 
	 * @throws GestioneAppaltiException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti eliminaVariante(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	java.lang.Long idVariante

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;

	////{PROTECTED REGION ID(R10124801) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo salvaAffidamento.
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
	 	
	 
	 * @param identitaIride [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param affidamentoDTO [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti
	 
	 * @throws GestioneAffidamentiException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti salvaAffidamento(

			java.lang.Long idUtente,

			java.lang.String identitaIride,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO affidamentoDTO

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;

	////{PROTECTED REGION ID(R637457561) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo eliminaAffidamento.
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
	 	
	 
	 * @param identitaIride [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idAppalto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti
	 
	 * @throws GestioneAffidamentiException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti eliminaAffidamento(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	java.lang.Long idAppalto

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;

	////{PROTECTED REGION ID(R1950043029) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findAffidamento.
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
	 	
	 
	 * @param identitaIride [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idAppalto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO
	 
	 * @throws GestioneAffidamentiException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO findAffidamento(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	java.lang.Long idAppalto

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;

	////{PROTECTED REGION ID(R-1273678921) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo inviaInVerifica.
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
	 	
	 
	 * @param identitaIride [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idAppalto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti
	 
	 * @throws GestioneAffidamentiException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti inviaInVerifica(

			java.lang.Long idUtente,

			java.lang.String identitaIride,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.ParamInviaInVerificaDTO paramInviaInVerificaDTO

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;

	////{PROTECTED REGION ID(R-310166515) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findFornitoriAffidamento.
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
	 	
	 
	 * @param identitaIride [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param filtroFornitoreAffidamentoDTO [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO[]
	 
	 * @throws GestioneAffidamentiException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO[] findFornitoriAffidamento(

			java.lang.Long idUtente,

			java.lang.String identitaIride,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO filtroFornitoreAffidamentoDTO

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;

	////{PROTECTED REGION ID(R-1778914013) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo creaFornitoreAffidamento.
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
	 	
	 
	 * @param identitaIride [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param fornitoreAffidamentoDTO [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti
	 
	 * @throws GestioneAffidamentiException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti creaFornitoreAffidamento(

			java.lang.Long idUtente,

			java.lang.String identitaIride,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO fornitoreAffidamentoDTO

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;

	////{PROTECTED REGION ID(R-2029452787) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo eliminaFornitoreAffidamento.
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
	 	
	 
	 * @param identitaIride [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idFornitoreAffidamento [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti
	 
	 * @throws GestioneAffidamentiException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti eliminaFornitoreAffidamento(

			java.lang.Long idUtente,

			java.lang.String identitaIride,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO fornitoreAffidamentoDTO

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;

	////{PROTECTED REGION ID(R-1717062745) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findElencoAffidamentiConFornitori.
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
	 	
	 
	 * @param identitaIride [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param filtro [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO[]
	 
	 * @throws GestioneAffidamentiException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO[] findElencoAffidamentiConFornitori(

			java.lang.Long idUtente,

			java.lang.String identitaIride,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.FiltroRicercaAffidamentiDTO filtro

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;

	////{PROTECTED REGION ID(R-1847484169) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo respingiAffidamento.
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
	 	
	 
	 * @param identitaIride [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idAppalto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti
	 
	 * @throws GestioneAffidamentiException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti respingiAffidamento(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	java.lang.Long idAppalto,

	java.lang.String note

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;

	////{PROTECTED REGION ID(R-2036606135) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findSommaImportiRendicontabili.
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
	 	
	 
	 * @param identitaIride [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idAppalto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return java.lang.Double
	 
	 * @throws GestioneAffidamentiException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double findSommaImportiRendicontabili(

	java.lang.Long idUtente,

	java.lang.String identitaIride,

	java.lang.Long idAppalto

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;

}
