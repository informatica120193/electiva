import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PublishappTestModule } from '../../../test.module';
import { UniversidadDetailComponent } from 'app/entities/universidad/universidad-detail.component';
import { Universidad } from 'app/shared/model/universidad.model';

describe('Component Tests', () => {
  describe('Universidad Management Detail Component', () => {
    let comp: UniversidadDetailComponent;
    let fixture: ComponentFixture<UniversidadDetailComponent>;
    const route = ({ data: of({ universidad: new Universidad(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublishappTestModule],
        declarations: [UniversidadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UniversidadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UniversidadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load universidad on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.universidad).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
