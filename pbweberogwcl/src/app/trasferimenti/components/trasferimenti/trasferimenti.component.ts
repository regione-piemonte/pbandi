/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { Constants } from 'src/app/core/commons/util/constants';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from "src/app/core/commons/dto/user-info";
import { BeneficiarioSec } from 'src/app/core/commons/dto/beneficiario';
import { TrasferimentiService } from "../../services/trasferimenti.service";
import { SoggettoTrasferimento } from "../../commons/dto/soggetto-trasferimento";
import { CausaleTrasferimento } from "../../commons/dto/causale-trasferimento";
import { TrasferimentiItem } from "../../commons/dto/trasferimenti-item";
import { FiltroTrasferimento } from "../../commons/dto/filtro-trasferimento";
import { FiltroTrasferimentoRequest } from "../../commons/requests/filtro-trasferimento-request";
import { NgForm } from "@angular/forms";
import { Observable as __Observable } from 'rxjs';
import { Trasferimenti2Service } from '../../services/trasferimenti2.service';
import { PbandiDLineaDiInterventoVO } from '../../commons/dto/pbandi-d-linea-di-intervento-vo';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
  selector: 'app-trasferimenti',
  templateUrl: './trasferimenti.component.html',
  styleUrls: ['./trasferimenti.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class TrasferimentiComponent implements OnInit {
  test: boolean = false;
  enableSelectBeneficiario: boolean = true; // precedence in concurrency with enableBasicSelectBeneficiario
  enableBasicSelectBeneficiario: boolean = false;
  user: UserInfoSec;

  //LOADED
  loadedBeneficiari: boolean;     //FORM
  loadedCausali: boolean;         //FORM
  loadedRicerca: boolean = true;
  loadedNormative: boolean;
  loadedElimina: boolean = true;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  //FORM
  @ViewChild("ricercaForm", { static: true }) ricercaForm: NgForm;
  criteriRicercaOpened: boolean = true;
  //beneficiariCD: Array<CodiceDescrizione>;  // select-beneficiario (CodiceDescrizione)
  //beneficiarioCD: CodiceDescrizione;  // select-beneficiario (CodiceDescrizione)
  beneficiariS: Array<BeneficiarioSec>;  // select-beneficiario
  beneficiarioS: BeneficiarioSec; // select-beneficiario
  beneficiari: Array<SoggettoTrasferimento>;
  causali: Array<CausaleTrasferimento>;
  beneficiarioSelected: SoggettoTrasferimento;
  causaleSelected: CausaleTrasferimento;
  normative: Array<PbandiDLineaDiInterventoVO>;
  normativaSelected: PbandiDLineaDiInterventoVO;
  //dataDal: FormControl = new FormControl();
  //dataAl: FormControl = new FormControl();
  dataDal: Date;  // .setHours(0, 0, 0, 0);
  dataAl: Date;
  codiceTrasferimento: string;
  tipo: string = '0';

  //TABLE
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  dataRequest: FiltroTrasferimento;
  dataResponse: Array<TrasferimentiItem>;

  //TABLE TRASFERIMENTI
  displayedColumnsTrasferimenti: string[] = ['dtTrasferimento', 'descCausaleTrasferimento', 'denominazioneBeneficiario', 'importoTrasferimentoFormatted', 'codiceTrasferimento', 'descPubblicoPrivato', 'descLinea', 'azioni'];
  displayedHeadersTrasferimenti: string[] = ['Data', 'Causale', 'Denominazione Beneficiario', 'Importo', 'Codice', 'Pubblico/Privato', 'Normativa', ''];
  displayedFootersTrasferimenti: string[] = [];
  displayedColumnsCustomTrasferimenti: string[] = ['', '', '', '', '', '', '', 'azioni'];
  displayedHeadersCustomTrasferimenti: string[] = this.displayedColumnsCustomTrasferimenti;
  displayedFootersCustomTrasferimenti: string[] = [];
  displayedColumnsCustomTrasferimentiAzioni: any = { visualizza: true, modifica: true, elimina: true };
  dataSourceTrasferimenti: MatTableDataSource<TrasferimentiItem>;

  constructor(
    private router: Router,
    private trasferimentiService: TrasferimentiService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog,
    private trasferimenti2Service: Trasferimenti2Service,
    private sharedService: SharedService,
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        console.log('user', data.ruolo);  // getInfoUtente
        console.log('user', data.ruoli.find(d => d.descrizione == data.ruolo).id);
        this.user = data;
        if (this.user.idIride && (this.user.codiceRuolo == 'ADG-IST-MASTER' || this.user.codiceRuolo == 'ADG-ISTRUTTORE' || this.user.codiceRuolo == 'ADG-CERT')) {
          this.loadData();
        }
      }
    });
  }

  loadData() {
    //LOAD SOGGETTI TRASFERIMENTO
    this.loadedBeneficiari = false;
    this.subscribers.soggettiTrasferimento = this.trasferimentiService.getSoggettiTrasferimento().subscribe((res: SoggettoTrasferimento[]) => {
      if (res) {
        if (this.enableBasicSelectBeneficiario) {
          this.beneficiari = res;
          //this.beneficiarioSelected = this.beneficiari[0];
        }
        if (this.enableSelectBeneficiario) {
          this.beneficiariS = res.map((res) => {  // select-beneficiario
            return {
              denominazione: res.denominazioneBeneficiario,
              codiceFiscale: res.cfBeneficiario,
              idBeneficiario: res.idSoggettoBeneficiario
            };
          });
        }
      }
      this.loadedBeneficiari = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedBeneficiari = true;
    });
    //LOAD CAUSALI TRASFERIMENTO
    this.loadedCausali = false;
    this.subscribers.causaliTrasferimento = this.trasferimentiService.getCausaliTrasferimento().subscribe((res: CausaleTrasferimento[]) => {
      if (res) {
        this.causali = res;
        //this.causaleSelected = this.causali[0];
      }
      this.loadedCausali = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCausali = true;
    });
    this.loadedNormative = false;
    this.subscribers.normative = this.trasferimenti2Service.getNormativa().subscribe(data => {
      if (data) {
        this.normative = data;
      }
      this.loadedNormative = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero delle normative.");
      this.loadedNormative = true;
    });
  }

  isLoading() {
    if (!this.loadedBeneficiari || !this.loadedCausali || !this.loadedNormative || !this.loadedRicerca) {
      return true;
    }
    return false;
  }

  onSelectBeneficiario() {

  }

  ricerca(filtro: FiltroTrasferimento, isMessageSuccess?: boolean) {
    if (!isMessageSuccess) {
      this.resetMessageSuccess();
    }
    this.resetMessageError();

    if (this.dataDal > this.dataAl) {
      this.showMessageError("Il campo 'Data dal' deve essere precedente al campo 'Data al'.");
      return;
    }
    this.loadedRicerca = false;
    this.dataRequest = {
      aDataTrasferimento: ((this.dataAl) ? this.dataAl.toLocaleDateString() : undefined),
      codiceTrasferimento: this.codiceTrasferimento,
      daDataTrasferimento: ((this.dataDal) ? this.dataDal.toLocaleDateString() : undefined),
      flagPubblicoPrivato: this.tipo,
      idCausaleTrasferimento: ((this.causaleSelected) ? this.causaleSelected.idCausaleTrasferimento : undefined),
      idLineaDiIntervento: this.normativaSelected ? this.normativaSelected.idLineaDiIntervento : null,
      idSoggettoBeneficiario: ((this.enableSelectBeneficiario) ?
        ((this.beneficiarioS) ? this.beneficiarioS.idBeneficiario : undefined)
        :
        ((this.enableBasicSelectBeneficiario) ?
          ((this.beneficiarioSelected) ? this.beneficiarioSelected.idSoggettoBeneficiario : undefined)
          :
          undefined
        )
      ),
      idTrasferimento: undefined
    };
    this.subscribers.ricercaTrasferimenti = this.trasferimentiService.ricercaTrasferimenti(this.dataRequest).subscribe(data => {
      this.setupTrasferimentiItemDatasource(data);
      this.loadedRicerca = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
      //this.resetTrasferimentiItemDatasource();
      ////this.setupTrasferimentiItemDatasourceMock();  // Todo: remove mock
      //this.loadedRicerca = true;
    });
  }

  setupTrasferimentiItemDatasourceMock(datas?: any) {
    let data: Array<TrasferimentiItem> = [
      {
        cfBeneficiario: '2225434',
        codiceTrasferimento: 'TR01',
        denominazioneBeneficiario: 'San Benedetto',
        descCausaleTrasferimento: 'Donazione 03',
        descPubblicoPrivato: 'Pubblico',
        dtTrasferimento: '2021-06-18 01:02:03',
        flagPubblicoPrivato: 'P',
        idCausaleTrasferimento: 1,
        idSoggettoBeneficiario: 2,
        idTrasferimento: 3,
        importoTrasferimento: 100,
        isEliminabile: true,
        isModificabile: true,
        lnkDettaglio: 'http://www.domain.tdl./dettaglio/1',
        lnkElimina: 'http://www.domain.tdl./elimina/1',
        lnkModifica: 'http://www.domain.tdl./modifica/1',
      },
      {
        cfBeneficiario: '123654',
        codiceTrasferimento: 'TR02',
        denominazioneBeneficiario: 'Del Tronto',
        descCausaleTrasferimento: 'Donazione 02',
        descPubblicoPrivato: 'Privato',
        dtTrasferimento: '2021-05-25 04:05:06',
        flagPubblicoPrivato: 'Pr',
        idCausaleTrasferimento: 4,
        idSoggettoBeneficiario: 5,
        idTrasferimento: 6,
        importoTrasferimento: 200,
        isEliminabile: false,
        isModificabile: true,
        lnkDettaglio: 'http://www.domain.tdl./dettaglio/2',
        lnkElimina: 'http://www.domain.tdl./elimina/2',
        lnkModifica: 'http://www.domain.tdl./modifica/2',
      },
      {
        cfBeneficiario: '8556343',
        codiceTrasferimento: 'TR03',
        denominazioneBeneficiario: 'Palmisano',
        descCausaleTrasferimento: 'Donazione 01',
        descPubblicoPrivato: 'Pubblico',
        dtTrasferimento: '2021-07-03 07:08:09',
        flagPubblicoPrivato: 'P',
        idCausaleTrasferimento: 7,
        idSoggettoBeneficiario: 8,
        idTrasferimento: 9,
        importoTrasferimento: 300,
        isEliminabile: true,
        isModificabile: false,
        lnkDettaglio: 'http://www.domain.tdl./dettaglio/3',
        lnkElimina: 'http://www.domain.tdl./elimina/3',
        lnkModifica: 'http://www.domain.tdl./modifica/3',
      }
    ];
    this.setupTrasferimentiItemDatasource(data);
  }

  resetTrasferimentiItemDatasource() {
    this.dataResponse = undefined;
    this.paginator.length = 0;
    this.paginator.pageIndex = 0;
  }

  setupTrasferimentiItemDatasource(data: Array<TrasferimentiItem>) {
    if (data) {
      this.dataResponse = data;
      console.log('test dr 1', this.dataResponse);
      console.log('test dr 2', this.dataResponse);
      this.dataResponse.forEach(t => {
        if (t.importoTrasferimento !== null && t.importoTrasferimento !== undefined) {
          t.importoTrasferimentoFormatted = this.sharedService.formatValue(t.importoTrasferimento.toString());
        }
        let linea = this.normative.find(n => n.idLineaDiIntervento === t.idLineaDiIntervento);
        t.descLinea = linea ? linea.descLinea : null;
      })
      this.dataSourceTrasferimenti = new TrasferimentiDatasource(this.dataResponse);
      //this.dataSourceTrasferimenti._updateChangeSubscription();

      console.log('test D', this.dataSourceTrasferimenti);
      console.log('test k', this.dataSourceTrasferimenti.data.keys());
      console.log('test k', this.dataSourceTrasferimenti.filteredData.keys());
    }
  }

  onSelectCausale() {

  }

  goToOperazioni() {

  }

  saveDataForNavigation() {
    //this.navigationService.filtroRicercaDocumentiSpesaSelezionato = this.setFiltro();
    //this.navigationService.lastElencoDocSpesaVisible = this.isDocSpesaVisible;
    this.salvaSortPaginator();
  }

  salvaSortPaginator() {
    //if (this.dataSource) {
    //  this.navigationService.sortDirectionTable = this.dataSource.sort.direction;
    //  this.navigationService.sortActiveTable = this.dataSource.sort.active;
    //  this.navigationService.paginatorPageIndexTable = this.dataSource.paginator.pageIndex;
    //  this.navigationService.paginatorPageSizeTable = this.dataSource.paginator.pageSize;
    //}
  }

  goToVisualizzaTrasferimento(idTrasferimento: number) {
    this.saveDataForNavigation();
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_TRASFERIMENTI + "/dettaglio-trasferimento", idTrasferimento]);
    //, this.idProgetto, this.idBandoLinea, idDocumento, { current: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO }
  }

  goToNuovoTrasferimento() {
    this.saveDataForNavigation();
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_TRASFERIMENTI + "/nuovo-trasferimento"]);
  }

  goToModificaTrasferimento(idTrasferimento: number) {
    this.saveDataForNavigation();
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_TRASFERIMENTI + "/modifica-trasferimento", idTrasferimento]);
    //  //, this.idProgetto, this.idBandoLinea, idDocumento, { current: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA }
  }

  onVisualizzaTrasferimento(row: TrasferimentiItem) {
    this.goToVisualizzaTrasferimento(row.idTrasferimento);
  }

  onModificaTrasferimento(row: TrasferimentiItem) {
    this.goToModificaTrasferimento(row.idTrasferimento);
  }

  onEliminaTrasferimento(row: TrasferimentiItem) {
    this.cancellaTrasferimento(row.idTrasferimento);
  }

  cancellaTrasferimento(idTrasferimento: number) {  // Todo: convert to DTO
    let doc = this.dataSourceTrasferimenti.data.find(d => d.idTrasferimento === idTrasferimento);
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi l'eliminazione della tipologia " + doc.descCausaleTrasferimento + " con codice " + doc.codiceTrasferimento + " effettuata in data " + doc.dtTrasferimento + "?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadedElimina = false;
        this.subscribers.elimina = this.trasferimentiService.cancellaTrasferimento(idTrasferimento).subscribe(data => {
          if (data) {
            if (data.esito && data.esito) {
              this.showMessageSuccess(data.msgs.map(o => o.msgKey).join("<br>"));
              this.ricerca(null, true);
            } else {
              this.showMessageError(data.msgs.map(o => o.msgKey).join("<br>"));
            }
          }
          this.loadedElimina = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedElimina = true;
          this.showMessageError("Errore nell'eliminazione del documento.");
        });
      }
    });
  }

  //MESSAGGI SUCCESS E ERROR - start
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
  //MESSAGGI SUCCESS E ERROR - end
}

export class TrasferimentiDatasource extends MatTableDataSource<TrasferimentiItem> {

  private filterByLineaInterventoEnable: boolean = true;
  private idLineaIntervento: number;
  /** /
    filterByLineaIntervento(idLineaIntervento: number) {
      this.idLineaIntervento = idLineaIntervento;
      this.filteredData = this._filterData(this.data);
      this._updateChangeSubscription();
    }
  
    _filterData(data: TrasferimentiItem[]): TrasferimentiItem[] {
      return data.filter((item: TrasferimentiItem) => {
  
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
  /**/
  _orderData(data: TrasferimentiItem[]): TrasferimentiItem[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "numproposta";
    }
    let direction = this.sort.direction || "asc";
    let sortedData = null;

    // viene riordinato in ordine ascendente
    switch (this.sort.active) {
      case "cfbeneficiario":
        sortedData = data.sort((propA, propB) => {
          return propA.codiceTrasferimento.localeCompare(propB.codiceTrasferimento);
        });
        break;
      case "codicetrasferimento":
        sortedData = data.sort((propA, propB) => {
          return propA.codiceTrasferimento.localeCompare(propB.codiceTrasferimento);
        });
        break;
      case "denominazionebeneficiario":
        sortedData = data.sort((propA, propB) => {
          return propA.denominazioneBeneficiario.localeCompare(propB.denominazioneBeneficiario);
        });
        break;
      case "descCausaletrasferimento":
        sortedData = data.sort((propA, propB) => {
          return propA.descCausaleTrasferimento.localeCompare(propB.descCausaleTrasferimento);
        });
        break;
      case "descpubblicoprivato":
        sortedData = data.sort((propA, propB) => {
          return propA.descPubblicoPrivato.localeCompare(propB.descPubblicoPrivato);
        });
        break;
      case "dttrasferimento":
        sortedData = data.sort((propA, propB) => {
          let datanumA = new Date(propA.dtTrasferimento).toLocaleDateString();
          let datanumB = new Date(propB.dtTrasferimento).toLocaleDateString();
          return datanumA.localeCompare(datanumB);
        });
        break;
      case "flagpubblicoprivato":
        sortedData = data.sort((propA, propB) => {
          return propA.flagPubblicoPrivato.localeCompare(propB.flagPubblicoPrivato);
        });
        break;
      case "idcausaletrasferimento":
        sortedData = data.sort((propA, propB) => {
          return propA.idCausaleTrasferimento - propB.idCausaleTrasferimento;
        });
        break;
      case "idsoggettobeneficiario":
        sortedData = data.sort((propA, propB) => {
          return propA.idSoggettoBeneficiario - propB.idSoggettoBeneficiario;
        });
        break;
      case "idTrasferimento":
        sortedData = data.sort((propA, propB) => {
          return propA.idTrasferimento - propB.idTrasferimento;
        });
        break;
      case "importotrasferimento":
        sortedData = data.sort((propA, propB) => {
          return propA.importoTrasferimento - propB.importoTrasferimento;
        });
        break;
      case "iseliminabile":
        sortedData = data.sort((propA, propB) => {
          return (+propA.isEliminabile) - (+propB.isEliminabile);
        });
        break;
      case "ismodificabile":
        sortedData = data.sort((propA, propB) => {
          return (+propA.isModificabile) - (+propB.isModificabile);
        });
        break;
      case "lnkdettaglio":
        sortedData = data.sort((propA, propB) => {
          return propA.lnkDettaglio.localeCompare(propB.lnkDettaglio);
        });
        break;
      case "lnkelimina":
        sortedData = data.sort((propA, propB) => {
          return propA.lnkElimina.localeCompare(propB.lnkElimina);
        });
        break;
      case "lnkmodifica":
        sortedData = data.sort((propA, propB) => {
          return propA.lnkModifica.localeCompare(propB.lnkModifica);
        });
        break;
      default:
        sortedData = data.sort((propA, propB) => {
          return propA.idTrasferimento - propB.idTrasferimento;
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: TrasferimentiItem[]) {
    super(data);
  }
}
