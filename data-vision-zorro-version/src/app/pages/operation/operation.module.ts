import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OperationRoutingModule } from './operation-routing.module';
import { BuyComponent } from './buy/buy.component';
import { SellComponent } from './sell/sell.component';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzBreadCrumbModule } from 'ng-zorro-antd/breadcrumb';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { NzFormModule } from 'ng-zorro-antd/form';
import { FormsModule } from '@angular/forms';
import { SellFundComponent } from './sell/sell-fund/sell-fund.component';
import { SellBondComponent } from './sell/sell-bond/sell-bond.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';

@NgModule({
  declarations: [
    BuyComponent,
    SellComponent,
    SellFundComponent,
    SellBondComponent
  ],
  imports: [
    CommonModule,
    OperationRoutingModule,
    NzTableModule,
    NzBreadCrumbModule,
    NzButtonModule,
    NzModalModule,
    NzFormModule,
    FormsModule,
    NgxChartsModule
  ]
})
export class OperationModule { }
