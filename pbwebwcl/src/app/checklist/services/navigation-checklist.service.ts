/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from "@angular/core";
import { NavigationService } from "src/app/shared/services/navigation.service";
import { FiltroRicercaChecklist } from "../commons/dto/filtro-ricerca-checklist";

@Injectable()
export class NavigationChecklistService extends NavigationService {
    private filtroRicercaChecklist: FiltroRicercaChecklist = null;

    public set filtroRicercaChecklistSelezionato(value: FiltroRicercaChecklist) {
        this.filtroRicercaChecklist = value;
    }

    public get filtroRicercaChecklistSelezionato(): FiltroRicercaChecklist {
        return this.filtroRicercaChecklist;
    }

}