import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {FilenotfoundComponent} from "./filenotfound/filenotfound.component";
import {ViewProductComponent} from "./product/view-product/view-product.component";
import {AddProductComponent} from "./product/add-product/add-product.component";
import {ListProductComponent} from "./product/list-product/list-product.component";

const appRoutes: Routes= [
  {path: 'detail/:id', component: ViewProductComponent},
  {path: 'add', component: AddProductComponent},
  {path: 'list', component: ListProductComponent},

  {path: '**', component: FilenotfoundComponent}
];
@NgModule({
  imports:[RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
