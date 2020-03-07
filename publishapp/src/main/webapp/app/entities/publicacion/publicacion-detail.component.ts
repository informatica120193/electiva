import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPublicacion } from 'app/shared/model/publicacion.model';

@Component({
  selector: 'jhi-publicacion-detail',
  templateUrl: './publicacion-detail.component.html'
})
export class PublicacionDetailComponent implements OnInit {
  publicacion: IPublicacion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ publicacion }) => (this.publicacion = publicacion));
  }

  previousState(): void {
    window.history.back();
  }
}
