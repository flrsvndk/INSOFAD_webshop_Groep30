export class GiftcardOwned {
  public id: number;
  public message: string;
  public price: number;
  public value: number;
  public buyerEmail: string;
  public ownerEmail: string;


  constructor(id: number, message: string, price: number, value: number, buyerEmail: string, ownerEmail: string) {
    this.id = id;
    this.message = message;
    this.price = price;
    this.value = value;
    this.buyerEmail = buyerEmail;
    this.ownerEmail = ownerEmail;
  }
}
