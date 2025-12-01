import {Component, OnInit} from '@angular/core';
import {StatsService} from "../stats.service";
import {GlobalService} from "../../global.service";
import {ChartComponent} from "ng-apexcharts";

@Component({
	selector: 'app-stats-categories-certs-size',
	imports: [
		ChartComponent
	],
	templateUrl: './stats-categories-certs-size.component.html',
	standalone: true,
})

export class StatsCategoriesCertsSizeComponent implements OnInit {

	chartOptions: any = null;

	names: any[] = [];
	values: any[] = [];

	constructor(
		private global: GlobalService,
		private service: StatsService,
	) {
	}

	ngOnInit(): void {
		this.service.categoriesCertsSize().subscribe({
			next: (res: any) => {
				this.names = res.data.names;
				this.values = res.data.values;
				this.draw();
			},
			error: (e: any) => this.global.alert(e)
		})
	}

	draw() {
		this.chartOptions = {
			labels: this.names,
			series: this.values,
			chart: {
				height: 400,
				type: "pie"
			},
			responsive: [
				{
					breakpoint: 480,
					options: {
						chart: {
							width: 200
						},
						legend: {
							position: "bottom"
						}
					}
				}
			]
		};
	}

}
