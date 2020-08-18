import { TestBed } from '@angular/core/testing';

import { PubeoService } from './pubeo.service';

describe('PubeoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PubeoService = TestBed.get(PubeoService);
    expect(service).toBeTruthy();
  });
});
