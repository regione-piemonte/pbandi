import { Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { ActivatedRoute } from '@angular/router';
import { CronoprogrammaFasiComponent } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { CustomDateAdapter, MY_FORMATS } from 'src/app/custom-date-adapter';
import { CodiceDescrizioneDTO } from 'src/app/gestione-spesa-validata/commons/dto/codice-descrizione-dto';
import { SalCorrenteDTO } from 'src/app/rendicontazione/commons/dto/sal-corrente-dto';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';

@Component({
  selector: 'app-cronoprogramma-fasi-container',
  templateUrl: './cronoprogramma-fasi-container.component.html',
  styleUrls: ['./cronoprogramma-fasi-container.component.scss'],
  providers:[
    { provide: DateAdapter, useClass: CustomDateAdapter },
    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS }
  ]
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class CronoprogrammaFasiContainerComponent implements OnInit, OnChanges {

  @Input("salCorrente") salCorrente: SalCorrenteDTO;

  salCorrenteCodDesc: CodiceDescrizioneDTO;
  idProgetto: number;
  idBandoLinea: number;
  user: UserInfoSec;
  apiURL: string;

  isBR58a: boolean;
  isBR58b: boolean;

  isConfermatoSuccess: boolean;

  @ViewChild(CronoprogrammaFasiComponent) cronoprogrammaFasiComponent: CronoprogrammaFasiComponent;

  messageError: Array<string> = new Array<string>();
  isMessageErrorVisible: boolean;

  //LOADED
  loadedBR58a: boolean;
  loadedBR58b: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private configService: ConfigService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.apiURL = this.configService.getApiURL();
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];

      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          this.loadData();
        }
      });
    });
    this.loadData();
  }


  loadData() {
    this.loadedBR58a = false;
    this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, "BR58a").subscribe(res => {
      this.isBR58a = res;
      this.loadedBR58a = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedBR58a = true;
    });
    this.loadedBR58b = false;
    this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, "BR58b").subscribe(res => {
      this.isBR58b = res;
      this.loadedBR58b = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedBR58b = true;
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (this.salCorrente?.idIter && changes.salCorrente) {
      this.salCorrenteCodDesc = new CodiceDescrizioneDTO(this.salCorrente.idIter.toString(), this.salCorrente.descIter);
    }
  }

  salva() { //chiamato solo dal crea dichiarazione di spesa
    this.cronoprogrammaFasiComponent.salva(true);
  }

  saveSuccess(result: boolean) {
    if (result) {
      this.isConfermatoSuccess = true;
    }
  }


  resetMessage() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  // resetta gli errori e motra il messaggio di errore 
  showMessageError(msg: Array<string>) {
    this.resetMessage()
    if (msg != undefined && msg != null) {
      this.messageError = msg;
    }
    else {
      this.messageError = ["Error"];
    }
    this.isMessageErrorVisible = true;

    //this.vps.scrollToAnchor("sclrollId"); // non funziona

    //const element = document.querySelector('#scrollId'); // non funziona
    var element = document.getElementById('scrollIdCrono')
    element.scrollIntoView();
  }

  isLoading() {
    if (!this.loadedBR58a || !this.loadedBR58b) {
      return true;
    }
    return false;
  }
}
