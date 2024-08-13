/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export interface PbandiTMonServAmmvoContabDTO{


 idMonitAmmVoCont : number;
 idServizio: number;
 idUtente : number;
 modalitaChiamata :string;
 esito :string;
 codiceErrore :string;
 messaggioErrore :string;
 dataInizioChiamata :string;
 dataFineChiamata:string;
 parametriInput :string;
 parametriOutput :string;
 idEntita : number;
 idTarget : number;
 numeroKo: number;
 categoriaServizio: string;
}
