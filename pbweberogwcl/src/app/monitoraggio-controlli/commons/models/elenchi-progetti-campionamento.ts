/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { ProgettoCampione } from './progetto-campione';
export interface ElenchiProgettiCampionamento {
  progettiDaAggiungere?: Array<ProgettoCampione>;
  progettiDelCampione?: Array<ProgettoCampione>;
  progettiGiaPresenti?: Array<ProgettoCampione>;
  progettiScartati?: string;
}
