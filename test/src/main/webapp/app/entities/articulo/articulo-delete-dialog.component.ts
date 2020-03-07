import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArticulo } from 'app/shared/model/articulo.model';
import { ArticuloService } from './articulo.service';

@Component({
  templateUrl: './articulo-delete-dialog.component.html'
})
export class ArticuloDeleteDialogComponent {
  articulo?: IArticulo;

  constructor(protected articuloService: ArticuloService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.articuloService.delete(id).subscribe(() => {
      this.eventManager.broadcast('articuloListModification');
      this.activeModal.close();
    });
  }
}
