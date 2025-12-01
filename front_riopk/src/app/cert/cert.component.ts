import {Component, OnInit} from '@angular/core';
import {GlobalService} from "../global.service";
import {CertService} from "./cert.service";
import {NavigateDirective} from "../navigate.directive";
import {NgIf} from "@angular/common";

@Component({
	selector: 'app-cert',
	imports: [
		NavigateDirective,
		NgIf
	],
	templateUrl: './cert.component.html',
	standalone: true,
})

export class CertComponent implements OnInit {

	certs: any[] = [];

	get certsSorted() {
		let res = this.certs;

		return res;
	}

	constructor(
		private global: GlobalService,
		private service: CertService,
	) {
	}

	get role() {
		return this.global.role;
	}

	ngOnInit(): void {
		this.service.certSubject.subscribe(value => {
			this.certs = value.certs;
		})
		this.service.findAll();
	}

}
