import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'autor',
        loadChildren: () => import('./autor/autor.module').then(m => m.TestAutorModule)
      },
      {
        path: 'articulo',
        loadChildren: () => import('./articulo/articulo.module').then(m => m.TestArticuloModule)
      },
      {
        path: 'publicacion',
        loadChildren: () => import('./publicacion/publicacion.module').then(m => m.TestPublicacionModule)
      },
      {
        path: 'categoria',
        loadChildren: () => import('./categoria/categoria.module').then(m => m.TestCategoriaModule)
      },
      {
        path: 'universidad',
        loadChildren: () => import('./universidad/universidad.module').then(m => m.TestUniversidadModule)
      },
      {
        path: 'ciudad',
        loadChildren: () => import('./ciudad/ciudad.module').then(m => m.TestCiudadModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TestEntityModule {}
