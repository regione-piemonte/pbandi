/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, ElementRef, OnInit, ViewChild } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { ActivatedRoute, Router } from "@angular/router";
import { Constants, HandleExceptionService } from "@pbandi/common-lib";
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { UserService } from "src/app/core/services/user.service";
import { ModGarResponseService } from "../../../gestione-crediti/services/modgar-response-service.service";
import { SchedaClienteMain } from "../../../gestione-crediti/commons/dto/scheda-cliente-main";
import { MatTableDataSource } from "@angular/material/table";
import { FinanziamentoErogato } from "../../../gestione-crediti/commons/dto/finanziamento-erogato";
import { GesGarResponseService } from "../../../gestione-crediti/services/gesgar-response-service.service";
import { DialogEditModgarComponent } from "../../../gestione-crediti/components/dialog-edit-modgar/dialog-edit-modgar.component";
import { DialogHistoryEscussioneComponent } from "../../../gestione-crediti/components/dialog-history-escussione/dialog-history-escussione.component";
import { DialogHistoryStatocreditoComponent } from "../../../gestione-crediti/components/dialog-history-statocredito/dialog-history-statocredito.component";
import { DialogEditStatoescussioneComponent } from "../../../gestione-crediti/components/dialog-edit-statoescussione/dialog-edit-statoescussione.component";
import { FormGroup } from "@angular/forms";
import { DialogRichiestaIntegrazione } from "../../../gestione-crediti/components/dialog-richiesta-integrazione/dialog-richiesta-integrazione.component";
import { DialogInvioEsito } from "../../../gestione-crediti/components/dialog-invio-esito/dialog-invio-esito.component";
import { DialogInsertEscussione } from "../../../gestione-crediti/components/dialog-insert-escussione/dialog-insert-escussione.component";
import { NavigationGestioneGaranzieService } from "../../../gestione-crediti/services/navigation-gestione-garanzie.service";
import { DialogRevocaBancariaComponent } from "../../../gestione-crediti/components/dialog-revoca-bancaria/dialog-revoca-bancaria.component";
import { DialogAzioniRecuperoBanca } from "../../../gestione-crediti/components/dialog-azioni-recupero-banca/dialog-azioni-recupero-banca.component";
import { DialogSaldoStralcio } from "../../../gestione-crediti/components/dialog-saldo-stralcio/dialog-saldo-stralcio.component";
import { AllegatoVO } from "../../../gestione-crediti/commons/dto/allegatoVO";
import { ArchivioFileService } from '@pbandi/common-lib';
import { ConfigService } from "src/app/core/services/config.service";
import { saveAs } from 'file-saver-es';
import { DialogEliminazioneAllegato } from "../../../gestione-crediti/components/dialog-eliminazione-allegato/dialog-eliminazione-allegato.component";
import { Messaggio } from "../../../gestione-crediti/commons/dto/messaggio-dto";
import { Location } from '@angular/common';
import { GaranziaVO } from "src/app/gestione-garanzie/commons/garanzia-vo";
import { DettaglioGaranziaVO } from "src/app/gestione-garanzie/commons/dettaglio-garanzia-vo";
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from "@angular/material/sort/sort";
import { AllegatiControdeduzioniVO } from "src/app/gestione-controdeduzioni/common/dto/allegati-controdeduzioni.vo";
import { GestioneRiassicurazioniService } from "../../services/gestione-riassicurazioni.service";
import { initGestioneEscussioneRiassicurazioniVO } from "../../commons/dto/init-gestione-escussione-riassicurazioni-VO";
import { DialogModificaEscussioneRiassicurazioni } from "./dialog-modifica-escussione-riassicurazioni/dialog-modifica-escussione-riassicurazioni.component";
import { GestioneAllegatiVO } from "src/app/gestione-crediti/commons/dto/gestione-allegati-VO";
@Component({
    selector: 'app-gestione-escussione-riassicurazioni',
    templateUrl: './gestione-escussione-riassicurazioni.component.html',
    styleUrls: ['./gestione-escussione-riassicurazioni.component.scss'],
})

@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneEscussioneRiassicurazioniComponent implements OnInit {
    @ViewChild("garanziaPaginator") paginator: MatPaginator;
    @ViewChild("garanziaSort") sort: MatSort;
    @ViewChild('uploadFileEscussione')myFileEscussione: ElementRef;
    
    // Per le chiamate
    public subscribers: any = {};

    // Gestione componente
    idUtente: number;
    idEscussione: number = 0;
    idProgetto: number;
    importoDebitoResuduo: number = 10000;
    importoApprovatoAllaData: number = 0;
    isImportoMAxRaggiunto: boolean = false;
    canEditEscussione: boolean = true;

    // Spinner
    masterSpinnerIsSpinning: boolean = false;

    // Dati
    objEscussione: initGestioneEscussioneRiassicurazioniVO = new initGestioneEscussioneRiassicurazioniVO();
    colonneDettaglioRiassicurazioni: string[] = ['dataRicezione', 'numeroProtocollo', 'tipoEscussione', 'statoEscussione', 'importoRichiesto', 'importoApprovato', 'options'];


    // Messaggi
    showErrorEscussione: boolean = false;
    showWarningEscussione: boolean = false;
    showErrorAllegati: boolean = false;
    showSuccEscussione: boolean = false;
    showSuccAllegati: boolean = false;
    messageErrorEscussione: string = "";
    messageWarningEscussione: string = "";
    messageErrorAllegati: string = "";
    messageSuccEscussione: string = "";
    messageSuccAllegati: string = "";
    
    schedaClienteMain: SchedaClienteMain = new SchedaClienteMain;
    schedaClienteMain2: Array<SchedaClienteMain> = [];
    schedaClienteMain3: Array<SchedaClienteMain> = [];
    messaggio: Messaggio = new Messaggio;
    
    
    idSoggetto: string;
    codiceFiscale: string;
    codUtente: string;
    nomeFile: string;
    beneficiario = "";
    codBanca: string;
    showSucc: boolean = false;
    idTarget: number;
    

    showTrueError: boolean = false;
    showTrueSucc: boolean = false;
    succMsg: string = "Salvato."
    errorMsg: string = "Si è verificato un problema nel salvataggio dei dati."
    showError: boolean = false;
    showError2: boolean = false;
    messageError: string = "";

    fin: Array<FinanziamentoErogato>;
    displayedInnerColumns: string[] = ['tipoAgevolazione', 'importoAmmesso', 'totaleFondo', 'totaleBanca', 'dtConcessione', 'dtErogazioneFinanziamento', 'importoDebitoResiduo', 'importoEscusso', 'statoCredito', 'revocaBancaria', 'azioniRecuperoBanca', 'saldoEStralcio'];
    dataSource: MatTableDataSource<FinanziamentoErogato> = new MatTableDataSource<FinanziamentoErogato>([]);
    dataSource2: MatTableDataSource<DettaglioGaranziaVO> = new MatTableDataSource<DettaglioGaranziaVO>([]);
    storicoEscussione: Array<SchedaClienteMain> = [];
    storicoStatocredito: Array<SchedaClienteMain> = [];
    schedaStatoCredito: SchedaClienteMain = new SchedaClienteMain;
    file: File;
    allegati = [];
    numeroDomanda: any;
    nag: any;
    partitaIva: any;
    public myForm: FormGroup;
    thereIsEscussione: boolean = false;
    lastEscussioneSend: boolean = false;

    gliAllegati: Array<File> = new Array<File>();
    listDocIndexAllegatiPresenti:  Array<number> = new Array<number>();
    url = '';
    isClicked: boolean = true;
    isAcconto: boolean = false;
    garanziaVO: GaranziaVO = new GaranziaVO();
    descStatoCredito: any;
    result: boolean;
    infoEscussione;
    messageAllegato: string;
    erroreAllegato: boolean;
    error: boolean;
    allegatiDaInviare = [];

    constructor(
        private escussioneService: ModGarResponseService,
        private riassicurazioniService: GestioneRiassicurazioniService,
        public dialog: MatDialog,
        private route: ActivatedRoute,
        private userService: UserService,
        private router: Router,
        private handleExceptionService: HandleExceptionService,
        private archivioFileService: ArchivioFileService,
        private configService: ConfigService,
        private navigationService: NavigationGestioneGaranzieService,
        private garanzieService: GesGarResponseService,
        private location: Location
    ) { }

    ngOnInit(): void {
        //console.log(this.navigationService.filtroGestioneGaranzieSelezionato);
        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
            if (data) {
                this.idUtente = data.idUtente;
                this.masterSpinnerIsSpinning = true;

                this.idProgetto = +this.route.snapshot.queryParamMap.get('idProgetto');
                this.idEscussione = +this.route.snapshot.queryParamMap.get("idEscussione");

                this.caricaUltimaEscussione();

                //this.getAllegati();
            }
        }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
        });
    }



    caricaUltimaEscussione() {
        this.masterSpinnerIsSpinning = true;

        this.subscribers.initGestioneEscussioneRiassicurazioni = this.riassicurazioniService.initGestioneEscussioneRiassicurazioni(this.idProgetto, this.idEscussione).subscribe(data => {
            if (data) {
                this.objEscussione = data;

                this.masterSpinnerIsSpinning = false;
                
                this.checkImportiTotaliParziali()

            } else {
                this.masterSpinnerIsSpinning = false;
                this.showMessageErrorEscussione("Si è verificato un problema durante il caricamento dell'ultima Escussione, riprova più tardi.")
            }

        }, err => {
            this.masterSpinnerIsSpinning = false;
            this.handleExceptionService.handleBlockingError(err);
        })
    }

    checkImportiTotaliParziali() {
        // Blocco l'inserimento di tutte le escussioni

        let somma: number = 0;

        if (this.objEscussione.escussioniPassate && this.objEscussione.escussioniPassate.length > 0) {
            this.objEscussione.escussioniPassate.forEach(element => {
                if (element.escussione_importoApprovato) {
                    somma = somma + element.escussione_importoApprovato
                }
            });
        }

        this.importoApprovatoAllaData = somma;

        if(this.importoDebitoResuduo && this.importoDebitoResuduo > 0) {
            if(this.importoApprovatoAllaData >= this.importoDebitoResuduo) {
                this.canEditEscussione = false;
                this.showMessageWarningEscussione("Importo Debito residuo raggiunto");
            }
        }
    }

    /*isImportoRichiestoRaggiunto(): boolean {

        let importoRichiestoTemp: number = this.infoEscussione?.importoRichiesto;
        let importoAprovatoTemp: number = this.infoEscussione?.importoApprovato;

        if(importoRichiestoTemp != null && importoAprovatoTemp != null && importoRichiestoTemp != 0) {
            if(importoAprovatoTemp >= importoRichiestoTemp) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }*/

    getAllegati(){
        this.gliAllegati = [];
        this.listDocIndexAllegatiPresenti = []
        this.escussioneService.getAllegati(this.idEscussione+'').subscribe(data => {

            for (let x = 0; x < data.length; x++) {
                this.gliAllegati.push(data[x]);
                this.listDocIndexAllegatiPresenti.push(data[x].idDocIndex);
            }
            console.log("List docIndex allegati:", this.listDocIndexAllegatiPresenti);
        })
    }

    /*getGaranzia(idEscussione){
        this.masterSpinnerIsSpinning= true; 
        this.subscribers.getGaranzia = this.garanzieService.getGaranzia(this.idProgetto, this.idEscussione).subscribe(data => {
            if (data) {
                console.log("dettaglio arrivato")
                this.garanziaVO = data;
                this.infoEscussione = data;
                this.dataSource2 = new MatTableDataSource<DettaglioGaranziaVO>(this.garanziaVO.listaDettagli);
                console.log(this.dataSource2.data.length);
                //this.paginator.length = this.dataSource2.data.length;
                //this.paginator.pageIndex = 0;
                //this.dataSource2.paginator = this.paginator;
                this.dataSource2.sort = this.sort;
                this.descStatoCredito= this.garanziaVO.listaDettagli[0].statoCredito
                this.masterSpinnerIsSpinning= false;
            } else{
                this.masterSpinnerIsSpinning= false;
            }
        });
        this.getAllegati();
    }*/

    /*inserisciModificaEscussione(stepEscussione,idTipoEscussione,esitoInviato): void {

       let dialogRef = this.dialog.open(DialogInsertEscussione, {
            width: '50em',

            data: {
                idUtente: this.idUtente,
                schedaCliente: this.schedaClienteMain2[0],
                stepEscussione : stepEscussione,
                idTipoEscussione:idTipoEscussione,
                infoEscussione : this.infoEscussione,
                esitoInviato : esitoInviato,
                new: false,
                listaAllegatiPresenti: this.listDocIndexAllegatiPresenti
            }

        });

        dialogRef.afterClosed().subscribe(result => {
            if(result == undefined || result == null){
                return
            }else {
                this.idEscussione = result.idEscussione;
                this.masterSpinnerIsSpinning= true; 
                 this.getGaranzia(this.idEscussione)

                 this.router.navigate(
                    [], 
                    {
                      relativeTo: this.route,
                      queryParams: { idEscussione: this.idEscussione },
                      queryParamsHandling: 'merge'
                    });
            }

            

        })

    }*/

    /*nuovaEscussione(stepEscussione,idTipoEscussione,esitoInviato): void {

        let dialogRef = this.dialog.open(DialogInsertEscussione, {
             width: '50em',
 
             data: {
                 idUtente: this.idUtente,
                 schedaCliente: this.schedaClienteMain2[0],
                 stepEscussione : stepEscussione,
                 idTipoEscussione:idTipoEscussione,
                 infoEscussione : this.infoEscussione,
                 esitoInviato : esitoInviato,
                 new: true,
                 listaAllegatiPresenti: []
             }
 
         });
 
         dialogRef.afterClosed().subscribe(result => {
             if(result == undefined || result == null){
                 return
             }else {
                 this.idEscussione = result.idEscussione;
                 this.masterSpinnerIsSpinning= true; 
                  this.getGaranzia(this.idEscussione)
 
                  this.router.navigate(
                     [], 
                     {
                       relativeTo: this.route,
                       queryParams: { idEscussione: this.idEscussione },
                       queryParamsHandling: 'merge'
                     });
             }
 
             
 
         })
 
    }*/

    modificaEscussioneRiassicurazioni(isNuovaEscussione: boolean, isEscussioneParziale: boolean) { // New

        this.resetMessages();

        let dialogRef = this.dialog.open(DialogModificaEscussioneRiassicurazioni, {
            width: '50em',
            data: {
                dati: this.objEscussione,
                isEscussioneParziale: isEscussioneParziale,
                isNuovaEscussione: isNuovaEscussione,
                importoDebitoResuduo: this.importoDebitoResuduo
            }

        });

        dialogRef.afterClosed().subscribe(result => {
            console.log("DialogModificaEscussioneRiassicurazioni res: ", result)

            if(result) {
                if(isNuovaEscussione) {
                    this.showMessageSuccEscussione("Escussione inserita correttamente.");
                } else {
                    this.showMessageSuccEscussione("Escussione modificata correttamente.");
                }
                
                this.caricaUltimaEscussione();
                
            }
        })

    }


    modificaEscussionePassataRiassicurazioni(element: initGestioneEscussioneRiassicurazioniVO) {

        this.resetMessages();

        let dialogRef = this.dialog.open(DialogModificaEscussioneRiassicurazioni, {
            width: '50em',
            data: {
                dati: element,
                isEscussioneParziale: false,
                isNuovaEscussione: false,
                importoDebitoResuduo: this.importoDebitoResuduo
            }

        });

        dialogRef.afterClosed().subscribe(result => {
            console.log("DialogModificaEscussioneRiassicurazioni res: ", result)

            if(result) {
                this.showMessageSuccEscussione("Escussione modificata correttamente.");

                this.caricaUltimaEscussione();
            }
        })

    }
    

    richiediIntegrazione(): void {
        this.showSucc = false;
        this.showError = false;

        let dialogRef = this.dialog.open(DialogRichiestaIntegrazione, {
            width: '50em',
            data: {
                idUtente: this.idUtente,
                idEscussione: this.idEscussione
            }

        });

        dialogRef.afterClosed().subscribe(result => {
            this.caricaUltimaEscussione();
            //this.getGaranzia(this.idEscussione);

        });

    }

    inviaEsito(): void {
        this.showSucc = false;
        this.showError = false;

        let dialogRef = this.dialog.open(DialogInvioEsito, {
            //minHeight: '40vh',
            minWidth: '40vw',
            data: {
                idUtente: this.idUtente,
                idEscussione: this.objEscussione.escussione_idEscussioneCorrente,
                idStatoEscussione : this.objEscussione.escussione_idStatoEscussione,
                idProgetto: this.objEscussione.idProgetto
            }

        });

        dialogRef.afterClosed().subscribe(result => {
            this.caricaUltimaEscussione();
            
            //this.getGaranzia(this.idEscussione);
            /* if (result != undefined && result != "" && result.save == "save") {

                if (this.schedaClienteMain2[0].descTipoEscussione === "Acconto") {
                    this.isAcconto = true;
                }

                this.isClicked = false;
            } */

        })

    }

    cambiaStato(idStatoEscussione) {
        this.showSucc = false;
        this.showError = false;

        let dialogRef = this.dialog.open(DialogEditStatoescussioneComponent, {
            minWidth: '30vw',
            data: {
                idUtente: this.idUtente,
                idStatoEscussione : idStatoEscussione,
                idEscussione:this.idEscussione,
                infoEscussione : this.infoEscussione,
                listaAllegatiPresenti: this.listDocIndexAllegatiPresenti
            }

        });

        dialogRef.afterClosed().subscribe(result => {

            if(result == undefined || result == null){
                return
            }else {
                this.idEscussione = result.idEscussione;
                this.masterSpinnerIsSpinning= true; 
                this.caricaUltimaEscussione()
                 //this.getGaranzia(this.idEscussione);
                 /*this.router.navigate(
                    [], 
                    {
                      relativeTo: this.route,
                      queryParams: { idEscussione: this.idEscussione },
                      queryParamsHandling: 'merge'
                    });*/
            }
            /* if (result != undefined && result != "" && result.save == "save") {
                this.showError = false;
                this.showSucc = false;
                this.spinner = false;
                if (result.newSchedaCliente.descStatoEscussione === 'In istruttoria') {

                    this.escussioneService.updateStatoEscussione(result.newSchedaCliente).subscribe(data => {
                        if (data) {
                            this.showSucc = true;

                            this.escussioneService.getSchedaCliente(this.idUtente, this.idProgetto).subscribe(data => {
                                if (data) {
                                    this.schedaClienteMain = data;
                                    this.spinner = false;
                                } else {
                                    this.spinner = false;
                                }
                            }, err => {
                                this.spinner = false;
                                this.handleExceptionService.handleBlockingError(err);
                            });
                        }
                    }, err => {
                        this.spinner = false;
                        this.showError = true;
                    });

                } else {

                    this.escussioneService.updateStatoEscussioneRichIntegrazione(result.newSchedaCliente).subscribe(data => {
                        if (data) {
                            this.showSucc = true;
                            this.escussioneService.getSchedaCliente(this.idUtente, this.idProgetto).subscribe(data => {
                                if (data) {
                                    this.schedaClienteMain = data;
                                    this.spinner = false;
                                } else {
                                    this.spinner = false;
                                }
                            }, err => {
                                this.spinner = false;
                                this.handleExceptionService.handleBlockingError(err);
                            });
                        }
                    }, err => {
                        this.spinner = false;
                        this.showError = true;
                    });

                }

            } */

        });

    }

    openStoricoEscussione(): void {
        this.showSucc = false;
        this.showError = false;

        this.dialog.open(DialogHistoryEscussioneComponent, {
            minWidth: '60vw',

            data: {
                info : this.objEscussione
            }

        });

    }
    
   

    openRevocaBancaria(idProgetto: any, totFin: any, totBan: any) {

        var totEro: number = Number(totFin) + Number(totBan);

        this.dialog.open(DialogRevocaBancariaComponent, {
            width: '800px',

            data: {
                idProgetto, totFin, totEro
            }

        });

    }

    openAzioniRecuperoBanca(idProgetto: any, totFin: any, totBan: any) {

        var totEro: number = Number(totFin) + Number(totBan);

        this.dialog.open(DialogAzioniRecuperoBanca, {
            width: '800px',

            data: {
                idProgetto, totFin, totEro
            }

        });

    }

    openSaldoStralcio(idProgetto: any, totFin: any, totBan: any) {

        var totEro: number = Number(totFin) + Number(totBan);

        this.dialog.open(DialogSaldoStralcio, {
            width: '800px',

            data: {
                idProgetto, totFin, totEro
            }

        });

    }

    /*resetAllMsgs() {
        this.showTrueError = false;
        this.showTrueSucc = false;
    }*/


    salvaFileEscussione(idProgetto: any) {
            this.escussioneService.inserisciAllegatiEscussione(this.allegati, this.objEscussione.escussione_idEscussioneCorrente).subscribe(data => {

                /*if (data.code  == 'OK') {
                    this.messageAllegato = data.message;
                    this.erroreAllegato = false;
                    setTimeout(() => {
                      this.erroreAllegato = null;
                      
                      this.getAllegati();
                    }, 2000); 
                  }
                } else if (data.code == 'KO') {
                    //this.erroreAllegato = true;
                    //this.messageAllegato = "Non è stato possibile inserire l'allegato";
                }*/
                
                this.allegati = [];

                this.caricaUltimaEscussione();
            })
    }

   
    downloadAllegato(files: GestioneAllegatiVO) {

        this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), files.idDocumentoIndex.toString()).subscribe(res => {

            if (res) {
                saveAs(res, files.nomeFile);
            }

        });

    }

    previewAllegato(blob) {
        var fileURL: any = URL.createObjectURL(blob);
        var a = document.createElement("a");
        a.href = fileURL;
        a.target = '_blank';
        a.click();
    }


    handleFileEscussione(files: Array<File>) {
        console.log("files[0]");
        this.resetMessages();
        //if (!files[0].name.endsWith(".pdf") && !files[0].name.endsWith(".PDF") && !files[0].name.endsWith(".DOC") && !files[0].name.endsWith(".doc") && !files[0].name.endsWith(".zip") && !files[0].name.endsWith(".ZIP")) {
          //this.showMessageError("Il file deve avere estensione .pdf oppure .doc oppure .zip");
        //} 
        //else {
            this.allegati.push(files[0]);
            console.log(this.allegati);
            
        //}
        console.log("CHECKLIST INTERNA CARICATA", this.allegati);
    }

    clearInputElement() {
        this.myFileEscussione.nativeElement.value = ''
      }
    
    eliminaFileEscussioneLocale(i) {
        this.allegati.splice(i, 1);
        this.clearInputElement();
    }

    eliminaFileEscussione(idDocIndex: number): void {
        this.showSucc = false;
        this.showError = false;

        let dialogRef = this.dialog.open(DialogEliminazioneAllegato, {
            width: '480px',
            data: {
                idDocIndex: idDocIndex,
                idUtente: this.idUtente,
                idEscussione: this.idEscussione,
                schedaCliente: this.schedaClienteMain2[0]
            }

        });

        dialogRef.afterClosed().subscribe(result => {
            this.clearInputElement();
            this.allegati = [];
            this.gliAllegati = [];
            this.listDocIndexAllegatiPresenti = [];
            this.getAllegati();

        })

    }
    /*showErrorEscussione: boolean = false;
    showErrorAllegati: boolean = false;
    showSuccEscussione: boolean = false;
    showSuccAllegati: boolean = false;
    messageSuccEscussione: string = "";
    messageSuccAllegati: string = ""; */

    showMessageErrorEscussione(msg: string) {
        this.resetMessages();
        this.messageErrorEscussione = msg;
        this.showErrorEscussione = true;
        //const element = document.querySelector('#scrollId');
        //element.scrollIntoView();
    }
    
    showMessageWarningEscussione(msg: string) {
        this.resetMessages();
        this.messageWarningEscussione = msg;
        this.showWarningEscussione = true;
        //const element = document.querySelector('#scrollId');
        //element.scrollIntoView();
    }

    showMessageErrorAllegati(msg: string) {
        this.resetMessages();
        this.messageErrorAllegati = msg;
        this.showErrorAllegati = true;
        //const element = document.querySelector('#scrollId');
        //element.scrollIntoView();
    }

    showMessageSuccEscussione(msg: string) {
        this.resetMessages();
        this.messageSuccEscussione = msg;
        this.showSuccEscussione = true;
        //const element = document.querySelector('#scrollId');
        //element.scrollIntoView();
    }

    showMessageSuccAllegati(msg: string) {
        this.resetMessages();
        this.messageSuccAllegati = msg;
        this.showSuccAllegati = true;
        //const element = document.querySelector('#scrollId');
        //element.scrollIntoView();
    }

    resetMessages() {
        this.messageErrorEscussione = "";
        this.messageWarningEscussione = "";
        this.messageErrorAllegati = "";
        this.messageSuccEscussione = "";
        this.messageSuccAllegati = "";
        this.showErrorEscussione = false;
        this.showWarningEscussione = false;
        this.showErrorAllegati = false;
        this.showSuccEscussione = false;
        this.showSuccAllegati = false
    }

    goBack() {
        this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_GESTIONE_RIASSICURAZIONI + "/gestioneRiassicurazioni"], { queryParams: {} });
    }

    /* openModifica(): void {
        this.showSucc = false;
        this.showError = false;
        this.showError2 = false;

        let dialogRef = this.dialog.open(DialogEditModgarComponent, {
            width: '550px',
            data: {
                idUtente: this.idUtente,
                schedaCliente: this.schedaClienteMain2[0],
            }

        });

        dialogRef.afterClosed().subscribe(result => {

            if (result != undefined && result != "" && result.save == "save") {
                this.showError = false;
                this.showSucc = false;
                this.showError2 = false;
                this.spinner = false;

                this.resService.updateEscussione(result.newSchedaCliente).subscribe(data => {

                    if (data) {
                        this.showSucc = true;
                        this.messaggio = data;

                        if (this.messaggio.messaggio) {
                            this.showError2 = true;
                            this.messageError = this.messaggio.messaggio;
                        }

                        this.resService.getSchedaCliente(this.idUtente, this.idProgetto).subscribe(data => {

                            if (data) {
                                this.schedaClienteMain = data;
                                this.spinner = false;
                            } else {
                                this.spinner = false;
                            }
                        }, err => {
                            this.spinner = false;
                            this.handleExceptionService.handleBlockingError(err);
                        });

                    }
                }, err => {
                    this.spinner = false;
                    this.showError = true;
                });

            }

        });

    } */

}