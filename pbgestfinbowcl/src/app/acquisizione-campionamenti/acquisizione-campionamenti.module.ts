/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AcquisizioneProgettiCampionatiComponent } from './components/acquisizione-progetti-campionati/acquisizione-progetti-campionati.component';
import { MaterialModule } from '../app-material.module';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { CoreModule } from '../core/core.module';
import { AcquisizioneCampionamentiService } from './services/acquisizione-campionamenti.service';



@NgModule({
  declarations: [AcquisizioneProgettiCampionatiComponent],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    SharedModule,
    CoreModule,
  ],
  exports: [
    AcquisizioneProgettiCampionatiComponent
  ],
  providers:[
    AcquisizioneCampionamentiService
  ]
})
export class AcquisizioneCampionamentiModule { }
