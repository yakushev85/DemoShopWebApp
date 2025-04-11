import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { ShowAuthedDirective } from './show-authed.directive';


@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    ShowAuthedDirective
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    RouterModule
  ],
  exports: [
    HeaderComponent,
    FooterComponent,
    ShowAuthedDirective,
    HttpClientModule,
    RouterModule
  ]
})
export class SharedModule { }
