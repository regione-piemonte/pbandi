import {CronoprogrammaItem} from "../dto/cronoprogramma-item";
import {DatiCronoprogramma} from "../dto/dati-cronoprogramma";

export class ValidazioneDatiRequest{
  constructor(
    public idProgetto: number,
    public datiCrono: DatiCronoprogramma,
    public fasi: Array<CronoprogrammaItem>
  ) { }
}
