import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPublicacion } from 'app/shared/model/publicacion.model';
import { PublicacionService } from './publicacion.service';

@Component({
  templateUrl: './publicacion-delete-dialog.component.html'
})
export class PublicacionDeleteDialogComponent {
  publicacion?: IPublicacion;

  constructor(
    protected publicacionService: PublicacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.publicacionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('publicacionListModification');
      this.activeModal.close();
    });
  }
}
