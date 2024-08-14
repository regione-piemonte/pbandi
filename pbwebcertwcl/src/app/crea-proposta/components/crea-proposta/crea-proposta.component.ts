/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { BandoLineaDTO } from 'src/app/gestione-proposte/commons/dto/bando-linea-dto';
import { BeneficiarioDTO } from 'src/app/shared/commons/dto/beneficiario-dto';
import { CodiceDescrizione } from 'src/app/shared/commons/dto/codice-descrizione';
import { LineaInterventoDTO } from 'src/app/shared/commons/dto/linea-intervento-dto';
import { ProgettoDTO } from 'src/app/shared/commons/dto/progetto-dto';
import { PropostaCertificazioneDTO } from 'src/app/shared/commons/dto/proposta-certificazione-dto';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { CertificazioneService } from 'src/app/shared/services/certificazione.service';
import { PropostaProgettoDTO } from '../../commons/dto/proposta-progetto-dto';
import { AccodaPropostaRequest } from '../../commons/requests/accoda-proposta-request';
import { AmmettiESospendiProgettiRequest } from '../../commons/requests/ammetti-e-sospendi-progetti-request';
import { LanciaPropostaRequest } from '../../commons/requests/lancia-proposta-request';
import { ExcelService } from "../../../shared/services/excel.service";
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-crea-proposta',
  templateUrl: './crea-proposta.component.html',
  styleUrls: ['./crea-proposta.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class CreaPropostaComponent implements OnInit {

  idProposta: number;
  lineaDiIntervento: LineaInterventoDTO;
  isBozza: boolean;
  criteriRicercaOpened: boolean = true;
  bandiLinea: Array<BandoLineaDTO>;
  bandoLineaSelected: BandoLineaDTO;
  beneficiari: Array<BeneficiarioDTO>;
  beneficiarioSelected: BeneficiarioDTO;
  progetti: Array<ProgettoDTO>;
  progettoSelected: ProgettoDTO;

  elencoProgettiAmmessi: Array<PropostaProgettoDTO>;
  elencoProgettiSospesi: Array<PropostaProgettoDTO>;

  idDettPropAmmessiChecked: Array<number> = new Array<number>();
  allCheckedAmmessi: boolean;
  idDettPropSospesiChecked: Array<number> = new Array<number>();
  allCheckedSospesi: boolean;

  dataSourceAmmessi: ElencoPropostaProgettiDatasource;
  dataSourceSospesi: ElencoPropostaProgettiDatasource;
  displayedColumnsAmmessi: string[] = ['check', 'codiceprogetto', 'beneficiario', 'importocert', 'pagamenti', 'importorev', 'lineaintervento'];
  displayedColumnsSospesi: string[] = ['check', 'codiceprogetto', 'beneficiario', 'importocert', 'pagamenti', 'importorev'];


  @ViewChild("paginatorAmmessi", { static: true }) paginatorAmmessi: MatPaginator;
  @ViewChild("sortAmmessi", { static: true }) sortAmmessi: MatSort;
  @ViewChild("paginatorSospesi", { static: true }) paginatorSospesi: MatPaginator;
  @ViewChild("sortSospesi", { static: true }) sortSospesi: MatSort;


  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedBandiLinea: boolean;
  loadedBeneficiari: boolean;
  loadedProgetti: boolean;
  loadedCerca: boolean = true;
  loadedOperazione: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private certificazioneService: CertificazioneService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog,
    private excelService: ExcelService
  ) { }

  ngOnInit(): void {
    if (this.activatedRoute.snapshot.paramMap.get('idLineaDiIntervento') != null) {
      this.lineaDiIntervento = new LineaInterventoDTO(+this.activatedRoute.snapshot.paramMap.get('idLineaDiIntervento'), "", this.activatedRoute.snapshot.paramMap.get('descLinea'));
    }
    if (this.activatedRoute.snapshot.paramMap.get('isBozza') != null) {
      this.isBozza = this.activatedRoute.snapshot.paramMap.get('isBozza') === 'S' ? true : false;
    }

    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProposta = +params['id'];
      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.loadBandiLinea();
          this.loadBeneficiari();
          this.loadProgetti();
          this.ricerca();
        }
      });
    });
  }

  loadBandiLinea() {
    this.loadedBandiLinea = false;
    this.subscribers.bandiLinea = this.certificazioneService.getAllBandoLineaPerAnteprima(this.idProposta).subscribe(data => {
      if (data) {
        this.bandiLinea = data;
        this.bandiLinea.unshift(new BandoLineaDTO(0, ""));
        this.bandoLineaSelected = null;
      }
      this.loadedBandiLinea = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedBandiLinea = true;
    });
  }

  loadBeneficiari() {
    this.loadedBeneficiari = false;
    this.subscribers.beneficiari = this.certificazioneService.getAllBeneficiariPerAnteprima(this.idProposta, this.bandoLineaSelected ? this.bandoLineaSelected.progrBandoLineaIntervento : null).subscribe(data => {
      if (data) {
        this.beneficiari = data;
        this.beneficiari.unshift(new BeneficiarioDTO("", "", "", 0, "", null, ""));
        this.beneficiarioSelected = null;
      }
      this.loadedBeneficiari = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedBeneficiari = true;
    });
  }

  loadProgetti() {
    this.loadedProgetti = false;
    this.subscribers.progetti = this.certificazioneService.getAllProgettiPerAnteprima(this.idProposta, this.bandoLineaSelected ? this.bandoLineaSelected.progrBandoLineaIntervento : null,
      this.beneficiarioSelected ? this.beneficiarioSelected.idSoggetto : null).subscribe(data => {
        if (data) {
          this.progetti = data;
          this.progetti.unshift(new ProgettoDTO(0, "", "", "", ""));
          this.progettoSelected = null
        }
        this.loadedProgetti = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.loadedProgetti = true;
      });
  }

  onSelectBandoLinea() {
    if (this.bandoLineaSelected.progrBandoLineaIntervento === 0) {
      this.bandoLineaSelected = null;
    }
    this.loadBeneficiari();
    this.loadProgetti();
  }

  onSelectBeneficiario() {
    if (this.beneficiarioSelected.idSoggetto === 0) {
      this.beneficiarioSelected = null;
    }
    this.loadProgetti();
  }

  onSelectProgetto() {
    if (this.progettoSelected.idProgetto === 0) {
      this.progettoSelected = null;
    }
  }

  ricerca(message?: string) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedCerca = false;
    this.subscribers.cerca = this.certificazioneService.getAnteprimaProposta(this.idProposta, this.bandoLineaSelected ? this.bandoLineaSelected.progrBandoLineaIntervento : null,
      this.beneficiarioSelected ? this.beneficiarioSelected.idSoggetto : null, this.progettoSelected ? this.progettoSelected.idProgetto : null, this.lineaDiIntervento.idLineaDiIntervento).subscribe(data => {
        if (data) {
          this.elencoProgettiAmmessi = data.filter(d => d.flagAttivo);
          this.elencoProgettiSospesi = data.filter(d => !d.flagAttivo);

          if (this.elencoProgettiAmmessi && this.elencoProgettiAmmessi.length > 0) {
            this.dataSourceAmmessi = new ElencoPropostaProgettiDatasource(this.elencoProgettiAmmessi);
            this.paginatorAmmessi.length = this.elencoProgettiAmmessi.length;
            this.paginatorAmmessi.pageIndex = 0;
            this.dataSourceAmmessi.paginator = this.paginatorAmmessi;
            this.dataSourceAmmessi.sort = this.sortAmmessi;
          } else {
            this.elencoProgettiAmmessi = null;
            this.dataSourceAmmessi = null;
          }
          if (this.elencoProgettiSospesi && this.elencoProgettiSospesi.length > 0) {
            this.dataSourceSospesi = new ElencoPropostaProgettiDatasource(this.elencoProgettiSospesi);
            this.paginatorSospesi.length = this.elencoProgettiSospesi.length;
            this.paginatorSospesi.pageIndex = 0;
            this.dataSourceSospesi.paginator = this.paginatorSospesi;
            this.dataSourceSospesi.sort = this.sortSospesi;
          } else {
            this.elencoProgettiSospesi = null;
            this.dataSourceSospesi = null;
          }
          this.criteriRicercaOpened = false;
        } else {
          this.elencoProgettiAmmessi = null;
          this.elencoProgettiSospesi = null;
          this.dataSourceAmmessi = null;
          this.dataSourceSospesi = null;
        }
        if (message) {
          this.showMessageSuccess(message);
        }
        this.loadedCerca = true;
      }), err => {
        this.handleExceptionService.handleBlockingError(err);
      };
  }

  selectAllCheckbox(type: string, e: any) {
    if (type === 'A') {
      this.dataSourceAmmessi.filteredData = this.dataSourceAmmessi._filterData(this.dataSourceAmmessi.data);
      this.dataSourceAmmessi.filteredData.map(data => {
        data.checked = e.checked;
      });
      if (e.checked) {
        this.idDettPropAmmessiChecked = this.elencoProgettiAmmessi.map(n => n.idPreviewDettPropCer);
      } else {
        this.idDettPropAmmessiChecked = new Array<number>();
      }
    } else if (type == 'S') {
      this.dataSourceSospesi.filteredData = this.dataSourceSospesi._filterData(this.dataSourceSospesi.data);
      this.dataSourceSospesi.filteredData.map(data => {
        data.checked = e.checked;
      });
      if (e.checked) {
        this.idDettPropSospesiChecked = this.elencoProgettiSospesi.map(n => n.idPreviewDettPropCer);
      } else {
        this.idDettPropSospesiChecked = new Array<number>();
      }
    }
  }

  selectCheckbox(idPreviewDettPropCer: number, type: String, e: any) {
    if (type === 'A') {
      if (e.checked) {
        this.idDettPropAmmessiChecked.push(idPreviewDettPropCer);
      } else {
        this.idDettPropAmmessiChecked = this.idDettPropAmmessiChecked.filter(id => id !== idPreviewDettPropCer);
      }
    } else if (type == 'S') {
      if (e.checked) {
        this.idDettPropSospesiChecked.push(idPreviewDettPropCer);
      } else {
        this.idDettPropSospesiChecked = this.idDettPropSospesiChecked.filter(id => id !== idPreviewDettPropCer);
      }
    }
  }

  sospendi() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedOperazione = false;
    this.subscribers.sospendi = this.certificazioneService.sospendiProgettiProposta(new AmmettiESospendiProgettiRequest(this.idDettPropAmmessiChecked)).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.idDettPropAmmessiChecked = new Array<number>();
          this.ricerca(data.msg);
        } else {
          this.showMessageError(data.msg);
        }
      }
      this.loadedOperazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nel sospendere i progetti.");
      this.loadedOperazione = true;
    });
  }

  riammetti() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedOperazione = false;
    this.subscribers.riammetti = this.certificazioneService.ammettiProgettiProposta(new AmmettiESospendiProgettiRequest(this.idDettPropSospesiChecked)).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.idDettPropSospesiChecked = new Array<number>();
          this.ricerca(data.msg);
        } else {
          this.showMessageError(data.msg);
        }
      }
      this.loadedOperazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nel riammettere i progetti.");
      this.loadedOperazione = true;
    });
  }

  creaProposta() {
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '30%',
      data: {
        message: "Confermi la richiesta di elaborazione?"
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === "SI") {
        this.creaPropostaConfirmed();
      }
    });
  }

  creaPropostaConfirmed() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedOperazione = false;
    let proposta = new PropostaCertificazioneDTO();
    proposta.idPropostaCertificaz = this.idProposta;
    proposta.isBozza = this.isBozza;
    proposta.idLineaDiIntervento = this.lineaDiIntervento.idLineaDiIntervento;
    this.subscribers.crea = this.certificazioneService.accodaPropostaCertificazione(new AccodaPropostaRequest(proposta)).subscribe(data1 => {
      if (data1) {
        if (data1.esito) {
          this.subscribers.lineeIntervento = this.certificazioneService.getLineeDiInterventoDisponibili().subscribe(data2 => {
            if (data2) {
              let lineeInterventoDisp: Array<LineaInterventoDTO> = data2;
              this.certificazioneService.lanciaCreazioneProposta(new LanciaPropostaRequest(proposta, lineeInterventoDisp.map(l => new CodiceDescrizione(l.idLineaDiIntervento.toString(), l.descLinea))));
              this.showMessageSuccess("La richiesta di elaborazione è stata salvata.");
              this.loadedOperazione = true;
            }
          }, err2 => {
            this.handleExceptionService.handleNotBlockingError(err2);
            this.loadedOperazione = true;
          });
        } else {
          this.showMessageError(data1.msg);
          this.loadedOperazione = true;
        }
      }
    }, err1 => {
      this.handleExceptionService.handleNotBlockingError(err1);
      this.showMessageError("Errore nella creazione della proposta.");
      this.loadedOperazione = true;
    });
  }

  goToCreaAnteprima() {
    this.router.navigateByUrl("/drawer/" + Constants.ID_OPERAZIONE_CERTIFICAZIONE_CREA_PROPOSTA + "/creaAnteprimaProposta");
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedBandiLinea || !this.loadedBeneficiari || !this.loadedProgetti || !this.loadedCerca || !this.loadedOperazione) {
      return true;
    }
    return false;
  }

  scaricaExcel(idProposta: number, elencoProgettiSospesi: Array<PropostaProgettoDTO>) {
    this.excelService.generaExcelPropostaProgetto(idProposta, elencoProgettiSospesi);
  }
}

export class ElencoPropostaProgettiDatasource extends MatTableDataSource<PropostaProgettoDTO> {

  _orderData(data: PropostaProgettoDTO[]): PropostaProgettoDTO[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "codiceprogetto";
    }
    let direction = this.sort.direction || "asc";
    let sortedData = null;

    // viene riordinato in ordine ascendente
    switch (this.sort.active) {
      case "codiceprogetto":
        sortedData = data.sort((propA, propB) => {
          return propA.codiceProgetto.localeCompare(propB.codiceProgetto);
        });
        break;
      case "beneficiario":
        sortedData = data.sort((propA, propB) => {
          return propA.denominazioneBeneficiario.localeCompare(propB.denominazioneBeneficiario);
        });
        break;
      case "importocert":
        sortedData = data.sort((propA, propB) => {
          return propA.importoCertificazioneNetto - propB.importoCertificazioneNetto;
        });
        break;
      case "pagamenti":
        sortedData = data.sort((propA, propB) => {
          return propA.importoPagamenti - propB.importoPagamenti;
        });
        break;
      case "importorev":
        sortedData = data.sort((propA, propB) => {
          return propA.importoRevoche - propB.importoRevoche;
        });
        break;
      case "lineaintervento":
        sortedData = data.sort((propA, propB) => {
          return propA.descLinea.localeCompare(propB.descLinea);
        });
        break;
      default:
        sortedData = data.sort((propA, propB) => {
          return propA.codiceProgetto.localeCompare(propB.codiceProgetto);
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: PropostaProgettoDTO[]) {
    super(data);
  }
}
