export class Giftcard {
  public id: number;
  public message: string;
  public price: number;
  public value: number;
  public buyerEmail: string;
  public buyerName: string;
  public ownerEmail: string;
  public ownerName: string;


  constructor(id: number, message: string, price: number, value: number, buyerEmail: string, buyerName: string, ownerEmail: string, ownerName: string) {
    this.id = id;
    this.message = message;
    this.price = price;
    this.value = value;
    this.buyerEmail = buyerEmail;
    this.buyerName = buyerName;
    this.ownerEmail = ownerEmail;
    this.ownerName = ownerName;
  }
}
