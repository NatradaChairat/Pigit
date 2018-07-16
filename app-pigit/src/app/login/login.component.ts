import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  model: any = {};
  error ='';
  constructor(private router: Router) { }

  ngOnInit() {
  }

  login(){
    if(this.model.username === "pigit" && this.model.password === "pigit1234"){

    }else{
      this.error = 'username or password is incorrect';
    }
  }

}
