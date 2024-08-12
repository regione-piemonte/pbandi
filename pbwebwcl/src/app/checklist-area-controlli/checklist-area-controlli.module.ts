import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GestioneChecklistControlliComponent } from './components/gestione-checklist-controlli/gestione-checklist-controlli.component';
import { SharedModule } from '../shared/shared.module';
import { ChecklistModule } from '../checklist/checklist.module';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '@pbandi/common-lib';



@NgModule({
  declarations: [GestioneChecklistControlliComponent],
  imports: [
    CommonModule,
    SharedModule, 
    ChecklistModule,
    FormsModule,
    MaterialModule
  ]
})
export class ChecklistAreaControlliModule { }
