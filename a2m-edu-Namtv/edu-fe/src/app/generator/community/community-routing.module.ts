import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from 'src/app/guard/role.guard';
import { ListPostComponent } from './post/list-post/list-post.component';
import { PostDiscussComponent } from './post/post-discuss/post-discuss.component';
import { PostFormComponent } from './post/post-form/post-form.component';


const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        component: ListPostComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'post',
        component: ListPostComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'post/:postId',
        component: PostDiscussComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'post-form',
        component: PostFormComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'post-form/**',
        component: PostFormComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'post-form/:postId',
        component: PostFormComponent,
        canActivate: [RoleGuard],
      },
    ],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CommunityRoutingModule { }
