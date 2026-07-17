import { apiRequest, buildPageQuery } from './client'
import type {
  PageParams,
  ProductCreateRequest,
  ProductResponse,
  SpringPage,
} from '../types/api'

const basePath = '/api/products'

export const productsApi = {
  list: (params?: PageParams) =>
    apiRequest<SpringPage<ProductResponse>>(`${basePath}${buildPageQuery(params)}`),
  get: (id: number) => apiRequest<ProductResponse>(`${basePath}/${id}`),
  create: (payload: ProductCreateRequest) =>
    apiRequest<ProductResponse>(basePath, { method: 'POST', body: payload }),
  update: (id: number, payload: ProductCreateRequest) =>
    apiRequest<ProductResponse>(`${basePath}/${id}`, { method: 'PUT', body: payload }),
  delete: (id: number) => apiRequest<void>(`${basePath}/${id}`, { method: 'DELETE' }),
}
