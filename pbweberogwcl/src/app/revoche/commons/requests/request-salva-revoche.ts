/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { RigaModificaRevocaItem } from '../models/riga-modifica-revoca-item';
import { RigaRevocaItem } from '../models/riga-revoca-item';
export interface RequestSalvaRevoche {
	codCausaleDisimpegno?: string;
	disimpegni?: Array<RigaModificaRevocaItem>;
	dtRevoca?: string;
	estremi?: string;
	idAnnoContabile?: number;
	idModalitaRecupero?: number;
	idMotivoRevoca?: number;
	idProgetto?: number;
	note?: string;
	ordineRecupero?: string;
	revoche?: Array<RigaRevocaItem>;
	dtDecorRevoca?: string;
}
