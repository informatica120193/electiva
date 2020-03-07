import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUniversidad, Universidad } from 'app/shared/model/universidad.model';
import { UniversidadService } from './universidad.service';
import { UniversidadComponent } from './universidad.component';
import { UniversidadDetailComponent } from './universidad-detail.component';
import { UniversidadUpdateComponent } from './universidad-update.component';

@Injectable({ providedIn: 'root' })
export class UniversidadResolve implements Resolve<IUniversidad> {
  constructor(private service: UniversidadService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUniversidad> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((universidad: HttpResponse<Universidad>) => {
          if (universidad.body) {
            return of(universidad.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Universidad());
  }
}

export const universidadRoute: Routes = [
  {
    path: '',
    component: UniversidadComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publishappApp.universidad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UniversidadDetailComponent,
    resolve: {
      universidad: UniversidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publishappApp.universidad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UniversidadUpdateComponent,
    resolve: {
      universidad: UniversidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publishappApp.universidad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UniversidadUpdateComponent,
    resolve: {
      universidad: UniversidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publishappApp.universidad.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
