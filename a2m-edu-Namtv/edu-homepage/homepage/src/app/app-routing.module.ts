import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import("./pages/pages.module").then(m=>m.PagesModule)
  },
  {
    path: '**',
    loadChildren: () => import("./errors/error404/error404.module").then(m=>m.Error404Module)
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {scrollPositionRestoration: 'top', relativeLinkResolution: 'legacy', useHash: true, preloadingStrategy: PreloadAllModules})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
