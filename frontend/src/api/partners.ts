import { apiRequest } from './client'
import type { PartnerCreateRequest, PartnerResponse } from '../types/api'

const basePath = '/api/partners'

export const partnersApi = {
  list: () => apiRequest<PartnerResponse[]>(basePath),
  get: (id: number) => apiRequest<PartnerResponse>(`${basePath}/${id}`),
  create: (payload: PartnerCreateRequest) =>
    apiRequest<PartnerResponse>(basePath, { method: 'POST', body: payload }),
  update: (id: number, payload: PartnerCreateRequest) =>
    apiRequest<PartnerResponse>(`${basePath}/${id}`, { method: 'PUT', body: payload }),
  delete: (id: number) => apiRequest<void>(`${basePath}/${id}`, { method: 'DELETE' }),
}
