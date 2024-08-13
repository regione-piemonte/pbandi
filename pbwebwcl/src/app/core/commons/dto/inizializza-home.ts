/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { UserInfoSec } from './user-info';
export class InizializzaHomeSec {
    constructor(
        public datiProgetto: string,
        public userInfoSec: UserInfoSec
    ) { }
}