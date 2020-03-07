import { IUniversidad } from 'app/shared/model/universidad.model';
import { IArticulo } from 'app/shared/model/articulo.model';

export interface IAutor {
  id?: number;
  nombre?: string;
  direccion?: string;
  telefono?: string;
  universidads?: IUniversidad[];
  articulos?: IArticulo[];
}

export class Autor implements IAutor {
  constructor(
    public id?: number,
    public nombre?: string,
    public direccion?: string,
    public telefono?: string,
    public universidads?: IUniversidad[],
    public articulos?: IArticulo[]
  ) {}
}
