/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { NotificheViaMailComponent } from './components/notifiche-via-mail/notifiche-via-mail.component';
import { NotificheViaMailService } from './services/notifiche-via-mail.service';
import { AssociaProgettiDialog } from './components/associa-progetti-dialog/associa-progetti-dialog.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        NotificheViaMailComponent,
        AssociaProgettiDialog
    ],
    declarations: [
        NotificheViaMailComponent,
        AssociaProgettiDialog
    ],
    entryComponents: [
    ],
    providers: [
        NotificheViaMailService
    ]
})
export class NotificheViaMailModule { }