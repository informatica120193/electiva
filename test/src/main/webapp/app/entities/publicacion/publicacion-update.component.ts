import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPublicacion, Publicacion } from 'app/shared/model/publicacion.model';
import { PublicacionService } from './publicacion.service';

@Component({
  selector: 'jhi-publicacion-update',
  templateUrl: './publicacion-update.component.html'
})
export class PublicacionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fecha: [],
    numero: []
  });

  constructor(protected publicacionService: PublicacionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ publicacion }) => {
      if (!publicacion.id) {
        const today = moment().startOf('day');
        publicacion.fecha = today;
      }

      this.updateForm(publicacion);
    });
  }

  updateForm(publicacion: IPublicacion): void {
    this.editForm.patchValue({
      id: publicacion.id,
      fecha: publicacion.fecha ? publicacion.fecha.format(DATE_TIME_FORMAT) : null,
      numero: publicacion.numero
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const publicacion = this.createFromForm();
    if (publicacion.id !== undefined) {
      this.subscribeToSaveResponse(this.publicacionService.update(publicacion));
    } else {
      this.subscribeToSaveResponse(this.publicacionService.create(publicacion));
    }
  }

  private createFromForm(): IPublicacion {
    return {
      ...new Publicacion(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      numero: this.editForm.get(['numero'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPublicacion>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
