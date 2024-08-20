/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DenominazioneSuggestVO } from '../../commons/denominazione-suggest-vo';
import { DistintaPropostaErogazioneVO } from '../../commons/distinta-proposta-erogazione-vo';
import { EstremiBancariVO } from '../../commons/estremo-bancari-vo';
import { FiltroProposteErogazioneDistVO } from '../../commons/filtro-proposte-erogazione-dist-vo';
import { ProgettoSuggestVO } from '../../commons/progetto-suggest-vo';
import { DistinteResponseService } from '../../services/distinte-response.service';
import { DatiDistintaVO } from '../../commons/dati-distinta-vo';
import { log } from 'console';
import { MatSort } from '@angular/material/sort';
import { AllegatoVO } from '../../commons/allegato-vo';
import { ProgettoAllegatoVO } from '../../commons/progetto-allegato.vo';
import { Observable, forkJoin } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-crea-distinta',
  templateUrl: './crea-distinta.component.html',
  styleUrls: ['./crea-distinta.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class CreaDistintaComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;

  spinner: boolean = false;
  success: boolean = false;
  error: boolean = false;

  messaggioErrore: string;
  messaggioSuccesso: string;

  //form
  public myForm: FormGroup;

  //suggerimenti form
  suggDenominazione: DenominazioneSuggestVO[] = [];
  suggProgetto: ProgettoSuggestVO[] = [];

  //valori ottenuti dall'URL
  idBando: string;
  idModalitaAgevolazione: string
  idModalitaAgevolazioneRif: string
  bancaTesoriera: string;
  listaBancaTesoriera: EstremiBancariVO[] = [];

  //oggetti ottenuti da fetch data
  titoloBando: string;
  descModalitaAgevolazione: string;
  descDistinta: string;

  //variabile per disattivare o meno "avvia iter"
  isAbilitatoAvvioIter: boolean;
  isAbilitatoSalvaInBozza: boolean;

  //allegati
  progettoAllegatoVOList: ProgettoAllegatoVO[] = [];

  //tabella
  displayedColumns: string[] = ["actions", "codiceVisualizzato", "denominazione", "codiceFiscaleSoggetto", "importoLordo", "importoIres", "importoNetto", "dataConcessione", "dataEsitoDS", "verificaPosizione", "flagFinistr"];
  listaOggetti: Array<DistintaPropostaErogazioneVO>;
  dataSource: MatTableDataSource<DistintaPropostaErogazioneVO>;
  selection = new SelectionModel<DistintaPropostaErogazioneVO>(true, []);

  //caricamento documento
  showCaricamentoDocumento: boolean = false;

  //Sort
  @ViewChild("matSortDistinte") matSortDistinte: MatSort;

  subscribers: any = {};
  flagEscussione: boolean = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private resService: DistinteResponseService,
    private handleExceptionService: HandleExceptionService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.spinner = true;
    this.removeMessageErrorAndSuccess();

    //recupero idBando e idModalitaAgevolazione
    this.route.queryParams.subscribe(params => {
      this.idBando = params['idBando'],
        this.idModalitaAgevolazioneRif = params['idModalitaAgevolazione'],
        this.flagEscussione = params['flagEscussione']==='true';

        //creo form
        this.myForm = this.fb.group({
          descrizioneDistinta: new FormControl(""),
          denominazione: new FormControl(""),
          codiceProgetto: new FormControl(""),
          dataConcessioneInizio: new FormControl(""),
          dataConcessioneFine: new FormControl(""),
          dataEsitoDSInizio: new FormControl(""),
          dataEsitoDSFine: new FormControl(""),
          bancaTesoriera: new FormControl("", Validators.required),
          flagEscussione: new FormControl({ value:'' , disabled: true })
        });
        
    });

    this.myForm.get("flagEscussione").setValue(this.flagEscussione);


    //fetchData
    this.resService.fetchData(this.idBando, this.idModalitaAgevolazioneRif).subscribe(data => {
      console.log("FETCH DATA", data);

      this.titoloBando = data.titoloBando;
      this.idModalitaAgevolazione = data.idModalitaAgevolazione;
      this.descModalitaAgevolazione = data.descModalitaAgevolazione;
      this.descDistinta = data.descDistinta;

      if(data.estremiBancariList?.length > 0) {
        this.listaBancaTesoriera = data.estremiBancariList;
      }
      if(data.estremiBancariList?.length == 1) {
        this.myForm.get("bancaTesoriera").setValue(data.estremiBancariList[0])
      }

      /*
      if (data.estremiBancariList?.length >= 1) {
        this.myForm.get("bancaTesoriera").setValue(data.estremiBancariList[0]);
        this.bancaTesoriera = data.estremiBancariList[0].iban;
      }else {
        this.myForm.get("bancaTesoriera").setValue({});
        this.bancaTesoriera = "Nessuna banca trovata";
      }
      */

      this.spinner = false;

    }, err => {

      this.spinner = false;
      this.showMessageError("Errore durante il caricamento delle informazioni.");
      this.handleExceptionService.handleNotBlockingError(err);

    });

    //recupero l'utente loggato
    this.resService.isAbilitatoAvvioIter().subscribe(data => {
      console.log("isAbilitatoAvvioIter:", data);
      this.isAbilitatoAvvioIter = data;

      this.spinner = false;

    }, err => {

      this.spinner = false;
      this.showMessageError("Errore durante il caricamento delle informazioni.");
      this.handleExceptionService.handleNotBlockingError(err);

    });

  }

  goBack() {
    this.removeMessageErrorAndSuccess();
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_CARICAMENTO_DISTINTE + "/caricamentoDistinte"], {});
  };

  //quando clicco su un progetto
  goToElencoDistinte(element: DistintaPropostaErogazioneVO) {
    this.removeMessageErrorAndSuccess();

    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_PROPOSTE + "/controlliPreErogazione"], {
      queryParams: {
        "idProposta": element.idPropostaErogazione
      }
    });
  }

  applicaFiltri() {
    this.spinner = true;
    this.removeMessageErrorAndSuccess();

    let formControls = this.myForm.getRawValue();
    //creo oggetto FiltroProposteErogazioneDistVO
    let listInput: FiltroProposteErogazioneDistVO = new FiltroProposteErogazioneDistVO();
    listInput.codiceFondoFinpis = formControls.bancaTesoriera?.codiceFondoFinpis;
    listInput.codiceProgetto = formControls.codiceProgetto.codiceVisualizzato;
    listInput.denominazione = formControls.denominazione.denominazione;
    listInput.dtConcessioneFrom = formControls.dataConcessioneInizio;
    listInput.dtConcessioneTo = formControls.dataConcessioneFine;
    listInput.dtValidazioneSpesaFrom = formControls.dataEsitoDSInizio;
    listInput.dtValidazioneSpesaTo = formControls.dataEsitoDSFine;
    listInput.idBando = this.idBando;
    listInput.idModalitaAgevolazione = this.idModalitaAgevolazioneRif;
    listInput.flagFinistr = (this.idModalitaAgevolazioneRif == '10' && !this.flagEscussione)? 'SI' : 'NO'
    console.log("STAMPA LISTINPUT");
    console.table(listInput);

    //effettuo la chiamata al BE
    this.resService.getProposteErogazione(listInput).subscribe(data => {
      console.log("GET PROPOSTE EROGAZIONE", data)
      this.spinner = false;

      if (data) {
        this.listaOggetti = data;
        this.dataSource = new MatTableDataSource<DistintaPropostaErogazioneVO>(this.listaOggetti.filter(element => element.abilitata));
        this.dataSource.sort = this.matSortDistinte;

        this.paginator.length = this.listaOggetti.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;

      } else {
        //se codiceFondoFinpis NON è ancora stato inserito
        if (listInput.codiceFondoFinpis == undefined || listInput.codiceFondoFinpis == null) {
          this.showMessageError("Non sono state trovate delle proposte di erogazione che hanno " +
            "superato i controlli di pre-erogazione e che appartengono allo stesso bando/modalità " +
            "di agevolazione");
        } else {
          this.showMessageError("Non sono state trovate delle proposte di erogazione che hanno " +
            "superato i controlli di pre-erogazione e che appartengono allo stesso bando/modalità " +
            "di agevolazione ed hanno lo stesso codice fondo FINPIS");
        }
      }

    }, err => {
      this.spinner = false;
      this.showMessageError("Errore durante la ricerca delle proposte di erogazione da inserire nella distinta.");
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }

  suggDen(value: string) {
    this.removeMessageErrorAndSuccess();
    if (value.length >= 3) {
      this.suggDenominazione = [];
      this.suggDenominazione[0] = {
        idSoggetto: "",
        denominazione: "Caricamento...",
        codiceFiscaleSoggetto: ''
      };

      this.resService.suggestDenominazione(value).subscribe(data => {
        if (data.length > 0) {
          console.table(data);
          this.suggDenominazione = data;
        } else {
          this.suggDenominazione[0] =
          {
            idSoggetto: "",
            denominazione: "Nessuna corrispondenza",
            codiceFiscaleSoggetto: ''
          }
        }
      })
    } else {
      this.suggDenominazione = []
    }
  }

  suggProg(value: string) {
    this.removeMessageErrorAndSuccess();
    if (value.length >= 1) {
      this.suggProgetto = [];
      this.suggProgetto[0] = {
        idProgetto: "",
        codiceVisualizzato: "Caricamento..."
      };

      this.resService.suggestProgetto(this.idBando, value).subscribe(data => {
        if (data.length > 0) {
          console.table(data);
          this.suggProgetto = data;
        } else {
          this.suggProgetto[0] =
          {
            idProgetto: "",
            codiceVisualizzato: "Nessuna corrispondenza"
          }
        }
      })
    } else {
      this.suggProgetto = []
    }
  }

  //Metodo per autocomplete
  displayDenominazione(element: DenominazioneSuggestVO): string {
    return element && element.denominazione ? element.denominazione : '';
  }
  //Metodo per autocomplete
  displayProgetto(element: ProgettoSuggestVO): string {
    return element && element.codiceVisualizzato ? element.codiceVisualizzato : '';
  }

  //allegati

  handleFileLetteraAccompagnatoria(codiceVisualizzato: string, files: Array<File>) {
    this.progettoAllegatoVOList.forEach(element => {
      if (element.codiceVisualizzatoProgetto == codiceVisualizzato) {
        element.visibilitaLettera = true;
        element.letteraAccompagnatoria = files[0];
        console.log("Lettera accompagnatoria caricata", element.letteraAccompagnatoria)
      }
    });
  }
  eliminaLetteraAccompagnatoria(codiceVisualizzato: string, uploadFile: any) {
    this.progettoAllegatoVOList.forEach(element => {
      if (element.codiceVisualizzatoProgetto == codiceVisualizzato) {
        if (element.letteraAccompagnatoria?.idDocumentoIndex != null) {
          this.resService.deleteFile(element.letteraAccompagnatoria.idDocumentoIndex).subscribe();
        }
        element.letteraAccompagnatoria = null;
      }
    });
    uploadFile.value = '';
  }
  handleFileChecklistInterna(codiceVisualizzato: string, files: Array<File>) {
    this.progettoAllegatoVOList.forEach(element => {
      if (element.codiceVisualizzatoProgetto == codiceVisualizzato) {
        element.visibilitaChecklist = false;
        element.checklistInterna = files[0];
        console.log("Checklist interna caricata", element.checklistInterna);
      }
    });
  }
  eliminaChecklistInterna(codiceVisualizzato: string, uploadFile2: any) {
    this.progettoAllegatoVOList.forEach(element => {
      if (element.codiceVisualizzatoProgetto == codiceVisualizzato) {
        if (element.checklistInterna?.idDocumentoIndex != null) {
          this.resService.deleteFile(element.checklistInterna.idDocumentoIndex).subscribe();
        }
        element.checklistInterna = null;
      }
    });
    uploadFile2.value = '';
  }
  handleFileReportValidazione(codiceVisualizzato: string, files: Array<File>) {
    this.progettoAllegatoVOList.forEach(element => {
      if (element.codiceVisualizzatoProgetto == codiceVisualizzato) {
        element.visibilitaReport = true;
        element.reportValidazione = files[0];
        console.log("Report validazione caricata", element.reportValidazione);
      }
    });
  }
  eliminaReportValidazione(codiceVisualizzato: string, uploadFile3: any) {
    this.progettoAllegatoVOList.forEach(element => {
      if (element.codiceVisualizzatoProgetto == codiceVisualizzato) {
        if (element.reportValidazione?.idDocumentoIndex != null) {
          this.resService.deleteFile(element.reportValidazione.idDocumentoIndex).subscribe();
        }
        element.reportValidazione = null;
      }
    });
    uploadFile3.value = '';
  }
  
  toggleVisibilita(element: ProgettoAllegatoVO, ambito: number) {
    switch(ambito) {
      case 1: {
        element.visibilitaLettera = !element.visibilitaLettera;
        break;
      }
      case 2: {
        element.visibilitaReport = !element.visibilitaReport;
        break;
      }
      case 3: {
        element.visibilitaChecklist = !element.visibilitaChecklist;
        break;
      }
    }
  }

  salvaAllegati(idDistinta: string, avviaIter: boolean) {
    let listOfObservables: Observable<boolean>[] = [];
    this.progettoAllegatoVOList.forEach(element => {
      if (element.letteraAccompagnatoria instanceof File) {
        listOfObservables.push(this.resService.uploadFile(element.letteraAccompagnatoria, element.visibilitaLettera, 42, idDistinta, element.idProgetto));
      }
      if (element.checklistInterna instanceof File) {
        listOfObservables.push(this.resService.uploadFile(element.checklistInterna, element.visibilitaChecklist, 33, idDistinta, element.idProgetto));
      }
      if (element.reportValidazione instanceof File) {
        listOfObservables.push(this.resService.uploadFile(element.reportValidazione, element.visibilitaReport, 63, idDistinta, element.idProgetto));
      }
    });
    forkJoin(listOfObservables).subscribe(element => {
      if (avviaIter) {
        this.avviaIterChiamata(idDistinta); //se devo avviare l'iter dopo l'aggiunta degli allegati avvio l'iter
      } else {
        setTimeout(() => {
          this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_ELENCO_DISTINTE + "/dettaglioDistinta"], {
            queryParams: {
              "idDistinta": idDistinta
            }
          });
        }, 2000);
      }
    });
    if (listOfObservables.length == 0 && !avviaIter) {
      setTimeout(() => {
        this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_ELENCO_DISTINTE + "/dettaglioDistinta"], {
          queryParams: {
            "idDistinta": idDistinta
          }
        });
      }, 2000);
    }
    if (listOfObservables.length == 0 && avviaIter) {
      this.avviaIterChiamata(idDistinta); //se devo avviare l'iter dopo l'aggiunta degli allegati avvio l'iter
    }
  }

  salvaBozzaChiamata(avviaIter: boolean): string {
    this.removeMessageErrorAndSuccess();
    this.spinner = true;

    //listaIdProposteErogazione
    let listaIdProposteErogazione: Array<string> = [];
    this.selection.selected.forEach(element => {
      listaIdProposteErogazione.push(element.idPropostaErogazione);
    });
    console.log("listaIdProposteErogazione:", listaIdProposteErogazione);

    //idTipoDistinta
    let idTipoDistinta: number = this.sceltaIdTipoDistinta();

    let datiDistintaVO: DatiDistintaVO = {
      idTipoDistinta: idTipoDistinta.toString(), // questo parametro deve essere uguale a 1 quando 
      idModalitaAgevolazione: this.idModalitaAgevolazione,
      descrizione: this.myForm.get("descrizioneDistinta")?.value,
      idEstremiBancari: this.myForm.get('bancaTesoriera')?.value?.idEstremiBancari,

      listaIdProposteErogazione: listaIdProposteErogazione
    };

    if (!datiDistintaVO.descrizione) {
      this.spinner = false;
      this.showMessageError("Inserire la descrizione per salvare la distinta!");
    } else {
      this.resService.salvaInBozza(datiDistintaVO).subscribe(data => {
        console.log("DATA:", data);
        this.spinner = false;

        if (data) {
          if(!avviaIter) {
            this.showMessageSuccess("Salvataggio in bozza avvenuto con successo!");
          }          
          this.salvaAllegati(data, avviaIter); //dopo aver salvato la distinta aggiungo gli allegati
        } else {
          this.showMessageError("Non è stato possibile effettuare il salvataggio in bozza!");
        }

      }, err => {
        this.spinner = false;
        this.showMessageError("Errore durante il salvataggio in bozza!");
        this.handleExceptionService.handleNotBlockingError(err);
      });
    }
    return null;
  }

  avviaIterChiamata(idDistinta: string) {
    this.resService.checkAllegati(idDistinta).subscribe(data => {
      if (data) {
        this.resService.avviaIter(idDistinta).subscribe(data => {
          console.log("DATA:", data);
          this.spinner = false;

          if (!data) {
            this.isAbilitatoAvvioIter = false;
            this.isAbilitatoSalvaInBozza = false;
            this.showMessageSuccess("Avvio iter autorizzativo avvenuto con successo!");
          } else {
            this.showMessageError("Non è stato possibile avviare l’iter autorizzativo!");
          }

          setTimeout(() => {
            this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_ELENCO_DISTINTE + "/dettaglioDistinta"], {
              queryParams: {
                "idDistinta": idDistinta
              }
            });
          }, 2000);

        }, err => {
          this.spinner = false;
          this.showMessageError("Errore durante l'avvio dell'iter autorizzativo!");
          this.handleExceptionService.handleNotBlockingError(err);
          
          setTimeout(() => {
            this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_ELENCO_DISTINTE + "/dettaglioDistinta"], {
              queryParams: {
                "idDistinta": idDistinta
              }
            });
          }, 2000);
        });
      } else {
        this.spinner = false;
        this.showMessageError("Verificare gli allegati!");
      }
    });
  }

  salvaBozza() {
    this.removeMessageErrorAndSuccess();
    this.salvaBozzaChiamata(false);
  }

  avviaIter() {
    this.removeMessageErrorAndSuccess();
    this.spinner = true;

    if (!this.myForm.get("descrizioneDistinta")?.value) {
      this.spinner = false;
      this.showMessageError("Inserire la descrizione per avviare l'iter!");
    } else {
      this.salvaBozzaChiamata(true);
    }
  }

  sceltaIdTipoDistinta() {
    if (this.idModalitaAgevolazioneRif == '1') {
      return 6;
    }
    if (this.idModalitaAgevolazioneRif == '5') {
      return 7;
    }
    if (this.idModalitaAgevolazioneRif == '10') {
      if(this.flagEscussione){
        return 1;
      }
      return 8;
    }

  }


  showMessageError(mex: string) {
    this.error = true;
    this.messaggioErrore = mex;
  }
  showMessageSuccess(mex: string) {
    this.success = true;
    this.messaggioSuccesso = mex;
  }
  removeMessageErrorAndSuccess() {
    this.error = false;
    this.messaggioErrore = "";
    this.success = false;
    this.messaggioSuccesso = "";
  }

  aggiornaListaProgetti() {
    //per ogni elemento selezionato
    this.selection.selected.forEach(element => {
      //se il progetto non appartiene alla lista dei progetti
      if (this.progettoAllegatoVOList.find(object => object.idProgetto.toString() == element.idProgetto) == undefined) {
        //lo aggiungo
        let progettoAllegatoVO: ProgettoAllegatoVO = new ProgettoAllegatoVO();
        progettoAllegatoVO.idProgetto = Number(element.idProgetto);
        progettoAllegatoVO.codiceVisualizzatoProgetto = element.codiceVisualizzato;
        progettoAllegatoVO.flagFinistr = element.flagFinistr;
        this.progettoAllegatoVOList.push(progettoAllegatoVO);
        //aggiorno formControl
        this.myForm.addControl("letteraAccompagnatoria" + element.codiceVisualizzato, new FormControl(""));
          this.myForm.addControl("checklistInterna" + element.codiceVisualizzato, new FormControl(""));
          this.myForm.addControl("reportValidazione" + element.codiceVisualizzato, new FormControl(""));
          if(element.flagFinistr == "NO") {
            this.myForm.get("letteraAccompagnatoria" + element.codiceVisualizzato).setValidators(Validators.required);
            this.myForm.get("checklistInterna" + element.codiceVisualizzato).setValidators(Validators.required);
            this.myForm.updateValueAndValidity();
          }
      }
    });
    //per ogni elemento nella lista dei progetti
    this.progettoAllegatoVOList.forEach(element => {
      //se non trovo almeno un elemento nella lista dei selezionati con lo stesso progetto
      if (this.selection.selected.find(object => object.idProgetto == element.idProgetto.toString()) == undefined) {
        //lo rimuovo
        this.progettoAllegatoVOList = this.progettoAllegatoVOList.filter(elem => elem.idProgetto != element.idProgetto);
        //rimuovo formControl
        if(element.flagFinistr != "SI") {
          this.myForm.removeControl("letteraAccompagnatoria" + element.codiceVisualizzatoProgetto);
          this.myForm.removeControl("checklistInterna" + element.codiceVisualizzatoProgetto);
          this.myForm.removeControl("reportValidazione" + element.codiceVisualizzatoProgetto);
        }
      }
    });

  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    let numRows: number = 0;
    this.dataSource.data.forEach(row => {
      if (row.abilitata && row.verificaPosizione) {
        numRows++;
      }
    })
    let numSelected: number = this.selection.selected.length;

    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.dataSource.data.forEach(row => {
        if (row.abilitata && row.verificaPosizione) {
          this.selection.deselect(row);
        }
      }) :
      this.dataSource.data.forEach(row => {
        if (row.abilitata && row.verificaPosizione) {
          this.selection.select(row);
        }
      });
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: DistintaPropostaErogazioneVO): string {
    if (!row) {
      return "${this.isAllSelected() ? 'select' : 'deselect'} all";
    }
    return "${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.position + 1}";
  }

}
