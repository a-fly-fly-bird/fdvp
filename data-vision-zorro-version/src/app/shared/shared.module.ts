import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableComponent } from './components/table/table.component';
import { DemoDirective } from './directive/demo.directive';
import { DemoPipe } from './pipe/demo.pipe';
import { NzTableModule } from 'ng-zorro-antd/table';
import { FundTableComponent } from './components/fund-table/fund-table.component';
import { BondTableComponent } from './components/bond-table/bond-table.component';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { NzFormModule } from 'ng-zorro-antd/form';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';

@NgModule({
  declarations: [
    TableComponent,
    DemoDirective,
    DemoPipe,
    FundTableComponent,
    BondTableComponent,
  ],
  imports: [
    CommonModule,
    NzTableModule,
    NzButtonModule,
    NzModalModule,
    NzFormModule,
    FormsModule,
    RouterModule,
    NzDatePickerModule
  ],
  exports: [
    TableComponent,
  ]
})
export class SharedModule { }
