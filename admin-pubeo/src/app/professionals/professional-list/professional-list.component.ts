import { Component, OnInit } from '@angular/core';
import { Professionals } from 'src/app/shared/professionals.model';
import { PubeoService } from 'src/app/shared/pubeo.service';

@Component({
  selector: 'app-professional-list',
  templateUrl: './professional-list.component.html',
  styleUrls: ['./professional-list.component.css']
})
export class ProfessionalListComponent implements OnInit {

  list: Professionals[];
  constructor(private pubeoService: PubeoService) { }

  ngOnInit() {
    return this.pubeoService.getAllProfessionals().subscribe(data => this.list = data);
  }

  onDelete(id: string) {
    if (confirm('Etes-vous sûr de vouloir supprimer cette ligne ?')) {
      this.pubeoService.deleteProfessional(id).subscribe(res => {
        window.location.reload();
      });
    }
  }

}
