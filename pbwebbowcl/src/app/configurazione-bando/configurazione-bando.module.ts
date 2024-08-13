/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { LineaInterventoService } from './services/linea-intervento.service';
import { DatiBandoService } from './services/dati-bando.service';
import { CommonModule, registerLocaleData } from '@angular/common';
import localeIt from '@angular/common/locales/it';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { ConfigurazioneBandoService } from './services/configurazione-bando.service';
import { SharedModule } from '../shared/shared.module';
import { ConfiguraBandoComponent } from './components/configura-bando/configura-bando.component';
import { NuovaMacroVoceDialogComponent } from './components/nuova-macro-voce-dialog/nuova-macro-voce-dialog.component';
import { NuovaMicroVoceDialogComponent } from './components/nuova-micro-voce-dialog/nuova-micro-voce-dialog.component';
import { RicercaBandiComponent } from './components/ricerca-bandi/ricerca-bandi.component';
import { ConfiguraBandoLineaComponent } from './components/configura-bando-linea/configura-bando-linea.component';
import { NavigationConfigurazioneBandoService } from './services/navigation-configurazione-bando.service';
import { NuovoTipoDocumentoDialogComponent } from './components/nuovo-tipo-documento-dialog/nuovo-tipo-documento-dialog.component';
import { NuovoModalitaPagamentoDialogComponent } from './components/nuovo-modalita-pagamento-dialog/nuovo-modalita-pagamento-dialog.component';
import { VociSpesaService } from './services/voci-spesa.service';
import { DatiAggiuntiviService } from './services/dati-aggiuntivi.service';
import { NuovaAreaScientificaDialogComponent } from './components/nuova-area-scientifica-dialog/nuova-area-scientifica-dialog.component';
import { ModificaModelloDocumentoDialogComponent } from './components/modifica-modello-documento-dialog/modifica-modello-documento-dialog.component';
import { EnteCompetenzaService } from './services/ente-competenza.service';
import { DatiAggiuntiviBandoLineaService } from './services/dati-aggiuntivi-bando-linea.service';
import { RegoleService } from './services/regole.service';
import { ModelliDocumentoService } from './services/modelli-documento.service';
import { DocModPagService } from './services/doc-mod-pag.service';
import { CheckListService } from './services/checklist.service';
import { CostantiService } from './services/costanti.service';
import { NuovoEstremoBancarioDialog } from './components/configura-bando-linea/nuovo-estremo-bancario-dialog/nuovo-estremo-bancario-dialog.component';
import { ModificaParametroMonitoraggioDialog } from './components/configura-bando-linea/modifica-parametro-monitoraggio-dialog/modifica-parametro-monitoraggio-dialog.component';
registerLocaleData(localeIt, 'it');

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
    ],
    declarations: [
        RicercaBandiComponent,
        ConfiguraBandoComponent,
        NuovaMacroVoceDialogComponent,
        NuovaMicroVoceDialogComponent,
        ConfiguraBandoLineaComponent,
        NuovoTipoDocumentoDialogComponent,
        NuovaAreaScientificaDialogComponent,
        ModificaModelloDocumentoDialogComponent,
        NuovoModalitaPagamentoDialogComponent,
        NuovoEstremoBancarioDialog,
        ModificaParametroMonitoraggioDialog
    ],
    entryComponents: [
    ],
    providers: [
        ConfigurazioneBandoService,
        DatiBandoService,
        VociSpesaService,
        DatiAggiuntiviService,
        LineaInterventoService,
        NavigationConfigurazioneBandoService,
        EnteCompetenzaService,
        DatiAggiuntiviBandoLineaService,
        RegoleService,
        ModelliDocumentoService,
        DocModPagService,
        CheckListService,
        CostantiService
    ]
})
export class ConfigurazioneBandoModule { }