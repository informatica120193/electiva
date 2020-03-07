import { Moment } from 'moment';
import { IPublicacion } from 'app/shared/model/publicacion.model';
import { IAutor } from 'app/shared/model/autor.model';
import { ICategoria } from 'app/shared/model/categoria.model';

export interface IArticulo {
  id?: number;
  fecha?: Moment;
  titulo?: string;
  publicacions?: IPublicacion[];
  autors?: IAutor[];
  categoria?: ICategoria;
}

export class Articulo implements IArticulo {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public titulo?: string,
    public publicacions?: IPublicacion[],
    public autors?: IAutor[],
    public categoria?: ICategoria
  ) {}
}
