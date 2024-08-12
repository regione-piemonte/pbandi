/* tslint:disable */
import { IndicatoreItem } from './indicatore-item';
export interface ValidazioneDatiIndicatoriRequest {
  idProgetto?: number;
  indicatori?: Array<IndicatoreItem>;
  altriIndicatori?: Array<IndicatoreItem>;
}
