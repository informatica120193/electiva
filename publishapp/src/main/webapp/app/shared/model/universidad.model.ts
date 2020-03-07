import { ICiudad } from 'app/shared/model/ciudad.model';
import { IAutor } from 'app/shared/model/autor.model';

export interface IUniversidad {
  id?: number;
  nombre?: string;
  direccion?: string;
  ciudad?: ICiudad;
  autors?: IAutor[];
}

export class Universidad implements IUniversidad {
  constructor(public id?: number, public nombre?: string, public direccion?: string, public ciudad?: ICiudad, public autors?: IAutor[]) {}
}
