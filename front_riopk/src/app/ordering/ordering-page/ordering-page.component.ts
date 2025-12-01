import {Component, OnInit} from '@angular/core';
import {GlobalService} from "../../global.service";
import {OrderingService} from "../ordering.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {NavigateDirective} from "../../navigate.directive";
import html2canvas from "html2canvas";
import {jsPDF} from "jspdf";
import {NgIf} from "@angular/common";

@Component({
	selector: 'app-ordering-page',
	imports: [
		FormsModule,
		NavigateDirective,
		NgIf
	],
	templateUrl: './ordering-page.component.html',
	standalone: true,
})

export class OrderingPageComponent implements OnInit {

	ordering: any = null;

	constructor(
		private global: GlobalService,
		private service: OrderingService,
		private router: Router,
		private activatedRoute: ActivatedRoute,
	) {
	}

	get role() {
		return this.global.role;
	}

	ngOnInit(): void {
		if (this.role !== 'MANAGER' && this.role !== 'USER') this.router.navigate(['/login'])

		this.activatedRoute.queryParams.subscribe(value => {
			this.service.find(value['id']).subscribe({
				next: (res: any) => {
					this.ordering = res.data
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
	}

	get activate() {
		if (this.ordering.status === 'USED') return false;

		if (!this.ordering.activate) return false;

		if (this.role === 'MANAGER') return true;

		if (this.role === 'USER' && this.ordering.type === 'MYSELF') return true;

		return false;
	}

	used() {
		this.service.used(this.ordering.id).subscribe({
			next: (res: any) => {
				this.ordering = res.data
			},
			error: (e: any) => this.global.alert(e),
		})
	}

	generatePDF() {
		let data: any = document.getElementById('pdf');
		html2canvas(data).then(canvas => {
			const content = canvas.toDataURL('image/png');

			let jsPdf;
			if (canvas.width > canvas.height) {
				jsPdf = new jsPDF('p', 'cm', 'a4');
				jsPdf.addImage(content, 'PNG', 0, 0, 21, 0);
			} else {
				jsPdf = new jsPDF('p', 'pt', [canvas.width, canvas.height]);
				jsPdf.addImage(content, 'PNG', 0, 0, canvas.width, canvas.height);
			}

			jsPdf.save('pdf.pdf');
		});
	}

}
