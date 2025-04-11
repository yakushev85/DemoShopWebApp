import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TransactionItemResolver } from './transaction-item/transaction-item-resolver.component';
import { TransactionItemComponent } from './transaction-item/transaction-item.component';
import { TransactionListComponent } from './transaction-list/transaction-list.component';

const routes: Routes = [
  {
    path: '',
    component: TransactionListComponent
  },
  {
    path: ':itemid',
    component: TransactionItemComponent,
    resolve: {
      transaction: TransactionItemResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TransactionRoutingModule {}
