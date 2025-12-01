import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {GlobalService} from "../global.service";
import {Router} from "@angular/router";

@Injectable({
	providedIn: 'root'
})

export class CertService {

	certSubject = new BehaviorSubject<any>({
		certs: [],
	})

	constructor(
		private http: HttpClient,
		private global: GlobalService,
		private router: Router,
	) {
	}

	private get url() {
		return this.global.backendURL + '/certs'
	}

	private page(id: number) {
		this.router.navigate(['/cert'], {queryParams: {id: id}})
	}

	findAll() {
		this.http.get(
			this.url,
		).subscribe({
			next: (res: any) => this.certSubject.next({
				...this.certSubject.value,
				certs: res.data,
			}),
			error: (e: any) => this.global.alert(e),
		})
	}

	find(id: number) {
		return this.http.get(
			this.url + `/${id}`,
		)
	}

	save(cert: any, reason: string, categoryId: number, img: any, file: any) {
		this.http.post(
			this.url,
			JSON.stringify(cert),
			{
				headers: this.global.headersJsonToken,
				params: new HttpParams().appendAll({
					reason: reason,
					categoryId: categoryId,
				})
			}
		).subscribe({
			next: (res: any) => this.updateImg(res.data.id, img, file),
			error: (e: any) => this.global.alert(e),
		})
	}

	update(id: number, cert: any, reason: string, categoryId: number, img: any, file: any) {
		this.http.put(
			this.url + `/${id}`,
			JSON.stringify(cert),
			{
				headers: this.global.headersJsonToken,
				params: new HttpParams().appendAll({
					reason: reason,
					categoryId: categoryId,
				})
			}
		).subscribe({
			next: (res: any) => {
				if (img !== null) this.updateImg(res.data.id, img, file)
				else if (file !== null) this.updateFile(res.data.id, file)
				else this.page(res.data.id)
			},
			error: (e: any) => this.global.alert(e),
		})
	}

	private updateImg(id: number, img: any, file: any) {
		let formData = new FormData();
		for (let i = 0; i < img.length; i++) {
			formData.append('files', img[i]);
		}
		this.http.patch(
			this.url + `/${id}/img`,
			formData,
			{headers: this.global.headersMultipartToken}
		).subscribe({
			next: (res: any) => {
				if (file !== null) this.updateFile(res.data.id, file)
				else this.page(res.data.id)
			},
			error: (e: any) => this.global.alert(e),
		})
	}

	private updateFile(id: number, file: any) {
		let formData = new FormData();
		for (let i = 0; i < file.length; i++) {
			formData.append('files', file[i]);
		}
		this.http.patch(
			this.url + `/${id}/file`,
			formData,
			{headers: this.global.headersMultipartToken}
		).subscribe({
			next: (res: any) => this.page(res.data.id),
			error: (e: any) => this.global.alert(e),
		})
	}

	delete(id: number) {
		this.http.delete(
			this.url + `/${id}`,
			{headers: this.global.headersToken}
		).subscribe({
			next: () => this.router.navigate(['/certs']),
			error: (e: any) => this.global.alert(e),
		})
	}

}
