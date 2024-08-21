/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FoldersVo } from './folders-vo';
import { UserSpaceDTO } from './user-space-vo';

export class LeftTreeVo {
    constructor(
        public dimMaxSingoloFile: number,
        public estensioniConsentite: Array<string>,
        public userSpaceDTO: UserSpaceDTO,
        public folders: Array<FoldersVo>
    ) { }
}