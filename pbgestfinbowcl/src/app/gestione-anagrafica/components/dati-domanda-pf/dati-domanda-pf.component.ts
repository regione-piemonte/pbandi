/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Sort } from '@angular/material/sort';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { DatiDomanda } from '../../commons/dto/dati-domanda';
import { SoggettoCorrelatoVO } from '../../commons/dto/soggetto-correlatoVO';
import { DatiDomandaService } from '../../services/dati-domanda-service';
import { AnagraficaBeneficiarioService } from '../../services/anagrafica-beneficiario.service';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

@Component({
  selector: 'app-dati-domanda-pf',
  templateUrl: './dati-domanda-pf.component.html',
  styleUrls: ['./dati-domanda-pf.component.scss']
})
export class DatiDomandaPFComponent implements OnInit {

  public myForm: FormGroup;

  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;
  idVersione: any;
  idSoggetto: any;
  idDomanda: any;
  tipoSoggetto: any;
  spinner: boolean;
  dati: DatiDomanda;
  datiPF: DatiDomanda;
  isPresentata: boolean

  public subscribers: any = {};
  ////sogg corr
  listaSoggettiCorr: Array<SoggettoCorrelatoVO> = new Array<SoggettoCorrelatoVO>();
  isSoggettiCorr: boolean;
  sortedData: SoggettoCorrelatoVO[];
  displayedColumnsSogg: string[] = ['tipologia', 'nag', 'denom', 'codiceFisc', 'ruolo', 'action'];
  setBackText: string = "Torna ai dati generali del beneficiario"
  isProgetto: boolean;
  editForm: boolean;
  ndg: string;
  idProgetto: string;
  isMessageErrorVisible: boolean;
  messageError: string;
  messageSuccess: any;
  isMessageSuccessVisible: boolean;
  user: UserInfoSec;
  idUtente: any;
  tipoDocumentoData: AttivitaDTO[];
  denominazione: string;
  codiceFiscale: string;
  nome: string;
  cognome: string;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private datiDomandaService: DatiDomandaService,
    private userService: UserService,
    private router: Router,
    private handleExceptionService: HandleExceptionService,
    private anagBeneService: AnagraficaBeneficiarioService) { }

  ngOnInit() {
    this.spinner = true;
    this.tipoSoggetto = this.route.snapshot.queryParamMap.get('tipoSoggetto');
    this.idVersione = this.route.snapshot.queryParamMap.get('idVersione');
    this.idDomanda = this.route.snapshot.queryParamMap.get('idDomanda');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.ndg = this.route.snapshot.queryParamMap.get('ndg');
    this.idProgetto = this.route.snapshot.queryParamMap.get('idProgetto');
    this.nome = this.route.snapshot.queryParamMap.get('nome');
    this.cognome = this.route.snapshot.queryParamMap.get('cognome');
    this.codiceFiscale = this.route.snapshot.queryParamMap.get('codiceFiscale');
    this.denominazione = (this.cognome!=null)?this.cognome: '-' ;
    this.denominazione +=' '
    this.denominazione += (this.nome!=null)?this.nome:'-';    

    this.subscribers.idUtente = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
      }
    });

    this.myForm = this.fb.group({
      //STATO DOMANDA
      numeroDomanda: new FormControl({ value: '', disabled: true }),
      statoDomanda: new FormControl({ value: '', disabled: true }),
      dataPresentazioneDomanda: new FormControl({ value: '', disabled: true }),
      //DOCUMENTO IDENTITA'
      documentoIdentita: new FormControl({ value: '', disabled: true }),
      numeroDocumento: new FormControl({ value: '', disabled: true }),
      dataRilascio: new FormControl({ value: '', disabled: true }),
      enteRilascio: new FormControl({ value: '', disabled: true }),
      scadenzaDocumento: new FormControl({ value: '', disabled: true }),
      //RECAPITI
      telefono: new FormControl({ value: '', disabled: true }),
      fax: new FormControl({ value: '', disabled: true }),
      email: new FormControl({ value: '', disabled: true }),
      pec: new FormControl({ value: '', disabled: true }),
      //CONTO CORRENTE
      numeroConto: new FormControl({ value: '', disabled: true }),
      iban: new FormControl({ value: '', disabled: true }),
      //BANCA DI APPOGGIO
      banca: new FormControl({ value: '', disabled: true }),
      abi: new FormControl({ value: '', disabled: true }),
      cab: new FormControl({ value: '', disabled: true }),
    });



    this.datiDomandaService.getDatiDomandaPF(this.idSoggetto, this.idDomanda).subscribe(data => {
      if (data) {
        this.dati = data;

        this.datiPF = this.dati[0];

        this.myForm.setValue({
          //STATO DOMANDA
          numeroDomanda: this.dati[0].numeroDomanda,
          statoDomanda: this.dati[0].statoDomanda,
          dataPresentazioneDomanda: new Date(Date.parse(this.dati[0].dataPresentazioneDomanda)),
          //DOCUMENTO IDENTITA'
          documentoIdentita: this.dati[0].documentoIdentita,
          numeroDocumento: this.dati[0].numeroDocumento,
          dataRilascio: new Date(Date.parse(this.dati[0].dataRilascio)),
          enteRilascio: this.dati[0].enteRilascio,
          scadenzaDocumento: (this.dati[0].scadenzaDocumento) ? new Date(this.dati[0].scadenzaDocumento) : '',
          //RECAPITI
          telefono: this.dati[0].telefono,
          fax: this.controlloCampoValido(this.dati[0].fax) ? this.dati[0].fax : '',
          email: this.dati[0].email,
          pec: this.dati[0].pec,
          //CONTO CORRENTE
          numeroConto: this.dati[0].numeroConto,
          iban: this.dati[0].iban,
          //BANCA DI APPOGGIO
          banca: this.controlloCampoValido(this.dati[0].banca) ? this.dati[0].banca : '',
          abi: this.dati[0].abi,
          cab: this.dati[0].cab,
        });
        if (this.dati[0].statoDomanda == "Presentata") {
          this.isPresentata = true;
        }
        if (this.dati[0].idProgetto != null) {
          this.isProgetto = true
        }


        this.subscribers.getElencoTipoDocumento = this.anagBeneService.getElencoTipoDocumento().subscribe(data => {
          if (data) {
            this.tipoDocumentoData = data;
            if (this.myForm && this.dati[0].idTipoDocumentoIdentita) {
              this.myForm.get("documentoIdentita").setValue(this.tipoDocumentoData.find(t => t.idAttivita === this.dati[0].idTipoDocumentoIdentita));
            }
          }
          // this.loadedFormeGiuridiche = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di caricamento del tipo anagrafica");
          // this.loadedFormeGiuridiche = true;

        });

        this.spinner = false;


      }
    });



    this.getElencoSoggettiCorrelati();
  }

  //FUNZIONE CHE PERMETTE DI MODIFICARE I CAMPI DELLA DOMANDA/PROGETTO SELEZIONATA DALL'ELENCO
  // configuraBandoLinea() {
  //   this.router.navigate(["/drawer/" + this.idOp + "/modificaDatiDomandaPF"], { queryParams: { idSoggetto: this.idSoggetto, idDomanda: this.idDomanda } });
  // }

  editMyForm() {
    this.myForm.enable();
    this.myForm.get("numeroDomanda").disable();
    this.myForm.get("statoDomanda").disable();
    this.myForm.get("dataPresentazioneDomanda").disable();
    this.myForm.get("banca").disable();
    this.myForm.get("abi").disable();
    this.myForm.get("iban").disable();
    this.myForm.get("pec").disable();
    this.myForm.get("email").disable();
    this.myForm.get("fax").disable();
    // this.myForm.get("scadenzaDocumento").disable();
    // this.myForm.get("enteRilascio").disable();
    // this.myForm.get("dataRilascio").disable();
    // this.myForm.get("numeroDocumento").disable();
    // this.myForm.get("documentoIdentita").disable();
    this.editForm = true;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }
  showMessageSuccess(msg: string) {
    this.isMessageSuccessVisible = true;
    this.messageSuccess = msg;
  }
  setDatiDomanda() {
    this.resetMessageError();
    this.resetMessageSuccess();
    let isModifica: boolean;
    // Recapiti
    if (this.myForm.get("telefono").value && (this.myForm.get("telefono").value) != this.datiPF.telefono) {
      this.datiPF.telefono = this.myForm.get("telefono").value;
      isModifica = true;
    }
    if (this.myForm.get("email").value && this.myForm.get("email").value != this.datiPF.email) {
      this.datiPF.email = this.myForm.get("email").value;
      isModifica = true;
    }
    if (this.myForm.get("fax").value && this.myForm.get("fax").value != this.datiPF.fax) {
      this.datiPF.fax = this.myForm.get("fax").value;
      isModifica = true;
    }
    if(this.myForm.get("pec").value){
      this.datiPF.pec = this.myForm.get("pec").value;
    }


    // documento identità 
    if (this.myForm.get("scadenzaDocumento").value && this.myForm.get("scadenzaDocumento").value != this.dati[0].scadenzaDocumento) {
      this.datiPF.scadenzaDocumento = this.myForm.get("scadenzaDocumento").value;
      isModifica = true;
    }
    if (this.myForm.get("enteRilascio").value && this.myForm.get("enteRilascio").value != this.dati[0].enteRilascio) {
      this.datiPF.enteRilascio = this.myForm.get("enteRilascio").value;
      isModifica = true;
    }
    if (this.myForm.get("dataRilascio").value && this.myForm.get("dataRilascio").value != this.dati[0].dataRilascio) {
      this.datiPF.dataRilascio = this.myForm.get("dataRilascio").value;
      isModifica = true;
    }
    if (this.myForm.get("numeroDocumento").value && this.myForm.get("numeroDocumento").value != this.dati[0].numeroDocumento) {
      this.datiPF.numeroDocumento = this.myForm.get("numeroDocumento").value;
      isModifica = true;
    }
    if (this.myForm.get("documentoIdentita").value && this.myForm.get("documentoIdentita").value.idAttivita != this.dati[0].idTipoDocumentoIdentita) {
      this.datiPF.idTipoDocumentoIdentita = this.myForm.get("documentoIdentita").value.idAttivita;
      isModifica = true;
    }


    if (isModifica) {
      this.salvaModifiche()
      //  console.log(this.myForm.value);

    } else {
      this.showMessageError("Modificare almeno un campo");
    }
  }

  annullaModifica() {
    this.myForm.disable();
    this.editForm = false;
    this.ngOnInit();
  }

  //FUNZIONE CHE MI CONTROLLA CHE UN CAMPO SIA DIVERSO DA NULL E STRINGA VUOTA
  controlloCampoValido(valore) {
    return valore != null && valore != '';
  }

  getElencoSoggettiCorrelati() {
    this.spinner = true;
    this.subscribers.getElencoSoggCorrDipDaDomanda = this.datiDomandaService.getElencoSoggCorrDipDaDomanda(this.idDomanda, this.idSoggetto).subscribe(data => {
      if (data.length > 0) {
        this.listaSoggettiCorr = data;
        this.isSoggettiCorr = true
        this.sortedData = data;
        this.spinner = false;
      } else {
        this.spinner = false;
        this.isSoggettiCorr = false;
      }
    })
  }

  visualizzaSoggettoCorr(soggetto: SoggettoCorrelatoVO) {
    // mi porto al componente per vizualizzare i dato del soggetto correlato a persona fisica con parametro 1 corrispondente a PF 
    if (soggetto.tipologia == "Persona Giuridica") {
      this.router.navigate(["/drawer/" + this.idOp + "/visualizzaSoggCorrDipDom"],
        {
          queryParams: {
            idSoggetto: this.idSoggetto,
            numeroDomanda: this.idDomanda,
            idDomanda: soggetto.idDomanda,
            tipoSoggetto: this.tipoSoggetto,
            idSoggettoCorr: soggetto.nag,
            tipoSoggettoCorr: soggetto.tipologia,
            idSoggCorr: soggetto.idSoggettoCorellato,
            idEnteGiuridico: this.idVersione,
          }
        });
    } else {
      this.router.navigate(["/drawer/" + this.idOp + "/visualizzaSoggCorrDipDomPf"],
        {
          queryParams: {
            idSoggetto: this.idSoggetto,
            numeroDomanda: this.idDomanda,
            idDomanda: soggetto.idDomanda,
            tipoSoggetto: this.tipoSoggetto,
            idSoggettoCorr: soggetto.nag,
            tipoSoggettoCorr: soggetto.tipologia,
            idSoggCorr: soggetto.idSoggettoCorellato,
            idEnteGiuridico: this.idVersione,
          }
        });
    }
  }

  sortData(sort: Sort) {

    const data = this.sortedData.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }
    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'tipologia':
          return compare(a.tipologia, b.tipologia, isAsc);
        case 'nag':
          return compare(a.nag, b.nag, isAsc);
        case 'denom':
          return compare(a.cognome + '' + a.nome, b.cognome + '' + b.nome, isAsc);
        case 'codiceFisc':
          return compare(a.codiceFiscale, b.codiceFiscale, isAsc);
        case 'ruolo':
          return compare(a.ruolo, b.ruolo, isAsc);
        default:
          return 0;
      }
    });
  }

  salvaModifiche() {

    this.subscribers.salvaModifiche = this.datiDomandaService.salvaDatiDomandaPersFisica(this.datiPF, this.idUtente).subscribe(data => {
      if (data) {
        this.showMessageSuccess("Salvataggio avvenuto con succeso!");
        setTimeout(() => {
          this.annullaModifica();
        }, 3000);
      } else {
        this.showMessageError("Errore salvataggio!")
      }
    });

  }
}

function compare(a: number | string | Date, b: number | string | Date, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}