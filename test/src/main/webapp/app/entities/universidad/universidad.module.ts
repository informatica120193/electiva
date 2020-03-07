import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestSharedModule } from 'app/shared/shared.module';
import { UniversidadComponent } from './universidad.component';
import { UniversidadDetailComponent } from './universidad-detail.component';
import { UniversidadUpdateComponent } from './universidad-update.component';
import { UniversidadDeleteDialogComponent } from './universidad-delete-dialog.component';
import { universidadRoute } from './universidad.route';

@NgModule({
  imports: [TestSharedModule, RouterModule.forChild(universidadRoute)],
  declarations: [UniversidadComponent, UniversidadDetailComponent, UniversidadUpdateComponent, UniversidadDeleteDialogComponent],
  entryComponents: [UniversidadDeleteDialogComponent]
})
export class TestUniversidadModule {}
