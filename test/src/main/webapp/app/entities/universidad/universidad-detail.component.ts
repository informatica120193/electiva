import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUniversidad } from 'app/shared/model/universidad.model';

@Component({
  selector: 'jhi-universidad-detail',
  templateUrl: './universidad-detail.component.html'
})
export class UniversidadDetailComponent implements OnInit {
  universidad: IUniversidad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ universidad }) => (this.universidad = universidad));
  }

  previousState(): void {
    window.history.back();
  }
}
