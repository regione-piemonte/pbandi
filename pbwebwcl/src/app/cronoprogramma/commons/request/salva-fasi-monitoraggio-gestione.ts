import {CronoprogrammaItem} from "../dto/cronoprogramma-item";

export class SalvaFasiMonitoraggioGestioneRequest {
  constructor(
    public idProgetto: number,
    public idIter: number,
    public fasi: Array<CronoprogrammaItem>,
    public idTipoOperazione?: number
  ) { }
}
