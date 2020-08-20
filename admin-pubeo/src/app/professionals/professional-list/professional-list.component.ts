import { Component, OnInit, Input, SimpleChanges } from '@angular/core';
import { Professionals } from 'src/app/shared/models/professionals.model';
import { PubeoService } from 'src/app/shared/services/pubeo.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-professional-list',
  templateUrl: './professional-list.component.html',
  styleUrls: ['./professional-list.component.css']
})
export class ProfessionalListComponent implements OnInit {

  @Input() event: Event;
  professionnals: Professionals[];
  constructor(private pubeoService: PubeoService, private router : Router) { }

  ngOnInit() {
    this.pubeoService.getAllProfessionals()
        .subscribe(data => this.professionnals = data,
                  () => this.connectionError());
  }

  deleteProfessional(Id: string){
    if(confirm("Voulez-vous vraiment supprimer cet utilisateur ?")){
      this.pubeoService.deleteProfessional(Id)
      .subscribe(null, error => this.alertError(error), () => this.proceedDelete(Id))
    }
  }

  proceedDelete(Id: string){
    for(let i = 0; i < this.professionnals.length; ++i){
      if (this.professionnals[i].id === Id) {
          this.professionnals.splice(i,1);
      }
    }
  }

  alertError(error: any){
    if(error.status == 404)
      alert("L'utilisateur n'existe pas");
    else
      this.connectionError();
  }

  ngOnChanges(changes: SimpleChanges) {
    if(changes.event.currentValue !== undefined){
      this.professionnals.push(changes.event.currentValue);
    }
  }

  connectionError(){
    this.router.navigate(['connectionError']);
  }
}
