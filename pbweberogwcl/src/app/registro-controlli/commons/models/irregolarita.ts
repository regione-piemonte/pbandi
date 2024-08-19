/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Allegato } from './allegato';
export interface Irregolarita {
  codiceVisualizzatoProgetto?: string;
  dataCampione?: string;
  dataCampioneProvv?: string;
  dataFineControlli?: string;
  dataFineControlliProvv?: string;
  dataInizioControlli?: string;
  dataInizioControlliProvv?: string;
  datiAggiuntivi?: Allegato;
  denominazioneBeneficiario?: string;
  descCategAnagrafica?: string;
  descCategAnagraficaProvv?: string;
  descDisimpegnoAssociato?: string;
  descMotivoRevoca?: string;
  descMotivoRevocaProvv?: string;
  descPeriodoVisualizzata?: string;
  descPeriodoVisualizzataProvv?: string;
  descrBreveDisposizioneComunitariaTrasgredita?: string;
  descrBreveMetodoIndividuazioneIrregolarita?: string;
  descrBreveNaturaSanzioneApplicata?: string;
  descrBreveQualificazioneIrregolarita?: string;
  descrBreveStatoAmministrativo?: string;
  descrBreveStatoFinanziario?: string;
  descrBreveTipoIrregolarita?: string;
  descrDisposizioneComunitariaTrasgredita?: string;
  descrMetodoIndividuazioneIrregolarita?: string;
  descrNaturaSanzioneApplicata?: string;
  descrQualificazioneIrregolarita?: string;
  descrStatoAmministrativo?: string;
  descrStatoFinanziario?: string;
  descrTipoIrregolarita?: string;
  dtComunicazione?: string;
  dtComunicazioneIrregolarita?: string;
  dtComunicazioneProvv?: string;
  dtFineProvvisoriaProvv?: string;
  dtFineValiditaProvv?: string;
  dtIms?: string;
  esitoControllo?: string;
  flagBlocco?: string;
  flagBloccata?: string;
  flagIrregolaritaAnnullataProvv?: string;
  flagProvvedimento?: string;
  idCategAnagrafica?: number;
  idCategAnagraficaProvv?: number;
  idEsitoControllo?: number;
  idIrregolarita?: number;
  numeroIms?: string;
  idIrregolaritaCollegata?: number;
  idIrregolaritaProvv?: number;
  idMotivoRevoca?: number;
  idMotivoRevocaProvv?: number;
  idPeriodo?: number;
  idPeriodoProvv?: number;
  idProgettoProvv?: number;
  identificativoVersione?: string;
  importoAgevolazioneIrreg?: number;
  importoAgevolazioneIrregProvv?: number;
  importoIrregolareCertificatoProvv?: number;
  importoIrregolarita?: number;
  importoIrregolaritaProvv?: number;
  note?: string;
  notePraticaUsata?: string;
  noteProvv?: string;
  numeroVersione?: string;
  quotaImpIrregCertificato?: number;
  riferimentoIMS?: string;
  schedaOLAF?: Allegato;
  soggettoResponsabile?: string;
  tastoElimina?: string;
  tastoModifica?: string;
  tastoVisualizza?: string;
  tipoControlli?: string;
  tipoControlliProvv?: string;
  trasformaIn?: string;
  versioneIrregolarita?: number;
}
