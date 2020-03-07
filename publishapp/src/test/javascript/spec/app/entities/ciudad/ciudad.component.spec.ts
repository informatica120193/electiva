import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PublishappTestModule } from '../../../test.module';
import { CiudadComponent } from 'app/entities/ciudad/ciudad.component';
import { CiudadService } from 'app/entities/ciudad/ciudad.service';
import { Ciudad } from 'app/shared/model/ciudad.model';

describe('Component Tests', () => {
  describe('Ciudad Management Component', () => {
    let comp: CiudadComponent;
    let fixture: ComponentFixture<CiudadComponent>;
    let service: CiudadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublishappTestModule],
        declarations: [CiudadComponent]
      })
        .overrideTemplate(CiudadComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CiudadComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CiudadService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Ciudad(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ciudads && comp.ciudads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
