/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AllegatoVO } from "./allegato-vo";
import { DistintaPropostaErogazioneVO } from "./distinta-proposta-erogazione-vo";
import { EstremiBancariVO } from "./estremo-bancari-vo";
import { ProgettoAllegatoVO } from "./progetto-allegato.vo";

export class DettaglioDistintaVO {

  //PBANDI_T_BANDO
  public idBando: string;
  public titoloBando: string;

  //PBANDI_D_MODALITA_AGEVOLAZIONE
  public idModalitaAgevolazione: string;
  public idModalitaAgevolazioneRif: string;
  public descModalitaAgevolazione: string;

  //PBANDI_T_DISTINTA
  public descDistinta: string;

  //altre
  public estremiBancariList?: Array<EstremiBancariVO>;
  public distintaPropostaErogazioneList?: Array<DistintaPropostaErogazioneVO>;
  public estremiBancariVO?: EstremiBancariVO;

  public bozza?: boolean;

  //allegati
  progettoAllegatoVOList: ProgettoAllegatoVO[];

}
