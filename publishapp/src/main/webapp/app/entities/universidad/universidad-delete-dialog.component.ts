import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUniversidad } from 'app/shared/model/universidad.model';
import { UniversidadService } from './universidad.service';

@Component({
  templateUrl: './universidad-delete-dialog.component.html'
})
export class UniversidadDeleteDialogComponent {
  universidad?: IUniversidad;

  constructor(
    protected universidadService: UniversidadService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.universidadService.delete(id).subscribe(() => {
      this.eventManager.broadcast('universidadListModification');
      this.activeModal.close();
    });
  }
}
