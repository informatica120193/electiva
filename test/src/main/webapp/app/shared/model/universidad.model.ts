import { IAutor } from 'app/shared/model/autor.model';
import { ICiudad } from 'app/shared/model/ciudad.model';

export interface IUniversidad {
  id?: number;
  nombre?: string;
  direccion?: string;
  autors?: IAutor[];
  ciudad?: ICiudad;
}

export class Universidad implements IUniversidad {
  constructor(public id?: number, public nombre?: string, public direccion?: string, public autors?: IAutor[], public ciudad?: ICiudad) {}
}
