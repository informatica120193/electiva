import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PublishappSharedModule } from 'app/shared/shared.module';
import { CiudadComponent } from './ciudad.component';
import { CiudadDetailComponent } from './ciudad-detail.component';
import { CiudadUpdateComponent } from './ciudad-update.component';
import { CiudadDeleteDialogComponent } from './ciudad-delete-dialog.component';
import { ciudadRoute } from './ciudad.route';

@NgModule({
  imports: [PublishappSharedModule, RouterModule.forChild(ciudadRoute)],
  declarations: [CiudadComponent, CiudadDetailComponent, CiudadUpdateComponent, CiudadDeleteDialogComponent],
  entryComponents: [CiudadDeleteDialogComponent]
})
export class PublishappCiudadModule {}
