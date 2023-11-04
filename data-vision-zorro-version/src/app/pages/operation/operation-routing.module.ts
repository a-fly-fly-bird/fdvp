import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BuyComponent } from './buy/buy.component';
import { SellComponent } from './sell/sell.component';
import { SellFundComponent } from './sell/sell-fund/sell-fund.component';
import { SellBondComponent } from './sell/sell-bond/sell-bond.component';

const routes: Routes = [
  {
    path: '',
    redirectTo:'buy',
    pathMatch:'full'
  },
  {
    path: 'buy',
    component: BuyComponent
  },
  {
    path: 'sell_stock',
    component: SellComponent
  },
  {
    path: 'sell_fund',
    component: SellFundComponent
  },
  {
    path: 'sell_bond',
    component: SellBondComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OperationRoutingModule { }
