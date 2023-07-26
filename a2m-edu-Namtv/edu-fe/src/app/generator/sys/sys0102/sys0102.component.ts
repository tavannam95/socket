import { Component, OnInit, ViewChild } from '@angular/core';
import { ProgramRoleComponent } from './components/program-role/program-role.component';
import { RoleListComponent } from './components/role-list/role-list.component';
import { RoleProgramComponent } from './components/role-program/role-program.component';
import { RoleUserComponent } from './components/role-user/role-user.component';
import { UserRoleComponent } from './components/user-role/user-role.component';

@Component({
  selector: 'app-sys0102',
  templateUrl: './sys0102.component.html',
  styleUrls: ['./sys0102.component.css']
})
export class Sys0102Component implements OnInit {

  @ViewChild('roleList')
  roleList!: RoleListComponent
  @ViewChild('roleUser')
  roleUser!: RoleUserComponent
  @ViewChild('userRole')
  userRole!: UserRoleComponent
  @ViewChild('roleProgram')
  roleProgram!: RoleProgramComponent
  @ViewChild('programRole')
  programRole!: ProgramRoleComponent

  constructor() { }

  ngOnInit(): void {
  }

  initData(){
    this.roleList.getData();
  }

  changeTab(tabNumber: any){
    switch(tabNumber){
      case 1: 
        // this.roleList.getData();
        this.roleList.ngOnInit();
        break;

      case 2: 
        // this.roleUser.initData();
        this.roleUser.ngOnInit();
        break;

      case 3: 
        // this.userRole.initData();
        this.userRole.ngOnInit();
        break;

      case 4: 
        // this.roleProgram.initData();
        this.roleProgram.ngOnInit();
        break;

      case 5: 
        // this.programRole.initData();
        this.programRole.ngOnInit();
        break;
    }
  }
}
