/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UserInfoSec } from './core/commons/dto/user-info';
import { UserService } from './core/services/user.service';
import { ContattiDialog } from './shared/components/contatti-dialog/contatti-dialog';
import { AutoUnsubscribe } from './shared/decorator/auto-unsubscribe';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AppComponent {

  user: UserInfoSec;

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  constructor(
    private userService: UserService,
    private dialog: MatDialog,
    private router: Router
  ) { }

  ngOnInit() {
    let params: URLSearchParams = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;
    if (params.get('idSg') != null) {
      this.userService.home(
        params.get('idPrj'), params.get('idSg'), params.get('idSgBen'), params.get('idU'), params.get('role'),
        params.get('taskIdentity'), params.get('taskName'), params.get('wkfAction'));
    } else {
      this.userService.getInfoUtente();
    }

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (!this.user.codiceRuolo || !this.user.beneficiarioSelezionato) {
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
