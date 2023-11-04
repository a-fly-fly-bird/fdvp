import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserOverviewRoutingModule } from './user-overview-routing.module';
import { UserOverviewComponent } from './user-overview.component';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzPaginationModule } from 'ng-zorro-antd/pagination';

@NgModule({
  declarations: [
    UserOverviewComponent
  ],
  imports: [
    CommonModule,
    UserOverviewRoutingModule,
    NzTableModule,
    NzPaginationModule
  ]
})
export class UserOverviewModule { }
