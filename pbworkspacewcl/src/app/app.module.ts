/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './app-material.module'
import { CoreModule } from './core/core.module';
import { HttpClientModule } from '@angular/common/http';
import { HomeModule } from './home/home.module';
import { AttivitaModule } from './attivita/attivita.module';
import { SharedModule } from './shared/shared.module';
import { NotificheViaMailModule } from './notifiche-via-mail/notifiche-via-mail.module';
import { LineeFinanziamentoModule } from './linee-finanziamento/linee-finanziamento.module';
import { HelpModule as HelpLibModule } from '@pbandi/common-lib';
import { QuillModule } from 'ngx-quill';
import { DashboardModule } from './dashboard/dashboard.module';

@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    CoreModule,
    HttpClientModule,
    HomeModule,
    AttivitaModule,
    SharedModule,
    NotificheViaMailModule,
    LineeFinanziamentoModule,
    HelpLibModule,
    QuillModule.forRoot(),
    DashboardModule
  ],
  declarations: [
    AppComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
