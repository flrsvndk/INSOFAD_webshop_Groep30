import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {RetourReason} from "../models/retour-reason.model";
import {environment} from "../../environments/environment";
import {NgForm} from "@angular/forms";
import {RetourRequest} from "../models/retour-request.model";

@Injectable({
  providedIn: 'root'
})
export class RetourService {

  private baseUrl: string = environment.base_url + "/retour";
  constructor(private http: HttpClient) { }

  public getReasons(): Observable<RetourReason[]> {
    return this.http.get<RetourReason[]>(this.baseUrl + "/reasons");
  }

  public createRetourRequest(form: NgForm, orderId: string): Observable<RetourRequest> {
    var retourRequest: RetourRequest = new RetourRequest();
    retourRequest = this.readFormValues(form, retourRequest);
    retourRequest.orderId = orderId;
    return this.http.post<RetourRequest>(this.baseUrl, retourRequest);
  }

  public getUserRetourRequests(): Observable<RetourRequest[]> {
    return this.http.get<RetourRequest[]>(this.baseUrl);
  }

  public getAllRetourRequests(): Observable<RetourRequest[]> {
    return this.http.get<RetourRequest[]>(this.baseUrl + "/all");
  }

  public acceptRetourRequest(id: number): Observable<RetourRequest> {
    return this.http.put<RetourRequest>(this.baseUrl + "/accept", {
      "id": id,
    });
  }

  public declineRetourRequest(id: number): Observable<RetourRequest> {
    return this.http.put<RetourRequest>(this.baseUrl + "/decline", {
      "id": id,
    });
  }

  public refundRetourRequest(id: number): Observable<RetourRequest> {
    return this.http.put<RetourRequest>(this.baseUrl + "/refund", {
      "id": id,
    });
  }

  private readFormValues(form: NgForm, retourRequest: RetourRequest): RetourRequest {
    const orderItemIds: number[] = [];
    for (const key in form.form.value) {
      if (form.form.value.hasOwnProperty(key)) {
        const value = form.form.value[key];
        if (key === 'comment' && typeof value === 'string') {
          retourRequest.comment = value;
        } else if (key === 'reason' && typeof value === 'string') {
          retourRequest.reasonId = value;
        } else {
          if (typeof value === 'boolean' && value) {
            orderItemIds.push(+key);
          }
        }
      }
    }
    retourRequest.orderItemIds = orderItemIds;
    return retourRequest;
  }
}