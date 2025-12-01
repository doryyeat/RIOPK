import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {GlobalService} from "../../global.service";
import {CertService} from "../cert.service";
import {ActivatedRoute, Router} from "@angular/router";
import {EnumService} from "../../enum.service";
import {CategoryService} from "../../category/category.service";
import {KeyValuePipe, NgForOf} from "@angular/common";
import {NavigateDirective} from "../../navigate.directive";

@Component({
	selector: 'app-cert-update',
	imports: [
		FormsModule,
		KeyValuePipe,
		NavigateDirective,
		NgForOf,
		ReactiveFormsModule
	],
	templateUrl: './cert-update.component.html',
	standalone: true,
})

export class CertUpdateComponent implements OnInit {

	id: number = 0;

	certFormGroup = new FormGroup({
		name: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		address: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		price: new FormControl("", [Validators.required, Validators.min(0.01), Validators.max(1000000)]),
		term: new FormControl("", [Validators.required, Validators.min(1), Validators.max(100)]),
		description: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(5000)]),
	})

	categories: any[] = [];
	categoryId: any = null;

	reasons: any[] = [];
	reason: any = null;

	img: any = null;
	file: any = null;

	constructor(
		private global: GlobalService,
		private service: CertService,
		private router: Router,
		private enumService: EnumService,
		private categoryService: CategoryService,
		private activatedRoute: ActivatedRoute,
	) {
	}

	get role() {
		return this.global.role;
	}

	ngOnInit(): void {
		if (this.global.role !== 'MANAGER') this.router.navigate(['/login'])

		this.activatedRoute.queryParams.subscribe(value => {
			this.id = value['id'];
			this.service.find(value['id']).subscribe({
				next: (res: any) => {
					this.certFormGroup.setValue({
						name: res.data.name,
						address: res.data.address,
						price: res.data.price,
						term: res.data.term,
						description: res.data.description,
					})
					this.categoryId = res.data.categoryId;
					this.reason = res.data.reason;
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

		this.categoryService.categorySubject.subscribe(value => {
			this.categories = value.categories;
		})
		this.categoryService.findAll();

		this.enumService.enumSubject.subscribe(value => {
			this.reasons = value.certReasons;
		})
		this.enumService.certReasons();
	}

	changeCategoryId(event: any) {
		this.categoryId = event.target.value
	}

	changeReason(event: any) {
		this.reason = event.target.value
	}

	changeImg(event: any) {
		this.img = event.target.files
	}

	changeFile(event: any) {
		this.file = event.target.files
	}

	get checkSubmit() {
		if (this.certFormGroup.invalid) return true;
		if (this.categoryId === null) return true;
		if (this.reason === null) return true;

		return false;
	}

	update() {
		this.service.update(this.id, this.certFormGroup.value, this.reason, this.categoryId, this.img, this.file);
	}

}
