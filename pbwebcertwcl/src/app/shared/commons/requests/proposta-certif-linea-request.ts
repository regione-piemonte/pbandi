/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { UserInfoSec } from "src/app/core/commons/dto/user-info";

export class PropostaCertifLineaRequest {
    constructor(
        public userInfo: UserInfoSec,
        public idLineeIntervento: Array<number>
    ) { }
}