import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PublishappTestModule } from '../../../test.module';
import { ArticuloDetailComponent } from 'app/entities/articulo/articulo-detail.component';
import { Articulo } from 'app/shared/model/articulo.model';

describe('Component Tests', () => {
  describe('Articulo Management Detail Component', () => {
    let comp: ArticuloDetailComponent;
    let fixture: ComponentFixture<ArticuloDetailComponent>;
    const route = ({ data: of({ articulo: new Articulo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublishappTestModule],
        declarations: [ArticuloDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ArticuloDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArticuloDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load articulo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.articulo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
