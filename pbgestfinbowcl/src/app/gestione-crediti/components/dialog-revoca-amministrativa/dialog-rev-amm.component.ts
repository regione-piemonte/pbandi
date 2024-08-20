/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatButtonModule} from '@angular/material/button';
import {MatTableModule} from '@angular/material/table';
import {MatTableDataSource} from '@angular/material/table';
import {MatDividerModule} from '@angular/material/divider';
import {MatPaginator} from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Observable } from 'rxjs';
import { MatSort } from '@angular/material/sort';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import { startWith, debounceTime, distinctUntilChanged, switchMap, map } from 'rxjs/operators';
import {MatIconModule} from '@angular/material/icon';
import { animate, state, style, transition, trigger } from '@angular/animations';
import {MatListModule} from '@angular/material/list';
import {MatDialogModule, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { DettaglioFinanziamentoErogato } from 'src/app/gestione-crediti/commons/dto/dettaglio-finanziamento-erogato';
import { CDK_CONNECTED_OVERLAY_SCROLL_STRATEGY_PROVIDER_FACTORY } from '@angular/cdk/overlay/overlay-directives';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { SchedaClienteHistory } from '../../commons/dto/scheda-cliente-history';
//import { Tracing } from 'trace_events';
import { SchedaClienteMain } from '../../commons/dto/scheda-cliente-main';
import { FinanziamentoErogato } from '../../commons/dto/finanziamento-erogato';
import { RicBenResponseService } from '../../services/ricben-response-service.service';
import { RevocaAmministrativaDTO } from '../../commons/dto/revoca-amministrativa-dto';




@Component({
  selector: 'dialog-rev-amm',
  templateUrl: './dialog-rev-amm.component.html',
  styleUrls: ['./dialog-rev-amm.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})



export class RevAmmDialogComponent implements OnInit {
  
  @ViewChild(MatPaginator) paginator: MatPaginator;

  public subscribers: any = {};

  // 0 = main
  // 1 = loading
  // 2 = error/no data
  faceId: number = 1;

  dettaglioFin: DettaglioFinanziamentoErogato = new DettaglioFinanziamentoErogato();
  revocam: Array<RevocaAmministrativaDTO> = new Array<RevocaAmministrativaDTO>();
  displayedColumns: string[] = ['numeroRevoca', 'dataNotificaProvRevoca', 'tipoRevoca', 'descStatoRevoca',  'descCausa', 'actions'];

  constructor(
    public dialogRef: MatDialogRef<RevAmmDialogComponent>,
    private resService: RicBenResponseService,
    @Inject(MAT_DIALOG_DATA) public data: any) {

      this.dettaglioFin = data.row;
      console.log("pass data:", data)

    }



  ngOnInit(): void {
    //console.log("dial: ", this.statoAzienda_dataInizio)
    this.faceId = 1;
    this.resService.getRevocaAmministrativa(this.dettaglioFin.idProgetto, this.dettaglioFin.idModalitaAgevolazioneOrig, this.dettaglioFin.idModalitaAgevolazioneRif, this.dettaglioFin.listaRevoche).subscribe(data => {
        if (data && data.length > 0) {
          this.revocam = data;
          console.log(this.revocam);
          this.faceId = 0
        } else {
          this.faceId = 2
        }
    }, err => {
        this.faceId = 2
    });
  }


  
  
}
