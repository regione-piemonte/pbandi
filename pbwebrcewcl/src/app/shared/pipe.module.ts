/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { SafeHtmlPipe } from './pipe/safeHTML.pipe';

@NgModule({
  imports: [
    // dep modules
  ],
  declarations: [ 
    SafeHtmlPipe
  ],
  exports: [
    SafeHtmlPipe
  ]
})
export class PipeModule {}