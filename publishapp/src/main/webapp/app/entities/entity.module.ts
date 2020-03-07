import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'autor',
        loadChildren: () => import('./autor/autor.module').then(m => m.PublishappAutorModule)
      },
      {
        path: 'articulo',
        loadChildren: () => import('./articulo/articulo.module').then(m => m.PublishappArticuloModule)
      },
      {
        path: 'publicacion',
        loadChildren: () => import('./publicacion/publicacion.module').then(m => m.PublishappPublicacionModule)
      },
      {
        path: 'categoria',
        loadChildren: () => import('./categoria/categoria.module').then(m => m.PublishappCategoriaModule)
      },
      {
        path: 'universidad',
        loadChildren: () => import('./universidad/universidad.module').then(m => m.PublishappUniversidadModule)
      },
      {
        path: 'ciudad',
        loadChildren: () => import('./ciudad/ciudad.module').then(m => m.PublishappCiudadModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PublishappEntityModule {}
