/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ApiMessage {
    constructor(
        public code: number,
        public message: string,
        public error: boolean
    ) { }
}