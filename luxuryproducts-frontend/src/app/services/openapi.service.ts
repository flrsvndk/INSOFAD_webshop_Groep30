import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class OpenAPIService {

  private headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${environment.API_KEY}`
  });

  constructor(private http: HttpClient) { }

  public generateResponse(query: string): Observable<any> {
    const headers = this.headers;
    const body = {
      "model": "gpt-3.5-turbo",
      "messages": [{"role": "user", "content": environment.PROMPT + query}],
      "temperature": 0.7
    };

    return this.http.post(environment.API_RESPONSE_URL, body, { headers });
  }

  public generateSpeech(text: string): Observable<Blob> {
    const headers = this.headers;
    const body = {
      "model": "tts-1",
      "input": text,
      "voice": "alloy"
    };

    return this.http.post(environment.API_AUDIO_URL, body, { headers, responseType: 'blob' });
  }
}