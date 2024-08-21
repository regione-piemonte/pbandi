/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.consultazioneattibilancio;

import it.csi.csi.wrapper.*;

import it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.*;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.consultazioneattibilancio.*;
import it.csi.pbandi.pbservizit.pbandisrv.exception.consultazioneattibilancio.*;

import javax.sql.DataSource;
import org.apache.log4j.*;

/*PROTECTED REGION ID(R-1136324196) ENABLED START*/
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessWrapper;
/*PROTECTED REGION END*/

/**
 * @generated
 */
public class ConsultazioneAttiBilancioImpl {
	/**
	 * @generated
	 */
	public static final String LOGGER_PREFIX = "pbandisrv";

	/*PROTECTED REGION ID(R-1784934854) ENABLED START*/
	// inserire qui la definizione di varibili locale o costanti dell'implementazione.
	// non verranno sovrascritte da successive rigenerazioni
	/*PROTECTED REGION END*/

	/// Implementazione operazioni esposte dal servizio

	/**
	 * @generated
	 */
	public it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.CodiceDescrizioneDTO[] getBeneficiariConAttiDiLiquidazione(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.consultazioneattibilancio.ConsultazioneAttiBilancioException

	{
		Logger logger = getLogger(null);
		logger
				.debug("[ConsultazioneAttiBilancioImpl::getBeneficiariConAttiDiLiquidazione] - START");

		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				"pbandisrv");
		// inizio misurazione
		watcher.start();

		/*PROTECTED REGION ID(R-1322160113) ENABLED START*/
		// inserire qui la dichiarazione di variabili locali al metodo
		// non verr� sovrascritto nelle successive rigenerazioni
		/*PROTECTED REGION END*/
		try {
			/*PROTECTED REGION ID(R1834109807) ENABLED START*/
			// inserire qui il codice di implementazione del metodo 'getBeneficiariConAttiDiLiquidazione'.
			// non verr� sovrascritto nelle successive rigenerazioni

			return (CodiceDescrizioneDTO[]) BusinessWrapper.execute(idUtente,
					identitaDigitale);

			/*PROTECTED REGION END*/
		}

		catch (Throwable ex) {
			if (CSIException.class.isAssignableFrom(ex.getClass())) {
				logger
						.error(
								"[ConsultazioneAttiBilancioImpl::getBeneficiariConAttiDiLiquidazione] - Errore CSI occorso durante l'esecuzione del metodo:"
										+ ex, ex);
				throw (CSIException) ex;
			} else {
				logger
						.error(
								"[ConsultazioneAttiBilancioImpl::getBeneficiariConAttiDiLiquidazione] - Errore imprevisto occorso durante l'esecuzione del metodo:"
										+ ex, ex);
				throw new UnrecoverableException(
						"Errore imprevisto occorso durante l'esecuzione del metodo:"
								+ ex, ex);
			}
		} finally {
			// fine misurazione
			watcher.stop();
			watcher
					.dumpElapsed(
							"ConsultazioneAttiBilancioImpl",
							"getBeneficiariConAttiDiLiquidazione()",
							"invocazione servizio [consultazioneattibilancio]::[getBeneficiariConAttiDiLiquidazione]",
							"(valore input omesso)");
			logger
					.debug("[ConsultazioneAttiBilancioImpl::getBeneficiariConAttiDiLiquidazione] - END");
		}
	}

	/**
	 * @generated
	 */
	public it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.CodiceDescrizioneDTO[] getProgettiConAttiDiLiquidazione(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.consultazioneattibilancio.ConsultazioneAttiBilancioException

	{
		Logger logger = getLogger(null);
		logger
				.debug("[ConsultazioneAttiBilancioImpl::getProgettiConAttiDiLiquidazione] - START");

		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				"pbandisrv");
		// inizio misurazione
		watcher.start();

		/*PROTECTED REGION ID(R397004576) ENABLED START*/
		// inserire qui la dichiarazione di variabili locali al metodo
		// non verr� sovrascritto nelle successive rigenerazioni
		/*PROTECTED REGION END*/
		try {
			/*PROTECTED REGION ID(R130396096) ENABLED START*/
			// inserire qui il codice di implementazione del metodo 'getProgettiConAttiDiLiquidazione'.
			// non verr� sovrascritto nelle successive rigenerazioni

			return (CodiceDescrizioneDTO[]) BusinessWrapper.execute(idUtente,
					identitaDigitale);

			/*PROTECTED REGION END*/
		}

		catch (Throwable ex) {
			if (CSIException.class.isAssignableFrom(ex.getClass())) {
				logger
						.error(
								"[ConsultazioneAttiBilancioImpl::getProgettiConAttiDiLiquidazione] - Errore CSI occorso durante l'esecuzione del metodo:"
										+ ex, ex);
				throw (CSIException) ex;
			} else {
				logger
						.error(
								"[ConsultazioneAttiBilancioImpl::getProgettiConAttiDiLiquidazione] - Errore imprevisto occorso durante l'esecuzione del metodo:"
										+ ex, ex);
				throw new UnrecoverableException(
						"Errore imprevisto occorso durante l'esecuzione del metodo:"
								+ ex, ex);
			}
		} finally {
			// fine misurazione
			watcher.stop();
			watcher
					.dumpElapsed(
							"ConsultazioneAttiBilancioImpl",
							"getProgettiConAttiDiLiquidazione()",
							"invocazione servizio [consultazioneattibilancio]::[getProgettiConAttiDiLiquidazione]",
							"(valore input omesso)");
			logger
					.debug("[ConsultazioneAttiBilancioImpl::getProgettiConAttiDiLiquidazione] - END");
		}
	}

	/**
	 * @generated
	 */
	public it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.AttoDiLiquidazioneDTO[] findAttiDiLiquidazione(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.AttoDiLiquidazioneDTO filtro

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.consultazioneattibilancio.ConsultazioneAttiBilancioException

	{
		Logger logger = getLogger(null);
		logger
				.debug("[ConsultazioneAttiBilancioImpl::findAttiDiLiquidazione] - START");

		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				"pbandisrv");
		// inizio misurazione
		watcher.start();

		/*PROTECTED REGION ID(R-1111345621) ENABLED START*/
		// inserire qui la dichiarazione di variabili locali al metodo
		// non verr� sovrascritto nelle successive rigenerazioni
		/*PROTECTED REGION END*/
		try {
			/*PROTECTED REGION ID(R-1199546677) ENABLED START*/
			// inserire qui il codice di implementazione del metodo 'findAttiDiLiquidazione'.
			// non verr� sovrascritto nelle successive rigenerazioni

			return (AttoDiLiquidazioneDTO[]) BusinessWrapper.execute(idUtente,
					identitaDigitale, filtro);

			/*PROTECTED REGION END*/
		}

		catch (Throwable ex) {
			if (CSIException.class.isAssignableFrom(ex.getClass())) {
				logger
						.error(
								"[ConsultazioneAttiBilancioImpl::findAttiDiLiquidazione] - Errore CSI occorso durante l'esecuzione del metodo:"
										+ ex, ex);
				throw (CSIException) ex;
			} else {
				logger
						.error(
								"[ConsultazioneAttiBilancioImpl::findAttiDiLiquidazione] - Errore imprevisto occorso durante l'esecuzione del metodo:"
										+ ex, ex);
				throw new UnrecoverableException(
						"Errore imprevisto occorso durante l'esecuzione del metodo:"
								+ ex, ex);
			}
		} finally {
			// fine misurazione
			watcher.stop();
			watcher
					.dumpElapsed(
							"ConsultazioneAttiBilancioImpl",
							"findAttiDiLiquidazione()",
							"invocazione servizio [consultazioneattibilancio]::[findAttiDiLiquidazione]",
							"(valore input omesso)");
			logger
					.debug("[ConsultazioneAttiBilancioImpl::findAttiDiLiquidazione] - END");
		}
	}

	/**
	 * @generated
	 */
	public it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.DettaglioAttoDiLiquidazioneDTO getDettaglioAttoDiLiquidazione(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idAttoDiLiquidazione

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.consultazioneattibilancio.ConsultazioneAttiBilancioException

	{
		Logger logger = getLogger(null);
		logger
				.debug("[ConsultazioneAttiBilancioImpl::getDettaglioAttoDiLiquidazione] - START");

		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				"pbandisrv");
		// inizio misurazione
		watcher.start();

		/*PROTECTED REGION ID(R1048644557) ENABLED START*/
		// inserire qui la dichiarazione di variabili locali al metodo
		// non verr� sovrascritto nelle successive rigenerazioni
		/*PROTECTED REGION END*/
		try {
			/*PROTECTED REGION ID(R-144927635) ENABLED START*/
			// inserire qui il codice di implementazione del metodo 'getDettaglioAttoDiLiquidazione'.
			// non verr� sovrascritto nelle successive rigenerazioni

			return (DettaglioAttoDiLiquidazioneDTO) BusinessWrapper.execute(
					idUtente, identitaDigitale, idAttoDiLiquidazione);

			/*PROTECTED REGION END*/
		}

		catch (Throwable ex) {
			if (CSIException.class.isAssignableFrom(ex.getClass())) {
				logger
						.error(
								"[ConsultazioneAttiBilancioImpl::getDettaglioAttoDiLiquidazione] - Errore CSI occorso durante l'esecuzione del metodo:"
										+ ex, ex);
				throw (CSIException) ex;
			} else {
				logger
						.error(
								"[ConsultazioneAttiBilancioImpl::getDettaglioAttoDiLiquidazione] - Errore imprevisto occorso durante l'esecuzione del metodo:"
										+ ex, ex);
				throw new UnrecoverableException(
						"Errore imprevisto occorso durante l'esecuzione del metodo:"
								+ ex, ex);
			}
		} finally {
			// fine misurazione
			watcher.stop();
			watcher
					.dumpElapsed(
							"ConsultazioneAttiBilancioImpl",
							"getDettaglioAttoDiLiquidazione()",
							"invocazione servizio [consultazioneattibilancio]::[getDettaglioAttoDiLiquidazione]",
							"(valore input omesso)");
			logger
					.debug("[ConsultazioneAttiBilancioImpl::getDettaglioAttoDiLiquidazione] - END");
		}
	}

	/**
	 * @generated
	 */
	public it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.DettaglioAttoDiLiquidazioneDTO getDettaglioAttoDiLiquidazionePersistito(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.consultazioneattibilancio.ConsultazioneAttiBilancioException

	{
		Logger logger = getLogger(null);
		logger
				.debug("[ConsultazioneAttiBilancioImpl::getDettaglioAttoDiLiquidazionePersistito] - START");

		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				"pbandisrv");
		// inizio misurazione
		watcher.start();

		/*PROTECTED REGION ID(R1970268669) ENABLED START*/
		// inserire qui la dichiarazione di variabili locali al metodo
		// non verr� sovrascritto nelle successive rigenerazioni
		/*PROTECTED REGION END*/
		try {
			/*PROTECTED REGION ID(R-1502532963) ENABLED START*/
			// inserire qui il codice di implementazione del metodo 'getDettaglioAttoDiLiquidazionePersistito'.
			// non verr� sovrascritto nelle successive rigenerazioni

			return (DettaglioAttoDiLiquidazioneDTO) BusinessWrapper.execute(
					idUtente, identitaDigitale, idProgetto);

			/*PROTECTED REGION END*/
		}

		catch (Throwable ex) {
			if (CSIException.class.isAssignableFrom(ex.getClass())) {
				logger
						.error(
								"[ConsultazioneAttiBilancioImpl::getDettaglioAttoDiLiquidazionePersistito] - Errore CSI occorso durante l'esecuzione del metodo:"
										+ ex, ex);
				throw (CSIException) ex;
			} else {
				logger
						.error(
								"[ConsultazioneAttiBilancioImpl::getDettaglioAttoDiLiquidazionePersistito] - Errore imprevisto occorso durante l'esecuzione del metodo:"
										+ ex, ex);
				throw new UnrecoverableException(
						"Errore imprevisto occorso durante l'esecuzione del metodo:"
								+ ex, ex);
			}
		} finally {
			// fine misurazione
			watcher.stop();
			watcher
					.dumpElapsed(
							"ConsultazioneAttiBilancioImpl",
							"getDettaglioAttoDiLiquidazionePersistito()",
							"invocazione servizio [consultazioneattibilancio]::[getDettaglioAttoDiLiquidazionePersistito]",
							"(valore input omesso)");
			logger
					.debug("[ConsultazioneAttiBilancioImpl::getDettaglioAttoDiLiquidazionePersistito] - END");
		}
	}

	/**
	 * @generated
	 */
	public it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio.RiepilogoAttoDiLiquidazionePerProgetto[] findRiepilogoAttiLiquidazione(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProgetto

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbservizit.pbandisrv.exception.consultazioneattibilancio.ConsultazioneAttiBilancioException

	{
		Logger logger = getLogger(null);
		logger
				.debug("[ConsultazioneAttiBilancioImpl::findRiepilogoAttiLiquidazione] - START");

		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				"pbandisrv");
		// inizio misurazione
		watcher.start();

		/*PROTECTED REGION ID(R-2111138816) ENABLED START*/
		// inserire qui la dichiarazione di variabili locali al metodo
		// non verr� sovrascritto nelle successive rigenerazioni
		/*PROTECTED REGION END*/
		try {
			/*PROTECTED REGION ID(R-1318357152) ENABLED START*/
			// inserire qui il codice di implementazione del metodo 'findRiepilogoAttiLiquidazione'.
			// non verr� sovrascritto nelle successive rigenerazioni

			return (RiepilogoAttoDiLiquidazionePerProgetto[]) BusinessWrapper
					.execute(idUtente, identitaDigitale, idProgetto);

			/*PROTECTED REGION END*/
		}

		catch (Throwable ex) {
			if (CSIException.class.isAssignableFrom(ex.getClass())) {
				logger
						.error(
								"[ConsultazioneAttiBilancioImpl::findRiepilogoAttiLiquidazione] - Errore CSI occorso durante l'esecuzione del metodo:"
										+ ex, ex);
				throw (CSIException) ex;
			} else {
				logger
						.error(
								"[ConsultazioneAttiBilancioImpl::findRiepilogoAttiLiquidazione] - Errore imprevisto occorso durante l'esecuzione del metodo:"
										+ ex, ex);
				throw new UnrecoverableException(
						"Errore imprevisto occorso durante l'esecuzione del metodo:"
								+ ex, ex);
			}
		} finally {
			// fine misurazione
			watcher.stop();
			watcher
					.dumpElapsed(
							"ConsultazioneAttiBilancioImpl",
							"findRiepilogoAttiLiquidazione()",
							"invocazione servizio [consultazioneattibilancio]::[findRiepilogoAttiLiquidazione]",
							"(valore input omesso)");
			logger
					.debug("[ConsultazioneAttiBilancioImpl::findRiepilogoAttiLiquidazione] - END");
		}
	}

	/// inizializzazione
	/**
	 * @generated
	 */
	public void init(Object initOptions) {
		/*PROTECTED REGION ID(R1521234241) ENABLED START*/
		// inserire qui il codice di inizializzazione della implementazione
		// non verr� sovrascritto da successive rigenerazioni
		/*PROTECTED REGION END*/
	}

	/**
	 * @generated
	 */
	protected Logger getLogger(String subsystem) {
		if (subsystem != null)
			return Logger.getLogger(LOGGER_PREFIX + "." + subsystem);
		else
			return Logger.getLogger(LOGGER_PREFIX);
	}

	/// eventuali metodi aggiuntivi
	/*PROTECTED REGION ID(R-1110264689) ENABLED START*/
	// inserire qui la dichiarazione di eventuali metodi aggiuntivi utili
	// per l'implementazione.
	// non verr� sovrascritto da successive rigenerazioni.
	/*PROTECTED REGION END*/
}
