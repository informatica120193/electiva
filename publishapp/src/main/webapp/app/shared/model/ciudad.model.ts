import { IUniversidad } from 'app/shared/model/universidad.model';

export interface ICiudad {
  id?: number;
  nombre?: string;
  descripcion?: string;
  habitantes?: number;
  universidads?: IUniversidad[];
}

export class Ciudad implements ICiudad {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public habitantes?: number,
    public universidads?: IUniversidad[]
  ) {}
}
