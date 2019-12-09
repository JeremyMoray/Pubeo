import { TestBed } from '@angular/core/testing';

import { ParticularsService } from './particulars.service';

describe('ParticularsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ParticularsService = TestBed.get(ParticularsService);
    expect(service).toBeTruthy();
  });
});
