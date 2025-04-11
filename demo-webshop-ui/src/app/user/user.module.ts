import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserListComponent } from './user-list/user-list.component';
import { UserItemComponent } from './user-item/user-item.component';
import { UserNewComponent } from './user-new/user-new.component';
import { UserRoutingModule } from './user-routing.module';
import { UserItemResolver } from './user-item/user-item-resolver.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';



@NgModule({
  declarations: [
    UserListComponent,
    UserItemComponent,
    UserNewComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    UserRoutingModule,
    NgbModule
  ],
  providers: [
    UserItemResolver
  ]
})
export class UserModule { }
