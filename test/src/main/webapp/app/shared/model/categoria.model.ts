import { IArticulo } from 'app/shared/model/articulo.model';

export interface ICategoria {
  id?: number;
  nombre?: string;
  descripcion?: string;
  articulos?: IArticulo[];
}

export class Categoria implements ICategoria {
  constructor(public id?: number, public nombre?: string, public descripcion?: string, public articulos?: IArticulo[]) {}
}
