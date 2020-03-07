import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestTestModule } from '../../../test.module';
import { UniversidadUpdateComponent } from 'app/entities/universidad/universidad-update.component';
import { UniversidadService } from 'app/entities/universidad/universidad.service';
import { Universidad } from 'app/shared/model/universidad.model';

describe('Component Tests', () => {
  describe('Universidad Management Update Component', () => {
    let comp: UniversidadUpdateComponent;
    let fixture: ComponentFixture<UniversidadUpdateComponent>;
    let service: UniversidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [UniversidadUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UniversidadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UniversidadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UniversidadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Universidad(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Universidad();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
