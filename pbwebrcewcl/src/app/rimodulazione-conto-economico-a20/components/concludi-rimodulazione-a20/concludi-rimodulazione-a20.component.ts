/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { FinanziamentoFonteFinanziaria } from 'src/app/rimodulazione-conto-economico/commons/dto/finanziamento-fonte-finanziaria';
import { ModalitaAgevolazione } from 'src/app/rimodulazione-conto-economico/commons/dto/modalita-agevolazione';
import { SalvaRimodulazioneConfermataRequest } from 'src/app/rimodulazione-conto-economico/commons/request/salva-rimodulazione-confermata-request';
import { ValidaRimodulazioneConfermataRequest } from 'src/app/rimodulazione-conto-economico/commons/request/valida-rimodulazione-confermata-request';
import { ModificaFonteFinanziariaDialogComponent } from 'src/app/rimodulazione-conto-economico/components/modifica-fonte-finanziaria-dialog/modifica-fonte-finanziaria-dialog.component';
import { RimodulazioneContoEconomicoService } from 'src/app/rimodulazione-conto-economico/services/rimodulazione-conto-economico.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { TabellaVociDiSpesa, mapToTabellaVociDiSpesa } from '../../commons/conto-economico-vo';

const PERIODO_CODICE_UNICO: number = 1;
@Component({
  selector: 'app-concludi-rimodulazione-a20',
  templateUrl: './concludi-rimodulazione-a20.component.html',
  styleUrls: ['./concludi-rimodulazione-a20.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ConcludiRimodulazioneA20Component implements OnInit {

  idOperazione: number;
  idProgetto: number;
  idBando: number;
  idContoEconomico: number;
  isIstruttoria: boolean = false;
  isBandoCultura: boolean;
  idRouting: number;
  codiceProgetto: string;
  user: UserInfoSec;
  totaleUltimaSpesaAmmessa: number;
  totaleSpesaAmmessaInRimodulazione: number;
  importoUltimaPropostaCertificazionePerProgetto: number;
  importoFinanziamentoBancario: number;
  modalita: Array<ModalitaAgevolazione>;
  fontiFiltrate: Array<FinanziamentoFonteFinanziaria>;
  listaFonti: Array<FinanziamentoFonteFinanziaria>;
  note: string;
  importoImpegnoFormatted: string;
  importoImpegno: number;
  data: FormControl = new FormControl();
  riferimento: string;
  isValidated: boolean;
  
  //SPESE
  totaleSpeseEffettive: number;


  displayedColumnsModalitaRimod: string[] = ['modalita', 'max', 'perc1', 'ultimoRichiesto', 'erogato', 'ultimoAgevolato', 'perc2', 'nuovoAgevolato', 'perc3'];
  displayedColumnsModalitaIstr: string[] = ['modalita', 'max', 'perc1', 'ultimoRichiesto', 'nuovoAgevolato', 'perc3'];
  displayedColumnsModalita: string[] = [];
  dataSourceModalita: MatTableDataSource<ModalitaAgevolazione> = new MatTableDataSource<ModalitaAgevolazione>([]);

  displayedColumnsFonti: string[] = ['fonti', 'quota', 'perc', 'azioni'];
  dataSourceFonti: MatTableDataSource<FinanziamentoFonteFinanziaria> = new MatTableDataSource<FinanziamentoFonteFinanziaria>([]);

  @ViewChild('modalitaForm', { static: true }) modalitaForm: NgForm;
  @ViewChild('infoForm', { static: true }) infoForm: NgForm;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;

  //LOADED
  loadedInizializza: boolean;
  loadedChiudiAttivita: boolean = true;
  loadedSalva: boolean = true;
  loadedConferma: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private sharedService: SharedService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    private rimodulazioneContoEconomicoService: RimodulazioneContoEconomicoService,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog) { }

  ngOnInit(): void {
		this.subscribers.router = this.activatedRoute.parent.params.subscribe(params => {
			this.idRouting = params.id;
			if (params.id == Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RIMODULAZIONE_CONTO_ECONOMICO_CULTURA) {
				this.isBandoCultura = true;
			}
		});
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
      if (this.idOperazione === Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RIMODULAZIONE_CONTO_ECONOMICO_ISTRUTTORIA) {
        this.isIstruttoria = true;
        this.displayedColumnsModalita = this.displayedColumnsModalitaIstr;
      } else {
        this.displayedColumnsModalita = this.displayedColumnsModalitaRimod;
      }

      this.subscribers.router = this.activatedRoute.params.subscribe(params => {
        this.idProgetto = +params['idProgetto'];
        this.idBando = +params['idBando'];
        this.idContoEconomico = +params['idContoEconomico'];
        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
          if (data) {
            this.user = data;
            this.loadedInizializza = false;
            this.subscribers.init = this.rimodulazioneContoEconomicoService.inizializzaConcludiRimodulazione(this.idProgetto, this.isIstruttoria, this.idBando).subscribe(data => {
              if (data) {
                this.getSpesa();
                this.codiceProgetto = data.codiceVisualizzatoProgetto;
                this.totaleUltimaSpesaAmmessa = data.totaleUltimaSpesaAmmessa;
                this.totaleSpesaAmmessaInRimodulazione = data.totaleSpesaAmmessaInRimodulazione;
                this.importoUltimaPropostaCertificazionePerProgetto = data.importoUltimaPropostaCertificazionePerProgetto;
                this.importoFinanziamentoBancario = data.importoFinanziamentoBancario;
                this.modalita = data.listaModalitaAgevolazione;
                if (this.modalita && this.modalita.length > 0) {
                  this.modalita.forEach(m => {
                    m.importoAgevolatoFormatted = m.importoAgevolato ? this.sharedService.formatValue(m.importoAgevolato.toString()) : "0,00";
                    m.percentualeImportoAgevolatoFormatted = m.percentualeImportoAgevolato ? this.sharedService.formatValue(m.percentualeImportoAgevolato.toString()) : "0,00";
                  })
                  this.dataSourceModalita = new MatTableDataSource<ModalitaAgevolazione>(this.modalita);
                }
                this.fontiFiltrate = data.fontiFiltrate;
                let totale = this.fontiFiltrate.find(f => f.isFonteTotale);
                totale.descFonteFinanziaria = totale.descFonteFinanziaria.toUpperCase();
                let fontiF = this.fontiFiltrate.filter(f => !(f.descPeriodo && !f.idFonteFinanziaria)); //rimuovo la riga del periodo
                this.dataSourceFonti = new MatTableDataSource<FinanziamentoFonteFinanziaria>(fontiF);
                this.listaFonti = data.listaFonti;
                this.importoImpegno = data.importoImpegnoGiuridico;
                if (this.importoImpegno !== null && this.importoImpegno !== undefined) {
                  this.importoImpegnoFormatted = this.sharedService.formatValue(this.importoImpegno.toString());
                }
                if (data.dataConcessione) {
                  this.data.setValue(new Date(data.dataConcessione));
                }
              }
              this.loadedInizializza = true;
            }, err => {
              this.handleExceptionService.handleBlockingError(err);
            });
          }
        });
      });
    });
  }

  formatImportoAgevolato(item: ModalitaAgevolazione) {
    if (item.importoAgevolatoFormatted) {
      item.importoAgevolato = this.sharedService.getNumberFromFormattedString(item.importoAgevolatoFormatted);
      if (item.importoAgevolato !== null) {
        item.importoAgevolatoFormatted = this.sharedService.formatValue(item.importoAgevolato.toString());
      }
    }
  }

  calcolaTotPercImportoAgevolato(item?: ModalitaAgevolazione, importoNuovo?: number) {
    let modTotale = this.modalita.find(m => m.isModalitaTotale);
    modTotale.importoAgevolato -= item.importoAgevolato;
    item.importoAgevolato = importoNuovo;
    item.importoAgevolatoFormatted = importoNuovo !== null ? importoNuovo.toString().replace(".", ",") : null; //non formatto sul ngModelChange, altrimenti non si riesce a scrivere
    modTotale.importoAgevolato += item.importoAgevolato;
    modTotale.importoAgevolatoFormatted = this.sharedService.formatValue(modTotale.importoAgevolato.toString());
    if(this.isBandoCultura){
      console.log("TOTALE ITEM: ", this.totaleSpeseEffettive)
      item.percentualeImportoAgevolato = this.totaleSpeseEffettive * (item.importoAgevolato / 100)  ;
    }else{
      item.percentualeImportoAgevolato = item.importoAgevolato * 100 / this.totaleSpesaAmmessaInRimodulazione;
    }
    item.percentualeImportoAgevolatoFormatted = this.sharedService.formatValue(item.percentualeImportoAgevolato.toString());
    if(this.isBandoCultura){
      console.log("TOTALE MOD: ", this.totaleSpeseEffettive) 
      modTotale.percentualeImportoAgevolato = this.totaleSpeseEffettive * (modTotale.importoAgevolato / 100);
    }else{
      modTotale.percentualeImportoAgevolato = modTotale.importoAgevolato * 100 / this.totaleSpesaAmmessaInRimodulazione;
    }
    modTotale.percentualeImportoAgevolatoFormatted = this.sharedService.formatValue(modTotale.percentualeImportoAgevolato.toString());
  }

  modelChangeImportoAgevolato(inputValue: string, item: ModalitaAgevolazione, index: number) {
    if (!this.modalitaForm.form.get('importoAgevolato' + index).hasError('pattern')) {
      this.calcolaTotPercImportoAgevolato(item, this.sharedService.getNumberFromFormattedString(inputValue));
    }
  }

  changeImportoAgevolato(item: ModalitaAgevolazione, index: number) {
    if (!this.modalitaForm.form.get('importoAgevolato' + index).hasError('pattern')) {
      this.formatImportoAgevolato(item);
      this.calcolaTotPercImportoAgevolato();
    }
  }

  findFonte(idFonteFinanziaria: number, idPeriodo: number, fonteModificata: FinanziamentoFonteFinanziaria) {
    if (idFonteFinanziaria != null && idPeriodo != null && this.listaFonti != null) {
      let fonte = this.listaFonti.find(f => f.idFonteFinanziaria != null && !f.isFonteTotale && !f.isSubTotale
        && f.idFonteFinanziaria === idFonteFinanziaria && (f.idPeriodo === idPeriodo || PERIODO_CODICE_UNICO === idPeriodo));
      fonte.estremiProvvedimento = fonteModificata.estremiProvvedimento;
      fonte.importoQuota = fonteModificata.importoQuota;
      fonte.percentualeQuota = fonteModificata.percentualeQuota;
      if (fonte.progrProgSoggFinanziat == null) {
        fonte.flagLvlprj = true;
      }
    }
  }

  modificaFonte(fonte: FinanziamentoFonteFinanziaria) {
    this.resetMessageWarning();
    this.resetMessageSuccess();
    this.resetMessageError();
    let error: boolean;
    let i: number = 0;
    for (let mod of this.modalita.filter(m => !m.isModalitaTotale)) {
      if (mod.importoAgevolato !== null && mod.importoAgevolato <= 0) {
        this.modalitaForm.form.get('importoAgevolato' + i).setErrors({ error: 'minoreUgualeZero' });
        this.modalitaForm.form.get('importoAgevolato' + i).markAsTouched();
        error = true;
      }
      i++;
    }
    if (error) {
      this.showMessageWarning("Attenzione! inserire il nuovo importo agevolato.");
      return;
    }
    let dialogRef = this.dialog.open(ModificaFonteFinanziariaDialogComponent, {
      width: '50%',
      maxHeight: '90%',
      data: {
        descFonteFinanziaria: fonte.descFonteFinanziaria,
        importoQuota: fonte.importoQuota,
        percentualeQuota: fonte.percentualeQuota,
        estremiProvvedimento: fonte.estremiProvvedimento,
        importoAgevolato: this.totaleSpesaAmmessaInRimodulazione
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        fonte.estremiProvvedimento = res.estremiProvvedimento;
        fonte.importoQuota = res.importoQuota;
        fonte.percentualeQuota = res.percentualeQuota;
        if (fonte.progrProgSoggFinanziat == null) {
          fonte.flagLvlprj = true;
        }
        //aggiorno corrispettivo in listaFonti
        this.findFonte(fonte.idFonteFinanziaria, fonte.idPeriodo, res);

        //aggiorno totale e subtotali
        let sommaImp: number = 0;
        let sommaPerc: number = 0;
        let sommaImpSub: number = 0;
        let sommaPercSub: number = 0;
        for (let fon of this.fontiFiltrate) {
          if (fon.idFonteFinanziaria) {
            sommaImp += fon.importoQuota;
            sommaPerc += fon.percentualeQuota;
            sommaImpSub += fon.importoQuota;
            sommaPercSub += fon.percentualeQuota;
          } else if (fon.isSubTotale) {
            fon.importoQuota = sommaImpSub;
            fon.percentualeQuota = sommaPercSub;
            sommaImpSub = 0;
            sommaPercSub = 0;
          }
        }
        let totale = this.fontiFiltrate.find(f => f.isFonteTotale);
        totale.importoQuota = sommaImp;
        totale.percentualeQuota = sommaPerc;
        //aggiorno corrispettivi in listaFonti
        let totaleL = this.listaFonti.find(f => f.isFonteTotale);
        totaleL.importoQuota = sommaImp;
        totaleL.percentualeQuota = sommaPerc;
      }
    });
  }

  salva() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.resetMessageWarning();
    let error: boolean;
    let message: string = "ATTENZIONE! Risultano errati alcuni campi.";
    if (!this.isIstruttoria) {
      if (this.importoImpegno === null || this.importoImpegno === undefined) {
        this.infoForm.form.get('importoImpegno').setErrors({ error: 'required' });
        this.infoForm.form.get('importoImpegno').markAsTouched();
        error = true;
      }
      if (this.importoImpegno < 0) {
        this.infoForm.form.get('importoImpegno').setErrors({ error: 'minore' });
        this.infoForm.form.get('importoImpegno').markAsTouched();
        error = true;
      }
      if (this.importoImpegno > this.totaleSpesaAmmessaInRimodulazione) {
        this.infoForm.form.get('importoImpegno').setErrors({ error: 'maggiore' });
        this.infoForm.form.get('importoImpegno').markAsTouched();
        error = true;
      }
    }
    if (!this.data || this.data.value === null) {
      this.data.setErrors({ error: 'required' });
      this.data.markAsTouched();
      error = true;
    }
    if (!this.riferimento || this.riferimento.length === 0) {
      this.infoForm.form.get('riferimento').setErrors({ error: 'required' });
      this.infoForm.form.get('riferimento').markAsTouched();
      error = true;
    }
    let mod = this.modalita.filter(m => !m.isModalitaTotale);
    for (let i = 0; i < mod.length; i++) {
      if (mod[i].importoAgevolato === null) {
        this.modalitaForm.form.get('importoAgevolato' + i).setErrors({ error: 'required' });
        this.modalitaForm.form.get('importoAgevolato' + i).markAsTouched();
        error = true;
      }
      if (mod[i].importoAgevolato < 0) {
        this.modalitaForm.form.get('importoAgevolato' + i).setErrors({ error: 'minore' });
        this.modalitaForm.form.get('importoAgevolato' + i).markAsTouched();
        error = true;
      }
      if (mod[i].importoAgevolato > 999999999999999.9) {
        this.modalitaForm.form.get('importoAgevolato' + i).setErrors({ error: 'maggiore' });
        this.modalitaForm.form.get('importoAgevolato' + i).markAsTouched();
        error = true;
      }
    }
    let tot = this.modalita.find(m => m.isModalitaTotale);
    if (tot.importoAgevolato > this.totaleSpesaAmmessaInRimodulazione) {
      //      message += "<br />Il totale dell'agevolato non può essere minore dell'ultimo importo netto certificato che, per il progetto corrente, è di "
      //        + this.importoUltimaPropostaCertificazionePerProgetto + ". <br />Contattare l'Autorità di Gestione di riferimento per procedere con una eventuale soppressione."
      message += "<br />Importo della spesa ammessa in rimodulazione, che per il progetto corrente è di " + this.totaleSpesaAmmessaInRimodulazione
        + " , non può essere minore dell'importo agevolato totale.";

      error = true;
    }
    if (!error) {
      this.loadedSalva = false;
      let mod = new Array<ModalitaAgevolazione>();
      this.modalita.forEach(m => mod.push(Object.assign({}, m)));
      mod.forEach(r => {
        //campi usati in concludi rimodulazione
        delete r['importoAgevolatoFormatted'];
        delete r['percentualeImportoAgevolatoFormatted'];
      });
      let request = new ValidaRimodulazioneConfermataRequest(mod, this.fontiFiltrate, this.totaleSpesaAmmessaInRimodulazione, this.idBando, this.isIstruttoria);
      this.subscribers.valida = this.rimodulazioneContoEconomicoService.validaRimodulazioneConfermata(request).subscribe(data => {
        if (data) {
          if (data.esito) {
            this.conferma();
          } else {
            let messaggio: string = '';
            if (data.messaggi && data.messaggi.length > 0) {
              data.messaggi.forEach(m => {
                messaggio += m + "<br/>";
              });
              messaggio = messaggio.substr(0, messaggio.length - 5);
            }
            if (data.erroreBloccante) {
              if (messaggio.length > 0) {
                this.showMessageError(messaggio);
              }
            } else {
              this.modalita = data.listaModalitaAgevolazione;
              this.modalita.forEach(m => {
                m.importoAgevolatoFormatted = m.importoAgevolato ? this.sharedService.formatValue(m.importoAgevolato.toString()) : "0,00";
                m.percentualeImportoAgevolatoFormatted = m.percentualeImportoAgevolato ? this.sharedService.formatValue(m.percentualeImportoAgevolato.toString()) : "0,00";
              })
              this.dataSourceModalita = new MatTableDataSource<ModalitaAgevolazione>(this.modalita);
              if (messaggio.length > 0) {
                this.showMessageWarning(messaggio);
              }
              this.isValidated = true;
            }
          }
        }
        this.loadedSalva = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di validazione.");
        this.loadedSalva = true;
      });
    } else {
      this.showMessageError(message);
    }
  }

  conferma() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.resetMessageWarning();
    this.loadedConferma = false;
    let mod = new Array<ModalitaAgevolazione>();
    this.modalita.forEach(m => mod.push(Object.assign({}, m)));
    mod.forEach(r => {
      //campi usati in concludi rimodulazione
      delete r['importoAgevolatoFormatted'];
      delete r['percentualeImportoAgevolatoFormatted'];
    });
    let request = new SalvaRimodulazioneConfermataRequest(this.idProgetto, this.idContoEconomico, this.user.beneficiarioSelezionato.idBeneficiario, mod, this.listaFonti,
      this.importoImpegno, this.importoFinanziamentoBancario, this.data.value, this.riferimento, this.note);
    this.subscribers.conferma = this.rimodulazioneContoEconomicoService.salvaRimodulazioneConfermata(request, this.isIstruttoria).subscribe(data => {
      if (data) {
        if (data.esito) {
          data.nuovoIdContoEconomico = data.nuovoIdContoEconomico ? data.nuovoIdContoEconomico : 0;
          this.router.navigate([`drawer/${this.idOperazione}/rimodulazioneContoEconomico/`
            + `${this.idProgetto}/${this.idBando}/${data.nuovoIdContoEconomico}`]);
        } else {
          let messaggio: string = '';
          if (data.messaggi && data.messaggi.length > 0) {
            data.messaggi.forEach(m => {
              messaggio += m + "<br/>";
            });
            messaggio = messaggio.substr(0, messaggio.length - 5);
          }
          if (messaggio.length > 0) {
            this.showMessageError(messaggio);
          }
        }
      }
      this.loadedConferma = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedConferma = true;
      this.showMessageError("Errore in fase di salvataggio.");
    });
  }

  indietro() {
    if (!this.isValidated) {
      this.router.navigate([`drawer/${this.idOperazione}/rimodulazioneContoEconomico/${this.idProgetto}/${this.idBando}`]);
    } else {
      this.isValidated = false;
      this.resetMessageWarning();
    }
  }

  setImportoImpegno() {
    this.importoImpegno = this.sharedService.getNumberFromFormattedString(this.importoImpegnoFormatted);
    if (this.importoImpegno !== null) {
      this.importoImpegnoFormatted = this.sharedService.formatValue(this.importoImpegno.toString());
    }
  }

  getDescBreveTask() {
    let descBreveTask: string;
    switch (this.idOperazione) {
      case Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RIMODULAZIONE_CONTO_ECONOMICO_CULTURA:
        descBreveTask = Constants.DESCR_BREVE_TASK_RIMODULAZIONE_CONTO_ECONOMICO_CULTURA;
        break;
      case Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RIMODULAZIONE_CONTO_ECONOMICO_ISTRUTTORIA:
        descBreveTask = Constants.DESCR_BREVE_TASK_RIMODULAZIONE_CONTO_ECONOMICO_ISTRUTTORIA;
        break;
      default:
        break;
    }
    return descBreveTask;
  }

  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, this.getDescBreveTask()).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
  }

  goToDatiProgettoEAttivitaPregresse() {
    this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
      width: '90%',
      maxHeight: '95%',
      data: {
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

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
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

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  getSpesa(){
    this.rimodulazioneContoEconomicoService.getSpesa(this.idProgetto).subscribe(data => {
    if (data) {
      this.totaleSpeseEffettive = mapToTabellaVociDiSpesa(data).totaleSpeseEffettive
    }
  });
  }

  isLoading() {
    if (!this.loadedChiudiAttivita || !this.loadedInizializza || !this.loadedSalva || !this.loadedConferma) {
      return true;
    }
    return false;
  }

}
