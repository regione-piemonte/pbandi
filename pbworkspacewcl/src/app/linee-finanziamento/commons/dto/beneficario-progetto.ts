/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FormControl } from "@angular/forms";
import { CodiceDescrizione } from "./codice-descrizione";
import { IdDescBreveDescEstesa } from "./id-desc-breve-desc-estesa";

export class BeneficiarioProgetto {
    id: number;
    tipoSoggetto: string;               //EG: persona giuridica PF: persona fisica
    ruoli: Array<CodiceDescrizione>;    //checkbox selezionate
    iban: string;

    //solo persona fisica
    codiceFiscalePF: string;
    cognome: string;
    nome: string;
    sesso: string;
    dataNascita: FormControl;
    nazioneNascita: IdDescBreveDescEstesa;
    nazioneResidenza: IdDescBreveDescEstesa;
    regioneNascita: CodiceDescrizione;
    regioneResidenza: CodiceDescrizione;
    provinciaNascita: CodiceDescrizione;
    provinciaResidenza: CodiceDescrizione;
    comuneNascita: CodiceDescrizione;
    comuneResidenza: CodiceDescrizione;
    indirizzoResidenza: string;
    civicoResidenza: string;
    capResidenza: string;
    email: string;
    telefono: string;
    fax: string;

    //solo persona giuridica
    tipoSoggGiur: string;
    codiceFiscaleEG: string;
    enteDR: CodiceDescrizione;
    settoreDR: CodiceDescrizione;
    entePA: CodiceDescrizione;
    settorePA: CodiceDescrizione;
    ateneo: CodiceDescrizione;
    dipartimento: CodiceDescrizione;
    denominazione: string;
    dataCostituzione: FormControl;
    formaGiuridica: CodiceDescrizione;
    partitaIva: string;
    partitaIvaDR: string;
    partitaIvaPA: string;
    settoreAttivita: CodiceDescrizione;
    attivitaAteco: CodiceDescrizione;
    nazioneSedeLegale: IdDescBreveDescEstesa;
    nazioneSedeLegaleDR: IdDescBreveDescEstesa;
    nazioneSedeLegalePA: IdDescBreveDescEstesa;
    regioneSedeLegale: CodiceDescrizione;
    regioneSedeLegaleDR: CodiceDescrizione;
    regioneSedeLegalePA: CodiceDescrizione;
    provinciaSedeLegale: CodiceDescrizione;
    provinciaSedeLegaleDR: CodiceDescrizione;
    provinciaSedeLegalePA: CodiceDescrizione;
    comuneSedeLegale: CodiceDescrizione;
    comuneSedeLegaleDR: CodiceDescrizione;
    comuneSedeLegalePA: CodiceDescrizione;
    indirizzoSedeLegale: string;
    indirizzoSedeLegaleDR: string;
    indirizzoSedeLegalePA: string;
    civicoSedeLegale: string;
    civicoSedeLegaleDR: string;
    civicoSedeLegalePA: string;
    capSedeLegale: string;
    capSedeLegaleDR: string;
    capSedeLegalePA: string;
    emailSedeLegale: string;
    emailSedeLegaleDR: string;
    emailSedeLegalePA: string;
    pecSedeLegale: string;
    pecSedeLegaleDR: string;
    pecSedeLegalePA: string;
    telefonoSedeLegale: string;
    telefonoSedeLegaleDR: string;
    telefonoSedeLegalePA: string;
    faxSedeLegale: string;
    faxSedeLegaleDR: string;
    faxSedeLegalePA: string;

    constructor() {
        this.dataNascita = new FormControl();
        this.dataCostituzione = new FormControl();
        this.ruoli = new Array<CodiceDescrizione>();
    }
}