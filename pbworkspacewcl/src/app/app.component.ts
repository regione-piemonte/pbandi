/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Location } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UserInfoSec } from './core/commons/vo/user-info';
import { UserService } from './core/services/user.service';
import { ContattiDialog } from './shared/components/contatti-dialog/contatti-dialog';
import { AutoUnsubscribe } from './shared/decorator/auto-unsubscribe';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AppComponent implements OnInit {

  user: UserInfoSec;

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  constructor(
    private router: Router,
    private userService: UserService,
    private dialog: MatDialog,
    private location: Location
  ) { }

  ngOnInit() {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (!this.user.codiceRuolo && !this.location.path().includes("homeSceltaProfilo")) {
          this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
        }
      }
    });
    this.subscribers.getInfoUtente$ = this.userService.getInfoUtente();
  }

  openContatti() {
    this.dialog.open(ContattiDialog, {
      width: '60%'
    });
  }

  isCambiaProfiloDisabled() {
    if (this.user && this.user.ruoli && this.user.ruoli.length <= 1) {
      return true;
    }
    return false;
  }

  goToHomeSceltaProfilo() {
    this.router.navigateByUrl("/homeSceltaProfilo");
  }

  logout() {
    this.userService.logOut();
  }
}
