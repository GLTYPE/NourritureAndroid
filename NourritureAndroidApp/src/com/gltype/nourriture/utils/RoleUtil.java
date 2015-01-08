package com.gltype.nourriture.utils;

public class RoleUtil {
	public String roleStr;
	public RoleUtil(int role){
				switch(role) {
			case 1: {
				this.roleStr="consumer";
				break;
			}
			case 2: {
				this.roleStr= "food supplier";
				break;
			}
			case 3:{
				this.roleStr= "gastronomist";
				break;
			}
			case 4:{
				this.roleStr= "administer";
				break;
			}
				}

	}
	public String getRoleStr() {
		return roleStr;
	}

}
