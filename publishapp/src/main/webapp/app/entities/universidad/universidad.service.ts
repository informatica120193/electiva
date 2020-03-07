import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUniversidad } from 'app/shared/model/universidad.model';

type EntityResponseType = HttpResponse<IUniversidad>;
type EntityArrayResponseType = HttpResponse<IUniversidad[]>;

@Injectable({ providedIn: 'root' })
export class UniversidadService {
  public resourceUrl = SERVER_API_URL + 'api/universidads';

  constructor(protected http: HttpClient) {}

  create(universidad: IUniversidad): Observable<EntityResponseType> {
    return this.http.post<IUniversidad>(this.resourceUrl, universidad, { observe: 'response' });
  }

  update(universidad: IUniversidad): Observable<EntityResponseType> {
    return this.http.put<IUniversidad>(this.resourceUrl, universidad, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUniversidad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUniversidad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
