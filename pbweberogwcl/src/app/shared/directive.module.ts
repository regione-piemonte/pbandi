/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { InputNumberDirective } from './directive/input-number.directive';

@NgModule({
  imports: [
    // dep modules
  ],
  declarations: [ 
    InputNumberDirective
  ],
  exports: [
    InputNumberDirective
  ]
})
export class DirectiveModule {}