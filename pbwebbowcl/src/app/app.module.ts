/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigurazioneBandoModule } from './configurazione-bando/configurazione-bando.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './app-material.module';
import { HttpClientModule } from '@angular/common/http';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { AssociazionePermessoRuoloModule } from './associazione-permesso-ruolo/associazione-permesso-ruolo.module';
import { AssociazioneRuoloPermessoModule } from './associazione-ruolo-permesso/associazione-ruolo-permesso.module';
import { CambiaBeneficiarioModule } from './cambia-beneficiario/cambia-beneficiario.module';
import { GestioneUtentiModule } from './gestione-utenti/gestione-utenti.module';
import { AssociazioneIstruttoreProgettiModule } from './associazione-istruttore-progetti/associazione-istruttore-progetti.module';
import { GestioneNewsModule } from './gestione-news/gestione-news.module';
import { GestioneTemplatesModule } from './gestione-templates/gestione-templates.module';
import { ConsoleApplicativaModule } from './console-applicativa/console-applicativa.module';
import { BoStorageModule } from './bo-storage/bo-storage.module';
import { ArchivioFileModule as ArchivioFileLibModule } from '@pbandi/common-lib';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    CoreModule,
    HttpClientModule,
    ConfigurazioneBandoModule,
    SharedModule,
    AssociazionePermessoRuoloModule,
    AssociazioneRuoloPermessoModule,
    CambiaBeneficiarioModule,
    GestioneUtentiModule,
    AssociazioneIstruttoreProgettiModule,
    GestioneNewsModule,
    GestioneTemplatesModule,
    ConsoleApplicativaModule,
    BoStorageModule,
    ArchivioFileLibModule,
    FormsModule,
    MatIconModule,
  ],
  declarations: [
    AppComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
