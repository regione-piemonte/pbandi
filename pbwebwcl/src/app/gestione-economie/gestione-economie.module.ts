import { SharedModule } from '../shared/shared.module';
import { MaterialModule } from '../app-material.module';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { GestioneEconomieComponent } from './components/gestione-economie/gestione-economie.component';
import { GestioneEconomieService } from './services/gestione-economie.service';
import { EconomiaComponent } from './components/economia/economia.component';
import { GestioneQuoteDialogComponent } from './components/gestione-quote-dialog/gestione-quote-dialog.component';
import { NavigationGestioneEconomieService } from './services/navigation-gestione-economie.service';
import { DettaglioProgettoDialogComponent } from './components/dettaglio-progetto-dialog/dettaglio-progetto-dialog.component';


@NgModule({
  imports: [
    MaterialModule,
    CommonModule,
    FormsModule,
    SharedModule
  ],
  exports: [
    GestioneEconomieComponent,
    EconomiaComponent,
    GestioneQuoteDialogComponent,
    DettaglioProgettoDialogComponent
  ],
  declarations: [
    GestioneEconomieComponent,
    EconomiaComponent,
    GestioneQuoteDialogComponent,
    DettaglioProgettoDialogComponent
  ],
  entryComponents: [
  ],
  providers: [
    GestioneEconomieService,
    NavigationGestioneEconomieService
  ]
})
export class GestioneEconomieModule { }
