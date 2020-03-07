import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPublicacion } from 'app/shared/model/publicacion.model';

type EntityResponseType = HttpResponse<IPublicacion>;
type EntityArrayResponseType = HttpResponse<IPublicacion[]>;

@Injectable({ providedIn: 'root' })
export class PublicacionService {
  public resourceUrl = SERVER_API_URL + 'api/publicacions';

  constructor(protected http: HttpClient) {}

  create(publicacion: IPublicacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(publicacion);
    return this.http
      .post<IPublicacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(publicacion: IPublicacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(publicacion);
    return this.http
      .put<IPublicacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPublicacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPublicacion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(publicacion: IPublicacion): IPublicacion {
    const copy: IPublicacion = Object.assign({}, publicacion, {
      fecha: publicacion.fecha && publicacion.fecha.isValid() ? publicacion.fecha.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((publicacion: IPublicacion) => {
        publicacion.fecha = publicacion.fecha ? moment(publicacion.fecha) : undefined;
      });
    }
    return res;
  }
}
