import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IArticulo } from 'app/shared/model/articulo.model';
import { ArticuloService } from './articulo.service';
import { ArticuloDeleteDialogComponent } from './articulo-delete-dialog.component';

@Component({
  selector: 'jhi-articulo',
  templateUrl: './articulo.component.html'
})
export class ArticuloComponent implements OnInit, OnDestroy {
  articulos?: IArticulo[];
  eventSubscriber?: Subscription;

  constructor(protected articuloService: ArticuloService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.articuloService.query().subscribe((res: HttpResponse<IArticulo[]>) => (this.articulos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInArticulos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IArticulo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInArticulos(): void {
    this.eventSubscriber = this.eventManager.subscribe('articuloListModification', () => this.loadAll());
  }

  delete(articulo: IArticulo): void {
    const modalRef = this.modalService.open(ArticuloDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.articulo = articulo;
  }
}
