/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, Input, OnInit, ViewChild } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { ActivatedRoute, Router } from "@angular/router";
import { HandleExceptionService, UserInfoSec } from "@pbandi/common-lib";
import { UserService } from "src/app/core/services/user.service";
import { SharedService } from "src/app/shared/services/shared.service";
import { RevocaBancariaDTO } from "../../commons/dto/revoca-bancaria-dto";
import { SchedaClienteMain } from "../../commons/dto/scheda-cliente-main";
import { StoricoRevocaDTO } from "../../commons/dto/storico-revoca-dto";
import { RevocaBancariaService } from "../../services/revoca-bancaria.service";

@Component({
    selector: 'dialog-revoca-bancaria',
    templateUrl: './dialog-revoca-bancaria.component.html',
    styleUrls: ['./dialog-revoca-bancaria.component.scss']
})

export class DialogRevocaBancariaComponent implements OnInit {
    @ViewChild(MatPaginator) paginator: MatPaginator;
    // MA CHE CA*** E'!! @Input() idModalitaAgevolazione ;
    myForm: FormGroup;
    revocaBancariaDTO: RevocaBancariaDTO = new RevocaBancariaDTO();
    isConferma: boolean;
    isRevoca: boolean;
    isMandato: boolean;
    isDisp: boolean;
    isCampiControl: boolean;
    idProgetto: number;
    dataRicezComunicazioneRevoca: Date
    dataRevoca: Date
    impDebitoResiduoBanca: number
    impDebitoResiduoFinpiemonte: number
    perCofinanziamentoFinpiemonte: number;
    numeroProtocollo: string;
    note: string
    idUtente: number;
    totaleFinepiemonte: number;
    totaleErogato: number;
    subscribers: any = {};
    storico: Array<StoricoRevocaDTO>;
    user: UserInfoSec;
    displayedColumns: string[] = ['dataInserimento', 'istruttore'];
    idRevoca: number;
    loadedRB: boolean;
    loadedUser: boolean;
    messageSuccess: string = "Salvato";
    isSaveSuccess: boolean;
    isCampiVuoti: boolean;
    isLoading = false;
    showError: boolean = false;
    disableStorico: boolean;
    isModifica: boolean;
    importoBanca: number;
    importoFormatted: string;
    impDebitoResiduoBancaFormatted: string;
    impDebitoResiduoFinpiemonteFormatted: string;
    
    idModalitaAgevolazione: number = 10;

    constructor(
        public dialogRef: MatDialogRef<DialogRevocaBancariaComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private route: ActivatedRoute,
        private router: Router,
        private revocaBancariaService: RevocaBancariaService,
        private userService: UserService,
        private handleExceptionService: HandleExceptionService,
        private sharedService: SharedService,
        public dialog: MatDialog
    ) {console.log("Dialog data: ", data)}

    ngOnInit(): void {
        this.loadedUser = false;
        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
            
            if(data) {
                this.user = data;
                this.idUtente = this.user.idUtente;
                this.loadedUser = true;

                this.route.queryParams.subscribe(params => {
                    this.totaleErogato = params.totEro;
                    this.totaleFinepiemonte = params.totFin;
                    this.idProgetto = params.progetto;
                });

                let factor = Math.pow(10, 2);
                let div = (this.totaleFinepiemonte / this.totaleErogato) * 100;
                this.perCofinanziamentoFinpiemonte = Math.round(div * factor) / factor;
                this.getRevocaBancaria();
            }
        }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
        });

    }

    getRevocaBancaria() {
        this.loadedRB = false;
        this.idProgetto = this.data.idProgetto;
        this.subscribers.revoca = this.revocaBancariaService.getRevoca(this.idUtente, this.idProgetto,this.idModalitaAgevolazione ).subscribe(data => {

            if (data.idRevoca != null) {
                this.revocaBancariaDTO = data;
                this.dataRevoca = this.revocaBancariaDTO.dataRevoca;
                this.dataRicezComunicazioneRevoca = this.revocaBancariaDTO.dataRicezComunicazioneRevoca;
                this.impDebitoResiduoBanca = this.revocaBancariaDTO.impDebitoResiduoBanca;

                if (this.impDebitoResiduoBanca != null) {
                    this.impDebitoResiduoBancaFormatted = this.sharedService.formatValue(this.impDebitoResiduoBanca.toString());
                }

                this.impDebitoResiduoFinpiemonte = this.revocaBancariaDTO.impDebitoResiduoFinpiemonte;

                if (this.impDebitoResiduoFinpiemonte != null) {
                    this.impDebitoResiduoFinpiemonteFormatted = this.sharedService.formatValue(this.impDebitoResiduoFinpiemonte.toString());
                }

                this.note = this.revocaBancariaDTO.note;
                this.perCofinanziamentoFinpiemonte = this.revocaBancariaDTO.perCofinanziamentoFinpiemonte;
                this.numeroProtocollo = this.revocaBancariaDTO.numeroProtocollo;
                this.idRevoca = this.revocaBancariaDTO.idRevoca;
                this.isRevoca = true;
                this.isConferma = false
            } else {
                this.isRevoca = false
                this.isConferma = true;
            }

            this.loadedRB = true;
        });

    }

    closeDialog() {
        this.dialogRef.close();
    }

}