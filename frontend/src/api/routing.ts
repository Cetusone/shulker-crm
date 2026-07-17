import { apiRequest } from './client'
import type {
  DeliveryCalculationResponse,
  DeliveryOptionRequest,
} from '../types/api'

const basePath = '/api/routing'

export const routingApi = {
  calculate: (payload: DeliveryOptionRequest) =>
    apiRequest<DeliveryCalculationResponse>(`${basePath}/calculate`, {
      method: 'POST',
      body: payload,
    }),
}
