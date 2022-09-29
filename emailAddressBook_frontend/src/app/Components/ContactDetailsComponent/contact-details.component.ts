import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { Contact } from 'src/app/Models/Contact';
import { ContactService } from 'src/app/Services/ContactService';

@Component({
  selector: 'app-contact-details',
  templateUrl: './contact-details.component.html',
  styleUrls: ['./contact-details.component.css']
})
export class ContactDetailsComponent implements OnInit {

  userId!:string
  contact!: Contact
  profilePicUrl!:String
  hobbies!:String

  constructor(private activatedRoute:ActivatedRoute, private title:Title,
    private contactSvc: ContactService, private logger:NGXLogger,
    private router: Router) { }

  ngOnInit(): void {

    this.userId = this.activatedRoute.snapshot.params['userId']

    this.contactSvc.getContactByUserId(this.userId)
      .then(result=>{
        this.logger.info(result)
        this.contact = result
        this.logger.info("Contact info below")
        this.logger.info(this.contact)
        this.profilePicUrl = result.profile_pic_url

        this.hobbies = result.hobbies.toString().replace(/[^a-zA-Z ]/g, " ")
      })


    
  }

  backToList(){
    this.router.navigate(['/list'])
  }

  deleteContact(){

    this.logger.info(this.contact.userId)
    this.contactSvc.deleteContactByUserId(this.contact.userId)
      .then(result=>console.log(result))

    this.router.navigate(['/list'])
      .then(()=>window.location.reload())
    
  }



}
