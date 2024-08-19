/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DocumentoIrregolaritaDTO } from "./documento-irregolarita-dto"

export interface IrregolaritaDTO {
    idIrregolarita?: number;
    numeroIms?: string;
    dtIms?: Date;
    dtComunicazione?: Date;
    flagCasoChiuso?: string;
    flagBlocco?: string;
    notePraticaUsata?: string;
    numeroVersione?: number;
    idProgetto?: number;
    idStatoAmministrativo?: number;
    idDispComunitaria?: number;
    idMetodoIndividuazione?: number;
    idNaturaSanzione?: number;
    idTipoIrregolarita?: number;
    idQualificazioneIrreg?: number;
    idStatoFinanziario?: number;
    codiceVisualizzato?: string;
    descTipoIrregolarita?: string;
    descBreveTipoIrregolarita?: string;
    descQualificazioneIrreg?: string;
    descBreveQualificIrreg?: string;
    descDispComunitaria?: string;
    descBreveDispComunitaria?: string;
    idSoggettoBeneficiario?: number;
    descBreveMetodoInd?: string;
    descMetodoInd?: string;
    descBreveStatoAmministrativ?: string;
    descStatoAmministrativo?: string;
    descBreveStatoFinanziario?: string;
    descStatoFinanziario?: string;
    flagProvvedimento?: string;
    descBreveNaturaSanzione?: string;
    descNaturaSanzione?: string;
    soggettoResponsabile?: string;
    schedaOLAF?: DocumentoIrregolaritaDTO;
    datiAggiuntivi?: DocumentoIrregolaritaDTO;
    cfBeneficiario?: string;
    denominazioneBeneficiario?: string;
    idIrregolaritaCollegata?: number;
    importoIrregolarita?: number;
    quotaImpIrregCertificato?: number;
    descDisimpegnoAssociato?: string;
    tipoControlli?: string;
    importoAgevolazioneIrreg?: number;
    idMotivoRevoca?: number;
    descMotivoRevoca?: string;
    dataInizioControlli?: Date;
    dataFineControlli?: Date;
    idIrregolaritaProvv?: number;
    dtComunicazioneProvv?: Date;
    tipoControlliProvv?: string;
    dtFineProvvisoriaProvv?: Date;
    idProgettoProvv?: number;
    idMotivoRevocaProvv?: number;
    descMotivoRevocaProvv?: string;
    importoIrregolaritaProvv?: number;
    importoAgevolazioneIrregProvv?: number;
    importoIrregolareCertificatoProvv?: number;
    dtFineValiditaProvv?: Date;
    dataInizioControlliProvv?: Date;
    dataFineControlliProvv?: Date;
    flagIrregolaritaAnnullataProvv?: string;
    idPeriodo?: number;
    descPeriodoVisualizzata?: string;
    idPeriodoProvv?: number;
    descPeriodoVisualizzataProvv?: string;
    idCategAnagrafica?: number;
    descCategAnagrafica?: string;
    idCategAnagraficaProvv?: number;
    descCategAnagraficaProvv?: string;
    esitoControllo?: string;
    idEsitoControllo?: number;
    note?: string;
    noteProvv?: string;
    dataCampione?: Date;
}