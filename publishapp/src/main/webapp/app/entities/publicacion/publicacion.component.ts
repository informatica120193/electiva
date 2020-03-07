import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPublicacion } from 'app/shared/model/publicacion.model';
import { PublicacionService } from './publicacion.service';
import { PublicacionDeleteDialogComponent } from './publicacion-delete-dialog.component';

@Component({
  selector: 'jhi-publicacion',
  templateUrl: './publicacion.component.html'
})
export class PublicacionComponent implements OnInit, OnDestroy {
  publicacions?: IPublicacion[];
  eventSubscriber?: Subscription;

  constructor(
    protected publicacionService: PublicacionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.publicacionService.query().subscribe((res: HttpResponse<IPublicacion[]>) => (this.publicacions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPublicacions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPublicacion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPublicacions(): void {
    this.eventSubscriber = this.eventManager.subscribe('publicacionListModification', () => this.loadAll());
  }

  delete(publicacion: IPublicacion): void {
    const modalRef = this.modalService.open(PublicacionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.publicacion = publicacion;
  }
}
