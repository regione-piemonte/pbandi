/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SelectionModel } from "@angular/cdk/collections";
import { Injectable } from "@angular/core";
import { NavigationService } from "src/app/shared/services/navigation.service";
import { CodiceDescrizioneDTO } from "../commons/dto/codice-descrizione-dto";
import { IstruttoreShowDTO } from "../commons/dto/istruttore-show-dto";

export interface PeriodicElement {
    select: number;
    impegno: string;
    impegnoReimp: string;
    annoEsercizio: string;
    capitolo: string;
    codFisc: string;
    ragSocCUP: string;
    provvedimento: string;
}

@Injectable()
export class NavigationAssociazioneIstruttoreProgettiService extends NavigationService {
    private cognome: string;
    private nome: string;
    private codiceFiscale: string;
    private bandoSelected: CodiceDescrizioneDTO;
    private selection = new SelectionModel<IstruttoreShowDTO>(true, []);

    public set selectionSelezionata(value: SelectionModel<IstruttoreShowDTO>) {
        this.selection = value;
    }

    public get selectionSelezionata(): SelectionModel<IstruttoreShowDTO> {
        return this.selection;
    }

    public set cognomeSelezionato(value: string) {
        this.cognome = value;
    }

    public get cognomeSelezionato(): string {
        return this.cognome;
    }

    public set bandoSelezionato(value: CodiceDescrizioneDTO) {
        this.bandoSelected = value;
    }

    public get bandoSelezionato(): CodiceDescrizioneDTO {
        return this.bandoSelected;
    }

    public set nomeSelected(value: string) {
        this.nome = value;
    }

    public get nomeSelected(): string {
        return this.nome;
    }

    public set codiceFiscaleSelected(value: string) {
        this.codiceFiscale = value;
    }

    public get codiceFiscaleSelected(): string {
        return this.codiceFiscale;
    }

}