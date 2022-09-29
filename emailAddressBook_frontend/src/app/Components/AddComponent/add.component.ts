import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { Contact } from 'src/app/Models/Contact';
import { ContactService } from 'src/app/Services/ContactService';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  addForm!:FormGroup
  hobbyArray!:FormArray
  constructor(private formBuilder: FormBuilder, private logger: NGXLogger, 
    private contactSvc:ContactService, private router: Router) { }

  ngOnInit(): void {

    this.hobbyArray = this.formBuilder.array([],[Validators.min(1)])

    this.addForm = this.formBuilder.group({
      name: this.formBuilder.control<String>('',[Validators.required]),
      email: this.formBuilder.control<String>('', [Validators.required, Validators.email]),
      mobile: this.formBuilder.control<String>('', [Validators.required, Validators.pattern('[0-9]*$'), Validators.minLength(8)]),
      profilePic: this.formBuilder.control(null, [Validators.required]),
      hobbies: this.hobbyArray
    })

    this.addHobby()

  }

  addHobby(){

    const hobbyGroup = this.formBuilder.group({
      hobby: this.formBuilder.control<String>('',[Validators.required, Validators.minLength(3)])
        .setErrors(null)
    })

    this.hobbyArray.push(hobbyGroup)

  }

  removeHobby(index: number){
    this.hobbyArray.removeAt(index)
}


  processForm(){
    const contact = this.addForm.value as Contact
    this.logger.info(contact)

    let newHobbyArr: string[] = []
    contact.hobbies.forEach(h=>newHobbyArr.push(Object.values(h).toString()))
    this.logger.info(">>>>>Processed Hobby array: " + newHobbyArr)
    contact.hobbies = newHobbyArr;

    this.contactSvc.saveContact(contact)
      .then(result=>{
        this.logger.info("See below for result")
        this.logger.info(result)
        this.router.navigate(['/contactDetails',result.userId])
      })

    this.resetForm()
  }

  resetForm(){
    this.addForm.reset()
    this.addForm.markAsUntouched()
    this.addForm.markAsPristine()
    Object.keys(this.addForm.controls).forEach(x=>{
      let control = this.addForm.controls[x]
      control.setErrors(null)
    })
    this.hobbyArray.clear()
    this.addHobby()
  }




}
