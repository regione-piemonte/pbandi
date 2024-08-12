/* tslint:disable */
import { IndicatoreItem } from './indicatore-item';
export interface SalvaIndicatoriRequest {
  altriIndicatori?: Array<IndicatoreItem>;
  idProgetto?: number;
  indicatoriMonitoraggio?: Array<IndicatoreItem>;
}
