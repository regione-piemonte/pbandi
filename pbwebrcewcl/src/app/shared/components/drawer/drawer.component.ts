/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from './../../../core/services/config.service';
import { Component, OnInit } from '@angular/core';
import { AutoUnsubscribe } from '../../decorator/auto-unsubscribe';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';

@Component({
  selector: 'app-drawer-container',
  templateUrl: './drawer.component.html',
  styleUrls: ['./drawer.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DrawerComponent implements OnInit {

  user: UserInfoSec;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private configService: ConfigService,
    private userService: UserService
  ) { }

  get apiURL() {
    return this.configService.getApiURL();
  }

  get pbwebURL() {
    return this.configService.getPbwebURL();
  }

  get pbworkspaceURL() {
    return this.configService.getPbworkspaceURL();
  }

  get pbwebboURL() {
    return this.configService.getPbwebboURL();
  }

  get pbwebrceURL() {
    return null;
  }

  get pbwebcertURL() {
    return this.configService.getPbwebcertURL();
  }

  get pbgestfinboURL() {
    return this.configService.getPbgestfinboURL();
  }

  get pbweberogURL() {
    return this.configService.getPbweberogURL();
  }

  get pbwebfinURL() {
    return this.configService.getPbwebfinURL();
  }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
      }
    });
  }
}
