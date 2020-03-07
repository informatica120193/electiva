import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICiudad } from 'app/shared/model/ciudad.model';
import { CiudadService } from './ciudad.service';
import { CiudadDeleteDialogComponent } from './ciudad-delete-dialog.component';

@Component({
  selector: 'jhi-ciudad',
  templateUrl: './ciudad.component.html'
})
export class CiudadComponent implements OnInit, OnDestroy {
  ciudads?: ICiudad[];
  eventSubscriber?: Subscription;

  constructor(protected ciudadService: CiudadService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.ciudadService.query().subscribe((res: HttpResponse<ICiudad[]>) => (this.ciudads = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCiudads();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICiudad): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCiudads(): void {
    this.eventSubscriber = this.eventManager.subscribe('ciudadListModification', () => this.loadAll());
  }

  delete(ciudad: ICiudad): void {
    const modalRef = this.modalService.open(CiudadDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ciudad = ciudad;
  }
}
