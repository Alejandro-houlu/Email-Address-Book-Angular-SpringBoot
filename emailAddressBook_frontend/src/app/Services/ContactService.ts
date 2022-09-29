import { HttpClient, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { NGXLogger } from "ngx-logger";
import { firstValueFrom } from "rxjs";
import { Contact } from "../Models/Contact";

@Injectable()
export class ContactService {

constructor(private http:HttpClient, private logger: NGXLogger){}

getAllContacts():Promise<Contact[]>{
    return firstValueFrom(
        this.http.get<Contact[]>('api/contacts')
    )
}

saveContact(contact:Contact):Promise<Contact>{

    const formData = new FormData()

    formData.set('name',contact.name)
    formData.set('email',contact.email)
    formData.set('mobile',contact.mobile)
    formData.set('profilePic',contact.profilePic)
    formData.set('hobbies', JSON.stringify(contact.hobbies))

    this.logger.info(formData.get("hobbies"))


    return firstValueFrom( this.http.post<any>('/api/contact/save',formData))

}

getContactByUserId(userId:string):Promise<Contact>{

    return firstValueFrom(
        this.http.get<Contact>(`/api/contact/${userId}`)
    )
}

deleteContactByUserId(userId:string):Promise<Response>{
    return firstValueFrom(
        this.http.post<any>('/api/contact/delete',userId))
            .catch(error=>this.logger.info(error))
    
}

}