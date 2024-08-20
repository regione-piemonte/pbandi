/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ProgettoEstrattoVO } from "../vo/ProgettoEstrattoVO";

export class ProgettiEstrattiEsclusiDTO{
    public estratti : ProgettoEstrattoVO[];
    public esclusi:  ProgettoEstrattoVO[]; 
    public campionati:  ProgettoEstrattoVO[]; 
}