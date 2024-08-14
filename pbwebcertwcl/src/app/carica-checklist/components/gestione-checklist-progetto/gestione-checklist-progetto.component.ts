/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';
import { PropostaCertificazioneDTO } from 'src/app/shared/commons/dto/proposta-certificazione-dto';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { BeneficiarioDTO } from '../../../shared/commons/dto/beneficiario-dto';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { CertificazioneService } from 'src/app/shared/services/certificazione.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { ProgettoDTO } from 'src/app/shared/commons/dto/progetto-dto';
import { LineaInterventoDTO } from 'src/app/shared/commons/dto/linea-intervento-dto';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-gestione-checklist-progetto',
  templateUrl: './gestione-checklist-progetto.component.html',
  styleUrls: ['./gestione-checklist-progetto.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneChecklistProgettoComponent implements OnInit {
  i: number;
  idProposta: number;
  proposta: PropostaCertificazioneDTO;
  attivita: Array<LineaInterventoDTO>;
  attivitaSelected: LineaInterventoDTO;
  beneficiari: Array<BeneficiarioDTO>;
  beneficiarioSelected: BeneficiarioDTO;
  progetti: Array<ProgettoDTO>;
  progettoSelected: ProgettoDTO;

  criteriRicercaOpened: boolean = true;

  elencoProgetti: Array<PropostaCertificazioneDTO>;

  dataSource: ElencoProgettiProposteDatasource;
  displayedColumns: string[] = ['attivita', 'beneficiario', 'codiceprogetto', 'contributopubblico', 'cofinanziamento', 'toterogato', 'totpagamenti', 'totfideiussioni', 'spesacertificabile', 'azioni'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  //LOADED
  loadedProposta: boolean;
  loadedAttivita: boolean;
  loadedBeneficiari: boolean;
  loadedProgetti: boolean;
  loadedElencoProgetti: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private certificazioneService: CertificazioneService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProposta = +params['id'];
      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.loadedProposta = false;
          this.loadedAttivita = false;
          this.subscribers.proposta = this.certificazioneService.getProposta(this.idProposta).subscribe(data1 => {
            if (data1) {
              this.proposta = data1;

              this.subscribers.attivita = this.certificazioneService.getAttivitaLineaIntervento(this.proposta.idLineaDiIntervento).subscribe(data2 => {
                if (data2) {
                  this.attivita = data2;
                  let a = new LineaInterventoDTO(0, "", "");
                  this.attivita.unshift(a);
                }
                this.loadedAttivita = true;
              }, err => {
                this.handleExceptionService.handleNotBlockingError(err);
                this.loadedAttivita = true;
              });
              this.loadBeneficiari();

              this.loadProgetti();
            }
            this.loadedProposta = true;
          }, err => {
            this.handleExceptionService.handleBlockingError(err);
          });
        }
      });
    });
  }

  loadBeneficiari() {
    this.loadedBeneficiari = false;
    this.subscribers.beneficiari = this.certificazioneService.getAllBeneficiari(this.idProposta, this.attivitaSelected ? this.attivitaSelected.idLineaDiIntervento : this.proposta.idLineaDiIntervento, null).subscribe(data => {
      if (data) {
        this.beneficiari = data;
        this.beneficiari.unshift(new BeneficiarioDTO("", "", "", 0, "", null, ""));
        this.beneficiarioSelected = null;
      }
      this.loadedBeneficiari = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  loadProgetti() {
    this.loadedProgetti = false;
    this.subscribers.progetti = this.certificazioneService.getAllProgetti(this.idProposta, this.attivitaSelected ? this.attivitaSelected.idLineaDiIntervento : this.proposta.idLineaDiIntervento,
      null,
      this.beneficiarioSelected ? this.beneficiarioSelected.idSoggetto : null, null).subscribe(data => {
        if (data) {
          this.progetti = data;
          this.progetti.unshift(new ProgettoDTO(0, "", "", "", ""));
          this.progettoSelected = null;
        }
        this.loadedProgetti = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
  }

  onSelectAttivita() {
    this.elencoProgetti = null;
    this.dataSource = null;
    if (this.attivitaSelected.idLineaDiIntervento === 0) {
      this.attivitaSelected = null;
    }
    this.loadBeneficiari();
    this.loadProgetti();
  }

  onSelectBeneficiario() {
    this.elencoProgetti = null;
    this.dataSource = null;
    if (this.beneficiarioSelected.idSoggetto === 0) {
      this.beneficiarioSelected = null;
    }
    this.loadProgetti()
  }

  onSelectProgetto() {
    this.elencoProgetti = null;
    this.dataSource = null;
    if (this.progettoSelected.idProgetto === 0) {
      this.progettoSelected = null;
    }
    //filtro altre combo
  }

  ricerca() {
    this.loadedElencoProgetti = false;
    this.subscribers.progetti = this.certificazioneService.getProgettiProposta(this.idProposta, this.progettoSelected != null ? this.progettoSelected.idProgetto : null,
      this.attivitaSelected != null ? this.attivitaSelected.idLineaDiIntervento : null, this.beneficiarioSelected != null ? this.beneficiarioSelected.idSoggetto : null)
      .subscribe(data => {
        if (data) {
          this.elencoProgetti = data;
          this.dataSource = new ElencoProgettiProposteDatasource(this.elencoProgetti);
          setTimeout(() => {
            this.paginator.length = this.elencoProgetti.length;
            this.paginator.pageIndex = 0;
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;
          }, 1000);

        }
        this.loadedElencoProgetti = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
  }

  goToCaricaChecklist() {
    this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_CERTIFICAZIONE_CARICA_CHECKLIST + "/caricaChecklist");
  }

  goToGestioneChecklist(idDettPropostaCertif: number) {
    this.router.navigate(['/drawer/' + Constants.ID_OPERAZIONE_CERTIFICAZIONE_CARICA_CHECKLIST + "/gestioneChecklist/" + this.idProposta, { action: idDettPropostaCertif }]);
  }

  isLoading() {
    if (!this.loadedProposta || !this.loadedAttivita || !this.loadedBeneficiari || !this.loadedElencoProgetti || !this.loadedProgetti) {
      return true;
    }
    return false;
  }
}


export class ElencoProgettiProposteDatasource extends MatTableDataSource<PropostaCertificazioneDTO> {

  private filterByLineaInterventoEnable: boolean = true;
  private idLineaIntervento: number;


  _orderData(data: PropostaCertificazioneDTO[]): PropostaCertificazioneDTO[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "numproposta";
    }
    let direction = this.sort.direction || "asc";
    let sortedData = null;
    // viene riordinato in ordine ascendente
    switch (this.sort.active) {
      case "attivita":
        sortedData = data.sort((propA, propB) => {
          return (propA.descBreveCompletaAttivita + " - " + propA.attivita).localeCompare(propB.descBreveCompletaAttivita + " - " + propB.attivita);
        });
        break;
      case "beneficiario":
        sortedData = data.sort((propA, propB) => {
          return propA.beneficiario.localeCompare(propB.beneficiario);
        });
        break;
      case "codiceprogetto":
        sortedData = data.sort((propA, propB) => {
          return propA.codiceVisualizzato.localeCompare(propB.codiceVisualizzato);
        });
        break;
      case "contributopubblico":
        sortedData = data.sort((propA, propB) => {
          return propA.percContributoPubblico - propB.percContributoPubblico;
        });
        break;
      case "cofinanziamento":
        sortedData = data.sort((propA, propB) => {
          return propA.percCofinFesr - propB.percCofinFesr;
        });
        break;
      case "toterogato":
        sortedData = data.sort((propA, propB) => {
          return propA.importoErogazioni - propB.importoErogazioni;
        });
        break;
      case "totpagamenti":
        sortedData = data.sort((propA, propB) => {
          return propA.importoPagamentiValidati - propB.importoPagamentiValidati;
        });
        break;
      case "totfideiussioni":
        sortedData = data.sort((propA, propB) => {
          return propA.importoFideiussioni - propB.importoFideiussioni;
        });
        break;
      case "spesacertificabile":
        sortedData = data.sort((propA, propB) => {
          return propA.spesaCertificabileLorda - propB.spesaCertificabileLorda;
        });
        break;
      default:
        sortedData = data.sort((propA, propB) => {
          return (propA.descBreveCompletaAttivita + " - " + propA.attivita).localeCompare(propB.descBreveCompletaAttivita + " - " + propB.attivita);
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: PropostaCertificazioneDTO[]) {
    super(data);
  }
}
