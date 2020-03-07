import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArticulo, Articulo } from 'app/shared/model/articulo.model';
import { ArticuloService } from './articulo.service';
import { ArticuloComponent } from './articulo.component';
import { ArticuloDetailComponent } from './articulo-detail.component';
import { ArticuloUpdateComponent } from './articulo-update.component';

@Injectable({ providedIn: 'root' })
export class ArticuloResolve implements Resolve<IArticulo> {
  constructor(private service: ArticuloService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArticulo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((articulo: HttpResponse<Articulo>) => {
          if (articulo.body) {
            return of(articulo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Articulo());
  }
}

export const articuloRoute: Routes = [
  {
    path: '',
    component: ArticuloComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testApp.articulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ArticuloDetailComponent,
    resolve: {
      articulo: ArticuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testApp.articulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ArticuloUpdateComponent,
    resolve: {
      articulo: ArticuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testApp.articulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ArticuloUpdateComponent,
    resolve: {
      articulo: ArticuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testApp.articulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
