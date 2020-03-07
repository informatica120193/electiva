import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestSharedModule } from 'app/shared/shared.module';
import { PublicacionComponent } from './publicacion.component';
import { PublicacionDetailComponent } from './publicacion-detail.component';
import { PublicacionUpdateComponent } from './publicacion-update.component';
import { PublicacionDeleteDialogComponent } from './publicacion-delete-dialog.component';
import { publicacionRoute } from './publicacion.route';

@NgModule({
  imports: [TestSharedModule, RouterModule.forChild(publicacionRoute)],
  declarations: [PublicacionComponent, PublicacionDetailComponent, PublicacionUpdateComponent, PublicacionDeleteDialogComponent],
  entryComponents: [PublicacionDeleteDialogComponent]
})
export class TestPublicacionModule {}
