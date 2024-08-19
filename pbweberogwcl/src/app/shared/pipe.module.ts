/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { SafeHtmlPipe } from './pipe/safeHTML.pipe';
import { CutDecimalPipe } from './pipe/cutDecimal.pipe';

@NgModule({
  imports: [
    // dep modules
  ],
  declarations: [ 
    SafeHtmlPipe,
    CutDecimalPipe
  ],
  exports: [
    SafeHtmlPipe,
    CutDecimalPipe
  ]
})
export class PipeModule {}