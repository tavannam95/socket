import { PagesComponent } from './pages.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    component: PagesComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      {
        path: 'home',
        loadChildren: () =>
          import('./home/home.module').then((m) => m.HomeModule),
      },
      {
        path: 'contact-us',
        loadChildren: () =>
          import('./contact/contact.module').then((m) => m.ContactModule),
      },
      {
        path: 'about-us',
        loadChildren: () =>
          import('./about-us/about-us.module').then((m) => m.AboutUsModule),
      },
      {
        path: 'blog',
        loadChildren: () =>
          import('./blog/blog.module').then((m) => m.BlogModule),
      },
      {
        path: 'courses',
        loadChildren: () =>
          import('./courses/courses.module').then((m) => m.CoursesModule),
      },
      {
        path: 'guide',
        loadChildren: () =>
          import('./guide/guide.module').then((m) => m.GuideModule),
      },
      {
        path: 'instructors',
        loadChildren: () =>
          import('./instructors/instructors.module').then(
            (m) => m.InstructorsModule
          ),
      },
      {
        path: 'apply',
        loadChildren: () =>
          import('./apply/apply.module').then((m) => m.ApplyModule),
      },
      {
        path: 'applyForCourse',
        loadChildren: () =>
          import('./applyForCourse/applyForCourse.module').then(
            (m) => m.ApplyForCourseModule
          ),
      },
      {
        path: 'students',
        loadChildren: () =>
          import('./students/students.module').then((m) => m.StudentsModule),
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {}
