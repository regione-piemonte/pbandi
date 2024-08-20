/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from "@angular/core";
import { NavigationService } from "src/app/shared/services/navigation.service";
import { FiltroGestioneGaranzie } from "../../gestione-garanzie/commons/filtro-gestione-garanzie";

@Injectable({
    providedIn: 'root'
})
export class NavigationGestioneGaranzieService extends NavigationService {
    private filtroGestioneGaranzie: FiltroGestioneGaranzie = null;

    public set filtroGestioneGaranzieSelezionato(value: FiltroGestioneGaranzie) {
        this.filtroGestioneGaranzie = value;
    }

    public get filtroGestioneGaranzieSelezionato(): FiltroGestioneGaranzie {
        return this.filtroGestioneGaranzie;
    }
}