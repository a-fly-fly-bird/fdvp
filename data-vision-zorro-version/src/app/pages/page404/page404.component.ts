import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-page404',
  templateUrl: './page404.component.html',
  styleUrls: ['./page404.component.scss']
})
export class Page404Component {
  title = '404 Not Found'

  constructor(private route: Router){}

  back_to_login(){
    this.route.navigate(['/login']);
  }
}
