import { apiRequest, buildPageQuery } from './client'
import type {
  PageParams,
  SpringPage,
  StockCreateRequest,
  StockResponse,
} from '../types/api'

const basePath = (warehouseId: number) => `/api/warehouse/${warehouseId}/stock`

export const stocksApi = {
  list: (warehouseId: number, params?: PageParams) =>
    apiRequest<SpringPage<StockResponse>>(
      `${basePath(warehouseId)}${buildPageQuery(params)}`,
    ),
  add: (warehouseId: number, payload: StockCreateRequest) =>
    apiRequest<StockResponse>(basePath(warehouseId), {
      method: 'POST',
      body: payload,
    }),
}
