/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from "@angular/core";
import { NavigationService } from "src/app/shared/services/navigation.service";
import { FiltroRicercaValidazione } from "../commons/dto/filtro-ricerca-validazione";

@Injectable()
export class NavigationValidazioneService extends NavigationService {
    private filtroRicerca: FiltroRicercaValidazione = null;
    private hasDocumenti: boolean = false;

    public set filtroRicercaSelezionato(value: FiltroRicercaValidazione) {
        this.filtroRicerca = value;
    }

    public get filtroRicercaSelezionato(): FiltroRicercaValidazione {
        return this.filtroRicerca;
    }

    public set hasDoc(value: boolean) {
        this.hasDocumenti = value;
    }

    public get hasDoc(): boolean {
        return this.hasDocumenti;
    }
}