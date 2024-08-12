import { SharedModule } from '../shared/shared.module';
import { MaterialModule } from '../app-material.module';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ArchivioFileContainerComponent } from './components/archivio-file-container/archivio-file-container.component';
import { ArchivioFileModule as ArchivioFileLibModule, CoreModule as CoreLibModule } from '@pbandi/common-lib';

@NgModule({
  imports: [
    MaterialModule,
    CommonModule,
    FormsModule,
    SharedModule,
    ArchivioFileLibModule,
    CoreLibModule
  ],
  exports: [
    ArchivioFileContainerComponent,

  ],
  declarations: [
    ArchivioFileContainerComponent,

  ],
  entryComponents: [
  ],
  providers: [
  ]
})
export class ArchivioFileContainerModule { }
