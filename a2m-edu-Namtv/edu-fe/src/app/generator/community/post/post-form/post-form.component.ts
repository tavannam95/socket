import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-post-form',
  templateUrl: './post-form.component.html',
  styleUrls: ['./post-form.component.css']
})
export class PostFormComponent implements OnInit {
  configCkeditor: any = {};
  post: any = {
    postTitle: "",
    postContent: ""
  }
  postId: any = "";
  commingsoon = false;
  referenceType: any = "";
  referenceId: Number = NaN;
  constructor(
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    this.postId = Number(routeParams.get('postId'));
    
    this.route.queryParams.subscribe( (params) => {
      this.referenceType = params['referenceType'];
      this.referenceId = params['referenceId'];
    });
  }
}
