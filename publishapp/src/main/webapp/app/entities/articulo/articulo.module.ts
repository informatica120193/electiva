import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PublishappSharedModule } from 'app/shared/shared.module';
import { ArticuloComponent } from './articulo.component';
import { ArticuloDetailComponent } from './articulo-detail.component';
import { ArticuloUpdateComponent } from './articulo-update.component';
import { ArticuloDeleteDialogComponent } from './articulo-delete-dialog.component';
import { articuloRoute } from './articulo.route';

@NgModule({
  imports: [PublishappSharedModule, RouterModule.forChild(articuloRoute)],
  declarations: [ArticuloComponent, ArticuloDetailComponent, ArticuloUpdateComponent, ArticuloDeleteDialogComponent],
  entryComponents: [ArticuloDeleteDialogComponent]
})
export class PublishappArticuloModule {}
