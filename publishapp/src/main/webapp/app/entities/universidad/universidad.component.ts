import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUniversidad } from 'app/shared/model/universidad.model';
import { UniversidadService } from './universidad.service';
import { UniversidadDeleteDialogComponent } from './universidad-delete-dialog.component';

@Component({
  selector: 'jhi-universidad',
  templateUrl: './universidad.component.html'
})
export class UniversidadComponent implements OnInit, OnDestroy {
  universidads?: IUniversidad[];
  eventSubscriber?: Subscription;

  constructor(
    protected universidadService: UniversidadService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.universidadService.query().subscribe((res: HttpResponse<IUniversidad[]>) => (this.universidads = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUniversidads();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUniversidad): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUniversidads(): void {
    this.eventSubscriber = this.eventManager.subscribe('universidadListModification', () => this.loadAll());
  }

  delete(universidad: IUniversidad): void {
    const modalRef = this.modalService.open(UniversidadDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.universidad = universidad;
  }
}
