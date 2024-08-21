/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.richiestaerogazione;

import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.*;
import it.csi.pbandi.pbservizit.pbandisrv.exception.richiestaerogazione.*;

////{PROTECTED REGION ID(R-1248757651) ENABLED START////}
/**
 * Inserire qui la documentazione dell'interfaccia pubblica del servizio richiesta_erogazione.
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
public interface RichiestaErogazioneSrv {

	////{PROTECTED REGION ID(R971043945) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findDatiRiepilogoRichiestaErogazione.
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
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.EsitoRichiestaErogazioneDTO
	 
	 * @throws RichiestaErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.EsitoRichiestaErogazioneDTO findDatiRiepilogoRichiestaErogazione(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto,

	java.lang.String codCausaleErogazione,

	java.lang.Long idDimensioneImpresa,

	java.lang.Long idFormaGiuridica,

	java.lang.Long idBandoLinea,

	java.lang.Long idSoggetto

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.richiestaerogazione.RichiestaErogazioneException;

	////{PROTECTED REGION ID(R115466386) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findRappresentantiLegali.
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
	 	
	 
	 * @param idSoggettoRappresentante [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO[]
	 
	 * @throws RichiestaErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO[] findRappresentantiLegali(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto,

	java.lang.Long idSoggettoRappresentante

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.richiestaerogazione.RichiestaErogazioneException;

	////{PROTECTED REGION ID(R-1218282313) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo creaReportRichiestaErogazione.
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
	 	
	 
	 * @param idSoggetto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param richiestaErogazioneDTO [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param rappresentanteLegaleDTO [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.EsitoRichiestaErogazioneDTO
	 
	 * @throws RichiestaErogazioneException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.EsitoReportRichiestaErogazioneDTO creaReportRichiestaErogazione(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			java.lang.Long idProgetto,

			java.lang.Long idSoggetto,

			it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneDTO richiestaErogazioneDTO,

			it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO rappresentanteLegaleDTO,

			it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.IstanzaAttivitaDTO istanzaAttivitaDTO,

			java.lang.String cfBeneficiario,

			java.lang.String codiceUtente,

			java.lang.Long idDelegato

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.richiestaerogazione.RichiestaErogazioneException;

}
