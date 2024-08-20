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
import { DatiDistintaVO } from '../../commons/dati-distinta-vo';
import { DettaglioDistintaVO } from '../../commons/dettaglio-distinta-vo';
import { DistintaPropostaErogazioneVO } from '../../commons/distinta-proposta-erogazione-vo';
import { EstremiBancariVO } from '../../commons/estremo-bancari-vo';
import { DistinteResponseService } from '../../services/distinte-response.service';
import { MatSort } from '@angular/material/sort';
import { ProgettoAllegatoVO } from '../../commons/progetto-allegato.vo';
import { Observable, forkJoin } from 'rxjs';
import { element } from 'protractor';

@Component({
  selector: 'app-copia-distinta',
  templateUrl: './copia-distinta.component.html',
  styleUrls: ['./copia-distinta.component.scss']
})
export class CopiaDistintaComponent implements OnInit {

  spinner: boolean = false;
  success: boolean = false;
  error: boolean = false;

  messaggioErrore: string;
  messaggioSuccesso: string;

  //pulsanti che abilitano/disabilitano i campi del form
  modDescrizione: boolean = true;
  modBanca: boolean = true;

  //allegati
  progettoAllegatoVOList: ProgettoAllegatoVO[];

  //parametro ricevuto
  idDistinta: string;

  //oggetto ricevuto
  dettaglioDistinta: DettaglioDistintaVO;
  bancaTesoriera: string;
  listaBancaTesoriera: EstremiBancariVO[] = [];

  verificaPosizioneAll: boolean = true;

  //tabella
  displayedColumns: string[] = ["progetto", "denominazione", "beneficiario", "importoLordo", "importoIres", "importoNetto", "dataConcessione", "dataEsitoDS", "verificaPosizione", "flagFinistr", "azioni"]; //"actions",
  listaOggetti: Array<DistintaPropostaErogazioneVO>;
  dataSource: MatTableDataSource<DistintaPropostaErogazioneVO>;
  selection = new SelectionModel<DistintaPropostaErogazioneVO>(true, []);

  //caricamento documento
  showCaricamentoDocumento: boolean = false;

  // FILE
  file: File;

  //form
  public myForm: FormGroup;

  idModalitaAgevolazione: string;
  idModalitaAgevolazioneRif: string;

  //Sort
  @ViewChild("matSortDistinte") matSortDistinte: MatSort;

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

    //creo il form
    this.myForm = this.fb.group({
      descrizioneDistinta: new FormControl({ disabled: true, value: '' }),
      bancaTesoriera: new FormControl({ disabled: true, value: '' })
    });

    //recupero parametri
    this.route.queryParams.subscribe(params => {
      this.idDistinta = params['idDistinta']
    });

    //fetchData
    if (this.idDistinta) {
      this.resService.fetchDataCopiaDistinte(this.idDistinta).subscribe(data => {
        console.log("FETCH DATA DETTAGLIO DISTINTA", data);

        //dettaglio distinta
        this.dettaglioDistinta = data;
        this.idModalitaAgevolazione = data.idModalitaAgevolazione;
        this.idModalitaAgevolazioneRif = data.idModalitaAgevolazioneRif;

        if(data?.estremiBancariList?.length > 0) {
          this.myForm.get("bancaTesoriera").setValue(data?.estremiBancariVO);
          this.listaBancaTesoriera = data?.estremiBancariList;
        }

        /*
        if (this.dettaglioDistinta.estremiBancariList?.length == 1) {
          this.myForm.get("bancaTesoriera").setValue(this.dettaglioDistinta.estremiBancariList[0]);
          this.bancaTesoriera = this.dettaglioDistinta.estremiBancariList[0].iban;
        }else {
          this.myForm.get("bancaTesoriera").setValue({});
          this.bancaTesoriera = "Nessuna banca trovata";
        }
        */

        this.myForm.get("descrizioneDistinta").setValue(this.dettaglioDistinta.descDistinta);

        //datasource
        this.listaOggetti = this.dettaglioDistinta.distintaPropostaErogazioneList;
        this.dataSource = new MatTableDataSource<DistintaPropostaErogazioneVO>(this.listaOggetti);
        this.dataSource.sort = this.matSortDistinte;
        this.listaOggetti.forEach(el => {
          if (el.verificaPosizione == false) {
            this.verificaPosizioneAll = false;
          }
        })

        //allegati
        this.progettoAllegatoVOList = data.progettoAllegatoVOList;
        this.progettoAllegatoVOList.forEach(element => {
          this.myForm.addControl("letteraAccompagnatoria" + element.codiceVisualizzatoProgetto, new FormControl(""));
          this.myForm.addControl("checklistInterna" + element.codiceVisualizzatoProgetto, new FormControl(""));
          this.myForm.addControl("reportValidazione" + element.codiceVisualizzatoProgetto, new FormControl(""));
          if(element.flagFinistr == "NO") {
            this.myForm.get("letteraAccompagnatoria" + element.codiceVisualizzatoProgetto).setValidators(Validators.required);
            this.myForm.get("checklistInterna" + element.codiceVisualizzatoProgetto).setValidators(Validators.required);
            this.myForm.updateValueAndValidity();
          }
        })

        this.spinner = false;
      }, err => {
        this.spinner = false;
        this.showMessageError("Errore durante il caricamento dei dati");
        this.handleExceptionService.handleNotBlockingError(err);
      });
    }
  }

  rimuoviProposta(idPropostaErogazione: string) : void {
    console.log("idProposta da rimuovere:", idPropostaErogazione);
    
    this.listaOggetti.splice(this.listaOggetti.findIndex(element => element.idPropostaErogazione == idPropostaErogazione), 1);
    this.dataSource._updateChangeSubscription()

    let toBeRemoved : ProgettoAllegatoVO[] = [];
    this.progettoAllegatoVOList.forEach(element => {
      let keep:boolean = false;
      this.listaOggetti.forEach(proposta => {
        if(proposta.idProgetto == element.idProgetto.toString()){
          keep = true;
        }
      })
      if(!keep){
        toBeRemoved.push(element);
      }
    })
    toBeRemoved.forEach(element => this.progettoAllegatoVOList.splice(this.progettoAllegatoVOList.findIndex(progettoAllegatoVO => progettoAllegatoVO == element), 1))
  }

  goBack() {
    this.removeMessageErrorAndSuccess();
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_CARICAMENTO_DISTINTE + "/caricamentoDistinte"], {});
  }

  //quando clicco su un progetto
  goToElencoDistinte(element: DistintaPropostaErogazioneVO) {
    this.removeMessageErrorAndSuccess();

    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_PROPOSTE + "/controlliPreErogazione"], {
      queryParams: {
        "idProposta": element.idPropostaErogazione
      }
    });
  }

  compareEstremiBancari(o1: any, o2: any): boolean {
    return o1 && o2 && o1.idEstremiBancari === o2.idEstremiBancari;
  }

  //allegati
  handleFileLetteraAccompagnatoria(codiceVisualizzato: string, files: Array<File>) {
    this.progettoAllegatoVOList.forEach(element => {
      if (element.codiceVisualizzatoProgetto == codiceVisualizzato) {
        element.letteraAccompagnatoria = files[0];
        element.visibilitaLettera = true;
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
        element.visibilitaChecklist = true;
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
    let listOfObservables : Observable<boolean>[] = [];
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
      if(avviaIter){
        this.avviaIterChiamata(idDistinta); //se devo avviare l'iter dopo l'aggiunta degli allegati avvio l'iter
      } else{
        setTimeout(() => {
          this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_ELENCO_DISTINTE + "/dettaglioDistinta"], {
            queryParams: {
              "idDistinta": idDistinta
            }
          });
        }, 2000);
      }
    });
    if(listOfObservables.length == 0 && !avviaIter){
      setTimeout(() => {
        this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_ELENCO_DISTINTE + "/dettaglioDistinta"], {
          queryParams: {
            "idDistinta": idDistinta
          }
        });
      }, 2000);   
    }
    if(listOfObservables.length == 0 && avviaIter){
      this.avviaIterChiamata(idDistinta); //se devo avviare l'iter dopo l'aggiunta degli allegati avvio l'iter
    }
  }

  salvaBozzaChiamata(avviaIter: boolean) : string {
    this.removeMessageErrorAndSuccess();
    this.spinner = true;

    //listaIdProposteErogazione
    let listaIdProposteErogazione: Array<string> = [];
    this.listaOggetti.forEach(element => {
      listaIdProposteErogazione.push(element.idPropostaErogazione);
    })
    console.log("listaIdProposteErogazione:", listaIdProposteErogazione);

    //idTipoDistinta
    let idTipoDistinta: number = this.sceltaIdTipoDistinta();

    let datiDistintaVO: DatiDistintaVO = {
      idTipoDistinta: idTipoDistinta.toString(),
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

          if (data == null) {
            this.showMessageSuccess("Avvia iter concluso correttamente correttamente")
          } else {
            this.showMessageError(data)
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

}
