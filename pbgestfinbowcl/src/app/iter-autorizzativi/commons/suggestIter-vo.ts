/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SuggestIterVO {
        public id ?: number; 
        public idSoggetto?: string;
        public idProgetto?: number; 
        public progBandoLinea?: number; 
        public denominazione?: string; 
        public codiceVisualizzato?: string;
        public nomeBando?: string; 
        public value?: string;
      }