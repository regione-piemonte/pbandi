/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { RevocaIrregolarita } from './revoca-irregolarita';
export interface RigaModificaRevocaItem {
	causaleDisimpegno?: string;
	data?: string;
	descModalitaRecupero?: string;
	descPeriodoVisualizzata?: string;
	hasCausaliErogazione?: boolean;
	hasIrregolarita?: boolean;
	idAnnoContabile?: string;
	idCausaleDisimpegno?: string;
	idModalitaAgevolazione?: string;
	idModalitaRecupero?: number;
	idMotivoRevoca?: string;
	idRevoca?: string;
	importoAgevolato?: number;
	importoErogato?: number;
	importoIrregolarita?: number;
	importoRecuperato?: number;
	importoRevocato?: number;
	irregolarita?: Array<RevocaIrregolarita>;
	isDeletable?: boolean;
	isRigaCausale?: boolean;
	isRigaModalita?: boolean;
	isRigaRecupero?: boolean;
	isRigaRevoca?: boolean;
	isRigaTotale?: boolean;
	isUpdatable?: boolean;
	label?: string;
	lnkElimina?: string;
	lnkIrregolarita?: string;
	lnkModifica?: string;
	motivazione?: string;
	ordineRecupero?: string;
	riferimento?: string;
	dtDecorRevoca?: string;
	isAssociabile?: boolean; //solo frontend per visualizzazione icona
}
