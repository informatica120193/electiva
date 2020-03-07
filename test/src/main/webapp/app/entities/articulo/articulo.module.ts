import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestSharedModule } from 'app/shared/shared.module';
import { ArticuloComponent } from './articulo.component';
import { ArticuloDetailComponent } from './articulo-detail.component';
import { ArticuloUpdateComponent } from './articulo-update.component';
import { ArticuloDeleteDialogComponent } from './articulo-delete-dialog.component';
import { articuloRoute } from './articulo.route';

@NgModule({
  imports: [TestSharedModule, RouterModule.forChild(articuloRoute)],
  declarations: [ArticuloComponent, ArticuloDetailComponent, ArticuloUpdateComponent, ArticuloDeleteDialogComponent],
  entryComponents: [ArticuloDeleteDialogComponent]
})
export class TestArticuloModule {}
