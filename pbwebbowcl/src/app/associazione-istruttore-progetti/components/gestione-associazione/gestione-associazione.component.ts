/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { BandoLineaAssociatiAIstruttoreVO } from '../../commons/dto/bando-linea-associati-istruttore-dto';
import { BandoLineaAssociatiAIstruttoreShowVO } from '../../commons/dto/bando-linea-associati-istruttore-show-dto';
import { ProgettoDTO } from '../../commons/dto/progetto-dto';
import { ProgettoShowDTO } from '../../commons/dto/progetto-show-dto';
import { AssociazioneIstruttoreProgettiService } from '../../services/associazione-istruttore-progetti.service';
import { Constants } from 'src/app/core/commons/util/constants';

@Component({
  selector: 'app-gestione-associazione',
  templateUrl: './gestione-associazione.component.html',
  styleUrls: ['./gestione-associazione.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneAssociazioneComponent implements OnInit {

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private associazioneIstruttoreProgettiService: AssociazioneIstruttoreProgettiService,
    private handleExceptionService: HandleExceptionService
  ) {
    this.dataSource = new MatTableDataSource();
    this.dataSourceBandi = new MatTableDataSource();
  }

  params: URLSearchParams;
  idOperazione: number;
  user: UserInfoSec;
  cognomeIstruttore: string;
  nomeIstruttore: string;
  codiceFiscale: string;
  tipoAnagrafica: string;
  idSoggettoIstruttore: string;
  showImpegniButtons: boolean = false;
  showBandiButtons: boolean = false;

  LS_ANAGRAFICA_ITRUTTORE_SELECTED_KEY = 'ricercaIstruttore_anagraficaIstruttoreSelected'
  anagraficaIstruttoreSelected: string;

  isIstruttoreAffidamenti: boolean;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedGestisciAssociazioni: boolean;
  loadedBandolineaAssociati: boolean;
  loadedDisProgetti: boolean;
  loadedDettaglioIstruttori: boolean;
  loadedEliminaAssociazioneIstruttoreBandolinea: boolean;

  // tabelle
  displayedColumns: string[] = ['codiceProgetto', 'bando', 'beneficiario', 'numIstrAssoc', 'azioni'];
  dataSource: MatTableDataSource<ProgettoShowDTO>;

  // tabelle
  displayedColumnsBandi: string[] = ['nomeBando', 'numIstrAssoc', 'azioni'];
  dataSourceBandi: MatTableDataSource<BandoLineaAssociatiAIstruttoreShowVO>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatPaginator, { static: true }) paginatorBandi: MatPaginator;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSourceBandi.paginator = this.paginatorBandi;
  }

  ngOnInit(): void {
    this.params = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;

    this.idSoggettoIstruttore = this.params.get('idSoggettoIstruttore');
    this.cognomeIstruttore = this.params.get('cognome');
    this.nomeIstruttore = this.params.get('nome');
    this.codiceFiscale = this.params.get('codFisc');
    this.tipoAnagrafica = this.params.get('tpAnagr');

    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;

        this.getTipoIstruttoreFromLocalStorage();

        this.loadedGestisciAssociazioni = true;
        this.subscribers.gestisciAssociazioni = this.associazioneIstruttoreProgettiService.gestisciAssociazioni(this.user, this.idSoggettoIstruttore, this.isIstruttoreAffidamenti).subscribe(data2 => {
          var comodo = new Array<ProgettoShowDTO>();
          data2?.forEach(element => {
            comodo.push(new ProgettoShowDTO(element.idProgetto, element.idSoggettoBeneficiario, element.idBando, element.numeroIstruttoriAssociati, element.titoloBando, element.codiceVisualizzato, element.beneficiario, element.istruttoriSempliciAssociati, element.progrSoggettoProgetto, element.cup, false));
          });
          this.dataSource = new MatTableDataSource(comodo);
          this.dataSource.paginator = this.paginator;
          this.loadedGestisciAssociazioni = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero dei progetti associati.");
          this.loadedGestisciAssociazioni = false;
        });

        this.loadedBandolineaAssociati = true;
        this.subscribers.bandolineaAssociati = this.associazioneIstruttoreProgettiService.bandolineaAssociati(this.user, this.idSoggettoIstruttore, this.isIstruttoreAffidamenti).subscribe(data2 => {
          var comodo = new Array<BandoLineaAssociatiAIstruttoreShowVO>();
          var com = new Array<BandoLineaAssociatiAIstruttoreVO>();
          data2?.forEach(element => {
            comodo.push(new BandoLineaAssociatiAIstruttoreShowVO(element.nomeBandolinea, element.progBandoLina, element.numIstruttoriAssociati, element.nomneIstruttore, element.cognomeIstruttore, false, com));
          });
          this.dataSourceBandi = new MatTableDataSource(comodo);
          this.dataSourceBandi.paginator = this.paginatorBandi;
          this.loadedBandolineaAssociati = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero dei bandi associati.");
          this.loadedBandolineaAssociati = false;
        });
      }
    });
  }

  tornaAllaRicercaIstruttori() {
    this.router.navigateByUrl("drawer/" + this.idOperazione + "/associazioniIstruttoreProgetti");
  }

  eliminaProgAssoc(row: ProgettoDTO) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDisProgetti = true;
    this.subscribers.disProgetti = this.associazioneIstruttoreProgettiService.disProgetti(this.user, this.user.idSoggetto.toString(), this.user.codiceRuolo, row.progrSoggettoProgetto.toString()).subscribe(data2 => {
      this.loadedDisProgetti = false;
      this.showMessageSuccess("Eliminazione effettuata con successo.");
      this.loadedGestisciAssociazioni = true;
      this.associazioneIstruttoreProgettiService.gestisciAssociazioni(this.user, this.idSoggettoIstruttore, this.isIstruttoreAffidamenti).subscribe(data2 => {
        var comodo = new Array<ProgettoShowDTO>();
        data2.forEach(element => {
          comodo.push(new ProgettoShowDTO(element.idProgetto, element.idSoggettoBeneficiario, element.idBando, element.numeroIstruttoriAssociati, element.titoloBando, element.codiceVisualizzato, element.beneficiario, element.istruttoriSempliciAssociati, element.progrSoggettoProgetto, element.cup, false));
        });
        this.dataSource = new MatTableDataSource(comodo);
        this.dataSource.paginator = this.paginator;
        this.loadedGestisciAssociazioni = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero dei progetti associati.");
        this.loadedGestisciAssociazioni = false;
      });
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di disassociazione progetti.");
      this.loadedDisProgetti = false;
    });
  }


  getTipoIstruttoreFromLocalStorage() {

    this.anagraficaIstruttoreSelected = localStorage.getItem(this.LS_ANAGRAFICA_ITRUTTORE_SELECTED_KEY);
    this.isIstruttoreAffidamenti = this.anagraficaIstruttoreSelected === Constants.CODICE_RUOLO_ISTR_AFFIDAMENTI;

  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    var element = document.getElementById('scrollId')
    element.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  associaNuovoProg() {
    this.router.navigateByUrl(`drawer/` + this.idOperazione + `/associaNuovoProgetto?idSoggettoIstruttore=` + this.params.get('idSoggettoIstruttore') + `&cognome=` + this.params.get('cognome') + `&nome=` + this.params.get('nome') + `&codFisc=` + this.params.get('codFisc') + `&idBando=` + this.params.get('idBando'));
  }

  associaNuovoBando() {
    this.router.navigateByUrl(`drawer/` + this.idOperazione + `/associaNuovoBando?idSoggettoIstruttore=` + this.params.get('idSoggettoIstruttore') + `&cognome=` + this.params.get('cognome') + `&nome=` + this.params.get('nome') + `&codFisc=` + this.params.get('codFisc') + `&idBando=` + this.params.get('idBando'));
  }

  apriChiudiNumIstrBandoLinea(row: BandoLineaAssociatiAIstruttoreShowVO) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.dataSourceBandi.filteredData.forEach(element => {
      if (row.progBandoLina == element.progBandoLina) {
        if (!(row.numIstruttoriAssociati == 0)) {
          if (!row.showIstrAssoc) {
            this.loadedDettaglioIstruttori = true;
            this.subscribers.dettaglioIstruttori = this.associazioneIstruttoreProgettiService.dettaglioIstruttori(this.user, this.params.get('idSoggettoIstruttore'), row.progBandoLina.toString()).subscribe(data2 => {
              element.istrAssociati = data2;
              this.loadedDettaglioIstruttori = false;
            }, err => {
              this.handleExceptionService.handleNotBlockingError(err);
              this.showMessageError("Errore in fase di recupero del dettaglio degli istruttori.");
              this.loadedDettaglioIstruttori = false;
            });
          }
          element.showIstrAssoc = !element.showIstrAssoc;
        }
      }
    });
  }

  eliminaBandiAssoc(row: BandoLineaAssociatiAIstruttoreVO) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaAssociazioneIstruttoreBandolinea = true;
    this.subscribers.eliminaAssociazioneIstruttoreBandolinea = this.associazioneIstruttoreProgettiService.eliminaAssociazioneIstruttoreBandolinea(this.user, this.idSoggettoIstruttore, row.progBandoLina.toString()).subscribe(data2 => {
      this.showMessageSuccess("Eliminazione effettuata con successo.");
      this.loadedEliminaAssociazioneIstruttoreBandolinea = false;
      this.loadedBandolineaAssociati = true;
      this.subscribers.bandolineaAssociati = this.associazioneIstruttoreProgettiService.bandolineaAssociati(this.user, this.idSoggettoIstruttore, this.isIstruttoreAffidamenti).subscribe(data2 => {
        var comodo = new Array<BandoLineaAssociatiAIstruttoreShowVO>();
        var com = new Array<BandoLineaAssociatiAIstruttoreVO>();
        data2?.forEach(element => {
          comodo.push(new BandoLineaAssociatiAIstruttoreShowVO(element.nomeBandolinea, element.progBandoLina, element.numIstruttoriAssociati, element.nomneIstruttore, element.cognomeIstruttore, false, com));
        });
        this.dataSourceBandi = new MatTableDataSource(comodo);
        this.dataSourceBandi.paginator = this.paginatorBandi;
        this.loadedBandolineaAssociati = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero dei bandi associati.");
        this.loadedBandolineaAssociati = false;
      });
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di disassociazione bandi linea.");
      this.loadedEliminaAssociazioneIstruttoreBandolinea = false;
    });
  }

  isLoading() {
    if (this.loadedGestisciAssociazioni || this.loadedBandolineaAssociati || this.loadedDisProgetti || this.loadedDettaglioIstruttori || this.loadedEliminaAssociazioneIstruttoreBandolinea) {
      return true;
    }
    return false;
  }
}
