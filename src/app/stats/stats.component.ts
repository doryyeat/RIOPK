import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {NgApexchartsModule} from "ng-apexcharts";
import {FormsModule} from "@angular/forms";
import {GlobalService} from "../global.service";
import {StatsCategoriesCertsSizeComponent} from "./stats-categories-certs-size/stats-categories-certs-size.component";
import {StatsCertsCtrComponent} from "./stats-certs-ctr/stats-certs-ctr.component";
import {StatsOrderingsSizeComponent} from "./stats-orderings-size/stats-orderings-size.component";
import {StatsOrderingsStatusComponent} from "./stats-orderings-status/stats-orderings-status.component";

@Component({
	selector: 'app-stats',
	standalone: true,
	imports: [
		NgApexchartsModule,
		FormsModule,
		StatsCategoriesCertsSizeComponent,
		StatsCertsCtrComponent,
		StatsOrderingsSizeComponent,
		StatsOrderingsStatusComponent
	],
	templateUrl: './stats.component.html',
})

export class StatsComponent implements OnInit {

	constructor(
		private router: Router,
		private global: GlobalService,
	) {
	}

	ngOnInit(): void {
		if (this.global.role !== 'ADMIN') this.router.navigate(['/login']);
	}

}
