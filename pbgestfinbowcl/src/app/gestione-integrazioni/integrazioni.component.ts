/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

// import { Component, OnInit, ViewChild } from '@angular/core';
// import { FormBuilder } from '@angular/forms';
// import { MatDialog } from '@angular/material/dialog';
// import { MatPaginator } from '@angular/material/paginator';
// import { MatSort } from '@angular/material/sort';
// import { MatTableDataSource } from '@angular/material/table';
// import { ActivatedRoute, Router } from '@angular/router';
// import { BeneficiarioSec } from 'src/app/core/commons/dto/beneficiario';
// import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
// import { Constants } from 'src/app/core/commons/util/constants';
// import { ConfigService } from 'src/app/core/services/config.service';
// import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
// import { UserService } from 'src/app/core/services/user.service';
// import { ControdeduzioneVO } from 'src/app/gestione-crediti/commons/dto/controdeduzioneVO';
// import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
// import { SharedService } from 'src/app/shared/services/shared.service';
// import { ControdeduzioniService } from '../gestione-controdeduzioni/services/controdeduzioni.service';
// import { IntegrazioniService } from './service/integrazioni.service';

// @Component({
//     selector: 'app-integrazioni',
//     templateUrl: './integrazioni.component.html',
//     styleUrls: ['./integrazioni.component.scss']
//   })
//   @AutoUnsubscribe({ objectName: 'subscribers' })

//   export class IntegrazioniComponent implements OnInit {
//     idOperazione: number;
//     idUtente: string;
//     idProgetto: number;
//     idBandoLinea: number;
//     @ViewChild(MatPaginator) paginator: MatPaginator;
//     @ViewChild(MatSort) sort: MatSort;
//     isMessageErrorVisible: boolean;
//     messageError: string;
//     showResults: boolean = false;
//     beneficiario: BeneficiarioSec;
//     con: Array<ControdeduzioneVO> = [];
//     user: UserInfoSec;
//     showSucc: boolean = false;
//     showError: boolean = false;
//     idControdeduz: number;
//     numeroControdeduz: number;
//     pro: Array<ControdeduzioneVO> = [];
//     thereIsProroga: boolean = false;
//     isApproved: number = 0;
//     subscribers: any = {};
//     dataSource: MatTableDataSource<ControdeduzioneVO> = new MatTableDataSource<ControdeduzioneVO>([]);
//     loadedChiudiAttivita: boolean = true;

//   constructor(
//     private resService: IntegrazioniService,
//     private fb: FormBuilder,
//     private router: Router,
//     private activatedRoute: ActivatedRoute,
//     private route: ActivatedRoute,
//     private userService: UserService,
//     private handleExceptionService: HandleExceptionService,
//     private dialog: MatDialog,
//     private sharedService: SharedService,
//     private configService: ConfigService,
//   ) { }

//   ngOnInit(): void {
//     //TODO
//   }

//   isLoading() {

//     if (!this.loadedChiudiAttivita) {
//       return true;
//     }

//     return false;
//   }

// }