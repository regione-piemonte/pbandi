import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { ValidazioneService } from '../../services/validazione.service';
import { IdDescModalitaAgevolazioneDTO } from '../../commons/dto/id-desc-modalita-agevolazione-dto';
import { MatSelectChange } from '@angular/material/select';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Constants } from 'src/app/core/commons/util/constants';
import { IdDescCausaleErogDTO } from '../../commons/dto/id-desc-causale-erog-dto';

@Component({
  selector: 'app-esito-validazione-dich-spesa',
  templateUrl: './esito-validazione-dich-spesa.component.html',
  styleUrls: ['./esito-validazione-dich-spesa.component.scss']
})
export class EsitoValidazioneDichSpesaComponent implements OnInit {

  //gestione messaggi errore/successo
  error: boolean = false;
  // success: boolean = false;
  messageError: string;

  //user
  user: UserInfoSec;

  //form
  myForm: FormGroup;

  //documenti
  noAllegati: boolean;
  checklistInterna: File;
  visibilitaChecklist: boolean = false;
  letteraAccompagnatoria: File;
  visibilitaLettera: boolean = true;
  reportValidazione: File;
  visibilitaReport: boolean = true;

  @ViewChild('uploadLetteraAccompagnatoria')
  myLetteraAccompagnatoria: ElementRef;

  @ViewChild('uploadCheckList')
  myCheckList: ElementRef;

  @ViewChild('uploadReportValidazione')
  myReportValidazione: ElementRef;

  //condizioni visualizzazioni campi
  disabledImporto: boolean = true;
  disabledIres: boolean = true;

  //pulsanti mostrati
  disableConferma: boolean = true;
  // disableAvviaIter: boolean = true;
  showConferma: boolean = true;

  br55: boolean;
  br80: boolean;

  //liste dropdown
  //"da mostrare": lista da mostrare nella select (non tutte le opzioni possono essere visualizzare)
  listEsitoDichiarazioneSpesa: any[] = [];
  listEsitoDichiarazioneSpesaDaMostrare: any[] = [];
  listAttributoEsito: any[] = [];
  listAttributoEsitoDaMostrare: any[] = [];
  listModalitaAgevolazione: Array<IdDescModalitaAgevolazioneDTO>;
  listCausaliErogazione: Array<IdDescCausaleErogDTO>;
  modalitaAgevControl = new FormControl();
  causaleFinanzControl = new FormControl();
  causaleContributoControl = new FormControl();

  //variabile entita
  entita: string;
  userGestione: any;
  statoCheckList: any;
  checklistHtml: any;
  idChecklist: any;
  files: File[];
  idBandoLinea: any;
  idDichiarazioneDiSpesa: any;
  idProgetto: any;
  idDocumentoIndex: any;
  statoChecklist: any;
  codiceProgetto: any;
  dsIntegrativaConsentita: any;
  isChiudiValidazione: any;
  chiusaUfficio: boolean;

  //LOADED
  loadedBR55: boolean;
  loadedBR80: boolean;
  loadedDropDown: boolean;
  loadedChiudiValidazioneUfficio: boolean = true;
  loadedChiudiValidazioneEsito: boolean = true;

  constructor(
    public dialogRef: MatDialogRef<EsitoValidazioneDichSpesaComponent>,
    public userService: UserService,
    private fb: FormBuilder,
    private validazioneService: ValidazioneService,
    private sharedService: SharedService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.noAllegati = false;

    this.myForm = this.fb.group({
      esitoDichiarazioneSpesa: new FormControl("", [Validators.required]),
      attributoEsito: new FormControl("", [Validators.required]),
      importo: new FormControl({ disabled: true, value: null }, []),
      ires: new FormControl({ disabled: true, value: "" }, []),
      premialita: new FormControl({ disabled: true, value: null }, [Validators.required]),
    });
    this.modalitaAgevControl.disable();
    this.causaleContributoControl.disable();
    this.causaleFinanzControl.disable();
    this.causaleContributoControl.setValidators([Validators.required]);
    this.causaleFinanzControl.setValidators([Validators.required]);
    if (this.data) {
      this.idProgetto = this.data.idProgetto;
    }
    this.loadedBR55 = false;
    this.userService.isRegolaApplicabileForBandoLinea(this.data.idBandoLinea, "BR55").subscribe(res => {
      if (res != undefined && res != null) {
        this.br55 = res;
      }
      this.loadedBR55 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedBR55 = true;
    });
    this.loadedBR80 = false;
    this.userService.isRegolaApplicabileForBandoLinea(this.data.idBandoLinea, "BR80").subscribe(res => {
      this.br80 = res;
      this.loadedBR80 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedBR80 = true;
    });
    //recupero oggetti dropDown
    this.loadedDropDown = false;
    this.validazioneService.getDropDown(this.idProgetto).subscribe(data => {
      console.log("DATA: ", data);

      //popolo le liste di dropdown
      //inizialmente mostro tutte le opzioni
      this.listEsitoDichiarazioneSpesa = data.esiti;
      this.listEsitoDichiarazioneSpesaDaMostrare = data.esiti;
      this.listAttributoEsito = data.attributi;
      this.listAttributoEsitoDaMostrare = data.attributi;
      this.listModalitaAgevolazione = data.modalitaAgevolazione;
      this.listCausaliErogazione = data.causaliErogazione;

      console.log("LIST ESITO DICHIARAZIONE SPESA", this.listEsitoDichiarazioneSpesa);
      console.log("LIST ATTRIBUTO ESITO", this.listAttributoEsito);
      this.loadedDropDown = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dati.");
      this.loadedDropDown = true;
    })

    this.userService.userInfo$.subscribe(data => {
      console.log("DATA USER SERVICE", data);

      this.user = data;
      console.log("THIS.USER", this.user)
    })



    if (this.data.isChiudiValidazione === "0") {
      this.idBandoLinea = this.data.idBandoLinea.toString(),
        this.idDichiarazioneDiSpesa = this.data.idDichiarazione.toString(),
        this.statoCheckList = this.data.statoCheckList
      if (this.statoCheckList == undefined) {
        this.statoCheckList = null
      } else {
        this.statoCheckList = this.data.statoCheckList
      }
      this.statoChecklist = this.data.statoChecklist
      if (this.statoChecklist == undefined) {
        this.statoChecklist = null
      } else {
        this.statoChecklist = this.data.statoChecklist
      }


      this.checklistHtml = this.data.t,
        this.files = this.data.files,
        this.isChiudiValidazione = this.data.isChiudiValidazione,

        this.userGestione = this.data.user,
        this.idChecklist = this.data.idDichiarazione,

        this.codiceProgetto = null,
        this.dsIntegrativaConsentita = null,
        this.idDocumentoIndex = null
    }

    else if (this.data.isChiudiValidazione === "1") {

      this.idBandoLinea = this.data.idBandoLinea.toString(),
        this.idDichiarazioneDiSpesa = this.data.idDichiarazioneDiSpesa.toString(),


        this.statoChecklist = this.data.statoChecklist
      if (this.statoChecklist == undefined) {
        this.statoChecklist = null
      } else {
        this.statoChecklist = this.data.statoChecklist
      }
      this.statoCheckList = this.data.statoCheckList
      if (this.statoCheckList == undefined) {
        this.statoCheckList = null
      } else {
        this.statoCheckList = this.data.statoCheckList
      }


      this.checklistHtml = this.data.checklistHtml,
        this.files = this.data.files,
        this.isChiudiValidazione = this.data.isChiudiValidazione,

        this.codiceProgetto = this.data.codiceProgetto,
        this.dsIntegrativaConsentita = this.data.dsIntegrativaConsentita.toString(),
        this.idDocumentoIndex = this.data.idDocumentoIndex.toString(),

        this.userGestione = null,
        this.idChecklist = -1
    }
    console.log(this.statoCheckList);
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

  changeModalitaAgevolazione(event: MatSelectChange) {
    let selectedArray: number[] = event.value;
    this.myForm.get("importo").disable();
    this.myForm.get("premialita").disable();
    this.myForm.get("ires").disable();
    this.causaleContributoControl.disable();
    this.causaleFinanzControl.disable();
    this.sharedService.removeValidatorOnForm(["importo", "premialita"], this.myForm);
    if (selectedArray.includes(0)) { //tutti
      this.modalitaAgevControl.setValue([0]);
      this.causaleContributoControl.enable();
      this.causaleFinanzControl.enable();
      this.myForm.get("importo").enable();
      this.myForm.get("premialita").enable();
      this.myForm.get("ires").enable();
      this.sharedService.addValidatorOnForm(["importo", "premialita"], this.myForm, Validators.required);
    } else {
      let mod = this.listModalitaAgevolazione.filter(m => selectedArray.includes(m.idModalitaAgevolazione));
      for (let m of mod) {
        if (m.descBreveModalitaAgevolazione === Constants.DESC_BREVE_MODALITA_AGEVOLAZ_CONTRIBUTO) { //contributo
          this.myForm.get("premialita").enable();
          this.sharedService.addValidatorOnForm(["premialita"], this.myForm, Validators.required);
          this.myForm.get("ires").enable();
          this.causaleContributoControl.enable();
        } else {
          this.myForm.get("importo").enable();
          this.sharedService.addValidatorOnForm(["importo"], this.myForm, Validators.required);
          this.causaleFinanzControl.enable();
        }
      }
      if (this.myForm.get("premialita").disabled) {
        this.myForm.controls['premialita'].setValue(null);
      }
      if (this.myForm.get("importo").disabled) {
        this.myForm.controls['importo'].setValue(null);
      }
    }
    this.myForm.updateValueAndValidity();
  }

  setImporto() {
    let importo: number = this.sharedService.getNumberFromFormattedString(this.myForm.get('importo').value);
    if (importo !== null) {
      this.myForm.get('importo').setValue(this.sharedService.formatValue(importo.toString()));
    }
  }

  setPremialita() {
    let premialita: number = this.sharedService.getNumberFromFormattedString(this.myForm.get('premialita').value);
    if (premialita !== null) {
      this.myForm.get('premialita').setValue(this.sharedService.formatValue(premialita.toString()));
    }
  }


  annulla() {
    this.resetMessage();

    //si chiude il dialog esito validazione e si torna nella pagina della checklist
    this.dialogRef.close({ esito: "N", files: [] });
  }

  conferma() {
    this.myForm.markAllAsTouched();
    this.causaleFinanzControl.markAsTouched();
    this.causaleContributoControl.markAsTouched();
    if (this.myForm.valid) {
      if (this.myForm.get("attributoEsito").value == 7) {
        this.loadedChiudiValidazioneUfficio = false;
        this.validazioneService.chiudiValidazioneUfficio(this.idDichiarazioneDiSpesa).subscribe(data => {
          this.loadedChiudiValidazioneUfficio = true;
          if (data.esito == false) {
            this.showMessageError(data.msg);
          } else {
            this.dialogRef.close(data);
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di chiusura validazione ufficio.")
          this.loadedChiudiValidazioneUfficio = true;
        })
      } else {
        this.resetMessage();
        if (!this.letteraAccompagnatoria && 
          (this.myForm.get("attributoEsito").value == '1' || this.myForm.get("attributoEsito").value == '4')) {
          this.showMessageError("È obbligatorio inserire la lettera accompagnatoria!")
        } else if (this.myForm.valid) {
          this.chiamataChiudiValidazione(this.entita);
        }
      }
    }
  }

  avviaIter() {
    this.resetMessage();
    if (!this.letteraAccompagnatoria &&
      (this.myForm.get("attributoEsito").value == '1' || this.myForm.get("attributoEsito").value == '3' || this.myForm.get("attributoEsito").value == '4')) {
      this.showMessageError("È obbligatorio inserire la lettera accompagnatoria!")
    } else if (this.myForm.valid) {
      this.chiamataChiudiValidazione('1');
    }
  }

  getIdModalitaAgevolazione(): Array<number> {
    if (this.modalitaAgevControl?.value.length > 0) {
      if (this.modalitaAgevControl.value.includes(0)) {
        //se ho selezionato "Tutte" ritorno tutti gli id 
        return this.listModalitaAgevolazione.map(m => m.idModalitaAgevolazione);
      }
      //ritorno solo gli id selezionati
      return this.modalitaAgevControl.value;
    }
    return [];
  }

  chiamataChiudiValidazione(entita: string) {
    this.loadedChiudiValidazioneEsito = false;
    console.log(this.statoCheckList);

    this.validazioneService.chiudiValidazioneEsito(

      this.isChiudiValidazione,
      this.letteraAccompagnatoria, this.visibilitaLettera,
      this.checklistInterna, this.visibilitaChecklist,
      this.reportValidazione, this.visibilitaReport,
      this.user.idUtente.toString(),
      this.user.idIride.toString(), this.data.idDichiarazioneDiSpesa,
      this.idProgetto, entita,    //da vedere
      this.statoCheckList, this.checklistHtml,
      this.idChecklist, this.files,

      this.idDichiarazioneDiSpesa, this.idDocumentoIndex,
      this.statoChecklist, this.idBandoLinea,
      this.codiceProgetto, this.dsIntegrativaConsentita,

      this.myForm.get("esitoDichiarazioneSpesa").value,
      this.myForm.get("attributoEsito").value,
      this.myForm.get("importo").value !== null ? this.sharedService.getNumberFromFormattedString(this.myForm.get("importo").value).toString() : "",
      this.myForm.get("ires").value?.toString(),
      this.getIdModalitaAgevolazione(),
      this.myForm.get("premialita").value !== null ? this.sharedService.getNumberFromFormattedString(this.myForm.get("premialita").value) : null,
      this.causaleFinanzControl?.value ? this.causaleFinanzControl.value : null,
      this.causaleContributoControl?.value ? this.causaleContributoControl.value : null
    )

      .subscribe(data => {
        console.log("RISPOSTA CHIUDI VALIDAZIONE SPESA ", data)
        this.loadedChiudiValidazioneEsito = true;
        console.log(this.isLoading);
        if (data.esito == false) {
          console.log(data.msg);
          //     this.messageError = data.msg;
          this.showMessageError(data.msg);
        } else {
          this.dialogRef.close(data);
          console.log(data);

        }
      }), err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di chiusura validazione esito.");
        this.loadedChiudiValidazioneEsito = true;
      }

  }

  filtraListaSelect(listaIndici: string[]) {
    let nuovaLista = []
    this.listAttributoEsito.forEach(element => {
      for (let i = 0; i < listaIndici.length; i++) {
        if (element.id.toString() == listaIndici[i]) {
          nuovaLista.push(element);
        }
      }
    })

    console.log("NUOVA LISTA", nuovaLista);
    return nuovaLista;
  }


  condizioniForm() {
    if (this.myForm.get('attributoEsito').value == 2 || this.myForm.get('attributoEsito').value == 5) {
      this.noAllegati = true;
    } else {
      this.noAllegati = false;
    }

    if (this.isChiudiValidazione === "0") {
      console.log("gestione checklist");

      this.condizioniFormNuovaCheckList()
    }

    else if (this.isChiudiValidazione === "1") {

      console.log("chiudi");
      this.resetMessage();
      this.chiusaUfficio = false;
      console.log(this.chiusaUfficio);

      console.log("this.myForm.get('esitoDichiarazioneSpesa').value", this.myForm.get("esitoDichiarazioneSpesa").value)
      console.log("this.myForm.get('attributoEsito').value", this.myForm.get("attributoEsito").value)
      if (this.myForm.get("esitoDichiarazioneSpesa").value == 3) {

        if (this.myForm.get("attributoEsito").value == 1 || this.myForm.get("attributoEsito").value == 2 || this.myForm.get("attributoEsito").value == 5) {
          this.myForm.get("attributoEsito").setValue('');
        }
        this.listAttributoEsitoDaMostrare = this.filtraListaSelect(['3', '4', '6', '7']);
      }

      else {
        if (this.myForm.get("attributoEsito").value == 6 || this.myForm.get("attributoEsito").value == 7) {
          this.myForm.get("attributoEsito").setValue('');
        }
        this.listAttributoEsitoDaMostrare = this.filtraListaSelect(['1', '2', '3', '4', '5'])
      }

      //condizioni per importo e ires
      if (this.myForm.get("esitoDichiarazioneSpesa").value && this.myForm.get("attributoEsito").value) {
        this.chiusaUfficio = false;
        this.disableConferma = false;
        console.log(this.chiusaUfficio);
        if (this.myForm.get("attributoEsito").value == 7) {
          this.chiusaUfficio = true;
          console.log(this.chiusaUfficio);
        }
        else {
          this.chiusaUfficio = false;
          console.log(this.chiusaUfficio);
        }
        if (this.myForm.get("attributoEsito").value == 1 || this.myForm.get("attributoEsito").value == 3 ||
          this.myForm.get("attributoEsito").value == 6 || this.myForm.get("attributoEsito").value == 4 || this.myForm.get("attributoEsito").value == 7
        ) {
          this.myForm.controls['importo'].setValue(null);
          this.myForm.controls['ires'].setValue("");
          this.myForm.controls['premialita'].setValue(null);
          this.myForm.get("ires").updateValueAndValidity();
          this.myForm.get("importo").disable();
          this.myForm.get("ires").disable();
          this.myForm.get("premialita").disable();
          this.sharedService.removeValidatorOnForm(["importo", "premialita"], this.myForm);
          this.modalitaAgevControl.setValue([]);
          this.modalitaAgevControl.disable();
          this.modalitaAgevControl.clearValidators();
          this.modalitaAgevControl.updateValueAndValidity();
          this.causaleContributoControl.setValue(null);
          this.causaleFinanzControl.setValue(null);
          this.causaleContributoControl.disable();
          this.causaleFinanzControl.disable();
          this.causaleFinanzControl.updateValueAndValidity();

        }

        if ((this.myForm.get("esitoDichiarazioneSpesa").value == 1 || this.myForm.get("esitoDichiarazioneSpesa").value == 2)
          && (this.myForm.get("attributoEsito").value == 2 || this.myForm.get("attributoEsito").value == 5)) {
          this.myForm.get("importo").enable();
          this.myForm.get("ires").enable();
          this.myForm.get("premialita").enable();
          this.sharedService.addValidatorOnForm(["importo", "premialita"], this.myForm, Validators.required);
          this.myForm.get("ires").updateValueAndValidity();
          this.modalitaAgevControl.enable();
          this.modalitaAgevControl.setValidators(Validators.required);
          this.modalitaAgevControl.updateValueAndValidity();
          this.causaleFinanzControl.enable();
          this.causaleFinanzControl.updateValueAndValidity();
          this.causaleContributoControl.enable();
          this.causaleContributoControl.updateValueAndValidity();
        }
      } else {
        this.myForm.controls['importo'].setValue(null);
        this.myForm.controls['ires'].setValue("");
        this.myForm.controls['premialita'].setValue(null);
        this.myForm.get("ires").updateValueAndValidity();
        this.myForm.get("importo").disable();
        this.myForm.get("ires").disable();
        this.myForm.get("premialita").disable();
        this.sharedService.removeValidatorOnForm(["importo", "premialita"], this.myForm);
        this.modalitaAgevControl.setValue([]);
        this.modalitaAgevControl.disable();
        this.modalitaAgevControl.clearValidators();
        this.modalitaAgevControl.updateValueAndValidity();
        this.causaleContributoControl.setValue(null);
        this.causaleFinanzControl.setValue(null);
        this.causaleContributoControl.disable();
        this.causaleFinanzControl.disable();
        this.causaleFinanzControl.updateValueAndValidity();
      }

      if (this.myForm.get("esitoDichiarazioneSpesa").value && this.myForm.get("attributoEsito").value) {
        if (((this.myForm.get("esitoDichiarazioneSpesa").value == 1 && this.myForm.get("attributoEsito").value == 1) ||
          (this.myForm.get("esitoDichiarazioneSpesa").value == 2 && this.myForm.get("attributoEsito").value == 1) ||
          (this.myForm.get("esitoDichiarazioneSpesa").value == 3 && this.myForm.get("attributoEsito").value == 6))) {
          this.showConferma = false;
        }
        else {
          this.showConferma = true
        }

      }

      //condizioni per entità
      if (this.myForm.get("esitoDichiarazioneSpesa").value && this.myForm.get("attributoEsito").value) {

        //proposta erogazione
        if ((this.myForm.get("esitoDichiarazioneSpesa").value == 1 || this.myForm.get("esitoDichiarazioneSpesa").value == 2)
          && this.myForm.get("attributoEsito").value == 2) {
          this.entita = '2';
        }

        //proposta revoca
        if ((this.myForm.get("esitoDichiarazioneSpesa").value == 1 || this.myForm.get("esitoDichiarazioneSpesa").value == 2 || this.myForm.get("esitoDichiarazioneSpesa").value == 3)
          && (this.myForm.get("attributoEsito").value == 3 || this.myForm.get("attributoEsito").value == 4)) {
          this.entita = '3';
        }

        //proposta revoca ed erogazione
        if ((this.myForm.get("esitoDichiarazioneSpesa").value == 1 || this.myForm.get("esitoDichiarazioneSpesa").value == 2)
          && this.myForm.get("attributoEsito").value == 5) {
          this.entita = '4';
        }
      }
    }
  }

  condizioniFormNuovaCheckList() {
    this.resetMessage();
    console.log("this.myForm.get('esitoDichiarazioneSpesa').value", this.myForm.get("esitoDichiarazioneSpesa").value)
    console.log("this.myForm.get('attributoEsito').value", this.myForm.get("attributoEsito").value)

    //restrizioni select in funzione delle scelte effettuare
    //se esito == negativo allora attributi possibile sono senza revoca / revoca parziale / revoca totale e basta
    if (this.myForm.get("esitoDichiarazioneSpesa").value == 3) {

      //se la combinazione non è realizzabile si resettano i valori
      if (this.myForm.get("attributoEsito").value == 1 || this.myForm.get("attributoEsito").value == 2 || this.myForm.get("attributoEsito").value == 5) {
        this.myForm.get("attributoEsito").setValue('');
      }

      this.listAttributoEsitoDaMostrare = this.filtraListaSelect(['3', '4', '6']);
      console.log("this.listAttributoEsitoDaMostrare", this.listAttributoEsitoDaMostrare)

    } else {

      //se la combinazione non è realizzabile si resettano i valori
      if (this.myForm.get("attributoEsito").value == 6) {
        this.myForm.get("attributoEsito").setValue('');
      }

      this.listAttributoEsitoDaMostrare = this.filtraListaSelect(['1', '2', '3', '4', '5'])
      console.log("this.listAttributoEsitoDaMostrare", this.listAttributoEsitoDaMostrare)
    }

    //condizioni per importo e ires
    if (this.myForm.get("esitoDichiarazioneSpesa").value && this.myForm.get("attributoEsito").value) {

      //abilito pulsante conferma
      this.disableConferma = false;

      //ESAURIENTE (1) o ESAURIENTE CON AMMESSO INFERIORE (2) o NEGATIVO (3)
      //SENZA EROGAZIONE (1) o REVOCA PARZIALE (3) o REVOCA TOTALE (4)

      // SENZA EROGAZIONE REVOCA PARZIALE REVOCA TOTALE SENZA REVOCA
      if (
        (
          this.myForm.get("attributoEsito").value == 1 ||
          this.myForm.get("attributoEsito").value == 3 ||
          this.myForm.get("attributoEsito").value == 6 ||
          this.myForm.get("attributoEsito").value == 4
        )
      ) {
        console.log(this.myForm.get("attributoEsito").value);
        this.myForm.controls['importo'].setValue(null);
        this.myForm.controls['ires'].setValue("");
        this.myForm.controls['premialita'].setValue(null);
        this.myForm.get("importo").updateValueAndValidity();
        this.myForm.get("ires").updateValueAndValidity();
        this.sharedService.removeValidatorOnForm(["importo", "premialita"], this.myForm);
        //disabilita pulsante importo e ires
        this.myForm.get("importo").disable();
        this.myForm.get("ires").disable();
        this.myForm.get("premialita").disable();
        this.modalitaAgevControl.setValue([]);
        this.modalitaAgevControl.disable();
        this.modalitaAgevControl.clearValidators();
        this.modalitaAgevControl.updateValueAndValidity();
        this.causaleContributoControl.setValue(null);
        this.causaleFinanzControl.setValue(null);
        this.causaleContributoControl.disable();
        this.causaleFinanzControl.disable();
        this.causaleFinanzControl.updateValueAndValidity();



      }

      //ESAURIENTE (1) e ESAURIENTE CON AMMESSO INFERIORE (2)
      //CON EROGAZIONE (2) o EROGAZIONE E REV. PARZIALE (5)
      if (
        (
          this.myForm.get("esitoDichiarazioneSpesa").value == 1 ||
          this.myForm.get("esitoDichiarazioneSpesa").value == 2
        ) &&
        (
          this.myForm.get("attributoEsito").value == 2 ||
          this.myForm.get("attributoEsito").value == 5
        )
      ) {

        //abilita pulsanti importo e ires
        this.myForm.get("importo").enable();
        this.myForm.get("ires").enable();
        this.myForm.get("premialita").enable();
        this.sharedService.addValidatorOnForm(["importo", "premialita"], this.myForm, Validators.required);
        this.myForm.get("ires").updateValueAndValidity();
        this.modalitaAgevControl.enable();
        this.modalitaAgevControl.setValidators(Validators.required);
        this.modalitaAgevControl.updateValueAndValidity();
      } this.causaleFinanzControl.enable();
      this.causaleFinanzControl.updateValueAndValidity();
      this.causaleContributoControl.enable();
      this.causaleContributoControl.updateValueAndValidity();

    }

    //condizioni per pulsanti
    if (this.myForm.get("esitoDichiarazioneSpesa").value && this.myForm.get("attributoEsito").value) {

      //ESAURIENTE (1) e SENZA EROGAZIONE (1)
      //ESAURIENTE CON AMMESSO (2) e SENZA EROGAZIONE (1)
      //NEGATIVO (3) e SENZA REVOCA (6)


      if (
        (
          (this.myForm.get("esitoDichiarazioneSpesa").value == 1 && this.myForm.get("attributoEsito").value == 1) ||

          (this.myForm.get("esitoDichiarazioneSpesa").value == 2 && this.myForm.get("attributoEsito").value == 1) ||

          (this.myForm.get("esitoDichiarazioneSpesa").value == 3 && this.myForm.get("attributoEsito").value == 6)
        )) {
        // this.disableAvviaIter = false;
        // this.disableConferma = true;
        this.showConferma = false;
      } else {
        // this.disableAvviaIter = true;
        // this.disableConferma = false;
        this.showConferma = true
      }

    }

    //condizioni per entità
    if (this.myForm.get("esitoDichiarazioneSpesa").value && this.myForm.get("attributoEsito").value) {

      //proposta erogazione
      if ((this.myForm.get("esitoDichiarazioneSpesa").value == 1 || this.myForm.get("esitoDichiarazioneSpesa").value == 2)
        && this.myForm.get("attributoEsito").value == 2) {
        this.entita = '2';
      }

      //proposta revoca
      if ((this.myForm.get("esitoDichiarazioneSpesa").value == 1 || this.myForm.get("esitoDichiarazioneSpesa").value == 2 || this.myForm.get("esitoDichiarazioneSpesa").value == 3)
        && (this.myForm.get("attributoEsito").value == 3 || this.myForm.get("attributoEsito").value == 4)) {
        this.entita = '3';
      }

      //proposta revoca ed erogazione
      if ((this.myForm.get("esitoDichiarazioneSpesa").value == 1 || this.myForm.get("esitoDichiarazioneSpesa").value == 2)
        && this.myForm.get("attributoEsito").value == 5) {
        this.entita = '4';
      }
    }

  }

  close() {
    this.resetMessage();
    this.dialogRef.close({ esito: "N", files: [] });
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

    console.log("CHECKLIST LETTERA ACCOMPAGNATORIA", this.letteraAccompagnatoria);

  }
  eliminaLetteraAccompagnatoria() {
    this.resetMessage();
    this.letteraAccompagnatoria = null;

    console.log(this.myLetteraAccompagnatoria.nativeElement.files);
    this.myLetteraAccompagnatoria.nativeElement.value = "";
    console.log(this.myLetteraAccompagnatoria.nativeElement.files);
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

  showMessageError(msg: string) {
    this.resetMessage();
    this.messageError = msg;
    this.error = true;
    //const element = document.querySelector('#scrollId');
    //element.scrollIntoView();
  }
  resetMessage() {
    this.messageError = null;
    this.error = false;
    // this.success = false;
  }

  isLoading() {
    if (!this.loadedBR55 || !this.loadedBR80 || !this.loadedChiudiValidazioneUfficio
      || !this.loadedChiudiValidazioneEsito || !this.loadedDropDown) {
      return true;
    }
    return false;
  }
}
