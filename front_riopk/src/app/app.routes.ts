import {Routes} from '@angular/router';
import {ErrorComponent} from "./error/error.component";
import {StatsComponent} from "./stats/stats.component";
import {UserComponent} from "./user/user.component";
import {LoginComponent} from "./auth/login/login.component";
import {RegComponent} from "./auth/reg/reg.component";
import {MainComponent} from "./main/main.component";
import {CategoryComponent} from "./category/category.component";
import {CertComponent} from "./cert/cert.component";
import {CertPageComponent} from "./cert/cert-page/cert-page.component";
import {CertAddComponent} from "./cert/cert-add/cert-add.component";
import {CertUpdateComponent} from "./cert/cert-update/cert-update.component";
import {OrderingComponent} from "./ordering/ordering.component";
import {OrderingPageComponent} from "./ordering/ordering-page/ordering-page.component";

export const routes: Routes = [

	{path: "", component: MainComponent},

	{path: "reg", component: RegComponent},
	{path: "login", component: LoginComponent},

	{path: "users", component: UserComponent},

	{path: "categories", component: CategoryComponent},

	{path: "certs", component: CertComponent},
	{path: "cert", component: CertPageComponent},
	{path: "cert_add", component: CertAddComponent},
	{path: "cert_update", component: CertUpdateComponent},

	{path: "orderings", component: OrderingComponent},
	{path: "ordering", component: OrderingPageComponent},

	{path: "stats", component: StatsComponent},

	{path: "error", component: ErrorComponent},
	{path: "**", component: ErrorComponent},

];
