/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RuoloSec } from 'src/app/core/commons/vo/ruolo';
import { UserInfoSec } from 'src/app/core/commons/vo/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { HomeService } from '../../services/home.service';

@Component({
  selector: 'app-home-scelta-profilo',
  templateUrl: './home-scelta-profilo.component.html',
  styleUrls: ['./home-scelta-profilo.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class HomeSceltaProfiloComponent implements OnInit {

  user: UserInfoSec;
  profili: Array<RuoloSec>;

  //LOADED
  loadedProfili: boolean;
  loadedAccedi: boolean = true;

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  constructor(
    private router: Router,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService
  ) { }

  ngOnInit(): void {
    this.loadedProfili = false;
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.ruoli && this.user.ruoli.length == 1) {
          this.user.ruolo = this.user.ruoli[0].descrizione;
          this.router.navigate(["/homeOperatore"]);
        }
        this.profili = this.user.ruoli ? this.user.ruoli.sort((a, b) => a.descrizione.localeCompare(b.descrizione)) : [];
        this.loadedProfili = true;
      }
    });

  }

  goToHomeOperatore(id: number) {
    this.loadedAccedi = false;
    this.user.ruolo = this.profili.find(p => p.id === id).descrizione;
    this.userService.user.ruolo = this.user.ruolo;
    this.subscribers.accedi = this.userService.accedi();
    this.subscribers.accedi$ = this.userService.accediSubject.subscribe(data => {
      this.loadedAccedi = true;
      this.router.navigate(["/homeOperatore"]);
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  isLoading() {
    if (!this.loadedProfili || !this.loadedAccedi) {
      return true;
    }
    return false;
  }
}
