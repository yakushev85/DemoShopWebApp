import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileGeneralComponent } from './profile-general/profile-general.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProfileRoutingModule } from './profile-routing.module';



@NgModule({
  declarations: [
    ProfileGeneralComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ProfileRoutingModule,
    ReactiveFormsModule
  ]
})
export class ProfileModule { }
