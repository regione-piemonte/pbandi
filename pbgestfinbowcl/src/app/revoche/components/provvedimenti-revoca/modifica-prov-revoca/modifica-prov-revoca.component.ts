/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { ArchivioFileService, Constants, HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { Location } from '@angular/common';
import { ProvvedimentiRevocaResponseService } from 'src/app/revoche/services/provvedimenti-revoca-response.service';
import { ProvvedimentoRevocaVO } from 'src/app/revoche/commons/provvedimenti-revoca-dto/provvedimento-revoca-vo';
import { DocumentoRevocaVO } from 'src/app/revoche/commons/procedimenti-revoca-dto/documento-revoca-vo';
import { SuggestIdDescVO } from 'src/app/revoche/commons/suggest-id-desc-vo';
import { ConfigService } from 'src/app/core/services/config.service';
import { saveAs } from 'file-saver-es';
import { EmissioneProvvedimentoDialogComponent } from '../emissione-provvedimento-dialog/emissione-provvedimento-dialog.component';
import { RitiroInAutotutelaDialogComponent } from '../ritiro-in-autotutela-dialog/ritiro-in-autotutela-dialog.component';
import { ConfermaProvvedimentoDialogComponent } from '../conferma-provvedimento-dialog/conferma-provvedimento-dialog.component';
import { SharedService } from 'src/app/shared/services/shared.service';
import { DialogPianoAmmortamento } from 'src/app/gestione-crediti/components/dialog-piano-ammortamento/dialog-piano-ammortamento.component';
import { DialogEstrattoConto } from 'src/app/gestione-crediti/components/dialog-estratto-conto/dialog-estratto-conto.component';

@Component({
  selector: 'app-modifica-prov-revoca',
  templateUrl: './modifica-prov-revoca.component.html',
  styleUrls: ['./modifica-prov-revoca.component.scss']
})
export class ModificaProvRevocaComponent implements OnInit {

  //Principali oggetti
  numeroProvvedimentoRevoca: number;
  user: UserInfoSec;
  provvedimentoRevoca: ProvvedimentoRevocaVO;
  listaModalitaRecupero: SuggestIdDescVO[] = [];
  listaMotivi: SuggestIdDescVO[] = [];
  listaDocumenti: DocumentoRevocaVO[] = [];

  //Variabili caricamento informazioni
  isLoading: boolean = false;
  userCaricato: boolean = false;
  provvedimentoRevocaCaricato: boolean = false;
  documentiCaricato: boolean = false;
  listaModalitaRecuperoCaricato: boolean = false;
  listaMotiviCaricato: boolean = false;

  //Form
  myForm: FormGroup;

  //gestione messaggi errore/successo
  error: boolean = false;
  success: boolean = false;
  messageError: string;
  messageSuccess: string;
  mostraErrori: boolean = true;

  //Tabella
  dataSource = new MatTableDataSource<any>([]);
  displayedColumns: string[] = ["modalita", "impAmmesso", "impConcesso", "impRevocato", "impErogato", "impRecuperato", "impRimborsato", "impConcessoAlRevocato", "impErogatoAlRecuperato", "ImpTotRevoca", "revocaSenzaRec", "revocaConRec", "revocaPreRec", "interessi", "actions"];

  //TextBox
  notaSolaLettura = true;
  noteBackend;

  //Pulsanti finali
  soloLettura: boolean = true;
  showElimina: boolean = false;
  showEmettiProvvedimentoRevoca: boolean = false;
  showRitiraInAutolettura: boolean = false;
  showConfermaProvvedimento: boolean = false;

  constructor(
    private router: Router,
    public sharedService: SharedService,
    private location: Location,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private userService: UserService,
    private provvedimentiRevocaService: ProvvedimentiRevocaResponseService,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService
  ) { }

  ngOnInit(): void {

    //recupero idProvvedimento precedentemente selezionato
    this.route.queryParams.subscribe(params => {
      this.numeroProvvedimentoRevoca = params['numeroProvvedimentoRevoca']
    });

    //creo il form: TO-DO AGGIORNARE CON TUTTI I CAMPI NUOVI
    this.myForm = this.fb.group({
      //numeroDiProtocollo: ['', Validators.required],
      numeroCovar: ['',[]],
      dataNotifica: ['', Validators.required],
      giorniScadenza: ['', Validators.required],
      determina: ['', Validators.required],
      dataDetermina: ['', Validators.required],
      estremiDetermina: ['', Validators.required],
      revoca: ['', Validators.required],
      modalitaRecupero: ['', []],
      motivoProvvedimentoRevoca: ['', []],

      // contributo: ['', Validators.required],
      // finanziamento: ['', Validators.required],
      contributoRevoca: ['', []],
      contributoMinorSpese: ['', []],
      contributoDecurtazioni: ['', []],

      finanziamentoRevoca: ['', []],
      finanziamentoMinorSpese: ['', []],
      finanziamentoDecurtazioni: ['', []],

      garanziaRevoca: ['', []],
      garanziaMinorSpese: ['', []],
      garanziaDecurtazioni: ['', []],

      note: ['', []],

      //importi
      importoSenzaRecuperoContributo: ['', []],
      importoConRecuperoContributo: ['', []],
      interessiContributo: ['', []],
      importoSenzaRecuperoFinanziamento: ['', []],
      importoConRecuperoFinanziamento: ['', []],
      importoPreRecuperoFinanziamento: ['', []],
      interessiFinanziamento: ['', []],
      importoSenzaRecuperoGaranzia: ['', []],
      importoConRecuperoGaranzia: ['', []],
      importoPreRecuperoGaranzia: ['', []],
      interessiGaranzia: ['', []],
    });

    //recupero tutti i dati che mi servono dal BE
    this.caricamentoDati();

  }

  setValueCheckbox() {
    this.myForm.get('contributoRevoca').setValue(this.provvedimentoRevoca.flagContribRevoca);
    this.myForm.get('contributoMinorSpese').setValue(this.provvedimentoRevoca.flagContribMinorSpese);
    this.myForm.get('contributoDecurtazioni').setValue(this.provvedimentoRevoca.flagContribDecurtaz);

    this.myForm.get('finanziamentoRevoca').setValue(this.provvedimentoRevoca.flagFinanzRevoca);
    this.myForm.get('finanziamentoMinorSpese').setValue(this.provvedimentoRevoca.flagFinanzMinorSpese);
    this.myForm.get('finanziamentoDecurtazioni').setValue(this.provvedimentoRevoca.flagFinanzDecurtaz);

    this.myForm.get('garanziaRevoca').setValue(this.provvedimentoRevoca.flagFinanzRevoca);
    this.myForm.get('garanziaMinorSpese').setValue(this.provvedimentoRevoca.flagFinanzMinorSpese);
    this.myForm.get('garanziaDecurtazioni').setValue(this.provvedimentoRevoca.flagFinanzDecurtaz);
  }

  caricamentoDati() {

    //attivo rotella
    this.isLoading = true;

    //recupero dati utente
    this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
      }
      this.userCaricato = true;
      this.caricamentoTerminato();
    }, err => {
      this.userCaricato = true;
      this.caricamentoTerminato();
      this.showMessageError(err);
    });

    //recupero il dettaglio del provvedimento
    this.provvedimentiRevocaService.getDettaglioProvvedimentoRevoca(this.numeroProvvedimentoRevoca.toString()).subscribe(data => {
      console.log("DATA GET DETTAGLIO PROVVEDIMENTO REVOCA", data)
      if (data) {
        this.provvedimentoRevoca = data;

        //seleziono o meno le checkbox
        this.setValueCheckbox();

        //creo dataSource per i dati da mostrare nella tabella
        this.createDatasource();

        //popolo tutti i campi del form con i parametri in arrivo dal BE
        this.setForm();

        //mostro o meno i pulsanti in fondo alla pagina
        this.condizioniAttivazioniPulsanti();

        this.provvedimentoRevocaCaricato = true;
        this.caricamentoTerminato();
      }

      this.provvedimentoRevocaCaricato = true;
      this.caricamentoTerminato();

    }, err => {
      this.provvedimentoRevocaCaricato = true;
      this.caricamentoTerminato();
      this.showMessageError(err);
    });

    //recupero la lista dei documenti
    this.provvedimentiRevocaService.getDocumentiProvvedimentoRevoca(this.numeroProvvedimentoRevoca.toString()).subscribe(data => {
      console.log("GET DOCUMENTI", data)
      if (data) {
        this.listaDocumenti = data;

        this.documentiCaricato = true;
        this.caricamentoTerminato();
      }

      this.documentiCaricato = true;
      this.caricamentoTerminato();
    }, err => {
      this.documentiCaricato = true;
      this.caricamentoTerminato();
      this.showMessageError(err);
    });

    //recupero la lista dei documenti
    this.provvedimentiRevocaService.getModalitaRecupero(this.numeroProvvedimentoRevoca.toString()).subscribe(data => {
      console.log("LISTA MODALITA' RECUPERO", data)
      if (data) {
        this.listaModalitaRecupero = data;

        this.listaModalitaRecuperoCaricato = true;
        this.caricamentoTerminato();
      }

      this.listaModalitaRecuperoCaricato = true;
      this.caricamentoTerminato();
    }, err => {
      this.listaModalitaRecuperoCaricato = true;
      this.caricamentoTerminato();
      this.showMessageError(err);
    });

    //recupero la lista dei documenti
    this.provvedimentiRevocaService.getMotivoRevoca(this.numeroProvvedimentoRevoca.toString()).subscribe(data => {
      console.log("LISTA MOTIVI REVOCA", data)
      if (data) {

        this.listaMotivi = data;
        
        //motivo vuoto
        let motivoVuoto = new SuggestIdDescVO();
        motivoVuoto.id = null;
        motivoVuoto.desc = " - ";
        this.listaMotivi.unshift(motivoVuoto);

        this.listaMotiviCaricato = true;
        this.caricamentoTerminato();
      }

      this.listaMotiviCaricato = true;
      this.caricamentoTerminato();
    }, err => {
      this.listaMotiviCaricato = true;
      this.caricamentoTerminato();
      this.showMessageError(err);
    });
  }

  caricamentoTerminato() {
    //Se è stato caricato tutto, cioè tutte le variabili booleane del caricamento sono true, allora isLoading diventa false
    if (
      this.userCaricato &&
      this.provvedimentoRevocaCaricato &&
      this.documentiCaricato &&
      this.listaModalitaRecuperoCaricato &&
      this.listaMotiviCaricato
    ) {
      this.isLoading = false;
      this.condizioniModificaCampi();
      this.soloLettura = false;
    }

  }

  //metodo che crea il datasource: quello da mostrare nella tabella
  createDatasource() {

    let listaElementiDatasource = [];

    //CONTRIBUTO
    if (this.provvedimentoRevoca.importoConcessoContributo != null) {
      let contributo = {
        idModalita: this.provvedimentoRevoca.idModAgevContrib,
        idModalitaRif: this.provvedimentoRevoca.idModAgevContribRif,
        modalita: this.provvedimentoRevoca.modAgevContrib,
        importoAmmessoIniziale: this.provvedimentoRevoca.importoAmmessoContributo,
        importoConcesso: this.provvedimentoRevoca.importoConcessoContributo,
        importoRevocato: this.provvedimentoRevoca.importoRevocatoContributo,
        importoErogato: this.provvedimentoRevoca.importoErogatoContributo,
        importoRecuperato: this.provvedimentoRevoca.importoRecuperatoContributo,

        importoRimborsato: null,

        impConcAlNettoRevocato: this.provvedimentoRevoca.impConcAlNettoRevocatoContributo,
        impErogAlNettoRecuERimb: this.provvedimentoRevoca.impErogAlNettoRecuERimbContributo,

        revocaSenzaRecupero: this.provvedimentoRevoca.impContribRevocaNoRecu,
        revocaConRecupero: this.provvedimentoRevoca.impContribRevocaRecu,
        importoTotaleRevoca: this.provvedimentoRevoca.impContribRevocaRecu + this.provvedimentoRevoca.impContribRevocaNoRecu,

        interessi: this.provvedimentoRevoca.impContribInteressi,

      }

      //setto le variabili del form
      this.setImporto("importoSenzaRecuperoContributo", this.provvedimentoRevoca?.impContribRevocaNoRecu?.toString(), undefined);
      this.setImporto("importoConRecuperoContributo", this.provvedimentoRevoca?.impContribRevocaRecu?.toString(), undefined);
      this.setImporto("interessiContributo", this.provvedimentoRevoca?.impContribInteressi?.toString(), undefined);

      //console.log("contributo", contributo);

      listaElementiDatasource.push(contributo);

      //console.log(this.dataSource.data);
    }

    //FINANZIAMENTO
    if (this.provvedimentoRevoca.importoConcessoFinanziamento != null) {
      let finanziamento = {
        idModalita: this.provvedimentoRevoca.idModAgevFinanz,
        idModalitaRif: this.provvedimentoRevoca.idModAgevFinanzRif,
        modalita: this.provvedimentoRevoca.modAgevFinanz,
        importoAmmessoIniziale: this.provvedimentoRevoca.importoAmmessoFinanziamento,
        importoConcesso: this.provvedimentoRevoca.importoConcessoFinanziamento,
        importoRevocato: this.provvedimentoRevoca.importoRevocatoFinanziamento,
        importoErogato: this.provvedimentoRevoca.importoErogatoFinanziamento,
        importoRecuperato: this.provvedimentoRevoca.importoRecuperatoFinanziamento,

        importoRimborsato: this.provvedimentoRevoca.importoRimborsatoFinanziamento,   //??

        impConcAlNettoRevocato: this.provvedimentoRevoca.impConcAlNettoRevocatoFinanziamento,
        impErogAlNettoRecuERimb: this.provvedimentoRevoca.impErogAlNettoRecuERimbFinanziamento,

        revocaSenzaRecupero: this.provvedimentoRevoca.impFinanzRevocaNoRecu,
        revocaConRecupero: this.provvedimentoRevoca.impFinanzRevocaRecu,
        revocaPreRecupero: this.provvedimentoRevoca.impFinanzPreRecu,
        importoTotaleRevoca: this.provvedimentoRevoca.impFinanzPreRecu + this.provvedimentoRevoca.impFinanzRevocaRecu + this.provvedimentoRevoca.impFinanzRevocaNoRecu,

        interessi: this.provvedimentoRevoca.impFinanzInteressi,
      }

      this.setImporto("importoSenzaRecuperoFinanziamento", this.provvedimentoRevoca?.impFinanzRevocaNoRecu?.toString(), undefined);
      this.setImporto("importoConRecuperoFinanziamento", this.provvedimentoRevoca?.impFinanzRevocaRecu?.toString(), undefined);
      this.setImporto("importoPreRecuperoFinanziamento", this.provvedimentoRevoca?.impFinanzPreRecu?.toString(), undefined);
      this.setImporto("interessiFinanziamento", this.provvedimentoRevoca?.impFinanzInteressi?.toString(), undefined);

      listaElementiDatasource.push(finanziamento);
    }

    //GARANZIA
    if (this.provvedimentoRevoca.importoConcessoGaranzia != null) {
      let garanzia = {
        idModalita: this.provvedimentoRevoca.idModAgevGaranz,
        idModalitaRif: this.provvedimentoRevoca.idModAgevGaranzRif,
        modalita: this.provvedimentoRevoca.modAgevGaranz,
        importoAmmessoIniziale: this.provvedimentoRevoca.importoAmmessoGaranzia,
        importoConcesso: this.provvedimentoRevoca.importoConcessoGaranzia,
        importoRevocato: this.provvedimentoRevoca.importoRevocatoGaranzia,
        importoErogato: this.provvedimentoRevoca.importoErogatoGaranzia,
        importoRecuperato: this.provvedimentoRevoca.importoRecuperatoGaranzia,

        importoRimborsato: null,

        impConcAlNettoRevocato: this.provvedimentoRevoca.impConcAlNettoRevocatoGaranzia,
        impErogAlNettoRecuERimb: this.provvedimentoRevoca.impErogAlNettoRecuERimbGaranzia,

        revocaSenzaRecupero: this.provvedimentoRevoca.impGaranziaRevocaNoRecu,
        revocaConRecupero: this.provvedimentoRevoca.impGaranziaRevocaRecu,
        revocaPreRecupero: this.provvedimentoRevoca.impGaranzPreRecu,
        importoTotaleRevoca: this.provvedimentoRevoca.impGaranzPreRecu + this.provvedimentoRevoca.impGaranziaRevocaRecu + this.provvedimentoRevoca.impGaranziaRevocaNoRecu,

        interessi: this.provvedimentoRevoca.impGaranziaInteressi,
      }

      this.setImporto("importoSenzaRecuperoGaranzia", this.provvedimentoRevoca?.impGaranziaRevocaNoRecu?.toString(), undefined);
      this.setImporto("importoConRecuperoGaranzia", this.provvedimentoRevoca?.impGaranziaRevocaRecu?.toString(), undefined);
      this.setImporto("importoPreRecuperoGaranzia", this.provvedimentoRevoca?.impGaranzPreRecu?.toString(), undefined);
      this.setImporto("interessiGaranzia", this.provvedimentoRevoca?.impGaranziaInteressi?.toString(), undefined);

      listaElementiDatasource.push(garanzia);

    }

    this.dataSource = new MatTableDataSource(listaElementiDatasource);
  }

  setImporto(formControlName: string, value : string, max : number) {
    if(value?.includes(',')){
      value = this.sharedService.getNumberFromFormattedString(value).toString();
    }
    console.log("Set " + formControlName + " value " + this.sharedService.formatValue(value));
    if(!max || this.sharedService.getNumberFromFormattedString(this.sharedService.formatValue(value)) <= max){
      this.myForm.get(formControlName).setValue(this.sharedService.formatValue(value));
    }else {
      this.myForm.get(formControlName).setValue(this.sharedService.formatValue("0"));
    }
  }

  getImporto(formControlName: string) : number {
    return this.sharedService.getNumberFromFormattedString(this.myForm.get(formControlName).value?.toString());
  }

  setForm() {
    this.myForm.get("dataNotifica").setValue(this.provvedimentoRevoca?.dataNotifica);
    this.myForm.get("giorniScadenza").setValue(this.provvedimentoRevoca?.giorniScadenza);
    this.myForm.get("determina").setValue(this.provvedimentoRevoca?.flagDetermina ? "S" : "N");
    this.myForm.get("dataDetermina").setValue(this.provvedimentoRevoca?.dtDetermina);
    this.myForm.get("numeroCovar").setValue(this.provvedimentoRevoca?.numeroCovar);
    this.myForm.get("estremiDetermina").setValue(this.provvedimentoRevoca?.estremi);
    this.myForm.get("revoca").setValue(this.provvedimentoRevoca?.flagOrdineRecupero ? "S" : "N");

    this.myForm.get("modalitaRecupero").setValue(this.provvedimentoRevoca?.idMancatoRecupero);            //TO DO
    this.myForm.get("motivoProvvedimentoRevoca").setValue(this.provvedimentoRevoca?.idMotivoRevoca);   //TO DO

    // this.myForm.get("contributo").setValue(this.provvedimentoRevoca?.dtDetermina);                  //TO DO
    // this.myForm.get("finanziamento").setValue(this.provvedimentoRevoca?.dtDetermina);               //TO DO

    this.myForm.get("note").setValue(this.provvedimentoRevoca?.note);

    this.myForm.disable();
  }

  changeCheckbox(name: string, event: any) {
    console.log("EVENT", event);
    switch (name) {
      case "contributoRevoca":
        this.provvedimentoRevoca.flagContribRevoca = event.checked;
        break;
      case "contributoMinorSpese":
        this.provvedimentoRevoca.flagContribMinorSpese = event.checked;
        break;
      case "contributoDecurtazioni":
        this.provvedimentoRevoca.flagContribDecurtaz = event.checked;
        break;

      case "finanziamentoRevoca":
        this.provvedimentoRevoca.flagFinanzRevoca = event.checked;
        break;
      case "finanziamentoMinorSpese":
        this.provvedimentoRevoca.flagFinanzMinorSpese = event.checked;
        break;
      case "finanziamentoDecurtazioni":
        this.provvedimentoRevoca.flagFinanzDecurtaz = event.checked;
        break;

      case "garanziaRevoca":
        this.provvedimentoRevoca.flagGaranziaRevoca = event.checked;
        break;
      case "garanziaMinorSpese":
        this.provvedimentoRevoca.flagGaranziaMinorSpese = event.checked;
        break;
      case "garanziaDecurtazioni":
        this.provvedimentoRevoca.flagGaranziaDecurtaz = event.checked;
        break;
    }
  }

  setRequiredDetermina(esito: boolean){
    if (this.provvedimentoRevoca?.idStatoRevoca == 5 && (this.provvedimentoRevoca?.idAttivitaRevoca == 0 || this.provvedimentoRevoca?.idAttivitaRevoca == null || this.provvedimentoRevoca?.idAttivitaRevoca == undefined)) {
      if(esito) {
        this.myForm.get('dataDetermina').enable();
        this.myForm.get('estremiDetermina').enable();
        this.myForm.updateValueAndValidity();
      } else {
        this.myForm.get('dataDetermina').disable();
        this.myForm.get('estremiDetermina').disable();
        this.myForm.get('dataDetermina').setValue(undefined);
        this.myForm.get('estremiDetermina').setValue(undefined);
        this.myForm.updateValueAndValidity();
      }
    }
  }

  salva() {
    console.log("FORM", this.myForm);

    if(!this.myForm.valid){
      return;
    }

    //creo parametro e copio il provvedimento revoca
    let obj: ProvvedimentoRevocaVO = Object.assign({}, this.provvedimentoRevoca);

    //riassegno i valori del form
    obj.dataNotifica = this.myForm.get("dataNotifica").value;
    obj.giorniScadenza = this.myForm.get("giorniScadenza").value;
    obj.flagDetermina = this.myForm.get("determina").value == "S" ? true : false;
    obj.dtDetermina = this.myForm.get("dataDetermina").value;
    obj.estremi = this.myForm.get("estremiDetermina").value;
    obj.numeroCovar = (this.myForm.get("numeroCovar")?.value);
    obj.flagOrdineRecupero = this.myForm.get("revoca").value == "S" ? true : false;

    //modalità recupero
    obj.idMancatoRecupero = this.myForm.get("modalitaRecupero").value;
    let el1: SuggestIdDescVO;
    this.listaModalitaRecupero.forEach(element => {
      if (element?.id == obj?.idMancatoRecupero?.toString()) {
        el1 = element;
      }
    });
    obj.descMancatoRecupero = el1?.desc;

    //motivo
    obj.idMotivoRevoca = this.myForm.get("motivoProvvedimentoRevoca").value;
    let el2: SuggestIdDescVO;
    this.listaModalitaRecupero.forEach(element => {
      if (element?.id == obj?.idMancatoRecupero?.toString()) {
        el2 = element;
      }
    });
    obj.descMotivoRevoca = el2?.desc;

    //importi
    obj.impContribRevocaNoRecu = this.getImporto("importoSenzaRecuperoContributo");
    obj.impContribRevocaRecu = this.getImporto("importoConRecuperoContributo");
    obj.impContribInteressi = this.getImporto("interessiContributo");

    obj.impFinanzRevocaNoRecu = this.getImporto("importoSenzaRecuperoFinanziamento");
    obj.impFinanzRevocaRecu = this.getImporto("importoConRecuperoFinanziamento");
    obj.impFinanzPreRecu = this.getImporto("importoPreRecuperoFinanziamento");
    obj.impFinanzInteressi = this.getImporto("interessiFinanziamento");

    obj.impGaranziaRevocaNoRecu = this.getImporto("importoSenzaRecuperoGaranzia");
    obj.impGaranziaRevocaRecu = this.getImporto("importoConRecuperoGaranzia");
    obj.impGaranzPreRecu = this.getImporto("importoPreRecuperoGaranzia");
    obj.impGaranziaInteressi = this.getImporto("interessiGaranzia");
    
    //note
    obj.note = this.myForm.get("note").value;

    console.log("OBJ", obj);

    this.provvedimentiRevocaService.modificaProvvedimentoRevoca(this.numeroProvvedimentoRevoca, obj).subscribe(data => {
      console.log(data);
      if (data.code == 'OK') {

        //faccio refresh pagina
        this.caricamentoDati();

        this.success = true;
        this.showMessageSuccess("Salvataggio terminato con successo!")
        this.setForm();

        //nascondo SALVA e mostro MODIFICA
        //this.soloLettura = true;

      } else {
        this.showMessageError(data.message);
      }
    })

    //popolo form con i valori
    // this.setForm();
    //nascondo SALVA e mostro MODIFICA
  }

  /*
  modifica() {
    //abilito solo alcuni campi del form
    this.condizioniModificaCampi();

    //nascondo MODIFICA e mostro SALVA
    this.soloLettura = false;
  }
  */

  ////////////////////////////////////////////////////////////////////////////////////////////
  /***************************************************************************
    Condizioni MODIFICA CAMPI FORM: vedi CDU14-V02 tabella 5.1.1
    le condizioni sono tutte legate allo STATO PROCEDIMENTO e all'ATTIVITA'
  ***************************************************************************/
  condizioniModificaCampi() {

    //BOZZA
    if (this.provvedimentoRevoca?.idStatoRevoca == 5 && (this.provvedimentoRevoca?.idAttivitaRevoca == 0 || this.provvedimentoRevoca?.idAttivitaRevoca == null || this.provvedimentoRevoca?.idAttivitaRevoca == undefined)) {
      this.myForm.get("determina").enable();
      this.setRequiredDetermina(this.provvedimentoRevoca?.flagDetermina);
      this.myForm.get("revoca").enable();
      this.myForm.get("modalitaRecupero").enable();
      this.myForm.get("motivoProvvedimentoRevoca").enable();

      //tutte le checkbox
      this.myForm.get("contributoRevoca").enable();
      this.myForm.get("contributoMinorSpese").enable();
      this.myForm.get("contributoDecurtazioni").enable();
      this.myForm.get("finanziamentoRevoca").enable();
      this.myForm.get("finanziamentoMinorSpese").enable();
      this.myForm.get("finanziamentoDecurtazioni").enable();
      this.myForm.get("garanziaRevoca").enable();
      this.myForm.get("garanziaMinorSpese").enable();
      this.myForm.get("garanziaDecurtazioni").enable();

      //importi
      this.myForm.get("importoSenzaRecuperoContributo").enable();
      this.myForm.get("importoConRecuperoContributo").enable();
      this.myForm.get("interessiContributo").enable();
      this.myForm.get("importoSenzaRecuperoFinanziamento").enable();
      this.myForm.get("importoConRecuperoFinanziamento").enable();
      this.myForm.get("importoPreRecuperoFinanziamento").enable();
      this.myForm.get("interessiFinanziamento").enable();
      this.myForm.get("importoSenzaRecuperoGaranzia").enable();
      this.myForm.get("importoConRecuperoGaranzia").enable();
      this.myForm.get("importoPreRecuperoGaranzia").enable();
      this.myForm.get("interessiGaranzia").enable();

      this.myForm.get("note").enable();
    }

    if (
      //BOZZA e AVVIATO ITER AUTORIZZATIVO PROVVEDIMENTO
      (this.provvedimentoRevoca?.idStatoRevoca == 5 && this.provvedimentoRevoca?.idAttivitaRevoca == 4) ||

      //EMESSO PROVVEDIMENTO DI REVOCA e RICEVUTA CONTESTAZIONE
      (this.provvedimentoRevoca?.idStatoRevoca == 8 && this.provvedimentoRevoca?.idAttivitaRevoca == 12) ||

      //EMESSO PROVVEDIMENTO DI REVOCA e AVVIATO ITER AUTORIZZATIVO RITIRO IN AUTOTUTELA
      (this.provvedimentoRevoca?.idStatoRevoca == 8 && this.provvedimentoRevoca?.idAttivitaRevoca == 5) ||

      //EMESSO PROVVEDIMENTO DI REVOCA e AVVIATO ITER AUTORIZZATIVO CONFERMA PROVVEDIMENTO
      (this.provvedimentoRevoca?.idStatoRevoca == 8 && this.provvedimentoRevoca?.idAttivitaRevoca == 13) ||

      //EMESSO PROVVEDIMENTO DI REVOCA e TERMINE SCADUTO
      (this.provvedimentoRevoca?.idStatoRevoca == 8 && this.provvedimentoRevoca?.idAttivitaRevoca == 11) ||

      //RITIRATO IN AUTOTUTELA
      (this.provvedimentoRevoca?.idStatoRevoca == 9 && (this.provvedimentoRevoca?.idAttivitaRevoca == 1 || this.provvedimentoRevoca?.idAttivitaRevoca == 0 || this.provvedimentoRevoca?.idAttivitaRevoca == null || this.provvedimentoRevoca?.idAttivitaRevoca == undefined)) ||

      //PROVVEDIMENTO CONFERMATO
      (this.provvedimentoRevoca?.idStatoRevoca == 10 && (this.provvedimentoRevoca?.idAttivitaRevoca == 0 || this.provvedimentoRevoca?.idAttivitaRevoca == null || this.provvedimentoRevoca?.idAttivitaRevoca == undefined)) ||

      //PROVVEDIMENTO CONFERMATO e RICHIESTO ACCESSO AGLI ATTI
      (this.provvedimentoRevoca?.idStatoRevoca == 10 && this.provvedimentoRevoca?.idAttivitaRevoca == 14)

    ) {
      this.myForm.get("modalitaRecupero").enable();
      this.myForm.get("motivoProvvedimentoRevoca").enable();
      this.myForm.get("note").enable();
    }

    //EMESSO PROVVEDIMENTO DI REVOCA
    if (this.provvedimentoRevoca?.idStatoRevoca == 8 && (this.provvedimentoRevoca?.idAttivitaRevoca == 0 || this.provvedimentoRevoca?.idAttivitaRevoca == null || this.provvedimentoRevoca?.idAttivitaRevoca == undefined)) {
      this.myForm.get("dataNotifica").enable();
      this.myForm.get("modalitaRecupero").enable();
      this.myForm.get("motivoProvvedimentoRevoca").enable();
      this.myForm.get("note").enable();
    }

    if((this.provvedimentoRevoca?.idStatoRevoca == 5 && (this.provvedimentoRevoca?.idAttivitaRevoca == 0 || this.provvedimentoRevoca?.idAttivitaRevoca == null 
      || this.provvedimentoRevoca?.idAttivitaRevoca == undefined)) || this.provvedimentoRevoca?.idAttivitaRevoca == 18 ){
        this.myForm.get("numeroCovar").enable();
    }

  }


  /***************************************************************************
    Condizioni ATTIVAZIONI PULSANTI: vedi CDU16-V02 tabella 6.2.1
    le condizioni sono tutte legate allo STATO PROCEDIMENTO e all'ATTIVITA'
  ***************************************************************************/
  condizioniAttivazioniPulsanti() {

    console.log("this.provvedimentoRevoca?.idStatoRevoca", this.provvedimentoRevoca?.idStatoRevoca)
    console.log("this.provvedimentoRevoca?.idAttivitaRevoca", this.provvedimentoRevoca?.idAttivitaRevoca)

    //BOZZA
    if (this.provvedimentoRevoca?.idStatoRevoca == 5 && (this.provvedimentoRevoca?.idAttivitaRevoca == 0 || this.provvedimentoRevoca?.idAttivitaRevoca == null || this.provvedimentoRevoca?.idAttivitaRevoca == undefined)) {
      this.showElimina = true;
      this.showEmettiProvvedimentoRevoca = true;
      this.showRitiraInAutolettura = false;
      this.showConfermaProvvedimento = false;
    }

    //BOZZA e ITER AUTORIZZATIVO FALLITO
    if (this.provvedimentoRevoca?.idStatoRevoca == 5 && this.provvedimentoRevoca?.idAttivitaRevoca == 18) {
      this.showElimina = false;
      this.showEmettiProvvedimentoRevoca = true;
      this.showRitiraInAutolettura = false;
      this.showConfermaProvvedimento = false;
    }

    if(
      //EMESSO PROVVEDIMENTO DI REVOCA
      this.provvedimentoRevoca?.idStatoRevoca == 8 && (this.provvedimentoRevoca?.idAttivitaRevoca == 0 || this.provvedimentoRevoca?.idAttivitaRevoca == null || this.provvedimentoRevoca?.idAttivitaRevoca == undefined)
    ) {
      this.showElimina = false;
      this.showEmettiProvvedimentoRevoca = false;
      this.showRitiraInAutolettura = true;
      this.showConfermaProvvedimento = false;
    }


    if (
      //EMESSO PROVVEDIMENTO DI REVOCA e TERMINE SCADUTO
      this.provvedimentoRevoca?.idStatoRevoca == 8 && this.provvedimentoRevoca?.idAttivitaRevoca == 11
    ) {
      this.showElimina = false;
      this.showEmettiProvvedimentoRevoca = false;
      this.showRitiraInAutolettura = true;
      this.showConfermaProvvedimento = true;
    }


    if (
      //EMESSO PROVVEDIMENTO DI REVOCA e RICEVUTA CONTESTAZIONE
      this.provvedimentoRevoca?.idStatoRevoca == 8 && this.provvedimentoRevoca?.idAttivitaRevoca == 12 ||

      //EMESSO PROVVEDIMENTO DI REVOCA e ITER AUTORIZZATIVO FALLITO
      this.provvedimentoRevoca?.idStatoRevoca == 8 && this.provvedimentoRevoca?.idAttivitaRevoca == 18
    ) {
      this.showElimina = false;
      this.showEmettiProvvedimentoRevoca = false;
      this.showRitiraInAutolettura = true;
      this.showConfermaProvvedimento = true;
    }

    if (
      //BOZZA e AVVIATO ITER AUTORIZZATIVO PROVVEDIMENTO
      this.provvedimentoRevoca?.idStatoRevoca == 5 && this.provvedimentoRevoca?.idAttivitaRevoca == 4 ||

      //EMESSO PROVVEDIMENTO DI REVOCA e AVVIATO ITER AUTORIZZATIVO RITIRO IN AUTOTUTELA
      this.provvedimentoRevoca?.idStatoRevoca == 8 && this.provvedimentoRevoca?.idAttivitaRevoca == 5 ||

      //EMESSO PROVVEDIMENTO DI REVOCA e AVVIATO ITER AUTORIZZATIVO RITIRO IN AUTOTUTELA
      this.provvedimentoRevoca?.idStatoRevoca == 8 && this.provvedimentoRevoca?.idAttivitaRevoca == 13 ||

      //RITIRATO IN AUTOTUTELA
      this.provvedimentoRevoca?.idStatoRevoca == 9 && (this.provvedimentoRevoca?.idAttivitaRevoca == 0 || this.provvedimentoRevoca?.idAttivitaRevoca == null || this.provvedimentoRevoca?.idAttivitaRevoca == undefined) ||

      //PROVVEDIMENTO CONFERMATO
      this.provvedimentoRevoca?.idStatoRevoca == 10 && (this.provvedimentoRevoca?.idAttivitaRevoca == 0 || this.provvedimentoRevoca?.idAttivitaRevoca == null || this.provvedimentoRevoca?.idAttivitaRevoca == undefined) ||

      //PROVVEDIMENTO CONFERMATO e RICHIESTO ACCESSO AGLI ATTI
      this.provvedimentoRevoca?.idStatoRevoca == 10 && this.provvedimentoRevoca?.idAttivitaRevoca == 14
    ) {
      this.showElimina = false;
      this.showEmettiProvvedimentoRevoca = false;
      this.showRitiraInAutolettura = false;
      this.showConfermaProvvedimento = false;
    }

  }


  ////////////////////////////////////////////////////////////////////////////////////////////
  emettiProvvedimentoRevoca() {
    this.resetMessage();

    this.mostraErrori = false;

    this.provvedimentiRevocaService.abilitaEmettiProvvedimento(this.numeroProvvedimentoRevoca.toString()).subscribe(data => {
      console.log("ABILITA EMETTI PROVVEDIMENTO", data);

      if (data.code == "OK") {

        //recupero eventuali altri documenti da passare come parametro
        let altriDocEmettiProvvedimento: Array<DocumentoRevocaVO> = [];
        this.listaDocumenti.forEach(element => {
          if (element.idTipoDocumento == '53') {
            altriDocEmettiProvvedimento.push(element);
          }
        })

        //apro finestra pop-up
        const dialogEmissioneProvvedimento = this.dialog.open(EmissioneProvvedimentoDialogComponent, {
          width: '40vw',
          data: {
            "numeroGestioneRevoca": this.provvedimentoRevoca.numeroProvvedimentoRevoca,
            "letteraAccompagnatoria": this.listaDocumenti.find(ele => ele.idTipoDocumento == '47'),
            "altriDocumenti": altriDocEmettiProvvedimento
          }
        });

        dialogEmissioneProvvedimento.afterClosed().subscribe(result => {

          console.log(result);

          //ricarico i dati del dettaglio
          this.caricamentoDati()

          this.mostraErrori = true;

        });


      } else {
        this.mostraErrori = true;
        this.showMessageError(data.message)
      }
    })
  }

  ritiroInAutolettura() {
    this.resetMessage();

    this.mostraErrori = false;

    //recupero eventuali altri documenti da passare come parametro
    let altriDocRitiroInAutolettura: Array<DocumentoRevocaVO> = [];
    this.listaDocumenti.forEach(element => {
      if (element.idTipoDocumento == '54') {
        altriDocRitiroInAutolettura.push(element);
      }
    })

    //apro finestra pop-up
    const dialogRitiroInAutotutela = this.dialog.open(RitiroInAutotutelaDialogComponent, {
      width: '40vw',
      data: {
        "numeroGestioneRevoca": this.provvedimentoRevoca.numeroProvvedimentoRevoca,
        "letteraAccompagnatoria": this.listaDocumenti.find(ele => ele.idTipoDocumento == '48'),
        "altriDocumenti": altriDocRitiroInAutolettura
      }
    });

    dialogRitiroInAutotutela.afterClosed().subscribe(result => {

      console.log(result);

      //ricarico i dati del dettaglio
      this.caricamentoDati()

      this.mostraErrori = true;

    });
  }

  provvedimentoConfermato() {
    this.resetMessage();

    this.mostraErrori = false;

    //recupero eventuali altri documenti da passare come parametro
    let altriDocProvvedimentoConfermato: Array<DocumentoRevocaVO> = [];
    this.listaDocumenti.forEach(element => {
      if (element.idTipoDocumento == '56') {
        altriDocProvvedimentoConfermato.push(element);
      }
    })

    //apro finestra pop-up
    const dialogConfermaProvvedimento = this.dialog.open(ConfermaProvvedimentoDialogComponent, {
      width: '40vw',
      data: {
        "numeroGestioneRevoca": this.provvedimentoRevoca.numeroProvvedimentoRevoca,
        "letteraAccompagnatoria": this.listaDocumenti.find(ele => ele.idTipoDocumento == '49'),
        "altriDocumenti": altriDocProvvedimentoConfermato
      }
    });

    dialogConfermaProvvedimento.afterClosed().subscribe(result => {

      console.log(result);

      //ricarico i dati del dettaglio
      this.caricamentoDati()

      this.mostraErrori = true;

    });
  }

  //ATTIVITA
  getEstrattoConto(element: any) {
    /*console.log(element);
    
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_MONITORAGGIO_CREDITI + "/estrattoConto"],
      {
        queryParams: {
          codProgetto: this.provvedimentoRevoca.codiceVisualizzatoProgetto,
          idProgetto: this.provvedimentoRevoca.idProgetto,
          ndg: this.provvedimentoRevoca.ndg, 
          idModalitaAgevolazione: element.idModalita, 
          idModalitaAgevolazioneRif:  element.idModalitaRif,
          componentFrom: 3,
          numeroProvvedimentoRevoca: this.numeroProvvedimentoRevoca,
          beneficiario: this.provvedimentoRevoca.denominazioneBeneficiario
        },
        skipLocationChange: true
      }
    );*/

    this.dialog.open(DialogEstrattoConto, {
      minWidth: '40vw',
      data: {
        componentFrom: 3, 

        bando: this.provvedimentoRevoca.nomeBandoLinea,
        codProgetto: this.provvedimentoRevoca.codiceVisualizzatoProgetto,
        beneficiario: this.provvedimentoRevoca.denominazioneBeneficiario,

        idProgetto: this.provvedimentoRevoca.idProgetto,
        ndg: this.provvedimentoRevoca.ndg,
        idModalitaAgevolazione: element.idModalita, 
        idModalitaAgevolazioneRif: element.idModalitaRif,
       }
    });
  }

  getPianoAmmortamento(element: any) {
    /*this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_MONITORAGGIO_CREDITI + "/pianoAmmortamento"],
      {
        queryParams: {
          codProgetto: this.provvedimentoRevoca.codiceVisualizzatoProgetto,
          idProgetto: this.provvedimentoRevoca.idProgetto,
          ndg: this.provvedimentoRevoca.ndg, 
          idModalitaAgevolazione: element.idModalita, 
          idModalitaAgevolazioneRif:  element.idModalitaRif, 
          componentFrom: 3,
          numeroProvvedimentoRevoca: this.numeroProvvedimentoRevoca,
          beneficiario: this.provvedimentoRevoca.denominazioneBeneficiario
        },
        skipLocationChange: true
      }
    );*/

    this.dialog.open(DialogPianoAmmortamento, {
      minWidth: '40vw',
      data: {
        componentFrom: 3,

        bando: this.provvedimentoRevoca.nomeBandoLinea,
        codProgetto: this.provvedimentoRevoca.codiceVisualizzatoProgetto,
        beneficiario: this.provvedimentoRevoca.denominazioneBeneficiario,

        idProgetto: this.provvedimentoRevoca.idProgetto,
        ndg: this.provvedimentoRevoca.ndg,
        idModalitaAgevolazione: element.idModalita, 
        idModalitaAgevolazioneRif: element.idModalitaRif,       
       }
    });
    

    console.log(element);
  }


  /////////////////////////////////////////////////////////////////////////////////////////////
  loadedDownload: boolean;
  downloadAllegato(idDocumentoIndex: string) {
    this.resetMessage();
    this.loadedDownload = false;
    this.archivioFileService.leggiFileConNome(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
      if (res) {
        let nome = res.headers.get("header-nome-file");
        saveAs(new Blob([res.body]), nome);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }


  //////////////////////////////////////////////////////////////////////////////////////////////////////////

  goBack() {
    this.location.back();
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.success = true;
  }
  showMessageError(msg: string) {
    this.messageError = msg;
    this.error = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessage() {
    this.messageError = null;
    this.error = false;
    this.success = false;
  }

}
