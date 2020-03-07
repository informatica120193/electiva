import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAutor, Autor } from 'app/shared/model/autor.model';
import { AutorService } from './autor.service';
import { IUniversidad } from 'app/shared/model/universidad.model';
import { UniversidadService } from 'app/entities/universidad/universidad.service';

@Component({
  selector: 'jhi-autor-update',
  templateUrl: './autor-update.component.html'
})
export class AutorUpdateComponent implements OnInit {
  isSaving = false;
  universidads: IUniversidad[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    direccion: [],
    telefono: [],
    universidads: []
  });

  constructor(
    protected autorService: AutorService,
    protected universidadService: UniversidadService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autor }) => {
      this.updateForm(autor);

      this.universidadService.query().subscribe((res: HttpResponse<IUniversidad[]>) => (this.universidads = res.body || []));
    });
  }

  updateForm(autor: IAutor): void {
    this.editForm.patchValue({
      id: autor.id,
      nombre: autor.nombre,
      direccion: autor.direccion,
      telefono: autor.telefono,
      universidads: autor.universidads
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autor = this.createFromForm();
    if (autor.id !== undefined) {
      this.subscribeToSaveResponse(this.autorService.update(autor));
    } else {
      this.subscribeToSaveResponse(this.autorService.create(autor));
    }
  }

  private createFromForm(): IAutor {
    return {
      ...new Autor(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      universidads: this.editForm.get(['universidads'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutor>>): void {
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

  trackById(index: number, item: IUniversidad): any {
    return item.id;
  }

  getSelected(selectedVals: IUniversidad[], option: IUniversidad): IUniversidad {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
