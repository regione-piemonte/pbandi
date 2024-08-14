/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { LineaInterventoDTO } from 'src/app/shared/commons/dto/linea-intervento-dto';
import { Constants } from 'src/app/core/commons/util/constants';
import { PropostaCertificazioneDTO } from 'src/app/shared/commons/dto/proposta-certificazione-dto';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { CertificazioneService } from 'src/app/shared/services/certificazione.service';
import { ProposteCertificazioneRequest } from 'src/app/shared/commons/requests/proposte-certificazione-request';
import { PropostaCertifLineaRequest } from 'src/app/shared/commons/requests/proposta-certif-linea-request';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-carica-checklist',
  templateUrl: './carica-checklist.component.html',
  styleUrls: ['./carica-checklist.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class CaricaChecklistComponent implements OnInit {
  user: UserInfoSec;

  stati: Array<string>;
  lineeIntervento: Array<LineaInterventoDTO>;
  lineaDiInterventoSelected: LineaInterventoDTO;
  proposte: Array<PropostaCertificazioneDTO>;

  dataSource: ElencoProposteDatasource;
  displayedColumns: string[] = ['numproposta', 'descproposta', 'statoproposta', 'datacreazione', 'azioni'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  //LOADED
  loadedLineeIntervento: boolean;
  loadedProposte: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private router: Router,
    private certificazioneService: CertificazioneService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;

        this.stati = new Array<string>();
        this.stati.push(Constants.DESC_BREVE_STATO_PROPOSTA_APPROVATA);
        this.stati.push(Constants.DESC_BREVE_STATO_PROPOSTA_APERTA);
        this.loadedProposte = false;
        this.loadedLineeIntervento = false;
        let requestProposte = new ProposteCertificazioneRequest(this.user, this.stati);
        this.subscribers.findProposte = this.certificazioneService.findProposteCertificazione(requestProposte).subscribe(data1 => {
          if (data1) {
            this.proposte = data1;
            this.dataSource = new ElencoProposteDatasource(this.proposte);
            this.paginator.length = this.proposte.length;
            this.paginator.pageIndex = 0;
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;
            let idLinee = new Array<number>();
            for (let id of this.proposte.map(p => p.idLineaDiIntervento)) {
              if (!idLinee.includes(id)) {
                idLinee.push(id);
              }
            }
            let requestLinee = new PropostaCertifLineaRequest(this.user, idLinee);
            this.subscribers.getLinee = this.certificazioneService.getLineeDiInterventoFromIdLinee(requestLinee).subscribe(data2 => {
              if (data2) {
                this.lineeIntervento = data2;
                this.lineeIntervento.unshift(new LineaInterventoDTO(0, "", "Tutte"));
                this.lineaDiInterventoSelected = this.lineeIntervento.find(l => l.idLineaDiIntervento === 0);
                this.filterByLineaIntervento();
              }
              this.loadedLineeIntervento = true;
            }, err => {
              this.handleExceptionService.handleNotBlockingError(err);
              this.loadedLineeIntervento = true;
            });
          }
          this.loadedProposte = true;
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
        });
      }
    });
  }



  goToGestioneChecklist(idProposta: number) {
    this.router.navigateByUrl("/drawer/" + Constants.ID_OPERAZIONE_CERTIFICAZIONE_CARICA_CHECKLIST + "/gestioneChecklist/" + idProposta);
  }

  goToGestioneChecklistProgetto(idProposta: number) {
    this.router.navigateByUrl("/drawer/" + Constants.ID_OPERAZIONE_CERTIFICAZIONE_CARICA_CHECKLIST + "/gestioneChecklistProgetto/" + idProposta);
  }

  filterByLineaIntervento() {
    this.dataSource.filterByLineaIntervento(this.lineaDiInterventoSelected.idLineaDiIntervento);
    this.paginator.pageIndex = 0;
    this.paginator.length = this.dataSource.filteredData.length;
  }

  isLoading() {
    if (!this.lineaDiInterventoSelected || !this.loadedProposte) {
      return true;
    }
    return false;
  }
}

export class ElencoProposteDatasource extends MatTableDataSource<PropostaCertificazioneDTO> {

  private filterByLineaInterventoEnable: boolean = true;
  private idLineaIntervento: number;

  filterByLineaIntervento(idLineaIntervento: number) {
    this.idLineaIntervento = idLineaIntervento;
    this.filteredData = this._filterData(this.data);
    this._updateChangeSubscription();
  }

  _filterData(data: PropostaCertificazioneDTO[]): PropostaCertificazioneDTO[] {
    return data.filter((item: PropostaCertificazioneDTO) => {

      let control = undefined;

      if (this.filterByLineaInterventoEnable) {
        if (this.idLineaIntervento === 0) {
          control = true;
        } else {
          if (item.idLineaDiIntervento === this.idLineaIntervento) {
            control = true;
          } else {
            control = false;
          }
        }
      }

      if (control != null && control != undefined) {
        if (control) return true;
        else return false;
      }
      else
        return true;
    });
  }


  _orderData(data: PropostaCertificazioneDTO[]): PropostaCertificazioneDTO[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "numproposta";
    }
    let direction = this.sort.direction || "asc";
    let sortedData = null;

    // viene riordinato in ordine ascendente 
    switch (this.sort.active) {
      case "numproposta":
        sortedData = data.sort((propA, propB) => {
          return propA.idPropostaCertificaz - propB.idPropostaCertificaz;
        });
        break;
      case "descproposta":
        sortedData = data.sort((propA, propB) => {
          return propA.descProposta.localeCompare(propB.descProposta);
        });
        break;
      case "statoproposta":
        sortedData = data.sort((propA, propB) => {
          return propA.descStatoPropostaCertif.localeCompare(propB.descStatoPropostaCertif);
        });
        break;
      case "datacreazione":
        sortedData = data.sort((propA, propB) => {
          let datanumA = new Date(propA.dtOraCreazione).toLocaleDateString();
          let datanumB = new Date(propB.dtOraCreazione).toLocaleDateString();
          return datanumA.localeCompare(datanumB);
        });
        break;
      default:
        sortedData = data.sort((propA, propB) => {
          return propA.idPropostaCertificaz - propB.idPropostaCertificaz;
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: PropostaCertificazioneDTO[]) {
    super(data);
  }
}
