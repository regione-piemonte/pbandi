/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { UserInfoSec } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { Constants } from '../../../core/commons/util/constants';
import { AttivitaIstruttorieService } from "../../services/attivitaIstruttorie.service";

@Component({
    selector: 'app-attivita-istruttore-garanzie',
    templateUrl:'./attivita-istruttore-garanzie.component.html',
    styleUrls: ['./attivita-istruttore-garanzie.component.scss']
})

export class AttivitaIstruttoreGaranzieComponent implements OnInit {
    subscribers: any = {};
    user: UserInfoSec;
    //@Input() item ;
    //@Input() idModalitaAgevolazione ;
    ambito: any;

    item: number;
    idModalitaAgevolazione: number = 10; // Garanzia

    constructor(
        private service: AttivitaIstruttorieService,
        private router: Router,
        private userService: UserService,
        private route: ActivatedRoute,
    ) {}
    spinner: boolean;

    ngOnInit(): void {

        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

            if (data) {
                this.user = data;
                //console.log(this.user.codiceRuolo);
                this.route.queryParams.subscribe(params => {
                    this.item = params.idProgetto;
                    //console.log("item comp: ", this.item);
                    this.ambito = "escussioni"
                  });
                
            }

        })

    }

    goBack() {
        this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_GESTIONE_GARANZIE + "/gestioneGaranzie"]);
    }
}