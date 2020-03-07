import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IArticulo, Articulo } from 'app/shared/model/articulo.model';
import { ArticuloService } from './articulo.service';
import { IPublicacion } from 'app/shared/model/publicacion.model';
import { PublicacionService } from 'app/entities/publicacion/publicacion.service';
import { IAutor } from 'app/shared/model/autor.model';
import { AutorService } from 'app/entities/autor/autor.service';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria/categoria.service';

type SelectableEntity = IPublicacion | IAutor | ICategoria;

type SelectableManyToManyEntity = IPublicacion | IAutor;

@Component({
  selector: 'jhi-articulo-update',
  templateUrl: './articulo-update.component.html'
})
export class ArticuloUpdateComponent implements OnInit {
  isSaving = false;
  publicacions: IPublicacion[] = [];
  autors: IAutor[] = [];
  categorias: ICategoria[] = [];

  editForm = this.fb.group({
    id: [],
    fecha: [],
    titulo: [],
    publicacions: [],
    autors: [],
    categoria: []
  });

  constructor(
    protected articuloService: ArticuloService,
    protected publicacionService: PublicacionService,
    protected autorService: AutorService,
    protected categoriaService: CategoriaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ articulo }) => {
      if (!articulo.id) {
        const today = moment().startOf('day');
        articulo.fecha = today;
      }

      this.updateForm(articulo);

      this.publicacionService.query().subscribe((res: HttpResponse<IPublicacion[]>) => (this.publicacions = res.body || []));

      this.autorService.query().subscribe((res: HttpResponse<IAutor[]>) => (this.autors = res.body || []));

      this.categoriaService.query().subscribe((res: HttpResponse<ICategoria[]>) => (this.categorias = res.body || []));
    });
  }

  updateForm(articulo: IArticulo): void {
    this.editForm.patchValue({
      id: articulo.id,
      fecha: articulo.fecha ? articulo.fecha.format(DATE_TIME_FORMAT) : null,
      titulo: articulo.titulo,
      publicacions: articulo.publicacions,
      autors: articulo.autors,
      categoria: articulo.categoria
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const articulo = this.createFromForm();
    if (articulo.id !== undefined) {
      this.subscribeToSaveResponse(this.articuloService.update(articulo));
    } else {
      this.subscribeToSaveResponse(this.articuloService.create(articulo));
    }
  }

  private createFromForm(): IArticulo {
    return {
      ...new Articulo(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      titulo: this.editForm.get(['titulo'])!.value,
      publicacions: this.editForm.get(['publicacions'])!.value,
      autors: this.editForm.get(['autors'])!.value,
      categoria: this.editForm.get(['categoria'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArticulo>>): void {
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

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
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
