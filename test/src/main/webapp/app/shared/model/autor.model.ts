import { IArticulo } from 'app/shared/model/articulo.model';
import { IUniversidad } from 'app/shared/model/universidad.model';

export interface IAutor {
  id?: number;
  nombre?: string;
  direccion?: string;
  telefono?: string;
  articulos?: IArticulo[];
  universidads?: IUniversidad[];
}

export class Autor implements IAutor {
  constructor(
    public id?: number,
    public nombre?: string,
    public direccion?: string,
    public telefono?: string,
    public articulos?: IArticulo[],
    public universidads?: IUniversidad[]
  ) {}
}
