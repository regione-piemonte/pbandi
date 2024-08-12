import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { ValidazioneService } from '../../services/validazione.service';
import { saveAs } from 'file-saver-es';
import { ArchivioFileService, Constants, DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { SalvaCheckListValidazioneDocumentaleHtmlRequest } from '../../commons/requests/salva-checklist-validazioneDocumentaleHml-request';
import { MatDialog } from '@angular/material/dialog';
import { AllegaVerbaleDialogComponent } from 'src/app/checklist/components/allega-verbale-dialog/allega-verbale-dialog.component';
import { EsitoValidazioneDichSpesaComponent } from '../esito-validazione-dich-spesa/esito-validazione-dich-spesa.component';
import { SharedService } from 'src/app/shared/services/shared.service';


declare const inizializzaCheckListValidazione: any;      //funzione definita in assets/js/checklistValidazione2.js
declare const wPrint: any;//funzione definita in assets/js/checklistValidazione2.js
declare const updateValuesCLValidazione: any;
declare const verifichePreSalvaChecklistValidazioneDefinitivo;

@Component({
  selector: 'app-checklist-chiudi-validazione',
  templateUrl: './checklist-chiudi-validazione.component.html',
  styleUrls: ['./checklist-chiudi-validazione.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ChecklistChiudiValidazioneComponent implements OnInit {

  idOperazione: number;
  idProgetto: number;
  idBandoLinea: number
  idDichiarazioneDiSpesa: number;
  user: UserInfoSec;
  beneficiario: string;
  codiceProgetto: string;
  idDocumentoIndex: number;
  nomeFile: string;
  idDocumentoIndexPiuGreen: number;
  hIdDocumentoIndex: number; //id della bozza

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  htmlModelloCheckList: string = '';  //se non lo inizializzo a video vedo per pochi sec "undefined"
  checklistHtml: string;
  statoChecklist: string;

  viewButtonScaricaBozza: boolean = false;
  dsIntegrativaConsentita: boolean;

  //LOADED
  loadedInizializza: boolean;
  loadedChecklist: boolean;
  loadedDownload: boolean = true;
  loadedSave: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  // Costante Bandi FP
  isFP: boolean = true;

  //visualizzazione modifiche
  isVisible: boolean = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private validazioneService: ValidazioneService,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog,
    private sharedService: SharedService,
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
      this.idDichiarazioneDiSpesa = +params['idDichiarazioneDiSpesa'];

      console.log('ANG checklist-chiudi-validazione this.idDichiarazioneDiSpesa=' + this.idDichiarazioneDiSpesa);
      console.log('ANG checklist-chiudi-validazione this.idProgetto=' + this.idProgetto);
      console.log('ANG checklist-chiudi-validazione this.idBandoLinea=' + this.idBandoLinea);

      if (this.activatedRoute.snapshot.paramMap.get('dsIntegrativaConsentita')) {
        this.dsIntegrativaConsentita = true;
      } else {
        this.dsIntegrativaConsentita = false;
      }
      console.log('ANG checklist-chiudi-validazione this.dsIntegrativaConsentita=' + this.dsIntegrativaConsentita);

      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
          this.loadedInizializza = false;
          this.subscribers.inizializza = this.validazioneService.inizializzaValidazione(this.idDichiarazioneDiSpesa, this.idProgetto, this.idBandoLinea, this.user.codiceRuolo)
            .subscribe(data => {
              if (data) {
                this.codiceProgetto = data.codiceVisualizzatoProgetto;
                this.idDocumentoIndexPiuGreen = data.idDocumentoIndexPiuGreen;
                this.idDocumentoIndex = data.idDocumentoIndex;
                this.nomeFile = data.nomeFile;
              }

              //PK pbandiweb -> CPBECheckListHtmlValidazione::inizializzaCP
              this.loadedChecklist = false;
              this.subscribers.checklistHtmlDTO = this.validazioneService.getModuloCheckListValidazioneHtml(this.idProgetto, this.idDichiarazioneDiSpesa).subscribe(res => {
                console.log('ANG checklist-chiudi-validazione res=' + res);

                console.log('ANG checklist-chiudi-validazione res.idDocumentoIndex=' + res.idDocumentoIndex);
                this.htmlModelloCheckList = res.contentHtml;
                this.funzInizializzaCheckList();

                /*
                ChecklistHtmlDTO moduloCheckListValidazioneHtml = checklistHtmlSrv.getModuloCheckListValidazioneHtml(user.getIdUtente(),
                 user.getIdIride(), theModel.getAppDatadatiGenerali().getProgetto().getId(), soggettoControllore,	idDichiarazione);

                 idProgetto : 19212
                 getIdDichiarazione() : 27121

                 String soggettoControllore = "";
                 if (!ObjectUtil.isEmpty(user.getCognome())    && !ObjectUtil.isEmpty(user.getNome()))
                     soggettoControllore = user.getCognome() + " " + user.getNome();
                 else if (!ObjectUtil.isEmpty(user.getCodFisc()))
                     soggettoControllore = user.getCodFisc();

                */

                console.log('ANG checklist-chiudi-validazione res.idDocumentoIndex=' + res.idDocumentoIndex);
                if (res.idDocumentoIndex != null) {
                  this.hIdDocumentoIndex = res.idDocumentoIndex;
                  this.viewButtonScaricaBozza = true;
                }
                this.loadedChecklist = true;
              }, err => {
                console.log('ANG checklist-chiudi-validazione  err=' + err);
                this.handleExceptionService.handleNotBlockingError(err);
                this.showMessageError("Errore in fase di caricamento della checklist.");
                this.loadedChecklist = true;
              });

              this.loadedInizializza = true;
            }, err => {
              this.handleExceptionService.handleBlockingError(err);
            });
        }
      });
    });


    // Controllo se abilitare le nuove finzioni e disabilitare le vecchie in base all'ambiente
    this.subscribers.costanteFin = this.userService.isCostanteFinpiemonte().subscribe(data => {
      console.log("isCostanteFinpiemonte: ", data)

      this.isVisible = data;
    })

    console.log("THIS:IDBANDOLINEA", this.idBandoLinea);
    this.subscribers.bandoFin = this.userService.isBandoCompetenzaFinpiemonte(this.idBandoLinea).subscribe(data => {
      this.isFP = data;
      console.log("IS FP: ", this.isFP)
    })
  }

  funzInizializzaCheckList() {
    console.log("ANG checklist-chiudi-validazione  funzInizializzaCheckList BEGIN");

    //PK tutti i javascript in assets/js vengono caricati assieme :
    //  non mettere stesso nome delle function
    //  non mettere javascript fuori dalle function

    //PK : forse l'html che importo e' troppo grosso, non riesco ad accedere direttamente al suo DOM
    // devo usare setTimeout.... anche solo 10ms bastano
    // chiamo una funziona Angular che chiama la funzione js
    setTimeout(() => { this.inizializzaCheckListANgular(), 2 });
    console.log("ANG checklist-chiudi-validazione  funzInizializzaCheckList END");
  }

  inizializzaCheckListANgular() {
    console.log("ANG checklist-chiudi-validazione  inizializzaCheckListANgular BEGIN");
    inizializzaCheckListValidazione();
    console.log("ANG checklist-chiudi-validazione  inizializzaCheckListANgular END");
  }


  funzSalvaChecklistValidazioneBozza() {
    this.resetMessageError();
    this.resetMessageSuccess();
    console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneBozza BEGIN");

    // if (!wPrint('b')) {

    console.log(' ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneBozza -> NOT WPRINT');

    // mostro messaggio errore
    //document.getElementById('checklistHtmlDiv').append('<div id="errorMsg" class="errorMsg"><span><p>Attenzione! La compilazione delle sezioni 04.D1 e 04.D2 &egrave; esclusiva.</p></span><i class="chiudiMsg fa fa-window-close-o fa-2" aria-hidden="true"></i></div>');

    // } else {

    // non ci sono errori
    console.log(' ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneBozza ->  WPRINT');

    if (document.getElementById('errorMsg') != null) {
      document.getElementById('errorMsg').remove();
      console.log(' ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneBozza -> rimosso errorMsg');
    }

    if (document.getElementById('fixH') != null) {
      document.getElementById('fixH').remove();
    }
    updateValuesCLValidazione();

    if (document.getElementById('checklistHtmlDiv') != null) {
      // recupero l'intera struttura HTML contenuta all'interno del div con id=checklistHtmlDiv
      this.checklistHtml = document.getElementById('checklistHtmlDiv').outerHTML;
    }
    console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneBozza this.checklistHtml.length=" + this.checklistHtml.length);

    // invoco metodo url : 'checklistHtml!saveCheckListDocumentaleHtml.do',
    this.statoChecklist = 'B';
    this.loadedSave = false;
    this.subscribers.save = this.validazioneService.saveCheckListDocumentaleHtml(new SalvaCheckListValidazioneDocumentaleHtmlRequest(this.idProgetto, this.idDichiarazioneDiSpesa,
      this.idDocumentoIndex, this.statoChecklist, this.checklistHtml, this.idBandoLinea, this.codiceProgetto, this.dsIntegrativaConsentita)).subscribe(data => {
        console.log(data);

        if (data) {

          console.log("ANG nuova-checklist-documentale  data esito=" + data.esito);

          // visualizzo bottone "scarica bozza"
          // this.viewButtonScaricaBozza = true;

          // PK : redirigo sulla pagina "validazione"
          this.tornaAValidazione("Salvataggio avvenuto con successo.");

        }
        this.loadedSave = true;
      }), err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di salvataggio.");
        console.log("ANG nuova-checklist-documentale  funzSalvaChecklistBZ errore");
        this.loadedSave = true;
      }

    console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneBozza  this.idProgetto=" + this.idProgetto);
    console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneBozza  this.idDichiarazioneDiSpesa=" + this.idDichiarazioneDiSpesa);
    console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneBozza  this.idDocumentoIndex=" + this.idDocumentoIndex);
    console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneBozza  this.idDocumentoIndexPiuGreen=" + this.idDocumentoIndexPiuGreen);

    // }

    console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneBozza END");
  }


  funzScaricaBozza() {
    console.log("ANG checklist-chiudi-validazione  funzScaricaBozza BEGIN");

    //console.log("ANG checklist-chiudi-validazione  funzScaricaBozza getApiURL=" + this.configService.getApiURL());
    //console.log("ANG checklist-chiudi-validazione  funzScaricaBozza idDocumentoIndex=" + this.idDocumentoIndex.toString());
    console.log("ANG checklist-chiudi-validazione  funzScaricaBozza hIdDocumentoIndex=" + this.hIdDocumentoIndex);

    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downlaodDS = this.archivioFileService.leggiFileConNome(this.configService.getApiURL(), this.hIdDocumentoIndex.toString()).subscribe(res => {
      if (res) {
        console.log("ANG checklist-chiudi-validazione  funzScaricaBozza saveas");
        let nome = res.headers.get("header-nome-file");
        saveAs(new Blob([res.body]), nome);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });

    console.log("ANG checklist-chiudi-validazione  funzScaricaBozza END");
  }


  funzSalvaChecklistValidazioneDefinitivo() {
    console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo BEGIN");

    // validazioni
    //if(!checkVerify () || !validazione() || !wPrint('d')){
    // wPrint : verifica le sezioni
    // validazione : verifica i campi
    //    attoConcessioneContributo : obbligatorio
    //    firmaResponsabile : obbligatorio, minlength: 3
    //    descIrregolarita : obbligatorio
    //    dataControllo: obbligatorio
    //  	luogoControllo: obbligatorio
    // checkVerify : verifica siano flaggati i checkbox

    //console.log("TEST:", this.htmlModelloCheckList.indexOf("TESTO DA CONCORDARdfgsffE"));
    if (this.isVisible && this.htmlModelloCheckList.indexOf("TESTO DA CONCORDARE") != -1) {
      console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo verifiche superate");

      this.loadedSave = true;
      let dialogRef = this.dialog.open(AllegaVerbaleDialogComponent, {
        width: '50%',
        data: {
          required: "N",
          codTipoChecklist: "CL"
        }
      });
      dialogRef.afterClosed().subscribe(res => {
        console.log("PUNTO 2");
        console.log(res);
        if (res.esito === "S") {

// damodificare marty prima chiude poi esito

          if (this.isFP) {
            //salvataggio checklist
            this.loadedSave = false;
            updateValuesCLValidazione();
            if (document.getElementById('checklistHtmlDiv') != null) {
              // recupero l'intera struttura HTML contenuta all'interno del div con id=checklistHtmlDiv
              this.checklistHtml = document.getElementById('checklistHtmlDiv').outerHTML;
            }
            let dialogRef = this.dialog.open(EsitoValidazioneDichSpesaComponent, {
              width: '60%',
              maxHeight: '90%',
              disableClose: true,
              data: {
                idBandoLinea: this.idBandoLinea,
                idDichiarazioneDiSpesa: this.idDichiarazioneDiSpesa,
                idProgetto: this.idProgetto,
                idDocumentoIndex: this.idDocumentoIndex,
                statoChecklist: this.statoChecklist,
                checklistHtml:  this.checklistHtml,
                codiceProgetto: this.codiceProgetto,
                dsIntegrativaConsentita :this.dsIntegrativaConsentita,
                files :res.files,
                isChiudiValidazione : "1"
              }
            });

            dialogRef.afterClosed().subscribe(data => {
                if (data.esito == true) {
                  if(data.params.length>0){

                  this.router.navigate(["/drawer/" + this.idOperazione + "/checklistChiudiValidazioneConferma/" + this.idProgetto + "/" + this.idBandoLinea + "/" + this.idDichiarazioneDiSpesa,
                  { 'idDocIndexCK': data.params[0], 'nomeFileCK': data.params[1], 'idReportDettaglioDocSpesa': data.params[2], 'nomeFileRD': data.params[3] , 'isChiusa': false }]);
                  
                  }

                  else{

                    this.router.navigate(["/drawer/" + this.idOperazione + "/checklistChiudiValidazioneConferma/" + this.idProgetto + "/" + this.idBandoLinea + "/" + this.idDichiarazioneDiSpesa,
                    { 'idDocIndexCK': data.params[0], 'nomeFileCK': data.params[1], 'idReportDettaglioDocSpesa': data.params[2], 'nomeFileRD': data.params[3] , 'isChiusa': true  }]);

                  }
                } else {
                  this.showMessageError("Errore in fase di chiusura validazione.");
                }
                this.loadedSave = true;
              }
            )
          } 
          
          
          else {

            //salvataggio checklist
            this.loadedSave = false;
            console.log('ANG checklist-chiudi-validazione funzSalvaChecklistValidazioneDefinitivo procedo col salvataggio con verbali');
            console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo this.files =" + res.files);
            console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo this.idDichiarazione =" + this.idDichiarazioneDiSpesa);
            console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo SI");

            updateValuesCLValidazione();

            if (document.getElementById('checklistHtmlDiv') != null) {
              // recupero l'intera struttura HTML contenuta all'interno del div con id=checklistHtmlDiv
              this.checklistHtml = document.getElementById('checklistHtmlDiv').outerHTML;
            }
            console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo this.checklistHtml.length=" + this.checklistHtml.length);
            console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo  this.user.beneficiarioSelezionato.idBeneficiario=" + this.user.beneficiarioSelezionato.idBeneficiario);

            // invoco metodo salvataggio
            this.loadedSave = false;
            this.subscribers.save = this.validazioneService.chiudiValidazioneChecklistHtml(this.idProgetto, this.idDichiarazioneDiSpesa,
              this.idDocumentoIndex, this.statoChecklist, this.checklistHtml, this.idBandoLinea, this.codiceProgetto, this.dsIntegrativaConsentita, res.files).subscribe(data => {
                console.log(data);
                if (data) {
                  if (data.esito) {
                    //data.params = [idDocIndexDichiarazione, nomeFileCK, idReportDettaglioDocSpesa, nomeFileRD]
                    console.log("ANG checklist-chiudi-validazione  chiudiValidazioneChecklistHtml fatta");


                    //09/02/2023: PBAN-OPE-CDU-039-V10-OPEREN039_Chiudi la validazione della dichiarazione di spesa
                    //Solo se si tratta di BANDI FINPIEMONTE devo aprire il dialog EsitoValidazioneDichSpesaComponent

                    // if(this.isFP) {
                    //   //apertura dialog per inserimento note
                    //   let dialogRef = this.dialog.open(EsitoValidazioneDichSpesaComponent, {
                    //     width: '60%',
                    //     maxHeight: '90%',
                    //     disableClose: true,
                    //     data: {
                    //       idBandoLinea: this.idBandoLinea,
                    //       idDichiarazioneDiSpesa: this.idDichiarazioneDiSpesa,
                    //       idProgetto: this.idProgetto
                    //     }
                    //   });

                    //   dialogRef.afterClosed().subscribe(data => {
                    //     console.log(data)
                    //   })

                    // } else {

                    //vado a checklistChiudiValidazioneConferma
                    this.router.navigate(["/drawer/" + this.idOperazione + "/checklistChiudiValidazioneConferma/" + this.idProgetto + "/" + this.idBandoLinea + "/" + this.idDichiarazioneDiSpesa,
                    { 'idDocIndexCK': data.params[0], 'nomeFileCK': data.params[1], 'idReportDettaglioDocSpesa': data.params[2], 'nomeFileRD': data.params[3] }]);

                    // }

                  } else {
                    this.showMessageError("Errore in fase di chiusura validazione.");
                  }
                }
                this.loadedSave = true;
              }, err => {
                this.handleExceptionService.handleNotBlockingError(err);
                this.showMessageError("Errore in fase di chiusura validazione.");
                this.loadedSave = true;
              });
          }
        }
      });
    } 
    
    else {

      if (!verifichePreSalvaChecklistValidazioneDefinitivo()) {

        console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo verifiche NON superate");

      } else {

        console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo verifiche superate");

        this.loadedSave = true;
        let dialogRef = this.dialog.open(AllegaVerbaleDialogComponent, {
          width: '50%',
          data: {
            required: "N",
            codTipoChecklist: "CL"
          }
        });
        dialogRef.afterClosed().subscribe(res => {
          console.log("PUNTO 1");
          
          console.log(res);
          
          if (res.esito === "S") {
            // damodificare marty prima chiude poi esito
            if (this.isVisible && this.isFP) {
              //salvataggio checklist
              this.loadedSave = false;

              updateValuesCLValidazione();

              if (document.getElementById('checklistHtmlDiv') != null) {
                // recupero l'intera struttura HTML contenuta all'interno del div con id=checklistHtmlDiv
                this.checklistHtml = document.getElementById('checklistHtmlDiv').outerHTML;
              }
              //apertura dialog per inserimento note
              let dialogRef = this.dialog.open(EsitoValidazioneDichSpesaComponent, {
                width: '60%',
                maxHeight: '90%',
                disableClose: true,
                data: {
                  idBandoLinea: this.idBandoLinea,
                  idDichiarazioneDiSpesa: this.idDichiarazioneDiSpesa,
                  idProgetto: this.idProgetto,
                  idDocumentoIndex: this.idDocumentoIndex,
                  statoChecklist: this.statoChecklist,
                  checklistHtml:  this.checklistHtml,
                  codiceProgetto: this.codiceProgetto,
                  dsIntegrativaConsentita :this.dsIntegrativaConsentita,
                  files :res.files,
                  isChiudiValidazione : "1"
                }
              });

              dialogRef.afterClosed().subscribe(data => {
                console.log(data)
                  if (data.esito === true) {
                    if(data.params.length>0){

                      this.router.navigate(["/drawer/" + this.idOperazione + "/checklistChiudiValidazioneConferma/" + this.idProgetto + "/" + this.idBandoLinea + "/" + this.idDichiarazioneDiSpesa,
                      { 'idDocIndexCK': data.params[0], 'nomeFileCK': data.params[1], 'idReportDettaglioDocSpesa': data.params[2], 'nomeFileRD': data.params[3] , 'isChiusa': false }]);
                      
                      }
    
                      else{
    
                        this.router.navigate(["/drawer/" + this.idOperazione + "/checklistChiudiValidazioneConferma/" + this.idProgetto + "/" + this.idBandoLinea + "/" + this.idDichiarazioneDiSpesa,
                      { 'idDocIndexCK': data.params[0], 'nomeFileCK': data.params[1], 'idReportDettaglioDocSpesa': data.params[2], 'nomeFileRD': data.params[3], 'isChiusa': true  }]);
    
                      }
                  } else {
                    this.showMessageError("Errore in fase di chiusura validazione.");
                  }
                  this.loadedSave = true;
              })

            } else {

              //salvataggio checklist
              this.loadedSave = false;
              console.log('ANG checklist-chiudi-validazione funzSalvaChecklistValidazioneDefinitivo procedo col salvataggio con verbali');
              console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo this.files =" + res.files);
              console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo this.idDichiarazione =" + this.idDichiarazioneDiSpesa);
              console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo SI");

              updateValuesCLValidazione();

              if (document.getElementById('checklistHtmlDiv') != null) {
                // recupero l'intera struttura HTML contenuta all'interno del div con id=checklistHtmlDiv
                this.checklistHtml = document.getElementById('checklistHtmlDiv').outerHTML;
              }
              console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo this.checklistHtml.length=" + this.checklistHtml.length);
              console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo  this.user.beneficiarioSelezionato.idBeneficiario=" + this.user.beneficiarioSelezionato.idBeneficiario);

              // invoco metodo salvataggio
              this.loadedSave = false;
              this.subscribers.save = this.validazioneService.chiudiValidazioneChecklistHtml(this.idProgetto, this.idDichiarazioneDiSpesa,
                this.idDocumentoIndex, this.statoChecklist, this.checklistHtml, this.idBandoLinea, this.codiceProgetto, this.dsIntegrativaConsentita, res.files).subscribe(data => {
                  console.log(data);
                  if (data) {
                    if (data.esito) {
                      //data.params = [idDocIndexDichiarazione, nomeFileCK, idReportDettaglioDocSpesa, nomeFileRD]
                      console.log("ANG checklist-chiudi-validazione  chiudiValidazioneChecklistHtml fatta");

                      //vado a checklistChiudiValidazioneConferma
                      this.router.navigate(["/drawer/" + this.idOperazione + "/checklistChiudiValidazioneConferma/" + this.idProgetto + "/" + this.idBandoLinea + "/" + this.idDichiarazioneDiSpesa,
                      { 'idDocIndexCK': data.params[0], 'nomeFileCK': data.params[1], 'idReportDettaglioDocSpesa': data.params[2], 'nomeFileRD': data.params[3] }]);

                      // }

                    } else {
                      this.showMessageError("Errore in fase di chiusura validazione.");
                    }
                  }
                  this.loadedSave = true;
                }, err => {
                  this.handleExceptionService.handleNotBlockingError(err);
                  this.showMessageError("Errore in fase di chiusura validazione.");
                  this.loadedSave = true;
                });
            }
          }
        });
      }
      // console.log("ANG checklist-chiudi-validazione  funzSalvaChecklistValidazioneDefinitivo END");
    }
  }

  downloadPdfDichiarazione() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downlaodDS = this.archivioFileService.leggiFile(this.configService.getApiURL(), this.idDocumentoIndex.toString()).subscribe(res => {
      if (res) {
        saveAs(res, this.nomeFile);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  tornaAValidazione(message?: string) {
    let url: string = "drawer/" + this.idOperazione + "/validazione/" + this.idProgetto + "/" + this.idBandoLinea + "/" + this.idDichiarazioneDiSpesa;
    if (message) {
      url += ";message=" + message;
    }
    this.router.navigateByUrl(url);
  }

  tornaAAttivitaDaSvolgere() {
    this.loadedSave = false;
    this.resetMessageError();
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto,
      this.idOperazione === Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_VALIDAZIONE ?
        Constants.DESCR_BREVE_TASK_VALIDAZIONE : Constants.DESCR_BREVE_TASK_VALIDAZIONE_INTEGRATIVA).subscribe(data => {
          window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedSave = true;
          this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
          this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
        });
  }


  /* continua() {
    //apertura dialog per inserimento note
    let dialogRef = this.dialog.open(EsitoValidazioneDichSpesaComponent, {
      width: '60%',
      maxHeight: '90%',
      disableClose: true,
      // data: {
      //   dataInit: data2
      // }
    });

    dialogRef.afterClosed().subscribe(data => {
      console.log(data)
    })
  } */

  datiProgettoEAttivitaPregresse() {
    this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
      width: '90%',
      maxHeight: '95%',
      data: {
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL()
      }
    });
  }

  contoEconomico() {
    this.dialog.open(VisualizzaContoEconomicoDialogComponent, {
      width: "90%",
      maxHeight: '90%',
      data: {
        idBandoLinea: this.idBandoLinea,
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL()
      }
    });
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
    if (!this.loadedInizializza || !this.loadedDownload || !this.loadedChecklist || !this.loadedSave) {
      return true;
    }
    return false;
  }

}
