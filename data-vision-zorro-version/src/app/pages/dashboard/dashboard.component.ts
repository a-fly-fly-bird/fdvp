import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NzPlacementType } from 'ng-zorro-antd/dropdown';
import { GlobalService } from 'src/app/shared/service/global.service';

const userList = ['Lucy', 'U', 'Tom', 'Edward'];
const colorList = ['#f56a00', '#7265e6', '#ffbf00', '#00a2ae'];

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent {
  constructor(private route: Router, private globalService: GlobalService) { }

  location: NzPlacementType = 'bottomCenter';
  text: string = this.globalService.getUserName();
  color: string = colorList[3];
  gap = 4;
  change(): void {
    let idx = userList.indexOf(this.text);
    ++idx;
    if (idx === userList.length) {
      idx = 0;
    }
    this.text = userList[idx];
    this.color = colorList[idx];
  }

  public goToUserDetail(){
    this.route.navigate(['/dashboard/user-overview']);
  }

  public goToLogin(){
    this.route.navigate(['/login']);
  }
}
