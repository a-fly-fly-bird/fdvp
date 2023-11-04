import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { TableComponent } from 'src/app/shared/components/table/table.component';
import { FundTableComponent } from 'src/app/shared/components/fund-table/fund-table.component';
import { BondTableComponent } from 'src/app/shared/components/bond-table/bond-table.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    // pathMatch: 'full',
    children:[
      {
        path: '',
        redirectTo: 'table',
        pathMatch: 'full'
      },
      {
        path: 'table',
        component: TableComponent
      },
      {
        path: 'fund_table',
        component: FundTableComponent
      },
      {
        path: 'bond_table',
        component: BondTableComponent
      },
      {
        path: 'details',
        loadChildren: () => import('../details/details.module').then(m => m.DetailsModule)
      },
      {
        path: 'history',
        loadChildren: () => import('../history/history.module').then(m => m.HistoryModule)
      },
      {
        path: 'operation',
        loadChildren: () => import('../operation/operation.module').then(m => m.OperationModule)
      },
      {
        path: 'user-overview',
        loadChildren: () => import('../user-overview/user-overview.module').then(m => m.UserOverviewModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class DashboardRoutingModule { }
