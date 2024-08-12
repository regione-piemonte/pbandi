import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UserInfoSec } from './core/commons/dto/user-info';
import { ConfigService } from './core/services/config.service';
import { UserService } from './core/services/user.service';
import { ContattiDialog } from './shared/components/contatti-dialog/contatti-dialog';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  user: UserInfoSec;

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  constructor(
    private userService: UserService,
    private configService: ConfigService,
    private dialog: MatDialog,
    private router: Router,
    private location: Location
  ) { }

  ngOnInit() {
    let params: URLSearchParams = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;
    if (params.get('idSg') != null) {
      if (!params.get('idPrj') || !params.get('taskIdentity') || !params.get('taskName') || !params.get('wkfAction')) {
        this.userService.home2(params.get('idSg'), params.get('idSgBen'), params.get('idU'), params.get('role'));
      } else {
        this.userService.home(
          params.get('idPrj'), params.get('idSg'), params.get('idSgBen'), params.get('idU'), params.get('role'),
          params.get('taskIdentity'), params.get('taskName'), params.get('wkfAction'));
      }
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

  isCambiaProfiloDisabled() {
    if (this.user && this.user.ruoli && this.user.ruoli.length <= 1) {
      return true;
    }
    if (!this.location.path().includes("documentiProgetto")) {
      return true;
    }
    return false;
  }

  goToHomeSceltaProfilo() {
    window.location.assign(this.configService.getPbworkspaceURL() + "/#/homeSceltaProfilo");
  }

  logout() {
    this.userService.logOut();
  }

}
