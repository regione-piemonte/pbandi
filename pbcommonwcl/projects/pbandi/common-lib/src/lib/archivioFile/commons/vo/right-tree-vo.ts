/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { UserSpaceDTO } from './user-space-vo';
import { FoldersVo } from './folders-vo';
export class RightTreeVo {
    constructor(
        public userSpaceDTO: UserSpaceDTO,
        public folders: Array<FoldersVo>
    ) { }
}