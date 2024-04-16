/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { BandoWidgetDTO } from "../dto/bando-widget-dto";
import { WidgetDTO } from "../dto/widget-dto";

export interface SalvaBandiAssociatiWidgetRequest extends WidgetDTO{
    bandiAssociati: Array<BandoWidgetDTO>;
}
