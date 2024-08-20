/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewInit, Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { UserService } from 'src/app/core/services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AffidamentiService } from '../../services/affidamenti.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { ViewEncapsulation } from '@angular/core';
import { Constants } from 'src/app/core/commons/util/constants';
import { ScrollingVisibility } from '@angular/cdk/overlay';
import { SalvaCheckListAffidamentoDocumentaleHtmlRequest } from '../../commons/requests/salva-checkList-affidamentoDocumentaleHtml-request';
import { ChecklistAffidamentoHtmlDTO } from '../../commons/dto/checklist-affidamentohtml-dto';
import { ChecklistHtmlDTO } from '../../commons/dto/checklisthtml-dto';
import { RichiestaIntegrazioneDialogComponent } from '../richiesta-integrazione-dialog/richiesta-integrazione-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { FileDTO } from "../../commons/dto/file-dto";
import { Observable, ReplaySubject } from 'rxjs';
import { ChecklistAffidamentoHtmlBozzaDTO } from '../../commons/dto/checklist-affidamentohtml-bozza-dto';
import { SalvaCheckListAffidamentoDocumentaleBozzaHtmlRequest } from '../../commons/requests/salva-checkList-affidamentoDocumentaleHtml-bozza-request';


declare const inizializzaCheckList: any; //funzione definita in assets/js/checklistAffidamenti2.js
//declare const updateValues: any;
declare const getChecklistHtmlBody: any; //funzione definita in assets/js/checklistAffidamenti2.js
//declare const visualizzaErrorMsg: any; //funzione definita in assets/js/checklistAffidamenti2.js
declare const visualizzaErrorMsg2: any; //funzione definita in assets/js/checklistAffidamenti2.js
@Component({
  selector: 'app-nuova-checklist-documentale',
  templateUrl: './nuova-checklist-documentale.component.html',
  styleUrls: ['./nuova-checklist-documentale.component.scss'],
  encapsulation: ViewEncapsulation.None   //PK : da mettere affinche vengano applicate le classi degi stili al parametro "html" 
})

@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovaChecklistDocumentaleComponent implements OnInit {

  idOperazione: number;
  idProgetto: number;
  idAffidamento: number;
  codiceProgetto: string;
  user: UserInfoSec;
  soggettoControllore: string;
  codTipoChecklist: string;
  htmlCheckList: string;
  statoDoc: string;
  statoInLoco: string;
  statoEsecuzioneGetChecklistHtmlBody: string;
  localCodStatoTipoDocIndex: string;

  checklistHtmlDTO: ChecklistHtmlDTO;

  files: Array<File> = new Array<File>();
  filesDTO: Array<FileDTO> = new Array<FileDTO>();

  idEsitoFase1: string;
  idEsitoFase2: string;
  isRichiestaIntegrazione: boolean = false;
  noteRichiestaIntegrazione: string;

  //LOADED
  loadedSalva: boolean = true;
  loadedRichiestaIntegrazione: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private affidamentiService: AffidamentiService,
    private handleExceptionService: HandleExceptionService,
    private router: Router,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idAffidamento = +params['id'];

            console.log('ANG nuova-checklist-documentale idAffidamento=' + this.idAffidamento);

            /*
            pbandiwebrce ->   CPBEChecklistAffidamentoHtml.caricaModelloChecklist()
            */

            this.soggettoControllore = '';
            if (this.user.cognome != null && this.user.nome != null) {
              this.soggettoControllore = this.user.cognome + ' ' + this.user.nome;
            } else {
              this.soggettoControllore = this.user.codFisc;
            }
            //console.log('ANG nuova-checklist-documentale soggettoControllore=' + this.soggettoControllore);

            //console.log('param progetto='+this.activatedRoute.snapshot.paramMap.get('progetto'));
            this.idProgetto = +this.activatedRoute.snapshot.paramMap.get('progetto');
            //console.log('ANG nuova-checklist-documentale idProgetto=' + this.idProgetto);

            //this.codTipoChecklist = 'CLA'; //PK Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_DOCUMENTALE
            //this.codTipoChecklist = 'CLILA'; //Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO

            this.codTipoChecklist = this.activatedRoute.snapshot.paramMap.get('tipoChecklistAffidamento');
            //console.log('ANG nuova-checklist-documentale codTipoChecklist=' + this.codTipoChecklist);

            this.subscribers.checklistHtmlDTO = this.affidamentiService.getModelloCheckListAffidamentoHtml(this.idProgetto,
              this.idAffidamento, this.soggettoControllore, this.codTipoChecklist).subscribe(res => {
                //console.log('ANG nuova-checklist-documentale res=' + res);

                this.htmlCheckList = res.contentHtml;
                this.checklistHtmlDTO = res;

                //if (res.allegati != null){
                //  console.log('ANG nuova-checklist-documentale res.allegati.length=' + res.allegati.length);
                //}

                // TODO PK : devo passare gli allegati nell'array "files"...
                // se la checklist e' una bozza non li visualizzo
                // se la checklist e' definitiva ???
                //this.files.push() = ...

                if (res.esitoIntermedio != null)
                  this.idEsitoFase1 = res.esitoIntermedio.idEsito + "";

                if (res.esitoDefinitivo != null)
                  this.idEsitoFase2 = res.esitoDefinitivo.idEsito + "";

                //console.log('ANG nuova-checklist-documentale codStatoTipoDocIndex=' + this.checklistHtmlDTO.codStatoTipoDocIndex);
                //console.log('ANG nuova-checklist-documentale idDocumentoIndex=' + this.checklistHtmlDTO.idDocumentoIndex);
                //console.log('ANG nuova-checklist-documentale esitoDefinitivo=' + this.checklistHtmlDTO.esitoDefinitivo);
                //console.log('ANG nuova-checklist-documentale esitoIntermedio=' + this.checklistHtmlDTO.esitoIntermedio);

                //console.log('ANG nuova-checklist-documentale checklistHtmlDTO.allegati.length=' + this.checklistHtmlDTO.allegati.length);

                this.funzInizializzaCheckListAffidamenti();

              }, err => {
                console.log('ANG nuova-checklist-documentale err=' + err);
                this.handleExceptionService.handleNotBlockingError(err);
              });
          });
        }
      }
    });
  }


  ngAfterViewInit() {
    //console.log("ANG nuova-checklist-documentale ngAfterViewInit BEGIN-END");
  }

  funzInizializzaCheckListAffidamenti() {
    //console.log("ANG nuova-checklist-documentale  funzInizializzaCheckListAffidamenti BEGIN");

    //PK : forse l'html che importo e' troppo grosso, non riesco ad accedere direttamente al suo DOM
    // devo usare setTimeout.... anche solo 10ms bastano
    // chiamo una funziona Angular che chiama la funzione js
    setTimeout(() => { this.inizializzaCheckListANgular(), 1 });
    console.log("ANG nuova-checklist-documentale  funzInizializzaCheckListAffidamenti END");
  }

  inizializzaCheckListANgular() {
    inizializzaCheckList();
  }
  /*
      scrivi(){
        console.log(" >> scrivi BEGIN");
          console.log(" >> attributo na_1=" + document.getElementById('na_1'));
          console.log(" >> attributo value=" + document.getElementById('na_1').getAttribute("value"));
          console.log(" >> attributo checked=" + document.getElementById('na_1').getAttribute("checked"));
  
          if(document.getElementById('na_1').getAttribute("checked") != null){
            console.log(" >> attributo na_1 is checked");
          }else{
            console.log(" >> attributo na_1 is NOT checked");
          }
  
          if(document.getElementById('neg_1').getAttribute("checked") != null){
            console.log(" >> attributo neg_1 is checked");
          }else{
            console.log(" >> attributo neg_1 is NOT checked");
          }
          console.log(" >> scrivi END");
      }
  */


  funzSalvaChecklistBozza() {  // checklist in loco "bozza"
    //console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBozza BEGIN");
    this.localCodStatoTipoDocIndex = 'B';
    this.funzSalvaChecklistBZ();
    //console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBozza END");
  }

  funzSalvaChecklistDefinitivo() { // checklist in loco "salva definitivo"
    //console.log("ANG nuova-checklist-documentale  funzSalvaChecklistDefinitivo BEGIN");
    this.localCodStatoTipoDocIndex = 'D';
    this.funzSalvaChecklist();
    //console.log("ANG nuova-checklist-documentale  funzSalvaChecklistDefinitivo END");
  }

  funzSalvaChecklistBZ() {
    this.loadedSalva = false;
    console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBZ BEGIN");

    // console.log("ANG funzSalvaChecklistBZ appDatatipoChecklistAffidamento = " + document.getElementById('widg_hiddenTipologiaChecklist').);
    //console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBZ appDatatipoChecklistAffidamento = " + this.codTipoChecklist);

    //inizializzaCheckList();
    if (document.getElementById('fixH') != null) {
      document.getElementById('fixH').remove();
    }

    this.statoEsecuzioneGetChecklistHtmlBody = getChecklistHtmlBody(null, true, this.localCodStatoTipoDocIndex);
    //console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBZ statoEsecuzioneGetChecklistHtmlBody=" + this.statoEsecuzioneGetChecklistHtmlBody);

    //invoco metodi di salvataggio su DB
    //pbandiwebrce : CPBEChecklistAffidamentoHtml.salvaCheckListBozza 

    //html da salvare si trova nel campo hidden  $("#hiddenChecklistHtmlContent")

    if (this.statoEsecuzioneGetChecklistHtmlBody == "OK") {
      //console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBZ eseguo invocazione servizi REST per salvataggio ");

      //PK : 13/10 devo controllare che sia stato allegato almeno un doc??
      //console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBZ html="+ document.getElementById('hiddenChecklistHtmlContent').getAttribute("value"));
      console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBZ attoConcessioneContributo=[" + document.getElementById('attoConcessioneContributo').getAttribute("value") + "]");
      console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBZ dataControllo=[" + document.getElementById('dataControllo').getAttribute("value") + "]");


      let checklistAffidamentoHtmlDTO = new ChecklistAffidamentoHtmlBozzaDTO(this.localCodStatoTipoDocIndex,
        document.getElementById('hiddenChecklistHtmlContent').getAttribute("value"),
        "", //fasiHtml
        this.idAffidamento,
        this.checklistHtmlDTO.idDocumentoIndex, //idDocumentoIndex
        this.idProgetto,
        this.soggettoControllore,
        "", //bytesVerbale
        this.files //Array<FileDTO>
      );


      this.subscribers.save = this.affidamentiService.salvaCheckListAffidamentoDocumentaleHtmlBozza(
        new SalvaCheckListAffidamentoDocumentaleBozzaHtmlRequest(this.idProgetto,
          this.localCodStatoTipoDocIndex,
          checklistAffidamentoHtmlDTO,
          this.idAffidamento,
          this.isRichiestaIntegrazione,
          this.noteRichiestaIntegrazione,
          this.codTipoChecklist
        )).subscribe(data => {
          if (data) {
            //console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBZ data.esito="+data.esito);
            console.log(data);
            if (data.esito) {

              console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBZ esito OK");

              // PK cosa faccio? mostro messaggio "Salvataggio OK" ??

              this.router.navigate(["drawer/" + this.idOperazione + "/modificaAffidamento/" + this.idAffidamento, { checklist: "SUCCESS" }]);
            } else {

              console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBZ esito KO");

              //this.showMessageError(data.msg);
            }
          }
          this.loadedSalva = true;
        }), err => {
          this.handleExceptionService.handleNotBlockingError(err);

          //console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBZ errore");
          //this.showMessageError("Errore nel salvataggio dell'affidamento.")
          this.loadedSalva = true;
        }

    } else {
      console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBZ NON eseguo invocazione servizi REST per salvataggio ");
      this.loadedSalva = true;
    }

    console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBZ END");
  }


  funzSalvaChecklist() { // checklist in loco "salva definitivo"

    this.loadedSalva = false; // fa partire il "coso che gira"
    console.log("ANG nuova-checklist-documentale  funzSalvaChecklist BEGIN ");
    console.log("ANG nuova-checklist-documentale  funzSalvaChecklist this.codTipoChecklist=" + this.codTipoChecklist);

    //inizializzaCheckList();
    if (document.getElementById('fixH') != null) {
      document.getElementById('fixH').remove();
    }

    // la funzione javascript getChecklistHtmlBody fa anche il controllo
    // che siano valorizzati a campi "required" e gli eventuali "esiti"
    if (this.isRichiestaIntegrazione) {
      // se e' una richiesta di integrazione non faccio nessun controllo
      this.statoEsecuzioneGetChecklistHtmlBody = getChecklistHtmlBody(null, true, this.localCodStatoTipoDocIndex);
    } else {
      this.statoEsecuzioneGetChecklistHtmlBody = getChecklistHtmlBody(null, false, this.localCodStatoTipoDocIndex);
    }
    console.log("ANG nuova-checklist-documentale  funzSalvaChecklist statoEsecuzioneGetChecklistHtmlBody=" + this.statoEsecuzioneGetChecklistHtmlBody);

    //console.log("ANG nuova-checklist-documentale  funzSalvaChecklist AA = "+document.getElementById('referenteBeneficiario').getAttribute("value"));

    if (this.statoEsecuzioneGetChecklistHtmlBody == "KO") {
      console.log("ANG nuova-checklist-documentale  funzSalvaChecklist MOSTRO ERRORE");
      this.loadedSalva = true;
    } else {
      /* PK : da colloquio con Analisti non vanno fatti controlli sulla valorizzazione dei cmapi checklist
      if(!this.isRichiestaIntegrazione){
      // verifico che siano valorizzati tutti i checkbox 
        if(!this.verificaValorizzazioneCheckbox()){
        console.log("ANG nuova-checklist-documentale  funzSalvaChecklist MOSTRO ERRORE");
        visualizzaErrorMsg2('CHECKBOX_ERR');
        this.loadedSalva = true; // disattivo il "coso che gira" per far vedere i messaggi d'errore
        this.statoEsecuzioneGetChecklistHtmlBody = "KO";
        console.log("ANG nuova-checklist-documentale this.codTipoChecklist="+this.codTipoChecklist);
        }
      }
      */
      if (this.statoEsecuzioneGetChecklistHtmlBody == "OK" && this.codTipoChecklist == "CLILA") {
        // verifico che sia presente il verbale
        if (this.files.length == 0) {
          console.log("ANG nuova-checklist-documentale  funzSalvaChecklist this.files NULLO error");

          visualizzaErrorMsg2('FILE_VERBALE_ERR');
          this.loadedSalva = true; // disattivo il "coso che gira" per far vedere i messaggi d'errore
          this.statoEsecuzioneGetChecklistHtmlBody = "KO";

        }
      }
    }
    //invoco metodi di salvataggio su DB
    //pbandiwebrce : CPBEChecklistAffidamentoHtml.salvaCheckListDefinitivo
    //html da salvare si trova nel campo hidden  $("#hiddenChecklistHtmlContent")

    console.log("ANG nuova-checklist-documentale  funzSalvaChecklist this.files =" + this.files);
    console.log("ANG nuova-checklist-documentale  funzSalvaChecklist this.statoEsecuzioneGetChecklistHtmlBody =" + this.statoEsecuzioneGetChecklistHtmlBody);

    if (this.statoEsecuzioneGetChecklistHtmlBody == "OK") {

      // checklist in loco
      if (this.codTipoChecklist == "CLILA") {
        //console.log("ANG nuova-checklist-documentale  funzSalvaChecklist [checklist in loco] eseguo invocazione servizi REST per salvataggio ");

        let checklistAffidamentoHtmlDTO = new ChecklistAffidamentoHtmlDTO(this.localCodStatoTipoDocIndex,
          document.getElementById('hiddenChecklistHtmlContent').getAttribute("value"),
          "", //fasiHtml
          this.idAffidamento,
          this.checklistHtmlDTO.idDocumentoIndex,
          this.idProgetto,
          this.soggettoControllore,
          "", //bytesVerbale
          this.files
        );
        console.log("ANG nuova-checklist-documentale 1");

        this.subscribers.save = this.affidamentiService.salvaCheckListAffidamentoInLocoHtml(
          new SalvaCheckListAffidamentoDocumentaleHtmlRequest(this.idProgetto,
            this.localCodStatoTipoDocIndex,
            checklistAffidamentoHtmlDTO,
            this.idAffidamento,
            this.isRichiestaIntegrazione,
            this.noteRichiestaIntegrazione,
            this.codTipoChecklist //'CLA' o 'CLILA'
          ), this.user).subscribe(data => {
            console.log("ANG nuova-checklist-documentale 2");
            if (data) {
              console.log(data);
              if (data.esito) {
                //console.log("ANG nuova-checklist-documentale  funzSalvaChecklist esito OK");
                this.router.navigate(["drawer/" + this.idOperazione + "/affidamenti/" + this.idProgetto, { checklist: "SUCCESS" }]);
              } else {
                console.log("ANG nuova-checklist-documentale  funzSalvaChecklist esito KO");
              }
            }
            this.loadedSalva = true;
          }), err => {
            this.handleExceptionService.handleNotBlockingError(err);
            console.log("ANG nuova-checklist-documentale  funzSalvaChecklist errore");
            this.loadedSalva = true;
          }

      } else if (this.codTipoChecklist == "CLA") { //checklist documentale

        console.log("ANG nuova-checklist-documentale  funzSalvaChecklist [checklist documentale] eseguo invocazione servizi REST per salvataggio ");

        let checklistAffidamentoHtmlDTO = new ChecklistAffidamentoHtmlDTO(this.localCodStatoTipoDocIndex,
          document.getElementById('hiddenChecklistHtmlContent').getAttribute("value"),
          "", //fasiHtml
          this.idAffidamento,
          this.checklistHtmlDTO.idDocumentoIndex, //idDocumentoIndex
          this.idProgetto,
          this.soggettoControllore,
          "", //bytesVerbale
          this.files
        );

        this.subscribers.save = this.affidamentiService.salvaCheckListAffidamentoDocumentaleHtml(
          new SalvaCheckListAffidamentoDocumentaleHtmlRequest(this.idProgetto,
            this.localCodStatoTipoDocIndex,
            checklistAffidamentoHtmlDTO,
            this.idAffidamento,
            this.isRichiestaIntegrazione,
            this.noteRichiestaIntegrazione,
            this.codTipoChecklist //'CLA' 
          )).subscribe(data => {
            if (data) {
              console.log(data);
              if (data.esito) {
                console.log("ANG nuova-checklist-documentale  funzSalvaChecklist esito OK ");
                this.router.navigate(["drawer/" + this.idOperazione + "/affidamenti/" + this.idProgetto, { checklist: "SUCCESS" }]);
              } else {
                console.log("ANG nuova-checklist-documentale  funzSalvaChecklist esito KO ");
              }
            }
            this.loadedSalva = true;
          }), err => {
            this.handleExceptionService.handleNotBlockingError(err);
            //console.log("ANG nuova-checklist-documentale  funzSalvaChecklist errore");
            this.loadedSalva = true;
          }
      }
    } else {

      console.log("ANG nuova-checklist-documentale  funzSalvaChecklist esito KOOOOOOOO");
      this.loadedSalva = true;

    }

    console.log("ANG nuova-checklist-documentale  funzSalvaChecklist END");
  }

  verificaValorizzazioneCheckbox() {
    console.log("ANG nuova-checklist-documentale  verificaValorizzazioneCheckbox");

    var inputsCB = document.querySelectorAll("input[type=checkbox]");
    console.log("ANG nuova-checklist-documentale  VV inputsCB = " + inputsCB.length);

    var tmpElementName = '';
    var numFalseValue = 0;
    for (var i = 0; i < inputsCB.length; i++) {
      var item = inputsCB[i] as HTMLInputElement;

      if (tmpElementName == item.name) {
        if (!item.checked) {
          numFalseValue = numFalseValue + 1;
        }
        //console.log("ANG nuova-checklist-documentale  vv IF item.name="+item.name+", item.checked="+item.checked+", numFalseValue="+numFalseValue);

        if (numFalseValue == 3) {
          // 3 checkbox con lo stesso nome tutti a false
          //console.log("ANG nuova-checklist-documentale  vv IF trovati 3 checkbox non valorizzati");
          return false;
        }
      } else {
        tmpElementName = item.name;
        numFalseValue = 0;
        if (!item.checked) {
          numFalseValue = 1;
        }
        //console.log("ANG nuova-checklist-documentale  vv ELSE tmpElementName="+tmpElementName+", item.checked="+item.checked+", numFalseValue="+numFalseValue);
      }
    }

    console.log("ANG nuova-checklist-documentale  vv TUTTI i checkbox sono valorizzati");
    return true;
  }

  funzRichiestaIntegrazione() {
    console.log("ANG nuova-checklist-documentale  funzRichiestaIntegrazione");
    //this.resetMessageError();
    //this.resetMessageSuccess();
    let dialogRef = this.dialog.open(RichiestaIntegrazioneDialogComponent, {
      width: '60%'
    });
    dialogRef.afterClosed().subscribe(motivazione => {
      if (motivazione) {

        console.log("ANG nuova-checklist-documentale  funzRichiestaIntegrazione motivazione=" + motivazione);
        this.isRichiestaIntegrazione = true;
        this.noteRichiestaIntegrazione = motivazione;
        this.funzSalvaChecklistDefinitivo();
      }
    });
  }


  handleFileInput(files: Array<File>) {
    this.files.push(...files);
  }

  eliminaFile(index: number) {
    this.files.splice(index, 1);
  }

  isLoading() {
    if (!this.htmlCheckList || !this.loadedSalva || !this.loadedRichiestaIntegrazione) {
      return true;
    }
    return false;
  }

  goToGestioneAffidamenti() {
    this.router.navigate(["drawer/" + this.idOperazione + "/modificaAffidamento", this.idAffidamento]);
  }
}