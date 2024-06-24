export class GiftcardPurchase {
  public message: string;
  public price: number;
  public ownerEmail: string;


  constructor(message: string, price: number, ownerEmail: string) {
    this.message = message;
    this.price = price;
    this.ownerEmail = ownerEmail;
  }
}
