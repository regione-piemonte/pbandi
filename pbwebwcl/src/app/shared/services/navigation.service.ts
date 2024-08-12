import { Injectable } from "@angular/core";
import { SortDirection } from "@angular/material/sort";

@Injectable()
export class NavigationService {
    private sortTable: Sort = new Sort(null, null);
    private paginatorTable: Paginator = new Paginator(null, null);

    public set sortDirectionTable(direction: SortDirection) {
        this.sortTable.direction = direction;
    }
    public set sortActiveTable(active: string) {
        this.sortTable.active = active;
    }
    public get sortDirectionTable(): SortDirection {
        if (this.sortTable.direction === null || this.sortTable.direction === undefined) return null;
        return this.sortTable.direction;
    }
    public get sortActiveTable(): string {
        return this.sortTable.active;
    }
    public set paginatorPageIndexTable(value: number) {
        this.paginatorTable.pageIndex = value;
    }
    public get paginatorPageIndexTable(): number {
        return this.paginatorTable.pageIndex;
    }
    public set paginatorPageSizeTable(value: number) {
        this.paginatorTable.pageSize = value;
    }
    public get paginatorPageSizeTable(): number {
        return this.paginatorTable.pageSize;
    }
}

//utilità per navigazione documenti
////////////////////////////////////////////////////////////////////////////////////////
export class Sort {
    constructor(public direction: SortDirection, public active: string) { }
}
export class Paginator {
    constructor(public pageIndex: number, public pageSize: number) { }
}