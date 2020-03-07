import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICiudad, Ciudad } from 'app/shared/model/ciudad.model';
import { CiudadService } from './ciudad.service';

@Component({
  selector: 'jhi-ciudad-update',
  templateUrl: './ciudad-update.component.html'
})
export class CiudadUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descripcion: [],
    habitantes: []
  });

  constructor(protected ciudadService: CiudadService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ciudad }) => {
      this.updateForm(ciudad);
    });
  }

  updateForm(ciudad: ICiudad): void {
    this.editForm.patchValue({
      id: ciudad.id,
      nombre: ciudad.nombre,
      descripcion: ciudad.descripcion,
      habitantes: ciudad.habitantes
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ciudad = this.createFromForm();
    if (ciudad.id !== undefined) {
      this.subscribeToSaveResponse(this.ciudadService.update(ciudad));
    } else {
      this.subscribeToSaveResponse(this.ciudadService.create(ciudad));
    }
  }

  private createFromForm(): ICiudad {
    return {
      ...new Ciudad(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      habitantes: this.editForm.get(['habitantes'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICiudad>>): void {
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
