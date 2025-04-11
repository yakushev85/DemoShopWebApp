import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProfileGeneralComponent } from './profile-general/profile-general.component';

const routes: Routes = [
  {
    path: 'general',
    component: ProfileGeneralComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule {}
