
--create database XmlDb

--go

IF OBJECT_ID(N'dbo.CrawlDataConfiguration', N'U') IS NOT NULL
BEGIN
    drop table CrawlDataConfiguration
END
create table CrawlDataConfiguration (
	id int identity(1,1) primary key,
	deleted bit,
	createdAt datetime,
	updatedAt datetime,
	className varchar(50),
	propertyName varchar(50),
	name nvarchar(100),
)

go

IF OBJECT_ID(N'dbo.CrawlDomainConfiguration', N'U') IS NOT NULL
BEGIN
    drop table CrawlDomainConfiguration
END
create table CrawlDomainConfiguration (
	id int identity(1,1) primary key,
	deleted bit,
	createdAt datetime,
	updatedAt datetime,
	domainName nvarchar(200),
	initUrl nvarchar(200),
	pagingXPathQuery nvarchar(MAX),
)

go

IF OBJECT_ID(N'dbo.CrawlDataMappingConfiguration', N'U') IS NOT NULL
BEGIN
    drop table CrawlDataMappingConfiguration
END
create table CrawlDataMappingConfiguration (
	id int identity(1,1) primary key,
	deleted bit,
	createdAt datetime,
	updatedAt datetime,
	domainId int references CrawlDomainConfiguration(id),
	dataId int references CrawlDataConfiguration(id),
	xPathQuery nvarchar(max),
	isNodeResult bit,
)

go

IF OBJECT_ID(N'dbo.Category', N'U') IS NOT NULL
BEGIN
    drop table Category
END
create table Category (
	id int identity(1,1) primary key,
	deleted bit,
	createdAt datetime,
	updatedAt datetime,
	name nvarchar(100),
)

go

IF OBJECT_ID(N'dbo.User', N'U') IS NOT NULL
BEGIN
    drop table [User]
END
create table [User] (
	id int identity(1,1) primary key,
	deleted bit,
	createdAt datetime,
	updatedAt datetime,
	username nvarchar(100),
	password varchar(100),
	fullName nvarchar(200),
	isAdmin bit,
)

go

IF OBJECT_ID(N'dbo.CategoryRaw', N'U') IS NOT NULL
BEGIN
    drop table CategoryRaw
END
create table CategoryRaw (
	id int identity(1,1) primary key,
	deleted bit,
	createdAt datetime,
	updatedAt datetime,
	[name] nvarchar(200),
	[url] nvarchar(200),
	domainId int,
	categoryId int,
)

go

IF OBJECT_ID(N'dbo.ProductRaw', N'U') IS NOT NULL
BEGIN
    drop table ProductRaw
END
create table ProductRaw (
	id int identity(1,1) primary key,
	deleted bit,
	createdAt datetime,
	updatedAt datetime,
	[name] nvarchar(max),
	imgUrl nvarchar(max),
	price int,
	detailUrl nvarchar(max),
	categoryRawId int,
	hashCode int,
	isNew bit,
)

go

IF OBJECT_ID(N'dbo.ProductDetailRaw', N'U') IS NOT NULL
BEGIN
    drop table ProductDetailRaw
END
create table ProductDetailRaw (
	id int identity(1,1) primary key,
	deleted bit,
	createdAt datetime,
	updatedAt datetime,
	[description] nvarchar(max),
	productRawId int,
)

go

IF OBJECT_ID(N'dbo.Product', N'U') IS NOT NULL
BEGIN
    drop table Product
END
create table Product (
	id int identity(1,1) primary key,
	deleted bit,
	createdAt datetime,
	updatedAt datetime,
	name nvarchar(max),
	categoryId int references Category(id),
	description nvarchar(max),
	imageUrl nvarchar(max),
	price int,
	productRawId int references ProductRaw(id),
)

go

IF OBJECT_ID(N'dbo.Order', N'U') IS NOT NULL
BEGIN
    drop table [Order]
END
create table [Order] (
	id int identity(1,1) primary key,
	deleted bit,
	createdAt datetime,
	updatedAt datetime,
	orderCode char(14),
	amount int,
	phoneNo varchar(20),
	[address] nvarchar(max),
	customerId int references [User](id)
)

go

IF OBJECT_ID(N'dbo.OrderDetail', N'U') IS NOT NULL
BEGIN
    drop table OrderDetail
END
create table OrderDetail (
	id int identity(1,1) primary key,
	deleted bit,
	createdAt datetime,
	updatedAt datetime,
	productName nvarchar(max),
	orderId int references [Order](id),
	productId int references Product(id),
	price int,
	quantity int,
)

go

--insert xpath expression
insert into [User](username, password, fullName, isAdmin, createdAt, deleted) 
values ('admin', '123123', N'Dương Anh Tuấn', 1, GETDATE(), 0)

go
set identity_insert CrawlDomainConfiguration on
insert into CrawlDomainConfiguration(id, domainName, initUrl, pagingXPathQuery, createdAt, deleted)
values (1, N'Đà Lạt laptop', 'https://dalatlaptop.com/', N'//a[@rel="next"]/@href', GETDATE(), 0)
insert into CrawlDomainConfiguration(id, domainName, initUrl, pagingXPathQuery, createdAt, deleted)
values (2, N'Nam Trường Thịnh ', 'http://namtruongthinhdalat.vn/', N'//a[@title="Trang sau"]/@href', GETDATE(), 0)
set identity_insert CrawlDomainConfiguration off

go 

set identity_insert CrawlDataConfiguration on
insert into CrawlDataConfiguration(id, name, className, propertyName, createdAt, deleted)
values (1, N'Category Block', 'CategoryRaw', null, GETDATE(), 0)
insert into CrawlDataConfiguration(id, name, className, propertyName, createdAt, deleted)
values (2, N'Category Url', 'CategoryRaw', 'url', GETDATE(), 0)
insert into CrawlDataConfiguration(id, name, className, propertyName, createdAt, deleted)
values (3, N'Category Name', 'CategoryRaw', 'name', GETDATE(), 0)

insert into CrawlDataConfiguration(id, name, className, propertyName, createdAt, deleted)
values (4, N'Product Block', 'ProductRaw', null, GETDATE(), 0)
insert into CrawlDataConfiguration(id, name, className, propertyName, createdAt, deleted)
values (5, N'Product Name', 'ProductRaw', 'name', GETDATE(), 0)
insert into CrawlDataConfiguration(id, name, className, propertyName, createdAt, deleted)
values (6, N'Product Image URL', 'ProductRaw', 'imgUrl', GETDATE(), 0)
insert into CrawlDataConfiguration(id, name, className, propertyName, createdAt, deleted)
values (7, N'Product Price', 'ProductRaw', 'price', GETDATE(), 0)
insert into CrawlDataConfiguration(id, name, className, propertyName, createdAt, deleted)
values (8, N'Product detail Url', 'ProductRaw', 'detailUrl', GETDATE(), 0)

insert into CrawlDataConfiguration(id, name, className, propertyName, createdAt, deleted)
values (9, N'Product detail Block', 'ProductDetailRaw', null, GETDATE(), 0)
insert into CrawlDataConfiguration(id, name, className, propertyName, createdAt, deleted)
values (10, N'Product detail Description', 'ProductDetailRaw', 'description', GETDATE(), 0)
set identity_insert CrawlDataConfiguration off

go

set identity_insert CrawlDataMappingConfiguration on

------------------------------------------------------------------------------------------------------------

--insert data for dalatlaptop
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (1, 1, 1, 0, N'//nav[@class="primary-nav"]//li[count(./*) = 1]', getdate(), 0)
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (2, 1, 2, 0, N'//a/@href', getdate(), 0)
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (3, 1, 3, 0, N'//a', getdate(), 0)

--insert productRaw
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (4, 1, 4, 0, N'//div[@class="col-xs-6 col-md-3 product sale sb-theme-product"]', getdate(), 0)
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (5, 1, 5, 0, N'//h2/a', getdate(), 0)
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (6, 1, 6, 0, N'//img/@src', getdate(), 0)
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (7, 1, 7, 0, N'//ins/span', getdate(), 0)
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (8, 1, 8, 0, N'//h2/a/@href', getdate(), 0)

--insert ProductDetailRaw
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (9, 1, 9, 0, N'//div[@class="woocommerce-Tabs-panel woocommerce-Tabs-panel--description panel entry-content wc-tab"]', getdate(), 0)
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (10, 1, 10, 1, N'/*[not(local-name(.) = "h2" and string(.) = "Mô tả sản phẩm")]', getdate(), 0)

--------------------------------------------------------------------------------------------------------


--insert data for namTruongThinh
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (11, 2, 1, 0, N'//div[@id="t3-off-canvas" and @class="t3-off-canvas "]//ul[@class="dropdown-menu"]/li[not(contains(@class,"dropdown-submenu"))]', getdate(), 0)
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (12, 2, 2, 0, N'//a/@href', getdate(), 0)
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (13, 2, 3, 0, N'//a', getdate(), 0)

--insert productRaw
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (14, 2, 4, 0, N'//div[@class="col-xs-12 col-sm-4 col-md-2 block_product"]', getdate(), 0)
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (15, 2, 5, 0, N'//div[@class="name"]/a', getdate(), 0)
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (16, 2, 6, 0, N'//img[@class="jshop_img img-responsive"]/@src', getdate(), 0)
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (17, 2, 7, 0, N'//div[@class="jshop_price"]/span', getdate(), 0)
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (18, 2, 8, 0, N'//div[@class="name"]/a/@href', getdate(), 0)

--insert ProductDetailRaw
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (19, 2, 9, 0, N'//div[@class="tab-content"]', getdate(), 0)
insert into CrawlDataMappingConfiguration(id, domainId, dataId, isNodeResult, xPathQuery, createdAt, deleted)
values (20, 2, 10, 1, N'/div/*', getdate(), 0)


set identity_insert CrawlDataMappingConfiguration off




--insert category
GO
SET IDENTITY_INSERT [dbo].[Category] ON 
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (1, 0, CAST(N'2019-03-17T23:43:40.780' AS DateTime), NULL, N'Laptop')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (2, 0, CAST(N'2019-03-17T23:43:56.227' AS DateTime), NULL, N'Máy bàn')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (3, 0, CAST(N'2019-03-17T23:44:04.450' AS DateTime), NULL, N'RAM')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (4, 0, CAST(N'2019-03-17T23:44:09.880' AS DateTime), NULL, N'Màn hình LCD')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (5, 0, CAST(N'2019-03-17T23:44:20.350' AS DateTime), NULL, N'Ổ cứng')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (6, 0, CAST(N'2019-03-17T23:44:32.110' AS DateTime), NULL, N'Bàn phím')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (7, 0, CAST(N'2019-03-17T23:44:46.000' AS DateTime), NULL, N'Card màn hình')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (8, 0, CAST(N'2019-03-17T23:45:07.113' AS DateTime), NULL, N'Thiết bị mạng')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (9, 0, CAST(N'2019-03-17T23:45:18.573' AS DateTime), NULL, N'Chuột máy tính')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (10, 0, CAST(N'2019-03-17T23:45:32.943' AS DateTime), NULL, N'SSD')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (11, 0, CAST(N'2019-03-17T23:45:39.530' AS DateTime), NULL, N'Ổ cứng di động')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (12, 0, CAST(N'2019-03-17T23:45:47.723' AS DateTime), NULL, N'Camera')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (13, 0, CAST(N'2019-03-17T23:46:15.180' AS DateTime), NULL, N'Máy in')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (14, 0, CAST(N'2019-03-17T23:46:43.643' AS DateTime), NULL, N'Thiết bị văn phòng')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (15, 0, CAST(N'2019-03-17T23:47:12.067' AS DateTime), NULL, N'CPU')
GO
INSERT [dbo].[Category] ([id], [deleted], [createdAt], [updatedAt], [name]) VALUES (16, 0, CAST(N'2019-03-17T23:47:51.740' AS DateTime), NULL, N'Khác')
GO
SET IDENTITY_INSERT [dbo].[Category] OFF
GO
