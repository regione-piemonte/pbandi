/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class FiltroGestioneGaranzie {
        constructor(
                public descrizioneBando?: string,
                public codiceProgetto?: string,
                public codiceFiscale?: string,
                public nag?: string,
                public partitaIva?: string,
                public denominazioneCognomeNome?: string,
                public statoEscussione?: string,
                public denominazioneBanca?: string,
        ) {}
        
}