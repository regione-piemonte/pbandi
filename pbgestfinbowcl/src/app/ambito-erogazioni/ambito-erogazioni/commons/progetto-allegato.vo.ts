/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AllegatoVO } from "./allegato-vo";

export class ProgettoAllegatoVO {
    public letteraAccompagnatoria: any;
    public visibilitaLettera: boolean;

    public checklistInterna: any;
    public visibilitaChecklist: boolean;

    public reportValidazione: any;
    public visibilitaReport: boolean;

    public flagFinistr: string;

    public codiceVisualizzatoProgetto: string;
    public idProgetto: number;

    public salvato: boolean;
}