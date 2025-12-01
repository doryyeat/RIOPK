import {Component, OnInit} from '@angular/core';
import {GlobalService} from "../../global.service";
import {StatsService} from "../stats.service";
import {ChartComponent} from "ng-apexcharts";

@Component({
	selector: 'app-stats-orderings-status',
	imports: [
		ChartComponent
	],
	templateUrl: './stats-orderings-status.component.html',
	standalone: true,
})

export class StatsOrderingsStatusComponent implements OnInit {

	chartOptions: any = null;

	names: any[] = [];
	values: any[] = [];

	constructor(
		private global: GlobalService,
		private service: StatsService,
	) {
	}

	ngOnInit(): void {
		this.service.orderingsStatus().subscribe({
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
