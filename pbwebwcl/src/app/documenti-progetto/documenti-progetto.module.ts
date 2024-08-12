import { DocumentiProgettoService } from './services/documenti-progetto.service';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { DocumentiProgettoComponent } from './components/documenti-progetto/documenti-progetto.component';
import { SharedModule } from '../shared/shared.module';
import { UploadDocumentoFirmatoDialogComponent } from './components/upload-documento-firmato-dialog/upload-documento-firmato-dialog.component';
import { UploadDocumentiProgettoDialogComponent } from './components/upload-documenti-progetto-dialog/upload-documenti-progetto-dialog.component';
import { ArchivioFileModule as ArchivioFileLibModule, CoreModule as CoreLibModule, HelpModule as HelpLibModule } from '@pbandi/common-lib';
import { LinkDocumentiActaDialogComponent } from './components/link-documenti-acta-dialog/link-documenti-acta-dialog.component';


@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        ArchivioFileLibModule,
        CoreLibModule,
        HelpLibModule
    ],
    exports: [
        DocumentiProgettoComponent,
        UploadDocumentoFirmatoDialogComponent,
        UploadDocumentiProgettoDialogComponent,
        LinkDocumentiActaDialogComponent
    ],
    declarations: [
        DocumentiProgettoComponent,
        UploadDocumentoFirmatoDialogComponent,
        UploadDocumentiProgettoDialogComponent,
        LinkDocumentiActaDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
        DocumentiProgettoService
    ]
})
export class DocumentiProgettoModule { }