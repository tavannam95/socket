import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guard/auth.guard';
import { RoleGuard } from './guard/role.guard';

const routes: Routes = [
  // {
  //   path: '',
  //   redirectTo: 'login',
  //   pathMatch: 'full'
  // },
  {
    path: 'login',
    loadChildren: () => import("./auth/login/login.module").then(m=>m.LoginModule)
  },
  {
    path: 'sign-up',
    loadChildren: () => import("./auth/sign-up/sign-up.module").then(m=>m.SignUpModule)
  },
  {
    path: 'chat',
    loadChildren: () => import("./generator/chat/chat.module").then(m=>m.ChatModule)
  },
  {
    path: 'iqTestViewPublic',
    loadChildren: ()=> import("./iqTest-public/iqTest-public.module").then(m=>m.IQTestPublicModule)
  },
  {
    path: '404',
    loadChildren: ()=> import("./error/error404/error404.module").then(m=>m.Error404Module)
  },
  {
    path: '',
    loadChildren: ()=> import("./generator/generator.module").then(m=>m.GeneratorModule),
    canLoad: [AuthGuard],
    // canActivate: [RoleGuard]
  },


  {
    path: '**',
    redirectTo: '404',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {scrollPositionRestoration: 'top', relativeLinkResolution: 'legacy', useHash: true , preloadingStrategy: PreloadAllModules})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
