import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {GlobalService} from "../global.service";
import {Router} from "@angular/router";

@Injectable({
	providedIn: 'root'
})

export class OrderingService {

	orderingSubject = new BehaviorSubject<any>({
		orderings: [],
	})

	constructor(
		private http: HttpClient,
		private global: GlobalService,
		private router: Router,
	) {
	}

	private get url() {
		return this.global.backendURL + '/orderings'
	}

	findAll() {
		this.http.get(
			this.url,
			{headers: this.global.headersToken}
		).subscribe({
			next: (res: any) => this.orderingSubject.next({
				...this.orderingSubject.value,
				orderings: res.data,
			}),
			error: (e: any) => this.global.alert(e),
		})
	}

	find(id: number) {
		return this.http.get(
			this.url + `/${id}`,
			{headers: this.global.headersToken}
		)
	}

	save(type: string, certId: number, email: string) {
		return this.http.post(
			this.url,
			"",
			{
				headers: this.global.headersToken,
				params: new HttpParams().appendAll({
					type: type,
					certId: certId,
					email: email
				})
			}
		)
	}

	used(id: number) {
		return this.http.patch(
			this.url + `/${id}/used`,
			"",
			{headers: this.global.headersToken}
		)
	}

}
