import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GlobalService} from "../global.service";

@Injectable({
	providedIn: 'root'
})
export class StatsService {

	constructor(
		private http: HttpClient,
		private global: GlobalService
	) {
	}

	private get url() {
		return this.global.backendURL + '/stats'
	}

	orderingsSize() {
		return this.http.get(
			this.url + '/orderings/size',
			{headers: this.global.headersToken}
		);
	}

	categoriesCertsSize() {
		return this.http.get(
			this.url + '/categories/certs/size',
			{headers: this.global.headersToken}
		);
	}

	orderingsStatus() {
		return this.http.get(
			this.url + '/orderings/status',
			{headers: this.global.headersToken}
		);
	}

	certsCtr() {
		return this.http.get(
			this.url + '/certs/ctr',
			{headers: this.global.headersToken}
		);
	}

}
