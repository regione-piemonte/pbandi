/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { Routes, RouterModule, ExtraOptions } from '@angular/router';
import { AttivitaComponent } from './attivita/components/attivita/attivita.component';
import { HomeOperatoreComponent } from './home/components/home-operatore/home-operatore.component';
import { HomeSceltaProfiloComponent } from './home/components/home-scelta-profilo/home-scelta-profilo.component';
import { DrawerComponent } from './shared/components/drawer/drawer.component';
import { NotificheComponent } from './attivita/components/notifiche/notifiche.component';
import { NotificheViaMailComponent } from './notifiche-via-mail/components/notifiche-via-mail/notifiche-via-mail.component';
import { LineeFinanziamentoComponent } from './linee-finanziamento/components/linee-finanziamento/linee-finanziamento.component';
import { AvvioProgettoComponent } from './linee-finanziamento/components/avvio-progetto/avvio-progetto.component';
import { ProgettoComponent } from './linee-finanziamento/components/progetto/progetto.component';
import { ErrorRoutingComponent } from './core/components/error-routing/error-routing.component';
import { DashboardHomeComponent } from './dashboard/components/dashboard-home/dashboard-home.component';
import { DashboardSettingsComponent } from './dashboard/components/dashboard-settings/dashboard-settings.component';

const routes: Routes = [
  { path: '', redirectTo: 'homeSceltaProfilo', pathMatch: 'full' },
  { path: 'homeSceltaProfilo', component: HomeSceltaProfiloComponent },
  { path: 'homeOperatore', component: HomeOperatoreComponent },
  { path: 'errorRouting', component: ErrorRoutingComponent },
  {
    path: 'drawer/:id', component: DrawerComponent,
    children: [
      { path: 'dashboardHome', component: DashboardHomeComponent },
      { path: 'dashboardSettings', component: DashboardSettingsComponent },
      { path: 'attivita', component: AttivitaComponent },
      { path: 'notifiche/:idBando/:idProgetto', component: NotificheComponent },
      { path: 'notificheViaMail', component: NotificheViaMailComponent },
      { path: 'lineeDiFinanziamento', component: LineeFinanziamentoComponent },
      { path: 'avvioProgetto/:idLinea', component: AvvioProgettoComponent },
      { path: 'progetto/:idLinea', component: ProgettoComponent },
      { path: 'progetto/:idLinea/:idProgetto', component: ProgettoComponent }
    ]
  },

  { path: '**', redirectTo: 'errorRouting', pathMatch: 'full' }
];

const configRouter: ExtraOptions = {
  enableTracing: false,
  useHash: true
};

@NgModule({
  imports: [RouterModule.forRoot(routes, configRouter)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
