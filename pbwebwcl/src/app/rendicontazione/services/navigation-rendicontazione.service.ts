/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from "@angular/core";
import { SortDirection } from "@angular/material/sort";
import { NavigationService } from "src/app/shared/services/navigation.service";
import { FiltroRicercaDocumentiSpesa } from "../commons/requests/filtro-ricerca-documenti-spesa";

@Injectable()
export class NavigationRendicontazioneService extends NavigationService {
    private filtroRicercaDocumentiSpesa: FiltroRicercaDocumentiSpesa = null;
    private elencoDocSpesaVisible: boolean;

    public set filtroRicercaDocumentiSpesaSelezionato(value: FiltroRicercaDocumentiSpesa) {
        this.filtroRicercaDocumentiSpesa = value;
    }

    public get filtroRicercaDocumentiSpesaSelezionato(): FiltroRicercaDocumentiSpesa {
        return this.filtroRicercaDocumentiSpesa;
    }

    public set lastElencoDocSpesaVisible(value: boolean) {
        this.elencoDocSpesaVisible = value;
    }

    public get lastElencoDocSpesaVisible(): boolean {
        return this.elencoDocSpesaVisible;
    }
}