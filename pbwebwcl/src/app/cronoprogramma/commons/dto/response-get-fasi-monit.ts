/* tslint:disable */
import { DatiCronoprogramma } from './dati-cronoprogramma';
import { CronoprogrammaItem } from './cronoprogramma-item';
export interface ResponseGetFasiMonit {
  datiCrono?: DatiCronoprogramma;
  item?: Array<CronoprogrammaItem>;
}
