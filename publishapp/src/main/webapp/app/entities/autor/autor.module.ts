import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PublishappSharedModule } from 'app/shared/shared.module';
import { AutorComponent } from './autor.component';
import { AutorDetailComponent } from './autor-detail.component';
import { AutorUpdateComponent } from './autor-update.component';
import { AutorDeleteDialogComponent } from './autor-delete-dialog.component';
import { autorRoute } from './autor.route';

@NgModule({
  imports: [PublishappSharedModule, RouterModule.forChild(autorRoute)],
  declarations: [AutorComponent, AutorDetailComponent, AutorUpdateComponent, AutorDeleteDialogComponent],
  entryComponents: [AutorDeleteDialogComponent]
})
export class PublishappAutorModule {}
