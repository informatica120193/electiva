import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUniversidad, Universidad } from 'app/shared/model/universidad.model';
import { UniversidadService } from './universidad.service';
import { IAutor } from 'app/shared/model/autor.model';
import { AutorService } from 'app/entities/autor/autor.service';
import { ICiudad } from 'app/shared/model/ciudad.model';
import { CiudadService } from 'app/entities/ciudad/ciudad.service';

type SelectableEntity = IAutor | ICiudad;

@Component({
  selector: 'jhi-universidad-update',
  templateUrl: './universidad-update.component.html'
})
export class UniversidadUpdateComponent implements OnInit {
  isSaving = false;
  autors: IAutor[] = [];
  ciudads: ICiudad[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    direccion: [],
    autors: [],
    ciudad: []
  });

  constructor(
    protected universidadService: UniversidadService,
    protected autorService: AutorService,
    protected ciudadService: CiudadService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ universidad }) => {
      this.updateForm(universidad);

      this.autorService.query().subscribe((res: HttpResponse<IAutor[]>) => (this.autors = res.body || []));

      this.ciudadService.query().subscribe((res: HttpResponse<ICiudad[]>) => (this.ciudads = res.body || []));
    });
  }

  updateForm(universidad: IUniversidad): void {
    this.editForm.patchValue({
      id: universidad.id,
      nombre: universidad.nombre,
      direccion: universidad.direccion,
      autors: universidad.autors,
      ciudad: universidad.ciudad
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const universidad = this.createFromForm();
    if (universidad.id !== undefined) {
      this.subscribeToSaveResponse(this.universidadService.update(universidad));
    } else {
      this.subscribeToSaveResponse(this.universidadService.create(universidad));
    }
  }

  private createFromForm(): IUniversidad {
    return {
      ...new Universidad(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      autors: this.editForm.get(['autors'])!.value,
      ciudad: this.editForm.get(['ciudad'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUniversidad>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IAutor[], option: IAutor): IAutor {
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
