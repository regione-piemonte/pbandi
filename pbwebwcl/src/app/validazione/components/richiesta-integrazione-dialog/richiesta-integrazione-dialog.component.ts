import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { ValidazioneService } from '../../services/validazione.service';

@Component({
  selector: 'app-richiesta-integrazione-dialog',
  templateUrl: './richiesta-integrazione-dialog.component.html',
  styleUrls: ['./richiesta-integrazione-dialog.component.scss']
})
export class RichiestaIntegrazioneDialogComponent implements OnInit {

  //SUBSCRIBERS
  subscribers: any = {};

  // Costante SVILUPPO_FP
  isVisible: boolean = false;

  // Costante Bandi FP
  isFP: boolean;

  motivazione: string;
  ggScadenza: number;

  nDocSospesi: number;
  progBandoLineaIntervento: number;

  @ViewChild('uploadLetteraAccompagnatoria')
  myLetteraAccompagnatoria: ElementRef;
  letteraAccompagnatoria: File;
  visibilitaLettera: boolean = true;

  @ViewChild('uploadReportValidazione')
  myReportValidazione: ElementRef;
  reportValidazione: File;
  visibilitaReport: boolean = true;

  @ViewChild('uploadCheckList')
  myCheckList: ElementRef;
  checklistInterna: File;
  visibilitaChecklist: boolean = false;

  //gestione messaggi errore/successo
  isMessageErrorVisible: boolean = false;
  isMessageSuccessVisible: boolean = false;
  messageError: string;
  messageSuccess: string;

  //user
  user: UserInfoSec;

  //quando la chiamata ha successo, si disabilitano i pulsanti
  disableAll: boolean = false;

  //LOADED
  loadedCostante: boolean;
  loadedBandoFP: boolean;
  loadedRichiesta: boolean = true;

  modules = {
    toolbar: [
      ['bold', 'italic', 'underline'],        // toggled buttons
      [{ 'list': 'ordered' }, { 'list': 'bullet' }],
      [{ 'script': 'sub' }, { 'script': 'super' }],      // superscript/subscript
      [{ 'indent': '-1' }, { 'indent': '+1' }],          // outdent/indent
    ]
  };

  constructor(
    private userService: UserService,
    private validazioneService: ValidazioneService,
    public dialogRef: MatDialogRef<RichiestaIntegrazioneDialogComponent>,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {

    this.nDocSospesi = this.data.nDocuSpesaSospesi;
    this.progBandoLineaIntervento = this.data.progBandoLineaIntervento;

    // Controlla l'ambiente, abilita nuove funzioni se in prod, a termine sviluppo
    this.loadedCostante = false;
    this.subscribers.costanteFin = this.userService.isCostanteFinpiemonte().subscribe(data => {
      this.isVisible = data;

      this.loadedCostante = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore durante il caricamento della costante.");
      this.loadedCostante = true;
    })

    // Controllo se il bando appartiene a Finpiemonte o regione Piemonte
    this.loadedBandoFP = false;
    this.subscribers.bandoFin = this.userService.isBandoCompetenzaFinpiemonte(this.progBandoLineaIntervento).subscribe(data => {
      this.isFP = data;
      this.loadedBandoFP = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore durante la verifica del bando competenza FP.");
      this.loadedBandoFP = true;
    })

    this.userService.userInfo$.subscribe(data => {

      this.user = data;

    });
  }

  toggleCheckbox(type) {
    switch (type) {
      case 1:
        this.visibilitaLettera = !this.visibilitaLettera;
        break;
      case 2:
        this.visibilitaChecklist = !this.visibilitaChecklist;
        break;
      case 3:
        this.visibilitaReport = !this.visibilitaReport;
        break;
    }
  }

  inviaRichiesta() {
    this.replaceCaratteriSpeciali();
    this.dialogRef.close({
      id: "old",
      motivazione: this.motivazione
    });
  }

  replaceCaratteriSpeciali() {
    this.motivazione = this.motivazione.replace(/%/g, '&percnt;');
  }

  close() {
    this.dialogRef.close();
  }

  aggAllegato() {

  }


  newInvia() {
    if (this.motivazione === undefined) {
      this.motivazione = "";
    }
    this.loadedRichiesta = false;
    // Imposto l'idStatoRichiesta diverso in base all'ente
    let idStatoRichiesta: number;
    let inviaIter: number;
    if (this.isFP) {
      idStatoRichiesta = 4;
      inviaIter = 1;
    } else {
      idStatoRichiesta = 1;
      inviaIter = 0;
    }
    if (!this.isFP) {
      this.visibilitaLettera = true;
    }
    this.subscribers.newRichiestaIntegrazione = this.validazioneService.newRichiediIntegrazione(
      this.letteraAccompagnatoria, this.visibilitaLettera,
      this.checklistInterna, this.visibilitaChecklist,
      this.reportValidazione, this.visibilitaReport,
      this.user.idUtente.toString(),
      this.data.idDichiarazioneDiSpesa,
      this.data.idProgetto,
      this.ggScadenza,
      this.motivazione,
      idStatoRichiesta,
      inviaIter
    ).subscribe(data => {
      this.loadedRichiesta = true;
      if (data?.esito) {
        this.disableAll = true;   //se la chiamata è andata a buon fine si disabilitano i pulsanti in fondo alla pagina
        this.showMessageSuccess(data.msg);
        setTimeout(() => {
          this.close();
        }, 4000);
      } else {
        this.showMessageError(data.msg);
      }
      this.loadedRichiesta = true
    }, err => {
      this.loadedRichiesta = true;
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di richiesta integrazione.");
    });


  }




  /*************************
   ***** GESTIONE FILE *****
   *************************/
  handleLetteraAccompagnatoria(files: Array<File>) {
    this.resetMessage();

    if (!files[0].name.endsWith(".pdf") && !files[0].name.endsWith(".PDF")) {
      this.showMessageError("Il file deve avere estensione .pdf");
      // check = false;
    } else {
      //viene considerato SOLO IL PRIMO FILE selezionato
      this.letteraAccompagnatoria = files[0];
    }

  }
  eliminaLetteraAccompagnatoria() {
    this.resetMessage();
    this.letteraAccompagnatoria = null;
    this.myLetteraAccompagnatoria.nativeElement.value = "";
  }

  handleReportValidazione(files: Array<File>) {
    this.resetMessage();
    if (!files[0].name.endsWith(".xlsx") && !files[0].name.endsWith(".XLSX") && !files[0].name.endsWith(".XLS") && !files[0].name.endsWith(".xls")) {
      this.showMessageError("Il file deve avere estensione .xls oppure .xlsx");
    } else {
      this.reportValidazione = files[0];
    }
    console.log("CHECKLIST INTERNA CARICATA", this.reportValidazione);
  }
  eliminaReportValidazione() {
    this.resetMessage();
    this.reportValidazione = null;
    console.log(this.myReportValidazione.nativeElement.files);
    this.myReportValidazione.nativeElement.value = "";
    console.log(this.myReportValidazione.nativeElement.files);

  }
  handleChecklistInterna(files: Array<File>) {
    this.resetMessage();

    //viene considerato SOLO IL PRIMO FILE selezionato
    this.checklistInterna = files[0];

    console.log("CHECKLIST INTERNA CARICATA", this.checklistInterna);

  }
  eliminaChecklistInterna() {
    this.resetMessage();
    this.checklistInterna = null;

    //uploadCheckList
    console.log(this.myCheckList.nativeElement.files);
    this.myCheckList.nativeElement.value = "";
    console.log(this.myCheckList.nativeElement.files);

  }

  /*************************
 **** MESSAGGI ERRORE ****
 *************************/

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  resetMessage() {
    this.resetMessageError();
    this.resetMessageSuccess();
  }

  isLoading() {
    if (!this.loadedBandoFP || !this.loadedCostante || !this.loadedRichiesta) {
      return true;
    }
    return false;
  }
}
