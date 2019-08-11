Declare @BackupFile varchar(255)
IF  NOT EXISTS (SELECT * FROM sys.databases WHERE name = N'QCBuild')
BEGIN
    
   CREATE DATABASE QCBuild
   ON
   ( NAME = QCBuildMDF,  
       FILENAME = 'D:\QCBuildMDFFile.mdf',
       SIZE = 10,
       MAXSIZE = 50,
       FILEGROWTH = 5 )  
   LOG ON
   ( NAME = QCBuildLDF,  
       FILENAME = 'D:\QCBuildLDFFile.ldf',
       SIZE = 5,
       MAXSIZE = 25,
       FILEGROWTH = 5 )
   
END
set @BackupFile = @FTPPath + '\\Qcommission.bak'
ALTER DATABASE QCBuild
SET SINGLE_USER WITH
ROLLBACK IMMEDIATE
--$(FTPPath)
----Restore Database
RESTORE DATABASE QCBuild
FROM DISK = @FTPPath WITH replace 

 
/*If there is no error in statement before database will be in multiuser
mode.
If error occurs please execute following command it will convert
database in multi user.*/
ALTER DATABASE QCBuild SET MULTI_USER
GO

