import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestTestModule } from '../../../test.module';
import { UniversidadComponent } from 'app/entities/universidad/universidad.component';
import { UniversidadService } from 'app/entities/universidad/universidad.service';
import { Universidad } from 'app/shared/model/universidad.model';

describe('Component Tests', () => {
  describe('Universidad Management Component', () => {
    let comp: UniversidadComponent;
    let fixture: ComponentFixture<UniversidadComponent>;
    let service: UniversidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [UniversidadComponent]
      })
        .overrideTemplate(UniversidadComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UniversidadComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UniversidadService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Universidad(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.universidads && comp.universidads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
