import { Component, OnInit } from '@angular/core';
import { Professionals } from 'src/app/shared/models/professionals.model';
import { ActivatedRoute } from '@angular/router';
import { PubeoService } from 'src/app/shared/services/pubeo.service';
import { Sticker } from 'src/app/shared/models/sticker.model';

@Component({
  selector: 'app-professional-details',
  templateUrl: './professional-details.component.html',
  styleUrls: ['./professional-details.component.css']
})
export class ProfessionalDetailsComponent implements OnInit {

  Id;
  professional: Professionals;
  stickers: Sticker[];
  errorStatus: number;

  constructor(private route: ActivatedRoute, private pubeoService: PubeoService) { }

  ngOnInit() {
    this.Id= this.route.snapshot.paramMap.get('Id');
    this.pubeoService.getProfessionalById(this.Id)
        .subscribe(data => this.professional = data,
          error => this.errorStatus = error.status);
    this.pubeoService.getAllStickersByProfessionalId(this.Id)
        .subscribe(data => this.stickers = data,
          error => this.errorStatus = error.status);
  }

}
