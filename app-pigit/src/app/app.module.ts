import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {AppRoutingModule} from "./app-routing.module";
import { FilenotfoundComponent } from './filenotfound/filenotfound.component';
import { ProductComponent } from './product/product.component';
import { AddProductComponent } from './product/add-product/add-product.component';
import { ListProductComponent } from './product/list-product/list-product.component';
import { ViewProductComponent } from './product/view-product/view-product.component';
import { PreorderComponent } from './preorder/preorder.component';
import { LoginComponent } from './login/login.component';
import { NavigatorBarComponent } from './navigator-bar/navigator-bar.component';

@NgModule({
  declarations: [
    AppComponent,
    FilenotfoundComponent,
    ProductComponent,
    AddProductComponent,
    ListProductComponent,
    ViewProductComponent,
    PreorderComponent,
    LoginComponent,
    NavigatorBarComponent
  ],
  imports: [
    BrowserModule, AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
