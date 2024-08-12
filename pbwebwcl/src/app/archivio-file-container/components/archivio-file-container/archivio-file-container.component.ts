import { Component, OnInit } from '@angular/core';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';


@Component({
  selector: 'archivio-file-container',
  templateUrl: './archivio-file-container.component.html',
  styleUrls: ['./archivio-file-container.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ArchivioFileContainerComponent implements OnInit {

  apiURL: string;
  user: UserInfoSec;
  drawerExpanded: Observable<boolean>;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private userService: UserService,
    private configService: ConfigService
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
      }
    });
    this.apiURL = this.configService.getApiURL();
  }

}