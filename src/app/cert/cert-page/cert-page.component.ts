import {Component, OnInit} from '@angular/core';
import {GlobalService} from "../../global.service";
import {CertService} from "../cert.service";
import {ActivatedRoute, Router} from "@angular/router";
import {OrderingService} from "../../ordering/ordering.service";
import {EnumService} from "../../enum.service";
import {NavigateDirective} from "../../navigate.directive";
import {KeyValuePipe, NgForOf, NgIf} from "@angular/common";
import {AlertService} from "../../alert/alert.service";
import {FormsModule} from "@angular/forms";

@Component({
	selector: 'app-cert-page',
	imports: [
		NavigateDirective,
		NgIf,
		FormsModule,
		NgForOf,
		KeyValuePipe
	],
	templateUrl: './cert-page.component.html',
	standalone: true,
})

export class CertPageComponent implements OnInit {

	cert: any = {
		name: '',
	}

	types: any[] = [];
	type: string = '';
	email: string = '';

	constructor(
		private global: GlobalService,
		private service: CertService,
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private orderingService: OrderingService,
		private enumService: EnumService,
		private alert: AlertService,
	) {
	}

	get role() {
		return this.global.role
	}

	ngOnInit(): void {
		this.activatedRoute.queryParams.subscribe(value => {
			this.service.find(value['id']).subscribe({
				next: (res: any) => {
					this.cert = res.data
				},
				error: (e: any) => {
					if (e.error.code === 404) {
						this.router.navigate(['/error'], {queryParams: {message: e.error.message}})
					} else {
						this.router.navigate(['/login'])
					}
				}
			})
		})

		if (this.role === 'USER') {
			this.enumService.enumSubject.subscribe(value => {
				this.types = value.orderingTypes;
			})
			this.enumService.orderingTypes();
		}
	}

	delete() {
		this.service.delete(this.cert.id);
	}

	get checkOrdering() {
		switch (this.type) {
			case 'GIFT':
				if (this.email !== '') return false;
				break;
			case 'MYSELF':
				return false;
		}

		return true;
	}

	ordering() {
		this.orderingService.save(this.type, this.cert.id, this.email).subscribe({
			next: () => {
				this.alert.add("Сертификат оформлен", "success")
			},
			error: (e: any) => this.global.alert(e),
		});
		setTimeout(() => {
			this.type = ''
			this.email = ''
		}, 500)
	}

}
