import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { NGXLogger } from 'ngx-logger';
import { Contact } from 'src/app/Models/Contact';
import { ContactService } from 'src/app/Services/ContactService';
import {MatTableDataSource} from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit, AfterViewInit {

  constructor(private contactSvc:ContactService, private logger: NGXLogger,
    private router: Router) { }

  contacts!:Contact[]
  displayedColumns!: string[];
  dataSource!:any

  @ViewChild(MatPaginator) 
  paginator!: MatPaginator

  ngOnInit(): void {

    this.contactSvc.getAllContacts()
      .then(result=>{
        this.contacts = result
        this.logger.info(this.contacts)
        this.displayedColumns = ['name', 'email', 'mobile']
        this.dataSource = new MatTableDataSource(this.contacts)
        this.dataSource.paginator = this.paginator
      })
      .catch(error=>this.logger.error(error))
  }

  ngAfterViewInit(): void {

  }
  search(event: Event){

    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    this.logger.info(this.dataSource)
  }

  selectContact(contact:Contact){
    this.logger.info(contact)
    this.router.navigate(['/contactDetails',contact.userId])
  }

}
