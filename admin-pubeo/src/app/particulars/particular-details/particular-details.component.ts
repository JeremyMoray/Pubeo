import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PubeoService } from 'src/app/shared/services/pubeo.service';
import { Particulars } from 'src/app/shared/models/particulars.model';

@Component({
  selector: 'app-particular-details',
  templateUrl: './particular-details.component.html',
  styleUrls: ['./particular-details.component.css']
})
export class ParticularDetailsComponent implements OnInit {

  Id;
  particular: Particulars;
  constructor(private route:ActivatedRoute, private pubeoService: PubeoService) { }

  ngOnInit() {
    this.Id= this.route.snapshot.paramMap.get('Id');
    this.pubeoService.getParticularById(this.Id)
        .subscribe(data => this.particular = data);
  }

}
