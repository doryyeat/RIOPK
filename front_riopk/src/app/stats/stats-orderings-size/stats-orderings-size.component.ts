import {Component, OnInit} from '@angular/core';
import {GlobalService} from "../../global.service";
import {StatsService} from "../stats.service";
import {ChartComponent} from "ng-apexcharts";

@Component({
	selector: 'app-stats-orderings-size',
    imports: [
        ChartComponent
    ],
	templateUrl: './stats-orderings-size.component.html',
	standalone: true,
})

export class StatsOrderingsSizeComponent implements OnInit {

	chartOptions: any = null;

	names: any[] = [];
	values: any[] = [];

	constructor(
		private global: GlobalService,
		private service: StatsService,
	) {
	}

	ngOnInit(): void {
		this.service.orderingsSize().subscribe({
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
			series: [
				{
					name: "Количество",
					data: this.values
				}
			],
			chart: {
				height: 400,
				type: "bar",
			},
			colors: [
				"#008FFB",
				"#00E396",
				"#FEB019",
				"#FF4560",
				"#775DD0",
				"#546E7A",
				"#26a69a",
				"#D10CE8"
			],
			plotOptions: {
				bar: {
					columnWidth: "45%",
					distributed: true
				}
			},
			dataLabels: {
				enabled: false
			},
			legend: {
				show: false
			},
			grid: {
				show: false
			},
			xaxis: {
				categories: this.names,
				labels: {
					style: {
						colors: [
							"#008FFB",
							"#00E396",
							"#FEB019",
							"#FF4560",
							"#775DD0",
							"#546E7A",
							"#26a69a",
							"#D10CE8"
						],
						fontSize: "12px"
					}
				}
			}
		};
	}

}
