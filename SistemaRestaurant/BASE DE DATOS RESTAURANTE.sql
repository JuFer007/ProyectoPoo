USE [master]
GO
/****** Object:  Database [RESTAURANTE]    Script Date: 9/12/2024 12:00:37 ******/
CREATE DATABASE [RESTAURANTE]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'RESTAURANTE', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER_DEV2022\MSSQL\DATA\RESTAURANTE.mdf' , SIZE = 73728KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'RESTAURANTE_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER_DEV2022\MSSQL\DATA\RESTAURANTE_log.ldf' , SIZE = 73728KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [RESTAURANTE] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [RESTAURANTE].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [RESTAURANTE] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [RESTAURANTE] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [RESTAURANTE] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [RESTAURANTE] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [RESTAURANTE] SET ARITHABORT OFF 
GO
ALTER DATABASE [RESTAURANTE] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [RESTAURANTE] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [RESTAURANTE] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [RESTAURANTE] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [RESTAURANTE] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [RESTAURANTE] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [RESTAURANTE] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [RESTAURANTE] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [RESTAURANTE] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [RESTAURANTE] SET  ENABLE_BROKER 
GO
ALTER DATABASE [RESTAURANTE] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [RESTAURANTE] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [RESTAURANTE] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [RESTAURANTE] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [RESTAURANTE] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [RESTAURANTE] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [RESTAURANTE] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [RESTAURANTE] SET RECOVERY FULL 
GO
ALTER DATABASE [RESTAURANTE] SET  MULTI_USER 
GO
ALTER DATABASE [RESTAURANTE] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [RESTAURANTE] SET DB_CHAINING OFF 
GO
ALTER DATABASE [RESTAURANTE] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [RESTAURANTE] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [RESTAURANTE] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [RESTAURANTE] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'RESTAURANTE', N'ON'
GO
ALTER DATABASE [RESTAURANTE] SET QUERY_STORE = ON
GO
ALTER DATABASE [RESTAURANTE] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [RESTAURANTE]
GO
/****** Object:  UserDefinedTableType [dbo].[DetallePedidoTipo]    Script Date: 9/12/2024 12:00:37 ******/
CREATE TYPE [dbo].[DetallePedidoTipo] AS TABLE(
	[numeroPlato] [int] NULL,
	[cantidad] [int] NULL
)
GO
/****** Object:  Table [dbo].[Cliente]    Script Date: 9/12/2024 12:00:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cliente](
	[idCliente] [int] IDENTITY(1,1) NOT NULL,
	[nombreCliente] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[idCliente] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Delivery]    Script Date: 9/12/2024 12:00:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Delivery](
	[idDelivery] [int] IDENTITY(1,1) NOT NULL,
	[direccionEntrega] [nvarchar](200) NULL,
	[estadoDelivery] [nvarchar](50) NULL,
	[idPedido] [int] NULL,
	[idRepartidor] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[idDelivery] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DetallePedido]    Script Date: 9/12/2024 12:00:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DetallePedido](
	[idDetallePedido] [int] IDENTITY(1,1) NOT NULL,
	[idPedido] [int] NOT NULL,
	[numeroPlato] [int] NOT NULL,
	[cantidad] [int] NOT NULL,
	[precioUnitario] [decimal](10, 2) NOT NULL,
	[nombrePlato] [varchar](255) NULL,
	[subtotal]  AS ([cantidad]*[precioUnitario]) PERSISTED,
PRIMARY KEY CLUSTERED 
(
	[idDetallePedido] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Empleado]    Script Date: 9/12/2024 12:00:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Empleado](
	[nombreEmpleado] [varchar](100) NULL,
	[salarioEmpleado] [decimal](10, 2) NULL,
	[dniEmpleado] [char](8) NULL,
	[cargoEmpleado] [varchar](50) NULL,
	[empleadoId] [int] NOT NULL,
 CONSTRAINT [PK__Empleado__5295297CA8AA4D6E] PRIMARY KEY CLUSTERED 
(
	[empleadoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Menu]    Script Date: 9/12/2024 12:00:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Menu](
	[idMenu] [int] IDENTITY(1,1) NOT NULL,
	[nombreMenu] [varchar](100) NOT NULL,
	[cantidadPlatos] [int] NOT NULL,
	[categoria] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idMenu] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Mesa]    Script Date: 9/12/2024 12:00:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Mesa](
	[idMesa] [int] IDENTITY(1,1) NOT NULL,
	[numeroMesa] [int] NULL,
	[capacidadMesa] [int] NULL,
	[estadoMesa] [varchar](50) NULL,
	[idMesero] [int] NULL,
	[estado_reserva] [varchar](20) NOT NULL,
	[idClienteReserva] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[idMesa] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Pago]    Script Date: 9/12/2024 12:00:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Pago](
	[idPago] [int] IDENTITY(1,1) NOT NULL,
	[montoPago] [decimal](10, 2) NULL,
	[metodoPago] [nvarchar](50) NULL,
	[fechaPago] [date] NULL,
	[estadoPago] [nvarchar](50) NULL,
	[idPedido] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[idPago] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Pedido]    Script Date: 9/12/2024 12:00:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Pedido](
	[idPedido] [int] IDENTITY(1,1) NOT NULL,
	[codigoPedido] [nvarchar](50) NULL,
	[fechaPedido] [date] NULL,
	[horaPedido] [time](0) NULL,
	[totalPedido] [decimal](10, 2) NULL,
	[idCliente] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[idPedido] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Plato]    Script Date: 9/12/2024 12:00:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Plato](
	[numeroPlato] [int] IDENTITY(1,1) NOT NULL,
	[nombrePlato] [nvarchar](100) NULL,
	[precioPlato] [decimal](10, 2) NULL,
	[idMenu] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[numeroPlato] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Cliente] ON 
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (1, N'Alejandro García')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (2, N'Beatriz Fernández')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (3, N'Carlos Hernández')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (4, N'Daniela López')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (5, N'Enrique Pérez')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (6, N'Fernanda Torres')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (7, N'Gabriel Morales')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (8, N'Helena Castillo')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (9, N'Ignacio Ortiz')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (10, N'Julieta Ramírez')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (11, N'Kevin Delgado')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (12, N'Laura Gutiérrez')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (13, N'Manuel Cruz')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (14, N'Natalia Mendoza')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (15, N'Oscar Serrano')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (16, N'Patricia Ríos')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (17, N'Raúl Vargas')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (18, N'Silvia Romero')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (19, N'Tomás Suárez')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (20, N'Úrsula Mejía')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (21, N'Victor Palacios')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (22, N'Wendy Flores')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (23, N'Xavier León')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (24, N'Yessenia Aguilar')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (25, N'Zoe Castillo')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (26, N'Francisco Rivera')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (27, N'Andrea Soto')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (28, N'Diego Paredes')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (29, N'Camila Navarro')
GO
INSERT [dbo].[Cliente] ([idCliente], [nombreCliente]) VALUES (30, N'Mario Luna')
GO
SET IDENTITY_INSERT [dbo].[Cliente] OFF
GO
SET IDENTITY_INSERT [dbo].[Delivery] ON 
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (1, N'Av. José Balta 123, Chiclayo', N'En camino', 1, 9123456)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (2, N'Calle Los Olivos 456, Chiclayo', N'Entregado', 2, 10450824)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (3, N'Av. Saenz Peña 789', N'Pendiente', 3, NULL)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (4, N'Calle Piura 321, Chiclayo', N'En camino', 4, 12377198)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (5, N'Av. José Leonardo Ortiz 654', N'Entregado', 5, 12940013)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (6, N'Calle San Martín 987, Chiclayo', N'En camino', 6, 14656202)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (7, N'Calle Los Jardines 432', N'Entregado', 7, 17723812)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (8, N'Calle Nueva 876, Chiclayo', N'En camino', 8, 21637576)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (9, N'Av. Libertad 543, Chiclayo', N'Pendiente', 9, NULL)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (10, N'Calle La Esperanza 210', N'En camino', 10, 24873461)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (11, N'Calle Los Pinos 789', N'Entregado', 11, 29706002)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (12, N'Calle La Unión 123, Chiclayo', N'Pendiente', 12, NULL)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (13, N'Calle Los Cocos 456', N'En camino', 13, 35554218)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (14, N'Calle Santa Rosa 999, Chiclayo', N'Entregado', 14, 37726222)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (15, N'Av. Los Incas 111', N'Pendiente', 15, NULL)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (16, N'Av. Los Reyes 222, Chiclayo', N'En camino', 16, 69568896)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (17, N'Calle San José 333, Chiclayo', N'Entregado', 17, 70092528)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (18, N'Calle Los Héroes 444, Chiclayo', N'Pendiente', 18, NULL)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (19, N'Av. José Abelardo Quiñones 555, Chiclayo', N'En camino', 19, 77343205)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (20, N'Calle 28 de Julio 666', N'Entregado', 20, 80088869)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (21, N'Calle El Trébol 777, Chiclayo', N'Pendiente', 21, NULL)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (22, N'Calle La Libertad 888, Chiclayo', N'En camino', 22, 93576820)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (23, N'Calle San Vicente 999, Chiclayo', N'Entregado', 23, 97157635)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (24, N'Calle La Primavera 123, Chiclayo', N'En camino', 24, 12345678)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (25, N'Calle Los Girasoles 456, Chiclayo', N'En camino', 25, 10450824)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (26, N'Calle Los Cactus 789', N'Entregado', 26, 12345678)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (27, N'Calle Los Andes 012, Chiclayo', N'Pendiente', 27, NULL)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (28, N'Calle Los Álamos 345', N'En camino', 28, 12940013)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (29, N'Calle Los Rosales 678, Chiclayo', N'Entregado', 29, 14656202)
GO
INSERT [dbo].[Delivery] ([idDelivery], [direccionEntrega], [estadoDelivery], [idPedido], [idRepartidor]) VALUES (30, N'Calle La Paz 901', N'Pendiente', 30, NULL)
GO
SET IDENTITY_INSERT [dbo].[Delivery] OFF
GO
SET IDENTITY_INSERT [dbo].[DetallePedido] ON 
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (2, 1, 9, 2, CAST(30.00 AS Decimal(10, 2)), N'Tacu Tacu')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (3, 1, 2, 1, CAST(40.00 AS Decimal(10, 2)), N'Arroz con Mariscos')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (5, 2, 5, 1, CAST(50.00 AS Decimal(10, 2)), N'Jalea Mixta')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (6, 3, 4, 4, CAST(38.00 AS Decimal(10, 2)), N'Parihuela')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (7, 3, 6, 3, CAST(32.00 AS Decimal(10, 2)), N'Sudado de Pescado')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (8, 3, 8, 2, CAST(30.00 AS Decimal(10, 2)), N'Lomo Saltado')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (9, 4, 7, 3, CAST(25.00 AS Decimal(10, 2)), N'Aji de Gallina')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (10, 4, 9, 2, CAST(30.00 AS Decimal(10, 2)), N'Tacu Tacu')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (11, 4, 10, 1, CAST(28.00 AS Decimal(10, 2)), N'Seco de Res')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (12, 5, 2, 4, CAST(40.00 AS Decimal(10, 2)), N'Arroz con Mariscos')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (13, 5, 6, 2, CAST(32.00 AS Decimal(10, 2)), N'Sudado de Pescado')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (14, 5, 11, 1, CAST(22.80 AS Decimal(10, 2)), N'Carapulcra')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (15, 6, 12, 1, CAST(29.00 AS Decimal(10, 2)), N'Cau Cau')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (16, 6, 16, 2, CAST(31.00 AS Decimal(10, 2)), N'Chicharron de Chancho')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (17, 6, 18, 1, CAST(36.00 AS Decimal(10, 2)), N'Chancho al Palo')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (18, 7, 7, 3, CAST(25.00 AS Decimal(10, 2)), N'Aji de Gallina')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (19, 7, 4, 2, CAST(38.00 AS Decimal(10, 2)), N'Parihuela')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (20, 7, 6, 1, CAST(32.00 AS Decimal(10, 2)), N'Sudado de Pescado')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (21, 8, 5, 4, CAST(50.00 AS Decimal(10, 2)), N'Jalea Mixta')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (22, 8, 9, 3, CAST(30.00 AS Decimal(10, 2)), N'Tacu Tacu')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (23, 8, 14, 1, CAST(40.00 AS Decimal(10, 2)), N'Chancho al Cilindro')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (24, 9, 10, 3, CAST(28.00 AS Decimal(10, 2)), N'Seco de Res')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (25, 9, 3, 2, CAST(50.00 AS Decimal(10, 2)), N'Chupe de Camarones')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (26, 9, 2, 1, CAST(40.00 AS Decimal(10, 2)), N'Arroz con Mariscos')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (27, 10, 5, 5, CAST(50.00 AS Decimal(10, 2)), N'Jalea Mixta')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (28, 10, 9, 3, CAST(30.00 AS Decimal(10, 2)), N'Tacu Tacu')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (29, 10, 1, 1, CAST(35.00 AS Decimal(10, 2)), N'Ceviche')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (30, 11, 12, 3, CAST(29.00 AS Decimal(10, 2)), N'Cau Cau')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (31, 11, 7, 2, CAST(25.00 AS Decimal(10, 2)), N'Aji de Gallina')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (32, 11, 10, 2, CAST(28.00 AS Decimal(10, 2)), N'Seco de Res')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (33, 12, 8, 3, CAST(30.00 AS Decimal(10, 2)), N'Lomo Saltado')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (34, 12, 3, 1, CAST(50.00 AS Decimal(10, 2)), N'Chupe de Camarones')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (35, 13, 1, 4, CAST(35.00 AS Decimal(10, 2)), N'Ceviche')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (36, 13, 14, 2, CAST(40.00 AS Decimal(10, 2)), N'Chancho al Cilindro')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (37, 13, 8, 1, CAST(30.00 AS Decimal(10, 2)), N'Lomo Saltado')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (38, 14, 9, 3, CAST(30.00 AS Decimal(10, 2)), N'Tacu Tacu')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (39, 14, 3, 3, CAST(50.00 AS Decimal(10, 2)), N'Chupe de Camarones')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (40, 14, 12, 2, CAST(29.00 AS Decimal(10, 2)), N'Cau Cau')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (41, 15, 2, 3, CAST(40.00 AS Decimal(10, 2)), N'Arroz con Mariscos')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (42, 15, 6, 2, CAST(32.00 AS Decimal(10, 2)), N'Sudado de Pescado')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (43, 15, 10, 1, CAST(28.00 AS Decimal(10, 2)), N'Seco de Res')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (44, 16, 4, 3, CAST(38.00 AS Decimal(10, 2)), N'Parihuela')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (45, 16, 7, 3, CAST(25.00 AS Decimal(10, 2)), N'Aji de Gallina')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (46, 16, 9, 2, CAST(30.00 AS Decimal(10, 2)), N'Tacu Tacu')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (47, 17, 1, 4, CAST(35.00 AS Decimal(10, 2)), N'Ceviche')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (48, 17, 5, 1, CAST(50.00 AS Decimal(10, 2)), N'Jalea Mixta')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (49, 18, 5, 2, CAST(50.00 AS Decimal(10, 2)), N'Jalea Mixta')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (50, 18, 8, 3, CAST(30.00 AS Decimal(10, 2)), N'Lomo Saltado')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (51, 18, 7, 2, CAST(25.00 AS Decimal(10, 2)), N'Aji de Gallina')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (52, 19, 12, 3, CAST(29.00 AS Decimal(10, 2)), N'Cau Cau')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (53, 19, 4, 2, CAST(38.00 AS Decimal(10, 2)), N'Parihuela')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (54, 19, 7, 2, CAST(25.00 AS Decimal(10, 2)), N'Aji de Gallina')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (55, 20, 2, 5, CAST(40.00 AS Decimal(10, 2)), N'Arroz con Mariscos')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (56, 20, 9, 1, CAST(30.00 AS Decimal(10, 2)), N'Tacu Tacu')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (57, 21, 5, 3, CAST(50.00 AS Decimal(10, 2)), N'Jalea Mixta')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (58, 21, 1, 2, CAST(35.00 AS Decimal(10, 2)), N'Ceviche')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (59, 21, 7, 1, CAST(25.00 AS Decimal(10, 2)), N'Aji de Gallina')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (60, 22, 9, 4, CAST(30.00 AS Decimal(10, 2)), N'Tacu Tacu')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (61, 22, 1, 5, CAST(35.00 AS Decimal(10, 2)), N'Ceviche')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (62, 22, 6, 1, CAST(32.00 AS Decimal(10, 2)), N'Sudado de Pescado')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (63, 23, 3, 2, CAST(50.00 AS Decimal(10, 2)), N'Chupe de Camarones')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (64, 23, 12, 2, CAST(29.00 AS Decimal(10, 2)), N'Cau Cau')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (65, 23, 4, 1, CAST(38.00 AS Decimal(10, 2)), N'Parihuela')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (66, 24, 5, 4, CAST(50.00 AS Decimal(10, 2)), N'Jalea Mixta')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (67, 24, 1, 3, CAST(35.00 AS Decimal(10, 2)), N'Ceviche')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (68, 24, 8, 1, CAST(30.00 AS Decimal(10, 2)), N'Lomo Saltado')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (69, 25, 1, 4, CAST(35.00 AS Decimal(10, 2)), N'Ceviche')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (70, 25, 8, 3, CAST(30.00 AS Decimal(10, 2)), N'Lomo Saltado')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (71, 25, 9, 2, CAST(30.00 AS Decimal(10, 2)), N'Tacu Tacu')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (72, 26, 2, 4, CAST(40.00 AS Decimal(10, 2)), N'Arroz con Mariscos')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (73, 26, 6, 2, CAST(32.00 AS Decimal(10, 2)), N'Sudado de Pescado')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (74, 26, 10, 1, CAST(28.00 AS Decimal(10, 2)), N'Seco de Res')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (75, 27, 7, 2, CAST(25.00 AS Decimal(10, 2)), N'Aji de Gallina')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (76, 27, 3, 1, CAST(50.00 AS Decimal(10, 2)), N'Chupe de Camarones')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (77, 27, 12, 1, CAST(29.00 AS Decimal(10, 2)), N'Cau Cau')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (78, 28, 5, 3, CAST(50.00 AS Decimal(10, 2)), N'Jalea Mixta')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (79, 28, 2, 1, CAST(40.00 AS Decimal(10, 2)), N'Arroz con Mariscos')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (80, 29, 8, 2, CAST(30.00 AS Decimal(10, 2)), N'Lomo Saltado')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (81, 29, 2, 3, CAST(40.00 AS Decimal(10, 2)), N'Arroz con Mariscos')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (82, 29, 7, 1, CAST(25.00 AS Decimal(10, 2)), N'Aji de Gallina')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (83, 30, 2, 5, CAST(40.00 AS Decimal(10, 2)), N'Arroz con Mariscos')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (84, 30, 5, 1, CAST(50.00 AS Decimal(10, 2)), N'Jalea Mixta')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (91, 35, 2, 3, CAST(40.00 AS Decimal(10, 2)), N'Arroz con Mariscos')
GO
INSERT [dbo].[DetallePedido] ([idDetallePedido], [idPedido], [numeroPlato], [cantidad], [precioUnitario], [nombrePlato]) VALUES (93, 36, 11, 5, CAST(22.80 AS Decimal(10, 2)), N'Carapulcra')
GO
SET IDENTITY_INSERT [dbo].[DetallePedido] OFF
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Piero Huaman', CAST(1300.00 AS Decimal(10, 2)), N'71374454', N'Mesero', 9123456)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'María López', CAST(1100.00 AS Decimal(10, 2)), N'23456789', N'Mesero', 10450824)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Juan Perez', CAST(1200.00 AS Decimal(10, 2)), N'90123456', N'Repartidor', 12345678)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Alejandro Silva', CAST(1350.00 AS Decimal(10, 2)), N'12345680', N'Mesero', 12377198)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Elena Romero', CAST(1120.00 AS Decimal(10, 2)), N'01234567', N'Repartidor', 12940013)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Sofía Jiménez', CAST(1320.00 AS Decimal(10, 2)), N'23456780', N'Mesero', 14656202)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'María Blanco', CAST(1150.00 AS Decimal(10, 2)), N'01234568', N'Repartidor', 17723812)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Mario Medina', CAST(1300.00 AS Decimal(10, 2)), N'78901235', N'Mesero', 21637576)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Cristina Ortega', CAST(1250.00 AS Decimal(10, 2)), N'89012346', N'Mesero', 23563098)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Gustavo Díaz', CAST(1280.00 AS Decimal(10, 2)), N'34567891', N'Repartidor', 24873461)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Carlos Sánchez', CAST(1300.00 AS Decimal(10, 2)), N'34567890', N'Repartidor', 29706002)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Fernando Castro', CAST(1500.00 AS Decimal(10, 2)), N'90123456', N'Mesero', 31689889)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Javier Salazar', CAST(1400.00 AS Decimal(10, 2)), N'90123457', N'Repartidor', 35554218)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Junior Zumaeta', CAST(1400.00 AS Decimal(10, 2)), N'56789012', N'Administrador', 37726222)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Piero Lopez', CAST(1300.00 AS Decimal(10, 2)), N'08123456', N'Repartidor', 45385772)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Ana García', CAST(1250.00 AS Decimal(10, 2)), N'45678901', N'Mesero', 69568896)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Lucía Fernández', CAST(1150.00 AS Decimal(10, 2)), N'67890123', N'Mesero', 70092528)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Claudia Vargas', CAST(1400.00 AS Decimal(10, 2)), N'45678902', N'Mesero', 73567930)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Marcelo Alarcon', CAST(1180.00 AS Decimal(10, 2)), N'89012345', N'Administrador', 77343205)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Patricia Ruiz', CAST(1100.00 AS Decimal(10, 2)), N'67890124', N'Repartidor', 80088869)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Juan Pérez', CAST(1200.00 AS Decimal(10, 2)), N'12345678', N'Mesero', 93086552)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Jose Kung', CAST(1230.00 AS Decimal(10, 2)), N'12345679', N'Administrador', 93576820)
GO
INSERT [dbo].[Empleado] ([nombreEmpleado], [salarioEmpleado], [dniEmpleado], [cargoEmpleado], [empleadoId]) VALUES (N'Diego Herrera', CAST(1200.00 AS Decimal(10, 2)), N'56789013', N'Mesero', 97157635)
GO
SET IDENTITY_INSERT [dbo].[Menu] ON 
GO
INSERT [dbo].[Menu] ([idMenu], [nombreMenu], [cantidadPlatos], [categoria]) VALUES (1, N'Menú Del Mar', 5, N'Mariscos y Pescados')
GO
INSERT [dbo].[Menu] ([idMenu], [nombreMenu], [cantidadPlatos], [categoria]) VALUES (2, N'Menú Criollo', 5, N'Comida Tradicional Peruana')
GO
INSERT [dbo].[Menu] ([idMenu], [nombreMenu], [cantidadPlatos], [categoria]) VALUES (3, N'Menú Parrillada', 5, N'Carnes a la Parrilla')
GO
INSERT [dbo].[Menu] ([idMenu], [nombreMenu], [cantidadPlatos], [categoria]) VALUES (4, N'Menú Andino', 4, N'Comida Andina')
GO
SET IDENTITY_INSERT [dbo].[Menu] OFF
GO
SET IDENTITY_INSERT [dbo].[Mesa] ON 
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (1, 1, 4, N'Libre', NULL, N'Reservado', 2)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (2, 2, 4, N'Ocupada', 10450824, N'No reservado', NULL)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (3, 3, 6, N'Libre', NULL, N'Reservado', 4)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (4, 4, 4, N'Ocupada', 14656202, N'No reservado', NULL)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (5, 5, 8, N'Libre', NULL, N'Reservado', 6)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (6, 6, 4, N'Ocupada', 23563098, N'No reservado', NULL)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (7, 7, 6, N'Libre', NULL, N'Reservado', 8)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (8, 8, 4, N'Ocupada', 69568896, N'No reservado', NULL)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (9, 9, 8, N'Libre', NULL, N'Reservado', 10)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (10, 10, 7, N'Ocupada', 73567930, N'No reservado', NULL)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (11, 11, 6, N'Libre', NULL, N'Reservado', 12)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (12, 12, 4, N'Ocupada', 97157635, N'No reservado', NULL)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (13, 13, 8, N'Libre', NULL, N'Reservado', 14)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (14, 14, 3, N'Ocupada', 10450824, N'No reservado', NULL)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (15, 15, 6, N'Libre', 31689889, N'Reservado', 16)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (16, 16, 4, N'Ocupada', 14656202, N'Reservado', 4)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (17, 17, 8, N'Libre', NULL, N'Reservado', 18)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (18, 18, 4, N'Ocupada', 23563098, N'No reservado', NULL)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (19, 19, 6, N'Libre', NULL, N'Reservado', 20)
GO
INSERT [dbo].[Mesa] ([idMesa], [numeroMesa], [capacidadMesa], [estadoMesa], [idMesero], [estado_reserva], [idClienteReserva]) VALUES (20, 20, 4, N'Ocupada', 69568896, N'No reservado', NULL)
GO
SET IDENTITY_INSERT [dbo].[Mesa] OFF
GO
SET IDENTITY_INSERT [dbo].[Pago] ON 
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (1, CAST(250.50 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-01' AS Date), N'Completado', 1)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (2, CAST(175.75 AS Decimal(10, 2)), N'Efectivo', CAST(N'2024-10-02' AS Date), N'Pendiente', 2)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (3, CAST(300.00 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-03' AS Date), N'Completado', 3)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (4, CAST(180.25 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-04' AS Date), N'Completado', 4)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (5, CAST(225.00 AS Decimal(10, 2)), N'Efectivo', CAST(N'2024-10-05' AS Date), N'Pendiente', 5)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (6, CAST(99.99 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-06' AS Date), N'Completado', 6)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (7, CAST(160.50 AS Decimal(10, 2)), N'Efectivo', CAST(N'2024-10-07' AS Date), N'Pendiente', 7)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (8, CAST(350.00 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-08' AS Date), N'Completado', 8)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (9, CAST(289.00 AS Decimal(10, 2)), N'Efectivo', CAST(N'2024-10-09' AS Date), N'Pendiente', 9)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (10, CAST(412.75 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-10' AS Date), N'Completado', 10)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (11, CAST(215.60 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-11' AS Date), N'Completado', 11)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (12, CAST(128.90 AS Decimal(10, 2)), N'Efectivo', CAST(N'2024-10-12' AS Date), N'Pendiente', 12)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (13, CAST(245.50 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-13' AS Date), N'Completado', 13)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (14, CAST(333.33 AS Decimal(10, 2)), N'Efectivo', CAST(N'2024-10-14' AS Date), N'Pendiente', 14)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (15, CAST(190.25 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-15' AS Date), N'Completado', 15)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (16, CAST(222.50 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-16' AS Date), N'Completado', 16)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (17, CAST(178.45 AS Decimal(10, 2)), N'Efectivo', CAST(N'2024-10-17' AS Date), N'Pendiente', 17)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (18, CAST(299.99 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-18' AS Date), N'Completado', 18)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (19, CAST(359.25 AS Decimal(10, 2)), N'Efectivo', CAST(N'2024-10-19' AS Date), N'Pendiente', 19)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (20, CAST(100.00 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-20' AS Date), N'Completado', 20)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (21, CAST(275.75 AS Decimal(10, 2)), N'Efectivo', CAST(N'2024-10-21' AS Date), N'Pendiente', 21)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (22, CAST(430.10 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-22' AS Date), N'Completado', 22)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (23, CAST(110.50 AS Decimal(10, 2)), N'Efectivo', CAST(N'2024-10-23' AS Date), N'Pendiente', 23)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (24, CAST(145.90 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-24' AS Date), N'Completado', 24)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (25, CAST(205.25 AS Decimal(10, 2)), N'Efectivo', CAST(N'2024-10-25' AS Date), N'Pendiente', 25)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (26, CAST(389.99 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-26' AS Date), N'Completado', 26)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (27, CAST(300.00 AS Decimal(10, 2)), N'Efectivo', CAST(N'2024-10-27' AS Date), N'Pendiente', 27)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (28, CAST(490.00 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-28' AS Date), N'Completado', 28)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (29, CAST(275.50 AS Decimal(10, 2)), N'Efectivo', CAST(N'2024-10-29' AS Date), N'Pendiente', 29)
GO
INSERT [dbo].[Pago] ([idPago], [montoPago], [metodoPago], [fechaPago], [estadoPago], [idPedido]) VALUES (30, CAST(150.00 AS Decimal(10, 2)), N'Tarjeta', CAST(N'2024-10-30' AS Date), N'Completado', 30)
GO
SET IDENTITY_INSERT [dbo].[Pago] OFF
GO
SET IDENTITY_INSERT [dbo].[Pedido] ON 
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (1, N'PED001', CAST(N'2024-03-01' AS Date), CAST(N'12:30:00' AS Time), CAST(275.00 AS Decimal(10, 2)), 1)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (2, N'PED002', CAST(N'2024-04-02' AS Date), CAST(N'14:15:00' AS Time), CAST(50.00 AS Decimal(10, 2)), 2)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (3, N'PED003', CAST(N'2024-08-03' AS Date), CAST(N'19:00:00' AS Time), CAST(308.00 AS Decimal(10, 2)), 3)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (4, N'PED004', CAST(N'2024-10-04' AS Date), CAST(N'11:45:00' AS Time), CAST(163.00 AS Decimal(10, 2)), 4)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (5, N'PED005', CAST(N'2024-10-05' AS Date), CAST(N'13:00:00' AS Time), CAST(246.80 AS Decimal(10, 2)), 5)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (6, N'PED006', CAST(N'2024-08-06' AS Date), CAST(N'17:30:00' AS Time), CAST(127.00 AS Decimal(10, 2)), 6)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (7, N'PED007', CAST(N'2024-09-07' AS Date), CAST(N'15:15:00' AS Time), CAST(183.00 AS Decimal(10, 2)), 7)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (8, N'PED008', CAST(N'2024-10-08' AS Date), CAST(N'20:00:00' AS Time), CAST(330.00 AS Decimal(10, 2)), 8)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (9, N'PED009', CAST(N'2024-09-09' AS Date), CAST(N'12:00:00' AS Time), CAST(185.00 AS Decimal(10, 2)), 9)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (10, N'PED010', CAST(N'2024-09-10' AS Date), CAST(N'13:45:00' AS Time), CAST(224.00 AS Decimal(10, 2)), 10)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (11, N'PED011', CAST(N'2024-08-11' AS Date), CAST(N'18:00:00' AS Time), CAST(193.00 AS Decimal(10, 2)), 11)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (12, N'PED012', CAST(N'2024-05-12' AS Date), CAST(N'16:30:00' AS Time), CAST(140.00 AS Decimal(10, 2)), 12)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (13, N'PED013', CAST(N'2024-07-13' AS Date), CAST(N'19:45:00' AS Time), CAST(250.00 AS Decimal(10, 2)), 13)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (14, N'PED014', CAST(N'2024-04-14' AS Date), CAST(N'21:15:00' AS Time), CAST(298.00 AS Decimal(10, 2)), 14)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (15, N'PED015', CAST(N'2024-06-15' AS Date), CAST(N'12:10:00' AS Time), CAST(212.00 AS Decimal(10, 2)), 15)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (16, N'PED016', CAST(N'2024-06-16' AS Date), CAST(N'14:25:00' AS Time), CAST(249.00 AS Decimal(10, 2)), 16)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (17, N'PED017', CAST(N'2024-06-17' AS Date), CAST(N'11:30:00' AS Time), CAST(190.00 AS Decimal(10, 2)), 17)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (18, N'PED018', CAST(N'2024-06-18' AS Date), CAST(N'13:15:00' AS Time), CAST(240.00 AS Decimal(10, 2)), 18)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (19, N'PED019', CAST(N'2024-09-19' AS Date), CAST(N'20:05:00' AS Time), CAST(213.00 AS Decimal(10, 2)), 19)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (20, N'PED020', CAST(N'2024-10-20' AS Date), CAST(N'11:45:00' AS Time), CAST(230.00 AS Decimal(10, 2)), 20)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (21, N'PED021', CAST(N'2024-08-21' AS Date), CAST(N'12:50:00' AS Time), CAST(245.00 AS Decimal(10, 2)), 21)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (22, N'PED022', CAST(N'2024-08-22' AS Date), CAST(N'14:00:00' AS Time), CAST(327.00 AS Decimal(10, 2)), 22)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (23, N'PED023', CAST(N'2024-04-23' AS Date), CAST(N'19:30:00' AS Time), CAST(196.00 AS Decimal(10, 2)), 23)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (24, N'PED024', CAST(N'2024-04-24' AS Date), CAST(N'18:20:00' AS Time), CAST(335.00 AS Decimal(10, 2)), 24)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (25, N'PED025', CAST(N'2024-07-25' AS Date), CAST(N'13:05:00' AS Time), CAST(290.00 AS Decimal(10, 2)), 25)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (26, N'PED026', CAST(N'2024-07-26' AS Date), CAST(N'20:35:00' AS Time), CAST(252.00 AS Decimal(10, 2)), 26)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (27, N'PED027', CAST(N'2024-07-27' AS Date), CAST(N'12:45:00' AS Time), CAST(129.00 AS Decimal(10, 2)), 27)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (28, N'PED028', CAST(N'2024-08-28' AS Date), CAST(N'14:30:00' AS Time), CAST(190.00 AS Decimal(10, 2)), 28)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (29, N'PED029', CAST(N'2024-08-29' AS Date), CAST(N'11:15:00' AS Time), CAST(205.00 AS Decimal(10, 2)), 29)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (30, N'PED030', CAST(N'2024-10-30' AS Date), CAST(N'13:40:00' AS Time), CAST(250.00 AS Decimal(10, 2)), 30)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (31, N'PED181', CAST(N'2024-12-08' AS Date), CAST(N'12:54:07' AS Time), CAST(113.00 AS Decimal(10, 2)), 1)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (32, N'PED264', CAST(N'2024-12-08' AS Date), CAST(N'12:55:45' AS Time), CAST(90.40 AS Decimal(10, 2)), 1)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (34, N'PED927', CAST(N'2024-12-08' AS Date), CAST(N'12:59:06' AS Time), CAST(119.00 AS Decimal(10, 2)), 1)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (35, N'PED1001', CAST(N'2024-01-05' AS Date), CAST(N'10:00:00' AS Time), CAST(120.00 AS Decimal(10, 2)), 1)
GO
INSERT [dbo].[Pedido] ([idPedido], [codigoPedido], [fechaPedido], [horaPedido], [totalPedido], [idCliente]) VALUES (36, N'PED0121', CAST(N'2024-02-15' AS Date), CAST(N'12:30:10' AS Time), CAST(114.00 AS Decimal(10, 2)), 6)
GO
SET IDENTITY_INSERT [dbo].[Pedido] OFF
GO
SET IDENTITY_INSERT [dbo].[Plato] ON 
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (1, N'Ceviche', CAST(35.00 AS Decimal(10, 2)), 1)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (2, N'Arroz con Mariscos', CAST(40.00 AS Decimal(10, 2)), 1)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (3, N'Chupe de Camarones', CAST(50.00 AS Decimal(10, 2)), 1)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (4, N'Parihuela', CAST(38.00 AS Decimal(10, 2)), 1)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (5, N'Jalea Mixta', CAST(50.00 AS Decimal(10, 2)), 1)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (6, N'Sudado de Pescado', CAST(32.00 AS Decimal(10, 2)), 1)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (7, N'Aji de Gallina', CAST(25.00 AS Decimal(10, 2)), 2)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (8, N'Lomo Saltado', CAST(30.00 AS Decimal(10, 2)), 2)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (9, N'Tacu Tacu', CAST(30.00 AS Decimal(10, 2)), 2)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (10, N'Seco de Res', CAST(28.00 AS Decimal(10, 2)), 2)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (11, N'Carapulcra', CAST(22.80 AS Decimal(10, 2)), 2)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (12, N'Cau Cau', CAST(29.00 AS Decimal(10, 2)), 4)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (13, N'Bistec a lo Pobre', CAST(32.00 AS Decimal(10, 2)), 1)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (14, N'Chancho al Cilindro', CAST(40.00 AS Decimal(10, 2)), 3)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (15, N'Anticuchos', CAST(20.00 AS Decimal(10, 2)), 3)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (16, N'Chicharron de Chancho', CAST(31.00 AS Decimal(10, 2)), 3)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (17, N'Lomo a la Parrilla', CAST(32.80 AS Decimal(10, 2)), 3)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (18, N'Chancho al Palo', CAST(36.00 AS Decimal(10, 2)), 3)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (20, N'Causa Norteña', CAST(33.80 AS Decimal(10, 2)), 4)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (21, N'Arroz con Pato', CAST(40.20 AS Decimal(10, 2)), 4)
GO
INSERT [dbo].[Plato] ([numeroPlato], [nombrePlato], [precioPlato], [idMenu]) VALUES (24, N'Juane', CAST(15.00 AS Decimal(10, 2)), 4)
GO
SET IDENTITY_INSERT [dbo].[Plato] OFF
GO
ALTER TABLE [dbo].[Plato] ADD  DEFAULT ((0)) FOR [idMenu]
GO
ALTER TABLE [dbo].[Delivery]  WITH CHECK ADD  CONSTRAINT [FK_Delivery_Empleado] FOREIGN KEY([idRepartidor])
REFERENCES [dbo].[Empleado] ([empleadoId])
GO
ALTER TABLE [dbo].[Delivery] CHECK CONSTRAINT [FK_Delivery_Empleado]
GO
ALTER TABLE [dbo].[Delivery]  WITH CHECK ADD  CONSTRAINT [fk_pedido] FOREIGN KEY([idPedido])
REFERENCES [dbo].[Pedido] ([idPedido])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Delivery] CHECK CONSTRAINT [fk_pedido]
GO
ALTER TABLE [dbo].[DetallePedido]  WITH CHECK ADD FOREIGN KEY([idPedido])
REFERENCES [dbo].[Pedido] ([idPedido])
GO
ALTER TABLE [dbo].[DetallePedido]  WITH CHECK ADD FOREIGN KEY([numeroPlato])
REFERENCES [dbo].[Plato] ([numeroPlato])
GO
ALTER TABLE [dbo].[Mesa]  WITH CHECK ADD  CONSTRAINT [fk_cliente_reservas] FOREIGN KEY([idClienteReserva])
REFERENCES [dbo].[Cliente] ([idCliente])
GO
ALTER TABLE [dbo].[Mesa] CHECK CONSTRAINT [fk_cliente_reservas]
GO
ALTER TABLE [dbo].[Pago]  WITH CHECK ADD FOREIGN KEY([idPedido])
REFERENCES [dbo].[Pedido] ([idPedido])
GO
ALTER TABLE [dbo].[Pedido]  WITH CHECK ADD FOREIGN KEY([idCliente])
REFERENCES [dbo].[Cliente] ([idCliente])
GO
ALTER TABLE [dbo].[Plato]  WITH CHECK ADD  CONSTRAINT [fk_plato_menu] FOREIGN KEY([idMenu])
REFERENCES [dbo].[Menu] ([idMenu])
GO
ALTER TABLE [dbo].[Plato] CHECK CONSTRAINT [fk_plato_menu]
GO
ALTER TABLE [dbo].[DetallePedido]  WITH CHECK ADD CHECK  (([cantidad]>(0)))
GO
/****** Object:  StoredProcedure [dbo].[AsignarMesaVaciaAMesero]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- Crear procedimiento AsignarMesaVaciaAMesero
CREATE   PROCEDURE [dbo].[AsignarMesaVaciaAMesero]
	@idMesa INT,
	@idMesero INT
AS 
BEGIN
    IF EXISTS(SELECT 1 FROM Mesa WHERE idMesa = @idMesa AND estadoMesa = 'Vacia')
    BEGIN
        UPDATE Mesero
        SET idMesa = @idMesa
        WHERE idMesero = @idMesero;

        UPDATE Mesa
        SET estadoMesa = 'Ocupada'
        WHERE idMesa = @idMesa;
    END
END;
GO
/****** Object:  StoredProcedure [dbo].[ModificarCapacidadMesa]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- Crear procedimiento ModificarCapacidadMesa
CREATE   PROCEDURE [dbo].[ModificarCapacidadMesa]
    @idMesa INT,
    @newCapacidad INT
AS
BEGIN
    IF EXISTS (SELECT 1 FROM Mesa WHERE idMesa = @idMesa)
    BEGIN
        UPDATE Mesa
        SET capacidadMesa = @newCapacidad
        WHERE idMesa = @idMesa;
    END
END;
GO
/****** Object:  StoredProcedure [dbo].[ModificarMesaOcupada]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- Crear procedimiento ModificarMesaOcupada
CREATE PROCEDURE [dbo].[ModificarMesaOcupada]
	@newMesero INT,
	@idMesa INT
AS
BEGIN
    IF EXISTS(
        SELECT 1 FROM Mesa WHERE idMesa = @idMesa AND estadoMesa = 'Ocupada'
    )
    BEGIN
        UPDATE Mesero
        SET idMesa = @idMesa
        WHERE idMesero = @newMesero;
    END
END;
GO
/****** Object:  StoredProcedure [dbo].[sp_ActualizarDelivery]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- Procedimiento para actualizar un delivery
CREATE   PROCEDURE [dbo].[sp_ActualizarDelivery]
    @idDelivery INT,
    @estadoDelivery NVARCHAR(50)

AS
BEGIN
    UPDATE Delivery
    SET 
        estadoDelivery = @estadoDelivery
    WHERE idDelivery = @idDelivery;
END;

GO
/****** Object:  StoredProcedure [dbo].[sp_EliminarDelivery]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- Procedimiento para eliminar un delivery
CREATE   PROCEDURE [dbo].[sp_EliminarDelivery]
    @idDelivery INT
AS
BEGIN
    DELETE FROM Delivery
    WHERE idDelivery = @idDelivery;
END;
GO
/****** Object:  StoredProcedure [dbo].[sp_EliminarPedido]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[sp_EliminarPedido] (
    @idPedido INT -- Parámetro para el id del pedido
)
AS
BEGIN
    BEGIN TRANSACTION; -- Inicia la transacción

    -- Elimina los detalles del pedido
    DELETE FROM DetallePedido
    WHERE idPedido = @idPedido;

    -- Elimina el pedido
    DELETE FROM Pedido
    WHERE idPedido = @idPedido;

    COMMIT TRANSACTION; -- Confirma los cambios
END;
GO
/****** Object:  StoredProcedure [dbo].[sp_InsertarDelivery]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[sp_InsertarDelivery]
    @direccionEntrega NVARCHAR(200),
    @estadoDelivery NVARCHAR(50),
    @idRepartidor INT
AS
BEGIN
    INSERT INTO Delivery (direccionEntrega, estadoDelivery, idRepartidor)
    VALUES (@direccionEntrega, @estadoDelivery, @idRepartidor);
END;
GO
/****** Object:  StoredProcedure [dbo].[sp_InsertarPedido]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[sp_InsertarPedido] 
    @CodigoPedido NVARCHAR(50),
    @FechaPedido DATE,
    @HoraPedido TIME,
    @IdCliente INT,
    @DetallePedido NVARCHAR(MAX), -- Formato JSON para los platos
    @EsDelivery BIT,
    @DireccionEntrega NVARCHAR(200) = NULL
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        -- Iniciar una transacción
        BEGIN TRANSACTION;

        -- Insertar el pedido en la tabla Pedido
        DECLARE @NuevoIdPedido INT;
        INSERT INTO Pedido (codigoPedido, fechaPedido, horaPedido, totalPedido, idCliente)
        VALUES (@CodigoPedido, @FechaPedido, @HoraPedido, 0, @IdCliente);
        SET @NuevoIdPedido = SCOPE_IDENTITY();

        -- Procesar los detalles del pedido
        DECLARE @JsonDetalle TABLE (
            numeroPlato INT,
            cantidad INT,
            precioUnitario DECIMAL(10, 2)
        );

        INSERT INTO @JsonDetalle
        SELECT numeroPlato, cantidad, precioUnitario
        FROM OPENJSON(@DetallePedido)
        WITH (
            numeroPlato INT,
            cantidad INT,
            precioUnitario DECIMAL(10, 2)
        );

        DECLARE @TotalPedido DECIMAL(10, 2) = 0;

        INSERT INTO DetallePedido (idPedido, numeroPlato, cantidad, precioUnitario, nombrePlato)
        SELECT @NuevoIdPedido, P.numeroPlato, D.cantidad, P.precioPlato, P.nombrePlato
        FROM @JsonDetalle D
        JOIN Plato P ON D.numeroPlato = P.numeroPlato;

        -- Calcular el total del pedido
        SELECT @TotalPedido = SUM(D.cantidad * D.precioUnitario)
        FROM @JsonDetalle D;

        UPDATE Pedido
        SET totalPedido = @TotalPedido
        WHERE idPedido = @NuevoIdPedido;

        -- Si es delivery, registrar la información
        IF @EsDelivery = 1
        BEGIN
            INSERT INTO Delivery (direccionEntrega, estadoDelivery, idPedido, idRepartidor)
            VALUES (@DireccionEntrega, 'En espera', @NuevoIdPedido, 
                (SELECT TOP 1 idRepartidor FROM Repartidor ORDER BY NEWID()));
        END;

        -- Confirmar transacción
        COMMIT TRANSACTION;

        -- Devolver el ID del nuevo pedido
        SELECT @NuevoIdPedido AS idPedido;

    END TRY
    BEGIN CATCH
        -- Revertir transacción en caso de error
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END;
GO
/****** Object:  StoredProcedure [dbo].[sp_LeerDelivery]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- Procedimiento para leer un delivery por idDelivery
CREATE   PROCEDURE [dbo].[sp_LeerDelivery](
    @idDelivery INT
)AS
BEGIN
    SELECT idDelivery, direccionEntrega, estadoDelivery, delivery.idPedido, pedido.codigoPedido, 
	pedido.fechaPedido, Pedido.horaPedido, idRepartidor
    FROM Delivery inner join Pedido on Delivery.idPedido = Pedido.idPedido
    WHERE idDelivery = @idDelivery;
END;

GO
/****** Object:  StoredProcedure [dbo].[sp_ListarDeliverys]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Procedimiento listar todos los deliverys
create   procedure [dbo].[sp_ListarDeliverys] 
as
begin
	select Delivery.idDelivery, Delivery.idPedido, Delivery.direccionEntrega, Delivery.estadoDelivery, 
	Delivery.idRepartidor
	from Delivery
end
GO
/****** Object:  StoredProcedure [dbo].[sp_ListarDetallesPedido]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- Procedimiento para listar los detalles de un pedido específico
CREATE   PROCEDURE [dbo].[sp_ListarDetallesPedido] (
    @idPedido INT -- Parámetro para el id del pedido
)
AS
BEGIN
    SELECT dp.idDetallePedido, dp.numeroPlato, dp.cantidad, dp.precioUnitario, dp.nombrePlato
    FROM DetallePedido dp
    WHERE dp.idPedido = @idPedido;
END;
GO
/****** Object:  StoredProcedure [dbo].[sp_ListarPedidos]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- Procedimiento para listar todos los pedidos y sus detalles
CREATE   PROCEDURE [dbo].[sp_ListarPedidos]
AS
BEGIN
    SELECT p.idPedido, p.codigoPedido, p.fechaPedido, p.horaPedido, p.totalPedido, c.nombreCliente
    FROM Pedido p
    INNER JOIN Cliente c ON p.idCliente = c.idCliente
    ORDER BY p.fechaPedido DESC; -- Ordena los pedidos por fecha (más recientes primero)
END;
GO
/****** Object:  StoredProcedure [dbo].[sp_ListarPedidosPorCliente]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- Procedimiento para listar pedidos de un cliente específico
CREATE   PROCEDURE [dbo].[sp_ListarPedidosPorCliente] (
    @nombreCliente NVARCHAR(100) -- Parámetro para el nombre del cliente
)
AS
BEGIN
    DECLARE @idCliente INT;

    -- Buscar el id del cliente basado en el nombre
    SELECT @idCliente = idCliente
    FROM Cliente
    WHERE nombreCliente = @nombreCliente;

    -- Si el cliente existe, listar sus pedidos
    IF @idCliente IS NOT NULL
    BEGIN
        SELECT p.idPedido, p.codigoPedido, p.fechaPedido, p.horaPedido, p.totalPedido
        FROM Pedido p
        WHERE p.idCliente = @idCliente
        ORDER BY p.fechaPedido DESC; -- Ordena los pedidos por fecha
    END
    ELSE
    BEGIN
        PRINT 'Cliente no encontrado.';
    END
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ActualizarCliente]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- 2. ACTUALIZAR CLIENTE
CREATE   PROCEDURE [dbo].[usp_ActualizarCliente] (
    @idCliente INT,               -- ID del cliente a actualizar
    @nuevoNombreCliente NVARCHAR(100)  -- Nuevo nombre del cliente
)
AS
BEGIN
    -- Verificar si el nuevo nombre ya está registrado
    IF EXISTS (SELECT 1 FROM Cliente WHERE nombreCliente = @nuevoNombreCliente)
    BEGIN
        -- Si ya existe, retornar un mensaje de error
        SELECT 'El nuevo nombre ya está registrado para otro cliente.' AS mensaje;
        RETURN;
    END

    -- Realizar la actualización del nombre del cliente
    UPDATE Cliente
    SET nombreCliente = @nuevoNombreCliente
    WHERE idCliente = @idCliente;  -- Usamos idCliente para asegurarnos de que se actualiza al cliente correcto

    -- Retornar mensaje de éxito
    SELECT 'Cliente actualizado correctamente' AS mensaje;
END

GO
/****** Object:  StoredProcedure [dbo].[usp_actualizarEmpleado]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_actualizarEmpleado] 
    @dniEmpleado VARCHAR(8),
    @nuevoNombre NVARCHAR(50),
    @nuevoCargo NVARCHAR(50),
    @nuevoSalario DECIMAL(10, 2),
    @nuevoIdEmpleado INT -- Se agrega el parámetro para el nuevo empleadoId
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Empleado WHERE dniEmpleado = @dniEmpleado)
    BEGIN
        SELECT 'Empleado no encontrado' AS mensaje;
        RETURN;
    END

    UPDATE Empleado
    SET 
        nombreEmpleado = @nuevoNombre,
        cargoEmpleado = @nuevoCargo,
        salarioEmpleado = @nuevoSalario,
        empleadoId = @nuevoIdEmpleado -- Se agrega la actualización del empleadoId
    WHERE dniEmpleado = @dniEmpleado;

    SELECT 'Datos actualizados correctamente' AS mensaje;
END

GO
/****** Object:  StoredProcedure [dbo].[usp_ActualizarPlato]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_ActualizarPlato] (
    @nombrePlato NVARCHAR(100),
    @nuevoNombrePlato NVARCHAR(100),
    @nuevoPrecioPlato DECIMAL(10, 2), 
	@nuevoIdMenuPlato int
)
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Plato WHERE nombrePlato = @nombrePlato)
    BEGIN
        SELECT 'Plato no encontrado' AS mensaje;
        RETURN;
    END

    UPDATE Plato
    SET nombrePlato = @nuevoNombrePlato,
        precioPlato = @nuevoPrecioPlato
    WHERE nombrePlato = @nombrePlato;

    SELECT 'Plato actualizado correctamente' AS mensaje;
END
GO
/****** Object:  StoredProcedure [dbo].[usp_AsignarMeseroAmesa]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_AsignarMeseroAmesa]
    @idMesero INT,
    @idMesa INT
AS
BEGIN
    UPDATE Mesa
    SET 
        idMesero = @idMesero,
        estadoMesa = 'Ocupada'
    WHERE idMesa = @idMesa;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_AsignarRepartidorAdelivery]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_AsignarRepartidorAdelivery]    
 @idRepartidor INT,    
 @idDelivery INT    
AS    
BEGIN    
    UPDATE Delivery    
    SET estadoDelivery = 'En camino'    
    WHERE idDelivery = @idDelivery;    
    
    UPDATE Delivery    
    SET idRepartidor = @idRepartidor    
    WHERE idDelivery = @idDelivery;        
END; 
GO
/****** Object:  StoredProcedure [dbo].[usp_AsignarReservacionPorCliente]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Crear procedimiento usp_ListarYAsignarMesero    
CREATE   PROCEDURE [dbo].[usp_AsignarReservacionPorCliente] (@idMesa INT,   @idCliente INT)   
AS    
BEGIN           
UPDATE Mesa  
SET idClienteReserva = @idCliente                
WHERE idMesa = @idMesa   
END 
GO
/****** Object:  StoredProcedure [dbo].[usp_ClientesFrecuentes]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Procedimiento 3: Clientes Más Frecuentes
CREATE PROCEDURE [dbo].[usp_ClientesFrecuentes]
    @Mes INT  -- El mes ingresado por el administrador
AS
BEGIN
    SELECT 
        C.nombreCliente AS Cliente,
        COUNT(P.idPedido) AS CantidadDePedidos
    FROM Pedido P
    INNER JOIN Cliente C ON P.idCliente = C.idCliente
    WHERE MONTH(P.fechaPedido) = @Mes
    GROUP BY C.nombreCliente
    ORDER BY CantidadDePedidos DESC;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_CrearCliente]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_CrearCliente] (
    @nombreCliente NVARCHAR(100)
)
AS
BEGIN 
    INSERT INTO Cliente (nombreCliente)  -- Solo insertamos el nombreCliente, no el idCliente
    VALUES (@nombreCliente);
END

GO
/****** Object:  StoredProcedure [dbo].[usp_CrearEmpleado]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_CrearEmpleado](
    @empleadoId INT,
    @nombreEmpleado VARCHAR(100), 
    @salarioEmpleado DECIMAL(10, 2), 
    @dniEmpleado VARCHAR(8), 
    @cargoEmpleado VARCHAR(50)
)
AS
BEGIN 
    -- Inserta todos los campos correctamente
    INSERT INTO Empleado (empleadoId, nombreEmpleado, salarioEmpleado, dniEmpleado, cargoEmpleado)
    VALUES (@empleadoId, @nombreEmpleado, @salarioEmpleado, @dniEmpleado, @cargoEmpleado);
END
GO
/****** Object:  StoredProcedure [dbo].[usp_CrearPlato]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_CrearPlato] (
    @nombrePlato NVARCHAR(100),
    @precioPlato DECIMAL(10, 2),
	@idMenu int
)
AS
BEGIN 
    INSERT INTO Plato (nombrePlato, precioPlato, idMenu)
    VALUES (@nombrePlato, @precioPlato, @idMenu);
END
GO
/****** Object:  StoredProcedure [dbo].[usp_DetallesProductosVendidos]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Procedimiento 2: Detalles de Productos Vendidos
CREATE PROCEDURE [dbo].[usp_DetallesProductosVendidos]
    @Mes INT  -- El mes ingresado por el administrador
AS
BEGIN
    SELECT 
        PL.nombrePlato AS Producto,
        SUM(DP.cantidad) AS CantidadVendida,
        PL.precioPlato AS PrecioUnitario,
        SUM(DP.cantidad * PL.precioPlato) AS TotalPorProducto
    FROM DetallePedido DP
    INNER JOIN Plato PL ON DP.numeroPlato = PL.numeroPlato
    INNER JOIN Pedido P ON DP.idPedido = P.idPedido
    WHERE MONTH(P.fechaPedido) = @Mes
    GROUP BY PL.nombrePlato, PL.precioPlato;
END;

GO
/****** Object:  StoredProcedure [dbo].[usp_editarEstadoPago]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[usp_editarEstadoPago] (
	@nuevoEstadoPago varchar(50),
	@idPago int
)
as
begin
	update pago 
	set estadoPago = @nuevoEstadoPago
	where idPago = @idPago 
end 
GO
/****** Object:  StoredProcedure [dbo].[usp_EliminarCliente]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- 3. ELIMINAR CLIENTE
CREATE   PROCEDURE [dbo].[usp_EliminarCliente] (
    @idCliente INT  -- Usamos idCliente para eliminar al cliente correcto
)
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Cliente WHERE idCliente = @idCliente)
    BEGIN
        SELECT 'Cliente no encontrado' AS mensaje;
        RETURN;
    END

    DELETE FROM Cliente
    WHERE idCliente = @idCliente;  -- Eliminamos el cliente por su idCliente

    SELECT 'Cliente eliminado correctamente' AS mensaje;
END

GO
/****** Object:  StoredProcedure [dbo].[usp_eliminarEmpleado]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_eliminarEmpleado] 
    @dniEmpleado VARCHAR(8)
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Empleado WHERE dniEmpleado = @dniEmpleado)
    BEGIN
        SELECT 'Empleado no encontrado' AS mensaje;
        RETURN;
    END

    DELETE FROM Empleado
    WHERE dniEmpleado = @dniEmpleado;

    SELECT 'Empleado eliminado correctamente' AS mensaje;
END

GO
/****** Object:  StoredProcedure [dbo].[usp_EliminarPedido]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_EliminarPedido] (
    @idPedido int
)
AS
BEGIN
	delete Pedido
	where idPedido = @idPedido
	
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_EliminarPlato]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- 3. ELIMINAR PLATO
CREATE   PROCEDURE [dbo].[usp_EliminarPlato] (
    @nombrePlato NVARCHAR(100)
)
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Plato WHERE nombrePlato = @nombrePlato)
    BEGIN
        SELECT 'Plato no encontrado' AS mensaje;
        RETURN;
    END

    DELETE FROM Plato
    WHERE nombrePlato = @nombrePlato;

    SELECT 'Plato eliminado correctamente' AS mensaje;
END
GO
/****** Object:  StoredProcedure [dbo].[usp_InsertarDelivery]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_InsertarDelivery]
    @idPedido INT,
    @direccionEntrega NVARCHAR(200),
    @idRepartidor INT
AS
BEGIN
    -- Insertar el delivery
    INSERT INTO Delivery (idPedido, direccionEntrega, estadoDelivery, idRepartidor)
    VALUES (@idPedido, @direccionEntrega, 'En espera', @idRepartidor);

    -- Retornar el idDelivery del nuevo delivery insertado
    SELECT SCOPE_IDENTITY() AS idDelivery;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_InsertarDetallePedido]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_InsertarDetallePedido]
    @idPedido INT,                   -- ID del Pedido relacionado
    @numeroPlato INT,                -- Número del plato
    @cantidad INT,                   -- Cantidad del plato
    @precioUnitario DECIMAL(10, 2)   -- Precio del plato
AS
BEGIN
    -- Insertar el Detalle del Pedido en la base de datos
    INSERT INTO DetallePedido (idPedido, numeroPlato, cantidad, precioUnitario)
    VALUES (@idPedido, @numeroPlato, @cantidad, @precioUnitario);
    
    -- Retornar el ID del nuevo Detalle de Pedido
    SELECT SCOPE_IDENTITY() AS idDetallePedido;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_InsertarNuevoDelivery]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[usp_InsertarNuevoDelivery]
(@direccionEntrega nvarchar(200), @estadoDelivery nvarchar(100), @idPedido int, @idRepartidor int)
as
begin 
	insert into Delivery (direccionEntrega, estadoDelivery, idPedido, idRepartidor)
	values (@direccionEntrega, @estadoDelivery, @idPedido, @idRepartidor)
end
GO
/****** Object:  StoredProcedure [dbo].[usp_InsertarPedido]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_InsertarPedido]
    @idCliente INT,
    @codigo NVARCHAR(50),
    @fecha DATE,
    @hora TIME,
    @total DECIMAL(10, 2)
AS
BEGIN
    -- Insertar el pedido
    INSERT INTO Pedido (idCliente, codigoPedido, fechaPedido, horaPedido, totalPedido)
    VALUES (@idCliente, @codigo, @fecha, @hora, @total);

    -- Retornar el idPedido del nuevo pedido insertado
    SELECT SCOPE_IDENTITY() AS idPedido;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_leerEmpleado]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_leerEmpleado] (
    @dniEmpleado VARCHAR(8)
)
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Empleado WHERE dniEmpleado = @dniEmpleado)
    BEGIN
        SELECT 'Empleado no encontrado' AS mensaje;
        RETURN;
    END

    SELECT empleadoId, nombreEmpleado, salarioEmpleado, dniEmpleado, cargoEmpleado
    FROM Empleado 
    WHERE dniEmpleado = @dniEmpleado;
END;

GO
/****** Object:  StoredProcedure [dbo].[usp_LeerPlato]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[usp_LeerPlato](
	@nombrePlato NVARCHAR(100),
	@precioPlato decimal
)
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Plato WHERE nombrePlato = @nombrePlato)
    BEGIN
        SELECT 'Empleado no encontrado' AS mensaje;
        RETURN;
    END

    UPDATE Plato
    SET 
        nombrePlato = @nombrePlato,
		precioPlato = @precioPlato
    WHERE nombrePlato = nombrePlato;

    SELECT 'Datos actualizados correctamente' AS mensaje;
END

GO
/****** Object:  StoredProcedure [dbo].[usp_ListarClientes]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- 4. LISTAR TODOS LOS CLIENTES
CREATE   PROCEDURE [dbo].[usp_ListarClientes]
AS
BEGIN
    SELECT idCliente, nombreCliente
    FROM Cliente
    WHERE EXISTS (SELECT 1 FROM Cliente)
    ORDER BY idCliente;
END

GO
/****** Object:  StoredProcedure [dbo].[usp_ListarDelivery]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[usp_ListarDelivery]   
as
begin
    select idDelivery, direccionEntrega, estadoDelivery, idPedido, idRepartidor, Empleado.nombreEmpleado
	from Delivery join Empleado on Delivery.idRepartidor = empleadoId
	and Delivery.idRepartidor = empleadoId and Empleado.cargoEmpleado = 'Repartidor'
	order by Delivery.idDelivery
end
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarDetalleSegunPedido]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[usp_ListarDetalleSegunPedido]
(@idPedido int)
as
begin
	select idPedido, numeroPlato, nombrePlato, cantidad, precioUnitario, subtotal
	from DetallePedido
	where idPedido = @idPedido	
end
GO
/****** Object:  StoredProcedure [dbo].[usp_listarEmpleado]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_listarEmpleado]
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Empleado)
    BEGIN
        SELECT 'No hay empleados en la base de datos.' AS mensaje;
        RETURN;
    END

    SELECT empleadoId, dniEmpleado, nombreEmpleado, salarioEmpleado, cargoEmpleado
    FROM Empleado
    ORDER BY Empleado.cargoEmpleado;
END
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarMenus]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_ListarMenus] 
    @nombreMenu VARCHAR(12), 
    @idMenu INT
AS
BEGIN
    SELECT Plato.numeroPlato, Plato.nombrePlato, Plato.precioPlato, Plato.idMenu
    FROM Plato
    INNER JOIN Menu ON Plato.idMenu = Menu.idMenu
    WHERE Menu.nombreMenu = @nombreMenu 
    AND Plato.idMenu = @idMenu;
END
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarMesasDisponibles]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/****** Object:  StoredProcedure [dbo].[usp_ListarMesasDisponibles]  Script Date: 23/11/2024 ******/
CREATE   PROCEDURE [dbo].[usp_ListarMesasDisponibles]
AS
BEGIN
    SELECT idMesa, numeroMesa, capacidadMesa
    FROM Mesa
    WHERE estadoMesa = 'Libre';
END

GO
/****** Object:  StoredProcedure [dbo].[usp_ListarMesasOcupadas]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_ListarMesasOcupadas]
AS
BEGIN
	select mesa.idMesa, numeroMesa, capacidadMesa
	from Mesa
	where estadoMesa = 'Ocupada'
END
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarPagos]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[usp_ListarPagos]
as
begin
	select idPago, montoPago, metodoPago, fechaPago, estadoPago, idPedido
	from Pago
end 
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarPlatos]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_ListarPlatos]
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Plato)
    BEGIN
        SELECT 'No hay platos en la base de datos.' AS mensaje;
        RETURN;
    END

    SELECT nombrePlato, precioPlato, idMenu
    FROM Plato
    ORDER BY nombrePlato;
END
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarTodasLasMesas]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_ListarTodasLasMesas]
AS
BEGIN
    SELECT idMesa, numeroMesa, capacidadMesa, estadoMesa
    FROM Mesa;
END

GO
/****** Object:  StoredProcedure [dbo].[usp_ListarYAsignarMesero]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Crear procedimiento usp_ListarYAsignarMesero
CREATE   PROCEDURE [dbo].[usp_ListarYAsignarMesero]
	@idMesero INT,
	@idMesa INT
AS
BEGIN
    IF EXISTS(
        SELECT 1 FROM Mesa
        WHERE idMesa = @idMesa AND estadoMesa = 'Vacia'
    )
    BEGIN
        IF NOT EXISTS(
            SELECT 1 FROM Mesero WHERE idMesero = @idMesero AND idMesa IS NOT NULL
        )
        BEGIN
            UPDATE Mesa
            SET estadoMesa = 'Ocupada'
            WHERE idMesa = @idMesa;

            UPDATE Mesero
            SET idMesa = @idMesa
            WHERE idMesero = @idMesero;
        END
    END
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ModificarDelivey]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_ModificarDelivey] (
    @idDelivery int,
	@NuevaDireccion varchar(200)
)
AS
BEGIN
	update Delivery
	set direccionEntrega = @NuevaDireccion
	where idDelivery = @idDelivery
	
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ModificarMenu]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
 create   procedure [dbo].[usp_ModificarMenu] (@nombreMenu varchar(12), @idMenu int)
 as
 begin
	IF EXISTS (SELECT 1 FROM Menu WHERE idMenu = @idMenu)
    BEGIN
        UPDATE Menu
        SET nombreMenu = @nombreMenu
        WHERE idMenu = @idMenu;
	end
 end 
GO
/****** Object:  StoredProcedure [dbo].[usp_MostrarDetallePedido]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_MostrarDetallePedido] (
    @idPedido int
)
AS
BEGIN
	select Cliente.nombreCliente, Pedido.codigoPedido, Pedido.fechaPedido, Pedido.horaPedido,  
	DetallePedido.numeroPlato, DetallePedido.nombrePlato, DetallePedido.cantidad, DetallePedido.precioUnitario,
	Pedido.totalPedido
	from Cliente inner join Pedido on Cliente.idCliente = Pedido.idCliente inner join DetallePedido
	on DetallePedido.idPedido = Pedido.idPedido
	where Pedido.idPedido = @idPedido 
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_NombresClientes]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[usp_NombresClientes]
as 
begin 
	select Cliente.nombreCliente 
	from Cliente
	order by nombreCliente
end
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerFechaPedido]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[usp_ObtenerFechaPedido] (@idPedido int)
as
begin 
	select top 1 Pedido.fechaPedido
	from Pedido
	where Pedido.idPedido = @idPedido
end 
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerHoraPedido]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[usp_ObtenerHoraPedido] (@idPedido int)
as
begin 
	select top 1 Pedido.horaPedido
	from Pedido
	where Pedido.idPedido = @idPedido
end 
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerMetodoPago]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[usp_ObtenerMetodoPago] (@idPedido int)
as
begin 
	select top 1 Pago.metodoPago
	from Pago
	where Pago.idPago = @idPedido
end 
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerNombreCliente]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[usp_ObtenerNombreCliente] (@idPedido int)
as
begin 
	select top 1 Cliente.nombreCliente
	from Cliente inner join Pedido on Pedido.idCliente = Cliente.idCliente inner join DetallePedido on DetallePedido.idPedido = Pedido.idPedido
	where Pedido.idPedido = @idPedido
end 
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerTotalPedido]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[usp_ObtenerTotalPedido] (@idPedido int)
as
begin 
	select top 1 Pedido.totalPedido
	from Pedido
	where Pedido.idPedido = @idPedido
end 
GO
/****** Object:  StoredProcedure [dbo].[usp_ResumenMensual]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_ResumenMensual]
    @Mes INT -- El mes ingresado por el administrador
AS
BEGIN
    -- Total de pedidos vendidos durante el mes
    SELECT 
        COUNT(*) AS TotalPedidos
    FROM Pedido P
    WHERE MONTH(P.fechaPedido) = @Mes;

    -- Cliente más frecuente
    SELECT TOP 1
        C.idCliente,
        C.nombreCliente AS ClienteMasFrecuente,
        COUNT(P.idPedido) AS PedidosClienteFrecuente
    FROM Pedido P
    INNER JOIN Cliente C ON P.idCliente = C.idCliente
    WHERE MONTH(P.fechaPedido) = @Mes
    GROUP BY C.idCliente, C.nombreCliente
    ORDER BY COUNT(P.idPedido) DESC;

    -- Plato más vendido
    SELECT TOP 1
        PL.numeroPlato,
        PL.nombrePlato AS PlatoMasVendido,
        SUM(DP.cantidad) AS CantidadVendida
    FROM DetallePedido DP
    INNER JOIN Plato PL ON DP.numeroPlato = PL.numeroPlato
    INNER JOIN Pedido P ON DP.idPedido = P.idPedido
    WHERE MONTH(P.fechaPedido) = @Mes
    GROUP BY PL.numeroPlato, PL.nombrePlato
    ORDER BY SUM(DP.cantidad) DESC;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ResumenMensualCompleto]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[usp_ResumenMensualCompleto]
    @Mes INT -- El mes ingresado por el administrador
AS
BEGIN
    -- Total de ventas del mes
    DECLARE @TotalVentas DECIMAL(18, 2);
    SELECT @TotalVentas = SUM(P.totalPedido)
    FROM Pedido P
    WHERE MONTH(P.fechaPedido) = @Mes;

    -- Total de productos vendidos
    DECLARE @TotalProductosVendidos INT;
    SELECT @TotalProductosVendidos = SUM(DP.cantidad)
    FROM DetallePedido DP
    INNER JOIN Pedido P ON DP.idPedido = P.idPedido
    WHERE MONTH(P.fechaPedido) = @Mes;

    -- Cliente más frecuente
    DECLARE @ClienteFrecuente NVARCHAR(255), @CantidadPedidos INT;
    SELECT TOP 1
        @ClienteFrecuente = C.nombreCliente,
        @CantidadPedidos = COUNT(P.idPedido)
    FROM Pedido P
    INNER JOIN Cliente C ON P.idCliente = C.idCliente
    WHERE MONTH(P.fechaPedido) = @Mes
    GROUP BY C.nombreCliente
    ORDER BY COUNT(P.idPedido) DESC;

    -- Plato más vendido
    DECLARE @PlatoMasVendido NVARCHAR(255), @CantidadVendida INT;
    SELECT TOP 1
        @PlatoMasVendido = PL.nombrePlato,
        @CantidadVendida = SUM(DP.cantidad)
    FROM DetallePedido DP
    INNER JOIN Plato PL ON DP.numeroPlato = PL.numeroPlato
    INNER JOIN Pedido P ON DP.idPedido = P.idPedido
    WHERE MONTH(P.fechaPedido) = @Mes
    GROUP BY PL.nombrePlato
    ORDER BY SUM(DP.cantidad) DESC;

    -- Resultados consolidados
    SELECT 
        @TotalProductosVendidos AS TotalProductosVendidos,
        @ClienteFrecuente AS ClienteMasFrecuente,
        @CantidadPedidos AS PedidosClienteFrecuente,
        @TotalVentas AS TotalVentasMes,
        @PlatoMasVendido AS PlatoMasVendido,
        @CantidadVendida AS CantidadVendidaPlato;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ResumenVentasMensuales]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Procedimiento 1: Resumen General de Ventas Mensuales
CREATE PROCEDURE [dbo].[usp_ResumenVentasMensuales]
    @Mes INT  -- El mes ingresado por el administrador
AS
BEGIN
    -- Total de ventas
    SELECT 
        SUM(P.totalPedido) AS TotalVentas
    FROM Pedido P
    WHERE MONTH(P.fechaPedido) = @Mes;

    -- Cantidad de productos vendidos
    SELECT 
        SUM(DP.cantidad) AS TotalProductosVendidos
    FROM DetallePedido DP
    INNER JOIN Pedido P ON DP.idPedido = P.idPedido
    WHERE MONTH(P.fechaPedido) = @Mes;

    -- Promedio de ventas
    SELECT 
        AVG(P.totalPedido) AS PromedioVentas
    FROM Pedido P
    WHERE MONTH(P.fechaPedido) = @Mes;
END;

GO
/****** Object:  StoredProcedure [dbo].[uspClienteMasFrecuente]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[uspClienteMasFrecuente] (@mes int)
as
begin
	select top 1 Cliente.nombreCliente, count(Pedido.idCliente) as NúmeroPedidos
	from Cliente inner join Pedido on Cliente.idCliente = Pedido.idCliente
	where month(Pedido.fechaPedido) = @mes
	group by Cliente.nombreCliente
	order by NúmeroPedidos desc
end 
GO
/****** Object:  StoredProcedure [dbo].[uspListaPedidoSegunMes]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[uspListaPedidoSegunMes](@mes int)
as 
begin 
	select Pedido.idPedido, Pedido.codigoPedido, fechaPedido, Pedido.horaPedido, Pedido.idCliente, Pedido.totalPedido
	from Pedido
	where month(Pedido.fechaPedido) = @mes
	order by Pedido.idPedido
end 
GO
/****** Object:  StoredProcedure [dbo].[uspListarPlatoMasVendidoPorMes]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[uspListarPlatoMasVendidoPorMes] (@mes int)
as
begin
	select top 1 DetallePedido.nombrePlato, count(DetallePedido.cantidad) as CantidadPlato 
	from DetallePedido inner join Pedido
	on Pedido.idPedido = DetallePedido.idPedido
	where month(Pedido.fechaPedido) = @mes
	group by nombrePlato
	order by CantidadPlato desc
end 
GO
/****** Object:  StoredProcedure [dbo].[uspTotalVendidoEnElMes]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[uspTotalVendidoEnElMes] (@mes int)
as
begin
	select sum(Pedido.totalPedido) as TotalDelMes
	from Pedido
	where month(Pedido.fechaPedido) = @mes
end 
GO
/****** Object:  Trigger [dbo].[trigger_EliminarPedido]    Script Date: 9/12/2024 12:00:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [dbo].[trigger_EliminarPedido]
ON [dbo].[Pedido]
instead of delete
AS
BEGIN
	delete from Pedido
	where idPedido = (select idPedido from deleted)

	delete from DetallePedido
	where idPedido = (select idPedido from deleted)
END;
GO
ALTER TABLE [dbo].[Pedido] ENABLE TRIGGER [trigger_EliminarPedido]
GO
USE [master]
GO
ALTER DATABASE [RESTAURANTE] SET  READ_WRITE 
GO
