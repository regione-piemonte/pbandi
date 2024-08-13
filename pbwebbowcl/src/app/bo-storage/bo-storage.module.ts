/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BoStorageComponent } from './components/bo-storage/bo-storage.component';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { ArchivioFileModule as ArchivioFileLibModule } from '@pbandi/common-lib';


@NgModule({
  declarations: [
    BoStorageComponent
  ],
  imports: [
    MaterialModule,
    CommonModule,
    FormsModule,
    SharedModule,
    ArchivioFileLibModule
  ]
})
export class BoStorageModule { }
