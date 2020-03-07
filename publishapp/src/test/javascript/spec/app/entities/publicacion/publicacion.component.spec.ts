import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PublishappTestModule } from '../../../test.module';
import { PublicacionComponent } from 'app/entities/publicacion/publicacion.component';
import { PublicacionService } from 'app/entities/publicacion/publicacion.service';
import { Publicacion } from 'app/shared/model/publicacion.model';

describe('Component Tests', () => {
  describe('Publicacion Management Component', () => {
    let comp: PublicacionComponent;
    let fixture: ComponentFixture<PublicacionComponent>;
    let service: PublicacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublishappTestModule],
        declarations: [PublicacionComponent]
      })
        .overrideTemplate(PublicacionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PublicacionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PublicacionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Publicacion(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.publicacions && comp.publicacions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
