import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserItemResolver } from './user-item/user-item-resolver.service';
import { UserItemComponent } from './user-item/user-item.component';
import { UserListComponent } from './user-list/user-list.component';
import { UserNewComponent } from './user-new/user-new.component';

const routes: Routes = [
  {
    path: '',
    component: UserListComponent
  },
  {
    path: 'new',
    component: UserNewComponent
  },
  {
    path: ':itemid',
    component: UserItemComponent,
    resolve: {
      user: UserItemResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule {}
