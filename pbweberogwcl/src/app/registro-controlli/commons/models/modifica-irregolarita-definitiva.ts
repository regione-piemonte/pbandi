/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { IrregolaritaDTO } from './irregolarita-dto';
export interface ModificaIrregolaritaDefinitiva {
    idU: number;
    modificaDatiAggiuntivi: boolean;
    strOldFileName?: string;
    irregolarita: IrregolaritaDTO;
    olaf: File;
	zipFile: File;
}