export type TransportType = 'AUTO' | 'RAILWAY' | 'AVIATION'

export type DeliveryPriority = 'FASTEST' | 'CHEAPEST'

export type ProductCharacteristic = {
  id?: number
  attributeName: string
  attributeValue: string
}

export type ProductCreateRequest = {
  name: string
  description?: string
  sku: string
  weightKg: number
  volumeM3: number
  characteristics: ProductCharacteristic[]
}

export type ProductResponse = ProductCreateRequest & {
  id: number
  createdAt?: string
  updatedAt?: string
}

export type TransportCreateRequest = {
  transportType: TransportType
  name: string
  maxWeightKg: number
  maxVolumeM3: number
  speedKmH: number
  costPerKm: number
}

export type TransportResponse = TransportCreateRequest & {
  id: number
  createdAt?: string
  updatedAt?: string
}

export type TransportShortDto = {
  id: number
  transportType: TransportType
  name: string
}

export type OwnWarehouseCreateRequest = {
  name: string
  address?: string
  latitude: number
  longitude: number
  transportIds: number[]
}

export type OwnWarehouseResponse = {
  id: number
  name: string
  address?: string
  latitude: number
  longitude: number
  isActive: boolean
  createdAt?: string
  updatedAt?: string
  transports: TransportShortDto[]
}

export type PartnerCreateRequest = {
  name: string
  apiKey: string
  contactEmail?: string
  isActive: boolean
}

export type PartnerResponse = Omit<PartnerCreateRequest, 'apiKey'> & {
  id: number
  createdAt?: string
  updatedAt?: string
}

export type PartnerWarehouseCreateRequest = {
  partnerId: number
  name: string
  address?: string
  latitude: number
  longitude: number
  acceptsLand: boolean
  acceptsSea: boolean
  acceptsAir: boolean
}

export type PartnerWarehouseResponse = PartnerWarehouseCreateRequest & {
  id: number
  isActive: boolean
  createdAt?: string
  updatedAt?: string
}

export type StockCreateRequest = {
  productId: number
  quantity: number
  reason?: string
}

export type StockResponse = {
  id: number
  ownWarehouseId: number
  productId: number
  quantity: number
  reservedQuantity: number
  updatedAt?: string
}

export type ApiValidationError = {
  timestamp?: string
  status: number
  error: string
  message: string
  errors?: Record<string, string>
  path?: string
}
