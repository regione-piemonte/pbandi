import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MaterialModule } from './../app-material.module';
import { ChecklistComponent } from './components/checklist/checklist.component';
import { SharedModule } from '../shared/shared.module';
import { CheckListService } from '../checklist/services/checkList.service';
import { NuovaChecklistComponent } from './components/nuova-checklist/nuova-checklist.component';
import { ChecklistTableComponent } from './components/checklist-table/checklist-table.component';
import { ArchivioFileModule, DatiProgettoAttivitaPregresseModule } from '@pbandi/common-lib';
import { DocumentiProgettoModule } from '../documenti-progetto/documenti-progetto.module';
import { AllegaVerbaleDialogComponent } from './components/allega-verbale-dialog/allega-verbale-dialog.component';
import { NavigationChecklistService } from './services/navigation-checklist.service';

@NgModule({
  imports: [
    MaterialModule,
    CommonModule,
    FormsModule,
    SharedModule,
    ArchivioFileModule,
    DatiProgettoAttivitaPregresseModule,
    DocumentiProgettoModule
  ],
  exports: [
    ChecklistComponent,
    NuovaChecklistComponent,
    ChecklistTableComponent,
    AllegaVerbaleDialogComponent
  ],
  declarations: [
    ChecklistComponent,
    NuovaChecklistComponent,
    ChecklistTableComponent,
    AllegaVerbaleDialogComponent
  ],
  entryComponents: [
  ],
  providers: [
    CheckListService,
    NavigationChecklistService
  ]
})

export class ChecklistModule { };