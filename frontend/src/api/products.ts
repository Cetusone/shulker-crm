import { apiRequest } from './client'
import type { ProductCreateRequest, ProductResponse } from '../types/api'

const basePath = '/api/products'

export const productsApi = {
  list: () => apiRequest<ProductResponse[]>(basePath),
  get: (id: number) => apiRequest<ProductResponse>(`${basePath}/${id}`),
  create: (payload: ProductCreateRequest) =>
    apiRequest<ProductResponse>(basePath, { method: 'POST', body: payload }),
  update: (id: number, payload: ProductCreateRequest) =>
    apiRequest<ProductResponse>(`${basePath}/${id}`, { method: 'PUT', body: payload }),
  delete: (id: number) => apiRequest<void>(`${basePath}/${id}`, { method: 'DELETE' }),
}
