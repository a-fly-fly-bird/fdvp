import { Assets } from "./assets";

export interface AssetsQueryReponse {
    content: Assets[],
    pageable: {},
    last: boolean,
    totalPages: number,
    totalElements: number,
    first: boolean,
    sort: {},
    number: number,
    size: number,
    numberOfElements: number,
    empty: boolean
}
