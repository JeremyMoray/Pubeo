import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ParticularsService } from 'src/app/shared/particulars.service';
import { Particulars } from 'src/app/shared/particulars.model';

@Component({
  selector: 'app-particular-details',
  templateUrl: './particular-details.component.html',
  styleUrls: ['./particular-details.component.css']
})
export class ParticularDetailsComponent implements OnInit {

  Id;
  particular: Particulars;
  constructor(private route:ActivatedRoute, private particularsService: ParticularsService) { }

  ngOnInit() {
    this.Id= this.route.snapshot.paramMap.get('Id');
    this.particular = this.particularsService.getParticularById(this.Id);
  }

}
