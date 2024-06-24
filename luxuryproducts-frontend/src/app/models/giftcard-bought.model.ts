export class GiftcardBought {
  public id: number;
  public message: string;
  public price: number;
  public ownerEmail: string;


  constructor(id: number, message: string, price: number, ownerEmail: string, ) {
    this.id = id;
    this.message = message;
    this.price = price;
    this.ownerEmail = ownerEmail;
  }
}
