import {
  AppstoreOutlined,
  CarOutlined,
  DatabaseOutlined,
  NodeIndexOutlined,
  PlusOutlined,
  ReloadOutlined,
  ShopOutlined,
} from '@ant-design/icons'
import {
  Alert,
  Button,
  ConfigProvider,
  Drawer,
  Empty,
  Form,
  Input,
  InputNumber,
  Layout,
  Menu,
  Row,
  Col,
  Select,
  Segmented,
  Space,
  Statistic,
  Table,
  Tag,
  Typography,
  message,
} from 'antd'
import type { TableProps } from 'antd'
import { useMemo, useState } from 'react'
import {
  BrowserRouter,
  Link,
  Navigate,
  Route,
  Routes,
  useLocation,
} from 'react-router-dom'
import './App.css'

const { Content, Header, Sider } = Layout
const { Text, Title } = Typography

type WarehouseType = 'OWN' | 'PARTNER'
type TransportType = 'AUTO' | 'RAILWAY' | 'AVIATION'
type DeliveryPriority = 'FASTEST' | 'CHEAPEST'

type Product = {
  id: number
  name: string
  description: string
  sku: string
  cargoUnitValue: number
  cargoUnitType: 'UNIT'
  characteristics: { attributeName: string; attributeValue: string }[]
}

type Warehouse = {
  id: number
  name: string
  address: string
  warehouseType: WarehouseType
  latitude: number
  longitude: number
  isActive: boolean
  transportIds: number[]
}

type Transport = {
  id: number
  name: string
  transportType: TransportType
  capacityCargoUnits: number
  speedKmH: number
  costPerKm: number
}

type Stock = {
  id: number
  warehouseId: number
  productId: number
  quantity: number
  reservedQuantity: number
}

type DeliveryOption = {
  sourceWarehouseId: number
  sourceWarehouseName: string
  partnerWarehouseId: number
  partnerWarehouseName: string
  transportId: number
  transportName: string
  transportType: TransportType
  distanceKm: number
  requiredCargoUnits: number
  transportCapacityCargoUnits: number
  tripsCount: number
  estimatedTimeHours: number
  estimatedCost: number
  availableQuantity: number
}

const initialProducts: Product[] = [
  {
    id: 1,
    name: 'Ноутбук Apple MacBook',
    description: 'Ноутбук для корпоративных клиентов',
    sku: 'APL-MAC-001',
    cargoUnitValue: 1.5,
    cargoUnitType: 'UNIT',
    characteristics: [
      { attributeName: 'brand', attributeValue: 'Apple' },
      { attributeName: 'screen', attributeValue: '14' },
    ],
  },
  {
    id: 2,
    name: 'Монитор Dell UltraSharp',
    description: 'Офисный монитор 27 дюймов',
    sku: 'DEL-MON-027',
    cargoUnitValue: 2,
    cargoUnitType: 'UNIT',
    characteristics: [{ attributeName: 'diagonal', attributeValue: '27' }],
  },
  {
    id: 3,
    name: 'Короб кабелей HDMI',
    description: 'Партия кабелей для рабочих мест',
    sku: 'HDMI-BOX-100',
    cargoUnitValue: 0.25,
    cargoUnitType: 'UNIT',
    characteristics: [{ attributeName: 'pack', attributeValue: '100 pcs' }],
  },
]

const initialTransports: Transport[] = [
  {
    id: 1,
    name: 'Авто 10 тонн',
    transportType: 'AUTO',
    capacityCargoUnits: 100,
    speedKmH: 80,
    costPerKm: 50,
  },
  {
    id: 2,
    name: 'Ж/Д контейнер',
    transportType: 'RAILWAY',
    capacityCargoUnits: 800,
    speedKmH: 60,
    costPerKm: 24,
  },
  {
    id: 3,
    name: 'Авиа срочная',
    transportType: 'AVIATION',
    capacityCargoUnits: 180,
    speedKmH: 680,
    costPerKm: 310,
  },
]

const initialWarehouses: Warehouse[] = [
  {
    id: 1,
    name: 'Склад Москва',
    address: 'Москва, ул. Складская, 1',
    warehouseType: 'OWN',
    latitude: 55.7558,
    longitude: 37.6173,
    isActive: true,
    transportIds: [1, 2, 3],
  },
  {
    id: 2,
    name: 'Склад Санкт-Петербург',
    address: 'Санкт-Петербург, Московский пр., 5',
    warehouseType: 'OWN',
    latitude: 59.9311,
    longitude: 30.3609,
    isActive: true,
    transportIds: [1, 2],
  },
  {
    id: 3,
    name: 'Партнер Казань',
    address: 'Казань, ул. Логистическая, 8',
    warehouseType: 'PARTNER',
    latitude: 55.7961,
    longitude: 49.1064,
    isActive: true,
    transportIds: [1],
  },
]

const initialStocks: Stock[] = [
  { id: 1, warehouseId: 1, productId: 1, quantity: 54, reservedQuantity: 4 },
  { id: 2, warehouseId: 1, productId: 2, quantity: 35, reservedQuantity: 0 },
  { id: 3, warehouseId: 2, productId: 1, quantity: 18, reservedQuantity: 2 },
  { id: 4, warehouseId: 2, productId: 3, quantity: 430, reservedQuantity: 10 },
]

const transportLabels: Record<TransportType, string> = {
  AUTO: 'Авто',
  RAILWAY: 'Ж/Д',
  AVIATION: 'Авиа',
}

const warehouseLabels: Record<WarehouseType, string> = {
  OWN: 'Наш',
  PARTNER: 'Партнер',
}

const priorityLabels: Record<DeliveryPriority, string> = {
  CHEAPEST: 'Дешевле',
  FASTEST: 'Быстрее',
}

function distanceKm(from: Warehouse, to: Warehouse) {
  const earthRadius = 6371
  const toRad = (value: number) => (value * Math.PI) / 180
  const dLat = toRad(to.latitude - from.latitude)
  const dLon = toRad(to.longitude - from.longitude)
  const a =
    Math.sin(dLat / 2) ** 2 +
    Math.cos(toRad(from.latitude)) *
      Math.cos(toRad(to.latitude)) *
      Math.sin(dLon / 2) ** 2
  return Math.round(earthRadius * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)))
}

function currency(value: number) {
  return new Intl.NumberFormat('ru-RU', {
    maximumFractionDigits: 0,
    style: 'currency',
    currency: 'RUB',
  }).format(value)
}

function numberValue(value: number) {
  return new Intl.NumberFormat('ru-RU', {
    maximumFractionDigits: 2,
  }).format(value)
}

function AppShell() {
  const location = useLocation()
  const [messageApi, contextHolder] = message.useMessage()
  const [products, setProducts] = useState<Product[]>(initialProducts)
  const [warehouses, setWarehouses] = useState<Warehouse[]>(initialWarehouses)
  const [transports, setTransports] = useState<Transport[]>(initialTransports)
  const [stocks, setStocks] = useState<Stock[]>(initialStocks)

  const nextId = (items: { id: number }[]) =>
    items.length ? Math.max(...items.map((item) => item.id)) + 1 : 1

  const calculateDelivery = (
    productId: number,
    quantity: number,
    partnerWarehouseId: number,
    priority: DeliveryPriority,
  ): DeliveryOption[] => {
    const product = products.find((item) => item.id === productId)
    const partnerWarehouse = warehouses.find(
      (item) => item.id === partnerWarehouseId && item.warehouseType === 'PARTNER',
    )

    if (!product || !partnerWarehouse || quantity <= 0) {
      return []
    }

    const options = warehouses
      .filter((warehouse) => warehouse.warehouseType === 'OWN' && warehouse.isActive)
      .flatMap((warehouse) => {
        const stock = stocks.find(
          (item) => item.warehouseId === warehouse.id && item.productId === productId,
        )
        const availableQuantity = stock
          ? stock.quantity - stock.reservedQuantity
          : 0

        if (availableQuantity < quantity) {
          return []
        }

        const requiredCargoUnits = quantity * product.cargoUnitValue
        const distance = distanceKm(warehouse, partnerWarehouse)

        return warehouse.transportIds
          .map((transportId) =>
            transports.find((transport) => transport.id === transportId),
          )
          .filter((transport): transport is Transport => Boolean(transport))
          .map((transport) => {
            const tripsCount = Math.ceil(
              requiredCargoUnits / transport.capacityCargoUnits,
            )
            return {
              sourceWarehouseId: warehouse.id,
              sourceWarehouseName: warehouse.name,
              partnerWarehouseId: partnerWarehouse.id,
              partnerWarehouseName: partnerWarehouse.name,
              transportId: transport.id,
              transportName: transport.name,
              transportType: transport.transportType,
              distanceKm: distance,
              requiredCargoUnits,
              transportCapacityCargoUnits: transport.capacityCargoUnits,
              tripsCount,
              estimatedTimeHours: Number(
                ((distance / transport.speedKmH) * tripsCount).toFixed(2),
              ),
              estimatedCost: Math.round(
                distance * transport.costPerKm * tripsCount,
              ),
              availableQuantity,
            }
          })
      })

    return options.sort((a, b) =>
      priority === 'CHEAPEST'
        ? a.estimatedCost - b.estimatedCost
        : a.estimatedTimeHours - b.estimatedTimeHours,
    )
  }

  const summary = useMemo(
    () => ({
      products: products.length,
      ownWarehouses: warehouses.filter((item) => item.warehouseType === 'OWN')
        .length,
      partnerWarehouses: warehouses.filter(
        (item) => item.warehouseType === 'PARTNER',
      ).length,
      transports: transports.length,
      stockRows: stocks.length,
    }),
    [products, warehouses, transports, stocks],
  )

  const menuItems = [
    {
      key: '/delivery',
      icon: <NodeIndexOutlined />,
      label: <Link to="/delivery">Расчет доставки</Link>,
    },
    {
      key: '/products',
      icon: <AppstoreOutlined />,
      label: <Link to="/products">Товары</Link>,
    },
    {
      key: '/warehouses',
      icon: <ShopOutlined />,
      label: <Link to="/warehouses">Склады</Link>,
    },
    {
      key: '/transports',
      icon: <CarOutlined />,
      label: <Link to="/transports">Транспорт</Link>,
    },
    {
      key: '/stocks',
      icon: <DatabaseOutlined />,
      label: <Link to="/stocks">Остатки</Link>,
    },
  ]

  return (
    <Layout className="app-shell">
      {contextHolder}
      <Sider breakpoint="lg" collapsedWidth="0" width={264} className="sider">
        <div className="brand">
          <div className="brand-mark">S</div>
          <div>
            <Text strong>Shulker SRM</Text>
            <Text type="secondary">Warehouse CRM</Text>
          </div>
        </div>
        <Menu
          mode="inline"
          selectedKeys={[location.pathname]}
          items={menuItems}
          className="side-menu"
        />
      </Sider>
      <Layout>
        <Header className="topbar">
          <div>
            <Text type="secondary">API MVP</Text>
            <Title level={3}>Панель управления складами</Title>
          </div>
          <Space wrap>
            <Tag color="blue">/api/v1</Tag>
            <Tag color="green">Mock data</Tag>
          </Space>
        </Header>
        <Content className="content">
          <section className="summary-grid">
            <Statistic title="Товары" value={summary.products} />
            <Statistic title="Наши склады" value={summary.ownWarehouses} />
            <Statistic title="Партнеры" value={summary.partnerWarehouses} />
            <Statistic title="Транспорт" value={summary.transports} />
            <Statistic title="Остатки" value={summary.stockRows} />
          </section>
          <Routes>
            <Route path="/" element={<Navigate to="/delivery" replace />} />
            <Route
              path="/delivery"
              element={
                <DeliveryPage
                  products={products}
                  warehouses={warehouses}
                  calculateDelivery={calculateDelivery}
                />
              }
            />
            <Route
              path="/products"
              element={
                <ProductsPage
                  products={products}
                  addProduct={(values) => {
                    setProducts((items) => [
                      ...items,
                      {
                        id: nextId(items),
                        cargoUnitType: 'UNIT',
                        characteristics: [],
                        ...values,
                      },
                    ])
                    messageApi.success('Товар добавлен')
                  }}
                />
              }
            />
            <Route
              path="/warehouses"
              element={
                <WarehousesPage
                  warehouses={warehouses}
                  transports={transports}
                  addWarehouse={(values) => {
                    setWarehouses((items) => [
                      ...items,
                      { id: nextId(items), isActive: true, transportIds: [], ...values },
                    ])
                    messageApi.success('Склад добавлен')
                  }}
                />
              }
            />
            <Route
              path="/transports"
              element={
                <TransportsPage
                  transports={transports}
                  addTransport={(values) => {
                    setTransports((items) => [...items, { id: nextId(items), ...values }])
                    messageApi.success('Транспорт добавлен')
                  }}
                />
              }
            />
            <Route
              path="/stocks"
              element={
                <StocksPage
                  products={products}
                  warehouses={warehouses}
                  stocks={stocks}
                  replenishStock={(values) => {
                    setStocks((items) => {
                      const existing = items.find(
                        (item) =>
                          item.productId === values.productId &&
                          item.warehouseId === values.warehouseId,
                      )
                      if (!existing) {
                        return [
                          ...items,
                          {
                            id: nextId(items),
                            productId: values.productId,
                            warehouseId: values.warehouseId,
                            quantity: values.quantity,
                            reservedQuantity: 0,
                          },
                        ]
                      }

                      return items.map((item) =>
                        item.id === existing.id
                          ? { ...item, quantity: item.quantity + values.quantity }
                          : item,
                      )
                    })
                    messageApi.success('Остаток пополнен')
                  }}
                />
              }
            />
          </Routes>
        </Content>
      </Layout>
    </Layout>
  )
}

function PageHeader({
  title,
  endpoint,
  action,
}: {
  title: string
  endpoint: string
  action?: React.ReactNode
}) {
  return (
    <div className="page-header">
      <div>
        <Title level={4}>{title}</Title>
        <Text code>{endpoint}</Text>
      </div>
      {action}
    </div>
  )
}

function ProductsPage({
  products,
  addProduct,
}: {
  products: Product[]
  addProduct: (values: Omit<Product, 'id' | 'cargoUnitType' | 'characteristics'>) => void
}) {
  const [open, setOpen] = useState(false)
  const [form] = Form.useForm()
  const columns: TableProps<Product>['columns'] = [
    { title: 'Название', dataIndex: 'name' },
    { title: 'SKU', dataIndex: 'sku', width: 180 },
    {
      title: 'Грузоместо',
      dataIndex: 'cargoUnitValue',
      width: 140,
      render: (value) => `${numberValue(value)} UNIT`,
    },
    { title: 'Описание', dataIndex: 'description' },
  ]

  return (
    <>
      <PageHeader
        title="Товары"
        endpoint="GET /api/v1/products"
        action={
          <Button icon={<PlusOutlined />} type="primary" onClick={() => setOpen(true)}>
            Создать
          </Button>
        }
      />
      <Table rowKey="id" columns={columns} dataSource={products} pagination={{ pageSize: 6 }} />
      <Drawer
        title="POST /api/v1/products"
        open={open}
        onClose={() => setOpen(false)}
        width={440}
      >
        <Form
          form={form}
          layout="vertical"
          onFinish={(values) => {
            addProduct(values)
            form.resetFields()
            setOpen(false)
          }}
        >
          <Form.Item name="name" label="Название" rules={[{ required: true, min: 2 }]}>
            <Input />
          </Form.Item>
          <Form.Item name="sku" label="SKU" rules={[{ required: true }]}>
            <Input />
          </Form.Item>
          <Form.Item name="description" label="Описание" rules={[{ required: true }]}>
            <Input.TextArea rows={3} />
          </Form.Item>
          <Form.Item
            name="cargoUnitValue"
            label="Грузоместо, UNIT"
            rules={[{ required: true }]}
          >
            <InputNumber min={0.01} step={0.25} className="full-width" />
          </Form.Item>
          <Button htmlType="submit" type="primary" block>
            Сохранить
          </Button>
        </Form>
      </Drawer>
    </>
  )
}

function WarehousesPage({
  warehouses,
  transports,
  addWarehouse,
}: {
  warehouses: Warehouse[]
  transports: Transport[]
  addWarehouse: (
    values: Omit<Warehouse, 'id' | 'isActive' | 'transportIds'>,
  ) => void
}) {
  const [open, setOpen] = useState(false)
  const [form] = Form.useForm()
  const columns: TableProps<Warehouse>['columns'] = [
    { title: 'Название', dataIndex: 'name' },
    {
      title: 'Тип',
      dataIndex: 'warehouseType',
      width: 120,
      render: (value: WarehouseType) => (
        <Tag color={value === 'OWN' ? 'blue' : 'purple'}>{warehouseLabels[value]}</Tag>
      ),
    },
    { title: 'Адрес', dataIndex: 'address' },
    {
      title: 'Координаты',
      width: 170,
      render: (_, record) => `${record.latitude}, ${record.longitude}`,
    },
    {
      title: 'Транспорт',
      width: 220,
      render: (_, record) => (
        <Space wrap>
          {record.transportIds.map((id) => {
            const transport = transports.find((item) => item.id === id)
            return transport ? <Tag key={id}>{transportLabels[transport.transportType]}</Tag> : null
          })}
        </Space>
      ),
    },
  ]

  return (
    <>
      <PageHeader
        title="Склады"
        endpoint="GET /api/v1/warehouses?type=OWN&active=true"
        action={
          <Button icon={<PlusOutlined />} type="primary" onClick={() => setOpen(true)}>
            Создать
          </Button>
        }
      />
      <Table rowKey="id" columns={columns} dataSource={warehouses} pagination={{ pageSize: 6 }} />
      <Drawer
        title="POST /api/v1/warehouses"
        open={open}
        onClose={() => setOpen(false)}
        width={440}
      >
        <Form
          form={form}
          layout="vertical"
          initialValues={{ warehouseType: 'OWN' }}
          onFinish={(values) => {
            addWarehouse(values)
            form.resetFields()
            setOpen(false)
          }}
        >
          <Form.Item name="name" label="Название" rules={[{ required: true, min: 2 }]}>
            <Input />
          </Form.Item>
          <Form.Item name="address" label="Адрес" rules={[{ required: true }]}>
            <Input />
          </Form.Item>
          <Form.Item name="warehouseType" label="Тип" rules={[{ required: true }]}>
            <Select
              options={[
                { value: 'OWN', label: 'Наш склад' },
                { value: 'PARTNER', label: 'Склад партнера' },
              ]}
            />
          </Form.Item>
          <Row gutter={12}>
            <Col span={12}>
              <Form.Item name="latitude" label="Широта" rules={[{ required: true }]}>
                <InputNumber min={-90} max={90} className="full-width" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item name="longitude" label="Долгота" rules={[{ required: true }]}>
                <InputNumber min={-180} max={180} className="full-width" />
              </Form.Item>
            </Col>
          </Row>
          <Button htmlType="submit" type="primary" block>
            Сохранить
          </Button>
        </Form>
      </Drawer>
    </>
  )
}

function TransportsPage({
  transports,
  addTransport,
}: {
  transports: Transport[]
  addTransport: (values: Omit<Transport, 'id'>) => void
}) {
  const [open, setOpen] = useState(false)
  const [form] = Form.useForm()
  const columns: TableProps<Transport>['columns'] = [
    { title: 'Название', dataIndex: 'name' },
    {
      title: 'Тип',
      dataIndex: 'transportType',
      width: 120,
      render: (value: TransportType) => <Tag>{transportLabels[value]}</Tag>,
    },
    { title: 'Вместимость', dataIndex: 'capacityCargoUnits', render: (value) => `${value} UNIT` },
    { title: 'Скорость', dataIndex: 'speedKmH', render: (value) => `${value} км/ч` },
    { title: 'Стоимость', dataIndex: 'costPerKm', render: (value) => `${currency(value)} / км` },
  ]

  return (
    <>
      <PageHeader
        title="Транспорт"
        endpoint="GET /api/v1/transports?type=AUTO"
        action={
          <Button icon={<PlusOutlined />} type="primary" onClick={() => setOpen(true)}>
            Создать
          </Button>
        }
      />
      <Table rowKey="id" columns={columns} dataSource={transports} pagination={false} />
      <Drawer
        title="POST /api/v1/transports"
        open={open}
        onClose={() => setOpen(false)}
        width={440}
      >
        <Form
          form={form}
          layout="vertical"
          initialValues={{ transportType: 'AUTO' }}
          onFinish={(values) => {
            addTransport(values)
            form.resetFields()
            setOpen(false)
          }}
        >
          <Form.Item name="name" label="Название" rules={[{ required: true }]}>
            <Input />
          </Form.Item>
          <Form.Item name="transportType" label="Тип" rules={[{ required: true }]}>
            <Select
              options={[
                { value: 'AUTO', label: 'Авто' },
                { value: 'RAILWAY', label: 'Ж/Д' },
                { value: 'AVIATION', label: 'Авиа' },
              ]}
            />
          </Form.Item>
          <Form.Item
            name="capacityCargoUnits"
            label="Вместимость, UNIT"
            rules={[{ required: true }]}
          >
            <InputNumber min={1} className="full-width" />
          </Form.Item>
          <Form.Item name="speedKmH" label="Скорость, км/ч" rules={[{ required: true }]}>
            <InputNumber min={1} className="full-width" />
          </Form.Item>
          <Form.Item name="costPerKm" label="Стоимость за км" rules={[{ required: true }]}>
            <InputNumber min={1} className="full-width" />
          </Form.Item>
          <Button htmlType="submit" type="primary" block>
            Сохранить
          </Button>
        </Form>
      </Drawer>
    </>
  )
}

function StocksPage({
  products,
  warehouses,
  stocks,
  replenishStock,
}: {
  products: Product[]
  warehouses: Warehouse[]
  stocks: Stock[]
  replenishStock: (values: { warehouseId: number; productId: number; quantity: number }) => void
}) {
  const [open, setOpen] = useState(false)
  const [form] = Form.useForm()
  const rows = stocks.map((stock) => {
    const product = products.find((item) => item.id === stock.productId)
    const warehouse = warehouses.find((item) => item.id === stock.warehouseId)
    return {
      ...stock,
      productName: product?.name ?? 'Неизвестный товар',
      sku: product?.sku ?? '-',
      warehouseName: warehouse?.name ?? 'Неизвестный склад',
      availableQuantity: stock.quantity - stock.reservedQuantity,
    }
  })

  const columns: TableProps<(typeof rows)[number]>['columns'] = [
    { title: 'Склад', dataIndex: 'warehouseName' },
    { title: 'Товар', dataIndex: 'productName' },
    { title: 'SKU', dataIndex: 'sku', width: 160 },
    { title: 'Количество', dataIndex: 'quantity', width: 130 },
    { title: 'Резерв', dataIndex: 'reservedQuantity', width: 110 },
    {
      title: 'Доступно',
      dataIndex: 'availableQuantity',
      width: 120,
      render: (value) => <Text strong>{value}</Text>,
    },
  ]

  return (
    <>
      <PageHeader
        title="Остатки"
        endpoint="GET /api/v1/warehouses/{id}/stock"
        action={
          <Button icon={<PlusOutlined />} type="primary" onClick={() => setOpen(true)}>
            Пополнить
          </Button>
        }
      />
      <Table rowKey="id" columns={columns} dataSource={rows} pagination={{ pageSize: 6 }} />
      <Drawer
        title="POST /api/v1/warehouses/{id}/stock"
        open={open}
        onClose={() => setOpen(false)}
        width={440}
      >
        <Form
          form={form}
          layout="vertical"
          onFinish={(values) => {
            replenishStock(values)
            form.resetFields()
            setOpen(false)
          }}
        >
          <Form.Item name="warehouseId" label="Склад" rules={[{ required: true }]}>
            <Select
              options={warehouses
                .filter((item) => item.warehouseType === 'OWN' && item.isActive)
                .map((item) => ({ value: item.id, label: item.name }))}
            />
          </Form.Item>
          <Form.Item name="productId" label="Товар" rules={[{ required: true }]}>
            <Select options={products.map((item) => ({ value: item.id, label: item.name }))} />
          </Form.Item>
          <Form.Item name="quantity" label="Количество" rules={[{ required: true }]}>
            <InputNumber min={1} className="full-width" />
          </Form.Item>
          <Button htmlType="submit" type="primary" block>
            Пополнить
          </Button>
        </Form>
      </Drawer>
    </>
  )
}

function DeliveryPage({
  products,
  warehouses,
  calculateDelivery,
}: {
  products: Product[]
  warehouses: Warehouse[]
  calculateDelivery: (
    productId: number,
    quantity: number,
    partnerWarehouseId: number,
    priority: DeliveryPriority,
  ) => DeliveryOption[]
}) {
  const [form] = Form.useForm()
  const [priority, setPriority] = useState<DeliveryPriority>('CHEAPEST')
  const [options, setOptions] = useState<DeliveryOption[]>([])
  const partnerWarehouses = warehouses.filter(
    (item) => item.warehouseType === 'PARTNER' && item.isActive,
  )

  const columns: TableProps<DeliveryOption>['columns'] = [
    {
      title: '#',
      width: 64,
      render: (_, __, index) => <Text strong>{index + 1}</Text>,
    },
    { title: 'Склад-источник', dataIndex: 'sourceWarehouseName' },
    {
      title: 'Транспорт',
      render: (_, record) => (
        <Space>
          <Tag>{transportLabels[record.transportType]}</Tag>
          <Text>{record.transportName}</Text>
        </Space>
      ),
    },
    { title: 'Расстояние', dataIndex: 'distanceKm', render: (value) => `${value} км` },
    {
      title: 'Груз',
      dataIndex: 'requiredCargoUnits',
      render: (value) => `${numberValue(value)} UNIT`,
    },
    { title: 'Рейсы', dataIndex: 'tripsCount' },
    {
      title: 'Время',
      dataIndex: 'estimatedTimeHours',
      render: (value) => `${numberValue(value)} ч`,
    },
    {
      title: 'Стоимость',
      dataIndex: 'estimatedCost',
      render: (value) => <Text strong>{currency(value)}</Text>,
    },
  ]

  return (
    <>
      <PageHeader title="Расчет доставки" endpoint="POST /api/v1/delivery/calculate" />
      <section className="workspace">
        <div className="panel form-panel">
          <Title level={5}>Параметры запроса</Title>
          <Form
            form={form}
            layout="vertical"
            initialValues={{
              productId: products[0]?.id,
              quantity: 20,
              partnerWarehouseId: partnerWarehouses[0]?.id,
            }}
            onFinish={(values) => {
              setOptions(
                calculateDelivery(
                  values.productId,
                  values.quantity,
                  values.partnerWarehouseId,
                  priority,
                ),
              )
            }}
          >
            <Form.Item name="productId" label="Товар" rules={[{ required: true }]}>
              <Select options={products.map((item) => ({ value: item.id, label: item.name }))} />
            </Form.Item>
            <Form.Item name="quantity" label="Количество" rules={[{ required: true }]}>
              <InputNumber min={1} className="full-width" />
            </Form.Item>
            <Form.Item
              name="partnerWarehouseId"
              label="Склад партнера"
              rules={[{ required: true }]}
            >
              <Select
                options={partnerWarehouses.map((item) => ({
                  value: item.id,
                  label: item.name,
                }))}
              />
            </Form.Item>
            <div className="field-block">
              <Text className="field-label">Приоритет</Text>
              <Segmented
                block
                value={priority}
                onChange={(value) => setPriority(value as DeliveryPriority)}
                options={[
                  { label: priorityLabels.CHEAPEST, value: 'CHEAPEST' },
                  { label: priorityLabels.FASTEST, value: 'FASTEST' },
                ]}
              />
            </div>
            <Button icon={<ReloadOutlined />} type="primary" htmlType="submit" block>
              Рассчитать
            </Button>
          </Form>
        </div>
        <div className="panel result-panel">
          <Space direction="vertical" size={16} className="full-width">
            <Alert
              type="info"
              showIcon
              message="Результат ранжируется так же, как должен ранжироваться ответ /delivery/calculate."
            />
            {options.length ? (
              <Table
                rowKey={(record) => `${record.sourceWarehouseId}-${record.transportId}`}
                columns={columns}
                dataSource={options}
                pagination={false}
                scroll={{ x: 920 }}
              />
            ) : (
              <Empty description="Заполните параметры и запустите расчет" />
            )}
          </Space>
        </div>
      </section>
    </>
  )
}

function App() {
  return (
    <ConfigProvider
      theme={{
        token: {
          borderRadius: 6,
          colorPrimary: '#2563eb',
          colorSuccess: '#16845b',
          colorWarning: '#b7791f',
          fontFamily:
            'Inter, ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif',
        },
        components: {
          Layout: {
            bodyBg: '#f5f7fb',
            headerBg: '#ffffff',
            siderBg: '#ffffff',
          },
        },
      }}
    >
      <BrowserRouter>
        <AppShell />
      </BrowserRouter>
    </ConfigProvider>
  )
}

export default App
