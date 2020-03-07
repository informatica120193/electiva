import { Moment } from 'moment';
import { IArticulo } from 'app/shared/model/articulo.model';

export interface IPublicacion {
  id?: number;
  fecha?: Moment;
  numero?: string;
  articulos?: IArticulo[];
}

export class Publicacion implements IPublicacion {
  constructor(public id?: number, public fecha?: Moment, public numero?: string, public articulos?: IArticulo[]) {}
}
