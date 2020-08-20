import { Component, OnInit, Input, SimpleChanges } from '@angular/core';
import { PubeoService } from 'src/app/shared/services/pubeo.service';
import { Particulars } from 'src/app/shared/models/particulars.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-particular-list',
  templateUrl: './particular-list.component.html',
  styleUrls: ['./particular-list.component.css']
})
export class ParticularListComponent implements OnInit {

  @Input() event: Event;
  particulars: Particulars[];
  constructor(private pubeoService: PubeoService, private router : Router) { }

  ngOnInit() {
    this.pubeoService.getAllParticulars()
        .subscribe(data => this.particulars = data,
                  () => this.connectionError());
  }

  deleteParticular(Id: string){
    if(confirm("Voulez-vous vraiment supprimer cet utilisateur ?")){
      this.pubeoService.deleteParticular(Id)
      .subscribe(null, error => this.alertError(error), () => this.proceedDelete(Id))
    }
  }

  proceedDelete(Id: string){
    for(let i = 0; i < this.particulars.length; ++i){
      if (this.particulars[i].id === Id) {
          this.particulars.splice(i,1);
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
      this.particulars.push(changes.event.currentValue);
    }
  }

  connectionError(){
      this.router.navigate(['connectionError']);
  }
}
