/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';
import { BloccoVO } from 'src/app/gestione-anagrafica/commons/dto/bloccoVO';
import { ControlliAntimafiaVO } from '../../commons/controlli-antimafia-vo';
import { ControlliDeggendorfVO } from '../../commons/controlli-deggendorf-vo';
import { ControlliDurcVO } from '../../commons/controlli-durc-vo';
import { ControlliPreErogazioneResponseService } from '../../services/controlli-pre-erogazione-response.service';
import { ControlloNonApplicabileDialogComponent } from '../controllo-non-applicabile-dialog/controllo-non-applicabile-dialog.component';
import { ModalitaRichiestaDialogComponent } from '../modalita-richiesta-dialog/modalita-richiesta-dialog.component';
import { AvviaIterDialogComponent } from '../avvia-iter-dialog/avvia-iter-dialog.component';
import { InterventoSostitutivoVO } from '../../commons/intervento-sostitutivo-vo';
import { NuovoInterventoSostitutivoDialogComponent } from '../nuovo-intervento-sostitutivo-dialog/nuovo-intervento-sostitutivo-dialog.component';
import { ListaInterventiDialogComponent } from '../lista-interventi-dialog/lista-interventi-dialog.component';
import { CreaDistintaInterventiDialogComponent } from '../crea-distinta-interventi-dialog/crea-distinta-interventi-dialog.component';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { SharedService } from 'src/app/shared/services/shared.service';
import { DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { ConfigService } from 'src/app/core/services/config.service';

@Component({
  selector: 'app-controlli-pre-erogazione',
  templateUrl: './controlli-pre-erogazione.component.html',
  styleUrls: ['./controlli-pre-erogazione.component.scss']
})
export class ControlliPreErogazioneComponent implements OnInit {

  spinner: boolean = false;
  error: boolean = false;
  success: boolean = false;

  //variabili boolean per SPINNER
  spinnerFetchData: boolean = false;
  spinnerBloccoSoggetto: boolean = false;
  spinnerBloccoDomanda: boolean = false;
  spinnerAntimafia: boolean = false;
  spinnerDurc: boolean = false;
  spinnerDeggendorf: boolean = false;
  spinnerCNAAntimafia: boolean = false;
  spinnerCNADurc: boolean = false;
  spinnerCNADeggendorf: boolean = false;

  //parametri in arrivo da fetchDati()
  importoLordo: number;
  importoBeneficiario: number;
  ibanBeneficiario: string;
  titoloBando: string;
  codiceProgetto: string;
  idModalitaAgevolazioneRif: number;
  descrizioneModalitaAgevolazione: string;
  beneficiario: string;
  dataControlli: Date;
  flagPubblicoPrivato: string;
  idSoggetto: string;
  numeroDomanda: string;
  idProposta: string;
  codiceFiscale: string;
  codiceBando: string;
  idProgetto: string;
  verificaAntimafia: boolean;
  flagFinistr: boolean;

  iterEsitoValidazioneInCorso: boolean;
  iterEsitoValidazioneConcluso: boolean;
  iterCommunicazioneInterventoInCorso: boolean;
  iterCommunicazioneInterventoConcluso: boolean;
  distintaCreata: boolean;

  messaggioErrore: string;

  //Variabili Controllo non applicabile
  motivazioneDurc: string;
  motivazioneAntimafia: string;
  motivazioneDeggendorf: string;

  //date che prendo dal BE
  esitoRichiestaDeggendorf: boolean;

  //Array arrivati da chiamate al BE
  listaBloccoSoggeto: BloccoVO[] = [];
  listaBloccoDomanda: BloccoVO[] = [];
  esitoAntimafia: ControlliAntimafiaVO = new ControlliAntimafiaVO();
  esitoDurc: ControlliDurcVO = new ControlliDurcVO();
  esitoDeggendorf: ControlliDeggendorfVO = new ControlliDeggendorfVO();

  //varibili booleane per le chiamate al BE andate a buon fine
  successoBloccoSoggetto = false;
  successoBloccoDomanda = false;
  successoAntimafia = false;
  successoDurc = false;
  successoDeggendorf = false;

  //variabili booleane per mostrare i messaggi di risposta
  inviaRichiestaAntimafiaResponse1: boolean = false;
  inviaRichiestaAntimafiaResponse2: boolean = false;
  inviaRichiestaAntimafiaResponse3: boolean = false;
  inviaRichiestaAntimafiaResponse4: boolean = false;
  inviaRichiestaDurcResponse1: boolean = false;
  inviaRichiestaDurcResponse2: boolean = false;
  inviaRichiestaDurcResponse3: boolean = false;

  //variabili per la gestione dei bottoni/tab
  bloccoAntimafia: boolean = false;
  bloccoDurc: boolean = false;
  avviaIterEsitoValidazione: boolean = false;
  avviaIterCommunicazioneIntervento: boolean = false;
  interventoSostitutivo: boolean = false;

  //variabili per gli interventi sostitutivi
  listaInterventi:InterventoSostitutivoVO[] = [];

  //Form
  myForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    public sharedService: SharedService,
    private userService: UserService,
    private dialog: MatDialog,
    private configService: ConfigService,
    private resService: ControlliPreErogazioneResponseService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit() {

    this.allSpinnerTrue();

    this.route.queryParams.subscribe(params => {
      this.idProposta = params['idProposta']
    });

    //creo il form
    this.myForm = this.fb.group({
      vercor: ['', Validators.required],
      importoDaErogare: ['', Validators.required],
    });

    //Fetch data
    this.resService.fetchData(this.idProposta).subscribe(data => {
      this.importoBeneficiario = data.importoBeneficiario;
      this.importoLordo = data.importoLordo;
      this.ibanBeneficiario = data.ibanBeneficiario;
      this.titoloBando = data.titoloBando;
      this.codiceProgetto = data.codiceProgetto;
      this.idModalitaAgevolazioneRif = data.idModalitaAgevolazioneRif;
      this.descrizioneModalitaAgevolazione = data.descrizioneModalitaAgevolazione;
      this.beneficiario = data.beneficiario;
      this.dataControlli = data.dataControlli;
      this.flagPubblicoPrivato = data.flagPubblicoPrivato;
      this.idSoggetto = data.idSoggetto.toString();
      this.numeroDomanda = data.numeroDomanda;
      this.idProposta = data.idProposta;
      this.codiceFiscale = data.codiceFiscale;
      this.codiceBando = data.codiceBando;
      this.idProgetto = data.idProgetto;
      this.verificaAntimafia = data.verificaAntimafia;
      this.verificaAntimafia = true //è stato richiesto di rendere antimafia sempre visibile
      this.flagFinistr = data.flagFinistr;
      
      this.setImporto("importoDaErogare", data.importoLordo.toString(), null);
      if(!this.flagFinistr){
        this.myForm.get('importoDaErogare').disable();
        this.myForm.updateValueAndValidity();
      }

      this.iterEsitoValidazioneConcluso = data.iterEsitoValidazioneConcluso;
      this.iterEsitoValidazioneInCorso = data.iterEsitoValidazioneInCorso;
      this.iterCommunicazioneInterventoConcluso = data.iterCommunicazioneInterventoConcluso;
      this.iterCommunicazioneInterventoInCorso = data.iterCommunicazioneInterventoInCorso;
      this.distintaCreata = data.distintaCreata;

      if(this.distintaCreata){
        this.listaInterventi = data.listaInterventi;
      }

      this.spinnerFetchData = false;

      //ora che ho i parametri, posso effettuare le altre chiamate al BE
      this.chiamateBE();

    }, err => {
      this.spinnerFetchData = false;
      this.error = true;
      this.messaggioErrore = "Errore durante il caricamento dei dati!";
      console.log(err);
    });
  }

  chiamateBE() {
    //Blocco anagrafico soggeto
    this.resService.getBloccoSoggetto(this.idSoggetto).subscribe(data => {
      this.listaBloccoSoggeto = data;
      this.esitoBloccoAnagraficaSoggetto(this.listaBloccoSoggeto);
      this.spinnerBloccoSoggetto = false;
    }, err => {
      this.spinnerBloccoSoggetto = false;
      this.error = true;
      this.messaggioErrore = "Errore durante il caricamento dei dati!";
      console.log(err);
    });

    //Blocco anagrafico domanda
    this.resService.getBloccoDomanda(this.idSoggetto, this.numeroDomanda).subscribe(data => {
      console.log("DATI BLOCCO DOMANDA");
      console.log(data);
      this.listaBloccoDomanda = data;
      this.esitoBloccoAnagraficaDomanda(this.listaBloccoDomanda);
      this.spinnerBloccoDomanda = false;
    }, err => {
      this.spinnerBloccoDomanda = false;
      this.error = true;
      this.messaggioErrore = "Errore durante il caricamento dei dati!";
      console.log(err);
    });

    if(this.verificaAntimafia){
      //Antimafia - Durc
      this.resService.getEsitoAntimafia(this.idProgetto).subscribe(data => {
        if (data != null) {
          console.log("DATI ANTIMAFIA");
          console.table(data);
          this.esitoAntimafia = data;
        } else {
          console.log("DATI ANTIMAFIA NULL");
        }

        this.cercaControlloAntimafia();
        this.spinnerAntimafia = false;

        //Check controllo non applicabile antimafia
        this.resService.getControlloNonApplicabile(this.idProposta, 3).subscribe(data => {

          this.motivazioneAntimafia = data;
          if (this.motivazioneAntimafia != null && this.motivazioneAntimafia != "") {
            this.successoAntimafia = true;
          }
          this.spinnerCNAAntimafia = false;
        }, err => {
          this.spinnerCNAAntimafia = false;
          this.error = true;
          this.messaggioErrore = "Errore durante il caricamento dei dati!";
          console.log(err);
        });

        //DURC
        this.resService.getEsitoDurc(this.idProgetto).subscribe(data => {
          if (data != null) {
            console.log("DURC", data);
            this.esitoDurc = data;
            if (data.dataScadenzaDurc) {
              this.successoDurc = true;
            }
          } else {
            console.log("DURC NULL");
          }
          this.cercaControlloDurc();
          this.spinnerDurc = false;

          
          //Check controllo non applicabile DURC
          this.resService.getControlloNonApplicabile(this.idProposta, 1).subscribe(data => {

            this.motivazioneDurc = data;
            if ((this.motivazioneDurc != null && this.motivazioneDurc != "") || this.esitoDurc?.descEsitoDurc == 'DSAN') {
              this.successoDurc = true;
            }
            this.spinnerCNADurc = false;

            //Imposto i bottoni per gli iter
            this.avviaIterEsitoValidazione = this.abilitaIterEsitoValidazione();
            this.avviaIterCommunicazioneIntervento = this.abilitaIterCommunicazioneInterventoSost();
            this.interventoSostitutivo = this.abilitaInterventoSostitutivo();

          }, err => {
            this.spinnerCNADurc = false;
            this.error = true;
            this.messaggioErrore = "Errore durante il caricamento dei dati!";
            console.log(err);
          });
        }, err => {
          this.spinnerDurc = false;
          this.error = true;
          this.messaggioErrore = "Errore durante il caricamento dei dati!";
          console.log(err);
        });
      }, err => {
        this.spinnerAntimafia = false;
        this.error = true;
        this.messaggioErrore = "Errore durante il caricamento dei dati!";
        console.log(err);
      });
    }else{
      this.spinnerAntimafia = false;
      this.spinnerCNAAntimafia = false;
      this.cercaControlloAntimafia();
      //DURC
      this.resService.getEsitoDurc(this.idProgetto).subscribe(data => {
        if (data != null) {
          console.log("DURC", data);
          this.esitoDurc = data;
          if (data.dataScadenzaDurc) {
            this.successoDurc = true;
          }
        } else {
          console.log("DURC NULL");
        }
        this.cercaControlloDurc();
        this.spinnerDurc = false;

        
        //Check controllo non applicabile DURC
        this.resService.getControlloNonApplicabile(this.idProposta, 1).subscribe(data => {

          this.motivazioneDurc = data;
          if ((this.motivazioneDurc != null && this.motivazioneDurc != "") || this.esitoDurc?.descEsitoDurc == 'DSAN') {
            this.successoDurc = true;
          }
          this.spinnerCNADurc = false;

          //Imposto i bottoni per gli iter
          this.avviaIterEsitoValidazione = this.abilitaIterEsitoValidazione();
          this.avviaIterCommunicazioneIntervento = this.abilitaIterCommunicazioneInterventoSost();
          this.interventoSostitutivo = this.abilitaInterventoSostitutivo();

        }, err => {
          this.spinnerCNADurc = false;
          this.error = true;
          this.messaggioErrore = "Errore durante il caricamento dei dati!";
          console.log(err);
        });
      }, err => {
        this.spinnerDurc = false;
        this.error = true;
        this.messaggioErrore = "Errore durante il caricamento dei dati!";
        console.log(err);
      });
    }

    //DEGGENDORF
    if (this.flagPubblicoPrivato == "1") {     //faccio la chiamata solo se il soggetto è PRIVATO (1) e non PUBBLICO (2)
      this.resService.getEsitoDeggendorf(this.idSoggetto).subscribe(data => {
        if (data != null) {
          console.log("DATI DEGGENDORF");
          console.table(data);
          this.esitoDeggendorf = data;
          this.myForm.get("vercor").setValue(data.vercor);
        } else {
          console.log("DATI DEGGENDORF NULL");
        }
        this.cercaControlloDeggendorf();

        if (data?.esitoRichiesta) {
          this.esitoRichiestaDeggendorf = data?.esitoRichiesta;

          if (this.esitoRichiestaDeggendorf) {
            this.motivazioneDeggendorf = "";
          }
        }
        // else {
        //   this.esitoRichiestaDeggendorf = false;
        // }

        // this.esitoRichiestaDeggendorf = data?.esitoRichiesta;
        this.spinnerDeggendorf = false;

        //Check controllo non applicabile Deggendorf
        this.resService.getControlloNonApplicabile(this.idProposta, 4).subscribe(data => {

          this.motivazioneDeggendorf = data;
          if (this.motivazioneDeggendorf != null && this.motivazioneDeggendorf != "") {
            this.successoDeggendorf = true;
          }
          this.spinnerCNADeggendorf = false;

          this.setRequiredVercor();
        }, err => {
          this.spinnerCNADeggendorf = false;
          this.error = true;
          this.messaggioErrore = "Errore durante il caricamento dei dati!";
          console.log(err);
        });
      }, err => {
        this.spinnerDeggendorf = false;
        this.error = true;
        this.messaggioErrore = "Errore durante il caricamento dei dati!";
        console.log(err);
      });
    } else {
      //se non è presente il controllo Deggendorf, setto successoDeggendorf = true
      this.successoDeggendorf = true;
      this.spinnerDeggendorf = false;
      this.spinnerCNADeggendorf = false;
      this.setRequiredVercor();
    }
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

  salvaImportoDaErogare() {
    if(!this.myForm.valid){
      this.myForm.get("vercor").markAsTouched();
      this.myForm.get("importoDaErogare").markAsTouched();
      return;
    }

    let importoDaErogare: number = this.getImporto("importoDaErogare");

    this.spinner = true;
    this.resService.salvaImportoDaErogare(this.idProposta, importoDaErogare).subscribe(data => {
      if (data) {
        this.success = true;
      } else {
        this.error = true;
        this.messaggioErrore = "Errore durante il salvataggio!";
      }
      this.spinner = false;
    }, err => {
      this.spinner = false;
      this.error = true;
      this.messaggioErrore = "Errore durante il caricamento dei dati!";
      console.log(err);
    });
  }

  goBack() {
    //resetto i messaggi d'errore
    this.resetMessaggiResponse();
    this.router.navigate(["/drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_PROPOSTE + "/ambitoErogazioni"], { queryParams: {} });
  }

  openDialogDatiProgettoAttivitaPregresse() {
    //console.log("idProgetto: ", this.idProgetto);
    //console.log("apiURL: ", this.configService.getApiURL());

    this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
      width: '90%',
      maxHeight: '95%',
      data: {
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL(),
      },
    });
  }

  openDialogContoEconomico() {
		this.dialog.open(VisualizzaContoEconomicoDialogComponent, {
			width: '90%',
			maxHeight: '90%',
			data: {
				idBandoLinea: this.userService.getProgrBandoLineaByIdProgetto(+this.idProgetto),
				idProgetto: this.idProgetto,
				apiURL: this.configService.getApiURL(),
			},
		});
	}

  avviaIterEsitoValidazioneErogazioneSospesa() {
    const avviaIterDialog = this.dialog.open(AvviaIterDialogComponent, {
      width: '40vw',
      data: {
        "idProposta": this.idProposta,
        "idTipoIter": 18
      }
    });

    avviaIterDialog.afterClosed().subscribe(result => {

      console.log(result);

      this.ngOnInit()

    });
  }

  avviaIterCommunicazioneInterventoSostitutivo() {
    const avviaIterDialog = this.dialog.open(AvviaIterDialogComponent, {
      width: '40vw',
      data: {
        "idProposta": this.idProposta,
        "idTipoIter": 19
      }
    });

    avviaIterDialog.afterClosed().subscribe(result => {

      console.log(result);

      this.ngOnInit()

    });
  }

  dettaglioInterventiSostitutivi() {
    const listaInterventiDialog = this.dialog.open(ListaInterventiDialogComponent, {
      width: '50vw',
      data: {
        "listaInterventi": this.listaInterventi,
        "importoTotale": this.importoBeneficiario,
        "ibanBeneficiario": this.ibanBeneficiario,
        "distintaCreata": this.distintaCreata
      }
    });

    listaInterventiDialog.afterClosed().subscribe(result => {
      console.log(result);
      if(result && !this.distintaCreata){
        this.listaInterventi = result;
      }

      this.ngOnInit()
    });
  }

  nuovoInterventoSostitutivo() {
    let importoBeneficiarioResiduo = this.importoBeneficiario;
    this.listaInterventi.forEach(data => importoBeneficiarioResiduo -= data.importoDaErogare);

    const nuovoInterventoDialog = this.dialog.open(NuovoInterventoSostitutivoDialogComponent, {
      width: '40vw',
      data: {
        "importoBeneficiario": importoBeneficiarioResiduo,
        "listaInterventi": this.listaInterventi,
      }
    });

    nuovoInterventoDialog.afterClosed().subscribe(result => {
      console.log(result);
      if(result){
        this.listaInterventi.push(result);
      }

      this.ngOnInit()
    });
  }

  creaDistintaErogazione() {
    const creaDistintaDialog = this.dialog.open(CreaDistintaInterventiDialogComponent, {
      width: '40vw',
      data: {
        "idProposta": this.idProposta,
        "listaInterventi": this.listaInterventi,
      }
    });

    creaDistintaDialog.afterClosed().subscribe(result => {

      console.log(result);

      this.ngOnInit()

    });
  }

  salva() {
    if(!this.myForm.valid){
      this.myForm.get("vercor").markAsTouched();
      this.myForm.get("importoDaErogare").markAsTouched();
      return;
    }

    //resetto i messaggi d'errore
    this.resetMessaggiResponse();

    this.spinner = true;

    let vercor : string = this.myForm.get("vercor").value;

    //chiamata al BE
    this.resService.saveControlliPreErogazione(this.idProposta, this.idSoggetto, this.successoDeggendorf, vercor).subscribe(data => {
      console.log(data);
      this.spinner = false;

      if (data) {

        //refresh pagina
        this.ngOnInit();

        //messaggio conferma
        this.success = true;
      } else {
        this.error = true;
        this.messaggioErrore = "Errore durante il salvataggio!";
      }


    }, err => {
      this.spinner = false;
      this.error = true;
      this.messaggioErrore = "Errore durante il caricamento dei dati!";
      console.log(err);
    });
  }

  changeEsitoRichiesta(event: any) {
    console.log("EVENT", event)
    
    this.successoDeggendorf = event.value == 'false' ? false : event.value == 'true' ? true : null;

    //faccio chiamata al BE in cui tolgo Controllo non applicabile se ho il cna
    if(this.motivazioneDeggendorf) {
      this.resService.setControlloNonApplicabile(this.idProposta, 4, null).subscribe(ris => {

        //se rimuovo la motivazione, il metodo mi restituisce false;
        if (!ris) {
          this.motivazioneDeggendorf = null;
          this.setRequiredVercor();
        } else {
          this.error = true;
          this.messaggioErrore = "Errore durante la cancellazione della motivazione!";
        }

      }, err => {
        this.error = true;
        this.messaggioErrore = "Errore durante il caricamento dei dati!";
        console.log(err);
      });
    } 
  }

  inviaRichiestaAntimafia() {
    //resetto i messaggi d'errore
    this.resetMessaggiResponse();

    const dialogModalitaRichiesta = this.dialog.open(ModalitaRichiestaDialogComponent, {
      width: '40vw',
      data: {
        "numeroDomanda": this.numeroDomanda,
        "codiceFiscale": this.codiceFiscale,
        "codiceBando": this.codiceBando,
        "codiceProgetto": this.codiceProgetto,
      }
    });

    dialogModalitaRichiesta.afterClosed().subscribe(result => {

      if (result != undefined && result != '') {

        if (this.successoAntimafia) {
          this.inviaRichiestaAntimafiaResponse4 = true;
        }

        //TRUE: se l'inserimento va a buon fine
        //FALSE: se era già presente un'altra richiesta
        else if (result.res) {
          //se la modalità è URGENTE (S), allora il controllo è subito valido
          //23/2/2023: rimossa questa condizione, il controllo resta ROSSO
          if (result.modalitaRichiesta == 'S') {
            // this.successoAntimafia = true;
            this.successoAntimafia = false;

            //È stata correttamente inserita una richiesta urgente.
            this.inviaRichiestaAntimafiaResponse3 = true;
          }
          //se la modalità è ORDINARIA (N), allora il controllo NON è subito valido
          else {
            this.successoAntimafia = false;

            //Abbiamo inserito la tua richiesta aspetta che venga elaborata.
            this.inviaRichiestaAntimafiaResponse2 = true;
          }

        } else {
          //Anche se nel DB è già presente una richiesta, ma la richiesta inserita è urgente, allora il comando diventa verde
          if (result.modalitaRichiesta == 'S') {
            // this.successoAntimafia = true;
            this.successoAntimafia = false;

            //È stata correttamente inserita una richiesta urgente.
            this.inviaRichiestaAntimafiaResponse3 = true;
          } else {
            //Esiste già una richiesta aspetta che venga elaborata!
            this.inviaRichiestaAntimafiaResponse1 = true;
          }
        }
      }

      //aggiorno la pagina
      this.ngOnInit();

    });
  }

  inviaRichiestaDURC() {
    //resetto i messaggi d'errore
    this.resetMessaggiResponse();

    this.spinner = true;

    //Ordinaria = 'N'
    this.resService.inviaRichiesta(1, this.numeroDomanda, 'N', this.codiceFiscale, this.codiceBando, this.codiceProgetto).subscribe(data => {
      console.log(data);

      if (data != undefined && data != null) {

        if (this.successoDurc) {
          this.inviaRichiestaDurcResponse3 = true;
        }

        //TRUE: se l'inserimento va a buon fine
        else if (data) {
          //23/2/2023: il controllo rimane rosso
          // this.successoDurc = true;
          this.successoDurc = false;

          //Abbiamo inserito la tua richiesta aspetta che venga lavorata
          this.inviaRichiestaDurcResponse1 = true;
        } else {
          this.successoDurc = false;

          //Esiste già una richiesta aspetta che venga elaborata
          this.inviaRichiestaDurcResponse2 = true;
        }
      }

      //aggiorno la pagina
      this.ngOnInit();

      this.spinner = false;


    }, err => {
      this.spinner = false;
      this.error = true;
      this.messaggioErrore = "Errore durante il caricamento dei dati!";
      console.log(err);
    });
  }

  forzaControllo(event: MatCheckboxChange, motivazione: string, idTipoRichiesta: number) {
    //resetto i messaggi d'errore
    this.resetMessaggiResponse();

    //se la checkbox NON è cliccata
    //quindi motivazione == null o == ""
    if (motivazione == null || motivazione == "") {

      motivazione = '';

      //apro dialog per inserire NUOVA MOTIVAZIONE
      const dialogForzaControllo = this.dialog.open(ControlloNonApplicabileDialogComponent, {
        width: '40vw',
        data: {
          "mot": motivazione,
          "idTipoRichiesta": idTipoRichiesta,
          "idProposta": this.idProposta,
        }
      });

      dialogForzaControllo.afterClosed().subscribe(result => {

        //se clicco sulla X o fuori dal dialog, result diventa o undefined o ''
        if (result == undefined || result == '') {
          //dececca la checkbox
          event.source.checked = false;

          if (idTipoRichiesta == 1) {
            this.motivazioneDurc = '';
            this.successoDurc = false;
          }
          if (idTipoRichiesta == 3) {
            this.motivazioneAntimafia = '';
            this.successoAntimafia = false;
          }
          if (idTipoRichiesta == 4) {
            this.motivazioneDeggendorf = '';
            this.successoDeggendorf = false;
          }
        }

        //se la chiamata è andata a buon fine (result.res = true)
        else if (result.res) {

          //1 = DURC
          //3 = ANTIMAFIA
          //4 = RNA
          if (idTipoRichiesta == 1) {
            this.motivazioneDurc = result.mot;
            this.successoDurc = true;
          }
          if (idTipoRichiesta == 3) {
            this.motivazioneAntimafia = result.mot;
            this.successoAntimafia = true;
          }
          if (idTipoRichiesta == 4) {
            this.motivazioneDeggendorf = result.mot;
            this.successoDeggendorf = true;
          }
          
          this.avviaIterEsitoValidazione = this.abilitaIterEsitoValidazione();
          this.avviaIterCommunicazioneIntervento = this.abilitaIterCommunicazioneInterventoSost();
          this.interventoSostitutivo = this.abilitaInterventoSostitutivo();
          this.setRequiredVercor();
        } else {
          this.error = true;
          this.messaggioErrore = "È stato impossibile forzare il controllo!";
        }
      });

    } else {
      //Se la checkbox è già selezionata clicco per RIMUOVERE MOTIVAZIONE: passo motivazione = null

      this.resService.setControlloNonApplicabile(this.idProposta, idTipoRichiesta, null).subscribe(ris => {

        //se rimuovo la motivazione, il metodo mi restituisce false;
        if (!ris) {
          if (idTipoRichiesta == 1) {
            this.motivazioneDurc = null;
            this.successoDurc = false;
          }
          if (idTipoRichiesta == 3) {
            this.motivazioneAntimafia = null;
            this.successoAntimafia = false;
          }
          if (idTipoRichiesta == 4) {
            this.motivazioneDeggendorf = null;
            this.successoDeggendorf = false;
          }

          
          this.avviaIterEsitoValidazione = this.abilitaIterEsitoValidazione();
          this.avviaIterCommunicazioneIntervento = this.abilitaIterCommunicazioneInterventoSost();
          this.interventoSostitutivo = this.abilitaInterventoSostitutivo();
          this.setRequiredVercor();
        } else {
          this.error = true;
          this.messaggioErrore = "Errore durante la cancellazione della motivazione!";
        }

      }, err => {
        this.error = true;
        this.messaggioErrore = "Errore durante il caricamento dei dati!";
        console.log(err);
      });
    }
  }

  setRequiredVercor() {
    if(this.flagPubblicoPrivato == "1" && !this.motivazioneDeggendorf) {
      this.myForm.get('vercor').enable();
      this.myForm.get('vercor').setValidators([Validators.required]);
      this.myForm.get('vercor').markAsTouched();
      this.myForm.updateValueAndValidity();
      console.log("Vercor enabled")
    } else {
      this.myForm.get('vercor').disable();
      this.myForm.get('vercor').clearValidators();
      this.myForm.get('vercor').setValue(undefined);
      this.myForm.updateValueAndValidity();
      console.log("Vercor disabled")
    }
  }

  modificaMotivazione(motivazione: string, idTipoRichiesta: number) {
    //resetto i messaggi d'errore
    this.resetMessaggiResponse();

    //apro dialog per MODIFICARE MOTIVAZIONE
    const dialogModificaMotivazione = this.dialog.open(ControlloNonApplicabileDialogComponent, {
      width: '40vw',
      data: {
        "mot": motivazione,
        "idTipoRichiesta": idTipoRichiesta,
        "idProposta": this.idProposta,
      }
    });

    dialogModificaMotivazione.afterClosed().subscribe(result => {
      console.log(result);
      if (result.res) {

        //se l'utente ha cancellato la motivazione, allora il controllo diventa rosso
        if (result.mot == '' || result.mot == null) {
          if (idTipoRichiesta == 1) {
            this.motivazioneDurc = result.mot;
            this.successoDurc = false;
          }
          if (idTipoRichiesta == 3) {
            this.motivazioneAntimafia = result.mot;
            this.successoAntimafia = false;
          }
          if (idTipoRichiesta == 4) {
            this.motivazioneDeggendorf = result.mot;
            this.successoDeggendorf = false;
          }
        } else {

          //1 = DURC
          //3 = ANTIMAFIA
          //4 = RNA
          if (idTipoRichiesta == 1) {
            this.motivazioneDurc = result.mot;
            this.successoDurc = true;
          }
          if (idTipoRichiesta == 3) {
            this.motivazioneAntimafia = result.mot;
            this.successoAntimafia = true;
          }
          if (idTipoRichiesta == 4) {
            this.motivazioneDeggendorf = result.mot;
            this.successoDeggendorf = true;
          }
        }
      } else {
        if (idTipoRichiesta == 1) {
          // this.motivazioneDurc = null;
          if (this.motivazioneDurc != null && this.motivazioneDurc != "") {
            this.successoDurc = true;
          } else {
            this.successoDurc = false;
          }
        }
        if (idTipoRichiesta == 3) {
          // this.motivazioneAntimafia = null;
          // this.successoAntimafia = false;
          if (this.motivazioneAntimafia != null && this.motivazioneAntimafia != "") {
            this.successoAntimafia = true;
          } else {
            this.successoAntimafia = false;
          }
        }
        if (idTipoRichiesta == 4) {
          // this.motivazioneDeggendorf = null;
          // this.successoDeggendorf = false;
          if (this.motivazioneDeggendorf != null && this.motivazioneDeggendorf != "") {
            this.successoDeggendorf = true;
          } else {
            this.successoDeggendorf = false;
          }
        }
      }
          
      this.avviaIterEsitoValidazione = this.abilitaIterEsitoValidazione();
      this.avviaIterCommunicazioneIntervento = this.abilitaIterCommunicazioneInterventoSost();
      this.interventoSostitutivo = this.abilitaInterventoSostitutivo();
      this.setRequiredVercor();
    });
  }

  isLoading(): boolean {
    return (
      this.spinnerFetchData == true ||
      this.spinnerBloccoSoggetto == true ||
      this.spinnerBloccoDomanda == true ||
      this.spinnerAntimafia == true ||
      this.spinnerDurc == true ||
      this.spinnerDeggendorf == true ||
      this.spinnerCNAAntimafia == true ||
      this.spinnerCNADurc == true ||
      this.spinnerCNADeggendorf == true ||
      this.spinner == true
    )
  }

  allSpinnerTrue() {
    this.spinnerFetchData = true;
    this.spinnerBloccoSoggetto = true;
    this.spinnerBloccoDomanda = true;
    this.spinnerAntimafia = true;
    this.spinnerDurc = true;
    this.spinnerDeggendorf = true;
    this.spinnerCNAAntimafia = true;
    this.spinnerCNADurc = true;
    this.spinnerCNADeggendorf = true;
  }

  cercaControlloAntimafia() {
    if (this.esitoAntimafia?.dataInvioBDNA != null || this.esitoAntimafia?.descEsitoAntimafia == "NON SUSSISTONO IMPEDIMENTI" || !this.verificaAntimafia) {
      this.successoAntimafia = true;
    }else {
      this.successoAntimafia = false;
    }
  }
  cercaControlloDurc() {
    if (this.esitoDurc.descEsitoDurc == "REGOLARE") {
      this.successoDurc = true;
    }else {
      this.successoDurc = false;
    }
  }
  cercaControlloDeggendorf() {
    if (this.esitoDeggendorf.esitoRichiesta != null) {
      this.successoDeggendorf = this.esitoDeggendorf.esitoRichiesta;
    }
  }

  esitoBloccoAnagraficaSoggetto(data: Array<BloccoVO>) {
    if (data.length == 0) {
      this.successoBloccoSoggetto = true;
    } else {
      this.successoBloccoSoggetto = data.every(el => el.dataInserimentoSblocco != null)
      data.forEach(e1 => {
        if(e1.descCausaleBlocco == "Durc" && e1.dataInserimentoSblocco == null){
          this.bloccoDurc = true;
        }
        if(e1.descCausaleBlocco == "Antimafia" && e1.dataInserimentoSblocco == null && this.verificaAntimafia){
          this.bloccoAntimafia = true;
        }
      })
    }
  }

  esitoBloccoAnagraficaDomanda(data: Array<BloccoVO>) {
    if (data.length == 0) {
      this.successoBloccoDomanda = true;
    } else {
      this.successoBloccoDomanda = data.every(el => el.dataInserimentoSblocco != null)
      data.forEach(e1 => {
        if(e1.descCausaleBlocco == "Durc" && e1.dataInserimentoSblocco == null){
          this.bloccoDurc = true;
        }
        if(e1.descCausaleBlocco == "Antimafia" && e1.dataInserimentoSblocco == null && this.verificaAntimafia){
          this.bloccoAntimafia = true;
        }
      })
    }
  }

  abilitaButtonSalva() {
    return !(this.myForm.valid)
  }

  abilitaIterEsitoValidazione() {
    return (this.esitoAntimafia.descStatoRichiestaAntimafia == "IN ELABORAZIONE" || this.esitoDurc.descStatoRichiestaDurc == "IN ELABORAZIONE") && !this.iterEsitoValidazioneConcluso && !this.iterEsitoValidazioneInCorso && this.esitoDurc?.descEsitoDurc != 'DSAN';
  }

  abilitaIterCommunicazioneInterventoSost() {
    return this.idModalitaAgevolazioneRif == 1 && this.successoAntimafia && this.successoDeggendorf && this.bloccoDurc && !this.iterCommunicazioneInterventoConcluso && !this.iterCommunicazioneInterventoInCorso;
  }

  abilitaInterventoSostitutivo(){
    return this.idModalitaAgevolazioneRif == 1 && this.successoAntimafia && this.successoDeggendorf && this.bloccoDurc && this.iterCommunicazioneInterventoConcluso;
  }

  resetMessaggiResponse() {
    this.inviaRichiestaDurcResponse1 = false;
    this.inviaRichiestaDurcResponse2 = false;
    this.inviaRichiestaDurcResponse3 = false;
    this.inviaRichiestaAntimafiaResponse1 = false;
    this.inviaRichiestaAntimafiaResponse2 = false;
    this.inviaRichiestaAntimafiaResponse3 = false;
    this.inviaRichiestaAntimafiaResponse4 = false;
    this.error = false;
    this.success = false;
  }

}
