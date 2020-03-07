import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PublishappTestModule } from '../../../test.module';
import { PublicacionDetailComponent } from 'app/entities/publicacion/publicacion-detail.component';
import { Publicacion } from 'app/shared/model/publicacion.model';

describe('Component Tests', () => {
  describe('Publicacion Management Detail Component', () => {
    let comp: PublicacionDetailComponent;
    let fixture: ComponentFixture<PublicacionDetailComponent>;
    const route = ({ data: of({ publicacion: new Publicacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublishappTestModule],
        declarations: [PublicacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PublicacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PublicacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load publicacion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.publicacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
