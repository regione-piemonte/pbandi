/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { UserService } from './core/services/user.service';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ContattiDialog } from './shared/components/contatti-dialog/contatti-dialog';
import { AutoUnsubscribe } from './shared/decorator/auto-unsubscribe';
import { Router } from '@angular/router';

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
    private dialog: MatDialog,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit() {
    let params: URLSearchParams = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;
    if (params.get('idSg') != null) {
      this.userService.inizializzaAmministrazione(params.get('idSg'), params.get('idSgBen'), params.get('idU'), params.get('role'));
    } else {
      this.userService.getInfoUtente();
    }

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (!this.user.codiceRuolo) {
          this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
        }
      }
    });
  }

  openContatti() {
    this.dialog.open(ContattiDialog, {
      width: '60%'
    });
  }

  logout() {
    this.userService.logOut();
  }
}
