import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { CronoprogrammaService } from './services/cronoprogramma.service';
import { CronoprogrammaComponent } from './components/cronoprogramma/cronoprogramma.component';
import { CronoprogrammaFasiContainerComponent } from './components/cronoprogramma-fasi-container/cronoprogramma-fasi-container.component';
import { CronoprogrammaFasiModule as CronoprogrammaFasiLibModule } from '@pbandi/common-lib';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        CronoprogrammaFasiLibModule
    ],
    exports: [
        CronoprogrammaComponent,
        CronoprogrammaFasiContainerComponent
    ],
    declarations: [
        CronoprogrammaComponent,
        CronoprogrammaFasiContainerComponent
    ],
    entryComponents: [
    ],
    providers: [
        CronoprogrammaService
    ]
})
export class CronoprogrammaModule { }
