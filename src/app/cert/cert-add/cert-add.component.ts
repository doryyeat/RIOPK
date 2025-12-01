import {Component, OnInit} from '@angular/core';
import {GlobalService} from "../../global.service";
import {CertService} from "../cert.service";
import {Router} from "@angular/router";
import {EnumService} from "../../enum.service";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {CategoryService} from "../../category/category.service";
import {NavigateDirective} from "../../navigate.directive";
import {KeyValuePipe, NgForOf, NgIf} from "@angular/common";

@Component({
	selector: 'app-cert-add',
	imports: [
		NavigateDirective,
		NgIf,
		ReactiveFormsModule,
		NgForOf,
		KeyValuePipe
	],
	templateUrl: './cert-add.component.html',
	standalone: true,
})

export class CertAddComponent implements OnInit {

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
	) {
	}

	get role() {
		return this.global.role;
	}

	ngOnInit(): void {
		if (this.global.role !== 'MANAGER') this.router.navigate(['/login'])

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
		if (this.img === null) return true;
		if (this.file === null) return true;

		return false;
	}

	save() {
		this.service.save(this.certFormGroup.value, this.reason, this.categoryId, this.img, this.file);
	}

}
