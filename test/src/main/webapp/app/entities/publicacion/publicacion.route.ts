import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPublicacion, Publicacion } from 'app/shared/model/publicacion.model';
import { PublicacionService } from './publicacion.service';
import { PublicacionComponent } from './publicacion.component';
import { PublicacionDetailComponent } from './publicacion-detail.component';
import { PublicacionUpdateComponent } from './publicacion-update.component';

@Injectable({ providedIn: 'root' })
export class PublicacionResolve implements Resolve<IPublicacion> {
  constructor(private service: PublicacionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPublicacion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((publicacion: HttpResponse<Publicacion>) => {
          if (publicacion.body) {
            return of(publicacion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Publicacion());
  }
}

export const publicacionRoute: Routes = [
  {
    path: '',
    component: PublicacionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testApp.publicacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PublicacionDetailComponent,
    resolve: {
      publicacion: PublicacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testApp.publicacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PublicacionUpdateComponent,
    resolve: {
      publicacion: PublicacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testApp.publicacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PublicacionUpdateComponent,
    resolve: {
      publicacion: PublicacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testApp.publicacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
