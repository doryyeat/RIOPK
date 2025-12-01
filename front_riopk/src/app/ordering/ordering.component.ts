import {Component, OnInit} from '@angular/core';
import {GlobalService} from "../global.service";
import {OrderingService} from "./ordering.service";
import {Router} from "@angular/router";
import {NavigateDirective} from "../navigate.directive";

@Component({
	selector: 'app-ordering',
	imports: [
		NavigateDirective
	],
	templateUrl: './ordering.component.html',
	standalone: true,
})

export class OrderingComponent implements OnInit {

	orderings: any[] = [];

	get orderingsSorted() {
		let res = this.orderings;

		return res;
	}

	constructor(
		private global: GlobalService,
		private service: OrderingService,
		private router: Router,
	) {
	}

	get role() {
		return this.global.role;
	}

	ngOnInit(): void {
		if (this.role !== 'MANAGER' && this.role !== 'USER') this.router.navigate(['/login'])

		this.service.orderingSubject.subscribe(value => {
			this.orderings = value.orderings;
		})
		this.service.findAll();
	}

}
