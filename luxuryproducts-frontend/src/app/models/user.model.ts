import {Adress} from "./adress.model";

export class User {
  public id?: string;
  public name: string;
  public infix: string;
  public lastName: string;
  public email: string;
  public imgUrl: string;
  public adress: Adress;
  public role: string;
  //public token: string;
}
