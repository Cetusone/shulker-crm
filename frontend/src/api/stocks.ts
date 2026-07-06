import { apiRequest } from './client'
import type { StockCreateRequest, StockResponse } from '../types/api'

const basePath = (warehouseId: number) => `/api/warehouse/${warehouseId}/stock`

export const stocksApi = {
  list: (warehouseId: number) => apiRequest<StockResponse[]>(basePath(warehouseId)),
  add: (warehouseId: number, payload: StockCreateRequest) =>
    apiRequest<StockResponse>(basePath(warehouseId), {
      method: 'POST',
      body: payload,
    }),
}
