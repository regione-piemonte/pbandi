/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/core/services/user.service';
import { ArchivioFileService, UserInfoSec } from '@pbandi/common-lib';
import { ProcedimentiRevocaResponseService } from 'src/app/revoche/services/procedimenti-revoca-response.service';
import { ProcedimentoRevocaVO } from 'src/app/revoche/commons/procedimenti-revoca-dto/procedimento-revoca-vo';
import { DocumentoRevocaVO } from 'src/app/revoche/commons/procedimenti-revoca-dto/documento-revoca-vo';
import { AvviaProcedimentoRevDialogComponent } from '../avvia-procedimento-rev-dialog/avvia-procedimento-rev-dialog.component';
import { ArchiviaProcedimentoRevDialogComponent } from '../archivia-procedimento-rev-dialog/archivia-procedimento-rev-dialog.component';
import { RichiestaIntegrazioneDialogComponent } from '../richiesta-integrazione-dialog/richiesta-integrazione-dialog.component';
import { RichiestaProrogaDialogComponent } from '../richiesta-proroga-dialog/richiesta-proroga-dialog.component';
import { GestioneProrogaVO } from 'src/app/revoche/commons/procedimenti-revoca-dto/gestione-proroga-vo';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { saveAs } from 'file-saver-es';
import { ModaleCampiErogazioneDialogComponent } from '../modale-campi-erogazione-dialog/modale-campi-erogazione-dialog.component';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
  selector: 'app-visualizza-modifica-proc-revoca',
  templateUrl: './visualizza-modifica-proc-revoca.component.html',
  styleUrls: ['./visualizza-modifica-proc-revoca.component.scss']
})
export class VisualizzaModificaProcRevocaComponent implements OnInit {

  //Variabili
  isLoading: boolean = false;
  idProcedimentoRevoca: number;
  user: UserInfoSec;
  procedimentoRevocaSelezionato: ProcedimentoRevocaVO;
  documentiRevoca: DocumentoRevocaVO[] = [];
  myForm: FormGroup;
  dataSource = new MatTableDataSource<any>([]);
  numeroRichiesteProroga: number = 0;
  richiestaProroga: GestioneProrogaVO;      //per ora prevedo che possa essere al massimo 1 richiesta

  documentiCaricati: boolean = false;
  richiestaProrogaCaricata: boolean = false;
  chiamataAbilitaRichiediIntegrazione: boolean = false;
  chiamataAbilitaChiudiIntegrazione: boolean = false;


  //gestione messaggi errore/successo
  error: boolean = false;
  success: boolean = false;
  messageError: string;
  messageSuccess: string;
  mostraErrori: boolean = true;   //false quando viene aperto un dialog

  //Tabella
  displayedColumns: string[] = ["modalita", "importoAmmessoIniziale", "impConcesso", "impRevocato", "impErogato", "impRecuperato", "impRimborsato", "impConcessoAlRevocato", "impErogatoAlRecuperato", "impDaRevocare"];

  //visualizzazione pulsanti
  onlyRead: boolean = true;   //modifica o salva
  hiddenCreaBozza: boolean = false;
  hiddenArchivia: boolean = false;
  hiddenRichiestaProroga: boolean = false;
  hiddenAvviaProcedimento: boolean = false;
  hiddenRichiediIntegrazione: boolean = false;
  hiddenInviaIncarico: boolean = false;

  abilitaRichiediIntegrazione: boolean = false;
  abilitaChiudiIntegrazione: boolean = false;



  constructor(
    public sharedService: SharedService,
    private location: Location,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private procedimentiRevocaResponseService: ProcedimentiRevocaResponseService,
    public dialog: MatDialog,
    private userService: UserService,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService
  ) { }

  ngOnInit(): void {
    this.resetMessage();
    this.isLoading = true;

    //creo form
    this.myForm = this.fb.group({
      //numeroDiProtocollo: new FormControl(this.procedimentoRevocaSelezionato?.numeroProtocollo == null ? "" : this.procedimentoRevocaSelezionato?.numeroProtocollo, [Validators.required]),
      //dataAvvioProcRevoc: new FormControl(this.procedimentoRevocaSelezionato?.dataAvvioProcedimento == null ? "" : this.procedimentoRevocaSelezionato?.dataAvvioProcedimento, [Validators.required]),
      dataNotifica: new FormControl(this.procedimentoRevocaSelezionato?.dataNotifica == null ? "" : this.procedimentoRevocaSelezionato?.dataNotifica, [Validators.required]),
      note: new FormControl(this.procedimentoRevocaSelezionato?.note == null ? "" : this.procedimentoRevocaSelezionato?.note, [Validators.required]),
      importoDaRevocareContributo: new FormControl('', []),
      importoDaRevocareFinanziamento: new FormControl('', []),
      importoDaRevocareGaranzia: new FormControl('', []),
    });


    //recupero idProcedimento precedentemente selezionato
    this.route.queryParams.subscribe(params => {
      this.idProcedimentoRevoca = params['idProcedimentoRevoca'];

      console.log("idProcedimentoRevoca", this.idProcedimentoRevoca);

      //faccio la fetch con la quale recupero i dati del procedimento precedentemente selezionato
      this.caricamentoDati()

    });

    //recupero dati utente
    this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
      }
    });

  }

  caricamentoDati() {
    //faccio la fetch con la quale recupero i dati del procedimento precedentemente selezionato
    this.procedimentiRevocaResponseService.getProcedimentoRevoca(this.idProcedimentoRevoca.toString()).subscribe(data => {

      console.log("Procedimento revoca in arrivo dal BE", data);

      if (data?.idProcedimentoRevoca) {

        this.procedimentoRevocaSelezionato = data;

        //popolo valori form
        this.popolareForm();

        //creo datasource
        this.createDatasource();

        //visualizzo i pulsanti in fondo alla pagina
        this.visualizzaButtons();

        //recupero i documenti
        this.procedimentiRevocaResponseService.getDocumenti(this.procedimentoRevocaSelezionato.numeroProcedimentoRevoca == undefined ? "" : this.procedimentoRevocaSelezionato.numeroProcedimentoRevoca).subscribe(data => {

          this.documentiRevoca = data;

          console.log("Documenti revoca", this.documentiRevoca);

          //this.isLoading = false;
          this.documentiCaricati = true;
          this.chiamateTerminate();

        }, err => {
          //this.isLoading = false;
          this.documentiCaricati = true;
          this.chiamateTerminate();
          this.showMessageError(err);
        });

        //recupero eventuali richieste di proroga
        this.procedimentiRevocaResponseService.getRichiestaProroga(this.procedimentoRevocaSelezionato.numeroProcedimentoRevoca == undefined ? "" : this.procedimentoRevocaSelezionato.numeroProcedimentoRevoca).subscribe(data => {

          console.log("Richiesta proroga", data);

          if (data != null || data != undefined) {
            this.numeroRichiesteProroga = 1;
          } else {
            this.numeroRichiesteProroga = 0;
          }

          this.richiestaProroga = data;

          //this.isLoading = false;
          this.richiestaProrogaCaricata = true;
          this.chiamateTerminate();

        }, err => {
          //this.isLoading = false;
          this.richiestaProrogaCaricata = true;
          this.chiamateTerminate();
          this.showMessageError(err);
        });

        //chiamo funziona abilitaRichiediIntegrazione
        this.procedimentiRevocaResponseService.abilitaRichiediIntegrazione(this.procedimentoRevocaSelezionato.idProcedimentoRevoca == undefined ? "" : this.procedimentoRevocaSelezionato.idProcedimentoRevoca).subscribe(data => {

          console.log("Oggetto ricevuto da ABILITA RICHIEDI INTEGRAZIONE", data);

          //mi viene restituito un Esito
          if (data.code == "OK") {
            this.abilitaRichiediIntegrazione = true
          } else {
            this.abilitaRichiediIntegrazione = false;
            // this.showMessageError(data.message)
          }

          this.chiamataAbilitaRichiediIntegrazione = true;
          this.chiamateTerminate();


        }, err => {
          this.chiamataAbilitaRichiediIntegrazione = true;
          this.chiamateTerminate();
          this.showMessageError(err);
        });

        //chiamo funziona abilitaChiudiIntegrazione
        this.procedimentiRevocaResponseService.abilitaChiudiIntegrazione(this.procedimentoRevocaSelezionato.idProcedimentoRevoca == undefined ? "" : this.procedimentoRevocaSelezionato.idProcedimentoRevoca).subscribe(data => {

          console.log("Oggetto ricevuto da ABILITA CHIUDI INTEGRAZIONE", data);

          //mi viene restituito un Esito
          if (data.code == "OK") {
            this.abilitaChiudiIntegrazione = true
          } else {
            this.abilitaChiudiIntegrazione = false;
            // this.showMessageError(data.message)
          }

          this.chiamataAbilitaChiudiIntegrazione = true;
          this.chiamateTerminate();


        }, err => {
          this.chiamataAbilitaChiudiIntegrazione = true;
          this.chiamateTerminate();
          this.showMessageError(err);
        });

      } else {

        console.log("CIAO")
        this.isLoading = false;

      }



    }, err => {
      this.isLoading = false;
      this.showMessageError(err);
    })
  }

  chiamateTerminate() {
    if (this.documentiCaricati == true && this.richiestaProrogaCaricata == true && this.chiamataAbilitaRichiediIntegrazione == true && this.chiamataAbilitaChiudiIntegrazione == true) {
      this.isLoading = false;
    }
  }

  //metodo che popola i campi del form una volta caricato il procedimento
  popolareForm() {
    this.myForm.get("dataNotifica").setValue(this.procedimentoRevocaSelezionato?.dataNotifica == null ? "" : this.procedimentoRevocaSelezionato?.dataNotifica);
    if(this.procedimentoRevocaSelezionato.dataAvvioProcedimento == null){
      this.myForm.get("dataNotifica").disable();
    }
    this.myForm.get("note").setValue(this.procedimentoRevocaSelezionato?.note == null ? "" : this.procedimentoRevocaSelezionato?.note);

    if (this.procedimentoRevocaSelezionato?.importoConcessoContributo != null) {
      this.setImporto("importoDaRevocareContributo", this.procedimentoRevocaSelezionato?.importoDaRevocareContributo == null ? '0' : this.procedimentoRevocaSelezionato?.importoDaRevocareContributo.toString(), undefined);
      if(this.procedimentoRevocaSelezionato.idStatoRevoca != "5"){
        this.myForm.get("importoDaRevocareContributo").disable();
      }
    }

    if (this.procedimentoRevocaSelezionato?.importoConcessoFinanziamento != null) {
      this.setImporto("importoDaRevocareFinanziamento", this.procedimentoRevocaSelezionato?.importoDaRevocareFinanziamento == null ? '0' : this.procedimentoRevocaSelezionato?.importoDaRevocareFinanziamento.toString(), undefined);
      if(this.procedimentoRevocaSelezionato.idStatoRevoca != "5"){
        this.myForm.get("importoDaRevocareFinanziamento").disable();
      }    
    }

    if (this.procedimentoRevocaSelezionato?.importoConcessoGaranzia != null) {
      this.setImporto("importoDaRevocareGaranzia", this.procedimentoRevocaSelezionato?.importoDaRevocareGaranzia == null ? '0' : this.procedimentoRevocaSelezionato?.importoDaRevocareGaranzia.toString(), undefined);
      if(this.procedimentoRevocaSelezionato.idStatoRevoca != "5"){
        this.myForm.get("importoDaRevocareGaranzia").disable();
      }
    }

    this.onlyRead = false;

    //i valori del form relativi agli importi vengono popolati quando si crea il datasource
  }

  //metodo che crea il datasource: quello da mostrare nella tabella
  createDatasource() {

    let listaElementiDatasource = [];

    //contributo
    if (this.procedimentoRevocaSelezionato.importoConcessoContributo != null) {
      let contributo = {
        modalita: "CONTRIBUTO",
        modalitaDesc: this.procedimentoRevocaSelezionato.modalitaAgevolazioneContributo,
        importoConcesso: this.procedimentoRevocaSelezionato.importoConcessoContributo,
        importoRevocato: this.procedimentoRevocaSelezionato.importoRevocatoContributo,
        importoErogato: this.procedimentoRevocaSelezionato.importoErogatoContributo,
        importoRecuperato: this.procedimentoRevocaSelezionato.importoRecuperatoContributo,

        importoRimborsato: null, //this.procedimentoRevocaSelezionato.importoRimborsatoFinanziamento,   //??

        importoConcessoNeRevocato: this.procedimentoRevocaSelezionato.importoConcessoNeRevocatoContributo,
        importoErogatoNeRecuperatoRimborsato: this.procedimentoRevocaSelezionato.importoErogatoNeRecuperatoRimborsatoContributo,
        importoAmmessoIniziale: this.procedimentoRevocaSelezionato.importoAmmessoContributo
      }

      this.setImporto("importoDaRevocareContributo", this.procedimentoRevocaSelezionato?.importoDaRevocareContributo == null ? '0' : this.procedimentoRevocaSelezionato?.importoDaRevocareContributo.toString(), undefined);

      //console.log("contributo", contributo);

      listaElementiDatasource.push(contributo);

      //console.log(this.dataSource.data);
    }

    //finanziamento
    if (this.procedimentoRevocaSelezionato.importoConcessoFinanziamento != null) {
      let finanziamento = {
        modalita: "FINANZIAMENTO",
        modalitaDesc: this.procedimentoRevocaSelezionato.modalitaAgevolazioneFinanziamento,
        importoConcesso: this.procedimentoRevocaSelezionato.importoConcessoFinanziamento,
        importoRevocato: this.procedimentoRevocaSelezionato.importoRevocatoFinanziamento,
        importoErogato: this.procedimentoRevocaSelezionato.importoErogatoFinanziamento,
        importoRecuperato: this.procedimentoRevocaSelezionato.importoRecuperatoFinanziamento,

        importoRimborsato: this.procedimentoRevocaSelezionato.importoRimborsatoFinanziamento,   //??

        importoConcessoNeRevocato: this.procedimentoRevocaSelezionato.importoConcessoNeRevocatoFinanziamento,
        importoErogatoNeRecuperatoRimborsato: this.procedimentoRevocaSelezionato.importoErogatoNeRecuperatoRimborsatoFinanziamento,
        importoAmmessoIniziale: this.procedimentoRevocaSelezionato.importoAmmessoFinanziamento
      }

      this.setImporto("importoDaRevocareFinanziamento", this.procedimentoRevocaSelezionato?.importoDaRevocareFinanziamento == null ? '0' : this.procedimentoRevocaSelezionato?.importoDaRevocareFinanziamento.toString(), undefined);

      listaElementiDatasource.push(finanziamento);
    }

    //garanzia
    if (this.procedimentoRevocaSelezionato.importoConcessoGaranzia != null) {
      let garanzia = {
        modalita: "GARANZIA",
        modalitaDesc: this.procedimentoRevocaSelezionato.modalitaAgevolazioneGaranzia,
        importoConcesso: this.procedimentoRevocaSelezionato.importoConcessoGaranzia,
        importoRevocato: this.procedimentoRevocaSelezionato.importoRevocatoGaranzia,
        importoErogato: this.procedimentoRevocaSelezionato.importoErogatoGaranzia,
        importoRecuperato: this.procedimentoRevocaSelezionato.importoRecuperatoGaranzia,

        importoRimborsato: null, //this.procedimentoRevocaSelezionato.importoRimborsatoFinanziamento,   //??

        importoConcessoNeRevocato: this.procedimentoRevocaSelezionato.importoConcessoNeRevocatoGaranzia,
        importoErogatoNeRecuperatoRimborsato: this.procedimentoRevocaSelezionato.importoErogatoNeRecuperatoRimborsatoGaranzia,
        importoAmmessoIniziale: this.procedimentoRevocaSelezionato.importoAmmessoGaranzia
      }

      this.setImporto("importoDaRevocareGaranzia", this.procedimentoRevocaSelezionato?.importoDaRevocareGaranzia == null ? '0' : this.procedimentoRevocaSelezionato?.importoDaRevocareGaranzia.toString(), undefined);

      listaElementiDatasource.push(garanzia);

    }

    this.dataSource = new MatTableDataSource(listaElementiDatasource);
  }

  openRichiestaProroga() {
    this.resetMessage();

    this.mostraErrori = false;

    if (this.numeroRichiesteProroga != 0) {
      const dialogRichiestaProroga = this.dialog.open(RichiestaProrogaDialogComponent, {
        width: '40vw',
        data: {
          "richiestaProroga": this.richiestaProroga,
          "numeroProcedimentoRevoca": this.procedimentoRevocaSelezionato.numeroProcedimentoRevoca
        }
      });

      dialogRichiestaProroga.afterClosed().subscribe(result => {

        console.log(result);

        this.isLoading = true;

        //ricarico i dati del dettaglio
        this.caricamentoDati()

        this.mostraErrori = true;

      });
    }

  }

  /*********************************
   **********   BUTTONS   **********
   *********************************/

  salva() {
    this.resetMessage();
    this.isLoading = true;

    let formControls = this.myForm.getRawValue();
    //console.log("FORM CONTROLS", formControls);

    let nuovoProcedimentoRevocaVO: ProcedimentoRevocaVO = this.procedimentoRevocaSelezionato;

    //update note
    nuovoProcedimentoRevocaVO.note = formControls.note;
    //update importi da revocare
    nuovoProcedimentoRevocaVO.importoDaRevocareContributo = this.getImporto("importoDaRevocareContributo");
    nuovoProcedimentoRevocaVO.importoDaRevocareFinanziamento = this.getImporto("importoDaRevocareFinanziamento");
    nuovoProcedimentoRevocaVO.importoDaRevocareGaranzia = this.getImporto("importoDaRevocareGaranzia");
    
    //update data notifica
    let data : Date;
    if(formControls.dataNotifica){
      data = new Date(formControls.dataNotifica);
    }else{
      data = null;
    }

    nuovoProcedimentoRevocaVO.dataNotifica = data;

    console.log("PARAMETRO CHE VIENE CREATO NEL SALVA", nuovoProcedimentoRevocaVO);

    this.procedimentiRevocaResponseService.updateProcedimentoRevoca(nuovoProcedimentoRevocaVO).subscribe((data) => {

      console.log("OGGETTO CHE RICEVO DAL BE (UPDATE PROCEDIMENTO REVOCA)", data);

      this.isLoading = false;

      if (data.code == 'OK') {

        //faccio refresh pagina
        this.caricamentoDati();

        this.success = true;
        this.showMessageSuccess("Salvataggio terminato con successo!")
        this.popolareForm();
      } else {
        this.showMessageError(data.message);
      }

    }, err => {
      this.isLoading = false;
      this.showMessageError(err);
    });

  }

  /*
  ->PBANDI-3933
  modifica() {
    this.myForm.get("dataNotifica").enable();
    this.myForm.get("note").enable();

    if (this.procedimentoRevocaSelezionato?.importoConcessoContributo != null) {
      this.myForm.get("importoDaRevocareContributo").enable();
    }
    if (this.procedimentoRevocaSelezionato?.importoConcessoFinanziamento != null) {
      this.myForm.get("importoDaRevocareFinanziamento").enable();
    }
    if (this.procedimentoRevocaSelezionato?.importoConcessoGaranzia != null) {
      this.myForm.get("importoDaRevocareGaranzia").enable();
    }
    this.onlyRead = false;
  }
  */

  avviaProcedimentoRevoca() {
    this.resetMessage();

    //Prima di aprire il dialog, devo fare il controllo verificaImporti
    this.procedimentiRevocaResponseService.verificaImporti(this.procedimentoRevocaSelezionato.idProcedimentoRevoca).subscribe((data) => {

      console.log("RISPOSTA VERIFICA IMPORTI", data);

      //this.isLoading = false;

      if (data.code == 'OK') {

        this.mostraErrori = false;

        //se il controllo ha dato esito positivo, apro il dialog
        const dialogAvviaProcedimentoRevoca = this.dialog.open(AvviaProcedimentoRevDialogComponent, {
          width: '40vw',
          data: {
            "numeroProcedimentoRevoca": this.procedimentoRevocaSelezionato.numeroProcedimentoRevoca,
            "idProcedimentoRevoca": this.procedimentoRevocaSelezionato.idProcedimentoRevoca
          }
        });

        dialogAvviaProcedimentoRevoca.afterClosed().subscribe(result => {

          this.mostraErrori = true;
          console.log(result);

          //ricarico i dati del dettaglio
          this.caricamentoDati()

          //Se il dialog viene chiuso, vuol dire che l'avvia procedimento di revoca NON ha avuto successo
          //(se ha successo si torna direttamente alla ricerca)
          //quindi, se si arriva qui dentro vuol dire che qualcosa è andato storto e che gli eventuali file aggiunti quando si era nella dialog vanno rimossi dal DB
          //--> implementerei l'eliminazione dei file aggiunti precedentemente dal db

        });


      } else {

        this.showMessageError(data.message);

      }

    }, err => {
      //this.isLoading = false;
      this.showMessageError(err);
    });


  }

  avviaIncaricoAdErogazionePerAvvioProcedimento() {
    this.resetMessage();

    //Prima di aprire il dialog, devo fare il controllo verificaImporti
    this.procedimentiRevocaResponseService.verificaImporti(this.procedimentoRevocaSelezionato.idProcedimentoRevoca).subscribe((data) => {

      console.log("RISPOSTA VERIFICA IMPORTI", data);

      //this.isLoading = false;

      if (data.code == 'OK') {

        this.mostraErrori = false;

        //se il controllo ha dato esito positivo, apro il dialog
        const dialogAvviaIncaricoAdErogazionePerAvvioProcedimento = this.dialog.open(ModaleCampiErogazioneDialogComponent, {
          width: '40vw',
          data: {
            "numeroProcedimentoRevoca": this.procedimentoRevocaSelezionato.numeroProcedimentoRevoca,
            "idProcedimentoRevoca": this.procedimentoRevocaSelezionato.idProcedimentoRevoca,
            "numeroDichiarazioneSpesa": this.procedimentoRevocaSelezionato?.idDichiarazioneSpesa,
            "impDaErogareContributo": this.procedimentoRevocaSelezionato?.impDaErogareContributo,
            "causaleErogazioneContributo": this.procedimentoRevocaSelezionato?.causaleErogazioneContributo,
            "impDaErogareFinanziamento": this.procedimentoRevocaSelezionato?.impDaErogareFinanziamento,
            "causaleErogazioneFinanziamento": this.procedimentoRevocaSelezionato?.causaleErogazioneFinanziamento,
            "impIres": this.procedimentoRevocaSelezionato?.impIres,
          }
        });

        dialogAvviaIncaricoAdErogazionePerAvvioProcedimento.afterClosed().subscribe(result => {

          this.mostraErrori = true;
          console.log(result);

          //ricarico i dati del dettaglio
          this.caricamentoDati()

          //Se il dialog viene chiuso, vuol dire che l'avvia procedimento di revoca NON ha avuto successo
          //(se ha successo si torna direttamente alla ricerca)
          //quindi, se si arriva qui dentro vuol dire che qualcosa è andato storto e che gli eventuali file aggiunti quando si era nella dialog vanno rimossi dal DB
          //--> implementerei l'eliminazione dei file aggiunti precedentemente dal db

        });


      } else {

        this.showMessageError(data.message);

      }

    }, err => {
      //this.isLoading = false;
      this.showMessageError(err);
    });

  }

  archivia() {
    this.resetMessage();

    const dialogArchiviaProcedimentoRevoca = this.dialog.open(ArchiviaProcedimentoRevDialogComponent, {
      width: '40vw',
      data: {
        "numeroProcedimentoRevoca": this.procedimentoRevocaSelezionato.numeroProcedimentoRevoca,
        "idProcedimentoRevoca": this.procedimentoRevocaSelezionato.idProcedimentoRevoca,
        "note": this.procedimentoRevocaSelezionato.note
      }
    });

    dialogArchiviaProcedimentoRevoca.afterClosed().subscribe(result => {

      console.log(result);

      //ricarico i dati del dettaglio
      this.caricamentoDati()

      this.mostraErrori = true;

    });
  }

  richiediIntegrazione() {
    this.resetMessage();

    let altriDocRichiestaIntegrazione: Array<DocumentoRevocaVO> = [];
    this.documentiRevoca.forEach(element => {
      if (element.idTipoDocumento == '51') {
        altriDocRichiestaIntegrazione.push(element);
      }
    })

    const dialogRichiestaIntegrazione = this.dialog.open(RichiestaIntegrazioneDialogComponent, {
      width: '40vw',
      data: {
        "numeroProcedimentoRevoca": this.procedimentoRevocaSelezionato.numeroProcedimentoRevoca,
        "idProcedimentoRevoca": this.procedimentoRevocaSelezionato.idProcedimentoRevoca,
        // "letteraAccompagnatoria": this.documentiRevoca.find(ele => ele.idTipoDocumento == '45'),
        // "altriDocumenti": altriDocRichiestaIntegrazione
      }
    });

    dialogRichiestaIntegrazione.afterClosed().subscribe(result => {

      console.log(result);

      //ricarico i dati del dettaglio
      this.caricamentoDati()

      this.mostraErrori = true;


      //devo tornare alla pagina di ricerca (?)
      //this.location.back();

    });
  }

  creaBozzaProvvedimento() {

    this.resetMessage();

    this.procedimentiRevocaResponseService.creaBozzaProvvedimentoRevoca(this.procedimentoRevocaSelezionato.idProcedimentoRevoca).subscribe((data) => {

      console.log("OGGETTO CHE RICEVO DAL BE (CREA BOZZA PROVVEDIMENTO REVOCA)", data);

      this.isLoading = false;

      if (data.code == 'OK') {
        this.success = true;
        this.showMessageSuccess(data.message);

        //ritorno alla ricerca
        this.location.back();

      } else {
        this.showMessageError(data.message);
      }

    }, err => {
      this.isLoading = false;
      this.showMessageError(err);
    });

  }

  richiediProroga() { }    //??


  chiudiRichiestaIntegrazione() {

    this.resetMessage();

    const dialogRichiestaIntegrazione = this.dialog.open(ConfermaDialog, {
      width: '40vw',
      data: {
        "message": "ATTENZIONE!\nSei sicuro di voler chiudere la richiesta di integrazione?"
      }
    });

    dialogRichiestaIntegrazione.afterClosed().subscribe(result => {

      console.log(result);

      if (result == "SI") {

        this.procedimentiRevocaResponseService.chiudiRichiestaIntegrazione(this.procedimentoRevocaSelezionato.idProcedimentoRevoca).subscribe((data) => {

          console.log("OGGETTO CHE RICEVO DAL BE (CHIUDI RICHIESTA INTEGRAZIONE)", data);

          this.isLoading = false;

          if (data.code == 'OK') {
            this.success = true;
            this.showMessageSuccess(data.message);

            //ricarico i dati del dettaglio
            this.caricamentoDati()

            this.mostraErrori = true;

            //ritorno alla ricerca
            //this.location.back();

          } else {
            this.showMessageError(data.message);
          }

        }, err => {
          this.isLoading = false;
          this.showMessageError(err);
        });

      } else {

        //non succede nulla (?)

      }

    });

  }

  visualizzaButtons() {
    //NOTA BENE: condizioni prese dalla tabella 6.1.1 del CDU10 VERSIONE 2! (12/12)

    //CREA BOZZA PROVVEDIMENTO DI REVOCA e ARCHIVIA e RICHIEDI INTEGRAZIONE
    //Stato revoca:
    //6: ATT_CONTRODED
    //7: SOSPESO
    //Attività revoca:
    //6: CONTRODED_RIC
    //11: TERMINE_SCAD
    //7: INTEGR_RIC
    //15: RICH_INTEGR_CHIUSA
    if (
      (this.procedimentoRevocaSelezionato.idStatoRevoca == '6' && 
      (this.procedimentoRevocaSelezionato.idAttivitaRevoca == '6' || this.procedimentoRevocaSelezionato.idAttivitaRevoca == '11' || this.procedimentoRevocaSelezionato.idAttivitaRevoca == '17')) ||
      (this.procedimentoRevocaSelezionato.idStatoRevoca == '7' && 
      (this.procedimentoRevocaSelezionato.idAttivitaRevoca == '7' || this.procedimentoRevocaSelezionato.idAttivitaRevoca == '11' || this.procedimentoRevocaSelezionato.idAttivitaRevoca == '15' || this.procedimentoRevocaSelezionato.idAttivitaRevoca == '17'))
    ) {
      this.hiddenCreaBozza = false;
      this.hiddenArchivia = false;
      this.hiddenRichiediIntegrazione = false;
    } else {
      this.hiddenCreaBozza = true;
      this.hiddenArchivia = true;
      this.hiddenRichiediIntegrazione = true;
    }

    //RICHIESTA PROROGA
    //Stato revoca:
    //6: ATT_CONTRODED
    //7: SOSPESO
    //Attività revoca:
    //8: ricevuta richiesta di proroga
    if (
      (this.procedimentoRevocaSelezionato.idStatoRevoca == '6' && this.procedimentoRevocaSelezionato.idAttivitaRevoca == '8') ||
      (this.procedimentoRevocaSelezionato.idStatoRevoca == '7' && this.procedimentoRevocaSelezionato.idAttivitaRevoca == '8')
    ) {
      this.hiddenRichiestaProroga = false;
    } else {
      this.hiddenRichiestaProroga = true;
    }

    //AVVIA PROCEDIMENTO DI REVOCA e INVIA INCARICO AD EROGAZIONE PER AVVIO PROCEDIMENTO
    //Stato revoca:
    //3: BOZZA_PROV_REV, Creata bozza provvedimento di revoca   (????)
    //5: BOZZA
    if (
      (this.procedimentoRevocaSelezionato.idStatoRevoca == '5' && (this.procedimentoRevocaSelezionato.idAttivitaRevoca == null || this.procedimentoRevocaSelezionato.idAttivitaRevoca == '17'))
    ) {

      if (this.procedimentoRevocaSelezionato.idCausaleBlocco == '24') {
        this.hiddenInviaIncarico = false;
        this.hiddenAvviaProcedimento = true;
      } else {
        this.hiddenAvviaProcedimento = false;
        this.hiddenInviaIncarico = true;
      }

    } else {
      this.hiddenAvviaProcedimento = true;
      this.hiddenInviaIncarico = true;
    }

  }



  goBack() {
    this.location.back();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.error = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }
  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.success = true;
  }

  resetMessage() {
    this.messageError = null;
    this.error = false;
    this.success = false;
  }


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

  /*
    isAnteprimaVisible(nomeFile: string, codTipoDoc: string) { //duplicato di isFileWithPreview dentro AnteprimaDialogComponent
      const splitted = nomeFile.split(".");
      if (splitted[splitted.length - 1] == "pdf" || splitted[splitted.length - 1] == "PDF" || splitted[splitted.length - 1] == "xml" || splitted[splitted.length - 1] == "XML" || splitted[splitted.length - 1] == "p7m" || splitted[splitted.length - 1] == "P7M") {
        return true;
      } else {
        return false;
      }
    }

    anteprimaAllegato(doc: DocumentoRevocaVO) {
      this.resetMessage();
      let comodo = new Array<any>();
      comodo.push(doc.nomeFile);   //nome
      comodo.push(doc.idDocumento);    //idDocumento
      comodo.push(new MatTableDataSource<InfoDocumentoVo>([new InfoDocumentoVo(doc.codTipoDoc, doc.documento, null, null, null, null, null, doc.idDocumentoIndex, null)]));     //tutti i file da mostrare
      comodo.push(this.configService.getApiURL());  //url del BE, lascio così
      comodo.push(doc.codTipoDoc);
      //PBANDI_T_DOCUMENTO_INDEX faccio join con PBNADI_C_TIPO_DOCUMENTO_INDEX tramite idTipoDocumentoIndex--> recupero DESC_BREVE_TIPO_DOC_INDEX
      //--> quando si fa upload caricare in REPOSITORY la desc_breve
      //poi questo codice mi viene passato nel DTO e lo restituisco per l'anteprima.
      //id_tipo_dpcumento_index è giusto, devo solo aggiungere in repository

      this.dialog.open(AnteprimaDialogComponent, {
        data: comodo,
        panelClass: 'anteprima-dialog-container'
      });

    } */

    

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
    return this.sharedService.getNumberFromFormattedString(this.myForm.get(formControlName).value.toString());
  }

}
