
export class DatiCronoprogramma {
  constructor(
    public idTipoOperazione: number,
    public descTipoOperazione: string,
    public idIter: number,
    public dtPresentazioneDomanda: string,
    public dtConcessioneComitato: string,
    public codiceFaseFinaleObbligatoria?: string
  ) { }
}

/* tslint:disable */
/*export interface DatiCronoprogramma {
  codiceFaseFinaleObbligatoria?: string;
  descIter?: string;
  descTipoOperazione?: string;
  dtConcessioneComitato?: string;
  dtPresentazioneDomanda?: string;
  idIter?: number;
  idTipoOperazione?: number;
}*/
