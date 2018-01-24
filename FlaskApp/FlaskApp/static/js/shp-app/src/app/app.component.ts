import { Component } from '@angular/core';
import { MatTableDataSource} from '@angular/material';
import { Element } from '@angular/compiler';
import { HttpClient } from '@angular/common/http';
/**
 * @title List with sections
 */
@Component({
  selector: 'app-list-sections-example',
  styleUrls: ['app.component.css'],
  templateUrl: 'app.component.html',
})
export class TableFilteringExampleComponent {
  constructor(private http: HttpClient) { }
  TaskList: Element[];
  displayedColumns = ['title', 'name', 'weight', 'symbol'];
  dataSource = new MatTableDataSource(this.TaskList);
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  getFullList(): void {
    this.http.get('http://localhost:5000/_todo').subscribe(data => {
      console.log(data);
      // this.TaskList = data.data.ret;
    });
  }
}

export interface Element {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}
