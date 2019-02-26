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
)

go

IF OBJECT_ID(N'dbo.MasterCategory', N'U') IS NOT NULL
BEGIN
    drop table MasterCategory
END
create table MasterCategory (
	id int identity(1,1) primary key,
	deleted bit,
	createdAt datetime,
	updatedAt datetime,
	name nvarchar(100),
)

go

IF OBJECT_ID(N'dbo.SubCategory', N'U') IS NOT NULL
BEGIN
    drop table SubCategory
END
create table SubCategory (
	id int identity(1,1) primary key,
	deleted bit,
	createdAt datetime,
	updatedAt datetime,
	name nvarchar(100),
	masterCategoryId int references MasterCategory(id),
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
	name nvarchar(100),
	subCategoryId int references SubCategory(id),
	description nvarchar(max),
	imageUrl nvarchar(200),
	price int,
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

IF OBJECT_ID(N'dbo.Order', N'U') IS NOT NULL
BEGIN
    drop table [Order]
END
create table [Order] (
	id int identity(1,1) primary key,
	deleted bit,
	createdAt datetime,
	updatedAt datetime,
	orderCode nvarchar(100),
	amount int,
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
	productName nvarchar(100),
	orderId int references [Order](id),
	productId int references Product(id),
	price int,
	quantity int,
)

go

