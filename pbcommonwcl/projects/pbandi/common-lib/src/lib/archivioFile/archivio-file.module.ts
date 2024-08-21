/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AnteprimaDialogComponent } from './components/anteprima-dialog/anteprima-dialog.component';
import { UploadDialogComponent } from './components/upload-dialog/upload-dialog.component';
import { InfoDialogComponent } from './components/info-dialog/info-dialog.component';
import { SpostaDialogComponent } from './components/sposta-dialog/sposta-dialog.component';
import { RinominaDialogComponent } from './components/rinomina-dialog/rinomina-dialog.component';
import { EliminaDialogComponent } from './components/elimina-dialog/elimina-dialog.component';
import { NuovaCartellaDialogComponent } from './components/nuova-cartella-dialog/nuova-cartella-dialog.component';
import { SharedModule } from './../shared/shared.module';
import { ArchivioFileService } from './services/archivio-file.service';
import { MaterialModule } from '../app-material.module';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ArchivioFileComponent } from './components/archivioFile/archivio-file.component';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { SafeHtmlPipe } from '../safeHTML.pipe';
import { ArchivioFileDialogComponent } from './components/archivio-file-dialog/archivio-file-dialog.component';
import { ResizableModule } from 'angular-resizable-element';


@NgModule({
  imports: [
    MaterialModule,
    CommonModule,
    FormsModule,
    SharedModule,
    PdfViewerModule,
    ResizableModule
  ],
  exports: [
    ArchivioFileComponent,
    NuovaCartellaDialogComponent,
    EliminaDialogComponent,
    RinominaDialogComponent,
    SpostaDialogComponent,
    InfoDialogComponent,
    UploadDialogComponent,
    AnteprimaDialogComponent,
    SafeHtmlPipe,
    ArchivioFileDialogComponent
  ],
  declarations: [
    ArchivioFileComponent,
    NuovaCartellaDialogComponent,
    EliminaDialogComponent,
    RinominaDialogComponent,
    SpostaDialogComponent,
    InfoDialogComponent,
    UploadDialogComponent,
    AnteprimaDialogComponent,
    SafeHtmlPipe,
    ArchivioFileDialogComponent
  ],
  entryComponents: [
  ],
  providers: [
    ArchivioFileService
  ]
})
export class ArchivioFileModule { }
