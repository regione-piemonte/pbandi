/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FrequenzaVO } from './frequenza-vo';
import { NotificaAlertVO } from './notifica-alert-vo';

export class NotificheFrequenzeVO {
    constructor(
        public frequenze: Array<FrequenzaVO>,
        public notificheAlert: Array<NotificaAlertVO>
    ) { }
}