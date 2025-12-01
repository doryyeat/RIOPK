import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject} from "rxjs";
import {GlobalService} from "./global.service";

@Injectable({
	providedIn: 'root'
})

export class EnumService {

	enumSubject = new BehaviorSubject<any>({
		roles: [],
		certReasons: [],
		orderingTypes: [],
	})

	constructor(
		private http: HttpClient,
		private global: GlobalService,
	) {
	}

	private get url() {
		return this.global.backendURL + '/enums';
	}

	roles() {
		this.http.get(
			this.url + '/roles',
		).subscribe({
			next: (res: any) => this.enumSubject.next({
				...this.enumSubject.value,
				roles: res.data,
			}),
			error: (e: any) => console.log(e.error),
		})
	}

	certReasons() {
		this.http.get(
			this.url + '/certReasons',
		).subscribe({
			next: (res: any) => this.enumSubject.next({
				...this.enumSubject.value,
				certReasons: res.data,
			}),
			error: (e: any) => console.log(e.error),
		})
	}

	orderingTypes() {
		this.http.get(
			this.url + '/orderingTypes',
		).subscribe({
			next: (res: any) => this.enumSubject.next({
				...this.enumSubject.value,
				orderingTypes: res.data,
			}),
			error: (e: any) => console.log(e.error),
		})
	}

}
